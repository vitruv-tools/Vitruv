package tools.vitruv.framework.vsum.branch.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link ReloadTriggerFile}.
 *
 * <p>All tests operate on a temporary directory so that the {@code .vitruvius} directory and trigger file are isolated between test runs.
 */
class ReloadTriggerFileTest {

    private static final String BRANCH = "feature-vcs";

    /**
     * Verifies that creating a trigger produces a file at the expected path inside the {@code .vitruvius} directory and 
     * that the parent directory is created automatically if it does not exist yet. 
     * Both aspects are tested together because they are part of a single write operation.
     */
    @Test
    @DisplayName("Creates trigger file and parent directory when they do not exist")
    void createsTriggerFileAndParentDirectory(@TempDir Path tempDir) throws Exception {
        var triggerFile = new ReloadTriggerFile(tempDir);

        // the .vitruvius directory must not exist before the first trigger is created.
        assertFalse(Files.exists(tempDir.resolve(".vitruvius")));

        triggerFile.createTrigger(BRANCH);

        // the directory and the trigger file must both be present after creation.
        assertTrue(Files.isDirectory(tempDir.resolve(".vitruvius")), "parent directory must be created automatically");
        assertTrue(Files.exists(tempDir.resolve(".vitruvius/reload-trigger")), "trigger file must exist after creation");
    }

    /**
     * Verifies that the trigger file content conforms to the expected three-field format and that the request identifier 
     * returned by {@link ReloadTriggerFile#createTrigger} matches the value written to the file.
     */
    @Test
    @DisplayName("Writes branch name, request identifier, and timestamp in the correct format")
    void writesCorrectFormat(@TempDir Path tempDir) throws Exception {
        var triggerFile = new ReloadTriggerFile(tempDir);
        String requestId = triggerFile.createTrigger(BRANCH);

        String content = Files.readString(tempDir.resolve(".vitruvius/reload-trigger"));
        String[] parts = content.split("\\|");

        // the file must contain exactly three separated fields.
        assertEquals(3, parts.length, "file must have three separated fields");
        assertEquals(BRANCH, parts[0], "first field must be the branch name");
        assertEquals(requestId, parts[1], "second field must match the returned request identifier");
        assertDoesNotThrow(() -> Long.parseLong(parts[2]), "third field must be a parseable timestamp");
    }

    /**
     * Verifies that each call to {@link ReloadTriggerFile#createTrigger} returns a different request identifier. 
     * This ensures that two consecutive branch switches can be distinguished in logs even if the watcher processes them with a slight delay.
     */
    @Test
    @DisplayName("Returns a unique request identifier for each trigger creation")
    void returnsUniqueRequestIdPerTrigger(@TempDir Path tempDir) throws Exception {
        var triggerFile = new ReloadTriggerFile(tempDir);

        String requestId1 = triggerFile.createTrigger("main");
        // clear the first trigger so the second write does not overwrite it.
        triggerFile.checkAndClearTrigger();
        String requestId2 = triggerFile.createTrigger("develop");

        assertNotEquals(requestId1, requestId2, "each trigger creation must produce a distinct request identifier");
    }

    /**
     * Verifies that passing a null branch name to {@link ReloadTriggerFile#createTrigger} raises a {@link NullPointerException} before any file is written.
     */
    @Test
    @DisplayName("Rejects a null branch name")
    void createTriggerRejectsNullBranch(@TempDir Path tempDir) {
        var triggerFile = new ReloadTriggerFile(tempDir);
        assertThrows(NullPointerException.class, () -> triggerFile.createTrigger(null));
    }

    /**
     * Verifies the normal consumption lifecycle: when a trigger exists, the returned {@link ReloadTriggerFile.TriggerInfo} carries the correct field values 
     * and the file is deleted so the watcher does not process the same trigger twice.
     */
    @Test
    @DisplayName("Returns trigger info with correct fields and deletes the file on consumption")
    void checkAndClearReturnsInfoAndDeletesFile(@TempDir Path tempDir) throws Exception {
        var triggerFile = new ReloadTriggerFile(tempDir);
        String requestId = triggerFile.createTrigger(BRANCH);

        ReloadTriggerFile.TriggerInfo result = triggerFile.checkAndClearTrigger();

        assertNotNull(result, "a valid trigger must return a non-null TriggerInfo");
        assertEquals(BRANCH, result.getBranchName());
        assertEquals(requestId, result.getRequestId());
        assertTrue(result.getTimestamp() > 0, "timestamp must be a positive epoch value");
        assertFalse(Files.exists(tempDir.resolve(".vitruvius/reload-trigger")), "trigger file must be deleted after consumption");
    }

    /**
     * Verifies that {@link ReloadTriggerFile#checkAndClearTrigger()} returns null when no trigger file exists, and continues to return null on subsequent calls. 
     * This also confirms that creating a new trigger after clearing makes it consumable again.
     */
    @Test
    @DisplayName("Returns null when no trigger exists and becomes consumable again after a new creation")
    void returnsNullWhenAbsentAndResetsAfterNewTrigger(@TempDir Path tempDir) throws Exception {
        var triggerFile = new ReloadTriggerFile(tempDir);

        // no trigger file has been created yet, so the first call must return null.
        assertNull(triggerFile.checkAndClearTrigger(), "must return null when no trigger file exists");

        triggerFile.createTrigger(BRANCH);
        assertNotNull(triggerFile.checkAndClearTrigger(), "must return info after a trigger is created");

        // the trigger was consumed, so all subsequent calls must return null again.
        assertNull(triggerFile.checkAndClearTrigger(), "must return null after the trigger is consumed");
        assertNull(triggerFile.checkAndClearTrigger(), "repeated calls must continue returning null");
    }

    /**
     * Verifies that the old human-readable text format (for example {@code "Reload requested at: 2026-02-10T10:00:00"}) is still accepted. 
     * The branch name cannot be recovered from this format, so {@code "unknown"} is substituted.
     */
    @Test
    @DisplayName("Parses the legacy 'Reload requested at' text format with unknown branch name")
    void handlesLegacyTextFormat(@TempDir Path tempDir) throws Exception {
        var triggerFile = new ReloadTriggerFile(tempDir);
        var filePath = tempDir.resolve(".vitruvius/reload-trigger");
        Files.createDirectories(filePath.getParent());
        // write the old human-readable format that older hook versions produced.
        Files.writeString(filePath, "Reload requested at: 2026-02-10T10:00:00");

        ReloadTriggerFile.TriggerInfo result = triggerFile.checkAndClearTrigger();

        assertNotNull(result, "legacy format must still be parsed successfully");
        assertEquals("unknown", result.getBranchName(), "branch name must be 'unknown' when it cannot be recovered from the legacy format");
        assertNotNull(result.getRequestId(), "a synthesized request identifier must be provided for legacy triggers");
        assertTrue(result.getTimestamp() > 0);
    }

    /**
     * Verifies that a file containing only a plain branch name (no delimiters) is accepted as a legacy format and the branch name is preserved.
     */
    @Test
    @DisplayName("Parses the legacy single-field format that contains only a branch name")
    void handlesLegacySingleFieldFormat(@TempDir Path tempDir) throws Exception {
        var triggerFile = new ReloadTriggerFile(tempDir);
        var filePath = tempDir.resolve(".vitruvius/reload-trigger");
        Files.createDirectories(filePath.getParent());
        // write only the branch name, as the original minimal hook version did.
        Files.writeString(filePath, "main");

        ReloadTriggerFile.TriggerInfo result = triggerFile.checkAndClearTrigger();

        assertNotNull(result, "single-field legacy format must be parsed successfully");
        assertEquals("main", result.getBranchName());
        assertNotNull(result.getRequestId());
    }

    /**
     * Verifies that a file with an unexpected number of separated fields (neither one nor three) is discarded and deleted rather than causing an exception. 
     * This prevents the watcher from looping on a permanently unreadable trigger file.
     */
    @Test
    @DisplayName("Discards and deletes a file with an unrecognized number of fields")
    void handlesInvalidFieldCount(@TempDir Path tempDir) throws Exception {
        var triggerFile = new ReloadTriggerFile(tempDir);
        var filePath = tempDir.resolve(".vitruvius/reload-trigger");
        Files.createDirectories(filePath.getParent());
        // write a file with five separated fields, which matches neither the current
        // three-field format nor the legacy single-field format.
        Files.writeString(filePath, "invalid||multiple|delimiters|wrong");

        ReloadTriggerFile.TriggerInfo result = triggerFile.checkAndClearTrigger();

        assertNull(result, "an unrecognized field count must result in a null return");
        assertFalse(Files.exists(filePath), "the malformed trigger file must be deleted to prevent repeated processing");
    }

    /**
     * Verifies that a file whose branch name field is empty is discarded.
     * An empty branch name cannot be used to reload the correct model state and would produce misleading logs.
     */
    @Test
    @DisplayName("Discards and deletes a file with an empty branch name field")
    void handlesEmptyBranchName(@TempDir Path tempDir) throws Exception {
        var triggerFile = new ReloadTriggerFile(tempDir);
        var filePath = tempDir.resolve(".vitruvius/reload-trigger");
        Files.createDirectories(filePath.getParent());
        // the branch name field (first field) is empty, which is not a valid state.
        Files.writeString(filePath, "|request-id|" + System.currentTimeMillis());

        ReloadTriggerFile.TriggerInfo result = triggerFile.checkAndClearTrigger();

        assertNull(result, "an empty branch name must be rejected");
        assertFalse(Files.exists(filePath));
    }

    /**
     * Verifies that a non-numeric timestamp field does not cause an exception.
     * The implementation falls back to the current time, so the trigger is still processed and the returned timestamp is close to the current wall clock time.
     */
    @Test
    @DisplayName("Falls back to current time when the timestamp field cannot be parsed")
    void handlesInvalidTimestamp(@TempDir Path tempDir) throws Exception {
        var triggerFile = new ReloadTriggerFile(tempDir);
        var filePath = tempDir.resolve(".vitruvius/reload-trigger");
        Files.createDirectories(filePath.getParent());
        // replace the numeric timestamp with a non-parseable string.
        Files.writeString(filePath, "main|request-id|not-a-number");

        long before = System.currentTimeMillis();
        ReloadTriggerFile.TriggerInfo result = triggerFile.checkAndClearTrigger();
        long after = System.currentTimeMillis();

        assertNotNull(result, "a malformed timestamp must not discard an otherwise valid trigger");
        assertTrue(result.getTimestamp() >= before && result.getTimestamp() <= after, "the fallback timestamp must be within the wall clock window of the call");
    }

    /**
     * Verifies that {@link ReloadTriggerFile#exists()} correctly reflects the presence and absence of the trigger file across the create-consume lifecycle.
     */
    @Test
    @DisplayName("Exists returns false initially, true after creation, and false after consumption")
    void existsReflectsFileLifecycle(@TempDir Path tempDir) throws Exception {
        var triggerFile = new ReloadTriggerFile(tempDir);

        assertFalse(triggerFile.exists(), "must not exist before any trigger is created");
        triggerFile.createTrigger(BRANCH);
        assertTrue(triggerFile.exists(), "must exist after trigger creation");
        triggerFile.checkAndClearTrigger();
        assertFalse(triggerFile.exists(), "must not exist after the trigger is consumed");
    }

    /**
     * Verifies that {@link ReloadTriggerFile#getTriggerPath()} returns the path that  Git hook uses to write the trigger file, which must be {@code <repositoryRoot>/.vitruvius/reload-trigger}.
     */
    @Test
    @DisplayName("Returns the correct trigger file path under the .vitruvius directory")
    void getTriggerPathReturnsCorrectPath(@TempDir Path tempDir) {
        var triggerFile = new ReloadTriggerFile(tempDir);
        assertEquals(tempDir.resolve(".vitruvius/reload-trigger"), triggerFile.getTriggerPath());
    }

    /**
     * Verifies that {@link ReloadTriggerFile.TriggerInfo} stores all provided fields and rejects null values for the required string fields.
     */
    @Test
    @DisplayName("TriggerInfo stores all fields correctly and rejects null branch name or request identifier")
    void triggerInfoConstructionAndNullRejection() {
        long timestamp = System.currentTimeMillis();
        var info = new ReloadTriggerFile.TriggerInfo(BRANCH, "test-uuid", timestamp);

        // all provided values must be retrievable through the getters.
        assertEquals(BRANCH, info.getBranchName());
        assertEquals("test-uuid", info.getRequestId());
        assertEquals(timestamp, info.getTimestamp());

        // null values for the string fields must be rejected immediately at construction time.
        assertThrows(NullPointerException.class, () -> new ReloadTriggerFile.TriggerInfo(null, "req-id", timestamp), "null branch name must be rejected");
        assertThrows(NullPointerException.class, () -> new ReloadTriggerFile.TriggerInfo(BRANCH, null, timestamp), "null request identifier must be rejected");
    }
}