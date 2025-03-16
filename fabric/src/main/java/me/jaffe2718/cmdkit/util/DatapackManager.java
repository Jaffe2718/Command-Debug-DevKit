package me.jaffe2718.cmdkit.util;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.jaffe2718.cmdkit.CommandDebugDevKit;
import me.jaffe2718.cmdkit.event.EventHandler;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Base64;
import java.util.Comparator;
import java.util.stream.Stream;

import static java.nio.file.Files.walk;

public abstract class DatapackManager {

    private static final List<Path> TEMPORARY_LINKED_DATAPACKS = Lists.newArrayList();

    /**
     * The thread that accepts the new client datapack management socket.
     * To accept the client datapack management socket.
     * @see CommandDebugDevKit#manageDatapackSocket
     * @see DatapackManager#TEMPORARY_LINKED_DATAPACKS
     */
    public static final Thread DATAPACK_MANAGEMENT_SOCKET_THREAD = new Thread(() -> {
        while (true) {
            try {
                Socket clientSocket = ClientSocketConnectionHandler.acceptClientSocket(CommandDebugDevKit.manageDatapackSocket);
                CommandDebugDevKit.LOGGER.info("Datapack management socket accepted on {}:{}", clientSocket.getInetAddress().getHostAddress(), clientSocket.getPort());
                BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String jsonStr = br.readLine();
                // parse the json
                JsonObject jsonObject = new Gson().fromJson(jsonStr, JsonObject.class);
                String flag = jsonObject.get("flag").getAsString();
                String datapackName = !flag.equals("query") ? jsonObject.get("name").getAsString() : "";
                String base64EncodedZip = flag.equals("link") || flag.equals("import") ? jsonObject.get("data").getAsString() : "";
                switch (flag) {
                    case "link" -> importOrLinkDatapack(datapackName, base64EncodedZip, false);
                    case "import" -> importOrLinkDatapack(datapackName, base64EncodedZip, true);
                    case "unlink" -> deleteOrUnlinkDatapack(datapackName, false);
                    case "delete" -> deleteOrUnlinkDatapack(datapackName, true);
                    case "enable" -> enableDatapack(datapackName);
                    case "disable" -> disableDatapack(datapackName);
                    case "query" -> queryDatapacksInfo(clientSocket);
                    default -> throw new RuntimeException("Unknown flag: " + flag);
                }
                clientSocket.close();
            } catch (Exception e) {
                CommandDebugDevKit.LOGGER.error("{} {}", e.getClass(), e.getMessage());
            }
        }
    });


    /**
     * Import or link a datapack.
     * @param name The name of the datapack zip file, automatically overwritten if the name already exists.
     * @param base64Data The base64 encoded zip file.
     * @param isTemp Whether to import the datapack or link it.
     * */
    private static void importOrLinkDatapack(String name, String base64Data, boolean isTemp) {
        final Path minecraftDir = FabricLoader.getInstance().getGameDir();
        try {
            assert EventHandler.serverRef != null;
            Path datapackPath = Paths.get(minecraftDir.toString(), "saves", EventHandler.serverRef.getSaveProperties().getLevelName(), "datapacks", name);
            if (Files.exists(datapackPath)) {
                Thread.ofVirtual().start(() -> {
                    EventHandler.serverRef.getDataPackManager().disable("file/" + name);
                    try {
                        Thread.sleep(16);
                        Files.delete(datapackPath);
                    } catch (Exception ignored) {}
                });
            }
            Files.write(datapackPath, Base64.getDecoder().decode(base64Data), StandardOpenOption.CREATE);
            if (!isTemp && !TEMPORARY_LINKED_DATAPACKS.contains(datapackPath)) {  // link
                TEMPORARY_LINKED_DATAPACKS.add(datapackPath);
            }
            Thread.ofVirtual().start(() -> {
                try {
                    Thread.sleep(16);
                } catch (InterruptedException ignored) {}
                EventHandler.serverRef.getDataPackManager().enable("file/" + name);
                EventHandler.serverRef.getPlayerManager().broadcast(
                        Text.translatable("message.cmdkit.word.Datapack")
                                .append("`")
                                .append(name)
                                .append("` ")
                                .append(Text.translatable("message.cmdkit.word.is"))
                                .append(Text.translatable("message.cmdkit.word." + (isTemp ? "imported" : "linked")))
                                .append("!"),
                        false);
            });
        } catch (Exception e) {
            throw new RuntimeException("Fail to " + (isTemp ? "import" : "link") + " datapack: " + e.getClass() + " "+ e.getMessage());
        }
    }

    /**
     * Delete or unlink a datapack.
     * @param name The name of the datapack zip file.
     * @param isDelete Whether to delete the datapack or unlink it.
     * */
    private static void deleteOrUnlinkDatapack(String name, boolean isDelete) {
        final Path minecraftDir = FabricLoader.getInstance().getGameDir();
        if (EventHandler.serverRef != null) {
            Path datapackPath = Paths.get(minecraftDir.toString(), "saves", EventHandler.serverRef.getSaveProperties().getLevelName(), "datapacks", name);
            Thread.ofVirtual().start(() -> {
                try {
                    if (Files.exists(datapackPath)) {
                        if (!isDelete) {  // unlink
                            boolean isFound = false;
                            for (Path path : TEMPORARY_LINKED_DATAPACKS) {
                                if (path.equals(datapackPath)) {
                                    isFound = true;
                                    TEMPORARY_LINKED_DATAPACKS.remove(path);
                                    break;
                                }
                            }
                            if (!isFound) {
                                throw new RuntimeException("Datapack `" + name + "` is not linked!");
                            }
                        }
                        EventHandler.serverRef.getDataPackManager().disable("file/" + name);
                        Thread.sleep(16);
                        if (datapackPath.toFile().isDirectory()) {
                            try (Stream<Path> walk = walk(datapackPath)) {
                                walk.sorted(Comparator.reverseOrder())
                                        .forEach(path -> {
                                            try {
                                                Files.delete(path);
                                            } catch (Exception ignored) {}
                                        });
                            }
                        } else if (datapackPath.toFile().isFile()) {
                            Files.delete(datapackPath);
                        }
                        EventHandler.serverRef.getPlayerManager().broadcast(
                                Text.translatable("message.cmdkit.word.Datapack")             // `Datapack `
                                        .append("`")
                                        .append(name)
                                        .append("` ")
                                        .append(Text.translatable("message.cmdkit.word.is"))  // `is `
                                        .append(Text.translatable("message.cmdkit.word." + (isDelete ? "deleted" : "unlinked")))
                                        .append("!")
                                , false);
                    } else {
                        throw new RuntimeException("Datapack `" + name + "` does not exist!");
                    }
                } catch (Exception e) {
                    throw new RuntimeException("Fail to " + (isDelete ? "delete" : "unlink") + " datapack: " + e.getClass() + " "+ e.getMessage());
                }
            });
        } else {
            CommandDebugDevKit.LOGGER.error("Fail to delete datapack: Minecraft server is not running!");
        }
    }

    /**
     * Query the info of all datapacks.
     * @param exSocket The socket of the external command.
     * */
    private static void queryDatapacksInfo(Socket exSocket) {
//        MinecraftServer server = mcClient.getServer();
        try {
            PrintWriter pw = new PrintWriter(exSocket.getOutputStream(), true);
            if (EventHandler.serverRef == null) {
                pw.println("{\"enabled\": [], \"all\": [], \"linked\":[] }");
                return;
            }
            StringBuilder sb = new StringBuilder("{");
            sb.append("\"enabled\": [");
            for (String datapackName : EventHandler.serverRef.getDataPackManager().getEnabledIds()) {
                sb.append("\"").append(datapackName).append("\",");
            }
            if (sb.charAt(sb.length() - 1) == ',') {
                sb.deleteCharAt(sb.length() - 1);
            }
            sb.append("], \"all\": [");
            for (String datapackName : EventHandler.serverRef.getDataPackManager().getIds()) {
                sb.append("\"").append(datapackName).append("\",");
            }
            if (sb.charAt(sb.length() - 1) == ',') {
                sb.deleteCharAt(sb.length() - 1);
            }
            sb.append("], \"linked\": [");
            for (Path datapackPath : TEMPORARY_LINKED_DATAPACKS) {
                sb.append("\"").append("file/").append(datapackPath.getFileName()).append("\",");
            }
            if (sb.charAt(sb.length() - 1) == ',') {
                sb.deleteCharAt(sb.length() - 1);
            }
            sb.append("]}");
            pw.println(sb);
        } catch (Exception e) {
            throw new RuntimeException("Fail to query datapacks info: " + e.getClass() + " "+ e.getMessage());
        }
    }

    private static void disableDatapack(@NotNull String datapackName) {
        if (datapackName.contains(".zip") && !datapackName.contains("file/")) {
            datapackName = "file/" + datapackName;
        }
        try {
            EventHandler.serverRef.getDataPackManager().disable(datapackName);
        } catch (Exception ignored) {}
    }

    private static void enableDatapack(@NotNull String datapackName) {
        if (datapackName.contains(".zip") && !datapackName.contains("file/")) {
            datapackName = "file/" + datapackName;
        }
        try {
            EventHandler.serverRef.getDataPackManager().enable(datapackName);
            // EventHandler.serverRef.getCommandManager().getDispatcher().execute("datapack enable " + datapackName, EventHandler.serverRef.getCommandSource());
        } catch (Exception ignored) {}
    }

    /**
     * Delete all temporary datapacks.
     * @param server The Minecraft server.
     * @param ignore The current world.
     */
    public static void delateTempDatapacks(MinecraftServer server, ServerWorld ignore) {
        for (Path datapackPath : TEMPORARY_LINKED_DATAPACKS) {
            String datapackName = "file/" + datapackPath.getFileName();
            if (server.getDataPackManager().getEnabledIds().contains(datapackName)) {
                server.getDataPackManager().disable(datapackName);
            }
        }
        for (Path datapackPath : TEMPORARY_LINKED_DATAPACKS) {
            try {
                Files.delete(datapackPath);
            } catch (Exception ignored) {}
        }
    }
}
