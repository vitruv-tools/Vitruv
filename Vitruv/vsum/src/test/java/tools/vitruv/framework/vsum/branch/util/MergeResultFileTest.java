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
 * Unit tests for {@link MergeResultFile}.
 *
 * <p>Tests cover all four validation outcomes (success, success-with-warnings, failure,
 * failure-with-warnings), the JSON round-trip for each outcome, metadata writing and
 * reading, file lifecycle operations (create, overwrite, exists, delete),
 * and isolation between concurrent requests using different request identifiers.
 */
class MergeResultFileTest {

    private static final String REQUEST_ID = "test-merge-request-1234";
    private static final String SHA = "deadbeef0000000000000000000000000000abcd";

    @Test
    @DisplayName("Writes both text and JSON files and creates the parent directory if needed")
    void writeResultCreatesBothFilesAndParentDirectory(@TempDir Path tempDir) throws IOException {
        var resultFile = new MergeResultFile(tempDir);

        assertFalse(Files.exists(tempDir.resolve(".vitruvius")));

        resultFile.writeResult(ValidationResult.success(), REQUEST_ID);

        assertTrue(Files.isDirectory(tempDir.resolve(".vitruvius")),
                "parent directory must be created automatically");
        assertTrue(Files.exists(resultFile.getTextResultPath(REQUEST_ID)),
                "text result file must exist after writing");
        assertTrue(Files.exists(resultFile.getJsonResultPath(REQUEST_ID)),
                "JSON result file must exist after writing");
    }

    @Test
    @DisplayName("Overwrites existing result files when written again for the same request identifier")
    void writeResultOverwritesExistingFiles(@TempDir Path tempDir) throws IOException {
        var resultFile = new MergeResultFile(tempDir);

        resultFile.writeResult(ValidationResult.success(), REQUEST_ID);
        String firstContent = Files.readString(resultFile.getTextResultPath(REQUEST_ID));

        resultFile.writeResult(ValidationResult.failure(List.of("Consistency violation")), REQUEST_ID);
        String secondContent = Files.readString(resultFile.getTextResultPath(REQUEST_ID));

        assertNotEquals(firstContent, secondContent, "the second write must replace the first content");
        assertTrue(secondContent.contains("Consistency violation"));
    }

    @Test
    @DisplayName("Different request identifiers produce independent result files")
    void differentRequestIdsProduceIndependentFiles(@TempDir Path tempDir) throws IOException {
        var resultFile = new MergeResultFile(tempDir);

        resultFile.writeResult(ValidationResult.success(), "request-alice");
        resultFile.writeResult(ValidationResult.failure(List.of("Error")), "request-bob");

        String aliceContent = Files.readString(resultFile.getTextResultPath("request-alice"));
        String bobContent = Files.readString(resultFile.getTextResultPath("request-bob"));
        assertTrue(aliceContent.contains("PASSED"));
        assertTrue(bobContent.contains("FAILED"),
                "bob's result must not be contaminated by alice's successful result");
    }

    @Test
    @DisplayName("Text file contains a passed indicator for a successful result")
    void textFormatShowsPassedForSuccess(@TempDir Path tempDir) throws IOException {
        var resultFile = new MergeResultFile(tempDir);
        resultFile.writeResult(ValidationResult.success(), REQUEST_ID);

        String content = Files.readString(resultFile.getTextResultPath(REQUEST_ID));

        assertTrue(content.contains("PASSED"), "text must indicate that post-merge validation passed");
        assertTrue(content.contains("No inconsistencies or warnings found"),
                "text must confirm no issues were found");
    }

    @Test
    @DisplayName("Text file contains a failed indicator and all error messages for a failed result")
    void textFormatShowsFailedWithErrors(@TempDir Path tempDir) throws IOException {
        var resultFile = new MergeResultFile(tempDir);
        resultFile.writeResult(ValidationResult.failure(
                List.of("Unresolved reference in merged model", "Broken containment hierarchy")),
                REQUEST_ID);

        String content = Files.readString(resultFile.getTextResultPath(REQUEST_ID));

        assertTrue(content.contains("FAILED"), "text must indicate that post-merge validation failed");
        assertTrue(content.contains("Unresolved reference in merged model"));
        assertTrue(content.contains("Broken containment hierarchy"));
    }

    @Test
    @DisplayName("Text file contains all warning messages when warnings are present")
    void textFormatShowsWarnings(@TempDir Path tempDir) throws IOException {
        var resultFile = new MergeResultFile(tempDir);
        resultFile.writeResult(ValidationResult.successWithWarnings(
                List.of("Stale correspondence detected", "Non-critical model deviation")),
                REQUEST_ID);

        String content = Files.readString(resultFile.getTextResultPath(REQUEST_ID));

        assertTrue(content.contains("Warning"), "text must include a warning section");
        assertTrue(content.contains("Stale correspondence detected"));
        assertTrue(content.contains("Non-critical model deviation"));
    }

    @Test
    @DisplayName("JSON file contains valid=true and all standard fields for a successful result")
    void jsonFormatContainsValidTrueForSuccess(@TempDir Path tempDir) throws IOException {
        var resultFile = new MergeResultFile(tempDir);
        resultFile.writeResult(ValidationResult.success(), REQUEST_ID);

        String json = Files.readString(resultFile.getJsonResultPath(REQUEST_ID));

        assertTrue(json.contains("\"valid\""));
        assertTrue(json.contains("true"), "valid must be true for a success result");
        assertTrue(json.contains("\"errors\""));
        assertTrue(json.contains("\"warnings\""));
        assertTrue(json.contains("\"timestamp\""));
    }

    @Test
    @DisplayName("JSON file contains valid=false and all error messages for a failed result")
    void jsonFormatContainsValidFalseForFailure(@TempDir Path tempDir) throws IOException {
        var resultFile = new MergeResultFile(tempDir);
        resultFile.writeResult(
                ValidationResult.failure(List.of("Error A", "Error B")), REQUEST_ID);

        String json = Files.readString(resultFile.getJsonResultPath(REQUEST_ID));

        assertTrue(json.contains("\"valid\""));
        assertTrue(json.contains("false"), "valid must be false for a failure result");
        assertTrue(json.contains("Error A"));
        assertTrue(json.contains("Error B"));
    }

    @Test
    @DisplayName("readResult returns null when no result file exists")
    void readResultReturnsNullWhenFileAbsent(@TempDir Path tempDir) throws IOException {
        var resultFile = new MergeResultFile(tempDir);
        assertNull(resultFile.readResult(REQUEST_ID));
    }

    @Test
    @DisplayName("All four validation outcomes round-trip correctly through the JSON file")
    void allOutcomesRoundTripThroughJsonFile(@TempDir Path tempDir) throws IOException {
        var resultFile = new MergeResultFile(tempDir);

        // success: valid, no errors, no warnings.
        resultFile.writeResult(ValidationResult.success(), "req-success");
        var readSuccess = resultFile.readResult("req-success");
        assertNotNull(readSuccess);
        assertTrue(readSuccess.isValid());
        assertFalse(readSuccess.hasErrors());
        assertFalse(readSuccess.hasWarnings());

        // success with warnings: valid, no errors, warnings present.
        resultFile.writeResult(
                ValidationResult.successWithWarnings(List.of("Merge warning 1")), "req-warn");
        var readWarn = resultFile.readResult("req-warn");
        assertNotNull(readWarn);
        assertTrue(readWarn.isValid());
        assertTrue(readWarn.hasWarnings());
        assertTrue(readWarn.getWarnings().contains("Merge warning 1"));

        // failure: invalid, errors present, no warnings.
        resultFile.writeResult(
                ValidationResult.failure(List.of("Error 1", "Error 2")), "req-fail");
        var readFail = resultFile.readResult("req-fail");
        assertNotNull(readFail);
        assertFalse(readFail.isValid());
        assertEquals(2, readFail.getErrors().size());
        assertTrue(readFail.getErrors().contains("Error 1"));
        assertFalse(readFail.hasWarnings(),
                "a failure result without warnings must not gain warnings after round-trip");

        // failure with warnings: invalid, errors and warnings both present.
        resultFile.writeResult(
                ValidationResult.failureWithWarnings(
                        List.of("Error 1"), List.of("Warning 1")), "req-fail-warn");
        var readFailWarn = resultFile.readResult("req-fail-warn");
        assertNotNull(readFailWarn);
        assertFalse(readFailWarn.isValid());
        assertTrue(readFailWarn.hasErrors());
        assertTrue(readFailWarn.hasWarnings(),
                "warnings in a failure-with-warnings result must be preserved through the round-trip");
        assertTrue(readFailWarn.getWarnings().contains("Warning 1"));
    }

    @Test
    @DisplayName("exists reflects the full file lifecycle for a request identifier")
    void existsReflectsFileLifecycle(@TempDir Path tempDir) throws IOException {
        var resultFile = new MergeResultFile(tempDir);

        assertFalse(resultFile.exists(REQUEST_ID), "must not exist before writing");
        resultFile.writeResult(ValidationResult.success(), REQUEST_ID);
        assertTrue(resultFile.exists(REQUEST_ID), "must exist after writing");

        // delete only the JSON file: the text file alone must still count as existing.
        Files.delete(resultFile.getJsonResultPath(REQUEST_ID));
        assertTrue(resultFile.exists(REQUEST_ID), "must still exist when at least one file is present");

        // delete the text file too: now neither file exists.
        Files.delete(resultFile.getTextResultPath(REQUEST_ID));
        assertFalse(resultFile.exists(REQUEST_ID), "must not exist when both files are deleted");

        assertFalse(resultFile.exists("other-request"),
                "a different request identifier must not appear to exist");
    }

    @Test
    @DisplayName("deleteResult removes both result files and is safe when files are absent")
    void deleteResultRemovesBothFilesAndIsSafe(@TempDir Path tempDir) throws IOException {
        var resultFile = new MergeResultFile(tempDir);

        assertDoesNotThrow(() -> resultFile.deleteResult(REQUEST_ID));

        resultFile.writeResult(ValidationResult.success(), REQUEST_ID);
        assertTrue(resultFile.exists(REQUEST_ID));

        resultFile.deleteResult(REQUEST_ID);

        assertFalse(Files.exists(resultFile.getTextResultPath(REQUEST_ID)));
        assertFalse(Files.exists(resultFile.getJsonResultPath(REQUEST_ID)));
    }

    @Test
    @DisplayName("deleteResult only removes files for the specified request identifier")
    void deleteResultOnlyRemovesSpecifiedRequest(@TempDir Path tempDir) throws IOException {
        var resultFile = new MergeResultFile(tempDir);
        resultFile.writeResult(ValidationResult.success(), "request-1");
        resultFile.writeResult(ValidationResult.success(), "request-2");

        resultFile.deleteResult("request-1");

        assertFalse(resultFile.exists("request-1"), "deleted request must not exist");
        assertTrue(resultFile.exists("request-2"),
                "the other request's files must not be affected by deletion of request-1");
    }

    @Test
    @DisplayName("Path methods return the correct filenames under .vitruvius")
    void pathMethodsReturnCorrectFilenames(@TempDir Path tempDir) {
        var resultFile = new MergeResultFile(tempDir);
        var vitruviusDir = tempDir.resolve(".vitruvius");

        assertEquals(vitruviusDir.resolve("merge-result-" + REQUEST_ID),
                resultFile.getTextResultPath(REQUEST_ID));
        assertEquals(vitruviusDir.resolve("merge-result-" + REQUEST_ID + ".json"),
                resultFile.getJsonResultPath(REQUEST_ID));
    }

    @Test
    @DisplayName("writeMetadata creates a metadata file under .vitruvius/merges and creates directories")
    void writeMetadataCreatesMetadataFile(@TempDir Path tempDir) throws IOException {
        var resultFile = new MergeResultFile(tempDir);

        assertFalse(Files.exists(tempDir.resolve(".vitruvius").resolve("merges")));

        resultFile.writeMetadata(SHA, SOURCE_BRANCH, TARGET_BRANCH,
                ValidationResult.success(), List.of());

        assertTrue(Files.exists(resultFile.getMetadataPath(SHA)),
                "metadata file must exist after writeMetadata");
    }

    @Test
    @DisplayName("readMetadata returns null when no metadata file exists")
    void readMetadataReturnsNullWhenAbsent(@TempDir Path tempDir) throws IOException {
        var resultFile = new MergeResultFile(tempDir);
        assertNull(resultFile.readMetadata(SHA));
    }

    @Test
    @DisplayName("metadataExists reflects whether a metadata file has been written")
    void metadataExistsReflectsFilePresence(@TempDir Path tempDir) throws IOException {
        var resultFile = new MergeResultFile(tempDir);

        assertFalse(resultFile.metadataExists(SHA), "must not exist before writing");
        resultFile.writeMetadata(SHA, SOURCE_BRANCH, TARGET_BRANCH,
                ValidationResult.success(), List.of());
        assertTrue(resultFile.metadataExists(SHA), "must exist after writing");
    }

    @Test
    @DisplayName("getMetadataPath returns the correct path under .vitruvius/merges")
    void getMetadataPathReturnsCorrectPath(@TempDir Path tempDir) {
        var resultFile = new MergeResultFile(tempDir);
        assertEquals(
                tempDir.resolve(".vitruvius").resolve("merges").resolve(SHA + ".metadata"),
                resultFile.getMetadataPath(SHA));
    }

    @Test
    @DisplayName("readMetadata round-trips the key metadata fields written by writeMetadata")
    void readMetadataRoundTripsKeyFields(@TempDir Path tempDir) throws IOException {
        var resultFile = new MergeResultFile(tempDir);
        var result = ValidationResult.failure(List.of("Conflict in model.xmi"));

        resultFile.writeMetadata(SHA, SOURCE_BRANCH, TARGET_BRANCH, result,
                List.of("conflict.xmi"));

        var metadata = resultFile.readMetadata(SHA);

        assertNotNull(metadata);
        assertEquals(SHA, metadata.get("mergeCommitSha"));
        assertEquals(SOURCE_BRANCH, metadata.get("sourceBranch"));
        assertEquals(TARGET_BRANCH, metadata.get("targetBranch"));
        assertEquals(false, metadata.get("valid"));
        @SuppressWarnings("unchecked")
        var errors = (java.util.List<String>) metadata.get("errors");
        assertTrue(errors.contains("Conflict in model.xmi"));
        @SuppressWarnings("unchecked")
        var conflictingFiles = (java.util.List<String>) metadata.get("conflictingFiles");
        assertTrue(conflictingFiles.contains("conflict.xmi"));
    }

    // ── constants ─────────────────────────────────────────────────────────────

    private static final String SOURCE_BRANCH = "feature-x";
    private static final String TARGET_BRANCH = "main";
}
