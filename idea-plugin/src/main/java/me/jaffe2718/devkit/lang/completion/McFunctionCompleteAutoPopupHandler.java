package me.jaffe2718.devkit.lang.completion;

import com.intellij.codeInsight.AutoPopupController;
import com.intellij.codeInsight.editorActions.TypedHandlerDelegate;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import me.jaffe2718.devkit.action.ConnectCompletionAction;
import org.jetbrains.annotations.NotNull;

import java.io.PrintWriter;

public class McFunctionCompleteAutoPopupHandler extends TypedHandlerDelegate {

    private static final String CHAR_SET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_@:~^.{[-";
    @Override
    public @NotNull Result checkAutoPopup(char charTyped,
                                          @NotNull Project project,
                                          @NotNull Editor editor,
                                          @NotNull PsiFile file) {
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
            return Result.STOP;
        }
        return super.checkAutoPopup(charTyped, project, editor, file);
    }

}
