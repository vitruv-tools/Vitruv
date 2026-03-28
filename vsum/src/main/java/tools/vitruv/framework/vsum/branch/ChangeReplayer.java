package tools.vitruv.framework.vsum.branch;

import com.google.gson.*;
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
import tools.vitruv.framework.vsum.branch.storage.SemanticChangeEntry;
import tools.vitruv.framework.vsum.branch.storage.SemanticChangeType;
import tools.vitruv.framework.vsum.branch.storage.SemanticChangelogManager;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Replays semantic changes from two diverged branches since their common ancestor
 * and detects element-level conflicts (DE-4, MG-2, MG-3).
 *
 * <h3>Motivation</h3>
 * <p>JGit's three-way merge ({@link MergeManager}) works at the text level: it produces
 * conflict markers in XMI files when both branches edited the same XML lines. A developer
 * sees a file conflict, not a model conflict. The ChangeReplayer lifts this to the <em>semantic</em> level:
 * <pre>
 *   Branch A: changed element 'e1', attribute 'name', from 'Foo' to 'Bar'
 *   Branch B: changed element 'e1', attribute 'name', from 'Foo' to 'Baz'
 *   -> MEDIUM conflict: same attribute modified to different values
 * </pre>
 *
 * <h3>How it works</h3>
 * <ol>
 *   <li>Find the <em>merge-base</em> (common ancestor) of the two branches using JGit's
 *       {@link RevFilter#MERGE_BASE}.</li>
 *   <li>Walk each branch's commit history from HEAD back to the merge-base and collect
 *       the 7-character short SHAs of all diverging commits.</li>
 *   <li>TreeWalk each branch's HEAD commit tree to read JSON changelog files under
 *       {@code .vitruvius/changelogs/<branch>/json/<shortSha>.json} that match those
 *       short SHAs - without checking out the branch.</li>
 *   <li>Parse the JSON changelogs into {@link SemanticChangeEntry} records.</li>
 *   <li>Detect conflicts: entries where both branches changed the same
 *       {@code (elementUuid, feature)} tuple to different values.</li>
 *   <li>Classify each conflict's {@link ConflictSeverity} using the rules documented
 *       in {@link SemanticChangeType}.</li>
 * </ol>
 *
 * <h3>Usage</h3>
 * <p>Call before or after a merge to understand its semantic impact:
 * <pre>{@code
 *   ChangeReplayer replayer = new ChangeReplayer(repoRoot);
 *   ReplayResult result = replayer.replayBranches("feature-x", "main");
 *   if (result.hasConflicts()) {
 *       result.getConflicts().forEach(c -> log.warn("Conflict: {}", c));
 *   }
 * }</pre>
 */
public class ChangeReplayer {

    private static final Logger LOGGER = LogManager.getLogger(ChangeReplayer.class);
    private static final String CHANGELOG_PREFIX = ".vitruvius/changelogs/";
    private static final String JSON_SUBDIR = "/json/";

    private final Path repoRoot;
    private final Gson gson;

    /**
     * Creates a new {@link ChangeReplayer} for the repository at the given root.
     *
     * @param repoRoot the root of the Git repository; must contain {@code .git/}.
     */
    public ChangeReplayer(Path repoRoot) {
        this.repoRoot = checkNotNull(repoRoot, "repoRoot must not be null");
        checkArgument(Files.isDirectory(repoRoot.resolve(".git")), "No Git repository found at: %s", repoRoot);
        this.gson = buildGson();
    }


    /**
     * Replays semantic changes for two branches since their common ancestor and
     * returns a {@link ReplayResult} describing what each branch changed and
     * which changes conflict.
     *
     * @param branchA name of the first branch (e.g. the current branch).
     * @param branchB name of the second branch (e.g. the branch being merged in).
     * @return a {@link ReplayResult} with full conflict analysis.
     * @throws BranchOperationException if either branch does not exist or the repository cannot be opened.
     */
    public ReplayResult replayBranches(String branchA, String branchB) throws BranchOperationException {
        checkNotNull(branchA, "branchA must not be null");
        checkNotNull(branchB, "branchB must not be null");
        checkArgument(!branchA.isBlank(), "branchA must not be blank");
        checkArgument(!branchB.isBlank(), "branchB must not be blank");

        try (Git git = Git.open(repoRoot.toFile())) {
            Repository repo = git.getRepository();

            ObjectId headA = repo.resolve("refs/heads/" + branchA);
            ObjectId headB = repo.resolve("refs/heads/" + branchB);
            if (headA == null) throw new BranchOperationException("Branch not found: " + branchA);
            if (headB == null) throw new BranchOperationException("Branch not found: " + branchB);

            // 1. Find common ancestor.
            ObjectId ancestor = findMergeBase(repo, headA, headB);
            String ancestorSha = ancestor != null ? ancestor.getName() : null;
            LOGGER.info("Replaying branches '{}' and '{}', common ancestor: {}", branchA, branchB, ancestorSha != null ? ancestorSha.substring(0, 7) : "none");

            // 2. Collect short SHAs of commits unique to each branch since the ancestor.
            List<String> shortShasA = collectShortShasSince(repo, headA, ancestor);
            List<String> shortShasB = collectShortShasSince(repo, headB, ancestor);
            LOGGER.debug("Commits since ancestor - {}: {}, {}: {}", branchA, shortShasA.size(), branchB, shortShasB.size());

            // 3. Load semantic change entries from the JSON changelogs in each branch's tree.
            List<SemanticChangeEntry> changesA = loadChangesFromBranch(repo, headA, branchA, new HashSet<>(shortShasA));
            List<SemanticChangeEntry> changesB = loadChangesFromBranch(repo, headB, branchB, new HashSet<>(shortShasB));
            LOGGER.debug("Semantic changes loaded - {}: {}, {}: {}", branchA, changesA.size(), branchB, changesB.size());

            // 4. Detect semantic conflicts.
            List<SemanticConflict> conflicts = detectConflicts(changesA, changesB);
            LOGGER.info("Replay complete: {} conflict(s) detected (HIGH={}, MEDIUM={})", conflicts.size(), conflicts.stream().filter(c -> c.getSeverity() == ConflictSeverity.HIGH).count(), conflicts.stream().filter(c -> c.getSeverity() == ConflictSeverity.MEDIUM).count());

            return new ReplayResult(ancestorSha, shortShasA, shortShasB, changesA, changesB, conflicts);

        } catch (IOException e) {
            throw new BranchOperationException("Failed to replay branches '" + branchA + "' and '" + branchB + "': " + e.getMessage(), e);
        }
    }


    // JGit helpers


    /**
     * Finds the merge-base (common ancestor) of two commits using JGit's
     * {@link RevFilter#MERGE_BASE}. Returns {@code null} if no common ancestor exists
     * (unrelated histories).
     */
    private ObjectId findMergeBase(Repository repo, ObjectId a, ObjectId b) throws IOException {
        try (RevWalk walk = new RevWalk(repo)) {
            walk.setRevFilter(RevFilter.MERGE_BASE);
            walk.markStart(walk.parseCommit(a));
            walk.markStart(walk.parseCommit(b));
            RevCommit base = walk.next();
            return base != null ? base.getId() : null;
        }
    }

    /**
     * Walks from {@code head} back to {@code stopAt} (exclusive) and returns
     * the 7-character short SHA of each commit encountered - i.e. the commits
     * that exist on this branch but not yet in the common ancestor.
     */
    private List<String> collectShortShasSince(Repository repo, ObjectId head, ObjectId stopAt) throws IOException {
        List<String> shas = new ArrayList<>();
        try (RevWalk walk = new RevWalk(repo)) {
            walk.markStart(walk.parseCommit(head));
            if (stopAt != null) {
                walk.markUninteresting(walk.parseCommit(stopAt));
            }
            for (RevCommit commit : walk) {
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
     * <p>The files are stored at
     * {@code .vitruvius/changelogs/<branch>/json/<shortSha>.json} inside the commit tree.
     * This method reads them directly from the Git object store, so no checkout is needed.
     */
    private List<SemanticChangeEntry> loadChangesFromBranch(Repository repo, ObjectId head, String branch, Set<String> targetShortShas) throws IOException {
        if (targetShortShas.isEmpty()) {
            return List.of();
        }

        String jsonDir = CHANGELOG_PREFIX + branch + JSON_SUBDIR;
        List<SemanticChangeEntry> entries = new ArrayList<>();

        try (RevWalk walk = new RevWalk(repo)) {
            RevCommit headCommit = walk.parseCommit(head);
            try (TreeWalk treeWalk = new TreeWalk(repo)) {
                treeWalk.addTree(headCommit.getTree());
                treeWalk.setRecursive(true);

                while (treeWalk.next()) {
                    String path = treeWalk.getPathString();
                    if (!path.startsWith(jsonDir) || !path.endsWith(".json")) continue;

                    // Filename is "<shortSha>.json"
                    String filename = path.substring(jsonDir.length());
                    String fileSha = filename.substring(0, Math.min(7, filename.length() - 5));
                    if (!targetShortShas.contains(fileSha)) continue;

                    ObjectLoader loader = repo.open(treeWalk.getObjectId(0));
                    String json = new String(loader.getBytes(), StandardCharsets.UTF_8);

                    SemanticChangelogManager.ChangelogDocument doc = gson.fromJson(json, SemanticChangelogManager.ChangelogDocument.class);
                    if (doc == null || doc.fileChanges == null) continue;

                    for (SemanticChangelogManager.ChangelogDocument.FileChangeInfo fileChange : doc.fileChanges) {
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


    // Conflict detection

    /**
     * Detects semantic conflicts between two lists of change entries.
     *
     * <p>Conflict rules (from {@link SemanticChangeType} javadoc):
     * <ul>
     *   <li>Same element, one side deleted -> {@link ConflictSeverity#HIGH}</li>
     *   <li>Same {@code (elementUuid, feature)} changed to different values
     *       on a reference feature -> {@link ConflictSeverity#HIGH}</li>
     *   <li>Same {@code (elementUuid, feature)} changed to different values
     *       on an attribute feature -> {@link ConflictSeverity#MEDIUM}</li>
     *   <li>Same change on both sides (identical {@code to} value) -> no conflict</li>
     * </ul>
     *
     * <p>Only the highest-severity conflict per {@code (elementUuid, feature)} pair
     * is kept (deduplication).
     */
    private List<SemanticConflict> detectConflicts(List<SemanticChangeEntry> changesA, List<SemanticChangeEntry> changesB) {
        // Deduplicate: key = "elementUuid|feature", value = highest-severity conflict seen so far.
        Map<String, SemanticConflict> best = new HashMap<>();

        for (SemanticChangeEntry entryA : changesA) {
            if (entryA.getElementUuid() == null || entryA.getElementUuid().equals("unknown")) continue;

            for (SemanticChangeEntry entryB : changesB) {
                if (!entryA.getElementUuid().equals(entryB.getElementUuid())) continue;

                ConflictSeverity severity = classifySeverity(entryA, entryB);
                if (severity == null) continue;

                // Use the feature of the A side as the conflict key (null for lifecycle)
                String feature = entryA.getFeature();
                String key = entryA.getElementUuid() + "|" + feature;

                SemanticConflict existing = best.get(key);
                if (existing == null || severity.ordinal() > existing.getSeverity().ordinal()) {
                    best.put(key, new SemanticConflict(entryA.getElementUuid(), feature, entryA, entryB, severity));
                }
            }
        }
        return new ArrayList<>(best.values());
    }

    /**
     * Returns the conflict severity for a pair of entries on the same element, or
     * {@code null} if there is no conflict (changes are compatible or identical).
     */
    private ConflictSeverity classifySeverity(SemanticChangeEntry a, SemanticChangeEntry b) {
        SemanticChangeType typeA = a.getChangeType();
        SemanticChangeType typeB = b.getChangeType();

        // Element deleted on one side + any change on the other = HIGH
        if (typeA == SemanticChangeType.ELEMENT_DELETED || typeB == SemanticChangeType.ELEMENT_DELETED) {
            // Both deleted the same element: identical outcome, not a conflict
            if (typeA == typeB) return null;
            return ConflictSeverity.HIGH;
        }

        // Both changed the same feature
        String featureA = a.getFeature();
        String featureB = b.getFeature();
        if (featureA == null || !featureA.equals(featureB)) {
            return null; // different features on the same element = no conflict
        }

        // Identical change on both branches -> auto-resolvable, not a conflict
        if (Objects.equals(a.getTo(), b.getTo())) {
            return null;
        }

        // Diverging reference change -> HIGH
        if (typeA == SemanticChangeType.REFERENCE_CHANGED || typeB == SemanticChangeType.REFERENCE_CHANGED || typeA == SemanticChangeType.REFERENCE_VALUE_INSERTED || typeB == SemanticChangeType.REFERENCE_VALUE_INSERTED || typeA == SemanticChangeType.REFERENCE_VALUE_REMOVED || typeB == SemanticChangeType.REFERENCE_VALUE_REMOVED || typeA == SemanticChangeType.REFERENCE_SET || typeB == SemanticChangeType.REFERENCE_SET || typeA == SemanticChangeType.REFERENCE_CLEARED || typeB == SemanticChangeType.REFERENCE_CLEARED) {
            return ConflictSeverity.HIGH;
        }

        // Diverging attribute change -> MEDIUM
        if (typeA == SemanticChangeType.ATTRIBUTE_CHANGED || typeB == SemanticChangeType.ATTRIBUTE_CHANGED || typeA == SemanticChangeType.ATTRIBUTE_SET || typeB == SemanticChangeType.ATTRIBUTE_SET || typeA == SemanticChangeType.ATTRIBUTE_CLEARED || typeB == SemanticChangeType.ATTRIBUTE_CLEARED) {
            return ConflictSeverity.MEDIUM;
        }

        // Multi-valued insertions/removals on same feature -> LOW
        return ConflictSeverity.LOW;
    }

    private Gson buildGson() {
        DateTimeFormatter fmt = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        return new GsonBuilder().registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (src, t, ctx) -> new JsonPrimitive(src.format(fmt))).registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, t, ctx) -> LocalDateTime.parse(json.getAsString(), fmt)).create();
    }
}
