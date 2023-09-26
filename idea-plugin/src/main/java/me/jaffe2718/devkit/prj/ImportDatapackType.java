package me.jaffe2718.devkit.prj;

import com.intellij.openapi.module.ModuleType;
import me.jaffe2718.devkit.McFunctionStaticRes;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class ImportDatapackType extends ModuleType<ImportDatapackBuilder> {

    public static final String ID = "IMPORT_DATAPACK_TYPE";
    public static final ImportDatapackType INSTANCE = new ImportDatapackType();

    protected ImportDatapackType() {
        super(ID);
    }

    @Override
    public @NotNull ImportDatapackBuilder createModuleBuilder() {
        return new ImportDatapackBuilder();
    }

    @Override
    public @NotNull @Nls(capitalization = Nls.Capitalization.Title) String getName() {
        return "Minecraft Datapack";
    }

    @Override
    public @NotNull @Nls(capitalization = Nls.Capitalization.Sentence) String getDescription() {
        return "Import a Minecraft datapack.";
    }

    @Override
    public @NotNull Icon getNodeIcon(boolean isOpened) {
        return McFunctionStaticRes.PLUGIN_ICON;
    }
}
