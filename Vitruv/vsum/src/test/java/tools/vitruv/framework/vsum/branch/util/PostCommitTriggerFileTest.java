package tools.vitruv.framework.vsum.branch.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link PostCommitTriggerFile}.
 *
 * <p>Tests cover the four-field format ({@code commitSha|branch|requestId|timestamp}),
 * the create-and-consume lifecycle, null rejection for required arguments,
 * whitespace trimming, resilience against malformed content, and the
 * {@link PostCommitTriggerFile.TriggerInfo} construction contract.
 */
class PostCommitTriggerFileTest {

    private static final String SHA = "abc1234567890abcdef1234567890abcdef12345";
    private static final String BRANCH = "feature-y";

    @Test
    @DisplayName("Creates trigger file with correct four-field format, parent directory, and returned request identifier")
    void createsTriggerFileWithCorrectFormatAndDirectory(@TempDir Path tempDir) throws IOException {
        var triggerFile = new PostCommitTriggerFile(tempDir);

        assertFalse(Files.exists(tempDir.resolve(".vitruvius")));

        String requestId = triggerFile.createTrigger(SHA, BRANCH);

        assertTrue(Files.isDirectory(tempDir.resolve(".vitruvius")),
                "parent directory must be created automatically");
        assertTrue(triggerFile.exists(), "trigger file must exist after creation");

        String content = Files.readString(triggerFile.getTriggerPath());
        String[] parts = content.split("\\|");
        assertEquals(4, parts.length, "trigger file must contain four pipe-separated fields");
        assertEquals(SHA, parts[0]);
        assertEquals(BRANCH, parts[1]);
        assertEquals(requestId, parts[2],
                "the returned request identifier must match the value written to the file");
        assertDoesNotThrow(() -> Long.parseLong(parts[3]),
                "the fourth field must be a parseable timestamp");
    }

    @Test
    @DisplayName("Returns a unique request identifier for each trigger creation")
    void returnsUniqueRequestIdPerTrigger(@TempDir Path tempDir) throws IOException {
        var triggerFile = new PostCommitTriggerFile(tempDir);

        String requestId1 = triggerFile.createTrigger(SHA, BRANCH);
        triggerFile.checkAndClearTrigger();
        String requestId2 = triggerFile.createTrigger(SHA, "develop");

        assertNotEquals(requestId1, requestId2);
    }

    @Test
    @DisplayName("Rejects null commit SHA or null branch")
    void createTriggerRejectsNullArguments(@TempDir Path tempDir) {
        var triggerFile = new PostCommitTriggerFile(tempDir);

        assertThrows(NullPointerException.class,
                () -> triggerFile.createTrigger(null, BRANCH));
        assertThrows(NullPointerException.class,
                () -> triggerFile.createTrigger(SHA, null));
    }

    @Test
    @DisplayName("Returns trigger info with all fields and deletes the file on consumption")
    void checkAndClearReturnsTriggerInfoAndDeletesFile(@TempDir Path tempDir) throws IOException {
        var triggerFile = new PostCommitTriggerFile(tempDir);
        String requestId = triggerFile.createTrigger(SHA, BRANCH);

        var info = triggerFile.checkAndClearTrigger();

        assertNotNull(info);
        assertEquals(SHA, info.getCommitSha());
        assertEquals(BRANCH, info.getBranch());
        assertEquals(requestId, info.getRequestId());
        assertTrue(info.getTimestamp() > 0 && info.getTimestamp() <= System.currentTimeMillis());
        assertFalse(triggerFile.exists(), "trigger file must be deleted after consumption");
    }

    @Test
    @DisplayName("Returns null when no trigger file exists")
    void checkAndClearReturnsNullWhenAbsent(@TempDir Path tempDir) {
        assertNull(new PostCommitTriggerFile(tempDir).checkAndClearTrigger());
    }

    @Test
    @DisplayName("Processes multiple sequential triggers correctly with unique request identifiers")
    void handlesMultipleSequentialTriggers(@TempDir Path tempDir) throws IOException {
        var triggerFile = new PostCommitTriggerFile(tempDir);

        String id1 = triggerFile.createTrigger(SHA, BRANCH);
        var info1 = triggerFile.checkAndClearTrigger();
        assertNotNull(info1);
        assertEquals(id1, info1.getRequestId());
        assertFalse(triggerFile.exists());

        String id2 = triggerFile.createTrigger(SHA, "develop");
        var info2 = triggerFile.checkAndClearTrigger();
        assertNotNull(info2);
        assertEquals("develop", info2.getBranch());
        assertEquals(id2, info2.getRequestId());
        assertNotEquals(id1, id2);
    }

    @Test
    @DisplayName("Trims whitespace from all fields when reading the trigger file")
    void checkAndClearTrimsWhitespaceFromFields(@TempDir Path tempDir) throws IOException {
        var triggerFile = new PostCommitTriggerFile(tempDir);
        var triggerPath = triggerFile.getTriggerPath();
        Files.createDirectories(triggerPath.getParent());
        long timestamp = System.currentTimeMillis();
        Files.writeString(triggerPath,
                "  " + SHA + " | " + BRANCH + " | req-id | " + timestamp + "  ");

        var info = triggerFile.checkAndClearTrigger();

        assertNotNull(info);
        assertEquals(SHA, info.getCommitSha());
        assertEquals(BRANCH, info.getBranch());
        assertEquals("req-id", info.getRequestId());
        assertEquals(timestamp, info.getTimestamp());
    }

    @Test
    @DisplayName("Discards and deletes a file with an unrecognized number of fields")
    void handlesUnrecognizedFieldCount(@TempDir Path tempDir) throws IOException {
        var triggerFile = new PostCommitTriggerFile(tempDir);
        var triggerPath = triggerFile.getTriggerPath();
        Files.createDirectories(triggerPath.getParent());
        Files.writeString(triggerPath, "no-delimiter-here");

        assertNull(triggerFile.checkAndClearTrigger());
        assertFalse(Files.exists(triggerPath), "malformed file must be deleted");
    }

    @Test
    @DisplayName("Discards and deletes a file with any empty required field")
    void handlesEmptyRequiredFields(@TempDir Path tempDir) throws IOException {
        var triggerFile = new PostCommitTriggerFile(tempDir);
        var triggerPath = triggerFile.getTriggerPath();
        Files.createDirectories(triggerPath.getParent());
        long ts = System.currentTimeMillis();

        // empty commit SHA
        Files.writeString(triggerPath, "|" + BRANCH + "|req-id|" + ts);
        assertNull(triggerFile.checkAndClearTrigger());
        assertFalse(Files.exists(triggerPath));

        // empty branch
        Files.writeString(triggerPath, SHA + "||req-id|" + ts);
        assertNull(triggerFile.checkAndClearTrigger());
        assertFalse(Files.exists(triggerPath));

        // empty request ID
        Files.writeString(triggerPath, SHA + "|" + BRANCH + "||" + ts);
        assertNull(triggerFile.checkAndClearTrigger());
        assertFalse(Files.exists(triggerPath));
    }

    @Test
    @DisplayName("Falls back to current time when the timestamp field cannot be parsed")
    void handlesInvalidTimestampWithFallback(@TempDir Path tempDir) throws IOException {
        var triggerFile = new PostCommitTriggerFile(tempDir);
        var triggerPath = triggerFile.getTriggerPath();
        Files.createDirectories(triggerPath.getParent());
        Files.writeString(triggerPath, SHA + "|" + BRANCH + "|req-id|not-a-number");

        long before = System.currentTimeMillis();
        var info = triggerFile.checkAndClearTrigger();
        long after = System.currentTimeMillis();

        assertNotNull(info);
        assertTrue(info.getTimestamp() >= before && info.getTimestamp() <= after,
                "fallback timestamp must be within the current wall clock window");
    }

    @Test
    @DisplayName("Returns the correct trigger file path under .vitruvius")
    void getTriggerPathReturnsCorrectPath(@TempDir Path tempDir) {
        assertEquals(
                tempDir.resolve(".vitruvius").resolve("post-commit-trigger"),
                new PostCommitTriggerFile(tempDir).getTriggerPath());
    }

    @Test
    @DisplayName("TriggerInfo stores all fields correctly and rejects null commit SHA, branch, and request identifier")
    void triggerInfoConstructionAndNullRejection() {
        long ts = System.currentTimeMillis();
        var info = new PostCommitTriggerFile.TriggerInfo(SHA, BRANCH, "req-id", ts);

        assertEquals(SHA, info.getCommitSha());
        assertEquals(BRANCH, info.getBranch());
        assertEquals("req-id", info.getRequestId());
        assertEquals(ts, info.getTimestamp());

        assertThrows(NullPointerException.class,
                () -> new PostCommitTriggerFile.TriggerInfo(null, BRANCH, "req-id", ts));
        assertThrows(NullPointerException.class,
                () -> new PostCommitTriggerFile.TriggerInfo(SHA, null, "req-id", ts));
        assertThrows(NullPointerException.class,
                () -> new PostCommitTriggerFile.TriggerInfo(SHA, BRANCH, null, ts));
    }

    @Test
    @DisplayName("TriggerInfo toString includes short commit SHA, branch, request identifier, and timestamp")
    void triggerInfoToStringIncludesAllFields() {
        long ts = 1707584000000L;
        var info = new PostCommitTriggerFile.TriggerInfo(SHA, BRANCH, "req-uuid-7777", ts);
        String str = info.toString();

        assertTrue(str.contains("abc1234"), "toString must include the short commit SHA");
        assertTrue(str.contains(BRANCH), "toString must include the branch");
        assertTrue(str.contains("req-uuid-7777"), "toString must include the request identifier");
        assertTrue(str.contains(String.valueOf(ts)), "toString must include the timestamp");
    }

    @Test
    @DisplayName("TriggerInfo equals and hashCode are consistent")
    void triggerInfoEqualsAndHashCode() {
        long ts = 1707584000000L;
        var a = new PostCommitTriggerFile.TriggerInfo(SHA, BRANCH, "req-1", ts);
        var b = new PostCommitTriggerFile.TriggerInfo(SHA, BRANCH, "req-1", ts);
        var c = new PostCommitTriggerFile.TriggerInfo(SHA, BRANCH, "req-2", ts);

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
    }
}
