package tools.vitruv.framework.vsum.branch.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link MergeTriggerFile}.
 *
 * <p>Tests verify the five-field file format, the create-and-consume lifecycle,
 * null rejection for all three required arguments, whitespace trimming, resilience
 * against malformed content, and the {@link MergeTriggerFile.TriggerInfo} construction contract.
 */
class MergeTriggerFileTest {

    private static final String SHA = "abc1234567890abcdef1234567890abcdef12345";
    private static final String SOURCE = "feature-x";
    private static final String TARGET = "main";

    @Test
    @DisplayName("Creates trigger file with correct five-field format, parent directory, and returned request identifier")
    void createsTriggerFileWithCorrectFormatAndDirectory(@TempDir Path tempDir) throws IOException {
        var triggerFile = new MergeTriggerFile(tempDir);

        assertFalse(Files.exists(tempDir.resolve(".vitruvius")));

        String requestId = triggerFile.createTrigger(SHA, SOURCE, TARGET);

        assertTrue(Files.isDirectory(tempDir.resolve(".vitruvius")),
                "parent directory must be created automatically");
        assertTrue(triggerFile.exists(), "trigger file must exist after creation");

        String content = Files.readString(triggerFile.getTriggerPath());
        String[] parts = content.split("\\|");
        assertEquals(5, parts.length, "trigger file must contain five pipe-separated fields");
        assertEquals(SHA, parts[0]);
        assertEquals(SOURCE, parts[1]);
        assertEquals(TARGET, parts[2]);
        assertEquals(requestId, parts[3],
                "the returned request identifier must match the value written to the file");
        assertDoesNotThrow(() -> Long.parseLong(parts[4]),
                "the fifth field must be a parseable timestamp");
    }

    @Test
    @DisplayName("Returns a unique request identifier for each trigger creation")
    void returnsUniqueRequestIdPerTrigger(@TempDir Path tempDir) throws IOException {
        var triggerFile = new MergeTriggerFile(tempDir);

        String requestId1 = triggerFile.createTrigger(SHA, SOURCE, TARGET);
        triggerFile.checkAndClearTrigger();
        String requestId2 = triggerFile.createTrigger(SHA, "feature-y", TARGET);

        assertNotEquals(requestId1, requestId2,
                "each trigger creation must produce a distinct request identifier");
    }

    @Test
    @DisplayName("Rejects null merge commit SHA, source branch, or target branch")
    void createTriggerRejectsNullArguments(@TempDir Path tempDir) {
        var triggerFile = new MergeTriggerFile(tempDir);

        assertThrows(NullPointerException.class,
                () -> triggerFile.createTrigger(null, SOURCE, TARGET),
                "null merge commit SHA must be rejected");
        assertThrows(NullPointerException.class,
                () -> triggerFile.createTrigger(SHA, null, TARGET),
                "null source branch must be rejected");
        assertThrows(NullPointerException.class,
                () -> triggerFile.createTrigger(SHA, SOURCE, null),
                "null target branch must be rejected");
    }

    @Test
    @DisplayName("Returns trigger info with all fields and deletes the file on consumption")
    void checkAndClearReturnsTriggerInfoAndDeletesFile(@TempDir Path tempDir) throws IOException {
        var triggerFile = new MergeTriggerFile(tempDir);
        String requestId = triggerFile.createTrigger(SHA, SOURCE, TARGET);

        var info = triggerFile.checkAndClearTrigger();

        assertNotNull(info, "a valid trigger must return a non-null TriggerInfo");
        assertEquals(SHA, info.getMergeCommitSha());
        assertEquals(SOURCE, info.getSourceBranch());
        assertEquals(TARGET, info.getTargetBranch());
        assertEquals(requestId, info.getRequestId(),
                "the returned request identifier must match what was written");
        assertTrue(info.getTimestamp() > 0 && info.getTimestamp() <= System.currentTimeMillis());
        assertFalse(triggerFile.exists(), "trigger file must be deleted after consumption");
    }

    @Test
    @DisplayName("Returns null when no trigger file exists")
    void checkAndClearReturnsNullWhenAbsent(@TempDir Path tempDir) {
        var triggerFile = new MergeTriggerFile(tempDir);
        assertNull(triggerFile.checkAndClearTrigger());
    }

    @Test
    @DisplayName("Processes multiple sequential triggers correctly with unique request identifiers")
    void handlesMultipleSequentialTriggers(@TempDir Path tempDir) throws IOException {
        var triggerFile = new MergeTriggerFile(tempDir);

        String requestId1 = triggerFile.createTrigger(SHA, SOURCE, TARGET);
        var info1 = triggerFile.checkAndClearTrigger();
        assertNotNull(info1);
        assertEquals(SOURCE, info1.getSourceBranch());
        assertEquals(TARGET, info1.getTargetBranch());
        assertEquals(requestId1, info1.getRequestId());
        assertFalse(triggerFile.exists());

        String requestId2 = triggerFile.createTrigger(SHA, "feature-y", "develop");
        var info2 = triggerFile.checkAndClearTrigger();
        assertNotNull(info2);
        assertEquals("feature-y", info2.getSourceBranch());
        assertEquals("develop", info2.getTargetBranch());
        assertEquals(requestId2, info2.getRequestId());
        assertFalse(triggerFile.exists());

        assertNotEquals(requestId1, requestId2);
    }

    @Test
    @DisplayName("Trims whitespace from all fields when reading the trigger file")
    void checkAndClearTrimsWhitespaceFromFields(@TempDir Path tempDir) throws IOException {
        var triggerFile = new MergeTriggerFile(tempDir);
        var triggerPath = triggerFile.getTriggerPath();
        Files.createDirectories(triggerPath.getParent());
        long timestamp = System.currentTimeMillis();
        Files.writeString(triggerPath,
                "  " + SHA + " | " + SOURCE + " | " + TARGET + " | req-id | " + timestamp + "  ");

        var info = triggerFile.checkAndClearTrigger();

        assertNotNull(info);
        assertEquals(SHA, info.getMergeCommitSha(), "merge commit SHA must be trimmed");
        assertEquals(SOURCE, info.getSourceBranch(), "source branch must be trimmed");
        assertEquals(TARGET, info.getTargetBranch(), "target branch must be trimmed");
        assertEquals("req-id", info.getRequestId(), "request ID must be trimmed");
        assertEquals(timestamp, info.getTimestamp());
    }

    @Test
    @DisplayName("Discards and deletes a file with an unrecognized number of fields")
    void handlesUnrecognizedFieldCount(@TempDir Path tempDir) throws IOException {
        var triggerFile = new MergeTriggerFile(tempDir);
        var triggerPath = triggerFile.getTriggerPath();
        Files.createDirectories(triggerPath.getParent());
        Files.writeString(triggerPath, "only-one-field-no-delimiter");

        assertNull(triggerFile.checkAndClearTrigger(),
                "an unrecognized field count must result in null");
        assertFalse(Files.exists(triggerPath),
                "the malformed trigger file must be deleted");
    }

    @Test
    @DisplayName("Discards and deletes a file with any empty required field")
    void handlesEmptyRequiredFields(@TempDir Path tempDir) throws IOException {
        var triggerFile = new MergeTriggerFile(tempDir);
        var triggerPath = triggerFile.getTriggerPath();
        Files.createDirectories(triggerPath.getParent());
        long ts = System.currentTimeMillis();

        // empty merge commit SHA
        Files.writeString(triggerPath, "|" + SOURCE + "|" + TARGET + "|req-id|" + ts);
        assertNull(triggerFile.checkAndClearTrigger(), "empty merge commit SHA must be rejected");
        assertFalse(Files.exists(triggerPath));

        // empty source branch
        Files.writeString(triggerPath, SHA + "||" + TARGET + "|req-id|" + ts);
        assertNull(triggerFile.checkAndClearTrigger(), "empty source branch must be rejected");
        assertFalse(Files.exists(triggerPath));

        // empty target branch
        Files.writeString(triggerPath, SHA + "|" + SOURCE + "||req-id|" + ts);
        assertNull(triggerFile.checkAndClearTrigger(), "empty target branch must be rejected");
        assertFalse(Files.exists(triggerPath));

        // empty request ID
        Files.writeString(triggerPath, SHA + "|" + SOURCE + "|" + TARGET + "||" + ts);
        assertNull(triggerFile.checkAndClearTrigger(), "empty request ID must be rejected");
        assertFalse(Files.exists(triggerPath));
    }

    @Test
    @DisplayName("Falls back to current time when the timestamp field cannot be parsed")
    void handlesInvalidTimestampWithFallback(@TempDir Path tempDir) throws IOException {
        var triggerFile = new MergeTriggerFile(tempDir);
        var triggerPath = triggerFile.getTriggerPath();
        Files.createDirectories(triggerPath.getParent());
        Files.writeString(triggerPath, SHA + "|" + SOURCE + "|" + TARGET + "|req-id|not-a-timestamp");

        long before = System.currentTimeMillis();
        var info = triggerFile.checkAndClearTrigger();
        long after = System.currentTimeMillis();

        assertNotNull(info, "a malformed timestamp must not discard an otherwise valid trigger");
        assertTrue(info.getTimestamp() >= before && info.getTimestamp() <= after,
                "the fallback timestamp must be within the wall clock window of the call");
    }

    @Test
    @DisplayName("Returns the correct trigger file path under the .vitruvius directory")
    void getTriggerPathReturnsCorrectPath(@TempDir Path tempDir) {
        var triggerFile = new MergeTriggerFile(tempDir);
        assertEquals(tempDir.resolve(".vitruvius").resolve("merge-trigger"),
                triggerFile.getTriggerPath());
    }

    @Test
    @DisplayName("TriggerInfo stores all fields correctly and rejects null string fields")
    void triggerInfoConstructionAndNullRejection() {
        long timestamp = System.currentTimeMillis();
        var info = new MergeTriggerFile.TriggerInfo(SHA, SOURCE, TARGET, "req-id", timestamp);

        assertEquals(SHA, info.getMergeCommitSha());
        assertEquals(SOURCE, info.getSourceBranch());
        assertEquals(TARGET, info.getTargetBranch());
        assertEquals("req-id", info.getRequestId());
        assertEquals(timestamp, info.getTimestamp());

        assertThrows(NullPointerException.class,
                () -> new MergeTriggerFile.TriggerInfo(null, SOURCE, TARGET, "req-id", timestamp),
                "null merge commit SHA must be rejected");
        assertThrows(NullPointerException.class,
                () -> new MergeTriggerFile.TriggerInfo(SHA, null, TARGET, "req-id", timestamp),
                "null source branch must be rejected");
        assertThrows(NullPointerException.class,
                () -> new MergeTriggerFile.TriggerInfo(SHA, SOURCE, null, "req-id", timestamp),
                "null target branch must be rejected");
        assertThrows(NullPointerException.class,
                () -> new MergeTriggerFile.TriggerInfo(SHA, SOURCE, TARGET, null, timestamp),
                "null request identifier must be rejected");
    }

    @Test
    @DisplayName("TriggerInfo toString includes short commit SHA, source branch, target branch, request identifier, and timestamp")
    void triggerInfoToStringIncludesAllFields() {
        long timestamp = 1707584000000L;
        var info = new MergeTriggerFile.TriggerInfo(SHA, SOURCE, TARGET, "test-uuid-9999", timestamp);

        String str = info.toString();

        assertTrue(str.contains("abc1234"), "toString must include the short commit SHA");
        assertTrue(str.contains(SOURCE), "toString must include the source branch");
        assertTrue(str.contains(TARGET), "toString must include the target branch");
        assertTrue(str.contains("test-uuid-9999"), "toString must include the request identifier");
        assertTrue(str.contains(String.valueOf(timestamp)), "toString must include the timestamp");
    }

    @Test
    @DisplayName("TriggerInfo equals and hashCode are consistent")
    void triggerInfoEqualsAndHashCode() {
        long ts = 1707584000000L;
        var a = new MergeTriggerFile.TriggerInfo(SHA, SOURCE, TARGET, "req-1", ts);
        var b = new MergeTriggerFile.TriggerInfo(SHA, SOURCE, TARGET, "req-1", ts);
        var c = new MergeTriggerFile.TriggerInfo(SHA, SOURCE, TARGET, "req-2", ts);

        assertEquals(a, b, "two TriggerInfos with the same fields must be equal");
        assertEquals(a.hashCode(), b.hashCode(), "equal objects must have the same hash code");
        assertNotEquals(a, c, "TriggerInfos with different request IDs must not be equal");
    }
}
