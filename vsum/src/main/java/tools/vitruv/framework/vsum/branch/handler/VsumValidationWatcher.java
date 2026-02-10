package tools.vitruv.framework.vsum.branch.handler;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tools.vitruv.framework.vsum.branch.data.SemanticChangelog;
import tools.vitruv.framework.vsum.branch.data.ValidationResult;
import tools.vitruv.framework.vsum.branch.util.ValidationResultFile;
import tools.vitruv.framework.vsum.branch.util.ValidationTriggerFile;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;

import java.nio.file.Path;

/**
 * Background watcher that monitors for VSUM validation requests.
 *
 * <p>Polls for validation trigger files created by Git pre-commit hooks.
 * When a trigger is detected:
 * <ol>
 *   <li>Validates the VSUM using {@link PreCommitHandler}</li>
 *   <li>Writes validation results to files (text + JSON)</li>
 *   <li>Generates semantic changelog if validation passed</li>
 *   <li>Deletes the trigger file</li>
 * </ol>
 */
public class VsumValidationWatcher {
    private static final Logger LOGGER = LogManager.getLogger(VsumValidationWatcher.class);
    private static final long POLL_INTERVAL_MS = 500;
    private static final String THREAD_NAME = "VSUM-Validation-Watcher";
    private final InternalVirtualModel virtualModel;
    private final ValidationTriggerFile triggerFile;
    private final ValidationResultFile resultFile;
    private final PreCommitHandler handler;
    private Thread watcherThread;
    /**
     * -- GETTER --
     *
     */
    @Getter
    private volatile boolean running;

    /**
     * Creates a validation watcher for the given virtual model.
     *
     * @param virtualModel the virtual model to validate
     * @param repositoryRoot the Git repository root directory
     */
    public VsumValidationWatcher(InternalVirtualModel virtualModel, Path repositoryRoot) {
        this.virtualModel = virtualModel;
        this.triggerFile = new ValidationTriggerFile(repositoryRoot);
        this.resultFile = new ValidationResultFile(repositoryRoot);
        this.handler = new PreCommitHandler(virtualModel);
        this.running = false;
    }

    /**
     * Starts the validation watcher in a background daemon thread.
     *
     * @throws IllegalStateException if the watcher is already running
     */
    public synchronized void start() {
        if (running) {
            throw new IllegalStateException("Validation watcher is already running");
        }
        running = true;
        watcherThread = new Thread(this::watchLoop, THREAD_NAME);
        watcherThread.setDaemon(true);
        watcherThread.start();
        LOGGER.info("V-SUM validation watcher started");
    }

    /**
     * Stops the validation watcher and waits for the thread to terminate.
     *
     * <p>Blocks for up to 2 seconds waiting for graceful shutdown.
     */
    public synchronized void stop() {
        if (!running) {
            return;
        }
        running = false;
        if (watcherThread != null) {
            watcherThread.interrupt();
            try {
                watcherThread.join(1000);
            } catch (InterruptedException e) {
                LOGGER.warn("Interrupted while waiting for watcher thread to stop");
                Thread.currentThread().interrupt();
            }
        }
        LOGGER.info("V-SUM validation watcher stopped");
    }

    /**
     * Main watch loop - polls for validation triggers.
     *
     * <p>Runs until {@link #stop()} is called or thread is interrupted.
     */
    private void watchLoop() {
        LOGGER.debug("Validation watch loop started, polling every {}ms", POLL_INTERVAL_MS);
        while (running) {
            try {
                // Check for validation trigger
                ValidationTriggerFile.TriggerInfo info = triggerFile.checkAndClearTrigger();
                if (info != null) {
                    handleValidationRequest(info);
                }
                // Sleep before next poll
                Thread.sleep(POLL_INTERVAL_MS);
            } catch (InterruptedException e) {
                LOGGER.debug("Validation watcher interrupted");
                running = false;
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                // Log error but keep running
                LOGGER.error("Error in validation watcher loop, will continue", e);
            }
        }
        LOGGER.debug("Validation watch loop exited");
    }

    /**
     * Handles a validation request from the trigger file.
     *
     * @param info the trigger information (commit SHA and branch)
     */
    private void handleValidationRequest(ValidationTriggerFile.TriggerInfo info) {
        String commitShort = info.getCommitSha().substring(0, Math.min(8, info.getCommitSha().length()));
        LOGGER.info("Validation requested for commit {} on branch {}", commitShort, info.getBranch());
        try {
            // Validate V-SUM
            long startTime = System.currentTimeMillis();
            ValidationResult result = handler.validate();
            long duration = System.currentTimeMillis() - startTime;
            LOGGER.info("Validation completed in {}ms: {}", duration, result.isValid() ? "PASSED ✓" : "FAILED ✗");
            // Write result files (text + JSON)
            try {
                resultFile.writeResult(result);
                LOGGER.debug("Validation result files written");
            } catch (Exception e) {
                LOGGER.error("Failed to write validation result files", e);
            }

            // Generate and save changelog if validation passed
            if (result.isValid()) {
                try {
                    SemanticChangelog changelog = handler.generateChangelog(info.getCommitSha(), info.getBranch());
                    // Save changelog to .vitruvius/changelogs/<commitSha>.txt
                    // Calculate vitruvius dir from trigger file path
                    Path vitruviusDir = triggerFile.getTriggerPath().getParent();
                    Path changelogDir = vitruviusDir.resolve("changelogs");
                    Path changelogFile = changelogDir.resolve(commitShort + ".txt");
                    changelog.writeTo(changelogFile);
                    LOGGER.info("Semantic changelog generated for commit {}", commitShort);
                    LOGGER.debug("Changelog saved to: {}", changelogFile);
                } catch (Exception e) {
                    LOGGER.warn("Failed to generate changelog, but validation passed", e);
                }
            } else {
                LOGGER.warn("Validation failed: {} errors, {} warnings", result.getErrors().size(), result.getWarnings().size());
                if (!result.getErrors().isEmpty()) {
                    LOGGER.warn("First error: {}", result.getErrors().get(0));
                }
            }
        } catch (Exception e) {
            LOGGER.error("Validation failed with exception for commit {}", commitShort, e);
            // Try to write error result
            try {
                ValidationResult errorResult = ValidationResult.failure(java.util.List.of("Validation crashed: " + e.getMessage()));
                resultFile.writeResult(errorResult);
            } catch (Exception writeError) {
                LOGGER.error("Failed to write error result", writeError);
            }
        }
    }
}