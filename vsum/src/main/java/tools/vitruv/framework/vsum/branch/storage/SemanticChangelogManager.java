package tools.vitruv.framework.vsum.branch.storage;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import tools.vitruv.change.atomic.EChange;
import tools.vitruv.change.atomic.eobject.CreateEObject;
import tools.vitruv.change.atomic.eobject.DeleteEObject;
import tools.vitruv.change.atomic.uuid.UuidResolver;
import tools.vitruv.change.changederivation.persistence.DeltaPersistence;
import tools.vitruv.framework.vsum.branch.data.FileOperation;

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
 *
 * <p><b>XMI format</b> - machine-readable, HierarchicalId-keyed, suitable for replay and
 * three-way merge.
 * One XMI file is written per changed resource using
 * {@link DeltaPersistence#saveResourceAsChanges} (state-based snapshot of the current
 * model state expressed as creation EChanges).
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
   * Writes the JSON changelog and XMI delta snapshots for the given commit.
   *
   * <p>JSON is written to {@code .vitruvius/changelogs/<branch>/json/<shortSha>.json}.
   * XMI snapshots are written to {@code .vitruvius/changelogs/<branch>/xmi/<shortSha>.xmi}
   * for each resource that had changes.
   *
   * @param commitSha full 40-character commit SHA.
   * @param branch branch name.
   * @param author author name.
   * @param authorDate date the changes were authored.
   * @param message commit message.
   * @param parentShas parent commit SHAs (one for normal commits, two for merge commits).
   * @param changesByResource map of resource URI -> ordered atomic EChanges, from
   *     {@link SemanticChangeBuffer#drainChanges()}.
   * @param activeResources all currently loaded EMF Resources; used to locate the resource
   *     objects for XMI snapshot writing. May be null or empty to skip XMI.
   * @param uuidResolver resolver used to convert EObjects to stable UUIDs for JSON output.
   * @return list of paths of all written files (JSON + any XMI files) for Git staging.
   * @throws IOException if the JSON file cannot be written (XMI failures are non-fatal).
   */
  public List<Path> write(
      String commitSha, String branch, String author, LocalDateTime authorDate,
      String message, List<String> parentShas,
      Map<String, List<EChange<EObject>>> changesByResource,
      Collection<Resource> activeResources, UuidResolver uuidResolver) throws IOException {

    checkNotNull(commitSha, "commitSha must not be null");
    checkNotNull(branch, "branch must not be null");
    checkNotNull(changesByResource, "changesByResource must not be null");
    checkNotNull(uuidResolver, "uuidResolver must not be null");

    String shortSha = commitSha.substring(0, Math.min(7, commitSha.length()));
    List<Path> writtenFiles = new ArrayList<>();

    // Write JSON changelog
    EChangeToEntryConverter converter = new EChangeToEntryConverter(uuidResolver);
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

    // Write XMI delta snapshots for each changed resource (non-fatal on failure)
    if (activeResources != null && !activeResources.isEmpty()) {
      Path xmiDir = repositoryRoot.resolve(".vitruvius").resolve("changelogs")
          .resolve(branch).resolve("xmi");
      Files.createDirectories(xmiDir);

      for (String resourceUri : changesByResource.keySet()) {
        Resource resource = findResource(activeResources, resourceUri);
        if (resource == null) {
          LOGGER.debug("No loaded resource found for URI '{}', skipping XMI snapshot",
              resourceUri);
          continue;
        }
        String resourceName = deriveResourceName(resourceUri);
        Path xmiFile = xmiDir.resolve(shortSha + "-" + resourceName + ".xmi");
        try {
          DeltaPersistence.saveResourceAsChanges(resource, xmiFile);
          writtenFiles.add(xmiFile);
          LOGGER.debug("XMI delta snapshot written: {}", xmiFile.getFileName());
        } catch (Exception e) {
          LOGGER.warn("Failed to write XMI snapshot for '{}' (non-critical): {}",
              resourceName, e.getMessage());
        }
      }
    }
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

    Path file = repositoryRoot.resolve(".vitruvius").resolve("changelogs")
        .resolve(branch).resolve("json").resolve(shortSha + ".json");
    if (!Files.exists(file)) {
      return null;
    }
    String json = Files.readString(file);
    return gson.fromJson(json, ChangelogDocument.class);
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
    Set<String> allUuids = new LinkedHashSet<>();

    Map<String, DiffEntry> gitDiff = buildGitDiffMap(commitSha);

    for (Map.Entry<String, List<EChange<EObject>>> entry : changesByResource.entrySet()) {
      String resourceUri = entry.getKey();
      List<EChange<EObject>> eChanges = entry.getValue();

      List<SemanticChangeEntry> entries = converter.convert(eChanges);
      totalSemantic += entries.size();

      entries.stream()
          .filter(e -> e.getElementUuid() != null && !e.getElementUuid().equals("unknown"))
          .map(SemanticChangeEntry::getElementUuid)
          .forEach(allUuids::add);

      String relPath = toRelativePath(resourceUri);
      DiffEntry diffEntry = gitDiff.get(relPath);

      ChangelogDocument.FileChangeInfo fileInfo = new ChangelogDocument.FileChangeInfo();
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
   * Finds the Resource whose URI string matches the given resourceUri from the active resources.
   */
  private Resource findResource(Collection<Resource> resources, String resourceUri) {
    for (Resource r : resources) {
      if (r.getURI() != null && resourceUri.equals(r.getURI().toString())) {
        return r;
      }
    }
    return null;
  }

  /**
   * Derives a short, file-system-safe name from a resource URI for use in XMI file names.
   */
  private String deriveResourceName(String resourceUri) {
    try {
      URI uri = URI.createURI(resourceUri);
      String lastSegment = uri.lastSegment();
      if (lastSegment != null && !lastSegment.isBlank()) {
        // Strip extension
        int dot = lastSegment.lastIndexOf('.');
        return dot > 0 ? lastSegment.substring(0, dot) : lastSegment;
      }
    } catch (Exception e) {
      LOGGER.debug("Could not derive resource name from URI '{}': {}", resourceUri, e.getMessage());
    }
    // Fallback: sanitize the URI string
    return resourceUri.replaceAll("[^a-zA-Z0-9_-]", "_");
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
        return Map.of();
      }
      try (RevWalk revWalk = new RevWalk(repo)) {
        RevCommit commit = revWalk.parseCommit(commitId);
        if (commit.getParentCount() == 0) {
          return Map.of();
        }
        RevCommit parent = revWalk.parseCommit(commit.getParent(0).getId());
        try (ObjectReader reader = repo.newObjectReader()) {
          CanonicalTreeParser oldTree = new CanonicalTreeParser();
          oldTree.reset(reader, parent.getTree().getId());
          CanonicalTreeParser newTree = new CanonicalTreeParser();
          newTree.reset(reader, commit.getTree().getId());

          List<DiffEntry> diffs = git.diff()
              .setOldTree(oldTree)
              .setNewTree(newTree)
              .call();

          Map<String, DiffEntry> result = new HashMap<>();
          for (DiffEntry entry : diffs) {
            result.put(entry.getNewPath(), entry);
          }
          return result;
        }
      }
    } catch (Exception e) {
      LOGGER.debug("Could not build Git diff map for commit '{}': {}", commitSha, e.getMessage());
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
