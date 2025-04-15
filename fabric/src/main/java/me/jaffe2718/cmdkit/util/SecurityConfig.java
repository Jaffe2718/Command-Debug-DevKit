package me.jaffe2718.cmdkit.util;

import com.google.common.collect.Lists;
import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;

import java.util.List;

/**
 * The security config for the command debug service.
 */
public class SecurityConfig extends MidnightConfig {



    /**
     * The port for the command debug service. The default value is 0, which means the port will be randomly assigned.
     * If you modify this value, it will be applied when the program restarts.
     * @see me.jaffe2718.cmdkit.CommandDebugDevKit#executeCmdSocket
     * @see me.jaffe2718.cmdkit.CommandDebugDevKit#onInitialize()
     */
    @SuppressWarnings("unused")
    @Entry(min = 0, max = 65535)
    public static int executeCmdSocketPort = 0;

    /**
     * The port for the command suggestion service. The default value is 0, which means the port will be randomly assigned.
     * If you modify this value, it will be applied when the program restarts.
     * @see me.jaffe2718.cmdkit.CommandDebugDevKit#suggestCmdSocket
     * @see me.jaffe2718.cmdkit.CommandDebugDevKit#onInitialize()
     */
    @SuppressWarnings("unused")
    @Entry(min = 0, max = 65535)
    public static int suggestCmdSocketPort = 0;

    /**
     * The port for the datapack management service. The default value is 0, which means the port will be randomly assigned.
     * If you modify this value, it will be applied when the program restarts.
     * @see me.jaffe2718.cmdkit.CommandDebugDevKit#manageDatapackSocket
     * @see me.jaffe2718.cmdkit.CommandDebugDevKit#onInitialize()
     */
    @SuppressWarnings("unused")
    @Entry(min = 0, max = 65535)
    public static int manageDatapackSocketPort = 0;

    /**
     * Whether to show the socket info in the command debug service.
     * @see me.jaffe2718.cmdkit.event.EventHandler#showSocketInfo(ServerPlayNetworkHandler, PacketSender, MinecraftServer)
     */
    @SuppressWarnings("unused")
    @Entry
    public static boolean shouldShowSocketInfo = true;

    /**
     * The trust mode for the command debug service.
     * @see TrustMode
     */
    @SuppressWarnings("unused")
    @Entry
    public static TrustMode trustMode = TrustMode.ALL_ALLOWED;

    /**
     * The white list for the command debug service.
     * @see TrustMode#WHITE_LIST
     * @see SecurityConfig#trustMode
     */
    @SuppressWarnings("unused")
    @Entry
    @Condition(requiredOption = "trustMode", requiredValue = "WHITE_LIST")
    public static List<String> trustedIPv4Addresses = Lists.newArrayList();
}
