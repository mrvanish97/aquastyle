package by.iba.aquastyle.visitors;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiRecursiveVisitor;

public abstract class AQSPsiRecursiveElementVisitor extends PsiElementVisitor implements PsiRecursiveVisitor {
    private final PsiElementVisitor visitor;

    public AQSPsiRecursiveElementVisitor(PsiRecursiveElementVisitorFactory visitorFactory) {
        this.visitor = visitorFactory.buildVisitor(this);
    }

    final public PsiElementVisitor getVisitor() {
        return visitor;
    }

    @Override
    final public void visitElement(PsiElement element) {
        beforeTreeTraversal(element);
        visitor.visitElement(element);
        afterTreeTraversal(element);
    }

    public void beforeTreeTraversal(PsiElement element) {}

    public void afterTreeTraversal(PsiElement element) {}

    public void beforeElementVisited(PsiElement element) {}

    public void afterElementVisited(PsiElement element) {}

    public void beforeElementFinished(PsiElement element) {}

    public void afterElementFinished(PsiElement element) {}
}
