package me.jaffe2718.cmdkit;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
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
     * Runs the mod initializer.
     *
     * @param mod the mod which is initialized
     */
    @Override
    public void onInitialize(ModContainer mod) {
        LOGGER.info("Initializing CommandDebugDevKit...");
        try {
            executeCmdSocket = new ServerSocket(0);
            suggestCmdSocket = new ServerSocket(0);
            LOGGER.info("Server socket created on localhost:" + executeCmdSocket.getLocalPort());
        } catch (Exception e) {
            LOGGER.error("Failed to create server socket: " + e.getMessage());
        }
    }
}
