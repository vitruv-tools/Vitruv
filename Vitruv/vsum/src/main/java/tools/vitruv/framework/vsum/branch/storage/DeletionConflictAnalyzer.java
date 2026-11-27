package tools.vitruv.framework.vsum.branch.storage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tools.vitruv.framework.vsum.branch.data.DeletionConflict;
import tools.vitruv.framework.vsum.branch.storage.SemanticChangelogManager.ChangelogDocument;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Analyzes two changelog documents (one from a source branch, one from a target
 * branch) and detects <em>delete-vs-update</em> conflicts.
 *
 * <p>A delete-vs-update conflict occurs when:
 * <ul>
 *   <li>Branch A records an {@link SemanticChangeType#ELEMENT_DELETED} for element X</li>
 *   <li>Branch B records any update whose {@code elementUuid} or {@code containerUuid}
 *       matches X (the element itself was updated, or one of its children was)</li>
 * </ul>
 *
 * <p>The analysis is performed symmetrically: if Branch A deletes and Branch B
 * updates, that is a conflict.  If Branch B deletes and Branch A updates, that
 * is also a conflict.
 *
 * @see DeletionConflict
 */
public class DeletionConflictAnalyzer {

    private static final Logger LOGGER = LogManager.getLogger(DeletionConflictAnalyzer.class);

    /**
     * Analyzes two changelogs and returns all delete-vs-update conflicts.
     *
     * @param sourceBranch    name of the source (incoming) branch.
     * @param targetBranch    name of the target (current) branch.
     * @param sourceChangelog changelog from the source branch, may be null.
     * @param targetChangelog changelog from the target branch, may be null.
     * @param ancestorAvailable whether a shared Git ancestor exists.
     * @return list of detected deletion conflicts, never null.
     */
    public List<DeletionConflict> analyze(String sourceBranch, String targetBranch,
                                          ChangelogDocument sourceChangelog,
                                          ChangelogDocument targetChangelog,
                                          boolean ancestorAvailable) {
        List<DeletionConflict> conflicts = new ArrayList<>();

        if (sourceChangelog == null || targetChangelog == null) {
            return conflicts;
        }

        List<SemanticChangeEntry> sourceEntries = collectAllEntries(sourceChangelog);
        List<SemanticChangeEntry> targetEntries = collectAllEntries(targetChangelog);

        // Check: source deletes vs target updates
        conflicts.addAll(findDeleteVsUpdate(
                sourceBranch, targetBranch, sourceEntries, targetEntries, ancestorAvailable));

        // Check: target deletes vs source updates
        conflicts.addAll(findDeleteVsUpdate(
                targetBranch, sourceBranch, targetEntries, sourceEntries, ancestorAvailable));

        if (!conflicts.isEmpty()) {
            LOGGER.info("Detected {} deletion conflict(s) between '{}' and '{}'",
                    conflicts.size(), sourceBranch, targetBranch);
        }
        return conflicts;
    }

    /**
     * Finds conflicts where {@code deletingEntries} contain deletions and
     * {@code updatingEntries} contain updates to the same elements or their
     * children.
     */
    private List<DeletionConflict> findDeleteVsUpdate(
            String deletingBranch, String updatingBranch,
            List<SemanticChangeEntry> deletingEntries,
            List<SemanticChangeEntry> updatingEntries,
            boolean ancestorAvailable) {

        List<DeletionConflict> conflicts = new ArrayList<>();

        // Collect all element UUIDs that were deleted on the deleting branch
        List<SemanticChangeEntry> deletions = deletingEntries.stream()
                .filter(e -> e.getChangeType() == SemanticChangeType.ELEMENT_DELETED
                        || e.getChangeType() == SemanticChangeType.ROOT_REMOVED)
                .collect(Collectors.toList());

        for (SemanticChangeEntry deletion : deletions) {
            String deletedUuid = deletion.getElementUuid();
            if (deletedUuid == null || "unknown".equals(deletedUuid)) {
                continue;
            }

            // Find updates on the other branch that affect this element or
            // are contained within it (containerUuid matches deletedUuid)
            List<SemanticChangeEntry> affected = updatingEntries.stream()
                    .filter(this::isNonDeletionChange)
                    .filter(e -> Objects.equals(deletedUuid, e.getElementUuid())
                            || Objects.equals(deletedUuid, e.getContainerUuid()))
                    .collect(Collectors.toList());

            if (!affected.isEmpty()) {
                conflicts.add(new DeletionConflict(
                        deletedUuid,
                        deletion.getEClass(),
                        deletingBranch,
                        updatingBranch,
                        affected,
                        ancestorAvailable,
                        deletion.getOrigin()
                ));
            }
        }
        return conflicts;
    }

    /**
     * Returns {@code true} if the entry represents a non-deletion change
     * (i.e. an update, creation, or modification that would be lost if the
     * parent element is deleted).
     */
    private boolean isNonDeletionChange(SemanticChangeEntry entry) {
        return entry.getChangeType() != SemanticChangeType.ELEMENT_DELETED
                && entry.getChangeType() != SemanticChangeType.ROOT_REMOVED;
    }

    /**
     * Flattens all semantic change entries from all file changes in a
     * changelog document into a single list.
     */
    private List<SemanticChangeEntry> collectAllEntries(ChangelogDocument doc) {
        List<SemanticChangeEntry> entries = new ArrayList<>();
        if (doc.fileChanges != null) {
            for (ChangelogDocument.FileChangeInfo fc : doc.fileChanges) {
                if (fc.semanticChanges != null) {
                    entries.addAll(fc.semanticChanges);
                }
            }
        }
        return entries;
    }
}
