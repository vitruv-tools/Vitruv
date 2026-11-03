package tools.vitruv.framework.vsum.constraints;

import java.util.List;

/**
 * The result of validating a single constraint against a virtual model.
 *
 * @param descriptor the validated constraint
 * @param status the outcome of the validation
 * @param violations the violating elements, resolved to their identity in the virtual model
 * @param errors messages explaining why the constraint could not be checked
 */
public record ConstraintValidationResult(
    ConstraintDescriptor descriptor,
    ConstraintStatus status,
    List<ResolvedConstraintViolation> violations,
    List<String> errors) {

  /** Makes the lists immutable. */
  public ConstraintValidationResult {
    violations = List.copyOf(violations);
    errors = List.copyOf(errors);
  }
}
