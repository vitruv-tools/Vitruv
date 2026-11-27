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
 * {@code post-commit} hook and
 * {@link tools.vitruv.framework.vsum.branch.handler.VsumPostCommitWatcher}.
 *
 * <ul>
 *   <li>The Git post-commit hook creates this file after every successful commit.</li>
 *   <li>The file contains the real commit SHA assigned by Git, the branch name,
 *       a request identifier, and a timestamp.</li>
 *   <li>The watcher polls for the file, generates the semantic changelog using the
 *       real commit SHA, auto-stages it, and deletes the trigger file.</li>
 * </ul>
 *
 * <p>Unlike the pre-commit trigger, the post-commit trigger is fire-and-forget:
 * the hook does not wait for a result file.
 * The changelog is generated asynchronously in the background while the developer
 * continues working.
 *
 * <p>The commit SHA in this trigger is the real SHA assigned by Git, not the parent
 * SHA read before the commit was created.
 * This is what makes the changelog traceable via {@code git log}: the changelog file
 * name and content both match the actual commit.
 *
 * <p>File format: {@code commitSha|branch|requestId|timestamp}
 */
public class PostCommitTriggerFile extends AbstractTriggerFile<PostCommitTriggerFile.TriggerInfo> {

  private static final Logger LOGGER = LogManager.getLogger(PostCommitTriggerFile.class);

  private static final String TRIGGER_FILENAME = "post-commit-trigger";

  /**
   * Creates a new post-commit trigger file manager for the given repository root.
   * The trigger file will be located at
   * {@code <repositoryRoot>/.vitruvius/post-commit-trigger}.
   *
   * @param repositoryRoot the root directory of the Git repository. Must not be null.
   */
  public PostCommitTriggerFile(Path repositoryRoot) {
    super(repositoryRoot, TRIGGER_FILENAME);
  }

  /**
   * Creates the trigger file to signal that a semantic changelog should be generated
   * for the given commit. A unique request identifier is generated for traceability.
   *
   * <p>File format: {@code commitSha|branch|requestId|timestamp}
   *
   * <p>The {@code commitSha} must be the real SHA of the commit just created, not the
   * parent SHA. On the command line, the post-commit hook reads this via
   * {@code git rev-parse HEAD}, which at post-commit time points to the new commit.
   *
   * @param commitSha the real Git commit SHA assigned to the new commit. Must not be null.
   * @param branch    the branch on which the commit was made. Must not be null.
   * @return the generated request identifier (UUID string) for logging purposes.
   * @throws IOException if the trigger file or its parent directory cannot be created.
   */
  public String createTrigger(String commitSha, String branch) throws IOException {
    checkNotNull(commitSha, "commit SHA must not be null");
    checkNotNull(branch, "branch must not be null");

    String requestId = UUID.randomUUID().toString();
    long timestamp = System.currentTimeMillis();

    writeTrigger(String.format("%s%s%s%s%s%s%d",
        commitSha, DELIMITER, branch, DELIMITER, requestId, DELIMITER, timestamp));

    LOGGER.debug("Created post-commit trigger: commit={}, branch={}, requestId={}",
        commitSha.substring(0, Math.min(7, commitSha.length())), branch, requestId);

    return requestId;
  }

  /**
   * Parses the split fields into a {@link TriggerInfo}.
   *
   * <p>Returns {@code null} if the field count is not four or any required field is empty.
   *
   * @param parts the fields split on {@link #DELIMITER}.
   * @return the parsed {@link TriggerInfo}, or {@code null} if the parts are invalid.
   */
  @Override
  protected TriggerInfo parseTriggerInfo(String[] parts) {
    if (parts.length != 4) {
      LOGGER.warn("Invalid post-commit trigger field count: expected 4, got {}", parts.length);
      return null;
    }
    String commitSha = parts[0].trim();
    String branch = parts[1].trim();
    String requestId = parts[2].trim();
    long timestamp;

    try {
      timestamp = Long.parseLong(parts[3].trim());
    } catch (NumberFormatException e) {
      LOGGER.warn("Invalid timestamp in post-commit trigger: '{}'", parts[3]);
      timestamp = System.currentTimeMillis();
    }

    if (commitSha.isEmpty() || branch.isEmpty() || requestId.isEmpty()) {
      LOGGER.warn("Empty required field in post-commit trigger, discarding");
      return null;
    }

    LOGGER.debug("Post-commit trigger parsed: commit={}, branch={}, requestId={}",
        commitSha.substring(0, Math.min(7, commitSha.length())), branch, requestId);

    return new TriggerInfo(commitSha, branch, requestId, timestamp);
  }

  /**
   * Holds the information parsed from a post-commit trigger file.
   *
   * <p>The {@code commitSha} is the real SHA assigned by Git to the new commit, not the
   * parent SHA. This ensures the generated changelog file name and content match the
   * actual commit in {@code git log}.
   */
  @Getter
  public static class TriggerInfo extends AbstractTriggerFile.TriggerInfo {

    /**
     * The real Git commit SHA assigned to the commit that just completed.
     */
    private final String commitSha;

    /**
     * The branch on which the commit was made.
     */
    private final String branch;

    /**
     * Creates a new {@link TriggerInfo}.
     *
     * @param commitSha the real commit SHA. Must not be null.
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
