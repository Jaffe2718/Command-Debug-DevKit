package me.jaffe2718.devkit.action;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.RunContentExecutor;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessHandlerFactory;
import com.intellij.execution.process.ProcessTerminatedListener;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Objects;

import static me.jaffe2718.devkit.McFunctionIcons.FILE;
import static me.jaffe2718.devkit.McFunctionIcons.TOOL_NAME;

public class McCommandConsoleAction extends AnAction {

    public McCommandConsoleAction() {
        super("Minecraft Command Console",
                "Create a new Minecraft Command Console to execute commands in.",
                FILE);
    }
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        String hostport = Messages.showInputDialog(
                "Enter the host and port of the command execution\n service socket to create new console to execute\n commands in interactive mode.",
                "New Minecraft Command Console",
                FILE,
                "localhost:",
                null);
        if (hostport != null) {
            // try to check if %HOMEPATH%\.mcfuncdev\ide-debug-tool-<version>.jar exists
            try {
                this.executeInIDE(e, this.extractIDEDebugTool(), hostport);
            } catch (ExecutionException ignored) {}
        }
    }

    private void executeInIDE(AnActionEvent e,File jarfile, String hostPort) throws ExecutionException {
        GeneralCommandLine commandLine = new GeneralCommandLine("java", "-jar", jarfile.getAbsolutePath(), hostPort);
        OSProcessHandler processHandler = ProcessHandlerFactory.getInstance().createColoredProcessHandler(commandLine);
        ProcessTerminatedListener.attach(processHandler);
        // add the process handler to the task manager
        // get the project
        new RunContentExecutor(Objects.requireNonNull(e.getProject()), processHandler)
                .withTitle("Minecraft Command Console (" + hostPort + ")")
                .withActivateToolWindow(true)
                .run();
    }

    private File extractIDEDebugTool() {
        File libs = Path.of(System.getenv("HOMEPATH"), ".mcfuncdev").toFile();
        if (libs.exists() && libs.isDirectory()) {
            for (File jarfile : Objects.requireNonNull(libs.listFiles())) {
                if (jarfile.getName().startsWith("ide-debug-tool-") && jarfile.getName().endsWith(".jar")) {
                    return jarfile;
                }
            }

        } else {
            // if not, create it
            assert libs.mkdir();
        }
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
