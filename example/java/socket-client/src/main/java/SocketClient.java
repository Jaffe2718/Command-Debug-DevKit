import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class SocketClient extends Frame {

    // input host:port
    TextField hostInput = new TextField(30);
    // connect button
    Button connectBtn = new Button("Connect");
    // msg view
    TextArea msgView = new TextArea(11, 36);
    // msg input
    TextField msgInput = new TextField(30);
    // send button
    Button sendBtn = new Button("Send");

    Socket socket = null;

    public SocketClient() {

        super("Socket Client");
        setLayout(new FlowLayout());

        connectBtn.addActionListener(e -> {
            // connect to socket server
            String host = hostInput.getText();
            if (host.isEmpty()) {
                msgView.append("[Client] Host is empty!\n");
            }
            String[] hostSplit = host.split(":");
            if (hostSplit.length != 2) {
                msgView.append("[Client] Host is invalid!\n");
            }
            String hostName = hostSplit[0];
            int port = Integer.parseInt(hostSplit[1]);
            try {
                this.socket = new Socket(hostName, port);
                msgView.append("[Client] connected to " + host + "\n");
                BufferedReader reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                Thread recvThread = new Thread(() -> {
                    while (true) {
                        try {
                            String msg;
                            while((msg = reader.readLine()) != null) {
                                msgView.append("[Server] " + msg + "\n");
                            }
                        } catch (IOException ex) {
                            msgView.append("[Client] connection closed! \n");
                        }
                    }
                });
                recvThread.start();
            } catch(IOException ex) {
                msgView.append("[Client] connection failed! \n");
            }
        });
        sendBtn.addActionListener(e -> {
            // send msg to socket server
            if (this.socket == null) {
                msgView.append("[Client] not connected!\n");
            }
            try {
                String msg = msgInput.getText();
                if(!msg.isEmpty()) {
                    PrintWriter writer = new PrintWriter(this.socket.getOutputStream(), true);
                    writer.println(msg);
                    msgView.append("[User] " + msg + "\n");
                }
            } catch(IOException ex) {
                msgView.append("[Client] send error! \n");
            }
        });
        msgView.setEditable(false);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    socket.close();
                } catch (Exception ignored) {
                }
                System.exit(0);
            }
        });
        add(hostInput, BorderLayout.NORTH);
        add(connectBtn, BorderLayout.NORTH);
        add(msgView, BorderLayout.CENTER);
        add(msgInput, BorderLayout.SOUTH);
        add(sendBtn, BorderLayout.SOUTH);
        setSize(425,300);
        setVisible(true);
    }

    public static void main(String[] args) {
        new SocketClient();
    }
}