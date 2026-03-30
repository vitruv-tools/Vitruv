package tools.vitruv.framework.vsum.branch.util;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Manages the trigger file used for inter-process communication between the Git
 * {@code pre-commit} hook and
 * {@link tools.vitruv.framework.vsum.branch.handler.VsumValidationWatcher}.
 *
 * <ul>
 *   <li>The Git pre-commit hook creates this file before allowing the commit.</li>
 *   <li>The file contains the commit SHA, branch name, request identifier, and
 *       timestamp.</li>
 *   <li>The watcher polls every 500ms, validates the virtual model, writes the
 *       result, and deletes the trigger file.</li>
 *   <li>The hook reads the validation result and allows or blocks the commit.</li>
 * </ul>
 *
 * <p>Concurrent commits are supported through unique request identifiers. Each
 * request produces its own result files identified by the request identifier, so
 * two simultaneous hook invocations cannot read each other's results.
 *
 * <p>File format: {@code commitSha|branch|requestId|timestamp}
 * <br>Legacy format (still accepted): {@code commitSha|branch}
 */
public class ValidationTriggerFile
    extends AbstractTriggerFile<ValidationTriggerFile.TriggerInfo> {

  private static final Logger LOGGER = LogManager.getLogger(ValidationTriggerFile.class);
  private static final String TRIGGER_FILENAME = "validate-trigger";

  /**
   * Creates a new validation trigger file manager for the given repository root.
   * The trigger file will be located at
   * {@code <repositoryRoot>/.vitruvius/validate-trigger}.
   *
   * @param repositoryRoot the root directory of the Git repository.
   */
  public ValidationTriggerFile(Path repositoryRoot) {
    super(repositoryRoot, TRIGGER_FILENAME);
  }

  /**
   * Creates the trigger file to request validation of the virtual model for the
   * given commit. A unique request identifier is generated and returned so that the
   * hook can later locate the correct result file.
   *
   * <p>File format: {@code commitSha|branch|requestId|timestamp}
   *
   * @param commitSha the Git commit SHA. Must not be null.
   * @param branch    the branch name. Must not be null.
   * @return the generated request identifier (UUID string) for reading the result file.
   *
   * @throws IOException if the file or its parent directory cannot be created.
   */
  public String createTrigger(String commitSha, String branch) throws IOException {
    checkNotNull(commitSha, "commit SHA must not be null");
    checkNotNull(branch, "branch must not be null");

    String requestId = UUID.randomUUID().toString();
    long timestamp = System.currentTimeMillis();

    // format the four fields and delegate the write to the base class.
    writeTrigger(String.format("%s%s%s%s%s%s%d",
        commitSha, DELIMITER, branch, DELIMITER, requestId, DELIMITER, timestamp));

    LOGGER.debug("Created validation trigger: commit={}, branch={}, requestId={}",
        commitSha.substring(0, Math.min(7, commitSha.length())), branch, requestId);

    return requestId;
  }

  /**
   * Parses the split fields into a {@link TriggerInfo}.
   *
   * <p>Accepts both the current four-field format and the legacy two-field format
   * written by older hook versions. Returns {@code null} if the field count is
   * unexpected or any required field is empty.
   *
   * @param parts the fields split on {@link #DELIMITER}.
   * @return the parsed {@link TriggerInfo}, or {@code null} if the parts are invalid.
   */
  @Override
  protected TriggerInfo parseTriggerInfo(String[] parts) {
    if (parts.length != 4 && parts.length != 2) {
      LOGGER.warn("Invalid validation trigger field count: expected 4 or 2, got {}",
          parts.length);
      return null;
    }

    String commitSha = parts[0].trim();
    String branch = parts[1].trim();
    String requestId;
    long timestamp;

    if (parts.length == 4) {
      requestId = parts[2].trim();
      try {
        timestamp = Long.parseLong(parts[3].trim());
      } catch (NumberFormatException e) {
        // malformed timestamp - fall back to now so the watcher can still
        // associate a meaningful time with the request.
        LOGGER.warn("Invalid timestamp in validation trigger: '{}'", parts[3]);
        timestamp = System.currentTimeMillis();
      }
    } else {
      // legacy two-field format: synthesize the missing fields so the rest of
      // the watcher flow is unchanged.
      requestId = UUID.randomUUID().toString();
      timestamp = System.currentTimeMillis();
      LOGGER.warn("Legacy validation trigger format detected, generated requestId='{}'",
          requestId);
    }

    if (commitSha.isEmpty() || branch.isEmpty() || requestId.isEmpty()) {
      LOGGER.warn("Empty required field in validation trigger, discarding");
      return null;
    }

    LOGGER.debug("Validation trigger parsed: commit={}, branch={}, requestId={}",
        commitSha.substring(0, Math.min(7, commitSha.length())), branch, requestId);

    return new TriggerInfo(commitSha, branch, requestId, timestamp);
  }

  /**
   * Holds the information parsed from a validation trigger file.
   *
   * <p>Identifies a single validation request: the commit being validated, the
   * branch it was made on, a unique request identifier for matching the result
   * file, and the timestamp for debugging and timeout detection.
   */
  @Getter
  public static class TriggerInfo extends AbstractTriggerFile.TriggerInfo {

    /**
     * The full Git commit SHA for which validation was requested.
     */
    private final String commitSha;

    /**
     * The branch on which the commit was made.
     */
    private final String branch;

    /**
     * Creates a new {@link TriggerInfo}.
     *
     * @param commitSha the Git commit SHA. Must not be null.
     * @param branch    the branch name. Must not be null.
     * @param requestId the unique request identifier. Must not be null.
     * @param timestamp the creation timestamp in milliseconds since epoch.
     */
    public TriggerInfo(String commitSha, String branch, String requestId, long timestamp) {
      super(requestId, timestamp);
      this.commitSha = Objects.requireNonNull(commitSha, "commit SHA must not be null");
      this.branch = Objects.requireNonNull(branch, "branch must not be null");
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      TriggerInfo that = (TriggerInfo) o;
      return getTimestamp() == that.getTimestamp()
          && Objects.equals(commitSha, that.commitSha)
          && Objects.equals(branch, that.branch)
          && Objects.equals(getRequestId(), that.getRequestId());
    }

    @Override
    public int hashCode() {
      return Objects.hash(commitSha, branch, getRequestId(), getTimestamp());
    }

    @Override
    public String toString() {
      return "TriggerInfo{"
          + "commitSha='" + commitSha.substring(0, Math.min(7, commitSha.length())) + "'"
          + ", branch='" + branch + "'"
          + ", requestId='" + getRequestId() + "'"
          + ", timestamp=" + getTimestamp()
          + "}";
    }
  }
}
