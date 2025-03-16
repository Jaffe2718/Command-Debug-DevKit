package me.jaffe2718.cmdkit.util;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

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
    * @see SecurityConfig#trustMode
    * @see SecurityConfig#trustedIPv4Addresses
     */
    public static Socket acceptClientSocket(@NotNull ServerSocket serverSocket) throws SecurityException, IOException {
        Socket clientSocket = serverSocket.accept();
        if (SecurityConfig.trustMode == SecurityConfig.TrustMode.WHITE_LIST
            && !SecurityConfig.trustedIPv4Addresses.contains(clientSocket.getInetAddress().getHostAddress())) {
            new PrintWriter(clientSocket.getOutputStream(), true).println("Connection denied: You are not allowed connect to this server.");
            clientSocket.shutdownInput();
            clientSocket.shutdownOutput();
            clientSocket.close();
            throw new SecurityException("Client socket accepted on " + clientSocket.getInetAddress().getHostAddress() + ":" + clientSocket.getPort() + " is not trusted.");
        }
        return clientSocket;
    }
}
