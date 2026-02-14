package tools.vitruv.framework.vsum.branch.data;

import lombok.Getter;

import java.util.Collections;
import java.util.List;

/**
 * Holds the outcome of validating the VSUM before a Git commit.
 * <p>A result is either valid (the commit may proceed) or invalid (the commit should be blocked). 
 * In both cases, warning messages may be present to inform the developer of non-blocking issues detected during validation.
 *
 * <p>Instances are created exclusively through the static factory methods 
 * ({@link #success()}, {@link #successWithWarnings}, {@link #failure}, {@link #failureWithWarnings})
 * rather than through a public constructor. 
 * This makes the intent of each result explicit at the call site and prevents invalid combinations such as a valid result that carries errors.
 *
 * <p>All instances are immutable and thread-safe. The error and warning lists are copied on construction and exposed as unmodifiable views.
 */
@Getter
public class ValidationResult {

    /** Whether the VirtualModel passed validation and the commit is allowed to proceed. */
    private final boolean valid;

    /**
     * Error messages that describe why validation failed. Empty when the result is valid.
     * The list is unmodifiable; any attempt to mutate it will throw {@link UnsupportedOperationException}.
     */
    private final List<String> errors;

    /**
     * Warning messages describing non-blocking issues detected during validation.
     * May be non-empty even when the result is valid. The list is unmodifiable.
     */
    private final List<String> warnings;

    /**
     * Private constructor to enforce use of the static factory methods, which guarantee that the valid flag and the presence of errors are always consistent.
     */
    private ValidationResult(boolean valid, List<String> errors, List<String> warnings) {
        // List.copyOf() both copies the content and returns an unmodifiable view, ensuring that callers cannot mutate the lists through the original references they passed in.
        this.valid = valid;
        this.errors = List.copyOf(errors);
        this.warnings = List.copyOf(warnings);
    }

    /**
     * Creates a successful validation result with no errors or warnings.
     * @return a valid result with empty error and warning lists.
     */
    public static ValidationResult success() {
        return new ValidationResult(true, Collections.emptyList(), Collections.emptyList());
    }

    /**
     * Creates a successful validation result with non-blocking warnings. The VirtualModel is consistent enough to allow the commit,
     * but the developer should be informed of the issues described in the warning messages.
     * @param warnings warning messages describing non-blocking issues. must not be null.
     * @return a valid result with the given warnings and no errors.
     * @throws NullPointerException if {@code warnings} is null.
     */
    public static ValidationResult successWithWarnings(List<String> warnings) {
        return new ValidationResult(true, Collections.emptyList(), warnings);
    }

    /**
     * Creates a failed validation result. The commit should be blocked until the issues described in the error messages are resolved.
     * @param errors error messages describing why validation failed, must not be null.
     * @return an invalid result with the given errors and no warnings.
     * @throws NullPointerException if {@code errors} is null.
     */
    public static ValidationResult failure(List<String> errors) {
        return new ValidationResult(false, errors, Collections.emptyList());
    }

    /**
     * Creates a failed validation result with both errors and warnings. The commit should be blocked,
     * and the warnings provide additional context about related issues.
     * @param errors   error messages describing why validation failed, must not be null.
     * @param warnings additional warning messages, must not be null.
     * @return an invalid result with the given errors and warnings.
     * @throws NullPointerException if either {@code errors} or {@code warnings} is null.
     */
    public static ValidationResult failureWithWarnings(List<String> errors, List<String> warnings) {
        return new ValidationResult(false, errors, warnings);
    }

    /**
     * Returns true if any warning messages are present. A result can have warnings regardless of whether it is valid or invalid.
     */
    public boolean hasWarnings() {
        return !warnings.isEmpty();
    }

    /**
     * Returns true if any error messages are present. An invalid result always has at least one error
     * A valid result never has errors.
     */
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