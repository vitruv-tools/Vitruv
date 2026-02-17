package tools.vitruv.framework.vsum.branch.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;

/**
 * Base class for all Vitruvius trigger files used for inter-process communication
 * between Git hooks and background watcher threads.
 *
 * <p>Each trigger file follows the same lifecycle:
 * <ol>
 *   <li>A Git hook creates the trigger file by calling {@code createTrigger(...)}
 *       on a concrete subclass.</li>
 *   <li>A background watcher polls for the file at a fixed interval.</li>
 *   <li>When found, the watcher calls {@link #checkAndClearTrigger()}, which reads
 *       the file, deletes it, and returns the parsed trigger information.</li>
 *   <li>The watcher acts on the information (reload, validate, or merge-validate)
 *       and optionally writes a result file for the hook to read.</li>
 * </ol>
 *
 * <p>All trigger files share the same pipe-delimited format, the same file lifecycle,
 * and the same error-handling strategy. Subclasses differ only in which fields they
 * write to the file and how they parse those fields back into a typed
 * {@link TriggerInfo} record.
 *
 * <p>Subclasses must:
 * <ul>
 *   <li>Call {@code super(repositoryRoot, filename)} from their constructor.</li>
 *   <li>Implement {@code createTrigger(...)} to write the pipe-delimited content
 *       via {@link #writeTrigger(String)}.</li>
 *   <li>Implement {@link #parseTriggerInfo(String[])} to parse the split parts
 *       into a typed {@link TriggerInfo} subclass instance, or return {@code null}
 *       if the parts are invalid.</li>
 * </ul>
 *
 * @param <T> the concrete {@link TriggerInfo} type produced by this trigger file.
 */
public abstract class AbstractTriggerFile<T extends AbstractTriggerFile.TriggerInfo> {

    private static final Logger LOGGER = LogManager.getLogger(AbstractTriggerFile.class);

    /**
     * Delimiter separating fields inside every trigger file.
     */
    protected static final String DELIMITER = "|";

    /**
     * Pre-compiled pattern for splitting trigger file content on the delimiter.
     * {@link Pattern#quote} prevents the pipe character from being treated as a
     * regex alternation operator.
     */
    protected static final Pattern SPLIT_PATTERN = Pattern.compile(Pattern.quote(DELIMITER));

    /**
     * Full path to the trigger file on disk. Set once at construction time.
     */
    protected final Path triggerFilePath;

    /**
     * Constructs the trigger file manager for the given repository root and filename.
     * The trigger file will be located at
     * {@code <repositoryRoot>/.vitruvius/<filename>}.
     *
     * @param repositoryRoot the root directory of the Git repository. Must not be null.
     * @param filename       the name of the trigger file inside {@code .vitruvius/}.
     *                       Must not be null.
     */
    protected AbstractTriggerFile(Path repositoryRoot, String filename) {
        this.triggerFilePath = repositoryRoot.resolve(".vitruvius").resolve(filename);
    }

    /**
     * Checks whether the trigger file currently exists on disk. Does not read or
     * modify the file.
     *
     * @return {@code true} if the trigger file exists, {@code false} otherwise.
     */
    public boolean exists() {
        return Files.exists(triggerFilePath);
    }

    /**
     * Returns the full path to the trigger file. Useful for Git hook scripts that
     * need to know the path before writing it.
     *
     * @return the full path to the trigger file.
     */
    public Path getTriggerPath() {
        return triggerFilePath;
    }

    /**
     * Checks whether the trigger file exists. If it does, reads the content, deletes
     * the file, and returns the parsed trigger information. Returns {@code null} if
     * the file does not exist, cannot be read, or contains malformed content.
     *
     * <p>If the file is malformed, it is deleted so the watcher does not retry the
     * same corrupted file on the next poll cycle.
     *
     * <p>If the file cannot be deleted after being read successfully, the trigger
     * information is still returned and the failure is logged as a warning. The
     * watcher will reattempt deletion on the next poll cycle.
     *
     * @return the parsed trigger information, or {@code null} if no valid trigger
     * was found.
     */
    public T checkAndClearTrigger() {
        if (!Files.exists(triggerFilePath)) {
            return null;
        }

        try {
            String content = Files.readString(triggerFilePath);
            String[] parts = SPLIT_PATTERN.split(content.trim());

            // delegate field-level parsing to the subclass - it knows how many fields
            // to expect and what each field means.
            T info = parseTriggerInfo(parts);
            if (info == null) {
                // parseTriggerInfo returns null when the parts are invalid; discard the file.
                LOGGER.warn("Malformed trigger file content at '{}', discarding: '{}'", triggerFilePath, content);
                attemptFileDeletion();
                return null;
            }

            // attempt to delete the trigger now that it has been successfully consumed.
            // if the delete fails, the info is still returned so the watcher can act on it;
            // deletion will be retried on the next poll cycle.
            try {
                Files.delete(triggerFilePath);
                LOGGER.debug("Trigger file consumed and cleared: {}", triggerFilePath);
            } catch (IOException deleteEx) {
                LOGGER.warn("Failed to delete trigger file '{}', will retry on next poll", triggerFilePath, deleteEx);
            }

            return info;

        } catch (IOException e) {
            LOGGER.warn("Failed to read trigger file: {}", triggerFilePath, e);
            return null;
        }
    }

    /**
     * Writes the given pipe-delimited content to the trigger file, creating the
     * {@code .vitruvius} parent directory if it does not already exist.
     *
     * <p>Subclasses call this from their {@code createTrigger(...)} implementation
     * after formatting the content string.
     *
     * @param content the pipe-delimited string to write. Must not be null.
     * @throws IOException if the file or its parent directory cannot be created or written.
     */
    protected void writeTrigger(String content) throws IOException {
        Files.createDirectories(triggerFilePath.getParent());
        Files.writeString(triggerFilePath, content);
        LOGGER.debug("Trigger file written: {}", triggerFilePath);
    }

    /**
     * Parses the split fields from the trigger file content into a typed
     * {@link TriggerInfo} instance.
     *
     * <p>Implementations must validate the number of fields and the content of each
     * required field. If the parts are invalid or a required field is empty, the
     * implementation must return {@code null} - {@link #checkAndClearTrigger()} will
     * then discard and delete the file.
     *
     * @param parts the fields split from the trigger file content on {@link #DELIMITER}.
     * @return the parsed trigger information, or {@code null} if the parts are invalid.
     */
    protected abstract T parseTriggerInfo(String[] parts);

    /**
     * Attempts to delete the trigger file, logging a warning if the deletion fails.
     * Used to discard malformed trigger files so they are not re-processed on the
     * next poll cycle.
     */
    protected void attemptFileDeletion() {
        try {
            Files.delete(triggerFilePath);
        } catch (IOException e) {
            LOGGER.warn("Could not delete malformed trigger file '{}': {}", triggerFilePath, e.getMessage());
        }
    }

    /**
     * Base class for all trigger information records parsed from trigger files.
     *
     * <p>Every trigger carries a unique request identifier for traceability across
     * hook logs and watcher logs, and a timestamp for debugging and timeout detection.
     * Subclasses add the domain-specific fields relevant to their hook type.
     */
    public abstract static class TriggerInfo {

        /**
         * Unique request identifier (UUID) for tracing this request across logs.
         */
        private final String requestId;

        /**
         * Timestamp in milliseconds since epoch when the trigger file was created.
         */
        private final long timestamp;

        /**
         * Creates a new {@link TriggerInfo} with the given request identifier and timestamp.
         *
         * @param requestId the unique request identifier. Must not be null.
         * @param timestamp the creation timestamp in milliseconds since epoch.
         */
        protected TriggerInfo(String requestId, long timestamp) {
            this.requestId = java.util.Objects.requireNonNull(requestId, "request identifier must not be null");
            this.timestamp = timestamp;
        }

        /**
         * Returns the unique request identifier for this trigger.
         *
         * @return the request identifier, never null.
         */
        public String getRequestId() {
            return requestId;
        }

        /**
         * Returns the timestamp when this trigger was created.
         *
         * @return milliseconds since epoch.
         */
        public long getTimestamp() {
            return timestamp;
        }
    }
}