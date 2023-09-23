package me.jaffe2718.devkit.filetype;

import com.intellij.openapi.fileTypes.LanguageFileType;
import me.jaffe2718.devkit.McFunctionStaticRes;
import me.jaffe2718.devkit.lang.McFunctionLanguage;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class McFunctionFileType extends LanguageFileType {

    public static final McFunctionFileType INSTANCE = new McFunctionFileType();

    private McFunctionFileType() {
        super(McFunctionLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "Minecraft Function";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Minecraft function language file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "mcfunction";
    }

    @NotNull
    @Override
    public Icon getIcon() {
        return McFunctionStaticRes.ICON;
    }

}
