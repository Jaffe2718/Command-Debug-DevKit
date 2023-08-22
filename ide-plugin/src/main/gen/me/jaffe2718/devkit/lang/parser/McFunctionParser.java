// This is a generated file. Not intended for manual editing.
package me.jaffe2718.devkit.lang.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static me.jaffe2718.devkit.lang.psi.McFunctionTypes.*;
import static me.jaffe2718.devkit.lang.parser.McFunctionParserUtil.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class McFunctionParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, null);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    r = parse_root_(t, b);
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b) {
    return parse_root_(t, b, 0);
  }

  static boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return mcFunctionFile(b, l + 1);
  }

  /* ********************************************************** */
  // NBT|STRING|NUMBER|REF|IDENTIFIER
  public static boolean ARGUMENT(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ARGUMENT")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ARGUMENT, "<argument>");
    r = NBT(b, l + 1);
    if (!r) r = consumeToken(b, STRING);
    if (!r) r = consumeToken(b, NUMBER);
    if (!r) r = REF(b, l + 1);
    if (!r) r = IDENTIFIER(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // COMMAND_NAME ARGUMENT*
  public static boolean COMMAND(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "COMMAND")) return false;
    if (!nextTokenIs(b, COMMAND_NAME)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMAND_NAME);
    r = r && COMMAND_1(b, l + 1);
    exit_section_(b, m, COMMAND, r);
    return r;
  }

  // ARGUMENT*
  private static boolean COMMAND_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "COMMAND_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!ARGUMENT(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "COMMAND_1", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // NAMESPACE? ELEMENT NBT?
  public static boolean IDENTIFIER(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "IDENTIFIER")) return false;
    if (!nextTokenIs(b, "<identifier>", ELEMENT, NAMESPACE)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, IDENTIFIER, "<identifier>");
    r = IDENTIFIER_0(b, l + 1);
    r = r && consumeToken(b, ELEMENT);
    r = r && IDENTIFIER_2(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // NAMESPACE?
  private static boolean IDENTIFIER_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "IDENTIFIER_0")) return false;
    consumeToken(b, NAMESPACE);
    return true;
  }

  // NBT?
  private static boolean IDENTIFIER_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "IDENTIFIER_2")) return false;
    NBT(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // "{" NBT_PAIR {"," NBT_PAIR}* "}" | EMPTY_NBT
  public static boolean NBT(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "NBT")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, NBT, "<nbt>");
    r = NBT_0(b, l + 1);
    if (!r) r = consumeToken(b, EMPTY_NBT);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // "{" NBT_PAIR {"," NBT_PAIR}* "}"
  private static boolean NBT_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "NBT_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "{");
    r = r && NBT_PAIR(b, l + 1);
    r = r && NBT_0_2(b, l + 1);
    r = r && consumeToken(b, "}");
    exit_section_(b, m, null, r);
    return r;
  }

  // {"," NBT_PAIR}*
  private static boolean NBT_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "NBT_0_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!NBT_0_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "NBT_0_2", c)) break;
    }
    return true;
  }

  // "," NBT_PAIR
  private static boolean NBT_0_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "NBT_0_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ",");
    r = r && NBT_PAIR(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // (NAMESPACE NBT_VALUE) | (STRING ":" NBT_VALUE)
  public static boolean NBT_PAIR(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "NBT_PAIR")) return false;
    if (!nextTokenIs(b, "<nbt pair>", NAMESPACE, STRING)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, NBT_PAIR, "<nbt pair>");
    r = NBT_PAIR_0(b, l + 1);
    if (!r) r = NBT_PAIR_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // NAMESPACE NBT_VALUE
  private static boolean NBT_PAIR_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "NBT_PAIR_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, NAMESPACE);
    r = r && NBT_VALUE(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // STRING ":" NBT_VALUE
  private static boolean NBT_PAIR_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "NBT_PAIR_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, STRING);
    r = r && consumeToken(b, ":");
    r = r && NBT_VALUE(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // STRING|NUMBER|ELEMENT|NBT
  public static boolean NBT_VALUE(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "NBT_VALUE")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, NBT_VALUE, "<nbt value>");
    r = consumeToken(b, STRING);
    if (!r) r = consumeToken(b, NUMBER);
    if (!r) r = consumeToken(b, ELEMENT);
    if (!r) r = NBT(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // SELECTOR ("["TAG ARGUMENT {"," TAG ARGUMENT}* "]")?
  public static boolean REF(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "REF")) return false;
    if (!nextTokenIs(b, SELECTOR)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, SELECTOR);
    r = r && REF_1(b, l + 1);
    exit_section_(b, m, REF, r);
    return r;
  }

  // ("["TAG ARGUMENT {"," TAG ARGUMENT}* "]")?
  private static boolean REF_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "REF_1")) return false;
    REF_1_0(b, l + 1);
    return true;
  }

  // "["TAG ARGUMENT {"," TAG ARGUMENT}* "]"
  private static boolean REF_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "REF_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "[");
    r = r && consumeToken(b, TAG);
    r = r && ARGUMENT(b, l + 1);
    r = r && REF_1_0_3(b, l + 1);
    r = r && consumeToken(b, "]");
    exit_section_(b, m, null, r);
    return r;
  }

  // {"," TAG ARGUMENT}*
  private static boolean REF_1_0_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "REF_1_0_3")) return false;
    while (true) {
      int c = current_position_(b);
      if (!REF_1_0_3_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "REF_1_0_3", c)) break;
    }
    return true;
  }

  // "," TAG ARGUMENT
  private static boolean REF_1_0_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "REF_1_0_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ",");
    r = r && consumeToken(b, TAG);
    r = r && ARGUMENT(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // "{" | "[" | "(" | "}" | "]" | ")" | "," | ":" | "=" | "^" | "~" | "@"
  public static boolean SYMBS_SET(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "SYMBS_SET")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, SYMBS_SET, "<symbs set>");
    r = consumeToken(b, "{");
    if (!r) r = consumeToken(b, "[");
    if (!r) r = consumeToken(b, "(");
    if (!r) r = consumeToken(b, "}");
    if (!r) r = consumeToken(b, "]");
    if (!r) r = consumeToken(b, ")");
    if (!r) r = consumeToken(b, ",");
    if (!r) r = consumeToken(b, ":");
    if (!r) r = consumeToken(b, "=");
    if (!r) r = consumeToken(b, "^");
    if (!r) r = consumeToken(b, "~");
    if (!r) r = consumeToken(b, "@");
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // COMMAND|COMMENT|CRLF
  static boolean item_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "item_")) return false;
    boolean r;
    r = COMMAND(b, l + 1);
    if (!r) r = consumeToken(b, COMMENT);
    if (!r) r = consumeToken(b, CRLF);
    return r;
  }

  /* ********************************************************** */
  // item_*
  static boolean mcFunctionFile(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "mcFunctionFile")) return false;
    while (true) {
      int c = current_position_(b);
      if (!item_(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "mcFunctionFile", c)) break;
    }
    return true;
  }

}
