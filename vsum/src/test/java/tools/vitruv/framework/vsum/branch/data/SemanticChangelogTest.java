package tools.vitruv.framework.vsum.branch.data;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link SemanticChangelog}.
 *
 * <p>The tests cover the two construction paths ({@link SemanticChangelog#create} for the common case and {@link SemanticChangelog#builder()} for custom committers),
 * the predicate methods, input validation, the {@code equals}/{@code hashCode}/{@code toString} contracts, and the {@link SemanticChangelog#writeTo} file output.
 */
class SemanticChangelogTest {

    private static final String COMMIT_SHA = "abc1234567890abcdef1234567890abcdef12345";
    private static final String AUTHOR = "Alice Müller <alice@example.com>";
    private static final String COMMITTER = "Bob Smith <bob@example.com>";
    private static final LocalDateTime NOW = LocalDateTime.now();
    private static final String MESSAGE = "Add initial commit";
    private static final String BRANCH = "feature-x";

    /**
     * Verifies that {@link SemanticChangelog#create} correctly sets all fields and automatically assigns the committer and commit date to match the author.
     * This is the primary path for direct commits where no cherry-pick or rebase has taken place.
     */
    @Test
    @DisplayName("create() stores all fields and sets committer equal to author")
    void createStoresAllFieldsAndSetsCommitterToAuthor() {
        var changes = List.of(new FileChange("models/User.java", FileOperation.MODIFIED));

        var changelog = SemanticChangelog.create(COMMIT_SHA, AUTHOR, NOW, MESSAGE, BRANCH, changes);

        // all provided values must be accessible through their getters.
        assertEquals(COMMIT_SHA, changelog.getCommitSha());
        assertEquals(AUTHOR, changelog.getAuthor());
        assertEquals(NOW, changelog.getAuthorDate());
        assertEquals(MESSAGE, changelog.getMessage());
        assertEquals(BRANCH, changelog.getBranch());
        assertEquals(1, changelog.getChanges().size());
        // the committer and commit date must be derived from the author fields.
        assertEquals(AUTHOR, changelog.getCommitter(), "committer must equal author for a direct commit");
        assertEquals(NOW, changelog.getCommitDate(), "commit date must equal author date for a direct commit");
    }

    /**
     * Verifies that the builder correctly stores a separate committer and commit date.
     * This is the path used for commits that were cherry-picked, rebased, or applied from a patch, where the original author identity must be preserved.
     */
    @Test
    @DisplayName("builder() stores a separate committer and commit date")
    void builderStoresSeparateCommitter() {
        var commitDate = NOW.plusHours(1);
        var changes = List.of(new FileChange("models/User.java", FileOperation.MODIFIED));

        var changelog = SemanticChangelog.builder()
                .commitSha(COMMIT_SHA)
                .author(AUTHOR)
                .authorDate(NOW)
                .committer(COMMITTER)
                .commitDate(commitDate)
                .message(MESSAGE)
                .branch(BRANCH)
                .changes(changes)
                .build();

        assertEquals(AUTHOR, changelog.getAuthor());
        assertEquals(COMMITTER, changelog.getCommitter(), "committer must be stored independently of the author");
        assertEquals(commitDate, changelog.getCommitDate(), "commit date must be stored independently of the author date");
        // the predicate must agree with the stored values.
        assertTrue(changelog.isAuthorDifferentFromCommitter());
    }

    /**
     * Verifies that {@link SemanticChangelog#isAuthorDifferentFromCommitter()} returns false for direct commits and true when a different committer was explicitly set.
     */
    @Test
    @DisplayName("isAuthorDifferentFromCommitter returns false for direct commits and true when committer differs")
    void isAuthorDifferentFromCommitterReflectsRelationship() {
        var direct = SemanticChangelog.create(COMMIT_SHA, AUTHOR, NOW, MESSAGE, BRANCH, List.of());
        assertFalse(direct.isAuthorDifferentFromCommitter(), "a direct commit must report author and committer as the same");

        var cherryPicked = SemanticChangelog.builder()
                .commitSha(COMMIT_SHA).author(AUTHOR).authorDate(NOW)
                .committer(COMMITTER).commitDate(NOW)
                .message(MESSAGE).branch(BRANCH).changes(List.of())
                .build();
        assertTrue(cherryPicked.isAuthorDifferentFromCommitter(), "a cherry-picked commit must report author and committer as different");
    }

    /**
     * Verifies that {@link SemanticChangelog#hasChanges()} returns false for an empty change list (such as an empty commit) and true when at least one change is present.
     */
    @Test
    @DisplayName("hasChanges returns false for an empty change list and true when changes are present")
    void hasChangesReflectsChangeListContent() {
        var empty = SemanticChangelog.create(COMMIT_SHA, AUTHOR, NOW, MESSAGE, BRANCH, List.of());
        assertFalse(empty.hasChanges(), "a changelog with no file changes must report hasChanges() as false");

        var withChanges = SemanticChangelog.create(COMMIT_SHA, AUTHOR, NOW, MESSAGE, BRANCH, List.of(new FileChange("models/User.java", FileOperation.MODIFIED)));
        assertTrue(withChanges.hasChanges(), "a changelog with file changes must report hasChanges() as true");
    }

    /**
     * Verifies that passing null or blank values for required fields throws an {@link IllegalArgumentException}.
     * This protects downstream consumers that read the changelog fields without null checks.
     */
    @Test
    @DisplayName("create() rejects null and blank values for required string fields")
    void createRejectsNullAndBlankFields() {
        // each required string field must be individually validated at construction time.
        assertThrows(IllegalArgumentException.class, () -> SemanticChangelog.create(null, AUTHOR, NOW, MESSAGE, BRANCH, List.of()), "null commit SHA must be rejected");
        assertThrows(IllegalArgumentException.class, () -> SemanticChangelog.create("  ", AUTHOR, NOW, MESSAGE, BRANCH, List.of()), "blank commit SHA must be rejected");
        assertThrows(IllegalArgumentException.class, () -> SemanticChangelog.create(COMMIT_SHA, null, NOW, MESSAGE, BRANCH, List.of()), "null author must be rejected");
        assertThrows(IllegalArgumentException.class, () -> SemanticChangelog.create(COMMIT_SHA, AUTHOR, null, MESSAGE, BRANCH, List.of()), "null author date must be rejected");
        assertThrows(IllegalArgumentException.class, () -> SemanticChangelog.create(COMMIT_SHA, AUTHOR, NOW, MESSAGE, null, List.of()), "null branch must be rejected");
        assertThrows(IllegalArgumentException.class, () -> SemanticChangelog.create(COMMIT_SHA, AUTHOR, NOW, MESSAGE, BRANCH, null), "null changes list must be rejected");
    }

    /**
     * Verifies the equals and hashCode: two changelogs with identical fields must be equal and produce the same hash code.
     * A changelog that differs in any field must not be equal to the original.
     */
    @Test
    @DisplayName("Equal changelogs compare as equal and produce the same hash code")
    void equalsAndHashCodeContract() {
        var changes = List.of(new FileChange("models/User.java", FileOperation.MODIFIED));
        var changelog1 = SemanticChangelog.create(COMMIT_SHA, AUTHOR, NOW, MESSAGE, BRANCH, changes);
        var changelog2 = SemanticChangelog.create(COMMIT_SHA, AUTHOR, NOW, MESSAGE, BRANCH, changes);
        var different = SemanticChangelog.create(COMMIT_SHA, AUTHOR, NOW, MESSAGE, "other-branch", changes);

        assertEquals(changelog1, changelog2, "changelogs with identical fields must be equal");
        assertNotEquals(changelog1, different, "changelogs differing in branch must not be equal");
        assertEquals(changelog1.hashCode(), changelog2.hashCode(), "equal changelogs must produce the same hash code");
    }

    /**
     * Verifies that {@link SemanticChangelog#toString()} produces a string that includes the short commit SHA (first seven characters), the branch name, and the author.
     * These are the fields most useful for identifying a changelog entry in log output.
     */
    @Test
    @DisplayName("toString includes the short commit SHA, branch, and author")
    void toStringIncludesKeyFields() {
        var changelog = SemanticChangelog.create(COMMIT_SHA, AUTHOR, NOW, MESSAGE, BRANCH, List.of());
        var str = changelog.toString();

        // only the first seven characters of the SHA appear
        assertTrue(str.contains("abc1234"), "toString must include the short commit SHA");
        assertTrue(str.contains(BRANCH), "toString must include the branch name");
        assertTrue(str.contains(AUTHOR), "toString must include the author");
    }

    /**
     * Verifies that {@link SemanticChangelog#writeTo} produces a file that contains all key metadata fields and the file change entries.
     * The committer section must be omitted for direct commits and included for cherry-picked commits.
     */
    @Test
    @DisplayName("writeTo writes all metadata and file changes to a readable text file")
    void writeToProducesCorrectFileContent(@TempDir Path tempDir) throws Exception {
        var changes = List.of(new FileChange("models/User.java", FileOperation.MODIFIED), new FileChange("models/NewRole.java", FileOperation.RENAMED, "models/Role.java"));
        var changelog = SemanticChangelog.create(COMMIT_SHA, AUTHOR, NOW, MESSAGE, BRANCH, changes);

        var outputPath = tempDir.resolve("changelog.txt");
        changelog.writeTo(outputPath);

        assertTrue(Files.exists(outputPath), "output file must be created");
        var content = Files.readString(outputPath);

        // the commit SHA, branch, author, and message must all appear in the output.
        assertTrue(content.contains(COMMIT_SHA), "commit SHA must appear in the file");
        assertTrue(content.contains(BRANCH), "branch must appear in the file");
        assertTrue(content.contains(AUTHOR), "author must appear in the file");
        assertTrue(content.contains(MESSAGE), "commit message must appear in the file");

        // file changes must be listed with their operation type.
        assertTrue(content.contains("MODIFIED: models/User.java"));
        assertTrue(content.contains("RENAMED: models/NewRole.java"));
        // for a renamed file, the old path must also appear.
        assertTrue(content.contains("models/Role.java"), "old path of a renamed file must appear in the file changes section");
    }

    /**
     * Verifies that {@link SemanticChangelog#writeTo} creates the parent directory automatically if it does not exist.
     * This is important because the {@code .vitruvius/changelogs/} directory is not guaranteed to exist before the first changelog is written.
     */
    @Test
    @DisplayName("writeTo creates parent directories automatically when they do not exist")
    void writeToCreatesParentDirectories(@TempDir Path tempDir) throws Exception {
        var changelog = SemanticChangelog.create(COMMIT_SHA, AUTHOR, NOW, MESSAGE, BRANCH, List.of());
        // use a deeply nested path that does not yet exist.
        var outputPath = tempDir.resolve(".vitruvius/changelogs/abc1234.txt");

        assertDoesNotThrow(() -> changelog.writeTo(outputPath), "writeTo must succeed even when the parent directory does not exist");
        assertTrue(Files.exists(outputPath));
    }
}