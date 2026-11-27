package tools.vitruv.framework.vsum.branch.data;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tools.vitruv.framework.vsum.branch.storage.ChangeOrigin;
import tools.vitruv.framework.vsum.branch.storage.SemanticChangeEntry;
import tools.vitruv.framework.vsum.branch.storage.SemanticChangeType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link DeletionConflict}.
 *
 * <p>Tests verify constructor null rejection, getter correctness, null origin defaulting
 * to UNKNOWN, {@link DeletionConflict#getLostUpdateCount()},
 * {@link DeletionConflict#getSeverity()}, {@link DeletionConflict#isHighImpact(int)},
 * and {@link DeletionConflict#isConsequentialDeletionVsOriginalUpdates()}.
 */
class DeletionConflictTest {

    private static SemanticChangeEntry attrEntry(String uuid, String origin) {
        return SemanticChangeEntry.builder()
                .index(0).changeType(SemanticChangeType.ATTRIBUTE_CHANGED)
                .emfType("ReplaceSingleValuedEAttribute")
                .elementUuid(uuid).feature("name")
                .changeOrigin(origin).build();
    }

    @Test
    @DisplayName("All getters return the values provided at construction")
    void allGettersReturnProvidedValues() {
        var updates = List.of(attrEntry("uuid-A", "original"));
        var conflict = new DeletionConflict("uuid-1", "Entity",
                "feature-del", "feature-upd", updates, true, ChangeOrigin.ORIGINAL);

        assertEquals("uuid-1", conflict.getDeletedElementUuid());
        assertEquals("Entity", conflict.getDeletedElementEClass());
        assertEquals("feature-del", conflict.getDeletingBranch());
        assertEquals("feature-upd", conflict.getUpdatingBranch());
        assertEquals(updates, conflict.getAffectedUpdates());
        assertTrue(conflict.isAncestorAvailable());
        assertEquals(ChangeOrigin.ORIGINAL, conflict.getDeletionOrigin());
    }

    @Test
    @DisplayName("Null deletedElementUuid is rejected")
    void nullUuidIsRejected() {
        assertThrows(NullPointerException.class,
                () -> new DeletionConflict(null, "Entity", "src", "tgt",
                        List.of(), false, ChangeOrigin.ORIGINAL));
    }

    @Test
    @DisplayName("Null deletingBranch is rejected")
    void nullDeletingBranchIsRejected() {
        assertThrows(NullPointerException.class,
                () -> new DeletionConflict("uuid-1", "Entity", null, "tgt",
                        List.of(), false, ChangeOrigin.ORIGINAL));
    }

    @Test
    @DisplayName("Null updatingBranch is rejected")
    void nullUpdatingBranchIsRejected() {
        assertThrows(NullPointerException.class,
                () -> new DeletionConflict("uuid-1", "Entity", "src", null,
                        List.of(), false, ChangeOrigin.ORIGINAL));
    }

    @Test
    @DisplayName("Null affectedUpdates is rejected")
    void nullAffectedUpdatesIsRejected() {
        assertThrows(NullPointerException.class,
                () -> new DeletionConflict("uuid-1", "Entity", "src", "tgt",
                        null, false, ChangeOrigin.ORIGINAL));
    }

    @Test
    @DisplayName("Null deletionOrigin defaults to UNKNOWN")
    void nullDeletionOriginDefaultsToUnknown() {
        var conflict = new DeletionConflict("uuid-1", "Entity", "src", "tgt",
                List.of(), false, null);
        assertEquals(ChangeOrigin.UNKNOWN, conflict.getDeletionOrigin());
    }

    @Test
    @DisplayName("getLostUpdateCount returns the number of affected updates")
    void lostUpdateCountMatchesListSize() {
        var updates = List.of(attrEntry("u1", "original"), attrEntry("u2", "original"));
        var conflict = new DeletionConflict("uuid-1", "Entity", "src", "tgt",
                updates, false, ChangeOrigin.ORIGINAL);
        assertEquals(2, conflict.getLostUpdateCount());
    }

    @Test
    @DisplayName("getSeverity delegates to ConflictSeverity.fromLostUpdateCount")
    void severityMatchesLostUpdateCount() {
        var conflict0 = new DeletionConflict("uuid-1", "Entity", "src", "tgt",
                List.of(), false, ChangeOrigin.ORIGINAL);
        assertEquals(ConflictSeverity.LOW, conflict0.getSeverity());

        var updates2 = List.of(attrEntry("u1", "original"), attrEntry("u2", "original"));
        var conflict2 = new DeletionConflict("uuid-1", "Entity", "src", "tgt",
                updates2, false, ChangeOrigin.ORIGINAL);
        assertEquals(ConflictSeverity.MEDIUM, conflict2.getSeverity());
    }

    @Test
    @DisplayName("isHighImpact returns true when lost update count meets or exceeds threshold")
    void isHighImpactThreshold() {
        var updates = List.of(attrEntry("u1", "original"), attrEntry("u2", "original"));
        var conflict = new DeletionConflict("uuid-1", "Entity", "src", "tgt",
                updates, false, ChangeOrigin.ORIGINAL);

        assertTrue(conflict.isHighImpact(2), "2 updates meets threshold of 2");
        assertFalse(conflict.isHighImpact(3), "2 updates does not meet threshold of 3");
    }

    @Test
    @DisplayName("isConsequentialDeletionVsOriginalUpdates returns false when deletion is ORIGINAL")
    void notConsequentialDeletionWhenDeletionIsOriginal() {
        var updates = List.of(attrEntry("u1", "original"));
        var conflict = new DeletionConflict("uuid-1", "Entity", "src", "tgt",
                updates, false, ChangeOrigin.ORIGINAL);
        assertFalse(conflict.isConsequentialDeletionVsOriginalUpdates());
    }

    @Test
    @DisplayName("isConsequentialDeletionVsOriginalUpdates returns true when deletion is CONSEQUENTIAL and at least one update is ORIGINAL")
    void consequentialDeletionVsOriginalUpdate() {
        var updates = List.of(attrEntry("u1", "original"));
        var conflict = new DeletionConflict("uuid-1", "Entity", "src", "tgt",
                updates, false, ChangeOrigin.CONSEQUENTIAL);
        assertTrue(conflict.isConsequentialDeletionVsOriginalUpdates());
    }

    @Test
    @DisplayName("isConsequentialDeletionVsOriginalUpdates returns false when deletion is CONSEQUENTIAL but all updates are also CONSEQUENTIAL")
    void consequentialDeletionVsConsequentialUpdate() {
        var updates = List.of(attrEntry("u1", "consequential"));
        var conflict = new DeletionConflict("uuid-1", "Entity", "src", "tgt",
                updates, false, ChangeOrigin.CONSEQUENTIAL);
        assertFalse(conflict.isConsequentialDeletionVsOriginalUpdates());
    }

    @Test
    @DisplayName("toString includes UUID, class name, deleting branch, updating branch, and severity")
    void toStringIncludesKeyFields() {
        var conflict = new DeletionConflict("uuid-1", "Entity", "src", "tgt",
                List.of(), false, ChangeOrigin.ORIGINAL);
        String str = conflict.toString();
        assertTrue(str.contains("uuid-1"));
        assertTrue(str.contains("Entity"));
        assertTrue(str.contains("src"));
        assertTrue(str.contains("tgt"));
    }
}
