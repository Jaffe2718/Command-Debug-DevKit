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
import me.jaffe2718.devkit.lang.unit.McFunctionScriptFactory;
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

    @Contract(" -> new")
    private @NotNull Thread createExecutionThread() {
        return new Thread(() -> {
            try {
                Editor editor = this.targetEditor.getEditor();
                PrintWriter pw = editor.getUserData(ConnectExecutionAction.k_executionPrintWriter);
                String rawText = editor.getDocument().getText();
                McFunctionScriptFactory scriptFactory = new McFunctionScriptFactory(rawText);
                if (scriptFactory.hasMacro()) {
                    ApplicationManager.getApplication().invokeLater(() ->
                            Messages.showWarningDialog("Macros cannot be executed directly in current IDE,\n" +
                                            "macro lines will be ignored!\n" +
                                            "Please pack the file into a datapack and execute\n it in Minecraft if you want to use macros.",
                                    "Macro Detected")
                    );
                }
                List<String> commands = scriptFactory.getCommands();
                // List<String> lines = editor.getDocument().getText().lines().toList().stream().map(this::removeComments).toList();
                for (String command: commands) {
                    if (command.isBlank() || command.startsWith("$")) continue;
                    Objects.requireNonNull(pw).println(command);
                    Thread.sleep(10);
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
