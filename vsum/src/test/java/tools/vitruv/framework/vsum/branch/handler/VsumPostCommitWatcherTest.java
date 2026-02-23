package tools.vitruv.framework.vsum.branch.handler;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import tools.vitruv.framework.vsum.branch.util.PostCommitTriggerFile;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link VsumPostCommitWatcher}.
 *
 * <p>These tests start the real watcher thread and interact with the real
 * {@link PostCommitTriggerFile} on a temporary directory. No mock VSUM is needed
 * because {@link VsumPostCommitWatcher} only depends on the repository root -
 * changelog generation is pure computation with no VSUM access.
 *
 * <p>The watcher is fire-and-forget: unlike {@link VsumValidationWatcher}, there
 * are no result files to poll for. Tests instead wait for the changelog file to
 * appear in {@code .vitruvius/changelogs/}.
 */
class VsumPostCommitWatcherTest {

    private static final String COMMIT_SHA = "abc1234567890abcdef1234567890abcdef12345";
    private static final String BRANCH     = "main";

    
    // lifecycle
    

    /**
     * Verifies the basic start/stop lifecycle: the watcher must not be running before
     * start, must be running after start, and must stop cleanly.
     */
    @Test
    @DisplayName("Starts and stops cleanly, reflecting running state correctly")
    void startsAndStopsCleanly(@TempDir Path tempDir) {
        var watcher = new VsumPostCommitWatcher(tempDir);

        assertFalse(watcher.isRunning());
        watcher.start();
        assertTrue(watcher.isRunning());
        watcher.stop();
        assertFalse(watcher.isRunning());
    }

    /**
     * Verifies that calling start() on an already-running watcher throws an exception.
     * A second start would create a second background thread polling the same trigger file,
     * which could generate duplicate changelog entries.
     */
    @Test
    @DisplayName("Throws an exception when started while already running")
    void throwsExceptionWhenStartingTwice(@TempDir Path tempDir) {
        var watcher = new VsumPostCommitWatcher(tempDir);
        watcher.start();
        try {
            assertThrows(IllegalStateException.class, watcher::start);
        } finally {
            watcher.stop();
        }
    }

    /**
     * Verifies that calling stop() on a watcher that was never started does not throw.
     * This makes it safe to call stop() in cleanup code regardless of whether start()
     * was ever reached.
     */
    @Test
    @DisplayName("Stopping a watcher that was never started completes without throwing")
    void stoppingNonRunningWatcherDoesNotThrow(@TempDir Path tempDir) {
        var watcher = new VsumPostCommitWatcher(tempDir);
        assertDoesNotThrow(watcher::stop);
    }

    /**
     * Verifies that stop() returns within a reasonable time. The watcher thread sleeps
     * for at most POLL_INTERVAL_MS between cycles, so shutdown must complete well within 3s.
     */
    @Test
    @DisplayName("Stop completes within 3 seconds")
    void stopsWithinReasonableTime(@TempDir Path tempDir) {
        var watcher = new VsumPostCommitWatcher(tempDir);
        watcher.start();

        long startTime = System.currentTimeMillis();
        watcher.stop();
        long duration = System.currentTimeMillis() - startTime;

        assertFalse(watcher.isRunning());
        assertTrue(duration < 3000,
                "stop() must complete within 3 seconds but took " + duration + "ms");
    }

    
    // trigger → changelog round-trip
    

    /**
     * Verifies the core end-to-end flow: when a trigger file is created, the watcher
     * detects it, generates the changelog, writes it to
     * {@code .vitruvius/changelogs/<shortSha>.txt}, and deletes the trigger file.
     */
    @Test
    @DisplayName("Detects a trigger and writes the changelog file")
    void detectsTriggerAndWritesChangelog(@TempDir Path tempDir) throws Exception {
        var watcher     = new VsumPostCommitWatcher(tempDir);
        var triggerFile = new PostCommitTriggerFile(tempDir);
        Path changelog  = changelogPath(tempDir, COMMIT_SHA);

        watcher.start();
        try {
            triggerFile.createTrigger(COMMIT_SHA, BRANCH);
            waitForFile(changelog, 3000);

            assertTrue(Files.exists(changelog),
                    "changelog file must be written after the post-commit trigger is processed");
            assertFalse(triggerFile.exists(),
                    "trigger file must be deleted after the watcher processes it");
        } finally {
            watcher.stop();
        }
    }

    /**
     * Verifies that the changelog file written by the watcher contains the real commit
     * SHA from the trigger. This is the traceability requirement: the changelog on disk
     * must match the commit visible in {@code git log}.
     */
    @Test
    @DisplayName("Changelog file contains the real commit SHA from the trigger")
    void changelogContainsRealCommitSha(@TempDir Path tempDir) throws Exception {
        var watcher     = new VsumPostCommitWatcher(tempDir);
        var triggerFile = new PostCommitTriggerFile(tempDir);
        Path changelog  = changelogPath(tempDir, COMMIT_SHA);

        watcher.start();
        try {
            triggerFile.createTrigger(COMMIT_SHA, BRANCH);
            waitForFile(changelog, 3000);

            String content = Files.readString(changelog);
            assertTrue(content.contains(COMMIT_SHA),
                    "changelog must contain the real commit SHA, but was:\n" + content);
            assertTrue(content.contains(BRANCH),
                    "changelog must contain the branch name, but was:\n" + content);
        } finally {
            watcher.stop();
        }
    }

    /**
     * Verifies that each trigger produces a distinct changelog file named after its own
     * short SHA. Two sequential triggers must not overwrite each other.
     */
    @Test
    @DisplayName("Two sequential triggers produce two distinct changelog files")
    void twoSequentialTriggersProduceTwoChangelogs(@TempDir Path tempDir) throws Exception {
        var watcher     = new VsumPostCommitWatcher(tempDir);
        var triggerFile = new PostCommitTriggerFile(tempDir);
        String sha1     = "aaa1111111111111111111111111111111111111";
        String sha2     = "bbb2222222222222222222222222222222222222";
        Path changelog1 = changelogPath(tempDir, sha1);
        Path changelog2 = changelogPath(tempDir, sha2);

        watcher.start();
        try {
            // process first trigger fully before creating the second.
            triggerFile.createTrigger(sha1, BRANCH);
            waitForFile(changelog1, 3000);

            triggerFile.createTrigger(sha2, BRANCH);
            waitForFile(changelog2, 3000);

            assertTrue(Files.exists(changelog1),
                    "first changelog must exist after processing");
            assertTrue(Files.exists(changelog2),
                    "second changelog must exist after processing");
            assertNotEquals(changelog1, changelog2,
                    "each commit must produce a distinct changelog file");

            // each file must contain only its own SHA.
            String content1 = Files.readString(changelog1);
            String content2 = Files.readString(changelog2);
            assertTrue(content1.contains(sha1),
                    "first changelog must contain sha1");
            assertTrue(content2.contains(sha2),
                    "second changelog must contain sha2");
            assertFalse(content1.contains(sha2),
                    "first changelog must not contain sha2");
            assertFalse(content2.contains(sha1),
                    "second changelog must not contain sha1");
        } finally {
            watcher.stop();
        }
    }

    /**
     * Verifies that the watcher remains idle when no trigger file is present. No
     * changelog files must be created spontaneously without a trigger.
     */
    @Test
    @DisplayName("Watcher remains idle and produces no changelog without a trigger")
    void watcherIdleWithNoTrigger(@TempDir Path tempDir) throws Exception {
        var watcher    = new VsumPostCommitWatcher(tempDir);
        Path changelog = changelogPath(tempDir, COMMIT_SHA);

        watcher.start();
        try {
            // wait two poll cycles with no trigger written.
            Thread.sleep(1200);

            assertFalse(Files.exists(changelog),
                    "no changelog must be written without a trigger");
        } finally {
            watcher.stop();
        }
    }

    
    // helpers
    

    /**
     * Returns the expected path for the changelog file of the given commit SHA.
     * Mirrors the path that {@link VsumPostCommitWatcher} writes to.
     */
    private static Path changelogPath(Path repoRoot, String commitSha) {
        return repoRoot.resolve(".vitruvius")
                .resolve("changelogs")
                .resolve(commitSha.substring(0, 7) + ".txt");
    }

    /**
     * Polls until the given file exists or the timeout expires. Fails the test if the
     * file does not appear within the timeout.
     */
    private static void waitForFile(Path path, long timeoutMs) throws InterruptedException {
        long deadline = System.currentTimeMillis() + timeoutMs;
        while (System.currentTimeMillis() < deadline) {
            if (Files.exists(path)) {
                return;
            }
            Thread.sleep(50);
        }
        fail("Expected file not written within " + timeoutMs + "ms: " + path);
    }
}