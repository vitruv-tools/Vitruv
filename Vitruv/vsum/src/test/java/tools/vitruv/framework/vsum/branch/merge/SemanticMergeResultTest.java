package tools.vitruv.framework.vsum.branch.merge;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link SemanticMergeResult} factory methods, {@code isSuccess()},
 * merge direction defaults, {@code withTimingStats()}, and {@link SemanticMergeResult.TimingStats}.
 */
class SemanticMergeResultTest {

    private static final Path MERGED_DIR = Path.of("/tmp/merged");

    @Test
    @DisplayName("success() produces SUCCESS status with isSuccess=true and empty conflicts")
    void successFactory() {
        var result = SemanticMergeResult.success(List.of(), MERGED_DIR);

        assertEquals(SemanticMergeResult.Status.SUCCESS, result.getStatus());
        assertTrue(result.isSuccess());
        assertTrue(result.getConflicts().isEmpty());
        assertEquals(MERGED_DIR, result.getMergedStateFolder());
        assertEquals(SemanticMergeResult.MergeDirection.FORWARD, result.getMergeDirection(),
                "default merge direction must be FORWARD");
    }

    @Test
    @DisplayName("conflict() produces CONFLICT status with isSuccess=false and the given conflicts")
    void conflictFactory() {
        var c = new MergeConflict("id", MergeConflict.ConflictType.MODIFY_MODIFY, List.of(), List.of());
        var result = SemanticMergeResult.conflict(List.of(c));

        assertEquals(SemanticMergeResult.Status.CONFLICT, result.getStatus());
        assertFalse(result.isSuccess());
        assertEquals(1, result.getConflicts().size());
        assertNull(result.getMergedStateFolder());
    }

    @Test
    @DisplayName("successWithResolutions() produces SUCCESS_WITH_RESOLUTIONS with isSuccess=true and stored resolutions")
    void successWithResolutionsFactory() {
        var res = new ConflictResolution("uuid-1", ConflictResolution.Choice.OURS);
        var result = SemanticMergeResult.successWithResolutions(List.of(res), List.of(), MERGED_DIR);

        assertEquals(SemanticMergeResult.Status.SUCCESS_WITH_RESOLUTIONS, result.getStatus());
        assertTrue(result.isSuccess());
        assertEquals(1, result.getAppliedResolutions().size());
        assertSame(res, result.getAppliedResolutions().get(0));
    }

    @Test
    @DisplayName("success with REVERSED direction stores the direction correctly")
    void successWithReversedDirection() {
        var result = SemanticMergeResult.success(
                List.of(), List.of(), MERGED_DIR, SemanticMergeResult.MergeDirection.REVERSED);
        assertEquals(SemanticMergeResult.MergeDirection.REVERSED, result.getMergeDirection());
    }

    @Test
    @DisplayName("success with warnings stores the warnings list")
    void successWithWarnings() {
        var w = new MergeConflict("id", MergeConflict.ConflictType.USER_VS_DERIVED_WARNING,
                List.of(), List.of());
        var result = SemanticMergeResult.success(List.of(), List.of(w), MERGED_DIR);

        assertFalse(result.getWarnings().isEmpty());
        assertEquals(1, result.getWarnings().size());
    }

    @Test
    @DisplayName("withTimingStats is chainable and stores the stats object")
    void withTimingStatsIsChainable() {
        var stats = new SemanticMergeResult.TimingStats().total(1_000_000L);
        var result = SemanticMergeResult.success(List.of(), MERGED_DIR).withTimingStats(stats);

        assertSame(stats, result.getTimingStats());
    }

    @Test
    @DisplayName("getTimingStats returns null when no stats were set")
    void timingStatsIsNullByDefault() {
        assertNull(SemanticMergeResult.success(List.of(), MERGED_DIR).getTimingStats());
    }

    @Test
    @DisplayName("TimingStats builder methods are chainable and nanosecond values are stored correctly")
    void timingStatsBuilderAndNanos() {
        var stats = new SemanticMergeResult.TimingStats()
                .gitStateExtraction(1_000_000L)
                .dtoLoading(2_000_000L)
                .conflictDetection(3_000_000L)
                .replay(4_000_000L)
                .total(10_000_000L);

        assertEquals(1_000_000L, stats.getGitStateExtractionNanos());
        assertEquals(2_000_000L, stats.getDtoLoadingNanos());
        assertEquals(3_000_000L, stats.getConflictDetectionNanos());
        assertEquals(4_000_000L, stats.getReplayNanos());
        assertEquals(10_000_000L, stats.getTotalNanos());
    }

    @Test
    @DisplayName("TimingStats millisecond helpers return nanos divided by 1_000_000")
    void timingStatsMsConversion() {
        var stats = new SemanticMergeResult.TimingStats().total(5_000_000L);
        assertEquals(5.0, stats.getTotalMs(), 0.001);
    }

    @Test
    @DisplayName("toString reflects the correct status for each factory method")
    void toStringReflectsStatus() {
        assertTrue(SemanticMergeResult.success(List.of(), MERGED_DIR).toString().contains("SUCCESS"));
        assertTrue(SemanticMergeResult.conflict(List.of()).toString().contains("CONFLICT"));
        assertTrue(SemanticMergeResult.successWithResolutions(
                List.of(), List.of(), MERGED_DIR).toString().contains("SUCCESS_WITH_RESOLUTIONS"));
    }
}
