package tools.vitruv.framework.remote.server.rest.endpoints.branch;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import tools.vitruv.framework.remote.common.json.JsonMapper;
import tools.vitruv.framework.remote.common.rest.constants.ContentType;
import tools.vitruv.framework.remote.server.exception.ServerHaltingException;
import tools.vitruv.framework.remote.server.http.HttpWrapper;
import tools.vitruv.framework.remote.server.rest.GetEndpoint;
import tools.vitruv.framework.vsum.branch.BranchManager;
import tools.vitruv.framework.vsum.branch.data.BranchState;
import tools.vitruv.framework.vsum.branch.exception.BranchOperationException;

/**
 * {@code GET /vsum/branch}
 *
 * <p>Returns a JSON array of branch summaries (name, state, maturity, parent),
 * excluding deleted branches. Analogous to {@code git branch -v}.
 *
 * <p>Example response:
 * <pre>
 * [{"name":"master","state":"ACTIVE","maturity":"DRAFT",...},
 *  {"name":"feature/my-feature","state":"ACTIVE","maturity":"DRAFT",...}]
 * </pre>
 */
public class ListBranchesEndpoint implements GetEndpoint {

  private final BranchManager branchManager;
  private final JsonMapper mapper;

  /**
   * Creates a new {@link ListBranchesEndpoint}.
   *
   * @param branchManager the branch manager used to retrieve branch metadata.
   * @param mapper the JSON mapper used to serialize the response.
   */
  public ListBranchesEndpoint(BranchManager branchManager, JsonMapper mapper) {
    this.branchManager = branchManager;
    this.mapper = mapper;
  }

  @Override
  public String process(HttpWrapper wrapper) throws ServerHaltingException {
    try {
      List<BranchResponse> branches = branchManager.listBranches().stream()
          .filter(b -> b.getState() != BranchState.DELETED)
          .map(BranchResponse::from)
          .toList();
      wrapper.setContentType(ContentType.APPLICATION_JSON);
      return mapper.serialize(branches);
    } catch (BranchOperationException | JsonProcessingException e) {
      throw internalServerError(e.getMessage());
    }
  }
}
