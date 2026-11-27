package tools.vitruv.framework.remote.server.rest.endpoints.version;

import java.util.List;
import tools.vitruv.framework.vsum.versioning.data.RollbackPreview;

/**
 * API response DTO for a rollback preview.
 *
 * <p>Converts {@link RollbackPreview} into a Jackson-serializable record.
 */
public record RollbackPreviewResponse(
    VersionResponse targetVersion,
    String currentHeadSha,
    String branch,
    List<String> commitsToAbandon,
    List<String> filesToChange,
    boolean hasUncommittedChanges) {

  /** Creates a {@link RollbackPreviewResponse} from a {@link RollbackPreview} domain object. */
  public static RollbackPreviewResponse from(RollbackPreview preview) {
    return new RollbackPreviewResponse(
        VersionResponse.from(preview.getTargetVersion()),
        preview.getCurrentHeadSha(),
        preview.getBranch(),
        preview.getCommitsToAbandon(),
        preview.getFilesToChange(),
        preview.isHasUncommittedChanges());
  }
}
