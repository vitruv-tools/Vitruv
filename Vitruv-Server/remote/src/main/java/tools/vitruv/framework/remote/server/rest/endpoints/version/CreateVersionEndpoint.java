package tools.vitruv.framework.remote.server.rest.endpoints.version;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import tools.vitruv.framework.remote.common.json.JsonMapper;
import tools.vitruv.framework.remote.common.rest.constants.ContentType;
import tools.vitruv.framework.remote.server.exception.ServerHaltingException;
import tools.vitruv.framework.remote.server.http.HttpWrapper;
import tools.vitruv.framework.remote.server.rest.PostEndpoint;
import tools.vitruv.framework.vsum.versioning.VersioningException;
import tools.vitruv.framework.vsum.versioning.VersioningService;

/**
 * {@code POST /vsum/version}
 *
 * <p>Creates an annotated Git tag at the current HEAD commit, marking it as a named version.
 * Version metadata is persisted under {@code .vitruvius/versions/}.
 *
 * <p>Expected request body:
 * <pre>
 * {
 *   "versionId": "v1.0",
 *   "description": "Stable baseline after sprint 1"
 * }
 * </pre>
 *
 * <p>{@code description} is optional.
 *
 * <p>Returns the newly created version as a JSON object.
 * Returns {@code 500} if the version ID already exists or the Git operation fails.
 */
public class CreateVersionEndpoint implements PostEndpoint {

  private final VersioningService versioningService;
  private final JsonMapper mapper;

  /**
   * Creates a new {@link CreateVersionEndpoint}.
   *
   * @param versioningService the versioning service used to create the version.
   * @param mapper the JSON mapper used to deserialize the request and serialize the response.
   */
  public CreateVersionEndpoint(VersioningService versioningService, JsonMapper mapper) {
    this.versioningService = versioningService;
    this.mapper = mapper;
  }

  @Override
  public String process(HttpWrapper wrapper) throws ServerHaltingException {
    try {
      String body = wrapper.getRequestBodyAsString();
      CreateVersionRequest request = mapper.deserialize(body, CreateVersionRequest.class);
      if (request.versionId() == null || request.versionId().isBlank()) {
        throw badRequest("versionId must not be blank");
      }
      VersionResponse response = VersionResponse.from(
          versioningService.createVersion(request.versionId(), request.description()));
      wrapper.setContentType(ContentType.APPLICATION_JSON);
      return mapper.serialize(response);
    } catch (VersioningException | IOException e) {
      throw internalServerError(e.getMessage());
    }
  }
}
