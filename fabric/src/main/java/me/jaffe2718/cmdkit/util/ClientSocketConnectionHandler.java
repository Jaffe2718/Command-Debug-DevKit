package me.jaffe2718.cmdkit.util;

import com.google.common.collect.Lists;
import me.jaffe2718.cmdkit.CommandDebugDevKit;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

/**
 * A utility class for handling client socket connections.
 */
public abstract class ClientSocketConnectionHandler {

    /**
     * Accepts a client socket from the server socket.
     * @param serverSocket The server socket.
     * @return The client socket.
     * @throws SecurityException If the client socket is not trusted.
     * @throws IOException If an I/O error occurs.
     * @see TrustMode
     * @see CommandDebugDevKit#getSecurityConfig(String)
     */
    public static Socket acceptClientSocket(@NotNull ServerSocket serverSocket) throws SecurityException, IOException{
        Socket clientSocket = serverSocket.accept();
        TrustMode trustMode = TrustMode.ALL_ALLOWED;
        List<String> trustedIPv4Addresses = Lists.newArrayList();
        try {
            trustMode = CommandDebugDevKit.getSecurityConfig("trustMode");
            trustedIPv4Addresses = CommandDebugDevKit.getSecurityConfig("trustedIPv4Addresses");
        } catch (Exception ignored) {}
        if (trustMode == TrustMode.WHITE_LIST
            && !trustedIPv4Addresses.contains(clientSocket.getInetAddress().getHostAddress())) {
            new PrintWriter(clientSocket.getOutputStream(), true).println("Connection denied: You are not allowed connect to this server.");
            clientSocket.shutdownInput();
            clientSocket.shutdownOutput();
            clientSocket.close();
            throw new SecurityException("Client socket accepted on " + clientSocket.getInetAddress().getHostAddress() + ":" + clientSocket.getPort() + " is not trusted.");
        }
        return clientSocket;
    }
}
