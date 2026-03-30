/**
 * Git hook handlers and background watcher threads.
 *
 * <p>Each Git hook has a corresponding handler (business logic) and watcher (trigger detection):
 * <ul>
 *   <li>{@link tools.vitruv.framework.vsum.branch.handler.PreCommitHandler} /
 *       {@link tools.vitruv.framework.vsum.branch.handler.VsumValidationWatcher} :
 *       validates V-SUM consistency before a commit is allowed to proceed.</li>
 *   <li>{@link tools.vitruv.framework.vsum.branch.handler.PostCommitHandler} /
 *       {@link tools.vitruv.framework.vsum.branch.handler.VsumPostCommitWatcher} :
 *       writes the semantic changelog after a direct Git commit.</li>
 *   <li>{@link tools.vitruv.framework.vsum.branch.handler.PostCheckoutHandler} /
 *       {@link tools.vitruv.framework.vsum.branch.handler.VsumReloadWatcher} :
 *       reloads V-SUM after a branch switch.</li>
 *   <li>{@link tools.vitruv.framework.vsum.branch.handler.PostMergeHandler} /
 *       {@link tools.vitruv.framework.vsum.branch.handler.VsumMergeWatcher} :
 *       validates the merged V-SUM state and writes merge metadata.</li>
 * </ul>
 */
package tools.vitruv.framework.vsum.branch.handler;
