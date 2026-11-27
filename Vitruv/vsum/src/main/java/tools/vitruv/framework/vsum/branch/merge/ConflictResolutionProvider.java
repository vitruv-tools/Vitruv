package tools.vitruv.framework.vsum.branch.merge;

import java.util.List;

/**
 * Strategy for resolving merge conflicts.
 * Receives a list of conflicts and returns resolutions (ours or theirs per conflict).
 *
 * <p>For {@link MergeConflict.ConflictType#MODIFY_MODIFY} conflicts, OURS keeps the
 * target branch's value for the conflicting feature; THEIRS accepts the source branch's value.
 *
 * <p>For deletion conflicts ({@link MergeConflict.ConflictType#DELETE_MODIFY} and
 * {@link MergeConflict.ConflictType#MODIFY_DELETE}), OURS/THEIRS applies to the entire
 * element, not a single feature. Choosing OURS for a DELETE_MODIFY conflict keeps the
 * target branch's state (element deleted); choosing THEIRS keeps the source branch's
 * modification (element preserved). The {@code filterByResolutions()} method in
 * {@link SemanticMergeEngine} filters by UUID only (not UUID+feature) for delete
 * conflicts, dropping all DTOs for the resolved element.
 */
@FunctionalInterface
public interface ConflictResolutionProvider {

    /**
     * Resolves the given conflicts by choosing OURS or THEIRS for each.
     *
     * @param conflicts the detected merge conflicts
     * @return one resolution per conflict
     */
    List<ConflictResolution> resolve(List<MergeConflict> conflicts);

    /**
     * Returns a provider that always chooses OURS (keep target branch values).
     */
    static ConflictResolutionProvider chooseAllOurs() {
        return conflicts -> conflicts.stream()
                .map(c -> new ConflictResolution(c.getElementId(),
                        ConflictResolution.Choice.OURS))
                .toList();
    }

    /**
     * Returns a provider that always chooses THEIRS (accept source branch values).
     */
    static ConflictResolutionProvider chooseAllTheirs() {
        return conflicts -> conflicts.stream()
                .map(c -> new ConflictResolution(c.getElementId(),
                        ConflictResolution.Choice.THEIRS))
                .toList();
    }
}
