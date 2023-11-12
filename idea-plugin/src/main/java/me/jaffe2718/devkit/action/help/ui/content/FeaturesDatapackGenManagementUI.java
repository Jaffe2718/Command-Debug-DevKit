package me.jaffe2718.devkit.action.help.ui.content;

import javax.swing.*;

public class FeaturesDatapackGenManagementUI {
    public JPanel rootPanel;
    private JTextPane textPaneDatapackGen;

    public FeaturesDatapackGenManagementUI() {
        this.textPaneDatapackGen.setContentType("text/html");
        this.textPaneDatapackGen.setText("""
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
            <h1>Prerequisites</h1>
            <ul>
                <li>Project is a Minecraft Datapack Project:
                    <ul>
                        <li>Project has a <code>pack.mcmeta</code> file.</li>
                        <li>Project has a <code>data</code> folder.</li>
                        <li>Project has a <code>pack.png</code> file (optional).</li>
                    </ul>
                </li>
                <li>
                    Minecraft is running with mod <code>Command Debug Service</code> installed,
                    and the world is loaded (for importing and linking).
                </li>
            </ul>
            <h1>Generate Minecraft Datapack</h1>
            <ol>
                <li>Click <code>Tools</code> -> <code>Minecraft Command DevKit</code> -> <code>Generate Minecraft Datapack</code>.</li>
                <li>Or right-click on the project file tree and select <code>Minecraft Command DevKit</code> -> <code>Generate Minecraft Datapack</code>.</li>
                <li>Or press <code>Ctrl + Shift + G</code>.</li>
                <li>The generated datapack will be saved as <code>build/&lt;project-name&gt;.zip</code>.</li>
            </ol>
            <h1>Generate Minecraft Datapack and import to Minecraft</h1>
            <ol>
                <li>Click <code>Tools</code> -> <code>Minecraft Command DevKit</code> -> <code>Generate and Import Datapack to Minecraft</code>.</li>
                <li>Or right-click on the project file tree and select <code>Minecraft Command DevKit</code> -> <code>Generate and Import Datapack to Minecraft</code>.</li>
                <li>Then input the host and port for <code>Datapack Management Service</code> in Minecraft instance.</li>
                <li>The generated datapack will be saved as <code>build/&lt;project-name&gt;.zip</code> and also imported to Minecraft.</li>
            </ol>
            <h1>Generate Minecraft Datapack and link to Minecraft</h1>
            <ol>
                <li>Click <code>Tools</code> -> <code>Minecraft Command DevKit</code> -> <code>Generate and Link Datapack to Minecraft</code>.</li>
                <li>Or right-click on the project file tree and select <code>Minecraft Command DevKit</code> -> <code>Generate and Link Datapack to Minecraft</code>.</li>
                <li>Then input the host and port for <code>Datapack Management Service</code> in Minecraft instance.</li>
                <li>The generated datapack will be saved as <code>build/&lt;project-name&gt;.zip</code> and also linked to Minecraft.</li>
                <li>The linked datapack will be expired after the world is closed.</li>
            </ol>
            <h1>Datapack Management</h1>
            <h2>1. Start datapack management service</h2>
            <ol>
                <li>Click <code>Tools</code> -> <code>Minecraft Command DevKit</code> -> <code>Datapack Management</code>;</li>
                <li>Or right-click on the project file tree and select <code>Minecraft Command DevKit</code> -> <code>Datapack Management</code>;</li>
                <li>Enter the host and port of the `Datapack Management Service` socket server shown in Minecraft game chat.</li>
                <li>Do your operations in the <code>Datapack Management</code> dialog.</li>
            </ol>
            <h2>2. Enable the datapack</h2>
            <ol>
                <li>Right-click on the item inside <code>Datapacks</code> -> <code>Status</code> -> <code>Available</code>.</li>
                <li>Click <code>Enable</code>.</li>
            </ol>
            <h2>3. Disable the datapack</h2>
            <ol>
                <li>Right-click on the item inside <code>Datapacks</code> -> <code>Status</code> -> <code>Enabled</code>.</li>
                <li>Click <code>Disable</code>.</li>
            </ol>
            <h2>4. Import the datapack</h2>
            <ol>
                <li>Right-click <code>Datapacks</code> -> <code>Type</code> -> <code>Common</code>.</li>
                <li>Click <code>Import...</code>.</li>
                <li>Choose the datapack file in the file chooser dialog, then click <code>OK</code>.</li>
            </ol>
            <h2>5. Delete the datapack</h2>
            <ol>
                <li>Right-click on the item inside <code>Datapacks</code> -> <code>Type</code> -> <code>Common</code>.</li>
                <li>Click <code>Delete</code>.</li>
            </ol>
            <h2>6. Link the datapack</h2>
            <ol>
                <li>Right-click on the item inside <code>Datapacks</code> -> <code>Type</code> -> <code>Linked</code>.</li>
                <li>Click <code>Link...</code>.</li>
                <li>Choose the datapack file in the file chooser dialog, then click <code>OK</code>.</li>
            </ol>
            <h2>7. Unlink the datapack</h2>
            <ol>
                <li>Right-click on the item inside <code>Datapacks</code> -> <code>Type</code> -> <code>Linked</code>.</li>
                <li>Click <code>Unlink</code>.</li>
            </ol>
            <h2>8. Apply the manage operations</h2>
            <ol>
                <li>After you have done all the operations you want, click <code>OK</code> in the <code>Datapack Management</code> dialog to apply the operations.</li>
            </ol>
            <h2>Note</h2>
            <p>
                The linked datapacks will be expired after the world is closed, but the common datapacks will not.
            </p>
            </body>
        </html>
        """);
    }
}
