package tools.vitruv.framework.vsum.branch.data;

import java.util.List;
import lombok.Getter;

/**
 * Outcome of validating the VSUM before a Git commit.
 *
 * <p>Either valid (commit may proceed) or invalid (commit should be blocked). Both states
 * may carry warnings for non-blocking issues the developer should be aware of.
 *
 * <p>Created exclusively through the static factory methods to prevent invalid combinations
 * (e.g. a valid result carrying errors). All instances are immutable and thread-safe.
 */
@Getter
public class ValidationResult {

  private final boolean valid;

  /** Reasons validation failed. Empty when valid. */
  private final List<String> errors;

  /** Non-blocking issues. May be non-empty even when valid. */
  private final List<String> warnings;

  private ValidationResult(boolean valid, List<String> errors, List<String> warnings) {
    this.valid = valid;
    // List.copyOf guarantees immutability regardless of what the caller passes in
    this.errors = List.copyOf(errors);
    this.warnings = List.copyOf(warnings);
  }

  /** Creates a successful result with no errors or warnings. */
  public static ValidationResult success() {
    return new ValidationResult(true, List.of(), List.of());
  }

  /**
   * Creates a successful result with non-blocking warnings.
   *
   * @param warnings warning messages, must not be null.
   */
  public static ValidationResult successWithWarnings(List<String> warnings) {
    return new ValidationResult(true, List.of(), warnings);
  }

  /**
   * Creates a failed result. The commit should be blocked.
   *
   * @param errors reasons validation failed, must not be null.
   */
  public static ValidationResult failure(List<String> errors) {
    return new ValidationResult(false, errors, List.of());
  }

  /**
   * Creates a failed result with both errors and contextual warnings.
   *
   * @param errors reasons validation failed, must not be null.
   * @param warnings additional context, must not be null.
   */
  public static ValidationResult failureWithWarnings(List<String> errors, List<String> warnings) {
    return new ValidationResult(false, errors, warnings);
  }

  /** Returns {@code true} if any warnings are present. */
  public boolean hasWarnings() {
    return !warnings.isEmpty();
  }

  /** Returns {@code true} if any errors are present. */
  public boolean hasErrors() {
    return !errors.isEmpty();
  }

  @Override
  public String toString() {
    var sb = new StringBuilder();
    sb.append("ValidationResult{valid=").append(valid);
    if (hasErrors()) {
      sb.append(", errors=").append(errors.size());
    }
    if (hasWarnings()) {
      sb.append(", warnings=").append(warnings.size());
    }
    sb.append("}");
    return sb.toString();
  }
}
