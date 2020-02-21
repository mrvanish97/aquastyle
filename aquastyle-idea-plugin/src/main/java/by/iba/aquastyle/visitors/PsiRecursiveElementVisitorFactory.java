package by.iba.aquastyle.visitors;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;

public interface PsiRecursiveElementVisitorFactory {
    PsiElementVisitor buildVisitor(TreeCallback<PsiElement> callback);
}
