package tools.vitruv.framework.vsum.branch.data;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tools.vitruv.framework.vsum.branch.storage.ChangeOrigin;
import tools.vitruv.framework.vsum.branch.storage.SemanticChangeEntry;
import tools.vitruv.framework.vsum.branch.storage.SemanticChangeType;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link UpdateConflict}.
 *
 * <p>Tests verify constructor null rejection, getter correctness,
 * that severity is always MEDIUM, the preferred-entry logic
 * ({@code isOriginalVsConsequential}, {@code getPreferredEntry},
 * {@code getPreferredBranch}), and equals/hashCode semantics.
 */
class UpdateConflictTest {

    private static SemanticChangeEntry entry(String uuid, String origin) {
        return SemanticChangeEntry.builder()
                .index(0).changeType(SemanticChangeType.ATTRIBUTE_CHANGED)
                .emfType("ReplaceSingleValuedEAttribute")
                .elementUuid(uuid).feature("name")
                .changeOrigin(origin).build();
    }

    private UpdateConflict conflict(String srcOrigin, String tgtOrigin) {
        return new UpdateConflict("uuid-1", "Entity", "name",
                "feature-src", "feature-tgt",
                entry("uuid-1", srcOrigin), entry("uuid-1", tgtOrigin));
    }

    @Test
    @DisplayName("All getters return the values provided at construction")
    void allGettersReturnProvidedValues() {
        var src = entry("uuid-1", "original");
        var tgt = entry("uuid-1", "consequential");
        var uc = new UpdateConflict("uuid-1", "Entity", "name", "src", "tgt", src, tgt);

        assertEquals("uuid-1", uc.getElementUuid());
        assertEquals("Entity", uc.getEClass());
        assertEquals("name", uc.getFeatureName());
        assertEquals("src", uc.getSourceBranch());
        assertEquals("tgt", uc.getTargetBranch());
        assertSame(src, uc.getSourceEntry());
        assertSame(tgt, uc.getTargetEntry());
    }

    @Test
    @DisplayName("Null elementUuid is rejected")
    void nullUuidIsRejected() {
        assertThrows(NullPointerException.class,
                () -> new UpdateConflict(null, "E", "f", "s", "t",
                        entry("u", "original"), entry("u", "original")));
    }

    @Test
    @DisplayName("Null featureName is rejected")
    void nullFeatureNameIsRejected() {
        assertThrows(NullPointerException.class,
                () -> new UpdateConflict("u", "E", null, "s", "t",
                        entry("u", "original"), entry("u", "original")));
    }

    @Test
    @DisplayName("Null sourceBranch is rejected")
    void nullSourceBranchIsRejected() {
        assertThrows(NullPointerException.class,
                () -> new UpdateConflict("u", "E", "f", null, "t",
                        entry("u", "original"), entry("u", "original")));
    }

    @Test
    @DisplayName("Null targetBranch is rejected")
    void nullTargetBranchIsRejected() {
        assertThrows(NullPointerException.class,
                () -> new UpdateConflict("u", "E", "f", "s", null,
                        entry("u", "original"), entry("u", "original")));
    }

    @Test
    @DisplayName("getSeverity always returns MEDIUM")
    void severityIsAlwaysMedium() {
        assertEquals(ConflictSeverity.MEDIUM, conflict("original", "original").getSeverity());
        assertEquals(ConflictSeverity.MEDIUM, conflict("consequential", "original").getSeverity());
    }

    @Test
    @DisplayName("isOriginalVsConsequential is true when sides have different origins")
    void isOriginalVsConsequentialMixed() {
        assertTrue(conflict("original", "consequential").isOriginalVsConsequential());
        assertTrue(conflict("consequential", "original").isOriginalVsConsequential());
    }

    @Test
    @DisplayName("isOriginalVsConsequential is false when both sides have the same origin")
    void isOriginalVsConsequentialSameOrigin() {
        assertFalse(conflict("original", "original").isOriginalVsConsequential());
        assertFalse(conflict("consequential", "consequential").isOriginalVsConsequential());
    }

    @Test
    @DisplayName("getPreferredEntry returns the ORIGINAL entry when origins differ")
    void preferredEntryIsOriginalSide() {
        var src = entry("uuid-1", "original");
        var tgt = entry("uuid-1", "consequential");
        var uc = new UpdateConflict("uuid-1", "E", "f", "src", "tgt", src, tgt);

        assertSame(src, uc.getPreferredEntry(), "source is ORIGINAL, so it must be preferred");
        assertEquals("src", uc.getPreferredBranch());
    }

    @Test
    @DisplayName("getPreferredEntry returns null when both entries have the same origin")
    void preferredEntryIsNullForSameOrigin() {
        assertNull(conflict("original", "original").getPreferredEntry());
        assertNull(conflict("original", "original").getPreferredBranch());
    }

    @Test
    @DisplayName("getPreferredBranch returns the target branch when target entry is ORIGINAL")
    void preferredBranchIsTargetWhenTargetIsOriginal() {
        var src = entry("uuid-1", "consequential");
        var tgt = entry("uuid-1", "original");
        var uc = new UpdateConflict("uuid-1", "E", "f", "src", "tgt", src, tgt);
        assertEquals("tgt", uc.getPreferredBranch());
    }

    @Test
    @DisplayName("equals uses elementUuid, featureName, sourceBranch, and targetBranch")
    void equalsUsesKeyFields() {
        var src = entry("u", "original");
        var tgt = entry("u", "consequential");
        var a = new UpdateConflict("u", "E", "f", "s", "t", src, tgt);
        var b = new UpdateConflict("u", "E", "f", "s", "t", src, tgt);
        var c = new UpdateConflict("u", "E", "f", "s", "t2", src, tgt);

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
    }
}
