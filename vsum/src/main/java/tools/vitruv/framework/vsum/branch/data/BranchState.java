package tools.vitruv.framework.vsum.branch.data;


/**
 * Represents the lifecycle state of a {@link BranchMetadata managed branch}
 * within Vitruvius's branching system.
 * <ul>
 *     <li>{@link #ACTIVE}: The branch is currently live and can be switched to and worked on.</li>
 *     <li>{@link #MERGED}: The branch has been merged into another branch
 *     and is no longer active.</li>
 *     <li>{@link #DELETED}: The branch has been explicitly deleted and is no longer available.</li>
 * </ul>
 */
public enum BranchState {
    ACTIVE,
    MERGED,
    DELETED
}

