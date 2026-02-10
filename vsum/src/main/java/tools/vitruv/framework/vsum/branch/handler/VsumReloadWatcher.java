package tools.vitruv.framework.vsum.branch.handler;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tools.vitruv.framework.vsum.VirtualModel;
import tools.vitruv.framework.vsum.branch.util.ReloadTriggerFile;
import tools.vitruv.framework.vsum.branch.exception.BranchOperationException;

import java.nio.file.Path;

/**
 * Watches a trigger file in the background and reloads the VSUM when needed.
 * How it works:
 * - Git hook creates a trigger file after a branch switch.
 * - This watcher checks for that file every CHECK_INTERVAL_MS milliseconds.
 * - If the file exists, VSUM reloads models and the file is deleted.
 * This ensures the VSUM stays updated even when developers use command-line Git.
 */
public class VsumReloadWatcher {
    private static final Logger LOGGER = LogManager.getLogger(VsumReloadWatcher.class);

    /**
     * How often to check for the trigger file (in milliseconds).
     * VSUM is only reloaded if a trigger exists, not reloaded every interval.
     */
    private static final long CHECK_INTERVAL_MS = 500;
    private final ReloadTriggerFile triggerFile;
    private final PostCheckoutHandler handler;

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
        this.triggerFile = new ReloadTriggerFile(repositoryRoot);
        this.handler = new PostCheckoutHandler(virtualModel);
        this.running = false;
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
     */
    private void watchLoop() {
        LOGGER.debug("Watch loop started");

        while (running) {
            try {
                // Check if trigger file exists
                if (triggerFile.checkAndClearTrigger()) {
                    LOGGER.info("Reload trigger detected, reloading VSUM...");
                    try {
                        handler.onBranchSwitch("unknown", "unknown");
                        LOGGER.info("VSUM reload completed successfully");
                    } catch (BranchOperationException e) {
                        LOGGER.error("Failed to reload VSUM after trigger detection", e);
                    }
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
}