package tools.vitruv.framework.vsum.branch.storage;

import com.google.gson.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.TreeWalk;
import tools.vitruv.change.atomic.EChange;
import tools.vitruv.change.atomic.eobject.CreateEObject;
import tools.vitruv.change.atomic.eobject.DeleteEObject;
import tools.vitruv.change.atomic.hid.internal.HierarchicalIdResolver;
import tools.vitruv.change.atomic.uuid.UuidResolver;
import tools.vitruv.framework.vsum.branch.data.FileOperation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Writes and reads semantic changelog files stored under
 * {@code .vitruvius/changelogs/<branch>/json/<shortSha>.json} (JSON) and
 * {@code .vitruvius/changelogs/<branch>/xmi/<shortSha>.xmi} (XMI delta snapshots).
 *
 * <p>At commit time, call {@link #write} with the drained contents of a
 * {@link SemanticChangeBuffer} to produce both files atomically.
 * Both files are staged as part of the same Git commit.
 *
 * <p><b>JSON format</b> - human-readable, operation-based, UUID-keyed (stable across branches):
 * <pre>
 * {
 *   "formatVersion": "1.0",
 *   "commit": {
 *      "sha": "...",
 *      "branch": "...",
 *      "author": { ... },
 *      "message": "...",
 *      "parentShas": [...] },
 *   "fileChanges": [
 *     {
 *       "operation": "MODIFIED",
 *       "path": "model/example.xmi",
 *       "oldPath": null,
 *       "semanticChanges": [ { "index": 0, "changeType": "ELEMENT_CREATED", ... }, ... ]
 *     }
 *   ],
 *   "summary": { "totalFileChanges": 1, "totalSemanticChanges": 3, "affectedElementUuids": [...] }
 * }
 * </pre>
 */
public class SemanticChangelogManager {

  private static final Logger LOGGER = LogManager.getLogger(SemanticChangelogManager.class);

  private static final String FORMAT_VERSION = "1.0";

  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

  private final Path repositoryRoot;

  private final Gson gson;

  /**
   * Creates a manager for the Git repository at the given root.
   *
   * @param repositoryRoot root directory of the repository, must not be null.
   */
  public SemanticChangelogManager(Path repositoryRoot) {
    this.repositoryRoot = checkNotNull(repositoryRoot, "repositoryRoot must not be null");
    this.gson = buildGson();
  }

  /**
   * Writes the JSON changelog for the given commit using a {@link SemanticChangeBuffer.DrainResult}.
   * The drain result provides both the accumulated changes and any pre-captured deletion UUID
   * overrides so that deleted elements are recorded with their correct UUID rather than "unknown".
   *
   * @param drainResult the result of {@link SemanticChangeBuffer#drainChanges()}, must not be null.
   */
  public List<Path> write(
      String commitSha, String branch, String author, LocalDateTime authorDate,
      String message, List<String> parentShas,
      SemanticChangeBuffer.DrainResult drainResult,
      Collection<Resource> activeResources, UuidResolver uuidResolver) throws IOException {

    checkNotNull(commitSha, "commitSha must not be null");
    checkNotNull(branch, "branch must not be null");
    checkNotNull(drainResult, "drainResult must not be null");
    checkNotNull(uuidResolver, "uuidResolver must not be null");

    Map<String, List<EChange<EObject>>> changesByResource = drainResult.changesByResource();

    String shortSha = commitSha.substring(0, Math.min(7, commitSha.length()));
    List<Path> writtenFiles = new ArrayList<>();

    // Build HierarchicalIdResolver from the active resource set if available,
    // so that each SemanticChangeEntry gets a hierarchicalId for merge engine use.
    HierarchicalIdResolver hidResolver = null;
    if (activeResources != null && !activeResources.isEmpty()) {
      var resourceSet = activeResources.iterator().next().getResourceSet();
      if (resourceSet != null) {
        hidResolver = HierarchicalIdResolver.create(resourceSet);
      }
    }

    // Write JSON changelog
    EChangeToEntryConverter converter = new EChangeToEntryConverter(
        uuidResolver, hidResolver, drainResult.deletionUuidOverrides());
    ChangelogDocument document = buildDocument(
        commitSha, branch, author, authorDate, message, parentShas, changesByResource, converter);

    Path jsonDir = repositoryRoot.resolve(".vitruvius").resolve("changelogs")
        .resolve(branch).resolve("json");
    Files.createDirectories(jsonDir);
    Path jsonFile = jsonDir.resolve(shortSha + ".json");

    Files.writeString(jsonFile, gson.toJson(document));

    writtenFiles.add(jsonFile);
    LOGGER.info("JSON changelog written: {} ({} file change(s), {} semantic change(s))",
        jsonFile.getFileName(), document.fileChanges.size(),
        document.summary.totalSemanticChanges);

    return writtenFiles;
  }

  /**
   * Reads the JSON changelog file for the given branch and short SHA.
   *
   * @param branch   the branch name used as the directory component.
   * @param shortSha the 7-character short SHA used as the file name prefix.
   * @return the parsed {@link ChangelogDocument}, or {@code null} if the file does not exist.
   * @throws IOException if the file exists but cannot be read or parsed.
   */
  public ChangelogDocument read(String branch, String shortSha) throws IOException {
    checkNotNull(branch, "branch must not be null");
    checkNotNull(shortSha, "shortSha must not be null");

    // Strictly canonical: only look in the branch's own directory.
    // The cross-branch fallback in resolveChangelogFile() is intentionally not used here
    // to preserve branch isolation (a changelog written on "main" must not be visible when
    // asking for "feature-x", even if both share the same short SHA).
    Path canonical = repositoryRoot.resolve(".vitruvius").resolve("changelogs")
        .resolve(branch).resolve("json").resolve(shortSha + ".json");
    if (Files.exists(canonical)) {
      String json = Files.readString(canonical);
      return gson.fromJson(json, ChangelogDocument.class);
    }
    // Fallback: read from git object store when the file is not in the current
    // working tree (e.g., querying a non-active branch's changelog while on master).
    String gitPath = ".vitruvius/changelogs/"
        + branch.replace('\\', '/') + "/json/" + shortSha + ".json";
    String json = readFromGitTree(branch, gitPath);
    if (json == null) return null;
    return gson.fromJson(json, ChangelogDocument.class);
  }

  /**
   * Returns the raw JSON string of the changelog file as written to disk,
   * bypassing deserialization and re-serialization. Returns {@code null} if
   * no changelog file exists for the given branch and short SHA.
   *
   * <p>If the file is not found under {@code changelogs/<branch>/json/<sha>.json},
   * all other branch directories are searched so that changelogs written on a
   * feature branch remain queryable after a fast-forward merge onto the target branch.
   */
  public String readRaw(String branch, String shortSha) throws IOException {
    checkNotNull(branch, "branch must not be null");
    checkNotNull(shortSha, "shortSha must not be null");

    Path file = resolveChangelogFile(branch, shortSha);
    if (file == null) {
      return null;
    }
    return Files.readString(file);
  }

  /**
   * Reads a file at the given path from the committed tree of {@code branch} using the
   * git object store. Returns {@code null} if the branch or the file cannot be found.
   */
  private String readFromGitTree(String branch, String filePath) {
    try (Git git = Git.open(repositoryRoot.toFile())) {
      org.eclipse.jgit.lib.Repository repo = git.getRepository();
      ObjectId branchHead = repo.resolve(branch);
      if (branchHead == null) return null;
      try (RevWalk revWalk = new RevWalk(repo)) {
        RevCommit commit = revWalk.parseCommit(branchHead);
        try (TreeWalk treeWalk = TreeWalk.forPath(repo, filePath, commit.getTree())) {
          if (treeWalk == null) return null;
          try (ObjectReader reader = repo.newObjectReader()) {
            byte[] bytes = reader.open(treeWalk.getObjectId(0)).getBytes();
            return new String(bytes, java.nio.charset.StandardCharsets.UTF_8);
          }
        }
      }
    } catch (IOException e) {
      return null;
    }
  }

  /**
   * Resolves the changelog JSON file for the given branch and short SHA.
   *
   * <p>Checks the canonical location first ({@code .vitruvius/changelogs/<branch>/json/<sha>.json}).
   * If the file is absent (e.g. after a fast-forward merge moves commits from one branch to
   * another without copying changelog files), falls back to scanning all branch sub-directories
   * under {@code .vitruvius/changelogs/} for a matching {@code <sha>.json} file.
   *
   * @return the resolved {@link Path} of the changelog file, or {@code null} if not found anywhere.
   */
  private Path resolveChangelogFile(String branch, String shortSha) throws IOException {
    String fileName = shortSha + ".json";
    Path changelogsRoot = repositoryRoot.resolve(".vitruvius").resolve("changelogs");

    // Fast path: canonical location
    Path canonical = changelogsRoot.resolve(branch).resolve("json").resolve(fileName);
    if (Files.exists(canonical)) {
      return canonical;
    }

    // Fallback: walk all json/ subdirectories under changelogs/ (handles merged commits and
    // multi-segment branch names like feature/sub/branch whose directories are nested).
    if (!Files.isDirectory(changelogsRoot)) {
      return null;
    }
    try (Stream<Path> allFiles = Files.walk(changelogsRoot)) {
      Optional<Path> found = allFiles
          .filter(p -> p.getFileName().toString().equals(fileName))
          .filter(p -> p.getParent() != null && p.getParent().getFileName() != null
              && p.getParent().getFileName().toString().equals("json"))
          .filter(Files::isRegularFile)
          .findFirst();
      if (found.isPresent()) {
        return found.get();
      }
    }
    return null;
  }

  private ChangelogDocument buildDocument(
      String commitSha, String branch, String author, LocalDateTime authorDate,
      String message, List<String> parentShas,
      Map<String, List<EChange<EObject>>> changesByResource,
      EChangeToEntryConverter converter) {
    ChangelogDocument doc = new ChangelogDocument();
    doc.formatVersion = FORMAT_VERSION;

    // Commit metadata
    doc.commit = new ChangelogDocument.CommitInfo();
    doc.commit.sha = commitSha;
    doc.commit.shortSha = commitSha.substring(0, Math.min(7, commitSha.length()));
    doc.commit.branch = branch;
    doc.commit.message = message;
    doc.commit.parentShas = parentShas != null ? parentShas : List.of();

    if (author != null) {
      doc.commit.author = new ChangelogDocument.PersonInfo();
      doc.commit.author.name = author;
      doc.commit.author.date = authorDate != null ? authorDate.format(DATE_FORMATTER) : null;
    }

    // File changes with semantic entries
    doc.fileChanges = new ArrayList<>();
    int totalSemantic = 0;
    Set<String> allUuids = new LinkedHashSet<>();  // ordered set to preserve insertion order for the summary

    // Build the Git diff map once upfront
    // used to classify each resource as ADDED/MODIFIED/DELETED/RENAMED
    Map<String, DiffEntry> gitDiff = buildGitDiffMap(commitSha);

    // One FileChangeInfo per changed resource
    for (Map.Entry<String, List<EChange<EObject>>> entry : changesByResource.entrySet()) {
      String resourceUri = entry.getKey();
      List<EChange<EObject>> eChanges = entry.getValue();

      // Translate raw EChange objects into human-readable SemanticChangeEntry records
      List<SemanticChangeEntry> entries = converter.convert(eChanges);
      totalSemantic += entries.size();

      // Collect every resolved UUID for the summary's affectedElementUuids list
      entries.stream()
          .filter(e -> e.getElementUuid() != null && !e.getElementUuid().equals("unknown"))
          .map(SemanticChangeEntry::getElementUuid)
          .forEach(allUuids::add);

      // Convert absolute EMF URI to a repo-relative path so it matches Git diff keys
      String relPath = toRelativePath(resourceUri);
      DiffEntry diffEntry = gitDiff.get(relPath);

      ChangelogDocument.FileChangeInfo fileInfo = new ChangelogDocument.FileChangeInfo();

      // detect file operation
      fileInfo.operation = detectOperation(relPath, eChanges, gitDiff).name();
      fileInfo.path = relPath;
      if (diffEntry != null && diffEntry.getChangeType() == DiffEntry.ChangeType.RENAME) {
        fileInfo.oldPath = diffEntry.getOldPath();
      }
      fileInfo.semanticChanges = entries;
      doc.fileChanges.add(fileInfo);
    }

    // Summary
    doc.summary = new ChangelogDocument.Summary();
    doc.summary.totalFileChanges = doc.fileChanges.size();
    doc.summary.totalSemanticChanges = totalSemantic;
    doc.summary.affectedElementUuids = new ArrayList<>(allUuids);

    return doc;
  }

  /**
   * Resolves the file-level operation for a resource. The Git diff map is consulted first
   * because it accurately reflects ADD, MODIFY, DELETE, and RENAME as recorded by Git.
   * The EChange heuristic is used only when no Git diff entry is available (e.g. for
   * in-memory-only resources that were never tracked by Git).
   *
   * @param relPath   repository-relative path of the resource (forward slashes).
   * @param eChanges  atomic EChanges for the resource, used as fallback.
   * @param gitDiff   map of new-path → {@link DiffEntry} built from the commit's parent diff.
   */
  private FileOperation detectOperation(String relPath, List<EChange<EObject>> eChanges,
      Map<String, DiffEntry> gitDiff) {
    DiffEntry diff = gitDiff.get(relPath);
    if (diff != null) {
      switch (diff.getChangeType()) {
        case ADD: return FileOperation.ADDED;
        case DELETE: return FileOperation.DELETED;
        case RENAME: return FileOperation.RENAMED;
        case MODIFY:
        default: return FileOperation.MODIFIED;
      }
    }
    // Fallback: infer from EChange types when no Git diff entry is present.
    boolean hasCreate = eChanges.stream().anyMatch(c -> c instanceof CreateEObject<?>);
    boolean hasDelete = eChanges.stream().anyMatch(c -> c instanceof DeleteEObject<?>);
    if (hasCreate && !hasDelete) {
      return FileOperation.ADDED;
    }
    if (hasDelete && !hasCreate) {
      return FileOperation.DELETED;
    }
    return FileOperation.MODIFIED;
  }

  /**
   * Builds a map of repository-relative new-path → {@link DiffEntry} by diffing the given
   * commit against its first parent. Returns an empty map for initial commits or on any error.
   */
  private Map<String, DiffEntry> buildGitDiffMap(String commitSha) {
    try (Git git = Git.open(repositoryRoot.toFile())) {
      var repo = git.getRepository();
      var commitId = repo.resolve(commitSha);
      if (commitId == null) {
        return Map.of(); // initial commit has no parent to diff against
      }
      try (RevWalk revWalk = new RevWalk(repo)) {
        RevCommit commit = revWalk.parseCommit(commitId);  // load commit object from object store
        if (commit.getParentCount() == 0) {
          return Map.of();
        }
        RevCommit parent = revWalk.parseCommit(commit.getParent(0).getId());
        try (ObjectReader reader = repo.newObjectReader()) {
          // CanonicalTreeParser reads a commit's file tree directly from the Git object store
          CanonicalTreeParser oldTree = new CanonicalTreeParser();
          oldTree.reset(reader, parent.getTree().getId());
          CanonicalTreeParser newTree = new CanonicalTreeParser();
          newTree.reset(reader, commit.getTree().getId());
          // Compute the diff
          // returns one DiffEntry per changed file
          List<DiffEntry> diffs = git.diff()
              .setOldTree(oldTree)
              .setNewTree(newTree)
              .call();
          // Index by new path so callers can look up a resource's DiffEntry by its current path
          Map<String, DiffEntry> result = new HashMap<>();
          for (DiffEntry entry : diffs) {
            result.put(entry.getNewPath(), entry);
          }
          return result;
        }
      }
    } catch (Exception e) {
      return Map.of();
    }
  }

  /**
   * Converts an absolute resource URI to a repository-relative path string.
   * Falls back to the URI string itself for non-file URIs.
   */
  private String toRelativePath(String resourceUri) {
    try {
      URI uri = URI.createURI(resourceUri);
      if (uri.isFile()) {
        Path absolute = Path.of(uri.toFileString());
        if (absolute.startsWith(repositoryRoot)) {
          return repositoryRoot.relativize(absolute).toString().replace('\\', '/');
        }
      }
    } catch (Exception e) {
      LOGGER.debug("Could not relativize URI '{}': {}", resourceUri, e.getMessage());
    }
    return resourceUri;
  }

  private Gson buildGson() {
    return new GsonBuilder()
        .setPrettyPrinting()
        .registerTypeAdapter(LocalDateTime.class,
            (JsonSerializer<LocalDateTime>) (src, type, ctx) ->
                new JsonPrimitive(src.format(DATE_FORMATTER)))
        .registerTypeAdapter(LocalDateTime.class,
            (JsonDeserializer<LocalDateTime>) (json, type, ctx) ->
                LocalDateTime.parse(json.getAsString(), DATE_FORMATTER))
        .create();
  }

  /**
   * Root JSON document written to
   * {@code .vitruvius/changelogs/<branch>/json/<shortSha>.json}.
   * Field names are intentionally kept in camelCase to match the agreed schema.
   */
  public static class ChangelogDocument {

    /** Format version tag for schema evolution. */
    public String formatVersion;

    /** Commit metadata. */
    public CommitInfo commit;

    /** Per-file semantic change records. */
    public List<FileChangeInfo> fileChanges;

    /** High-level statistics for the changelog entry. */
    public Summary summary;

    /**
     * Metadata about the Git commit associated with this changelog entry.
     */
    public static class CommitInfo {

      /** Full 40-character commit SHA. */
      public String sha;

      /** Abbreviated 7-character commit SHA. */
      public String shortSha;

      /** Branch the commit was made on. */
      public String branch;

      /** Author of the commit. */
      public PersonInfo author;

      /** Committer (may differ from author for rebased commits). */
      public PersonInfo committer;

      /** Commit message. */
      public String message;

      /** Parent commit SHAs (one for regular commits, two for merge commits). */
      public List<String> parentShas;
    }

    /**
     * Identifies a person (author or committer) in the Git commit metadata.
     */
    public static class PersonInfo {

      /** Display name of the person. */
      public String name;

      /** Email address of the person. */
      public String email;

      /** ISO-8601 timestamp of when the person's action was recorded. */
      public String date;
    }

    /**
     * Records the semantic changes that occurred in a single file within a commit.
     */
    public static class FileChangeInfo {

      /** File-level operation: ADDED, MODIFIED, DELETED, or RENAMED. */
      public String operation;

      /** Repository-relative path of the file. */
      public String path;

      /** Previous path, non-null only for rename operations. */
      public String oldPath;

      /** Ordered list of atomic semantic changes within this file. */
      public List<SemanticChangeEntry> semanticChanges;
    }

    /**
     * High-level statistics summarising the changelog entry.
     */
    public static class Summary {

      /** Total number of files that were changed in the commit. */
      public int totalFileChanges;

      /** Total number of atomic semantic changes across all files. */
      public int totalSemanticChanges;

      /** UUIDs of all model elements directly affected by the commit. */
      public List<String> affectedElementUuids;
    }
  }
}
