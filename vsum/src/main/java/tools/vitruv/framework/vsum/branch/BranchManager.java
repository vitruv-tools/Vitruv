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
import tools.vitruv.framework.vsum.branch.exception.BranchOperationException;

/**
 * Manages Git-based branches for the Vitruvius. All branch operations such as creation, switching, deletion,
 * and querying will be delegated to Git via JGit. Branch metadata (lifecycle, state, parent, timestamps) is
 * persisted in {@code .vitruvius/branches/} alongside the repository.
 */
public class BranchManager {
    private static final Logger LOGGER = LogManager.getLogger(BranchManager.class);
    private static final String METADATA_DIR = ".vitruvius/branches";
    private final Path repoRoot;
    @Setter
    private PostCheckoutHandler postCheckoutHandler;

    /**
     * Creates a new {@link BranchManager} for the Git repository at the given path.
     *
     * @param repoRoot The root directory of the Git repository.
     * @throws IllegalArgumentException If the path does not contain a Git repository.
     */
    public BranchManager(Path repoRoot) {
        this.repoRoot = checkNotNull(repoRoot, "Repository root must not be null");
        checkArgument(Files.isDirectory(repoRoot.resolve(".git")), "No Git repository found at: %s", repoRoot);
    }

    /**
     * Creates a new branch with the given name, forked from the specified source branch. The new
     * branch is created in {@link BranchState#ACTIVE} state.
     *
     * @param name       The name of the new branch.
     * @param fromBranch The name of the branch to fork from.
     * @return The {@link BranchMetadata} of the newly created branch.
     * @throws BranchOperationException If the branch already exists, the source branch does not exist, or the Git operation fails.
     */
    public BranchMetadata createBranch(String name, String fromBranch) throws BranchOperationException {
        checkNotNull(name, "Branch name must not be null");
        checkNotNull(fromBranch, "Source branch must not be null");
        validateBranchName(name);
        LOGGER.debug("createBranch called: name='{}', fromBranch='{}'", name, fromBranch);

        // handling .git directory
        try (var git = Git.open(repoRoot.toFile())) {
            // resolve the source branch git open
            var repo = git.getRepository();
            //find source branch reference git show-ref refs/heads/<frombranch>
            var sourceRef = repo.findRef("refs/heads/" + fromBranch);
            if (sourceRef == null) {
                LOGGER.debug("Source branch not found: '{}'", fromBranch);
                throw new BranchOperationException("Source branch does not exist: " + fromBranch);
            }
            // create a new branch that points to the same commit as the source branch
            // equivalent to git branch <name> <fromBranch> or git branch <name> <commit-hash>
            // todo: DOKU sourceRef (main, HEAD,..) getObjectId() returns commit id ref points to, getName returns commit hash as string
            git.branchCreate().setName(name).setStartPoint(sourceRef.getObjectId().getName()).call();
            LOGGER.debug("Git branch created: name='{}', startPoint='{}'", name, sourceRef.getObjectId().getName());

            // get ref to the new branch
            var newRef = repo.findRef("refs/heads/" + name);
            var uid = newRef.getObjectId().getName().substring(0, 7);

            // write metadata of the new branch
            var now = LocalDateTime.now();
            var metadata = new BranchMetadata(name, uid, BranchState.ACTIVE, fromBranch, now, now);
            metadata.writeTo(metadataPath(name));
            LOGGER.info("Created branch '{}' from '{}'", name, fromBranch);
            return metadata;

        } catch (GitAPIException e) {
            throw new BranchOperationException("Failed to create branch '" + name + "'", e);

        } catch (IOException e) {
            throw new BranchOperationException("Failed to write metadata for branch '" + name + "'", e);
        }
    }

    /**
     * Switches the working directory to the specified branch. The branch can be identified either by
     * its name or by a prefix of its unique identifier (uid).
     *
     * @param nameOrUid The branch name or an uid prefix to switch to.
     * @throws BranchOperationException If the branch cannot be found or the checkout fails.
     */
    public void switchBranch(String nameOrUid) throws BranchOperationException {
        checkNotNull(nameOrUid, "Branch identifier must not be null");
        var resolvedName = resolveBranchIdentifier(nameOrUid);

        String oldBranch = null;
        try (var git = Git.open(repoRoot.toFile())) {
            var repo = git.getRepository();
            var head = repo.findRef("HEAD"); // get cirrent branch name git symbolic-ref --short HEAD
            if (head != null && head.isSymbolic()) {
                oldBranch = Repository.shortenRefName(head.getTarget().getName());
            }
            //checkout the new branch git checkout <targetbranch>
            git.checkout().setName(resolvedName).call();
            LOGGER.info("Switched to branch '{}'", resolvedName);

            //trigger post checkout actions
            if (postCheckoutHandler != null && oldBranch != null) {
                LOGGER.debug("Invoking post-checkout handler for branch switch");
                postCheckoutHandler.onBranchSwitch(oldBranch, resolvedName);
            }

        } catch (GitAPIException e) {
            throw new BranchOperationException("Failed to switch to branch '" + resolvedName + "'", e);

        } catch (IOException e) {
            throw new BranchOperationException("Failed to open repository while switching to branch '" + resolvedName + "'", e);

        }
    }

    /**
     * Deletes the specified branch. The branch must not be the currently checked-out branch. The
     * branch's {@link BranchMetadata} is updated to {@link BranchState#DELETED} rather than removed,
     * to preserve history.
     *
     * @param name The name of the branch to delete.
     * @throws BranchOperationException If the branch is currently active, does not exist, or the
     *                                  Git operation fails.
     */
    public void deleteBranch(String name) throws BranchOperationException {
        checkNotNull(name, "Branch name must not be null");

        try (var git = Git.open(repoRoot.toFile())) {
            var repo = git.getRepository();
            // check currently checked-out branch to avoid accidentally deleting current branch
            var head = repo.findRef("HEAD");
            if (head != null && head.getTarget() != null && head.getTarget().getName().equals("refs/heads/" + name)) {
                throw new BranchOperationException("Cannot delete the currently checked-out branch: " + name);
            }

            // delete the branch git branch -D <branchname>
            git.branchDelete().setBranchNames(name).setForce(true).call();

            // Update branch lifecycle state to DELETED
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
            throw new BranchOperationException("Failed to update metadata for deleted branch '" + name + "'", e);

        }
    }

    /**
     * Returns metadata for all branches currently tracked by Git. Branches that were created outside
     * of the {@link BranchManager} (i.e., have no metadata file) are included with synthesized
     * metadata: they are marked {@link BranchState#ACTIVE} with an unknown parent.
     *
     * @return A list of {@link BranchMetadata} for every branch in the repository.
     * @throws BranchOperationException If the repository cannot be read.
     */
    public List<BranchMetadata> listBranches() throws BranchOperationException {
        try (var git = Git.open(repoRoot.toFile())) {
            //list all local branches git branch --list
            var refs = git.branchList().call();
            var result = new ArrayList<BranchMetadata>();

            for (var ref : refs) {
                var name = ref.getName().replace("refs/heads/", "");
                var metadataFile = metadataPath(name);

                if (Files.exists(metadataFile)) {
                    result.add(BranchMetadata.readFrom(metadataFile));
                } else {
                    // Branch exists in Git but not in Vitruvius metadata. synthesize metadata to ensure consistent handling.
                    var uid = ref.getObjectId().getName().substring(0, 7);
                    var now = LocalDateTime.now();
                    result.add(new BranchMetadata(name, uid, BranchState.ACTIVE, "unknown", now, now));
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
     * Returns all branches whose names match the given glob pattern. Supports {@code *} (matches any
     * sequence of characters) and {@code ?} (matches a single character).
     *
     * @param pattern A glob pattern to match against branch names (e.g., {@code "feature-*"}).
     * @return A list of {@link BranchMetadata} for all matching branches.
     * @throws BranchOperationException If the branch list cannot be retrieved.
     */
    public List<BranchMetadata> findBranches(String pattern) throws BranchOperationException {
        checkNotNull(pattern, "Search pattern must not be null");
        var matcher = repoRoot.getFileSystem().getPathMatcher("glob:" + pattern);
        var allBranches = listBranches();
        var matches = allBranches.stream().filter(m -> matcher.matches(Path.of(m.getName()))).collect(Collectors.toList());
        LOGGER.debug("Found {} branch(es) matching pattern '{}'", matches.size(), pattern);
        return matches;
    }

    /**
     * Resolves a branch identifier to a branch name. The identifier can be either an exact branch
     * name or a prefix of a branch's unique identifier (uid). If the identifier matches both a name
     * and one or more uids, the name takes precedence.
     *
     * @param nameOrUid A branch name or uid prefix.
     * @return The resolved branch name.
     * @throws BranchOperationException If the identifier matches no branch or is ambiguous (matches
     *                                  multiple branches by uid).
     */
    public String resolveBranchIdentifier(String nameOrUid) throws BranchOperationException {
        checkNotNull(nameOrUid, "Branch identifier must not be null");
        try (var git = Git.open(repoRoot.toFile())) {
            var repo = git.getRepository();
            // exact branch name match
            var exactRef = repo.findRef("refs/heads/" + nameOrUid);
            if (exactRef != null) {
                LOGGER.debug("Resolved identifier '{}' to branch name directly", nameOrUid);
                return nameOrUid;
            }
            // resolve via UID prefix using metadata files
            var metadataDir = repoRoot.resolve(METADATA_DIR);
            if (!Files.isDirectory(metadataDir)) {
                throw new BranchOperationException("No branch matches identifier: " + nameOrUid);
            }
            var matches = Files.list(metadataDir)
                    .filter(p -> p.toString().endsWith(".metadata"))
                    .map(p -> {
                        try {
                            return BranchMetadata.readFrom(p);
                        } catch (IOException e) {
                            LOGGER.warn("Failed to read metadata file: {}", p, e);
                            return null;
                        }
                    })
                    .filter(m -> m != null && m.getUid().startsWith(nameOrUid)).toList();
            if (matches.size() == 1) {
                LOGGER.debug("Resolved uid prefix '{}' to branch '{}'", nameOrUid, matches.get(0).getName());
                return matches.get(0).getName();
            } else if (matches.isEmpty()) {
                throw new BranchOperationException("No branch matches identifier: " + nameOrUid);
            } else {
                //todo: enable returning all branches that match the name pattern
                var ambiguous = matches.stream().map(BranchMetadata::getName).collect(Collectors.joining(", "));
                throw new BranchOperationException("Ambiguous identifier '" + nameOrUid + "' matches multiple branches: " + ambiguous);
            }

        } catch (IOException e) {
            throw new BranchOperationException("Failed to read metadata while resolving identifier '" + nameOrUid + "'", e);

        }
    }

    /**
     * Returns the parent-child topology of all managed branches. Each entry maps a parent branch
     * name to the list of branches that were directly forked from it. Only branches with persisted
     * {@link BranchMetadata} are included — branches created outside of the {@link BranchManager}
     * are not part of the topology.
     *
     * @return A map from parent branch name to its direct child branch names.
     * @throws BranchOperationException If the metadata files cannot be read.
     */
    public Map<String, List<String>> getBranchTopology() throws BranchOperationException {
        var metadataDir = repoRoot.resolve(METADATA_DIR);
        if (!Files.isDirectory(metadataDir)) {
            return new LinkedHashMap<>();
        }
        try {
            var topology = new LinkedHashMap<String, List<String>>();
            try (var stream = Files.list(metadataDir)) {
                var metadataFiles = stream.filter(p -> p.toString().endsWith(".metadata")).toList();
                for (var file : metadataFiles) {
                    var metadata = BranchMetadata.readFrom(file);
                    if (metadata.getState() == BranchState.DELETED) {
                        continue;
                    }
                    topology.computeIfAbsent(metadata.getParent(), k -> new ArrayList<>()).add(metadata.getName());
                }
            }
            LOGGER.debug("Built branch topology with {} parent(s)", topology.size());
            return topology;

        } catch (IOException e) {
            throw new BranchOperationException("Failed to read metadata while building topology", e);

        }
    }

    /**
     * Validates a proposed branch name. The name must not be empty, must not conflict with an
     * existing branch, and must conform to Git's branch naming rules (no spaces, no {@code ..}, no
     * trailing {@code .lock}, no special characters {@code ~^:?*[\\}).
     *
     * @param name The branch name to validate.
     * @throws BranchOperationException If the name is invalid or already in use.
     */
    public void validateBranchName(String name) throws BranchOperationException {
        checkNotNull(name, "Branch name must not be null");

        if (name.isBlank()) {
            throw new BranchOperationException("Branch name must not be blank");
        }
        if (name.contains("..")) {
            throw new BranchOperationException("Branch name must not contain '..': " + name);
        }
        if (name.endsWith(".lock")) {
            throw new BranchOperationException("Branch name must not end with '.lock': " + name);
        }
        if (name.chars().anyMatch(c -> " ~^:?*[\\".indexOf(c) >= 0)) {
            throw new BranchOperationException("Branch name contains illegal characters: " + name);
        }
        // Check for conflict with an existing branch
        try (var git = Git.open(repoRoot.toFile())) {
            var repo = git.getRepository();
            if (repo.findRef("refs/heads/" + name) != null) {
                throw new BranchOperationException("Branch already exists: " + name);
            }
        } catch (IOException e) {
            throw new BranchOperationException("Failed to open repository while validating branch name '" + name + "'", e);
        }
    }

    /**
     * Returns the current lifecycle state of the specified branch. If the branch has no persisted
     * metadata (e.g., it was created outside of the {@link BranchManager}), it is assumed to be
     * {@link BranchState#ACTIVE}.
     *
     * @param name The name of the branch.
     * @return The {@link BranchState} of the branch.
     * @throws BranchOperationException If the branch does not exist in Git or the metadata cannot be
     *                                  read.
     */
    public BranchState getBranchState(String name) throws BranchOperationException {
        checkNotNull(name, "Branch name must not be null");

        try (var git = Git.open(repoRoot.toFile())) {
            var repo = git.getRepository();
            if (repo.findRef("refs/heads/" + name) == null) {
                var metadataFile = metadataPath(name);
                if (Files.exists(metadataFile)) {
                    return BranchMetadata.readFrom(metadataFile).getState();
                }
                throw new BranchOperationException("Branch does not exist: " + name);
            }
            var metadataFile = metadataPath(name);
            if (Files.exists(metadataFile)) {
                return BranchMetadata.readFrom(metadataFile).getState();
            }
            LOGGER.debug("Branch '{}' has no metadata, defaulting to ACTIVE", name);
            return BranchState.ACTIVE;
        } catch (IOException e) {
            throw new BranchOperationException("Failed to read metadata for branch '" + name + "'", e);
        }
    }

    /**
     * Returns the path to the metadata file for the given branch name.
     *
     * @param branchName The branch name.
     * @return The path to the {@code .metadata} file.
     */
    private Path metadataPath(String branchName) {
        return repoRoot.resolve(METADATA_DIR).resolve(branchName + ".metadata");
    }
}