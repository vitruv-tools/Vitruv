package tools.vitruv.framework.vsum.branch;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import tools.vitruv.change.atomic.EChange;
import tools.vitruv.change.atomic.uuid.UuidResolver;
import tools.vitruv.framework.vsum.branch.data.BranchMetadata;
import tools.vitruv.framework.vsum.branch.data.CommitOptions;
import tools.vitruv.framework.vsum.branch.data.CommitResult;
import tools.vitruv.framework.vsum.branch.data.CommitSummary;
import tools.vitruv.framework.vsum.branch.data.ValidationResult;
import tools.vitruv.framework.vsum.branch.exception.BranchOperationException;
import tools.vitruv.framework.vsum.branch.handler.PreCommitHandler;
import tools.vitruv.framework.vsum.branch.storage.SemanticChangeBuffer;
import tools.vitruv.framework.vsum.branch.storage.SemanticChangelogManager;
import tools.vitruv.framework.vsum.branch.util.MergeTriggerFile;
import tools.vitruv.framework.vsum.branch.util.PostCommitTriggerFile;

/**
 * Manages Git commit operations for Vitruvius model files.
 *
 * <p>Responsibilities:
 * <ul>
 *   <li>Auto-stage modified model files (.xmi, .model, .model2, .ecore, .uml, .genmodel)</li>
 *   <li>Stage branch metadata (lastModified update) as part of the same commit</li>
 *   <li>Create the Git commit via JGit using author info from Git config</li>
 *   <li>If semantic change tracking is attached: write the JSON/XMI changelog synchronously
 *       right after the commit and stage it via {@code git add} as uncommitted changes</li>
 *   <li>Write the post-commit trigger file so {@link tools.vitruv.framework.vsum.branch.handler.VsumPostCommitWatcher}
 *       can also react to the commit. If the changelog was already written above, the watcher's
 *       attempt is a no-op because the buffer has already been drained.</li>
 * </ul>
 */
public class CommitManager {

  private static final Logger LOGGER = LogManager.getLogger(CommitManager.class);

  private static final String METADATA_DIR = ".vitruvius/branches";

  /**
   * Model file extensions that are auto-staged on every commit.
   * Matches the filter used in the post-commit hook script.
   */
  private static final Set<String> MODEL_EXTENSIONS =
      Set.of(".xmi", ".ecore", ".uml", ".genmodel");

  private static final Pattern MODEL_PATTERN =
      Pattern.compile(".*\\.model\\d*$", Pattern.CASE_INSENSITIVE);

  private final Path repoRoot;
  private final PostCommitTriggerFile triggerFile;
  private final MergeTriggerFile mergeTriggerFile;
  private final SemanticChangelogManager changelogManager;

  /**
   * Optional: validates the VSUM before committing when set via
   * {@link #attachValidation}. Null in Git-only scenarios where no live VirtualModel
   * is present.
   */
  private PreCommitHandler preCommitHandler;

  /**
   * Optional: buffer, resolver, and resource supplier provided by
   * {@link BranchAwareVirtualModel} so that semantic changes can be written to the
   * changelog at commit time.
   * All three are null when CommitManager is used without a live VirtualModel
   * (Git-only scenarios).
   */
  private SemanticChangeBuffer changeBuffer;
  private Supplier<UuidResolver> uuidResolverSupplier;

  /**
   * Supplies the currently loaded EMF Resources at commit time so XMI snapshots can be
   * written. Called lazily inside {@link #writeSemanticChangelog} to capture the
   * post-commit resource state.
   */
  private Supplier<Collection<Resource>> resourceSupplier;

  /**
   * Creates a new CommitManager for the Git repository at the given path.
   *
   * @param repoRoot the root directory of the Git repository.
   *
   * @throws IllegalArgumentException if the path is not a valid Git repository.
   */
  public CommitManager(Path repoRoot) {
    this.repoRoot = checkNotNull(repoRoot, "repository root must not be null");
    checkArgument(Files.isDirectory(repoRoot.resolve(".git")),
        "No Git repository found at: %s", repoRoot);
    this.triggerFile = new PostCommitTriggerFile(repoRoot);
    this.mergeTriggerFile = new MergeTriggerFile(repoRoot);
    this.changelogManager = new SemanticChangelogManager(repoRoot);
  }

  /**
   * Attaches pre-commit VSUM validation so that {@link #commit} rejects commits when the
   * VirtualModel is in an inconsistent state, matching the behaviour of the Git CLI
   * pre-commit hook.
   *
   * @param handler the handler to use for validation, must not be null.
   */
  public void attachValidation(PreCommitHandler handler) {
    this.preCommitHandler = checkNotNull(handler, "PreCommitHandler must not be null");
    LOGGER.info("Pre-commit validation attached to CommitManager");
  }

  /**
   * Attaches semantic change tracking so that EChanges accumulated since the last commit
   * are written to JSON and XMI changelog files as part of each {@link #commit} call.
   *
   * @param changeBuffer     the buffer to drain at commit time, must not be null.
   * @param uuidResolverSupplier     the resolver used to convert EObjects to stable UUIDs for
   *                         JSON output, must not be null.
   * @param resourceSupplier supplier that returns the currently loaded EMF Resources at
   *                         commit time, used to write XMI delta snapshots. Must not be
   *                         null.
   */
  public void attachSemanticChangeTracking(
      SemanticChangeBuffer changeBuffer,
      Supplier<UuidResolver> uuidResolverSupplier,
      Supplier<Collection<Resource>> resourceSupplier) {
    this.changeBuffer = checkNotNull(changeBuffer, "changeBuffer must not be null");
    this.uuidResolverSupplier = checkNotNull(uuidResolverSupplier, "uuidResolverSupplier must not be null");
    this.resourceSupplier =
        checkNotNull(resourceSupplier, "resourceSupplier must not be null");
    LOGGER.info("Semantic change tracking attached to CommitManager");
  }

  /**
   * Returns a summary of all commits reachable from the tip of the given branch, newest first.
   *
   * <p>For each commit, the semantic changelog JSON is checked. If a changelog exists for the
   * commit, {@link CommitSummary#hasChangelog()} is {@code true} and
   * {@link CommitSummary#totalSemanticChanges()} reflects the stored count.
   *
   * @param branch the branch name to walk.
   * @return ordered list of commit summaries, newest first.
   * @throws BranchOperationException if the branch does not exist or the repository cannot
   *     be read.
   */
  public List<CommitSummary> listCommits(String branch) throws BranchOperationException {
    checkNotNull(branch, "branch must not be null");
    try (Git git = Git.open(repoRoot.toFile())) {
      Repository repo = git.getRepository();
      ObjectId branchId = repo.resolve(branch);
      if (branchId == null) {
        throw new BranchOperationException("Branch not found: " + branch);
      }
      List<CommitSummary> result = new ArrayList<>();
      for (RevCommit commit : git.log().add(branchId).call()) {
        String sha = commit.getName();
        String shortSha = sha.substring(0, 7);
        PersonIdent author = commit.getAuthorIdent();
        String authorDate = LocalDateTime
            .ofInstant(Instant.ofEpochMilli(author.getWhen().getTime()), ZoneId.systemDefault())
            .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        List<String> parentShas = Arrays.stream(commit.getParents())
            .map(RevCommit::getName)
            .toList();

        boolean hasChangelog = false;
        int totalSemanticChanges = 0;
        try {
          SemanticChangelogManager.ChangelogDocument doc = changelogManager.read(branch, shortSha);
          if (doc != null) {
            hasChangelog = true;
            totalSemanticChanges = doc.summary.totalSemanticChanges;
          }
        } catch (IOException e) {
          LOGGER.debug("Could not read changelog for commit {}: {}", shortSha, e.getMessage());
        }

        result.add(new CommitSummary(
            sha, shortSha, branch,
            author.getName(), author.getEmailAddress(),
            authorDate, commit.getFullMessage().trim(),
            parentShas, hasChangelog, totalSemanticChanges));
      }
      return result;
    } catch (GitAPIException e) {
      throw new BranchOperationException("Failed to list commits: " + e.getMessage(), e);
    } catch (IOException e) {
      throw new BranchOperationException("Failed to open repository: " + e.getMessage(), e);
    }
  }

  /**
   * Reads the semantic changelog for the given branch and short SHA.
   *
   * @param branch   the branch name.
   * @param shortSha the 7-character short SHA of the commit.
   * @return the parsed {@link SemanticChangelogManager.ChangelogDocument}, or {@code null}
   *     if no changelog file exists for this commit.
   * @throws BranchOperationException if the file exists but cannot be read.
   */
  public SemanticChangelogManager.ChangelogDocument readChangelog(String branch, String shortSha)
      throws BranchOperationException {
    checkNotNull(branch, "branch must not be null");
    checkNotNull(shortSha, "shortSha must not be null");
    try {
      return changelogManager.read(branch, shortSha);
    } catch (IOException e) {
      throw new BranchOperationException(
          "Failed to read changelog for " + shortSha + ": " + e.getMessage(), e);
    }
  }

  /**
   * Returns the raw JSON string of the changelog file exactly as written to disk.
   * Returns {@code null} if no changelog exists for the given branch and short SHA.
   */
  public String readChangelogRaw(String branch, String shortSha)
      throws BranchOperationException {
    checkNotNull(branch, "branch must not be null");
    checkNotNull(shortSha, "shortSha must not be null");
    try {
      return changelogManager.readRaw(branch, shortSha);
    } catch (IOException e) {
      throw new BranchOperationException(
          "Failed to read changelog for " + shortSha + ": " + e.getMessage(), e);
    }
  }

  /**
   * Commits all modified model files with the given message.
   * Branch metadata (lastModified) is also staged automatically.
   *
   * @param message the commit message, must not be null or blank.
   * @return the {@link CommitResult} containing the commit SHA and staged files.
   *
   * @throws BranchOperationException if staging or committing fails.
   */
  public CommitResult commit(String message) throws BranchOperationException {
    return commit(message, CommitOptions.builder().build());
  }

  /**
   * Commits all modified model files with the given message and additional options.
   * Additional files can be staged and specific model files can be excluded via
   * {@link CommitOptions}.
   *
   * @param message the commit message, must not be null or blank.
   * @param options staging options for additional or excluded files.
   * @return the {@link CommitResult} containing the commit SHA and staged files.
   *
   * @throws BranchOperationException if staging or committing fails.
   */
  public CommitResult commit(String message, CommitOptions options)
      throws BranchOperationException {
    checkNotNull(message, "commit message must not be null");
    checkArgument(!message.isBlank(), "commit message must not be blank");
    checkNotNull(options, "commit options must not be null");

    if (preCommitHandler != null) {
      ValidationResult validation = preCommitHandler.validate();
      if (!validation.isValid()) {
        throw new BranchOperationException(
            "Pre-commit validation failed: " + validation.getErrors());
      }
    }

    try (Git git = Git.open(repoRoot.toFile())) {
      Repository repo = git.getRepository();
      String branch = repo.getBranch();
      LOGGER.info("Committing on branch '{}': {}", branch, message);

      // stage model files
      List<String> stagedFiles = stageModelFiles(git, options);
      stageBranchMetadata(git, branch, stagedFiles);
      stageVsumState(git, branch, stagedFiles);

      Status status = git.status().call();
      if (status.getAdded().isEmpty()
          && status.getChanged().isEmpty()
          && status.getRemoved().isEmpty()) {
        throw new BranchOperationException(
            "Nothing to commit, no modified model files found");
      }

      // perform commit: skip pre-commit and commit-msg hooks (REST path manages everything).
      git.commit().setMessage(message).setNoVerify(true).call();

      // Re-read HEAD: post-commit hook is NOT skipped by --no-verify and may amend the commit
      // (to include VSUM state), changing the SHA. JGit waits for the hook before returning,
      // so HEAD is already the final post-amend SHA by this point.
      ObjectId headId = repo.resolve("HEAD");
      RevCommit revCommit;
      try (org.eclipse.jgit.revwalk.RevWalk walk = new org.eclipse.jgit.revwalk.RevWalk(repo)) {
        revCommit = walk.parseCommit(headId);
      }
      String commitSha = revCommit.getName();
      PersonIdent author = revCommit.getAuthorIdent();
      LocalDateTime authorDate = LocalDateTime.ofInstant(
          Instant.ofEpochMilli(author.getWhen().getTime()), ZoneId.systemDefault());
      LOGGER.info("Commit created: {} on branch '{}'", commitSha.substring(0, 7), branch);

      boolean hasModelChanges = stagedFiles.stream().anyMatch(this::isModelFile);
      CommitResult result = new CommitResult(
          commitSha, branch, author.getName(), author.getEmailAddress(),
          authorDate, stagedFiles, hasModelChanges);

      // Write semantic changelog if change tracking is attached and model files changed.
      if (hasModelChanges && changeBuffer != null && changeBuffer.hasChanges()) {
        writeSemanticChangelog(git, commitSha, branch, author.getName(), authorDate, revCommit);
      }

      // Write post-commit trigger only in CLI mode (no changeBuffer attached).
      // In REST mode the changelog is written inline above; the watcher does not run.
      if (hasModelChanges && changeBuffer == null) {
        try {
          triggerFile.createTrigger(commitSha, branch);
        } catch (IOException e) {
          // changelog generation failing should not undo the commit
          LOGGER.warn("Failed to write post-commit trigger (non-critical): {}",
              e.getMessage());
        }
      }

      // Write merge trigger when the commit is a merge (two parents), matching
      // the post-commit hook behaviour for direct Git CLI merges.
      if (revCommit.getParentCount() >= 2) {
        writeMergeTriggerIfNeeded(git, revCommit, commitSha, branch);
      }

      return result;

    } catch (GitAPIException e) {
      throw new BranchOperationException("Failed to create commit: " + e.getMessage(), e);
    } catch (IOException e) {
      throw new BranchOperationException("Failed to open repository: " + e.getMessage(), e);
    }
  }

  /**
   * Scans the working directory for modified model files and stages them.
   * Files in {@link CommitOptions#getExcludeFiles()} are skipped.
   * Files in {@link CommitOptions#getAdditionalFiles()} are staged in addition.
   *
   * <p>Conflicting files (those with unresolved git conflict markers) are intentionally
   * included as staging candidates so that a caller who has manually resolved semantic
   * conflicts can commit the resolution via the normal commit path. The caller is
   * responsible for ensuring all conflict markers have been removed before invoking
   * this method.
   *
   * @param git     the open Git instance.
   * @param options staging options.
   * @return list of relative paths of all staged files.
   *
   * @throws GitAPIException if a JGit operation fails.
   * @throws IOException     if the repository cannot be read.
   */
  private List<String> stageModelFiles(Git git, CommitOptions options)
      throws GitAPIException, IOException {
    Status status = git.status().call();
    Set<String> candidates = new HashSet<>();
    candidates.addAll(status.getModified());
    candidates.addAll(status.getUntracked());
    candidates.addAll(status.getMissing());
    // Intentional: allows committing a manually resolved conflict (see Javadoc).
    candidates.addAll(status.getConflicting());
    List<String> staged = new ArrayList<>();
    // Resolve excluded paths to relative strings for comparison
    List<String> excluded = options.getExcludeFiles().stream()
            .map(p -> repoRoot.relativize(p).toString().replace('\\', '/'))
            .toList();

    for (String relativePath : candidates) {
      if (excluded.contains(relativePath)) {
        continue;
      }
      if (isModelFile(relativePath)) {
        git.add().addFilepattern(relativePath).call();
        staged.add(relativePath);
      }
    }

    // Stage additional files specified by caller
    for (Path additionalFile : options.getAdditionalFiles()) {
      String relativePath =
          repoRoot.relativize(additionalFile).toString().replace('\\', '/');
      if (!staged.contains(relativePath)) {
        git.add().addFilepattern(relativePath).call();
        staged.add(relativePath);
      }
    }

    LOGGER.info("Staged {} file(s) for commit", staged.size());
    return staged;
  }

  /**
   * Updates the branch metadata lastModified timestamp and stages the metadata file
   * so it is included in the same commit without requiring a separate manual commit.
   *
   * @param git    the open Git instance.
   * @param branch the current branch name.
   * @param staged the accumulating list of staged file paths.
   *
   * @throws IOException     if the metadata file cannot be read or written.
   * @throws GitAPIException if staging fails.
   */
  private void stageBranchMetadata(Git git, String branch, List<String> staged)
      throws IOException, GitAPIException {
    Path metadataDir = repoRoot.resolve(METADATA_DIR);

    // Update lastModified timestamp for the current branch's metadata only
    Path currentMetaFile = metadataDir.resolve(branch + ".metadata");
    if (Files.exists(currentMetaFile)) {
      try {
        BranchMetadata metadata = BranchMetadata.readFrom(currentMetaFile);
        metadata.updateLastModified();
        metadata.writeTo(currentMetaFile);
      } catch (Exception e) {
        LOGGER.warn("Failed to update branch metadata lastModified (non-critical): {}",
            e.getMessage());
      }
    } else {
      LOGGER.debug("No metadata file for branch '{}', skipping lastModified update", branch);
    }

    // Stage ALL metadata files so that files created by commitMetadataFile() (which
    // writes directly to the git ref without touching the index) stay tracked across
    // regular commits. Without this, the next commit would drop them from its tree.
    if (!Files.isDirectory(metadataDir)) {
      return;
    }
    try (var stream = Files.list(metadataDir)) {
      stream.filter(f -> f.getFileName().toString().endsWith(".metadata"))
          .forEach(f -> {
            String relativePath = repoRoot.relativize(f).toString().replace('\\', '/');
            if (!staged.contains(relativePath)) {
              try {
                git.add().addFilepattern(relativePath).call();
                staged.add(relativePath);
              } catch (GitAPIException e) {
                LOGGER.warn("Failed to stage branch metadata '{}' (non-critical): {}",
                    relativePath, e.getMessage());
              }
            }
          });
    } catch (IOException e) {
      LOGGER.warn("Failed to list branch metadata directory (non-critical): {}", e.getMessage());
    }
  }

  /**
   * Stages all files inside the V-SUM state folder for the current branch
   * ({@code .vitruvius/vsum/{branch}/}) so that correspondences, uuid.uuid,
   * and models.models are committed to git alongside model files.
   * Without this, V-SUM state is only on disk and is lost or incorrectly
   * inherited when switching branches.
   *
   * @param git    the open Git instance.
   * @param branch the current branch name.
   * @param staged the accumulating list of staged file paths.
   */
  private void stageVsumState(Git git, String branch, List<String> staged) {
    Path vsumDir = repoRoot.resolve(".vitruvius/vsum").resolve(branch);
    if (!Files.isDirectory(vsumDir)) {
      return;
    }
    try {
      git.add().addFilepattern(".vitruvius/vsum/" + branch + "/").call();
      LOGGER.info("Staged V-SUM state for branch '{}'", branch);
    } catch (GitAPIException e) {
      LOGGER.warn("Failed to stage V-SUM state directory (non-critical): {}", e.getMessage());
    }
  }

  /**
   * Drains the change buffer, writes the JSON changelog and XMI delta snapshots, and
   * stages all produced files so they are tracked by Git.
   * Failures are logged as warnings and do not roll back the commit.
   *
   * @param git        the open Git instance.
   * @param commitSha  the SHA of the commit just created.
   * @param branch     the branch on which the commit was made.
   * @param authorName the commit author's name.
   * @param authorDate the commit author date.
   * @param revCommit  the JGit commit object (used to read parent SHAs).
   */
  private void writeSemanticChangelog(
      Git git, String commitSha, String branch, String authorName,
      LocalDateTime authorDate, RevCommit revCommit) {
    try {
      SemanticChangeBuffer.DrainResult drainResult = changeBuffer.drainChanges();

      if (drainResult.changesByResource().isEmpty()) {
        return;
      }

      // Collect parent SHAs for three-way merge support
      List<String> parentShas = new ArrayList<>();
      for (RevCommit parent : revCommit.getParents()) {
        parentShas.add(parent.getName());
      }

      // Resolve active resources for XMI snapshot writing
      Collection<Resource> activeResources =
          resourceSupplier != null ? resourceSupplier.get() : Collections.emptyList();

      List<Path> writtenFiles = changelogManager.write(
          commitSha, branch, authorName, authorDate,
          revCommit.getFullMessage().trim(),
          parentShas, drainResult, activeResources, uuidResolverSupplier.get());

      // Stage all written changelog files (JSON + XMI) so they are tracked by Git
      for (Path file : writtenFiles) {
        String relativePath = repoRoot.relativize(file).toString().replace('\\', '/');
        git.add().addFilepattern(relativePath).call();
      }
      LOGGER.info("Staged {} changelog file(s) for commit {}",
          writtenFiles.size(), commitSha.substring(0, 7));

      // Auto-commit changelog files so they are bound to this branch and commit.
      // Without this commit they remain as staged-but-uncommitted changes, which
      // can bleed onto other branches when the developer switches without committing.
      git.commit()
          .setMessage("[vitruvius] Semantic changelog for " + commitSha.substring(0, 7)
              + "\n\nVitruvius-System: true")
          .setNoVerify(true)
          .call();
      LOGGER.info("Changelog auto-committed for {}", commitSha.substring(0, 7));

    } catch (Exception e) {
      LOGGER.warn("Failed to write semantic changelog for commit {} (non-critical): {}",
          commitSha.substring(0, 7), e.getMessage());
    }
  }

  /**
   * Returns true if the given relative file path has a model file extension.
   *
   * @param relativePath the relative file path to check.
   * @return {@code true} if the path ends with a known model file extension.
   */
  private boolean isModelFile(String relativePath) {
    String lower = relativePath.toLowerCase();
    if (MODEL_EXTENSIONS.stream().anyMatch(lower::endsWith)) {
      return true;
    }
    return MODEL_PATTERN.matcher(lower).matches();
  }

  private void writeMergeTriggerIfNeeded(
      Git git, RevCommit revCommit, String commitSha, String targetBranch) {
    try {
      String secondParentSha = revCommit.getParent(1).getName();
      String sourceBranch = resolveBranchName(git, secondParentSha);
      mergeTriggerFile.createTrigger(commitSha, sourceBranch, targetBranch);
      LOGGER.info("Merge trigger written for commit {} ({} -> {})",
          commitSha.substring(0, 7), sourceBranch, targetBranch);
    } catch (IOException e) {
      LOGGER.warn("Failed to write merge trigger (non-critical): {}", e.getMessage());
    }
  }

  /**
   * Finds the local branch whose tip matches the given SHA.
   * Falls back to {@code "unknown-branch"} if no match is found or on error,
   * consistent with the post-commit hook's {@code git name-rev} fallback.
   */
  private String resolveBranchName(Git git, String sha) {
    try {
      for (org.eclipse.jgit.lib.Ref ref : git.branchList().call()) {
        if (ref.getObjectId() != null && ref.getObjectId().getName().equals(sha)) {
          return org.eclipse.jgit.lib.Repository.shortenRefName(ref.getName());
        }
      }
    } catch (Exception e) {
      LOGGER.debug("Could not resolve branch name for SHA {}: {}",
          sha.substring(0, 7), e.getMessage());
    }
    return "unknown-branch";
  }
}
