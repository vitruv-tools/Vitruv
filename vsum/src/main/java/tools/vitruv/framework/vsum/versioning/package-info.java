/**
 * Versioning and rollback services for the Vitruvius V-SUM.
 *
 * <p>Provides a Git-backed versioning layer on top of the branch-aware V-SUM,
 * allowing developers to inspect history and roll back to previous model states.
 *
 * <p>Contains:
 * <ul>
 *   <li>{@link tools.vitruv.framework.vsum.versioning.VersioningService}:creates snapshots,
 *       lists version history, and performs rollbacks to a given commit SHA.</li>
 *   <li>{@link tools.vitruv.framework.vsum.versioning.VersionBrowserService}:reads Git
 *       history and changelog files to produce human-readable version summaries.</li>
 *   <li>{@link tools.vitruv.framework.vsum.versioning.VersionSummary}:lightweight
 *       description of a single version entry (SHA, message, timestamp).</li>
 *   <li>{@link tools.vitruv.framework.vsum.versioning.VersioningException}:checked
 *       exception for versioning and rollback failures.</li>
 * </ul>
 */
package tools.vitruv.framework.vsum.versioning;
