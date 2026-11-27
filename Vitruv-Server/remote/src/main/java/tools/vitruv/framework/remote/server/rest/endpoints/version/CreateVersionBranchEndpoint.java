package tools.vitruv.framework.remote.server.rest.endpoints.version;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import tools.vitruv.framework.remote.common.json.JsonMapper;
import tools.vitruv.framework.remote.common.rest.constants.ContentType;
import tools.vitruv.framework.remote.server.exception.ServerHaltingException;
import tools.vitruv.framework.remote.server.http.HttpWrapper;
import tools.vitruv.framework.remote.server.rest.PostEndpoint;
import tools.vitruv.framework.remote.server.rest.endpoints.branch.BranchResponse;
import tools.vitruv.framework.vsum.versioning.VersioningException;
import tools.vitruv.framework.vsum.versioning.VersioningService;

/**
 * {@code POST /vsum/version/branch}
 *
 * <p>Creates a new Git branch whose V-SUM state is initialised from the given version's commit.
 * The version ID is extracted from path segment 0 after {@code /vsum/version/}.
 *
 * <p>Expected request body:
 * <pre>
 * {
 *   "branchName": "feature/from-v1.0"
 * }
 * </pre>
 *
 * <p>Returns the newly created branch as a JSON object (same shape as branch list response).
 * Returns {@code 405} if the version does not exist or the branch name is already taken.
 */
public class CreateVersionBranchEndpoint implements PostEndpoint {

  private final VersioningService versioningService;
  private final JsonMapper mapper;

  /**
   * Creates a new {@link CreateVersionBranchEndpoint}.
   *
   * @param versioningService the versioning service used to create the branch.
   * @param mapper the JSON mapper used to deserialize the request and serialize the response.
   */
  public CreateVersionBranchEndpoint(VersioningService versioningService, JsonMapper mapper) {
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
      String body = wrapper.getRequestBodyAsString();
      CreateVersionBranchRequest request = mapper.deserialize(
          body, CreateVersionBranchRequest.class);
      if (request.branchName() == null || request.branchName().isBlank()) {
        throw badRequest("branchName must not be blank");
      }
      BranchResponse response = BranchResponse.from(
          versioningService.createBranchFromVersion(request.branchName(), versionId));
      wrapper.setContentType(ContentType.APPLICATION_JSON);
      return mapper.serialize(response);
    } catch (VersioningException e) {
      throw notFound(e.getMessage());
    } catch (IOException e) {
      throw internalServerError(e.getMessage());
    }
  }
}
