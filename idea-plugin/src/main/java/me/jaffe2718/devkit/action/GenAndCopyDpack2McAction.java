package me.jaffe2718.devkit.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.ui.Messages;
import me.jaffe2718.devkit.prj.unit.DatapackGenerator;
import org.jetbrains.annotations.NotNull;

import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

import static me.jaffe2718.devkit.McFunctionStaticRes.PLUGIN_ICON;

public class GenAndCopyDpack2McAction extends AnAction {

    public GenAndCopyDpack2McAction() {
        super("Generate and Copy Datapack to Minecraft",
                "Generate a Minecraft datapack from current project and copy it to Minecraft datapack folder.",
                PLUGIN_ICON);
    }

    /**
     * Performs the action logic.
     * <p>
     * It is called on the UI thread with all data in the provided {@link DataContext} instance.
     *
     * @param e Carries information on the invocation place
     * @see #beforeActionPerformedUpdate(AnActionEvent)
     */
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        String hostport = Messages.showInputDialog(
                        "Please input the host and port for `Datapack Receive Service` in Minecraft instance.",
                "Generate & Copy Datapack to Minecraft", PLUGIN_ICON, "localhost:", null);
        if (hostport == null) {
            return;
        }
        String[] hostportSplit = hostport.split(":");
        if (hostportSplit.length != 2) {
            Messages.showErrorDialog("Invalid host and port.", "Error");
            return;
        }
        String host = hostportSplit[0];
        int port;
        try {
            port = Integer.parseInt(hostportSplit[1]);
            if (port < 0 || port > 65535) {
                Messages.showErrorDialog("Invalid host and port.", "Error");
                return;
            }
        } catch (NumberFormatException ex) {
            Messages.showErrorDialog("Invalid host and port.", "Error");
            return;
        }
        // generate datapack
        DatapackGenerator generator = new DatapackGenerator(e.getProject());
        switch (generator.generate()) {
            case VALID->
                    Messages.showInfoMessage("Datapack generated successfully as:\n" +
                            Path.of(e.getProject().getBasePath(),
                                    "build", e.getProject().getName() + ".zip"), "Success");
            case VALID_WITH_WARNINGS ->
                    Messages.showWarningDialog("Datapack generated with warnings:\n" +
                            "The icon `pack.png` is missing.", "Warning");
            case INVALID -> {
                Messages.showErrorDialog("Failed to generate datapack:\n" +
                        "Please ensure that the project contains `pack.mcmeta` file and `data` directory.", "Error");
                return;
            }
        }
        // send datapack
        sendDatapack(host, port, e);
    }

    /**
     * Send datapack to Minecraft instance.
     * datapack is located at `[project root]/build/[project name].zip`.
     * send datapack to Minecraft instance Datapack Receive Service at `host:port`.
     * send in base64 encoding json format:  {"name": "[datapack name].zip", "data": "[base64 encoded datapack]"}
     *
     * @param host Host of the Minecraft instance.
     * @param port Port of the Minecraft instance.
     * @param e action event.
     * */
    private void sendDatapack(String host, int port, @NotNull AnActionEvent e) {
        Path datapackPath = Path.of(e.getProject().getBasePath())
                .resolve("build")
                .resolve(e.getProject().getName() + ".zip");
        String datapackName = datapackPath.getFileName().toString();
        // read datapack file as base64 string
        // send json to Minecraft instance
        try (Socket socket = new Socket(host, port)) {
            // generate json
            String json = "{\"name\": \"" + datapackName + "\", \"data\": \"" +
                    Base64.getEncoder().encodeToString(Files.readAllBytes(datapackPath))
                    + "\"}";
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println(json);
        } catch (Exception ex) {
            Messages.showErrorDialog("Failed to send datapack to Minecraft instance.", "Error");
        } finally {
            Messages.showWarningDialog("Datapack sent to `" + host + ":" + port + "` Successfully!\n" +
                    "Warning: if the player does not enter the world, the datapack will be ignored.",
                    "Success with Warning");
        }
    }

}
