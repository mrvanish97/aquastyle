package by.iba.aquastyle.visitors;

import com.intellij.psi.PsiElementVisitor;

public interface PsiRecursiveElementVisitorFactory {
    PsiElementVisitor buildVisitor(AQSPsiRecursiveElementVisitor delegate);
}
