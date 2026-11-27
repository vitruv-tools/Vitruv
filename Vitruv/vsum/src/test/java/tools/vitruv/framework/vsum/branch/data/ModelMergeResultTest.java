package tools.vitruv.framework.vsum.branch.data;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link ModelMergeResult} and its factory methods.
 *
 * <p>Tests verify the four factory methods ({@code success}, {@code fastForward},
 * {@code conflicting}, {@code failed}), the {@code isSuccessful()} convenience method,
 * and the content of the generated human-readable messages.
 */
class ModelMergeResultTest {

    private static final String SOURCE = "feature-x";
    private static final String TARGET = "main";
    private static final String SHA = "abc1234567890abcdef";

    @Test
    @DisplayName("success() produces SUCCESS status with the merge commit SHA and isSuccessful=true")
    void successFactory() {
        var result = ModelMergeResult.success(SOURCE, TARGET, SHA);

        assertEquals(ModelMergeResult.MergeStatus.SUCCESS, result.getStatus());
        assertTrue(result.isSuccessful());
        assertEquals(SOURCE, result.getSourceBranch());
        assertEquals(TARGET, result.getTargetBranch());
        assertEquals(SHA, result.getMergeCommitSha());
        assertFalse(result.isFastForward());
        assertTrue(result.getConflictingFiles().isEmpty());
        assertTrue(result.getMessage().contains(SOURCE));
        assertTrue(result.getMessage().contains(TARGET));
    }

    @Test
    @DisplayName("fastForward() produces FAST_FORWARD status with fastForward=true and isSuccessful=true")
    void fastForwardFactory() {
        var result = ModelMergeResult.fastForward(SOURCE, TARGET, SHA);

        assertEquals(ModelMergeResult.MergeStatus.FAST_FORWARD, result.getStatus());
        assertTrue(result.isSuccessful());
        assertTrue(result.isFastForward());
        assertEquals(SHA, result.getMergeCommitSha(),
                "newHeadSha is stored in the mergeCommitSha field for FAST_FORWARD");
        assertTrue(result.getConflictingFiles().isEmpty());
    }

    @Test
    @DisplayName("conflicting() produces CONFLICTING status with null SHA, isSuccessful=false, and all conflicting files")
    void conflictingFactory() {
        var conflictFiles = List.of("model/User.xmi", "model/Order.xmi");
        var result = ModelMergeResult.conflicting(SOURCE, TARGET, conflictFiles);

        assertEquals(ModelMergeResult.MergeStatus.CONFLICTING, result.getStatus());
        assertFalse(result.isSuccessful());
        assertNull(result.getMergeCommitSha());
        assertEquals(conflictFiles, result.getConflictingFiles());
        assertTrue(result.getMessage().contains(String.valueOf(conflictFiles.size())));
    }

    @Test
    @DisplayName("failed() produces FAILED status with null SHA and isSuccessful=false")
    void failedFactory() {
        var result = ModelMergeResult.failed(SOURCE, TARGET, "I/O error reading pack file");

        assertEquals(ModelMergeResult.MergeStatus.FAILED, result.getStatus());
        assertFalse(result.isSuccessful());
        assertNull(result.getMergeCommitSha());
        assertTrue(result.getConflictingFiles().isEmpty());
        assertTrue(result.getMessage().contains("I/O error reading pack file"));
    }

    @Test
    @DisplayName("toString includes status, source branch, target branch, and conflict count")
    void toStringIncludesKeyInfo() {
        var str = ModelMergeResult.success(SOURCE, TARGET, SHA).toString();
        assertTrue(str.contains("SUCCESS"));
        assertTrue(str.contains(SOURCE));
        assertTrue(str.contains(TARGET));
    }
}
