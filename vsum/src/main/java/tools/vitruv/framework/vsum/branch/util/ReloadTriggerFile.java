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
 * Manages the reload trigger file used for inter-process communication between Git hooks and the Vitruvius background watcher thread.
 *
 * <p>Logic:
 * <ul>
 *   <li>The {@code post-checkout} Git hook creates this file after a branch switch.</li>
 *   <li>The Vitruvius background watcher polls for the file at a fixed interval.</li>
 *   <li>When the file is found, the watcher reads it, deletes it, and reloads the VirtualModel.</li>
 * </ul>
 *
 * <p>This file-based approach ensures that the VirtualModel is reloaded even when developers
 * perform branch switches using the command-line Git interface rather than through the Vitruvius API.
 *
 * <p>Each reload request carries a unique request identifier for logging and debugging.
 * Unlike validation triggers, reload triggers are fire-and-forget: the hook does not wait for a result file, so no result is written back.
 *
 * <p>File format: {@code branchName|requestId|timestamp}
 * <br>Example: {@code feature-vcs|550e8400-e29b-41d4-a716-446655440000|1707584123000}
 */
public class ReloadTriggerFile {
    private static final Logger LOGGER = LogManager.getLogger(ReloadTriggerFile.class);
    private static final String TRIGGER_FILENAME = "reload-trigger";
    private static final String DELIMITER = "|";

    /**
     * Pre-compiled pattern for splitting the trigger file content on the delimiter.
     * Using {@link Pattern#quote} avoids treating the pipe character as a regex metacharacter, which would otherwise cause the split to behave incorrectly.
     */
    private static final Pattern SPLIT_PATTERN = Pattern.compile(Pattern.quote(DELIMITER));

    private final Path triggerFilePath;

    /**
     * Creates a new {@link ReloadTriggerFile} manager for the given repository root.
     * The trigger file will be located at {@code <repositoryRoot>/.vitruvius/reload-trigger}.
     *
     * @param repositoryRoot the root directory of the Git repository.
     */
    public ReloadTriggerFile(Path repositoryRoot) {
        // the trigger file lives in the .vitruvius directory alongside other IPC files.
        this.triggerFilePath = repositoryRoot.resolve(".vitruvius").resolve(TRIGGER_FILENAME);
    }

    /**
     * Creates the trigger file to signal that a VirtualModel reload is needed. The file content encodes the branch name,
     * a freshly generated request identifier, and the current timestamp separated by {@code |} characters.
     *
     * <p>The parent directory is created automatically if it does not exist yet, so callers do not need to set up the {@code .vitruvius} directory in advance.
     *
     * <p>The request identifier is returned so that the caller (typically the Git hook) can
     * include it in log output for traceability.
     *
     * @param branchName the name of the branch that was just checked out.
     * @return the generated request identifier (UUID string) for logging purposes.
     * @throws IOException if the trigger file or its parent directory cannot be created.
     */
    public String createTrigger(String branchName) throws IOException {
        checkNotNull(branchName, "branch name must not be null");

        String requestId = UUID.randomUUID().toString();
        long timestamp = System.currentTimeMillis();

        Files.createDirectories(triggerFilePath.getParent());

        // write the three fields separated by the delimiter so the watcher can parse them.
        String content = String.format("%s%s%s%s%d", branchName, DELIMITER, requestId, DELIMITER, timestamp);
        Files.writeString(triggerFilePath, content);
        LOGGER.debug("Created reload trigger: branch='{}', requestId='{}'", branchName, requestId);
        return requestId;
    }

    /**
     * Checks whether the trigger file exists. If it does, reads the content, deletes the file, and returns the parsed trigger information.
     * Returns {@code null} if the file does not exist.
     *
     * <p>If the file exists but its content is malformed (wrong number of fields or empty required fields),
     * it is deleted and {@code null} is returned so the watcher does not retry the same corrupted file indefinitely.
     *
     * <p>If the file cannot be deleted after reading, the trigger information is still returned and the delete failure is logged as a warning.
     * The watcher will attempt to delete the file again on the next poll cycle.
     * @return the parsed {@link TriggerInfo}, or {@code null} if no valid trigger was found.
     */
    public TriggerInfo checkAndClearTrigger() {
        if (!Files.exists(triggerFilePath)) {
            return null;
        }

        try {
            String content = Files.readString(triggerFilePath);
            String[] parts = SPLIT_PATTERN.split(content.trim());

            String branchName;
            String requestId;
            long timestamp;

            if (parts.length >= 3) {
                // current format: branchName|requestId|timestamp
                branchName = parts[0].trim();
                requestId = parts[1].trim();
                try {
                    timestamp = Long.parseLong(parts[2].trim());
                } catch (NumberFormatException e) {
                    // the timestamp field is malformed, fall back to the current time so the
                    // watcher can still process the reload request.
                    timestamp = System.currentTimeMillis();
                }
            } else if (parts.length == 1) {
                // legacy format: either a plain branch name or the human-readable text.
                String rawContent = parts[0].trim();
                if (rawContent.startsWith("Reload requested at")) {
                    branchName = "unknown";
                } else {
                    branchName = rawContent;
                }
                // synthesize a request identifier so the rest of the watcher flow is unchanged.
                requestId = UUID.randomUUID().toString();
                timestamp = System.currentTimeMillis();
                LOGGER.debug("Legacy reload trigger format detected, generated requestId='{}'", requestId);
            } else {
                // the file has an unexpected number of fields and cannot be safely parsed.
                LOGGER.warn("Invalid reload trigger format, discarding file: '{}'", content);
                attemptFileDeletion();
                return null;
            }

            // a trigger with an empty branch name or request identifier cannot be processed meaningfully, so it is discarded rather than passed to the watcher.
            if (branchName.isEmpty() || requestId.isEmpty()) {
                LOGGER.warn("Empty branch name or request identifier in reload trigger, discarding");
                attemptFileDeletion();
                return null;
            }

            TriggerInfo info = new TriggerInfo(branchName, requestId, timestamp);

            // attempt to delete the trigger file now that it has been consumed.
            // if the delete fails (for example due to a concurrent write), the info is still returned and the watcher will reattempt deletion on the next poll cycle.
            try {
                Files.delete(triggerFilePath);
                LOGGER.debug("Reload trigger consumed and cleared: branch='{}', requestId='{}'", branchName, requestId);
            } catch (IOException deleteEx) {
                LOGGER.warn("Failed to delete reload trigger file, will retry on next poll: {}", triggerFilePath, deleteEx);
            }
            return info;

        } catch (IOException e) {
            LOGGER.warn("Failed to read reload trigger file: {}", triggerFilePath, e);
            return null;
        }
    }

    /**
     * Returns the path where the trigger file is written.
     *
     * @return the full path to the trigger file.
     */
    public Path getTriggerPath() {
        return triggerFilePath;
    }

    /**
     * Returns true if the trigger file currently exists on disk. Does not read or modify the file.
     *
     * @return {@code true} if the trigger file exists, {@code false} otherwise.
     */
    public boolean exists() {
        return Files.exists(triggerFilePath);
    }

    /**
     * Attempts to delete the trigger file, logging a warning if the delete fails.
     * Used to discard malformed or invalid trigger files so they are not re-processed.
     */
    private void attemptFileDeletion() {
        try {
            Files.delete(triggerFilePath);
        } catch (IOException e) {
            LOGGER.warn("Could not delete malformed reload trigger file: {}", triggerFilePath, e);
        }
    }

    /**
     * Holds the information read from a reload trigger file.
     * <p>Unlike validation trigger information, reload trigger information does not have an associated result file.
     * The request identifier is provided purely for logging and debugging purposes so that a specific reload request can be traced across the hook log and the watcher log.
     */
    @Getter
    public static class TriggerInfo {

        /** The name of the branch that was checked out when the trigger was created. */
        private final String branchName;

        /**
         * Unique request identifier for this reload request.
         */
        private final String requestId;

        /** Timestamp (milliseconds since epoch) when the trigger file was created. */
        private final long timestamp;

        /**
         * Creates a new {@link TriggerInfo} with the given fields.
         *
         * @param branchName the branch name, must not be null.
         * @param requestId  the unique request identifier. must not be null.
         * @param timestamp  the creation timestamp in milliseconds since epoch.
         */
        public TriggerInfo(String branchName, String requestId, long timestamp) {
            this.branchName = Objects.requireNonNull(branchName, "branch name must not be null");
            this.requestId = Objects.requireNonNull(requestId, "request identifier must not be null");
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
            return "TriggerInfo{branchName='" + branchName + "', requestId='" + requestId + "', timestamp=" + timestamp + "}";
        }
    }
}