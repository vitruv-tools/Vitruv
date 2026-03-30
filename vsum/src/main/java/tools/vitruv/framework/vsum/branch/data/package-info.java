/**
 * Data transfer objects and result types for branch and merge operations.
 *
 * <p>All classes in this package are immutable and thread-safe.
 *
 * <p>Branch lifecycle:
 * <ul>
 *   <li>{@link tools.vitruv.framework.vsum.branch.data.BranchMetadata}:per-branch metadata
 *       (name, parent, state, timestamps) persisted as JSON.</li>
 *   <li>{@link tools.vitruv.framework.vsum.branch.data.BranchState}:lifecycle states:
 *       ACTIVE, MERGED, DELETED.</li>
 * </ul>
 *
 * <p>Commit and merge results:
 * <ul>
 *   <li>{@link tools.vitruv.framework.vsum.branch.data.CommitOptions}:optional parameters
 *       for a commit (message, author, amend flag).</li>
 *   <li>{@link tools.vitruv.framework.vsum.branch.data.CommitResult}:outcome of a commit
 *       operation (SHA, branch, staged files).</li>
 *   <li>{@link tools.vitruv.framework.vsum.branch.data.ModelMergeResult}:outcome of a merge
 *       operation with status (SUCCESS, FAST_FORWARD, CONFLICTING, FAILED).</li>
 *   <li>{@link tools.vitruv.framework.vsum.branch.data.ValidationResult}:pre-commit
 *       validation outcome carrying errors and warnings.</li>
 * </ul>
 *
 * <p>Conflict and replay:
 * <ul>
 *   <li>{@link tools.vitruv.framework.vsum.branch.data.ReplayResult}:per-branch change lists
 *       and detected conflicts produced by {@code ChangeReplayer}.</li>
 *   <li>{@link tools.vitruv.framework.vsum.branch.data.SemanticConflict}:a single
 *       {@code (elementUuid, feature)} conflict with severity and conflicting values.</li>
 *   <li>{@link tools.vitruv.framework.vsum.branch.data.ConflictSeverity}:HIGH / MEDIUM
 *       severity levels for semantic conflicts.</li>
 *   <li>{@link tools.vitruv.framework.vsum.branch.data.FileOperation}:file-level Git
 *       operation type: ADDED, MODIFIED, DELETED, RENAMED, COPIED.</li>
 * </ul>
 */
package tools.vitruv.framework.vsum.branch.data;
