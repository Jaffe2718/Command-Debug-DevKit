// This is a generated file. Not intended for manual editing.
package me.jaffe2718.devkit.lang.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static me.jaffe2718.devkit.lang.psi.McFunctionTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import me.jaffe2718.devkit.lang.psi.*;

public class McFunctionIdentifierImpl extends ASTWrapperPsiElement implements McFunctionIdentifier {

  public McFunctionIdentifierImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull McFunctionVisitor visitor) {
    visitor.visitIdentifier(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof McFunctionVisitor) accept((McFunctionVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public McFunctionComplexEle getComplexEle() {
    return findChildByClass(McFunctionComplexEle.class);
  }

  @Override
  @Nullable
  public McFunctionNbt getNbt() {
    return findChildByClass(McFunctionNbt.class);
  }

  @Override
  @Nullable
  public McFunctionTagList getTagList() {
    return findChildByClass(McFunctionTagList.class);
  }

}
