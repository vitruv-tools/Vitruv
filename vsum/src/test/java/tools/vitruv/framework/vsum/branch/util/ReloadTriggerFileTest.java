package tools.vitruv.framework.vsum.branch.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link ReloadTriggerFile}.
 * <p>All tests operate on a temporary directory so that the {@code .vitruvius}
 * directory and trigger file are isolated between test runs.
 * <p>Current trigger file format: {@code newBranch|requestId|timestamp|oldBranch}
 */
class ReloadTriggerFileTest {

    private static final String BRANCH = "feature-vcs";
    private static final String OLD_BRANCH = "master";

    /**
     * Verifies that creating a trigger produces a file at the expected path inside
     * the {@code .vitruvius} directory and that the parent directory is created
     * automatically if it does not exist yet.
     * Both aspects are tested together because they are part of a single write operation.
     */
    @Test
    @DisplayName("Creates trigger file and parent directory when they do not exist")
    void createsTriggerFileAndParentDirectory(@TempDir Path tempDir) throws Exception {
        var triggerFile = new ReloadTriggerFile(tempDir);
        // the .vitruvius directory must not exist before the first trigger is created.
        assertFalse(Files.exists(tempDir.resolve(".vitruvius")));
        triggerFile.createTrigger(BRANCH, OLD_BRANCH);
        // the directory and the trigger file must both be present after creation.
        assertTrue(Files.isDirectory(tempDir.resolve(".vitruvius")),
                "parent directory must be created automatically");
        assertTrue(Files.exists(tempDir.resolve(".vitruvius/reload-trigger")),
                "trigger file must exist after creation");
    }

    /**
     * Verifies that the trigger file content conforms to the expected four-field format
     * and that the request identifier returned by {@link ReloadTriggerFile#createTrigger}
     * matches the value written to the file.
     * Also verifies that the old branch name is stored as the fourth field.
     */
    @Test
    @DisplayName("Writes branch name, request identifier, timestamp, and old branch name in the correct format")
    void writesCorrectFormat(@TempDir Path tempDir) throws Exception {
        var triggerFile = new ReloadTriggerFile(tempDir);
        String requestId = triggerFile.createTrigger(BRANCH, OLD_BRANCH);
        String content = Files.readString(tempDir.resolve(".vitruvius/reload-trigger"));
        String[] parts = content.split("\\|");
        // the file must contain exactly four pipe-separated fields.
        assertEquals(4, parts.length, "file must have four pipe-separated fields");
        assertEquals(BRANCH, parts[0], "first field must be the new branch name");
        assertEquals(requestId, parts[1], "second field must match the returned request identifier");
        assertDoesNotThrow(() -> Long.parseLong(parts[2]), "third field must be a parseable timestamp");
        assertEquals(OLD_BRANCH, parts[3], "fourth field must be the old branch name");
    }

    /**
     * Verifies that each call to {@link ReloadTriggerFile#createTrigger} returns a
     * different request identifier. This ensures that two consecutive branch switches
     * can be distinguished in logs even if the watcher processes them with a slight delay.
     */
    @Test
    @DisplayName("Returns a unique request identifier for each trigger creation")
    void returnsUniqueRequestIdPerTrigger(@TempDir Path tempDir) throws Exception {
        var triggerFile = new ReloadTriggerFile(tempDir);
        String requestId1 = triggerFile.createTrigger("main", "develop");
        // clear the first trigger so the second write does not overwrite it.
        triggerFile.checkAndClearTrigger();
        String requestId2 = triggerFile.createTrigger("develop", "main");
        assertNotEquals(requestId1, requestId2,
                "each trigger creation must produce a distinct request identifier");
    }

    /**
     * Verifies that passing a null new branch name to
     * {@link ReloadTriggerFile#createTrigger} raises a {@link NullPointerException}
     * before any file is written.
     */
    @Test
    @DisplayName("Rejects a null new branch name")
    void createTriggerRejectsNullNewBranch(@TempDir Path tempDir) {
        var triggerFile = new ReloadTriggerFile(tempDir);
        assertThrows(NullPointerException.class, () -> triggerFile.createTrigger(null, OLD_BRANCH),
                "null new branch name must be rejected");
    }

    /**
     * Verifies that passing a null old branch name to
     * {@link ReloadTriggerFile#createTrigger} raises a {@link NullPointerException}
     * before any file is written.
     */
    @Test
    @DisplayName("Rejects a null old branch name")
    void createTriggerRejectsNullOldBranch(@TempDir Path tempDir) {
        var triggerFile = new ReloadTriggerFile(tempDir);
        assertThrows(NullPointerException.class, () -> triggerFile.createTrigger(BRANCH, null),
                "null old branch name must be rejected");
    }

    /**
     * Verifies the normal consumption lifecycle: when a trigger exists, the returned
     * {@link ReloadTriggerFile.TriggerInfo} carries the correct field values including
     * the old branch name, and the file is deleted so the watcher does not process
     * the same trigger twice.
     */
    @Test
    @DisplayName("Returns trigger info with correct fields including old branch name, and deletes the file on consumption")
    void checkAndClearReturnsInfoAndDeletesFile(@TempDir Path tempDir) throws Exception {
        var triggerFile = new ReloadTriggerFile(tempDir);
        String requestId = triggerFile.createTrigger(BRANCH, OLD_BRANCH);

        ReloadTriggerFile.TriggerInfo result = triggerFile.checkAndClearTrigger();

        assertNotNull(result, "a valid trigger must return a non-null TriggerInfo");
        assertEquals(BRANCH, result.getBranchName(),
                "branch name must match the value passed to createTrigger");
        assertEquals(OLD_BRANCH, result.getOldBranchName(),
                "old branch name must match the value passed to createTrigger");
        assertEquals(requestId, result.getRequestId(),
                "request identifier must match the value returned by createTrigger");
        assertTrue(result.getTimestamp() > 0,
                "timestamp must be a positive epoch value");
        assertFalse(Files.exists(tempDir.resolve(".vitruvius/reload-trigger")),
                "trigger file must be deleted after consumption");
    }

    /**
     * Verifies that {@link ReloadTriggerFile#checkAndClearTrigger()} returns null when
     * no trigger file exists, and continues to return null on subsequent calls.
     * Also confirms that creating a new trigger after clearing makes it consumable again.
     */
    @Test
    @DisplayName("Returns null when no trigger exists and becomes consumable again after a new creation")
    void returnsNullWhenAbsentAndResetsAfterNewTrigger(@TempDir Path tempDir) throws Exception {
        var triggerFile = new ReloadTriggerFile(tempDir);

        // no trigger file has been created yet.
        assertNull(triggerFile.checkAndClearTrigger(),
                "must return null when no trigger file exists");

        triggerFile.createTrigger(BRANCH, OLD_BRANCH);
        assertNotNull(triggerFile.checkAndClearTrigger(),
                "must return info after a trigger is created");

        // the trigger was consumed.
        assertNull(triggerFile.checkAndClearTrigger(),
                "must return null after the trigger is consumed");
        assertNull(triggerFile.checkAndClearTrigger(),
                "repeated calls must continue returning null");
    }

    /**
     * Verifies that the new four-field format correctly round-trips the old branch name
     * so that PostCheckoutHandler can use it for vsum state inheritance on first branch visit.
     */
    @Test
    @DisplayName("Round-trips old branch name correctly through the four-field format")
    void writesAndReadsOldBranchName(@TempDir Path tempDir) throws Exception {
        var triggerFile = new ReloadTriggerFile(tempDir);
        triggerFile.createTrigger("feature", "master");
        ReloadTriggerFile.TriggerInfo result = triggerFile.checkAndClearTrigger();
        assertNotNull(result);
        assertEquals("feature", result.getBranchName());
        assertEquals("master", result.getOldBranchName());
    }

    /**
     * Verifies that branch names containing slashes (e.g. feature/login) are preserved
     * correctly when written and read back. Slashes must not be confused with the
     * pipe delimiter.
     */
    @Test
    @DisplayName("Preserves branch names containing slashes")
    void preservesBranchNamesWithSlashes(@TempDir Path tempDir) throws Exception {
        var triggerFile = new ReloadTriggerFile(tempDir);
        triggerFile.createTrigger("feature/login", "bugfix/issue-42");
        ReloadTriggerFile.TriggerInfo result = triggerFile.checkAndClearTrigger();
        assertNotNull(result);
        assertEquals("feature/login", result.getBranchName());
        assertEquals("bugfix/issue-42", result.getOldBranchName());
    }




    /**
     * Verifies that a file with an unexpected number of separated fields (not 1, 3, or 4)
     * is discarded and deleted rather than causing an exception.
     * This prevents the watcher from looping on a permanently unreadable trigger file.
     */
    @Test
    @DisplayName("Discards and deletes a file with an unrecognized number of fields")
    void handlesInvalidFieldCount(@TempDir Path tempDir) throws Exception {
        var triggerFile = new ReloadTriggerFile(tempDir);
        var filePath = tempDir.resolve(".vitruvius/reload-trigger");
        Files.createDirectories(filePath.getParent());
        Files.writeString(filePath, "invalid||multiple|delimiters|wrong");
        ReloadTriggerFile.TriggerInfo result = triggerFile.checkAndClearTrigger();
        assertNull(result, "an unrecognized field count must result in a null return");
        assertFalse(Files.exists(filePath),
                "the malformed trigger file must be deleted to prevent repeated processing");
    }

    /**
     * Verifies that a file whose branch name field is empty is discarded.
     * An empty branch name cannot be used to reload the correct model state.
     */
    @Test
    @DisplayName("Discards and deletes a file with an empty branch name field")
    void handlesEmptyBranchName(@TempDir Path tempDir) throws Exception {
        var triggerFile = new ReloadTriggerFile(tempDir);
        var filePath = tempDir.resolve(".vitruvius/reload-trigger");
        Files.createDirectories(filePath.getParent());
        // the branch name field (first field) is empty.
        Files.writeString(filePath, "|request-id|" + System.currentTimeMillis() + "|master");
        ReloadTriggerFile.TriggerInfo result = triggerFile.checkAndClearTrigger();
        assertNull(result, "an empty branch name must be rejected");
        assertFalse(Files.exists(filePath));
    }

    /**
     * Verifies that a non-numeric timestamp field does not cause an exception.
     * The implementation falls back to the current time so the trigger is still processed.
     */
    @Test
    @DisplayName("Falls back to current time when the timestamp field cannot be parsed")
    void handlesInvalidTimestamp(@TempDir Path tempDir) throws Exception {
        var triggerFile = new ReloadTriggerFile(tempDir);
        var filePath = tempDir.resolve(".vitruvius/reload-trigger");
        Files.createDirectories(filePath.getParent());
        // replace the numeric timestamp with a non-parseable string.
        Files.writeString(filePath, "main|request-id|not-a-number|master");
        long before = System.currentTimeMillis();
        ReloadTriggerFile.TriggerInfo result = triggerFile.checkAndClearTrigger();
        long after = System.currentTimeMillis();

        assertNotNull(result,
                "a malformed timestamp must not discard an otherwise valid trigger");
        assertEquals("master", result.getOldBranchName(),
                "old branch name must still be parsed correctly despite malformed timestamp");
        assertTrue(result.getTimestamp() >= before && result.getTimestamp() <= after,
                "the fallback timestamp must be within the wall clock window of the call");
    }


    /**
     * Verifies that {@link ReloadTriggerFile#exists()} correctly reflects the presence
     * and absence of the trigger file across the create-consume lifecycle.
     */
    @Test
    @DisplayName("Exists returns false initially, true after creation, and false after consumption")
    void existsReflectsFileLifecycle(@TempDir Path tempDir) throws Exception {
        var triggerFile = new ReloadTriggerFile(tempDir);
        assertFalse(triggerFile.exists(), "must not exist before any trigger is created");
        triggerFile.createTrigger(BRANCH, OLD_BRANCH);
        assertTrue(triggerFile.exists(), "must exist after trigger creation");
        triggerFile.checkAndClearTrigger();
        assertFalse(triggerFile.exists(), "must not exist after the trigger is consumed");
    }

    /**
     * Verifies that {@link ReloadTriggerFile#getTriggerPath()} returns the path that
     * the Git hook uses to write the trigger file.
     */
    @Test
    @DisplayName("Returns the correct trigger file path under the .vitruvius directory")
    void getTriggerPathReturnsCorrectPath(@TempDir Path tempDir) {
        var triggerFile = new ReloadTriggerFile(tempDir);
        assertEquals(tempDir.resolve(".vitruvius/reload-trigger"), triggerFile.getTriggerPath());
    }


    /**
     * Verifies that {@link ReloadTriggerFile.TriggerInfo} stores all four fields
     * correctly and rejects null values for required string fields.
     */
    @Test
    @DisplayName("TriggerInfo stores all fields correctly and rejects null values for required fields")
    void triggerInfoConstructionAndNullRejection() {
        long timestamp = System.currentTimeMillis();
        var info = new ReloadTriggerFile.TriggerInfo(BRANCH, OLD_BRANCH, "test-uuid", timestamp);

        // all provided values must be retrievable through the getters.
        assertEquals(BRANCH, info.getBranchName());
        assertEquals(OLD_BRANCH, info.getOldBranchName());
        assertEquals("test-uuid", info.getRequestId());
        assertEquals(timestamp, info.getTimestamp());

        // null values for required string fields must be rejected at construction time.
        assertThrows(NullPointerException.class, () -> new ReloadTriggerFile.TriggerInfo(
                null, OLD_BRANCH, "req-id", timestamp),
                "null new branch name must be rejected");
        assertThrows(NullPointerException.class, () -> new ReloadTriggerFile.TriggerInfo(
                BRANCH, null, "req-id", timestamp),
                "null old branch name must be rejected");
        assertThrows(NullPointerException.class, () -> new ReloadTriggerFile.TriggerInfo(
                BRANCH, OLD_BRANCH, null, timestamp),
                "null request identifier must be rejected");
    }
}