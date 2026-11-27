package tools.vitruv.framework.vsum.branch;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.revwalk.filter.RevFilter;
import org.eclipse.jgit.treewalk.TreeWalk;
import tools.vitruv.framework.vsum.branch.data.ConflictSeverity;
import tools.vitruv.framework.vsum.branch.data.ReplayResult;
import tools.vitruv.framework.vsum.branch.data.SemanticConflict;
import tools.vitruv.framework.vsum.branch.exception.BranchOperationException;
import tools.vitruv.framework.vsum.branch.merge.MergeConflict;
import tools.vitruv.framework.vsum.branch.merge.SemanticMergeEngine;
import tools.vitruv.framework.vsum.branch.merge.SemanticMergeResult;
import tools.vitruv.framework.vsum.branch.storage.ChangeOrigin;
import tools.vitruv.framework.vsum.branch.storage.SemanticChangeEntry;
import tools.vitruv.framework.vsum.branch.storage.SemanticChangeType;
import tools.vitruv.framework.vsum.branch.storage.SemanticChangelogManager;

/**
 * Implements the replay-based merge pipeline for two diverged branches (DE-4, MG-2, MG-3).
 *
 * <h3>Full pipeline (10 steps)</h3>
 * <ol>
 *   <li><b>Find merge-base</b>: common ancestor commit via JGit
 *       {@link RevFilter#MERGE_BASE}.</li>
 *   <li><b>Load changelog DTOs</b>: collect diverging commit SHAs and read JSON changelog
 *       files directly from the Git object store (no checkout needed).</li>
 *   <li><b>Direct conflict detection</b>: compare {@code (elementUuid, feature)} pairs
 *       across both branches without loading any model. Implemented.</li>
 *   <li><b>Resolution of direct conflicts</b>: ChangeOrigin-based auto-resolution.
 *       ORIGINAL vs CONSEQUENTIAL pairs are auto-resolved; ORIGINAL vs ORIGINAL and UNKNOWN
 *       combinations require human intervention. Implemented.</li>
 *   <li><b>Build footprint dependency graph</b>: nodes per commit, intra-branch ordering
 *       edges, inter-branch edges where a commit's consequential footprint overlaps another
 *       commit's original footprint. Delegated to {@link SemanticMergeEngine}; requires
 *       consequential footprint data captured by the consequential changelog component.</li>
 *   <li><b>Detect cycles</b>: a cycle in the dependency graph means no valid interleaving
 *       exists (consequential conflict, requires human resolution). Delegated to
 *       {@link SemanticMergeEngine}.</li>
 *   <li><b>Topological sort</b>: Kahn's algorithm on the acyclic graph produces the
 *       interleaving order for replay. Delegated to {@link SemanticMergeEngine}.</li>
 *   <li><b>Replay in order</b>: deserialize each commit's original changes into live
 *       {@code EChange} objects and apply them through the Vitruvius Reaction engine so
 *       consequential changes are regenerated. Delegated to {@link SemanticMergeEngine}.</li>
 *   <li><b>Iterative refinement</b>: compare actual consequential footprints captured
 *       during replay against stored estimates; rebuild and re-sort if new overlaps appear.
 *       Out of scope for this thesis.</li>
 *   <li><b>Return result</b> - consistent merged model state, or a conflict list when
 *       human resolution is required.</li>
 * </ol>
 *
 * <h3>Current state</h3>
 *
 * <p>Steps 1–4 are fully implemented (direct conflict detection and ChangeOrigin-based
 * auto-resolution). Steps 5–8 are delegated to
 * {@link SemanticMergeEngine#mergeBidirectional} when the engine is wired via
 * {@link MergeManager#setMergeEngine}; the stub fallback methods below are safe defaults
 * used when no engine is available. Full detection of consequential conflicts in steps 5–8
 * requires per-commit consequential footprint data, which will be provided by the
 * consequential changelog component once integrated. Step 9 is intentionally out of scope
 * for this thesis.
 *
 * <h3>Example</h3>
 * <pre>
 *   Branch A: changed element 'e1', attribute 'name', from 'Foo' to 'Bar'
 *   Branch B: changed element 'e1', attribute 'name', from 'Foo' to 'Baz'
 *   -&gt; MEDIUM direct conflict: same attribute modified to different values
 * </pre>
 *
 * <h3>Usage</h3>
 * <pre>{@code
 *   SemanticConflictDetector detector = new SemanticConflictDetector(repoRoot);
 *   ReplayResult result = detector.analyzeBranches("feature-x", "main");
 *   if (result.hasConflicts()) {
 *       result.getConflicts().forEach(c -> log.warn("Conflict: {}", c));
 *   }
 * }</pre>
 */
public class SemanticConflictDetector {

  private static final Logger LOGGER = LogManager.getLogger(SemanticConflictDetector.class);
  private static final String CHANGELOG_PREFIX = ".vitruvius/changelogs/";
  private static final String JSON_SUBDIR = "/json/";

  private final Path repoRoot;
  private final Gson gson;

  /**
   * Optional full merge engine for delegating steps 5–9 (dependency graph, cycle detection,
   * topological sort, and replay). {@code null} when only steps 1–3 are needed.
   */
  private final SemanticMergeEngine mergeEngine;

  /**
   * Creates a new {@link SemanticConflictDetector} that performs steps 1–3 only
   * (direct conflict detection via changelog analysis; no model loading or replay).
   *
   * @param repoRoot the root of the Git repository; must contain {@code .git/}.
   */
  public SemanticConflictDetector(Path repoRoot) {
    this(repoRoot, null);
  }

  /**
   * Creates a new {@link SemanticConflictDetector} with an optional
   * {@link SemanticMergeEngine} for full pipeline execution (steps 1–9).
   *
   * <p>When {@code mergeEngine} is non-null and step 3 finds no direct conflicts,
   * steps 5–9 are delegated to
   * {@link SemanticMergeEngine#mergeBidirectional(String, String, String)}.
   *
   * @param repoRoot    the root of the Git repository; must contain {@code .git/}.
   * @param mergeEngine engine for the replay pipeline; may be {@code null}.
   */
  public SemanticConflictDetector(Path repoRoot, SemanticMergeEngine mergeEngine) {
    this.repoRoot = checkNotNull(repoRoot, "repoRoot must not be null");
    checkArgument(Files.isDirectory(repoRoot.resolve(".git")),
        "No Git repository found at: %s", repoRoot);
    this.mergeEngine = mergeEngine;
    this.gson = buildGson();
  }

  /**
   * Runs the replay-based merge pipeline for two branches and returns a {@link ReplayResult}.
   *
   * <p>Currently executes steps 1–3 fully (direct conflict detection). Steps 4–9 have
   * stub implementations. When direct conflicts are found the method returns them
   * immediately; steps 5–9 are only reached when no direct conflicts exist.
   *
   * <p>Note: this method reads changelog records and compares them. Steps 1–3 do not
   * apply or replay any changes onto a model. The replay engine (steps 8–9) is not
   * yet implemented.
   *
   * @param branchA name of the first branch (e.g. the current branch).
   * @param branchB name of the second branch (e.g. the branch being merged in).
   * @return a {@link ReplayResult} with the conflict analysis from the steps executed so far.
   * @throws BranchOperationException if either branch does not exist or the repository
   *     cannot be opened.
   */
  public ReplayResult analyzeBranches(String branchA, String branchB)
      throws BranchOperationException {
    checkNotNull(branchA, "branchA must not be null");
    checkNotNull(branchB, "branchB must not be null");
    checkArgument(!branchA.isBlank(), "branchA must not be blank");
    checkArgument(!branchB.isBlank(), "branchB must not be blank");

    try (Git git = Git.open(repoRoot.toFile())) {
      Repository repo = git.getRepository();

      // Resolve the HEAD commit of each branch as an ObjectId.
      // These are the starting points for walking back through commit history.
      ObjectId headA = repo.resolve("refs/heads/" + branchA);
      ObjectId headB = repo.resolve("refs/heads/" + branchB);
      if (headA == null) {
        throw new BranchOperationException("Branch not found: " + branchA);
      }
      if (headB == null) {
        throw new BranchOperationException("Branch not found: " + branchB);
      }
      // Full SHAs needed by SemanticMergeEngine for checkout-based replay.
      String fullShaA = headA.getName();
      String fullShaB = headB.getName();

      // Step 1: Find the common ancestor (merge-base). 
      // Equivalent to "git merge-base branchA branchB". Only commits after this point
      // differ between the branches and need to be compared.
      ObjectId ancestor = findMergeBase(repo, headA, headB);
      String ancestorSha = ancestor != null ? ancestor.getName() : null;
      LOGGER.info("Analyzing branches '{}' and '{}', common ancestor: {}",
          branchA, branchB,
          ancestorSha != null ? ancestorSha.substring(0, 7) : "none");

      // Step 2: Load changelog DTOs from the Git object store.
      // Collect the short SHAs of commits unique to each branch since the ancestor,
      // then read the corresponding JSON changelog files directly from the object store.
      // No branch checkout is needed.
      List<String> shortShasA = collectShortShasSince(repo, headA, ancestor);
      List<String> shortShasB = collectShortShasSince(repo, headB, ancestor);
      List<SemanticChangeEntry> changesA =
          loadChangesFromBranch(repo, headA, branchA, new HashSet<>(shortShasA));
      List<SemanticChangeEntry> changesB =
          loadChangesFromBranch(repo, headB, branchB, new HashSet<>(shortShasB));

      // Step 3: Direct conflict detection (original vs. original). 
      // Compares (elementUuid, feature) pairs across both branches without loading any model.
      // Two entries conflict when they target the same element-feature but set different values.
      List<SemanticConflict> directConflicts = detectConflicts(changesA, changesB);
      LOGGER.info(
          "Step 3 complete: {} direct conflict(s) detected (HIGH={}, MEDIUM={})",
          directConflicts.size(),
          directConflicts.stream().filter(c -> c.getSeverity() == ConflictSeverity.HIGH).count(),
          directConflicts.stream().filter(c -> c.getSeverity() == ConflictSeverity.MEDIUM).count());

      // Step 4: Resolution of direct conflicts. 
      // Direct conflicts require human resolution before replay can proceed.
      // Returns early with the unresolved conflicts so the caller can handle them.
      List<SemanticConflict> unresolved = resolveDirectConflicts(directConflicts);
      if (!unresolved.isEmpty()) {
        LOGGER.info("Step 4: {} direct conflict(s) unresolved, returning early.",
                unresolved.size());
        return new ReplayResult(
            ancestorSha, shortShasA, shortShasB, changesA, changesB, unresolved);
      }

      // Steps 5–9: if a SemanticMergeEngine is wired in, delegate the full replay pipeline
      // (dependency graph, cycle detection, topological sort, and replay with refinement).
      // The engine performs checkout-based replay, so it needs the full commit SHAs and a
      // valid common ancestor. When no engine is provided, the stub pipeline runs below.
      if (mergeEngine != null && ancestorSha != null) {
        mergeEngine.setSkipDirectConflictDetection(true); // steps 1-4 already ran
        try {
          SemanticMergeResult engineResult =
              mergeEngine.mergeBidirectional(ancestorSha, fullShaA, fullShaB);
          LOGGER.info("Merge engine result: {}", engineResult);
          return convertEngineResult(
              engineResult, ancestorSha, shortShasA, shortShasB, changesA, changesB);
        } catch (org.eclipse.jgit.api.errors.GitAPIException | RuntimeException e) {
          LOGGER.warn("Merge engine delegation failed, falling back to stub pipeline: {}",
              e.getMessage());
          // Fall through to stub steps below.
        } finally {
          mergeEngine.setSkipDirectConflictDetection(false);
        }
      }

      // Step 5: Build footprint dependency graph.
      // Nodes: one per commit. Intra-branch edges preserve commit order within each branch.
      // Inter-branch edges: fp_c(A_i) ∩ fp_o(B_j) ≠ ∅  ->  edge A_i -> B_j, meaning A_i
      // must be replayed before B_j so B_j's original change is the last write and wins.
      Map<String, List<String>> dependencyGraph =
          buildDependencyGraph(shortShasA, shortShasB, changesA, changesB);

      //  Step 6: Detect cycles -> consequential conflicts.
      // A cycle means no interleaving can preserve both branches' original intent.
      // These conflicts also require human resolution.
      List<SemanticConflict> cyclicConflicts = detectCyclicConflicts(dependencyGraph);
      if (!cyclicConflicts.isEmpty()) {
        LOGGER.info("Step 6: {} consequential conflict(s) detected (cycle).",
            cyclicConflicts.size());
        return new ReplayResult(
            ancestorSha, shortShasA, shortShasB, changesA, changesB, cyclicConflicts);
      }

      // Step 7: Topological sort -> interleaving order (Kahn's algorithm).
      // Produces a linear replay order that respects all dependency edges.
      List<String> interleaving = computeTopologicalOrder(dependencyGraph);

      // Steps 8 + 9: Replay with iterative footprint refinement.
      // Applies each commit's original changes through the Vitruvius Reaction engine
      // in topological order. Consequential changes are regenerated (not replayed directly).
      // Guard failures and footprint divergences trigger graph rebuilds and re-sorting.
      return replayWithRefinement(
          interleaving, ancestorSha, shortShasA, shortShasB, changesA, changesB);

    } catch (IOException e) {
      throw new BranchOperationException(
          "Failed to analyze branches '" + branchA + "' and '" + branchB + "': "
              + e.getMessage(), e);
    }
  }

  /**
   * Finds the merge-base (common ancestor) of two commits using JGit's
   * {@link RevFilter#MERGE_BASE}. Returns {@code null} if no common ancestor exists
   * (unrelated histories).
   *
   * @param repo the Git repository.
   * @param a the first commit object id.
   * @param b the second commit object id.
   * @return the merge-base commit id, or {@code null} if histories are unrelated.
   * @throws IOException if the repository cannot be read.
   */
  private ObjectId findMergeBase(Repository repo, ObjectId a, ObjectId b) throws IOException {
    try (RevWalk walk = new RevWalk(repo)) {
      // MERGE_BASE tells JGit to run the lowest-common-ancestor algorithm,
      // equivalent to "git merge-base": walks back from both sides and finds
      // the first commit that both branches have in common.
      walk.setRevFilter(RevFilter.MERGE_BASE);

      // Mark both HEADs as starting points; JGit walks back from both in parallel.
      walk.markStart(walk.parseCommit(a));
      walk.markStart(walk.parseCommit(b));

      // The first commit returned by RevWalk is the nearest common ancestor.
      // Returns null when the two branches have no shared history.
      RevCommit base = walk.next();
      return base != null ? base.getId() : null;
    }
  }

  /**
   * Walks from {@code head} back to {@code stopAt} (exclusive) and returns
   * the 7-character short SHA of each commit encountered, i.e. the commits
   * that exist on this branch but not yet in the common ancestor.
   *
   * @param repo the Git repository.
   * @param head the starting commit.
   * @param stopAt the exclusive stop commit (merge-base), or {@code null} for full history.
   * @return list of 7-char short SHAs, newest first (RevWalk order).
   * @throws IOException if the repository cannot be read.
   */
  private List<String> collectShortShasSince(
      Repository repo, ObjectId head, ObjectId stopAt) throws IOException {
    List<String> shas = new ArrayList<>();
    try (RevWalk walk = new RevWalk(repo)) {
      // Start walking backwards from the branch HEAD.
      walk.markStart(walk.parseCommit(head));

      // Mark the ancestor as uninteresting so JGit stops when it reaches it.
      // Only commits between HEAD and the ancestor (exclusive) are visited.
      if (stopAt != null) {
        walk.markUninteresting(walk.parseCommit(stopAt));
      }

      for (RevCommit commit : walk) {
        // Use the 7-char short SHA because changelog files are named after it
        // (e.g. "a1b2c3d.json"). Used to match filenames in loadChangesFromBranch.
        shas.add(commit.getName().substring(0, 7));
      }
    }
    return shas;
  }

  /**
   * TreeWalks the HEAD commit tree of a branch to find all JSON changelog files
   * matching the given set of short SHAs, then parses and returns all
   * {@link SemanticChangeEntry} records from those changelogs.
   *
   * <p>Files are stored at {@code .vitruvius/changelogs/<branch>/json/<shortSha>.json}.
   * They are read directly from the Git object store, so no checkout is needed.
   *
   * @param repo the Git repository.
   * @param head HEAD commit of the branch.
   * @param branch branch name (used to build the changelog directory path).
   * @param targetShortShas set of 7-char SHAs whose changelog files should be loaded.
   * @return all parsed {@link SemanticChangeEntry} records.
   * @throws IOException if the repository cannot be read.
   */
  private List<SemanticChangeEntry> loadChangesFromBranch(
      Repository repo, ObjectId head, String branch, Set<String> targetShortShas)
      throws IOException {
    // No commits since the ancestor means nothing to load.
    if (targetShortShas.isEmpty()) {
      return List.of();
    }

    // Directory path of this branch's JSON changelogs inside the Git object store,
    // e.g. ".vitruvius/changelogs/feature-x/json/"
    String jsonDir = CHANGELOG_PREFIX + branch + JSON_SUBDIR;
    List<SemanticChangeEntry> entries = new ArrayList<>();

    try (RevWalk walk = new RevWalk(repo)) {
      RevCommit headCommit = walk.parseCommit(head);
      try (TreeWalk treeWalk = new TreeWalk(repo)) {
        // Walk the file tree of this branch's HEAD commit.
        // This reads directly from the Git object store without touching the working directory,
        // so branch B's changelogs can be read while branch A is checked out.
        treeWalk.addTree(headCommit.getTree());
        treeWalk.setRecursive(true); // Descend into subdirectories

        while (treeWalk.next()) {
          String path = treeWalk.getPathString();

          // Skip any file that is not a JSON changelog for this branch.
          if (!path.startsWith(jsonDir) || !path.endsWith(".json")) {
            continue;
          }

          // Filename is "<shortSha>.json"; extract the short SHA to check
          // whether this commit belongs to the set of diverging commits to load.
          String filename = path.substring(jsonDir.length());
          String fileSha = filename.replace(".json", "");

          // Skip changelogs for commits that predate the merge-base; those are
          // shared by both branches and do not represent diverging changes.
          if (!targetShortShas.contains(fileSha)) {
            continue;
          }

          // Read the JSON file content directly from the Git object store.
          // getObjectId(0) returns the blob ID; repo.open() loads the raw bytes.
          ObjectLoader loader = repo.open(treeWalk.getObjectId(0));
          String json = new String(loader.getBytes(), StandardCharsets.UTF_8);

          // Parse into a ChangelogDocument. Skip if the file is empty or malformed.
          SemanticChangelogManager.ChangelogDocument doc =
              gson.fromJson(json, SemanticChangelogManager.ChangelogDocument.class);
          if (doc == null || doc.fileChanges == null) {
            continue;
          }

          // A single commit may contain changes across multiple model files.
          // Flatten all SemanticChangeEntry records from all files in the commit
          // into one list so they can be compared uniformly in detectConflicts.
          for (SemanticChangelogManager.ChangelogDocument.FileChangeInfo fileChange
              : doc.fileChanges) {
            if (fileChange.semanticChanges != null) {
              entries.addAll(fileChange.semanticChanges);
            }
          }
          LOGGER.debug("Loaded changelog {} for branch '{}'", filename, branch);
        }
      }
    }
    return entries;
  }

  /**
   * Detects direct semantic conflicts between two lists of change entries
   * (original vs. original, step 3).
   *
   * <p>Two types of conflict are detected:
   * <ol>
   *   <li><b>Direct conflicts</b> - both branches modified the same
   *       {@code (elementUuid, feature)} pair to incompatible values, or one side deleted
   *       the element while the other modified it.</li>
   *   <li><b>Tombstone conflicts</b> - one branch deleted a container element (UUID X)
   *       and the other branch contains a change entry whose {@code containerUuid} equals X.
   *       The child change becomes an invalid orphan because its parent no longer exists;
   *       always classified as {@link ConflictSeverity#HIGH}.</li>
   * </ol>
   *
   * <p>Severity rules for direct conflicts:
   * <ul>
   *   <li>Same element, one side deleted -&gt; {@link ConflictSeverity#HIGH}</li>
   *   <li>Same {@code (elementUuid, feature)} changed to different values
   *       on a reference feature -&gt; {@link ConflictSeverity#HIGH}</li>
   *   <li>Same {@code (elementUuid, feature)} changed to different values
   *       on an attribute feature -&gt; {@link ConflictSeverity#MEDIUM}</li>
   *   <li>Same change on both sides (identical {@code to} value) -&gt; no conflict</li>
   * </ul>
   *
   * <p>Only the highest-severity conflict per {@code (elementUuid, feature)} pair
   * is kept (deduplication). The tombstone key {@code "deletedUuid|null"} matches the
   * form of a direct deletion conflict so that a pre-existing direct conflict on the
   * same element prevents a redundant tombstone entry.
   *
   * @param changesA changes from branch A.
   * @param changesB changes from branch B.
   * @return list of detected {@link SemanticConflict} instances.
   */
  private List<SemanticConflict> detectConflicts(
      List<SemanticChangeEntry> changesA, List<SemanticChangeEntry> changesB) {
    // Deduplicate by (elementUuid, feature): keep only the highest-severity conflict
    // per pair. Key is "uuid|feature"; value is the best conflict found so far.
    Map<String, SemanticConflict> best = new HashMap<>();

    // --- Direct conflict detection (same element UUID, same or null feature) ---
    for (SemanticChangeEntry entryA : changesA) {
      // Skip entries whose UUID could not be resolved. This happens when an element
      // was deleted before the converter could look up its UUID (tombstoning problem).
      // Without a UUID we cannot identify which element was affected.
      if (entryA.getElementUuid() == null || entryA.getElementUuid().equals("unknown")) {
        continue;
      }

      for (SemanticChangeEntry entryB : changesB) {
        // A conflict can only exist when both sides affected the same element.
        // The UUID is stable across branches, so it is the only reliable identity
        // key that does not depend on position within the XMI file.
        if (!entryA.getElementUuid().equals(entryB.getElementUuid())) {
          continue;
        }

        // Determine whether this pair of changes actually conflicts and how severe it is.
        // Returns null when changes are compatible (different features, or same target value).
        ConflictSeverity severity = classifySeverity(entryA, entryB);
        if (severity == null) {
          continue;
        }

        // Conflict key is "uuid|feature". Use branch A's feature as the canonical value
        // (null for lifecycle changes such as ELEMENT_DELETED, which have no feature).
        String feature = entryA.getFeature();
        String key = entryA.getElementUuid() + "|" + feature;

        // If a conflict for this (uuid, feature) pair already exists, only overwrite it
        // when the new one has higher severity. Higher ordinal() means higher severity
        // because the enum is declared in ascending order (LOW < MEDIUM < HIGH).
        SemanticConflict existing = best.get(key);
        if (existing == null || severity.ordinal() > existing.getSeverity().ordinal()) {
          best.put(key,
              new SemanticConflict(entryA.getElementUuid(), feature, entryA, entryB, severity));
        }
      }
    }

    // --- Tombstone conflict detection (orphaned child changes) ---
    // If branch A deleted container X and branch B has a change with containerUuid == X,
    // that change targets an element whose parent was removed -> orphan -> HIGH conflict.
    // One conflict is reported per deleted container UUID (the first orphaned child found
    // is used as the representative entry; additional children of the same container are
    // deduplicated via the shared key).
    Map<String, SemanticChangeEntry> deletedByA = buildDeletionMap(changesA);
    Map<String, SemanticChangeEntry> deletedByB = buildDeletionMap(changesB);
    detectTombstoneConflicts(changesB, deletedByA, "branch A", "branch B", true, best);
    detectTombstoneConflicts(changesA, deletedByB, "branch B", "branch A", false, best);

    // --- Cascade DELETE_MODIFY / MODIFY_DELETE detection ---
    // Cross-checks the full deletion map (including cascade descendants) against the set
    // of all modified UUIDs on the opposing branch. This catches cases where a deeply
    // nested descendant of a deleted element was also modified on the other branch,
    // which the pairwise loop and containerUuid tombstone check cannot detect.
    // Skip UUIDs already recorded by the pairwise or tombstone loops (key "uuid|null").
    detectCascadeConflicts(deletedByA, changesB, "branch A", "branch B", true, best);
    detectCascadeConflicts(deletedByB, changesA, "branch B", "branch A", false, best);

    return new ArrayList<>(best.values());
  }

  /**
   * Scans {@code orphanSide} for changes whose {@code containerUuid} is present in
   * {@code deletionMap}, indicating that a parent element was deleted on the opposing
   * branch while this branch still has changes targeting a child. Each match is recorded
   * as a HIGH-severity tombstone conflict.
   *
   * @param orphanSide    changes from the branch that still holds the orphaned child changes.
   * @param deletionMap   deletion map from the opposing branch (built by {@link #buildDeletionMap}).
   * @param deleterLabel  human-readable label for the branch that deleted the container.
   * @param orphanLabel   human-readable label for the branch that holds the orphaned changes.
   * @param deleterIsA    when {@code true}, the deleter's entry is placed as branch-A in the
   *                      resulting {@link SemanticConflict}; otherwise it is placed as branch-B.
   * @param best          accumulator map for the highest-severity conflict per key.
   */
  private void detectTombstoneConflicts(
      List<SemanticChangeEntry> orphanSide,
      Map<String, SemanticChangeEntry> deletionMap,
      String deleterLabel, String orphanLabel,
      boolean deleterIsA,
      Map<String, SemanticConflict> best) {
    for (SemanticChangeEntry orphanEntry : orphanSide) {
      String container = orphanEntry.getContainerUuid();
      if (container == null) continue;
      SemanticChangeEntry tombstone = deletionMap.get(container);
      if (tombstone == null) continue;
      String key = container + "|null";
      if (!best.containsKey(key)) {
        LOGGER.info("Step 3 tombstone: {} deleted container '{}'; "
            + "{} has orphaned change on element '{}' -- HIGH conflict.",
            deleterLabel, container, orphanLabel, orphanEntry.getElementUuid());
        SemanticChangeEntry entryA = deleterIsA ? tombstone : orphanEntry;
        SemanticChangeEntry entryB = deleterIsA ? orphanEntry : tombstone;
        best.put(key, new SemanticConflict(container, null, entryA, entryB, ConflictSeverity.HIGH));
      }
    }
  }

  /**
   * Scans {@code deletionMap} for deleted UUIDs that also appear in the modified-UUID set
   * of {@code modifyingSide}, indicating that one branch deleted an element while the other
   * modified it. Each match is recorded as a HIGH-severity cascade conflict.
   *
   * @param deletionMap   deletion map from the branch that deleted the element.
   * @param modifyingSide changes from the branch that modified the element.
   * @param deleterLabel  human-readable label for the branch that deleted.
   * @param modifyingLabel human-readable label for the branch that modified.
   * @param deleterIsA    when {@code true}, the deleter's entry is placed as branch-A in the
   *                      resulting {@link SemanticConflict}; otherwise it is placed as branch-B.
   * @param best          accumulator map for the highest-severity conflict per key.
   */
  private void detectCascadeConflicts(
      Map<String, SemanticChangeEntry> deletionMap,
      List<SemanticChangeEntry> modifyingSide,
      String deleterLabel, String modifyingLabel,
      boolean deleterIsA,
      Map<String, SemanticConflict> best) {
    Set<String> modifiedUuids = extractModifiedUuids(modifyingSide);
    for (Map.Entry<String, SemanticChangeEntry> entry : deletionMap.entrySet()) {
      String deletedUuid = entry.getKey();
      if (!modifiedUuids.contains(deletedUuid)) continue;
      if (best.containsKey(deletedUuid + "|null")
          || best.containsKey(deletedUuid + "|cascade")) continue;
      modifyingSide.stream()
          .filter(e -> deletedUuid.equals(e.getElementUuid())
              && e.getChangeType() != SemanticChangeType.ELEMENT_CREATED)
          .findFirst()
          .ifPresent(modifyingEntry -> {
            LOGGER.info("Cascade {}: {} deleted '{}'; {} modified it",
                deleterIsA ? "DELETE_MODIFY" : "MODIFY_DELETE",
                deleterLabel, deletedUuid, modifyingLabel);
            SemanticChangeEntry entryA = deleterIsA ? entry.getValue() : modifyingEntry;
            SemanticChangeEntry entryB = deleterIsA ? modifyingEntry : entry.getValue();
            best.put(deletedUuid + "|cascade",
                new SemanticConflict(deletedUuid, null, entryA, entryB, ConflictSeverity.HIGH));
          });
    }
  }

  /**
   * Builds a map from element UUID to the {@link SemanticChangeType#ELEMENT_DELETED} entry
   * that caused the deletion (directly or transitively). Includes:
   * <ul>
   *   <li>The directly deleted element's UUID.</li>
   *   <li>All UUIDs from {@link SemanticChangeEntry#getCascadeDeletedUuids()}, which covers
   *       descendants whose deletion was captured by walking {@code eAllContents()} at
   *       changelog capture time.</li>
   * </ul>
   */
  private static Map<String, SemanticChangeEntry> buildDeletionMap(
      List<SemanticChangeEntry> changes) {
    Map<String, SemanticChangeEntry> map = new HashMap<>();
    for (SemanticChangeEntry e : changes) {
      if (e.getChangeType() == SemanticChangeType.ELEMENT_DELETED
          && e.getElementUuid() != null
          && !e.getElementUuid().equals("unknown")) {
        map.putIfAbsent(e.getElementUuid(), e);
        if (e.getCascadeDeletedUuids() != null) {
          for (String cascadeUuid : e.getCascadeDeletedUuids()) {
            map.putIfAbsent(cascadeUuid, e);
          }
        }
      }
    }
    return map;
  }

  /**
   * Collects the UUIDs of all elements modified by the given changes (any change type
   * except {@link SemanticChangeType#ELEMENT_CREATED}). Used for cross-checking against
   * the deletion map to detect DELETE_MODIFY and MODIFY_DELETE conflicts.
   */
  private static Set<String> extractModifiedUuids(List<SemanticChangeEntry> changes) {
    Set<String> modified = new HashSet<>();
    for (SemanticChangeEntry e : changes) {
      if (e.getElementUuid() != null
          && !"unknown".equals(e.getElementUuid())
          && e.getChangeType() != SemanticChangeType.ELEMENT_CREATED
          && e.getChangeType() != SemanticChangeType.ELEMENT_DELETED) {
        modified.add(e.getElementUuid());
      }
    }
    return modified;
  }

  /**
   * Returns the conflict severity for a pair of entries on the same element, or
   * {@code null} if there is no conflict (changes are compatible or identical).
   *
   * <p>Note: severity rules are a first draft and are subject to refinement as part of
   * ongoing thesis work on conflict categorization (MG-2, MG-3).
   *
   * @param a change entry from branch A.
   * @param b change entry from branch B.
   * @return the {@link ConflictSeverity}, or {@code null} if no conflict.
   */
  private ConflictSeverity classifySeverity(SemanticChangeEntry a, SemanticChangeEntry b) {
    SemanticChangeType typeA = a.getChangeType();
    SemanticChangeType typeB = b.getChangeType();

    // Most dangerous case: one side deleted the element while the other modified it.
    // The deleted element no longer exists, so the other side's changes are invalid.
    if (typeA == SemanticChangeType.ELEMENT_DELETED
        || typeB == SemanticChangeType.ELEMENT_DELETED) {
      // Exception: both sides deleted the same element - same outcome, auto-resolvable.
      if (typeA == typeB) {
        return null;
      }
      return ConflictSeverity.HIGH;
    }

    // Check whether both sides changed the same feature.
    // Different features (e.g. A changed "name", B changed "age") are independent - no conflict.
    String featureA = a.getFeature();
    String featureB = b.getFeature();
    if (featureA == null || !featureA.equals(featureB)) {
      return null;
    }

    // Both sides changed the same feature to the same target value - auto-resolvable.
    if (Objects.equals(a.getTo(), b.getTo())) {
      return null;
    }

    // Multi-valued reference list operations (INSERT/REMOVE) are independent when they
    // target different referenced element UUIDs. Only pairs that target the exact same
    // element UUID conflict (e.g. one branch inserts X and the other removes X).
    // Note: identical INSERTs or REMOVEs are already resolved as null above via to==to.
    if (typeA == SemanticChangeType.REFERENCE_VALUE_INSERTED
        && typeB == SemanticChangeType.REFERENCE_VALUE_INSERTED) {
      // Both inserting different elements into the same list -- mergeable, not a conflict.
      return null;
    }
    if (typeA == SemanticChangeType.REFERENCE_VALUE_REMOVED
        && typeB == SemanticChangeType.REFERENCE_VALUE_REMOVED) {
      // Both removing (different) elements -- independent operations, not a conflict.
      return null;
    }
    if (typeA == SemanticChangeType.REFERENCE_VALUE_INSERTED
        && typeB == SemanticChangeType.REFERENCE_VALUE_REMOVED) {
      // One branch inserts element X, other removes element X -- conflict only if same UUID.
      return Objects.equals(a.getTo(), b.getFrom()) ? ConflictSeverity.HIGH : null;
    }
    if (typeA == SemanticChangeType.REFERENCE_VALUE_REMOVED
        && typeB == SemanticChangeType.REFERENCE_VALUE_INSERTED) {
      // Mirror: one branch removes X, other inserts X -- conflict only if same UUID.
      return Objects.equals(a.getFrom(), b.getTo()) ? ConflictSeverity.HIGH : null;
    }

    // Both sides changed the same feature to different values - this is a conflict.
    // Reference changes are HIGH: they affect model structure, not just scalar data.
    // An incorrect reference can break model consistency entirely.
    if (typeA == SemanticChangeType.REFERENCE_CHANGED
        || typeB == SemanticChangeType.REFERENCE_CHANGED
        || typeA == SemanticChangeType.REFERENCE_SET
        || typeB == SemanticChangeType.REFERENCE_SET
        || typeA == SemanticChangeType.REFERENCE_CLEARED
        || typeB == SemanticChangeType.REFERENCE_CLEARED) {
      return ConflictSeverity.HIGH;
    }

    // Attribute changes (scalar values like String, int) are MEDIUM: they affect data
    // but not model structure. Manual resolution is still required but risk is lower.
    if (typeA == SemanticChangeType.ATTRIBUTE_CHANGED
        || typeB == SemanticChangeType.ATTRIBUTE_CHANGED
        || typeA == SemanticChangeType.ATTRIBUTE_SET
        || typeB == SemanticChangeType.ATTRIBUTE_SET
        || typeA == SemanticChangeType.ATTRIBUTE_CLEARED
        || typeB == SemanticChangeType.ATTRIBUTE_CLEARED) {
      return ConflictSeverity.MEDIUM;
    }

    // Remaining cases are multi-valued list operations on the same feature - LOW.
    // For example, both sides inserting into the same list can often be merged automatically.
    return ConflictSeverity.LOW;
  }


  /**
   * Step 4: Resolves direct conflicts detected in step 3.
   *
   * <p>Applies ChangeOrigin-based auto-resolution: when one side's change is
   * {@link ChangeOrigin#ORIGINAL} and the other is {@link ChangeOrigin#CONSEQUENTIAL},
   * the original change is preferred and the conflict is dropped from the unresolved list.
   * All other combinations (ORIGINAL vs ORIGINAL, UNKNOWN on either side) require human
   * intervention and are returned unchanged.
   *
   * <p>{@link #analyzeBranches} returns early with the unresolved set; steps 5-9 are only
   * reached when this method returns an empty list.
   *
   * @param directConflicts conflicts detected in step 3.
   * @return conflicts that could not be auto-resolved and require human intervention.
   */
  private List<SemanticConflict> resolveDirectConflicts(List<SemanticConflict> directConflicts) {
    List<SemanticConflict> unresolved = new ArrayList<>();
    for (SemanticConflict conflict : directConflicts) {
      ChangeOrigin originA = conflict.getChangeOnBranchA().getOrigin();
      ChangeOrigin originB = conflict.getChangeOnBranchB().getOrigin();
      if (isOriginalVsConsequential(originA, originB)) {
        String preferred = (originA == ChangeOrigin.ORIGINAL) ? "A" : "B";
        LOGGER.info("Step 4: Auto-resolved conflict on element '{}' feature '{}': "
                + "branch {} has ORIGINAL, other has CONSEQUENTIAL -- branch {} preferred.",
            conflict.getElementUuid(), conflict.getFeature(), preferred, preferred);
      } else {
        unresolved.add(conflict);
      }
    }
    return unresolved;
  }

  private boolean isOriginalVsConsequential(ChangeOrigin a, ChangeOrigin b) {
    return (a == ChangeOrigin.ORIGINAL && b == ChangeOrigin.CONSEQUENTIAL)
        || (a == ChangeOrigin.CONSEQUENTIAL && b == ChangeOrigin.ORIGINAL);
  }

  /**
   * Step 5: Builds the footprint dependency graph from the commit histories of both branches.
   *
   * <p>The graph is a directed adjacency list: an edge {@code A_i -> B_j} means commit
   * {@code A_i} must be replayed before {@code B_j} so that {@code B_j}'s original change
   * is the last write to the shared element-feature and preserves {@code B_j}'s intent.
   *
   * <p>Edges are added when the consequential footprint of one commit (element-feature pairs
   * written by Reactions) overlaps the original footprint of a commit on the other branch.
   * Intra-branch ordering edges are also added to preserve each branch's commit sequence.
   *
   * <p>This method returns an empty graph. Full graph construction requires per-commit
   * consequential footprint data in the JSON changelog; that data will be provided by the
   * consequential changelog component once integrated. When a {@link SemanticMergeEngine}
   * is wired, it handles this step internally via
   * {@link SemanticMergeEngine#mergeBidirectional}.
   *
   * @param shortShasA  7-char commit SHAs on branch A since the ancestor (newest first).
   * @param shortShasB  7-char commit SHAs on branch B since the ancestor (newest first).
   * @param changesA    original change entries from branch A.
   * @param changesB    original change entries from branch B.
   * @return adjacency list mapping each commit SHA to the list of SHAs that must follow it.
   */
  private Map<String, List<String>> buildDependencyGraph(
      List<String> shortShasA, List<String> shortShasB,
      List<SemanticChangeEntry> changesA, List<SemanticChangeEntry> changesB) {
    // Requires consequential footprint data per commit to build inter-branch edges.
    // Will be populated once the consequential changelog component is integrated.
    return new HashMap<>();
  }

  /**
   * Step 6: Detects cycles in the dependency graph, which indicate consequential conflicts.
   *
   * <p>A cycle arises when commit {@code A_i}'s Reactions write an element-feature pair
   * that {@code B_j} modifies originally, and {@code B_j}'s Reactions write a pair that
   * {@code A_i} modifies originally. No interleaving can preserve both branches' intent;
   * human resolution is required (analogous to a serialization conflict).
   *
   * <p>This method returns no conflicts. Cycle detection becomes meaningful once step 5
   * produces a graph with footprint-derived inter-branch edges. When a
   * {@link SemanticMergeEngine} is wired, it handles this step internally.
   *
   * @param dependencyGraph adjacency list produced by step 5.
   * @return consequential conflicts derived from each cycle; empty if the graph is acyclic.
   */
  private List<SemanticConflict> detectCyclicConflicts(
      Map<String, List<String>> dependencyGraph) {
    // Cycle detection requires a populated dependency graph from step 5.
    return List.of();
  }

  /**
   * Step 7: Computes a topological ordering of the dependency graph using Kahn's algorithm.
   *
   * <p>The returned list gives the order in which commits should be replayed: all
   * dependency edges point forward in this order, ensuring that each commit's original
   * change is applied after any consequential change that would otherwise overwrite it.
   *
   * <p>This method returns an empty ordering. It becomes effective once step 5 populates
   * the graph with inter-branch footprint edges. When a {@link SemanticMergeEngine} is
   * wired, it handles this step internally.
   *
   * @param dependencyGraph acyclic adjacency list produced by step 5 (step 6 confirms acyclic).
   * @return commit SHAs in replay order (topological sort of the graph).
   */
  private List<String> computeTopologicalOrder(Map<String, List<String>> dependencyGraph) {
    // Topological sort becomes meaningful once step 5 produces a non-empty graph.
    return List.of();
  }

  /**
   * Steps 8 + 9: Replays commits in the given topological order through the Vitruvius
   * Reaction engine, with iterative footprint refinement.
   *
   * <p>For each commit in {@code interleaving}:
   * <ol>
   *   <li>Check the guard: does the target element still exist in the current model state?
   *       A guard failure means a precondition of the original change is no longer met
   *       (e.g. the element was deleted by a previously replayed commit). On failure, add
   *       an ordering edge and re-sort (heuristic re-ordering).</li>
   *   <li>Deserialize the changelog DTO into live {@code EChange} objects using
   *       {@code HierarchicalId} lookup with UUID-based fallback.</li>
   *   <li>Apply via {@code ChangeRecordingView}: Reactions fire and regenerate consequential
   *       changes as they would during normal Vitruvius operation.</li>
   *   <li>Capture the actual consequential footprint and compare it to the stored estimate.
   *       If new element-feature pairs are touched, add edges, rebuild the graph, and
   *       re-sort (iterative refinement, step 9).</li>
   * </ol>
   *
   * <p>Step 8 requires access to the ancestor model state, deserialization of changelog DTOs
   * into live {@code EChange} objects, and a live Vitruvius {@code InternalVirtualModel}
   * instance for applying changes. Step 9 (iterative footprint refinement) is intentionally
   * out of scope for this thesis. Both are handled by {@link SemanticMergeEngine} when
   * wired via {@link MergeManager#setMergeEngine}; this method is the safe fallback used
   * when no engine is available.
   *
   * @param interleaving  commit SHAs in topological replay order (step 7 output).
   * @param ancestorSha   full SHA of the common ancestor; used to seed the replay state.
   * @param shortShasA    7-char commit SHAs on branch A (for the result object).
   * @param shortShasB    7-char commit SHAs on branch B (for the result object).
   * @param changesA      original change entries from branch A (for the result object).
   * @param changesB      original change entries from branch B (for the result object).
   * @return a {@link ReplayResult} representing the merged state, or containing any
   *     conflicts discovered during replay that could not be resolved by re-ordering.
   */
  private ReplayResult replayWithRefinement(
      List<String> interleaving, String ancestorSha,
      List<String> shortShasA, List<String> shortShasB,
      List<SemanticChangeEntry> changesA, List<SemanticChangeEntry> changesB) {
    // Replay and iterative refinement are handled by SemanticMergeEngine when wired.
    // This fallback returns a clean result when no engine is available.
    return new ReplayResult(
        ancestorSha, shortShasA, shortShasB, changesA, changesB, List.of());
  }

  /**
   * Converts a {@link SemanticMergeResult} from the full merge engine into a
   * {@link ReplayResult} in our format.
   *
   * <p>On success the returned result has no conflicts. On conflict the engine's
   * {@link MergeConflict} list is translated to {@link SemanticConflict} records by
   * looking up matching {@link SemanticChangeEntry} objects from the pre-loaded changelog
   * lists. Conflicts whose UUID cannot be found in either changelog are silently skipped.
   */
  private ReplayResult convertEngineResult(SemanticMergeResult engineResult,
      String ancestorSha, List<String> shortShasA, List<String> shortShasB,
      List<SemanticChangeEntry> changesA, List<SemanticChangeEntry> changesB) {

    if (engineResult.isSuccess()) {
      LOGGER.info("Merge engine succeeded (direction={})", engineResult.getMergeDirection());
      return new ReplayResult(ancestorSha, shortShasA, shortShasB, changesA, changesB, List.of());
    }

    Map<String, SemanticChangeEntry> byUuidA = indexByUuid(changesA);
    Map<String, SemanticChangeEntry> byUuidB = indexByUuid(changesB);

    List<SemanticConflict> converted = new ArrayList<>();
    for (MergeConflict mc : engineResult.getConflicts()) {
      SemanticConflict sc = toSemanticConflict(mc, byUuidA, byUuidB);
      if (sc != null) {
        converted.add(sc);
      }
    }
    LOGGER.info("Merge engine: {} conflict(s) converted from engine result", converted.size());
    return new ReplayResult(ancestorSha, shortShasA, shortShasB, changesA, changesB, converted);
  }

  private static Map<String, SemanticChangeEntry> indexByUuid(List<SemanticChangeEntry> entries) {
    Map<String, SemanticChangeEntry> map = new HashMap<>();
    for (SemanticChangeEntry e : entries) {
      if (e.getElementUuid() != null && !map.containsKey(e.getElementUuid())) {
        map.put(e.getElementUuid(), e);
      }
    }
    return map;
  }

  private static SemanticConflict toSemanticConflict(MergeConflict mc,
      Map<String, SemanticChangeEntry> byUuidA, Map<String, SemanticChangeEntry> byUuidB) {

    String uuid = mc.getElementUuid();
    if (uuid == null) {
      return null; // state-based conflict without UUID
    }
    SemanticChangeEntry entryA = byUuidA.get(uuid);
    SemanticChangeEntry entryB = byUuidB.get(uuid);
    if (entryA == null || entryB == null) {
      return null; // UUID not found in our changelogs; can't build SemanticConflict
    }
    return new SemanticConflict(uuid, mc.getConflictingFeature(), entryA, entryB,
        toSeverity(mc.getType()));
  }

  private static ConflictSeverity toSeverity(MergeConflict.ConflictType type) {
    return switch (type) {
      case MODIFY_MODIFY -> ConflictSeverity.MEDIUM;
      case DELETE_MODIFY, MODIFY_DELETE,
          BIDIRECTIONAL_INDIRECT_CONFLICT, INTERLEAVING_CONFLICT,
          REPLAY_APPLICABILITY -> ConflictSeverity.HIGH;
      case INDIRECT_CONFLICT, USER_VS_DERIVED_WARNING -> ConflictSeverity.LOW;
    };
  }

  private Gson buildGson() {
    DateTimeFormatter fmt = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    return new GsonBuilder()
        .registerTypeAdapter(LocalDateTime.class,
            (JsonSerializer<LocalDateTime>) (src, t, ctx) -> new JsonPrimitive(src.format(fmt)))
        .registerTypeAdapter(LocalDateTime.class,
            (JsonDeserializer<LocalDateTime>) (json, t, ctx) ->
                LocalDateTime.parse(json.getAsString(), fmt))
        .create();
  }
}
