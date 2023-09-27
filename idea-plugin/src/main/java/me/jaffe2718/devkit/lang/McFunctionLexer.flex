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

CONTINUE=\\(" "|\t|\f|{LINE_COMMENT})*\r?\n
CRLF=\R
WHITE_SPACE=[\ \n\t\f]
LINE_COMMENT="# "[^\r\n]*

NUM=[0-9]
ELE_START=[a-zA-Z_]
ELE_CHAR={ELE_START}|{NUM}
REF_C=[aeprs]

TAG_NAME={ELE_START}{ELE_CHAR}*=
NUMBER_LIKE=([~\^]?-?{NUM}+(\.{NUM}+)?) | [~\^]

NAMESPACE={ELE_START}{ELE_CHAR}*:
ELEMENT={ELE_START}{ELE_CHAR}*
ELE_PATH={ELE_START}{ELE_CHAR}*(\/{ELE_START}{ELE_CHAR}*)+  // like `adventure/kill_a_mob`
MACRO=("$"\({ELEMENT}\)) | ("$"\{{ELEMENT}\})
STRING_DATA=\"[^\"]*\"
OPERATOR=(<|<=|=|>=|>)

/* match a nbt data like
{
    key0:value0,
    key1:{key2:value2}
}
*/
EMPTY_NBT_DATA=\{\}
EMPTY_LIST_DATA=\[\]
UUID = [0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}
RANGE = (-?[0-9]+\.\.(-?[0-9]+)?)|((-?[0-9]+)?\.\.-?[0-9]+)

%state WAITING_VALUE

%%
<YYINITIAL> {

    {CONTINUE}                                         { return CONTINUATION; }
    {MACRO}                                            { return MACRO; }
    ^{ELE_START}{ELE_CHAR}*                            { return COMMAND_NAME;}
    ^"$"                                               { return MACRO_START; }
    {EMPTY_NBT_DATA}                                   { return EMPTY_NBT; }
    {EMPTY_LIST_DATA}                                  { return EMPTY_LIST; }
    {RANGE}                                            { return RANGE; }
    {NUMBER_LIKE}                                      { return NUMBER; }
    {OPERATOR}                                         { return OPERATOR; }
    {TAG_NAME}                                         { return TAG; }
    {NAMESPACE}                                        { return NAMESPACE; }
    @{REF_C}                                           { return REF; }
    {STRING_DATA}                                      { return STRING; }
    {UUID}                                             { return UUID; }
    {ELEMENT}|{ELE_PATH}                               { return ELEMENT; }
    {LINE_COMMENT}                                     { yybegin(YYINITIAL); return McFunctionTypes.COMMENT; }
    ({CRLF}|{WHITE_SPACE})+                            { yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }
}
// ("{" | "[" | "(" | "}" | "]" | ")" | "," | ":" | "=" | "^" | "~" | "@")
// [\{\[\(\}\]\)\,\:\=\^\~\@#]                            { return SYMBS_SET; }
[^]                                                    { return EX_SYNTAX; }
