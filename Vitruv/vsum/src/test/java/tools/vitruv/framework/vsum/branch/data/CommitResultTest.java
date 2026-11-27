package tools.vitruv.framework.vsum.branch.data;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link CommitResult}.
 *
 * <p>Tests verify that all getters return the values provided at construction,
 * that null required fields are rejected, that {@code stagedFiles} is defensively
 * copied, and that the {@code hasModelChanges} flag is correctly stored.
 */
class CommitResultTest {

    private static final String SHA = "abc1234567890abcdef1234567890abcdef12345";
    private static final String BRANCH = "main";
    private static final String AUTHOR_NAME = "Alice";
    private static final String AUTHOR_EMAIL = "alice@example.com";
    private static final LocalDateTime DATE = LocalDateTime.of(2024, 3, 1, 10, 0);

    private CommitResult buildResult(boolean hasModelChanges) {
        return new CommitResult(SHA, BRANCH, AUTHOR_NAME, AUTHOR_EMAIL,
                DATE, List.of("model/User.xmi"), hasModelChanges);
    }

    @Test
    @DisplayName("All getters return the values provided at construction")
    void allGettersReturnProvidedValues() {
        var result = buildResult(true);

        assertEquals(SHA, result.getCommitSha());
        assertEquals(BRANCH, result.getBranch());
        assertEquals(AUTHOR_NAME, result.getAuthorName());
        assertEquals(AUTHOR_EMAIL, result.getAuthorEmail());
        assertEquals(DATE, result.getAuthorDate());
        assertEquals(List.of("model/User.xmi"), result.getStagedFiles());
        assertTrue(result.isHasModelChanges());
    }

    @Test
    @DisplayName("hasModelChanges is false when no model files were staged")
    void hasModelChangesIsFalseWhenNoModelFiles() {
        var result = new CommitResult(SHA, BRANCH, AUTHOR_NAME, AUTHOR_EMAIL,
                DATE, List.of("README.md"), false);
        assertFalse(result.isHasModelChanges());
    }

    @Test
    @DisplayName("Null commit SHA is rejected at construction time")
    void nullCommitShaIsRejected() {
        assertThrows(NullPointerException.class,
                () -> new CommitResult(null, BRANCH, AUTHOR_NAME, AUTHOR_EMAIL,
                        DATE, List.of(), true));
    }

    @Test
    @DisplayName("Null branch is rejected at construction time")
    void nullBranchIsRejected() {
        assertThrows(NullPointerException.class,
                () -> new CommitResult(SHA, null, AUTHOR_NAME, AUTHOR_EMAIL,
                        DATE, List.of(), true));
    }

    @Test
    @DisplayName("Null author name is rejected at construction time")
    void nullAuthorNameIsRejected() {
        assertThrows(NullPointerException.class,
                () -> new CommitResult(SHA, BRANCH, null, AUTHOR_EMAIL,
                        DATE, List.of(), true));
    }

    @Test
    @DisplayName("Null author email is rejected at construction time")
    void nullAuthorEmailIsRejected() {
        assertThrows(NullPointerException.class,
                () -> new CommitResult(SHA, BRANCH, AUTHOR_NAME, null,
                        DATE, List.of(), true));
    }

    @Test
    @DisplayName("Null authorDate is rejected at construction time")
    void nullAuthorDateIsRejected() {
        assertThrows(NullPointerException.class,
                () -> new CommitResult(SHA, BRANCH, AUTHOR_NAME, AUTHOR_EMAIL,
                        null, List.of(), true));
    }

    @Test
    @DisplayName("stagedFiles list is defensively copied and unmodifiable")
    void stagedFilesIsDefensivelyCopied() {
        var mutableList = new ArrayList<String>();
        mutableList.add("model/A.xmi");
        var result = new CommitResult(SHA, BRANCH, AUTHOR_NAME, AUTHOR_EMAIL,
                DATE, mutableList, false);
        mutableList.add("model/B.xmi"); // mutation after construction

        assertEquals(1, result.getStagedFiles().size(),
                "mutation of the source list must not affect the stored list");
        assertThrows(UnsupportedOperationException.class,
                () -> result.getStagedFiles().add("model/C.xmi"),
                "the stored staged files list must be unmodifiable");
    }
}
