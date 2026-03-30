package tools.vitruv.framework.vsum.branch.handler;

import static com.google.common.base.Preconditions.checkNotNull;

import java.nio.file.Path;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.PersonIdent;
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
 * <p><b>Changelog writing</b>: Both commit paths produce the same JSON/XMI semantic changelog,
 * and in both cases the changelog ends up as staged but uncommitted changes:
 * <ul>
 *   <li><b>API path</b> ({@link tools.vitruv.framework.vsum.branch.CommitManager}): changelog
 *       is written synchronously right after the commit and staged via {@code git add}. The
 *       post-commit trigger file is also written, but the watcher's attempt to write the
 *       changelog is a no-op because the buffer has already been drained.</li>
 *   <li><b>Direct Git path</b> (this class): changelog is written asynchronously when the
 *       post-commit hook fires and the watcher detects the trigger file, then staged via
 *       {@code git add} as uncommitted changes.</li>
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

  private UuidResolver uuidResolver;

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
   * @param changeBuffer     buffer that accumulated EChanges since the last drain, must not
   *                         be null.
   * @param uuidResolver     resolver used to convert EObjects to stable UUIDs, must not be
   *                         null.
   * @param resourceSupplier supplier that returns the currently loaded EMF resources, must
   *                         not be null.
   */
  public void attachSemanticChangeTracking(SemanticChangeBuffer changeBuffer,
      UuidResolver uuidResolver, Supplier<Collection<Resource>> resourceSupplier) {
    this.changeBuffer = checkNotNull(changeBuffer, "changeBuffer must not be null");
    this.uuidResolver = checkNotNull(uuidResolver, "uuidResolver must not be null");
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
    LOGGER.info("Post-commit detected for {} on branch '{}' (direct Git path)", shortSha, branch);

    if (changeBuffer != null && changeBuffer.hasChanges()) {
      writeSemanticChangelog(commitSha, branch, shortSha);
    } else if (changeBuffer != null) {
      LOGGER.debug("No semantic changes buffered for commit {} - skipping changelog", shortSha);
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

      Map<String, List<EChange<EObject>>> changesByResource = changeBuffer.drainChanges();
      Collection<Resource> activeResources = resourceSupplier.get();

      List<Path> writtenFiles = changelogManager.write(
          commitSha, branch, author.getName(), authorDate,
          revCommit.getFullMessage().trim(),
          parentShas, changesByResource, activeResources, uuidResolver);

      for (Path file : writtenFiles) {
        String relativePath = repositoryRoot.relativize(file).toString().replace('\\', '/');
        git.add().addFilepattern(relativePath).call();
        LOGGER.debug("Staged changelog file: {}", file.getFileName());
      }
      LOGGER.info("Semantic changelog written ({} file(s)) for commit {}",
          writtenFiles.size(), shortSha);

    } catch (Exception e) {
      LOGGER.warn("Failed to write semantic changelog for commit {} (non-critical): {}",
          shortSha, e.getMessage());
    }
  }
}
