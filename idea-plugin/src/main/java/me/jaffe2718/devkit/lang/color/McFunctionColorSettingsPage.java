package me.jaffe2718.devkit.lang.color;

import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;
import com.intellij.openapi.util.NlsContexts;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Map;

import static me.jaffe2718.devkit.McFunctionStaticRes.MC_ICON;

public class McFunctionColorSettingsPage implements ColorSettingsPage {

    private static final AttributesDescriptor[] DESCRIPTORS = new AttributesDescriptor[]{
            new AttributesDescriptor("Command name", McFunctionSyntaxHighlighter.MC_FUNCTION_COMMAND_NAME),
            new AttributesDescriptor("Macro line start", McFunctionSyntaxHighlighter.MC_FUNCTION_MACRO_START),
            new AttributesDescriptor("Macro", McFunctionSyntaxHighlighter.MC_FUNCTION_MACRO),
            new AttributesDescriptor("Comment", McFunctionSyntaxHighlighter.MC_FUNCTION_COMMENT),
            new AttributesDescriptor("Continuation", McFunctionSyntaxHighlighter.MC_FUNCTION_CONTINUATION),
            new AttributesDescriptor("Element", McFunctionSyntaxHighlighter.MC_FUNCTION_ELEMENT),
            new AttributesDescriptor("Messages", McFunctionSyntaxHighlighter.MC_FUNCTION_MESSAGES),
            new AttributesDescriptor("Namespace", McFunctionSyntaxHighlighter.MC_FUNCTION_NAMESPACE),
            new AttributesDescriptor("Number like", McFunctionSyntaxHighlighter.MC_FUNCTION_NUMBER),
            new AttributesDescriptor("Range", McFunctionSyntaxHighlighter.MC_FUNCTION_RANGE),
            new AttributesDescriptor("Selector reference", McFunctionSyntaxHighlighter.MC_FUNCTION_SELECTOR),
            new AttributesDescriptor("String", McFunctionSyntaxHighlighter.MC_FUNCTION_STRING),
            new AttributesDescriptor("Tag name", McFunctionSyntaxHighlighter.MC_FUNCTION_TAG),
            new AttributesDescriptor("UUID", McFunctionSyntaxHighlighter.MC_FUNCTION_UUID),
            new AttributesDescriptor("Extended syntax", McFunctionSyntaxHighlighter.MC_FUNCTION_EX_SYNTAX),
    };

    @Override
    public @Nullable Icon getIcon() {
        return MC_ICON;
    }

    @Override
    public @NotNull SyntaxHighlighter getHighlighter() {
        return new McFunctionSyntaxHighlighter();
    }

    /**
     * @return the demo text to be displayed in the preview pane of the settings dialog.
     */
    @Override
    public @NonNls @NotNull String getDemoText() {
        return """
               # 1. This is a comment
                   # 2. This is a comment
                   
               # command name
                tellraw
                 title
                  execute
                say
                   give
                summon
               
               # selector(with tag) & element & number
                give @a[tag=foo] command_block 1
               
               # namespace & element
               give @s minecraft:stone 1
               
               # nbt & string
                tellraw @a {"text":"Hello, world!"}
                
               # uuid & tag name
                scoreboard players set @e[limit=1,tag=69ae2b89-6de9-42a3-8c37-c34c26a61bb9] a 0
                
               # range
                random value 0..10
                random value 10..
                random value -5..
                
               # messages
                say Hello, world!
                tell @a Hello, world!
               
               # macro line
                $give $(someone) minecraft:stone ${count}
               
               # continuation
                $give $(someone) \\
                 minecraft:stone ${count}
                say \\
                 Hello,\\
                  world!
                time query \\
                 daytime
               """;
    }

    @Override
    public @Nullable Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
        Map<String, TextAttributesKey> map = new java.util.HashMap<>();
        map.put("MC_FUNCTION_EX_SYNTAX", McFunctionSyntaxHighlighter.MC_FUNCTION_EX_SYNTAX);
        return map;
    }

    @Override
    public AttributesDescriptor @NotNull [] getAttributeDescriptors() {
        return DESCRIPTORS;
    }

    @Override
    public ColorDescriptor @NotNull [] getColorDescriptors() {
        return ColorDescriptor.EMPTY_ARRAY;
    }

    @Override
    public @NotNull @NlsContexts.ConfigurableName String getDisplayName() {
        return "Minecraft Function";
    }
}
