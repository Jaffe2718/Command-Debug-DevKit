package me.jaffe2718.devkit.action.help.ui.content;

import javax.swing.*;

public class FeaturesMiscellaneousUI {
    public JPanel rootPanel;
    private JTextPane textPaneContent;

    public FeaturesMiscellaneousUI() {
        textPaneContent.setContentType("text/html");
        /*
        1. Help
            - `Tools` -> `Minecraft Command DevKit` -> `Help`
        2. Minecraft Command Console
            - `Tools` -> `Minecraft Command DevKit` -> `Minecraft Command Console`
            - host + port
        3. Code Highlighting Configuration
            - `File` -> `Settings`
            - `Editor` -> `Color Scheme` -> `Minecraft Function`
         */
        textPaneContent.setText("""
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
            <h1>Help</h1>
            <ol>
                <li>Click <code>Tools</code> -> <code>Minecraft Command DevKit</code> -> <code>Help</code> to open the help page.</li>
            </ol>
            <h1>Minecraft Command Console</h1>
            <ol>
                <li>Click <code>Tools</code> -> <code>Minecraft Command DevKit</code> -> <code>Minecraft Command Console</code>;</li>
                <li>Then input the host and port for <code>Command Execution Service</code> shown in Minecraft instance game chat;</li>
                <li>Click <code>OK</code> to connect and open the console;</li>
                <li>Enter the command in the console and press <code>Enter</code> to execute it in Minecraft.</li>
            </ol>
            <h1>Code Highlighting Configuration</h1>
            <ol>
                <li>Click <code>File</code> -> <code>Settings</code> to open the settings window;</li>
                <li>Find <code>Editor</code> -> <code>Color Scheme</code> -> <code>Minecraft Function</code>;</li>
                <li>Then you can configure the color scheme for Minecraft function files.</li>
            </ol>
            </body>
        """);
    }
}
