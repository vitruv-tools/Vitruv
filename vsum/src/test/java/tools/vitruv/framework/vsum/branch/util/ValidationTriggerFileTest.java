package tools.vitruv.framework.vsum.branch.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link ValidationTriggerFile}.
 *
 * <p>The tests cover the normal create-and-consume lifecycle, the four-field file format, backward compatibility with the legacy two-field format,
 * resilience against malformed content, and the {@link ValidationTriggerFile.TriggerInfo} construction contract.
 */
class ValidationTriggerFileTest {

    private static final String COMMIT_SHA = "abc1234567890abcdef1234567890abcdef12345";
    private static final String BRANCH = "feature-vcs";

    /**
     * Verifies that creating a trigger writes a file in the correct four-field format, creates the {@code .vitruvius} parent directory automatically,
     * and returns a non-null request identifier that matches the value written to the file.
     * All three aspects are verified together because they describe one write operation.
     */
    @Test
    @DisplayName("Creates trigger file with correct four-field format, parent directory, and returned request identifier")
    void createsTriggerFileWithCorrectFormatAndDirectory(@TempDir Path tempDir) throws IOException {
        var triggerFile = new ValidationTriggerFile(tempDir);

        // the .vitruvius directory must not exist before the first trigger is created.
        assertFalse(Files.exists(tempDir.resolve(".vitruvius")));

        String requestId = triggerFile.createTrigger(COMMIT_SHA, BRANCH);

        // the directory and trigger file must both be present after creation.
        assertTrue(Files.isDirectory(tempDir.resolve(".vitruvius")), "parent directory must be created automatically");
        assertTrue(triggerFile.exists(), "trigger file must exist after creation");

        // verify the four-field format: commitSha|branch|requestId|timestamp.
        String content = Files.readString(triggerFile.getTriggerPath());
        String[] parts = content.split("\\|");
        assertEquals(4, parts.length, "trigger file must contain four pipe-separated fields");
        assertEquals(COMMIT_SHA, parts[0]);
        assertEquals(BRANCH, parts[1]);
        assertEquals(requestId, parts[2], "the returned request identifier must match the value written to the file");
        assertDoesNotThrow(() -> Long.parseLong(parts[3]), "the fourth field must be a parseable timestamp");
    }

    /**
     * Verifies that each call to {@link ValidationTriggerFile#createTrigger} returns a different request identifier so that consecutive commits can be distinguished.
     */
    @Test
    @DisplayName("Returns a unique request identifier for each trigger creation")
    void returnsUniqueRequestIdPerTrigger(@TempDir Path tempDir) throws IOException {
        var triggerFile = new ValidationTriggerFile(tempDir);

        String requestId1 = triggerFile.createTrigger(COMMIT_SHA, "main");
        // consume the first trigger so the second write does not overwrite it before it is read.
        triggerFile.checkAndClearTrigger();
        String requestId2 = triggerFile.createTrigger(COMMIT_SHA, "develop");

        assertNotEquals(requestId1, requestId2, "each trigger creation must produce a distinct request identifier");
    }

    /**
     * Verifies that passing a null commit SHA or null branch to {@link ValidationTriggerFile#createTrigger} throws a {@link NullPointerException} before any file is written.
     */
    @Test
    @DisplayName("Rejects a null commit SHA or null branch name")
    void createTriggerRejectsNullArguments(@TempDir Path tempDir) {
        var triggerFile = new ValidationTriggerFile(tempDir);

        assertThrows(NullPointerException.class, () -> triggerFile.createTrigger(null, BRANCH), "null commit SHA must be rejected");
        assertThrows(NullPointerException.class, () -> triggerFile.createTrigger(COMMIT_SHA, null), "null branch must be rejected");
    }

    /**
     * Verifies the normal consumption lifecycle: the returned {@link ValidationTriggerFile.TriggerInfo} carries all four field values correctly
     * and the trigger file is deleted after reading.
     */
    @Test
    @DisplayName("Returns trigger info with all fields and deletes the file on consumption")
    void checkAndClearReturnsTriggerInfoAndDeletesFile(@TempDir Path tempDir) throws IOException {
        var triggerFile = new ValidationTriggerFile(tempDir);
        String requestId = triggerFile.createTrigger(COMMIT_SHA, BRANCH);

        var info = triggerFile.checkAndClearTrigger();

        assertNotNull(info, "a valid trigger must return a non-null TriggerInfo");
        assertEquals(COMMIT_SHA, info.getCommitSha());
        assertEquals(BRANCH, info.getBranch());
        assertEquals(requestId, info.getRequestId(), "the returned request identifier must match what was written");
        assertTrue(info.getTimestamp() > 0 && info.getTimestamp() <= System.currentTimeMillis());
        assertFalse(triggerFile.exists(), "trigger file must be deleted after consumption");
    }

    /**
     * Verifies that {@link ValidationTriggerFile#checkAndClearTrigger()} returns null when no trigger file exists.
     */
    @Test
    @DisplayName("Returns null when no trigger file exists")
    void checkAndClearReturnsNullWhenAbsent(@TempDir Path tempDir) {
        var triggerFile = new ValidationTriggerFile(tempDir);
        assertNull(triggerFile.checkAndClearTrigger());
    }

    /**
     * Verifies that the full create-consume-create-consume cycle works correctly for two sequential triggers.
     * Each trigger must produce independent info and a distinct request identifier.
     */
    @Test
    @DisplayName("Processes multiple sequential triggers correctly with unique request identifiers")
    void handlesMultipleSequentialTriggers(@TempDir Path tempDir) throws IOException {
        var triggerFile = new ValidationTriggerFile(tempDir);

        // first trigger: create, consume, and verify.
        String requestId1 = triggerFile.createTrigger(COMMIT_SHA, "main");
        var info1 = triggerFile.checkAndClearTrigger();
        assertNotNull(info1);
        assertEquals("main", info1.getBranch());
        assertEquals(requestId1, info1.getRequestId());
        assertFalse(triggerFile.exists());

        // second trigger: create, consume, and verify independently.
        String requestId2 = triggerFile.createTrigger(COMMIT_SHA, "develop");
        var info2 = triggerFile.checkAndClearTrigger();
        assertNotNull(info2);
        assertEquals("develop", info2.getBranch());
        assertEquals(requestId2, info2.getRequestId());
        assertFalse(triggerFile.exists());

        // the two request identifiers must be different.
        assertNotEquals(requestId1, requestId2);
    }

    /**
     * Verifies that field values with surrounding whitespace are trimmed correctly.
     * A manually created trigger file may contain spaces around the delimiters.
     */
    @Test
    @DisplayName("Trims whitespace from all fields when reading the trigger file")
    void checkAndClearTrimsWhitespaceFromFields(@TempDir Path tempDir) throws IOException {
        var triggerFile = new ValidationTriggerFile(tempDir);
        var triggerPath = triggerFile.getTriggerPath();
        Files.createDirectories(triggerPath.getParent());
        long timestamp = System.currentTimeMillis();
        // write with deliberate leading/trailing whitespace on each field.
        Files.writeString(triggerPath, "  " + COMMIT_SHA + " | " + BRANCH + " | req-id | " + timestamp + "  ");

        var info = triggerFile.checkAndClearTrigger();

        assertNotNull(info);
        assertEquals(COMMIT_SHA, info.getCommitSha(), "commit SHA must be trimmed");
        assertEquals(BRANCH, info.getBranch(), "branch must be trimmed");
        assertEquals("req-id", info.getRequestId(), "request ID must be trimmed");
        assertEquals(timestamp, info.getTimestamp());
    }

    /**
     * Verifies that the legacy two-field format ({@code commitSha|branch}) is accepted and
     * that a fresh request identifier and current timestamp are synthesized for the missing
     * fields. This ensures that repositories using an older hook version are not broken
     * after an upgrade.
     */
    @Test
    @DisplayName("Accepts the legacy two-field format and synthesizes a request identifier")
    void handlesLegacyTwoFieldFormat(@TempDir Path tempDir) throws IOException {
        var triggerFile = new ValidationTriggerFile(tempDir);
        var triggerPath = triggerFile.getTriggerPath();
        Files.createDirectories(triggerPath.getParent());
        // write the old two-field format produced by earlier hook versions.
        Files.writeString(triggerPath, COMMIT_SHA + "|" + BRANCH);

        var info = triggerFile.checkAndClearTrigger();

        assertNotNull(info, "legacy format must still be parsed successfully");
        assertEquals(COMMIT_SHA, info.getCommitSha());
        assertEquals(BRANCH, info.getBranch());
        // a request identifier must be synthesized so the watcher flow is unchanged.
        assertNotNull(info.getRequestId(),
                "a request identifier must be generated for legacy triggers");
        assertFalse(info.getRequestId().isEmpty());
        assertTrue(info.getTimestamp() > 0);
    }

    // -------------------------------------------------------------------------
    // checkAndClearTrigger — resilience against malformed content
    // -------------------------------------------------------------------------

    /**
     * Verifies that a file with an unrecognized number of fields is discarded and deleted.
     * This prevents the watcher from looping indefinitely on a permanently unreadable file.
     */
    @Test
    @DisplayName("Discards and deletes a file with an unrecognized number of fields")
    void handlesUnrecognizedFieldCount(@TempDir Path tempDir) throws IOException {
        var triggerFile = new ValidationTriggerFile(tempDir);
        var triggerPath = triggerFile.getTriggerPath();
        Files.createDirectories(triggerPath.getParent());
        // write a file with no delimiter at all, which matches neither format.
        Files.writeString(triggerPath, "invalid-content-no-delimiter");

        assertNull(triggerFile.checkAndClearTrigger(),
                "an unrecognized field count must result in null");
        assertFalse(Files.exists(triggerPath),
                "the malformed trigger file must be deleted");
    }

    /**
     * Verifies that empty required fields (commit SHA, branch, or request ID) cause the
     * trigger to be discarded and the file to be deleted. An empty field cannot be used
     * to run a meaningful validation.
     */
    @Test
    @DisplayName("Discards and deletes a file with any empty required field")
    void handlesEmptyRequiredFields(@TempDir Path tempDir) throws IOException {
        var triggerFile = new ValidationTriggerFile(tempDir);
        var triggerPath = triggerFile.getTriggerPath();
        Files.createDirectories(triggerPath.getParent());
        long ts = System.currentTimeMillis();

        // empty commit SHA.
        Files.writeString(triggerPath, "|" + BRANCH + "|req-id|" + ts);
        assertNull(triggerFile.checkAndClearTrigger(), "empty commit SHA must be rejected");
        assertFalse(Files.exists(triggerPath), "file with empty commit SHA must be deleted");

        // empty branch.
        Files.writeString(triggerPath, COMMIT_SHA + "||req-id|" + ts);
        assertNull(triggerFile.checkAndClearTrigger(), "empty branch must be rejected");
        assertFalse(Files.exists(triggerPath), "file with empty branch must be deleted");

        // empty request ID.
        Files.writeString(triggerPath, COMMIT_SHA + "|" + BRANCH + "||" + ts);
        assertNull(triggerFile.checkAndClearTrigger(), "empty request ID must be rejected");
        assertFalse(Files.exists(triggerPath), "file with empty request ID must be deleted");
    }

    /**
     * Verifies that a non-numeric timestamp field does not cause an exception. The
     * implementation falls back to the current time, so the trigger is still processed
     * and the returned timestamp is within the wall clock window of the call.
     */
    @Test
    @DisplayName("Falls back to current time when the timestamp field cannot be parsed")
    void handlesInvalidTimestampWithFallback(@TempDir Path tempDir) throws IOException {
        var triggerFile = new ValidationTriggerFile(tempDir);
        var triggerPath = triggerFile.getTriggerPath();
        Files.createDirectories(triggerPath.getParent());
        Files.writeString(triggerPath, COMMIT_SHA + "|" + BRANCH + "|req-id|not-a-timestamp");

        long before = System.currentTimeMillis();
        var info = triggerFile.checkAndClearTrigger();
        long after = System.currentTimeMillis();

        assertNotNull(info, "a malformed timestamp must not discard an otherwise valid trigger");
        assertTrue(info.getTimestamp() >= before && info.getTimestamp() <= after,
                "the fallback timestamp must be within the wall clock window of the call");
    }

    // -------------------------------------------------------------------------
    // exists / getTriggerPath
    // -------------------------------------------------------------------------

    /**
     * Verifies that {@link ValidationTriggerFile#getTriggerPath()} returns the path under
     * the {@code .vitruvius} directory that the hook uses to write the trigger file.
     */
    @Test
    @DisplayName("Returns the correct trigger file path under the .vitruvius directory")
    void getTriggerPathReturnsCorrectPath(@TempDir Path tempDir) {
        var triggerFile = new ValidationTriggerFile(tempDir);
        assertEquals(tempDir.resolve(".vitruvius").resolve("validate-trigger"),
                triggerFile.getTriggerPath());
    }

    // -------------------------------------------------------------------------
    // TriggerInfo
    // -------------------------------------------------------------------------

    /**
     * Verifies that {@link ValidationTriggerFile.TriggerInfo} stores all provided fields
     * correctly and rejects null values for the required string fields. Both aspects are
     * tested together because they describe the same construction contract.
     */
    @Test
    @DisplayName("TriggerInfo stores all fields correctly and rejects null string fields")
    void triggerInfoConstructionAndNullRejection() {
        long timestamp = System.currentTimeMillis();
        var info = new ValidationTriggerFile.TriggerInfo(COMMIT_SHA, BRANCH, "req-id", timestamp);

        // all provided values must be accessible through the getters.
        assertEquals(COMMIT_SHA, info.getCommitSha());
        assertEquals(BRANCH, info.getBranch());
        assertEquals("req-id", info.getRequestId());
        assertEquals(timestamp, info.getTimestamp());

        // null values for any string field must be rejected at construction time.
        assertThrows(NullPointerException.class,
                () -> new ValidationTriggerFile.TriggerInfo(null, BRANCH, "req-id", timestamp),
                "null commit SHA must be rejected");
        assertThrows(NullPointerException.class,
                () -> new ValidationTriggerFile.TriggerInfo(COMMIT_SHA, null, "req-id", timestamp),
                "null branch must be rejected");
        assertThrows(NullPointerException.class,
                () -> new ValidationTriggerFile.TriggerInfo(COMMIT_SHA, BRANCH, null, timestamp),
                "null request identifier must be rejected");
    }

    /**
     * Verifies that {@link ValidationTriggerFile.TriggerInfo#toString()} includes the short
     * commit SHA, branch, request identifier, and timestamp, all in a form that is
     * identifiable in log output.
     */
    @Test
    @DisplayName("TriggerInfo toString includes short commit SHA, branch, request identifier, and timestamp")
    void triggerInfoToStringIncludesAllFields() {
        long timestamp = 1707584000000L;
        var info = new ValidationTriggerFile.TriggerInfo(
                COMMIT_SHA, BRANCH, "test-uuid-5678", timestamp);

        String str = info.toString();

        // the short SHA (first seven characters) must appear.
        assertTrue(str.contains("abc1234"), "toString must include the short commit SHA");
        assertTrue(str.contains(BRANCH), "toString must include the branch");
        assertTrue(str.contains("test-uuid-5678"), "toString must include the request identifier");
        assertTrue(str.contains(String.valueOf(timestamp)), "toString must include the timestamp");
    }
}