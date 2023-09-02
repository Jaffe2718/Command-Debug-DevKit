package me.jaffe2718.devkit.action;

import com.intellij.execution.RunContentExecutor;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessHandlerFactory;
import com.intellij.execution.process.ProcessTerminatedListener;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import me.jaffe2718.devkit.editor.McFunctionFileEditor;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Path;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import static me.jaffe2718.devkit.McFunctionIcons.TOOL_NAME;

public class ExecuteAction extends AnAction {

    private final McFunctionFileEditor targetEditor;

    public ExecuteAction(McFunctionFileEditor target) {
        super("Execute",
                "Execute the commands in this file.",
                AllIcons.Actions.Execute);
        this.targetEditor = target;
    }
    /**
     * Performs the action logic.
     * <p>
     * It is called on the UI thread with all data in the provided {@link DataContext} instance.
     *
     * @param e the event which occurred (passed to {@link AnAction#update(AnActionEvent)}).
     * @see #beforeActionPerformedUpdate(AnActionEvent)
     */
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        this.executeInIDE(this.extractTool());
    }

    /** try to run the tool jar in the IDE
     * @param toolJar the tool jar file
     * */
    private void executeInIDE(File toolJar) {
        try {
            AtomicReference<Socket> execSocket = new AtomicReference<>(targetEditor.getEditor().getUserData(ConnectExecutionAction.k_executionSocket));
            Project project = targetEditor.getEditor().getProject();
            String mcfunc = targetEditor.getFile().getPath();
            assert execSocket.get() != null && execSocket.get().isConnected() && project != null;
            String hostPort = execSocket.get().getInetAddress().getHostAddress() + ":" + execSocket.get().getPort();
            GeneralCommandLine commandLine = new GeneralCommandLine("java", "-jar", toolJar.getAbsolutePath(), hostPort, mcfunc);
            OSProcessHandler processHandler = ProcessHandlerFactory.getInstance().createColoredProcessHandler(commandLine);
            ProcessTerminatedListener.attach(processHandler);
            // add the process handler to the task manager
            new RunContentExecutor(project, processHandler)
                    .withTitle(mcfunc)
                    .withActivateToolWindow(true)
                    .run();
        } catch (AssertionError ignore) {
            Messages.showWarningDialog("Please connect to the Minecraft execution service socket first.", "Not Connected");
        } catch (Exception ignored) {}
    }

    private File extractTool() {
        File libs = Path.of(System.getenv("HOMEPATH"), ".mcfuncdev").toFile();
        if (libs.exists() && libs.isDirectory()) {
            // check if ide-debug-tool-<version>.jar is present
            for (File jarfile : Objects.requireNonNull(libs.listFiles())) {
                if (jarfile.getName().startsWith("ide-debug-tool-") && jarfile.getName().endsWith(".jar")) {
                    return jarfile;
                }
            }
            // if not, download it from github
        } else {
            // if not, create it
            assert libs.mkdir();
        }
        // if not exist, extract it from resource to %HOMEPATH%\.mcfuncdev\
        try (OutputStream outputStream = new FileOutputStream(
                Path.of(System.getenv("HOMEPATH"),
                        ".mcfuncdev",
                        TOOL_NAME).toFile())) {
            InputStream inputStream = ExecuteAction.class.getResourceAsStream("/tool/" + TOOL_NAME);
            assert inputStream != null;
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            inputStream.close();
            outputStream.flush();
        } catch (Exception ignored) {}
        return Path.of(System.getenv("HOMEPATH"),
                ".mcfuncdev",
                TOOL_NAME).toFile();
    }
}
