package tools.vitruv.framework.vsum.branch.data;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link SemanticChangelog}.
 */
class SemanticChangelogTest {

    private static final String COMMIT_SHA = "abc1234567890abcdef1234567890abcdef12345";
    private static final String AUTHOR = "Jane Doe <jane@example.com>";
    private static final String COMMITTER = "Bob Smith <bob@example.com>";
    private static final LocalDateTime NOW = LocalDateTime.now();
    private static final String MESSAGE = "Add initial commit";
    private static final String BRANCH = "featureX";

    @Test
    @DisplayName("Author and Committer are identical")
    void testAuthorEqualsCommitter() {
        List<FileChange> changes = List.of(new FileChange("models/User.java", FileOperation.MODIFIED));
        SemanticChangelog changelog = SemanticChangelog.create(COMMIT_SHA, AUTHOR, NOW, MESSAGE, BRANCH, changes);
        assertEquals(COMMIT_SHA, changelog.getCommitSha());
        assertEquals(AUTHOR, changelog.getAuthor());
        assertEquals(NOW, changelog.getAuthorDate());
        assertEquals(AUTHOR, changelog.getCommitter());
        assertEquals(NOW, changelog.getCommitDate());
        assertEquals(MESSAGE, changelog.getMessage());
        assertEquals(BRANCH, changelog.getBranch());
        assertEquals(1, changelog.getChanges().size());
    }

    @Test
    @DisplayName("Author and Commiter are different")
    void testBuilder_DifferentCommitter() {
        List<FileChange> changes = List.of(new FileChange("models/User.java", FileOperation.MODIFIED));
        LocalDateTime commitDate = NOW.plusHours(1);
        SemanticChangelog changelog = SemanticChangelog.builder()
                .commitSha(COMMIT_SHA)
                .author(AUTHOR)
                .authorDate(NOW)
                .committer(COMMITTER)
                .commitDate(commitDate)
                .message(MESSAGE)
                .branch(BRANCH)
                .changes(changes)
                .build();
        assertEquals(COMMIT_SHA, changelog.getCommitSha());
        assertEquals(AUTHOR, changelog.getAuthor());
        assertEquals(NOW, changelog.getAuthorDate());
        assertEquals(COMMITTER, changelog.getCommitter());
        assertEquals(commitDate, changelog.getCommitDate());
        assertNotEquals(AUTHOR, COMMITTER);
    }

    @Test
    void testIsAuthorDifferentFromCommitter_Same() {
        SemanticChangelog changelog = SemanticChangelog.create(COMMIT_SHA, AUTHOR, NOW, MESSAGE, BRANCH, List.of());
        assertFalse(changelog.isAuthorDifferentFromCommitter());
    }

    @Test
    void testIsAuthorDifferentFromCommitter_Different() {
        SemanticChangelog changelog = SemanticChangelog.builder()
                .commitSha(COMMIT_SHA)
                .author(AUTHOR)
                .authorDate(NOW)
                .committer(COMMITTER)
                .commitDate(NOW)
                .message(MESSAGE)
                .branch(BRANCH)
                .changes(List.of())
                .build();
        assertTrue(changelog.isAuthorDifferentFromCommitter());
    }

    @Test
    void testHasChanges_Empty() {
        SemanticChangelog changelog = SemanticChangelog.create(COMMIT_SHA, AUTHOR, NOW, MESSAGE, BRANCH, List.of());
        assertFalse(changelog.hasChanges());
    }

    @Test
    void testHasChanges_NotEmpty() {
        List<FileChange> changes = List.of(new FileChange("models/User.java", FileOperation.MODIFIED));
        SemanticChangelog changelog = SemanticChangelog.create(COMMIT_SHA, AUTHOR, NOW, MESSAGE, BRANCH, changes);
        assertTrue(changelog.hasChanges());
    }

    @Test
    void testToString() {
        SemanticChangelog changelog = SemanticChangelog.create(COMMIT_SHA, AUTHOR, NOW, MESSAGE, BRANCH, List.of());
        String str = changelog.toString();
        assertTrue(str.contains("abc1234"));
        assertTrue(str.contains(BRANCH));
        assertTrue(str.contains(AUTHOR));
    }

    @Test
    void testEquals() {
        List<FileChange> changes = List.of(new FileChange("models/User.java", FileOperation.MODIFIED));
        SemanticChangelog changelog1 = SemanticChangelog.create(COMMIT_SHA, AUTHOR, NOW, MESSAGE, BRANCH, changes);
        SemanticChangelog changelog2 = SemanticChangelog.create(COMMIT_SHA, AUTHOR, NOW, MESSAGE, BRANCH, changes);
        assertEquals(changelog1, changelog2);
    }

    @Test
    void testHashCode() {
        List<FileChange> changes = List.of(new FileChange("models/User.java", FileOperation.MODIFIED));
        SemanticChangelog changelog1 = SemanticChangelog.create(COMMIT_SHA, AUTHOR, NOW, MESSAGE, BRANCH, changes);
        SemanticChangelog changelog2 = SemanticChangelog.create(COMMIT_SHA, AUTHOR, NOW, MESSAGE, BRANCH, changes);
        assertEquals(changelog1.hashCode(), changelog2.hashCode());
    }
}