package me.jaffe2718.devkit.prj;

import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.ui.Messages;

import javax.swing.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Hashtable;

public class DatapackWizardStep extends ModuleWizardStep {
    public static Hashtable<String, DatapackPrjMeta> packsMeta = new Hashtable<>();
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
        if (!Paths.get(this.configUI.locationTextField.getText()).isAbsolute()) {
            Messages.showErrorDialog("Invalid path of project file directory: " +
                    this.configUI.locationTextField.getText(), "Invalid Path");
            throw new RuntimeException("Invalid path of project file directory: " +
                    this.configUI.locationTextField.getText());
        }else if (this.configUI.datapackNameTextField.getText().isBlank()) {
            Messages.showErrorDialog("Datapack name cannot be empty.", "Invalid Datapack Name");
            throw new RuntimeException("Datapack name cannot be empty.");
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
        public String getDatapackName() {
            return datapackName;
        }

        public String getNamespace() {
            return namespace;
        }

        public String getDescription() {
            return description;
        }

        public String getPackFormat() {
            return packFormat;
        }
    }
}
