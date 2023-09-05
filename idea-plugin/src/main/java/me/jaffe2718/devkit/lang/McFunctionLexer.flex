package me.jaffe2718.devkit.lang;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import me.jaffe2718.devkit.lang.psi.McFunctionTypes;
import com.intellij.psi.TokenType;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static me.jaffe2718.devkit.lang.psi.McFunctionTypes.*;

%%

%public
%class McFunctionLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

CRLF=\R
WHITE_SPACE=[\ \n\t\f]
LINE_COMMENT="#"[^\r\n]*

NUM=[0-9]
ELE_START=[a-zA-Z_]
ELE_CHAR={ELE_START}|{NUM}
REF_C=[aeprs]

TAG_NAME={ELE_START}{ELE_CHAR}*=
NUMBER_LIKE=([~\^]?-?{NUM}+(\.{NUM}+)?) | [~\^]

NAMESPACE={ELE_START}{ELE_CHAR}*:
ELEMENT={ELE_START}{ELE_CHAR}*
ADVANCEMENT={ELE_START}{ELE_CHAR}*\/{ELE_START}{ELE_CHAR}*
STRING_DATA=\"[^\"]*\"

/* match a nbt data like
{
    key0:value0,
    key1:{key2:value2}
}
*/
EMPTY_NBT_DATA=\{\}
EMPTY_LIST_DATA=\[\]

%state WAITING_VALUE

%%
<YYINITIAL> {

    ^{ELE_START}{ELE_CHAR}*                            { return COMMAND_NAME;}
    {EMPTY_NBT_DATA}                                   { return EMPTY_NBT; }
    {EMPTY_LIST_DATA}                                  { return EMPTY_LIST; }
    {NUMBER_LIKE}                                      { return NUMBER; }
    {TAG_NAME}                                         { return TAG; }
    {NAMESPACE}                                        { return NAMESPACE; }
    {ELEMENT}                                          { return ELEMENT; }
    {ADVANCEMENT}                                      { return ELEMENT; }
    @{REF_C}                                           { return SELECTOR; }
    {STRING_DATA}                                      { return STRING; }

    {LINE_COMMENT}                                   { yybegin(YYINITIAL); return McFunctionTypes.COMMENT; }
    ({CRLF}|{WHITE_SPACE})+                          { yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }
}
// ("{" | "[" | "(" | "}" | "]" | ")" | "," | ":" | "=" | "^" | "~" | "@")
[\{\[\(\}\]\)\,\:\=\^\~\@]                           { return SYMBS_SET; }
[^]                                                  { return TokenType.BAD_CHARACTER; }
