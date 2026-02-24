package tools.vitruv.framework.vsum.branch.handler;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tools.vitruv.framework.vsum.VirtualModel;
import tools.vitruv.framework.vsum.branch.exception.BranchOperationException;
import tools.vitruv.framework.vsum.branch.util.ReloadTriggerFile;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * Background daemon that polls for a reload trigger file and reloads Vsum when a branch switch is detected.
 *
 * <p>Logic:
 * <ul>
 *   <li>The {@code post-checkout} Git hook writes a trigger file after a branch switch.</li>
 *   <li>This watcher checks for that file every {@value #CHECK_INTERVAL_MS} milliseconds.</li>
 *   <li>When the file is found, the VirtualModel is reloaded and the file is deleted.</li>
 * </ul>
 *
 * <p>This file-based approach ensures the VirtualModel stays consistent even when
 * developers perform branch switches directly through the Git command line rather than through the Vitruvius API.
 *
 * <p>Concurrency protection: a {@link FileLock} on {@code .vitruvius/.reload.lock} ensures that only one reload runs at a time.
 * If the lock cannot be acquired because another reload is already in progress, the trigger file is re-created so the request is retried on the next poll cycle.
 */
public class VsumReloadWatcher {
    private static final Logger LOGGER = LogManager.getLogger(VsumReloadWatcher.class);

    /**
     * How often the watcher polls for a trigger file, in milliseconds.
     */
    private static final long CHECK_INTERVAL_MS = 500;

    /**
     * Maximum time in milliseconds to wait for the watcher thread to terminate on stop.
     */
    private static final long STOP_TIMEOUT_MS = 2000;

    private static final String LOCK_FILENAME = ".reload.lock";

    private final ReloadTriggerFile triggerFile;
    private final PostCheckoutHandler handler;

    /**
     * Path to the OS-managed lock file that prevents concurrent reloads.
     */
    private final Path lockFile;

    private Thread watcherThread;

    @Getter
    private volatile boolean running;

    /**
     * Creates a new {@link VsumReloadWatcher} for the given VirtualModel and repository.
     *
     * @param virtualModel   the VirtualModel to reload when a branch switch is detected.
     * @param repositoryRoot the root directory of the Git repository. The trigger file and lock file will be located under {@code .vitruvius/} inside this directory.
     */
    public VsumReloadWatcher(VirtualModel virtualModel, Path repositoryRoot) {
        this.triggerFile = new ReloadTriggerFile(repositoryRoot);
        this.handler = new PostCheckoutHandler(virtualModel);
        this.running = false;
        this.lockFile = repositoryRoot.resolve(".vitruvius").resolve(LOCK_FILENAME);
    }

    /**
     * Starts the watcher in a background daemon thread.
     * The thread is marked as a daemon so it does not prevent the JVM from shutting down when the main application exits.
     *
     * @throws IllegalStateException if the watcher is already running.
     */
    public synchronized void start() {
        if (running) {
            throw new IllegalStateException("watcher is already running");
        }
        running = true;
        watcherThread = new Thread(this::watchLoop, "VSUM-Reload-Watcher");
        watcherThread.setDaemon(true);
        watcherThread.start();
        LOGGER.info("VSUM reload watcher started (polling every {}ms)", CHECK_INTERVAL_MS);
    }

    /**
     * Stops the watcher thread and waits up to {@value #STOP_TIMEOUT_MS} milliseconds for it to terminate cleanly.
     * Calling this method on a watcher that is not running is safe and does nothing.
     */
    public synchronized void stop() {
        if (!running) {
            return;
        }
        LOGGER.info("Stopping VSUM reload watcher");
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

        // clean up any leftover lock file when stopping
        try {
            if (Files.deleteIfExists(lockFile)) {
                LOGGER.debug("Cleaned up lock file on watcher stop");
            }
        } catch (IOException e) {
            LOGGER.warn("Failed to clean up lock file on stop (non-critical)", e);
        }

        LOGGER.info("VSUM reload watcher stopped");
    }

    /**
     * Main loop executed by the background thread. Polls for the trigger file and delegates to {@link #handleReloadRequest} when a trigger is found.
     * Exits when {@link #running} becomes false or the thread is interrupted.
     */
    private void watchLoop() {
        LOGGER.debug("Watch loop started");

        while (running) {
            try {
                // check whether the trigger file exists and parse its contents if so.
                ReloadTriggerFile.TriggerInfo info = triggerFile.checkAndClearTrigger();
                if (info != null) {
                    handleReloadRequest(info);
                }
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
     * Handles a single reload request with OS-level lock protection to prevent concurrent reloads from corrupting the VirtualModel state.
     * <p>If the lock cannot be acquired because another reload is already in progress, the trigger is re-created so that the request is retried on the next poll cycle.
     * This approach is consistent with the validation watcher and avoids silently dropping branch switch events under load.
     *
     * @param info the trigger information parsed from the trigger file.
     */
    private void handleReloadRequest(ReloadTriggerFile.TriggerInfo info) {
        LOGGER.info("Reload trigger detected for branch '{}' (requestId='{}')", info.getBranchName(), info.getRequestId());

        try {
            // the lock file directory must exist before FileChannel.open() can create the file.
            Files.createDirectories(lockFile.getParent());

            // use a non-blocking tryLock() so the watcher can immediately retry on the next
            // poll cycle rather than blocking indefinitely if another reload is in progress.
            try (FileChannel channel = FileChannel.open(lockFile,
                    StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
                FileLock lock = channel.tryLock();
                if (lock == null) {
                    // the lock is held by another reload operation
                    // require re-creating the trigger so this request is not lost and will be retried on the next poll.
                    LOGGER.warn("Another reload is in progress, re-queuing trigger for retry (requestId='{}')", info.getRequestId());
                    try {
                        triggerFile.createTrigger(info.getBranchName());
                    } catch (IOException e) {
                        LOGGER.error("Failed to re-create trigger for retry (requestId='{}')", info.getRequestId(), e);
                    }
                    return;
                }

                try {
                    LOGGER.debug("Lock acquired, performing reload (requestId='{}')", info.getRequestId());
                    performReload(info);
                } finally {
                    // always release the lock, even if performReload throws, to unblock any reload requests that are waiting to retry.
                    lock.release();
                    LOGGER.debug("Lock released (requestId='{}')", info.getRequestId());
                    // delete the lock file after releasing the lock
                    try {
                        Files.deleteIfExists(lockFile);
                        LOGGER.debug("Lock file deleted (requestId='{}')", info.getRequestId());
                    } catch (IOException deleteError) {
                        LOGGER.warn("Failed to delete lock file (non-critical) (requestId='{}')", info.getRequestId(), deleteError);
                    }
                }

            } catch (IOException e) {
                LOGGER.error("Failed to acquire reload lock (requestId='{}')", info.getRequestId(), e);
            }

        } catch (Exception e) {
            LOGGER.error("Unexpected error handling reload request (requestId='{}')", info.getRequestId(), e);
        }
    }

    /**
     * Performs the actual VirtualModel reload while holding the lock.
     * The old branch name is passed as {@code "unknown"} because the watcher receives only the new branch name from the trigger file
     * The previous branch is not available in the fire-and-forget inter-process communication path.
     *
     * @param info the trigger information containing the new branch name and request identifier.
     */
    private void performReload(ReloadTriggerFile.TriggerInfo info) {
        try {
            long startTime = System.currentTimeMillis();
            // the old branch name is unknown in this path because the trigger file only records the new branch.
            // passing "unknown" is safe because PostCheckoutHandler uses the old branch name only for logging, not for any model operation.
            handler.onBranchSwitch("unknown", info.getBranchName());
            long duration = System.currentTimeMillis() - startTime;
            LOGGER.info("VirtualModel reloaded successfully in {}ms (branch='{}', requestId='{}')", duration, info.getBranchName(), info.getRequestId());

        } catch (BranchOperationException e) {
            // a failed reload leaves the VirtualModel in a stale state.
            // the error is logged so the developer can investigate
            LOGGER.error("Failed to reload VirtualModel after branch switch (branch='{}', requestId='{}')", info.getBranchName(), info.getRequestId(), e);
        }
    }
}