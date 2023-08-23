package me.jaffe2718.devkit.lang.syntax;

import com.intellij.lexer.FlexAdapter;
import me.jaffe2718.devkit.lang.McFunctionLexer;

public class McFunctionLexerAdapter extends FlexAdapter {

    public McFunctionLexerAdapter() {
        super(new McFunctionLexer(null));
    }
}
