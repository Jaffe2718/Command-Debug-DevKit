package me.jaffe2718.devkit.prj;

import com.intellij.ide.util.projectWizard.ModuleBuilder;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import me.jaffe2718.devkit.McFunctionStaticRes;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The project wizard step for creating a new datapack.
 * */
public class DatapackModuleBuilder extends ModuleBuilder {

    @Override
    public void setupRootModel(@NotNull ModifiableRootModel rootModel) {
        Project project = rootModel.getProject();
        Path prjPth = Path.of(Objects.requireNonNull(project.getBasePath())).toAbsolutePath(); // the project directory
        var meta = DatapackWizardStep.packsMeta.get(project.getName());
        // create the pack.mcmeta file
        File packMetaFile = new File(prjPth.toString(), "pack.mcmeta");
        try {
            // create the file and write the contents
            assert packMetaFile.createNewFile();
            String packMetaContents = "{\n" +
                    "  \"pack\": {\n" +
                    "    \"pack_format\": " + meta.packFormat() + ",\n" +
                    "    \"description\": \"" + meta.description().replace("\n", "\\n") + "\"\n" +
                    "  }\n" +
                    "}";
            VirtualFile packMetaVFile = LocalFileSystem.getInstance().refreshAndFindFileByIoFile(packMetaFile);
            assert packMetaVFile != null;
            packMetaVFile.setBinaryContent(packMetaContents.getBytes());
            packMetaVFile.refresh(false, false);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create pack.mcmeta file.", e);
        }
        // create the `data/<namespace>/functions/demo.mcfunction` file
        File demoFunctionFile = new File(prjPth.toString(), "data/" + meta.namespace() + "/functions/demo.mcfunction");
        try {
            // create the file and write the contents
            assert demoFunctionFile.getParentFile().mkdirs() && demoFunctionFile.createNewFile();
            VirtualFile demoFunctionVFile = LocalFileSystem.getInstance().refreshAndFindFileByIoFile(demoFunctionFile);
            assert demoFunctionVFile != null;
            String demoFunctionContents =
                    "# This is a demo function.\n" +
                    "# It will be run when the datapack is published and loaded.\n" +
                    "# \n" +
                    "# To run this function, type `/function " + meta.namespace() + ":demo` into chat.\n" +
                    "# \n" +
                    "# To learn more about functions, visit https://minecraft.fandom.com/wiki/Function_(Java_Edition)\n" +

                    "say Hello, world!";
            demoFunctionVFile.setBinaryContent(demoFunctionContents.getBytes());
            demoFunctionVFile.refresh(false, false);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create demo function file.", e);
        }
        assert new File(prjPth.toString(), "build").mkdir();    // create the `build` directory
        // copy the `pack.png` file
        try (OutputStream outputStream = new FileOutputStream(
                Path.of(prjPth.toString(), "pack.png").toAbsolutePath().toFile())) {
            InputStream inputStream = getClass().getResourceAsStream(McFunctionStaticRes.PACK_PNG);
            assert inputStream != null;
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to copy pack.png file.", e);
        }
        VirtualFile prjVFile = LocalFileSystem.getInstance().refreshAndFindFileByIoFile(prjPth.toFile());
        VirtualFile srcVFile = LocalFileSystem.getInstance().refreshAndFindFileByIoFile(new File(prjPth.toString(), "data"));
        VirtualFile buildVFile = LocalFileSystem.getInstance().refreshAndFindFileByIoFile(new File(prjPth.toString(), "build"));
        var rootEntry = rootModel.addContentEntry(prjVFile);
        rootEntry.addSourceFolder(srcVFile, false);
        rootEntry.addExcludeFolder(buildVFile);
        DatapackWizardStep.packsMeta.clear();                        // clear the cached metadata
    }

    @Override
    public ModuleType<?> getModuleType() {
        return DatapackModuleType.INSTANCE;
    }

    @Override
    protected @Nls(capitalization = Nls.Capitalization.Title) String getModuleTypeName() {
        return "Minecraft Datapack";
    }

    @Override
    public @Nullable ModuleWizardStep getCustomOptionsStep(@NotNull WizardContext context,
                                                           @NotNull Disposable parentDisposable) {
        return new DatapackWizardStep(context);
    }

    @Override
    public @NotNull List<Class<? extends ModuleWizardStep>> getIgnoredSteps() {
        ArrayList<Class<? extends ModuleWizardStep>> ignoredSteps = new ArrayList<>();
        ignoredSteps.add(ModuleWizardStep.class);
        return ignoredSteps;
    }
}
