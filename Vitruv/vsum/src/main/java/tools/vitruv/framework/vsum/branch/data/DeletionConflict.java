package tools.vitruv.framework.vsum.branch.data;

import tools.vitruv.framework.vsum.branch.storage.ChangeOrigin;
import tools.vitruv.framework.vsum.branch.storage.SemanticChangeEntry;

import java.util.List;
import java.util.Objects;

/**
 * Describes a delete-vs-update conflict detected during a branch merge.
 *
 * <p>A deletion conflict occurs when one branch deletes a model element while
 * the other branch modifies that same element (or its children).  The
 * {@link #affectedUpdates} list captures every update on the opposing branch
 * that would be destroyed if the deletion is accepted.
 *
 * <p>The {@link #ancestorAvailable} flag indicates whether a shared Git
 * ancestor exists from which the deleted element can be recovered.  When
 * recovery is possible, the interactive conflict resolver can offer the user
 * a "Recover from ancestor" option.
 */
public class DeletionConflict {

    private final String deletedElementUuid;
    private final String deletedElementEClass;
    private final String deletingBranch;
    private final String updatingBranch;
    private final List<SemanticChangeEntry> affectedUpdates;
    private final boolean ancestorAvailable;
    private final ChangeOrigin deletionOrigin;

    /**
     * Creates a new deletion conflict descriptor.
     *
     * @param deletedElementUuid  UUID of the element that was deleted.
     * @param deletedElementEClass  EClass name of the deleted element (for display).
     * @param deletingBranch      name of the branch that deleted the element.
     * @param updatingBranch      name of the branch that updated the element.
     * @param affectedUpdates     updates on the opposite branch that touch the deleted
     *                            element or its children (will be lost on acceptance).
     * @param ancestorAvailable   whether a shared ancestor exists for recovery.
     * @param deletionOrigin      whether the deletion was human-made or engine-generated.
     */
    public DeletionConflict(String deletedElementUuid, String deletedElementEClass,
                            String deletingBranch, String updatingBranch,
                            List<SemanticChangeEntry> affectedUpdates,
                            boolean ancestorAvailable, ChangeOrigin deletionOrigin) {
        this.deletedElementUuid = Objects.requireNonNull(deletedElementUuid);
        this.deletedElementEClass = deletedElementEClass;
        this.deletingBranch = Objects.requireNonNull(deletingBranch);
        this.updatingBranch = Objects.requireNonNull(updatingBranch);
        this.affectedUpdates = Objects.requireNonNull(affectedUpdates);
        this.ancestorAvailable = ancestorAvailable;
        this.deletionOrigin = deletionOrigin != null ? deletionOrigin : ChangeOrigin.UNKNOWN;
    }

    public String getDeletedElementUuid() { return deletedElementUuid; }
    public String getDeletedElementEClass() { return deletedElementEClass; }
    public String getDeletingBranch() { return deletingBranch; }
    public String getUpdatingBranch() { return updatingBranch; }
    public List<SemanticChangeEntry> getAffectedUpdates() { return affectedUpdates; }
    public boolean isAncestorAvailable() { return ancestorAvailable; }
    public ChangeOrigin getDeletionOrigin() { return deletionOrigin; }

    /**
     * Returns the number of updates that would be destroyed if this deletion
     * is accepted.
     */
    public int getLostUpdateCount() {
        return affectedUpdates.size();
    }

    /**
     * Returns {@code true} if the deletion was consequential (engine-generated)
     * but at least one of the conflicting updates was original (human-made).
     * In this case, the Vitruvius rule "original &gt; consequential" strongly
     * recommends recovery.
     */
    public boolean isConsequentialDeletionVsOriginalUpdates() {
        if (deletionOrigin != ChangeOrigin.CONSEQUENTIAL) {
            return false;
        }
        return affectedUpdates.stream()
                .anyMatch(u -> u.getOrigin() == ChangeOrigin.ORIGINAL);
    }

    /**
     * Returns {@code true} if this conflict is high-impact, defined as having
     * more lost updates than the given threshold.
     */
    public boolean isHighImpact(int threshold) {
        return getLostUpdateCount() >= threshold;
    }

    /**
     * Computes the {@link ConflictSeverity} of this conflict based on the
     * number of updates that would be lost if the deletion is accepted.
     *
     * <ul>
     *   <li>0 lost updates → {@link ConflictSeverity#LOW}</li>
     *   <li>1–2 lost updates → {@link ConflictSeverity#MEDIUM}</li>
     *   <li>3–9 lost updates → {@link ConflictSeverity#HIGH}</li>
     *   <li>10+ lost updates → {@link ConflictSeverity#CRITICAL}</li>
     * </ul>
     */
    public ConflictSeverity getSeverity() {
        return ConflictSeverity.fromLostUpdateCount(getLostUpdateCount());
    }

    @Override
    public String toString() {
        return "DeletionConflict{" +
                "element=" + deletedElementEClass + " (uuid=" + deletedElementUuid + ")" +
                ", deletingBranch='" + deletingBranch + '\'' +
                ", updatingBranch='" + updatingBranch + '\'' +
                ", lostUpdates=" + affectedUpdates.size() +
                ", severity=" + getSeverity() +
                ", ancestorAvailable=" + ancestorAvailable +
                ", deletionOrigin=" + deletionOrigin +
                '}';
    }
}
