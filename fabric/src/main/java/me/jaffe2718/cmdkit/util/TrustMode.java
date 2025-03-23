package me.jaffe2718.cmdkit.util;

/**
 * The trust mode for the command debug service.
 * <ul>
 *     <li>ALL_ALLOWED: All clients are allowed to connect.</li>
 *     <li>WHITE_LIST: Only clients in the white list are allowed to connect.</li>
 * </ul>
 */
public enum TrustMode {
    ALL_ALLOWED,
    WHITE_LIST,
}