package tools.vitruv.framework.vsum.branch.handler;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tools.vitruv.framework.vsum.branch.data.ValidationResult;
import tools.vitruv.framework.vsum.branch.util.ValidationResultFile;
import tools.vitruv.framework.vsum.branch.util.ValidationTriggerFile;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;

/**
 * Background daemon that polls for validation trigger files created by the Git
 * {@code pre-commit} hook and validates the VSUM before allowing the commit to proceed.
 *
 * <p>When a trigger is detected, the watcher:
 * <ol>
 *   <li>Acquires a lock file to prevent concurrent validations from corrupting the model
 *       state.</li>
 *   <li>Validates the VirtualModel using {@link PreCommitHandler}.</li>
 *   <li>Writes the validation result to request-specific files so the hook can read it.</li>
 *   <li>Releases the lock.</li>
 * </ol>
 *
 * <p>Changelog generation is handled separately by {@link VsumPostCommitWatcher}, which runs
 * after the commit is finalized and can therefore use the real commit SHA assigned by Git.
 *
 * <p>Concurrency: multiple validation requests that arrive within the same poll window are
 * processed sequentially. If the lock is already held when a second request arrives, the
 * trigger is re-created so it is retried on the next poll cycle. Each request carries a
 * unique request identifier so that result files can be matched to the originating hook
 * invocation even when requests are retried.
 */
public class VsumValidationWatcher {

  private static final Logger LOGGER = LogManager.getLogger(VsumValidationWatcher.class);

  private static final long POLL_INTERVAL_MS = 500;

  /** Maximum time in milliseconds to wait for the watcher thread to terminate on stop. */
  private static final long STOP_TIMEOUT_MS = 2000;

  private static final String THREAD_NAME = "VSUM-Validation-Watcher";

  private static final String LOCK_FILENAME = ".validation.lock";

  private final Path repositoryRoot;

  private final ValidationTriggerFile triggerFile;

  private final ValidationResultFile resultFile;

  private final PreCommitHandler handler;

  /** OS-managed lock file that prevents two validation runs from executing concurrently. */
  private final Path lockFile;

  private Thread watcherThread;

  /** True while the background polling thread is running. */
  @Getter
  private volatile boolean running;

  /**
   * Creates a validation watcher for the given VirtualModel and repository.
   *
   * @param virtualModel   the VirtualModel to validate on each pre-commit trigger. must not
   *                       be null.
   * @param repositoryRoot the root directory of the Git repository. must not be null.
   */
  public VsumValidationWatcher(InternalVirtualModel virtualModel, Path repositoryRoot) {
    checkNotNull(virtualModel, "VirtualModel must not be null");
    this.repositoryRoot = checkNotNull(repositoryRoot, "repository root must not be null");
    this.triggerFile = new ValidationTriggerFile(repositoryRoot);
    this.resultFile = new ValidationResultFile(repositoryRoot);
    this.handler = new PreCommitHandler(virtualModel);
    this.running = false;
    this.lockFile = repositoryRoot.resolve(".vitruvius").resolve(LOCK_FILENAME);
  }

  /**
   * Starts the validation watcher in a background daemon thread.
   * The thread is marked as a daemon so it does not prevent the JVM from shutting down
   * when the main application exits.
   *
   * @throws IllegalStateException if the watcher is already running.
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
   * Stops the validation watcher and waits up to {@value #STOP_TIMEOUT_MS} milliseconds
   * for the background thread to terminate.
   * Calling this method on a watcher that is not running is safe and does nothing.
   */
  public synchronized void stop() {
    if (!running) {
      return;
    }
    running = false;
    if (watcherThread != null) {
      // interrupt the thread in case it is currently sleeping between poll cycles.
      watcherThread.interrupt();
      try {
        watcherThread.join(STOP_TIMEOUT_MS);
      } catch (InterruptedException e) {
        LOGGER.warn("Interrupted while waiting for watcher thread to stop");
        // restore the interrupt flag so the caller can handle it if needed.
        Thread.currentThread().interrupt();
      }
    }
    try {
      Files.deleteIfExists(lockFile);
    } catch (IOException e) {
      LOGGER.warn("Failed to clean up lock file on stop (non-critical)", e);
    }

    LOGGER.info("V-SUM validation watcher stopped");
  }

  /**
   * Main polling loop executed by the background thread.
   * Checks for a trigger file on every cycle and delegates to {@link #handleValidationRequest}
   * when one is found. Exits when {@link #running} becomes false or the thread is interrupted.
   */
  private void watchLoop() {
    LOGGER.debug("Validation watch loop started, polling every {}ms", POLL_INTERVAL_MS);
    while (running) {
      try {
        ValidationTriggerFile.TriggerInfo info = triggerFile.checkAndClearTrigger();
        if (info != null) {
          handleValidationRequest(info);
        }
        Thread.sleep(POLL_INTERVAL_MS);
      } catch (InterruptedException e) {
        LOGGER.debug("Validation watcher interrupted");
        running = false;
        Thread.currentThread().interrupt();
      } catch (Exception e) {
          LOGGER.error("Error in validation watcher loop, will continue", e);
      }
    }
    LOGGER.debug("Validation watch loop exited");
  }

  /**
   * Handles a single validation request.
   * Acquires the OS lock before starting validation so that two concurrent hook invocations
   * cannot run the validation simultaneously. If the lock is already held, the trigger is
   * re-created for retry on the next poll cycle and an error result is written so the hook
   * does not time out.
   *
   * @param info the trigger information parsed from the trigger file.
   */
  private void handleValidationRequest(ValidationTriggerFile.TriggerInfo info) {
    String commitShort = info.getCommitSha().substring(0, Math.min(7,
        info.getCommitSha().length()));
    String requestId = info.getRequestId();

    LOGGER.info("Validation requested for commit {} on branch {} (requestId='{}')",
        commitShort, info.getBranch(), requestId);

    try {
      // the lock file directory must exist before FileChannel.open() can create the file.
      Files.createDirectories(lockFile.getParent());

      // tryLock() is non-blocking: returns null immediately if another validation holds it.
      try (FileChannel channel = FileChannel.open(lockFile,
          StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
        FileLock lock = channel.tryLock();
        if (lock == null) {
          // the lock is held by another validation
          // re-create the trigger so this request is not lost and will be retried.
          LOGGER.warn("Another validation is in progress, re-queuing trigger for retry"
              + " (requestId='{}')", requestId);
          try {
            triggerFile.createTrigger(info.getCommitSha(), info.getBranch());
          } catch (IOException e) {
            LOGGER.error("Failed to re-create trigger for retry (requestId='{}')",
                requestId, e);
            // write an error result so the hook receives a response and does not hang.
            writeErrorResult(requestId, "Validation queue full - try again");
          }
          return;
        }

        try {
          LOGGER.debug("Lock acquired for validation (requestId='{}')", requestId);
          performValidation(info, commitShort, requestId);
        } finally {
          lock.release();
          LOGGER.debug("Lock released (requestId='{}')", requestId);
          try {
            Files.deleteIfExists(lockFile);
            LOGGER.debug("Lock file deleted (requestId='{}')", info.getRequestId());
          } catch (IOException deleteError) {
            LOGGER.warn("Failed to delete lock file (non-critical) (requestId='{}')",
                info.getRequestId(), deleteError);
          }
        }

      } catch (IOException e) {
        LOGGER.error("Failed to acquire validation lock (requestId='{}')", requestId, e);
        writeErrorResult(requestId, "Failed to acquire validation lock: " + e.getMessage());
      }

    } catch (Exception e) {
      LOGGER.error("Unexpected error handling validation request (requestId='{}')", requestId, e);
      writeErrorResult(requestId, "Validation failed: " + e.getMessage());
    }
  }

  /**
   * Performs validation and writes the result while holding the lock.
   *
   * @param info        the trigger information.
   * @param commitShort the first seven characters of the commit SHA, used in logs.
   * @param requestId   the unique request identifier used to name the result files.
   */
  private void performValidation(ValidationTriggerFile.TriggerInfo info,
      String commitShort, String requestId) {
    try {
      long startTime = System.currentTimeMillis();
      ValidationResult result = handler.validate();
      long duration = System.currentTimeMillis() - startTime;
      LOGGER.info("Validation completed in {}ms: {} (requestId='{}')",
          duration, result.isValid() ? "PASSED ✓" : "FAILED ✗", requestId);

      try {
        resultFile.writeResult(result, requestId);
        LOGGER.debug("Validation result files written (requestId='{}')", requestId);
      } catch (Exception e) {
        LOGGER.error("Failed to write validation result files (requestId='{}')", requestId, e);
      }

      if (!result.isValid()) {
        LOGGER.warn("Validation failed: {} errors, {} warnings (requestId='{}')",
            result.getErrors().size(), result.getWarnings().size(), requestId);
        if (!result.getErrors().isEmpty()) {
          LOGGER.warn("First error: {}", result.getErrors().get(0));
        }
      }

    } catch (Exception e) {
      LOGGER.error("Validation failed with exception for commit {} (requestId='{}')",
          commitShort, requestId, e);
      writeErrorResult(requestId, "Validation crashed: " + e.getMessage());
    }
  }

  /**
   * Writes an error result file when validation cannot be performed, for example when the
   * lock cannot be acquired. This ensures the pre-commit hook always receives a response
   * and does not hang waiting for a result file that will never appear.
   *
   * @param requestId    the request identifier used to name the result file.
   * @param errorMessage the error message to include in the result.
   */
  private void writeErrorResult(String requestId, String errorMessage) {
    try {
      ValidationResult errorResult = ValidationResult.failure(List.of(errorMessage));
      resultFile.writeResult(errorResult, requestId);
    } catch (Exception writeError) {
      LOGGER.error("Failed to write error result (requestId='{}')", requestId, writeError);
    }
  }
}
