package me.jaffe2718.devkit.prj;

import com.intellij.openapi.module.ModuleType;
import me.jaffe2718.devkit.McFunctionStaticRes;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class DatapackModuleType extends ModuleType<DatapackModuleBuilder> {

    private static final String ID = "DATAPACK_MODULE_TYPE";

    public static final DatapackModuleType INSTANCE = new DatapackModuleType();

    public DatapackModuleType() {
        super(ID);
    }

    @Override
    public @NotNull DatapackModuleBuilder createModuleBuilder() {
        return new DatapackModuleBuilder();
    }

    @Override
    public @NotNull @Nls(capitalization = Nls.Capitalization.Title) String getName() {
        return "Datapack (Minecraft)";
    }

    @Override
    public @NotNull @Nls(capitalization = Nls.Capitalization.Sentence) String getDescription() {
        return "A Minecraft datapack.";
    }

    @Override
    public @NotNull Icon getNodeIcon(boolean isOpened) {
        return McFunctionStaticRes.PLUGIN_ICON;
    }
}

