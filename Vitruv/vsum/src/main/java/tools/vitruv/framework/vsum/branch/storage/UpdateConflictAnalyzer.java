package tools.vitruv.framework.vsum.branch.storage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tools.vitruv.framework.vsum.branch.data.UpdateConflict;
import tools.vitruv.framework.vsum.branch.storage.SemanticChangelogManager.ChangelogDocument;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Analyzes two changelog documents and detects <em>update-vs-update</em> conflicts.
 *
 * <p>An update-vs-update conflict occurs when:
 * <ul>
 *   <li>Branch A records a modification to feature F on element X</li>
 *   <li>Branch B also records a modification to the same feature F on the same element X</li>
 * </ul>
 *
 * <p>The detection is purely DTO-based (UUID + feature name comparison).
 * No model instances are loaded into memory, preserving the performance
 * characteristics described in the paper.
 *
 * <p>When one side is {@link ChangeOrigin#ORIGINAL} and the other is
 * {@link ChangeOrigin#CONSEQUENTIAL}, the conflict can potentially be
 * auto-resolved by preferring the original (human-made) change.
 *
 * @see UpdateConflict
 * @see DeletionConflictAnalyzer
 */
public class UpdateConflictAnalyzer {

    private static final Logger LOGGER = LogManager.getLogger(UpdateConflictAnalyzer.class);

    /**
     * Change types that represent attribute/reference modifications.
     * Lifecycle changes (create/delete) and root changes are excluded
     * because they don't represent "lost update" scenarios.
     */
    private static final Set<SemanticChangeType> MODIFYING_TYPES = Set.of(
            SemanticChangeType.ATTRIBUTE_CHANGED,
            SemanticChangeType.ATTRIBUTE_SET,
            SemanticChangeType.ATTRIBUTE_CLEARED,
            SemanticChangeType.ATTRIBUTE_VALUE_INSERTED,
            SemanticChangeType.ATTRIBUTE_VALUE_REMOVED,
            SemanticChangeType.REFERENCE_CHANGED,
            SemanticChangeType.REFERENCE_SET,
            SemanticChangeType.REFERENCE_CLEARED,
            SemanticChangeType.REFERENCE_VALUE_INSERTED,
            SemanticChangeType.REFERENCE_VALUE_REMOVED
    );

    /**
     * Analyzes two changelogs for update-vs-update conflicts.
     *
     * @param sourceBranch    name of the source (incoming) branch.
     * @param targetBranch    name of the target (current) branch.
     * @param sourceChangelog changelog from the source branch, may be null.
     * @param targetChangelog changelog from the target branch, may be null.
     * @return list of update conflicts, never null.
     */
    public List<UpdateConflict> analyze(String sourceBranch, String targetBranch,
                                        ChangelogDocument sourceChangelog,
                                        ChangelogDocument targetChangelog) {
        List<UpdateConflict> conflicts = new ArrayList<>();

        if (sourceChangelog == null || targetChangelog == null) {
            return conflicts;
        }

        List<SemanticChangeEntry> sourceEntries = collectModifyingEntries(sourceChangelog);
        List<SemanticChangeEntry> targetEntries = collectModifyingEntries(targetChangelog);
        Set<String> recorded = new HashSet<>();

        // For each source modification, check if the target also modifies the same UUID+feature
        for (SemanticChangeEntry sourceEntry : sourceEntries) {
            String uuid = sourceEntry.getElementUuid();
            String feature = sourceEntry.getFeature();

            if (uuid == null || "unknown".equals(uuid) || feature == null) {
                continue;
            }

            for (SemanticChangeEntry targetEntry : targetEntries) {
                if (Objects.equals(uuid, targetEntry.getElementUuid())
                        && Objects.equals(feature, targetEntry.getFeature())) {

                    if (!recorded.add(uuid + "#" + feature)) {
                        continue;
                    }

                    conflicts.add(new UpdateConflict(
                            uuid,
                            sourceEntry.getEClass() != null ? sourceEntry.getEClass() : targetEntry.getEClass(),
                            feature,
                            sourceBranch,
                            targetBranch,
                            sourceEntry,
                            targetEntry
                    ));
                }
            }
        }

        if (!conflicts.isEmpty()) {
            LOGGER.info("Detected {} update-vs-update conflict(s) between '{}' and '{}'",
                    conflicts.size(), sourceBranch, targetBranch);
        }
        return conflicts;
    }

    /**
     * Collects all entries that represent attribute/reference modifications.
     */
    private List<SemanticChangeEntry> collectModifyingEntries(ChangelogDocument doc) {
        List<SemanticChangeEntry> entries = new ArrayList<>();
        if (doc.fileChanges != null) {
            for (ChangelogDocument.FileChangeInfo fc : doc.fileChanges) {
                if (fc.semanticChanges != null) {
                    for (SemanticChangeEntry entry : fc.semanticChanges) {
                        if (MODIFYING_TYPES.contains(entry.getChangeType())) {
                            entries.add(entry);
                        }
                    }
                }
            }
        }
        return entries;
    }
}
