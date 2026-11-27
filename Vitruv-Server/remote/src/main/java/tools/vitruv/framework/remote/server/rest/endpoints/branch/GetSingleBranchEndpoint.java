package tools.vitruv.framework.remote.server.rest.endpoints.branch;

import com.fasterxml.jackson.core.JsonProcessingException;
import tools.vitruv.framework.remote.common.json.JsonMapper;
import tools.vitruv.framework.remote.common.rest.constants.ContentType;
import tools.vitruv.framework.remote.server.exception.ServerHaltingException;
import tools.vitruv.framework.remote.server.http.HttpWrapper;
import tools.vitruv.framework.remote.server.rest.GetEndpoint;
import tools.vitruv.framework.vsum.branch.BranchManager;
import tools.vitruv.framework.vsum.branch.exception.BranchOperationException;

/**
 * {@code GET /vsum/branch/{branchName}}
 *
 * <p>Returns full metadata for a single branch. The branch name is path segment 0
 * after {@code /vsum/branch/}.
 *
 * <p>Example request:
 * <pre>
 *   GET /vsum/branch/feature%2Fmy-feature
 * </pre>
 *
 * <p>Returns {@code 404} if the branch does not exist.
 */
public class GetSingleBranchEndpoint implements GetEndpoint {

  private final BranchManager branchManager;
  private final JsonMapper mapper;

  public GetSingleBranchEndpoint(BranchManager branchManager, JsonMapper mapper) {
    this.branchManager = branchManager;
    this.mapper = mapper;
  }

  @Override
  public String process(HttpWrapper wrapper) throws ServerHaltingException {
    String name = wrapper.getPathSegment(0);
    if (name == null || name.isBlank()) {
      throw badRequest("Missing branch name in path");
    }
    try {
      BranchResponse response = branchManager.listBranches().stream()
          .filter(b -> b.getName().equals(name))
          .findFirst()
          .map(BranchResponse::from)
          .orElseThrow(() -> new BranchOperationException("Branch not found: " + name));
      wrapper.setContentType(ContentType.APPLICATION_JSON);
      return mapper.serialize(response);
    } catch (BranchOperationException e) {
      throw notFound(e.getMessage());
    } catch (JsonProcessingException e) {
      throw internalServerError(e.getMessage());
    }
  }
}
