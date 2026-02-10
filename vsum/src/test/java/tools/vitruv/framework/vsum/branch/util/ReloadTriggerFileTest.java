package tools.vitruv.framework.vsum.branch.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link ReloadTriggerFile}.
 */
class ReloadTriggerFileTest {

    private static final String BRANCH = "feature-ui";

    @Test
    @DisplayName("creates trigger file in .vitruvius directory")
    void createsTriggerFile(@TempDir Path tempDir) throws Exception {
        var triggerFile = new ReloadTriggerFile(tempDir);
        triggerFile.createTrigger(BRANCH);
        Path expectedPath = tempDir.resolve(".vitruvius/reload-trigger");
        assertTrue(Files.exists(expectedPath), "Trigger file should exist");
    }

    @Test
    @DisplayName("creates parent directory if needed")
    void createsParentDirectory(@TempDir Path tempDir) throws Exception {
        var triggerFile = new ReloadTriggerFile(tempDir);
        assertFalse(Files.exists(tempDir.resolve(".vitruvius")));
        triggerFile.createTrigger(BRANCH);
        assertTrue(Files.isDirectory(tempDir.resolve(".vitruvius")));
    }

    @Test
    @DisplayName("returns unique request ID for each trigger")
    void returnsUniqueRequestId(@TempDir Path tempDir) throws Exception {
        var triggerFile = new ReloadTriggerFile(tempDir);
        String requestId1 = triggerFile.createTrigger("main");
        triggerFile.checkAndClearTrigger();
        String requestId2 = triggerFile.createTrigger("develop");

        assertNotNull(requestId1);
        assertNotNull(requestId2);
        assertNotEquals(requestId1, requestId2, "Each trigger should get unique request ID");
    }

    @Test
    @DisplayName("writes branch name, request ID, and timestamp to trigger file")
    void writesCorrectFormat(@TempDir Path tempDir) throws Exception {
        var triggerFile = new ReloadTriggerFile(tempDir);
        String requestId = triggerFile.createTrigger(BRANCH);

        Path filePath = tempDir.resolve(".vitruvius/reload-trigger");
        String content = Files.readString(filePath);

        String[] parts = content.split("\\|");
        assertEquals(3, parts.length, "File should have 3 parts");
        assertEquals(BRANCH, parts[0]);
        assertEquals(requestId, parts[1]);
        assertDoesNotThrow(() -> Long.parseLong(parts[2]), "Third part should be timestamp");
    }

    @Test
    @DisplayName("checkAndClearTrigger returns null when file doesn't exist")
    void checkAndClearReturnsNullWhenNotExists(@TempDir Path tempDir) {
        var triggerFile = new ReloadTriggerFile(tempDir);
        ReloadTriggerFile.TriggerInfo result = triggerFile.checkAndClearTrigger();
        assertNull(result, "Should return null when trigger doesn't exist");
    }

    @Test
    @DisplayName("checkAndClearTrigger returns info and deletes file when it exists")
    void checkAndClearReturnsInfoAndDeletes(@TempDir Path tempDir) throws Exception {
        var triggerFile = new ReloadTriggerFile(tempDir);
        String requestId = triggerFile.createTrigger(BRANCH);

        Path filePath = tempDir.resolve(".vitruvius/reload-trigger");
        assertTrue(Files.exists(filePath), "File should exist before check");

        ReloadTriggerFile.TriggerInfo result = triggerFile.checkAndClearTrigger();

        assertNotNull(result, "Should return TriggerInfo when trigger exists");
        assertEquals(BRANCH, result.getBranchName());
        assertEquals(requestId, result.getRequestId());
        assertTrue(result.getTimestamp() > 0);
        assertFalse(Files.exists(filePath), "File should be deleted after check");
    }

    @Test
    @DisplayName("multiple checks return null after first clear")
    void multipleChecksReturnNull(@TempDir Path tempDir) throws Exception {
        var triggerFile = new ReloadTriggerFile(tempDir);
        triggerFile.createTrigger(BRANCH);

        assertNotNull(triggerFile.checkAndClearTrigger());
        assertNull(triggerFile.checkAndClearTrigger());
        assertNull(triggerFile.checkAndClearTrigger());
    }

    @Test
    @DisplayName("exists returns true when file exists")
    void existsReturnsTrueWhenFileExists(@TempDir Path tempDir) throws Exception {
        var triggerFile = new ReloadTriggerFile(tempDir);
        assertFalse(triggerFile.exists(), "Should not exist initially");

        triggerFile.createTrigger(BRANCH);
        assertTrue(triggerFile.exists(), "Should exist after creation");
    }

    @Test
    @DisplayName("getTriggerPath returns correct path")
    void getTriggerPathReturnsCorrectPath(@TempDir Path tempDir) {
        var triggerFile = new ReloadTriggerFile(tempDir);
        Path expectedPath = tempDir.resolve(".vitruvius/reload-trigger");
        assertEquals(expectedPath, triggerFile.getTriggerPath());
    }

    @Test
    @DisplayName("recreating trigger after clear works")
    void recreatingTriggerWorks(@TempDir Path tempDir) throws Exception {
        var triggerFile = new ReloadTriggerFile(tempDir);
        triggerFile.createTrigger(BRANCH);
        assertNotNull(triggerFile.checkAndClearTrigger());

        triggerFile.createTrigger(BRANCH);
        assertTrue(triggerFile.exists());
    }

    @Test
    @DisplayName("handles legacy format with old timestamp text")
    void handlesLegacyFormat(@TempDir Path tempDir) throws Exception {
        var triggerFile = new ReloadTriggerFile(tempDir);
        Path filePath = tempDir.resolve(".vitruvius/reload-trigger");
        Files.createDirectories(filePath.getParent());
        Files.writeString(filePath, "Reload requested at: 2026-02-10T10:00:00");

        ReloadTriggerFile.TriggerInfo result = triggerFile.checkAndClearTrigger();

        assertNotNull(result, "Should parse legacy format");
        assertEquals("unknown", result.getBranchName());
        assertNotNull(result.getRequestId());
        assertTrue(result.getTimestamp() > 0);
    }

    @Test
    @DisplayName("handles legacy format with just branch name")
    void handlesLegacyFormatBranchOnly(@TempDir Path tempDir) throws Exception {
        var triggerFile = new ReloadTriggerFile(tempDir);
        Path filePath = tempDir.resolve(".vitruvius/reload-trigger");
        Files.createDirectories(filePath.getParent());
        Files.writeString(filePath, "main");

        ReloadTriggerFile.TriggerInfo result = triggerFile.checkAndClearTrigger();

        assertNotNull(result, "Should parse legacy single-value format");
        assertEquals("main", result.getBranchName());
        assertNotNull(result.getRequestId());
    }

    @Test
    @DisplayName("TriggerInfo stores all fields correctly")
    void triggerInfoStoresFields() {
        String requestId = "test-uuid";
        long timestamp = System.currentTimeMillis();

        ReloadTriggerFile.TriggerInfo info = new ReloadTriggerFile.TriggerInfo(BRANCH, requestId, timestamp);

        assertEquals(BRANCH, info.getBranchName());
        assertEquals(requestId, info.getRequestId());
        assertEquals(timestamp, info.getTimestamp());
    }

    @Test
    @DisplayName("TriggerInfo requires non-null branch name")
    void triggerInfoRequiresNonNullBranch() {
        assertThrows(NullPointerException.class, () -> new ReloadTriggerFile.TriggerInfo(null, "req-id", System.currentTimeMillis()));
    }

    @Test
    @DisplayName("TriggerInfo requires non-null request ID")
    void triggerInfoRequiresNonNullRequestId() {
        assertThrows(NullPointerException.class, () -> new ReloadTriggerFile.TriggerInfo(BRANCH, null, System.currentTimeMillis()));
    }

    @Test
    @DisplayName("createTrigger requires non-null branch name")
    void createTriggerRequiresNonNullBranch(@TempDir Path tempDir) {
        var triggerFile = new ReloadTriggerFile(tempDir);
        assertThrows(NullPointerException.class, () -> triggerFile.createTrigger(null));
    }

    @Test
    @DisplayName("handles invalid format and deletes file")
    void handlesInvalidFormat(@TempDir Path tempDir) throws Exception {
        var triggerFile = new ReloadTriggerFile(tempDir);
        Path filePath = tempDir.resolve(".vitruvius/reload-trigger");
        Files.createDirectories(filePath.getParent());
        Files.writeString(filePath, "invalid||multiple|delimiters|wrong");

        ReloadTriggerFile.TriggerInfo result = triggerFile.checkAndClearTrigger();

        assertNull(result, "Should return null for invalid format");
        assertFalse(Files.exists(filePath), "Invalid file should be deleted");
    }

    @Test
    @DisplayName("handles empty branch name in file")
    void handlesEmptyBranchName(@TempDir Path tempDir) throws Exception {
        var triggerFile = new ReloadTriggerFile(tempDir);
        Path filePath = tempDir.resolve(".vitruvius/reload-trigger");
        Files.createDirectories(filePath.getParent());
        Files.writeString(filePath, "|request-id|" + System.currentTimeMillis());

        ReloadTriggerFile.TriggerInfo result = triggerFile.checkAndClearTrigger();

        assertNull(result, "Should reject empty branch name");
        assertFalse(Files.exists(filePath));
    }

    @Test
    @DisplayName("handles invalid timestamp gracefully")
    void handlesInvalidTimestamp(@TempDir Path tempDir) throws Exception {
        var triggerFile = new ReloadTriggerFile(tempDir);
        Path filePath = tempDir.resolve(".vitruvius/reload-trigger");
        Files.createDirectories(filePath.getParent());
        Files.writeString(filePath, "main|request-id|not-a-number");

        long before = System.currentTimeMillis();
        ReloadTriggerFile.TriggerInfo result = triggerFile.checkAndClearTrigger();
        long after = System.currentTimeMillis();

        assertNotNull(result);
        assertTrue(result.getTimestamp() >= before && result.getTimestamp() <= after, "Should fallback to current time for invalid timestamp");
    }
}