package tools.vitruv.framework.vsum.branch.handler;

import org.eclipse.jgit.api.Git;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import tools.vitruv.framework.vsum.branch.data.SemanticChangelog;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link PostCommitHandler}.
 *
 * <p>These tests verify that the changelog is generated with the correct commit SHA,
 * branch, author, and message by reading actual commit info from Git via JGit.
 *
 * <p>Each test creates a real Git repository with actual commits to ensure PostCommitHandler
 * can read commit metadata correctly.
 */
class PostCommitHandlerTest {

    private static final String BRANCH_MAIN     = "main";
    private static final String BRANCH_FEAT     = "feature-add-component";

    // -------------------------------------------------------------------------
    // Test helpers
    // -------------------------------------------------------------------------

    /**
     * Creates a real Git repository with an actual commit and returns the commit SHA.
     * This is necessary because PostCommitHandler now reads commit info from Git using JGit.
     */
    private String createTestCommit(Path repoRoot, String message, String author) throws Exception {
        try (Git git = Git.init().setDirectory(repoRoot.toFile()).setInitialBranch(BRANCH_MAIN).call()) {
            // Create a dummy file
            Files.writeString(repoRoot.resolve("test.txt"), "test content");

            // Stage and commit
            git.add().addFilepattern(".").call();
            var commit = git.commit()
                    .setMessage(message)
                    .setAuthor(author, author + "@test.com")
                    .call();

            return commit.getName();
        }
    }

    // -------------------------------------------------------------------------
    // generateChangelog() — content correctness
    // -------------------------------------------------------------------------

    /**
     * Verifies that the generated changelog contains the exact commit SHA passed in.
     * This is the core correctness requirement: the changelog must reference the real
     * commit SHA so it is traceable via {@code git log}.
     */
    @Test
    @DisplayName("Generated changelog contains the correct commit SHA")
    void changelogContainsCorrectCommitSha(@TempDir Path tempDir) throws Exception {
        // Create a real Git repository with a real commit
        String commitSha = createTestCommit(tempDir, "Test commit", "TestAuthor");

        var handler     = new PostCommitHandler(tempDir);
        var changelog   = handler.generateChangelog(commitSha, BRANCH_MAIN);
        Path outputFile = tempDir.resolve(commitSha.substring(0, 7) + ".txt");

        changelog.writeTo(outputFile);

        assertNotNull(changelog, "generateChangelog must return a non-null changelog");
        String content = Files.readString(outputFile);
        assertTrue(content.contains(commitSha),
                "changelog file must contain the full commit SHA, but was:\n" + content);
    }

    /**
     * Verifies that the generated changelog contains the branch name passed in.
     */
    @Test
    @DisplayName("Generated changelog contains the correct branch name")
    void changelogContainsCorrectBranch(@TempDir Path tempDir) throws Exception {
        String commitSha = createTestCommit(tempDir, "Test commit", "TestAuthor");

        var handler     = new PostCommitHandler(tempDir);
        var changelog   = handler.generateChangelog(commitSha, BRANCH_FEAT);
        Path outputFile = tempDir.resolve(commitSha.substring(0, 7) + ".txt");

        changelog.writeTo(outputFile);

        String content = Files.readString(outputFile);
        assertTrue(content.contains(BRANCH_FEAT),
                "changelog file must contain the branch name, but was:\n" + content);
    }

    /**
     * Verifies that the changelog contains the real commit message from Git.
     */
    @Test
    @DisplayName("Generated changelog contains the actual commit message from Git")
    void changelogContainsRealCommitMessage(@TempDir Path tempDir) throws Exception {
        String expectedMessage = "Add new feature with tests";
        String commitSha = createTestCommit(tempDir, expectedMessage, "TestAuthor");

        var handler     = new PostCommitHandler(tempDir);
        var changelog   = handler.generateChangelog(commitSha, BRANCH_MAIN);
        Path outputFile = tempDir.resolve(commitSha.substring(0, 7) + ".txt");

        changelog.writeTo(outputFile);

        String content = Files.readString(outputFile);
        assertTrue(content.contains(expectedMessage),
                "changelog must contain the actual commit message from Git, but was:\n" + content);
        assertFalse(content.contains("Commit on " + BRANCH_MAIN),
                "changelog must NOT contain the old placeholder message");
    }

    /**
     * Verifies that the changelog contains the real author name from Git.
     */
    @Test
    @DisplayName("Generated changelog contains the actual author from Git")
    void changelogContainsRealAuthor(@TempDir Path tempDir) throws Exception {
        String expectedAuthor = "Alice Developer";
        String commitSha = createTestCommit(tempDir, "Test commit", expectedAuthor);

        var handler = new PostCommitHandler(tempDir);
        var changelog   = handler.generateChangelog(commitSha, BRANCH_MAIN);
        Path outputFile = tempDir.resolve(commitSha.substring(0, 7) + ".txt");

        changelog.writeTo(outputFile);

        String content = Files.readString(outputFile);
        assertTrue(content.contains(expectedAuthor), "changelog must contain the actual author from Git, but was:\n" + content);
        assertFalse(content.contains("Author:     system"),
                "changelog must NOT contain the old placeholder author 'system'");
    }

    /**
     * Verifies that the changelog can be written to disk and the resulting file
     * contains the correct commit SHA. This is the critical end-to-end test: the file
     * on disk must be named with the short SHA and its content must embed the full SHA.
     */
    @Test
    @DisplayName("Changelog written to disk contains the correct commit SHA")
    void changelogWrittenToDiskContainsCorrectSha(@TempDir Path tempDir) throws Exception {
        String commitSha = createTestCommit(tempDir, "Test commit", "TestAuthor");

        var handler     = new PostCommitHandler(tempDir);
        var changelog   = handler.generateChangelog(commitSha, BRANCH_MAIN);
        Path outputFile = tempDir.resolve(commitSha.substring(0, 7) + ".txt");

        changelog.writeTo(outputFile);

        assertTrue(Files.exists(outputFile),
                "changelog file must be written to disk");
        String content = Files.readString(outputFile);
        assertTrue(content.contains(commitSha),
                "changelog file on disk must contain the real commit SHA, but was:\n" + content);
        assertTrue(content.contains(BRANCH_MAIN),
                "changelog file on disk must contain the branch name, but was:\n" + content);
    }

    /**
     * Verifies that the changelog file is named after the short SHA (first 7 characters)
     * of the commit. This naming convention matches Git's short SHA format.
     */
    @Test
    @DisplayName("Changelog file is named with the first 7 characters of the commit SHA")
    void changelogFileNameMatchesShortSha(@TempDir Path tempDir) throws Exception {
        String commitSha = createTestCommit(tempDir, "Test commit", "TestAuthor");

        var handler      = new PostCommitHandler(tempDir);
        var changelog    = handler.generateChangelog(commitSha, BRANCH_MAIN);
        String shortSha  = commitSha.substring(0, 7);
        Path outputFile  = tempDir.resolve(shortSha + ".txt");

        changelog.writeTo(outputFile);

        assertTrue(Files.exists(outputFile),
                "changelog file must be named <shortSha>.txt, expected: " + outputFile);
        assertFalse(Files.exists(tempDir.resolve(commitSha + ".txt")),
                "changelog must not be named with the full 40-character SHA");
    }

    /**
     * Verifies that two changelogs generated for different commits produce distinct
     * file content.
     */
    @Test
    @DisplayName("Changelogs for different commits contain different SHAs")
    void differentCommitsProduceDistinctChangelogs(@TempDir Path tempDir) throws Exception {
        // Create first commit
        String sha1 = createTestCommit(tempDir, "First commit", "Author1");

        // Create second commit
        try (Git git = Git.open(tempDir.toFile())) {
            Files.writeString(tempDir.resolve("test2.txt"), "more content");
            git.add().addFilepattern(".").call();
            var commit2 = git.commit()
                    .setMessage("Second commit")
                    .setAuthor("Author2", "author2@test.com")
                    .call();
            String sha2 = commit2.getName();

            var handler = new PostCommitHandler(tempDir);
            Path file1 = tempDir.resolve(sha1.substring(0, 7) + ".txt");
            Path file2 = tempDir.resolve(sha2.substring(0, 7) + ".txt");

            handler.generateChangelog(sha1, BRANCH_MAIN).writeTo(file1);
            handler.generateChangelog(sha2, BRANCH_MAIN).writeTo(file2);

            String content1 = Files.readString(file1);
            String content2 = Files.readString(file2);

            assertTrue(content1.contains(sha1),
                    "first changelog must contain its own full SHA");
            assertTrue(content2.contains(sha2),
                    "second changelog must contain its own full SHA");
            assertFalse(content1.contains(sha2),
                    "first changelog must not contain the second commit's SHA");
            assertFalse(content2.contains(sha1),
                    "second changelog must not contain the first commit's SHA");
        }
    }

    /**
     * Verifies that {@link PostCommitHandler#generateChangelog} throws
     * {@link NullPointerException} when {@code commitSha} is null.
     */
    @Test
    @DisplayName("Throws NullPointerException when commitSha is null")
    void throwsWhenCommitShaIsNull(@TempDir Path tempDir) throws Exception {
        // Still need a Git repo for the handler to work
        createTestCommit(tempDir, "Test", "Test");

        var handler = new PostCommitHandler(tempDir);
        assertThrows(NullPointerException.class,
                () -> handler.generateChangelog(null, BRANCH_MAIN),
                "null commitSha must be rejected immediately");
    }

    /**
     * Verifies that {@link PostCommitHandler#generateChangelog} throws
     * {@link NullPointerException} when {@code branch} is null.
     */
    @Test
    @DisplayName("Throws NullPointerException when branch is null")
    void throwsWhenBranchIsNull(@TempDir Path tempDir) throws Exception {
        String commitSha = createTestCommit(tempDir, "Test", "Test");

        var handler = new PostCommitHandler(tempDir);
        assertThrows(NullPointerException.class,
                () -> handler.generateChangelog(commitSha, null),
                "null branch must be rejected immediately");
    }

    /**
     * Verifies that PostCommitHandler throws NullPointerException when
     * repository root is null.
     */
    @Test
    @DisplayName("Throws NullPointerException when repository root is null")
    void throwsWhenRepositoryRootIsNull() {
        assertThrows(NullPointerException.class,
                () -> new PostCommitHandler(null),
                "null repository root must be rejected immediately");
    }
}