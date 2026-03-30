package tools.vitruv.framework.vsum.branch.data;

import java.util.List;
import java.util.Objects;
import lombok.Getter;
import tools.vitruv.framework.vsum.branch.storage.SemanticChangeEntry;

/**
 * Result of a semantic replay performed by
 * {@link tools.vitruv.framework.vsum.branch.ChangeReplayer}.
 *
 * <p>Contains the full semantic picture of what two branches changed since their common
 * ancestor: per-branch commit SHAs, all {@link SemanticChangeEntry} records from those
 * commits' JSON changelogs, and any detected {@link SemanticConflict}s.
 */
@Getter
public class ReplayResult {

  /**
   * Full SHA of the common ancestor commit. {@code null} if histories are unrelated.
   */
  private final String ancestorSha;

  /** 7-char short SHAs of commits on branch A since the ancestor. */
  private final List<String> commitShasOnA;

  /** 7-char short SHAs of commits on branch B since the ancestor. */
  private final List<String> commitShasOnB;

  /** Semantic change entries collected from branch A's changelogs. */
  private final List<SemanticChangeEntry> changesOnA;

  /** Semantic change entries collected from branch B's changelogs. */
  private final List<SemanticChangeEntry> changesOnB;

  /**
   * Conflicts where both branches changed the same {@code (elementUuid, feature)} pair
   * in an incompatible way. Empty when branches made non-overlapping changes.
   */
  private final List<SemanticConflict> conflicts;

  /**
   * Creates a new {@link ReplayResult}.
   *
   * @param ancestorSha full SHA of the common ancestor; may be null.
   * @param commitShasOnA short SHAs of commits on branch A.
   * @param commitShasOnB short SHAs of commits on branch B.
   * @param changesOnA semantic changes from branch A.
   * @param changesOnB semantic changes from branch B.
   * @param conflicts detected semantic conflicts.
   */
  public ReplayResult(String ancestorSha, List<String> commitShasOnA,
      List<String> commitShasOnB,
      List<SemanticChangeEntry> changesOnA,
      List<SemanticChangeEntry> changesOnB,
      List<SemanticConflict> conflicts) {
    this.ancestorSha = ancestorSha;
    this.commitShasOnA = List.copyOf(Objects.requireNonNull(commitShasOnA));
    this.commitShasOnB = List.copyOf(Objects.requireNonNull(commitShasOnB));
    this.changesOnA = List.copyOf(Objects.requireNonNull(changesOnA));
    this.changesOnB = List.copyOf(Objects.requireNonNull(changesOnB));
    this.conflicts = List.copyOf(Objects.requireNonNull(conflicts));
  }

  /** Returns {@code true} if at least one semantic conflict was detected. */
  public boolean hasConflicts() {
    return !conflicts.isEmpty();
  }

  /** Returns the number of HIGH-severity conflicts. */
  public long highSeverityCount() {
    return conflicts.stream().filter(c -> c.getSeverity() == ConflictSeverity.HIGH).count();
  }

  /** Returns the number of MEDIUM-severity conflicts. */
  public long mediumSeverityCount() {
    return conflicts.stream().filter(c -> c.getSeverity() == ConflictSeverity.MEDIUM).count();
  }

  @Override
  public String toString() {
    return "ReplayResult{"
        + "ancestor=" + (ancestorSha != null
            ? ancestorSha.substring(0, Math.min(7, ancestorSha.length()))
            : "none")
        + ", commitsOnA=" + commitShasOnA.size()
        + ", commitsOnB=" + commitShasOnB.size()
        + ", changesOnA=" + changesOnA.size()
        + ", changesOnB=" + changesOnB.size()
        + ", conflicts=" + conflicts.size()
        + " (HIGH=" + highSeverityCount() + ", MEDIUM=" + mediumSeverityCount() + ")"
        + '}';
  }
}
