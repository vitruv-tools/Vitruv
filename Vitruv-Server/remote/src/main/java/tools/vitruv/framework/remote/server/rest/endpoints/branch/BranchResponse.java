package tools.vitruv.framework.remote.server.rest.endpoints.branch;

import java.time.format.DateTimeFormatter;
import tools.vitruv.framework.vsum.branch.data.BranchMetadata;

/**
 * API response DTO for a single branch.
 *
 * <p>Converts {@link BranchMetadata} into a JSON-serializable record by formatting
 * {@code LocalDateTime} fields as ISO strings.
 */
public record BranchResponse(
    String name,
    String state,
    String maturity,
    String parentBranch,
    String createdAt,
    String lastModified) {

  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

  /** Creates a {@link BranchResponse} from a {@link BranchMetadata} domain object. */
  public static BranchResponse from(BranchMetadata metadata) {
    return new BranchResponse(
        metadata.getName(),
        metadata.getState().name(),
        metadata.getMaturity().name(),
        metadata.getParent(),
        metadata.getCreatedAt().format(FORMATTER),
        metadata.getLastModified().format(FORMATTER));
  }
}
