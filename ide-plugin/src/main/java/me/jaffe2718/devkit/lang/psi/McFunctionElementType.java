package me.jaffe2718.devkit.lang.psi;

import com.intellij.psi.tree.IElementType;
import me.jaffe2718.devkit.lang.McFunctionLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class McFunctionElementType extends IElementType {
    /**
     * Creates and registers a new element type for the specified language.
     *
     * @param debugName the name of the element type, used for debugging purposes.
     */
    public McFunctionElementType(@NonNls @NotNull String debugName) {
        super(debugName, McFunctionLanguage.INSTANCE);
    }
}
