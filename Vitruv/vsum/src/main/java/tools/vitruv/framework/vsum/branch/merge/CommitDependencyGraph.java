package tools.vitruv.framework.vsum.branch.merge;

import java.util.*;

/**
 * Directed precedence graph for commit ordering in interleaved merge.
 *
 * <p>Nodes 0..m-1 represent commits from branch A (a_0..a_{m-1}).
 * Nodes m..m+n-1 represent commits from branch B (b_0..b_{n-1}).
 *
 * <p>An edge from → to means "from must be scheduled before to".
 *
 * <p>Intra-branch ordering constraints (a_0 → a_1 → ... and b_0 → b_1 → ...) are added
 * automatically in the constructor, ensuring causal consistency within each branch.
 *
 * <p>Inter-branch edges are added by the caller based on footprint analysis:
 * if reactionFP(a_i) ∩ directFP(b_j) ≠ ∅, add edge b_j → a_i (b_j before a_i).
 *
 * <p>Uses Kahn's algorithm (BFS-based topological sort) to detect cycles and derive orderings.
 */
public class CommitDependencyGraph {

    private final int m; // number of A-commits
    private final int n; // number of B-commits
    private final int total;

    // Adjacency list: edges[v] = set of nodes that v must precede
    private final List<Set<Integer>> edges;
    // In-degree for Kahn's algorithm
    private final int[] inDegree;

    /**
     * Creates a dependency graph with sequential intra-branch ordering
     * (a_0 → a_1 → ... and b_0 → b_1 → ...).
     */
    public CommitDependencyGraph(int m, int n) {
        this(m, n, IntraBranchDependencyMode.SEQUENTIAL, List.of());
    }

    /**
     * Creates a dependency graph with configurable intra-branch ordering.
     *
     * @param m                number of A-commits
     * @param n                number of B-commits
     * @param mode             how intra-branch edges are determined
     * @param intraBranchEdges explicit intra-branch edges (used when mode is CALCULATED)
     */
    public CommitDependencyGraph(int m, int n, IntraBranchDependencyMode mode,
                                  List<int[]> intraBranchEdges) {
        this.m = m;
        this.n = n;
        this.total = m + n;
        this.edges = new ArrayList<>(total);
        this.inDegree = new int[total];
        for (int i = 0; i < total; i++) {
            edges.add(new HashSet<>());
        }
        if (mode == IntraBranchDependencyMode.SEQUENTIAL) {
            // Add sequential intra-branch ordering: a_0 → a_1 → ... and b_0 → b_1 → ...
            for (int i = 0; i < m - 1; i++) addEdge(i, i + 1);
            for (int j = 0; j < n - 1; j++) addEdge(m + j, m + j + 1);
        } else {
            // Add only the explicitly computed intra-branch edges
            for (int[] edge : intraBranchEdges) addEdge(edge[0], edge[1]);
        }
    }

    /** Returns the node index for A-commit i (0-indexed). */
    public int nodeA(int i) { return i; }

    /** Returns the node index for B-commit j (0-indexed). */
    public int nodeB(int j) { return m + j; }

    /** Adds a directed edge "from must precede to". Idempotent. */
    public void addEdge(int from, int to) {
        if (from == to) return;
        if (edges.get(from).add(to)) {
            inDegree[to]++;
        }
    }

    /** Returns true if the graph has a cycle (no valid topological ordering exists). */
    public boolean hasCycle() {
        return topologicalOrderInternal() == null;
    }

    /**
     * Performs a topological sort (Kahn's algorithm) and returns the resulting
     * commit ordering as a list of booleans (true=from A, false=from B).
     *
     * @return ordering list, or null if the graph has a cycle
     */
    public List<Boolean> topologicalSort() {
        List<Integer> order = topologicalOrderInternal();
        if (order == null) return null;
        List<Boolean> result = new ArrayList<>(total);
        for (int node : order) {
            result.add(node < m); // true if A-commit, false if B-commit
        }
        return result;
    }

    /**
     * Returns the pairs of (A-commit index, B-commit index) that are involved in cycles.
     * Used for error reporting when INTERLEAVING_CONFLICT is generated.
     */
    public List<int[]> getCyclicPairs() {
        List<int[]> pairs = new ArrayList<>();
        for (int ai = 0; ai < m; ai++) {
            for (int bj = 0; bj < n; bj++) {
                int bNode = m + bj;
                // Check for mutual dependency: ai→bNode and bNode→ai
                if (edges.get(ai).contains(bNode) && edges.get(bNode).contains(ai)) {
                    pairs.add(new int[]{ai, bj});
                }
            }
        }
        return pairs;
    }

    private List<Integer> topologicalOrderInternal() {
        // Copy in-degrees for this run
        int[] deg = Arrays.copyOf(inDegree, total);
        Queue<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < total; i++) {
            if (deg[i] == 0) queue.add(i);
        }
        List<Integer> result = new ArrayList<>(total);
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            result.add(cur);
            for (int next : edges.get(cur)) {
                if (--deg[next] == 0) queue.add(next);
            }
        }
        return result.size() == total ? result : null; // null = cycle detected
    }
}
