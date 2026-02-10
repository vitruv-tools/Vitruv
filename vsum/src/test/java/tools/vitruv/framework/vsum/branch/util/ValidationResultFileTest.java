package tools.vitruv.framework.vsum.branch.util;

import tools.vitruv.framework.vsum.branch.data.ValidationResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link ValidationResultFile}.
 */
@DisplayName("ValidationResultFile Tests")
class ValidationResultFileTest {

    private static final String REQUEST_ID = "test-request-uuid-1234";

    @Test
    @DisplayName("Writing validation result should create both text and JSON files with request ID")
    void testWriteResult_CreatesBothFiles(@TempDir Path tempDir) throws IOException {
        ValidationResultFile resultFile = new ValidationResultFile(tempDir);
        ValidationResult result = ValidationResult.success();

        resultFile.writeResult(result, REQUEST_ID);

        assertTrue(Files.exists(resultFile.getTextResultPath(REQUEST_ID)));
        assertTrue(Files.exists(resultFile.getJsonResultPath(REQUEST_ID)));
    }

    @Test
    @DisplayName("Text format should show PASSED for successful validation")
    void testWriteResult_SuccessTextFormat(@TempDir Path tempDir) throws IOException {
        ValidationResultFile resultFile = new ValidationResultFile(tempDir);
        ValidationResult result = ValidationResult.success();

        resultFile.writeResult(result, REQUEST_ID);

        String content = Files.readString(resultFile.getTextResultPath(REQUEST_ID));
        assertTrue(content.contains("✓") || content.contains("PASSED") || content.contains("✔"));
        assertTrue(content.contains("No errors"));
    }

    @Test
    @DisplayName("Text format should show FAILED with error messages for failed validation")
    void testWriteResult_FailureTextFormat(@TempDir Path tempDir) throws IOException {
        ValidationResultFile resultFile = new ValidationResultFile(tempDir);
        ValidationResult result = ValidationResult.failure(List.of("Resource not loadable: models/User.xmi", "Broken correspondence in User.java"));

        resultFile.writeResult(result, REQUEST_ID);

        String content = Files.readString(resultFile.getTextResultPath(REQUEST_ID));
        assertTrue(content.contains("✗") || content.contains("FAILED") || content.contains("✘"));
        assertTrue(content.contains("Resource not loadable"));
        assertTrue(content.contains("Broken correspondence"));
    }

    @Test
    @DisplayName("Text format should display warnings")
    void testWriteResult_WithWarningsTextFormat(@TempDir Path tempDir) throws IOException {
        ValidationResultFile resultFile = new ValidationResultFile(tempDir);
        ValidationResult result = ValidationResult.successWithWarnings(List.of("Model file size exceeds 10MB", "Unused correspondence found"));

        resultFile.writeResult(result, REQUEST_ID);

        String content = Files.readString(resultFile.getTextResultPath(REQUEST_ID));
        assertTrue(content.contains("Warning") || content.contains("⚠"));
        assertTrue(content.contains("Model file size"));
        assertTrue(content.contains("Unused correspondence"));
    }

    @Test
    @DisplayName("JSON format should contain valid=true for successful validation")
    void testWriteResult_SuccessJsonFormat(@TempDir Path tempDir) throws IOException {
        ValidationResultFile resultFile = new ValidationResultFile(tempDir);
        ValidationResult result = ValidationResult.success();

        resultFile.writeResult(result, REQUEST_ID);

        String json = Files.readString(resultFile.getJsonResultPath(REQUEST_ID));
        assertTrue(json.contains("\"valid\""));
        assertTrue(json.contains("true"));
        assertTrue(json.contains("\"errors\""));
        assertTrue(json.contains("\"warnings\""));
        assertTrue(json.contains("\"timestamp\""));
    }

    @Test
    @DisplayName("JSON format should contain valid=false and error list for failed validation")
    void testWriteResult_FailureJsonFormat(@TempDir Path tempDir) throws IOException {
        ValidationResultFile resultFile = new ValidationResultFile(tempDir);
        ValidationResult result = ValidationResult.failure(List.of("Error 1", "Error 2"));

        resultFile.writeResult(result, REQUEST_ID);

        String json = Files.readString(resultFile.getJsonResultPath(REQUEST_ID));
        assertTrue(json.contains("\"valid\""));
        assertTrue(json.contains("false"));
        assertTrue(json.contains("Error 1"));
        assertTrue(json.contains("Error 2"));
    }

    @Test
    @DisplayName("Writing result should create parent directory if it doesn't exist")
    void testWriteResult_CreatesParentDirectory(@TempDir Path tempDir) throws IOException {
        Path vitruviusDir = tempDir.resolve(".vitruvius");
        assertFalse(Files.exists(vitruviusDir));

        ValidationResultFile resultFile = new ValidationResultFile(tempDir);
        resultFile.writeResult(ValidationResult.success(), REQUEST_ID);

        assertTrue(Files.exists(vitruviusDir));
        assertTrue(Files.isDirectory(vitruviusDir));
    }

    @Test
    @DisplayName("Writing result should overwrite existing files for same request ID")
    void testWriteResult_OverwritesExistingFile(@TempDir Path tempDir) throws IOException {
        ValidationResultFile resultFile = new ValidationResultFile(tempDir);

        resultFile.writeResult(ValidationResult.success(), REQUEST_ID);
        String firstContent = Files.readString(resultFile.getTextResultPath(REQUEST_ID));

        resultFile.writeResult(ValidationResult.failure(List.of("Error")), REQUEST_ID);
        String secondContent = Files.readString(resultFile.getTextResultPath(REQUEST_ID));

        assertNotEquals(firstContent, secondContent);
        assertTrue(secondContent.contains("Error"));
    }

    @Test
    @DisplayName("Different request IDs should create separate files")
    void testWriteResult_DifferentRequestIds(@TempDir Path tempDir) throws IOException {
        ValidationResultFile resultFile = new ValidationResultFile(tempDir);

        String requestId1 = "request-alice";
        String requestId2 = "request-bob";

        resultFile.writeResult(ValidationResult.success(), requestId1);
        resultFile.writeResult(ValidationResult.failure(List.of("Error")), requestId2);

        // Both files should exist
        assertTrue(Files.exists(resultFile.getTextResultPath(requestId1)));
        assertTrue(Files.exists(resultFile.getTextResultPath(requestId2)));

        // Contents should be different
        String content1 = Files.readString(resultFile.getTextResultPath(requestId1));
        String content2 = Files.readString(resultFile.getTextResultPath(requestId2));

        assertTrue(content1.contains("PASSED"));
        assertTrue(content2.contains("FAILED"));
    }

    @Test
    @DisplayName("Delete should remove both result files for specific request ID")
    void testDeleteResult_RemovesBothFiles(@TempDir Path tempDir) throws IOException {
        ValidationResultFile resultFile = new ValidationResultFile(tempDir);
        resultFile.writeResult(ValidationResult.success(), REQUEST_ID);

        assertTrue(resultFile.exists(REQUEST_ID));

        resultFile.deleteResult(REQUEST_ID);

        assertFalse(Files.exists(resultFile.getTextResultPath(REQUEST_ID)));
        assertFalse(Files.exists(resultFile.getJsonResultPath(REQUEST_ID)));
        assertFalse(resultFile.exists(REQUEST_ID));
    }

    @Test
    @DisplayName("Delete should not throw exception when files don't exist")
    void testDeleteResult_WhenNotExist(@TempDir Path tempDir) {
        ValidationResultFile resultFile = new ValidationResultFile(tempDir);
        assertDoesNotThrow(() -> resultFile.deleteResult(REQUEST_ID));
    }

    @Test
    @DisplayName("Delete should only remove files for specified request ID")
    void testDeleteResult_OnlyDeletesSpecifiedRequest(@TempDir Path tempDir) throws IOException {
        ValidationResultFile resultFile = new ValidationResultFile(tempDir);

        String requestId1 = "request-1";
        String requestId2 = "request-2";

        resultFile.writeResult(ValidationResult.success(), requestId1);
        resultFile.writeResult(ValidationResult.success(), requestId2);

        resultFile.deleteResult(requestId1);

        assertFalse(resultFile.exists(requestId1));
        assertTrue(resultFile.exists(requestId2), "Other request's files should not be deleted");
    }

    @Test
    @DisplayName("Exists should return true when either file exists for request ID")
    void testExists_ReturnsTrueWhenEitherFileExists(@TempDir Path tempDir) throws IOException {
        ValidationResultFile resultFile = new ValidationResultFile(tempDir);

        assertFalse(resultFile.exists(REQUEST_ID));

        resultFile.writeResult(ValidationResult.success(), REQUEST_ID);
        assertTrue(resultFile.exists(REQUEST_ID));

        // Delete JSON file but keep text file
        Files.delete(resultFile.getJsonResultPath(REQUEST_ID));
        assertTrue(resultFile.exists(REQUEST_ID), "Should still return true when text file exists");
    }

    @Test
    @DisplayName("Exists should return false for different request ID")
    void testExists_DifferentRequestId(@TempDir Path tempDir) throws IOException {
        ValidationResultFile resultFile = new ValidationResultFile(tempDir);

        resultFile.writeResult(ValidationResult.success(), "request-alice");

        assertTrue(resultFile.exists("request-alice"));
        assertFalse(resultFile.exists("request-bob"));
    }

    @Test
    @DisplayName("Text result path should include request ID in filename")
    void testGetTextResultPath(@TempDir Path tempDir) {
        ValidationResultFile resultFile = new ValidationResultFile(tempDir);

        Path expected = tempDir.resolve(".vitruvius").resolve("validation-result-" + REQUEST_ID);
        assertEquals(expected, resultFile.getTextResultPath(REQUEST_ID));
    }

    @Test
    @DisplayName("JSON result path should include request ID in filename")
    void testGetJsonResultPath(@TempDir Path tempDir) {
        ValidationResultFile resultFile = new ValidationResultFile(tempDir);

        Path expected = tempDir.resolve(".vitruvius").resolve("validation-result-" + REQUEST_ID + ".json");
        assertEquals(expected, resultFile.getJsonResultPath(REQUEST_ID));
    }

    @Test
    @DisplayName("Read result should return null when file doesn't exist")
    void testReadResult_WhenNotExists(@TempDir Path tempDir) throws IOException {
        ValidationResultFile resultFile = new ValidationResultFile(tempDir);

        ValidationResult result = resultFile.readResult(REQUEST_ID);

        assertNull(result);
    }

    @Test
    @DisplayName("Read result should return success result correctly")
    void testReadResult_Success(@TempDir Path tempDir) throws IOException {
        ValidationResultFile resultFile = new ValidationResultFile(tempDir);
        ValidationResult original = ValidationResult.success();

        resultFile.writeResult(original, REQUEST_ID);
        ValidationResult read = resultFile.readResult(REQUEST_ID);

        assertNotNull(read);
        assertTrue(read.isValid());
        assertFalse(read.hasErrors());
        assertFalse(read.hasWarnings());
    }

    @Test
    @DisplayName("Read result should return failure result with errors correctly")
    void testReadResult_Failure(@TempDir Path tempDir) throws IOException {
        ValidationResultFile resultFile = new ValidationResultFile(tempDir);
        ValidationResult original = ValidationResult.failure(List.of("Error 1", "Error 2"));

        resultFile.writeResult(original, REQUEST_ID);
        ValidationResult read = resultFile.readResult(REQUEST_ID);

        assertNotNull(read);
        assertFalse(read.isValid());
        assertTrue(read.hasErrors());
        assertEquals(2, read.getErrors().size());
        assertTrue(read.getErrors().contains("Error 1"));
        assertTrue(read.getErrors().contains("Error 2"));
    }

    @Test
    @DisplayName("Read result should return success with warnings correctly")
    void testReadResult_SuccessWithWarnings(@TempDir Path tempDir) throws IOException {
        ValidationResultFile resultFile = new ValidationResultFile(tempDir);
        ValidationResult original = ValidationResult.successWithWarnings(List.of("Warning 1"));

        resultFile.writeResult(original, REQUEST_ID);
        ValidationResult read = resultFile.readResult(REQUEST_ID);

        assertNotNull(read);
        assertTrue(read.isValid());
        assertTrue(read.hasWarnings());
        assertEquals(1, read.getWarnings().size());
        assertTrue(read.getWarnings().contains("Warning 1"));
    }

    @Test
    @DisplayName("Deprecated writeResult without request ID should log warning")
    @SuppressWarnings("deprecation")
    void testDeprecatedWriteResult(@TempDir Path tempDir) throws IOException {
        ValidationResultFile resultFile = new ValidationResultFile(tempDir);

        // Should not throw, but writes to "legacy" file
        assertDoesNotThrow(() -> resultFile.writeResult(ValidationResult.success()));

        // Should be written to "legacy" file
        assertTrue(resultFile.exists("legacy"));
    }

    @Test
    @DisplayName("Deprecated readResult without request ID should throw exception")
    @SuppressWarnings("deprecation")
    void testDeprecatedReadResult(@TempDir Path tempDir) {
        ValidationResultFile resultFile = new ValidationResultFile(tempDir);

        assertThrows(UnsupportedOperationException.class, resultFile::readResult);
    }
}