package tools.vitruv.framework.vsum.branch.merge;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link CommitDependencyGraph}.
 *
 * <p>Tests verify node-index helpers, sequential and calculated intra-branch edges,
 * inter-branch edge addition, Kahn's cycle detection, topological ordering, and
 * cyclic-pair reporting.
 */
class CommitDependencyGraphTest {

    @Test
    @DisplayName("nodeA and nodeB return the correct node indices")
    void nodeIndexHelpers() {
        var g = new CommitDependencyGraph(3, 2);
        assertEquals(0, g.nodeA(0));
        assertEquals(1, g.nodeA(1));
        assertEquals(2, g.nodeA(2));
        assertEquals(3, g.nodeB(0));
        assertEquals(4, g.nodeB(1));
    }

    @Test
    @DisplayName("SEQUENTIAL mode adds intra-branch ordering edges automatically")
    void sequentialModeAddsIntraBranchEdges() {
        // With 2 A-commits and 2 B-commits in SEQUENTIAL mode:
        // a0→a1 and b0→b1 must be present.
        // Topological order must respect: a0 before a1, b0 before b1.
        var g = new CommitDependencyGraph(2, 2);

        assertFalse(g.hasCycle());
        List<Boolean> order = g.topologicalSort();
        assertNotNull(order);
        assertEquals(4, order.size());

        // verify intra-branch ordering: a0 must appear before a1, b0 before b1
        int posA0 = order.indexOf(true);          // first occurrence of true = a0
        int posA1 = order.lastIndexOf(true);       // last occurrence of true = a1
        int posB0 = order.indexOf(false);          // first occurrence of false = b0
        int posB1 = order.lastIndexOf(false);      // last occurrence of false = b1
        assertTrue(posA0 < posA1, "a0 must precede a1 in the ordering");
        assertTrue(posB0 < posB1, "b0 must precede b1 in the ordering");
    }

    @Test
    @DisplayName("CALCULATED mode with no explicit edges produces no intra-branch ordering")
    void calculatedModeWithNoEdges() {
        var g = new CommitDependencyGraph(2, 2, IntraBranchDependencyMode.CALCULATED, List.of());
        // No intra-branch edges → 4 independent nodes → any of the 6 orderings is valid.
        assertFalse(g.hasCycle());
        List<Boolean> order = g.topologicalSort();
        assertNotNull(order);
        assertEquals(4, order.size());
    }

    @Test
    @DisplayName("CALCULATED mode respects explicitly supplied intra-branch edges")
    void calculatedModeWithExplicitEdges() {
        // Provide only the a0→a1 intra-branch edge; b0 and b1 are independent.
        var g = new CommitDependencyGraph(2, 2, IntraBranchDependencyMode.CALCULATED,
                List.of(new int[]{0, 1}));
        assertFalse(g.hasCycle());
        List<Boolean> order = g.topologicalSort();
        assertNotNull(order);
        int posA0 = order.indexOf(true);
        int posA1 = order.lastIndexOf(true);
        assertTrue(posA0 < posA1, "explicit edge a0→a1 must be respected");
    }

    @Test
    @DisplayName("addEdge creates a precedence constraint that appears in the topological order")
    void addEdgeCreatesConstraint() {
        var g = new CommitDependencyGraph(1, 1, IntraBranchDependencyMode.CALCULATED, List.of());
        // No edges yet: a0 and b0 are independent.
        // Add inter-branch edge b0→a0 (b0 must come before a0).
        g.addEdge(g.nodeB(0), g.nodeA(0));

        assertFalse(g.hasCycle());
        List<Boolean> order = g.topologicalSort();
        assertNotNull(order);
        assertEquals(2, order.size());
        // order[0] must be false (b0), order[1] must be true (a0)
        assertFalse(order.get(0), "b0 must be scheduled first");
        assertTrue(order.get(1), "a0 must be scheduled second");
    }

    @Test
    @DisplayName("addEdge is idempotent: adding the same edge twice does not alter the graph")
    void addEdgeIsIdempotent() {
        var g = new CommitDependencyGraph(2, 1, IntraBranchDependencyMode.CALCULATED, List.of());
        g.addEdge(g.nodeB(0), g.nodeA(0));
        g.addEdge(g.nodeB(0), g.nodeA(0)); // duplicate
        assertFalse(g.hasCycle());
        assertNotNull(g.topologicalSort());
    }

    @Test
    @DisplayName("Self-loop addEdge(i, i) is ignored and does not create a cycle")
    void selfLoopIsIgnored() {
        var g = new CommitDependencyGraph(2, 1, IntraBranchDependencyMode.CALCULATED, List.of());
        g.addEdge(0, 0);
        assertFalse(g.hasCycle());
    }

    @Test
    @DisplayName("hasCycle returns true for a graph with a mutual dependency")
    void hasCycleDetectsCycle() {
        var g = new CommitDependencyGraph(1, 1, IntraBranchDependencyMode.CALCULATED, List.of());
        g.addEdge(g.nodeA(0), g.nodeB(0)); // a0 → b0
        g.addEdge(g.nodeB(0), g.nodeA(0)); // b0 → a0 (cycle)
        assertTrue(g.hasCycle());
    }

    @Test
    @DisplayName("topologicalSort returns null when the graph has a cycle")
    void topologicalSortReturnsNullForCycle() {
        var g = new CommitDependencyGraph(1, 1, IntraBranchDependencyMode.CALCULATED, List.of());
        g.addEdge(g.nodeA(0), g.nodeB(0));
        g.addEdge(g.nodeB(0), g.nodeA(0));
        assertNull(g.topologicalSort());
    }

    @Test
    @DisplayName("getCyclicPairs identifies pairs with mutual dependencies")
    void getCyclicPairsReturnsMutualDependencies() {
        var g = new CommitDependencyGraph(2, 2, IntraBranchDependencyMode.CALCULATED, List.of());
        // Create a cycle between a0 and b0
        g.addEdge(g.nodeA(0), g.nodeB(0));
        g.addEdge(g.nodeB(0), g.nodeA(0));

        List<int[]> pairs = g.getCyclicPairs();
        assertEquals(1, pairs.size());
        assertEquals(0, pairs.get(0)[0], "A-index in cyclic pair should be 0");
        assertEquals(0, pairs.get(0)[1], "B-index in cyclic pair should be 0");
    }

    @Test
    @DisplayName("getCyclicPairs returns empty list when there are no cycles")
    void getCyclicPairsEmptyForAcyclicGraph() {
        var g = new CommitDependencyGraph(2, 2);
        assertTrue(g.getCyclicPairs().isEmpty());
    }

    @Test
    @DisplayName("Single A-commit and single B-commit with no edges produces two-node ordering")
    void singleCommitsEachBranch() {
        var g = new CommitDependencyGraph(1, 1, IntraBranchDependencyMode.CALCULATED, List.of());
        assertFalse(g.hasCycle());
        List<Boolean> order = g.topologicalSort();
        assertNotNull(order);
        assertEquals(2, order.size());
        // Any order is valid since nodes are independent
        assertTrue(order.contains(true) && order.contains(false));
    }
}
