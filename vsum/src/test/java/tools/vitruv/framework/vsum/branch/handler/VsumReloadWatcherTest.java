package tools.vitruv.framework.vsum.branch.handler;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import tools.vitruv.framework.vsum.VirtualModel;
import tools.vitruv.framework.vsum.branch.util.ReloadTriggerFile;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link VsumReloadWatcher}.
 *
 * <p>These tests start the real watcher thread and use {@link Thread#sleep} to give it timeto process triggers. 
 * This approach is intentionally simple and avoids introducing a polling library dependency.
 *
 * <p>The VirtualModel is mocked in all tests so that the actual Vitruvius initialization is not required. Only the watcher's interaction with the model is verified.
 */
class VsumReloadWatcherTest {

    private static final String BRANCH = "feature-vcs";

    /**
     * Verifies the basic start/stop lifecycle: the watcher must not be running before start, must be running after start, and must not be running after stop.
     */
    @Test
    @DisplayName("Starts and stops cleanly, reflecting running state correctly")
    void startsAndStopsCleanly(@TempDir Path tempDir) {
        var mockVsum = mock(VirtualModel.class);
        var watcher = new VsumReloadWatcher(mockVsum, tempDir);

        assertFalse(watcher.isRunning(), "watcher must not be running before start() is called");
        watcher.start();
        assertTrue(watcher.isRunning(), "watcher must be running after start()");
        watcher.stop();
        assertFalse(watcher.isRunning(), "watcher must not be running after stop()");
    }

    /**
     * Verifies that calling start() on an already-running watcher throws an exception.
     * Allowing a second start would create a second background thread polling the same trigger file, which would lead to duplicate reloads.
     */
    @Test
    @DisplayName("Throws an exception when started while already running")
    void throwsExceptionWhenStartingTwice(@TempDir Path tempDir) {
        var mockVsum = mock(VirtualModel.class);
        var watcher = new VsumReloadWatcher(mockVsum, tempDir);

        watcher.start();
        try {
            assertThrows(IllegalStateException.class, watcher::start, "a second start() call on a running watcher must throw");
        } finally {
            // always stop to avoid leaving a background thread running after the test.
            watcher.stop();
        }
    }

    /**
     * Verifies that calling stop() on a watcher that was never started does not throw.
     * This makes it safe to call stop() in cleanup code regardless of whether start() was ever reached.
     */
    @Test
    @DisplayName("Stopping a watcher that was never started completes without throwing")
    void stoppingNonRunningWatcherDoesNotThrow(@TempDir Path tempDir) {
        var mockVsum = mock(VirtualModel.class);
        var watcher = new VsumReloadWatcher(mockVsum, tempDir);

        // stop() must be idempotent and safe to call even before start().
        assertDoesNotThrow(watcher::stop);
    }

    /**
     * Verifies that stop() returns within a reasonable time so that the application does not hang on shutdown.
     * The watcher thread sleeps for at most CHECK_INTERVAL_MS (500ms) between polls, so stop() should return well within 3 seconds.
     */
    @Test
    @DisplayName("Stop completes within 3 seconds")
    void stopsWithinReasonableTime(@TempDir Path tempDir) {
        var mockVsum = mock(VirtualModel.class);
        var watcher = new VsumReloadWatcher(mockVsum, tempDir);
        watcher.start();

        long startTime = System.currentTimeMillis();
        watcher.stop();
        long duration = System.currentTimeMillis() - startTime;

        assertTrue(duration < 3000, "stop() must return within 3 seconds, but took " + duration + "ms");
    }

    /**
     * Verifies that when a trigger file is created, the watcher detects it within the next poll cycle,
     * reloads the VirtualModel exactly once, and deletes the trigger file.
     */
    @Test
    @DisplayName("Detects a trigger file and reloads the VirtualModel exactly once")
    void detectsTriggerFileAndReloads(@TempDir Path tempDir) throws Exception {
        var mockVsum = mock(VirtualModel.class);
        var watcher = new VsumReloadWatcher(mockVsum, tempDir);
        var triggerFile = new ReloadTriggerFile(tempDir);

        watcher.start();
        // wait for the watcher to complete its first poll cycle before writing the trigger, so the timing does not depend on the thread scheduler.
        Thread.sleep(100);

        triggerFile.createTrigger(BRANCH);
        // wait long enough for the watcher to pick up the trigger (poll interval is 500ms).
        Thread.sleep(1500);

        verify(mockVsum, times(1)).reload();
        assertFalse(triggerFile.exists(), "trigger file must be deleted after the reload completes");

        watcher.stop();
    }

    /**
     * Verifies that the watcher does not call reload when no trigger file has been created.
     * The VirtualModel must remain untouched between poll cycles.
     */
    @Test
    @DisplayName("Does not reload the VirtualModel when no trigger file exists")
    void doesNotReloadWithoutTrigger(@TempDir Path tempDir) throws Exception {
        var mockVsum = mock(VirtualModel.class);
        var watcher = new VsumReloadWatcher(mockVsum, tempDir);

        watcher.start();
        // wait for several poll cycles to confirm the watcher polls without side effects.
        Thread.sleep(1500);

        verify(mockVsum, never()).reload();

        watcher.stop();
    }

    /**
     * Verifies that three trigger files created sequentially each result in exactly one VirtualModel reload.
     * This confirms that the watcher correctly processes one trigger per file and does not batch or skip any.
     */
    @Test
    @DisplayName("Processes multiple sequential triggers, reloading once per trigger")
    void handlesMultipleSequentialReloads(@TempDir Path tempDir) throws Exception {
        var mockVsum = mock(VirtualModel.class);
        var watcher = new VsumReloadWatcher(mockVsum, tempDir);
        var triggerFile = new ReloadTriggerFile(tempDir);

        watcher.start();
        Thread.sleep(100);

        // create each trigger only after the previous one has been consumed, to ensure they are processed sequentially and not overwritten.
        triggerFile.createTrigger("main");
        Thread.sleep(1500);

        triggerFile.createTrigger("develop");
        Thread.sleep(1500);

        triggerFile.createTrigger("feature");
        Thread.sleep(1500);

        // three triggers must produce exactly three reload calls.
        verify(mockVsum, times(3)).reload();

        watcher.stop();
    }

    /**
     * Verifies that when the VirtualModel throws an exception during reload, the watcher continues running and successfully processes the next trigger.
     * The watcher must never crash or stop because of a reload failure.
     */
    @Test
    @DisplayName("Continues running and processes subsequent triggers after a reload failure")
    void continuesRunningAfterReloadFailure(@TempDir Path tempDir) throws Exception {
        var mockVsum = mock(VirtualModel.class);
        // configure the first reload call to fail and the second to succeed.
        doThrow(new RuntimeException("Simulated reload failure")).doNothing().when(mockVsum).reload();

        var watcher = new VsumReloadWatcher(mockVsum, tempDir);
        var triggerFile = new ReloadTriggerFile(tempDir);

        watcher.start();
        Thread.sleep(100);

        triggerFile.createTrigger("branch-1");
        Thread.sleep(1500);

        // the watcher must still be running after the first reload threw an exception.
        assertTrue(watcher.isRunning(), "watcher must keep running after a reload failure");

        triggerFile.createTrigger("branch-2");
        Thread.sleep(1500);

        // both reload calls must have been attempted: the first failed, the second succeeded.
        verify(mockVsum, times(2)).reload();

        watcher.stop();
    }

    /**
     * Verifies that the lock mechanism prevents two concurrent reloads from running at the same time.
     * Two triggers are created in quick succession while the first reload is slowed down.
     * Both must eventually be processed, but sequentially.
     * <p>Note: this test verifies eventual completion rather than strict ordering, because
     * the exact interleaving depends on the thread scheduler. The important guarantee is
     * that both reloads complete and neither is silently dropped.
     */
    @Test
    @DisplayName("Lock file ensures concurrent triggers are processed sequentially, not dropped")
    void lockFilePreventsConurrentReloads(@TempDir Path tempDir) throws Exception {
        var mockVsum = mock(VirtualModel.class);
        // slow down each reload so there is a window for the second trigger to arrive
        // while the first reload is still holding the lock.
        doAnswer(invocation -> {
            Thread.sleep(500);
            return null;
        }).when(mockVsum).reload();

        var watcher = new VsumReloadWatcher(mockVsum, tempDir);
        var triggerFile = new ReloadTriggerFile(tempDir);

        watcher.start();
        Thread.sleep(100);

        // write the first trigger and wait long enough for the watcher to pick it up and start the slow reload before writing the second trigger.
        // the poll interval is 500ms, so any time longer als 500ms is sufficient for the first trigger to be consumed and the reload to begin.

        triggerFile.createTrigger("branch-1");
        Thread.sleep(700);
        triggerFile.createTrigger("branch-2");

        // wait long enough for both triggers to be processed sequentially.
        // first reload: ~500ms + poll overhead. second: waits for lock + ~500ms.
        Thread.sleep(5000);

        // both reloads must have completed eventually, confirming that the re-queue mechanism prevents the second trigger from being silently dropped.
        verify(mockVsum, atLeast(2)).reload();

        watcher.stop();
    }
}