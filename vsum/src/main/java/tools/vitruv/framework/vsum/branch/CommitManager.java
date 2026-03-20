package tools.vitruv.framework.vsum.branch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import tools.vitruv.change.atomic.EChange;
import tools.vitruv.change.atomic.uuid.UuidResolver;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import tools.vitruv.framework.vsum.branch.data.BranchMetadata;
import tools.vitruv.framework.vsum.branch.data.CommitOptions;
import tools.vitruv.framework.vsum.branch.data.CommitResult;
import tools.vitruv.framework.vsum.branch.exception.BranchOperationException;
import tools.vitruv.framework.vsum.branch.storage.SemanticChangeBuffer;
import tools.vitruv.framework.vsum.branch.storage.SemanticChangelogManager;
import tools.vitruv.framework.vsum.branch.util.PostCommitTriggerFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Manages Git commit operations for Vitruvius model files.
 *
 * <p>Responsibilities:
 * <ul>
 *   <li>Auto-stage modified model files (.xmi, .model, .model2, .ecore, .uml, .genmodel)</li>
 *   <li>Stage branch metadata (lastModified update) as part of the same commit</li>
 *   <li>Create the Git commit via JGit using author info from Git config</li>
 *   <li>Write the post-commit trigger file so the existing VsumPostCommitWatcher generates the changelog asynchronously</li>
 * </ul>
 */
public class CommitManager {

    private static final Logger LOGGER = LogManager.getLogger(CommitManager.class);

    private static final String METADATA_DIR = ".vitruvius/branches";

    /**
     * Model file extensions that are auto-staged on every commit.
     * Matches the filter used in the post-commit hook script.
     */
    private static final Set<String> MODEL_EXTENSIONS = Set.of(".xmi", ".ecore", ".uml", ".genmodel");
    private static final java.util.regex.Pattern MODEL_PATTERN = java.util.regex.Pattern.compile(".*\\.model\\d*$", java.util.regex.Pattern.CASE_INSENSITIVE);
    private final Path repoRoot;
    private final PostCommitTriggerFile triggerFile;
    private final SemanticChangelogManager changelogManager;

    /**
     * Optional: buffer, resolver, and resource supplier provided by {@link BranchAwareVirtualModel}
     * so that semantic changes can be written to the changelog at commit time.
     * All three are null when CommitManager is used without a live VirtualModel (Git-only scenarios).
     */
    private SemanticChangeBuffer changeBuffer;
    private UuidResolver uuidResolver;
    /**
     * Supplies the currently loaded EMF Resources at commit time so XMI snapshots can be written.
     * Called lazily inside {@link #writeSemanticChangelog} to capture the post-commit resource state.
     */
    private Supplier<Collection<Resource>> resourceSupplier;

    /**
     * Creates a new CommitManager for the Git repository at the given path.
     * @param repoRoot the root directory of the Git repository.
     * @throws IllegalArgumentException if the path is not a valid Git repository.
     */
    public CommitManager(Path repoRoot) {
        this.repoRoot = checkNotNull(repoRoot, "repository root must not be null");
        checkArgument(Files.isDirectory(repoRoot.resolve(".git")), "No Git repository found at: %s", repoRoot);
        this.triggerFile = new PostCommitTriggerFile(repoRoot);
        this.changelogManager = new SemanticChangelogManager(repoRoot);
    }

    /**
     * Attaches semantic change tracking so that EChanges accumulated since the last commit are
     * written to JSON and XMI changelog files as part of each {@link #commit} call.
     *
     * @param changeBuffer     the buffer to drain at commit time, must not be null.
     * @param uuidResolver     the resolver used to convert EObjects to stable UUIDs for JSON output, must not be null.
     * @param resourceSupplier supplier that returns the currently loaded EMF Resources at commit time
     *                         used to write XMI delta snapshots. Must not be null.
     */
    public void attachSemanticChangeTracking(SemanticChangeBuffer changeBuffer, UuidResolver uuidResolver, Supplier<Collection<Resource>> resourceSupplier) {
        this.changeBuffer = checkNotNull(changeBuffer, "changeBuffer must not be null");
        this.uuidResolver = checkNotNull(uuidResolver, "uuidResolver must not be null");
        this.resourceSupplier = checkNotNull(resourceSupplier, "resourceSupplier must not be null");
        LOGGER.info("Semantic change tracking attached to CommitManager");
    }

    /**
     * Commits all modified model files with the given message.
     * Branch metadata (lastModified) is also staged automatically.
     * @param message the commit message, must not be null or blank.
     * @return the {@link CommitResult} containing the commit SHA and staged files.
     * @throws BranchOperationException if staging or committing fails.
     */
    public CommitResult commit(String message) throws BranchOperationException {
        return commit(message, CommitOptions.builder().build());
    }

    /**
     * Commits all modified model files with the given message and additional options.
     * Additional files can be staged and specific model files can be excluded via {@link CommitOptions}.
     * @param message the commit message, must not be null or blank.
     * @param options staging options for additional or excluded files.
     * @return the {@link CommitResult} containing the commit SHA and staged files.
     * @throws BranchOperationException if staging or committing fails.
     */
    public CommitResult commit(String message, CommitOptions options) throws BranchOperationException {
        checkNotNull(message, "commit message must not be null");
        checkArgument(!message.isBlank(), "commit message must not be blank");
        checkNotNull(options, "commit options must not be null");

        try (Git git = Git.open(repoRoot.toFile())) {
            Repository repo = git.getRepository();
            // Resolve current branch
            String branch = repo.getBranch();
            LOGGER.info("Committing on branch '{}': {}", branch, message);
            // Stage model files
            List<String> stagedFiles = stageModelFiles(git, options);
            // Stage branch metadata (lastModified update)
            stageBranchMetadata(git, branch, stagedFiles);
            // Nothing to commit. no staged files
            Status status = git.status().call();
            if (status.getAdded().isEmpty() && status.getChanged().isEmpty() && status.getRemoved().isEmpty()) {
                throw new BranchOperationException("Nothing to commit, no modified model files found");
            }

            // Create commit using author from Git config (same as regular git commit)
            RevCommit revCommit = git.commit().setMessage(message).call();
            String commitSha = revCommit.getName();
            PersonIdent author = revCommit.getAuthorIdent();
            LocalDateTime authorDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(author.getWhen().getTime()), ZoneId.systemDefault());
            LOGGER.info("Commit created: {} on branch '{}'", commitSha.substring(0, 7), branch);
            boolean hasModelChanges = stagedFiles.stream().anyMatch(this::isModelFile);
            CommitResult result = new CommitResult(commitSha, branch, author.getName(), author.getEmailAddress(), authorDate, stagedFiles, hasModelChanges);

            // Write semantic changelog if change tracking is attached and model files were changed.
            if (hasModelChanges && changeBuffer != null && changeBuffer.hasChanges()) {
                writeSemanticChangelog(git, commitSha, branch, author.getName(), authorDate, revCommit);
            }

            // Write post-commit trigger so VsumPostCommitWatcher generates the (text-based) changelog.
            // Only trigger if model files were changed, mirrors the hook behavior.
            if (hasModelChanges) {
                try {
                    triggerFile.createTrigger(commitSha, branch);
                    LOGGER.debug("Post-commit trigger written for changelog generation");
                } catch (IOException e) {
                    // changelog generation failing should not undo the commit
                    LOGGER.warn("Failed to write post-commit trigger (non-critical): {}", e.getMessage());
                }
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
     * @return list of relative paths of all staged files.
     */
    private List<String> stageModelFiles(Git git, CommitOptions options) throws GitAPIException, IOException {
        List<String> staged = new ArrayList<>();
        // Resolve excluded paths to relative strings for comparison
        List<String> excluded = options.getExcludeFiles().stream().map(p -> repoRoot.relativize(p).toString().replace('\\', '/')).toList();
        // Check working directory status for modified/untracked files
        Status status = git.status().call();
        // Collect all modified, untracked, and missing files
        Set<String> candidates = new java.util.HashSet<>();
        candidates.addAll(status.getModified());
        candidates.addAll(status.getUntracked());
        candidates.addAll(status.getMissing());
        candidates.addAll(status.getConflicting());
        for (String relativePath : candidates) {
            if (excluded.contains(relativePath)) {
                LOGGER.debug("Skipping excluded file: {}", relativePath);
                continue;
            }
            if (isModelFile(relativePath)) {
                git.add().addFilepattern(relativePath).call();
                staged.add(relativePath);
                LOGGER.debug("Staged model file: {}", relativePath);
            }
        }
        // Stage additional files specified by caller
        for (Path additionalFile : options.getAdditionalFiles()) {
            String relativePath = repoRoot.relativize(additionalFile).toString().replace('\\', '/');
            if (!staged.contains(relativePath)) {
                git.add().addFilepattern(relativePath).call();
                staged.add(relativePath);
                LOGGER.debug("Staged additional file: {}", relativePath);
            }
        }
        LOGGER.info("Staged {} file(s) for commit", staged.size());
        return staged;
    }

    /**
     * Updates the branch metadata lastModified timestamp and stages the metadata file
     * so it is included in the same commit without requiring a separate manual commit.
     */
    private void stageBranchMetadata(Git git, String branch, List<String> staged)
            throws IOException, GitAPIException {
        Path metadataFile = repoRoot.resolve(METADATA_DIR).resolve(branch + ".metadata");

        if (!Files.exists(metadataFile)) {
            LOGGER.debug("No metadata file for branch '{}', skipping metadata staging", branch);
            return;
        }
        try {
            BranchMetadata metadata = BranchMetadata.readFrom(metadataFile);
            metadata.updateLastModified();
            metadata.writeTo(metadataFile);

            String relativePath = repoRoot.relativize(metadataFile).toString().replace('\\', '/');
            git.add().addFilepattern(relativePath).call();
            staged.add(relativePath);
            LOGGER.debug("Staged branch metadata with updated lastModified: {}", relativePath);
        } catch (Exception e) {
            // metadata update failing should not block the commit
            LOGGER.warn("Failed to update branch metadata lastModified (non-critical): {}", e.getMessage());
        }
    }

    /**
     * Drains the change buffer, writes the JSON changelog and XMI delta snapshots, and stages
     * all produced files so they are tracked by Git.
     * Failures are logged as warnings and do not roll back the commit.
     */
    private void writeSemanticChangelog(Git git, String commitSha, String branch, String authorName, LocalDateTime authorDate, RevCommit revCommit) {
        try {
            java.util.Map<String, java.util.List<EChange<EObject>>> changesByResource = changeBuffer.drainChanges();

            if (changesByResource.isEmpty()) {
                LOGGER.debug("No semantic changes to write for commit {}", commitSha.substring(0, 7));
                return;
            }

            // Collect parent SHAs for three-way merge support
            java.util.List<String> parentShas = new java.util.ArrayList<>();
            for (RevCommit parent : revCommit.getParents()) {
                parentShas.add(parent.getName());
            }

            // Resolve active resources for XMI snapshot writing
            Collection<Resource> activeResources = resourceSupplier != null ? resourceSupplier.get() : java.util.Collections.emptyList();

            java.util.List<java.nio.file.Path> writtenFiles = changelogManager.write(
                    commitSha, branch, authorName, authorDate,
                    revCommit.getFullMessage().trim(),
                    parentShas, changesByResource, activeResources, uuidResolver);

            // Stage all written changelog files (JSON + XMI) so they are tracked by Git
            for (java.nio.file.Path file : writtenFiles) {
                String relativePath = repoRoot.relativize(file).toString().replace('\\', '/');
                git.add().addFilepattern(relativePath).call();
                LOGGER.debug("Staged changelog file: {}", file.getFileName());
            }
            LOGGER.info("Staged {} changelog file(s) for commit {}", writtenFiles.size(), commitSha.substring(0, 7));

        } catch (Exception e) {
            LOGGER.warn("Failed to write semantic changelog for commit {} (non-critical): {}", commitSha.substring(0, 7), e.getMessage());
        }
    }

    /**
     * Returns true if the given relative file path has a model file extension.
     */
    private boolean isModelFile(String relativePath) {
        String lower = relativePath.toLowerCase();
        if (MODEL_EXTENSIONS.stream().anyMatch(lower::endsWith)) {
            return true;
        }
        return MODEL_PATTERN.matcher(lower).matches();
    }
}