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
 * {@code post-merge} hook and
 * {@link tools.vitruv.framework.vsum.branch.handler.VsumMergeWatcher}.
 *
 * <ul>
 *   <li>The Git post-merge hook creates this file after a merge completes.</li>
 *   <li>The file contains the merge commit SHA, source branch, target branch,
 *       request identifier, and timestamp.</li>
 *   <li>The watcher polls for the file, validates the merged VSUM state, writes the
 *       result and merge metadata, and deletes the trigger file.</li>
 *   <li>Unlike the pre-commit hook, post-merge validation is non-blocking — the merge
 *       has already completed. The result is written as a warning report if the merged
 *       state contains inconsistencies.</li>
 * </ul>
 *
 * <p>The trigger carries both branches involved in the merge because the merge metadata
 * written to {@code .vitruvius/merges/<sha>.metadata} must record the full merge context:
 * which branch was merged in (source) and which branch received the merge (target).
 *
 * <p>File format:
 * {@code mergeCommitSha|sourceBranch|targetBranch|requestId|timestamp}
 */
public class MergeTriggerFile extends AbstractTriggerFile<MergeTriggerFile.TriggerInfo> {

  private static final Logger LOGGER = LogManager.getLogger(MergeTriggerFile.class);

  private static final String TRIGGER_FILENAME = "merge-trigger";

  /**
   * Creates a new merge trigger file manager for the given repository root.
   * The trigger file will be located at
   * {@code <repositoryRoot>/.vitruvius/merge-trigger}.
   *
   * @param repositoryRoot the root directory of the Git repository, must not be null.
   */
  public MergeTriggerFile(Path repositoryRoot) {
    super(repositoryRoot, TRIGGER_FILENAME);
  }

  /**
   * Creates the trigger file to request post-merge validation of the virtual model.
   * A unique request identifier is generated and returned so that the hook can later
   * locate the correct result file.
   *
   * <p>File format:
   * {@code mergeCommitSha|sourceBranch|targetBranch|requestId|timestamp}
   *
   * @param mergeCommitSha the SHA of the merge commit created by Git, must not be null.
   * @param sourceBranch   the branch that was merged in (e.g. {@code feature-x}), must not
   *     be null.
   * @param targetBranch   the branch that received the merge (e.g. {@code main}), must not
   *     be null.
   * @return the generated request identifier (UUID string) for reading the result file.
   * @throws IOException if the file or its parent directory cannot be created.
   */
  public String createTrigger(
      String mergeCommitSha, String sourceBranch, String targetBranch) throws IOException {
    checkNotNull(mergeCommitSha, "merge commit SHA must not be null");
    checkNotNull(sourceBranch, "source branch must not be null");
    checkNotNull(targetBranch, "target branch must not be null");

    String requestId = UUID.randomUUID().toString();
    long timestamp = System.currentTimeMillis();

    // format the five fields and delegate the write to the base class.
    writeTrigger(String.format("%s%s%s%s%s%s%s%s%d",
        mergeCommitSha, DELIMITER, sourceBranch, DELIMITER,
        targetBranch, DELIMITER, requestId, DELIMITER, timestamp));

    LOGGER.debug("Created merge trigger: mergeCommit={}, source={}, target={}, requestId={}",
        mergeCommitSha.substring(0, Math.min(7, mergeCommitSha.length())),
        sourceBranch, targetBranch, requestId);

    return requestId;
  }

  /**
   * Parses the split fields into a {@link TriggerInfo}.
   *
   * <p>Returns {@code null} if the field count is not five or any required field is empty.
   * No legacy format is supported for merge triggers because the two-branch context is
   * essential for writing correct merge metadata.
   *
   * @param parts the fields split on {@link #DELIMITER}.
   * @return the parsed {@link TriggerInfo}, or {@code null} if the parts are invalid.
   */
  @Override
  protected TriggerInfo parseTriggerInfo(String[] parts) {
    if (parts.length != 5) {
      LOGGER.warn("Invalid merge trigger field count: expected 5, got {}", parts.length);
      return null;
    }

    String mergeCommitSha = parts[0].trim();
    String sourceBranch = parts[1].trim();
    String targetBranch = parts[2].trim();
    String requestId = parts[3].trim();
    long timestamp;

    try {
      timestamp = Long.parseLong(parts[4].trim());
    } catch (NumberFormatException e) {
      // malformed timestamp - fall back to now so the watcher can still
      // associate a meaningful time with the merge validation request.
      LOGGER.warn("Invalid timestamp in merge trigger: '{}'", parts[4]);
      timestamp = System.currentTimeMillis();
    }

    if (mergeCommitSha.isEmpty() || sourceBranch.isEmpty()
        || targetBranch.isEmpty() || requestId.isEmpty()) {
      LOGGER.warn("Empty required field in merge trigger, discarding");
      return null;
    }

    LOGGER.debug("Merge trigger parsed: mergeCommit={}, source={}, target={}, requestId={}",
        mergeCommitSha.substring(0, Math.min(7, mergeCommitSha.length())),
        sourceBranch, targetBranch, requestId);

    return new TriggerInfo(mergeCommitSha, sourceBranch, targetBranch, requestId, timestamp);
  }

  /**
   * Holds the information parsed from a merge trigger file.
   *
   * <p>Identifies a single post-merge validation request: the merge commit SHA, the source
   * branch that was merged in, the target branch that received the merge, a unique request
   * identifier for matching the result file, and a timestamp for debugging and timeout
   * detection.
   */
  @Getter
  public static class TriggerInfo extends AbstractTriggerFile.TriggerInfo {

    /**
     * The SHA of the merge commit created by Git.
     */
    private final String mergeCommitSha;

    /**
     * The branch that was merged in (e.g. {@code feature-x}).
     */
    private final String sourceBranch;

    /**
     * The branch that received the merge (e.g. {@code main}).
     */
    private final String targetBranch;

    /**
     * Creates a new {@link TriggerInfo}.
     *
     * @param mergeCommitSha the merge commit SHA, must not be null.
     * @param sourceBranch   the branch merged in, must not be null.
     * @param targetBranch   the branch that received the merge, must not be null.
     * @param requestId      the unique request identifier, must not be null.
     * @param timestamp      the creation timestamp in milliseconds since epoch.
     */
    public TriggerInfo(
        String mergeCommitSha, String sourceBranch, String targetBranch,
        String requestId, long timestamp) {
      super(requestId, timestamp);
      this.mergeCommitSha = Objects.requireNonNull(
          mergeCommitSha, "merge commit SHA must not be null");
      this.sourceBranch = Objects.requireNonNull(sourceBranch, "source branch must not be null");
      this.targetBranch = Objects.requireNonNull(targetBranch, "target branch must not be null");
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
          && Objects.equals(mergeCommitSha, that.mergeCommitSha)
          && Objects.equals(sourceBranch, that.sourceBranch)
          && Objects.equals(targetBranch, that.targetBranch)
          && Objects.equals(getRequestId(), that.getRequestId());
    }

    @Override
    public int hashCode() {
      return Objects.hash(
          mergeCommitSha, sourceBranch, targetBranch, getRequestId(), getTimestamp());
    }

    @Override
    public String toString() {
      return "TriggerInfo{"
          + "mergeCommitSha='"
          + mergeCommitSha.substring(0, Math.min(7, mergeCommitSha.length())) + "'"
          + ", sourceBranch='" + sourceBranch + "'"
          + ", targetBranch='" + targetBranch + "'"
          + ", requestId='" + getRequestId() + "'"
          + ", timestamp=" + getTimestamp()
          + "}";
    }
  }
}
