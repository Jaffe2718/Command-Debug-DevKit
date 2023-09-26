package me.jaffe2718.devkit.prj;

import com.intellij.ide.util.projectWizard.ModuleBuilder;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ContentEntry;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import me.jaffe2718.devkit.prj.unit.DatapackImporter;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public class ImportDatapackBuilder extends ModuleBuilder implements DatapackImporter {

    @Override
    public void setupRootModel(@NotNull ModifiableRootModel rootModel) {
        Project prj = rootModel.getProject();
        var meta = ImportDatapackWizardStep.importPacksMeta.get(prj.getName());
        Path extPth = Path.of(meta.extDir(), meta.prjName()).toAbsolutePath();
        extPth.resolve("build").toFile().mkdirs();
        ContentEntry rootEntry = rootModel.addContentEntry(extPth.toUri().toString());
        rootEntry.addExcludeFolder(extPth.resolve("build").toUri().toString());
        rootEntry.addSourceFolder(extPth.resolve("data").toUri().toString(), false);
        this.extractDatapack(meta.packPth(), extPth.toString());
        ImportDatapackWizardStep.importPacksMeta.remove(prj.getName());
        // refresh project
        VirtualFile extDir = LocalFileSystem.getInstance().refreshAndFindFileByIoFile(new File(extPth.toString()));
        assert extDir != null;
        extDir.refresh(false, true);
    }

    @Override
    public ModuleType<?> getModuleType() {
        return ImportDatapackType.INSTANCE;
    }

    @Override
    protected @Nls(capitalization = Nls.Capitalization.Title) String getModuleTypeName() {
        return "Import Minecraft Datapack";
    }

    @Override
    public @Nullable ModuleWizardStep getCustomOptionsStep(WizardContext context, Disposable parentDisposable) {
        return new ImportDatapackWizardStep(context);
    }

    @Override
    public @NotNull List<Class<? extends ModuleWizardStep>> getIgnoredSteps() {
        return List.of(ModuleWizardStep.class);
    }
}
