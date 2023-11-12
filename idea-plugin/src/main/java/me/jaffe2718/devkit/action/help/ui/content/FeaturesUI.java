package me.jaffe2718.devkit.action.help.ui.content;

import javax.swing.*;

public class FeaturesUI {
    public JPanel rootPanel;
    private JTextPane featuresContentTextPane;

    public FeaturesUI() {
        featuresContentTextPane.setContentType("text/html");
        featuresContentTextPane.setText("""
        <html>
        <head>
        <style>
        body {
            font-family: sans-serif;
        }
        </style>
        </head>
        <body>
        <h2>Project Setup & Configuration</h2>
        <ul>
            <li>Create New Datapack Project</li>
            <li>Import Project from Existing Datapack</li>
        </ul>
        <h2>Minecraft Function Editing</h2>
        <ul>
            <li>Create New Minecraft Function File</li>
            <li>Code Completion</li>
            <li>Code Execution</li>
        </ul>
        <h2>Datapack Generation & Management</h2>
        <ul>
            <li>Generate Datapack</li>
            <li>Generate & Import Datapack to Minecraft</li>
            <li>Generate & Link Datapack to Minecraft</li>
            <li>Datapack Management</li>
        </ul>
        <h2>Miscellaneous</h2>
        <ul>
            <li>Help</li>
            <li>Minecraft Command Console</li>
            <li>Code Highlighting Configuration</li>
        </ul>
        </body>
        </html> 
        """);
    }
}
