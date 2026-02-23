package tools.vitruv.framework.vsum.branch.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import tools.vitruv.framework.vsum.branch.data.FileChange;
import tools.vitruv.framework.vsum.branch.data.SemanticChangelog;

import java.nio.file.Path;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Generates the semantic changelog entry for a Git commit after the commit has been finalized by Git.
 *
 * <p>Changelog generation was intentionally separated from {@link PreCommitHandler} because the pre-commit hook fires before the commit exists and therefore cannot know the real commit SHA that Git will assign.
 * Using the parent SHA at pre-commit time would produce changelogs whose SHA does not match any entry in {@code git log},
 * making the audit trail misleading and untraceable.
 *
 * <p>The post-commit hook fires after the commit is finalized.
 * At that point, {@code git rev-parse HEAD} returns the real SHA of the new commit, which is what this handler receives and embeds in the changelog.
 *
 * <p>todo: actual file changes will be populated once JGit diff integration is available. At that point, {@link #generateChangelog} will diff HEAD against HEAD^
 * and produce a {@link FileChange} entry for each added, modified, or deleted file.
 */
public class PostCommitHandler {

    private static final Logger LOGGER = LogManager.getLogger(PostCommitHandler.class);
    private final Path repositoryRoot;

    /**
     * Creates a post-commit handler.
     */
    public PostCommitHandler(Path repositoryRoot) {
        this.repositoryRoot = checkNotNull(repositoryRoot, "repository root must not be null");
    }

    /**
     * Generates a semantic changelog entry for the given commit.
     *
     * <p>The {@code commitSha} must be the real SHA assigned by Git to the new commit, read from {@code git rev-parse HEAD} in the post-commit hook.
     * This ensures the changelog file name ({@code .vitruvius/changelogs/<shortSha>.txt}) and the SHA embedded in the changelog content both match the actual commit in {@code git log}.
     *
     * <p>Returns a placeholder changelog because querying Git for the actual changed files via JGit diff is planned for a future iteration.
     * At that point, this method will diff HEAD against HEAD^ and produce a {@link FileChange} entry for each added, modified, or deleted file.
     *
     * @param commitSha the real Git commit SHA assigned to the new commit. Must not be null.
     * @param branch    the branch on which the commit was made. Must not be null.
     * @return a {@link SemanticChangelog} with an empty change list and a placeholder author.
     */
    public SemanticChangelog generateChangelog(String commitSha, String branch) {
        checkNotNull(commitSha, "commit SHA must not be null");
        checkNotNull(branch, "branch must not be null");

        // use the standard seven-character short SHA that matches the Git convention
        // and the format used in SemanticChangelog.toString().
        LOGGER.info("Generating changelog for commit {} on branch {}", commitSha.substring(0, Math.min(7, commitSha.length())), branch);

        try (Git git = Git.open(repositoryRoot.toFile());
             RevWalk revWalk = new RevWalk(git.getRepository())) {

            // Read the commit object from Git
            ObjectId commitId = git.getRepository().resolve(commitSha);
            RevCommit commit = revWalk.parseCommit(commitId);

            // Extract real commit info
            String author = commit.getAuthorIdent().getName();
            Instant commitTime = Instant.ofEpochSecond(commit.getCommitTime());
            LocalDateTime authorDate = LocalDateTime.ofInstant(commitTime, ZoneId.systemDefault());
            String message = commit.getFullMessage().trim();

            // File changes are still a placeholder (TODO: add JGit diff integration)
            List<FileChange> changes = new ArrayList<>();

            LOGGER.debug("Read commit info from Git: author={}, message={}", author, message);

            return SemanticChangelog.create(
                    commitSha, author, authorDate, message, branch, changes);

        } catch (Exception e) {
            LOGGER.error("Failed to read commit info from Git for SHA {}, using fallback", commitSha, e);

            // Fallback to placeholders if Git read fails
            return SemanticChangelog.create(
                    commitSha, "unknown", LocalDateTime.now(),
                    "Commit on " + branch, branch, new ArrayList<>());
        }
    }
}