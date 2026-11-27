package tools.vitruv.framework.vsum.branch.handler;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link PostCommitHandler}.
 *
 * <p>The handler's current responsibility is to accept a post-commit notification from
 * {@link VsumPostCommitWatcher} and log it. Tests verify that the handler accepts valid
 * inputs without throwing and rejects null inputs with {@link NullPointerException}.
 */
class PostCommitHandlerTest {

    private static final String COMMIT_SHA = "abc1234567890abcdef1234567890abcdef12345";
    private static final String BRANCH = "main";

    @Test
    @DisplayName("handlePostCommit completes without throwing for valid inputs")
    void handlePostCommitCompletesForValidInputs(@TempDir Path tempDir) {
        var handler = new PostCommitHandler(tempDir);
        assertDoesNotThrow(() -> handler.handlePostCommit(COMMIT_SHA, BRANCH));
    }

    @Test
    @DisplayName("handlePostCommit accepts feature branch names")
    void handlePostCommitAcceptsFeatureBranch(@TempDir Path tempDir) {
        var handler = new PostCommitHandler(tempDir);
        assertDoesNotThrow(() -> handler.handlePostCommit(COMMIT_SHA, "feature/add-component"));
    }

    @Test
    @DisplayName("Throws NullPointerException when commitSha is null")
    void throwsWhenCommitShaIsNull(@TempDir Path tempDir) {
        var handler = new PostCommitHandler(tempDir);
        assertThrows(NullPointerException.class, () -> handler.handlePostCommit(null, BRANCH));
    }

    @Test
    @DisplayName("Throws NullPointerException when branch is null")
    void throwsWhenBranchIsNull(@TempDir Path tempDir) {
        var handler = new PostCommitHandler(tempDir);
        assertThrows(NullPointerException.class, () -> handler.handlePostCommit(COMMIT_SHA, null));
    }

    @Test
    @DisplayName("Throws NullPointerException when repository root is null")
    void throwsWhenRepositoryRootIsNull() {
        assertThrows(NullPointerException.class, () -> new PostCommitHandler(null));
    }
}
