package me.jaffe2718.devkit.lang;

import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import me.jaffe2718.devkit.lang.parser.McFunctionParser;
import me.jaffe2718.devkit.lang.psi.McFunctionFile;
import me.jaffe2718.devkit.lang.psi.McFunctionTokenSets;
import me.jaffe2718.devkit.lang.psi.McFunctionTypes;
import me.jaffe2718.devkit.lang.syntax.McFunctionLexerAdapter;
import org.jetbrains.annotations.NotNull;

public class McFunctionParserDefinition implements ParserDefinition {

    public static final IFileElementType FILE = new IFileElementType(McFunctionLanguage.INSTANCE);

    /**
     * Returns the lexer for lexing files in the specified project. This lexer does not need to support incremental relexing - it is always
     * called for the entire file.
     *
     * @param project the project to which the lexer is connected.
     * @return the lexer instance.
     */
    @Override
    public @NotNull Lexer createLexer(Project project) {
        return new McFunctionLexerAdapter();
    }

    /**
     * Returns the parser for parsing files in the specified project.
     *
     * @param project the project to which the parser is connected.
     * @return the parser instance.
     */
    @Override
    public @NotNull PsiParser createParser(Project project) {
        return new McFunctionParser();
    }

    /**
     * Returns the element type of the node describing a file in the specified language.
     *
     * @return the file node element type.
     */
    @Override
    public @NotNull IFileElementType getFileNodeType() {
        return FILE;
    }

    /**
     * Returns the set of token types which are treated as comments by the PSI builder.
     * Tokens of those types are automatically skipped by PsiBuilder. Also, To Do patterns
     * are searched in the text of tokens of those types.
     * For composite comment elements it should contain only the root element type
     *
     * @return the set of comment token types.
     */
    @Override
    public @NotNull TokenSet getCommentTokens() {
        return McFunctionTokenSets.COMMENTS;
    }

    /**
     * Returns the set of element types which are treated as string literals. "Search in strings"
     * option in refactorings is applied to the contents of such tokens.
     *
     * @return the set of string literal element types.
     */
    @Override
    public @NotNull TokenSet getStringLiteralElements() {
        return McFunctionTokenSets.STRINGS;
    }

    /**
     * Creates a PSI element for the specified AST node. The AST tree is a simple, semantic-free
     * tree of AST nodes which is built during the PsiBuilder parsing pass. The PSI tree is built
     * over the AST tree and includes elements of different types for different language constructs.
     * <p>
     * !!!WARNING!!! PSI element types should be unambiguously determined by AST node element types.
     * You should not produce different PSI elements from AST nodes of the same types (e.g. based on AST node content).
     * Typically, your code should be as simple as that:
     * <pre>{@code
     *   if (node.getElementType == MY_ELEMENT_TYPE) {
     *     return new MyPsiElement(node);
     *   }
     * }</pre>
     *
     * @param node the node for which the PSI element should be returned.
     * @return the PSI element matching the element type of the AST node.
     */
    @Override
    public @NotNull PsiElement createElement(ASTNode node) {
        return McFunctionTypes.Factory.createElement(node);
    }

    /**
     * Creates a PSI element for the specified virtual file.
     *
     * @param viewProvider virtual file.
     * @return the PSI file element.
     */
    @Override
    public @NotNull PsiFile createFile(@NotNull FileViewProvider viewProvider) {
        return new McFunctionFile(viewProvider);
    }
}
