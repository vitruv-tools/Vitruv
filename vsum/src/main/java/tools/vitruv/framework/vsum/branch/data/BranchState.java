package tools.vitruv.framework.vsum.branch.data;

/**
 * Lifecycle state of a {@link BranchMetadata}-managed branch.
 *
 * <ul>
 *   <li>{@link #ACTIVE} – live branch; can be switched to and worked on.</li>
 *   <li>{@link #MERGED} – merged into another branch; no longer active.</li>
 *   <li>{@link #DELETED} – explicitly deleted; no longer available in Git.</li>
 * </ul>
 */
public enum BranchState {
  ACTIVE,
  MERGED,
  DELETED
}
