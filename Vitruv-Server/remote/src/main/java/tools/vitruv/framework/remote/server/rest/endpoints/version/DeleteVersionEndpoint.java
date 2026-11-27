package tools.vitruv.framework.remote.server.rest.endpoints.version;

import tools.vitruv.framework.remote.server.exception.ServerHaltingException;
import tools.vitruv.framework.remote.server.http.HttpWrapper;
import tools.vitruv.framework.remote.server.rest.DeleteEndpoint;
import tools.vitruv.framework.vsum.versioning.VersioningException;
import tools.vitruv.framework.vsum.versioning.VersioningService;

/**
 * {@code DELETE /vsum/version/{versionId}}
 *
 * <p>Deletes an existing version: removes the Git tag and the metadata file.
 * The version ID is extracted from path segment 0 after {@code /vsum/version/}.
 *
 * <p>Example request:
 * <pre>
 *   DELETE /vsum/version/v1.0
 * </pre>
 *
 * <p>Returns {@code 200 OK} with no body on success.
 * Returns {@code 405} if the version does not exist.
 */
public class DeleteVersionEndpoint implements DeleteEndpoint {

  private final VersioningService versioningService;

  /**
   * Creates a new {@link DeleteVersionEndpoint}.
   *
   * @param versioningService the versioning service used to delete the version.
   */
  public DeleteVersionEndpoint(VersioningService versioningService) {
    this.versioningService = versioningService;
  }

  @Override
  public String process(HttpWrapper wrapper) throws ServerHaltingException {
    String versionId = wrapper.getPathSegment(0);
    if (versionId == null || versionId.isBlank()) {
      throw badRequest("Missing version ID in path");
    }
    try {
      versioningService.deleteVersion(versionId);
      return null;
    } catch (VersioningException e) {
      throw notFound(e.getMessage());
    }
  }
}
