package tools.vitruv.framework.vsum.branch;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.MergeResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.merge.MergeStrategy;
import tools.vitruv.framework.vsum.branch.data.BranchMetadata;
import tools.vitruv.framework.vsum.branch.data.BranchState;
import tools.vitruv.framework.vsum.branch.data.ModelMergeResult;
import tools.vitruv.framework.vsum.branch.data.ValidationResult;
import tools.vitruv.framework.vsum.branch.exception.BranchOperationException;
import tools.vitruv.framework.vsum.branch.util.MergeResultFile;
import tools.vitruv.framework.vsum.branch.util.MergeTriggerFile;

/**
 * Manages Git merge operations for Vitruvius model branches.
 *
 * <p>Responsibilities:
 * <ul>
 *   <li>Perform three-way merge of a source branch into the current branch (MG-5)</li>
 *   <li>Detect fast-forward vs non-fast-forward merges</li>
 *   <li>Return a {@link ModelMergeResult} describing the outcome including conflicts</li>
 *   <li>Mark the source branch as MERGED on success (BR-5, BR-9)</li>
 *   <li>Optionally delete the source branch after successful merge (BR-5)</li>
 *   <li>Write the merge trigger file so VsumMergeWatcher validates the merged state</li>
 * </ul>
 */
public class MergeManager {

  private static final Logger LOGGER = LogManager.getLogger(MergeManager.class);
  private static final String METADATA_DIR = ".vitruvius/branches";

  private final Path repoRoot;
  private final MergeTriggerFile mergeTriggerFile;

  /**
   * Creates a new MergeManager for the Git repository at the given path.
   *
   * @param repoRoot the root directory of the Git repository.
   *
   * @throws IllegalArgumentException if the path is not a valid Git repository.
   */
  public MergeManager(Path repoRoot) {
    this.repoRoot = checkNotNull(repoRoot, "repository root must not be null");
    checkArgument(Files.isDirectory(repoRoot.resolve(".git")),
        "No Git repository found at: %s", repoRoot);
    this.mergeTriggerFile = new MergeTriggerFile(repoRoot);
  }

  /**
   * Merges the given source branch into the current branch using a three-way merge
   * (MG-5).
   *
   * <p>On success: marks source branch as MERGED, writes merge trigger for validation.
   *
   * <p>On fast-forward: same as success but no merge commit is created.
   *
   * <p>On conflict: returns {@link ModelMergeResult.MergeStatus#CONFLICTING} with the
   * list of conflicting files. The developer must resolve manually and commit.
   *
   * @param sourceBranch the name of the branch to merge into the current branch.
   * @return a {@link ModelMergeResult} describing the outcome.
   *
   * @throws BranchOperationException if the source branch does not exist or the
   *     repository cannot be opened.
   */
  public ModelMergeResult merge(String sourceBranch) throws BranchOperationException {
    return merge(sourceBranch, false);
  }

  /**
   * Merges the given source branch into the current branch, with an option to
   * automatically delete the source branch after a successful merge (BR-5).
   *
   * @param sourceBranch     the name of the branch to merge into the current branch.
   * @param deleteAfterMerge whether to delete the source branch after success.
   * @return a {@link ModelMergeResult} describing the outcome.
   *
   * @throws BranchOperationException if the source branch does not exist or the
   *     repository cannot be opened.
   */
  public ModelMergeResult merge(String sourceBranch, boolean deleteAfterMerge)
      throws BranchOperationException {
    checkNotNull(sourceBranch, "source branch must not be null");
    checkArgument(!sourceBranch.isBlank(), "source branch must not be blank");

    try (Git git = Git.open(repoRoot.toFile())) {
      Repository repo = git.getRepository();
      String targetBranch = repo.getBranch();
      LOGGER.info("Merging '{}' into '{}'", sourceBranch, targetBranch);

      Ref sourceRef = repo.findRef("refs/heads/" + sourceBranch);
      if (sourceRef == null) {
        throw new BranchOperationException(
            "Source branch does not exist: " + sourceBranch);
      }
      if (sourceBranch.equals(targetBranch)) {
        throw new BranchOperationException(
            "Cannot merge a branch into itself: " + sourceBranch);
      }

      MergeResult jgitResult = git.merge()
          .include(sourceRef)
          .setStrategy(MergeStrategy.RECURSIVE)
          .setCommit(true)
          .setMessage("Merge branch '" + sourceBranch + "' into '" + targetBranch + "'")
          .call();

      ModelMergeResult result = buildResult(jgitResult, sourceBranch, targetBranch, repo);

      if (result.isSuccessful()
          || result.getStatus() == ModelMergeResult.MergeStatus.CONFLICTING) {
        writeMergeMetadataDirectly(result, sourceBranch, targetBranch);
      }
      LOGGER.info("Merge result: {}", result);

      if (result.isSuccessful()) {
        markAsMerged(sourceBranch);
        writeMergeTrigger(result, sourceBranch, targetBranch);
        if (deleteAfterMerge) {
          deleteSourceBranch(git, sourceBranch);
        }
      }
      return result;

    } catch (GitAPIException e) {
      throw new BranchOperationException("Merge failed: " + e.getMessage(), e);
    } catch (IOException e) {
      throw new BranchOperationException(
          "Failed to open repository: " + e.getMessage(), e);
    }
  }

  /**
   * Writes merge metadata directly when merge is triggered via API, since the conflict
   * file list is available from JGit but would be lost if passed through the trigger
   * file / watcher path. The watcher path (hook-triggered) writes its own metadata via
   * PostMergeHandler.
   *
   * @param result       the merge result.
   * @param sourceBranch the branch that was merged in.
   * @param targetBranch the branch that received the merge.
   */
  private void writeMergeMetadataDirectly(
      ModelMergeResult result, String sourceBranch, String targetBranch) {
    try {
      MergeResultFile resultFile = new MergeResultFile(repoRoot);
      String sha = result.getMergeCommitSha() != null
          ? result.getMergeCommitSha()
          : "no-commit-" + System.currentTimeMillis();
      ValidationResult validationResult = result.isSuccessful()
          ? ValidationResult.success()
          : ValidationResult.failure(List.of(
              "Merge resulted in conflicts: "
                  + String.join(", ", result.getConflictingFiles())));
      resultFile.writeMetadata(
          sha, sourceBranch, targetBranch, validationResult, result.getConflictingFiles());
      LOGGER.info("Merge metadata written directly for commit {}",
          sha.substring(0, Math.min(7, sha.length())));
    } catch (IOException e) {
      LOGGER.warn("Failed to write merge metadata directly (non-critical): {}",
          e.getMessage());
    }
  }

  /**
   * Translates a JGit {@link MergeResult} into a {@link ModelMergeResult}.
   *
   * @param jgitResult   the raw JGit merge result.
   * @param sourceBranch the branch that was merged in.
   * @param targetBranch the branch that received the merge.
   * @param repo         the Git repository (unused, reserved for future use).
   * @return the translated {@link ModelMergeResult}.
   *
   * @throws IOException if the repository cannot be read.
   */
  private ModelMergeResult buildResult(
      MergeResult jgitResult, String sourceBranch, String targetBranch,
      Repository repo) throws IOException {
    switch (jgitResult.getMergeStatus()) {

      case FAST_FORWARD:
      case FAST_FORWARD_SQUASHED:
        {
        ObjectId newHead = jgitResult.getNewHead();
        String sha = newHead != null ? newHead.getName() : "";
        LOGGER.info("Fast-forward merge completed, new HEAD: {}",
            sha.substring(0, Math.min(7, sha.length())));
        return ModelMergeResult.fastForward(sourceBranch, targetBranch, sha);
        }

      case MERGED:
      case MERGED_SQUASHED:
      case MERGED_NOT_COMMITTED:
        {
        ObjectId newHead = jgitResult.getNewHead();
        String sha = newHead != null ? newHead.getName() : "";
        LOGGER.info("Merge commit created: {}", sha.substring(0, Math.min(7, sha.length())));
        return ModelMergeResult.success(sourceBranch, targetBranch, sha);
        }

      case CONFLICTING:
        {
        // getConflicts() returns Map<String, int[][]> only need the file paths
        Map<String, int[][]> conflicts =
            jgitResult.getConflicts() != null ? jgitResult.getConflicts() : Map.of();
        // TODO: MG-2 conflict classification
        // TODO: MG-3 conflict priority assignment
        // TODO: MG-8 conflict resolution modes
        List<String> conflictingFiles = new ArrayList<>(conflicts.keySet());
        LOGGER.warn("Merge resulted in {} conflict(s): {}",
            conflictingFiles.size(), conflictingFiles);
        return ModelMergeResult.conflicting(sourceBranch, targetBranch, conflictingFiles);
        }

      case ABORTED:
      case CHECKOUT_CONFLICT:
      case FAILED:
      default:
        {
        // getFailingPaths() returns Map<String, MergeFailureReason> for CHECKOUT_CONFLICT
        String reason = jgitResult.getMergeStatus().toString();
        if (jgitResult.getFailingPaths() != null
            && !jgitResult.getFailingPaths().isEmpty()) {
          String failingFiles = String.join(", ", jgitResult.getFailingPaths().keySet());
          reason += " - failing paths: " + failingFiles;
        }
        LOGGER.error("Merge failed with status: {}", reason);
        return ModelMergeResult.failed(sourceBranch, targetBranch, reason);
        }
    }
  }

  /**
   * Marks the source branch metadata state as MERGED (BR-9).
   * Non-fatal if the metadata file does not exist.
   *
   * @param sourceBranch the name of the branch to mark as merged.
   */
  private void markAsMerged(String sourceBranch) {
    Path metadataFile =
        repoRoot.resolve(METADATA_DIR).resolve(sourceBranch + ".metadata");

    if (!Files.exists(metadataFile)) {
      LOGGER.debug("No metadata file for '{}', skipping MERGED status update", sourceBranch);
      return;
    }
    try {
      BranchMetadata metadata = BranchMetadata.readFrom(metadataFile);
      metadata.setState(BranchState.MERGED);
      metadata.writeTo(metadataFile);
      LOGGER.info("Branch '{}' marked as MERGED", sourceBranch);
    } catch (IOException e) {
      LOGGER.warn("Failed to mark branch '{}' as MERGED (non-critical): {}",
          sourceBranch, e.getMessage());
    }
  }

  /**
   * Writes the merge trigger file so VsumMergeWatcher picks up and validates the merged
   * state. Non-fatal if writing fails — the merge has already completed successfully.
   *
   * @param result       the successful merge result.
   * @param sourceBranch the branch that was merged in.
   * @param targetBranch the branch that received the merge.
   */
  private void writeMergeTrigger(
      ModelMergeResult result, String sourceBranch, String targetBranch) {
    try {
      String sha = result.getMergeCommitSha() != null
          ? result.getMergeCommitSha()
          : "fast-forward";
      mergeTriggerFile.createTrigger(sha, sourceBranch, targetBranch);
      LOGGER.debug("Merge trigger written for VsumMergeWatcher");
    } catch (IOException e) {
      LOGGER.warn("Failed to write merge trigger (non-critical): {}", e.getMessage());
    }
  }

  /**
   * Deletes the source branch from Git after a successful merge (BR-5).
   * Non-fatal if deletion fails — the merge has already completed.
   *
   * @param git          the open Git instance.
   * @param sourceBranch the branch to delete.
   */
  private void deleteSourceBranch(Git git, String sourceBranch) {
    try {
      git.branchDelete().setBranchNames(sourceBranch).setForce(false).call();
      LOGGER.info("Source branch '{}' deleted after merge (BR-5)", sourceBranch);
    } catch (GitAPIException e) {
      LOGGER.warn("Failed to delete source branch '{}' after merge (non-critical): {}",
          sourceBranch, e.getMessage());
    }
  }
}
