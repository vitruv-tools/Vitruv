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
 * {@code GET /vsum/branch/{branchName}/state}
 *
 * <p>Returns the lifecycle state of a single branch. The branch name is extracted from the
 * path: the first dynamic segment after {@code /vsum/branch/}.
 *
 * <p>Example request:
 * <pre>
 *   GET /vsum/branch/feature%2Fmy-feature/state
 * </pre>
 *
 * <p>Example response:
 * <pre>
 *   "ACTIVE"
 * </pre>
 *
 * <p>Possible values are {@code ACTIVE}, {@code MERGED}, and {@code DELETED}.
 */
public class BranchStateEndpoint implements GetEndpoint {

  private final BranchManager branchManager;
  private final JsonMapper mapper;

  /**
   * Creates a new {@link BranchStateEndpoint}.
   *
   * @param branchManager the branch manager used to retrieve the branch state.
   * @param mapper the JSON mapper used to serialize the response.
   */
  public BranchStateEndpoint(BranchManager branchManager, JsonMapper mapper) {
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
      String state = branchManager.getBranchState(name).name();
      wrapper.setContentType(ContentType.APPLICATION_JSON);
      return mapper.serialize(state);
    } catch (BranchOperationException | JsonProcessingException e) {
      throw internalServerError(e.getMessage());
    }
  }
}
