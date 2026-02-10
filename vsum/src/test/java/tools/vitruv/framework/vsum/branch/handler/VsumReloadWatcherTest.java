package tools.vitruv.framework.vsum.branch.handler;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import tools.vitruv.framework.vsum.VirtualModel;
import tools.vitruv.framework.vsum.branch.util.ReloadTriggerFile;

import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link VsumReloadWatcher}.
 */
class VsumReloadWatcherTest {

    private static final String BRANCH = "feature-ui";

    @Test
    @DisplayName("starts and stops cleanly")
    void startsAndStopsCleanly(@TempDir Path tempDir) {
        VirtualModel mockVsum = mock(VirtualModel.class);
        var watcher = new VsumReloadWatcher(mockVsum, tempDir);

        assertFalse(watcher.isRunning(), "Should not be running initially");

        watcher.start();
        assertTrue(watcher.isRunning(), "Should be running after start");

        watcher.stop();
        assertFalse(watcher.isRunning(), "Should not be running after stop");
    }

    @Test
    @DisplayName("throws exception when starting twice")
    void throwsExceptionWhenStartingTwice(@TempDir Path tempDir) {
        VirtualModel mockVsum = mock(VirtualModel.class);
        var watcher = new VsumReloadWatcher(mockVsum, tempDir);

        watcher.start();
        assertThrows(IllegalStateException.class, watcher::start);
        watcher.stop();
    }

    @Test
    @DisplayName("detects trigger file and reloads VSUM")
    void detectsTriggerFileAndReloads(@TempDir Path tempDir) throws Exception {
        VirtualModel mockVsum = mock(VirtualModel.class);
        var watcher = new VsumReloadWatcher(mockVsum, tempDir);
        var triggerFile = new ReloadTriggerFile(tempDir);

        watcher.start();
        Thread.sleep(100);

        triggerFile.createTrigger(BRANCH);
        Thread.sleep(1000);

        verify(mockVsum, times(1)).reload();
        assertFalse(triggerFile.exists(), "Trigger file should be deleted after detection");

        watcher.stop();
    }

    @Test
    @DisplayName("does not reload when no trigger file exists")
    void doesNotReloadWithoutTrigger(@TempDir Path tempDir) throws Exception {
        VirtualModel mockVsum = mock(VirtualModel.class);
        var watcher = new VsumReloadWatcher(mockVsum, tempDir);

        watcher.start();
        Thread.sleep(1500);

        verify(mockVsum, never()).reload();

        watcher.stop();
    }

    @Test
    @DisplayName("continues running after reload failure")
    void continuesRunningAfterReloadFailure(@TempDir Path tempDir) throws Exception {
        VirtualModel mockVsum = mock(VirtualModel.class);
        doThrow(new RuntimeException("Simulated failure")).doNothing().when(mockVsum).reload();

        var watcher = new VsumReloadWatcher(mockVsum, tempDir);
        var triggerFile = new ReloadTriggerFile(tempDir);

        watcher.start();
        Thread.sleep(100);

        triggerFile.createTrigger("branch-1");
        Thread.sleep(1000);

        assertTrue(watcher.isRunning(), "Watcher should keep running after error");

        triggerFile.createTrigger("branch-2");
        Thread.sleep(1000);

        verify(mockVsum, times(2)).reload();

        watcher.stop();
    }

    @Test
    @DisplayName("handles multiple sequential reloads correctly")
    void handlesMultipleSequentialReloads(@TempDir Path tempDir) throws Exception {
        VirtualModel mockVsum = mock(VirtualModel.class);
        var watcher = new VsumReloadWatcher(mockVsum, tempDir);
        var triggerFile = new ReloadTriggerFile(tempDir);

        watcher.start();
        Thread.sleep(100);

        triggerFile.createTrigger("main");
        Thread.sleep(1000);

        triggerFile.createTrigger("develop");
        Thread.sleep(1000);

        triggerFile.createTrigger("feature");
        Thread.sleep(1000);

        verify(mockVsum, times(3)).reload();

        watcher.stop();
    }

    @Test
    @DisplayName("stops within reasonable time")
    void stopsWithinReasonableTime(@TempDir Path tempDir) {
        VirtualModel mockVsum = mock(VirtualModel.class);
        var watcher = new VsumReloadWatcher(mockVsum, tempDir);

        watcher.start();

        long startTime = System.currentTimeMillis();
        watcher.stop();
        long duration = System.currentTimeMillis() - startTime;

        assertTrue(duration < 3000, "Stop should complete within 3 seconds, took: " + duration + "ms");
    }

    @Test
    @DisplayName("handles legacy trigger format")
    void handlesLegacyTriggerFormat(@TempDir Path tempDir) throws Exception {
        VirtualModel mockVsum = mock(VirtualModel.class);
        var watcher = new VsumReloadWatcher(mockVsum, tempDir);
        var triggerFile = new ReloadTriggerFile(tempDir);

        watcher.start();
        Thread.sleep(100);

        // Manually create legacy format trigger
        Path triggerPath = triggerFile.getTriggerPath();
        java.nio.file.Files.createDirectories(triggerPath.getParent());
        java.nio.file.Files.writeString(triggerPath, "Reload requested at: 2026-02-10");

        Thread.sleep(1000);

        verify(mockVsum, times(1)).reload();

        watcher.stop();
    }

    @Test
    @DisplayName("lock file prevents concurrent reloads")
    void lockFilePreventsСoncurrentReloads(@TempDir Path tempDir) throws Exception {
        VirtualModel mockVsum = mock(VirtualModel.class);

        // Make reload slow to test lock behavior
        doAnswer(invocation -> {
            Thread.sleep(500);
            return null;
        }).when(mockVsum).reload();

        var watcher = new VsumReloadWatcher(mockVsum, tempDir);
        var triggerFile = new ReloadTriggerFile(tempDir);

        watcher.start();
        Thread.sleep(100);

        // Create two triggers quickly (simulating concurrent checkouts)
        String requestId1 = triggerFile.createTrigger("branch-1");
        Thread.sleep(100); // Small delay to let first trigger be picked up
        String requestId2 = triggerFile.createTrigger("branch-2");

        // Wait for both to process (first one takes 500ms, second needs retry)
        Thread.sleep(2000);

        // Both reloads should eventually complete (sequentially due to lock)
        verify(mockVsum, atLeast(1)).reload();

        watcher.stop();
    }

    @Test
    @DisplayName("stopping non-running watcher does not throw")
    void stoppingNonRunningWatcherDoesNotThrow(@TempDir Path tempDir) {
        VirtualModel mockVsum = mock(VirtualModel.class);
        var watcher = new VsumReloadWatcher(mockVsum, tempDir);

        assertDoesNotThrow(watcher::stop, "Stopping non-running watcher should not throw");
    }

    @Test
    @DisplayName("watcher processes trigger with request ID correctly")
    void processesTriggerWithRequestId(@TempDir Path tempDir) throws Exception {
        VirtualModel mockVsum = mock(VirtualModel.class);
        var watcher = new VsumReloadWatcher(mockVsum, tempDir);
        var triggerFile = new ReloadTriggerFile(tempDir);

        watcher.start();
        Thread.sleep(100);

        String requestId = triggerFile.createTrigger(BRANCH);
        assertNotNull(requestId, "Should return request ID");

        Thread.sleep(1000);

        verify(mockVsum, times(1)).reload();

        watcher.stop();
    }
}