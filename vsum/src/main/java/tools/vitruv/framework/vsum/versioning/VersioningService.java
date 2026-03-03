package tools.vitruv.framework.vsum.versioning;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.util.io.DisabledOutputStream;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;
import tools.vitruv.framework.vsum.versioning.data.RollbackPreview;
import tools.vitruv.framework.vsum.versioning.data.RollbackResult;
import tools.vitruv.framework.vsum.versioning.data.VersionMetadata;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Manages model versioning for Vitruvius using Git annotated tags.
 *
 * <p>Responsibilities:
 * <ul>
 *   <li>Create annotated Git tags to mark model states as versions (DE-5)</li>
 *   <li>Persist version metadata under {@code .vitruvius/versions/} (DE-6)</li>
 *   <li>Preview rollback before execution showing commits lost and files changed (DE-8)</li>
 *   <li>Execute confirmed rollback via {@code git reset --hard} and reload VSUM (DE-8)</li>
 * </ul>
 */
public class VersioningService {

    private static final Logger LOGGER = LogManager.getLogger(VersioningService.class);
    private static final String VERSIONS_DIR = ".vitruvius/versions";
    private static final String TAG_PREFIX = "refs/tags/";

    private final Path repoRoot;
    private final InternalVirtualModel virtualModel;

    /**
     * Creates a new VersioningService for the given repository and virtual model.
     *
     * @param repoRoot the root directory of the Git repository.
     * @param virtualModel the virtual model to reload after rollback.
     */
    public VersioningService(Path repoRoot, InternalVirtualModel virtualModel) {
        this.repoRoot = checkNotNull(repoRoot, "repository root must not be null");
        this.virtualModel = checkNotNull(virtualModel, "virtual model must not be null");
        checkArgument(Files.isDirectory(repoRoot.resolve(".git")), "No Git repository found at: %s", repoRoot);
    }

    /**
     * Creates an annotated Git tag for the current HEAD commit, marking it as a
     * named version. Version metadata is also persisted under
     * {@code .vitruvius/versions/<versionId>.metadata}.
     * @param versionId the version identifier (e.g. "v1.0"). Must be a valid Git tag name.
     * @param description optional human-readable description of this version. May be null.
     * @return the {@link VersionMetadata} for the newly created version.
     * @throws VersioningException if the tag already exists or the Git operation fails.
     */
    public VersionMetadata createVersion(String versionId, String description)
            throws VersioningException {
        checkNotNull(versionId, "version ID must not be null");
        checkArgument(!versionId.isBlank(), "version ID must not be blank");
        try (Git git = Git.open(repoRoot.toFile())) {
            Repository repo = git.getRepository();

            // Check tag does not already exist
            if (repo.findRef(TAG_PREFIX + versionId) != null) {
                throw new VersioningException("Version already exists: " + versionId);
            }
            // Read current branch and HEAD commit
            String branch = repo.getBranch();
            ObjectId headId = repo.resolve("HEAD");
            if (headId == null) {
                throw new VersioningException("No commits found - cannot create version on empty repository");
            }
            String commitSha = headId.getName();
            // Read author info from Git config - same as regular git tag behavior
            PersonIdent tagger = new PersonIdent(repo);
            // Create annotated tag - stores tagger, date, message in Git object
            String tagMessage = description != null && !description.isBlank() ? description : "Version " + versionId;
            git.tag().setName(versionId).setMessage(tagMessage).setAnnotated(true).call();
            LOGGER.info("Created annotated tag '{}' at commit {} on branch '{}'", versionId, commitSha.substring(0, 7), branch);
            // Build and persist version metadata
            LocalDateTime createdAt = LocalDateTime.ofInstant(tagger.getWhen().toInstant(), ZoneId.systemDefault());
            VersionMetadata metadata = new VersionMetadata(versionId, commitSha, branch, tagger.getName(), tagger.getEmailAddress(), createdAt, description != null ? description : "");
            metadata.writeTo(versionMetadataPath(versionId));
            LOGGER.info("Version metadata written for '{}'", versionId);
            return metadata;
        } catch (GitAPIException e) {
            throw new VersioningException("Failed to create version tag '" + versionId + "': " + e.getMessage(), e);
        } catch (IOException e) {
            throw new VersioningException("Failed to open repository: " + e.getMessage(), e);
        }
    }

    /**
     * Returns all versions defined on the repository, sorted by creation time
     * <p>REST API: GET /branches/{id}/versions
     * @return list of {@link VersionMetadata}, newest first. Empty if no versions exist.
     * @throws VersioningException if metadata files cannot be read.
     */
    public List<VersionMetadata> listVersions() throws VersioningException {
        Path versionsDir = repoRoot.resolve(VERSIONS_DIR);
        if (!Files.isDirectory(versionsDir)) {
            return List.of();
        }
        try (Stream<Path> files = Files.list(versionsDir)) {
            List<VersionMetadata> versions = new ArrayList<>();
            for (Path file : files.filter(p -> p.toString().endsWith(".metadata")).toList()) {
                try {
                    versions.add(VersionMetadata.readFrom(file));
                } catch (Exception e) {
                    LOGGER.warn("Failed to read version metadata from {}: {}", file, e.getMessage());
                }
            }
            // Sort newest first by createdAt
            versions.sort((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()));
            LOGGER.debug("Listed {} version(s)", versions.size());
            return versions;
        } catch (IOException e) {
            throw new VersioningException("Failed to list versions: " + e.getMessage(), e);
        }
    }

    /**
     * Returns the metadata for a specific version by its ID.
     * @param versionId the version identifier to look up.
     * @return the {@link VersionMetadata} for the version.
     * @throws VersioningException if the version does not exist or cannot be read.
     */
    public VersionMetadata getVersion(String versionId) throws VersioningException {
        checkNotNull(versionId, "version ID must not be null");
        Path metadataFile = versionMetadataPath(versionId);
        if (!Files.exists(metadataFile)) {
            throw new VersioningException("Version not found: " + versionId);
        }
        try {
            return VersionMetadata.readFrom(metadataFile);
        } catch (IOException e) {
            throw new VersioningException("Failed to read version metadata for '" + versionId + "': " + e.getMessage(), e);
        }
    }

    /**
     * Previews a rollback to the given version without executing it.
     * Shows commits that will be abandoned, files that will change, and whether
     * uncommitted changes are present. The developer must call
     * {@link #confirmRollback(RollbackPreview)} to execute.
     * <p>REST API: first step of POST /branches/{id}/rollback (DE-8)
     * @param versionId the version to roll back to.
     * @return a {@link RollbackPreview} describing the impact.
     * @throws VersioningException if the version does not exist or Git info cannot be read.
     */
    public RollbackPreview previewRollback(String versionId) throws VersioningException {
        checkNotNull(versionId, "version ID must not be null");
        VersionMetadata targetVersion = getVersion(versionId);
        try (Git git = Git.open(repoRoot.toFile());
             RevWalk revWalk = new RevWalk(git.getRepository())) {
            Repository repo = git.getRepository();
            String branch = repo.getBranch();
            // Resolve current HEAD
            ObjectId headId = repo.resolve("HEAD");
            String currentHeadSha = headId != null ? headId.getName() : "";
            // Resolve target commit from tag
            ObjectId targetId = repo.resolve(versionId + "^{commit}");
            if (targetId == null) {
                throw new VersioningException("Cannot resolve commit for version '" + versionId + "'");
            }
            // Collect commits between target and HEAD that will be abandoned
            List<String> commitsToAbandon = new ArrayList<>();
            RevCommit targetCommit = revWalk.parseCommit(targetId);
            revWalk.markStart(revWalk.parseCommit(headId));
            revWalk.markUninteresting(targetCommit);
            for (RevCommit commit : revWalk) {
                commitsToAbandon.add(commit.getName().substring(0, 7) + " " + commit.getShortMessage());
            }
            // Collect files that will change via git diff
            List<String> filesToChange = computeChangedFiles(git, repo, targetId, headId);

            // Check for uncommitted changes, only consider modified/deleted tracked files, not untracked:
            var status = git.status().call();
            boolean hasUncommittedChanges = !status.getModified().isEmpty()
                    || !status.getChanged().isEmpty()
                    || !status.getRemoved().isEmpty()
                    || !status.getMissing().isEmpty()
                    || !status.getConflicting().isEmpty();
            LOGGER.info("Rollback preview for version '{}': {} commit(s) to abandon, " + "{} file(s) to change, uncommittedChanges={}",
                    versionId, commitsToAbandon.size(), filesToChange.size(), hasUncommittedChanges);
            return new RollbackPreview(targetVersion, currentHeadSha, branch, commitsToAbandon, filesToChange, hasUncommittedChanges);
        } catch (GitAPIException | IOException e) {
            throw new VersioningException("Failed to preview rollback to '" + versionId + "': " + e.getMessage(), e);
        }
    }

    /**
     * Executes the rollback described by the given preview. Resets the working
     * directory to the target version commit via {@code git reset --hard} and
     * reloads the VSUM to reflect the restored model state.
     *
     * <p>This operation is irreversible. Uncommitted changes and commits after
     * the target version are permanently lost.
     * <p>REST API: second step of POST /branches/{id}/rollback (DE-8)
     * @param preview the rollback preview returned by {@link #previewRollback}.
     * @return a {@link RollbackResult} describing the outcome.
     * @throws VersioningException if the Git reset fails.
     */
    public RollbackResult confirmRollback(RollbackPreview preview) throws VersioningException {
        checkNotNull(preview, "rollback preview must not be null");
        VersionMetadata targetVersion = preview.getTargetVersion();
        LOGGER.info("Confirming rollback to version '{}' at commit {}", targetVersion.getVersionId(), targetVersion.getCommitSha().substring(0, 7));
        try (Git git = Git.open(repoRoot.toFile())) {
            Repository repo = git.getRepository();
            // Resolve target commit from tag
            ObjectId targetId = repo.resolve(targetVersion.getVersionId() + "^{commit}");
            if (targetId == null) {
                return RollbackResult.failed(targetVersion, "Cannot resolve commit for version '" + targetVersion.getVersionId() + "'");
            }

            // Execute git reset --hard <targetCommit>
            git.reset().setMode(org.eclipse.jgit.api.ResetCommand.ResetType.HARD).setRef(targetId.getName()).call();
            String newHeadSha = repo.resolve("HEAD").getName();
            LOGGER.info("Reset complete, new HEAD: {}", newHeadSha.substring(0, 7));
            // Reload VSUM to reflect restored model state
            // Same path as post-checkout reload - discards in-memory state and re-reads disk
            try {
                virtualModel.reload();
                LOGGER.info("VSUM reloaded successfully after rollback");
                return RollbackResult.success(targetVersion, newHeadSha);
            } catch (Exception e) {
                LOGGER.error("VSUM reload failed after rollback: {}", e.getMessage());
                return RollbackResult.successReloadFailed(targetVersion, newHeadSha, e.getMessage());
            }

        } catch (GitAPIException e) {
            throw new VersioningException("Git reset failed during rollback to '" + targetVersion.getVersionId() + "': " + e.getMessage(), e);
        } catch (IOException e) {
            throw new VersioningException("Failed to open repository during rollback: " + e.getMessage(), e);
        }
    }

    /**
     * Computes the list of files that differ between the target commit and HEAD.
     * Used in rollback preview to show the developer which files will change.
     */
    private List<String> computeChangedFiles(Git git, Repository repo, ObjectId targetId, ObjectId headId) {
        List<String> changed = new ArrayList<>();
        try (DiffFormatter formatter = new DiffFormatter(DisabledOutputStream.INSTANCE)) {
            formatter.setRepository(repo);
            formatter.setDetectRenames(true);
            try (RevWalk rw = new RevWalk(repo)) {
                RevCommit targetCommit = rw.parseCommit(targetId);
                RevCommit headCommit = rw.parseCommit(headId);
                List<DiffEntry> diffs = formatter.scan(targetCommit.getTree(), headCommit.getTree());
                for (DiffEntry diff : diffs) {changed.add(diff.getChangeType() + ": " + diff.getNewPath());
                }
            }
        } catch (IOException e) {
            LOGGER.warn("Failed to compute changed files for rollback preview: {}", e.getMessage());
        }
        return changed;
    }

    /**
     * Returns the path to the metadata file for the given version ID.
     */
    private Path versionMetadataPath(String versionId) {
        return repoRoot.resolve(VERSIONS_DIR).resolve(versionId + ".metadata");
    }
}