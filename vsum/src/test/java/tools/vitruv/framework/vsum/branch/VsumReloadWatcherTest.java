package tools.vitruv.framework.vsum.branch;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import tools.vitruv.framework.vsum.VirtualModel;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link VsumReloadWatcher}.
 */
class VsumReloadWatcherTest {

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
        // Second start should fail
        assertThrows(IllegalStateException.class, watcher::start);
        watcher.stop();
    }

    @Test
    @DisplayName("detects trigger file and reloads VSUM")
    void detectsTriggerFileAndReloads(@TempDir Path tempDir) throws Exception {
        VirtualModel mockVsum = mock(VirtualModel.class);
        var watcher = new VsumReloadWatcher(mockVsum, tempDir);
        var triggerFile = new ReloadTriggerFile(tempDir);
        // start watching
        watcher.start();
        // wait to ensure watcher is running
        Thread.sleep(100);
        // create trigger file
        triggerFile.createTrigger();
        // wait for watcher to detect it
        Thread.sleep(1000);
        // verify reload was called
        verify(mockVsum, times(1)).reload();
        // verify trigger file was deleted
        assertFalse(triggerFile.exists(), "Trigger file should be deleted after detection");
        watcher.stop();
    }

    @Test
    @DisplayName("does not reload when no trigger file exists")
    void doesNotReloadWithoutTrigger(@TempDir Path tempDir) throws Exception {
        VirtualModel mockVsum = mock(VirtualModel.class);
        var watcher = new VsumReloadWatcher(mockVsum, tempDir);
        watcher.start();
        // wait several check intervals
        Thread.sleep(1500);
        // reload should never be called (no trigger file created)
        verify(mockVsum, never()).reload();
        watcher.stop();
    }

    @Test
    @DisplayName("continues running after reload failure")
    void continuesRunningAfterReloadFailure(@TempDir Path tempDir) throws Exception {
        VirtualModel mockVsum = mock(VirtualModel.class);
        // first reload fails, second succeeds
        doThrow(new RuntimeException("Simulated failure")).doNothing().when(mockVsum).reload();
        var watcher = new VsumReloadWatcher(mockVsum, tempDir);
        var triggerFile = new ReloadTriggerFile(tempDir);
        watcher.start();
        Thread.sleep(100);
        // first trigger (will fail)
        triggerFile.createTrigger();
        Thread.sleep(1000);
        // watcher should still be running
        assertTrue(watcher.isRunning(), "Watcher should keep running after error");
        // second trigger (will succeed)
        triggerFile.createTrigger();
        Thread.sleep(1000);
        // both reload attempts should have been made
        verify(mockVsum, times(2)).reload();
        watcher.stop();
    }
}