package tools.vitruv.framework.vsum.branch.data;

import java.util.List;

/**
 * Summary of a single Git commit on a branch, returned by
 * {@link tools.vitruv.framework.vsum.branch.CommitManager#listCommits(String)}.
 *
 * <p>{@code authorDate} is pre-formatted as an ISO-8601 string for direct use in REST responses.
 * {@code hasChangelog} indicates whether a semantic changelog JSON file was found for this commit.
 * {@code totalSemanticChanges} is 0 when no changelog exists.
 */
public record CommitSummary(
    String sha,
    String shortSha,
    String branch,
    String authorName,
    String authorEmail,
    String authorDate,
    String message,
    List<String> parentShas,
    boolean hasChangelog,
    int totalSemanticChanges
) {}
