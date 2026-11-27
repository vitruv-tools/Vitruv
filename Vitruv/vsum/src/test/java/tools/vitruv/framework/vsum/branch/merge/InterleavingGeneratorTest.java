package tools.vitruv.framework.vsum.branch.merge;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link InterleavingGenerator}.
 *
 * <p>Tests cover the boundary cases (m=0, n=0, both zero), exhaustive mode for small
 * histories, heuristic mode for large histories, and the dependency-graph-driven path.
 */
class InterleavingGeneratorTest {

    private final InterleavingGenerator gen = new InterleavingGenerator();

    @Test
    @DisplayName("Both zero commits produce a single empty ordering")
    void bothZeroProducesSingleEmptyOrdering() {
        List<List<Boolean>> result = gen.generate(0, 0);
        assertEquals(1, result.size());
        assertTrue(result.get(0).isEmpty());
    }

    @Test
    @DisplayName("Zero A-commits produces a single all-false ordering")
    void zeroACommitsProducesAllFalseOrdering() {
        List<List<Boolean>> result = gen.generate(0, 3);
        assertEquals(1, result.size());
        assertEquals(3, result.get(0).size());
        assertTrue(result.get(0).stream().noneMatch(b -> b));
    }

    @Test
    @DisplayName("Zero B-commits produces a single all-true ordering")
    void zeroBCommitsProducesAllTrueOrdering() {
        List<List<Boolean>> result = gen.generate(3, 0);
        assertEquals(1, result.size());
        assertEquals(3, result.get(0).size());
        assertTrue(result.get(0).stream().allMatch(b -> b));
    }

    @Test
    @DisplayName("Exhaustive mode generates C(m+n, m) orderings for small histories")
    void exhaustiveModeGeneratesCorrectCount() {
        // C(4, 2) = 6 orderings for m=2, n=2
        List<List<Boolean>> result = gen.generate(2, 2);
        assertEquals(6, result.size());
    }

    @Test
    @DisplayName("Exhaustive mode orderings each have the correct length")
    void exhaustiveModeOrderingsHaveCorrectLength() {
        List<List<Boolean>> result = gen.generate(2, 3);
        for (List<Boolean> ordering : result) {
            assertEquals(5, ordering.size());
        }
    }

    @Test
    @DisplayName("Exhaustive mode orderings each contain exactly m trues and n falses")
    void exhaustiveModeOrderingsHaveCorrectTrueAndFalseCounts() {
        int m = 3, n = 2;
        List<List<Boolean>> result = gen.generate(m, n);
        for (List<Boolean> ordering : result) {
            long trueCount = ordering.stream().filter(b -> b).count();
            long falseCount = ordering.stream().filter(b -> !b).count();
            assertEquals(m, trueCount, "each ordering must have exactly m=3 true values");
            assertEquals(n, falseCount, "each ordering must have exactly n=2 false values");
        }
    }

    @Test
    @DisplayName("Exhaustive mode generates all distinct orderings with no duplicates")
    void exhaustiveModeGeneratesDistinctOrderings() {
        List<List<Boolean>> result = gen.generate(2, 2);
        long distinct = result.stream().distinct().count();
        assertEquals(result.size(), distinct, "all generated orderings must be distinct");
    }

    @Test
    @DisplayName("Heuristic mode is used when m+n exceeds the exhaustive threshold")
    void heuristicModeActivatedForLargeHistories() {
        // EXHAUSTIVE_THRESHOLD = 8, so m=5, n=5 (total=10) triggers heuristic
        List<List<Boolean>> result = gen.generate(5, 5);
        assertFalse(result.isEmpty());
        // A-first ordering must be present: [T,T,T,T,T,F,F,F,F,F]
        boolean hasAFirst = result.stream().anyMatch(o ->
                o.subList(0, 5).stream().allMatch(b -> b)
                && o.subList(5, 10).stream().noneMatch(b -> b));
        assertTrue(hasAFirst, "heuristic result must include the A-first ordering");
    }

    @Test
    @DisplayName("Heuristic mode orderings each have the correct length and true/false counts")
    void heuristicModeOrderingsAreWellFormed() {
        int m = 6, n = 4;
        List<List<Boolean>> result = gen.generate(m, n);
        for (List<Boolean> ordering : result) {
            assertEquals(m + n, ordering.size());
            long trues = ordering.stream().filter(b -> b).count();
            long falses = ordering.stream().filter(b -> !b).count();
            assertEquals(m, trues);
            assertEquals(n, falses);
        }
    }

    @Test
    @DisplayName("generateAll produces C(m+n, m) orderings")
    void generateAllProducesCorrectCount() {
        // C(3+2, 3) = C(5, 3) = 10
        List<List<Boolean>> result = gen.generateAll(3, 2);
        assertEquals(10, result.size());
    }

    @Test
    @DisplayName("generateFromDependencyGraph returns topological ordering for acyclic graph")
    void generateFromDependencyGraphReturnsOrdering() {
        // b0 must precede a0
        var g = new CommitDependencyGraph(1, 1, IntraBranchDependencyMode.CALCULATED, List.of());
        g.addEdge(g.nodeB(0), g.nodeA(0));

        List<List<Boolean>> result = gen.generateFromDependencyGraph(g);
        assertEquals(1, result.size());
        assertEquals(List.of(false, true), result.get(0),
                "b0 before a0 means [false, true]");
    }

    @Test
    @DisplayName("generateFromDependencyGraph returns empty list for a cyclic graph")
    void generateFromDependencyGraphReturnsEmptyForCycle() {
        var g = new CommitDependencyGraph(1, 1, IntraBranchDependencyMode.CALCULATED, List.of());
        g.addEdge(g.nodeA(0), g.nodeB(0));
        g.addEdge(g.nodeB(0), g.nodeA(0)); // cycle

        List<List<Boolean>> result = gen.generateFromDependencyGraph(g);
        assertTrue(result.isEmpty(), "cyclic graph must produce no valid ordering");
    }
}
