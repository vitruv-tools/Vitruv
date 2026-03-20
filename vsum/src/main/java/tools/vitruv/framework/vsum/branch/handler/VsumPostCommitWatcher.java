package tools.vitruv.framework.vsum.branch.handler;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.emf.ecore.resource.Resource;
import tools.vitruv.change.atomic.uuid.UuidResolver;
import tools.vitruv.framework.vsum.branch.storage.SemanticChangeBuffer;
import tools.vitruv.framework.vsum.branch.util.PostCommitTriggerFile;

import java.nio.file.Path;
import java.util.Collection;
import java.util.function.Supplier;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Background daemon that polls for post-commit trigger files created by the Git
 * {@code post-commit} hook and delegates handling to {@link PostCommitHandler}.
 *
 * <p>Responsibilities of this class are limited to lifecycle management and trigger detection:
 * <ol>
 *   <li>Start and stop the background polling thread.</li>
 *   <li>Poll {@link PostCommitTriggerFile} every {@value #POLL_INTERVAL_MS} ms.</li>
 *   <li>On detection, hand off to {@link PostCommitHandler#handlePostCommit} and clear the trigger.</li>
 * </ol>
 *
 * <p>All post-commit actions (changelog writing, file staging, etc.) are the responsibility
 * of {@link PostCommitHandler}, keeping this class focused on detection only.
 *
 * <p>The watcher thread is a daemon thread so it does not prevent JVM shutdown.
 */
public class VsumPostCommitWatcher {

    private static final Logger LOGGER = LogManager.getLogger(VsumPostCommitWatcher.class);
    private static final long POLL_INTERVAL_MS = 500;
    private static final long STOP_TIMEOUT_MS = 2000;
    private static final String THREAD_NAME = "VSUM-PostCommit-Watcher";

    private final PostCommitTriggerFile triggerFile;
    private final PostCommitHandler handler;

    private Thread watcherThread;

    /**
     * True while the background polling thread is running.
     */
    @Getter
    private volatile boolean running;

    /**
     * Creates a post-commit watcher for the given repository.
     *
     * @param repositoryRoot the root directory of the Git repository. Must not be null.
     */
    public VsumPostCommitWatcher(Path repositoryRoot) {
        checkNotNull(repositoryRoot, "repository root must not be null");
        this.triggerFile = new PostCommitTriggerFile(repositoryRoot);
        this.handler = new PostCommitHandler(repositoryRoot);
        this.running = false;
    }

    /**
     * Attaches semantic change tracking so that JSON and XMI changelogs are written
     * on every post-commit trigger (direct Git path). Delegates to {@link PostCommitHandler}.
     *
     * <p>Must be called before the first commit to ensure no changes are missed.
     * Calling after {@link #start()} is safe but may miss the first trigger.
     *
     * @param changeBuffer     buffer that accumulated EChanges since the last drain, must not be null.
     * @param uuidResolver     resolver used to convert EObjects to stable UUIDs, must not be null.
     * @param resourceSupplier supplier that returns the currently loaded EMF resources, must not be null.
     */
    public void attachSemanticChangeTracking(SemanticChangeBuffer changeBuffer, UuidResolver uuidResolver, Supplier<Collection<Resource>> resourceSupplier) {
        handler.attachSemanticChangeTracking(changeBuffer, uuidResolver, resourceSupplier);
    }

    /**
     * Starts the post-commit watcher in a background daemon thread.
     *
     * @throws IllegalStateException if the watcher is already running.
     */
    public synchronized void start() {
        if (running) {
            throw new IllegalStateException("Post-commit watcher is already running");
        }
        running = true;
        watcherThread = new Thread(this::watchLoop, THREAD_NAME);
        watcherThread.setDaemon(true);
        watcherThread.start();
        LOGGER.info("V-SUM post-commit watcher started");
    }

    /**
     * Stops the post-commit watcher and waits up to {@value #STOP_TIMEOUT_MS} milliseconds
     * for the background thread to terminate. Safe to call on a watcher that is not running.
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
                LOGGER.warn("Interrupted while waiting for post-commit watcher thread to stop");
                Thread.currentThread().interrupt();
            }
        }
        LOGGER.info("V-SUM post-commit watcher stopped");
    }

    private void watchLoop() {
        LOGGER.debug("Post-commit watch loop started, polling every {}ms", POLL_INTERVAL_MS);
        while (running) {
            try {
                PostCommitTriggerFile.TriggerInfo info = triggerFile.checkAndClearTrigger();
                if (info != null) {
                    handler.handlePostCommit(info.getCommitSha(), info.getBranch());
                }
                Thread.sleep(POLL_INTERVAL_MS);
            } catch (InterruptedException e) {
                LOGGER.debug("Post-commit watcher interrupted");
                running = false;
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                LOGGER.error("Error in post-commit watcher loop, will continue", e);
            }
        }
        LOGGER.debug("Post-commit watch loop exited");
    }
}
