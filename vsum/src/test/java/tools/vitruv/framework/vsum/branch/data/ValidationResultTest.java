package tools.vitruv.framework.vsum.branch.data;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link ValidationResult}.
 */
class ValidationResultTest {

    @Test
    @DisplayName("success creates valid result with no errors or warnings")
    void successCreatesValidResult() {
        ValidationResult result = ValidationResult.success();
        assertTrue(result.isValid());
        assertFalse(result.hasErrors());
        assertFalse(result.hasWarnings());
        assertTrue(result.getErrors().isEmpty());
        assertTrue(result.getWarnings().isEmpty());
    }

    @Test
    @DisplayName("successWithWarnings creates valid result with warnings")
    void successWithWarningsCreatesValidResult() {
        List<String> warnings = List.of("Warning 1", "Warning 2");
        ValidationResult result = ValidationResult.successWithWarnings(warnings);
        assertTrue(result.isValid());
        assertFalse(result.hasErrors());
        assertTrue(result.hasWarnings());
        assertEquals(2, result.getWarnings().size());
        assertEquals("Warning 1", result.getWarnings().get(0));
    }

    @Test
    @DisplayName("failure creates invalid result with errors")
    void failureCreatesInvalidResult() {
        List<String> errors = List.of("Error 1", "Error 2");
        ValidationResult result = ValidationResult.failure(errors);
        assertFalse(result.isValid());
        assertTrue(result.hasErrors());
        assertFalse(result.hasWarnings());
        assertEquals(2, result.getErrors().size());
        assertEquals("Error 1", result.getErrors().get(0));
    }

    @Test
    @DisplayName("failureWithWarnings creates invalid result with errors and warnings")
    void failureWithWarningsCreatesInvalidResult() {
        List<String> errors = List.of("Error 1");
        List<String> warnings = List.of("Warning 1", "Warning 2");
        ValidationResult result = ValidationResult.failureWithWarnings(errors, warnings);
        assertFalse(result.isValid());
        assertTrue(result.hasErrors());
        assertTrue(result.hasWarnings());
        assertEquals(1, result.getErrors().size());
        assertEquals(2, result.getWarnings().size());
    }

    @Test
    @DisplayName("toString provides useful output")
    void toStringProvidesUsefulOutput() {
        ValidationResult success = ValidationResult.success();
        assertTrue(success.toString().contains("valid=true"));

        ValidationResult failure = ValidationResult.failure(List.of("E1", "E2"));
        assertTrue(failure.toString().contains("valid=false"));
        assertTrue(failure.toString().contains("errors=2"));
    }
}