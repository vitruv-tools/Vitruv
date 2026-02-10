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
 * Manages a small trigger file that tells the VSUM to reload models.
 *
 * <p>How it works:
 * <ul>
 *   <li>Git hook creates this file after a branch switch</li>
 *   <li>VSUM background watcher periodically checks for the file</li>
 *   <li>If it exists, VSUM reloads models and deletes the file</li>
 * </ul>
 *
 * <p>This ensures VSUM is updated even when developers use command-line Git.
 *
 * <p>Each reload request has a unique request ID for debugging and logging.
 * Unlike validation triggers, reload triggers do NOT wait for results - they are fire-and-forget.
 */
public class ReloadTriggerFile {
    private static final Logger LOGGER = LogManager.getLogger(ReloadTriggerFile.class);
    private static final String TRIGGER_FILENAME = "reload-trigger";
    private static final String DELIMITER = "|";

    private final Path triggerFilePath;

    /**
     * Creates a new trigger file manager for the given repository.
     *
     * @param repositoryRoot The root directory of the Git repository
     */
    public ReloadTriggerFile(Path repositoryRoot) {
        // store reload-trigger file in .vitruvius directory
        this.triggerFilePath = repositoryRoot.resolve(".vitruvius").resolve(TRIGGER_FILENAME);
    }

    /**
     * Creates the trigger file to signal that a reload is needed.
     *
     * <p><b>File format: {@code branchName|requestId|timestamp}
     *
     * <p>The request ID is generated automatically and returned for logging purposes.
     * Unlike validation requests, reload requests do NOT use the request ID
     * to match result files - reloads are fire-and-forget operations.
     *
     * @param branchName the name of the branch that was checked out
     * @return the generated request ID (UUID string) for logging/debugging
     * @throws IOException if the file cannot be created
     */
    public String createTrigger(String branchName) throws IOException {
        Objects.requireNonNull(branchName, "branchName must not be null");

        // Generate unique request ID for this reload request
        String requestId = UUID.randomUUID().toString();
        long timestamp = System.currentTimeMillis();

        // create parent directory if needed
        Files.createDirectories(triggerFilePath.getParent());

        // Write branchName|requestId|timestamp
        String content = String.format("%s%s%s%s%d", branchName, DELIMITER, requestId, DELIMITER, timestamp);
        Files.writeString(triggerFilePath, content);
        LOGGER.debug("Created reload trigger file at: {} (branch={}, requestId={})", triggerFilePath, branchName, requestId);
        //Return request ID for logging in hook
        return requestId;
    }

    /**
     * Checks if the trigger file exists. If it does, reads the information,
     * deletes it, and returns the trigger info.
     *
     * <p>Now parses branch name, request ID, and timestamp for logging.
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
            // Parse branchName|requestId|timestamp
            String[] parts = content.trim().split("\\" + DELIMITER);
            // Support both old format (just text) and new format
            String branchName;
            String requestId;
            long timestamp;

            if (parts.length >= 3) {
                // New format with request ID
                branchName = parts[0].trim();
                requestId = parts[1].trim();
                try {
                    timestamp = Long.parseLong(parts[2].trim());
                } catch (NumberFormatException e) {
                    timestamp = System.currentTimeMillis();
                }
            } else if (parts.length == 1) {
                // Legacy format: just branch name or old text message
                String rawContent = parts[0].trim();

                // Check if it's the old "Reload requested at..." format
                if (rawContent.startsWith("Reload requested at")) {
                    branchName = "unknown";  // Can't extract branch from old format
                } else {
                    branchName = rawContent;  // Assume it's a branch name
                }
                requestId = UUID.randomUUID().toString();  // Generate for compatibility
                timestamp = System.currentTimeMillis();

                LOGGER.debug("Legacy reload trigger format detected, generated requestId={}", requestId);
            } else {
                LOGGER.warn("Invalid reload trigger format: '{}'", content);
                try {
                    Files.delete(triggerFilePath);
                } catch (IOException deleteEx) {
                    LOGGER.warn("Could not delete malformed trigger file", deleteEx);
                }
                return null;
            }

            // Validate parsed data
            if (branchName.isEmpty() || requestId.isEmpty()) {
                LOGGER.warn("Empty branchName or requestId in reload trigger file");
                try {
                    Files.delete(triggerFilePath);
                } catch (IOException deleteEx) {
                    LOGGER.warn("Could not delete invalid trigger file", deleteEx);
                }
                return null;
            }

            TriggerInfo info = new TriggerInfo(branchName, requestId, timestamp);

            // Try to delete trigger file
            try {
                Files.delete(triggerFilePath);
                LOGGER.debug("Reload trigger detected and cleared: branch={}, requestId={}", branchName, requestId);
            } catch (IOException deleteEx) {
                LOGGER.warn("Failed to delete reload trigger file: {}. Will retry on next poll.", triggerFilePath, deleteEx);
            }
            return info;
        } catch (IOException e) {
            LOGGER.warn("Failed to read reload trigger file: {}", triggerFilePath, e);
            return null;
        }
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
     * Checks if the trigger file currently exists, without reading or deleting it.
     *
     * @return true if the trigger file exists, false otherwise
     */
    public boolean exists() {
        return Files.exists(triggerFilePath);
    }

    /**
     * Information read from a reload trigger file.
     * Contains the branch name, request ID, and timestamp for logging/debugging.
     *
     * <p>Unlike validation triggers, reload triggers do not have associated
     * result files. The request ID is purely for logging and debugging purposes.
     */
    @Getter
    public static class TriggerInfo {
        /**
         * The branch name that was checked out.
         */
        private final String branchName;

        /**
         * Unique request ID (UUID) identifying this reload request.
         * Used for logging and debugging only (no result file matching).
         */
        private final String requestId;

        /**
         * Timestamp (milliseconds since epoch) when the trigger was created.
         */
        private final long timestamp;

        /**
         * Creates trigger information.
         *
         * @param branchName the branch name
         * @param requestId the unique request identifier (UUID)
         * @param timestamp the creation timestamp (milliseconds since epoch)
         * @throws NullPointerException if branchName or requestId is null
         */
        public TriggerInfo(String branchName, String requestId, long timestamp) {
            this.branchName = Objects.requireNonNull(branchName, "branchName must not be null");
            this.requestId = Objects.requireNonNull(requestId, "requestId must not be null");
            this.timestamp = timestamp;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TriggerInfo that = (TriggerInfo) o;
            return timestamp == that.timestamp && Objects.equals(branchName, that.branchName) && Objects.equals(requestId, that.requestId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(branchName, requestId, timestamp);
        }

        @Override
        public String toString() {
            return "TriggerInfo{" + "branchName=" + branchName + ", requestId=" + requestId + ", timestamp=" + timestamp + "}";
        }
    }
}