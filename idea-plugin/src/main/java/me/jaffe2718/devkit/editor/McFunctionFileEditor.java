package me.jaffe2718.devkit.editor;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.TextEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import me.jaffe2718.devkit.action.*;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class McFunctionFileEditor extends AdvancedTextEditor {


    public McFunctionFileEditor(Project project, TextEditor textEditor, VirtualFile virtualFile) {
        super(project, textEditor, virtualFile);
        ActionToolbar toolbar = createMcFunctionEditorToolbar();
        mainComponent.add(toolbar.getComponent(), BorderLayout.NORTH);
    }

    /**
     * Returns editor's name - a string that identifies the editor among others
     * (e.g.: "GUI Designer" for graphical editing and "Text" for textual representation of a GUI form editor).
     */
    @Override
    public @Nls(capitalization = Nls.Capitalization.Title) @NotNull String getName() {
        return "McFunction Editor";
    }

    private ActionToolbar createMcFunctionEditorToolbar() {
        ActionToolbar toolbar = ActionManager.getInstance().createActionToolbar(
                ActionPlaces.EDITOR_TOOLBAR, this.createActionGroup(), true);
        toolbar.setTargetComponent(textEditor.getEditor().getContentComponent());
        return toolbar;
        // return new ActionToolbarImpl()
    }

    private ActionGroup createActionGroup() {
        List<AnAction> actions = new ArrayList<>();
        actions.add(new ConnectCompletionAction(this));
        actions.add(new ConnectExecutionAction(this));
        actions.add(new Separator());
        actions.add(new ExecuteWithoutLogAction(this));
        actions.add(new ExecuteAction(this));
        return new DefaultActionGroup(actions);
    }

    public Thread buildCompletionListenerThread() {
        return new Thread(() -> {
            while (true) {
                try {
                    Editor editor = this.getEditor();
                    if (editor.isDisposed()) {  // check if this is the correct way to check if the editor is disposed
                        return;
                    }
                    Socket socket = editor.getUserData(ConnectCompletionAction.k_completionSocket);
                    assert socket != null;
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    List<String> completions = editor.getUserData(ConnectCompletionAction.k_completionList);
                    assert completions != null;
                    while (true) {
                        String line;
                        while((line = reader.readLine()) != null) {
                            completions.add(completions.size(), line);
                        }
                    }
                } catch (Exception ignored) {
                }
            }
        });
    }
}
