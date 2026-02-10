package tools.vitruv.framework.vsum.branch.handler;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tools.vitruv.framework.vsum.VirtualModel;
import tools.vitruv.framework.vsum.branch.util.ReloadTriggerFile;
import tools.vitruv.framework.vsum.branch.exception.BranchOperationException;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * Watches a trigger file in the background and reloads the VSUM when needed.
 *
 * <p>How it works:
 * <ul>
 *   <li>Git hook creates a trigger file after a branch switch</li>
 *   <li>This watcher checks for that file every CHECK_INTERVAL_MS milliseconds</li>
 *   <li>If the file exists, VSUM reloads models and the file is deleted</li>
 * </ul>
 *
 * <p>This ensures the VSUM stays updated even when developers use command-line Git.
 *
 * <p><b>NEW:</b> Lock file prevents concurrent reloads from corrupting VSUM state.
 * Request IDs enable debugging but do NOT block the hook (fire-and-forget).
 */
public class VsumReloadWatcher {
    private static final Logger LOGGER = LogManager.getLogger(VsumReloadWatcher.class);

    /**
     * How often to check for the trigger file (in milliseconds).
     * VSUM is only reloaded if a trigger exists, not reloaded every interval.
     */
    private static final long CHECK_INTERVAL_MS = 500;
    private static final String LOCK_FILENAME = ".reload.lock";  // Lock file name

    private final ReloadTriggerFile triggerFile;
    private final PostCheckoutHandler handler;
    private final Path lockFile;  //Lock file path

    // Background thread that checks the trigger file
    private Thread watcherThread;
    @Getter
    private volatile boolean running;

    /**
     * Creates a new watcher for the given VSUM and repository.
     *
     * @param virtualModel   The VSUM to reload when triggered
     * @param repositoryRoot The root directory of the Git repository
     */
    public VsumReloadWatcher(VirtualModel virtualModel, Path repositoryRoot) {
        // Store for lock file
        this.triggerFile = new ReloadTriggerFile(repositoryRoot);
        this.handler = new PostCheckoutHandler(virtualModel);
        this.running = false;
        this.lockFile = repositoryRoot.resolve(".vitruvius").resolve(LOCK_FILENAME);  // NEW
    }

    /**
     * Starts the watcher in a background thread.
     * Checks for reload triggers every CHECK_INTERVAL_MS milliseconds.
     *
     * @throws IllegalStateException if the watcher is already running
     */
    public void start() {
        if (running) {
            throw new IllegalStateException("Watcher is already running");
        }
        running = true;
        watcherThread = new Thread(this::watchLoop, "VSUM-Reload-Watcher");
        watcherThread.setDaemon(true);
        watcherThread.start();
        LOGGER.info("VSUM reload watcher started (checking every {}ms)", CHECK_INTERVAL_MS);
    }

    /**
     * Stops the watcher thread safely.
     * Waits for it to finish before returning.
     */
    public void stop() {
        if (!running) {
            return;
        }
        LOGGER.info("Stopping VSUM reload watcher...");
        running = false;
        if (watcherThread != null) {
            watcherThread.interrupt();  // wake up if sleeping
            try {
                watcherThread.join(2000);  // wait up to 2 seconds for clean shutdown
            } catch (InterruptedException e) {
                LOGGER.warn("Interrupted while waiting for watcher thread to stop");
                Thread.currentThread().interrupt();  // restore interrupt status
            }
        }
        LOGGER.info("VSUM reload watcher stopped");
    }

    /**
     * The main loop of the watcher.
     * Checks the trigger file periodically and reloads VSUM if needed.
     *
     * <p> Acquires lock before reload to prevent concurrent reloads from corrupting VSUM state.
     */
    private void watchLoop() {
        LOGGER.debug("Watch loop started");

        while (running) {
            try {
                //Check if trigger file exists and parse it
                ReloadTriggerFile.TriggerInfo info = triggerFile.checkAndClearTrigger();
                if (info != null) {
                    handleReloadRequest(info);
                }
                // wait before checking again
                Thread.sleep(CHECK_INTERVAL_MS);

            } catch (InterruptedException e) {
                LOGGER.debug("Watcher thread interrupted, stopping");
                break;
            } catch (Exception e) {
                LOGGER.error("Unexpected error in watcher loop", e);
            }
        }
        LOGGER.debug("Watch loop exited");
    }

    /**
     * NEW: Handles a reload request with lock protection.
     *
     * <p>Acquires lock before reloading to prevent concurrent reloads.
     * If lock cannot be acquired, logs warning and retries on next poll.
     *
     * @param info the trigger information (branch name, request ID, timestamp)
     */
    private void handleReloadRequest(ReloadTriggerFile.TriggerInfo info) {
        LOGGER.info("Reload trigger detected for branch '{}' (requestId={})", info.getBranchName(), info.getRequestId());

        try {
            // Ensure .vitruvius directory exists
            Files.createDirectories(lockFile.getParent());

            // Try to acquire lock (non-blocking)
            try (FileChannel channel = FileChannel.open(lockFile, StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
                FileLock lock = channel.tryLock();
                if (lock == null) {
                    // Another reload is in progress
                    LOGGER.warn("Another reload is in progress, will retry on next poll (requestId={})", info.getRequestId());

                    // Put the trigger back for retry
                    try {
                        triggerFile.createTrigger(info.getBranchName());
                        LOGGER.debug("Re-created trigger for retry (requestId={})", info.getRequestId());
                    } catch (IOException e) {
                        LOGGER.error("Failed to re-create trigger for retry (requestId={})", info.getRequestId(), e);
                    }
                    return;
                }

                try {
                    // Lock acquired - perform reload
                    LOGGER.debug("Lock acquired for reload (requestId={})", info.getRequestId());
                    performReload(info);

                } finally {
                    // Release lock
                    lock.release();
                    LOGGER.debug("Lock released (requestId={})", info.getRequestId());
                }

            } catch (IOException e) {
                LOGGER.error("Failed to acquire lock for reload (requestId={})", info.getRequestId(), e);
            }

        } catch (Exception e) {
            LOGGER.error("Unexpected error handling reload request (requestId={})", info.getRequestId(), e);
        }
    }

    /**
     * Performs the actual reload (called while holding lock).
     * The handler will reload regardless of branch names.
     *
     * @param info the trigger information
     */
    private void performReload(ReloadTriggerFile.TriggerInfo info) {
        try {
            long startTime = System.currentTimeMillis();

            // Reload VSUM
            handler.onBranchSwitch("unknown", info.getBranchName());

            long duration = System.currentTimeMillis() - startTime;
            LOGGER.info("VSUM reload completed successfully in {}ms (branch={}, requestId={})", duration, info.getBranchName(), info.getRequestId());

        } catch (BranchOperationException e) {
            LOGGER.error("Failed to reload VSUM after trigger detection (branch={}, requestId={})", info.getBranchName(), info.getRequestId(), e);
            // no result file, user will see the error in logs if they check
        }
    }
}