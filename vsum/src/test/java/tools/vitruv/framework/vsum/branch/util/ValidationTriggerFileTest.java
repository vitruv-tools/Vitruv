package tools.vitruv.framework.vsum.branch.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link ValidationTriggerFile}.
 */
@DisplayName("ValidationTriggerFile Tests")
class ValidationTriggerFileTest {

    private static final String COMMIT_SHA = "abc1234567890abcdef1234567890abcdef12345";
    private static final String BRANCH = "feature-ui";

    @Test
    @DisplayName("Creating trigger should write file with correct format including request ID")
    void testCreateTrigger_FileContent(@TempDir Path tempDir) throws IOException {
        ValidationTriggerFile triggerFile = new ValidationTriggerFile(tempDir);

        String requestId = triggerFile.createTrigger(COMMIT_SHA, BRANCH);

        // Verify request ID is returned
        assertNotNull(requestId);
        assertFalse(requestId.isEmpty());

        assertTrue(triggerFile.exists());
        String content = Files.readString(triggerFile.getTriggerPath());

        // File format should be: commitSha|branch|requestId|timestamp
        String[] parts = content.split("\\|");
        assertEquals(4, parts.length, "Trigger file should have 4 parts");
        assertEquals(COMMIT_SHA, parts[0]);
        assertEquals(BRANCH, parts[1]);
        assertEquals(requestId, parts[2]);
        // Timestamp should be parseable as long
        assertDoesNotThrow(() -> Long.parseLong(parts[3]));
    }

    @Test
    @DisplayName("Request ID should be unique for each trigger")
    void testCreateTrigger_UniqueRequestIds(@TempDir Path tempDir) throws IOException {
        ValidationTriggerFile triggerFile = new ValidationTriggerFile(tempDir);

        String requestId1 = triggerFile.createTrigger(COMMIT_SHA, "main");
        // Delete trigger to allow creating another
        triggerFile.checkAndClearTrigger();

        String requestId2 = triggerFile.createTrigger(COMMIT_SHA, "develop");

        assertNotEquals(requestId1, requestId2, "Each trigger should get a unique request ID");
    }

    @Test
    @DisplayName("Creating trigger should create parent directory if needed")
    void testCreateTrigger_CreatesParentDirectory(@TempDir Path tempDir) throws IOException {
        Path vitruviusDir = tempDir.resolve(".vitruvius");
        assertFalse(Files.exists(vitruviusDir));

        ValidationTriggerFile triggerFile = new ValidationTriggerFile(tempDir);
        triggerFile.createTrigger(COMMIT_SHA, BRANCH);

        assertTrue(Files.exists(vitruviusDir));
        assertTrue(Files.isDirectory(vitruviusDir));
    }

    @Test
    @DisplayName("Creating trigger with null commit SHA should throw exception")
    void testCreateTrigger_NullCommitSha(@TempDir Path tempDir) {
        ValidationTriggerFile triggerFile = new ValidationTriggerFile(tempDir);

        assertThrows(NullPointerException.class, () -> triggerFile.createTrigger(null, BRANCH));
    }

    @Test
    @DisplayName("Creating trigger with null branch should throw exception")
    void testCreateTrigger_NullBranch(@TempDir Path tempDir) {
        ValidationTriggerFile triggerFile = new ValidationTriggerFile(tempDir);

        assertThrows(NullPointerException.class, () -> triggerFile.createTrigger(COMMIT_SHA, null));
    }

    @Test
    @DisplayName("Checking trigger should return info with request ID and delete file")
    void testCheckAndClearTrigger_WhenExists(@TempDir Path tempDir) throws IOException {
        ValidationTriggerFile triggerFile = new ValidationTriggerFile(tempDir);
        String requestId = triggerFile.createTrigger(COMMIT_SHA, BRANCH);
        assertTrue(triggerFile.exists());

        ValidationTriggerFile.TriggerInfo info = triggerFile.checkAndClearTrigger();

        assertNotNull(info);
        assertEquals(COMMIT_SHA, info.getCommitSha());
        assertEquals(BRANCH, info.getBranch());
        // Verify request ID and timestamp
        assertEquals(requestId, info.getRequestId());
        assertTrue(info.getTimestamp() > 0);
        assertTrue(info.getTimestamp() <= System.currentTimeMillis());

        assertFalse(triggerFile.exists());
    }

    @Test
    @DisplayName("Checking trigger when file doesn't exist should return null")
    void testCheckAndClearTrigger_WhenNotExists(@TempDir Path tempDir) {
        ValidationTriggerFile triggerFile = new ValidationTriggerFile(tempDir);

        ValidationTriggerFile.TriggerInfo info = triggerFile.checkAndClearTrigger();

        assertNull(info);
    }

    @Test
    @DisplayName("Checking trigger with invalid format should return null and cleanup")
    void testCheckAndClearTrigger_InvalidFormat(@TempDir Path tempDir) throws IOException {
        ValidationTriggerFile triggerFile = new ValidationTriggerFile(tempDir);
        Path triggerPath = triggerFile.getTriggerPath();
        Files.createDirectories(triggerPath.getParent());
        Files.writeString(triggerPath, "invalid-content-no-delimiter");

        ValidationTriggerFile.TriggerInfo info = triggerFile.checkAndClearTrigger();

        assertNull(info);
        assertFalse(Files.exists(triggerPath));
    }

    @Test
    @DisplayName("Checking trigger with legacy format (2 parts) should work with generated request ID")
    void testCheckAndClearTrigger_LegacyFormat(@TempDir Path tempDir) throws IOException {
        ValidationTriggerFile triggerFile = new ValidationTriggerFile(tempDir);
        Path triggerPath = triggerFile.getTriggerPath();
        Files.createDirectories(triggerPath.getParent());
        // commitSha|branch (no request ID)
        Files.writeString(triggerPath, COMMIT_SHA + "|" + BRANCH);

        ValidationTriggerFile.TriggerInfo info = triggerFile.checkAndClearTrigger();

        // Should still work - generates request ID automatically
        assertNotNull(info);
        assertEquals(COMMIT_SHA, info.getCommitSha());
        assertEquals(BRANCH, info.getBranch());
        assertNotNull(info.getRequestId(), "Should generate request ID for legacy format");
        assertFalse(info.getRequestId().isEmpty());
        assertTrue(info.getTimestamp() > 0);
    }

    @Test
    @DisplayName("Checking trigger should trim whitespace from values")
    void testCheckAndClearTrigger_WithWhitespace(@TempDir Path tempDir) throws IOException {
        ValidationTriggerFile triggerFile = new ValidationTriggerFile(tempDir);
        Path triggerPath = triggerFile.getTriggerPath();
        Files.createDirectories(triggerPath.getParent());

        // Create trigger with whitespace (simulating manual creation)
        String requestId = "test-request-id";
        long timestamp = System.currentTimeMillis();
        Files.writeString(triggerPath, "  " + COMMIT_SHA + " | " + BRANCH + " | " + requestId + " | " + timestamp + "  ");

        ValidationTriggerFile.TriggerInfo info = triggerFile.checkAndClearTrigger();

        assertNotNull(info);
        assertEquals(COMMIT_SHA, info.getCommitSha());
        assertEquals(BRANCH, info.getBranch());
        assertEquals(requestId, info.getRequestId());
        assertEquals(timestamp, info.getTimestamp());
    }

    @Test
    @DisplayName("Trigger path should be in .vitruvius directory")
    void testGetTriggerPath(@TempDir Path tempDir) {
        ValidationTriggerFile triggerFile = new ValidationTriggerFile(tempDir);

        Path expected = tempDir.resolve(".vitruvius").resolve("validate-trigger");
        assertEquals(expected, triggerFile.getTriggerPath());
    }

    @Test
    @DisplayName("TriggerInfo should store commit SHA, branch, request ID, and timestamp")
    void testTriggerInfo_Constructor() {
        String requestId = "test-uuid-1234";
        long timestamp = System.currentTimeMillis();

        ValidationTriggerFile.TriggerInfo info = new ValidationTriggerFile.TriggerInfo(COMMIT_SHA, BRANCH, requestId, timestamp);

        assertEquals(COMMIT_SHA, info.getCommitSha());
        assertEquals(BRANCH, info.getBranch());
        assertEquals(requestId, info.getRequestId());
        assertEquals(timestamp, info.getTimestamp());
    }

    @Test
    @DisplayName("TriggerInfo with null commit SHA should throw exception")
    void testTriggerInfo_NullCommitSha() {
        assertThrows(NullPointerException.class, () -> new ValidationTriggerFile.TriggerInfo(null, BRANCH, "req-id", System.currentTimeMillis()));
    }

    @Test
    @DisplayName("TriggerInfo with null branch should throw exception")
    void testTriggerInfo_NullBranch() {
        assertThrows(NullPointerException.class, () -> new ValidationTriggerFile.TriggerInfo(COMMIT_SHA, null, "req-id", System.currentTimeMillis()));
    }

    @Test
    @DisplayName("TriggerInfo with null request ID should throw exception")
    void testTriggerInfo_NullRequestId() {
        assertThrows(NullPointerException.class, () -> new ValidationTriggerFile.TriggerInfo(COMMIT_SHA, BRANCH, null, System.currentTimeMillis()));
    }

    @Test
    @DisplayName("TriggerInfo toString should include all fields")
    void testTriggerInfo_ToString() {
        String requestId = "test-uuid-5678";
        long timestamp = 1707584000000L;

        ValidationTriggerFile.TriggerInfo info = new ValidationTriggerFile.TriggerInfo(COMMIT_SHA, BRANCH, requestId, timestamp);

        String str = info.toString();
        assertTrue(str.contains("abc1234")); // Short SHA
        assertTrue(str.contains(BRANCH));
        assertTrue(str.contains(requestId));
        assertTrue(str.contains(String.valueOf(timestamp)));
    }

    @Test
    @DisplayName("Multiple sequential triggers should work correctly with unique request IDs")
    void testMultipleTriggersSequentially(@TempDir Path tempDir) throws IOException {
        ValidationTriggerFile triggerFile = new ValidationTriggerFile(tempDir);

        // First trigger
        String requestId1 = triggerFile.createTrigger(COMMIT_SHA, "main");
        ValidationTriggerFile.TriggerInfo info1 = triggerFile.checkAndClearTrigger();
        assertNotNull(info1);
        assertEquals("main", info1.getBranch());
        assertEquals(requestId1, info1.getRequestId());
        assertFalse(triggerFile.exists());

        // Second trigger
        String requestId2 = triggerFile.createTrigger(COMMIT_SHA, "develop");
        ValidationTriggerFile.TriggerInfo info2 = triggerFile.checkAndClearTrigger();
        assertNotNull(info2);
        assertEquals("develop", info2.getBranch());
        assertEquals(requestId2, info2.getRequestId());
        assertFalse(triggerFile.exists());

        // Request IDs should be different
        assertNotEquals(requestId1, requestId2);
    }

    @Test
    @DisplayName("Trigger with invalid timestamp should use current time as fallback")
    void testCheckAndClearTrigger_InvalidTimestamp(@TempDir Path tempDir) throws IOException {
        ValidationTriggerFile triggerFile = new ValidationTriggerFile(tempDir);
        Path triggerPath = triggerFile.getTriggerPath();
        Files.createDirectories(triggerPath.getParent());

        // Invalid timestamp (not a number)
        Files.writeString(triggerPath, COMMIT_SHA + "|" + BRANCH + "|req-id|not-a-timestamp");

        long before = System.currentTimeMillis();
        ValidationTriggerFile.TriggerInfo info = triggerFile.checkAndClearTrigger();
        long after = System.currentTimeMillis();

        assertNotNull(info);
        // Should fallback to current time
        assertTrue(info.getTimestamp() >= before);
        assertTrue(info.getTimestamp() <= after);
    }

    @Test
    @DisplayName("Checking trigger with empty commit SHA should return null")
    void testCheckAndClearTrigger_EmptyCommitSha(@TempDir Path tempDir) throws IOException {
        ValidationTriggerFile triggerFile = new ValidationTriggerFile(tempDir);
        Path triggerPath = triggerFile.getTriggerPath();
        Files.createDirectories(triggerPath.getParent());

        Files.writeString(triggerPath, "|" + BRANCH + "|req-id|" + System.currentTimeMillis());

        ValidationTriggerFile.TriggerInfo info = triggerFile.checkAndClearTrigger();

        assertNull(info, "Empty commit SHA should be rejected");
        assertFalse(Files.exists(triggerPath), "Invalid trigger file should be cleaned up");
    }

    @Test
    @DisplayName("Checking trigger with empty branch should return null")
    void testCheckAndClearTrigger_EmptyBranch(@TempDir Path tempDir) throws IOException {
        ValidationTriggerFile triggerFile = new ValidationTriggerFile(tempDir);
        Path triggerPath = triggerFile.getTriggerPath();
        Files.createDirectories(triggerPath.getParent());

        Files.writeString(triggerPath, COMMIT_SHA + "||req-id|" + System.currentTimeMillis());

        ValidationTriggerFile.TriggerInfo info = triggerFile.checkAndClearTrigger();

        assertNull(info, "Empty branch should be rejected");
        assertFalse(Files.exists(triggerPath), "Invalid trigger file should be cleaned up");
    }

    @Test
    @DisplayName("Checking trigger with empty request ID should return null")
    void testCheckAndClearTrigger_EmptyRequestId(@TempDir Path tempDir) throws IOException {
        ValidationTriggerFile triggerFile = new ValidationTriggerFile(tempDir);
        Path triggerPath = triggerFile.getTriggerPath();
        Files.createDirectories(triggerPath.getParent());

        Files.writeString(triggerPath, COMMIT_SHA + "|" + BRANCH + "||" + System.currentTimeMillis());

        ValidationTriggerFile.TriggerInfo info = triggerFile.checkAndClearTrigger();

        assertNull(info, "Empty request ID should be rejected");
        assertFalse(Files.exists(triggerPath), "Invalid trigger file should be cleaned up");
    }
}