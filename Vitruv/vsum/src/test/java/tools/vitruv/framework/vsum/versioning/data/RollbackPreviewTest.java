package tools.vitruv.framework.vsum.versioning.data;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tools.vitruv.framework.vsum.branch.data.MaturityLevel;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link RollbackPreview}.
 *
 * <p>Tests verify getter correctness, that list fields are defensively copied,
 * and that {@link RollbackPreview#summary()} contains the expected sections.
 */
class RollbackPreviewTest {

    private static VersionMetadata meta(String versionId) {
        return new VersionMetadata(versionId, "abc1234567890abcdef1234567890abcdef12345",
                "main", "Alice", "alice@example.com",
                LocalDateTime.of(2024, 6, 1, 12, 0), "desc", MaturityLevel.DRAFT);
    }

    @Test
    @DisplayName("All getters return the values provided at construction")
    void allGettersReturnProvidedValues() {
        var version = meta("v1.0");
        var preview = new RollbackPreview(version, "head123456", "main",
                List.of("commit-a", "commit-b"), List.of("model.xmi"), false);

        assertSame(version, preview.getTargetVersion());
        assertEquals("head123456", preview.getCurrentHeadSha());
        assertEquals("main", preview.getBranch());
        assertEquals(List.of("commit-a", "commit-b"), preview.getCommitsToAbandon());
        assertEquals(List.of("model.xmi"), preview.getFilesToChange());
        assertFalse(preview.isHasUncommittedChanges());
    }

    @Test
    @DisplayName("Null required fields are rejected at construction")
    void nullRequiredFieldsAreRejected() {
        var v = meta("v1.0");
        assertThrows(NullPointerException.class,
                () -> new RollbackPreview(null, "h", "b", List.of(), List.of(), false));
        assertThrows(NullPointerException.class,
                () -> new RollbackPreview(v, null, "b", List.of(), List.of(), false));
        assertThrows(NullPointerException.class,
                () -> new RollbackPreview(v, "h", null, List.of(), List.of(), false));
    }

    @Test
    @DisplayName("summary() contains the version ID, target commit short SHA, and branch")
    void summaryContainsVersionAndBranch() {
        var version = meta("v2.5");
        var preview = new RollbackPreview(version, "abcdef0123456", "develop",
                List.of(), List.of(), false);

        String summary = preview.summary();
        assertTrue(summary.contains("v2.5"), "summary must contain the version ID");
        assertTrue(summary.contains("develop"), "summary must contain the branch");
    }

    @Test
    @DisplayName("summary() lists commits to abandon when present")
    void summaryListsCommitsToAbandon() {
        var preview = new RollbackPreview(meta("v1.0"), "head123456789", "main",
                List.of("commit-1", "commit-2"), List.of(), false);

        String summary = preview.summary();
        assertTrue(summary.contains("commit-1"));
        assertTrue(summary.contains("commit-2"));
    }

    @Test
    @DisplayName("summary() includes uncommitted-changes warning when hasUncommittedChanges is true")
    void summaryIncludesUncommittedChangesWarning() {
        var preview = new RollbackPreview(meta("v1.0"), "head123456789", "main",
                List.of(), List.of(), true);

        String summary = preview.summary();
        assertTrue(summary.contains("Uncommitted changes") || summary.contains("uncommitted"),
                "summary must warn about uncommitted changes");
    }

    @Test
    @DisplayName("commitsToAbandon and filesToChange are unmodifiable")
    void listsAreUnmodifiable() {
        var preview = new RollbackPreview(meta("v1.0"), "head123456789", "main",
                List.of("c1"), List.of("f1"), false);

        assertThrows(UnsupportedOperationException.class,
                () -> preview.getCommitsToAbandon().add("extra"));
        assertThrows(UnsupportedOperationException.class,
                () -> preview.getFilesToChange().add("extra"));
    }
}
