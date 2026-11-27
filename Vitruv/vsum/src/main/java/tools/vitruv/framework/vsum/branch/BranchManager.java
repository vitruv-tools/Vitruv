package tools.vitruv.framework.vsum.branch;

import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ResetCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheBuilder;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.lib.*;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import tools.vitruv.framework.vsum.branch.data.BranchMetadata;
import tools.vitruv.framework.vsum.branch.data.BranchState;
import tools.vitruv.framework.vsum.branch.data.MaturityLevel;
import tools.vitruv.framework.vsum.branch.exception.BranchOperationException;
import tools.vitruv.framework.vsum.branch.handler.PostCheckoutHandler;
import tools.vitruv.framework.vsum.branch.util.GitNameValidator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Manages Git-based branches for the Vitruvius. All branch operations such as creation,
 * switching, deletion, and querying will be delegated to Git via JGit.
 *
 * <p>Branch metadata (lifecycle state, parent branch, unique identifier and timestamps)
 * is persisted in {@code .vitruvius/branches/} alongside the repository.
 * This allows Vitruvius to track branch history and topology independently of Git,
 * which only stores the current state of each branch reference.
 *
 * <p>Branches that are created outside of this manager (for example, via a direct
 * {@code git branch} command) are still visible through this manager with synthesized
 * metadata. They are treated as {@link BranchState#ACTIVE} with unknown parent, so that
 * the rest of the system can handle them correctly without failing.
 */
public class BranchManager {

  private static final Logger LOGGER = LogManager.getLogger(BranchManager.class);

  /**
   * Subdirectory inside repository root where branch metadata files are stored.
   */
  private static final String METADATA_DIR = ".vitruvius/branches";

  private final Path repoRoot;

  /**
   * Handler invoked after a branch switch is performed. If set, the handler receives
   * the old and new branch names so that the VirtualModel can reload its state correctly.
   */
  @Setter
  private PostCheckoutHandler postCheckoutHandler;

  /**
   * Creates a new {@link BranchManager} for the Git repository at the given path.
   *
   * <p>Initializes metadata for all branches that already exist in Git but have no
   * persisted metadata file (e.g., {@code master} created via {@code git init}).
   * These branches use their own name as parent (self-referential root), consistent
   * with the post-checkout hook which sets {@code OLD_BRANCH=NEW_BRANCH} for
   * {@code master}/{@code main}.
   *
   * @param repoRoot the root directory of the Git repository, which must contain a
   *                 {@code .git} subdirectory.
   * @throws IllegalArgumentException if the path does not point to a valid Git repository.
   */
  public BranchManager(Path repoRoot) {
    this.repoRoot = checkNotNull(repoRoot, "Repository root must not be null");
    checkArgument(Files.isDirectory(repoRoot.resolve(".git")),
        "No Git repository found at: %s", repoRoot);
    initializeMissingMetadata();
  }

  /**
   * Creates metadata files for all Git branches that do not yet have one.
   * Uses the branch's own name as parent for branches created outside this manager
   * (e.g., the initial {@code master} branch from {@code git init}).
   * Called once from the constructor; failures are non-fatal and logged as warnings.
   */
  private void initializeMissingMetadata() {
    try (var git = Git.open(repoRoot.toFile())) {
      var refs = git.branchList().call();
      for (var ref : refs) {
        var name = ref.getName().replace("refs/heads/", "");
        // Use the branch's own name as parent: consistent with the post-checkout hook,
        // which sets OLD_BRANCH=NEW_BRANCH for master/main (branches that predate this system).
        ensureMetadataExists(name, name);
      }
    } catch (IOException | GitAPIException e) {
      LOGGER.warn("Failed to initialize branch metadata: {}", e.getMessage());
    }
  }

  /**
   * Creates a new branch with the given name, forked from a specified source branch.
   * The new branch is created at the same commit as the source branch and is recorded
   * in {@link BranchState#ACTIVE} state with a metadata file.
   *
   * <p>The unique identifier stored in metadata is the first seven characters of the
   * commit hash that the new branch points to.
   *
   * @param name       name of the new branch.
   * @param fromBranch name of the existing branch to fork from.
   * @return the {@link BranchMetadata} of the newly created branch.
   *
   * @throws BranchOperationException when a branch with identical name already exists,
   *     the source branch does not exist, or the Git operation fails.
   */
  public BranchMetadata createBranch(String name, String fromBranch)
      throws BranchOperationException {
    checkNotNull(name, "Branch name must not be null");
    checkNotNull(fromBranch, "Source branch must not be null");
    validateBranchName(name);

    try (var git = Git.open(repoRoot.toFile())) {
      var repo = git.getRepository();

      // resolve the source branch reference to verify it exists before creating anything
      var sourceRef = repo.findRef("refs/heads/" + fromBranch);
      if (sourceRef == null) {
        throw new BranchOperationException("Source branch does not exist: " + fromBranch);
      }

      // write metadata of the new branch to disk first so it can be committed
      var now = LocalDateTime.now();
      var metadata = new BranchMetadata(name, BranchState.ACTIVE, fromBranch, now, now,
          MaturityLevel.DRAFT);
      Path metaFile = metadataPath(name);
      metadata.writeTo(metaFile);

      // Commit metadata to the parent branch so the file is in the parent's tree.
      // When the new branch is then created from the updated parent HEAD, both branches
      // share the metadata commit as their common base, which means git checkout between
      // them never removes the metadata file from the working directory.
      commitMetadataFile(git, fromBranch, metaFile);

      // Rebuild the index to match the new HEAD when fromBranch is currently checked out.
      // commitMetadataFile uses a low-level ref update that does not touch the on-disk index,
      // so without this sync the index would be one commit behind HEAD. A stale index causes
      // subsequent commits (e.g. made by a freshly-opened Git instance) to build their tree
      // from the old index, silently omitting the newly-committed metadata file.
      String currentBranch = repo.getBranch();
      if (fromBranch.equals(currentBranch)) {
        rebuildIndexFromHead(repo);
      }

      // create new branch from the UPDATED parent HEAD (which now includes the metadata file)
      var updatedSourceRef = repo.findRef("refs/heads/" + fromBranch);
      git.branchCreate()
          .setName(name)
          .setStartPoint(updatedSourceRef.getObjectId().getName())
          .call();
      LOGGER.info("Created branch '{}' from '{}'", name, fromBranch);
      return metadata;

    } catch (GitAPIException e) {
      throw new BranchOperationException("Failed to create branch '" + name + "'", e);

    } catch (IOException e) {
      throw new BranchOperationException(
          "Failed to write metadata for branch '" + name + "'", e);
    }
  }

  /**
   * Switches the working directory to the specified branch and triggers the post-checkout
   * handler if one has been configured. The branch can be identified by its exact name.
   *
   * @param nameOrUid the branch name to switch to.
   *
   * @throws BranchOperationException if the branch cannot be found or the checkout fails.
   */
  public void switchBranch(String nameOrUid) throws BranchOperationException {
    checkNotNull(nameOrUid, "Branch identifier must not be null");
    var resolvedName = resolveBranchIdentifier(nameOrUid);

    String oldBranch = null;
    try (var git = Git.open(repoRoot.toFile())) {
      var repo = git.getRepository();

      // capture the current branch name before switching so the post-checkout handler
      // can compare old and new state.
      var head = repo.findRef("HEAD");
      if (head != null && head.isSymbolic()) {
        oldBranch = Repository.shortenRefName(head.getTarget().getName());
      }

      // Hard-reset to HEAD before checking out. Discards VSUM-internal writes that
      // accumulate in the working tree after each commit (e.g., correspondence model
      // re-saves triggered by EMF notifications). These post-commit writes are never
      // user data; the VSUM reloads from HEAD after switching, so discarding them
      // is safe and prevents CheckoutConflictException.
      git.reset().setMode(ResetCommand.ResetType.HARD).call();

      // perform the checkout
      git.checkout().setName(resolvedName).call();
      LOGGER.info("Switched to branch '{}'", resolvedName);

      // notify the VirtualModel so it can reload its state to match the new branch
      if (postCheckoutHandler != null && oldBranch != null) {
        postCheckoutHandler.onBranchSwitch(oldBranch, resolvedName);
      } else if (postCheckoutHandler == null) {
        LOGGER.warn("No post-checkout handler configured; VSUM will not be reloaded after "
            + "switching to branch '{}'", resolvedName);
      }

    } catch (GitAPIException e) {
      throw new BranchOperationException(
          "Failed to switch to branch '" + resolvedName + "'", e);

    } catch (IOException e) {
      throw new BranchOperationException(
          "Failed to open repository while switching to branch '" + resolvedName + "'", e);
    }
  }

  /**
   * Deletes the specified branch from Git and marks its metadata as
   * {@link BranchState#DELETED}. The metadata file is intentionally preserved after
   * deletion so that the branch topology and lifecycle history remain queryable even
   * for branches that no longer exist in Git.
   *
   * <p>Deleting the currently checked-out branch is not permitted, as Git itself does
   * not allow this, and it would leave the working directory in an undefined state.
   *
   * <p>Deleting an {@code ACTIVE} (unmerged) branch is blocked by default to protect
   * unmerged work. Pass {@code force = true} to override this check explicitly.
   *
   * @param name the name of the branch to delete.
   *
   * @throws BranchOperationException if the branch is currently checked out, the branch
   *     is ACTIVE and {@code force} is {@code false}, or the Git operation fails.
   */
  public void deleteBranch(String name) throws BranchOperationException {
    deleteBranch(name, false);
  }

  /**
   * Deletes a branch by name, with optional force override for unmerged branches.
   *
   * @param name  the name of the branch to delete.
   * @param force {@code true} to allow deletion of ACTIVE (unmerged) branches;
   *              {@code false} to block with an exception instead.
   *
   * @throws BranchOperationException if the branch is currently checked out, blocked by
   *     the ACTIVE guard (when {@code force} is {@code false}), or the Git operation fails.
   */
  public void deleteBranch(String name, boolean force) throws BranchOperationException {
    checkNotNull(name, "Branch name must not be null");

    try (var git = Git.open(repoRoot.toFile())) {
      var repo = git.getRepository();

      // check currently checked-out branch to avoid accidentally deleting current branch
      var head = repo.findRef("HEAD");
      if (head != null && head.getTarget() != null
          && head.getTarget().getName().equals("refs/heads/" + name)) {
        throw new BranchOperationException(
            "Cannot delete the currently checked-out branch: " + name);
      }

      // Refuse to delete an unmerged branch unless the caller explicitly requested force.
      // Read metadata before touching the git ref so the check is always consistent.
      var metadataFile = metadataPath(name);
      if (!force && Files.exists(metadataFile)) {
        var metadata = BranchMetadata.readFrom(metadataFile);
        if (metadata.getState() == BranchState.ACTIVE) {
          throw new BranchOperationException(
              "Cannot delete unmerged branch '" + name + "': branch state is ACTIVE. "
              + "Merge the branch first, or pass force=true to override.");
        }
      }

      // force-delete the branch reference
      git.branchDelete().setBranchNames(name).setForce(true).call();

      // update branch lifecycle state to DELETED rather than removing the file so that
      // the branch history and topology remain intact.
      if (Files.exists(metadataFile)) {
        var metadata = BranchMetadata.readFrom(metadataFile);
        metadata.setState(BranchState.DELETED);
        metadata.writeTo(metadataFile);
      }
      LOGGER.info("Deleted branch '{}'", name);

    } catch (GitAPIException e) {
      throw new BranchOperationException("Failed to delete branch '" + name + "'", e);

    } catch (IOException e) {
      throw new BranchOperationException(
          "Failed to update metadata for deleted branch '" + name + "'", e);
    }
  }

  /**
   * Returns metadata for all branches currently tracked by Git. Branches that were
   * created outside the {@link BranchManager} are included with synthesized metadata:
   * they are marked {@link BranchState#ACTIVE} with an unknown parent.
   *
   * @return a list of {@link BranchMetadata} for every branch currently present
   *     in the repository.
   *
   * @throws BranchOperationException if the repository cannot be read.
   */
  public List<BranchMetadata> listBranches() throws BranchOperationException {
    reconcileDeletedBranches();
    try (var git = Git.open(repoRoot.toFile())) {

      // list all local branch references
      var refs = git.branchList().call();
      var result = new ArrayList<BranchMetadata>();

      for (var ref : refs) {
        var name = ref.getName().replace("refs/heads/", "");
        var metadataFile = metadataPath(name);

        if (Files.exists(metadataFile)) {
          result.add(BranchMetadata.readFrom(metadataFile));
        } else {
          // branch exists in Git but not in Vitruvius metadata, so synthesize defaults.
          var now = LocalDateTime.now();
          result.add(new BranchMetadata(name, BranchState.ACTIVE, "unknown", now, now,
              MaturityLevel.DRAFT));
        }
      }
      return result;

    } catch (GitAPIException e) {
      throw new BranchOperationException("Failed to list branches", e);

    } catch (IOException e) {
      throw new BranchOperationException("Failed to read branch metadata while listing", e);
    }
  }

  /**
   * Returns all branches whose names match the given pattern. Supports {@code *} (matches
   * any sequence of characters) and {@code ?} (matches a single character).
   *
   * <p>The pattern is evaluated against branch names only (not unique identifiers or
   * parent names). For example, the pattern {@code "bugfix-*"} matches
   * {@code "bugfix-viewtype"} and {@code "bugfix-propagation"} but not
   * {@code "feature-vcs"}.
   *
   * <p>A glob wildcard is used instead of regex to keep the pattern syntax simple.
   *
   * @param pattern a glob pattern to match against branch names.
   * @return a list of {@link BranchMetadata} for all matching branches. Empty list is
   *     returned if no branch matches.
   *
   * @throws BranchOperationException if the branch list cannot be retrieved.
   */
  public List<BranchMetadata> findBranches(String pattern) throws BranchOperationException {
    checkNotNull(pattern, "Search pattern must not be null");

    // the glob matcher is obtained from the file system so that pattern semantics
    // are consistent with the underlying platform.
    var matcher = repoRoot.getFileSystem().getPathMatcher("glob:" + pattern);
    var allBranches = listBranches();
    var matches = allBranches.stream()
        .filter(m -> matcher.matches(Path.of(m.getName())))
        .collect(Collectors.toList());
    LOGGER.debug("Found {} branch(es) matching pattern '{}'", matches.size(), pattern);
    return matches;
  }

  /**
   * Validates that a branch with the given name exists in the repository and returns the
   * name unchanged. Performs an exact Git ref lookup ({@code refs/heads/<name>}).
   *
   * @param name the exact branch name to resolve.
   * @return {@code name}, confirmed to exist as a Git branch.
   *
   * @throws BranchOperationException if no branch with that exact name exists.
   */
  public String resolveBranchIdentifier(String name) throws BranchOperationException {
    checkNotNull(name, "Branch identifier must not be null");
    try (var git = Git.open(repoRoot.toFile())) {
      var repo = git.getRepository();

      var exactRef = repo.findRef("refs/heads/" + name);
      if (exactRef != null) {
        return name;
      }

      throw new BranchOperationException("No branch matches identifier: " + name);

    } catch (IOException e) {
      throw new BranchOperationException(
          "Failed to open repository while resolving identifier '" + name + "'", e);
    }
  }

  /**
   * Returns the parent-child topology of all managed branches. Each entry maps a parent
   * branch name to the list of branches that were directly forked from it via
   * {@link #createBranch}. Only branches with persisted {@link BranchMetadata} are
   * included. Branches created outside the {@link BranchManager} are not part of the
   * topology.
   *
   * @return a map from parent branch name to the list of its direct child branch names.
   *     Returns an empty map if no managed branches exist.
   *
   * @throws BranchOperationException if the metadata files cannot be read.
   */
  public Map<String, List<String>> getBranchTopology() throws BranchOperationException {
    reconcileDeletedBranches();
    var metadataDir = repoRoot.resolve(METADATA_DIR);
    if (!Files.isDirectory(metadataDir)) {
      return new LinkedHashMap<>();
    }
    try {
      var topology = new LinkedHashMap<String, List<String>>();
      try (var stream = Files.walk(metadataDir)) {
        var metadataFiles = stream
            .filter(p -> p.toString().endsWith(".metadata"))
            .toList();
        for (var file : metadataFiles) {
          var metadata = BranchMetadata.readFrom(file);

          if (metadata.getState() == BranchState.DELETED) {
            continue;
          }
          if (metadata.getParent().equals(metadata.getName())) {
            // Root branch: ensure it appears as a key even when it has no children yet.
            topology.computeIfAbsent(metadata.getName(), k -> new ArrayList<>());
            continue;
          }
          topology.computeIfAbsent(metadata.getParent(), k -> new ArrayList<>())
              .add(metadata.getName());
        }
      }
      return topology;

    } catch (IOException e) {
      throw new BranchOperationException(
          "Failed to read metadata while building topology", e);
    }
  }

  /**
   * Validates a proposed branch name against Git naming rules and checks that no branch
   * with the same name already exists in the repository.
   *
   * <p>The following rules are enforced:
   * <ul>
   *   <li>name must not be blank.</li>
   *   <li>name must not contain {@code ..} (Git interprets this as a range operator).</li>
   *   <li>name must not end with {@code .lock} (Git uses this suffix for lock files).</li>
   *   <li>name must not contain any of the characters {@code space ~ ^ : ? * [ \}.</li>
   *   <li>name must not conflict with an existing branch in the repository.</li>
   * </ul>
   *
   * @param name the branch name to validate.
   *
   * @throws BranchOperationException if the name is invalid or already in use.
   */
  public void validateBranchName(String name) throws BranchOperationException {
    checkNotNull(name, "Branch name must not be null");
    try {
      GitNameValidator.validateFormat(name);
    } catch (IllegalArgumentException e) {
      throw new BranchOperationException(e.getMessage(), e);
    }
    // check for a name collision with an existing branch reference.
    try (var git = Git.open(repoRoot.toFile())) {
      var repo = git.getRepository();
      if (repo.findRef("refs/heads/" + name) != null) {
        throw new BranchOperationException("Branch already exists: " + name);
      }
    } catch (IOException e) {
      throw new BranchOperationException(
          "Failed to open repository while validating branch name '" + name + "'", e);
    }
  }

  /**
   * Returns the current lifecycle state of the specified branch. If the branch has no
   * persisted metadata, it is assumed to be {@link BranchState#ACTIVE}.
   *
   * <p>If the branch no longer exists in Git but a metadata file is still present (for
   * example, after it was deleted via {@link #deleteBranch}), the state from the metadata
   * file is returned. This allows callers to distinguish deleted branches from unknown
   * branches.
   *
   * @param name the name of the branch.
   * @return the {@link BranchState} of the branch.
   *
   * @throws BranchOperationException if the branch does not exist in Git or the metadata
   *     cannot be read.
   */
  public BranchState getBranchState(String name) throws BranchOperationException {
    checkNotNull(name, "Branch name must not be null");

    try (var git = Git.open(repoRoot.toFile())) {
      var repo = git.getRepository();

      if (repo.findRef("refs/heads/" + name) == null) {
        // the branch is not present in Git; check whether a metadata file exists,
        // which would indicate it was previously managed and then deleted.
        var metadataFile = metadataPath(name);
        if (Files.exists(metadataFile)) {
          return BranchMetadata.readFrom(metadataFile).getState();
        }
        throw new BranchOperationException("Branch does not exist: " + name);
      }

      // branch exists in Git; return the state from metadata if available,
      // otherwise default to ACTIVE for branches not created via this manager.
      var metadataFile = metadataPath(name);
      if (Files.exists(metadataFile)) {
        return BranchMetadata.readFrom(metadataFile).getState();
      }
      return BranchState.ACTIVE;
    } catch (IOException e) {
      throw new BranchOperationException(
          "Failed to read metadata for branch '" + name + "'", e);
    }
  }

  /**
   * Marks the given branch as MERGED by updating its metadata state.
   * Called automatically after a successful merge is detected by VsumMergeWatcher.
   * The branch remains visible in metadata history but is excluded from topology.
   *
   * @param branchName the name of the branch that was merged in.
   *
   * @throws BranchOperationException if the metadata file cannot be read or written.
   */
  public void markAsMerged(String branchName) throws BranchOperationException {
    checkNotNull(branchName, "branch name must not be null");
    var metadataFile = metadataPath(branchName);

    if (!Files.exists(metadataFile)) {
      return;
    }

    try {
      var metadata = BranchMetadata.readFrom(metadataFile);
      metadata.setState(BranchState.MERGED);
      metadata.writeTo(metadataFile);
      LOGGER.info("Branch '{}' marked as MERGED", branchName);
    } catch (IOException e) {
      throw new BranchOperationException(
          "Failed to update metadata for merged branch '" + branchName + "'", e);
    }
  }

  /**
   * Updates the maturity level of the specified branch and persists the change.
   *
   * <p>If no metadata file exists for the branch (e.g. a branch created outside this
   * manager), the call is a no-op and a warning is logged.
   *
   * @param branchName the name of the branch.
   * @param maturity   the new maturity level, must not be null.
   *
   * @throws BranchOperationException if the metadata file cannot be read or written.
   */
  public void setBranchMaturity(String branchName, MaturityLevel maturity)
      throws BranchOperationException {
    checkNotNull(branchName, "branch name must not be null");
    checkNotNull(maturity, "maturity must not be null");

    var metadataFile = metadataPath(branchName);
    if (!Files.exists(metadataFile)) {
      LOGGER.warn("No metadata file found for branch '{}', skipping maturity update", branchName);
      return;
    }
    try {
      var metadata = BranchMetadata.readFrom(metadataFile);
      metadata.setMaturity(maturity);
      metadata.writeTo(metadataFile);
      LOGGER.info("Branch '{}' maturity set to {}", branchName, maturity);
    } catch (IOException e) {
      throw new BranchOperationException(
          "Failed to update maturity for branch '" + branchName + "'", e);
    }
  }

  /**
   * Creates a metadata file for the given branch if one does not already exist, then
   * commits it into the Git trees of both {@code branchName} and {@code parentBranch}
   * using the same low-level object-insertion approach as {@link #createBranch}. This
   * ensures the file is tracked in Git regardless of which branch is checked out, so
   * that a subsequent {@code git checkout} never silently removes it from disk.
   *
   * <p>When the caller is currently on {@code branchName}, the Git index is re-synced to
   * the new HEAD after the low-level ref update. Without that sync, the index would be
   * one commit behind the ref and {@code git status} would incorrectly show the metadata
   * file as deleted. No sync is needed when committing onto a non-current branch because
   * the index is not affected by ref updates on other branches.
   *
   * <p>All failures are non-fatal and logged as warnings so that a metadata write or
   * commit error never blocks a branch switch.
   *
   * @param branchName   the name of the branch.
   * @param parentBranch the name of the branch this was branched from.
   */
  public void ensureMetadataExists(String branchName, String parentBranch) {
    checkNotNull(branchName, "branch name must not be null");
    checkNotNull(parentBranch, "parent branch must not be null");

    Path metadataFile = metadataPath(branchName);
    if (Files.exists(metadataFile)) {
      return;
    }
    try {
      Files.createDirectories(metadataFile.getParent());
      LocalDateTime now = LocalDateTime.now();
      BranchMetadata metadata =
          new BranchMetadata(branchName, BranchState.ACTIVE, parentBranch, now, now,
              MaturityLevel.DRAFT);
      metadata.writeTo(metadataFile);
      LOGGER.info("Created metadata for branch '{}' (parent: '{}')", branchName, parentBranch);
    } catch (IOException e) {
      LOGGER.warn("Failed to create metadata for branch '{}' (non-critical): {}",
          branchName, e.getMessage());
      return;
    }

    try (var git = Git.open(repoRoot.toFile())) {
      var repo = git.getRepository();

      var head = repo.findRef("HEAD");
      String currentBranch = (head != null && head.isSymbolic())
          ? Repository.shortenRefName(head.getTarget().getName())
          : null;

      commitMetadataFile(git, branchName, metadataFile);
      if (branchName.equals(currentBranch)) {
        rebuildIndexFromHead(repo);
      }
      if (!branchName.equals(parentBranch)) {
        commitMetadataFile(git, parentBranch, metadataFile);
      }
    } catch (IOException e) {
      LOGGER.warn("Failed to commit metadata for branch '{}' into Git (non-critical): {}",
          branchName, e.getMessage());
    }
  }

  /**
   * Walks all {@code .metadata} files in the metadata directory and marks any branch whose
   * Git ref no longer exists as {@link BranchState#DELETED}. Only branches in state
   * {@link BranchState#ACTIVE} are updated; {@link BranchState#MERGED} branches whose ref
   * was removed after merging are left unchanged because MERGED is the more informative
   * terminal state. Failures are non-fatal and logged as warnings.
   */
  private void reconcileDeletedBranches() {
    Path metadataDir = repoRoot.resolve(METADATA_DIR);
    if (!Files.isDirectory(metadataDir)) {
      return;
    }
    try (var git = Git.open(repoRoot.toFile())) {
      var repo = git.getRepository();
      try (var stream = Files.walk(metadataDir)) {
        stream.filter(p -> p.toString().endsWith(".metadata"))
            .forEach(file -> {
              try {
                var metadata = BranchMetadata.readFrom(file);
                if (metadata.getState() == BranchState.ACTIVE
                    && repo.findRef("refs/heads/" + metadata.getName()) == null) {
                  metadata.setState(BranchState.DELETED);
                  metadata.writeTo(file);
                  LOGGER.info("Reconciled branch '{}' as DELETED (ref no longer exists)",
                      metadata.getName());
                }
              } catch (IOException e) {
                LOGGER.warn("Failed to reconcile metadata file '{}' (non-critical): {}",
                    file.getFileName(), e.getMessage());
              }
            });
      }
    } catch (IOException e) {
      LOGGER.warn("Failed to open repository during branch reconciliation (non-critical): {}",
          e.getMessage());
    }
  }

  /**
   * Returns the path to the metadata file for the given branch name.
   *
   * @param branchName the branch name.
   * @return the path to the {@code .metadata} file.
   */
  private Path metadataPath(String branchName) {
    return repoRoot.resolve(METADATA_DIR).resolve(branchName + ".metadata");
  }

  /**
   * Rebuilds the Git index to match the current HEAD tree. Called after a low-level ref
   * update on the current branch so that {@code git status} remains clean. Low-level ref
   * updates advance the HEAD commit without touching the working directory index, which
   * would otherwise cause tracked files added in the new commit to appear as deleted.
   */
  private void rebuildIndexFromHead(Repository repo) throws IOException {
    ObjectId headId = repo.resolve(Constants.HEAD);
    if (headId == null) {
      return;
    }
    try (RevWalk rw = new RevWalk(repo); ObjectReader reader = repo.newObjectReader()) {
      RevCommit head = rw.parseCommit(headId);
      DirCache dc = repo.lockDirCache();
      DirCacheBuilder builder = dc.builder();
      builder.addTree(new byte[0], DirCacheEntry.STAGE_0, reader, head.getTree());
      builder.finish();
      dc.write();
      dc.commit();
    }
  }

  /**
   * Adds a single file to a branch's committed tree using JGit's low-level object-insertion API.
   * This advances the branch ref by one system commit without touching the working directory index
   * or triggering any hooks. Used by {@link #createBranch} and {@link #ensureMetadataExists} to
   * ensure branch metadata files are present in the Git tree of each branch, so that a subsequent
   * {@code git checkout} never removes the metadata file from disk.
   */
  private void commitMetadataFile(Git git, String branch, Path file) throws IOException {
    Repository repo = git.getRepository();
    String refName = Constants.R_HEADS + branch;
    ObjectId headId = repo.resolve(refName);

    try (ObjectInserter inserter = repo.newObjectInserter();
         ObjectReader reader = repo.newObjectReader()) {

      DirCache cache = DirCache.newInCore();
      DirCacheBuilder builder = cache.builder();

      if (headId != null) {
        try (RevWalk rw = new RevWalk(reader)) {
          RevCommit head = rw.parseCommit(headId);
          builder.addTree(new byte[0], DirCacheEntry.STAGE_0, reader, head.getTree());
        }
      }

      byte[] content = Files.readAllBytes(file);
      ObjectId blobId = inserter.insert(Constants.OBJ_BLOB, content);
      String relativePath = repoRoot.relativize(file).toString().replace('\\', '/');
      DirCacheEntry entry = new DirCacheEntry(relativePath);
      entry.setFileMode(FileMode.REGULAR_FILE);
      entry.setObjectId(blobId);
      builder.add(entry);
      builder.finish();

      ObjectId treeId = cache.writeTree(inserter);

      CommitBuilder cb = new CommitBuilder();
      cb.setTreeId(treeId);
      if (headId != null) {
        cb.setParentId(headId);
      }
      PersonIdent ident = new PersonIdent("Vitruvius", "vitruvius@system");
      cb.setAuthor(ident);
      cb.setCommitter(ident);
      cb.setMessage("[vitruvius] Branch metadata: " + file.getFileName()
          + "\n\nVitruvius-System: true\n");

      ObjectId newCommitId = inserter.insert(cb);
      inserter.flush();

      RefUpdate ru = repo.updateRef(refName);
      ru.setNewObjectId(newCommitId);
      ru.setExpectedOldObjectId(headId != null ? headId : ObjectId.zeroId());
      ru.setRefLogMessage("[vitruvius] Branch metadata", false);
      RefUpdate.Result result = ru.update();

      if (result != RefUpdate.Result.NEW && result != RefUpdate.Result.FAST_FORWARD) {
        throw new IOException(
            "Branch ref update for '" + branch + "' failed: " + result);
      }
    }
  }
}
