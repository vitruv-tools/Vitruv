package tools.vitruv.framework.vsum.branch.data;

import lombok.Getter;
import tools.vitruv.framework.vsum.branch.storage.SemanticChangeEntry;

import java.util.List;
import java.util.Objects;

/**
 * The result of a semantic replay performed by
 * {@link tools.vitruv.framework.vsum.branch.ChangeReplayer}.
 *
 * <p>Contains the full semantic picture of what two branches changed since their common
 * ancestor:
 * <ul>
 *   <li>The SHA of the common ancestor commit.</li>
 *   <li>Short SHAs of all commits on each branch since the ancestor.</li>
 *   <li>All {@link SemanticChangeEntry} records collected from those commits' JSON changelogs.</li>
 *   <li>The list of {@link SemanticConflict}s where both branches changed the same model element
 *       in an incompatible way.</li>
 * </ul>
 *
 * <p>Consumed by the conflict resolution UI (MG-8) and by {@code MergeManager} when choosing
 * between auto-merge and manual resolution strategies.
 */
@Getter
public class ReplayResult {

    /**
     * Full SHA of the common ancestor commit from which both branches diverged.
     * {@code null} if no common ancestor could be found (unrelated histories).
     */
    private final String ancestorSha;

    /**
     * Short SHAs (7-char) of commits on branch A since the ancestor.
     */
    private final List<String> commitShasOnA;

    /**
     * Short SHAs (7-char) of commits on branch B since the ancestor.
     */
    private final List<String> commitShasOnB;

    /**
     * All semantic change entries collected from branch A's changelogs.
     */
    private final List<SemanticChangeEntry> changesOnA;

    /**
     * All semantic change entries collected from branch B's changelogs.
     */
    private final List<SemanticChangeEntry> changesOnB;

    /**
     * Conflicts where both branches changed the same {@code (elementUuid, feature)} pair
     * in an incompatible way. Empty when both branches made non-overlapping changes.
     */
    private final List<SemanticConflict> conflicts;

    public ReplayResult(String ancestorSha,
                        List<String> commitShasOnA,
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

    /**
     * Returns {@code true} if at least one semantic conflict was detected.
     */
    public boolean hasConflicts() {
        return !conflicts.isEmpty();
    }

    /**
     * Returns the number of HIGH-severity conflicts.
     */
    public long highSeverityCount() {
        return conflicts.stream().filter(c -> c.getSeverity() == ConflictSeverity.HIGH).count();
    }

    /**
     * Returns the number of MEDIUM-severity conflicts.
     */
    public long mediumSeverityCount() {
        return conflicts.stream().filter(c -> c.getSeverity() == ConflictSeverity.MEDIUM).count();
    }

    @Override
    public String toString() {
        return "ReplayResult{"
                + "ancestor=" + (ancestorSha != null ? ancestorSha.substring(0, Math.min(7, ancestorSha.length())) : "none")
                + ", commitsOnA=" + commitShasOnA.size()
                + ", commitsOnB=" + commitShasOnB.size()
                + ", changesOnA=" + changesOnA.size()
                + ", changesOnB=" + changesOnB.size()
                + ", conflicts=" + conflicts.size()
                + " (HIGH=" + highSeverityCount() + ", MEDIUM=" + mediumSeverityCount() + ")"
                + '}';
    }
}
