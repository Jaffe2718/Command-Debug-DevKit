package me.jaffe2718.devkit.action.help.ui.content;

import com.intellij.openapi.util.IconLoader;

import javax.swing.*;
import java.io.IOException;

public class InfoDependenciesUI {
    public JPanel rootPanel;
    private JButton buttonModrinth;
    private JButton buttonGitHub;

    public InfoDependenciesUI () {
        this.buttonModrinth.setIcon(IconLoader.getIcon("/graph/modrinth.svg", InfoDependenciesUI.class));
        this.buttonModrinth.addActionListener(e -> {
            try {
                java.awt.Desktop.getDesktop().browse(java.net.URI.create("https://modrinth.com/mod/command-debug-service"));
            } catch (IOException ie) {
                throw new RuntimeException(ie);
            }
        });
        this.buttonGitHub.setIcon(IconLoader.getIcon("/graph/github.svg", InfoDependenciesUI.class));
        this.buttonGitHub.addActionListener(e -> {
            try {
                java.awt.Desktop.getDesktop().browse(java.net.URI.create("https://github.com/Jaffe2718/Command-Debug-DevKit/releases"));
            } catch (IOException ie) {
                throw new RuntimeException(ie);
            }
        });
    }
}
