/**
 * Handlers and watchers for Git hook integration.
 *
 * <p>Contains:
 * <ul>
 *   <li>PostCheckoutHandler - Reloads V-SUM after branch switch</li>
 *   <li>PreCommitHandler - Validates V-SUM before commit</li>
 *   <li>VsumReloadWatcher - Background monitoring for reload triggers</li>
 *   <li>VsumValidationWatcher - Background monitoring for validation triggers</li>
 * </ul>
 */
package tools.vitruv.framework.vsum.branch.handler;