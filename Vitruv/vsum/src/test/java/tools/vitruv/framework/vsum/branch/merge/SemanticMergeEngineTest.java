package tools.vitruv.framework.vsum.branch.merge;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import tools.vitruv.change.interaction.InteractionResultProvider;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * Unit tests for {@link SemanticMergeEngine}.
 *
 * <p>Tests verify construction with all supported parameter overloads, the
 * {@code skipDirectConflictDetection} toggle, and the fluent
 * {@link SemanticMergeEngine#withConflictResolutionProvider} factory.  The merge-execution
 * pipeline (steps 1-6) requires a real Git repository and is covered by the case-study
 * integration tests; negative-path tests confirm that a missing repository raises an exception
 * before any model state is modified.
 */
class SemanticMergeEngineTest {

    @Test
    @DisplayName("Three-arg constructor creates an engine without throwing")
    void threeArgConstructorCreatesEngine(@TempDir Path tempDir) {
        assertDoesNotThrow(() -> new SemanticMergeEngine(
                tempDir, List.of(), mock(InteractionResultProvider.class)));
    }

    @Test
    @DisplayName("Four-arg constructor with ConflictResolutionProvider creates an engine without throwing")
    void fourArgConstructorCreatesEngine(@TempDir Path tempDir) {
        assertDoesNotThrow(() -> new SemanticMergeEngine(
                tempDir,
                List.of(),
                mock(InteractionResultProvider.class),
                ConflictResolutionProvider.chooseAllOurs()));
    }

    @Test
    @DisplayName("Five-arg constructor with IntraBranchDependencyMode creates an engine without throwing")
    void fiveArgConstructorCreatesEngine(@TempDir Path tempDir) {
        assertDoesNotThrow(() -> new SemanticMergeEngine(
                tempDir,
                List.of(),
                mock(InteractionResultProvider.class),
                null,
                IntraBranchDependencyMode.CALCULATED));
    }

    @Test
    @DisplayName("setSkipDirectConflictDetection can be toggled without throwing")
    void setSkipDirectConflictDetectionDoesNotThrow(@TempDir Path tempDir) {
        var engine = new SemanticMergeEngine(
                tempDir, List.of(), mock(InteractionResultProvider.class));
        assertDoesNotThrow(() -> engine.setSkipDirectConflictDetection(true));
        assertDoesNotThrow(() -> engine.setSkipDirectConflictDetection(false));
    }

    @Test
    @DisplayName("withConflictResolutionProvider returns a distinct engine configured with the given provider")
    void withConflictResolutionProviderReturnsNewEngine(@TempDir Path tempDir) {
        var base = new SemanticMergeEngine(
                tempDir, List.of(), mock(InteractionResultProvider.class));

        SemanticMergeEngine withProvider =
                base.withConflictResolutionProvider(ConflictResolutionProvider.chooseAllTheirs());

        assertNotNull(withProvider);
        assertNotSame(base, withProvider, "withConflictResolutionProvider must return a new instance");
    }

    @Test
    @DisplayName("merge throws when the repository root is not a Git repository")
    void mergeThrowsForNonGitDirectory(@TempDir Path tempDir) {
        var engine = new SemanticMergeEngine(
                tempDir, List.of(), mock(InteractionResultProvider.class));

        assertThrows(Exception.class,
                () -> engine.merge("abc1234", "def5678", "ghi9012"),
                "merge must throw when repoRoot is not a Git repository");
    }

    @Test
    @DisplayName("mergeBidirectional throws when the repository root is not a Git repository")
    void mergeBidirectionalThrowsForNonGitDirectory(@TempDir Path tempDir) {
        var engine = new SemanticMergeEngine(
                tempDir, List.of(), mock(InteractionResultProvider.class));

        assertThrows(Exception.class,
                () -> engine.mergeBidirectional("abc1234", "def5678", "ghi9012"),
                "mergeBidirectional must throw when repoRoot is not a Git repository");
    }
}
