package tools.vitruv.framework.vsum.branch;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import tools.vitruv.framework.vsum.branch.data.BranchMetadata;
import tools.vitruv.framework.vsum.branch.data.BranchState;
import tools.vitruv.framework.vsum.branch.exception.BranchOperationException;
import tools.vitruv.framework.vsum.branch.handler.PostCheckoutHandler;

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
   * @param repoRoot the root directory of the Git repository, which must contain a
   *                 {@code .git} subdirectory.
   * @throws IllegalArgumentException if the path does not point to a valid Git repository.
   */
  public BranchManager(Path repoRoot) {
    this.repoRoot = checkNotNull(repoRoot, "Repository root must not be null");
    checkArgument(Files.isDirectory(repoRoot.resolve(".git")),
        "No Git repository found at: %s", repoRoot);
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
    LOGGER.debug("createBranch called: name='{}', fromBranch='{}'", name, fromBranch);

    try (var git = Git.open(repoRoot.toFile())) {
      var repo = git.getRepository();

      // resolve the source branch reference to verify it exists before creating anything
      var sourceRef = repo.findRef("refs/heads/" + fromBranch);
      if (sourceRef == null) {
        LOGGER.debug("Source branch not found: '{}'", fromBranch);
        throw new BranchOperationException("Source branch does not exist: " + fromBranch);
      }

      // create a new branch that points to the same commit as the source branch
      git.branchCreate()
          .setName(name)
          .setStartPoint(sourceRef.getObjectId().getName())
          .call();
      LOGGER.debug("Git branch created: name='{}', startPoint='{}'",
          name, sourceRef.getObjectId().getName());

      // write metadata of the new branch
      var now = LocalDateTime.now();
      var metadata = new BranchMetadata(name, BranchState.ACTIVE, fromBranch, now, now);
      metadata.writeTo(metadataPath(name));
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
   * handler if one has been configured. The branch can be identified either by its exact
   * name or by a prefix of its unique identifier (uid).
   *
   * @param nameOrUid the branch name or a uid prefix to switch to.
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

      // perform the checkout
      git.checkout().setName(resolvedName).call();
      LOGGER.info("Switched to branch '{}'", resolvedName);

      // notify the VirtualModel so it can reload its state to match the new branch
      if (postCheckoutHandler != null && oldBranch != null) {
        LOGGER.debug("Invoking post-checkout handler for branch switch");
        postCheckoutHandler.onBranchSwitch(oldBranch, resolvedName);
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
   * @param name the name of the branch to delete.
   *
   * @throws BranchOperationException if the branch is currently checked out, the branch
   *     does not exist, or the Git operation fails.
   */
  public void deleteBranch(String name) throws BranchOperationException {
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

      // force-delete the branch reference
      git.branchDelete().setBranchNames(name).setForce(true).call();

      // update branch lifecycle state to DELETED rather than removing the file so that
      // the branch history and topology remain intact.
      var metadataFile = metadataPath(name);
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
          // branch exists in Git but not in Vitruvius metadata — synthesize defaults.
          var now = LocalDateTime.now();
          result.add(new BranchMetadata(name, BranchState.ACTIVE, "unknown", now, now));
          LOGGER.debug("Branch '{}' has no metadata file, synthesized defaults", name);
        }
      }
      LOGGER.debug("Listed {} branch(es)", result.size());
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
   * Resolves a branch identifier to a branch name.
   *
   * @param name a branch name.
   * @return the resolved branch name.
   *
   * @throws BranchOperationException if the identifier matches no branch or is ambiguous
   *     (matches multiple branches by uid).
   */
  public String resolveBranchIdentifier(String name) throws BranchOperationException {
    checkNotNull(name, "Branch identifier must not be null");
    try (var git = Git.open(repoRoot.toFile())) {
      var repo = git.getRepository();

      var exactRef = repo.findRef("refs/heads/" + name);
      if (exactRef != null) {
        LOGGER.debug("Resolved identifier '{}' to branch name directly", name);
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
    var metadataDir = repoRoot.resolve(METADATA_DIR);
    if (!Files.isDirectory(metadataDir)) {
      return new LinkedHashMap<>();
    }
    try {
      var topology = new LinkedHashMap<String, List<String>>();
      try (var stream = Files.list(metadataDir)) {
        var metadataFiles = stream
            .filter(p -> p.toString().endsWith(".metadata"))
            .toList();
        for (var file : metadataFiles) {
          var metadata = BranchMetadata.readFrom(file);

          // deleted branches are excluded from the topology
          if (metadata.getState() == BranchState.DELETED) {
            continue;
          }
          topology.computeIfAbsent(metadata.getParent(), k -> new ArrayList<>())
              .add(metadata.getName());
        }
      }
      LOGGER.debug("Built branch topology with {} parent(s) {}", topology.size(), topology);
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

    if (name.isBlank()) {
      throw new BranchOperationException("Branch name must not be blank");
    }
    // Git interprets '..' as a commit range operator, so it is not valid in a branch name.
    if (name.contains("..")) {
      throw new BranchOperationException("Branch name must not contain '..': " + name);
    }

    // Git uses the '.lock' suffix for reference lock files; a branch with this suffix
    // would conflict with Git internal file naming.
    if (name.endsWith(".lock")) {
      throw new BranchOperationException("Branch name must not end with '.lock': " + name);
    }
    // these characters have special meaning in Git revision syntax or on the file system.
    if (name.chars().anyMatch(c -> " ~^:?*[\\".indexOf(c) >= 0)) {
      throw new BranchOperationException("Branch name contains illegal characters: " + name);
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
      LOGGER.debug("Branch '{}' has no metadata, defaulting to ACTIVE", name);
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
      LOGGER.debug("No metadata file found for branch '{}', skipping MERGED status update",
          branchName);
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
   * Returns the path to the metadata file for the given branch name.
   *
   * @param branchName the branch name.
   * @return the path to the {@code .metadata} file.
   */
  private Path metadataPath(String branchName) {
    return repoRoot.resolve(METADATA_DIR).resolve(branchName + ".metadata");
  }
}
