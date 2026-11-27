package tools.vitruv.framework.vsum.branch.data;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link CommitOptions} and its builder.
 *
 * <p>Tests verify the default state of an empty builder, accumulation of additional
 * and excluded files via chained builder calls, and that the resulting lists are
 * defensively copied (unmodifiable).
 */
class CommitOptionsTest {

    @Test
    @DisplayName("Empty builder produces empty additional and exclude lists")
    void emptyBuilderProducesEmptyLists() {
        var opts = CommitOptions.builder().build();
        assertTrue(opts.getAdditionalFiles().isEmpty());
        assertTrue(opts.getExcludeFiles().isEmpty());
    }

    @Test
    @DisplayName("addFile appends to the additional files list")
    void addFileAppendsToAdditionalFiles() {
        var path = Path.of("model/User.xmi");
        var opts = CommitOptions.builder().addFile(path).build();

        assertEquals(List.of(path), opts.getAdditionalFiles());
        assertTrue(opts.getExcludeFiles().isEmpty());
    }

    @Test
    @DisplayName("excludeFile appends to the exclude list")
    void excludeFileAppendsToExcludeList() {
        var path = Path.of("model/Stale.xmi");
        var opts = CommitOptions.builder().excludeFile(path).build();

        assertEquals(List.of(path), opts.getExcludeFiles());
        assertTrue(opts.getAdditionalFiles().isEmpty());
    }

    @Test
    @DisplayName("Multiple addFile and excludeFile calls accumulate independently")
    void multipleCallsAccumulateIndependently() {
        var extra1 = Path.of("a.xmi");
        var extra2 = Path.of("b.xmi");
        var exc1 = Path.of("c.xmi");

        var opts = CommitOptions.builder()
                .addFile(extra1)
                .addFile(extra2)
                .excludeFile(exc1)
                .build();

        assertEquals(List.of(extra1, extra2), opts.getAdditionalFiles());
        assertEquals(List.of(exc1), opts.getExcludeFiles());
    }

    @Test
    @DisplayName("Builder is fluent — addFile and excludeFile return the same builder instance")
    void builderIsFluent() {
        var builder = CommitOptions.builder();
        assertSame(builder, builder.addFile(Path.of("x.xmi")));
        assertSame(builder, builder.excludeFile(Path.of("y.xmi")));
    }

    @Test
    @DisplayName("The returned lists are unmodifiable")
    void returnedListsAreUnmodifiable() {
        var opts = CommitOptions.builder().addFile(Path.of("a.xmi")).build();

        assertThrows(UnsupportedOperationException.class,
                () -> opts.getAdditionalFiles().add(Path.of("extra.xmi")),
                "additionalFiles must be unmodifiable");
        assertThrows(UnsupportedOperationException.class,
                () -> opts.getExcludeFiles().add(Path.of("extra.xmi")),
                "excludeFiles must be unmodifiable");
    }
}
