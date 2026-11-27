package tools.vitruv.framework.remote.server.rest.endpoints.branch;

import java.io.IOException;
import tools.vitruv.framework.remote.common.json.JsonMapper;
import tools.vitruv.framework.remote.server.exception.ServerHaltingException;
import tools.vitruv.framework.remote.server.http.HttpWrapper;
import tools.vitruv.framework.remote.server.rest.PatchEndpoint;
import tools.vitruv.framework.vsum.branch.BranchManager;
import tools.vitruv.framework.vsum.branch.data.MaturityLevel;
import tools.vitruv.framework.vsum.branch.exception.BranchOperationException;

/**
 * {@code PATCH /vsum/branch/{branchName}/maturity}
 *
 * <p>Updates the maturity level of a branch. The branch name is path segment 0
 * after {@code /vsum/branch/}. The new maturity level is supplied in the request body.
 *
 * <p>Expected request body:
 * <pre>
 * {
 *   "maturity": "REVIEWED"
 * }
 * </pre>
 *
 * <p>Valid maturity values: {@code DRAFT}, {@code REVIEWED}, {@code FINAL}.
 * Returns {@code 200 OK} with no body on success.
 */
public class SetBranchMaturityEndpoint implements PatchEndpoint {

  private final BranchManager branchManager;
  private final JsonMapper mapper;

  public SetBranchMaturityEndpoint(BranchManager branchManager, JsonMapper mapper) {
    this.branchManager = branchManager;
    this.mapper = mapper;
  }

  @Override
  public String process(HttpWrapper wrapper) throws ServerHaltingException {
    String branchName = wrapper.getPathSegment(0);
    if (branchName == null || branchName.isBlank()) {
      throw badRequest("Missing branch name in path");
    }
    try {
      String body = wrapper.getRequestBodyAsString();
      SetBranchMaturityRequest request = mapper.deserialize(body, SetBranchMaturityRequest.class);
      if (request.maturity() == null || request.maturity().isBlank()) {
        throw badRequest("Missing required field: maturity");
      }
      MaturityLevel level;
      try {
        level = MaturityLevel.valueOf(request.maturity().toUpperCase());
      } catch (IllegalArgumentException e) {
        throw badRequest("Invalid maturity value: " + request.maturity()
            + ". Valid values: DRAFT, REVIEWED, FINAL");
      }
      branchManager.setBranchMaturity(branchName, level);
      return null;
    } catch (BranchOperationException e) {
      throw notFound(e.getMessage());
    } catch (IOException e) {
      throw internalServerError(e.getMessage());
    }
  }
}
