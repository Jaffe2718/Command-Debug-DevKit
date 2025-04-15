package me.jaffe2718.devkit.editor;

import com.intellij.codeHighlighting.BackgroundEditorHighlighter;
import com.intellij.ide.structureView.StructureViewBuilder;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorLocation;
import com.intellij.openapi.fileEditor.FileEditorState;
import com.intellij.openapi.fileEditor.FileEditorStateLevel;
import com.intellij.openapi.fileEditor.TextEditor;
import com.intellij.openapi.fileEditor.impl.text.TextEditorProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.pom.Navigatable;
import me.jaffe2718.devkit.action.help.HelpAction;
import me.jaffe2718.devkit.action.mcfunction.editor.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class McFunctionFileEditor implements TextEditor  {

    protected final Project project;
    protected final TextEditor textEditor;
    protected final VirtualFile virtualFile;
    protected final JPanel mainComponent;

    public McFunctionFileEditor(Project project, @NotNull TextEditor textEditor, VirtualFile virtualFile) {
        this.project = project;
        this.textEditor = textEditor;
        this.virtualFile = virtualFile;
        this.mainComponent = new JPanel(new BorderLayout());
        this.mainComponent.add(textEditor.getComponent(), BorderLayout.CENTER);
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

    private @NotNull ActionToolbar createMcFunctionEditorToolbar() {
        ActionToolbar toolbar = ActionManager.getInstance().createActionToolbar(
                ActionPlaces.EDITOR_TOOLBAR, this.createActionGroup(), true);
        toolbar.setTargetComponent(textEditor.getEditor().getContentComponent());
        return toolbar;
        // return new ActionToolbarImpl()
    }

    @Contract(" -> new")
    private @NotNull ActionGroup createActionGroup() {
        List<AnAction> actions = new ArrayList<>();
        actions.add(new ConnectExecutionAction(this));
        actions.add(new ConnectCompletionAction(this));
        actions.add(new Separator());
        actions.add(new ExecuteWithoutLogAction(this));
        actions.add(new ExecuteAction(this));
        actions.add(new Separator());
        actions.add(new HelpAction());
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

    @Override
    public <T> @Nullable T getUserData(@NotNull Key<T> key) {
        return textEditor.getUserData(key);
    }

    @Override
    public <T> void putUserData(@NotNull Key<T> key, @Nullable T value) {
        textEditor.putUserData(key, value);
    }

    public void dispose() {
        TextEditorProvider.getInstance().disposeEditor(textEditor);
    }

    public @NotNull JComponent getComponent() {
        return mainComponent;
    }

    public @NotNull FileEditorState getState(@NotNull FileEditorStateLevel level) {
        return textEditor.getState(level);
    }

    public void setState(@NotNull FileEditorState state) {
        textEditor.setState(state);
    }

    public boolean isModified() {
        return textEditor.isModified();
    }

    public boolean isValid() {
        return textEditor.isValid();
    }

    public BackgroundEditorHighlighter getBackgroundHighlighter() {
        return textEditor.getBackgroundHighlighter();
    }

    public FileEditorLocation getCurrentLocation() {
        return textEditor.getCurrentLocation();
    }

    public JComponent getPreferredFocusedComponent() {
        return textEditor.getPreferredFocusedComponent();
    }

    public StructureViewBuilder getStructureViewBuilder() {
        return textEditor.getStructureViewBuilder();
    }

    public @NotNull Editor getEditor() {
        return textEditor.getEditor();
    }

    public void navigateTo(@NotNull Navigatable navigatable) {
        textEditor.navigateTo(navigatable);
    }

    public boolean canNavigateTo(@NotNull Navigatable navigatable) {
        return textEditor.canNavigateTo(navigatable);
    }

    public VirtualFile getFile() {
        return virtualFile;
    }

    public void addPropertyChangeListener(@NotNull PropertyChangeListener listener) {
        this.textEditor.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(@NotNull PropertyChangeListener listener) {
        this.textEditor.removePropertyChangeListener(listener);
    }
}
