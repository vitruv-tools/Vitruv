package tools.vitruv.framework.remote.server.rest.endpoints.version;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import tools.vitruv.framework.remote.common.json.JsonMapper;
import tools.vitruv.framework.remote.common.rest.constants.ContentType;
import tools.vitruv.framework.remote.server.exception.ServerHaltingException;
import tools.vitruv.framework.remote.server.http.HttpWrapper;
import tools.vitruv.framework.remote.server.rest.GetEndpoint;
import tools.vitruv.framework.vsum.versioning.VersioningException;
import tools.vitruv.framework.vsum.versioning.VersioningService;

/**
 * {@code GET /vsum/version}
 *
 * <p>Returns a lightweight summary of all versions, newest first.
 * Each entry contains only {@code versionId} and {@code description}.
 * Use {@code GET /vsum/version/detail} with the {@code Version-Id} header
 * to retrieve the full metadata for a specific version.
 *
 * <p>Example request:
 * <pre>
 *   GET /vsum/version
 * </pre>
 *
 * <p>Example response:
 * <pre>
 * [
 *   { "versionId": "v1.0", "description": "Stable baseline after sprint 1" }
 * ]
 * </pre>
 */
public class ListVersionsEndpoint implements GetEndpoint {

  private final VersioningService versioningService;
  private final JsonMapper mapper;

  /**
   * Creates a new {@link ListVersionsEndpoint}.
   *
   * @param versioningService the versioning service used to list versions.
   * @param mapper the JSON mapper used to serialize the response.
   */
  public ListVersionsEndpoint(VersioningService versioningService, JsonMapper mapper) {
    this.versioningService = versioningService;
    this.mapper = mapper;
  }

  @Override
  public String process(HttpWrapper wrapper) throws ServerHaltingException {
    try {
      List<VersionSummary> versions = versioningService.listVersions().stream()
          .map(VersionSummary::from)
          .toList();
      wrapper.setContentType(ContentType.APPLICATION_JSON);
      return mapper.serialize(versions);
    } catch (VersioningException | JsonProcessingException e) {
      throw internalServerError(e.getMessage());
    }
  }
}
