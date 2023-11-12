package me.jaffe2718.devkit.action.datapack.ui;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.jaffe2718.devkit.action.datapack.ui.node.DirTreeNode;
import me.jaffe2718.devkit.action.datapack.ui.node.ItemTreeNode;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.List;

public class DatapackManagementUI {

    private final String[] hostPortSplit;

    // UI components
    private JPanel rootPanel;

    /**
     * Datapacks<br>
     *   |- Status<br>
     *   |   |- Enabled<br>
     *   |   |- Available<br>
     *   |<br>
     *   |- Type<br>
     *   |   |- Common<br>
     *   |   |- Linked<br>
     * */
    private JTree datapackTree;

    /**
     * {"all": [...], "enabled": [...], "linked": [...]}
     * disabled = all - enabled
     * common = enabled - linked
     * */
    public DatapackManagementUI(String hostPort) throws IOException {
        this.hostPortSplit = hostPort.split(":");
        Socket targetSocket = new Socket(this.hostPortSplit[0], Integer.parseInt(this.hostPortSplit[1]));
        BufferedReader targetReader = new BufferedReader(new InputStreamReader(targetSocket.getInputStream()));
        PrintWriter targetWriter = new PrintWriter(targetSocket.getOutputStream(), true);
        // read datapack
        targetWriter.println("{\"flag\": \"query\"}");
        JsonObject jsonObject = new Gson().fromJson(targetReader.readLine(), JsonObject.class);
        JsonArray all = jsonObject.get("all").getAsJsonArray();
        JsonArray enabled = jsonObject.get("enabled").getAsJsonArray();
        JsonArray linked = jsonObject.get("linked").getAsJsonArray();
        // build tree
        DirTreeNode rootNode = new DirTreeNode("Datapacks");
        DirTreeNode statusNode = new DirTreeNode("Status");
        DirTreeNode availableNode = new DirTreeNode("Available");
        DirTreeNode enabledNode = new DirTreeNode("Enabled");
        DirTreeNode typeNode = new DirTreeNode("Type");
        DirTreeNode commonNode = new DirTreeNode("Common");
        DirTreeNode linkedNode = new DirTreeNode("Linked");
        rootNode.add(statusNode);
        rootNode.add(typeNode);
        statusNode.add(enabledNode);
        statusNode.add(availableNode);
        typeNode.add(commonNode);
        typeNode.add(linkedNode);
        for (int i = 0; i < all.size(); i++) {
            JsonElement datapack = all.get(i);
            String datapackName = datapack.getAsString().contains("/") ? '"' + datapack.getAsString() + '"' : datapack.getAsString();
            if (enabled.contains(datapack)) {
                enabledNode.add(new ItemTreeNode(datapackName));
            } else {
                availableNode.add(new ItemTreeNode(datapackName));
            }
            if (linked.contains(datapack)) {
                linkedNode.add(new ItemTreeNode(datapackName));
            } else {
                commonNode.add(new ItemTreeNode(datapackName));
            }
        }
        this.datapackTree.setModel(new DefaultTreeModel(rootNode));
        this.datapackTree.addMouseListener(new DatapackTreeMouseAdapter());
    }

    public JComponent getPanel() {
        return rootPanel;
    }

    public void onOK() {
        List<DatapackTreeMouseAdapter.DatapackOperation> dpWorkflow = ((DatapackTreeMouseAdapter) this.
                datapackTree.getMouseListeners()[0]).getDatapackOperationWorkflow();
        // use this.targetWriter to send the tasks in dpWorkflow to the target socket
        new Thread(() -> {
            for (var task : dpWorkflow) {
                this.executeTask(task.operation(), task.parameter());
            }
        }).start();
    }

    private void executeTask(@NotNull String flag, String param) {
        try {
            StringBuilder json = new StringBuilder("{\"flag\": \"" + flag +"\", ");
            if (flag.equals("link") || flag.equals("import")) {
                Path datapack = Path.of(param);
                String name = datapack.getFileName().toString();
                json.append("\"name\": \"").append(name).append("\", ");
                String data = Base64.getEncoder().encodeToString(Files.readAllBytes(datapack));
                json.append("\"data\": \"").append(data).append("\"}");
            } else {  // flag.equals("enable") || flag.equals("disable") || flag.equals("unlink") || flag.equals("delete")
                json.append("\"name\": \"")
                        .append(param.contains("/") ? param.split("/")[1].replace("\"", ""): param)
                        .append("\"}");
            }
            Socket targetSocket = new Socket(this.hostPortSplit[0], Integer.parseInt(this.hostPortSplit[1]));
            PrintWriter targetWriter = new PrintWriter(targetSocket.getOutputStream(), true);
            targetWriter.println(json);
            targetWriter.close();
            targetSocket.close();
            Thread.sleep(30);
        } catch (IOException | InterruptedException ignored) {
            System.err.println("Failed to execute task: " + flag + " " + param);
        }

    }
}
