/*
 * Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package me.jaffe2718.devkit.editor;

import com.intellij.codeHighlighting.BackgroundEditorHighlighter;
import com.intellij.ide.structureView.StructureViewBuilder;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;

public abstract class AdvancedTextEditor implements TextEditor {

    protected final Project project;
    protected final TextEditor textEditor;
    protected final VirtualFile virtualFile;
    protected final JPanel mainComponent;

    public AdvancedTextEditor(Project project, @NotNull TextEditor textEditor, VirtualFile virtualFile) {
        this.project = project;
        this.textEditor = textEditor;
        this.virtualFile = virtualFile;
        this.mainComponent = new JPanel(new BorderLayout());
        this.mainComponent.add(textEditor.getComponent(), BorderLayout.CENTER);
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
        textEditor.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(@NotNull PropertyChangeListener listener) {
        textEditor.removePropertyChangeListener(listener);
    }
}