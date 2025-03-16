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
     * The trust mode for the command debug service.
     * <ul>
     *     <li>ALL_ALLOWED: All clients are allowed to connect.</li>
     *     <li>WHITE_LIST: Only clients in the white list are allowed to connect.</li>
     * </ul>
     * @see SecurityConfig#trustedIPv4Addresses
     */
    public enum TrustMode {
        ALL_ALLOWED,
        WHITE_LIST,
    }

    /**
     * Whether to show the socket info in the command debug service.
     * @see me.jaffe2718.cmdkit.event.EventHandler#showSocketInfo(ServerPlayNetworkHandler, PacketSender, MinecraftServer)
     */
    @Entry
    public static boolean shouldShowSocketInfo = true;

    /**
     * The trust mode for the command debug service.
     * @see TrustMode
     */
    @Entry
    public static TrustMode trustMode = TrustMode.WHITE_LIST;

    /**
     * The white list for the command debug service.
     * @see TrustMode#WHITE_LIST
     * @see SecurityConfig#trustMode
     */
    @Entry
    public static List<String> trustedIPv4Addresses = Lists.newArrayList();
}
