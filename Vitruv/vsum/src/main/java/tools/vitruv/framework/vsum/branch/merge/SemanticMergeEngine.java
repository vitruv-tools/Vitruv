package tools.vitruv.framework.vsum.branch.merge;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jgit.api.errors.GitAPIException;
import tools.vitruv.change.atomic.EChange;
import tools.vitruv.change.atomic.hid.HierarchicalId;
import tools.vitruv.change.atomic.uuid.Uuid;
import tools.vitruv.change.atomic.uuid.UuidResolver;
import tools.vitruv.change.composite.description.PropagatedChange;
import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.change.composite.propagation.ChangePropagationListener;
import tools.vitruv.change.interaction.InteractionResultProvider;
import tools.vitruv.change.propagation.ChangePropagationSpecification;
import tools.vitruv.framework.vsum.branch.SemanticConflictDetector;
import tools.vitruv.framework.vsum.branch.storage.SemanticChangeEntry;
import tools.vitruv.framework.vsum.branch.storage.SemanticChangelogManager;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Core semantic three-way merge engine.
 *
 * <p>Replays serialized EChange transactions from the source branch onto the target
 * branch's VSUM. The pipeline is:
 * <ol>
 *   <li>Extract base/ours/theirs states via JGit TreeWalk</li>
 *   <li>Load changelog DTOs from extracted temp dirs</li>
 *   <li>UUID-based conflict detection on DTOs</li>
 *   <li>Filter DTOs by conflict resolutions (ours/theirs choice)</li>
 *   <li>Deserialize filtered DTOs into live {@code EChange<HierarchicalId>} objects</li>
 *   <li>Replay: {@code resolveAndApply → assignIds → propagateChange}</li>
 * </ol>
 *
 * <p>The replay step follows the same pipeline as
 * {@code IdentityMappingViewType.commitViewChanges()} (lines 96-109).
 * Reactions fire automatically during {@code propagateChange()}.
 */
public class SemanticMergeEngine {

    private static final Logger LOGGER = LogManager.getLogger(SemanticMergeEngine.class);

    private final Path repoRoot;
    private final Collection<ChangePropagationSpecification> specs;
    private final InteractionResultProvider interactionProvider;
    private final ConflictResolutionProvider conflictResolutionProvider;
    private final IntraBranchDependencyMode intraBranchMode;
    /**
     * When {@code true}, the UUID-based conflict detection pass inside {@link #merge} is skipped.
     * Set by {@link SemanticConflictDetector} before delegating steps 5-9, since steps 1-4
     * already performed direct conflict detection on the same data.
     * -- SETTER --
     *  Instructs this engine to skip the UUID-based direct conflict detection pass in
     * . Call this before delegating from
     *  so the engine does not redo work that steps 1-4 already performed. Reset to
     *  in a
     *  block after the merge call returns.
     *

     */
    @Setter
    private boolean skipDirectConflictDetection = false;

    public SemanticMergeEngine(Path repoRoot,
                                Collection<ChangePropagationSpecification> specs,
                                InteractionResultProvider interactionProvider) {
        this(repoRoot, specs, interactionProvider, null, IntraBranchDependencyMode.CALCULATED);
    }

    public SemanticMergeEngine(Path repoRoot,
                                Collection<ChangePropagationSpecification> specs,
                                InteractionResultProvider interactionProvider,
                                ConflictResolutionProvider conflictResolutionProvider) {
        this(repoRoot, specs, interactionProvider, conflictResolutionProvider,
                IntraBranchDependencyMode.CALCULATED);
    }

    public SemanticMergeEngine(Path repoRoot,
                                Collection<ChangePropagationSpecification> specs,
                                InteractionResultProvider interactionProvider,
                                ConflictResolutionProvider conflictResolutionProvider,
                                IntraBranchDependencyMode intraBranchMode) {
        this.repoRoot = repoRoot;
        this.specs = specs;
        this.interactionProvider = interactionProvider;
        this.conflictResolutionProvider = conflictResolutionProvider;
        this.intraBranchMode = intraBranchMode;
    }

    /**
     * Returns a new engine with the same configuration but with the given
     * {@link ConflictResolutionProvider} substituted in. Use this to obtain a
     * provider-aware engine from a shared base engine without mutating the original.
     *
     * @param provider the conflict resolution provider to use; must not be {@code null}.
     * @return a new {@link SemanticMergeEngine} that consults {@code provider} during replay.
     */
    public SemanticMergeEngine withConflictResolutionProvider(ConflictResolutionProvider provider) {
        return new SemanticMergeEngine(repoRoot, specs, interactionProvider, provider,
                intraBranchMode);
    }

    /**
     * Performs a bidirectional merge between two branches.
     *
     * <p>First attempts A→B (replay A onto B). If indirect conflicts are detected
     * (derived(A) vs user(B)), attempts the reverse direction B→A. If B→A is clean,
     * uses that result. If both directions produce indirect conflicts, escalates to
     * a true blocking conflict.
     *
     * <p>This avoids the inconsistency problem where discarding derived(A) in A→B
     * leaves reactions unexecuted. In B→A, user(B)'s changes are replayed and
     * reactions fire naturally.
     *
     * @param baseSha    common ancestor commit SHA
     * @param branchASha commit SHA of branch A
     * @param branchBSha commit SHA of branch B
     * @return the merge result, with {@link SemanticMergeResult.MergeDirection} indicating
     *         which direction was used
     */
    public SemanticMergeResult mergeBidirectional(String baseSha, String branchASha, String branchBSha)
            throws IOException, GitAPIException {

        LOGGER.info("Bidirectional merge: base={}, A={}, B={}",
                baseSha.substring(0, 7), branchASha.substring(0, 7), branchBSha.substring(0, 7));

        MergeTracer.trace("");
        MergeTracer.section("MERGE TRACE: Bidirectional merge");
        MergeTracer.trace("  Base: " + baseSha.substring(0, 7)
                + "  |  Branch A: " + branchASha.substring(0, 7)
                + "  |  Branch B: " + branchBSha.substring(0, 7));

        // 1. Try forward: replay A onto B (ours=B, theirs=A)
        MergeTracer.trace("[BIDIR] Step 1: Attempting forward merge (A→B)...");
        SemanticMergeResult forwardResult = merge(baseSha, branchBSha, branchASha);

        // 2. If direct conflicts with no resolver, return immediately
        if (!forwardResult.isSuccess()) {
            MergeTracer.trace("[BIDIR] Forward merge (A→B) failed with direct conflicts -- aborting");
            return forwardResult;
        }

        // 3. Check for indirect conflicts in forward direction
        List<MergeConflict> forwardIndirect = forwardResult.getWarnings().stream()
                .filter(w -> w.getType() == MergeConflict.ConflictType.INDIRECT_CONFLICT)
                .toList();

        if (forwardIndirect.isEmpty()) {
            LOGGER.info("Forward merge (A→B) clean -- no indirect conflicts");
            MergeTracer.trace("[BIDIR] Forward merge (A→B) clean -- no indirect conflicts");
            MergeTracer.trace("[BIDIR] Using forward result");
            return forwardResult;
        }

        LOGGER.info("Forward merge (A→B) has {} indirect conflict(s) -- attempting reverse (B→A)",
                forwardIndirect.size());
        MergeTracer.trace("[BIDIR] Forward merge (A→B) has " + forwardIndirect.size()
                + " indirect conflict(s) -- attempting reverse (B→A)");

        // 4. Try reverse: replay B onto A (ours=A, theirs=B)
        //    For the reverse direction, we need to invert the conflict resolution provider
        //    because ours/theirs roles are swapped.
        SemanticMergeEngine reverseEngine = new SemanticMergeEngine(
                repoRoot, specs, interactionProvider,
                invertResolutionProvider(conflictResolutionProvider));
        SemanticMergeResult reverseResult = reverseEngine.merge(baseSha, branchASha, branchBSha);

        // 5. If reverse had direct conflicts (no resolver), return forward result as-is
        //    (direct conflicts are symmetric, so this shouldn't happen if forward succeeded)
        if (!reverseResult.isSuccess()) {
            LOGGER.warn("Reverse merge (B→A) failed with direct conflicts -- returning forward result");
            return forwardResult;
        }

        // 6. Check for indirect conflicts in reverse direction
        List<MergeConflict> reverseIndirect = reverseResult.getWarnings().stream()
                .filter(w -> w.getType() == MergeConflict.ConflictType.INDIRECT_CONFLICT)
                .toList();

        if (reverseIndirect.isEmpty()) {
            LOGGER.info("Reverse merge (B→A) clean -- using reversed result");
            MergeTracer.trace("[BIDIR] Reverse merge (B→A) clean -- using REVERSED result");
            // Return reverse result annotated as REVERSED
            List<MergeConflict> reverseWarnings = reverseResult.getWarnings();
            if (!reverseResult.getAppliedResolutions().isEmpty()) {
                return SemanticMergeResult.successWithResolutions(
                        reverseResult.getAppliedResolutions(),
                        reverseResult.getAppliedChanges(),
                        reverseWarnings,
                        reverseResult.getMergedStateFolder(),
                        SemanticMergeResult.MergeDirection.REVERSED);
            }
            return SemanticMergeResult.success(
                    reverseResult.getAppliedChanges(),
                    reverseWarnings,
                    reverseResult.getMergedStateFolder(),
                    SemanticMergeResult.MergeDirection.REVERSED);
        }

        // 7. Both directions have indirect conflicts -- true conflict
        LOGGER.warn("Both directions have indirect conflicts -- escalating to true conflict");
        MergeTracer.trace("[BIDIR] Both directions have indirect conflicts → BIDIRECTIONAL_INDIRECT_CONFLICT");
        List<MergeConflict> bidirectionalConflicts = new ArrayList<>();
        for (MergeConflict ic : forwardIndirect) {
            bidirectionalConflicts.add(new MergeConflict(
                    ic.getElementId(),
                    MergeConflict.ConflictType.BIDIRECTIONAL_INDIRECT_CONFLICT,
                    ic.getElementUuid(), ic.getConflictingFeature(),
                    ic.getBaseValue(), ic.getOursValue(), ic.getTheirsValue()));
        }
        for (MergeConflict ic : reverseIndirect) {
            bidirectionalConflicts.add(new MergeConflict(
                    ic.getElementId(),
                    MergeConflict.ConflictType.BIDIRECTIONAL_INDIRECT_CONFLICT,
                    ic.getElementUuid(), ic.getConflictingFeature(),
                    ic.getBaseValue(), ic.getOursValue(), ic.getTheirsValue()));
        }
        return SemanticMergeResult.conflict(bidirectionalConflicts);
    }

    /**
     * Creates an inverting wrapper around a {@link ConflictResolutionProvider} that
     * flips OURS↔THEIRS choices. Used for reverse-direction merges where the
     * ours/theirs roles are swapped.
     */
    private static ConflictResolutionProvider invertResolutionProvider(
            ConflictResolutionProvider provider) {
        if (provider == null) return null;
        return conflicts -> provider.resolve(conflicts).stream()
                .map(r -> new ConflictResolution(r.elementUuid(),
                        r.choice() == ConflictResolution.Choice.OURS
                                ? ConflictResolution.Choice.THEIRS
                                : ConflictResolution.Choice.OURS))
                .toList();
    }

    /**
     * Performs an interleaved merge of two branches by trying different commit orderings
     * from the common base.
     *
     * <p>Instead of replaying all A commits then all B commits (or vice versa), this method
     * tries different interleavings of commits from A and B, starting fresh from the base
     * state for each candidate ordering. The first ordering that produces no indirect
     * conflicts is returned as the merged result.
     *
     * <p>If no ordering avoids indirect conflicts, returns a conflict with type
     * {@link MergeConflict.ConflictType#INTERLEAVING_CONFLICT}.
     *
     * @param baseSha    common ancestor commit SHA
     * @param branchASha commit SHA of branch A
     * @param branchBSha commit SHA of branch B
     * @return the merge result with {@link SemanticMergeResult.MergeDirection#INTERLEAVED}
     *         if a clean ordering is found, or a conflict otherwise
     */
    public SemanticMergeResult mergeWithInterleaving(String baseSha, String branchASha, String branchBSha)
            throws IOException, org.eclipse.jgit.api.errors.GitAPIException {

        LOGGER.info("Interleaving merge: base={}, A={}, B={}",
                baseSha.substring(0, 7), branchASha.substring(0, 7), branchBSha.substring(0, 7));

        MergeTracer.trace("");
        MergeTracer.section("MERGE TRACE: Interleaving merge (dependency-graph-based)");
        MergeTracer.trace("  Base: " + baseSha.substring(0, 7)
                + "  |  Branch A: " + branchASha.substring(0, 7)
                + "  |  Branch B: " + branchBSha.substring(0, 7));

        GitStateLoader loader = new GitStateLoader(repoRoot);

        Path baseDir = GitStateLoader.createTempDir("interleave-base-");
        Path aDirFull = GitStateLoader.createTempDir("interleave-a-");
        Path bDirFull = GitStateLoader.createTempDir("interleave-b-");

        loader.checkoutStateAtCommit(baseSha, baseDir);
        loader.checkoutStateAtCommit(branchASha, aDirFull);
        loader.checkoutStateAtCommit(branchBSha, bDirFull);

        List<List<SemanticChangeLog.ChangeDto>> aTransactions = loadTransactionsFromDir(aDirFull);
        List<List<SemanticChangeLog.ChangeDto>> bTransactions = loadTransactionsFromDir(bDirFull);

        // Load UUID mappings for UUID-based element resolution fallback
        Map<String, String> aUuidMappings = loadUuidMappingsFromDir(aDirFull);
        Map<String, String> bUuidMappings = loadUuidMappingsFromDir(bDirFull);
        // Merge and reverse: hid → uuid (for both branches)
        Map<String, String> allUuidMappings = new HashMap<>(aUuidMappings);
        allUuidMappings.putAll(bUuidMappings);
        Map<String, String> hidToUuid = reverseUuidMappings(allUuidMappings);

        List<SemanticChangeLog.ChangeDto> aDtos = aTransactions.stream().flatMap(List::stream).toList();
        List<SemanticChangeLog.ChangeDto> bDtos = bTransactions.stream().flatMap(List::stream).toList();

        // Check direct (MODIFY_MODIFY) conflicts -- cannot be resolved by reordering
        UuidConflictDetector detector = new UuidConflictDetector();
        List<MergeConflict> directConflicts = detector.detectConflicts(aDtos, bDtos);
        if (!directConflicts.isEmpty() && conflictResolutionProvider == null) {
            LOGGER.warn("Interleaving merge aborted: {} direct conflicts", directConflicts.size());
            return SemanticMergeResult.conflict(directConflicts);
        }

        int m = aTransactions.size();
        int n = bTransactions.size();

        if (m == 0 && n == 0) {
            return SemanticMergeResult.success(List.of(), baseDir);
        }

        // Compute direct footprints (free -- from changelog DTOs)
        List<Set<String>> aDirectFP = aTransactions.stream()
                .map(this::collectUuidFootprints).toList();
        List<Set<String>> bDirectFP = bTransactions.stream()
                .map(this::collectUuidFootprints).toList();

        // Collect all known UUIDs from both branches' changelogs.
        // Only footprints for these elements matter for dependency analysis -- elements
        // created by reactions get new random UUIDs that can't conflict across branches.
        Set<String> knownUuids = new HashSet<>();
        for (var dto : aDtos) { if (dto.affectedElementUuid != null) knownUuids.add(dto.affectedElementUuid); }
        for (var dto : bDtos) { if (dto.affectedElementUuid != null) knownUuids.add(dto.affectedElementUuid); }

        // Load stored consequential footprints (captured at commit time)
        List<Set<String>> aStoredFP = loadStoredFootprintsFromDir(aDirFull);
        List<Set<String>> bStoredFP = loadStoredFootprintsFromDir(bDirFull);

        boolean hasStoredFP = (aStoredFP.size() == m && bStoredFP.size() == n
                && aStoredFP.stream().noneMatch(Objects::isNull)
                && bStoredFP.stream().noneMatch(Objects::isNull));

        List<Set<String>> aReactionFP = new ArrayList<>();
        List<Set<String>> bReactionFP = new ArrayList<>();
        boolean depAnalysisOk = true;

        if (!hasStoredFP) {
            LOGGER.warn("Changelogs do not contain stored consequential footprints. "
                    + "Ensure changelogs are created with ChangeLogCapture.drainConsequentialFootprints().");
            // Fall back to empty footprints -- the fixpoint loop will discover them via replay
            MergeTracer.trace("[INTERLEAVE] No stored footprints -- starting with empty estimates for "
                    + m + " A-commits and " + n + " B-commits");
            for (int i = 0; i < m; i++) aReactionFP.add(new HashSet<>());
            for (int j = 0; j < n; j++) bReactionFP.add(new HashSet<>());
        } else {
            // Use stored footprints (fast path -- no replay needed).
            // Filter to only include elements with UUIDs known from changelogs.
            MergeTracer.trace("[INTERLEAVE] Using stored consequential footprints for "
                    + m + " A-commits and " + n + " B-commits");
            for (int i = 0; i < m; i++) {
                Set<String> filtered = filterFootprintsByKnownUuids(aStoredFP.get(i), knownUuids);
                aReactionFP.add(filtered);
            }
            for (int j = 0; j < n; j++) {
                Set<String> filtered = filterFootprintsByKnownUuids(bStoredFP.get(j), knownUuids);
                bReactionFP.add(filtered);
            }
        }

        // Iterative fixpoint loop
        // Runtime-discovered dependency edges from guard failures (carried across iterations)
        List<int[]> runtimeEdges = new ArrayList<>();
        int maxIterations = m + n + 2;
        for (int iteration = 0; iteration < maxIterations; iteration++) {
            LOGGER.info("[INTERLEAVE] Iteration {} / {}", iteration + 1, maxIterations);
            MergeTracer.trace("[INTERLEAVE] Dependency-graph iteration " + (iteration + 1));

            // Build dependency graph with configurable intra-branch edges
            List<int[]> intraBranchEdges = (intraBranchMode == IntraBranchDependencyMode.CALCULATED)
                    ? computeIntraBranchEdges(m, n, aDirectFP, bDirectFP, aReactionFP, bReactionFP)
                    : List.of(); // sequential mode adds edges in the constructor
            CommitDependencyGraph graph = new CommitDependencyGraph(m, n, intraBranchMode, intraBranchEdges);

            // Add inter-branch edges based on footprint overlaps
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    // If a_i's reaction touches b_j's direct changes: a_i must precede b_j.
                    Set<String> aRxnOverlapBDirect = new HashSet<>(aReactionFP.get(i));
                    aRxnOverlapBDirect.retainAll(bDirectFP.get(j));
                    if (!aRxnOverlapBDirect.isEmpty()) {
                        graph.addEdge(graph.nodeA(i), graph.nodeB(j));
                    }

                    // If b_j's reaction touches a_i's direct changes: b_j must precede a_i.
                    Set<String> bRxnOverlapADirect = new HashSet<>(bReactionFP.get(j));
                    bRxnOverlapADirect.retainAll(aDirectFP.get(i));
                    if (!bRxnOverlapADirect.isEmpty()) {
                        graph.addEdge(graph.nodeB(j), graph.nodeA(i));
                    }
                }
            }

            // Add runtime-discovered dependency edges from prior guard failures
            for (int[] edge : runtimeEdges) {
                graph.addEdge(edge[0], edge[1]);
            }

            // Check for cycle
            if (graph.hasCycle()) {
                LOGGER.warn("[INTERLEAVE] Dependency graph has cycle → INTERLEAVING_CONFLICT");
                MergeTracer.trace("[INTERLEAVE] Dependency graph cycle → INTERLEAVING_CONFLICT");
                List<MergeConflict> conflicts = new ArrayList<>();
                for (int[] pair : graph.getCyclicPairs()) {
                    int ai = pair[0], bj = pair[1];
                    Set<String> overlap = new HashSet<>(aReactionFP.get(ai));
                    overlap.retainAll(bDirectFP.get(bj));
                    Set<String> overlapB = new HashSet<>(bReactionFP.get(bj));
                    overlapB.retainAll(aDirectFP.get(ai));
                    overlap.addAll(overlapB);
                    for (String fp : overlap) {
                        String[] parts = fp.split("#", 2);
                        conflicts.add(new MergeConflict(
                                parts[0], MergeConflict.ConflictType.INTERLEAVING_CONFLICT,
                                parts[0], parts.length > 1 ? parts[1] : null,
                                null, null, null));
                    }
                }
                if (conflicts.isEmpty()) {
                    conflicts.add(new MergeConflict("unknown",
                            MergeConflict.ConflictType.INTERLEAVING_CONFLICT,
                            null, null, null, null, null));
                }
                return SemanticMergeResult.conflict(conflicts);
            }

            // Topological sort → proposed ordering
            List<Boolean> ordering = graph.topologicalSort();
            MergeTracer.trace("[INTERLEAVE] Proposed ordering: " + orderingToString(ordering));

            // Execute ordering, capture actual reaction footprints per step
            Path tryDir = GitStateLoader.createTempDir("interleave-try-");
            copyDirectory(baseDir, tryDir);

            InterleavingReplayResult replayResult = tryInterleavingWithFootprintCapture(
                    tryDir, aTransactions, bTransactions, ordering,
                    aDtos, bDtos, knownUuids, hidToUuid);

            // Guard failure: the graph-proposed ordering makes some commit inapplicable.
            // Add the discovered dependency edge and retry instead of falling back to enumeration.
            if (replayResult.inapplicable()) {
                GuardFailureInfo gf = replayResult.guardFailure();
                if (gf != null && gf.lastOtherBranchCommitIndex() >= 0) {
                    int fromNode = gf.failedFromA()
                            ? graph.nodeA(gf.failedCommitIndex())
                            : graph.nodeB(gf.failedCommitIndex());
                    int toNode = gf.failedFromA()
                            ? graph.nodeB(gf.lastOtherBranchCommitIndex())
                            : graph.nodeA(gf.lastOtherBranchCommitIndex());
                    int[] newEdge = {fromNode, toNode};

                    // Check if this exact edge was already added (no progress possible)
                    boolean duplicate = runtimeEdges.stream()
                            .anyMatch(e -> e[0] == newEdge[0] && e[1] == newEdge[1]);

                    if (!duplicate) {
                        runtimeEdges.add(newEdge);
                        LOGGER.info("[INTERLEAVE] Guard failure: adding runtime edge "
                                + "{}[{}] → {}[{}] -- retrying",
                                gf.failedFromA() ? "A" : "B", gf.failedCommitIndex(),
                                gf.failedFromA() ? "B" : "A", gf.lastOtherBranchCommitIndex());
                        MergeTracer.trace("[INTERLEAVE] Guard failure → runtime edge "
                                + (gf.failedFromA() ? "A" : "B") + "[" + gf.failedCommitIndex() + "]"
                                + " → "
                                + (gf.failedFromA() ? "B" : "A") + "[" + gf.lastOtherBranchCommitIndex() + "]");
                        continue; // Retry with updated graph
                    }

                    LOGGER.warn("[INTERLEAVE] Duplicate runtime edge -- no progress. "
                            + "Falling back to enumeration");
                } else {
                    LOGGER.warn("[INTERLEAVE] Guard failure without identifiable dependency. "
                            + "Falling back to enumeration");
                }
                MergeTracer.trace("[INTERLEAVE] Guard failure → enumeration fallback");
                return mergeWithInterleavingEnumeration(baseDir, aTransactions, bTransactions,
                        aDtos, bDtos, m, n, hidToUuid);
            }

            // Check whether the actual consequential footprints from this replay match the
            // estimates used to build the dependency graph. If they do, the graph was correct
            // for this ordering and the merge is complete. If not, the estimates are enlarged
            // (monotone union) and the graph must be rebuilt with the new footprints.
            boolean footprintsStabilized = true;
            for (int i = 0; i < m; i++) {
                Set<String> actual = replayResult.actualAReactionFP().get(i);
                if (actual != null && !aReactionFP.get(i).containsAll(actual)) {
                    LOGGER.info("[INTERLEAVE] A[{}] gained new reaction footprint entries: {}",
                            i, minus(actual, aReactionFP.get(i)));
                    // Enlarge the estimate (monotone union) -- footprints only grow, never shrink
                    aReactionFP.get(i).addAll(actual);
                    footprintsStabilized = false;
                }
            }
            for (int j = 0; j < n; j++) {
                Set<String> actual = replayResult.actualBReactionFP().get(j);
                if (actual != null && !bReactionFP.get(j).containsAll(actual)) {
                    LOGGER.info("[INTERLEAVE] B[{}] gained new reaction footprint entries: {}",
                            j, minus(actual, bReactionFP.get(j)));
                    bReactionFP.get(j).addAll(actual);
                    footprintsStabilized = false;
                }
            }

            if (footprintsStabilized) {
                // Estimates matched actuals: the dependency graph was correct for this ordering,
                // and all commits replayed successfully. The merge is complete.
                LOGGER.info("[INTERLEAVE] Footprints stabilized at iteration {}", iteration + 1);
                MergeTracer.trace("[INTERLEAVE] Footprints stabilized at iteration " + (iteration + 1));
                SemanticMergeResult result = replayResult.result();
                return SemanticMergeResult.success(
                        result.getAppliedChanges(), List.of(),
                        result.getMergedStateFolder(),
                        SemanticMergeResult.MergeDirection.INTERLEAVED);
            }
            MergeTracer.trace("[INTERLEAVE] New reaction footprints discovered -- re-sorting");
        }

        // Max iterations exceeded
        LOGGER.warn("[INTERLEAVE] Max iterations ({}) exceeded", maxIterations);
        return SemanticMergeResult.conflict(List.of(new MergeConflict("unknown",
                MergeConflict.ConflictType.INTERLEAVING_CONFLICT, null, null, null, null, null)));
    }

    /**
     * Result of a single interleaving attempt, including per-commit actual reaction footprints.
     *
     * @param inapplicable true if at least one commit in this ordering failed to apply (guard
     *                     failure / element not found). The result field is not meaningful in
     *                     that case. The ordering should be discarded and another tried.
     */
    private record InterleavingReplayResult(
            SemanticMergeResult result,
            Map<Integer, Set<String>> actualAReactionFP,
            Map<Integer, Set<String>> actualBReactionFP,
            boolean inapplicable,
            GuardFailureInfo guardFailure
    ) {}

    /**
     * Captures which commit failed during replay and which preceding
     * other-branch commit is the likely cause.  Used to add a runtime
     * dependency edge so the graph can be re-sorted without falling
     * back to full enumeration.
     */
    private record GuardFailureInfo(
            boolean failedFromA,
            int failedCommitIndex,
            int lastOtherBranchCommitIndex
    ) {}

    private InterleavingReplayResult tryInterleavingWithFootprintCapture(
            Path baseWorkDir,
            List<List<SemanticChangeLog.ChangeDto>> aTransactions,
            List<List<SemanticChangeLog.ChangeDto>> bTransactions,
            List<Boolean> ordering,
            List<SemanticChangeLog.ChangeDto> allADtos,
            List<SemanticChangeLog.ChangeDto> allBDtos,
            Set<String> knownUuids,
            Map<String, String> hidToUuid) throws IOException {

        String uriPrefix = org.eclipse.emf.common.util.URI.createFileURI(
                baseWorkDir.toAbsolutePath().toString()).toString();

        InternalVirtualModel vsum = GitStateLoader.loadVsumFromDir(baseWorkDir, specs, interactionProvider);

        List<EChange<HierarchicalId>> allApplied = new ArrayList<>();

        Map<Integer, Set<String>> actualAReactionFP = new HashMap<>();
        Map<Integer, Set<String>> actualBReactionFP = new HashMap<>();

        int aIdx = 0, bIdx = 0;
        boolean lastStepFromA = false;
        int lastTxnIndex = -1;

        try {
            for (int step = 0; step < ordering.size(); step++) {
                boolean fromA = ordering.get(step);
                lastStepFromA = fromA;
                List<SemanticChangeLog.ChangeDto> txnDtos;
                int txnIndex;

                if (fromA) {
                    if (aIdx >= aTransactions.size()) continue;
                    txnIndex = aIdx;
                    lastTxnIndex = txnIndex;
                    txnDtos = aTransactions.get(aIdx++);
                } else {
                    if (bIdx >= bTransactions.size()) continue;
                    txnIndex = bIdx;
                    lastTxnIndex = txnIndex;
                    txnDtos = bTransactions.get(bIdx++);
                }

                if (txnDtos.isEmpty()) continue;

                ChangeDtoDeserializer deserializer = new ChangeDtoDeserializer(null, uriPrefix);
                List<EChange<HierarchicalId>> txnChanges = deserializer.deserializeAll(txnDtos);
                if (txnChanges.isEmpty()) continue;

                DerivedChangeCapture derivedCapture = new DerivedChangeCapture();
                vsum.addChangePropagationListener(derivedCapture);

                replayChanges(vsum, txnChanges, hidToUuid);

                vsum.removeChangePropagationListener(derivedCapture);
                allApplied.addAll(txnChanges);

                // Record actual reaction footprint for this commit.
                // Filter to only include elements with stable UUIDs from changelogs.
                // Newly created elements get random UUIDs that differ per replay iteration.
                Set<String> actualFP = extractFootprintsFromCapture(
                        derivedCapture.getDerivedChanges(), vsum.getUuidResolver());
                actualFP.removeIf(fp -> {
                    String uuid = fp.contains("#") ? fp.substring(0, fp.indexOf('#')) : fp;
                    return !knownUuids.contains(uuid);
                });
                if (fromA) {
                    actualAReactionFP.put(txnIndex, actualFP);
                } else {
                    actualBReactionFP.put(txnIndex, actualFP);
                }
            }
        } catch (Exception e) {
            LOGGER.warn("[INTERLEAVE] Ordering {} is inapplicable (guard failure): {}",
                    orderingToString(ordering), e.getMessage());
            MergeTracer.trace("[INTERLEAVE] Guard failure: " + e.getClass().getSimpleName()
                    + ": " + e.getMessage());
            vsum.dispose();
            int lastOtherBranchIdx = lastStepFromA ? (bIdx - 1) : (aIdx - 1);
            GuardFailureInfo gf = (lastOtherBranchIdx >= 0)
                    ? new GuardFailureInfo(lastStepFromA, lastTxnIndex, lastOtherBranchIdx)
                    : null;
            return new InterleavingReplayResult(null, actualAReactionFP, actualBReactionFP, true, gf);
        }

        vsum.dispose();

        SemanticMergeResult result = SemanticMergeResult.success(allApplied, List.of(), baseWorkDir);
        return new InterleavingReplayResult(result, actualAReactionFP, actualBReactionFP, false, null);
    }

    private SemanticMergeResult mergeWithInterleavingEnumeration(
            Path baseDir,
            List<List<SemanticChangeLog.ChangeDto>> aTransactions,
            List<List<SemanticChangeLog.ChangeDto>> bTransactions,
            List<SemanticChangeLog.ChangeDto> aDtos,
            List<SemanticChangeLog.ChangeDto> bDtos,
            int m, int n,
            Map<String, String> hidToUuid) throws IOException {

        // Generate candidate orderings
        InterleavingGenerator generator = new InterleavingGenerator();
        List<List<Boolean>> orderings = generator.generate(m, n);
        LOGGER.info("[FALLBACK] Testing {} interleaving ordering(s)", orderings.size());

        for (int oi = 0; oi < orderings.size(); oi++) {
            List<Boolean> ordering = orderings.get(oi);
            LOGGER.info("[FALLBACK] Trying ordering {}/{}: {}", oi + 1, orderings.size(),
                    orderingToString(ordering));
            MergeTracer.trace("[FALLBACK] Trying ordering " + (oi + 1) + "/" + orderings.size()
                    + ": " + orderingToString(ordering));

            Path tryDir = GitStateLoader.createTempDir("interleave-try-");
            copyDirectory(baseDir, tryDir);

            SemanticMergeResult result = tryInterleaving(
                    tryDir, aTransactions, bTransactions, ordering,
                    aDtos, bDtos, hidToUuid);

            if (result == null) {
                // Guard failure: this ordering makes some commit inapplicable -- skip it
                LOGGER.info("[FALLBACK] Ordering {}/{} is inapplicable -- skipping", oi + 1, orderings.size());
                MergeTracer.trace("[FALLBACK] Ordering " + (oi + 1) + " inapplicable (guard failure) -- skipped");
                continue;
            }

            if (result.isSuccess()) {
                LOGGER.info("[FALLBACK] Found clean interleaving at ordering {}/{}", oi + 1, orderings.size());
                MergeTracer.trace("[FALLBACK] Clean ordering found at attempt " + (oi + 1));
                return SemanticMergeResult.success(
                        result.getAppliedChanges(),
                        List.of(),
                        result.getMergedStateFolder(),
                        SemanticMergeResult.MergeDirection.INTERLEAVED);
            }
        }

        LOGGER.warn("[FALLBACK] No clean interleaving found -- escalating to INTERLEAVING_CONFLICT");
        MergeTracer.trace("[FALLBACK] No clean ordering found → INTERLEAVING_CONFLICT");

        return SemanticMergeResult.conflict(List.of(new MergeConflict(
                "unknown", MergeConflict.ConflictType.INTERLEAVING_CONFLICT,
                null, null, null, null, null)));
    }

    private static Set<String> minus(Set<String> a, Set<String> b) {
        Set<String> result = new HashSet<>(a);
        result.removeAll(b);
        return result;
    }

    /**
     * Extracts reaction footprints from a set of {@link PropagatedChange} objects captured
     * during a replay.
     *
     * @param derivedChanges the propagated changes captured during replay
     * @param uuidResolver   UUID resolver for the VSUM used during replay
     * @return set of UUID#feature strings written by reactions
     */
    static Set<String> extractFootprintsFromCapture(
            List<PropagatedChange> derivedChanges,
            UuidResolver uuidResolver) {

        Set<String> footprints = new HashSet<>();
        for (PropagatedChange pc : derivedChanges) {
            VitruviusChange<EObject> consequential = pc.getConsequentialChanges();
            if (consequential == null || !consequential.containsConcreteChange()) continue;
            for (EChange<EObject> ec : consequential.getEChanges()) {
                if (!(ec instanceof tools.vitruv.change.atomic.feature.FeatureEChange<EObject, ?> fc))
                    continue;
                EObject element = fc.getAffectedElement();
                String featureName = fc.getAffectedFeature() != null
                        ? fc.getAffectedFeature().getName() : null;
                if (element == null || featureName == null) continue;
                try {
                    String uuid = uuidResolver.getUuid(element).toString();
                    footprints.add(uuid + "#" + featureName);
                } catch (IllegalStateException e) {
                    // Element not in resolver (transiently created) -- skip
                }
            }
        }
        return footprints;
    }

    /**
     * Filters a set of UUID#feature footprints to only include entries whose UUID
     * is in the known set. This removes footprints for elements created by reactions
     * that get new random UUIDs on each replay iteration.
     */
    private static Set<String> filterFootprintsByKnownUuids(Set<String> footprints, Set<String> knownUuids) {
        Set<String> filtered = new HashSet<>();
        for (String fp : footprints) {
            String uuid = fp.contains("#") ? fp.substring(0, fp.indexOf('#')) : fp;
            if (knownUuids.contains(uuid)) {
                filtered.add(fp);
            }
        }
        return filtered;
    }

    /**
     * Computes intra-branch dependency edges from footprint overlaps.
     * Two commits on the same branch need an ordering edge if:
     * <ul>
     *   <li>Write-write: both modify the same element-feature pair</li>
     *   <li>Consequential-write: earlier commit's reaction writes what later commit originally changes</li>
     *   <li>Write-consequential: later commit's reaction writes what earlier commit originally changes</li>
     * </ul>
     * Commits with no footprint overlap are independent and can be freely reordered.
     */
    private List<int[]> computeIntraBranchEdges(
            int m, int n,
            List<Set<String>> aDirectFP, List<Set<String>> bDirectFP,
            List<Set<String>> aReactionFP, List<Set<String>> bReactionFP) {

        List<int[]> edges = new ArrayList<>();

        // Branch A: for each pair (i, j) where i < j
        for (int i = 0; i < m; i++) {
            for (int j = i + 1; j < m; j++) {
                boolean iBeforeJ = hasFootprintOverlap(aDirectFP.get(i), aReactionFP.get(i),
                        aDirectFP.get(j), aReactionFP.get(j));
                boolean jBeforeI = hasFootprintOverlap(aDirectFP.get(j), aReactionFP.get(j),
                        aDirectFP.get(i), aReactionFP.get(i));

                if (iBeforeJ || jBeforeI) {
                    // There is a dependency; preserve original order (i before j)
                    edges.add(new int[]{i, j});
                }
                // If neither direction has overlap, commits are independent -- no edge
            }
        }

        // Branch B: for each pair (i, j) where i < j
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                boolean iBeforeJ = hasFootprintOverlap(bDirectFP.get(i), bReactionFP.get(i),
                        bDirectFP.get(j), bReactionFP.get(j));
                boolean jBeforeI = hasFootprintOverlap(bDirectFP.get(j), bReactionFP.get(j),
                        bDirectFP.get(i), bReactionFP.get(i));

                if (iBeforeJ || jBeforeI) {
                    edges.add(new int[]{m + i, m + j});
                }
            }
        }

        return edges;
    }

    /**
     * Checks if two commits have overlapping footprints that require ordering.
     * Returns true if commit "earlier" must precede "later" because:
     * - Their original footprints overlap (write-write)
     * - Earlier's consequential footprint overlaps later's original (consequential-write)
     * - Later's consequential footprint overlaps earlier's original (write-consequential)
     */
    private static boolean hasFootprintOverlap(
            Set<String> earlierDirect, Set<String> earlierReaction,
            Set<String> laterDirect, Set<String> laterReaction) {
        // Write-write: both modify the same element-feature
        if (!Collections.disjoint(earlierDirect, laterDirect)) return true;
        // Consequential-write: earlier's reaction writes what later originally changes
        if (!Collections.disjoint(earlierReaction, laterDirect)) return true;
        // Write-consequential: later's reaction writes what earlier originally changes
        return !Collections.disjoint(laterReaction, earlierDirect);
    }

    /**
     * Tries one specific commit ordering from the base state.
     *
     * <p>Takes a fresh copy of the base VSUM and replays commits from A and B
     * in the order specified by {@code ordering}. Tracks which footprints belong
     * to A vs B for dynamic indirect conflict detection.
     *
     * @param baseWorkDir       a copy of the base state directory (will be modified by replay)
     * @param aTransactions     per-commit DTOs from branch A (in commit order)
     * @param bTransactions     per-commit DTOs from branch B (in commit order)
     * @param ordering          list of booleans: true=take from A, false=take from B
     * @param allADtos          all A DTOs flat (for footprint collection)
     * @param allBDtos          all B DTOs flat (for footprint collection)
     * @return merge result for this ordering
     */
    private SemanticMergeResult tryInterleaving(
            Path baseWorkDir,
            List<List<SemanticChangeLog.ChangeDto>> aTransactions,
            List<List<SemanticChangeLog.ChangeDto>> bTransactions,
            List<Boolean> ordering,
            List<SemanticChangeLog.ChangeDto> allADtos,
            List<SemanticChangeLog.ChangeDto> allBDtos,
            Map<String, String> hidToUuid) throws IOException {

        String uriPrefix = org.eclipse.emf.common.util.URI.createFileURI(
                baseWorkDir.toAbsolutePath().toString()).toString();

        InternalVirtualModel vsum = GitStateLoader.loadVsumFromDir(baseWorkDir, specs, interactionProvider);

        List<EChange<HierarchicalId>> allApplied = new ArrayList<>();
        int aIdx = 0, bIdx = 0;

        try {
            for (int step = 0; step < ordering.size(); step++) {
                boolean fromA = ordering.get(step);
                List<SemanticChangeLog.ChangeDto> txnDtos;

                if (fromA) {
                    if (aIdx >= aTransactions.size()) continue;
                    txnDtos = aTransactions.get(aIdx++);
                } else {
                    if (bIdx >= bTransactions.size()) continue;
                    txnDtos = bTransactions.get(bIdx++);
                }

                if (txnDtos.isEmpty()) continue;

                ChangeDtoDeserializer deserializer = new ChangeDtoDeserializer(null, uriPrefix);
                List<EChange<HierarchicalId>> txnChanges = deserializer.deserializeAll(txnDtos);
                if (txnChanges.isEmpty()) continue;

                replayChanges(vsum, txnChanges, hidToUuid);
                allApplied.addAll(txnChanges);
            }
        } catch (Exception e) {
            LOGGER.warn("[FALLBACK] Ordering {} is inapplicable (guard failure): {}",
                    orderingToString(ordering), e.getMessage());
            vsum.dispose();
            return null;
        }

        vsum.dispose();
        return SemanticMergeResult.success(allApplied, List.of(), baseWorkDir);
    }

    /**
     * Copies a directory recursively from {@code source} to {@code target}.
     * Used to clone the base state for each interleaving candidate.
     */
    static void copyDirectory(Path source, Path target) throws IOException {
        try (var walk = Files.walk(source)) {
            for (Path src : walk.toList()) {
                Path dest = target.resolve(source.relativize(src));
                if (Files.isDirectory(src)) {
                    Files.createDirectories(dest);
                } else {
                    Files.copy(src, dest);
                }
            }
        }
    }

    /**
     * Formats an interleaving ordering as a compact string for trace output.
     * e.g., [A, B, A, A, B]
     */
    private static String orderingToString(List<Boolean> ordering) {
        return ordering.stream()
                .map(b -> b ? "A" : "B")
                .collect(Collectors.joining(", ", "[", "]"));
    }


    public SemanticMergeResult merge(String baseSha, String oursSha, String theirsSha)
            throws IOException, GitAPIException {

        LOGGER.info("Semantic merge: base={}, ours={}, theirs={}",
                baseSha.substring(0, 7), oursSha.substring(0, 7), theirsSha.substring(0, 7));

        MergeTracer.trace("");
        MergeTracer.section("MERGE TRACE: Directed merge (replay theirs → ours)");
        MergeTracer.trace("  Base: " + baseSha.substring(0, 7)
                + "  |  Ours (target): " + oursSha.substring(0, 7)
                + "  |  Theirs (source): " + theirsSha.substring(0, 7));

        long mergeStartNanos = System.nanoTime();
        long gitStateExtractionNanos = 0;
        long dtoLoadingNanos = 0;
        long conflictDetectionNanos = 0;
        long replayPhaseNanos = 0;

        GitStateLoader loader = new GitStateLoader(repoRoot);

        // 1. Extract states via JGit TreeWalk
        long phaseStart = System.nanoTime();
        Path baseDir = GitStateLoader.createTempDir("merge-base-");
        Path theirsDir = GitStateLoader.createTempDir("merge-theirs-");
        Path oursDir = GitStateLoader.createTempDir("merge-ours-");

        loader.checkoutStateAtCommit(baseSha, baseDir);
        loader.checkoutStateAtCommit(theirsSha, theirsDir);
        loader.checkoutStateAtCommit(oursSha, oursDir);
        gitStateExtractionNanos = System.nanoTime() - phaseStart;
        LOGGER.info("[TIMING] Git state extraction: {} ms", gitStateExtractionNanos / 1_000_000);

        // 2. Load changelog DTOs -- subtract base changelogs so that only NET changes
        // per branch are compared. Both branches inherit the base's changelog files;
        // including them in conflict detection produces false MODIFY_MODIFY positives
        // when only one side actually changed an element relative to the base.
        phaseStart = System.nanoTime();
        Set<String> baseChangelogPaths = getChangelogRelativePaths(baseDir);
        List<SemanticChangeLog.ChangeDto> oursDtos =
                loadAllDtosExcluding(oursDir, baseChangelogPaths);
        List<SemanticChangeLog.ChangeDto> theirsDtos =
                loadAllDtosExcluding(theirsDir, baseChangelogPaths);
        dtoLoadingNanos = System.nanoTime() - phaseStart;
        LOGGER.info("Loaded {} ours DTOs, {} theirs DTOs", oursDtos.size(), theirsDtos.size());
        LOGGER.info("[TIMING] DTO loading: {} ms", dtoLoadingNanos / 1_000_000);
        MergeTracer.trace("[LOAD] Loaded " + oursDtos.size() + " ours (target) changelog DTOs, "
                + theirsDtos.size() + " theirs (source) changelog DTOs");
        if (!oursDtos.isEmpty()) {
            MergeTracer.trace("[LOAD] Ours (target branch) changes:");
            for (var dto : oursDtos) {
                MergeTracer.trace("         " + formatChangeDto(dto));
            }
        }
        if (!theirsDtos.isEmpty()) {
            MergeTracer.trace("[LOAD] Theirs (source branch) changes:");
            for (var dto : theirsDtos) {
                MergeTracer.trace("         " + formatChangeDto(dto));
            }
        }

        if (theirsDtos.isEmpty()) {
            LOGGER.info("No theirs changelog DTOs -- nothing to replay");
            MergeTracer.trace("[RESULT] No source changes to replay -- merge trivially succeeds");
            long totalNanosEarly = System.nanoTime() - mergeStartNanos;
            return SemanticMergeResult.success(List.of(), oursDir)
                    .withTimingStats(new SemanticMergeResult.TimingStats()
                            .gitStateExtraction(gitStateExtractionNanos)
                            .dtoLoading(dtoLoadingNanos)
                            .total(totalNanosEarly));
        }

        // 3. UUID-based conflict detection
        // Skipped when called from SemanticConflictDetector (steps 1-4 already ran there).
        phaseStart = System.nanoTime();
        List<MergeConflict> conflicts = List.of();
        List<ConflictResolution> resolutions = List.of();

        if (!skipDirectConflictDetection) {
            UuidConflictDetector detector = new UuidConflictDetector();
            conflicts = detector.detectConflicts(oursDtos, theirsDtos);

            if (conflicts.isEmpty()) {
                MergeTracer.trace("[CONFLICT] No direct UUID-based conflicts detected");
            } else {
                MergeTracer.trace("[CONFLICT] Detected " + conflicts.size() + " direct UUID-based conflict(s):");
                for (var c : conflicts) {
                    MergeTracer.trace("           " + formatConflict(c));
                }
            }

            if (!conflicts.isEmpty()) {
                if (conflictResolutionProvider == null) {
                    LOGGER.warn("Merge aborted: {} conflicts", conflicts.size());
                    MergeTracer.trace("");
                    MergeTracer.section("MERGE RESULT: CONFLICT (" + conflicts.size()
                            + " blocking conflict(s), merge aborted)");
                    long totalNanosConflict = System.nanoTime() - mergeStartNanos;
                    conflictDetectionNanos = System.nanoTime() - phaseStart;
                    return SemanticMergeResult.conflict(conflicts)
                            .withTimingStats(new SemanticMergeResult.TimingStats()
                                    .gitStateExtraction(gitStateExtractionNanos)
                                    .dtoLoading(dtoLoadingNanos)
                                    .conflictDetection(conflictDetectionNanos)
                                    .total(totalNanosConflict));
                }
                resolutions = conflictResolutionProvider.resolve(conflicts);
                theirsDtos = filterByResolutions(theirsDtos, conflicts, resolutions);
                MergeTracer.trace("[CONFLICT] Conflicts resolved -- " + theirsDtos.size()
                        + " DTOs remaining to replay");
                LOGGER.info("After conflict resolution: {} DTOs to replay", theirsDtos.size());
            }
        }

        conflictDetectionNanos = System.nanoTime() - phaseStart;
        LOGGER.info("[TIMING] Conflict detection: {} ms", conflictDetectionNanos / 1_000_000);

        // 4. Load changelog DTOs grouped by transaction for per-transaction replay.
        // Exclude base changelogs here too so we replay only the source branch's net changes.
        List<List<SemanticChangeLog.ChangeDto>> theirsTransactions =
                loadTransactionsExcluding(theirsDir, baseChangelogPaths);
        // Apply conflict resolution filtering to each transaction
        if (!resolutions.isEmpty()) {
            final var finalConflicts = conflicts;
            final var finalResolutions = resolutions;
            theirsTransactions = theirsTransactions.stream()
                    .map(txn -> filterByResolutions(txn, finalConflicts, finalResolutions))
                    .filter(txn -> !txn.isEmpty())
                    .toList();
        }

        // 5. Load target VSUM from ours state
        InternalVirtualModel targetVsum = GitStateLoader.loadVsumFromDir(oursDir, specs, interactionProvider);
        // Use EMF URI format for the target prefix (the deserializer extracts filename
        // from source IDs and prepends this prefix)
        String oursUriPrefix = org.eclipse.emf.common.util.URI.createFileURI(
                oursDir.toAbsolutePath().toString()).toString();

        // 5b. Load base VSUM for USER_VS_DERIVED_WARNING comparison.
        // We need base values to distinguish "unchanged base" from "derived by reactions on B".
        // Only warn when B's value differs from the base (truly derived), not when it's unchanged.
        InternalVirtualModel baseVsum = GitStateLoader.loadVsumFromDir(baseDir, specs, interactionProvider);
        Map<String, EObject> baseUuidToElement = buildUuidMap(baseVsum);
        baseVsum.dispose();

        // 6. Replay each transaction separately (per-transaction restore/reactions)
        //    After each transaction:
        //    - Check indirect conflicts: derived(replay(A)) vs user(B)
        //    - Check warnings: user(A) vs derived(B)
        List<EChange<HierarchicalId>> allApplied = new ArrayList<>();
        List<MergeConflict> indirectConflicts = new ArrayList<>();
        List<MergeConflict> warnings = new ArrayList<>();

        // Collect user-authored footprints from target branch for conflict/warning checks
        Set<String> oursUserFootprints = collectUuidFootprints(oursDtos);

        MergeTracer.trace("[REPLAY] Starting per-transaction replay ("
                + theirsTransactions.size() + " transaction(s))");
        long replayPhaseStart = System.nanoTime();
        try {
            for (int i = 0; i < theirsTransactions.size(); i++) {
                List<SemanticChangeLog.ChangeDto> txnDtos = theirsTransactions.get(i);
                MergeTracer.trace("[REPLAY] ── Transaction " + (i + 1) + "/"
                        + theirsTransactions.size() + " (" + txnDtos.size() + " change(s)) ──");
                for (var dto : txnDtos) {
                    MergeTracer.trace("           replay: " + formatChangeDto(dto));
                }

                // Build UUID-string → EObject map from the VSUM's model elements.
                // Used for element existence checks and value snapshots.
                Map<String, EObject> uuidToElement = buildUuidMap(targetVsum);

                // Check user(A) vs derived(B) warnings BEFORE replay:
                // If this transaction's user changes target elements whose state on B
                // is derived (not in oursDtos), that's a warning.
                // Uses the loaded VSUM to check element existence (not just changelogs).
                int warningsBefore = warnings.size();
                warnings.addAll(detectUserVsDerivedWarnings(
                        txnDtos, oursUserFootprints, uuidToElement, baseUuidToElement));
                int newWarnings = warnings.size() - warningsBefore;
                if (newWarnings > 0) {
                    MergeTracer.trace("           [WARNING] " + newWarnings
                            + " USER_VS_DERIVED_WARNING(s) detected before replay:");
                    for (int w = warningsBefore; w < warnings.size(); w++) {
                        MergeTracer.trace("             → " + formatConflict(warnings.get(w)));
                    }
                }

                // Snapshot user(B) footprint values BEFORE replay for indirect conflict detection.
                // After replay, any footprint whose value changed was overwritten by derived(A).
                Map<String, Object> preReplayValues = snapshotUserFootprintValues(
                        oursUserFootprints, uuidToElement);

                // Fresh deserializer per transaction (resets cache ID counter)
                ChangeDtoDeserializer deserializer =
                        new ChangeDtoDeserializer(null, oursUriPrefix);
                List<EChange<HierarchicalId>> txnChanges = deserializer.deserializeAll(txnDtos);
                if (txnChanges.isEmpty()) continue;

                // Capture derived changes from this transaction's reactions
                DerivedChangeCapture derivedCapture = new DerivedChangeCapture();
                targetVsum.addChangePropagationListener(derivedCapture);

                replayChanges(targetVsum, txnChanges);

                targetVsum.removeChangePropagationListener(derivedCapture);
                allApplied.addAll(txnChanges);

                // Check indirect conflicts AFTER replay: derived(replay(A)) vs user(B)
                // Two approaches -- PropagatedChange-based (existing) and snapshot-based (robust fallback):
                List<MergeConflict> txnIndirect = detectIndirectConflicts(
                        derivedCapture.getDerivedChanges(), oursDtos,
                        targetVsum.getUuidResolver());

                // Snapshot-based: compare pre/post replay values for user(B) footprints.
                // Catches derived(A) overwrites that PropagatedChange misses.
                Set<String> theirsDirectFootprints = collectUuidFootprints(txnDtos);
                Map<String, EObject> postReplayMap = buildUuidMap(targetVsum);
                txnIndirect.addAll(detectIndirectConflictsViaSnapshot(
                        preReplayValues, oursUserFootprints, theirsDirectFootprints,
                        postReplayMap));

                // Deduplicate by footprint
                int indirectBefore = indirectConflicts.size();
                Set<String> seen = new HashSet<>();
                for (MergeConflict ic : txnIndirect) {
                    String key = ic.getElementUuid() + "#" + ic.getConflictingFeature();
                    if (seen.add(key)) {
                        indirectConflicts.add(ic);
                    }
                }
                int newIndirect = indirectConflicts.size() - indirectBefore;
                if (newIndirect > 0) {
                    MergeTracer.trace("           [INDIRECT] " + newIndirect
                            + " INDIRECT_CONFLICT(s) detected after replay:");
                    for (int ic = indirectBefore; ic < indirectConflicts.size(); ic++) {
                        MergeTracer.trace("             → " + formatConflict(indirectConflicts.get(ic)));
                    }
                }
                MergeTracer.trace("           [REPLAY] Transaction " + (i + 1) + " complete -- "
                        + txnChanges.size() + " changes applied, "
                        + newIndirect + " indirect conflict(s), "
                        + newWarnings + " warning(s)");

                LOGGER.info("Replayed transaction {}/{} ({} changes, {} indirect conflicts, {} warnings)",
                        i + 1, theirsTransactions.size(), txnChanges.size(),
                        indirectConflicts.size(), warnings.size());
            }
        } catch (Exception e) {
            LOGGER.warn("Replay failed (guard failure -- element may have been deleted): {}",
                    e.getMessage());
            targetVsum.dispose();

            // Report as a replay-applicability conflict instead of crashing.
            // This happens when the source branch modifies an element that was
            // deleted on the target branch (or vice versa) and the conflict was
            // not caught by static UUID-based detection (e.g., cascade deletions).
            List<MergeConflict> replayConflicts = new ArrayList<>(conflicts.isEmpty()
                    ? List.of() : conflicts);
            replayConflicts.add(new MergeConflict(
                    "replay-applicability",
                    MergeConflict.ConflictType.REPLAY_APPLICABILITY,
                    null, null, null, null,
                    "Replay failed: " + e.getMessage()));
            long totalNanos = System.nanoTime() - mergeStartNanos;
            return SemanticMergeResult.conflict(replayConflicts)
                    .withTimingStats(new SemanticMergeResult.TimingStats()
                            .gitStateExtraction(gitStateExtractionNanos)
                            .dtoLoading(dtoLoadingNanos)
                            .conflictDetection(conflictDetectionNanos)
                            .replay(System.nanoTime() - replayPhaseStart)
                            .total(totalNanos));
        }
        replayPhaseNanos = System.nanoTime() - replayPhaseStart;
        LOGGER.info("[TIMING] Replay phase (all transactions): {} ms",
                replayPhaseNanos / 1_000_000);

        targetVsum.dispose();

        if (!indirectConflicts.isEmpty()) {
            LOGGER.warn("{} indirect conflict(s): derived(replay(A)) vs user(B)",
                    indirectConflicts.size());
        }
        if (!warnings.isEmpty()) {
            LOGGER.info("{} warning(s): user(A) vs derived(B)", warnings.size());
        }

        // Combine all warnings: user(A) vs derived(B) + indirect conflicts.
        // Indirect conflicts are reported as warnings (non-blocking) in a directed merge
        // because the merge direction (A→B) means A's changes take precedence.
        List<MergeConflict> allWarnings = new ArrayList<>(warnings);
        allWarnings.addAll(indirectConflicts);

        long totalNanos = System.nanoTime() - mergeStartNanos;
        long totalMs = totalNanos / 1_000_000;
        LOGGER.info("[TIMING] Total merge: {} ms", totalMs);

        // Build per-phase timing stats
        SemanticMergeResult.TimingStats timingStats = new SemanticMergeResult.TimingStats()
                .gitStateExtraction(gitStateExtractionNanos)
                .dtoLoading(dtoLoadingNanos)
                .conflictDetection(conflictDetectionNanos)
                .replay(replayPhaseNanos)
                .total(totalNanos);

        // Print final result summary
        String statusStr = resolutions.isEmpty() ? "SUCCESS" : "SUCCESS_WITH_RESOLUTIONS";
        MergeTracer.trace("");
        MergeTracer.section("MERGE RESULT: " + statusStr);
        MergeTracer.trace("    Changes applied: " + allApplied.size());
        MergeTracer.trace("    Warnings: " + allWarnings.size());
        if (!allWarnings.isEmpty()) {
            for (var w : allWarnings) {
                MergeTracer.trace("      - " + formatConflict(w));
            }
        }
        MergeTracer.trace("    Conflicts: 0 (blocking)");
        if (!resolutions.isEmpty()) {
            MergeTracer.trace("    Resolutions applied: " + resolutions.size());
        }
        MergeTracer.trace("    Duration: " + totalMs + " ms");

        if (!resolutions.isEmpty()) {
            return SemanticMergeResult.successWithResolutions(
                    resolutions, allApplied, allWarnings, oursDir)
                    .withTimingStats(timingStats);
        }
        return SemanticMergeResult.success(allApplied, allWarnings, oursDir)
                .withTimingStats(timingStats);
    }

    /**
     * Replays deserialized EChanges onto a target VSUM through a ChangeRecordingView.
     *
     * <p>Changes are applied using EMF's reflective API (eSet, eGet, list.add) rather
     * than ApplyEChangeSwitch (which uses EMF Commands via EditingDomain). Direct
     * reflective calls trigger EMF notifications on the objects' adapters, which the
     * ChangeRecordingView's ChangeRecorder captures. ApplyEChangeSwitch uses ad-hoc
     * EditingDomains that bypass the ResourceSet-level adapters.
     *
     * <p>The ChangeRecordingView captures EMF notifications, and {@code view.commitChanges()}
     * propagates them through the reaction engine, enabling transitive propagation
     * across coupled models.
     */
    @SuppressWarnings("unchecked")
    static void replayChanges(InternalVirtualModel targetVsum,
                               List<EChange<HierarchicalId>> changes) {
        replayChanges(targetVsum, changes, Map.of());
    }

    /**
     * Replays changes with UUID-based fallback resolution.
     *
     * @param hidToUuidFallback mapping from normalized HierarchicalId string → UUID string,
     *        built from the changelog's uuidMappings (reversed). Used when HierarchicalId
     *        path resolution fails because the model structure changed during interleaving.
     */
    static void replayChanges(InternalVirtualModel targetVsum,
                               List<EChange<HierarchicalId>> changes,
                               Map<String, String> hidToUuidFallback) {

        // Create a ChangeRecordingView on all model objects
        var selector = targetVsum.createSelector(
                tools.vitruv.framework.views.ViewTypeFactory.createIdentityMappingViewType("merge-replay"));
        targetVsum.getViewSourceModels().stream()
                .flatMap(r -> r.getContents().stream())
                .forEach(root -> selector.setSelected(root, true));
        var view = selector.createView().withChangeRecordingTrait();

        // Resolve HierarchicalIds using the view's ResourceSet
        var rootObjs = view.getRootObjects(EObject.class);
        if (rootObjs.isEmpty()) {
            throw new IllegalStateException(
                    "replayChanges: VSUM loaded from temp dir has no model root objects");
        }
        ResourceSet viewRs = rootObjs.iterator().next()
                .eResource().getResourceSet();
        var idResolver = tools.vitruv.change.atomic.hid.internal.HierarchicalIdResolver.create(viewRs);

        // Build UUID-to-EObject fallback map: for each UUID string in the changelog,
        // find the corresponding EObject in the current VSUM state.
        Map<String, EObject> uuidToElement = buildUuidMap(targetVsum);
        // Build HID-to-EObject fallback map: hid string → EObject via UUID bridge
        Map<String, EObject> hidFallback = new HashMap<>();
        for (var entry : hidToUuidFallback.entrySet()) {
            String hidStr = entry.getKey();
            String uuidStr = entry.getValue();
            EObject element = uuidToElement.get(uuidStr);
            if (element != null) {
                hidFallback.put(hidStr, element);
            }
        }

        // Cache ID remapping: the deserializer assigns placeholder cache IDs ("cache:/0", etc.)
        // that may not match the HierarchicalIdResolver's internal cache ID counter.
        // When CreateEObject is processed, we record placeholder → actual ID mapping,
        // so subsequent InsertEReference can resolve the created element correctly.
        Map<String, HierarchicalId> cacheIdRemap = new HashMap<>();

        // Apply each change using EMF reflective API (triggers notifications for ChangeRecorder)
        for (int ci = 0; ci < changes.size(); ci++) {
            EChange<HierarchicalId> eChange = changes.get(ci);
            try {
                applyChangeReflectively(eChange, idResolver, hidFallback, cacheIdRemap);
            } catch (Exception e) {
                MergeTracer.trace("[REPLAY] Failed at change " + (ci + 1) + "/" + changes.size()
                        + ": " + eChange.getClass().getSimpleName() + " -- " + e.getMessage());
                throw e;
            }
        }

        // Commit: ChangeRecordingView captured EMF notifications → propagateChange → reactions fire
        try {
            view.commitChanges();
        } catch (Exception e) {
            MergeTracer.trace("[REPLAY] commitChanges failed: " + e.getClass().getSimpleName()
                    + " -- " + e.getMessage());
            throw e;
        }
    }

    /**
     * Remaps a cache ID from the deserializer's placeholder to the resolver's actual ID.
     * Non-cache IDs are returned unchanged.
     */
    private static HierarchicalId remapCacheId(HierarchicalId hid, Map<String, HierarchicalId> cacheIdRemap) {
        if (hid == null || cacheIdRemap == null || cacheIdRemap.isEmpty()) return hid;
        HierarchicalId remapped = cacheIdRemap.get(hid.getId());
        return remapped != null ? remapped : hid;
    }

    /**
     * Resolves an element by HierarchicalId with UUID verification and fallback.
     *
     * <ol>
     *   <li>Try HierarchicalId (positional path) resolution (fast path).</li>
     *   <li>If the HID has a known UUID mapping, verify the resolved element's UUID matches.
     *       If it doesn't (positional path pointed to wrong element due to model changes),
     *       fall through to UUID fallback.</li>
     *   <li>UUID fallback: look up the element directly by UUID from a prebuilt map.</li>
     * </ol>
     *
     * @param hidFallback mapping from HierarchicalId string → EObject, built at replay time
     *        by bridging the changelog's uuid→hid mapping with the VSUM's uuid→EObject mapping
     */
    private static EObject resolveElement(HierarchicalId hid,
                                           tools.vitruv.change.atomic.hid.internal.HierarchicalIdResolver idResolver,
                                           Map<String, EObject> hidFallback) {
        if (hid == null) return null;
        String hidStr = hid.getId();

        // Determine the expected EObject from UUID (if available in fallback map).
        // Try both the full HID string and the bare fragment, because changelog
        // uuidMappings store bare fragments (e.g., "/0/@brakeComponents.2") while
        // deserialized EChanges use full URIs (e.g., "file:///tmp/.../model#/0/@brakeComponents.2").
        EObject expectedByUuid = null;
        if (hidFallback != null) {
            expectedByUuid = hidFallback.get(hidStr);
            if (expectedByUuid == null && hidStr.contains("#")) {
                String fragment = hidStr.substring(hidStr.indexOf('#') + 1);
                expectedByUuid = hidFallback.get(fragment);
            }
        }

        // Try HierarchicalId resolution first (fast path)
        try {
            EObject result = idResolver.getEObject(hid);
            if (result != null) {
                // Verify UUID match: if we know the expected element via UUID,
                // check that the HID resolved to the same object
                if (expectedByUuid != null && result != expectedByUuid) {

                    return expectedByUuid;
                }
                return result;
            }
        } catch (Exception e) {
            // Fall through to UUID fallback
        }

        // UUID fallback
        if (expectedByUuid != null) {
            return expectedByUuid;
        }
        return null;
    }

    /**
     * Applies a deserialized EChange to the view's model using EMF's reflective API.
     * Direct calls to eSet/eGet/list.add trigger proper EMF notifications that the
     * ChangeRecorder captures (unlike ApplyEChangeSwitch which uses EditingDomain Commands).
     *
     * <h4>Deletion handling</h4>
     * <ul>
     *   <li>{@code DeleteEObject}: Tolerates already-removed elements (returns silently).
     *       This happens when a prior {@code RemoveEReference} in the same transaction
     *       already detached the element from the containment tree.</li>
     *   <li>{@code RemoveEReference}: Tolerates unresolvable containers (logs and returns).
     *       The container may have been deleted by a cascade or prior change.</li>
     *   <li>{@code ReplaceSingleValuedEAttribute}: Throws {@link IllegalStateException}
     *       if the target element cannot be resolved; this is a guard failure indicating
     *       the element was deleted on the target branch. The caller catches this and
     *       reports a {@link MergeConflict.ConflictType#REPLAY_APPLICABILITY} conflict.</li>
     * </ul>
     */
    @SuppressWarnings("unchecked")
    private static void applyChangeReflectively(EChange<HierarchicalId> eChange,
                                          tools.vitruv.change.atomic.hid.internal.HierarchicalIdResolver idResolver,
                                          Map<String, EObject> hidFallback,
                                          Map<String, HierarchicalId> cacheIdRemap) {
        if (eChange instanceof tools.vitruv.change.atomic.eobject.CreateEObject<HierarchicalId> ce) {
            EObject created = EcoreUtil.create(ce.getAffectedEObjectType());
            HierarchicalId assignedId = idResolver.getAndUpdateId(created);
            // Record the mapping from the deserializer's placeholder cache ID
            // to the resolver's actual cache ID
            HierarchicalId placeholderId = ce.getAffectedElement();
            if (placeholderId != null && placeholderId.isCache()
                    && !placeholderId.equals(assignedId)) {
                cacheIdRemap.put(placeholderId.getId(), assignedId);
            }

        } else if (eChange instanceof tools.vitruv.change.atomic.feature.reference.InsertEReference<HierarchicalId> ir) {
            EObject container = resolveElement(remapCacheId(ir.getAffectedElement(), cacheIdRemap), idResolver, hidFallback);
            EObject newElement = resolveElement(remapCacheId(ir.getNewValue(), cacheIdRemap), idResolver, hidFallback);
            var list = (List<EObject>) container.eGet(ir.getAffectedFeature());
            // Set-based semantics: always append, ignore recorded index.
            // Multi-valued features are treated as sets for merge purposes.
            list.add(newElement);

        } else if (eChange instanceof tools.vitruv.change.atomic.feature.attribute.ReplaceSingleValuedEAttribute<HierarchicalId, ?> rsa) {
            EObject element = resolveElement(remapCacheId(rsa.getAffectedElement(), cacheIdRemap), idResolver, hidFallback);
            if (element == null) {
                throw new IllegalStateException("Cannot apply ReplaceSingleValuedEAttribute: "
                        + "element not found for " + rsa.getAffectedElement());
            }
            var feature = rsa.getAffectedFeature();
            if (element.eClass().getEStructuralFeature(feature.getName()) == null) {
                // Feature belongs to a subclass but element was resolved as parent type
                // (e.g., BrakeCaliper.pistonDiameterInMM on a BrakeComponent).
                // Treat as guard failure -- the element's concrete type differs in this state.
                throw new IllegalStateException("Feature '" + feature.getName()
                        + "' is not valid on " + element.eClass().getName()
                        + " (expected " + feature.getEContainingClass().getName() + ")");
            }
            element.eSet(feature, rsa.getNewValue());

        } else if (eChange instanceof tools.vitruv.change.atomic.feature.reference.RemoveEReference<HierarchicalId> rr) {
            try {
                EObject container = resolveElement(remapCacheId(rr.getAffectedElement(), cacheIdRemap), idResolver, hidFallback);
                if (container == null) throw new IllegalStateException("Container not found");
                var list = (List<EObject>) container.eGet(rr.getAffectedFeature());
                if (rr.getIndex() >= 0 && rr.getIndex() < list.size()) {
                    list.remove(rr.getIndex());
                }
            } catch (Exception e) {
                LOGGER.debug("RemoveEReference: container not resolvable (may be deleted): {}",
                        rr.getAffectedElement());
            }

        } else if (eChange instanceof tools.vitruv.change.atomic.eobject.DeleteEObject<HierarchicalId> de) {
            EObject element = resolveElement(remapCacheId(de.getAffectedElement(), cacheIdRemap), idResolver, hidFallback);
            if (element != null) {
                EcoreUtil.remove(element);
            } else {
                LOGGER.debug("DeleteEObject: element not resolvable (already removed): {}",
                        de.getAffectedElement());
            }

        } else if (eChange instanceof tools.vitruv.change.atomic.feature.attribute.InsertEAttributeValue<HierarchicalId, ?> ia) {
            EObject element = resolveElement(remapCacheId(ia.getAffectedElement(), cacheIdRemap), idResolver, hidFallback);
            var list = (List<Object>) element.eGet(ia.getAffectedFeature());
            // Set-based semantics: always append, ignore recorded index.
            list.add(ia.getNewValue());

        } else if (eChange instanceof tools.vitruv.change.atomic.root.InsertRootEObject<HierarchicalId> iro) {
            EObject newRoot = resolveElement(remapCacheId(iro.getNewValue(), cacheIdRemap), idResolver, hidFallback);
            // Set-based semantics: always append, ignore recorded index.
            idResolver.getResource(org.eclipse.emf.common.util.URI.createURI(iro.getUri()))
                    .getContents().add(newRoot);
        }
    }

    /**
     * Filters theirs' DTOs based on conflict resolutions.
     * For OURS choice: remove the conflicting theirs DTO.
     * For THEIRS choice: keep it (will be replayed).
     *
     * <p>For DELETE_MODIFY / MODIFY_DELETE conflicts, filtering is UUID-only
     * (not UUID+feature) because a deletion affects all features of an element.
     * When OURS is chosen for a delete conflict, ALL DTOs referencing the
     * conflicting UUID are dropped (the entire deletion or modification group).
     */
    private List<SemanticChangeLog.ChangeDto> filterByResolutions(
            List<SemanticChangeLog.ChangeDto> theirsDtos,
            List<MergeConflict> conflicts,
            List<ConflictResolution> resolutions) {

        // UUIDs where the user chose OURS -- these theirs DTOs should be skipped
        Set<String> skipUuids = resolutions.stream()
                .filter(r -> r.choice() == ConflictResolution.Choice.OURS)
                .map(ConflictResolution::elementUuid)
                .collect(Collectors.toSet());

        // For MODIFY_MODIFY: filter by UUID+feature (only skip the conflicting feature)
        Map<String, String> conflictFeatures = conflicts.stream()
                .filter(c -> c.getConflictingFeature() != null)
                .filter(c -> c.getType() == MergeConflict.ConflictType.MODIFY_MODIFY)
                .collect(Collectors.toMap(
                        c -> c.getElementUuid() + "#" + c.getConflictingFeature(),
                        MergeConflict::getConflictingFeature,
                        (a, b) -> a));

        // For DELETE_MODIFY / MODIFY_DELETE: filter by UUID only (skip all DTOs for that element)
        Set<String> deleteConflictUuids = conflicts.stream()
                .filter(c -> c.getType() == MergeConflict.ConflictType.DELETE_MODIFY
                        || c.getType() == MergeConflict.ConflictType.MODIFY_DELETE)
                .map(MergeConflict::getElementUuid)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        return theirsDtos.stream()
                .filter(dto -> {
                    if (dto.affectedElementUuid == null) return true;

                    // For delete conflicts resolved as OURS: skip ALL DTOs for this UUID
                    if (deleteConflictUuids.contains(dto.affectedElementUuid)
                            && skipUuids.contains(dto.affectedElementUuid)) {
                        return false;
                    }

                    // For MODIFY_MODIFY conflicts resolved as OURS: skip only matching feature
                    String key = dto.affectedElementUuid + "#" + dto.featureName;
                    return !(conflictFeatures.containsKey(key)
                            && skipUuids.contains(dto.affectedElementUuid));
                })
                .toList();
    }

    /**
     * Returns the set of relative paths (using forward slashes) for every JSON changelog
     * file under {@code dir/.vitruvius/changelogs/}. Used to identify which changelog
     * files belong to the merge base so they can be excluded when loading branch-specific DTOs.
     */
    private Set<String> getChangelogRelativePaths(Path dir) throws IOException {
        Path changelogsRoot = dir.resolve(".vitruvius/changelogs");
        if (!Files.isDirectory(changelogsRoot)) return Set.of();
        try (Stream<Path> stream = Files.walk(changelogsRoot)) {
            return stream.filter(p -> !Files.isDirectory(p))
                    .filter(p -> p.toString().endsWith(".json")
                            && p.getParent() != null
                            && "json".equals(p.getParent().getFileName().toString()))
                    .map(p -> dir.relativize(p).toString().replace('\\', '/'))
                    .collect(java.util.stream.Collectors.toSet());
        }
    }

    private List<SemanticChangeLog.ChangeDto> loadAllDtosExcluding(
            Path dir, Set<String> excludedRelPaths) throws IOException {
        return loadTransactionsExcluding(dir, excludedRelPaths).stream()
                .flatMap(List::stream).toList();
    }

    /**
     * Loads changelog DTOs grouped by transaction, skipping any JSON file whose
     * repository-relative path appears in {@code excludedRelPaths}. This removes
     * base-inherited changelogs so only net changes per branch are loaded.
     *
     * <p>Changelogs are sorted by git ancestry (parents before children) rather than
     * alphabetically by filename. SHA hashes are not chronological, so alphabetical
     * order can place a rename commit before the create commit it depends on.
     */
    private List<List<SemanticChangeLog.ChangeDto>> loadTransactionsExcluding(
            Path dir, Set<String> excludedRelPaths) throws IOException {
        Path changelogsRoot = dir.resolve(".vitruvius/changelogs");
        if (!Files.isDirectory(changelogsRoot)) return List.of();
        Gson gson = new GsonBuilder().create();

        Map<String, SemanticChangelogManager.ChangelogDocument> bySha = new LinkedHashMap<>();
        List<SemanticChangelogManager.ChangelogDocument> noShaList = new ArrayList<>();

        try (Stream<Path> stream = Files.walk(changelogsRoot)
                .filter(p -> p.toString().endsWith(".json"))
                .filter(p -> p.getParent() != null
                        && "json".equals(p.getParent().getFileName().toString()))) {
            for (Path jsonFile : stream.toList()) {
                String relPath = dir.relativize(jsonFile).toString().replace('\\', '/');
                if (excludedRelPaths.contains(relPath)) continue;
                String json = Files.readString(jsonFile);
                SemanticChangelogManager.ChangelogDocument doc =
                        gson.fromJson(json, SemanticChangelogManager.ChangelogDocument.class);
                if (doc == null || doc.fileChanges == null) continue;
                String fullSha = (doc.commit != null) ? doc.commit.sha : null;
                if (fullSha != null) {
                    bySha.put(fullSha, doc);
                } else {
                    noShaList.add(doc);
                }
            }
        }

        List<SemanticChangelogManager.ChangelogDocument> sorted = sortByAncestry(bySha);
        sorted.addAll(noShaList);

        List<List<SemanticChangeLog.ChangeDto>> transactions = new ArrayList<>();
        for (SemanticChangelogManager.ChangelogDocument doc : sorted) {
            List<SemanticChangeLog.ChangeDto> dtos = new ArrayList<>();
            for (SemanticChangelogManager.ChangelogDocument.FileChangeInfo fc : doc.fileChanges) {
                if (fc.semanticChanges == null) continue;
                for (SemanticChangeEntry entry : fc.semanticChanges) {
                    dtos.add(SemanticChangeEntryToChangeDtoConverter.convert(entry));
                }
            }
            if (!dtos.isEmpty()) transactions.add(dtos);
        }
        return transactions;
    }

    /**
     * Sorts changelog documents by commit author timestamp (oldest first).
     * This ensures earlier commits (e.g., CREATE) are replayed before later
     * ones (e.g., RENAME). Using author date instead of {@code parentShas}
     * avoids mismatch caused by auto-changelog commits interleaved between
     * user commits in the git graph: parentShas in the changelog doc point
     * to auto-changelog SHAs that are not keys in our map, making
     * parentSha-based topological sort unreliable.
     *
     * <p>ISO-8601 date strings (e.g., "2026-05-24T22:37:00") sort correctly
     * lexicographically, so a plain string comparison gives chronological order.
     */
    private static List<SemanticChangelogManager.ChangelogDocument> sortByAncestry(
            Map<String, SemanticChangelogManager.ChangelogDocument> bySha) {
        return bySha.values().stream()
                .sorted(Comparator.comparing(doc -> {
                    if (doc.commit != null && doc.commit.author != null
                            && doc.commit.author.date != null) {
                        return doc.commit.author.date;
                    }
                    return "";
                }))
                .collect(Collectors.toList());
    }

    private List<SemanticChangeLog.ChangeDto> loadAllDtosFromDir(Path dir) throws IOException {
        return loadTransactionsFromDir(dir).stream().flatMap(List::stream).toList();
    }

    /**
     * Loads changelog DTOs grouped by transaction (one list per changelog file) from our
     * JSON format stored at {@code .vitruvius/changelogs/{branch}/json/{sha7}.json}.
     *
     * <p>Each file represents one user commit = one transaction. The method walks all
     * {@code json/} subdirectories under {@code .vitruvius/changelogs/} so it works
     * regardless of the branch name.
     *
     * <p>Documents are sorted by git ancestry (parents before children) so that replay
     * always processes earlier commits (e.g., creates) before later ones (e.g., renames).
     */
    private List<List<SemanticChangeLog.ChangeDto>> loadTransactionsFromDir(Path dir) throws IOException {
        Path changelogsRoot = dir.resolve(".vitruvius/changelogs");
        if (!Files.isDirectory(changelogsRoot)) return List.of();
        Gson gson = new GsonBuilder().create();

        Map<String, SemanticChangelogManager.ChangelogDocument> bySha = new LinkedHashMap<>();
        List<SemanticChangelogManager.ChangelogDocument> noShaList = new ArrayList<>();

        try (Stream<Path> stream = Files.walk(changelogsRoot)
                .filter(p -> p.toString().endsWith(".json"))
                .filter(p -> p.getParent() != null
                        && "json".equals(p.getParent().getFileName().toString()))) {
            for (Path jsonFile : stream.toList()) {
                String json = Files.readString(jsonFile);
                SemanticChangelogManager.ChangelogDocument doc =
                        gson.fromJson(json, SemanticChangelogManager.ChangelogDocument.class);
                if (doc == null || doc.fileChanges == null) continue;
                String fullSha = (doc.commit != null) ? doc.commit.sha : null;
                if (fullSha != null) {
                    bySha.put(fullSha, doc);
                } else {
                    noShaList.add(doc);
                }
            }
        }

        List<SemanticChangelogManager.ChangelogDocument> sorted = sortByAncestry(bySha);
        sorted.addAll(noShaList);

        List<List<SemanticChangeLog.ChangeDto>> transactions = new ArrayList<>();
        for (SemanticChangelogManager.ChangelogDocument doc : sorted) {
            List<SemanticChangeLog.ChangeDto> dtos = new ArrayList<>();
            for (SemanticChangelogManager.ChangelogDocument.FileChangeInfo fc : doc.fileChanges) {
                if (fc.semanticChanges == null) continue;
                for (SemanticChangeEntry entry : fc.semanticChanges) {
                    dtos.add(SemanticChangeEntryToChangeDtoConverter.convert(entry));
                }
            }
            if (!dtos.isEmpty()) transactions.add(dtos);
        }
        return transactions;
    }

    /**
     * Loads all UUID to HierarchicalId mappings from our changelog format.
     * Since each {@link SemanticChangeEntry} carries both {@code elementUuid} and
     * {@code hierarchicalId}, the mapping is derived directly from the entries.
     */
    private Map<String, String> loadUuidMappingsFromDir(Path dir) throws IOException {
        Path changelogsRoot = dir.resolve(".vitruvius/changelogs");
        if (!Files.isDirectory(changelogsRoot)) {
            return Map.of();
        }
        Gson gson = new GsonBuilder().create();
        Map<String, String> allMappings = new HashMap<>();

        try (Stream<Path> jsonFiles = Files.walk(changelogsRoot)
                .filter(p -> p.toString().endsWith(".json"))
                .filter(p -> p.getParent() != null
                        && "json".equals(p.getParent().getFileName().toString()))) {

            for (Path jsonFile : jsonFiles.toList()) {
                String json = Files.readString(jsonFile);
                SemanticChangelogManager.ChangelogDocument doc =
                        gson.fromJson(json, SemanticChangelogManager.ChangelogDocument.class);
                if (doc == null || doc.fileChanges == null) continue;

                for (SemanticChangelogManager.ChangelogDocument.FileChangeInfo fc : doc.fileChanges) {
                    if (fc.semanticChanges == null) continue;
                    for (SemanticChangeEntry entry : fc.semanticChanges) {
                        if (entry.getElementUuid() != null && entry.getHierarchicalId() != null) {
                            allMappings.put(entry.getElementUuid(), entry.getHierarchicalId());
                        }
                    }
                }
            }
        }
        return allMappings;
    }

    /**
     * Reverses a UUID→HierarchicalId mapping to HierarchicalId→UUID.
     */
    private static Map<String, String> reverseUuidMappings(Map<String, String> uuidToHid) {
        Map<String, String> hidToUuid = new HashMap<>();
        for (var entry : uuidToHid.entrySet()) {
            hidToUuid.put(entry.getValue(), entry.getKey());
        }
        return hidToUuid;
    }

    /**
     * Extension point for Tural's consequential footprint component (steps 5-8).
     *
     * <p>Consequential footprints represent the set of element+feature pairs that are
     * modified by Reactions as a side effect of replaying a primary change. Storing them
     * at commit time enables the fast path in the interleaved replay algorithm: if stored
     * footprints are available for all transactions on both branches, the fixpoint loop
     * can skip full model replay and use the pre-computed sets directly.
     *
     * <p>Capturing footprints requires {@code ChangeLogCapture} to intercept
     * {@code ChangePropagationListener.postChangePropagation} events and record which
     * elements were touched by Reactions. This integration is outside the scope of the
     * current thesis. Until it is implemented, this method returns an empty list,
     * causing the caller to fall back to empty reaction-footprint estimates.
     *
     * @param dir directory containing changelog files for one branch
     * @return always empty as footprints not yet captured
     */
    private List<Set<String>> loadStoredFootprintsFromDir(Path dir) throws IOException {
        return List.of();
    }

    /**
     * Collects UUID#feature footprints from changelog DTOs.
     * Also includes cascade-deleted child UUIDs as wildcard footprints (UUID without feature)
     * so that any modification to a cascade-deleted child is detected as a conflict.
     */
    private Set<String> collectUuidFootprints(List<SemanticChangeLog.ChangeDto> dtos) {
        Set<String> footprints = new HashSet<>();
        for (SemanticChangeLog.ChangeDto dto : dtos) {
            if (dto.affectedElementUuid != null && dto.featureName != null) {
                footprints.add(dto.affectedElementUuid + "#" + dto.featureName);
            }
            // Cascade-deleted children: add their UUIDs as footprints
            if (dto.cascadeDeletedUuids != null) {
                for (String childUuid : dto.cascadeDeletedUuids) {
                    footprints.add(childUuid + "#*");
                }
            }
        }
        return footprints;
    }

    /**
     * Detects indirect conflicts: derived(replay(A)) vs user(B).
     *
     * <p>After replaying a source transaction, reactions (restore) produce consequential
     * changes. If any of those derived changes modify an element+feature that the target
     * branch's user explicitly changed, that's an indirect conflict.
     *
     * <p>Uses the VSUM's UuidResolver to get per-element UUIDs for the derived changes,
     * enabling precise element-level (not type-level) conflict detection.
     *
     * <p>Implements Section 7.3 of the formalization.
     */
    private List<MergeConflict> detectIndirectConflicts(
            List<PropagatedChange> derivedChanges,
            List<SemanticChangeLog.ChangeDto> oursDtos,
            tools.vitruv.change.atomic.uuid.UuidResolver uuidResolver) {

        List<MergeConflict> conflicts = new ArrayList<>();

        // Build target-branch user footprints: UUID#feature
        Set<String> oursUserFootprints = collectUuidFootprints(oursDtos);
        if (oursUserFootprints.isEmpty()) return conflicts;

        // Extract derived footprints using UuidResolver for per-element identity
        for (PropagatedChange pc : derivedChanges) {
            VitruviusChange<EObject> consequential = pc.getConsequentialChanges();
            if (consequential == null || !consequential.containsConcreteChange()) continue;

            for (EChange<EObject> ec : consequential.getEChanges()) {
                if (!(ec instanceof tools.vitruv.change.atomic.feature.FeatureEChange<EObject, ?> fc))
                    continue;
                EObject element = fc.getAffectedElement();
                String featureName = fc.getAffectedFeature() != null
                        ? fc.getAffectedFeature().getName() : null;
                if (element == null || featureName == null) continue;

                // Resolve the element's UUID for per-element matching
                String uuid;
                try {
                    uuid = uuidResolver.getUuid(element).toString();
                } catch (IllegalStateException e) {
                    continue; // element not in UUID resolver (e.g., newly created by reaction)
                }

                String footprint = uuid + "#" + featureName;
                if (oursUserFootprints.contains(footprint)) {
                    conflicts.add(new MergeConflict(
                            uuid, MergeConflict.ConflictType.INDIRECT_CONFLICT,
                            uuid, featureName, null, null, null));
                    LOGGER.warn("Indirect conflict: derived change from replay " +
                            "overwrites user change on target: uuid={}, feature={}",
                            uuid, featureName);
                }
            }
        }
        return conflicts;
    }

    /**
     * Detects user(A) vs derived(B) warnings.
     *
     * <p>If a source transaction's user changes modify an element+feature that is NOT
     * in the target branch's user-authored changes (i.e., the target state for that
     * element+feature is derived, not user-authored), that's a warning.
     *
     * <p>Uses the loaded VSUM's UuidResolver to check element existence in the actual
     * model state, not just the changelog DTOs. This correctly detects elements that
     * were created by reactions (derived state) which don't appear in changelogs.
     *
     * <p>Policy: replay proceeds, source user intent wins, warning is recorded.
     *
     * <p>Implements Section 8.3 of the formalization.
     */
    private List<MergeConflict> detectUserVsDerivedWarnings(
            List<SemanticChangeLog.ChangeDto> theirsTxnDtos,
            Set<String> oursUserFootprints,
            Map<String, EObject> uuidToElement,
            Map<String, EObject> baseUuidToElement) {

        List<MergeConflict> warnings = new ArrayList<>();

        for (SemanticChangeLog.ChangeDto dto : theirsTxnDtos) {
            if (dto.affectedElementUuid == null || dto.featureName == null) continue;

            String footprint = dto.affectedElementUuid + "#" + dto.featureName;

            // Skip if the footprint IS in ours user changes (that would be a direct conflict,
            // already detected by UuidConflictDetector).
            if (oursUserFootprints.contains(footprint)) continue;

            // Check: does the element exist on the target branch's VSUM?
            EObject elementOnB = uuidToElement.get(dto.affectedElementUuid);
            if (elementOnB == null) continue;

            // Compare B's value against the base value.
            // Only warn if the value on B DIFFERS from the base -- meaning reactions on B
            // actually derived a new value. If B's value equals the base, this is just
            // an unchanged base value and A's change is a normal three-way merge application.
            EObject elementOnBase = baseUuidToElement.get(dto.affectedElementUuid);
            if (elementOnBase != null) {
                var feature = elementOnB.eClass().getEStructuralFeature(dto.featureName);
                if (feature != null) {
                    Object valueOnB = elementOnB.eGet(feature);
                    var baseFeature = elementOnBase.eClass().getEStructuralFeature(dto.featureName);
                    Object valueOnBase = baseFeature != null ? elementOnBase.eGet(baseFeature) : null;
                    if (java.util.Objects.equals(valueOnB, valueOnBase)) {
                        continue;
                    }
                }
            }
            // Element doesn't exist in base (created by reactions on B) or value differs → warning
            String oursValue = null;
            var feature = elementOnB.eClass().getEStructuralFeature(dto.featureName);
            if (feature != null) {
                Object val = elementOnB.eGet(feature);
                oursValue = val != null ? val.toString() : null;
            }
            warnings.add(new MergeConflict(
                    dto.affectedElementUuid,
                    MergeConflict.ConflictType.USER_VS_DERIVED_WARNING,
                    dto.affectedElementUuid, dto.featureName,
                    oursValue, null, String.valueOf(dto.newLiteralValue)));
            LOGGER.info("Warning: user(A) overwrites derived(B) state: uuid={}, feature={}, "
                    + "valueOnB={}, valueOnBase={}",
                    dto.affectedElementUuid, dto.featureName, oursValue,
                    elementOnBase != null ? "exists" : "absent");
        }
        return warnings;
    }

    /**
     * Builds a map from UUID-string → EObject for all elements in the VSUM's model resources.
     * Used for element existence checks and value snapshots without needing to construct
     * package-private {@code Uuid} objects.
     */
    private static Map<String, EObject> buildUuidMap(InternalVirtualModel vsum) {
        Map<String, EObject> map = new java.util.HashMap<>();
        UuidResolver resolver = vsum.getUuidResolver();
        for (var sourceModel : vsum.getViewSourceModels()) {
            for (Resource resource : sourceModel.getResourceSet().getResources()) {
                var it = resource.getAllContents();
                while (it.hasNext()) {
                    EObject obj = it.next();
                    try {
                        String uuidStr = resolver.getUuid(obj).toString();
                        map.put(uuidStr, obj);
                    } catch (IllegalStateException e) {
                        // No UUID for this object (e.g., proxy or transient)
                    }
                }
            }
        }
        return map;
    }

    /**
     * Snapshots the current values of all element+feature pairs in the user(B) footprints.
     * Used for snapshot-based indirect conflict detection after replay.
     */
    private Map<String, Object> snapshotUserFootprintValues(
            Set<String> userFootprints, Map<String, EObject> uuidToElement) {
        Map<String, Object> snapshot = new java.util.HashMap<>();
        for (String footprint : userFootprints) {
            String[] parts = footprint.split("#", 2);
            if (parts.length != 2) continue;
            String uuid = parts[0];
            String featureName = parts[1];
            EObject element = uuidToElement.get(uuid);
            if (element == null) continue;
            var feature = element.eClass().getEStructuralFeature(featureName);
            if (feature == null) continue;
            Object value = element.eGet(feature);
            snapshot.put(footprint, value);
        }
        return snapshot;
    }

    /**
     * Detects indirect conflicts by comparing pre-replay snapshots with post-replay state.
     *
     * <p>If a user(B) footprint's value changed during replay, AND the change was not a
     * direct user(A) change (i.e., it was caused by a reaction), then derived(replay(A))
     * overwrote user(B)'s intent.
     *
     * <p>This is a robust fallback for cases where {@code PropagatedChange.getConsequentialChanges()}
     * doesn't capture the derived changes properly.
     *
     * <p>Implements Section 8.4 of the formalization.
     */
    private List<MergeConflict> detectIndirectConflictsViaSnapshot(
            Map<String, Object> preReplayValues,
            Set<String> oursUserFootprints,
            Set<String> theirsDirectFootprints,
            Map<String, EObject> uuidToElement) {

        List<MergeConflict> conflicts = new ArrayList<>();

        for (Map.Entry<String, Object> entry : preReplayValues.entrySet()) {
            String footprint = entry.getKey();
            Object oldValue = entry.getValue();

            // Skip footprints that were directly changed by user(A) -- those are
            // direct conflicts (already detected by UuidConflictDetector).
            if (theirsDirectFootprints.contains(footprint)) continue;

            String[] parts = footprint.split("#", 2);
            if (parts.length != 2) continue;
            String uuid = parts[0];
            String featureName = parts[1];

            EObject element = uuidToElement.get(uuid);
            if (element == null) continue;
            var feature = element.eClass().getEStructuralFeature(featureName);
            if (feature == null) continue;
            Object newValue = element.eGet(feature);

            // Compare: if value changed, derived(A) overwrote user(B)
            if (!java.util.Objects.equals(oldValue, newValue)) {
                conflicts.add(new MergeConflict(
                        uuid, MergeConflict.ConflictType.INDIRECT_CONFLICT,
                        uuid, featureName,
                        String.valueOf(oldValue), String.valueOf(oldValue),
                        String.valueOf(newValue)));
                LOGGER.warn("Indirect conflict (snapshot): derived(replay(A)) " +
                        "changed user(B) value: uuid={}, feature={}, {} → {}",
                        uuid, featureName, oldValue, newValue);
            }
        }
        return conflicts;
    }

    // Trace formatting helpers

    /**
     * Formats a ChangeDto into a human-readable description for trace output.
     */
    static String formatChangeDto(SemanticChangeLog.ChangeDto dto) {
        String elementDesc = dto.affectedEClassName != null ? dto.affectedEClassName : "?";
        String idShort = dto.affectedElementId != null ? shortenId(dto.affectedElementId) : "";

        return switch (dto.changeType) {
            case "CreateEObject" -> "create " + (dto.affectedEObjectType != null ? dto.affectedEObjectType : elementDesc);
            case "DeleteEObject" -> "delete " + elementDesc + "(" + idShort + ")";
            case "InsertRootEObject" -> "insert root " + (dto.newValueId != null ? shortenId(dto.newValueId) : "")
                    + " into " + shortenUri(dto.resourceUri);
            case "RemoveRootEObject" -> "remove root " + (dto.oldValueId != null ? shortenId(dto.oldValueId) : "")
                    + " from " + shortenUri(dto.resourceUri);
            case "InsertEReference" -> "add " + (dto.newValueId != null ? shortenId(dto.newValueId) : "element")
                    + " to " + elementDesc + "(" + idShort + ")." + dto.featureName
                    + " at index " + dto.index;
            case "RemoveEReference" -> "remove " + (dto.oldValueId != null ? shortenId(dto.oldValueId) : "element")
                    + " from " + elementDesc + "(" + idShort + ")." + dto.featureName;
            case "ReplaceSingleValuedEReference" -> "set reference " + elementDesc + "(" + idShort + ")."
                    + dto.featureName + " → " + (dto.newValueId != null ? shortenId(dto.newValueId) : "null");
            case "ReplaceSingleValuedEAttribute" -> "modify " + elementDesc + "(" + idShort + ")."
                    + dto.featureName + ": " + dto.oldLiteralValue + " → " + dto.newLiteralValue;
            case "InsertEAttributeValue" -> "insert attribute value " + dto.newLiteralValue
                    + " into " + elementDesc + "(" + idShort + ")." + dto.featureName;
            case "RemoveEAttributeValue" -> "remove attribute value " + dto.oldLiteralValue
                    + " from " + elementDesc + "(" + idShort + ")." + dto.featureName;
            default -> dto.changeType + " on " + elementDesc + "(" + idShort + ")";
        };
    }

    /**
     * Formats a MergeConflict into a human-readable description for trace output.
     */
    static String formatConflict(MergeConflict conflict) {
        return switch (conflict.getType()) {
            case MODIFY_MODIFY -> "MODIFY_MODIFY on feature '" + conflict.getConflictingFeature()
                    + "': ours=" + conflict.getOursValue() + ", theirs=" + conflict.getTheirsValue()
                    + (conflict.getBaseValue() != null ? " (base=" + conflict.getBaseValue() + ")" : "");
            case DELETE_MODIFY -> "DELETE_MODIFY: element deleted on one branch, modified on other"
                    + (conflict.getElementUuid() != null ? " [uuid=" + shortenUuid(conflict.getElementUuid()) + "]" : "");
            case MODIFY_DELETE -> "MODIFY_DELETE: element modified on one branch, deleted on other"
                    + (conflict.getElementUuid() != null ? " [uuid=" + shortenUuid(conflict.getElementUuid()) + "]" : "");
            case INDIRECT_CONFLICT -> "INDIRECT_CONFLICT: derived change overwrites user change on feature '"
                    + conflict.getConflictingFeature() + "'"
                    + (conflict.getOursValue() != null ? " (was: " + conflict.getOursValue()
                            + " → became: " + conflict.getTheirsValue() + ")" : "");
            case USER_VS_DERIVED_WARNING -> "USER_VS_DERIVED_WARNING: user(source) overwrites derived(target) on feature '"
                    + conflict.getConflictingFeature() + "'"
                    + (conflict.getTheirsValue() != null ? " → " + conflict.getTheirsValue() : "");
            case BIDIRECTIONAL_INDIRECT_CONFLICT -> "BIDIRECTIONAL_INDIRECT_CONFLICT on feature '"
                    + conflict.getConflictingFeature() + "': both directions have indirect conflicts";
            case INTERLEAVING_CONFLICT -> "INTERLEAVING_CONFLICT on feature '"
                    + conflict.getConflictingFeature() + "': no commit ordering avoids indirect conflicts";
            case REPLAY_APPLICABILITY -> "REPLAY_APPLICABILITY: replay failed -- target element missing"
                    + (conflict.getTheirsValue() != null ? " (" + conflict.getTheirsValue() + ")" : "");
        };
    }

    private static String shortenId(String id) {
        if (id == null) return "";
        // Extract just the fragment part (after #) or the last path segment
        int hash = id.indexOf('#');
        if (hash >= 0) return id.substring(hash);
        int lastSlash = id.lastIndexOf('/');
        if (lastSlash >= 0 && lastSlash < id.length() - 1) return id.substring(lastSlash);
        return id;
    }

    private static String shortenUri(String uri) {
        if (uri == null) return "";
        int lastSlash = uri.lastIndexOf('/');
        if (lastSlash >= 0 && lastSlash < uri.length() - 1) return uri.substring(lastSlash + 1);
        return uri;
    }

    private static String shortenUuid(String uuid) {
        if (uuid == null) return "";
        if (uuid.length() > 20) return uuid.substring(0, 20) + "...";
        return uuid;
    }

    /**
     * Captures derived (propagated) changes during a single {@code propagateChange()} call.
     * Used for indirect conflict detection after each replayed transaction.
     */
    static class DerivedChangeCapture implements ChangePropagationListener {
        private final List<PropagatedChange> derivedChanges = new ArrayList<>();

        @Override
        public void startedChangePropagation(VitruviusChange<Uuid> change) { }

        @Override
        public void finishedChangePropagation(Iterable<PropagatedChange> propagatedChanges) {
            for (PropagatedChange pc : propagatedChanges) {
                derivedChanges.add(pc);
            }
        }

        public List<PropagatedChange> getDerivedChanges() { return derivedChanges; }
    }
}
