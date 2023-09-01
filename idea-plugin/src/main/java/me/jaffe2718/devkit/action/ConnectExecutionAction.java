package me.jaffe2718.devkit.action;

import com.intellij.icons.AllIcons;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.Key;
import me.jaffe2718.devkit.editor.McFunctionFileEditor;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectExecutionAction extends AnAction {

    public static final Key<Socket> k_executionSocket = Key.create("executionSocket");
    public static final Key<PrintWriter> k_executionPrintWriter = Key.create("executionPrintWriter");

    private final McFunctionFileEditor targetEditor;

    public ConnectExecutionAction(McFunctionFileEditor target) {
        super("Connect to Execution Service",
                "Enter the host and port of the command execution service socket to connect to.",
                AllIcons.Actions.MoveToButton);
        this.targetEditor = target;
    }

    /**
     * Performs the action logic.
     * <p>
     * It is called on the UI thread with all data in the provided {@link DataContext} instance.
     *
     * @param e the event which occurred (passed to {@link AnAction#update(AnActionEvent)}).
     * @see #beforeActionPerformedUpdate(AnActionEvent)
     */
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {

        String hostPort = Messages.showInputDialog(
                "Please input the host and port of the Minecraft instance to connect to command execution service.",
                "Connect to Execution Service",
                Messages.getQuestionIcon(), "localhost:", null);
        try {
            Editor editor = this.targetEditor.getEditor();
            assert hostPort != null;
            String[] hostPortSplit = hostPort.split(":");
            Socket oldSocket;
            if ((oldSocket = this.targetEditor.getEditor().getUserData(k_executionSocket)) != null) {  // close the old socket
                try {
                    oldSocket.close();
                } catch (IOException ignored) {
                }
            }
            Socket executionSocket = new Socket(hostPortSplit[0], Integer.parseInt(hostPortSplit[1]));
            PrintWriter executionPrintWriter = new PrintWriter(executionSocket.getOutputStream(), true);
            editor.putUserData(k_executionSocket, executionSocket);
            editor.putUserData(k_executionPrintWriter, executionPrintWriter);
            ApplicationManager.getApplication().invokeLater(() -> {
                Notifications.Bus.notify(
                        new Notification(
                                "me.jaffe2718.devkit.notification",
                                "Minecraft Command DevKit",
                                "Successfully connected to the Minecraft instance command execution service at " + hostPort + ".",
                                NotificationType.INFORMATION
                        )
                );
            });
        } catch (AssertionError ignore) {} catch (Exception ignored) {
            Messages.showErrorDialog("Could not connect to Minecraft instance: invalid host or port.", "Connection Failed");
        }
    }
}
