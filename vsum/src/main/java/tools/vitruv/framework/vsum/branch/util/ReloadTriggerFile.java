package tools.vitruv.framework.vsum.branch.util;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Manages the reload trigger file used for inter-process communication between the
 * Git {@code post-checkout} hook and
 * {@link tools.vitruv.framework.vsum.branch.handler.VsumReloadWatcher}.
 *
 * <ul>
 *   <li>The Git post-checkout hook creates this file after a branch switch.</li>
 *   <li>The watcher polls for the file at a fixed interval.</li>
 *   <li>When found, the watcher reads it, deletes it, and reloads the VirtualModel.</li>
 * </ul>
 *
 * <p>This file-based approach ensures that the VirtualModel is reloaded even when
 * developers perform branch switches using the command-line Git interface rather than
 * through the Vitruvius API.
 *
 * <p>Unlike validation triggers, reload triggers are fire-and-forget: the hook does
 * not wait for a result file. The request identifier is provided purely for
 * logging and traceability across hook and watcher logs.
 *
 * <p>File format: {@code branchName|requestId|timestamp}
 * <br>Legacy format (still accepted): {@code branchName}
 */
public class ReloadTriggerFile extends AbstractTriggerFile<ReloadTriggerFile.TriggerInfo> {

    private static final Logger LOGGER = LogManager.getLogger(ReloadTriggerFile.class);
    private static final String TRIGGER_FILENAME = "reload-trigger";

    /**
     * Creates a new reload trigger file manager for the given repository root.
     * The trigger file will be located at
     * {@code <repositoryRoot>/.vitruvius/reload-trigger}.
     *
     * @param repositoryRoot the root directory of the Git repository.
     */
    public ReloadTriggerFile(Path repositoryRoot) {
        super(repositoryRoot, TRIGGER_FILENAME);
    }

    /**
     * Creates the trigger file to signal that a VirtualModel reload is needed for
     * the given branch. A unique request identifier is generated for traceability.
     *
     * <p>File format: {@code branchName|requestId|timestamp}
     *
     * @param branchName the name of the branch that was just checked out.
     *                   Must not be null.
     * @return the generated request identifier (UUID string) for logging purposes.
     * @throws IOException if the trigger file or its parent directory cannot be created.
     */
    public String createTrigger(String branchName, String oldBranchName) throws IOException {
        checkNotNull(branchName, "branch name must not be null");
        checkNotNull(oldBranchName, "old branch name must not be null");
        String requestId = UUID.randomUUID().toString();
        long timestamp = java.lang.System.currentTimeMillis();
        writeTrigger(String.format("%s%s%s%s%d%s%s", branchName, DELIMITER, requestId, DELIMITER, timestamp, DELIMITER, oldBranchName));
        LOGGER.debug("Created reload trigger: branch='{}', oldBranch='{}', requestId='{}'", branchName, oldBranchName, requestId);
        return requestId;
    }

    /**
     * Parses the split fields into a {@link TriggerInfo}.
     *
     * <p>Accepts the current three-field format and the legacy single-field format
     * written by older hook versions. Returns {@code null} if the field count is
     * unexpected or any required field is empty.
     *
     * @param parts the fields split on {@link #DELIMITER}.
     * @return the parsed {@link TriggerInfo}, or {@code null} if the parts are invalid.
     */
    @Override
    protected TriggerInfo parseTriggerInfo(String[] parts) {
        if (parts.length != 4 && parts.length != 3 && parts.length != 1) {
            LOGGER.warn("Invalid reload trigger field count: expected 4, 3, or 1, got {}", parts.length);
            return null;
        }

        String branchName;
        String requestId;
        long timestamp;
        String oldBranchName;

        if (parts.length == 4) {
            branchName = parts[0].trim();
            requestId = parts[1].trim();
            try {
                timestamp = Long.parseLong(parts[2].trim());
            } catch (NumberFormatException e) {
                LOGGER.warn("Invalid timestamp in reload trigger: '{}'", parts[2]);
                timestamp = java.lang.System.currentTimeMillis();
            }
            oldBranchName = parts[3].trim();

        } else if (parts.length == 3) {
            // old format without oldBranch — fall back to "unknown"
            branchName = parts[0].trim();
            requestId = parts[1].trim();
            try {
                timestamp = Long.parseLong(parts[2].trim());
            } catch (NumberFormatException e) {
                LOGGER.warn("Invalid timestamp in reload trigger: '{}'", parts[2]);
                timestamp = java.lang.System.currentTimeMillis();
            }
            oldBranchName = "unknown";
            LOGGER.warn("Reload trigger missing oldBranch field — inheritance may not work correctly");

        } else {
            // legacy single-field format
            branchName = parts[0].trim();
            requestId = UUID.randomUUID().toString();
            timestamp = java.lang.System.currentTimeMillis();
            oldBranchName = "unknown";
            LOGGER.warn("Legacy reload trigger format — oldBranch unknown, requestId='{}'",
                    requestId);
        }

        if (branchName.isEmpty() || requestId.isEmpty()) {
            LOGGER.warn("Empty required field in reload trigger, discarding");
            return null;
        }

        LOGGER.debug("Reload trigger parsed: branch='{}', oldBranch='{}', requestId='{}'",
                branchName, oldBranchName, requestId);

        return new TriggerInfo(branchName, oldBranchName, requestId, timestamp);
    }


    /**
     * Holds the information parsed from a reload trigger file.
     *
     * <p>Unlike validation trigger information, reload trigger information has no
     * associated result file - the hook is fire-and-forget. The request identifier
     * is provided purely for traceability across hook and watcher logs.
     */
    @Getter
    public static class TriggerInfo extends AbstractTriggerFile.TriggerInfo {

        private final String branchName;
        private final String oldBranchName;   // ← add this

        public TriggerInfo(String branchName, String oldBranchName,
                           String requestId, long timestamp) {
            super(requestId, timestamp);
            this.branchName = Objects.requireNonNull(branchName);
            this.oldBranchName = Objects.requireNonNull(oldBranchName);
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TriggerInfo that = (TriggerInfo) o;
            return getTimestamp() == that.getTimestamp()
                    && Objects.equals(branchName, that.branchName)
                    && Objects.equals(getRequestId(), that.getRequestId());
        }

        @Override
        public int hashCode() {
            return Objects.hash(branchName, getRequestId(), getTimestamp());
        }

        @Override
        public String toString() {
            return "TriggerInfo{"
                    + "branchName='" + branchName + "'"
                    + ", requestId='" + getRequestId() + "'"
                    + ", timestamp=" + getTimestamp()
                    + "}";
        }
    }
}