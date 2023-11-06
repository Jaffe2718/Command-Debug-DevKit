package me.jaffe2718.cmdkit;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ServerSocket;

public class CommandDebugDevKit implements ModInitializer {

    /**
     * The mod ID.
     */
    public static final String MOD_ID = "cmdkit";

    /**
     * The mod logger.
     */
    public static Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    /**
     * The server socket used to listen for commands and execute them.
     * */
    public static ServerSocket executeCmdSocket = null;

    /**
     * The server socket used to listen for commands and send suggestions.
     * */
    public static ServerSocket suggestCmdSocket = null;

    /**
     * The server socket used to receive datapack files.
     * */
    public static ServerSocket receiveDatapackSocket = null;

    /**
     * Runs the mod initializer.
     */
    @Override
    public void onInitialize() {
        LOGGER.info("Initializing CommandDebugDevKit...");
        try {
            executeCmdSocket = new ServerSocket(0);
            suggestCmdSocket = new ServerSocket(0);
            receiveDatapackSocket = new ServerSocket(0);
            LOGGER.info("Server socket created on localhost:" + executeCmdSocket.getLocalPort());
        } catch (Exception e) {
            LOGGER.error("Failed to create server socket: " + e.getMessage());
        }
    }
}
