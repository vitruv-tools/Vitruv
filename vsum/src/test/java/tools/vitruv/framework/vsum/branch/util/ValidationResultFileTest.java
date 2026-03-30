package tools.vitruv.framework.vsum.branch.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import tools.vitruv.framework.vsum.branch.data.ValidationResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link ValidationResultFile}.
 *
 * <p>The tests cover all four validation outcomes
 * (success, success-with-warnings, failure, failure-with-warnings),
 * the JSON round-trip for each outcome, file lifecycle operations (create, overwrite, exists, delete),
 * and isolation between concurrent requests using different request identifiers.
 */
class ValidationResultFileTest {

    private static final String REQUEST_ID = "test-request-uuid-1234";

    /**
     * Verifies that writing a result creates both the text file and the JSON file,
     * and also creates the parent {@code .vitruvius} directory automatically.
     * Both aspects are tested together because they are part of the same single write operation.
     */
    @Test
    @DisplayName("Writes both text and JSON files and creates the parent directory if needed")
    void writeResultCreatesBothFilesAndParentDirectory(@TempDir Path tempDir) throws IOException {
        var resultFile = new ValidationResultFile(tempDir);
        // the .vitruvius directory must not exist before the first write.
        assertFalse(Files.exists(tempDir.resolve(".vitruvius")));

        resultFile.writeResult(ValidationResult.success(), REQUEST_ID);

        // the directory and both result files must be present after to write.
        assertTrue(Files.isDirectory(tempDir.resolve(".vitruvius")),
                "parent directory must be created automatically");
        assertTrue(Files.exists(resultFile.getTextResultPath(REQUEST_ID)),
                "text result file must exist after writing");
        assertTrue(Files.exists(resultFile.getJsonResultPath(REQUEST_ID)),
                "JSON result file must exist after writing");
    }

    /**
     * Verifies that writing a second result for the same request identifier overwrites the previous files.
     * This is the expected behaviour when a validation is retried.
     */
    @Test
    @DisplayName("Overwrites existing result files when written again for the same request identifier")
    void writeResultOverwritesExistingFiles(@TempDir Path tempDir) throws IOException {
        var resultFile = new ValidationResultFile(tempDir);

        resultFile.writeResult(ValidationResult.success(), REQUEST_ID);
        String firstContent = Files.readString(resultFile.getTextResultPath(REQUEST_ID));

        // write a different result for the same request identifier.
        resultFile.writeResult(ValidationResult.failure(List.of("Error")), REQUEST_ID);
        String secondContent = Files.readString(resultFile.getTextResultPath(REQUEST_ID));

        assertNotEquals(firstContent, secondContent,
                "the second write must replace the first content");
        assertTrue(secondContent.contains("Error"));
    }

    /**
     * Verifies that two concurrent requests identified by different identifiers
     * each produce their own independent pair of result files,
     * and that deleting one does not affect the other.
     */
    @Test
    @DisplayName("Different request identifiers produce independent result files")
    void differentRequestIdsProduceIndependentFiles(@TempDir Path tempDir) throws IOException {
        var resultFile = new ValidationResultFile(tempDir);

        resultFile.writeResult(ValidationResult.success(), "request-alice");
        resultFile.writeResult(ValidationResult.failure(List.of("Error")), "request-bob");

        // each request must have its own file with the correct content.
        String aliceContent = Files.readString(resultFile.getTextResultPath("request-alice"));
        String bobContent = Files.readString(resultFile.getTextResultPath("request-bob"));
        assertTrue(aliceContent.contains("PASSED"));
        assertTrue(bobContent.contains("FAILED"),
                "bob's result must not be contaminated by alice's successful result");
    }

    /**
     * Verifies that a successful result produces a text file containing a passed indicator
     * and a confirmation that no errors or warnings were found.
     */
    @Test
    @DisplayName("Text file contains a passed indicator for a successful result")
    void textFormatShowsPassedForSuccess(@TempDir Path tempDir) throws IOException {
        var resultFile = new ValidationResultFile(tempDir);
        resultFile.writeResult(ValidationResult.success(), REQUEST_ID);

        String content = Files.readString(resultFile.getTextResultPath(REQUEST_ID));

        assertTrue(content.contains("✓") || content.contains("PASSED") || content.contains("✔"),
                "text must indicate that validation passed");
        assertTrue(content.contains("No errors"), "text must confirm no issues were found");
    }

    /**
     * Verifies that a failed result produces a text file containing a failed indicator
     * and all error messages so the developer knows exactly what to fix.
     */
    @Test
    @DisplayName("Text file contains a failed indicator and all error messages for a failed result")
    void textFormatShowsFailedWithErrors(@TempDir Path tempDir) throws IOException {
        var resultFile = new ValidationResultFile(tempDir);
        resultFile.writeResult(ValidationResult.failure(List.of("Resource not loadable: models/User.xmi",
                "Broken correspondence in User.java")), REQUEST_ID);

        String content = Files.readString(resultFile.getTextResultPath(REQUEST_ID));

        assertTrue(content.contains("✗") || content.contains("FAILED") || content.contains("✘"),
                "text must indicate that validation failed");
        // both error messages must appear so the developer can identify all issues at once.
        assertTrue(content.contains("Resource not loadable"));
        assertTrue(content.contains("Broken correspondence"));
    }

    /**
     * Verifies that a result with warnings includes all warning messages in the text file,
     * regardless of whether validation passed or failed.
     */
    @Test
    @DisplayName("Text file contains all warning messages when warnings are present")
    void textFormatShowsWarnings(@TempDir Path tempDir) throws IOException {
        var resultFile = new ValidationResultFile(tempDir);
        resultFile.writeResult(ValidationResult.successWithWarnings(
                List.of("Model file size exceeds 10MB", "Unused correspondence found")), REQUEST_ID);

        String content = Files.readString(resultFile.getTextResultPath(REQUEST_ID));

        assertTrue(content.contains("Warning")
                || content.contains("⚠"), "text must include a warning indicator");
        assertTrue(content.contains("Model file size"));
        assertTrue(content.contains("Unused correspondence"));
    }

    /**
     * Verifies that a successful result produces a JSON file containing {@code "valid": true}
     * and the standard fields that a future UI component will rely on.
     */
    @Test
    @DisplayName("JSON file contains valid=true and all standard fields for a successful result")
    void jsonFormatContainsValidTrueForSuccess(@TempDir Path tempDir) throws IOException {
        var resultFile = new ValidationResultFile(tempDir);
        resultFile.writeResult(ValidationResult.success(), REQUEST_ID);

        String json = Files.readString(resultFile.getJsonResultPath(REQUEST_ID));

        assertTrue(json.contains("\"valid\""));
        assertTrue(json.contains("true"), "valid must be true for a success result");
        // the errors, warnings, and timestamp fields must always be present for UI parsing.
        assertTrue(json.contains("\"errors\""));
        assertTrue(json.contains("\"warnings\""));
        assertTrue(json.contains("\"timestamp\""));
    }

    /**
     * Verifies that a failed result produces a JSON file containing {@code "valid": false}
     * and all error messages so they can be parsed programmatically.
     */
    @Test
    @DisplayName("JSON file contains valid=false and all error messages for a failed result")
    void jsonFormatContainsValidFalseForFailure(@TempDir Path tempDir) throws IOException {
        var resultFile = new ValidationResultFile(tempDir);
        resultFile.writeResult(ValidationResult.failure(List.of("Error 1", "Error 2")), REQUEST_ID);

        String json = Files.readString(resultFile.getJsonResultPath(REQUEST_ID));

        assertTrue(json.contains("\"valid\""));
        assertTrue(json.contains("false"), "valid must be false for a failure result");
        assertTrue(json.contains("Error 1"));
        assertTrue(json.contains("Error 2"));
    }

    /**
     * Verifies that {@code readResult} returns {@code null} when no result file exists.
     * This is the signal the hook uses to detect that the watcher has not yet finished.
     */
    @Test
    @DisplayName("readResult returns null when no result file exists")
    void readResultReturnsNullWhenFileAbsent(@TempDir Path tempDir) throws IOException {
        var resultFile = new ValidationResultFile(tempDir);
        assertNull(resultFile.readResult(REQUEST_ID));
    }

    /**
     * Verifies that all four factory method outcomes round-trip correctly
     * through the JSON file: success, success-with-warnings, failure, and failure-with-warnings.
     * Each must be reconstructed to an equivalent result so the hook reads exactly what the watcher wrote.
     */
    @Test
    @DisplayName("All four validation outcomes round-trip correctly through the JSON file")
    void allOutcomesRoundTripThroughJsonFile(@TempDir Path tempDir) throws IOException {
        var resultFile = new ValidationResultFile(tempDir);

        // success: valid, no errors, no warnings.
        resultFile.writeResult(ValidationResult.success(), "req-success");
        var readSuccess = resultFile.readResult("req-success");
        assertNotNull(readSuccess);
        assertTrue(readSuccess.isValid());
        assertFalse(readSuccess.hasErrors());
        assertFalse(readSuccess.hasWarnings());

        // success with warnings: valid, no errors, warnings present.
        resultFile.writeResult(ValidationResult.successWithWarnings(List.of("Warning 1")), "req-warn");
        var readWarn = resultFile.readResult("req-warn");
        assertNotNull(readWarn);
        assertTrue(readWarn.isValid());
        assertTrue(readWarn.hasWarnings());
        assertTrue(readWarn.getWarnings().contains("Warning 1"));

        // failure: invalid, errors present, no warnings.
        resultFile.writeResult(ValidationResult.failure(List.of("Error 1", "Error 2")), "req-fail");
        var readFail = resultFile.readResult("req-fail");
        assertNotNull(readFail);
        assertFalse(readFail.isValid());
        assertEquals(2, readFail.getErrors().size());
        assertTrue(readFail.getErrors().contains("Error 1"));
        assertFalse(readFail.hasWarnings(),
                "a failure result without warnings must not gain warnings after round-trip");

        // failure with warnings: invalid, errors and warnings both present.
        resultFile.writeResult(ValidationResult.failureWithWarnings(
                List.of("Error 1"), List.of("Warning 1")), "req-fail-warn");
        var readFailWarn = resultFile.readResult(
                "req-fail-warn");
        assertNotNull(readFailWarn);
        assertFalse(readFailWarn.isValid());
        assertTrue(readFailWarn.hasErrors());
        assertTrue(readFailWarn.hasWarnings(),
                "warnings in a failure-with-warnings result must be preserved through the round-trip");
        assertTrue(readFailWarn.getWarnings().contains("Warning 1"));
    }

    /**
     * Verifies that {@link ValidationResultFile#exists} correctly reflects the file lifecycle:
     * false before creation, true after creation, still true if only one of the two files is present,
     * and false after both are deleted.
     */
    @Test
    @DisplayName("exists reflects the full file lifecycle for a request identifier")
    void existsReflectsFileLifecycle(@TempDir Path tempDir) throws IOException {
        var resultFile = new ValidationResultFile(tempDir);

        assertFalse(resultFile.exists(REQUEST_ID), "must not exist before writing");
        resultFile.writeResult(ValidationResult.success(), REQUEST_ID);
        assertTrue(resultFile.exists(REQUEST_ID), "must exist after writing");

        // delete only the JSON file: the text file alone must still count as existing.
        Files.delete(resultFile.getJsonResultPath(REQUEST_ID));
        assertTrue(resultFile.exists(REQUEST_ID), "must still exist when at least one file is present");

        // delete the text file too: now neither file exists.
        Files.delete(resultFile.getTextResultPath(REQUEST_ID));
        assertFalse(resultFile.exists(REQUEST_ID), "must not exist when both files are deleted");

        // exists for a different request identifier must not be affected.
        assertFalse(resultFile.exists("other-request"),
                "a different request identifier must not appear to exist");
    }

    /**
     * Verifies that deleting a result removes both the text and JSON files,
     * and that deleting a non-existent result does not throw.
     */
    @Test
    @DisplayName("deleteResult removes both result files and is safe when files are absent")
    void deleteResultRemovesBothFilesAndIsSafe(@TempDir Path tempDir) throws IOException {
        var resultFile = new ValidationResultFile(tempDir);

        // deleting before any file exists must not throw.
        assertDoesNotThrow(() -> resultFile.deleteResult(REQUEST_ID));

        resultFile.writeResult(ValidationResult.success(), REQUEST_ID);
        assertTrue(resultFile.exists(REQUEST_ID));

        resultFile.deleteResult(REQUEST_ID);

        // both files must be gone after deletion.
        assertFalse(Files.exists(resultFile.getTextResultPath(REQUEST_ID)));
        assertFalse(Files.exists(resultFile.getJsonResultPath(REQUEST_ID)));
    }

    /**
     * Verifies that deleting the result for one request identifier does not affect
     * the result files of another request identifier.
     */
    @Test
    @DisplayName("deleteResult only removes files for the specified request identifier")
    void deleteResultOnlyRemovesSpecifiedRequest(@TempDir Path tempDir) throws IOException {
        var resultFile = new ValidationResultFile(tempDir);
        resultFile.writeResult(ValidationResult.success(), "request-1");
        resultFile.writeResult(ValidationResult.success(), "request-2");

        resultFile.deleteResult("request-1");

        assertFalse(resultFile.exists("request-1"), "deleted request must not exist");
        assertTrue(resultFile.exists("request-2"),
                "the other request's files must not be affected by deletion of request-1");
    }

    /**
     * Verifies that the text and JSON path methods return the expected filenames under the {@code .vitruvius} directory.
     * These paths are also used by the pre-commit hook and must remain stable.
     */
    @Test
    @DisplayName("Path methods return the correct filenames under .vitruvius")
    void pathMethodsReturnCorrectFilenames(@TempDir Path tempDir) {
        var resultFile = new ValidationResultFile(tempDir);
        var vitruviusDir = tempDir.resolve(".vitruvius");

        assertEquals(vitruviusDir.resolve(
                "validation-result-" + REQUEST_ID), resultFile.getTextResultPath(REQUEST_ID));
        assertEquals(vitruviusDir.resolve(
                "validation-result-" + REQUEST_ID + ".json"), resultFile.getJsonResultPath(REQUEST_ID));
    }

}