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
  IElementType NBT = new McFunctionElementType("NBT");
  IElementType NBT_LIST = new McFunctionElementType("NBT_LIST");
  IElementType NBT_PAIR = new McFunctionElementType("NBT_PAIR");
  IElementType NBT_VALUE = new McFunctionElementType("NBT_VALUE");
  IElementType REF = new McFunctionElementType("REF");
  IElementType SYMBS_SET = new McFunctionElementType("SYMBS_SET");

  IElementType COMMAND_NAME = new McFunctionTokenType("COMMAND_NAME");
  IElementType COMMENT = new McFunctionTokenType("COMMENT");
  IElementType CRLF = new McFunctionTokenType("CRLF");
  IElementType ELEMENT = new McFunctionTokenType("ELEMENT");
  IElementType EMPTY_LIST = new McFunctionTokenType("EMPTY_LIST");
  IElementType EMPTY_NBT = new McFunctionTokenType("EMPTY_NBT");
  IElementType NAMESPACE = new McFunctionTokenType("NAMESPACE");
  IElementType NUMBER = new McFunctionTokenType("NUMBER");
  IElementType SELECTOR = new McFunctionTokenType("SELECTOR");
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
      else if (type == REF) {
        return new McFunctionRefImpl(node);
      }
      else if (type == SYMBS_SET) {
        return new McFunctionSymbsSetImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
