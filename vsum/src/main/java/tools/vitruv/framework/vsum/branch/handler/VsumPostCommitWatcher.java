package tools.vitruv.framework.vsum.branch.handler;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jgit.api.Git;
import tools.vitruv.framework.vsum.branch.data.SemanticChangelog;
import tools.vitruv.framework.vsum.branch.util.PostCommitTriggerFile;

import java.nio.file.Path;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Background daemon that polls for post-commit trigger files created by the Git {@code post-commit} hook and generates the semantic changelog for each commit.
 *
 * <p>Unlike {@link VsumValidationWatcher}, this watcher is fire-and-forget: the hook exits immediately after writing the trigger file without waiting for any result.
 * The changelog is generated asynchronously in the background while the developer continues working.
 *
 * <p>No lock file is needed because changelog generation is read-only with respect to the VSUM
 * it reads the commit information from the trigger and writes a changelog file, but does not modify the virtual model state.
 *
 * <p>When a trigger is detected, the watcher:
 * <ol>
 *   <li>Reads the real commit SHA and branch from the trigger file.</li>
 *   <li>Generates the semantic changelog using {@link PostCommitHandler}.</li>
 *   <li>Writes the changelog to {@code .vitruvius/changelogs/<shortSha>.txt}.</li>
 *   <li>Auto-stages the changelog file so it is tracked by Git.</li>
 * </ol>
 *
 * <p>The changelog file name and content both use the real commit SHA, ensuring the changelog is traceable via {@code git log}.
 * This is not possible with pre-commit hooks, where the SHA is not yet known.
 */
public class VsumPostCommitWatcher {

    private static final Logger LOGGER = LogManager.getLogger(VsumPostCommitWatcher.class);
    private static final long POLL_INTERVAL_MS = 500;
    private static final long STOP_TIMEOUT_MS = 2000;
    private static final String THREAD_NAME = "VSUM-PostCommit-Watcher";

    private final Path repositoryRoot;
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
        this.repositoryRoot = checkNotNull(repositoryRoot, "repository root must not be null");
        this.triggerFile = new PostCommitTriggerFile(repositoryRoot);
        this.handler = new PostCommitHandler(repositoryRoot);
        this.running = false;
    }

    /**
     * Starts the post-commit watcher in a background daemon thread.
     * The thread is marked as a daemon so it does not prevent the JVM from shutting down when the main application exits.
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
     * Stops the post-commit watcher and waits up to {@value #STOP_TIMEOUT_MS}
     * milliseconds for the background thread to terminate. Calling this method on a
     * watcher that is not running is safe and does nothing.
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
                LOGGER.warn("Interrupted while waiting for post-commit watcher thread to stop");
                // restore the interrupt flag so the caller can handle it if needed.
                Thread.currentThread().interrupt();
            }
        }
        LOGGER.info("V-SUM post-commit watcher stopped");
    }

    /**
     * Main polling loop executed by the background thread. Checks for a trigger file on every cycle and delegates to {@link #handlePostCommit} when one is found.
     * Exits when {@link #running} becomes false or the thread is interrupted.
     */
    private void watchLoop() {
        LOGGER.debug("Post-commit watch loop started, polling every {}ms", POLL_INTERVAL_MS);
        while (running) {
            try {
                // check whether a post-commit trigger has been created by the post-commit hook.
                PostCommitTriggerFile.TriggerInfo info = triggerFile.checkAndClearTrigger();
                if (info != null) {
                    handlePostCommit(info);
                }
                Thread.sleep(POLL_INTERVAL_MS);
            } catch (InterruptedException e) {
                LOGGER.debug("Post-commit watcher interrupted");
                running = false;
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                // log and continue so that a single bad trigger does not permanently stop the watcher from processing future commits.
                LOGGER.error("Error in post-commit watcher loop, will continue", e);
            }
        }
        LOGGER.debug("Post-commit watch loop exited");
    }

    /**
     * Handles a single post-commit event: generates the changelog and auto-stages it.
     * A failure to generate or stage the changelog is logged as a warning and does not affect anything else
     *
     * @param info the trigger information containing the real commit SHA and branch.
     */
    private void handlePostCommit(PostCommitTriggerFile.TriggerInfo info) {
        String commitShort = info.getCommitSha().substring(0, Math.min(7, info.getCommitSha().length()));

        LOGGER.info("Post-commit changelog generation triggered for commit {} on branch {}", commitShort, info.getBranch());
        try {
            SemanticChangelog changelog = handler.generateChangelog(info.getCommitSha(), info.getBranch());

            Path changelogFile = repositoryRoot.resolve(".vitruvius").resolve("changelogs").resolve(commitShort + ".txt");

            changelog.writeTo(changelogFile);
            LOGGER.info("Semantic changelog generated: {}", changelogFile.getFileName());

            autoStageChangelog(changelogFile, commitShort);

        } catch (Exception e) {
            LOGGER.warn("Failed to generate changelog for commit {} (commit is not affected)", commitShort, e);
        }
    }

    /**
     * Auto-stages the given changelog file using JGit so that it is tracked by Git.
     * A failure to stage is logged as a warning and does not affect the commit.
     *
     * @param changelogFile the changelog file to stage.
     * @param commitShort   the short commit SHA, used in log messages.
     */
    private void autoStageChangelog(Path changelogFile, String commitShort) {
        try (Git git = Git.open(repositoryRoot.toFile())) {
            // git add expects a forward-slash path relative to the repository root.
            Path relativePath = repositoryRoot.relativize(changelogFile);
            String filePattern = relativePath.toString().replace('\\', '/');
            git.add().addFilepattern(filePattern).call();
            LOGGER.info("Changelog auto-staged for commit {} ({})", commitShort, filePattern);
        } catch (Exception e) {
            LOGGER.warn("Failed to auto-stage changelog for commit {} (not critical)", commitShort, e);
            LOGGER.warn("Developer can manually stage with: git add {}", changelogFile);
        }
    }
}