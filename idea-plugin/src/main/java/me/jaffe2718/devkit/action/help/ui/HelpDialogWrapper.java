package me.jaffe2718.devkit.action.help.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class HelpDialogWrapper extends DialogWrapper {

    public HelpDialogWrapper() {
        super(true);
        this.init();
        this.setTitle("Minecraft Command DevKit: Help");
        this.setOKButtonText("Issue Tracker");
        this.setCancelButtonText("Close");
    }

    /**
     * Factory method. It creates panel with dialog options. Options panel is located at the
     * center of the dialog's content pane. The implementation can return {@code null}
     * value. In this case there will be no options panel.
     */
    @Override
    protected @Nullable JComponent createCenterPanel() {
        return new HelpUI().getRootPanel();
    }

    @Override
    protected @Nullable JComponent createSouthPanel() {
        return null;
    }

}
