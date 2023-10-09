package me.jaffe2718.devkit.filetype;

import com.intellij.json.JsonFileType;
import org.jetbrains.annotations.NotNull;

public class PackMcMetaFileType extends JsonFileType {
    public static final PackMcMetaFileType INSTANCE = new PackMcMetaFileType();

    @Override
    public @NotNull String getName() {
        return "pack.mcmeta " + super.getName();
    }

    @Override
    public @NotNull String getDefaultExtension() {
        return "mcmeta";
    }

    @Override
    public @NotNull String getDescription() {
        return "Minecraft pack.mcmeta file";
    }
}
