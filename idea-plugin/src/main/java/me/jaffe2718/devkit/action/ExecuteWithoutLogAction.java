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
import me.jaffe2718.devkit.editor.McFunctionFileEditor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class ExecuteWithoutLogAction extends AnAction {

    private final McFunctionFileEditor targetEditor;

    public ExecuteWithoutLogAction(McFunctionFileEditor target) {
        super("Execute Without Log",
                "Send the commands in current file to the Minecraft instance to execute without logging.",
                ExpUiIcons.Toolwindow.Run);
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
        try {
            AtomicReference<PrintWriter> pw = new AtomicReference<>(this.targetEditor.getEditor().getUserData(ConnectExecutionAction.k_executionPrintWriter));
            assert pw.get() != null && !pw.get().checkError();
        } catch (AssertionError ignored) {
            Messages.showWarningDialog("Please connect to the Minecraft execution service socket first.", "Not Connected");
            return;
        }
        createExecutionThread().start();
    }

    private String removeComments(String text) {
        // remove the comments #... and the spaces before it
        // but strings are not comments like "...#..."
        boolean inString = false;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (inString) {
                if (c == '"') {             // end of string
                    inString = false;
                }
                result.append(c);
            }
            else {          // not in string
                if (c == '"') {             // start of string
                    inString = true;
                } else if (c == '#') {      // start of comment
                    break;
                }
                result.append(c);
            }
        }
        return result.toString().trim();
    }

    @Contract(" -> new")
    private @NotNull Thread createExecutionThread() {
        return new Thread(() -> {
            try {
                Editor editor = this.targetEditor.getEditor();
                PrintWriter pw = editor.getUserData(ConnectExecutionAction.k_executionPrintWriter);
                List<String> lines = editor.getDocument().getText().lines().toList().stream().map(this::removeComments).toList();
                for (String line: lines) {
                    if (line.isEmpty()) continue;
                    Objects.requireNonNull(pw).println(line);
                }
                ApplicationManager.getApplication().invokeLater(() ->
                        Notifications.Bus.notify(
                                new Notification("me.jaffe2718.devkit.notification",
                                        "Minecraft Command DevKit",
                                        "Commands sent to Minecraft successfully!",
                                        NotificationType.INFORMATION)
                        )
                );
                //ApplicationManager.getApplication().invokeLater(() -> Messages.showInfoMessage("Commands sent to Minecraft.", "Success"));
            } catch (Exception ignored) {}
        });
    }
}
