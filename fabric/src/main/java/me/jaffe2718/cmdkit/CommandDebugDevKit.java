package me.jaffe2718.cmdkit;

import com.google.common.collect.Lists;
import me.jaffe2718.cmdkit.event.EventHandler;
import me.jaffe2718.cmdkit.util.TrustMode;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.util.Enumeration;

public class CommandDebugDevKit implements ModInitializer {

    /**
     * The mod ID.
     */
    public static final String MOD_ID = "cmdkit";

    public static String ipv4;

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
    public static ServerSocket manageDatapackSocket = null;

    /**
     * Initializes the security config.
     */
    private static void initSecurityConfig() {
        try {
            Class<?> midnightConfig = Class.forName("eu.midnightdust.lib.config.MidnightConfig");
            Class<?> securityConfigClass = Class.forName("me.jaffe2718.cmdkit.util.SecurityConfig");
            midnightConfig.getMethod("init", String.class, Class.class)
                    .invoke(null, CommandDebugDevKit.MOD_ID, securityConfigClass);
        } catch (ClassNotFoundException ce) {
            LOGGER.warn("Could not load security config because MidnightLib is not installed", ce);
        } catch (Exception ignored) {}
    }

    /**
     * Gets the security config value.
     * @param fieldName the name of the field.
     * @param <T> the type of the field.
     * @return the value of the field.
     * @see me.jaffe2718.cmdkit.util.SecurityConfig
     */
    @SuppressWarnings("unchecked")
    public static <T> T getSecurityConfig(String fieldName) throws NoSuchFieldException {
        try {
            Class.forName("eu.midnightdust.lib.config.MidnightConfig");
            Class<?> securityConfigClass = Class.forName("me.jaffe2718.cmdkit.util.SecurityConfig");
            return (T) securityConfigClass.getField(fieldName).get(null);
        } catch (ClassNotFoundException | IllegalAccessException e) {
            switch (fieldName) {
                case "executeCmdSocketPort", "suggestCmdSocketPort", "manageDatapackSocketPort" -> {
                    return (T) Integer.valueOf(0);
                }
                case "trustedIPv4Addresses" -> {
                    return (T) Lists.newArrayList();
                }
                case "shouldShowSocketInfo" -> {
                    return (T) Boolean.TRUE;
                }
                case "trustMode" -> {
                    return (T) TrustMode.ALL_ALLOWED;
                }
            }
        }
        throw new NoSuchFieldException(fieldName);
    }

    /**
     * Runs the mod initializer.
     */
    @Override
    public void onInitialize() {
        CommandDebugDevKit.initSecurityConfig();
        try {
            executeCmdSocket = new ServerSocket(CommandDebugDevKit.getSecurityConfig("executeCmdSocketPort"));
            suggestCmdSocket = new ServerSocket(CommandDebugDevKit.getSecurityConfig("suggestCmdSocketPort"));
            manageDatapackSocket = new ServerSocket(CommandDebugDevKit.getSecurityConfig("manageDatapackSocketPort"));
            // Get the local IP address
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                Enumeration<java.net.InetAddress> inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    java.net.InetAddress inetAddress = inetAddresses.nextElement();
                    if (inetAddress.isSiteLocalAddress()) {
                        ipv4 = inetAddress.getHostAddress();
                    }
                }
            }
            LOGGER.info("For Command Execution Service: " + ipv4 + ":" + executeCmdSocket.getLocalPort());
            LOGGER.info("For Command Suggestion Service: " + ipv4 + ":" + suggestCmdSocket.getLocalPort());
            LOGGER.info("For Datapack Management Service: " + ipv4 + ":" + manageDatapackSocket.getLocalPort());
        } catch (Exception e) {
            LOGGER.error("Failed to create server socket: " + e.getMessage());
        }
        EventHandler.register();
        LOGGER.info("Command Debug Service initialized");
    }
}
