package tools.vitruv.framework.vsum.branch.storage;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tools.vitruv.framework.vsum.branch.data.DeletionConflict;
import tools.vitruv.framework.vsum.branch.storage.SemanticChangelogManager.ChangelogDocument;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link DeletionConflictAnalyzer}.
 *
 * <p>Tests verify that delete-vs-update conflicts are detected symmetrically between
 * two changelog documents, that null changelogs and unknown UUIDs are handled gracefully,
 * and that container-UUID matching identifies child updates after parent deletion.
 */
class DeletionConflictAnalyzerTest {

    private final DeletionConflictAnalyzer analyzer = new DeletionConflictAnalyzer();

    // ── document builders ────────────────────────────────────────────────────

    private static ChangelogDocument docWith(List<SemanticChangeEntry> entries) {
        var doc = new ChangelogDocument();
        doc.fileChanges = new ArrayList<>();
        var fc = new ChangelogDocument.FileChangeInfo();
        fc.semanticChanges = entries;
        doc.fileChanges.add(fc);
        return doc;
    }

    private static SemanticChangeEntry deleted(String uuid) {
        return SemanticChangeEntry.builder()
                .index(0).changeType(SemanticChangeType.ELEMENT_DELETED)
                .emfType("DeleteEObject").elementUuid(uuid).build();
    }

    private static SemanticChangeEntry rootRemoved(String uuid) {
        return SemanticChangeEntry.builder()
                .index(0).changeType(SemanticChangeType.ROOT_REMOVED)
                .emfType("RemoveRootEObject").elementUuid(uuid).build();
    }

    private static SemanticChangeEntry attrChanged(String uuid) {
        return SemanticChangeEntry.builder()
                .index(1).changeType(SemanticChangeType.ATTRIBUTE_CHANGED)
                .emfType("ReplaceSingleValuedEAttribute")
                .elementUuid(uuid).feature("name").build();
    }

    private static SemanticChangeEntry attrChangedInContainer(String uuid, String containerUuid) {
        return SemanticChangeEntry.builder()
                .index(1).changeType(SemanticChangeType.ATTRIBUTE_CHANGED)
                .emfType("ReplaceSingleValuedEAttribute")
                .elementUuid(uuid).containerUuid(containerUuid).feature("name").build();
    }

    // ── null / empty ─────────────────────────────────────────────────────────

    @Test
    @DisplayName("Returns empty list when source changelog is null")
    void nullSourceReturnsEmpty() {
        assertTrue(analyzer.analyze("src", "tgt", null, docWith(List.of()), true).isEmpty());
    }

    @Test
    @DisplayName("Returns empty list when target changelog is null")
    void nullTargetReturnsEmpty() {
        assertTrue(analyzer.analyze("src", "tgt", docWith(List.of()), null, true).isEmpty());
    }

    @Test
    @DisplayName("Returns empty list when both changelogs are null")
    void bothNullReturnsEmpty() {
        assertTrue(analyzer.analyze("src", "tgt", null, null, true).isEmpty());
    }

    @Test
    @DisplayName("Returns empty list when there are no deletion or update entries")
    void noEntriesReturnsEmpty() {
        var doc = docWith(List.of());
        assertTrue(analyzer.analyze("src", "tgt", doc, doc, true).isEmpty());
    }

    // ── source deletes, target updates ───────────────────────────────────────

    @Test
    @DisplayName("DELETE_MODIFY conflict detected when source deletes and target updates same UUID")
    void conflictWhenSourceDeletesTargetUpdates() {
        var source = docWith(List.of(deleted("uuid-A")));
        var target = docWith(List.of(attrChanged("uuid-A")));

        List<DeletionConflict> conflicts = analyzer.analyze("src", "tgt", source, target, true);

        assertEquals(1, conflicts.size());
        assertEquals("uuid-A", conflicts.get(0).getDeletedElementUuid());
        assertEquals("src", conflicts.get(0).getDeletingBranch());
        assertEquals("tgt", conflicts.get(0).getUpdatingBranch());
    }

    @Test
    @DisplayName("Conflict detected when source deletes and target updates a child (containerUuid match)")
    void conflictForChildUpdateAfterParentDeletion() {
        var source = docWith(List.of(deleted("parent-uuid")));
        var target = docWith(List.of(attrChangedInContainer("child-uuid", "parent-uuid")));

        List<DeletionConflict> conflicts = analyzer.analyze("src", "tgt", source, target, true);

        assertEquals(1, conflicts.size());
        assertEquals("parent-uuid", conflicts.get(0).getDeletedElementUuid());
    }

    // ── symmetric: target deletes, source updates ────────────────────────────

    @Test
    @DisplayName("Conflict detected symmetrically when target deletes and source updates same UUID")
    void conflictSymmetricWhenTargetDeletesSourceUpdates() {
        var source = docWith(List.of(attrChanged("uuid-B")));
        var target = docWith(List.of(deleted("uuid-B")));

        List<DeletionConflict> conflicts = analyzer.analyze("src", "tgt", source, target, true);

        assertEquals(1, conflicts.size());
        assertEquals("uuid-B", conflicts.get(0).getDeletedElementUuid());
        assertEquals("tgt", conflicts.get(0).getDeletingBranch());
        assertEquals("src", conflicts.get(0).getUpdatingBranch());
    }

    // ── no conflict scenarios ────────────────────────────────────────────────

    @Test
    @DisplayName("No conflict when deletion on source but no update on target")
    void noConflictWhenDeletionWithNoUpdate() {
        var source = docWith(List.of(deleted("uuid-A")));
        var target = docWith(List.of(attrChanged("uuid-B")));
        assertTrue(analyzer.analyze("src", "tgt", source, target, true).isEmpty());
    }

    @Test
    @DisplayName("No conflict when both branches delete the same element")
    void noConflictWhenBothDelete() {
        var source = docWith(List.of(deleted("uuid-A")));
        var target = docWith(List.of(deleted("uuid-A")));
        assertTrue(analyzer.analyze("src", "tgt", source, target, true).isEmpty());
    }

    @Test
    @DisplayName("Element with unknown UUID is skipped and does not cause a conflict")
    void unknownUuidIsSkipped() {
        var source = docWith(List.of(
                SemanticChangeEntry.builder().index(0)
                        .changeType(SemanticChangeType.ELEMENT_DELETED)
                        .emfType("DeleteEObject").elementUuid("unknown").build()));
        var target = docWith(List.of(attrChanged("unknown")));
        assertTrue(analyzer.analyze("src", "tgt", source, target, true).isEmpty());
    }

    // ── ROOT_REMOVED ─────────────────────────────────────────────────────────

    @Test
    @DisplayName("ROOT_REMOVED is also treated as a deletion for conflict detection")
    void rootRemovedTreatedAsDeletion() {
        var source = docWith(List.of(rootRemoved("uuid-C")));
        var target = docWith(List.of(attrChanged("uuid-C")));

        List<DeletionConflict> conflicts = analyzer.analyze("src", "tgt", source, target, true);
        assertEquals(1, conflicts.size());
    }

    // ── multiple conflicts ────────────────────────────────────────────────────

    @Test
    @DisplayName("Multiple deletion conflicts are all returned")
    void multipleConflictsAreAllReturned() {
        var source = docWith(List.of(deleted("uuid-1"), deleted("uuid-2")));
        var target = docWith(List.of(attrChanged("uuid-1"), attrChanged("uuid-2")));

        List<DeletionConflict> conflicts = analyzer.analyze("src", "tgt", source, target, true);
        assertEquals(2, conflicts.size());
    }
}
