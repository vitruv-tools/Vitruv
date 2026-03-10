package tools.vitruv.framework.vsum.branch.handler;

import org.eclipse.jgit.api.Git;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import tools.vitruv.framework.vsum.branch.BranchAwareVirtualModel;
import tools.vitruv.framework.vsum.branch.util.ReloadTriggerFile;
import tools.vitruv.framework.vsum.helper.VsumFileSystemLayout;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link VsumReloadWatcher}.
 *
 * <p>These tests start the real watcher thread and use {@link Thread#sleep} to give
 * it time to process triggers. This approach is intentionally simple and avoids
 * introducing a polling library dependency.
 *
 * <p>The VirtualModel is mocked in all tests so that the actual Vitruvius
 * initialization is not required. The watcher's interaction with the trigger file
 * mechanism and its calls to {@code reload(VsumFileSystemLayout)} are both verified.
 */
class VsumReloadWatcherTest {

    private static final String BRANCH = "feature-vcs";
    private static final String OLD_BRANCH = "master";

    /**
     * Verifies the basic start/stop lifecycle: the watcher must not be running before
     * start, must be running after start, and must not be running after stop.
     */
    @Test
    @DisplayName("Starts and stops cleanly, reflecting running state correctly")
    void startsAndStopsCleanly(@TempDir Path tempDir) {
        var mockBranchVsum = mock(BranchAwareVirtualModel.class);
        var watcher = new VsumReloadWatcher(mockBranchVsum, tempDir);

        assertFalse(watcher.isRunning(), "watcher must not be running before start() is called");
        watcher.start();
        assertTrue(watcher.isRunning(), "watcher must be running after start()");
        watcher.stop();
        assertFalse(watcher.isRunning(), "watcher must not be running after stop()");
    }

    /**
     * Verifies that calling start() on an already-running watcher throws an exception.
     * Allowing a second start would create a second background thread polling the same
     * trigger file, which would lead to duplicate reloads.
     */
    @Test
    @DisplayName("Throws an exception when started while already running")
    void throwsExceptionWhenStartingTwice(@TempDir Path tempDir) {
        var mockBranchVsum = mock(BranchAwareVirtualModel.class);
        var watcher = new VsumReloadWatcher(mockBranchVsum, tempDir);

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
     * This makes it safe to call stop() in cleanup code regardless of whether start()
     * was ever reached.
     */
    @Test
    @DisplayName("Stopping a watcher that was never started completes without throwing")
    void stoppingNonRunningWatcherDoesNotThrow(@TempDir Path tempDir) {
        var mockBranchVsum = mock(BranchAwareVirtualModel.class);
        var watcher = new VsumReloadWatcher(mockBranchVsum, tempDir);

        // stop() must be idempotent and safe to call even before start().
        assertDoesNotThrow(watcher::stop);
    }

    /**
     * Verifies that stop() returns within a reasonable time so that the application
     * does not hang on shutdown. The watcher thread sleeps for at most CHECK_INTERVAL_MS
     * (500ms) between polls, so stop() should return well within 3 seconds.
     */
    @Test
    @DisplayName("Stop completes within 3 seconds")
    void stopsWithinReasonableTime(@TempDir Path tempDir) {
        var mockBranchVsum = mock(BranchAwareVirtualModel.class);
        var watcher = new VsumReloadWatcher(mockBranchVsum, tempDir);
        watcher.start();

        long startTime = System.currentTimeMillis();
        watcher.stop();
        long duration = System.currentTimeMillis() - startTime;

        assertTrue(duration < 3000, "stop() must return within 3 seconds, but took " + duration + "ms");
    }

    /**
     * Verifies that the watcher does not call reload() when no trigger file has been
     * created. The VirtualModel must remain untouched between poll cycles.
     * No Git repository is needed here because reload() is never called, so
     * PostCheckoutHandler never constructs a VsumFileSystemLayout.
     */
    @Test
    @DisplayName("Does not call reload() when no trigger file exists")
    void doesNotReloadWithoutTrigger(@TempDir Path tempDir) throws Exception {
        var mockBranchVsum = mock(BranchAwareVirtualModel.class);
        var watcher = new VsumReloadWatcher(mockBranchVsum, tempDir);
        watcher.start();
        // wait for several poll cycles to confirm the watcher polls without side effects.
        Thread.sleep(1500);

        verify(mockBranchVsum, never()).reload(any(VsumFileSystemLayout.class));
        assertFalse(triggerFile(tempDir).exists(), "trigger file must remain absent when no trigger was created");

        watcher.stop();
    }

    /**
     * Verifies that when a trigger file is created with both new and old branch names,
     * the watcher detects it within the next poll cycle, calls
     * {@code reload(VsumFileSystemLayout)} exactly once on the VirtualModel, and
     * deletes the trigger file. The old branch name is forwarded to PostCheckoutHandler
     * so it can use it for vsum state inheritance on first branch visit.
     */
    @Test
    @DisplayName("Detects a trigger file, calls reload() exactly once, and deletes the file")
    void detectsTriggerFileAndReloads(@TempDir Path tempDir) throws Exception {
        initGitRepo(tempDir);
        var mockBranchVsum = mock(BranchAwareVirtualModel.class);
        var watcher = new VsumReloadWatcher(mockBranchVsum, tempDir);
        when(mockBranchVsum.getFolder()).thenReturn(tempDir);
        var triggerFile = new ReloadTriggerFile(tempDir);

        watcher.start();
        // wait for the watcher to complete its first poll cycle before writing the trigger, so the timing does not depend on the thread scheduler.
        Thread.sleep(100);

        triggerFile.createTrigger(BRANCH, OLD_BRANCH);
        // wait long enough for the watcher to pick up the trigger (poll interval is 500ms).
        Thread.sleep(1500);

        verify(mockBranchVsum, times(1)).switchBranch(eq(OLD_BRANCH), eq(BRANCH));
        assertFalse(triggerFile.exists(), "trigger file must be deleted after the reload completes");
        watcher.stop();
    }

    /**
     * Verifies that three trigger files created sequentially each result in exactly
     * one {@code reload()} call on the VirtualModel, and that each trigger file is
     * deleted before the next one is created. This confirms the watcher processes
     * one trigger per file without batching or skipping any.
     */
    @Test
    @DisplayName("Processes multiple sequential triggers, calling reload() once per trigger")
    void handlesMultipleSequentialReloads(@TempDir Path tempDir) throws Exception {
        initGitRepo(tempDir);
        var mockBranchVsum = mock(BranchAwareVirtualModel.class);
        when(mockBranchVsum.getFolder()).thenReturn(tempDir);
        var watcher = new VsumReloadWatcher(mockBranchVsum, tempDir);
        var triggerFile = new ReloadTriggerFile(tempDir);

        watcher.start();
        Thread.sleep(100);

        // create each trigger only after the previous one has been consumed to ensure
        // they are processed sequentially and not overwritten.
        triggerFile.createTrigger("main", "develop");
        Thread.sleep(1500);
        assertFalse(triggerFile.exists(), "first trigger must be deleted after processing");

        triggerFile.createTrigger("develop", "main");
        Thread.sleep(1500);
        assertFalse(triggerFile.exists(), "second trigger must be deleted after processing");

        triggerFile.createTrigger("feature", "develop");
        Thread.sleep(1500);
        assertFalse(triggerFile.exists(), "third trigger must be deleted after processing");

        // three triggers must produce exactly three reload calls.
        verify(mockBranchVsum, times(3)).switchBranch(anyString(), anyString());
        watcher.stop();
    }


    /**
     * Verifies that a trigger written in the legacy single-field format (branch name
     * only, as produced by the earliest hook versions) is still detected, results in
     * a {@code reload()} call, and is deleted. Backward compatibility ensures developers
     * with older hooks installed do not lose reload functionality after updating Vitruvius.
     * The old branch name is unavailable in this format and falls back to "unknown".
     */
    @Test
    @DisplayName("Detects and processes a legacy single-field trigger, calling reload() once")
    void handlesLegacySingleFieldTrigger(@TempDir Path tempDir) throws Exception {
        initGitRepo(tempDir);
        var mockBranchVsum = mock(BranchAwareVirtualModel.class);
        when(mockBranchVsum.getFolder()).thenReturn(tempDir);
        var watcher = new VsumReloadWatcher(mockBranchVsum, tempDir);
        var filePath = tempDir.resolve(".vitruvius/reload-trigger");

        watcher.start();
        Thread.sleep(100);

        // write only the branch name — the original minimal format produced by early hooks.
        Files.createDirectories(filePath.getParent());
        Files.writeString(filePath, "feature");
        Thread.sleep(1500);

        verify(mockBranchVsum, times(1)).switchBranch(eq("unknown"), eq("feature"));
        assertFalse(Files.exists(filePath), "legacy single-field trigger file must be detected and deleted");
        watcher.stop();
    }

    /**
     * Initializes a real Git repository with one empty commit in the given directory.
     * Required for tests that trigger a reload, because {@link PostCheckoutHandler}
     * constructs a {@link VsumFileSystemLayout} from the repository root using JGit
     * to resolve the current branch name. Without a Git repository, construction
     * throws {@link IllegalStateException}.
     */
    private Git initGitRepo(Path dir) throws Exception {
        Git git = Git.init().setDirectory(dir.toFile()).call();
        git.commit().setMessage("init").setAllowEmpty(true).setAuthor("Test", "test@test.com").call();
        return git;
    }

    private ReloadTriggerFile triggerFile(Path tempDir) {
        return new ReloadTriggerFile(tempDir);
    }
}