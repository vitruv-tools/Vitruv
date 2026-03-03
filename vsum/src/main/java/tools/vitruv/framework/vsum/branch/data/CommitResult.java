package tools.vitruv.framework.vsum.branch.data;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Holds the result of a commit operation performed by {@link tools.vitruv.framework.vsum.branch.CommitManager}.
 * Contains enough information for the post-commit changelog watcher to generate the changelog entry.
 */
@Getter
public class CommitResult {

    /** The full Git commit SHA assigned by Git to the new commit. */
    private final String commitSha;

    /** The branch on which the commit was made. */
    private final String branch;

    /** The author name read from Git config. */
    private final String authorName;

    /** The author email read from Git config. */
    private final String authorEmail;

    /** The timestamp of the commit. */
    private final LocalDateTime authorDate;

    /** Relative paths of all files that were staged and included in the commit. */
    private final List<String> stagedFiles;

    /** Whether any model files were included — determines if changelog should be generated. */
    private final boolean hasModelChanges;

    public CommitResult(String commitSha, String branch, String authorName, String authorEmail, LocalDateTime authorDate, List<String> stagedFiles, boolean hasModelChanges) {
        this.commitSha = Objects.requireNonNull(commitSha);
        this.branch = Objects.requireNonNull(branch);
        this.authorName = Objects.requireNonNull(authorName);
        this.authorEmail = Objects.requireNonNull(authorEmail);
        this.authorDate = Objects.requireNonNull(authorDate);
        this.stagedFiles = List.copyOf(stagedFiles);
        this.hasModelChanges = hasModelChanges;
    }
}