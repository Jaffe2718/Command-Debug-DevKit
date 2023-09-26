package me.jaffe2718.devkit.prj;

import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.ui.Messages;
import me.jaffe2718.devkit.prj.ui.ConfigUI;

import javax.swing.*;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Hashtable;

public class DatapackWizardStep extends ModuleWizardStep {
    public static Hashtable<String, DatapackPrjMeta> packsMeta = new Hashtable<>();    // <prjName, metaObj>
    WizardContext context;
    ConfigUI configUI;

    public DatapackWizardStep(WizardContext context) {
        this.context = context;
        this.configUI = new ConfigUI();
        this.configUI.locationTextField.setText(context.getProjectFileDirectory());
    }

    /**
     * @return {@link JComponent} that represents step's UI in the wizard. This
     * method should not return {@code null}.
     */
    @Override
    public JComponent getComponent() {
        return this.configUI.getPanel();
    }

    /**
     * Commits data from UI into ModuleBuilder and WizardContext
     */
    @Override
    public void updateDataModel() {
        try {
            assert Paths.get(this.configUI.locationTextField.getText()).isAbsolute();
        } catch (AssertionError | InvalidPathException e) {
            Messages.showErrorDialog("Invalid path of project file directory: " +
                    this.configUI.locationTextField.getText(), "Invalid Path");
            throw new RuntimeException("Invalid path of project file directory: " +
                    this.configUI.locationTextField.getText());
        }
        String prjName = this.configUI.datapackNameTextField.getText();
        String parentDir = this.configUI.locationTextField.getText();
        try {
            assert Path.of(parentDir, prjName).isAbsolute();
        } catch (AssertionError | InvalidPathException e) {
            Messages.showErrorDialog("Invalid datapack name: " + prjName, "Invalid Datapack Name");
            throw new RuntimeException("Invalid datapack name: " + prjName);
        }
        if (prjName.isBlank() || Path.of(parentDir, prjName).getParent().toString().equals(Path.of(parentDir).toString())) {
            Messages.showErrorDialog("Invalid project name: " + prjName, "Invalid Project Name");
            throw new RuntimeException("Invalid project name: " + prjName);
        } else if (!this.configUI.namespaceTextField.getText().matches("[a-z_][a-z0-9_]*")) {
            Messages.showErrorDialog("Namespace must be a valid Minecraft namespace.", "Invalid Namespace");
            throw new RuntimeException("Namespace must be a valid Minecraft namespace.");
        }

        this.context.setProjectName(this.configUI.datapackNameTextField.getText());
        this.context.setProjectFileDirectory(Path.of(
                this.configUI.locationTextField.getText(),
                this.configUI.datapackNameTextField.getText()).toString());
        this.context.setCompilerOutputDirectory(Path.of(
                this.configUI.locationTextField.getText(),
                this.configUI.datapackNameTextField.getText(),
                "build").toString());
        packsMeta.put(this.configUI.datapackNameTextField.getText(),
                new DatapackPrjMeta(
                        this.configUI.datapackNameTextField.getText(),
                        this.configUI.namespaceTextField.getText(),
                        this.configUI.descriptionTextArea.getText(),
                        this.configUI.packFormatSpinner.getValue().toString()
                )
        );
    }

    public record DatapackPrjMeta(String datapackName, String namespace, String description, String packFormat) {
    }
}
