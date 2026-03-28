package tools.vitruv.framework.vsum.branch.data;

import lombok.Getter;
import tools.vitruv.framework.vsum.branch.storage.SemanticChangeEntry;
import tools.vitruv.framework.vsum.branch.storage.SemanticChangeType;

import java.util.Objects;

/**
 * Represents a semantic conflict between two branches: the same model element was changed
 * on both branches in an incompatible way since their common ancestor (MG-2).
 *
 * <p>A conflict is identified by the {@code (elementUuid, feature)} tuple:
 * <ul>
 *   <li>{@code elementUuid} – the stable Vitruvius UUID of the affected model element.</li>
 *   <li>{@code feature} – the structural feature (attribute or reference) that was changed.
 *       {@code null} for lifecycle conflicts ({@link SemanticChangeType#ELEMENT_DELETED}
 *       vs. any modification).</li>
 * </ul>
 *
 * <p>The {@link #severity} is assigned by the {@code ChangeReplayer} according to the rules
 * documented in {@link ConflictSeverity} and {@link SemanticChangeType}.
 *
 * <p>The two {@link SemanticChangeEntry} fields ({@link #changeOnBranchA},
 * {@link #changeOnBranchB}) contain the full change detail from each branch's JSON changelog,
 * so the developer or resolver can see exactly what each branch did.
 */
@Getter
public class SemanticConflict {

    /**
     * Stable Vitruvius UUID of the model element at the centre of this conflict.
     * Matches {@link SemanticChangeEntry#getElementUuid()} on both sides.
     */
    private final String elementUuid;

    /**
     * Name of the structural feature (attribute or reference) that was changed.
     * {@code null} when the conflict is an element-lifecycle conflict
     * (e.g. element deleted on one branch, modified on the other).
     */
    private final String feature;

    /**
     * The semantic change recorded on branch A for this element/feature.
     */
    private final SemanticChangeEntry changeOnBranchA;

    /**
     * The semantic change recorded on branch B for this element/feature.
     */
    private final SemanticChangeEntry changeOnBranchB;

    /**
     * How severe this conflict is, used to prioritise resolution effort.
     */
    private final ConflictSeverity severity;

    public SemanticConflict(String elementUuid, String feature,
                            SemanticChangeEntry changeOnBranchA,
                            SemanticChangeEntry changeOnBranchB,
                            ConflictSeverity severity) {
        this.elementUuid     = Objects.requireNonNull(elementUuid, "elementUuid must not be null");
        this.feature         = feature; // nullable for lifecycle conflicts
        this.changeOnBranchA = Objects.requireNonNull(changeOnBranchA, "changeOnBranchA must not be null");
        this.changeOnBranchB = Objects.requireNonNull(changeOnBranchB, "changeOnBranchB must not be null");
        this.severity        = Objects.requireNonNull(severity, "severity must not be null");
    }

    @Override
    public String toString() {
        return "SemanticConflict{"
                + "elementUuid='" + elementUuid + '\''
                + ", feature='" + feature + '\''
                + ", severity=" + severity
                + ", typeA=" + changeOnBranchA.getChangeType()
                + ", typeB=" + changeOnBranchB.getChangeType()
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SemanticConflict that = (SemanticConflict) o;
        return Objects.equals(elementUuid, that.elementUuid)
                && Objects.equals(feature, that.feature)
                && severity == that.severity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(elementUuid, feature, severity);
    }
}
