package me.jaffe2718.devkit.action;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class HelpAction extends AnAction {

    public HelpAction() {
        super("Help", "Open the help documention website", AllIcons.Actions.Help);
    }

    /**
     * Performs the action logic.
     * <p>
     * It is called on the UI thread with all data in the provided {@link DataContext} instance.
     *
     * @param e
     * @see #beforeActionPerformedUpdate(AnActionEvent)
     */
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        // TODO: Open the help documentation website by default browser `https://github.com/Jaffe2718/Command-Debug-DevKit#command-debug-devkit`
        try {
            java.awt.Desktop.getDesktop().browse(java.net.URI.create("https://github.com/Jaffe2718/Command-Debug-DevKit#command-debug-devkit"));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
