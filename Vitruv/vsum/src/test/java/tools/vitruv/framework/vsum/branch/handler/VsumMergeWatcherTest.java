package tools.vitruv.framework.vsum.branch.handler;

import org.eclipse.jgit.api.Git;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import tools.vitruv.change.atomic.uuid.UuidResolver;
import tools.vitruv.change.correspondence.view.EditableCorrespondenceModelView;
import tools.vitruv.framework.vsum.branch.util.MergeTriggerFile;
import tools.vitruv.framework.vsum.branch.util.MergeResultFile;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link VsumMergeWatcher}.
 *
 * <p>The watcher's responsibilities are lifecycle management and trigger detection: it polls
 * for a {@link MergeTriggerFile}, clears it when found, delegates to {@link PostMergeHandler},
 * and writes result files via {@link MergeResultFile}.  Tests verify these behaviors without
 * requiring a live VSUM -- the {@link InternalVirtualModel} is mocked throughout.
 */
@SuppressWarnings("unchecked")
class VsumMergeWatcherTest {

    @TempDir
    Path repoRoot;

    private static final String MERGE_SHA = "abc1234567890abcdef1234567890abcdef12345";
    private static final String SOURCE_BRANCH = "feature-x";
    private static final String TARGET_BRANCH = "main";

    @BeforeEach
    void initGitRepository() throws Exception {
        try (var git = Git.init().setDirectory(repoRoot.toFile()).setInitialBranch("master").call()) {
            Files.writeString(repoRoot.resolve("init.txt"), "initial");
            git.add().addFilepattern("init.txt").call();
            git.commit().setMessage("Initial commit").setAllowEmpty(false).call();
        }
    }

    @Test
    @DisplayName("Throws NullPointerException when virtualModel is null")
    void throwsWhenVirtualModelIsNull() {
        assertThrows(NullPointerException.class,
                () -> new VsumMergeWatcher(null, repoRoot));
    }

    @Test
    @DisplayName("Throws NullPointerException when repositoryRoot is null")
    void throwsWhenRepositoryRootIsNull() {
        assertThrows(NullPointerException.class,
                () -> new VsumMergeWatcher(mock(InternalVirtualModel.class), null));
    }

    @Test
    @DisplayName("Starts and stops cleanly, reflecting running state correctly")
    void startsAndStopsCleanly() {
        var watcher = new VsumMergeWatcher(mock(InternalVirtualModel.class), repoRoot);

        assertFalse(watcher.isRunning());
        watcher.start();
        assertTrue(watcher.isRunning());
        watcher.stop();
        assertFalse(watcher.isRunning());
    }

    @Test
    @DisplayName("Throws when started while already running")
    void throwsExceptionWhenStartingTwice() {
        var watcher = new VsumMergeWatcher(mock(InternalVirtualModel.class), repoRoot);
        watcher.start();
        try {
            assertThrows(IllegalStateException.class, watcher::start);
        } finally {
            watcher.stop();
        }
    }

    @Test
    @DisplayName("Stopping a watcher that was never started completes without throwing")
    void stoppingNonRunningWatcherDoesNotThrow() {
        var watcher = new VsumMergeWatcher(mock(InternalVirtualModel.class), repoRoot);
        assertDoesNotThrow(watcher::stop);
    }

    @Test
    @DisplayName("Stop completes within 3 seconds")
    void stopsWithinReasonableTime() {
        var watcher = new VsumMergeWatcher(mock(InternalVirtualModel.class), repoRoot);
        watcher.start();

        long startTime = System.currentTimeMillis();
        watcher.stop();
        long duration = System.currentTimeMillis() - startTime;

        assertFalse(watcher.isRunning());
        assertTrue(duration < 3000, "stop() must return within 3 seconds but took " + duration + "ms");
    }

    @Test
    @DisplayName("Detects a merge trigger, clears the trigger file, and writes result files")
    void detectsTriggerAndWritesResultFiles() throws Exception {
        var watcher = new VsumMergeWatcher(validMockVsum(), repoRoot);
        var triggerFile = new MergeTriggerFile(repoRoot);
        var resultFile = new MergeResultFile(repoRoot);

        watcher.start();
        try {
            String requestId = triggerFile.createTrigger(MERGE_SHA, SOURCE_BRANCH, TARGET_BRANCH);
            waitForResult(resultFile, requestId, 3000);

            assertTrue(resultFile.exists(requestId),
                    "result file must exist for requestId=" + requestId);
            assertFalse(triggerFile.exists(), "trigger file must be deleted after processing");
            assertTrue(Files.exists(resultFile.getTextResultPath(requestId)));
            assertTrue(Files.exists(resultFile.getJsonResultPath(requestId)));
        } finally {
            watcher.stop();
        }
    }

    @Test
    @DisplayName("Watcher remains idle and does not validate when no trigger is present")
    void watcherIdleWithNoTrigger() throws Exception {
        var mockVsum = mock(InternalVirtualModel.class);
        var watcher = new VsumMergeWatcher(mockVsum, repoRoot);

        watcher.start();
        try {
            Thread.sleep(1200);
            verify(mockVsum, never()).getViewSourceModels();
        } finally {
            watcher.stop();
        }
    }

    @Test
    @DisplayName("Two sequential triggers each produce an independent result file")
    void twoSequentialTriggersProduceSeparateResults() throws Exception {
        var watcher = new VsumMergeWatcher(validMockVsum(), repoRoot);
        var triggerFile = new MergeTriggerFile(repoRoot);
        var resultFile = new MergeResultFile(repoRoot);

        watcher.start();
        try {
            String sha1 = "aaa1111111111111111111111111111111111111";
            String sha2 = "bbb2222222222222222222222222222222222222";

            String requestId1 = triggerFile.createTrigger(sha1, SOURCE_BRANCH, TARGET_BRANCH);
            waitForResult(resultFile, requestId1, 3000);
            assertTrue(resultFile.exists(requestId1));
            resultFile.deleteResult(requestId1);

            String requestId2 = triggerFile.createTrigger(sha2, SOURCE_BRANCH, TARGET_BRANCH);
            waitForResult(resultFile, requestId2, 3000);
            assertTrue(resultFile.exists(requestId2));

            assertNotEquals(requestId1, requestId2, "each trigger must produce a unique request identifier");
        } finally {
            watcher.stop();
        }
    }

    private static InternalVirtualModel validMockVsum() {
        var mockVsum = mock(InternalVirtualModel.class);
        when(mockVsum.getViewSourceModels()).thenReturn(List.of());
        when(mockVsum.getCorrespondenceModel()).thenReturn(mock(EditableCorrespondenceModelView.class));
        when(mockVsum.getUuidResolver()).thenReturn(mock(UuidResolver.class));
        return mockVsum;
    }

    private static void waitForResult(MergeResultFile resultFile, String requestId, long timeoutMs)
            throws InterruptedException {
        long deadline = System.currentTimeMillis() + timeoutMs;
        while (System.currentTimeMillis() < deadline) {
            if (Files.exists(resultFile.getTextResultPath(requestId))
                    && Files.exists(resultFile.getJsonResultPath(requestId))) {
                return;
            }
            Thread.sleep(50);
        }
        fail("Result files not written for requestId=" + requestId + " within " + timeoutMs + "ms");
    }
}
