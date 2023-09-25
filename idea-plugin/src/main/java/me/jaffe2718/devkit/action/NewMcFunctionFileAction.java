package me.jaffe2718.devkit.action;

import com.intellij.ide.actions.CreateFileAction;
import com.intellij.ide.ui.newItemPopup.NewItemPopupUtil;
import com.intellij.ide.ui.newItemPopup.NewItemSimplePopupPanel;
import com.intellij.lang.LangBundle;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.Experiments;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.InputValidatorEx;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import me.jaffe2718.devkit.McFunctionStaticRes;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.function.Consumer;

public class NewMcFunctionFileAction extends CreateFileAction {
    public NewMcFunctionFileAction() {
        super("Minecraft Function File", "Creates a new McFunction file", McFunctionStaticRes.MC_ICON);
    }

    @Override
    protected PsiElement @NotNull [] create(@NotNull String newName, @NotNull PsiDirectory directory) throws Exception {
        return super.create(newName, directory);
    }

    @Override
    protected String getDefaultExtension() {
        return "mcfunction";
    }

    @Override
    protected void invokeDialog(@NotNull Project project, @NotNull PsiDirectory directory, @NotNull Consumer<? super PsiElement[]> elementsConsumer) {
        MyInputValidator validator = new MyValidator(project, directory);
        if (ApplicationManager.getApplication().isUnitTestMode()) {
            try {
                elementsConsumer.accept(validator.create("test"));
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        else {
            if (Experiments.getInstance().isFeatureEnabled("show.create.new.element.in.popup")) {
                createLightWeightPopup(validator, elementsConsumer).showCenteredInCurrentWindow(project);
            }
            else {
                Messages.showInputDialog(project, "Create a new Minecraft function file (*.mcfunction).",
                        "New Minecraft Function File", McFunctionStaticRes.MC_ICON, null, validator);
                elementsConsumer.accept(validator.getCreatedElements());
            }
        }
    }

    private @NotNull JBPopup createLightWeightPopup(@NotNull MyInputValidator validator,
                                                    @NotNull Consumer<? super PsiElement[]> consumer) {
        NewItemSimplePopupPanel contentPanel = new NewItemSimplePopupPanel();
        JTextField nameField = contentPanel.getTextField();
        JBPopup popup = NewItemPopupUtil.createNewItemPopup("New Minecraft Function File", contentPanel, nameField);
        popup.setCaptionIcon(McFunctionStaticRes.MC_ICON);
        contentPanel.setApplyAction(event -> {
            String name = nameField.getText();
            if (validator.checkInput(name) && validator.canClose(name)) {
                popup.closeOk(event);
                consumer.accept(validator.getCreatedElements());
            }
            else {
                String errorMessage = validator instanceof InputValidatorEx
                        ? ((InputValidatorEx)validator).getErrorText(name)
                        : LangBundle.message("incorrect.name");
                contentPanel.setError(errorMessage);
            }
        });

        return popup;
    }
}
