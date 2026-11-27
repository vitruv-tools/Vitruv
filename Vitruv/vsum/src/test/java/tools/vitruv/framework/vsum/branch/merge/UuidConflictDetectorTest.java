package tools.vitruv.framework.vsum.branch.merge;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link UuidConflictDetector}.
 *
 * <p>Tests verify the three conflict rules: MODIFY_MODIFY (same UUID+feature, different values),
 * DELETE_MODIFY (ours deleted, theirs modified), and MODIFY_DELETE (theirs deleted, ours modified).
 * Edge cases include identical value changes (no conflict), cascade-deleted UUIDs, and
 * CreateEObject changes (which must not participate in delete conflicts).
 */
class UuidConflictDetectorTest {

    private final UuidConflictDetector detector = new UuidConflictDetector();

    // ── helpers ──────────────────────────────────────────────────────────────

    private static SemanticChangeLog.ChangeDto replace(String uuid, String feature,
            Object oldVal, Object newVal) {
        var dto = new SemanticChangeLog.ChangeDto();
        dto.changeType = "ReplaceSingleValuedEAttribute";
        dto.affectedElementUuid = uuid;
        dto.affectedElementId = uuid + "-hid";
        dto.featureName = feature;
        dto.oldLiteralValue = oldVal;
        dto.newLiteralValue = newVal;
        return dto;
    }

    private static SemanticChangeLog.ChangeDto delete(String uuid) {
        var dto = new SemanticChangeLog.ChangeDto();
        dto.changeType = "DeleteEObject";
        dto.affectedElementUuid = uuid;
        dto.affectedElementId = uuid + "-hid";
        return dto;
    }

    private static SemanticChangeLog.ChangeDto create(String uuid) {
        var dto = new SemanticChangeLog.ChangeDto();
        dto.changeType = "CreateEObject";
        dto.affectedElementUuid = uuid;
        dto.affectedElementId = uuid + "-hid";
        return dto;
    }

    private static SemanticChangeLog.ChangeDto anyModify(String uuid) {
        var dto = new SemanticChangeLog.ChangeDto();
        dto.changeType = "InsertEReference";
        dto.affectedElementUuid = uuid;
        dto.affectedElementId = uuid + "-hid";
        return dto;
    }

    // ── empty / no-overlap ───────────────────────────────────────────────────

    @Test
    @DisplayName("Empty DTO lists produce no conflicts")
    void emptyListsProduceNoConflicts() {
        assertTrue(detector.detectConflicts(List.of(), List.of()).isEmpty());
    }

    @Test
    @DisplayName("Non-overlapping changes produce no conflicts")
    void nonOverlappingChangesProduceNoConflicts() {
        var ours = List.of(replace("uuid-1", "name", "old", "new-a"));
        var theirs = List.of(replace("uuid-2", "name", "old", "new-b"));
        assertTrue(detector.detectConflicts(ours, theirs).isEmpty());
    }

    // ── MODIFY_MODIFY ────────────────────────────────────────────────────────

    @Test
    @DisplayName("MODIFY_MODIFY detected when both branches change the same UUID+feature to different values")
    void modifyModifyDetected() {
        var ours = List.of(replace("uuid-1", "name", "base", "ours-value"));
        var theirs = List.of(replace("uuid-1", "name", "base", "theirs-value"));

        List<MergeConflict> conflicts = detector.detectConflicts(ours, theirs);

        assertEquals(1, conflicts.size());
        assertEquals(MergeConflict.ConflictType.MODIFY_MODIFY, conflicts.get(0).getType());
        assertEquals("uuid-1", conflicts.get(0).getElementUuid());
        assertEquals("name", conflicts.get(0).getConflictingFeature());
    }

    @Test
    @DisplayName("No MODIFY_MODIFY conflict when both branches set the same value (idempotent change)")
    void noConflictWhenBothSetSameValue() {
        var ours = List.of(replace("uuid-1", "name", "old", "same-value"));
        var theirs = List.of(replace("uuid-1", "name", "old", "same-value"));
        assertTrue(detector.detectConflicts(ours, theirs).isEmpty());
    }

    @Test
    @DisplayName("MODIFY_MODIFY on different features of the same element are independent conflicts")
    void modifyModifyOnDifferentFeaturesAreIndependent() {
        var ours = List.of(
                replace("uuid-1", "name", "old", "a"),
                replace("uuid-1", "age", "0", "1"));
        var theirs = List.of(
                replace("uuid-1", "name", "old", "b"),
                replace("uuid-1", "age", "0", "99"));

        List<MergeConflict> conflicts = detector.detectConflicts(ours, theirs);
        assertEquals(2, conflicts.size());
    }

    @Test
    @DisplayName("Only ReplaceSingleValued changes participate in MODIFY_MODIFY detection")
    void onlyReplaceSingleValuedCausesModifyModify() {
        // InsertEReference is not a ReplaceSingleValued change, so it should not conflict
        var ours = List.of(anyModify("uuid-1"));
        var theirs = List.of(anyModify("uuid-1"));
        assertTrue(detector.detectConflicts(ours, theirs).isEmpty());
    }

    // ── DELETE_MODIFY ────────────────────────────────────────────────────────

    @Test
    @DisplayName("DELETE_MODIFY detected when ours deletes and theirs modifies the same element")
    void deleteModifyDetected() {
        var ours = List.of(delete("uuid-1"));
        var theirs = List.of(anyModify("uuid-1"));

        List<MergeConflict> conflicts = detector.detectConflicts(ours, theirs);

        assertEquals(1, conflicts.size());
        assertEquals(MergeConflict.ConflictType.DELETE_MODIFY, conflicts.get(0).getType());
    }

    @Test
    @DisplayName("MODIFY_DELETE detected when theirs deletes and ours modifies the same element")
    void modifyDeleteDetected() {
        var ours = List.of(anyModify("uuid-1"));
        var theirs = List.of(delete("uuid-1"));

        List<MergeConflict> conflicts = detector.detectConflicts(ours, theirs);

        assertEquals(1, conflicts.size());
        assertEquals(MergeConflict.ConflictType.MODIFY_DELETE, conflicts.get(0).getType());
    }

    @Test
    @DisplayName("CreateEObject changes do not trigger DELETE_MODIFY or MODIFY_DELETE")
    void createChangesDoNotTriggerDeleteConflict() {
        // Even if one branch deletes uuid-1 and the other 'creates' uuid-1, that is not a conflict:
        // new elements have no history in the ancestor.
        var ours = List.of(delete("uuid-1"));
        var theirs = List.of(create("uuid-1"));
        assertTrue(detector.detectConflicts(ours, theirs).isEmpty(),
                "Create on the same UUID after deletion is not a modify conflict");
    }

    @Test
    @DisplayName("Cascade-deleted UUIDs on a delete DTO cause DELETE_MODIFY for children")
    void cascadeDeletedUuidsCauseConflict() {
        var delDto = delete("parent-uuid");
        delDto.cascadeDeletedUuids = List.of("child-uuid");

        var ours = List.of(delDto);
        var theirs = List.of(anyModify("child-uuid"));

        List<MergeConflict> conflicts = detector.detectConflicts(ours, theirs);

        assertEquals(1, conflicts.size());
        assertEquals(MergeConflict.ConflictType.DELETE_MODIFY, conflicts.get(0).getType());
    }

    @Test
    @DisplayName("DTO with null UUID is skipped in all detection paths")
    void dtoWithNullUuidIsSkipped() {
        var nullUuid = new SemanticChangeLog.ChangeDto();
        nullUuid.changeType = "ReplaceSingleValuedEAttribute";
        nullUuid.featureName = "name";
        nullUuid.newLiteralValue = "x";
        // affectedElementUuid is null

        assertTrue(detector.detectConflicts(List.of(nullUuid), List.of(nullUuid)).isEmpty());
    }
}
