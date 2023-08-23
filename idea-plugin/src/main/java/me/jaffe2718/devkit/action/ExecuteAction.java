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
        // check if %HOMEPATH%\.mcfuncdev\ is present
        File libs = Path.of(System.getenv("HOMEPATH"), ".mcfuncdev").toFile();
        if (libs.exists() && libs.isDirectory()) {
            // check if ide-debug-tool-<version>.jar is present
            for (File jarfile : Objects.requireNonNull(libs.listFiles())) {
                if (jarfile.getName().startsWith("ide-debug-tool-") && jarfile.getName().endsWith(".jar")) {
                    // TODO: if so, execute it and return
                    executeInIDE(jarfile);
                    return;  // exit the method
                }
            }
            // if not, download it from github
        } else {
            // if not, create it
            File file = Path.of(System.getenv("HOMEPATH"), ".mcfuncdev").toFile();
            assert file.mkdir();
        }
        // if not exist, extract it resource/tool/<all files> to %HOMEPATH%\.mcfuncdev\
        try (OutputStream outputStream = new FileOutputStream(
                Path.of(System.getenv("HOMEPATH"),
                        ".mcfuncdev",
                        "ide-debug-tool-1.1.0.jar").toFile())) {
            InputStream inputStream = ExecuteAction.class.getResourceAsStream("/tool/ide-debug-tool-1.1.0.jar");
            assert inputStream != null;
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            inputStream.close();
            outputStream.flush();
        } catch (Exception ignored) {}
        executeInIDE(Path.of(System.getenv("HOMEPATH"),
                ".mcfuncdev",
                "ide-debug-tool-1.1.0.jar").toFile());
    }

    /** try to build a idea Jar Application Configuration and run it
     * Name: is the file name of the mcfunction file
     * Path to Jar: is the path to the tool jar
     * Program arguments: {hostPort} {mcfunc}
     *
     * @param toolJar the tool jar file
     * */
    private void executeInIDE(File toolJar) {
        try {
            AtomicReference<Socket> execSocket = new AtomicReference<>(targetEditor.getEditor().getUserData(ConnectExecutionAction.k_executionSocket));
            Project project = targetEditor.getEditor().getProject();
            String mcfunc = targetEditor.getFile().getPath();
            // String taskName = targetEditor.getFile().getName();
            assert execSocket.get() != null && execSocket.get().isConnected() && project != null;
            String hostPort = execSocket.get().getInetAddress().getHostAddress() + ":" + execSocket.get().getPort();
            GeneralCommandLine commandLine = new GeneralCommandLine("java", "-jar", toolJar.getAbsolutePath(), hostPort, mcfunc);
            OSProcessHandler processHandler = ProcessHandlerFactory.getInstance().createColoredProcessHandler(commandLine);
            ProcessTerminatedListener.attach(processHandler);
            // add the process handler to the task manager
            RunContentExecutor executor = new RunContentExecutor(project, processHandler)
                    .withTitle(mcfunc)
                    .withActivateToolWindow(true);
            executor.run();
        } catch (AssertionError ignore) {
            Messages.showWarningDialog("Please connect to the Minecraft execution service socket first.", "Not Connected");
        } catch (Exception ignored) {}
    }
}
