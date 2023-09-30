package me.jaffe2718.devkit.editor;

import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorPolicy;
import com.intellij.openapi.fileEditor.FileEditorProvider;
import com.intellij.openapi.fileEditor.TextEditor;
import com.intellij.openapi.fileEditor.impl.text.TextEditorProvider;
import com.intellij.openapi.fileTypes.FileTypeRegistry;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import me.jaffe2718.devkit.filetype.McFunctionFileType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class McFunctionFileEditorProvider implements FileEditorProvider {
    /**
     * The method is expected to run fast.
     *
     * @param project the project in which the file is opened.
     * @param file    file to be tested for acceptance.
     * @return {@code true} if provider can create valid editor for the specified {@code file}.
     */
    @Override
    public boolean accept(@NotNull Project project, @NotNull VirtualFile file) {
        return FileTypeRegistry.getInstance().isFileOfType(file, McFunctionFileType.INSTANCE);
    }

    /**
     * Creates editor for the specified file.
     * <p>
     * This method is called only if the provider has accepted this file (i.e. method {@link #accept(Project, VirtualFile)} returned
     * {@code true}).
     * The provider should return only valid editor.
     *
     * @param project the project in which the file is opened.
     * @param file    the file for which the editor must be created.
     * @return created editor for specified file.
     */
    @Override
    public @NotNull FileEditor createEditor(@NotNull Project project, @NotNull VirtualFile file) {
        TextEditor editor = (TextEditor) TextEditorProvider.getInstance().createEditor(project, file);
        return new McFunctionFileEditor(project, editor, file);
    }

    /**
     * @return editor type ID for the editors created with this FileEditorProvider. Each FileEditorProvider should have
     * a unique nonnull ID. The ID is used for saving/loading of EditorStates.
     * <p>
     * Please consider setting extension id in registration also for performance reasons.
     */
    @Override
    public @NotNull @NonNls String getEditorTypeId() {
        return "mcfunction-editor";
    }

    /**
     * @return a policy that specifies how an editor created via this provider should be opened.
     */
    @Override
    public @NotNull FileEditorPolicy getPolicy() {
        return FileEditorPolicy.HIDE_DEFAULT_EDITOR;
    }
}
