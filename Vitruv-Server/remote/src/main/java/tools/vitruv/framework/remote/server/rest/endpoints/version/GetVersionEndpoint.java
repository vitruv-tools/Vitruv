package tools.vitruv.framework.remote.server.rest.endpoints.version;

import com.fasterxml.jackson.core.JsonProcessingException;
import tools.vitruv.framework.remote.common.json.JsonMapper;
import tools.vitruv.framework.remote.common.rest.constants.ContentType;
import tools.vitruv.framework.remote.server.exception.ServerHaltingException;
import tools.vitruv.framework.remote.server.http.HttpWrapper;
import tools.vitruv.framework.remote.server.rest.GetEndpoint;
import tools.vitruv.framework.vsum.versioning.VersioningException;
import tools.vitruv.framework.vsum.versioning.VersioningService;

/**
 * {@code GET /vsum/version/{versionId}}
 *
 * <p>Returns the metadata for a single version. The version ID is extracted from path
 * segment 0 after {@code /vsum/version/}.
 *
 * <p>Example request:
 * <pre>
 *   GET /vsum/version/v1.0
 * </pre>
 *
 * <p>Returns {@code 405} if the version does not exist.
 */
public class GetVersionEndpoint implements GetEndpoint {

  private final VersioningService versioningService;
  private final JsonMapper mapper;

  /**
   * Creates a new {@link GetVersionEndpoint}.
   *
   * @param versioningService the versioning service used to retrieve the version.
   * @param mapper the JSON mapper used to serialize the response.
   */
  public GetVersionEndpoint(VersioningService versioningService, JsonMapper mapper) {
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
      VersionResponse response = VersionResponse.from(versioningService.getVersion(versionId));
      wrapper.setContentType(ContentType.APPLICATION_JSON);
      return mapper.serialize(response);
    } catch (VersioningException e) {
      throw notFound(e.getMessage());
    } catch (JsonProcessingException e) {
      throw internalServerError(e.getMessage());
    }
  }
}
