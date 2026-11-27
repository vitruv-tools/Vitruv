package tools.vitruv.framework.vsum.branch.storage;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tools.vitruv.framework.vsum.branch.data.UpdateConflict;
import tools.vitruv.framework.vsum.branch.storage.SemanticChangelogManager.ChangelogDocument;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link UpdateConflictAnalyzer}.
 *
 * <p>Tests verify update-vs-update conflict detection (same UUID+feature modified by both
 * branches), non-conflicting scenarios (different UUIDs or features), deduplication of the
 * same UUID+feature pair, and graceful handling of null changelogs.
 */
class UpdateConflictAnalyzerTest {

    private final UpdateConflictAnalyzer analyzer = new UpdateConflictAnalyzer();

    // ── document builders ────────────────────────────────────────────────────

    private static ChangelogDocument docWith(List<SemanticChangeEntry> entries) {
        var doc = new ChangelogDocument();
        doc.fileChanges = new ArrayList<>();
        var fc = new ChangelogDocument.FileChangeInfo();
        fc.semanticChanges = new ArrayList<>(entries);
        doc.fileChanges.add(fc);
        return doc;
    }

    private static SemanticChangeEntry attrChanged(String uuid, String feature) {
        return SemanticChangeEntry.builder()
                .index(0).changeType(SemanticChangeType.ATTRIBUTE_CHANGED)
                .emfType("ReplaceSingleValuedEAttribute")
                .elementUuid(uuid).feature(feature).build();
    }

    private static SemanticChangeEntry refChanged(String uuid, String feature) {
        return SemanticChangeEntry.builder()
                .index(0).changeType(SemanticChangeType.REFERENCE_CHANGED)
                .emfType("ReplaceSingleValuedEReference")
                .elementUuid(uuid).feature(feature).build();
    }

    private static SemanticChangeEntry created(String uuid) {
        return SemanticChangeEntry.builder()
                .index(0).changeType(SemanticChangeType.ELEMENT_CREATED)
                .emfType("CreateEObject").elementUuid(uuid).build();
    }

    private static SemanticChangeEntry deleted(String uuid) {
        return SemanticChangeEntry.builder()
                .index(0).changeType(SemanticChangeType.ELEMENT_DELETED)
                .emfType("DeleteEObject").elementUuid(uuid).build();
    }

    // ── null / empty ─────────────────────────────────────────────────────────

    @Test
    @DisplayName("Returns empty list when source changelog is null")
    void nullSourceReturnsEmpty() {
        assertTrue(analyzer.analyze("src", "tgt", null, docWith(List.of())).isEmpty());
    }

    @Test
    @DisplayName("Returns empty list when target changelog is null")
    void nullTargetReturnsEmpty() {
        assertTrue(analyzer.analyze("src", "tgt", docWith(List.of()), null).isEmpty());
    }

    @Test
    @DisplayName("Returns empty list when both changelogs are null")
    void bothNullReturnsEmpty() {
        assertTrue(analyzer.analyze("src", "tgt", null, null).isEmpty());
    }

    @Test
    @DisplayName("Returns empty list when there are no modifying entries")
    void noModifyingEntriesReturnsEmpty() {
        var doc = docWith(List.of());
        assertTrue(analyzer.analyze("src", "tgt", doc, doc).isEmpty());
    }

    // ── conflict detected ─────────────────────────────────────────────────────

    @Test
    @DisplayName("Update-vs-update conflict detected when both branches modify same UUID+feature")
    void conflictDetectedForSameUuidAndFeature() {
        var source = docWith(List.of(attrChanged("uuid-1", "name")));
        var target = docWith(List.of(attrChanged("uuid-1", "name")));

        List<UpdateConflict> conflicts = analyzer.analyze("src", "tgt", source, target);

        assertEquals(1, conflicts.size());
        assertEquals("uuid-1", conflicts.get(0).getElementUuid());
        assertEquals("name", conflicts.get(0).getFeatureName());
        assertEquals("src", conflicts.get(0).getSourceBranch());
        assertEquals("tgt", conflicts.get(0).getTargetBranch());
    }

    @Test
    @DisplayName("Reference changes also trigger update-vs-update conflict detection")
    void referenceConflictDetected() {
        var source = docWith(List.of(refChanged("uuid-1", "children")));
        var target = docWith(List.of(refChanged("uuid-1", "children")));

        List<UpdateConflict> conflicts = analyzer.analyze("src", "tgt", source, target);
        assertEquals(1, conflicts.size());
        assertEquals("children", conflicts.get(0).getFeatureName());
    }

    // ── no conflict ───────────────────────────────────────────────────────────

    @Test
    @DisplayName("No conflict when branches modify different elements")
    void noConflictForDifferentUuids() {
        var source = docWith(List.of(attrChanged("uuid-A", "name")));
        var target = docWith(List.of(attrChanged("uuid-B", "name")));
        assertTrue(analyzer.analyze("src", "tgt", source, target).isEmpty());
    }

    @Test
    @DisplayName("No conflict when branches modify different features of the same element")
    void noConflictForDifferentFeatures() {
        var source = docWith(List.of(attrChanged("uuid-1", "name")));
        var target = docWith(List.of(attrChanged("uuid-1", "age")));
        assertTrue(analyzer.analyze("src", "tgt", source, target).isEmpty());
    }

    @Test
    @DisplayName("ELEMENT_CREATED and ELEMENT_DELETED entries do not trigger update conflicts")
    void lifecycleChangesDoNotConflict() {
        var source = docWith(List.of(created("uuid-1")));
        var target = docWith(List.of(deleted("uuid-1")));
        assertTrue(analyzer.analyze("src", "tgt", source, target).isEmpty());
    }

    @Test
    @DisplayName("Entry with null UUID is skipped in update conflict detection")
    void nullUuidIsSkipped() {
        var entry = SemanticChangeEntry.builder()
                .index(0).changeType(SemanticChangeType.ATTRIBUTE_CHANGED)
                .emfType("RSVEAttribute").elementUuid(null).feature("name").build();
        var source = docWith(List.of(entry));
        var target = docWith(List.of(entry));
        assertTrue(analyzer.analyze("src", "tgt", source, target).isEmpty());
    }

    @Test
    @DisplayName("Entry with unknown UUID string is skipped")
    void unknownUuidStringIsSkipped() {
        var entry = SemanticChangeEntry.builder()
                .index(0).changeType(SemanticChangeType.ATTRIBUTE_CHANGED)
                .emfType("RSVEAttribute").elementUuid("unknown").feature("name").build();
        var source = docWith(List.of(entry));
        var target = docWith(List.of(entry));
        assertTrue(analyzer.analyze("src", "tgt", source, target).isEmpty());
    }

    // ── deduplication ─────────────────────────────────────────────────────────

    @Test
    @DisplayName("Only one conflict is reported per UUID+feature pair even with multiple entries")
    void deduplicatesSameUuidFeaturePair() {
        // source has two attribute-changed entries for the same uuid+feature
        var source = docWith(List.of(
                attrChanged("uuid-1", "name"),
                attrChanged("uuid-1", "name")));
        var target = docWith(List.of(attrChanged("uuid-1", "name")));

        List<UpdateConflict> conflicts = analyzer.analyze("src", "tgt", source, target);
        assertEquals(1, conflicts.size());
    }

    // ── multiple conflicts ────────────────────────────────────────────────────

    @Test
    @DisplayName("Multiple independent UUID+feature conflicts are all reported")
    void multipleConflictsAreAllReported() {
        var source = docWith(List.of(
                attrChanged("uuid-1", "name"),
                attrChanged("uuid-2", "age")));
        var target = docWith(List.of(
                attrChanged("uuid-1", "name"),
                attrChanged("uuid-2", "age")));

        List<UpdateConflict> conflicts = analyzer.analyze("src", "tgt", source, target);
        assertEquals(2, conflicts.size());
    }
}
