package tools.vitruv.framework.remote.server.rest.endpoints.version;

import com.fasterxml.jackson.core.JsonProcessingException;
import tools.vitruv.framework.remote.common.json.JsonMapper;
import tools.vitruv.framework.remote.common.rest.constants.ContentType;
import tools.vitruv.framework.remote.server.exception.ServerHaltingException;
import tools.vitruv.framework.remote.server.http.HttpWrapper;
import tools.vitruv.framework.remote.server.rest.PostEndpoint;
import tools.vitruv.framework.vsum.versioning.VersioningException;
import tools.vitruv.framework.vsum.versioning.VersioningService;
import tools.vitruv.framework.vsum.versioning.data.RollbackPreview;

/**
 * {@code POST /vsum/version/rollback/confirm}
 *
 * <p>Executes a rollback to the given version. Resets the working directory via
 * {@code git reset --hard} and reloads the V-SUM. This operation is irreversible.
 * Uncommitted changes and commits after the target version are permanently lost.
 *
 * <p>This is step 2 of a two-step rollback. Call
 * {@code POST /vsum/version/rollback/preview} first to review the impact.
 * The version ID is extracted from path segment 0 after {@code /vsum/version/}.
 *
 * <p>Example request:
 * <pre>
 *   POST /vsum/version/v1.0/rollback/confirm
 * </pre>
 *
 * <p>Returns {@code 405} if the version does not exist.
 */
public class RollbackConfirmEndpoint implements PostEndpoint {

  private final VersioningService versioningService;
  private final JsonMapper mapper;

  /**
   * Creates a new {@link RollbackConfirmEndpoint}.
   *
   * @param versioningService the versioning service used to execute the rollback.
   * @param mapper the JSON mapper used to serialize the response.
   */
  public RollbackConfirmEndpoint(VersioningService versioningService, JsonMapper mapper) {
    this.versioningService = versioningService;
    this.mapper = mapper;
  }

  @Override
  public String process(HttpWrapper wrapper) throws ServerHaltingException {
    String versionId = wrapper.getPathSegment(0);
    if (versionId == null || versionId.isBlank()) {
      throw badRequest("Missing version ID in path");
    }
    try {
      RollbackPreview preview = versioningService.previewRollback(versionId);
      RollbackResultResponse response = RollbackResultResponse.from(
          versioningService.confirmRollback(preview));
      wrapper.setContentType(ContentType.APPLICATION_JSON);
      return mapper.serialize(response);
    } catch (VersioningException e) {
      throw notFound(e.getMessage());
    } catch (JsonProcessingException e) {
      throw internalServerError(e.getMessage());
    }
  }
}
