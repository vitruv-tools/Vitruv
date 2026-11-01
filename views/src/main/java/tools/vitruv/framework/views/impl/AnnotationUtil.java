package tools.vitruv.framework.views.impl;

import java.util.Map;
import tools.vitruv.change.composite.description.Annotatable;

/** Utility for transferring annotations from a view to the changes it commits. */
final class AnnotationUtil {
  private AnnotationUtil() {}

  /**
   * Sets all given annotations on the target.
   *
   * @param annotations the annotations to copy, keyed by their type.
   * @param target the element to annotate.
   */
  static void copyAnnotations(Map<Class<?>, Object> annotations, Annotatable target) {
    annotations.forEach((type, value) -> setAnnotation(target, type, value));
  }

  private static <T> void setAnnotation(Annotatable target, Class<T> type, Object value) {
    target.setAnnotation(type, type.cast(value));
  }
}
