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

%state MESSAGE_ARGS
%state MESSAGES
%state MACRO_LINE

%%
<YYINITIAL> {
    {CONTINUE}                                         { return CONTINUATION; }
    ^"say"                                             { yybegin(MESSAGES); return COMMAND_NAME;}
    ^("msg"|"w")                                       { yybegin(MESSAGE_ARGS); return COMMAND_NAME;}
    ^{ELE_START}{ELE_CHAR}*                            { return COMMAND_NAME;}
    ^"$"                                               { yybegin(MACRO_LINE); return MACRO_START; }
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

<MESSAGE_ARGS> {
    {EMPTY_NBT_DATA}                                   { yybegin(MESSAGES); return EMPTY_NBT; }
    {EMPTY_LIST_DATA}                                  { yybegin(MESSAGES); return EMPTY_LIST; }
    {RANGE}                                            { yybegin(MESSAGES); return RANGE; }
    {NUMBER_LIKE}                                      { yybegin(MESSAGES); return NUMBER; }
    {OPERATOR}                                         { yybegin(MESSAGES); return OPERATOR; }
    {TAG_NAME}                                         { yybegin(MESSAGES); return TAG; }
    {NAMESPACE}                                        { yybegin(MESSAGES); return NAMESPACE; }
    @{REF_C}                                           { yybegin(MESSAGES); return REF; }
    {STRING_DATA}                                      { yybegin(MESSAGES); return STRING; }
    {UUID}                                             { yybegin(MESSAGES); return UUID; }
    {ELEMENT}|{ELE_PATH}                               { yybegin(MESSAGES); return ELEMENT; }
    " "                                                { return WHITE_SPACE;}
}

<MESSAGES> {
     " "                                               { return WHITE_SPACE;}
     [^\s\r\n][^\r\n]*                                 {yybegin(YYINITIAL); return McFunctionTypes.MESSAGES;}
     [\r\n]+                                           {yybegin(YYINITIAL); return WHITE_SPACE;}
}

<MACRO_LINE> {
    {MACRO}                                            { return MACRO; }
    {CONTINUE}                                         { return CONTINUATION;}
    {LINE_COMMENT}                                     { yybegin(YYINITIAL); return McFunctionTypes.COMMENT; }
    \s                                                 { return WHITE_SPACE;}
    (\n|\r)+                                           { yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }
    [^\s]*?                                            { return STATIC_TEXT;}
}
// ("{" | "[" | "(" | "}" | "]" | ")" | "," | ":" | "=" | "^" | "~" | "@")
// [\{\[\(\}\]\)\,\:\=\^\~\@#]                            { return SYMBS_SET; }
[^]                                                    { return EX_SYNTAX; }
