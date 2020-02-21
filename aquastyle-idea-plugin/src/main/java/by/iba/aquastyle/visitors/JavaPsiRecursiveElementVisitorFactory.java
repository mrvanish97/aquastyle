package by.iba.aquastyle.visitors;

import com.intellij.psi.JavaElementVisitor;
import com.intellij.psi.JavaRecursiveElementWalkingVisitor;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

public class JavaPsiRecursiveElementVisitorFactory implements PsiRecursiveElementVisitorFactory {
    @Override
    public JavaElementVisitor buildVisitor(TreeCallback<PsiElement> callback) {
        return new JavaRecursiveElementWalkingVisitor() {
            @Override
            public void visitElement(PsiElement element) {
                if (callback.shouldBeSkipped(element)) {
                    super.visitElement(element);
                    return;
                }
                callback.beforeElementVisited(element);
                super.visitElement(element);
                callback.afterElementVisited(element);
            }

            @Override
            protected void elementFinished(@NotNull PsiElement element) {
                if (callback.shouldBeSkipped(element)) {
                    super.visitElement(element);
                    return;
                }
                callback.beforeElementFinished(element);
                super.elementFinished(element);
                callback.afterElementFinished(element);
            }
        };
    }
}
