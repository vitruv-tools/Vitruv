package tools.vitruv.framework.vsum.branch.data;

import java.util.List;
import java.util.Objects;
import lombok.Getter;

/**
 * Result of a merge operation performed by
 * {@link tools.vitruv.framework.vsum.branch.MergeManager}.
 *
 * <p>The {@link MergeStatus} indicates whether the merge succeeded cleanly, was
 * fast-forwarded, produced conflicts requiring manual resolution, or failed entirely.
 *
 * <p>When status is {@link MergeStatus#CONFLICTING}, the merge has been applied to the
 * working directory but no merge commit exists yet. The developer must resolve the conflict
 * markers manually and commit the resolution.
 */
@Getter
public class ModelMergeResult {

  /**
   * Outcome of a merge operation.
   */
  public enum MergeStatus {
    /** Clean merge: a merge commit was created successfully. */
    SUCCESS,
    /** Fast-forward: HEAD was moved forward, no merge commit created. */
    FAST_FORWARD,
    /** Conflicts detected: working directory contains conflict markers. */
    CONFLICTING,
    /** Unexpected error: the merge could not be attempted or completed. */
    FAILED
  }

  private final MergeStatus status;
  private final String sourceBranch;
  private final String targetBranch;

  /**
   * SHA of the merge commit. {@code null} for FAST_FORWARD (no merge commit)
   * and CONFLICTING (no commit created yet).
   */
  private final String mergeCommitSha;

  private final boolean fastForward;

  /** Relative paths of files with conflict markers. Empty for SUCCESS and FAST_FORWARD. */
  private final List<String> conflictingFiles;

  /** Human-readable summary of the merge outcome. */
  private final String message;

  private ModelMergeResult(MergeStatus status, String sourceBranch, String targetBranch,
      String mergeCommitSha, boolean fastForward, List<String> conflictingFiles, String message) {
    this.status = Objects.requireNonNull(status);
    this.sourceBranch = Objects.requireNonNull(sourceBranch);
    this.targetBranch = Objects.requireNonNull(targetBranch);
    this.mergeCommitSha = mergeCommitSha;
    this.fastForward = fastForward;
    this.conflictingFiles = List.copyOf(conflictingFiles);
    this.message = Objects.requireNonNull(message);
  }

  /**
   * Creates a result for a clean merge that produced a merge commit.
   *
   * @param sourceBranch   the branch merged in.
   * @param targetBranch   the branch that received the merge.
   * @param mergeCommitSha the SHA of the merge commit.
   * @return a {@link MergeStatus#SUCCESS} result.
   */
  public static ModelMergeResult success(String sourceBranch, String targetBranch,
      String mergeCommitSha) {
    return new ModelMergeResult(MergeStatus.SUCCESS, sourceBranch, targetBranch,
        mergeCommitSha, false, List.of(),
        "Merged '" + sourceBranch + "' into '" + targetBranch + "' successfully");
  }

  /**
   * Creates a result for a fast-forward merge (no merge commit created).
   *
   * @param sourceBranch the branch merged in.
   * @param targetBranch the branch that received the merge.
   * @param newHeadSha   the SHA of the new HEAD after the fast-forward.
   * @return a {@link MergeStatus#FAST_FORWARD} result.
   */
  public static ModelMergeResult fastForward(String sourceBranch, String targetBranch,
      String newHeadSha) {
    return new ModelMergeResult(MergeStatus.FAST_FORWARD, sourceBranch, targetBranch,
        newHeadSha, true, List.of(),
        "Fast-forward merge of '" + sourceBranch + "' into '" + targetBranch + "'");
  }

  /**
   * Creates a result for a merge that detected conflicts.
   * No merge commit is created; the developer must resolve and commit manually.
   *
   * @param sourceBranch     the branch merged in.
   * @param targetBranch     the branch that received the merge.
   * @param conflictingFiles relative paths of files with conflict markers.
   * @return a {@link MergeStatus#CONFLICTING} result.
   */
  public static ModelMergeResult conflicting(String sourceBranch, String targetBranch,
      List<String> conflictingFiles) {
    return new ModelMergeResult(MergeStatus.CONFLICTING, sourceBranch, targetBranch,
        null, false, conflictingFiles,
        "Merge of '" + sourceBranch + "' into '" + targetBranch + "' resulted in "
            + conflictingFiles.size() + " conflict(s). "
            + "Resolve conflicts manually and commit the resolution.");
  }

  /**
   * Creates a result for a merge that could not be completed.
   *
   * @param sourceBranch the branch merged in.
   * @param targetBranch the branch that received the merge.
   * @param reason       human-readable description of the failure cause.
   * @return a {@link MergeStatus#FAILED} result.
   */
  public static ModelMergeResult failed(String sourceBranch, String targetBranch, String reason) {
    return new ModelMergeResult(MergeStatus.FAILED, sourceBranch, targetBranch,
        null, false, List.of(),
        "Merge of '" + sourceBranch + "' into '" + targetBranch + "' failed: " + reason);
  }

  /**
   * Returns {@code true} if the merge completed without conflicts
   * ({@link MergeStatus#SUCCESS} or {@link MergeStatus#FAST_FORWARD}).
   */
  public boolean isSuccessful() {
    return status == MergeStatus.SUCCESS || status == MergeStatus.FAST_FORWARD;
  }

  @Override
  public String toString() {
    return "ModelMergeResult{status=" + status
        + ", source='" + sourceBranch + "'"
        + ", target='" + targetBranch + "'"
        + (mergeCommitSha != null
            ? ", sha='" + mergeCommitSha.substring(0, Math.min(7, mergeCommitSha.length())) + "'"
            : "")
        + ", conflicts=" + conflictingFiles.size()
        + ", message='" + message + "'"
        + "}";
  }
}
