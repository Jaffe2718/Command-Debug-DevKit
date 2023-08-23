package me.jaffe2718.devkit.lang.psi;

import com.intellij.psi.tree.TokenSet;

public interface McFunctionTokenSets {

    TokenSet COMMENTS = TokenSet.create(McFunctionTypes.COMMENT);
//    TokenSet COMMAND_NAMES = TokenSet.create(McFunctionTypes.COMMAND_NAME);
//    TokenSet SELECTORS = TokenSet.create(McFunctionTypes.SELECTOR);
//    TokenSet NUMBER_LIKES = TokenSet.create(McFunctionTypes.NUMBER_LIKE);
    TokenSet STRINGS = TokenSet.create(McFunctionTypes.STRING);
//    TokenSet OBJECT = TokenSet.create(McFunctionTypes.OBJECT);
}
