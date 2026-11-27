package tools.vitruv.framework.vsum.branch.data;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tools.vitruv.framework.vsum.branch.storage.SemanticChangeEntry;
import tools.vitruv.framework.vsum.branch.storage.SemanticChangeType;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link SemanticConflict}.
 *
 * <p>Tests verify constructor null rejection, that feature may be null for lifecycle
 * conflicts, equals/hashCode based on uuid+feature+severity, and toString content.
 */
class SemanticConflictTest {

    private static SemanticChangeEntry entry(String uuid) {
        return SemanticChangeEntry.builder()
                .index(0).changeType(SemanticChangeType.ATTRIBUTE_CHANGED)
                .emfType("ReplaceSingleValuedEAttribute")
                .elementUuid(uuid).feature("name").build();
    }

    @Test
    @DisplayName("All getters return the values provided at construction")
    void allGettersReturnProvidedValues() {
        var eA = entry("u1");
        var eB = entry("u1");
        var conflict = new SemanticConflict("u1", "name", eA, eB, ConflictSeverity.MEDIUM);

        assertEquals("u1", conflict.getElementUuid());
        assertEquals("name", conflict.getFeature());
        assertSame(eA, conflict.getChangeOnBranchA());
        assertSame(eB, conflict.getChangeOnBranchB());
        assertEquals(ConflictSeverity.MEDIUM, conflict.getSeverity());
    }

    @Test
    @DisplayName("Null elementUuid is rejected")
    void nullUuidIsRejected() {
        assertThrows(NullPointerException.class,
                () -> new SemanticConflict(null, "name", entry("u"), entry("u"), ConflictSeverity.MEDIUM));
    }

    @Test
    @DisplayName("Null changeOnBranchA is rejected")
    void nullChangeAIsRejected() {
        assertThrows(NullPointerException.class,
                () -> new SemanticConflict("u", "name", null, entry("u"), ConflictSeverity.MEDIUM));
    }

    @Test
    @DisplayName("Null changeOnBranchB is rejected")
    void nullChangeBIsRejected() {
        assertThrows(NullPointerException.class,
                () -> new SemanticConflict("u", "name", entry("u"), null, ConflictSeverity.MEDIUM));
    }

    @Test
    @DisplayName("Null severity is rejected")
    void nullSeverityIsRejected() {
        assertThrows(NullPointerException.class,
                () -> new SemanticConflict("u", "name", entry("u"), entry("u"), null));
    }

    @Test
    @DisplayName("Feature may be null for lifecycle conflicts")
    void nullFeatureIsAllowedForLifecycleConflicts() {
        var conflict = new SemanticConflict("u", null, entry("u"), entry("u"), ConflictSeverity.HIGH);
        assertNull(conflict.getFeature());
    }

    @Test
    @DisplayName("equals is based on elementUuid, feature, and severity")
    void equalsBasedOnKeyFields() {
        var e = entry("u1");
        var a = new SemanticConflict("u1", "name", e, e, ConflictSeverity.MEDIUM);
        var b = new SemanticConflict("u1", "name", e, e, ConflictSeverity.MEDIUM);
        var c = new SemanticConflict("u1", "name", e, e, ConflictSeverity.HIGH);

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c, "different severity must produce a different result");
    }

    @Test
    @DisplayName("toString includes elementUuid, feature, and severity")
    void toStringIncludesKeyFields() {
        var e = entry("u1");
        var conflict = new SemanticConflict("u1", "name", e, e, ConflictSeverity.HIGH);
        String str = conflict.toString();
        assertTrue(str.contains("u1"));
        assertTrue(str.contains("name"));
        assertTrue(str.contains("HIGH"));
    }
}
