package tools.vitruv.framework.vsum.branch.merge;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link GitMergeDriver}.
 *
 * <p>Tests focus on the stale-cache detection logic ({@link GitMergeDriver#isCacheStale})
 * and the cache cleanup ({@link GitMergeDriver#cleanCache}). The full {@code main()} entry
 * point is not tested here because it requires a live Git merge operation and calls
 * {@code System.exit()} -- those scenarios are covered by integration tests.
 */
class GitMergeDriverTest {

    private static final String CACHE_DIR = ".vitruvius/merge-cache";
    private static final String DONE_MARKER = ".merge-done";

    // --- isCacheStale (T3) ---

    /**
     * Verifies the primary stale condition: .merge-done exists (a prior merge ran) but
     * .git/MERGE_HEAD does not (that merge already committed). The driver must evict the
     * cache so it does not serve files from the wrong merge operation.
     */
    @Test
    @DisplayName("isCacheStale returns true when .merge-done exists and MERGE_HEAD is absent")
    void isCacheStaleWhenDoneMarkerExistsAndMergeHeadAbsent(@TempDir Path repoRoot)
            throws Exception {
        Path cacheDir = repoRoot.resolve(CACHE_DIR);
        Files.createDirectories(cacheDir);
        Files.createFile(cacheDir.resolve(DONE_MARKER));
        Files.createDirectories(repoRoot.resolve(".git"));

        assertTrue(GitMergeDriver.isCacheStale(repoRoot, cacheDir),
                "cache must be considered stale when prior merge committed and MERGE_HEAD is gone");
    }

    /**
     * Verifies that a cache with .merge-done is NOT stale while .git/MERGE_HEAD still exists,
     * meaning we are mid-merge and Git is invoking the driver for a subsequent file. The cache
     * must be reused so all files receive the same merged content.
     */
    @Test
    @DisplayName("isCacheStale returns false when .merge-done exists and MERGE_HEAD is present")
    void isCacheStaleReturnsFalseWhenMergeHeadPresent(@TempDir Path repoRoot) throws Exception {
        Path cacheDir = repoRoot.resolve(CACHE_DIR);
        Files.createDirectories(cacheDir);
        Files.createFile(cacheDir.resolve(DONE_MARKER));
        Path gitDir = repoRoot.resolve(".git");
        Files.createDirectories(gitDir);
        Files.createFile(gitDir.resolve("MERGE_HEAD"));

        assertFalse(GitMergeDriver.isCacheStale(repoRoot, cacheDir),
                "cache must not be considered stale while an ongoing merge holds MERGE_HEAD");
    }

    /**
     * Verifies that a cache without .merge-done is not stale regardless of MERGE_HEAD state.
     * No prior merge completed, so there is no stale content to evict.
     */
    @Test
    @DisplayName("isCacheStale returns false when .merge-done is absent")
    void isCacheStaleReturnsFalseWhenDoneMarkerAbsent(@TempDir Path repoRoot) throws Exception {
        Path cacheDir = repoRoot.resolve(CACHE_DIR);
        Files.createDirectories(cacheDir);
        Files.createDirectories(repoRoot.resolve(".git"));

        assertFalse(GitMergeDriver.isCacheStale(repoRoot, cacheDir),
                "cache must not be considered stale when no prior merge completed");
    }

    /**
     * Verifies that isCacheStale returns false when the cache directory does not exist at all.
     * This is the first-run case where no prior merge cache has ever been created.
     */
    @Test
    @DisplayName("isCacheStale returns false when the cache directory does not exist")
    void isCacheStaleReturnsFalseWhenCacheDirAbsent(@TempDir Path repoRoot) throws Exception {
        Path cacheDir = repoRoot.resolve(CACHE_DIR);
        Files.createDirectories(repoRoot.resolve(".git"));

        assertFalse(GitMergeDriver.isCacheStale(repoRoot, cacheDir),
                "cache must not be stale when the cache directory has never been created");
    }

    // --- cleanCache (T3) ---

    /**
     * Verifies that cleanCache deletes the entire merge-cache directory including all
     * files inside it, leaving no residual state for subsequent merge operations.
     */
    @Test
    @DisplayName("cleanCache deletes the merge-cache directory and all its contents")
    void cleanCacheDeletesCacheDirectory(@TempDir Path repoRoot) throws Exception {
        Path cacheDir = repoRoot.resolve(CACHE_DIR);
        Files.createDirectories(cacheDir);
        Files.createFile(cacheDir.resolve(DONE_MARKER));
        Files.createFile(cacheDir.resolve("some.model"));

        GitMergeDriver.cleanCache(repoRoot);

        assertFalse(Files.exists(cacheDir),
                "cache directory must be fully deleted by cleanCache");
    }

    /**
     * Verifies that cleanCache does not throw when the cache directory does not exist.
     * This can happen if cleanCache is called redundantly after a previous cleanup.
     */
    @Test
    @DisplayName("cleanCache is a no-op when the cache directory does not exist")
    void cleanCacheIsNoOpWhenCacheAbsent(@TempDir Path repoRoot) throws Exception {
        assertDoesNotThrow(() -> GitMergeDriver.cleanCache(repoRoot),
                "cleanCache must not throw when the cache directory is already absent");
    }
}
