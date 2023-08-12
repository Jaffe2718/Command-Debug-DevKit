package me.jaffe2718.sckinj;

import java.io.IOException;
import java.net.Socket;

/**
 * This is the IDE Debug Tool
 */
public class IdeDebugTool {

    static Socket socket = null;

    /**
     * Main method
     * if no arguments are provided, show usage and exit
     * if one argument is provided, parse it as a "host:port" format, connect to "host:port" and enter interactive mode
     * if two arguments are provided, parse the first as a "host:port" format, connect to "host:port" and read the second as a file name, execute the file and exit
     * @param args command line arguments
     */
    public static void main(String[] args) {
        switch (args.length) {
            case 1 -> connectAndEnterInteractiveMode(args[0]);
            case 2 -> connectAndExecuteFile(args[0], args[1]);
            default -> showUsageAndExit();
        }
    }

    /**
     * Show usage and exit
     */
    private static void showUsageAndExit() {
        System.out.println("Usage: java -jar ide-debug-tool-[version].jar [host:port] [file]");
        System.out.println("    no arguments    show usage and exit");
        System.out.println("    host:port    connect to \"host:port\" and enter interactive mode");
        System.out.println("    host:port file    connect to \"host:port\" and execute the file as Minecraft commands and exit");
        System.exit(0);
    }

    /**
     * Connect to "host:port" and enter interactive mode
     * @param hostPort "host:port" format
     */
    private static void connectAndEnterInteractiveMode(String hostPort) {
        // connect to "host:port"
        try {
            socket = new Socket(hostPort.split(":")[0], Integer.parseInt(hostPort.split(":")[1]));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // start a thread to Listen to the socket and print the messages from the socket
        Thread listener = new Thread(() -> {
            try {
                java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(socket.getInputStream()));
                while (socket.isConnected()) {
                    String msg;
                    while ((msg = reader.readLine()) != null) {
                        System.out.println("[Server] " + msg);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        listener.start();
        // start a thread to read the input from the console and send it to the socket
        Thread sender = new Thread(() -> {
            try {
                java.io.PrintWriter writer = new java.io.PrintWriter(socket.getOutputStream(), true);
                java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
                while (socket.isConnected()) {
                    String msg = reader.readLine();
                    if (msg != null) {
                        writer.println(msg);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        sender.start();
    }

    /**
     * Connect to "host:port" and read the second as a file name, execute the file and exit
     * @param hostPort "host:port" format
     * @param fileName file name
     */
    private static void connectAndExecuteFile(String hostPort, String fileName) {
        // connect to "host:port"
        try {
            socket = new Socket(hostPort.split(":")[0], Integer.parseInt(hostPort.split(":")[1]));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // read the second as a file name and read the file line by line
        String[] lines = new String[0];
        try {
            lines = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(fileName))).split("\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // try to remove the comments which start with "#" and trim the lines, and remove "/" at the beginning of each line
        for (int i = 0; i < lines.length; i++) {
            lines[i] = lines[i].split("#")[0].trim();
            if (lines[i].startsWith("/")) {
                lines[i] = lines[i].substring(1);
            }
        }
        // start a thread to Listen to the socket and print the messages from the socket
        Thread listener = new Thread(() -> {
            try {
                java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(socket.getInputStream()));
                while (socket.isConnected()) {
                    String msg;
                    while ((msg = reader.readLine()) != null) {
                        System.out.println("[Server] " + msg);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        listener.start();
        // send the lines to the socket
        try {
            java.io.PrintWriter writer = new java.io.PrintWriter(socket.getOutputStream(), true);
            for (String line : lines) {
                writer.println(line);
            }
            Thread.sleep(100);
            socket.close();
            System.exit(0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
