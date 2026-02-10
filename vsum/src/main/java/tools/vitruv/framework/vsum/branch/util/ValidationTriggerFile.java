package tools.vitruv.framework.vsum.branch.util;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.UUID;

/**
 * Manages a trigger file that signals validation requests from Git pre-commit hook.
 *
 * <ul>
 *   <li>Git pre-commit hook creates this file before allowing commit</li>
 *   <li>File contains commit SHA, branch name, request ID, and timestamp</li>
 *   <li>VsumValidationWatcher checks for the file periodically (every 500ms)</li>
 *   <li>If exists, watcher validates V-SUM, writes result, and deletes trigger</li>
 *   <li>Hook reads validation result and allows/blocks commit</li>
 * </ul>
 *
 * <p><b>Concurrency:</b> Multiple concurrent commits are supported via unique request IDs.
 * Each request gets a separate result file identified by its request ID.
 */
public class ValidationTriggerFile {
    private static final Logger LOGGER = LogManager.getLogger(ValidationTriggerFile.class);
    private static final String TRIGGER_FILENAME = "validate-trigger";
    private static final String DELIMITER = "|";

    private final Path triggerFilePath;

    /**
     * Creates a new validation trigger file manager for the given repository.
     *
     * @param repositoryRoot The root directory of the Git repository
     */
    public ValidationTriggerFile(Path repositoryRoot) {
        this.triggerFilePath = repositoryRoot.resolve(".vitruvius").resolve(TRIGGER_FILENAME);
    }

    /**
     * Creates the trigger file with commit information to request validation.
     *
     * <p>File format: {@code commitSha|branch|requestId|timestamp}
     *
     * <p> Generates a unique request ID (UUID) to identify this validation request.
     * This enables concurrent commits by allowing each request to have its own result file.
     *
     * @param commitSha the Git commit SHA (40 characters)
     * @param branch the branch name
     * @return the generated request ID (UUID string) - use this to read the result
     * @throws IOException if the file cannot be created
     */
    public String createTrigger(String commitSha, String branch) throws IOException {
        Objects.requireNonNull(commitSha, "commitSha must not be null");
        Objects.requireNonNull(branch, "branch must not be null");

        // Generate unique request ID for this validation request
        String requestId = UUID.randomUUID().toString();
        long timestamp = System.currentTimeMillis();

        // Create parent directory if needed
        Files.createDirectories(triggerFilePath.getParent());

        // Write commitSha|branch|requestId|timestamp
        String content = String.format("%s%s%s%s%s%s%d", commitSha, DELIMITER, branch, DELIMITER, requestId, DELIMITER, timestamp);

        Files.writeString(triggerFilePath, content);

        LOGGER.debug("Created validation trigger file at: {} (commit={}, branch={}, requestId={})", triggerFilePath, commitSha.substring(0, Math.min(7, commitSha.length())), branch, requestId);

        //Return request ID so hook can wait for the correct result file
        return requestId;
    }

    /**
     * Checks if the trigger file exists. If it does, reads the information,
     * deletes it, and returns the trigger info.
     *
     * <p>If the file cannot be deleted, a warning is logged but the trigger
     * information is still returned. The file will be cleaned up on the next poll.
     *
     * <p>Now parses request ID and timestamp for concurrent request tracking.
     *
     * @return trigger information if file existed, null otherwise
     */
    public TriggerInfo checkAndClearTrigger() {
        if (!Files.exists(triggerFilePath)) {
            return null;  // No trigger
        }

        try {
            // Read content
            String content = Files.readString(triggerFilePath);

            // Parse commitSha|branch|requestId|timestamp
            String[] parts = content.trim().split("\\" + DELIMITER);

            // Support both old format (2 parts) and new format (4 parts) for backward compatibility
            if (parts.length != 2 && parts.length != 4) {
                LOGGER.warn("Invalid validation trigger format: '{}'. Expected: commitSha|branch|requestId|timestamp or commitSha|branch", content);
                // Try to delete malformed trigger
                try {
                    Files.delete(triggerFilePath);
                } catch (IOException deleteEx) {
                    LOGGER.warn("Could not delete malformed trigger file", deleteEx);
                }
                return null;
            }

            String commitSha = parts[0].trim();
            String branch = parts[1].trim();

            // Parse request ID and timestamp if present
            String requestId;
            long timestamp;

            if (parts.length == 4) {
                requestId = parts[2].trim();
                try {
                    timestamp = Long.parseLong(parts[3].trim());
                } catch (NumberFormatException e) {
                    LOGGER.warn("Invalid timestamp in trigger file: {}", parts[3]);
                    timestamp = System.currentTimeMillis(); // Fallback to current time
                }
            } else {
                // Old format without request ID - generate one for compatibility
                requestId = UUID.randomUUID().toString();
                timestamp = System.currentTimeMillis();
                LOGGER.warn("Legacy trigger format detected (no request ID). Generated requestId={}", requestId);
            }

            // Validate parsed data
            if (commitSha.isEmpty() || branch.isEmpty() || requestId.isEmpty()) {
                LOGGER.warn("Empty commitSha, branch, or requestId in trigger file");
                try {
                    Files.delete(triggerFilePath);
                } catch (IOException deleteEx) {
                    LOGGER.warn("Could not delete invalid trigger file", deleteEx);
                }
                return null;
            }

            // Create TriggerInfo with request ID and timestamp
            TriggerInfo info = new TriggerInfo(commitSha, branch, requestId, timestamp);

            // Try to delete trigger file
            try {
                Files.delete(triggerFilePath);
                LOGGER.debug("Validation trigger detected and cleared: commit={}, branch={}, requestId={}", commitSha.substring(0, Math.min(7, commitSha.length())), branch, requestId);
            } catch (IOException deleteEx) {
                LOGGER.warn("Failed to delete validation trigger file: {}. Will retry on next poll.", triggerFilePath, deleteEx);
            }
            return info;
        } catch (IOException e) {
            LOGGER.warn("Failed to read validation trigger file: {}", triggerFilePath, e);
            return null;
        }
    }

    /**
     * Checks if the trigger file currently exists, without reading or deleting it.
     *
     * @return true if the trigger file exists, false otherwise
     */
    public boolean exists() {
        return Files.exists(triggerFilePath);
    }

    /**
     * Returns the path where the trigger file is located.
     * Useful for Git hooks that need to create the file.
     *
     * @return The full path to the trigger file
     */
    public Path getTriggerPath() {
        return triggerFilePath;
    }

    /**
     * Information read from a validation trigger file.
     * Contains the commit SHA, branch name, request ID, and timestamp for which validation is requested.
     *
     * <p>Now includes request ID (UUID) and timestamp for concurrent request tracking.
     */
    @Getter
    public static class TriggerInfo {
        /**
         * The Git commit SHA (40 characters).
         */
        private final String commitSha;

        /**
         * The branch name.
         */
        private final String branch;

        /**
         * Unique request ID (UUID) identifying this validation request.
         * Used to match validation results with the correct request.
         */
        private final String requestId;

        /**
         * Timestamp (milliseconds since epoch) when the trigger was created.
         * Used for debugging and timeout detection.
         */
        private final long timestamp;

        /**
         * Creates trigger information with request ID and timestamp.
         *
         * <p>includes requestId and timestamp for concurrency support.
         *
         * @param commitSha the Git commit SHA
         * @param branch the branch name
         * @param requestId the unique request identifier (UUID)
         * @param timestamp the creation timestamp (milliseconds since epoch)
         * @throws NullPointerException if commitSha, branch, or requestId is null
         */
        public TriggerInfo(String commitSha, String branch, String requestId, long timestamp) {
            this.commitSha = Objects.requireNonNull(commitSha, "commitSha must not be null");
            this.branch = Objects.requireNonNull(branch, "branch must not be null");
            this.requestId = Objects.requireNonNull(requestId, "requestId must not be null");
            this.timestamp = timestamp;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TriggerInfo that = (TriggerInfo) o;
            return timestamp == that.timestamp && Objects.equals(commitSha, that.commitSha) && Objects.equals(branch, that.branch) && Objects.equals(requestId, that.requestId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(commitSha, branch, requestId, timestamp);
        }

        @Override
        public String toString() {
            return "TriggerInfo{" + "commitSha=" + commitSha.substring(0, Math.min(7, commitSha.length())) + ", branch=" + branch + ", requestId=" + requestId + ", timestamp=" + timestamp + "}";
        }
    }
}