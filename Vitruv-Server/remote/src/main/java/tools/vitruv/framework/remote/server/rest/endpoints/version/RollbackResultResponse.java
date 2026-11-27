package tools.vitruv.framework.remote.server.rest.endpoints.version;

import tools.vitruv.framework.vsum.versioning.data.RollbackResult;

/**
 * API response DTO for a rollback result.
 *
 * <p>Converts {@link RollbackResult} into a Jackson-serializable record.
 */
public record RollbackResultResponse(
    String status,
    VersionResponse targetVersion,
    String newHeadSha,
    String message,
    boolean successful) {

  /** Creates a {@link RollbackResultResponse} from a {@link RollbackResult} domain object. */
  public static RollbackResultResponse from(RollbackResult result) {
    return new RollbackResultResponse(
        result.getStatus().name(),
        VersionResponse.from(result.getTargetVersion()),
        result.getNewHeadSha(),
        result.getMessage(),
        result.isSuccessful());
  }
}
