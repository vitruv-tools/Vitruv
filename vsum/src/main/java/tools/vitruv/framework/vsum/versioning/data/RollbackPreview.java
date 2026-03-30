package tools.vitruv.framework.vsum.versioning.data;

import java.util.List;
import java.util.Objects;
import lombok.Getter;

/**
 * Describes what will happen if a rollback to a specific version is confirmed.
 *
 * <p>Shown to the caller before
 * {@link tools.vitruv.framework.vsum.versioning.VersioningService#confirmRollback}
 * is invoked, so the developer can make an informed decision before
 * irreversibly discarding uncommitted changes and commits.
 *
 * <p>Covers DE-8: rollback preview step before execution.
 */
@Getter
public class RollbackPreview {

  /** The version that will be restored. */
  private final VersionMetadata targetVersion;

  /** The commit SHA currently at HEAD - will be abandoned after rollback. */
  private final String currentHeadSha;

  /** The branch on which the rollback will be performed. */
  private final String branch;

  /**
   * Commits that exist between the target version and HEAD that will be lost.
   * Listed from newest to oldest.
   */
  private final List<String> commitsToAbandon;

  /**
   * Relative paths of files that will change in the working directory after rollback.
   * Derived from git diff between HEAD and the target commit.
   */
  private final List<String> filesToChange;

  /**
   * Whether the working directory currently has uncommitted changes.
   * If true, those changes will be permanently lost on rollback.
   */
  private final boolean hasUncommittedChanges;

  public RollbackPreview(VersionMetadata targetVersion, String currentHeadSha,
                         String branch, List<String> commitsToAbandon,
                         List<String> filesToChange, boolean hasUncommittedChanges) {
    this.targetVersion = Objects.requireNonNull(targetVersion);
    this.currentHeadSha = Objects.requireNonNull(currentHeadSha);
    this.branch = Objects.requireNonNull(branch);
    this.commitsToAbandon = List.copyOf(commitsToAbandon);
    this.filesToChange = List.copyOf(filesToChange);
    this.hasUncommittedChanges = hasUncommittedChanges;
  }

  /**
   * Returns a human-readable summary of what the rollback will do.
   * Intended for display in the version browser UI (UI-2) or CLI output.
   */
  public String summary() {
    var sb = new StringBuilder();
    sb.append("Rollback to version '").append(targetVersion.getVersionId()).append("'\n");
    sb.append("  Target commit : ").append(
            targetVersion.getCommitSha().substring(0, 7)).append("\n");
    sb.append("  Current HEAD  : ").append(
            currentHeadSha.substring(0, Math.min(7, currentHeadSha.length()))).append("\n");
    sb.append("  Branch        : ").append(branch).append("\n");

    if (!commitsToAbandon.isEmpty()) {
      sb.append("  Commits lost  : ").append(commitsToAbandon.size()).append("\n");
      commitsToAbandon.forEach(c -> sb.append("    - ").append(c).append("\n"));
    }
    if (!filesToChange.isEmpty()) {
      sb.append("  Files changed : ").append(filesToChange.size()).append("\n");
      filesToChange.forEach(f -> sb.append("    - ").append(f).append("\n"));
    }
    if (hasUncommittedChanges) {
      sb.append("  ⚠ WARNING: Uncommitted changes will be permanently lost!\n");
    }
    return sb.toString();
  }
}