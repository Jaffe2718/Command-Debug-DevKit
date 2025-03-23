package me.jaffe2718.cmdkit.event;

import com.google.common.collect.Lists;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import io.netty.channel.local.LocalAddress;
import me.jaffe2718.cmdkit.CommandDebugDevKit;
import me.jaffe2718.cmdkit.util.ClientSocketConnectionHandler;
import me.jaffe2718.cmdkit.util.DatapackManager;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.text.*;
import net.minecraft.util.math.ColorHelper;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class EventHandler {

    public static MinecraftServer serverRef = null;

    /**
     * Synchronized queue for sending messages to the client socket.
     */
    public static Queue<String> syncMessageQueue = new ConcurrentLinkedQueue<>();

    /**
     * The last command that the player input in the chat screen.
     */
    public static volatile String lastInput = "";

    /**
     * The list of client execute sockets.
     * To manage the client execute sockets.
     */
    private static final List<Socket> CLIENT_EXECUTE_SOCKETS = new ArrayList<>();

    /**
     * The list of client suggest sockets.
     * To manage the client suggest sockets.
     */
    private static final List<Socket> CLIENT_SUGGEST_SOCKETS = new ArrayList<>();

    /**
     * The thread that accepts the new client execute socket.
     * To accept the client execute socket.
     */
    private static final Thread ACCEPT_EXECUTE_SOCKET_THREAD = new Thread(() -> {
        while (true) {
            try {
                Socket clientSocket = ClientSocketConnectionHandler.acceptClientSocket(CommandDebugDevKit.executeCmdSocket);
                CLIENT_EXECUTE_SOCKETS.add(clientSocket);
                buildClientSocketThread(clientSocket, true).start();
                CommandDebugDevKit.LOGGER.info("Client socket accepted on {}: {}", clientSocket.getInetAddress().getHostAddress(), clientSocket.getPort());
            } catch (Exception e) {
                CommandDebugDevKit.LOGGER.error("Failed to accept client socket: {} {}",e.getClass(), e.getMessage());
            }
        }
    });

    /**
     * The thread that accepts the new client suggest socket.
     * To accept the client suggest socket.
     */
    private static final Thread ACCEPT_SUGGEST_SOCKET_THREAD = new Thread(() -> {
        while (true) {
            try {
                Socket clientSocket = ClientSocketConnectionHandler.acceptClientSocket(CommandDebugDevKit.suggestCmdSocket);
                CLIENT_SUGGEST_SOCKETS.add(clientSocket);
                buildClientSocketThread(clientSocket, false).start();
                CommandDebugDevKit.LOGGER.info("Client socket accepted on localhost: {}", clientSocket.getLocalPort());
            } catch (Exception e) {
                CommandDebugDevKit.LOGGER.error("Failed to accept client socket: {} {}", e.getClass(), e.getMessage());
            }
        }
    });

    /**
     * The thread that checks the client socket.
     * To check the client socket and remove the closed client socket.
     * @see EventHandler#CLIENT_EXECUTE_SOCKETS
     * @see EventHandler#CLIENT_SUGGEST_SOCKETS
     * @see CommandDebugDevKit#executeCmdSocket
     * @see CommandDebugDevKit#suggestCmdSocket
     */
    private static final Thread MANAGE_CLIENT_SOCKET_THREAD = new Thread(() -> {
        while (true) {
            for (Socket clientSocket : CLIENT_EXECUTE_SOCKETS) {
                if (clientSocket.isClosed() || !clientSocket.isConnected()) {
                    CLIENT_EXECUTE_SOCKETS.remove(clientSocket);
                    CommandDebugDevKit.LOGGER.info("Client socket {}:{} is disconnected", clientSocket.getInetAddress().getHostAddress(), clientSocket.getPort());
                }
            }
            for (Socket clientSocket : CLIENT_SUGGEST_SOCKETS) {
                if (clientSocket.isClosed() || !clientSocket.isConnected()) {
                    CLIENT_SUGGEST_SOCKETS.remove(clientSocket);
                    CommandDebugDevKit.LOGGER.info("Client socket {}:{} is disconnected", clientSocket.getInetAddress().getHostAddress(), clientSocket.getPort());
                }
            }
        }
    });

    /**
     * The thread that sends the message to the client socket.
     * To send the message to the client socket.
     * @see EventHandler#syncMessageQueue
     */
    private static final Thread SYNC_MESSAGE_THREAD = new Thread(() -> {
        while (true) {
            if (!syncMessageQueue.isEmpty()) {
                String message = syncMessageQueue.poll();
                try {
                    for (Socket clientSocket : CLIENT_EXECUTE_SOCKETS) {
                        PrintWriter pw = new PrintWriter(clientSocket.getOutputStream(), true);
                        pw.println(message);
                    }
                } catch (IOException ignored) {}
            }
        }
    });


    /**
     * Registers the event handler.
     */
    public static void register() {
        ACCEPT_EXECUTE_SOCKET_THREAD.start();
        ACCEPT_SUGGEST_SOCKET_THREAD.start();
        MANAGE_CLIENT_SOCKET_THREAD.start();
        SYNC_MESSAGE_THREAD.start();
        DatapackManager.DATAPACK_MANAGEMENT_SOCKET_THREAD.start();
        ServerWorldEvents.LOAD.register((server, world) -> serverRef = server);
        ServerWorldEvents.UNLOAD.register((server, world) -> serverRef = null);
        ServerWorldEvents.UNLOAD.register(DatapackManager::delateTempDatapacks);
        ServerPlayConnectionEvents.JOIN.register(EventHandler::showSocketInfo);
    }

    /**
     * Builds the thread that gets and listens to the client socket then resolves the message from client socket.
     * @param clientSocket The client socket that the thread listens to
     * @param execute The flag that indicates whether the client socket is for client execute or get suggestions
     */
    @Contract("_, _ -> new")
    private static @NotNull Thread buildClientSocketThread(Socket clientSocket, boolean execute) {
        return Thread.ofVirtual().unstarted(() -> {
            while (clientSocket !=null && clientSocket.isConnected() && !clientSocket.isClosed()) {
                try {
                    assert MinecraftClient.getInstance().player != null;
                    BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    String cmd = br.readLine().replace("\n", "");
                    if (execute && EventHandler.serverRef != null && !cmd.isBlank()) {
                        try {
                            EventHandler.serverRef.getCommandManager().getDispatcher().execute(cmd, EventHandler.serverRef.getCommandSource());
                        } catch (CommandSyntaxException cse) {
                            EventHandler.syncMessageQueue.add(cse.getLocalizedMessage());
                        }
                    } else if (EventHandler.serverRef != null) {  // get suggestion by read and send suggestion back
                        List<String> suggestions = EventHandler.getCommandSuggestions(cmd, serverRef);
                        PrintWriter pw = new PrintWriter(clientSocket.getOutputStream(), true);
                        for (String s : suggestions) {
                            pw.println(s);
                        }
                    }
                } catch (IOException ignored) {}
            }
        });
    }


    public static @NotNull List<String> getCommandSuggestions(@NotNull String cmd, @NotNull MinecraftServer server) {
        EventHandler.lastInput = cmd;
        List<String> suggestionList = new ArrayList<>();
        try {
            ParseResults<ServerCommandSource> parseResults = server.getCommandManager().getDispatcher().parse(cmd, server.getCommandSource());
            CompletableFuture<Suggestions> suggestions = server.getCommandManager().getDispatcher().getCompletionSuggestions(parseResults);
            suggestions.get().getList().forEach(suggestion -> suggestionList.add(suggestion.getText()));
        } catch (Exception ignored) {}
        return suggestionList;
    }

    /**
     * Shows the client socket info and warning when the player joins a world.
     * @param server The server.
     */
    private static void showSocketInfo(ServerPlayNetworkHandler networkHandler, PacketSender packetSender, @NotNull MinecraftServer server) {
        boolean showSocketInfo = true;
        List<String> trustedIPv4Addresses = Lists.newArrayList();
        try {
            showSocketInfo = CommandDebugDevKit.getSecurityConfig("shouldShowSocketInfo");
            trustedIPv4Addresses = CommandDebugDevKit.getSecurityConfig("trustedIPv4Addresses");
        } catch (Exception ignored) {}
        if (showSocketInfo
                && (networkHandler.getConnectionAddress() instanceof LocalAddress
                        || (networkHandler.getConnectionAddress() instanceof InetSocketAddress inetSocketAddress
                                && trustedIPv4Addresses.contains(inetSocketAddress.getAddress().getHostAddress())))) {
            Text[] texts = {
                    Text.literal(Text.translatable("message.cmdkit.run").getString()),
                    Text.literal(Text.translatable("message.cmdkit.service.execution").getString()),
                    clickToCopy(String.format("%s:%d", CommandDebugDevKit.ipv4, CommandDebugDevKit.executeCmdSocket.getLocalPort()), ColorHelper.getArgb(255, 170, 0)),
                    Text.literal(Text.translatable("message.cmdkit.service.suggestion").getString()),
                    clickToCopy(String.format("%s:%d", CommandDebugDevKit.ipv4, CommandDebugDevKit.suggestCmdSocket.getLocalPort()), ColorHelper.getArgb(255, 85, 255)),
                    Text.literal(Text.translatable("message.cmdkit.service.datapackManagement").getString()),
                    clickToCopy(String.format("%s:%d", CommandDebugDevKit.ipv4, CommandDebugDevKit.manageDatapackSocket.getLocalPort()), ColorHelper.getArgb(85, 255, 255)),
                    Text.literal(Text.translatable("message.cmdkit.warining").getString())
            };
            Thread.ofVirtual().start(() -> {
                try {
                    Thread.sleep(1024);
                } catch (InterruptedException ignored) {}
                for (Text msg : texts) {
                    networkHandler.player.sendMessage(msg);
                }
            });
        }
    }

    /**
     * Creates a clickable text, click to copy the text.
     * @param text The text.
     * @param color The color, use {@link ColorHelper#getArgb(int, int, int)} to get the color.
     * @return The clickable text.
     */
    private static Text clickToCopy(String text, int color) {
        String tabStr = "    ";
        return Text.literal(tabStr).append(
                Text.literal(text)
                        .setStyle(
                                Style.EMPTY.withUnderline(true).withClickEvent(
                                        new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, text)
                                ).withColor(color)
                        )
        );
    }

}
