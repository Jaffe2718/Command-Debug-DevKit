package me.jaffe2718.devkit.lang.syntax;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import me.jaffe2718.devkit.lang.psi.McFunctionTypes;
import org.jetbrains.annotations.NotNull;

public class McFunctionSyntaxHighlighter extends SyntaxHighlighterBase {

    public static final TextAttributesKey MC_FUNCTION_COMMENT =
            TextAttributesKey.createTextAttributesKey("MC_FUNCTION_COMMENT",
                    DefaultLanguageHighlighterColors.LINE_COMMENT);
    public static final TextAttributesKey MC_FUNCTION_SYMBOL =
            TextAttributesKey.createTextAttributesKey("MC_FUNCTION_SYMBOL",
                    DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey MC_FUNCTION_STRING =
            TextAttributesKey.createTextAttributesKey("MC_FUNCTION_STRING",
                    DefaultLanguageHighlighterColors.STRING);
    public static final TextAttributesKey MC_FUNCTION_NUMBER =
            TextAttributesKey.createTextAttributesKey("MC_FUNCTION_NUMBER",
                    DefaultLanguageHighlighterColors.NUMBER);
    public static final TextAttributesKey MC_FUNCTION_COMMAND_NAME =
            TextAttributesKey.createTextAttributesKey("MC_FUNCTION_COMMAND_NAME",
                    DefaultLanguageHighlighterColors.CONSTANT);
    public static final TextAttributesKey MC_FUNCTION_SELECTOR =
            TextAttributesKey.createTextAttributesKey("MC_FUNCTION_REF",
                    DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey MC_FUNCTION_NAMESPACE =
            TextAttributesKey.createTextAttributesKey("MC_FUNCTION_NAMESPACE",
                    DefaultLanguageHighlighterColors.FUNCTION_DECLARATION);
    public static final TextAttributesKey MC_FUNCTION_ELEMENT =
            TextAttributesKey.createTextAttributesKey("MC_FUNCTION_ELEMENT",
                    DefaultLanguageHighlighterColors.FUNCTION_CALL);

    private static final TextAttributesKey[] COMMENT_KEYS = new TextAttributesKey[]{MC_FUNCTION_COMMENT};
    private static final TextAttributesKey[] SYMBOL_KEYS = new TextAttributesKey[]{MC_FUNCTION_SYMBOL};
    private static final TextAttributesKey[] STRING_KEYS = new TextAttributesKey[]{MC_FUNCTION_STRING};
    private static final TextAttributesKey[] NUMBER_KEYS = new TextAttributesKey[]{MC_FUNCTION_NUMBER};
    private static final TextAttributesKey[] COMMAND_NAME_KEYS = new TextAttributesKey[]{MC_FUNCTION_COMMAND_NAME};
    private static final TextAttributesKey[] SELECTOR_KEYS = new TextAttributesKey[]{MC_FUNCTION_SELECTOR};
    private static final TextAttributesKey[] NAMESPACE_KEYS = new TextAttributesKey[]{MC_FUNCTION_NAMESPACE};
    private static final TextAttributesKey[] ELEMENT_KEYS = new TextAttributesKey[]{MC_FUNCTION_ELEMENT};

    /**
     * Returns the lexer used for highlighting the file. The lexer is invoked incrementally when the file is changed, so it must be
     * capable of saving/restoring state and resuming lexing from the middle of the file.
     *
     * @return The lexer implementation.
     */
    @Override
    public @NotNull Lexer getHighlightingLexer() {
        return new McFunctionLexerAdapter();
    }

    /**
     * Returns the list of text attribute keys used for highlighting the specified token type. The attributes of all attribute keys
     * returned for the token type are successively merged to obtain the color and attributes of the token.
     *
     * @param tokenType The token type for which the highlighting is requested.
     * @return The array of text attribute keys.
     */
    @Override
    public TextAttributesKey @NotNull [] getTokenHighlights(IElementType tokenType) {
        if (tokenType.equals(McFunctionTypes.COMMENT)) {
            return COMMENT_KEYS;
        } else if (tokenType.equals(McFunctionTypes.SYMBS_SET)) {
            return SYMBOL_KEYS;
        } else if (tokenType.equals(McFunctionTypes.STRING)) {
            return STRING_KEYS;
        } else if (tokenType.equals(McFunctionTypes.NUMBER)) {
            return NUMBER_KEYS;
        } else if (tokenType.equals(McFunctionTypes.COMMAND_NAME)) {
            return COMMAND_NAME_KEYS;
        } else if (tokenType.equals(McFunctionTypes.SELECTOR)) {
            return SELECTOR_KEYS;
        } else if (tokenType.equals(McFunctionTypes.NAMESPACE)) {
            return NAMESPACE_KEYS;
        } else if (tokenType.equals(McFunctionTypes.ELEMENT)) {
            return ELEMENT_KEYS;
        } else {
            return TextAttributesKey.EMPTY_ARRAY;
        }
    }
}
