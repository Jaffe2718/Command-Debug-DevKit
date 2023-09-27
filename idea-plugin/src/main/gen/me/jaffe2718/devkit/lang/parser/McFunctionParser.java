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
  // (SELECTOR|IDENTIFIER_DOMAIN|IDENTIFIER|NBT|COMPLEX_ELE|UUID|RANGE|STRING|NUMBER|ELEMENT|OPERATOR) | EX_SYNTAX
  public static boolean ARGUMENT(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ARGUMENT")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ARGUMENT, "<argument>");
    r = ARGUMENT_0(b, l + 1);
    if (!r) r = consumeToken(b, EX_SYNTAX);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // SELECTOR|IDENTIFIER_DOMAIN|IDENTIFIER|NBT|COMPLEX_ELE|UUID|RANGE|STRING|NUMBER|ELEMENT|OPERATOR
  private static boolean ARGUMENT_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ARGUMENT_0")) return false;
    boolean r;
    r = SELECTOR(b, l + 1);
    if (!r) r = IDENTIFIER_DOMAIN(b, l + 1);
    if (!r) r = IDENTIFIER(b, l + 1);
    if (!r) r = NBT(b, l + 1);
    if (!r) r = COMPLEX_ELE(b, l + 1);
    if (!r) r = consumeToken(b, UUID);
    if (!r) r = consumeToken(b, RANGE);
    if (!r) r = consumeToken(b, STRING);
    if (!r) r = consumeToken(b, NUMBER);
    if (!r) r = consumeToken(b, ELEMENT);
    if (!r) r = consumeToken(b, OPERATOR);
    return r;
  }

  /* ********************************************************** */
  // COMMAND_NAME (ARGUMENT|CONTINUATION)*
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

  // (ARGUMENT|CONTINUATION)*
  private static boolean COMMAND_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "COMMAND_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!COMMAND_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "COMMAND_1", c)) break;
    }
    return true;
  }

  // ARGUMENT|CONTINUATION
  private static boolean COMMAND_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "COMMAND_1_0")) return false;
    boolean r;
    r = ARGUMENT(b, l + 1);
    if (!r) r = consumeToken(b, CONTINUATION);
    return r;
  }

  /* ********************************************************** */
  // (ELEMENT|UUID) ("." (ELEMENT|UUID|NUMBER))+
  public static boolean COMPLEX_ELE(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "COMPLEX_ELE")) return false;
    if (!nextTokenIs(b, "<complex ele>", ELEMENT, UUID)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, COMPLEX_ELE, "<complex ele>");
    r = COMPLEX_ELE_0(b, l + 1);
    r = r && COMPLEX_ELE_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ELEMENT|UUID
  private static boolean COMPLEX_ELE_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "COMPLEX_ELE_0")) return false;
    boolean r;
    r = consumeToken(b, ELEMENT);
    if (!r) r = consumeToken(b, UUID);
    return r;
  }

  // ("." (ELEMENT|UUID|NUMBER))+
  private static boolean COMPLEX_ELE_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "COMPLEX_ELE_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = COMPLEX_ELE_1_0(b, l + 1);
    while (r) {
      int c = current_position_(b);
      if (!COMPLEX_ELE_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "COMPLEX_ELE_1", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // "." (ELEMENT|UUID|NUMBER)
  private static boolean COMPLEX_ELE_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "COMPLEX_ELE_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ".");
    r = r && COMPLEX_ELE_1_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ELEMENT|UUID|NUMBER
  private static boolean COMPLEX_ELE_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "COMPLEX_ELE_1_0_1")) return false;
    boolean r;
    r = consumeToken(b, ELEMENT);
    if (!r) r = consumeToken(b, UUID);
    if (!r) r = consumeToken(b, NUMBER);
    return r;
  }

  /* ********************************************************** */
  // NAMESPACE (COMPLEX_ELE|ELEMENT) NBT? TAG_LIST?
  public static boolean IDENTIFIER(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "IDENTIFIER")) return false;
    if (!nextTokenIs(b, NAMESPACE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, NAMESPACE);
    r = r && IDENTIFIER_1(b, l + 1);
    r = r && IDENTIFIER_2(b, l + 1);
    r = r && IDENTIFIER_3(b, l + 1);
    exit_section_(b, m, IDENTIFIER, r);
    return r;
  }

  // COMPLEX_ELE|ELEMENT
  private static boolean IDENTIFIER_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "IDENTIFIER_1")) return false;
    boolean r;
    r = COMPLEX_ELE(b, l + 1);
    if (!r) r = consumeToken(b, ELEMENT);
    return r;
  }

  // NBT?
  private static boolean IDENTIFIER_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "IDENTIFIER_2")) return false;
    NBT(b, l + 1);
    return true;
  }

  // TAG_LIST?
  private static boolean IDENTIFIER_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "IDENTIFIER_3")) return false;
    TAG_LIST(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // "#" IDENTIFIER
  public static boolean IDENTIFIER_DOMAIN(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "IDENTIFIER_DOMAIN")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, IDENTIFIER_DOMAIN, "<identifier domain>");
    r = consumeToken(b, "#");
    r = r && IDENTIFIER(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // MACRO_START (ARGUMENT|CONTINUATION|MACRO)*
  public static boolean MACRO_LINE(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MACRO_LINE")) return false;
    if (!nextTokenIs(b, MACRO_START)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, MACRO_START);
    r = r && MACRO_LINE_1(b, l + 1);
    exit_section_(b, m, MACRO_LINE, r);
    return r;
  }

  // (ARGUMENT|CONTINUATION|MACRO)*
  private static boolean MACRO_LINE_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MACRO_LINE_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!MACRO_LINE_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "MACRO_LINE_1", c)) break;
    }
    return true;
  }

  // ARGUMENT|CONTINUATION|MACRO
  private static boolean MACRO_LINE_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MACRO_LINE_1_0")) return false;
    boolean r;
    r = ARGUMENT(b, l + 1);
    if (!r) r = consumeToken(b, CONTINUATION);
    if (!r) r = consumeToken(b, MACRO);
    return r;
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
  // "[" (NBT|STRING) ("," (NBT|STRING))* ","? "]" | EMPTY_LIST
  public static boolean NBT_LIST(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "NBT_LIST")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, NBT_LIST, "<nbt list>");
    r = NBT_LIST_0(b, l + 1);
    if (!r) r = consumeToken(b, EMPTY_LIST);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // "[" (NBT|STRING) ("," (NBT|STRING))* ","? "]"
  private static boolean NBT_LIST_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "NBT_LIST_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "[");
    r = r && NBT_LIST_0_1(b, l + 1);
    r = r && NBT_LIST_0_2(b, l + 1);
    r = r && NBT_LIST_0_3(b, l + 1);
    r = r && consumeToken(b, "]");
    exit_section_(b, m, null, r);
    return r;
  }

  // NBT|STRING
  private static boolean NBT_LIST_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "NBT_LIST_0_1")) return false;
    boolean r;
    r = NBT(b, l + 1);
    if (!r) r = consumeToken(b, STRING);
    return r;
  }

  // ("," (NBT|STRING))*
  private static boolean NBT_LIST_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "NBT_LIST_0_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!NBT_LIST_0_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "NBT_LIST_0_2", c)) break;
    }
    return true;
  }

  // "," (NBT|STRING)
  private static boolean NBT_LIST_0_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "NBT_LIST_0_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ",");
    r = r && NBT_LIST_0_2_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // NBT|STRING
  private static boolean NBT_LIST_0_2_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "NBT_LIST_0_2_0_1")) return false;
    boolean r;
    r = NBT(b, l + 1);
    if (!r) r = consumeToken(b, STRING);
    return r;
  }

  // ","?
  private static boolean NBT_LIST_0_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "NBT_LIST_0_3")) return false;
    consumeToken(b, ",");
    return true;
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
  // STRING|NUMBER|ELEMENT|UUID|NBT|NBT_LIST|COMPLEX_ELE
  public static boolean NBT_VALUE(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "NBT_VALUE")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, NBT_VALUE, "<nbt value>");
    r = consumeToken(b, STRING);
    if (!r) r = consumeToken(b, NUMBER);
    if (!r) r = consumeToken(b, ELEMENT);
    if (!r) r = consumeToken(b, UUID);
    if (!r) r = NBT(b, l + 1);
    if (!r) r = NBT_LIST(b, l + 1);
    if (!r) r = COMPLEX_ELE(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // REF TAG_LIST?
  public static boolean SELECTOR(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "SELECTOR")) return false;
    if (!nextTokenIs(b, REF)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, REF);
    r = r && SELECTOR_1(b, l + 1);
    exit_section_(b, m, SELECTOR, r);
    return r;
  }

  // TAG_LIST?
  private static boolean SELECTOR_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "SELECTOR_1")) return false;
    TAG_LIST(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // "[" TAG ARGUMENT {"," TAG ARGUMENT}* "]"
  public static boolean TAG_LIST(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TAG_LIST")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, TAG_LIST, "<tag list>");
    r = consumeToken(b, "[");
    r = r && consumeToken(b, TAG);
    r = r && ARGUMENT(b, l + 1);
    r = r && TAG_LIST_3(b, l + 1);
    r = r && consumeToken(b, "]");
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // {"," TAG ARGUMENT}*
  private static boolean TAG_LIST_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TAG_LIST_3")) return false;
    while (true) {
      int c = current_position_(b);
      if (!TAG_LIST_3_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "TAG_LIST_3", c)) break;
    }
    return true;
  }

  // "," TAG ARGUMENT
  private static boolean TAG_LIST_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TAG_LIST_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ",");
    r = r && consumeToken(b, TAG);
    r = r && ARGUMENT(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // COMMAND|MACRO_LINE|COMMENT|CRLF
  static boolean item_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "item_")) return false;
    boolean r;
    r = COMMAND(b, l + 1);
    if (!r) r = MACRO_LINE(b, l + 1);
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
