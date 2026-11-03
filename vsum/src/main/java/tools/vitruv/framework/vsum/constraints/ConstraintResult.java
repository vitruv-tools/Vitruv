package tools.vitruv.framework.vsum.constraints;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

/**
 * The result of checking a single constraint, as reported by a {@link ConstraintChecker}.
 *
 * @param descriptor the checked constraint
 * @param status the outcome of the check
 * @param violations the violating elements, empty unless the status is {@link
 *     ConstraintStatus#VIOLATED}
 * @param errors messages explaining why the constraint could not be checked, empty unless the
 *     status is {@link ConstraintStatus#ERROR}
 */
public record ConstraintResult(
    ConstraintDescriptor descriptor,
    ConstraintStatus status,
    List<ConstraintViolation> violations,
    List<String> errors) {

  /** Validates the descriptor and status and makes the lists immutable. */
  public ConstraintResult {
    checkNotNull(descriptor, "descriptor");
    checkNotNull(status, "status");
    violations = violations == null ? List.of() : List.copyOf(violations);
    errors = errors == null ? List.of() : List.copyOf(errors);
  }

  /**
   * Creates the result for a constraint that holds.
   *
   * @param descriptor the checked constraint
   * @return the result
   */
  public static ConstraintResult satisfied(ConstraintDescriptor descriptor) {
    return new ConstraintResult(descriptor, ConstraintStatus.SATISFIED, List.of(), List.of());
  }

  /**
   * Creates the result for a violated constraint.
   *
   * @param descriptor the checked constraint
   * @param violations the violating elements, may be empty for constraints without a single
   *     violating element
   * @return the result
   */
  public static ConstraintResult violated(
      ConstraintDescriptor descriptor, List<ConstraintViolation> violations) {
    return new ConstraintResult(descriptor, ConstraintStatus.VIOLATED, violations, List.of());
  }

  /**
   * Creates the result for a constraint whose status is derived from the given violations: it is
   * satisfied if there are none and violated otherwise.
   *
   * @param descriptor the checked constraint
   * @param violations the violating elements
   * @return the result
   */
  public static ConstraintResult of(
      ConstraintDescriptor descriptor, List<ConstraintViolation> violations) {
    return violations.isEmpty() ? satisfied(descriptor) : violated(descriptor, violations);
  }

  /**
   * Creates the result for a constraint that could not be checked.
   *
   * @param descriptor the constraint
   * @param errors the reasons why the constraint could not be checked
   * @return the result
   */
  public static ConstraintResult error(ConstraintDescriptor descriptor, List<String> errors) {
    return new ConstraintResult(descriptor, ConstraintStatus.ERROR, List.of(), errors);
  }
}
