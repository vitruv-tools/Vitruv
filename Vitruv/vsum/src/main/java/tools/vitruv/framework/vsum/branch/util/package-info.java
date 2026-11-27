/**
 * File-based inter-process communication utilities for Git hook integration.
 *
 * <p>Trigger files are written by Git hooks and polled by background watcher threads.
 * Result files carry the watcher's response back to the hook process.
 *
 * <p>Base infrastructure:
 * <ul>
 *   <li>{@link tools.vitruv.framework.vsum.branch.util.AbstractTriggerFile}: base class
 *       providing the pipe-delimited read/write/clear lifecycle shared by all trigger files.</li>
 * </ul>
 *
 * <p>Trigger files (hook → watcher):
 * <ul>
 *   <li>{@link tools.vitruv.framework.vsum.branch.util.ValidationTriggerFile}: written by
 *       the {@code pre-commit} hook to request V-SUM validation.</li>
 *   <li>{@link tools.vitruv.framework.vsum.branch.util.ReloadTriggerFile}: written by the
 *       {@code post-checkout} hook to request a V-SUM reload after a branch switch.</li>
 *   <li>{@link tools.vitruv.framework.vsum.branch.util.PostCommitTriggerFile}: written by
 *       the {@code post-commit} hook to trigger semantic changelog generation.</li>
 *   <li>{@link tools.vitruv.framework.vsum.branch.util.MergeTriggerFile}: written by the
 *       {@code post-merge} hook to request post-merge V-SUM validation.</li>
 * </ul>
 *
 * <p>Result files (watcher → hook):
 * <ul>
 *   <li>{@link tools.vitruv.framework.vsum.branch.util.ValidationResultFile}: carries
 *       the validation outcome back to the {@code pre-commit} hook.</li>
 *   <li>{@link tools.vitruv.framework.vsum.branch.util.MergeResultFile}: carries the
 *       merge validation outcome and persists merge metadata.</li>
 * </ul>
 *
 * <p>Installation:
 * <ul>
 *   <li>{@link tools.vitruv.framework.vsum.branch.util.GitHookInstaller}: installs the
 *       Vitruvius Git hook scripts into the repository's {@code .git/hooks/} directory.</li>
 * </ul>
 */
package tools.vitruv.framework.vsum.branch.util;
