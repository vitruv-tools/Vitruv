package tools.vitruv.framework.vsum.branch.merge;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link MergeConflict} and its two constructors.
 *
 * <p>Tests cover null rejection, both the EChange-based and UUID-based construction
 * paths, getter correctness for each path, and the toString output.
 */
class MergeConflictTest {

    @Test
    @DisplayName("Null elementId is rejected by both constructors")
    void nullElementIdIsRejected() {
        assertThrows(NullPointerException.class,
                () -> new MergeConflict(null, MergeConflict.ConflictType.MODIFY_MODIFY,
                        List.of(), List.of()));
        assertThrows(NullPointerException.class,
                () -> new MergeConflict(null, MergeConflict.ConflictType.MODIFY_MODIFY,
                        "uuid-1", "name", "base", "ours", "theirs"));
    }

    @Test
    @DisplayName("Null type is rejected by both constructors")
    void nullTypeIsRejected() {
        assertThrows(NullPointerException.class,
                () -> new MergeConflict("el-id", null, List.of(), List.of()));
        assertThrows(NullPointerException.class,
                () -> new MergeConflict("el-id", null,
                        "uuid-1", "name", "base", "ours", "theirs"));
    }

    @Test
    @DisplayName("EChange-based constructor stores type and elementId; UUID fields are null")
    void eChangeBasedConstructorStoresCorrectly() {
        var conflict = new MergeConflict("el-hid-1", MergeConflict.ConflictType.DELETE_MODIFY,
                List.of(), List.of());

        assertEquals("el-hid-1", conflict.getElementId());
        assertEquals(MergeConflict.ConflictType.DELETE_MODIFY, conflict.getType());
        assertTrue(conflict.getOursChanges().isEmpty());
        assertTrue(conflict.getTheirsChanges().isEmpty());
        assertNull(conflict.getElementUuid(), "UUID fields must be null for EChange-based conflicts");
        assertNull(conflict.getConflictingFeature());
    }

    @Test
    @DisplayName("UUID-based constructor stores all value fields; change lists are empty")
    void uuidBasedConstructorStoresCorrectly() {
        var conflict = new MergeConflict("el-hid-1", MergeConflict.ConflictType.MODIFY_MODIFY,
                "uuid-A", "name", "base-value", "ours-value", "theirs-value");

        assertEquals("el-hid-1", conflict.getElementId());
        assertEquals(MergeConflict.ConflictType.MODIFY_MODIFY, conflict.getType());
        assertEquals("uuid-A", conflict.getElementUuid());
        assertEquals("name", conflict.getConflictingFeature());
        assertEquals("base-value", conflict.getBaseValue());
        assertEquals("ours-value", conflict.getOursValue());
        assertEquals("theirs-value", conflict.getTheirsValue());
        assertTrue(conflict.getOursChanges().isEmpty());
        assertTrue(conflict.getTheirsChanges().isEmpty());
    }

    @Test
    @DisplayName("toString includes UUID and feature for UUID-based conflicts")
    void uuidBasedToStringIncludesUuidAndFeature() {
        var conflict = new MergeConflict("el-hid", MergeConflict.ConflictType.MODIFY_MODIFY,
                "uuid-X", "age", "0", "1", "2");
        String str = conflict.toString();
        assertTrue(str.contains("uuid-X"), "toString must include UUID");
        assertTrue(str.contains("age"), "toString must include feature name");
    }

    @Test
    @DisplayName("toString includes element id and change counts for EChange-based conflicts")
    void eChangeBasedToStringIncludesElementId() {
        var conflict = new MergeConflict("el-hid", MergeConflict.ConflictType.DELETE_MODIFY,
                List.of(), List.of());
        String str = conflict.toString();
        assertTrue(str.contains("el-hid"), "toString must include element id");
        assertTrue(str.contains("DELETE_MODIFY"), "toString must include conflict type");
    }

    @Test
    @DisplayName("All ConflictType values are accessible and distinct")
    void allConflictTypesAreDistinct() {
        var types = MergeConflict.ConflictType.values();
        assertTrue(types.length >= 5, "at least 5 conflict types must exist");
    }
}
