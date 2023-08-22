package me.jaffe2718.devkit.lang.psi;

import com.intellij.lang.Language;
import com.intellij.psi.tree.IElementType;
import me.jaffe2718.devkit.lang.McFunctionLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class McFunctionTokenType extends IElementType {
    /**
     * Creates and registers a new element type for the specified language.
     *
     * @param debugName the name of the element type, used for debugging purposes.
     */
    public McFunctionTokenType(@NonNls @NotNull String debugName) {
        super(debugName, McFunctionLanguage.INSTANCE);
    }

    @Override
    public String toString() {
        return "McFunctionTokenType." + super.toString();
    }
}
