package by.iba.aquastyle.visitors;

import com.intellij.psi.JavaElementVisitor;
import com.intellij.psi.JavaRecursiveElementWalkingVisitor;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

public class JavaPsiRecursiveElementVisitorFactory implements PsiRecursiveElementVisitorFactory {
    @Override
    public JavaElementVisitor buildVisitor(AQSPsiRecursiveElementVisitor delegate) {
        return new JavaRecursiveElementWalkingVisitor() {
            @Override
            public void visitElement(PsiElement element) {
                delegate.beforeElementVisited(element);
                super.visitElement(element);
                delegate.afterElementVisited(element);
            }

            @Override
            protected void elementFinished(@NotNull PsiElement element) {
                delegate.beforeElementFinished(element);
                super.elementFinished(element);
                delegate.afterElementFinished(element);
            }
        };
    }
}
