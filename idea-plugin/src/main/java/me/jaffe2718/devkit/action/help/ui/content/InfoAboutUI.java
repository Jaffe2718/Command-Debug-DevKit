package me.jaffe2718.devkit.action.help.ui.content;

import com.intellij.openapi.util.IconLoader;

import javax.swing.*;
import java.io.IOException;

public class InfoAboutUI {
    public JPanel rootPanel;
    private JButton buttonRepo;
    private JButton reportIssuesButton;
    private JTextPane commandDebugDevKitTextPane;
    private JLabel iconLabel;

    public InfoAboutUI() {
        buttonRepo.addActionListener(e -> {
            try {
                java.awt.Desktop.getDesktop().browse(java.net.URI.create("https://github.com/Jaffe2718/Command-Debug-DevKit"));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        reportIssuesButton.addActionListener(e -> {
            try {
                java.awt.Desktop.getDesktop().browse(java.net.URI.create("https://github.com/Jaffe2718/Command-Debug-DevKit/issues"));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        iconLabel.setIcon(IconLoader.getIcon("/graph/cmdkit48.svg", InfoDependenciesUI.class));
    }
}
