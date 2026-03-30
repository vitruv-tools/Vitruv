/**
 * Branch-aware extension of the Vitruvius Virtual Structured Model (V-SUM).
 *
 * <p>This package provides the top-level API for branching, committing, merging, and
 * visualizing semantic changes within a Git-backed Vitruvius workspace.
 *
 * <p>Key classes:
 * <ul>
 *   <li>{@link tools.vitruv.framework.vsum.branch.BranchAwareVirtualModel}: decorator
 *       around {@code InternalVirtualModel} that reloads in place on branch switch.</li>
 *   <li>{@link tools.vitruv.framework.vsum.branch.BranchManager}: creates, deletes, lists,
 *       and switches Git branches with Vitruvius metadata tracking.</li>
 *   <li>{@link tools.vitruv.framework.vsum.branch.CommitManager}: stages model files and
 *       commits to Git, writing the semantic changelog synchronously.</li>
 *   <li>{@link tools.vitruv.framework.vsum.branch.MergeManager}: merges a source branch into
 *       the current branch via JGit and returns a typed result.</li>
 *   <li>{@link tools.vitruv.framework.vsum.branch.ChangeReplayer}: replays per-branch
 *       changelog entries to compute semantic deltas and conflicts.</li>
 *   <li>{@link tools.vitruv.framework.vsum.branch.DeltaVisualizationService}: produces
 *       {@link tools.vitruv.framework.vsum.branch.DeltaView} and
 *       {@link tools.vitruv.framework.vsum.branch.BranchDeltaView} for UI-1.</li>
 * </ul>
 */
package tools.vitruv.framework.vsum.branch;
