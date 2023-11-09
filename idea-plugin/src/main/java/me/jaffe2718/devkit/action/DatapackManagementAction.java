package me.jaffe2718.devkit.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.ui.DialogBuilder;
import com.intellij.openapi.ui.Messages;
import me.jaffe2718.devkit.action.ui.DatapackManagementUI;
import org.jetbrains.annotations.NotNull;

import static me.jaffe2718.devkit.McFunctionStaticRes.PLUGIN_ICON;

public class DatapackManagementAction extends AnAction {

    public DatapackManagementAction() {
        super("Datapack Management",
                "Manage the datapacks of the current world of Minecraft instance.",
                PLUGIN_ICON);
    }

    /**
     * Performs the action logic.
     * <p>
     * It is called on the UI thread with all data in the provided {@link DataContext} instance.
     *
     * @param e Carries information on the invocation place
     * @see #beforeActionPerformedUpdate(AnActionEvent)
     */
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        String hostPort = Messages.showInputDialog(
                "Please input the host and port for `Datapack Management Service` in Minecraft instance.",
                "Datapack Management", PLUGIN_ICON, "localhost:", null);
        try {
            DialogBuilder builder = new DialogBuilder();
            builder.setTitle("Datapack Management");
            assert hostPort != null;
            DatapackManagementUI ui = new DatapackManagementUI(hostPort);
            builder.setCenterPanel(ui.getPanel());
            builder.setOkOperation(() -> {
                ui.onOK();
                builder.getDialogWrapper().close(0); // close the dialog
            });
            builder.show();
        } catch (AssertionError ignored) { // user canceled the input dialog
        } catch (Exception ex) {
            Messages.showErrorDialog(ex.getMessage(), "Error");
        }
    }
}
