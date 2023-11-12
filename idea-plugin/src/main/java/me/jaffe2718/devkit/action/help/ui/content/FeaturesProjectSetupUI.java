package me.jaffe2718.devkit.action.help.ui.content;

import javax.swing.*;

public class FeaturesProjectSetupUI {
    public JPanel rootPanel;
    private JTextPane textPaneContent;

    public FeaturesProjectSetupUI() {
        this.textPaneContent.setContentType("text/html");
        this.textPaneContent.setText("""
        <html>
            <head>
                <style>
                    body {
                        font-family: sans-serif;
                    }
                    code {
                        font-family: monospace;
                        background-color: rgba(127, 127, 127, 0.2);
                        border: 1px solid #ddd;
                        border-radius: 0.2em;
                        padding: 0 0.2em;
                    }
                </style>
            </head>
            <body>
            <h1>Create New Datapack Project</h1>
            <ol>
                <li>At the welcome screen, click <code>New Project</code> -> <code>Minecraft Datapack</code>.</li>
                <li>Fill the <code>Location</code>, <code>Datapack Name</code>, <code>Namespace</code>, <code>Pack Format</code> and <code>Description</code>(optional) fields.</li>
                <li>Click <code>Create</code>.</li>
            </ol>
            <h1>Import Project from Existing Datapack</h1>
            <ol>
                <li>Enter the <code>Datapack Location</code>, <code>Extract Directory</code> and <code>Project Name</code>.</li>
                <li>Click <code>Create</code>.</li>
            </ol>
            </body>
        </html>
        """);
    }
}
