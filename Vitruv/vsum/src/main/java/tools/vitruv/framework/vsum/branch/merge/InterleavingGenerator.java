package tools.vitruv.framework.vsum.branch.merge;

import java.util.ArrayList;
import java.util.List;

/**
 * Generates candidate commit orderings for interleaved merge.
 *
 * <p>An ordering is represented as a {@code List<Boolean>} of length {@code m + n},
 * where {@code true} means "take the next commit from branch A" and {@code false}
 * means "take the next commit from branch B".
 */
public class InterleavingGenerator {

    /** Maximum total commits for exhaustive (all-orderings) enumeration. */
    static final int EXHAUSTIVE_THRESHOLD = 8;

    /** Maximum number of orderings to try in heuristic mode. */
    static final int MAX_HEURISTIC_ORDERINGS = 20;

    /**
     * Generates candidate orderings of {@code m} A-commits and {@code n} B-commits.
     *
     * <p>If {@code m + n <= EXHAUSTIVE_THRESHOLD}, all C(m+n, m) orderings are returned.
     * Otherwise, a fixed heuristic set (A-first, B-first, round-robin AB, round-robin BA)
     * is returned.
     *
     * @param m number of commits from branch A
     * @param n number of commits from branch B
     * @return list of candidate orderings
     */
    public List<List<Boolean>> generate(int m, int n) {
        if (m == 0 && n == 0) return List.of(List.of());
        if (m == 0) return List.of(allFalse(n));
        if (n == 0) return List.of(allTrue(m));

        if (m + n <= EXHAUSTIVE_THRESHOLD) {
            return generateAll(m, n);
        } else {
            return generateHeuristic(m, n);
        }
    }

    /**
     * Generates all C(m+n, m) orderings by selecting positions for A-commits among all positions.
     */
    List<List<Boolean>> generateAll(int m, int n) {
        List<List<Boolean>> result = new ArrayList<>();
        boolean[] ordering = new boolean[m + n];
        generateAllHelper(ordering, 0, m, n, result);
        return result;
    }

    private void generateAllHelper(boolean[] ordering, int pos, int aLeft, int bLeft,
                                    List<List<Boolean>> result) {
        if (aLeft == 0 && bLeft == 0) {
            List<Boolean> order = new ArrayList<>(ordering.length);
            for (boolean b : ordering) order.add(b);
            result.add(order);
            return;
        }
        if (aLeft > 0) {
            ordering[pos] = true;
            generateAllHelper(ordering, pos + 1, aLeft - 1, bLeft, result);
        }
        if (bLeft > 0) {
            ordering[pos] = false;
            generateAllHelper(ordering, pos + 1, aLeft, bLeft - 1, result);
        }
    }

    /**
     * Generates a fixed heuristic set of orderings for large commit histories.
     * Orderings: A-first, B-first, round-robin AB (starting A), round-robin BA (starting B).
     */
    List<List<Boolean>> generateHeuristic(int m, int n) {
        List<List<Boolean>> result = new ArrayList<>();

        // A-first then B
        result.add(concat(allTrue(m), allFalse(n)));
        // B-first then A
        result.add(concat(allFalse(n), allTrue(m)));

        // Round-robin starting with A
        List<Boolean> rrAB = new ArrayList<>(m + n);
        int ai = 0, bi = 0;
        while (ai < m || bi < n) {
            if (ai < m) { rrAB.add(true); ai++; }
            if (bi < n) { rrAB.add(false); bi++; }
        }
        if (!result.contains(rrAB)) result.add(rrAB);

        // Round-robin starting with B
        List<Boolean> rrBA = new ArrayList<>(m + n);
        ai = 0; bi = 0;
        while (ai < m || bi < n) {
            if (bi < n) { rrBA.add(false); bi++; }
            if (ai < m) { rrBA.add(true); ai++; }
        }
        if (!result.contains(rrBA)) result.add(rrBA);

        return result;
    }

    private List<Boolean> allTrue(int count) {
        List<Boolean> list = new ArrayList<>(count);
        for (int i = 0; i < count; i++) list.add(true);
        return list;
    }

    private List<Boolean> allFalse(int count) {
        List<Boolean> list = new ArrayList<>(count);
        for (int i = 0; i < count; i++) list.add(false);
        return list;
    }

    private List<Boolean> concat(List<Boolean> a, List<Boolean> b) {
        List<Boolean> result = new ArrayList<>(a.size() + b.size());
        result.addAll(a);
        result.addAll(b);
        return result;
    }

    /**
     * Returns the single topologically-sorted ordering derived from the dependency graph,
     * or an empty list if the graph has a cycle (meaning no valid ordering exists).
     *
     * @param graph the commit dependency graph
     * @return singleton list containing the topological ordering, or empty list on cycle
     */
    public List<List<Boolean>> generateFromDependencyGraph(CommitDependencyGraph graph) {
        List<Boolean> ordering = graph.topologicalSort();
        if (ordering == null) return List.of(); // cycle
        return List.of(ordering);
    }
}
