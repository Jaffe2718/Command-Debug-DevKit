package me.jaffe2718.sckinj;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ServerSocket;

public class SocketInjector implements ModInitializer {

    /**
     * The mod ID.
     */
    public static final String MOD_ID = "sckinj";

    /**
     * The mod logger.
     */
    public static Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static ServerSocket serverSocket = null;

    /**
     * Runs the mod initializer.
     */
    @Override
    public void onInitialize() {
        LOGGER.info("Initializing SocketInjector...");
        try {
            serverSocket = new ServerSocket(0);
            LOGGER.info("Server socket created on localhost:" + serverSocket.getLocalPort());
        } catch (Exception e) {
            LOGGER.error("Failed to create server socket: " + e.getMessage());
        }
    }
}
