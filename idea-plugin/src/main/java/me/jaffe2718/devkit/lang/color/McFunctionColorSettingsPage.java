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

import static me.jaffe2718.devkit.McFunctionIcons.FILE;

public class McFunctionColorSettingsPage implements ColorSettingsPage {

    private static final AttributesDescriptor[] DESCRIPTORS = new AttributesDescriptor[]{
            new AttributesDescriptor("Command name", McFunctionSyntaxHighlighter.MC_FUNCTION_COMMAND_NAME),
            new AttributesDescriptor("Comment", McFunctionSyntaxHighlighter.MC_FUNCTION_COMMENT),
            new AttributesDescriptor("Element", McFunctionSyntaxHighlighter.MC_FUNCTION_ELEMENT),
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
        return FILE;
    }

    @Override
    public @NotNull SyntaxHighlighter getHighlighter() {
        return new McFunctionSyntaxHighlighter();
    }

    @Override
    public @NonNls @NotNull String getDemoText() {
        return """
                # Command name #
                
                say
                execute
                summon
                
                # Comment #
                
                # This is a comment
                give @s stone # This is a comment behind a command
                            
                # Element #
                
                give @s stone # the `stone` is an element
                           
                # Namespace #
                
                 minecraft:
                 mod_name:
                
                # Number like #
                
                 1.0
                 ~3
                 -5
                
                # Range #
                
                 1..5
                 ..5
                 1..
                
                # Selector reference #
                
                 @s
                 @a
                 @e
                
                # String #
                
                 "Hello, world!"
                
                # Tag name #
                
                 type=
                 tag=
                 name=
                
                UUID:
                 00000000-0000-0000-0000-000000000000
                
                # Extended syntax #
                
                ^^^^^
                # other symbols or words or etc.
                """;
    }

    @Override
    public @Nullable Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
        // TODO: write additional highlighting
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
