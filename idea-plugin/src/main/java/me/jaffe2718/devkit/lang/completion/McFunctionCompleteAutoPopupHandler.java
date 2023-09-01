package me.jaffe2718.devkit.lang.completion;

import com.intellij.codeInsight.AutoPopupController;
import com.intellij.codeInsight.editorActions.TypedHandlerDelegate;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import me.jaffe2718.devkit.action.ConnectCompletionAction;
import org.jetbrains.annotations.NotNull;

import java.io.PrintWriter;
import java.util.Objects;

public class McFunctionCompleteAutoPopupHandler extends TypedHandlerDelegate {

    private static final String CHAR_SET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_@:~^.{[-\"";
    @Override
    public @NotNull Result checkAutoPopup(char charTyped,
                                          @NotNull Project project,
                                          @NotNull Editor editor,
                                          @NotNull PsiFile file) {
        file.clearCaches();
        try {
            Objects.requireNonNull(editor.getUserData(ConnectCompletionAction.k_completionList)).clear();
        } catch (java.lang.NullPointerException ignored) {}
        if (CHAR_SET.contains(String.valueOf(charTyped))) {
            try {
                PrintWriter pw = editor.getUserData(ConnectCompletionAction.k_completionPrintWriter);
                String lineText = editor.getDocument().getText().substring(
                        editor.getCaretModel().getVisualLineStart(),
                        editor.getCaretModel().getOffset()
                ) + charTyped;
                assert pw != null;
                pw.println(lineText);
                Thread.sleep(20);
            } catch (Exception|AssertionError ignored) {}
            AutoPopupController.getInstance(project).scheduleAutoPopup(editor);
            switch (charTyped) {
                case '"' -> editor.getDocument().insertString(editor.getCaretModel().getOffset(), "\"");
                case '[' -> editor.getDocument().insertString(editor.getCaretModel().getOffset(), "]");
                case '{' -> editor.getDocument().insertString(editor.getCaretModel().getOffset(), "}");
            }
            return Result.STOP;
        } else if (charTyped == ' ') {
            try {
                PrintWriter pw = editor.getUserData(ConnectCompletionAction.k_completionPrintWriter);
                String lineText = editor.getDocument().getText().substring(
                        editor.getCaretModel().getVisualLineStart(),
                        editor.getCaretModel().getOffset()
                ) + charTyped;
                assert pw != null;
                pw.println(lineText);
            } catch (Exception|AssertionError ignored) {}
        }
        return super.checkAutoPopup(charTyped, project, editor, file);
    }

}
