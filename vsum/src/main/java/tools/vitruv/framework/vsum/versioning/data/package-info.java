/**
 * Data structures for versioning and rollback operations.
 *
 * <p>Contains:
 * <ul>
 *   <li>{@link tools.vitruv.framework.vsum.versioning.data.VersionMetadata}: persisted
 *       metadata for a single version snapshot (SHA, branch, author, timestamp).</li>
 *   <li>{@link tools.vitruv.framework.vsum.versioning.data.RollbackPreview}: a dry-run
 *       result showing which files would be affected by a rollback before it is applied.</li>
 *   <li>{@link tools.vitruv.framework.vsum.versioning.data.RollbackResult}: the outcome
 *       of a completed rollback operation, including affected files and new HEAD SHA.</li>
 * </ul>
 */
package tools.vitruv.framework.vsum.versioning.data;
