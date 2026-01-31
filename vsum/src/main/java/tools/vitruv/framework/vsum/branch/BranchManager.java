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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

/**
 * Manages Git-based branches for the Vitruvius. All branch operations such as creation, switching, deletion,
 * and querying will be delegated to Git via JGit. Branch metadata (lifecycle, state, parent, timestamps) is
 * persisted in {@code .vitruvius/branches/} alongside the repository.
 *
 * <p>This class is stateless: every operation reads directly from the Git repository and the
 * metadata files on disk, so the instance is always consistent with the actual repository state.
 */
public class BranchManager {
    private static final Logger LOGGER = LogManager.getLogger(BranchManager.class);
    private static final String METADATA_DIR = ".vitruvius/branches";
    private final Path repoRoot;

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
     * @throws BranchOperationException If the branch already exists, the source branch does not
     *                                  exist, or the Git operation fails.
     */
    public BranchMetadata createBranch(String name, String fromBranch) throws BranchOperationException {
        checkNotNull(name, "Branch name must not be null");
        checkNotNull(fromBranch, "Source branch must not be null");
        validateBranchName(name);

        // handling .git directory
        try (var git = Git.open(repoRoot.toFile())) {
            // resolve the source branch
            var repo = git.getRepository();
            var sourceRef = repo.findRef("refs/heads/" + fromBranch);
            if (sourceRef == null) {
                throw new BranchOperationException("Source branch does not exist: " + fromBranch);
            }

            // create a new branch that points to the same commit as the source branch
            // equivalent to git branch <name> <fromBranch> or git branch <name> <commit-hash>
            // todo: DOKU sourceRef (main, HEAD,..) getObjectId() returns commit id ref points to, getName returns commit hash as string
            git.branchCreate().setName(name).setStartPoint(sourceRef.getObjectId().getName()).call();

            // resolve the new branch's commit SHA for the uid
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
            throw new BranchOperationException(
                    "Failed to write metadata for branch '" + name + "'", e);
        }
    }

    /**
     * Switches the working directory to the specified branch. The branch can be identified either by
     * its name or by a prefix of its unique identifier (uid).
     *
     * @param nameOrUid The branch name or a uid prefix to switch to.
     * @throws BranchOperationException If the branch cannot be found or the checkout fails.
     */
    public void switchBranch(String nameOrUid) throws BranchOperationException {
        checkNotNull(nameOrUid, "Branch identifier must not be null");

        var resolvedName = resolveBranchIdentifier(nameOrUid);

        try (var git = Git.open(repoRoot.toFile())) {
            git.checkout().setName(resolvedName).call();
            LOGGER.info("Switched to branch '{}'", resolvedName);

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
     *     Git operation fails.
     */
    public void deleteBranch(String name) throws BranchOperationException {
        checkNotNull(name, "Branch name must not be null");

        try (var git = Git.open(repoRoot.toFile())) {
            // prevent deletion of the currently checked-out branch
            var repo = git.getRepository();
            var head = repo.findRef("HEAD");
            if (head != null && head.getTarget() != null && head.getTarget().getName().equals("refs/heads/" + name)) {
                throw new BranchOperationException("Cannot delete the currently checked-out branch: " + name);
            }

            // delete the branch
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

    private String resolveBranchIdentifier(String nameOrUid) {
        return  nameOrUid + METADATA_DIR;
    }

    private Path metadataPath(String name) {
        return null;
    }

    private void validateBranchName(String name) {
    }
}