package tools.vitruv.framework.vsum.branch.util;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Manages the trigger file used for inter-process communication between the Git {@code pre-commit} hook and the Vitruvius background validation watcher.
 * <ul>
 *   <li>The Git pre-commit hook creates this file before allowing the commit.</li>
 *   <li>The file contains the commit SHA, branch name, request identifier, and timestamp.</li>
 *   <li>{@link tools.vitruv.framework.vsum.branch.handler.VsumValidationWatcher} polls for the file every 500ms.</li>
 *   <li>When found, the watcher validates the virtual model, writes the result, and deletes the trigger file.</li>
 *   <li>The hook reads the validation result and allows or blocks the commit.</li>
 * </ul>
 * <p>Concurrent commits are supported through unique request identifiers.
 * Each request produces its own pair of result files identified by the request identifier, so two simultaneous hook invocations cannot read each other's results.
 */
public class ValidationTriggerFile {
    private static final Logger LOGGER = LogManager.getLogger(ValidationTriggerFile.class);
    private static final String TRIGGER_FILENAME = "validate-trigger";
    private static final String DELIMITER = "|";

    /**
     * Pre-compiled pattern for splitting the trigger file content on the delimiter.
     * Using {@link Pattern#quote} prevents the pipe character from being interpreted as a regex alternation operator.
     */
    private static final Pattern SPLIT_PATTERN = Pattern.compile(Pattern.quote(DELIMITER));

    private final Path triggerFilePath;

    /**
     * Creates a new validation trigger file manager for the given repository root.
     * The trigger file will be located at {@code <repositoryRoot>/.vitruvius/validate-trigger}.
     * @param repositoryRoot the root directory of the Git repository.
     */
    public ValidationTriggerFile(Path repositoryRoot) {
        this.triggerFilePath = repositoryRoot.resolve(".vitruvius").resolve(TRIGGER_FILENAME);
    }

    /**
     * Creates the trigger file to request validation of the virtual model for the given commit.
     * A unique request identifier is generated automatically and returned so that the hook can later read the correct result file by identifier.
     * <p>File format: {@code commitSha|branch|requestId|timestamp}
     * <p>The parent directory is created automatically if it does not exist yet.
     * @param commitSha the Git commit SHA, must not be null.
     * @param branch    the branch name, must not be null.
     * @return the generated request identifier (UUID string) for reading the result file.
     * @throws IOException if the file or its parent directory cannot be created.
     */
    public String createTrigger(String commitSha, String branch) throws IOException {
        checkNotNull(commitSha, "commit SHA must not be null");
        checkNotNull(branch, "branch must not be null");

        // generate a UUID so the hook can locate its specific result file later.
        String requestId = UUID.randomUUID().toString();
        long timestamp = System.currentTimeMillis();

        Files.createDirectories(triggerFilePath.getParent());

        // write the four fields separated by the delimiter so the watcher can parse them.
        String content = String.format("%s%s%s%s%s%s%d", commitSha, DELIMITER, branch, DELIMITER, requestId, DELIMITER, timestamp);
        Files.writeString(triggerFilePath, content);

        LOGGER.debug("Created validation trigger file at: {} (commit={}, branch={}, requestId={})", triggerFilePath, commitSha.substring(0, Math.min(7, commitSha.length())), branch, requestId);

        return requestId;
    }

    /**
     * Checks whether the trigger file exists. If it does, reads the content, deletes the file, and returns the parsed trigger information.
     * Returns {@code null} if the file does not exist.
     * <p>If the file content is malformed (wrong number of fields or empty required fields), it is deleted and
     * {@code null} is returned so the watcher does not retry the same corrupted file.
     * <p>If the file cannot be deleted after reading, the trigger information is still returned and the failure is logged as a warning.
     * The watcher will attempt deletion again on the next poll cycle.
     * @return the parsed {@link TriggerInfo}, or {@code null} if no valid trigger was found.
     */
    public TriggerInfo checkAndClearTrigger() {
        if (!Files.exists(triggerFilePath)) {
            return null;
        }

        try {
            String content = Files.readString(triggerFilePath);
            String[] parts = SPLIT_PATTERN.split(content.trim());

            // accept either the current four-field format or the legacy two-field format.
            if (parts.length != 2 && parts.length != 4) {
                LOGGER.warn("Invalid validation trigger format: '{}'. " + "Expected commitSha|branch|requestId|timestamp or commitSha|branch", content);
                attemptFileDeletion();
                return null;
            }

            String commitSha = parts[0].trim();
            String branch    = parts[1].trim();
            String requestId;
            long   timestamp;

            if (parts.length == 4) {
                // current four-field format.
                requestId = parts[2].trim();
                try {
                    timestamp = Long.parseLong(parts[3].trim());
                } catch (NumberFormatException e) {
                    // the timestamp field is malformed
                    // fall back to the current time so the watcher can still associate a meaningful time with the request.
                    LOGGER.warn("Invalid timestamp in trigger file: '{}'", parts[3]);
                    timestamp = System.currentTimeMillis();
                }
            } else {
                // synthesize the missing fields so the rest of the watcher flow is unchanged.
                requestId = UUID.randomUUID().toString();
                timestamp = System.currentTimeMillis();
                LOGGER.warn("Legacy trigger format detected (no request ID), generated requestId='{}'", requestId);
            }

            // a trigger with any empty required field cannot be processed meaningfully.
            if (commitSha.isEmpty() || branch.isEmpty() || requestId.isEmpty()) {
                LOGGER.warn("Empty commit SHA, branch, or request ID in trigger file, discarding");
                attemptFileDeletion();
                return null;
            }

            TriggerInfo info = new TriggerInfo(commitSha, branch, requestId, timestamp);

            // attempt to delete the trigger now that it has been consumed.
            // if the delete fails, the info is still returned and deletion will be retried on the next poll.
            try {
                Files.delete(triggerFilePath);
                LOGGER.debug("Validation trigger detected and cleared: commit={}, branch={}, requestId={}", commitSha.substring(0, Math.min(7, commitSha.length())), branch, requestId);
            } catch (IOException deleteEx) {
                LOGGER.warn("Failed to delete validation trigger file, will retry on next poll: {}", triggerFilePath, deleteEx);
            }
            return info;

        } catch (IOException e) {
            LOGGER.warn("Failed to read validation trigger file: {}", triggerFilePath, e);
            return null;
        }
    }

    /**
     * Returns true if the trigger file currently exists on disk. Does not read or modify the file.
     * @return {@code true} if the trigger file exists, {@code false} otherwise.
     */
    public boolean exists() {
        return Files.exists(triggerFilePath);
    }

    /**
     * Returns the path where the trigger file is written. Useful for Git hooks that need to know the full path before writing the file.
     * @return the full path to the trigger file.
     */
    public Path getTriggerPath() {
        return triggerFilePath;
    }

    /**
     * Attempts to delete the trigger file, logging a warning if the delete fails.
     * Used to discard malformed or invalid trigger files without propagating an exception to the caller.
     */
    private void attemptFileDeletion() {
        try {
            Files.delete(triggerFilePath);
        } catch (IOException e) {
            LOGGER.warn("Could not delete malformed validation trigger file: {}", triggerFilePath, e);
        }
    }

    /**
     * Holds the information parsed from a validation trigger file.
     * <p>Each instance identifies a single validation request: the commit being validated, the branch it was made on,
     * a unique request identifier for matching the result file, and the timestamp for debugging and timeout detection.
     */
    @Getter
    public static class TriggerInfo {

        /** The full Git commit SHA for which validation was requested. */
        private final String commitSha;

        /** The branch on which the commit was made. */
        private final String branch;

        /**
         * Unique request identifier (UUID) that links this trigger to its result files.
         * The pre-commit hook uses this identifier to read the correct result file.
         */
        private final String requestId;

        /** Timestamp (milliseconds since epoch) when the trigger file was created. */
        private final long timestamp;

        /**
         * Creates a new {@link TriggerInfo} with the given fields.
         * @param commitSha the Git commit SHA. must not be null.
         * @param branch    the branch name. must not be null.
         * @param requestId the unique request identifier. must not be null.
         * @param timestamp the creation timestamp in milliseconds since epoch.
         */
        public TriggerInfo(String commitSha, String branch, String requestId, long timestamp) {
            this.commitSha = Objects.requireNonNull(commitSha, "commit SHA must not be null");
            this.branch    = Objects.requireNonNull(branch, "branch must not be null");
            this.requestId = Objects.requireNonNull(requestId, "request identifier must not be null");
            this.timestamp = timestamp;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TriggerInfo that = (TriggerInfo) o;
            return timestamp == that.timestamp
                    && Objects.equals(commitSha, that.commitSha)
                    && Objects.equals(branch, that.branch)
                    && Objects.equals(requestId, that.requestId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(commitSha, branch, requestId, timestamp);
        }

        @Override
        public String toString() {
            return "TriggerInfo{"
                    + "commitSha='" + commitSha.substring(0, Math.min(7, commitSha.length())) + "'"
                    + ", branch='" + branch + "'"
                    + ", requestId='" + requestId + "'"
                    + ", timestamp=" + timestamp
                    + "}";
        }
    }
}