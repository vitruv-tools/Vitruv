package tools.vitruv.framework.vsum.branch.handler;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import tools.vitruv.framework.vsum.branch.util.PostCommitTriggerFile;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link VsumPostCommitWatcher}.
 *
 * <p>The watcher's sole responsibility is lifecycle management and trigger detection:
 * it polls for a {@link PostCommitTriggerFile}, clears it when found, and delegates
 * to {@link PostCommitHandler}. Tests verify these behaviors only — post-commit
 * actions are the responsibility of {@link PostCommitHandler} and tested separately.
 */
class VsumPostCommitWatcherTest {

    private static final String COMMIT_SHA = "abc1234567890abcdef1234567890abcdef12345";
    private static final String BRANCH = "main";

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
     * Verifies that calling start() on an already-running watcher throws.
     * A second start would create a second background thread polling the same trigger file.
     */
    @Test
    @DisplayName("Throws when started while already running")
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
     * Verifies that stop() on a watcher that was never started does not throw.
     * This makes it safe to call stop() in cleanup code unconditionally.
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
        assertTrue(duration < 3000, "stop() must complete within 3 seconds but took " + duration + "ms");
    }

    /**
     * Verifies that the watcher detects a trigger file and clears it within the poll interval.
     */
    @Test
    @DisplayName("Detects a trigger and clears the trigger file")
    void detectsTriggerAndClearsTriggerFile(@TempDir Path tempDir) throws Exception {
        var watcher = new VsumPostCommitWatcher(tempDir);
        var triggerFile = new PostCommitTriggerFile(tempDir);

        watcher.start();
        try {
            triggerFile.createTrigger(COMMIT_SHA, BRANCH);
            waitForTriggerCleared(triggerFile, 3000);
            assertFalse(triggerFile.exists(), "trigger file must be deleted after the watcher processes it");
        } finally {
            watcher.stop();
        }
    }

    /**
     * Verifies that two sequential triggers are both detected and cleared.
     */
    @Test
    @DisplayName("Two sequential triggers are both detected and cleared")
    void twoSequentialTriggersAreBothCleared(@TempDir Path tempDir) throws Exception {
        var watcher = new VsumPostCommitWatcher(tempDir);
        var triggerFile = new PostCommitTriggerFile(tempDir);
        String sha1 = "aaa1111111111111111111111111111111111111";
        String sha2 = "bbb2222222222222222222222222222222222222";

        watcher.start();
        try {
            triggerFile.createTrigger(sha1, BRANCH);
            waitForTriggerCleared(triggerFile, 3000);
            assertFalse(triggerFile.exists(), "trigger file must be cleared after first trigger");

            triggerFile.createTrigger(sha2, BRANCH);
            waitForTriggerCleared(triggerFile, 3000);
            assertFalse(triggerFile.exists(), "trigger file must be cleared after second trigger");
        } finally {
            watcher.stop();
        }
    }

    /**
     * Verifies that the watcher does not create any files when no trigger is written.
     */
    @Test
    @DisplayName("Watcher remains idle when no trigger is present")
    void watcherIdleWithNoTrigger(@TempDir Path tempDir) throws Exception {
        var watcher = new VsumPostCommitWatcher(tempDir);

        watcher.start();
        try {
            Thread.sleep(1200); // wait two poll cycles
            assertFalse(triggerFile(tempDir).exists(), "no trigger file must appear without an external write");
        } finally {
            watcher.stop();
        }
    }

    private static PostCommitTriggerFile triggerFile(Path repoRoot) {
        return new PostCommitTriggerFile(repoRoot);
    }

    /**
     * Polls until the trigger file no longer exists or the timeout expires.
     */
    private static void waitForTriggerCleared(PostCommitTriggerFile triggerFile, long timeoutMs) throws InterruptedException {
        long deadline = System.currentTimeMillis() + timeoutMs;
        while (System.currentTimeMillis() < deadline) {
            if (!triggerFile.exists()) {
                return;
            }
            Thread.sleep(50);
        }
        fail("Trigger file was not cleared within " + timeoutMs + "ms");
    }
}
