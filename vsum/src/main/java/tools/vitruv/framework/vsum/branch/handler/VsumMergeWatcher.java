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
import org.eclipse.jgit.api.Git;
import tools.vitruv.framework.vsum.branch.data.ValidationResult;
import tools.vitruv.framework.vsum.branch.util.MergeResultFile;
import tools.vitruv.framework.vsum.branch.util.MergeTriggerFile;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;

/**
 * Background daemon that polls for merge trigger files created by the Git {@code post-merge}
 * hook and orchestrates post-merge VSUM validation, state propagation, and metadata writing.
 */
public class VsumMergeWatcher {

  private static final Logger LOGGER = LogManager.getLogger(VsumMergeWatcher.class);

  private static final long POLL_INTERVAL_MS = 500;

  private static final long STOP_TIMEOUT_MS = 2000;

  private static final String THREAD_NAME = "VSUM-Merge-Watcher";

  private static final String LOCK_FILENAME = ".merge.lock";

  private final Path repositoryRoot;

  private final MergeTriggerFile triggerFile;

  private final MergeResultFile resultFile;

  private final PostMergeHandler handler;

  private final Path lockFile;

  private Thread watcherThread;

  @Getter
  private volatile boolean running;

  /**
   * Creates a merge watcher for the given VirtualModel and repository root.
   *
   * @param virtualModel   the VirtualModel to validate and reload after a merge, must not
   *                       be null.
   * @param repositoryRoot the root directory of the Git repository, must not be null.
   */
  public VsumMergeWatcher(InternalVirtualModel virtualModel, Path repositoryRoot) {
    checkNotNull(virtualModel, "virtual model must not be null");
    this.repositoryRoot = checkNotNull(repositoryRoot, "repository root must not be null");
    this.triggerFile = new MergeTriggerFile(repositoryRoot);
    this.resultFile = new MergeResultFile(repositoryRoot);
    this.handler = new PostMergeHandler(virtualModel, repositoryRoot);
    this.running = false;
    this.lockFile = repositoryRoot.resolve(".vitruvius").resolve(LOCK_FILENAME);
  }

  /**
   * Starts the merge watcher in a background daemon thread.
   *
   * @throws IllegalStateException if the watcher is already running.
   */
  public synchronized void start() {
    if (running) {
      throw new IllegalStateException("Merge watcher is already running");
    }
    running = true;
    watcherThread = new Thread(this::watchLoop, THREAD_NAME);
    watcherThread.setDaemon(true);
    watcherThread.start();
    LOGGER.info("V-SUM merge watcher started");
  }

  /**
   * Stops the merge watcher and waits up to {@value #STOP_TIMEOUT_MS} milliseconds for the
   * background thread to terminate. Safe to call on a watcher that is not running.
   */
  public synchronized void stop() {
    if (!running) {
      return;
    }
    running = false;
    if (watcherThread != null) {
      watcherThread.interrupt();
      try {
        watcherThread.join(STOP_TIMEOUT_MS);
      } catch (InterruptedException e) {
        LOGGER.warn("Interrupted while waiting for merge watcher thread to stop");
        Thread.currentThread().interrupt();
      }
    }
    LOGGER.info("V-SUM merge watcher stopped");
  }

  private void watchLoop() {
    LOGGER.debug("Merge watch loop started, polling every {}ms", POLL_INTERVAL_MS);
    while (running) {
      try {
        MergeTriggerFile.TriggerInfo info = triggerFile.checkAndClearTrigger();
        if (info != null) {
          handleMergeRequest(info);
        }
        Thread.sleep(POLL_INTERVAL_MS);
      } catch (InterruptedException e) {
        LOGGER.debug("Merge watcher interrupted");
        running = false;
        Thread.currentThread().interrupt();
      } catch (Exception e) {
        LOGGER.error("Error in merge watcher loop, will continue", e);
      }
    }
    LOGGER.debug("Merge watcher loop exited");
  }

  private void handleMergeRequest(MergeTriggerFile.TriggerInfo info) {
    String mergeShort = info.getMergeCommitSha()
        .substring(0, Math.min(7, info.getMergeCommitSha().length()));
    String requestId = info.getRequestId();

    LOGGER.info("Merge validation requested for commit {} ({} -> {}) (requestId='{}')",
        mergeShort, info.getSourceBranch(), info.getTargetBranch(), requestId);

    try {
      Files.createDirectories(lockFile.getParent());

      // tryLock() is non-blocking: returns null if another merge validation holds it.
      try (FileChannel channel = FileChannel.open(lockFile,
          StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
        FileLock lock = channel.tryLock();
        if (lock == null) {
          LOGGER.warn("Another merge validation is in progress, "
              + "re-queuing trigger for retry (requestId='{}')", requestId);
          try {
            triggerFile.createTrigger(info.getMergeCommitSha(),
                info.getSourceBranch(), info.getTargetBranch());
          } catch (IOException e) {
            LOGGER.error("Failed to re-create merge trigger for retry (requestId='{}')",
                requestId, e);
            // write a warning result so the hook receives a response instead of waiting
            writeWarningResult(requestId, "Merge validation queue full - try again");
          }
          return;
        }
        try {
          LOGGER.debug("Lock acquired for merge validation (requestId='{}')", requestId);
          performMergeValidation(info, mergeShort, requestId);
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
        LOGGER.error("Failed to acquire merge lock (requestId='{}')", requestId, e);
        writeWarningResult(requestId,
            "Failed to acquire merge validation lock: " + e.getMessage());
      }
    } catch (Exception e) {
      LOGGER.error("Unexpected error handling merge request (requestId='{}')", requestId, e);
      writeWarningResult(requestId, "Merge validation failed: " + e.getMessage());
    }
  }

  private void performMergeValidation(MergeTriggerFile.TriggerInfo info,
      String mergeShort, String requestId) {
    try {
      long startTime = System.currentTimeMillis();

      ValidationResult result = handler.performPostMerge(
          info.getSourceBranch(), info.getTargetBranch());
      long duration = System.currentTimeMillis() - startTime;

      LOGGER.info("Post-merge operations completed in {}ms: {} (requestId='{}')",
          duration, result.isValid() ? "PASSED" : "WARNING", requestId);

      try {
        resultFile.writeResult(result, requestId);
      } catch (Exception e) {
        LOGGER.error("Failed to write merge result files (requestId='{}')", requestId, e);
      }

      try {
        handler.generateMergeMetadata(resultFile, info.getMergeCommitSha(),
            info.getSourceBranch(), info.getTargetBranch(), result, List.of());
        LOGGER.info("Merge metadata written for commit {}", mergeShort);
        autoStageMetadata(info.getMergeCommitSha(), mergeShort);
      } catch (Exception e) {
        LOGGER.warn("Failed to generate merge metadata for commit {} (non-critical)",
            mergeShort, e);
      }

    } catch (Exception e) {
      LOGGER.error("Merge validation failed with exception for commit {} (requestId='{}')",
          mergeShort, requestId, e);
      writeWarningResult(requestId, "Merge validation crashed: " + e.getMessage());
    }
  }

  private void autoStageMetadata(String mergeCommitSha, String mergeShort) {
    try (Git git = Git.open(repositoryRoot.toFile())) {
      Path metadataFile = resultFile.getMetadataPath(mergeCommitSha);
      // git add expects a forward-slash path relative to the repo root
      Path relativePath = repositoryRoot.relativize(metadataFile);
      String filePattern = relativePath.toString().replace('\\', '/');
      git.add().addFilepattern(filePattern).call();
      LOGGER.info("Merge metadata auto-staged for commit {} ({})", mergeShort, filePattern);
    } catch (Exception e) {
      LOGGER.warn("Failed to auto-stage merge metadata for commit {} (not critical)",
          mergeShort, e);
    }
  }

  /**
   * Writes a warning result file when merge validation cannot be performed, for
   * example when the lock cannot be acquired. This ensures the post-merge hook
   * always receives a response and does not hang waiting indefinitely.
   *
   * <p>The result is written as a failure with a warning message rather than a hard
   * failure, consistent with the non-blocking nature of post-merge validation.
   *
   * @param requestId    the request identifier used to name the result file.
   * @param errorMessage the warning message to include in the result.
   */
  private void writeWarningResult(String requestId, String errorMessage) {
    try {
      ValidationResult warningResult = ValidationResult.failure(List.of(errorMessage));
      resultFile.writeResult(warningResult, requestId);
    } catch (Exception writeError) {
      LOGGER.error("Failed to write warning result (requestId='{}')", requestId, writeError);
    }
  }
}
