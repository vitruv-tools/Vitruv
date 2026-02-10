package tools.vitruv.framework.vsum.branch.handler;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jgit.api.Git;
import tools.vitruv.framework.vsum.branch.data.SemanticChangelog;
import tools.vitruv.framework.vsum.branch.data.ValidationResult;
import tools.vitruv.framework.vsum.branch.util.ValidationResultFile;
import tools.vitruv.framework.vsum.branch.util.ValidationTriggerFile;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * Background watcher that monitors for VSUM validation requests.
 *
 * <p>Polls for validation trigger files created by Git pre-commit hooks.
 * When a trigger is detected:
 * <ol>
 *   <li>Acquires lock to prevent concurrent validations</li>
 *   <li>Validates the VSUM using {@link PreCommitHandler}</li>
 *   <li>Writes validation results to request-specific files</li>
 *   <li>Generates semantic changelog if validation passed</li>
 *   <li>Auto-stages changelog for commit</li>
 *   <li>Releases lock and deletes the trigger file</li>
 * </ol>
 *
 * <p><b>Concurrency:</b> Multiple validation requests are processed sequentially using
 * a lock file. Each request gets its own result files identified by request ID.
 */
public class VsumValidationWatcher {
    private static final Logger LOGGER = LogManager.getLogger(VsumValidationWatcher.class);
    private static final long POLL_INTERVAL_MS = 500;
    private static final String THREAD_NAME = "VSUM-Validation-Watcher";
    private static final String LOCK_FILENAME = ".validation.lock";  // Lock file name

    private final Path repositoryRoot;  // Store repository root for Git operations
    private final ValidationTriggerFile triggerFile;
    private final ValidationResultFile resultFile;
    private final PreCommitHandler handler;
    private final Path lockFile;  // lock file path

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
        this.repositoryRoot = repositoryRoot;
        this.triggerFile = new ValidationTriggerFile(repositoryRoot);
        this.resultFile = new ValidationResultFile(repositoryRoot);
        this.handler = new PreCommitHandler(virtualModel);
        this.running = false;
        this.lockFile = repositoryRoot.resolve(".vitruvius").resolve(LOCK_FILENAME);  // NEW: Lock file
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
     * <p> Acquires lock before validation to prevent concurrent validations
     * from corrupting VSUM state. Writes results to request-specific files.
     *
     * @param info the trigger information (commit SHA, branch, request ID, timestamp)
     */
    private void handleValidationRequest(ValidationTriggerFile.TriggerInfo info) {
        String commitShort = info.getCommitSha().substring(0, Math.min(8, info.getCommitSha().length()));
        String requestId = info.getRequestId();  //Get request ID

        LOGGER.info("Validation requested for commit {} on branch {} (requestId={})", commitShort, info.getBranch(), requestId);

        // Acquire lock before validation
        try {
            // Ensure .vitruvius directory exists
            Files.createDirectories(lockFile.getParent());
            // Try to acquire lock (non-blocking with timeout)
            try (FileChannel channel = FileChannel.open(lockFile, StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
                // Try to acquire lock with timeout
                FileLock lock = channel.tryLock();
                if (lock == null) {
                    // Another validation is in progress
                    LOGGER.warn("Another validation is in progress, will retry on next poll (requestId={})", requestId);
                    // Put the trigger back for retry on next poll
                    try {
                        triggerFile.createTrigger(info.getCommitSha(), info.getBranch());
                        LOGGER.debug("Re-created trigger for retry (requestId={})", requestId);
                    } catch (IOException e) {
                        LOGGER.error("Failed to re-create trigger for retry (requestId={})", requestId, e);
                        // Write error result so hook doesnt timeout
                        writeErrorResult(requestId, "Validation queue full - try again");
                    }
                    return;
                }

                try {
                    // Lock acquired, perform validation
                    LOGGER.debug("Lock acquired for validation (requestId={})", requestId);
                    performValidation(info, commitShort, requestId);
                } finally {
                    // Release lock
                    lock.release();
                    LOGGER.debug("Lock released (requestId={})", requestId);
                }
            } catch (IOException e) {
                LOGGER.error("Failed to acquire lock for validation (requestId={})", requestId, e);
                writeErrorResult(requestId, "Failed to acquire validation lock: " + e.getMessage());
            }

        } catch (Exception e) {
            LOGGER.error("Unexpected error handling validation request (requestId={})", requestId, e);
            writeErrorResult(requestId, "Validation failed: " + e.getMessage());
        }
    }

    /**
     * Performs the actual validation (called while holding lock).
     *
     * @param info the trigger information
     * @param commitShort the short commit SHA (first 8 chars)
     * @param requestId the unique request identifier
     */
    private void performValidation(ValidationTriggerFile.TriggerInfo info, String commitShort, String requestId) {
        try {
            // Validate V-SUM
            long startTime = System.currentTimeMillis();
            ValidationResult result = handler.validate();
            long duration = System.currentTimeMillis() - startTime;
            LOGGER.info("Validation completed in {}ms: {} (requestId={})", duration, result.isValid() ? "PASSED ✓" : "FAILED ✗", requestId);
            // Write result files with request ID
            try {
                resultFile.writeResult(result, requestId);
                LOGGER.debug("Validation result files written for requestId={}", requestId);
            } catch (Exception e) {
                LOGGER.error("Failed to write validation result files (requestId={})", requestId, e);
            }

            // Generate and save changelog if validation passed
            if (result.isValid()) {
                try {
                    SemanticChangelog changelog = handler.generateChangelog(info.getCommitSha(), info.getBranch());
                    // Save changelog to .vitruvius/changelogs/<commitShort>.txt
                    Path vitruviusDir = triggerFile.getTriggerPath().getParent();
                    Path changelogDir = vitruviusDir.resolve("changelogs");
                    Path changelogFile = changelogDir.resolve(commitShort + ".txt");

                    changelog.writeTo(changelogFile);

                    LOGGER.info("Semantic changelog generated for commit {}", commitShort);
                    LOGGER.debug("Changelog saved to: {}", changelogFile);

                    // auto-stage the changelog for commit
                    autoStageChangelog(changelogFile, commitShort);

                } catch (Exception e) {
                    LOGGER.warn("Failed to generate changelog, but validation passed (requestId={})", requestId, e);
                }
            } else {
                LOGGER.warn("Validation failed: {} errors, {} warnings (requestId={})", result.getErrors().size(), result.getWarnings().size(), requestId);
                if (!result.getErrors().isEmpty()) {
                    LOGGER.warn("First error: {}", result.getErrors().get(0));
                }
            }

        } catch (Exception e) {
            LOGGER.error("Validation failed with exception for commit {} (requestId={})", commitShort, requestId, e);
            writeErrorResult(requestId, "Validation crashed: " + e.getMessage());
        }
    }

    /**
     * Auto-stages the changelog file for commit using JGit.
     *
     * <p>This ensures changelogs are automatically included in commits without
     * developer action. If auto-staging fails, logs warning but doesn't fail validation.
     *
     * @param changelogFile the changelog file to stage
     * @param commitShort the short commit SHA (for logging)
     */
    private void autoStageChangelog(Path changelogFile, String commitShort) {
        try {
            // Open Git repository
            Git git = Git.open(repositoryRoot.toFile());
            // Compute relative path from repository root
            Path relativePath = repositoryRoot.relativize(changelogFile);
            String filePattern = relativePath.toString().replace('\\', '/');  // Git uses forward slashes
            // Stage the changelog file
            git.add().addFilepattern(filePattern).call();
            LOGGER.info("Changelog auto-staged for commit {} ({})", commitShort, filePattern);
        } catch (Exception e) {
            LOGGER.warn("Failed to auto-stage changelog for commit {} (not critical)", commitShort, e);
            LOGGER.warn("Developer can manually stage with: git add {}", changelogFile);
        }
    }

    /**
     * Writes an error result when validation cannot be performed.
     *
     * @param requestId the request identifier
     * @param errorMessage the error message to write
     */
    private void writeErrorResult(String requestId, String errorMessage) {
        try {
            ValidationResult errorResult = ValidationResult.failure(java.util.List.of(errorMessage));
            resultFile.writeResult(errorResult, requestId);
        } catch (Exception writeError) {
            LOGGER.error("Failed to write error result (requestId={})", requestId, writeError);
        }
    }
}