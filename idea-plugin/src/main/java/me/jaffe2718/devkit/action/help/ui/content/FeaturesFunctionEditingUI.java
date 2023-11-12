package me.jaffe2718.devkit.action.help.ui.content;

import javax.swing.*;

public class FeaturesFunctionEditingUI {
    public JPanel rootPanel;
    private JTextPane textPaneContent;

    public FeaturesFunctionEditingUI() {
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
                <p>1. Creeate new Minecraft Function file (<code>*.mcfunction</code>):</p>
                <ul>
                    <li>Right-click the folder in the Project view where you want to create the <code>*.mcfunction</code> file;</li>
                    <li>Select <code>New</code> -&gt; <code>Minecraft Function File</code>, and enter the name without extension for the file;</li>
                    <li>Press <code>Enter</code> to create the file.</li>
                </ul>
                <p>2. Edit the <code>*.mcfunction</code> file with code completion support:</p>
                <ul>
                    <li>Open or create a <code>*.mcfunction</code> file which you want to edit;</li>
                    <li>Click the <code>Connect to Completion Service</code> button in the toolbar of the editor;</li>
                    <li>Enter the host and port of code completion service shown in the Minecraft game chat;</li>
                    <li>Edit the <code>*.mcfunction</code> file with code completion support.</li>
                </ul>
                <p>3. Execute the <code>*.mcfunction</code> file in Minecraft:</p>
                <ul>
                    <li>Open or create a <code>*.mcfunction</code> file which you want to execute;</li>
                    <li>Click the <code>Execute Function</code> button in the toolbar of the editor;</li>
                    <li>Enter the host and port of code execution service shown in the Minecraft game chat;</li>
                    <li>Click the <code>Execute</code> button in the toolbar of the editor to execute the <code>*.mcfunction</code> file in Minecraft.</li>
                    <li>Or click <code>Execute Without Log</code> button to execute the <code>*.mcfunction</code> file in Minecraft without log.</li>
                </ul>
            </body>
        </html>
        """);
    }
}
