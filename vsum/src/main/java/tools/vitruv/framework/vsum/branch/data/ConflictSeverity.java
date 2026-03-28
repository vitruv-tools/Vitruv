package tools.vitruv.framework.vsum.branch.data;

/**
 * Classifies how severe a conflict is during a branch merge
 *
 * <p>Severity is determined by the types of changes involved:
 * <ul>
 *   <li>{@link #LOW} – both branches added or inserted different values; the divergence
 *       is unlikely to break model consistency and may be auto-resolvable.</li>
 *   <li>{@link #MEDIUM} – both branches modified the same attribute or reference on the
 *       same element to different values; a developer must pick the winner.</li>
 *   <li>{@link #HIGH} – one branch deleted an element that the other branch still modifies,
 *       or both branches changed a reference in an incompatible way. The developer must
 *       decide whether the element should survive.</li>
 * </ul>
 *
 * <p>The severity levels are used by the conflict resolution UI to prioritise which
 * conflicts require manual attention and which can be auto-resolved.
 */
public enum ConflictSeverity {

    /**
     * Low-risk conflict: both branches made independent additions or list insertions.
     * Can often be automatically merged or requires only minor developer confirmation.
     */
    LOW,

    /**
     * Medium-risk conflict: both branches modified the same scalar attribute or
     * single-valued reference to different values. Requires developer to pick one.
     */
    MEDIUM,

    /**
     * High-risk conflict: one branch deleted an element that the other modifies,
     * or both branches changed a reference in incompatible directions.
     * Requires careful developer decision to maintain model consistency.
     */
    HIGH
}
