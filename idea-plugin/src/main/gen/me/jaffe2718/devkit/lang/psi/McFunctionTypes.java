// This is a generated file. Not intended for manual editing.
package me.jaffe2718.devkit.lang.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import me.jaffe2718.devkit.lang.psi.impl.*;

public interface McFunctionTypes {

  IElementType ARGUMENT = new McFunctionElementType("ARGUMENT");
  IElementType COMMAND = new McFunctionElementType("COMMAND");
  IElementType COMPLEX_ELE = new McFunctionElementType("COMPLEX_ELE");
  IElementType IDENTIFIER = new McFunctionElementType("IDENTIFIER");
  IElementType IDENTIFIER_DOMAIN = new McFunctionElementType("IDENTIFIER_DOMAIN");
  IElementType MACRO_LINE = new McFunctionElementType("MACRO_LINE");
  IElementType NBT = new McFunctionElementType("NBT");
  IElementType NBT_LIST = new McFunctionElementType("NBT_LIST");
  IElementType NBT_PAIR = new McFunctionElementType("NBT_PAIR");
  IElementType NBT_VALUE = new McFunctionElementType("NBT_VALUE");
  IElementType SELECTOR = new McFunctionElementType("SELECTOR");
  IElementType TAG_LIST = new McFunctionElementType("TAG_LIST");

  IElementType COMMAND_NAME = new McFunctionTokenType("COMMAND_NAME");
  IElementType COMMENT = new McFunctionTokenType("COMMENT");
  IElementType CONTINUATION = new McFunctionTokenType("CONTINUATION");
  IElementType CRLF = new McFunctionTokenType("CRLF");
  IElementType ELEMENT = new McFunctionTokenType("ELEMENT");
  IElementType EMPTY_LIST = new McFunctionTokenType("EMPTY_LIST");
  IElementType EMPTY_NBT = new McFunctionTokenType("EMPTY_NBT");
  IElementType EX_SYNTAX = new McFunctionTokenType("EX_SYNTAX");
  IElementType MACRO = new McFunctionTokenType("MACRO");
  IElementType MACRO_START = new McFunctionTokenType("MACRO_START");
  IElementType MESSAGES = new McFunctionTokenType("MESSAGES");
  IElementType NAMESPACE = new McFunctionTokenType("NAMESPACE");
  IElementType NUMBER = new McFunctionTokenType("NUMBER");
  IElementType OPERATOR = new McFunctionTokenType("OPERATOR");
  IElementType RANGE = new McFunctionTokenType("RANGE");
  IElementType REF = new McFunctionTokenType("REF");
  IElementType STATIC_TEXT = new McFunctionTokenType("STATIC_TEXT");
  IElementType STRING = new McFunctionTokenType("STRING");
  IElementType TAG = new McFunctionTokenType("TAG");
  IElementType UUID = new McFunctionTokenType("UUID");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == ARGUMENT) {
        return new McFunctionArgumentImpl(node);
      }
      else if (type == COMMAND) {
        return new McFunctionCommandImpl(node);
      }
      else if (type == COMPLEX_ELE) {
        return new McFunctionComplexEleImpl(node);
      }
      else if (type == IDENTIFIER) {
        return new McFunctionIdentifierImpl(node);
      }
      else if (type == IDENTIFIER_DOMAIN) {
        return new McFunctionIdentifierDomainImpl(node);
      }
      else if (type == MACRO_LINE) {
        return new McFunctionMacroLineImpl(node);
      }
      else if (type == NBT) {
        return new McFunctionNbtImpl(node);
      }
      else if (type == NBT_LIST) {
        return new McFunctionNbtListImpl(node);
      }
      else if (type == NBT_PAIR) {
        return new McFunctionNbtPairImpl(node);
      }
      else if (type == NBT_VALUE) {
        return new McFunctionNbtValueImpl(node);
      }
      else if (type == SELECTOR) {
        return new McFunctionSelectorImpl(node);
      }
      else if (type == TAG_LIST) {
        return new McFunctionTagListImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
