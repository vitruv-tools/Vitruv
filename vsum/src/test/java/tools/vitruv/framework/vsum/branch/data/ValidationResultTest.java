package tools.vitruv.framework.vsum.branch.data;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link ValidationResult}.
 * <p>The tests cover each factory method, the predicate methods {@link ValidationResult#hasErrors()} and {@link ValidationResult#hasWarnings()}, 
 * the immutability guarantee stated in the class Javadoc, and the {@code toString} representation used in log output.
 */
class ValidationResultTest {

    /**
     * Verifies that {@link ValidationResult#success()} produces a valid result with no errors and no warnings. 
     * This is the expected outcome when the VirtualModel passes all consistency checks before a commit.
     */
    @Test
    @DisplayName("success() creates a valid result with no errors or warnings")
    void successCreatesValidResult() {
        var result = ValidationResult.success();

        assertTrue(result.isValid(), "a success result must be valid");
        assertFalse(result.hasErrors(), "a success result must not have errors");
        assertFalse(result.hasWarnings(), "a success result must not have warnings");
        assertTrue(result.getErrors().isEmpty());
        assertTrue(result.getWarnings().isEmpty());
    }

    /**
     * Verifies that {@link ValidationResult#successWithWarnings(List)} produces a valid result that carries the provided warnings.
     * The VirtualModel is consistent enough to allow the commit, but the developer must be informed of the non-blocking issues.
     */
    @Test
    @DisplayName("successWithWarnings() creates a valid result that carries the provided warnings")
    void successWithWarningsCreatesValidResult() {
        var result = ValidationResult.successWithWarnings(List.of("Warning 1", "Warning 2"));

        assertTrue(result.isValid(), "a success-with-warnings result must still be valid");
        assertFalse(result.hasErrors());
        assertTrue(result.hasWarnings());
        // both the count and the content must match to confirm the list is preserved exactly.
        assertEquals(2, result.getWarnings().size());
        assertEquals("Warning 1", result.getWarnings().get(0), "warning messages must be preserved in the original order");
    }

    /**
     * Verifies that {@link ValidationResult#failure(List)} produces an invalid result that carries the provided error messages.
     * The commit must be blocked until the errors described in the list are resolved.
     */
    @Test
    @DisplayName("failure() creates an invalid result that carries the provided errors")
    void failureCreatesInvalidResult() {
        var result = ValidationResult.failure(List.of("Error 1", "Error 2"));

        assertFalse(result.isValid(), "a failure result must not be valid");
        assertTrue(result.hasErrors());
        assertFalse(result.hasWarnings(), "a failure result without explicit warnings must not have warnings");
        assertEquals(2, result.getErrors().size());
        assertEquals("Error 1", result.getErrors().get(0), "error messages must be preserved in the original order");
    }

    /**
     * Verifies that {@link ValidationResult#failureWithWarnings(List, List)} produces an invalid result that carries both errors and warnings.
     * Both lists must be stored independently with their correct sizes.
     */
    @Test
    @DisplayName("failureWithWarnings() creates an invalid result with both errors and warnings")
    void failureWithWarningsCreatesInvalidResult() {
        var result = ValidationResult.failureWithWarnings(List.of("Error 1"), List.of("Warning 1", "Warning 2"));

        assertFalse(result.isValid());
        assertTrue(result.hasErrors());
        assertTrue(result.hasWarnings());
        assertEquals(1, result.getErrors().size());
        assertEquals(2, result.getWarnings().size());
    }

    /**
     * Verifies that the error and warning lists returned by the getters are unmodifiable.
     * The class Javadoc states that all instances are immutable, so external code must not be able to mutate the internal state of a result after it is created.
     */
    @Test
    @DisplayName("Error and warning lists are unmodifiable after construction")
    void listsAreUnmodifiable() {
        var result = ValidationResult.failureWithWarnings(List.of("Error 1"), List.of("Warning 1"));

        // attempting to add to either returned list must throw immediately rather than corrupting the internal state of the result.
        assertThrows(UnsupportedOperationException.class, () -> result.getErrors().add("extra error"), "the errors list must not allow external modification");
        assertThrows(UnsupportedOperationException.class, () -> result.getWarnings().add("extra warning"), "the warnings list must not allow external modification");
    }

    /**
     * Verifies that passing a null list to any factory method throws a {@link NullPointerException}.
     * The internal constructor uses {@code List.copyOf()} which rejects null, so callers receive a clear failure immediately rather than a silent empty list or a delayed null pointer elsewhere.
     */
    @Test
    @DisplayName("Factory methods reject null error and warning lists")
    void factoryMethodsRejectNullLists() {
        assertThrows(NullPointerException.class, () -> ValidationResult.failure(null), "null error list must be rejected by failure()");
        assertThrows(NullPointerException.class, () -> ValidationResult.successWithWarnings(null), "null warning list must be rejected by successWithWarnings()");
        assertThrows(NullPointerException.class, () -> ValidationResult.failureWithWarnings(null, List.of()), "null error list must be rejected by failureWithWarnings()");
        assertThrows(NullPointerException.class, () -> ValidationResult.failureWithWarnings(List.of(), null), "null warning list must be rejected by failureWithWarnings()");
    }

    /**
     * Verifies that {@link ValidationResult#toString()} produces a string that includes the validity flag and, when present, the counts of errors and warnings.
     * This output appears in log messages and exception descriptions during pre-commit validation.
     */
    @Test
    @DisplayName("toString includes validity flag and counts of errors and warnings when present")
    void toStringIncludesValidityAndCounts() {
        var success = ValidationResult.success();
        assertTrue(success.toString().contains("valid=true"), "toString must include valid=true for a success result");

        var failure = ValidationResult.failure(List.of("E1", "E2"));
        assertTrue(failure.toString().contains("valid=false"));
        assertTrue(failure.toString().contains("errors=2"), "toString must include the error count for a failure result");
    }
}