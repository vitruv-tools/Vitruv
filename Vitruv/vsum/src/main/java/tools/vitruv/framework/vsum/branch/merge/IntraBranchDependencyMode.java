package tools.vitruv.framework.vsum.branch.merge;

/**
 * Controls how intra-branch commit ordering constraints are computed
 * in the dependency graph for interleaving merge.
 */
public enum IntraBranchDependencyMode {
    /** Preserve commit order within each branch (a_0 → a_1 → ... → a_{m-1}). */
    SEQUENTIAL,
    /**
     * Compute dependencies from footprint overlaps; independent commits
     * (whose original and consequential footprints do not overlap) may be reordered.
     */
    CALCULATED
}
