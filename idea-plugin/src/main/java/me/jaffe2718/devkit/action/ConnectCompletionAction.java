package me.jaffe2718.devkit.action;

import com.intellij.icons.ExpUiIcons;
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

import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ConnectCompletionAction extends AnAction {

    public static final Key<List<String>>  k_completionList = Key.create("completionList");
    public static final Key<PrintWriter> k_completionPrintWriter = Key.create("completionPrintWriter");
    public static final Key<Socket> k_completionSocket = Key.create("completionSocket");

    private final McFunctionFileEditor targetEditor;

    public ConnectCompletionAction(McFunctionFileEditor target) {
        super("Connect to Completion Service",
                "Enter the host and port of the code completion service socket to connect to.",
                ExpUiIcons.Nodes.Plugin);
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
        // show a dialog to input the host and port
        String hostPort = Messages.showInputDialog(
                "Please input the host and port of the Minecraft instance to get code completion service.",
                "Connect to Completion Service",
                Messages.getQuestionIcon(), "localhost:", null);
        try {
            // split the host and port
            Editor editor = this.targetEditor.getEditor();
            assert hostPort != null;
            String[] hostPortSplit = hostPort.split(":");
            Socket completionSocket = new Socket(hostPortSplit[0], Integer.parseInt(hostPortSplit[1]));
            editor.putUserData(k_completionSocket, completionSocket);
            editor.putUserData(
                    k_completionPrintWriter,
                    new PrintWriter(completionSocket.getOutputStream(), true)
            );
            editor.putUserData(k_completionList, new ArrayList<>());
            this.targetEditor.buildCompletionListenerThread().start();
            ApplicationManager.getApplication().invokeLater(() -> {
                Notifications.Bus.notify(
                        new Notification(
                                "me.jaffe2718.devkit.notification",
                                "Minecraft Command DevKit",
                                "Successfully connected to the Minecraft instance code completion service at " + hostPort + ".",
                                NotificationType.INFORMATION
                        )
                );
            });
        } catch (AssertionError ignored) {} catch (Exception ignored) {
            Messages.showErrorDialog("Failed to connect to the Minecraft instance.", "Connection Failed");
        }
    }
}
