package by.iba.aquastyle.visitors;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiRecursiveVisitor;

public abstract class AQSPsiRecursiveElementVisitor extends PsiElementVisitor implements PsiRecursiveVisitor, TreeCallback<PsiElement> {
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

    @Override
    public void beforeTreeTraversal(PsiElement element) {}

    @Override
    public void afterTreeTraversal(PsiElement element) {}

    @Override
    public void beforeElementVisited(PsiElement element) {}

    @Override
    public void afterElementVisited(PsiElement element) {}

    @Override
    public void beforeElementFinished(PsiElement element) {}

    @Override
    public void afterElementFinished(PsiElement element) {}

    @Override
    public boolean shouldBeSkipped(PsiElement element) {
        return false;
    }
}
