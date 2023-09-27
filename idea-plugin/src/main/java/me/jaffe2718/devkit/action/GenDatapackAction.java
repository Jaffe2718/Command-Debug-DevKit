package me.jaffe2718.devkit.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.MessageConstants;
import com.intellij.openapi.ui.Messages;
import me.jaffe2718.devkit.prj.unit.DatapackGenerator;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

import static me.jaffe2718.devkit.McFunctionStaticRes.PLUGIN_ICON;

public class GenDatapackAction extends AnAction {

    public GenDatapackAction() {
        super("Generate Minecraft Datapack",
                "Generate a Minecraft datapack from current project.",
                PLUGIN_ICON);
    }
    /**
     * Performs the action logic.
     * <p>
     * It is called on the UI thread with all data in the provided {@link com.intellij.openapi.actionSystem.DataContext} instance.
     *
     * @param e the event which occurred (passed to {@link #update(AnActionEvent)}).
     * @see #beforeActionPerformedUpdate(AnActionEvent)
     */
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        if(Messages.showOkCancelDialog(e.getProject(), "Generate a Minecraft datapack from current project?",
                "Generate Minecraft Datapack", "Generate", "Cancel", PLUGIN_ICON) != Messages.OK) {
            return;
        }
        DatapackGenerator generator = new DatapackGenerator(e.getProject());
        switch (generator.generate()) {
            case VALID->
                    Messages.showInfoMessage("Datapack generated successfully as:\n" +
                            Path.of(e.getProject().getBasePath(),
                                    "build", e.getProject().getName() + ".zip"), "Success");
            case VALID_WITH_WARNINGS ->
                Messages.showWarningDialog("Datapack generated with warnings:\n" +
                        "The icon `pack.png` is missing.", "Warning");
            case INVALID ->
                Messages.showErrorDialog("Failed to generate datapack:\n" +
                        "Please ensure that the project contains `pack.mcmeta` file and `data` directory.", "Error");
        }
    }
}
