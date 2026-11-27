package tools.vitruv.framework.remote.server.rest.endpoints.commit;

import java.time.format.DateTimeFormatter;
import tools.vitruv.framework.vsum.branch.data.CommitResult;

/**
 * API response DTO for a completed commit operation.
 *
 * <p>Converts {@link CommitResult} into a Jackson-serializable record by formatting
 * the {@code LocalDateTime} author date as an ISO string.
 */
public record CommitResponse(
    String commitSha,
    String branch,
    String authorName,
    String authorEmail,
    String authorDate,
    java.util.List<String> stagedFiles,
    boolean hasModelChanges) {

  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

  /** Creates a {@link CommitResponse} from a {@link CommitResult} domain object. */
  public static CommitResponse from(CommitResult result) {
    return new CommitResponse(
        result.getCommitSha(),
        result.getBranch(),
        result.getAuthorName(),
        result.getAuthorEmail(),
        result.getAuthorDate().format(FORMATTER),
        result.getStagedFiles(),
        result.isHasModelChanges());
  }
}
