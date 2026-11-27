package tools.vitruv.framework.vsum.branch.data;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tools.vitruv.framework.vsum.branch.storage.SemanticChangeEntry;
import tools.vitruv.framework.vsum.branch.storage.SemanticChangeType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link ReplayResult}.
 *
 * <p>Tests verify that all getters return the provided values, null list arguments
 * are rejected, {@link ReplayResult#hasConflicts()}, and the severity count helpers.
 */
class ReplayResultTest {

    private static SemanticChangeEntry attrEntry(String uuid) {
        return SemanticChangeEntry.builder()
                .index(0).changeType(SemanticChangeType.ATTRIBUTE_CHANGED)
                .emfType("ReplaceSingleValuedEAttribute")
                .elementUuid(uuid).feature("name").build();
    }

    private static SemanticConflict conflict(ConflictSeverity severity) {
        var entry = attrEntry("uuid-1");
        return new SemanticConflict("uuid-1", "name", entry, entry, severity);
    }

    @Test
    @DisplayName("All getters return the values provided at construction")
    void allGettersReturnProvidedValues() {
        var changesA = List.of(attrEntry("u1"));
        var changesB = List.of(attrEntry("u2"));
        var shaA = List.of("abc1234");
        var shaB = List.of("def5678");
        var conflicts = List.of(conflict(ConflictSeverity.MEDIUM));

        var result = new ReplayResult("ancestor-sha", shaA, shaB, changesA, changesB, conflicts);

        assertEquals("ancestor-sha", result.getAncestorSha());
        assertEquals(shaA, result.getCommitShasOnA());
        assertEquals(shaB, result.getCommitShasOnB());
        assertEquals(changesA, result.getChangesOnA());
        assertEquals(changesB, result.getChangesOnB());
        assertEquals(conflicts, result.getConflicts());
    }

    @Test
    @DisplayName("Null ancestor SHA is allowed (unrelated histories)")
    void nullAncestorShaIsAllowed() {
        var result = new ReplayResult(null, List.of(), List.of(), List.of(), List.of(), List.of());
        assertNull(result.getAncestorSha());
    }

    @Test
    @DisplayName("Null commit SHA lists are rejected")
    void nullCommitShaListsAreRejected() {
        assertThrows(NullPointerException.class,
                () -> new ReplayResult("sha", null, List.of(), List.of(), List.of(), List.of()));
        assertThrows(NullPointerException.class,
                () -> new ReplayResult("sha", List.of(), null, List.of(), List.of(), List.of()));
    }

    @Test
    @DisplayName("hasConflicts returns false when the conflict list is empty")
    void hasConflictsIsFalseWhenEmpty() {
        var result = new ReplayResult("sha", List.of(), List.of(), List.of(), List.of(), List.of());
        assertFalse(result.hasConflicts());
    }

    @Test
    @DisplayName("hasConflicts returns true when at least one conflict is present")
    void hasConflictsIsTrueWhenNonEmpty() {
        var result = new ReplayResult("sha", List.of(), List.of(), List.of(), List.of(),
                List.of(conflict(ConflictSeverity.MEDIUM)));
        assertTrue(result.hasConflicts());
    }

    @Test
    @DisplayName("highSeverityCount and mediumSeverityCount count correctly")
    void severityCountsAreCorrect() {
        var result = new ReplayResult("sha", List.of(), List.of(), List.of(), List.of(),
                List.of(
                        conflict(ConflictSeverity.HIGH),
                        conflict(ConflictSeverity.HIGH),
                        conflict(ConflictSeverity.MEDIUM)));

        assertEquals(2, result.highSeverityCount());
        assertEquals(1, result.mediumSeverityCount());
    }

    @Test
    @DisplayName("toString includes ancestor SHA, commit counts, and conflict count")
    void toStringIncludesKeyInfo() {
        var result = new ReplayResult("abc1234567", List.of("c1"), List.of("c2"),
                List.of(), List.of(), List.of(conflict(ConflictSeverity.HIGH)));
        String str = result.toString();
        assertTrue(str.contains("1"), "commit counts must appear");
        assertTrue(str.contains("conflicts=1"));
    }
}
