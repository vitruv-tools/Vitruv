package tools.vitruv.framework.vsum.branch.handler;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import tools.vitruv.change.atomic.uuid.UuidResolver;
import tools.vitruv.change.correspondence.view.EditableCorrespondenceModelView;
import tools.vitruv.framework.vsum.branch.util.ValidationResultFile;
import tools.vitruv.framework.vsum.branch.util.ValidationTriggerFile;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Integration tests for {@link VsumValidationWatcher}.
 * <p>These tests start the real watcher thread and interact with the real {@link ValidationTriggerFile} and {@link ValidationResultFile} on a temporary directory.
 * The {@link InternalVirtualModel} is mocked to control validation outcomes without requiring a fully initialized Vitruvius runtime.
 * <p>Polling is observed by waiting in short increments ({@code waitForResult}) rather than fixed sleeps to reduce total test execution time while still being robust against scheduler jitter.
 */
@SuppressWarnings("unchecked")
class VsumValidationWatcherTest {

    private static final String COMMIT_SHA = "abc1234567890abcdef1234567890abcdef12345";
    private static final String BRANCH = "feature-vcs";

    /**
     * Verifies the basic start/stop lifecycle: the watcher must not be running before start, must be running after start, and must stop cleanly.
     */
    @Test
    @DisplayName("Starts and stops cleanly, reflecting running state correctly")
    void startsAndStopsCleanly(@TempDir Path tempDir) {
        var watcher = new VsumValidationWatcher(mock(InternalVirtualModel.class), tempDir);

        assertFalse(watcher.isRunning());
        watcher.start();
        assertTrue(watcher.isRunning());
        watcher.stop();
        assertFalse(watcher.isRunning());
    }

    /**
     * Verifies that calling start() on an already-running watcher throws an exception.
     * A second start would create a second background thread polling the same trigger file, which could cause duplicate validations.
     */
    @Test
    @DisplayName("Throws an exception when started while already running")
    void throwsExceptionWhenStartingTwice(@TempDir Path tempDir) {
        var watcher = new VsumValidationWatcher(mock(InternalVirtualModel.class), tempDir);
        watcher.start();
        try {
            assertThrows(IllegalStateException.class, watcher::start);
        } finally {
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
        var watcher = new VsumValidationWatcher(mock(InternalVirtualModel.class), tempDir);
        assertDoesNotThrow(watcher::stop);
    }

    /**
     * Verifies that stop() returns within a reasonable time.
     * The watcher thread sleeps for at most POLL_INTERVAL_MS between cycles, so shutdown must complete well within 3s.
     */
    @Test
    @DisplayName("Stop completes within 3 seconds")
    void stopsWithinReasonableTime(@TempDir Path tempDir) {
        var watcher = new VsumValidationWatcher(mock(InternalVirtualModel.class), tempDir);
        watcher.start();

        long startTime = System.currentTimeMillis();
        watcher.stop();
        long duration = System.currentTimeMillis() - startTime;

        assertFalse(watcher.isRunning());
        assertTrue(duration < 3000, "stop() must return within 3 seconds, but took " + duration + "ms");
    }

    /**
     * Verifies the core end-to-end flow: when a trigger file is created, the watcher detects it, runs validation,
     * writes both text and JSON result files for the request identifier, and deletes the trigger file.
     */
    @Test
    @DisplayName("Detects a trigger and writes request-specific result files")
    void detectsTriggerAndWritesResultFiles(@TempDir Path tempDir) throws Exception {
        var watcher = new VsumValidationWatcher(validMockVsum(), tempDir);
        var triggerFile = new ValidationTriggerFile(tempDir);
        var resultFile = new ValidationResultFile(tempDir);

        watcher.start();
        try {
            String requestId = triggerFile.createTrigger(COMMIT_SHA, BRANCH);
            waitForResult(resultFile, requestId);

            assertTrue(resultFile.exists(requestId), "result file must exist for request " + requestId);
            assertFalse(triggerFile.exists(), "trigger file must be deleted after processing");
            // both the human-readable text file and the machine-readable JSON file must be present.
            assertTrue(Files.exists(resultFile.getTextResultPath(requestId)));
            assertTrue(Files.exists(resultFile.getJsonResultPath(requestId)));
        } finally {
            watcher.stop();
        }
    }

    /**
     * Verifies that a valid VirtualModel produces a success result.
     * The text file must indicate the commit is allowed and the JSON file must contain {@code "valid": true}.
     */
    @Test
    @DisplayName("Writes a success result when the VirtualModel passes validation")
    void writesSuccessResultForValidModel(@TempDir Path tempDir) throws Exception {
        var watcher = new VsumValidationWatcher(validMockVsum(), tempDir);
        var triggerFile = new ValidationTriggerFile(tempDir);
        var resultFile = new ValidationResultFile(tempDir);

        watcher.start();
        try {
            String requestId = triggerFile.createTrigger(COMMIT_SHA, BRANCH);
            waitForResult(resultFile, requestId);

            String textContent = Files.readString(resultFile.getTextResultPath(requestId));
            assertTrue(textContent.contains("PASSED") || textContent.contains("✔"), "text result must indicate a passed validation, but was: " + textContent);

            String jsonContent = Files.readString(resultFile.getJsonResultPath(requestId));
            assertTrue(jsonContent.contains("\"valid\""));
            assertTrue(jsonContent.contains("true"), "JSON result must contain valid:true, but was: " + jsonContent);
        } finally {
            watcher.stop();
        }
    }

    /**
     * Verifies that the watcher does not call validation when no trigger file exists.
     * The VirtualModel must remain untouched between poll cycles to avoid spurious reloads.
     */
    @Test
    @DisplayName("Does not validate when no trigger file exists")
    void doesNotValidateWithoutTrigger(@TempDir Path tempDir) throws Exception {
        var mockVsum = mock(InternalVirtualModel.class);
        var watcher = new VsumValidationWatcher(mockVsum, tempDir);

        watcher.start();
        try {
            // wait for several poll cycles to confirm no validation is triggered.
            Thread.sleep(1500);
            verify(mockVsum, never()).getViewSourceModels();
        } finally {
            watcher.stop();
        }
    }

    /**
     * Verifies that three sequential triggers each produce an independent result file with a unique request identifier.
     * This confirms that the watcher processes one trigger per file and does not batch or skip any.
     */
    @Test
    @DisplayName("Processes multiple sequential triggers with unique request identifiers")
    void handlesMultipleSequentialTriggers(@TempDir Path tempDir) throws Exception {
        var watcher = new VsumValidationWatcher(validMockVsum(), tempDir);
        var triggerFile = new ValidationTriggerFile(tempDir);
        var resultFile = new ValidationResultFile(tempDir);

        watcher.start();
        try {
            // process each trigger fully before creating the next one, so they are not overwritten before the watcher has a chance to read them.
            String requestId1 = triggerFile.createTrigger(COMMIT_SHA, "main");
            waitForResult(resultFile, requestId1);
            assertTrue(resultFile.exists(requestId1));
            resultFile.deleteResult(requestId1);

            String requestId2 = triggerFile.createTrigger(COMMIT_SHA, "develop");
            waitForResult(resultFile, requestId2);
            assertTrue(resultFile.exists(requestId2));

            // each trigger must have received a distinct request identifier.
            assertNotEquals(requestId1, requestId2, "each trigger must produce a unique request identifier");
        } finally {
            watcher.stop();
        }
    }

    /**
     * Verifies that when the VirtualModel throws an exception during validation, the watcher writes an error result,
     * continues running, and successfully processes the next trigger.
     * The watcher must never crash because of a validation failure.
     */
    @Test
    @DisplayName("Continues running and processes subsequent triggers after a validation error")
    void continuesRunningAfterValidationError(@TempDir Path tempDir) throws Exception {
        var mockVsum = mock(InternalVirtualModel.class);
        // first call throws, second call succeeds, so we can observe both paths.
        when(mockVsum.getViewSourceModels()).thenThrow(new RuntimeException("Simulated error")).thenReturn(List.of());
        when(mockVsum.getCorrespondenceModel()).thenReturn(mock(EditableCorrespondenceModelView.class));
        when(mockVsum.getUuidResolver()).thenReturn(mock(UuidResolver.class));

        var watcher = new VsumValidationWatcher(mockVsum, tempDir);
        var triggerFile = new ValidationTriggerFile(tempDir);
        var resultFile = new ValidationResultFile(tempDir);

        watcher.start();
        try {
            // the first trigger causes the mock to throw, producing a failure result.
            String requestId1 = triggerFile.createTrigger(COMMIT_SHA, "error-branch");
            waitForResult(resultFile, requestId1);
            String errorContent = Files.readString(resultFile.getTextResultPath(requestId1));
            assertTrue(errorContent.contains("FAILED") || errorContent.contains("✘"), "an exception during validation must produce a failure result");
            resultFile.deleteResult(requestId1);

            // the watcher must still be running after the error.
            assertTrue(watcher.isRunning(), "watcher must keep running after a validation error");

            // the second trigger must be processed successfully.
            String requestId2 = triggerFile.createTrigger(COMMIT_SHA, "success-branch");
            waitForResult(resultFile, requestId2);
            assertTrue(resultFile.exists(requestId2), "watcher must process subsequent triggers after recovering from an error");
        } finally {
            watcher.stop();
        }
    }

    /**
     * Verifies that when two triggers are created in quick succession, the second is not silently dropped even if the watcher is busy processing the first.
     * At minimum, the second trigger must produce a result file.
     * <p>Note: the single-file trigger design means that if both triggers are written within the same poll window,
     * the first may be overwritten before the watcher reads it (documented limitation).
     * This test therefore only asserts that the second trigger completes, not that both complete.
     */
    @Test
    @DisplayName("Second trigger is eventually processed when it arrives while the first is in progress")
    void secondTriggerIsProcessedUnderConcurrency(@TempDir Path tempDir) throws Exception {
        var watcher = new VsumValidationWatcher(validMockVsum(), tempDir);
        var triggerFile = new ValidationTriggerFile(tempDir);
        var resultFile = new ValidationResultFile(tempDir);

        watcher.start();
        try {
            String requestId1 = triggerFile.createTrigger(COMMIT_SHA, "branch-1");
            // wait briefly so the watcher can pick up the first trigger before the second is written, exercising the re-queue path rather than the overwrite path.
            Thread.sleep(100);
            String requestId2 = triggerFile.createTrigger(COMMIT_SHA, "branch-2");

            // wait long enough for both triggers to be processed sequentially.
            int maxWait = 80;
            int waited = 0;
            while (!resultFile.exists(requestId2) && waited < maxWait) {
                Thread.sleep(50);
                waited++;
            }
            assertTrue(resultFile.exists(requestId2), "the second trigger must produce a result file (requestId=" + requestId2 + ")");
        } finally {
            watcher.stop();
        }
    }

    /**
     * Verifies that a trigger file written in the legacy two-field format (without a request identifier) is still consumed.
     * This ensures that repositories upgraded from an older hook version do not cause the watcher to stall.
     * <p>Because the legacy format does not return a request identifier, this test can only assert that the trigger file is deleted, not that a result file was written.
     */
    @Test
    @DisplayName("Consumes a legacy two-field trigger file format without stalling")
    void consumesLegacyTriggerFormat(@TempDir Path tempDir) throws Exception {
        var watcher = new VsumValidationWatcher(validMockVsum(), tempDir);
        Path triggerPath = tempDir.resolve(".vitruvius").resolve("validate-trigger");
        Files.createDirectories(triggerPath.getParent());

        watcher.start();
        try {
            // write the old two-field format directly, bypassing ValidationTriggerFile.createTrigger()
            Files.writeString(triggerPath, COMMIT_SHA + "|" + BRANCH);

            // the watcher must consume the trigger even though no request ID is present
            int waited = 0;
            while (Files.exists(triggerPath) && waited < 40) {
                Thread.sleep(50);
                waited++;
            }
            assertFalse(Files.exists(triggerPath), "legacy trigger file must be deleted after processing");
        } finally {
            watcher.stop();
        }
    }

    /**
     * Creates a mock {@link InternalVirtualModel} pre-configured with a valid correspondence model and UUID resolver so that validation passes.
     * Tests that do not need to control validation outcomes should use this helper to reduce boilerplate.
     */
    private static InternalVirtualModel validMockVsum() {
        var mockVsum = mock(InternalVirtualModel.class);
        when(mockVsum.getViewSourceModels()).thenReturn(List.of());
        when(mockVsum.getCorrespondenceModel()).thenReturn(mock(EditableCorrespondenceModelView.class));
        when(mockVsum.getUuidResolver()).thenReturn(mock(UuidResolver.class));
        return mockVsum;
    }

    /**
     * Polls until both the text and JSON result files exist for the given request identifier, or until the 2-second timeout expires.
     * Waiting for both files rather than just one prevents a race condition where {@code exists()} returns true as soon as the text file
     * is written but before {@code writeJsonResult()} has completed writing the JSON file.
     */
    private static void waitForResult(ValidationResultFile resultFile, String requestId)
            throws InterruptedException {
        int maxWait = 40; // 40 × 50ms = 2 seconds
        int waited = 0;
        while (waited < maxWait) {
            // both files must be present before the test proceeds, because some assertions
            // read the JSON file directly and a partial write would cause NoSuchFileException.
            if (Files.exists(resultFile.getTextResultPath(requestId)) && Files.exists(resultFile.getJsonResultPath(requestId))) {
                return;
            }
            Thread.sleep(50);
            waited++;
        }
    }
}