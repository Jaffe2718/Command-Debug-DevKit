package me.jaffe2718.devkit.prj;

import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.ui.Messages;
import me.jaffe2718.devkit.prj.ui.ImportUI;
import me.jaffe2718.devkit.prj.unit.DatapackImporter;

import javax.swing.*;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Hashtable;

public class ImportDatapackWizardStep extends ModuleWizardStep implements DatapackImporter {

    public static Hashtable<String, ImportPackMeta> importPacksMeta = new Hashtable<>();    // <prjName, metaObj>
    ImportUI ui;
    WizardContext context;

    public ImportDatapackWizardStep(WizardContext ctx) {
        this.context = ctx;
        this.ui = new ImportUI();
        this.ui.textFieldExtDir.setText(ctx.getProjectFileDirectory());
    }

    @Override
    public JComponent getComponent() {
        return ui.getPanel();
    }

    /**
     * Commits data from UI into ModuleBuilder and WizardContext
     */
    @Override
    public void updateDataModel() {

        String datapackPth = this.ui.textFieldPckPth.getText();
        String extDir = this.ui.textFieldExtDir.getText();
        String prjName = this.ui.textFieldPrjName.getText();

        if (!this.validateDatapack(datapackPth)) {
            Messages.showErrorDialog(
                    "The datapack path is invalid or the target file is not in datapack format.",
                    "Invalid Datapack Path");
            throw new RuntimeException("Invalid datapack path");
        } else {
            try {
                assert Paths.get(extDir).isAbsolute();
            } catch (InvalidPathException e) {
                Messages.showErrorDialog(
                        "The directory to contain the project folder is invalid.",
                        "Invalid Directory Path");
                throw new RuntimeException("Invalid directory path");
            }
            try {
                Path prjPth = Paths.get(extDir, prjName);
                assert prjPth.isAbsolute() && prjPth.getParent().equals(Paths.get(extDir));
            } catch (InvalidPathException e) {
                Messages.showErrorDialog(
                        "The project name is invalid.",
                        "Invalid Project Name");
                throw new RuntimeException("Invalid project name");
            }
        }

        this.context.setProjectName(prjName);
        this.context.setProjectFileDirectory(Path.of(extDir, prjName).toString());
        this.context.setCompilerOutputDirectory(Path.of(extDir, prjName, "build").toString());
        importPacksMeta.put(prjName, new ImportPackMeta(datapackPth, extDir, prjName));
    }

    public record ImportPackMeta(String packPth,  // the path to the datapack zip file
                                 String extDir,   // the path to the parent dir of the project dir
                                 String prjName   // the name of the project to be created
    ){}
}
