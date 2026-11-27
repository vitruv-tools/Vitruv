package tools.vitruv.framework.vsum.branch.handler;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheBuilder;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.lib.CommitBuilder;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import tools.vitruv.change.atomic.EChange;
import tools.vitruv.change.atomic.uuid.UuidResolver;
import tools.vitruv.framework.vsum.branch.storage.SemanticChangeBuffer;
import tools.vitruv.framework.vsum.branch.storage.SemanticChangelogManager;

/**
 * Handles post-commit events for commits made directly via Git (not through the API).
 *
 * <p>When a developer commits directly from their IDE or Git CLI, the Git {@code post-commit}
 * hook writes a trigger file that {@link VsumPostCommitWatcher} detects and forwards here.
 *
 * <p><b>Changelog writing</b>: Changelogs are written and immediately committed as a
 * follow-up commit on the same branch. This ensures they are in the Git object store and
 * readable cross-branch via {@code TreeWalk} (used by {@code SemanticConflictDetector}).
 * <ul>
 *   <li><b>API path</b> ({@link tools.vitruv.framework.vsum.branch.CommitManager}): changelog
 *       is written synchronously right after the commit and staged via {@code git add}. The
 *       post-commit trigger file is also written, but the watcher's attempt to write the
 *       changelog is a no-op because the buffer has already been drained.</li>
 *   <li><b>Direct Git path</b> (this class): changelog is written asynchronously when the
 *       post-commit hook fires and the watcher detects the trigger file, then staged and
 *       committed as {@code "[vitruvius] Semantic changelog for {sha}"}.</li>
 * </ul>
 *
 * <p>Semantic tracking (buffer, UUID resolver, resource supplier) is optional. If not attached
 * via {@link #attachSemanticChangeTracking}, the handler logs the event only. This allows the
 * class to be used in Git-only scenarios without a live VSUM.
 */
public class PostCommitHandler {

  private static final Logger LOGGER = LogManager.getLogger(PostCommitHandler.class);

  private final Path repositoryRoot;

  private final SemanticChangelogManager changelogManager;

  /** Nullable - only set when {@link #attachSemanticChangeTracking} is called. */
  private SemanticChangeBuffer changeBuffer;

  // Supplier so the resolver is fetched fresh on each trigger; after a VSUM reload the
  // InternalVirtualModel returns a new UuidResolver instance and the old reference is stale.
  private Supplier<UuidResolver> uuidResolverSupplier;

  private Supplier<Collection<Resource>> resourceSupplier;

  /**
   * Creates a post-commit handler for the repository at the given root.
   *
   * @param repositoryRoot the root directory of the Git repository, must not be null.
   */
  public PostCommitHandler(Path repositoryRoot) {
    this.repositoryRoot = checkNotNull(repositoryRoot, "repository root must not be null");
    this.changelogManager = new SemanticChangelogManager(repositoryRoot);
  }

  /**
   * Attaches semantic change tracking so that JSON and XMI changelogs are written
   * when a post-commit trigger is detected (direct Git path).
   *
   * <p>Must be called before the first commit is made to ensure no changes are missed.
   *
   * @param changeBuffer         buffer that accumulated EChanges since the last drain, must not
   *                             be null.
   * @param uuidResolverSupplier supplier for the resolver used to convert EObjects to stable
   *                             UUIDs; called fresh on each trigger so that post-reload
   *                             resolver instances are always used, must not be null.
   * @param resourceSupplier     supplier that returns the currently loaded EMF resources, must
   *                             not be null.
   */
  public void attachSemanticChangeTracking(SemanticChangeBuffer changeBuffer,
      Supplier<UuidResolver> uuidResolverSupplier, Supplier<Collection<Resource>> resourceSupplier) {
    this.changeBuffer = checkNotNull(changeBuffer, "changeBuffer must not be null");
    this.uuidResolverSupplier = checkNotNull(uuidResolverSupplier, "uuidResolverSupplier must not be null");
    this.resourceSupplier = checkNotNull(resourceSupplier, "resourceSupplier must not be null");
    LOGGER.info("Semantic change tracking attached to PostCommitHandler");
  }

  /**
   * Called by {@link VsumPostCommitWatcher} when a post-commit trigger is detected.
   * Writes the JSON and XMI semantic changelog if tracking is attached and the buffer
   * contains changes. Failures are non-fatal and logged as warnings.
   *
   * @param commitSha the full 40-character SHA of the new commit, must not be null.
   * @param branch    the branch on which the commit was made, must not be null.
   */
  public void handlePostCommit(String commitSha, String branch) {
    checkNotNull(commitSha, "commitSha must not be null");
    checkNotNull(branch, "branch must not be null");
    String shortSha = commitSha.substring(0, Math.min(7, commitSha.length()));

    // Skip system commits to avoid cascade trigger loops: the watcher's own git.commit()
    // fires the post-commit hook again, as does every VsumMergeWatcher VSUM-state commit.
    // These are not user commits and carry no model changes.
    if (isSystemCommit(commitSha, shortSha)) {
      return;
    }
    LOGGER.info("Post-commit detected for {} on branch '{}' (direct Git path)", shortSha, branch);

    if (changeBuffer != null && changeBuffer.hasChanges()) {
      writeSemanticChangelog(commitSha, branch, shortSha);
    }
  }

  private boolean isSystemCommit(String commitSha, String shortSha) {
    try (Git git = Git.open(repositoryRoot.toFile())) {
      ObjectId oid = git.getRepository().resolve(commitSha);
      if (oid == null) {
        return false;
      }
      RevCommit revCommit;
      try (RevWalk walk = new RevWalk(git.getRepository())) {
        revCommit = walk.parseCommit(oid);
      }
      boolean system = revCommit.getFullMessage().contains("\nVitruvius-System: true");
      if (system) {
        LOGGER.debug("Skipping system commit {} - no changelog written", shortSha);
      }
      return system;
    } catch (Exception e) {
      LOGGER.debug("Could not read commit message for {} - treating as user commit", shortSha);
      return false;
    }
  }

  private void writeSemanticChangelog(String commitSha, String branch, String shortSha) {
    try (Git git = Git.open(repositoryRoot.toFile())) {
      ObjectId oid = git.getRepository().resolve(commitSha);
      if (oid == null) {
        LOGGER.warn("Cannot resolve commit SHA {} - skipping semantic changelog", shortSha);
        return;
      }

      RevCommit revCommit;
      try (RevWalk walk = new RevWalk(git.getRepository())) {
        revCommit = walk.parseCommit(oid);
      }

      PersonIdent author = revCommit.getAuthorIdent();
      LocalDateTime authorDate = LocalDateTime.ofInstant(
          Instant.ofEpochMilli(author.getWhen().getTime()), ZoneId.systemDefault());

      List<String> parentShas = new ArrayList<>();
      for (RevCommit parent : revCommit.getParents()) {
        parentShas.add(parent.getName());
      }

      SemanticChangeBuffer.DrainResult drainResult = changeBuffer.drainChanges();
      Collection<Resource> activeResources = resourceSupplier.get();

      List<Path> writtenFiles = changelogManager.write(
          commitSha, branch, author.getName(), authorDate,
          revCommit.getFullMessage().trim(),
          parentShas, drainResult, activeResources, uuidResolverSupplier.get());

      // Commit directly to the target branch ref using low-level JGit object insertion.
      // This bypasses CommitCommand so no post-commit hook runs, and the commit always
      // lands on the correct branch regardless of the current HEAD.
      commitFilesToBranch(git, branch, writtenFiles,
          "[vitruvius] Semantic changelog for " + shortSha
          + "\n\nVitruvius-System: true", author);

      LOGGER.info("Semantic changelog committed ({} file(s)) for commit {}",
          writtenFiles.size(), shortSha);

    } catch (Exception e) {
      LOGGER.warn("Failed to write semantic changelog for commit {} (non-critical): {}",
          shortSha, e.getMessage());
    }
  }

  /**
   * Commits {@code files} directly onto the tip of {@code branch} using JGit's low-level
   * object-insertion API. This approach never triggers hooks and always targets the correct
   * branch ref regardless of which branch is currently checked out.
   */
  private void commitFilesToBranch(Git git, String branch, List<Path> files,
      String message, PersonIdent author) throws IOException {
    org.eclipse.jgit.lib.Repository repo = git.getRepository();
    String refName = Constants.R_HEADS + branch;
    ObjectId branchHeadId = repo.resolve(refName);

    try (ObjectInserter inserter = repo.newObjectInserter();
         ObjectReader reader = repo.newObjectReader()) {

      DirCache dirCache = DirCache.newInCore();
      DirCacheBuilder builder = dirCache.builder();

      // Seed the in-memory index from the branch's current committed tree.
      if (branchHeadId != null) {
        try (RevWalk rw = new RevWalk(reader)) {
          RevCommit branchHead = rw.parseCommit(branchHeadId);
          builder.addTree(new byte[0], DirCacheEntry.STAGE_0, reader, branchHead.getTree());
        }
      }

      // Insert each changelog file as a blob and add it to the index.
      // Changelog paths are unique per commit SHA, so no collision with existing entries.
      // The file is deleted from the working tree after insertion: the content is now in the
      // git object store and SemanticConflictDetector reads via TreeWalk, not from disk.
      // Deleting the working-tree copy prevents CheckoutConflictException when switching branches.
      for (Path file : files) {
        byte[] content = Files.readAllBytes(file);
        ObjectId blobId = inserter.insert(Constants.OBJ_BLOB, content);
        String relativePath = repositoryRoot.relativize(file).toString().replace('\\', '/');
        DirCacheEntry dce = new DirCacheEntry(relativePath);
        dce.setFileMode(FileMode.REGULAR_FILE);
        dce.setObjectId(blobId);
        builder.add(dce);
        Files.deleteIfExists(file);
        LOGGER.debug("Inserted changelog blob and removed from working tree: {}", file.getFileName());
      }

      builder.finish();
      ObjectId treeId = dirCache.writeTree(inserter);

      CommitBuilder cb = new CommitBuilder();
      cb.setTreeId(treeId);
      if (branchHeadId != null) {
        cb.setParentId(branchHeadId);
      }
      cb.setAuthor(author);
      cb.setCommitter(author);
      cb.setMessage(message + "\n");

      ObjectId newCommitId = inserter.insert(cb);
      inserter.flush();

      RefUpdate ru = repo.updateRef(refName);
      ru.setNewObjectId(newCommitId);
      ru.setExpectedOldObjectId(branchHeadId != null ? branchHeadId : ObjectId.zeroId());
      ru.setRefLogMessage(message, false);
      RefUpdate.Result result = ru.update();

      if (result != RefUpdate.Result.NEW && result != RefUpdate.Result.FAST_FORWARD) {
        throw new IOException("Branch ref update for '" + branch + "' failed: " + result);
      }
    }
  }
}
