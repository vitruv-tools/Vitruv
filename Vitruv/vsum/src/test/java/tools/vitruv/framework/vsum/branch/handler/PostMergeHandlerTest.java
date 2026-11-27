package tools.vitruv.framework.vsum.branch.handler;

import org.eclipse.jgit.api.Git;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import tools.vitruv.change.atomic.uuid.UuidResolver;
import tools.vitruv.change.correspondence.view.EditableCorrespondenceModelView;
import tools.vitruv.framework.vsum.branch.data.ValidationResult;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link PostMergeHandler}.
 *
 * <p>Tests cover null-parameter rejection, the validation flow with a mocked
 * {@link InternalVirtualModel}, the conditional {@code reload()} call, and the
 * filesystem VSUM copy that propagates source-branch state to the target branch.
 */
@SuppressWarnings("unchecked")
class PostMergeHandlerTest {

    @TempDir
    Path repoRoot;

    private static final String SOURCE_BRANCH = "feature-x";
    private static final String TARGET_BRANCH = "main";

    @BeforeEach
    void initGitRepository() throws Exception {
        try (var git = Git.init().setDirectory(repoRoot.toFile()).setInitialBranch("master").call()) {
            Files.writeString(repoRoot.resolve("init.txt"), "initial");
            git.add().addFilepattern("init.txt").call();
            git.commit().setMessage("Initial commit").setAllowEmpty(false).call();
        }
    }

    @Test
    @DisplayName("Throws NullPointerException when virtualModel is null")
    void throwsWhenVirtualModelIsNull() {
        assertThrows(NullPointerException.class,
                () -> new PostMergeHandler(null, repoRoot));
    }

    @Test
    @DisplayName("Throws NullPointerException when repositoryRoot is null")
    void throwsWhenRepositoryRootIsNull() {
        assertThrows(NullPointerException.class,
                () -> new PostMergeHandler(mock(InternalVirtualModel.class), null));
    }

    @Test
    @DisplayName("performPostMerge throws NullPointerException when sourceBranch is null")
    void performPostMergeThrowsWhenSourceBranchIsNull() {
        var handler = new PostMergeHandler(validMockVsum(), repoRoot);
        assertThrows(NullPointerException.class,
                () -> handler.performPostMerge(null, TARGET_BRANCH));
    }

    @Test
    @DisplayName("performPostMerge throws NullPointerException when targetBranch is null")
    void performPostMergeThrowsWhenTargetBranchIsNull() {
        var handler = new PostMergeHandler(validMockVsum(), repoRoot);
        assertThrows(NullPointerException.class,
                () -> handler.performPostMerge(SOURCE_BRANCH, null));
    }

    @Test
    @DisplayName("copyVsumFromSourceBranch throws NullPointerException when sourceBranch is null")
    void copyVsumThrowsWhenSourceBranchIsNull() {
        var handler = new PostMergeHandler(validMockVsum(), repoRoot);
        assertThrows(NullPointerException.class,
                () -> handler.copyVsumFromSourceBranch(null, TARGET_BRANCH));
    }

    @Test
    @DisplayName("copyVsumFromSourceBranch throws NullPointerException when targetBranch is null")
    void copyVsumThrowsWhenTargetBranchIsNull() {
        var handler = new PostMergeHandler(validMockVsum(), repoRoot);
        assertThrows(NullPointerException.class,
                () -> handler.copyVsumFromSourceBranch(SOURCE_BRANCH, null));
    }

    @Test
    @DisplayName("validate returns a valid result when the VirtualModel subsystems are accessible")
    void validateReturnsValidResultWithAccessibleSubsystems() {
        var handler = new PostMergeHandler(validMockVsum(), repoRoot);

        ValidationResult result = handler.validate();

        assertTrue(result.isValid());
    }

    @Test
    @DisplayName("validate returns an invalid result when the UUID resolver is null")
    void validateReturnsInvalidResultWhenUuidResolverIsNull() {
        var mockVsum = mock(InternalVirtualModel.class);
        when(mockVsum.getViewSourceModels()).thenReturn(List.of());
        when(mockVsum.getCorrespondenceModel()).thenReturn(mock(EditableCorrespondenceModelView.class));
        when(mockVsum.getUuidResolver()).thenReturn(null);

        var handler = new PostMergeHandler(mockVsum, repoRoot);
        ValidationResult result = handler.validate();

        assertFalse(result.isValid());
        assertFalse(result.getErrors().isEmpty());
    }

    @Test
    @DisplayName("performPostMerge returns a valid result and calls reload() when validation passes")
    void performPostMergeCallsReloadWhenValidationPasses() {
        var mockVsum = validMockVsum();
        var handler = new PostMergeHandler(mockVsum, repoRoot);

        ValidationResult result = handler.performPostMerge(SOURCE_BRANCH, TARGET_BRANCH);

        assertTrue(result.isValid());
        verify(mockVsum, times(1)).reload();
    }

    @Test
    @DisplayName("performPostMerge does not call reload() when validation fails")
    void performPostMergeDoesNotCallReloadWhenValidationFails() {
        var mockVsum = mock(InternalVirtualModel.class);
        when(mockVsum.getViewSourceModels()).thenReturn(List.of());
        when(mockVsum.getCorrespondenceModel()).thenReturn(mock(EditableCorrespondenceModelView.class));
        when(mockVsum.getUuidResolver()).thenReturn(null);

        var handler = new PostMergeHandler(mockVsum, repoRoot);
        ValidationResult result = handler.performPostMerge(SOURCE_BRANCH, TARGET_BRANCH);

        assertFalse(result.isValid());
        verify(mockVsum, never()).reload();
    }

    @Test
    @DisplayName("copyVsumFromSourceBranch copies files from source to target VSUM directory")
    void copyVsumCopiesFilesFromSourceToTarget() throws IOException {
        Path vsumRoot = repoRoot.resolve(".vitruvius/vsum");
        Path sourceVsum = vsumRoot.resolve(SOURCE_BRANCH);
        Files.createDirectories(sourceVsum);
        Files.writeString(sourceVsum.resolve("model.xmi"), "<model/>");

        var handler = new PostMergeHandler(validMockVsum(), repoRoot);
        handler.copyVsumFromSourceBranch(SOURCE_BRANCH, TARGET_BRANCH);

        assertTrue(Files.exists(vsumRoot.resolve(TARGET_BRANCH).resolve("model.xmi")),
                "VSUM file must be copied to the target branch directory");
    }

    @Test
    @DisplayName("copyVsumFromSourceBranch replaces stale target state with the source state")
    void copyVsumReplacesStaleTargetState() throws IOException {
        Path vsumRoot = repoRoot.resolve(".vitruvius/vsum");
        Path sourceVsum = vsumRoot.resolve(SOURCE_BRANCH);
        Path targetVsum = vsumRoot.resolve(TARGET_BRANCH);
        Files.createDirectories(sourceVsum);
        Files.createDirectories(targetVsum);
        Files.writeString(sourceVsum.resolve("new.xmi"), "<new/>");
        Files.writeString(targetVsum.resolve("stale.xmi"), "<stale/>");

        var handler = new PostMergeHandler(validMockVsum(), repoRoot);
        handler.copyVsumFromSourceBranch(SOURCE_BRANCH, TARGET_BRANCH);

        assertTrue(Files.exists(vsumRoot.resolve(TARGET_BRANCH).resolve("new.xmi")),
                "new source file must appear in the target directory");
        assertFalse(Files.exists(vsumRoot.resolve(TARGET_BRANCH).resolve("stale.xmi")),
                "stale target file must be removed before the copy");
    }

    private static InternalVirtualModel validMockVsum() {
        var mockVsum = mock(InternalVirtualModel.class);
        when(mockVsum.getViewSourceModels()).thenReturn(List.of());
        when(mockVsum.getCorrespondenceModel()).thenReturn(mock(EditableCorrespondenceModelView.class));
        when(mockVsum.getUuidResolver()).thenReturn(mock(UuidResolver.class));
        return mockVsum;
    }
}
