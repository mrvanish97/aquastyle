package by.iba.aquastyle.actions;

import by.iba.aquastyle.tokenutils.TokenUtils;
import by.iba.aquastyle.visitors.AQSPsiRecursiveElementVisitor;
import by.iba.aquastyle.visitors.JavaPsiRecursiveElementVisitorFactory;
import by.iba.aquastyle.visitors.PsiRecursiveElementVisitorFactories;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.pom.Navigatable;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

public class PopupDialogAction extends AnAction {

    final static String PATH = "/Users/uonagent/output_plugin3.txt";
    @Override
    public void actionPerformed(AnActionEvent e) {
        Project currentProject = e.getProject();
        StringBuffer dlgMsg = new StringBuffer(e.getPresentation().getText() + " Selected!");
        String dlgTitle = e.getPresentation().getDescription();
        Navigatable nav = e.getData(CommonDataKeys.NAVIGATABLE);

        if (nav != null) {
            dlgMsg.append(String.format("\nSelected Element: %s", nav.toString()));
        }

        /*VirtualFile file = e.getData(PlatformDataKeys.VIRTUAL_FILE);
        if(file != null) {
            try {
                byte[] openedFileBytes = file.contentsToByteArray();
                String openedFileAsString = new String(openedFileBytes, StandardCharsets.UTF_8);
                dlgMsg.append("\n" + openedFileAsString) ;
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }*/
        try {
            Files.deleteIfExists(Paths.get(PATH));
            Files.createFile(Paths.get(PATH));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);
        if (psiFile != null) {

            /*psiFile.accept(new AQSPsiRecursiveElementVisitor(PsiRecursiveElementVisitorFactories.JAVA) {

                private final List<String> typeStack = new ArrayList<>();
                private final List<Integer> uidStack = new ArrayList<>();
                private int counter = 0;

                @Override
                public void beforeTreeTraversal(PsiElement element) {
                    super.beforeTreeTraversal(element);
                    printToFile("DOPE STARTED\n\n");
                }

                @Override
                public void afterTreeTraversal(PsiElement element) {
                    super.afterTreeTraversal(element);
                    printToFile("DOPE FINISHED\n");
                }

                @Override
                public void beforeElementVisited(PsiElement element) {
                    super.beforeElementVisited(element);
                    if (element.getText().trim().length() != 0) { // not a WHITESPACE or any other kind of meaningless junk
                        final String msg = trimAndFormatElementString(element.toString());
                        if (element.getChildren().length != 0) { // if NOT a LEAF
                            String lastStackElement = null;
                            if (typeStack.size() > 0 && uidStack.size() > 0) {
                                lastStackElement = typeStack.get(typeStack.size() - 1);
                            }
                            if (lastStackElement == null || !lastStackElement.equals(element.toString())) {
                                typeStack.add(element.toString());
                                uidStack.add(counter++);
                            }
                        } else {
                            String elemText = element.getText();
                            boolean isDot = elemText.equals(";") || elemText.equals("{") || elemText.equals("->");;
                            StringBuilder sb = new StringBuilder("");
                            sb.append(element.getText()).append("\n\t[").append(msg).append("]");
                            for (int i = typeStack.size() - 1; i >= 0; --i) {
                                sb.append("\n\t----").append(trimAndFormatElementString(typeStack.get(i))).append("(").append(uidStack.get(i)).append(")");
                            }
                            if (isDot) {
                                sb.append("\n").append("//////// END OF THE STATEMENT ////////");
                            }
                            sb.append("\n\n");
                            try {
                                Files.write(Paths.get(PATH), sb.toString().getBytes(), StandardOpenOption.APPEND);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }

                @Override
                public void afterElementFinished(PsiElement element) {
                    super.afterElementFinished(element);
                    if (element.getChildren().length != 0 && typeStack.size() > 0 && uidStack.size() > 0) {
                        final String peekedElementString = typeStack.get(typeStack.size() - 1);
                        if (peekedElementString.equals(element.toString())) {
                            typeStack.remove(typeStack.size() - 1);
                            uidStack.remove(uidStack.size() - 1);
                        }
                    }
                }

                private void printToFile(String s) {
                    try {
                        Files.write(Paths.get(PATH), s.getBytes(), StandardOpenOption.APPEND);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }

                private String trimAndFormatElementString(final String elementString) {
                    String trimmedString = elementString;
                    final int colonIndexOfMsg = elementString.indexOf(':');
                    if (colonIndexOfMsg != -1) {
                        trimmedString = elementString.substring(0, colonIndexOfMsg);
                    }
                    return trimmedString.replace("Psi", "");
                }
            });*/

            psiFile.accept(new AQSPsiRecursiveElementVisitor(PsiRecursiveElementVisitorFactories.JAVA) {

                private final StringBuilder sb = new StringBuilder();

                @Override
                public void beforeElementVisited(PsiElement element) {
                    final String msg = trimAndFormatElementString(element.toString());
                    sb.append("-").append(
                        String.join(" ", TokenUtils.CAMEL_CASE_SPLITTER.apply(msg)).toLowerCase().replaceAll(" ", "")
                    ).append(" ");
                    if (isLeaf(element)) {
                        final String text = element.getText().trim();
                        sb.append(
                            String.join(" ", TokenUtils.CAMEL_CASE_SPLITTER.apply(text)).toLowerCase()
                        ).append("\n");
                        if (TokenUtils.JAVA_DOTS.contains(text)) {
                            sb.append("\n");
                        }
                    } else {
                        sb.append("\n");
                    }
                }

                @Override
                public void afterElementFinished(PsiElement element) {
                    if (!isLeaf(element)) {
                        final String msg = trimAndFormatElementString(element.toString());
                        sb.append("-endof-").append(
                            String.join(" ", TokenUtils.CAMEL_CASE_SPLITTER.apply(msg)).toLowerCase().replaceAll(" ", "")
                        ).append("\n");
                    }
                }

                @Override
                public boolean shouldBeSkipped(PsiElement element) {
                    return element.getText().trim().length() == 0;
                }

                @Override
                public void afterTreeTraversal(PsiElement element) {
                    try {
                        Files.write(Paths.get(PATH), sb.toString().getBytes(), StandardOpenOption.APPEND);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }

                private String trimAndFormatElementString(final String elementString) {
                    String trimmedString = elementString;
                    final int colonIndexOfMsg = elementString.indexOf(':');
                    if (colonIndexOfMsg != -1) {
                        trimmedString = elementString.substring(0, colonIndexOfMsg);
                    }
                    return trimmedString.replace("Psi", "");
                }

                private boolean isLeaf(PsiElement element) {
                    return element.getChildren().length == 0;
                }
            });
        }

        Messages.showMessageDialog(currentProject, dlgMsg.toString(), dlgTitle, Messages.getInformationIcon());
    }
    @Override
    public void update(AnActionEvent e) {

    }
}
