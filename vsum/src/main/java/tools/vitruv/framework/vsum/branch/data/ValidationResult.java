package tools.vitruv.framework.vsum.branch.data;

import lombok.Getter;
import java.util.Collections;
import java.util.List;

/**
 * Represents the result of validating a Vsum before a Git commit.
 * Contains whether the validation passed and any error or warning messages.
 *
 * <p>This class is immutable and thread-safe.
 */
@Getter
public class ValidationResult {

    /**
     * -- GETTER --
     *
     */
    private final boolean valid;
    /**
     * -- GETTER --
     *
     */
    private final List<String> errors;
    /**
     * -- GETTER --
     *
     */
    private final List<String> warnings;

    /**
     * Private constructor to enforce use of factory methods.
     */
    private ValidationResult(boolean valid, List<String> errors, List<String> warnings) {
        this.valid = valid;
        this.errors = List.copyOf(errors);
        this.warnings = List.copyOf(warnings);
    }

    /**
     * Creates a successful validation result with no errors or warnings.
     *
     * @return a valid result
     */
    public static ValidationResult success() {
        return new ValidationResult(true, Collections.emptyList(), Collections.emptyList());
    }

    /**
     * Creates a successful validation result with warnings.
     *
     * @param warnings warning messages (V-SUM is valid but has issues)
     * @return a valid result with warnings
     */
    public static ValidationResult successWithWarnings(List<String> warnings) {
        return new ValidationResult(true, Collections.emptyList(), warnings);
    }

    /**
     * Creates a failed validation result with errors.
     *
     * @param errors error messages explaining why validation failed
     * @return an invalid result
     */
    public static ValidationResult failure(List<String> errors) {
        return new ValidationResult(false, errors, Collections.emptyList());
    }

    /**
     * Creates a failed validation result with both errors and warnings.
     *
     * @param errors error messages explaining why validation failed
     * @param warnings additional warning messages
     * @return an invalid result with warnings
     */
    public static ValidationResult failureWithWarnings(List<String> errors, List<String> warnings) {
        return new ValidationResult(false, errors, warnings);
    }

    /**
     * @return true if there are any warnings
     */
    public boolean hasWarnings() {
        return !warnings.isEmpty();
    }

    /**
     * @return true if there are any errors
     */
    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
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