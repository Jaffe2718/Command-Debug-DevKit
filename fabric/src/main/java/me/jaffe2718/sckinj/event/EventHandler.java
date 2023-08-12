package me.jaffe2718.sckinj.event;

import me.jaffe2718.sckinj.SocketInjector;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public abstract class EventHandler {

    private static boolean shown = false;

    /**
     * The list of client sockets.
     * To manage the client sockets.
     */
    private static final List<Socket> clientSockets = new ArrayList<>();

    /**
     * The thread that accepts the new client socket.
     * To accept the client socket.
     */
    private static final Thread acceptClientSocketThread = new Thread(() -> {
        while (true) {
            try {
                Socket clientSocket = SocketInjector.serverSocket.accept();
                clientSockets.add(clientSocket);
                buildClientSocketThread(clientSocket).start();
                SocketInjector.LOGGER.info("Client socket accepted on localhost:" + clientSocket.getLocalPort());
            } catch (IOException e) {
                SocketInjector.LOGGER.error("Failed to accept client socket: " + e.getMessage());
            }
        }
    });

    /**
     * The thread that checks the client socket.
     * To check the client socket.
     */
    private static final Thread checkClientSocketThread = new Thread(() -> {
        while (true) {
            for (Socket clientSocket : clientSockets) {
                if (clientSocket.isClosed() || !clientSocket.isConnected()) {
                    clientSockets.remove(clientSocket);
                    SocketInjector.LOGGER.info("Client socket removed on localhost:" + clientSocket.getLocalPort());
                }
            }
        }
    });

    /**
     * Registers the event handler.
     * 1. Creates a thread that gets and listens to the client socket then resolves the message from client socket.
     * 2. Registers the event handler for the client tick event: show client socket info and warning when player joins a world.
     * 3. Registers the event handler for the client receive message event: send the chat message to the client socket.
     */
    public static void register() {
        acceptClientSocketThread.start();
        checkClientSocketThread.start();
        // EventHandler.getClientSocketThread.start();
        ClientTickEvents.END_CLIENT_TICK.register(EventHandler::showWarning);
        ClientReceiveMessageEvents.GAME.register(EventHandler::sendChatToClientSocket);
    }

    @Contract("_ -> new")
    private static @NotNull Thread buildClientSocketThread(Socket clientSocket) {
        return new Thread(() -> {
            while (clientSocket !=null && clientSocket.isConnected() && !clientSocket.isClosed()) {
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    String cmd = br.readLine().replace("\n", "").replace("\r", "").replace("§", "");
                    assert MinecraftClient.getInstance().player != null && !cmd.isBlank();
                    MinecraftClient.getInstance().player.networkHandler.sendChatCommand(cmd);
                } catch (EOFException eof) {   // Client socket closed
                    try {
                        clientSocket.close();
                        return;
                    } catch (IOException ignored) {
                        return;
                    }
                } catch (Exception ignored) {
                }
            }
        });
    }


    private static void sendChatToClientSocket(@NotNull Text chatMsg, boolean overlay) {
        try {
            int count = 0;
            for (Socket clientSocket : clientSockets) {
                PrintWriter pw = new PrintWriter(clientSocket.getOutputStream(), true);
                pw.println(chatMsg.getString());
                count++;
            }
            SocketInjector.LOGGER.info("Chat message sent to " + count + " client socket(s).");
        } catch (Exception ignore) {
        }
    }

    private static void showWarning(@NotNull MinecraftClient client) {
        if (client.player == null) {
            shown = false;
        } else if (!shown) {
            client.player.sendMessage(Text.of("§a[Socket Injection Debugger] The Socket Server is running on"), false);
            client.player.sendMessage(Text.of("  §6localhost:" + SocketInjector.serverSocket.getLocalPort()), false);
            client.player.sendMessage(Text.of("Connect to this server with a socket client to send commands to the server."), false);
            client.player.sendMessage(Text.of("§c§lWARNING: §r§cDo not share this host and port with untrusted clients!"), false);
            shown = true;
        }
    }
}
