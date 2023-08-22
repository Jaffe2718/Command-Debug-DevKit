package me.jaffe2718.devkit.lang.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.lang.Language;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import me.jaffe2718.devkit.filetype.McFunctionFileType;
import me.jaffe2718.devkit.lang.McFunctionLanguage;
import org.jetbrains.annotations.NotNull;

public class McFunctionFile extends PsiFileBase {

    public McFunctionFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, McFunctionLanguage.INSTANCE);
    }

    /**
     * Returns the file type for the file.
     *
     * @return the file type instance.
     */
    @Override
    public @NotNull FileType getFileType() {
        return McFunctionFileType.INSTANCE;
    }

    public String toString() {
        return "Minecraft Function File";
    }
}
