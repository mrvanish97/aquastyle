package by.iba.aquastyle.visitors;

public interface TreeCallback<T> {
  void beforeTreeTraversal(T element);

  void afterTreeTraversal(T element);

  void beforeElementVisited(T element);

  void afterElementVisited(T element);

  void beforeElementFinished(T element);

  void afterElementFinished(T element);

  boolean shouldBeSkipped(T element);
}
