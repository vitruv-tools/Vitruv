package tools.vitruv.framework.vsum.branch.data;

import tools.vitruv.framework.vsum.branch.storage.ChangeOrigin;
import tools.vitruv.framework.vsum.branch.storage.SemanticChangeEntry;

import java.util.Objects;

/**
 * Describes an update-vs-update conflict detected during a branch merge.
 *
 * <p>An update conflict occurs when both branches modify the <em>same</em>
 * structural feature on the <em>same</em> element (identified by UUID).
 * If the merge is accepted naively, one branch's value silently overwrites
 * the other's -- a "lost update" scenario.
 *
 * <p>Unlike a {@link DeletionConflict}, neither branch deletes the element;
 * the model stays intact, but the final attribute/reference value is ambiguous.
 *
 * <p>Severity is always {@link ConflictSeverity#MEDIUM} for a single feature
 * collision, but escalates to {@link ConflictSeverity#HIGH} when multiple
 * features on the same element conflict simultaneously.
 */
public class UpdateConflict {

    private final String elementUuid;
    private final String eClass;
    private final String featureName;
    private final String sourceBranch;
    private final String targetBranch;
    private final SemanticChangeEntry sourceEntry;
    private final SemanticChangeEntry targetEntry;

    /**
     * Creates an update conflict.
     *
     * @param elementUuid  UUID of the element both branches modified.
     * @param eClass       EClass name of the element (for display).
     * @param featureName  name of the conflicting structural feature.
     * @param sourceBranch name of the source (incoming) branch.
     * @param targetBranch name of the target (current) branch.
     * @param sourceEntry  the change entry from the source branch.
     * @param targetEntry  the change entry from the target branch.
     */
    public UpdateConflict(String elementUuid, String eClass, String featureName,
                          String sourceBranch, String targetBranch,
                          SemanticChangeEntry sourceEntry, SemanticChangeEntry targetEntry) {
        this.elementUuid = Objects.requireNonNull(elementUuid);
        this.eClass = eClass;
        this.featureName = Objects.requireNonNull(featureName);
        this.sourceBranch = Objects.requireNonNull(sourceBranch);
        this.targetBranch = Objects.requireNonNull(targetBranch);
        this.sourceEntry = Objects.requireNonNull(sourceEntry);
        this.targetEntry = Objects.requireNonNull(targetEntry);
    }

    public String getElementUuid() { return elementUuid; }
    public String getEClass() { return eClass; }
    public String getFeatureName() { return featureName; }
    public String getSourceBranch() { return sourceBranch; }
    public String getTargetBranch() { return targetBranch; }
    public SemanticChangeEntry getSourceEntry() { return sourceEntry; }
    public SemanticChangeEntry getTargetEntry() { return targetEntry; }

    /**
     * Returns the severity. Update conflicts are MEDIUM severity because the data is
     * not lost, but the final value is ambiguous.
     */
    public ConflictSeverity getSeverity() {
        return ConflictSeverity.MEDIUM;
    }

    /**
     * Returns {@code true} if one side is ORIGINAL and the other is CONSEQUENTIAL.
     * In this case, the ORIGINAL side should be preferred per the Vitruvius rule.
     */
    public boolean isOriginalVsConsequential() {
        ChangeOrigin srcOrigin = sourceEntry.getOrigin();
        ChangeOrigin tgtOrigin = targetEntry.getOrigin();
        return (srcOrigin == ChangeOrigin.ORIGINAL && tgtOrigin == ChangeOrigin.CONSEQUENTIAL)
                || (srcOrigin == ChangeOrigin.CONSEQUENTIAL && tgtOrigin == ChangeOrigin.ORIGINAL);
    }

    /**
     * Returns the entry that should be preferred when one side is ORIGINAL and
     * the other is CONSEQUENTIAL. Returns {@code null} if both have the same
     * origin (manual resolution required).
     */
    public SemanticChangeEntry getPreferredEntry() {
        if (sourceEntry.getOrigin() == ChangeOrigin.ORIGINAL
                && targetEntry.getOrigin() == ChangeOrigin.CONSEQUENTIAL) {
            return sourceEntry;
        }
        if (targetEntry.getOrigin() == ChangeOrigin.ORIGINAL
                && sourceEntry.getOrigin() == ChangeOrigin.CONSEQUENTIAL) {
            return targetEntry;
        }
        return null; // same origin, no auto-preference
    }

    /**
     * Returns the branch name of the preferred entry, or {@code null} if
     * no auto-preference can be determined.
     */
    public String getPreferredBranch() {
        SemanticChangeEntry preferred = getPreferredEntry();
        if (preferred == null) return null;
        return preferred == sourceEntry ? sourceBranch : targetBranch;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateConflict that = (UpdateConflict) o;
        return Objects.equals(elementUuid, that.elementUuid)
                && Objects.equals(featureName, that.featureName)
                && Objects.equals(sourceBranch, that.sourceBranch)
                && Objects.equals(targetBranch, that.targetBranch);
    }

    @Override
    public int hashCode() {
        return Objects.hash(elementUuid, featureName, sourceBranch, targetBranch);
    }

    @Override
    public String toString() {
        return "UpdateConflict{" +
                "element=" + eClass + " (uuid=" + elementUuid + ")" +
                ", feature='" + featureName + '\'' +
                ", source='" + sourceBranch + "' [" + sourceEntry.getOrigin() + "]" +
                ", target='" + targetBranch + "' [" + targetEntry.getOrigin() + "]" +
                ", severity=" + getSeverity() +
                '}';
    }
}
