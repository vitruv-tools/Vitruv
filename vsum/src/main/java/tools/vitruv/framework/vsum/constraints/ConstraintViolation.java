package tools.vitruv.framework.vsum.constraints;

import static com.google.common.base.Preconditions.checkNotNull;

import org.eclipse.emf.ecore.EObject;

/**
 * A single element violating a constraint, as reported by a {@link ConstraintChecker}.
 *
 * @param element the violating element, which must be an element of the virtual model
 * @param severity the severity of this violation
 * @param message a human-readable explanation, may be {@code null}
 */
public record ConstraintViolation(EObject element, ConstraintSeverity severity, String message) {

  /** Validates that the element and severity are set. */
  public ConstraintViolation {
    checkNotNull(element, "violating element");
    checkNotNull(severity, "severity");
  }
}
