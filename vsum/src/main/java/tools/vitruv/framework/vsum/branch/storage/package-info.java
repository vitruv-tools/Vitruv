/**
 * Semantic changelog storage and EChange-to-entry conversion.
 *
 * <p>Contains:
 * <ul>
 *   <li>{@link tools.vitruv.framework.vsum.branch.storage.SemanticChangeBuffer} : accumulates
 *       atomic {@code EChange} instances between commits as a
 *       {@code ChangePropagationListener}.</li>
 *   <li>{@link tools.vitruv.framework.vsum.branch.storage.EChangeToEntryConverter} : maps
 *       EMF {@code EChange} subtypes to {@link tools.vitruv.framework.vsum.branch.storage.SemanticChangeEntry}
 *       records for JSON serialization.</li>
 *   <li>{@link tools.vitruv.framework.vsum.branch.storage.SemanticChangelogManager} : writes
 *       and reads JSON and XMI changelog files under
 *       {@code .vitruvius/changelogs/<branch>/}.</li>
 *   <li>{@link tools.vitruv.framework.vsum.branch.storage.SemanticChangeType} : enum of
 *       human-readable change categories used as the primary key in conflict detection.</li>
 * </ul>
 */
package tools.vitruv.framework.vsum.branch.storage;
