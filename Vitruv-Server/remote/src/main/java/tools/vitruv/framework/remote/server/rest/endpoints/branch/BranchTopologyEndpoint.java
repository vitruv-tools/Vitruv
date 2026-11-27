package tools.vitruv.framework.remote.server.rest.endpoints.branch;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import tools.vitruv.framework.remote.common.json.JsonMapper;
import tools.vitruv.framework.remote.common.rest.constants.ContentType;
import tools.vitruv.framework.remote.server.exception.ServerHaltingException;
import tools.vitruv.framework.remote.server.http.HttpWrapper;
import tools.vitruv.framework.remote.server.rest.GetEndpoint;
import tools.vitruv.framework.vsum.branch.BranchManager;
import tools.vitruv.framework.vsum.branch.exception.BranchOperationException;

/**
 * {@code GET /vsum/branch/topology}
 *
 * <p>Returns the branch topology as a JSON object where each key is a parent branch name
 * and the value is a list of its direct child branch names. Deleted branches are excluded.
 *
 * <p>Example response:
 * <pre>
 * {
 *   "master": ["feature/a", "feature/b"],
 *   "feature/a": ["feature/a-fix"]
 * }
 * </pre>
 */
public class BranchTopologyEndpoint implements GetEndpoint {

  private final BranchManager branchManager;
  private final JsonMapper mapper;

  /**
   * Creates a new {@link BranchTopologyEndpoint}.
   *
   * @param branchManager the branch manager used to retrieve the branch topology.
   * @param mapper the JSON mapper used to serialize the response.
   */
  public BranchTopologyEndpoint(BranchManager branchManager, JsonMapper mapper) {
    this.branchManager = branchManager;
    this.mapper = mapper;
  }

  @Override
  public String process(HttpWrapper wrapper) throws ServerHaltingException {
    try {
      // "root" is a sentinel value used internally by BranchManager to anchor
      // the initial branch in the topology DAG. Strip it before sending the
      // response so clients only see real branch names as keys.
      Map<String, List<String>> topology = branchManager.getBranchTopology()
          .entrySet().stream()
          .filter(e -> !"root".equals(e.getKey()))
          .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
      wrapper.setContentType(ContentType.APPLICATION_JSON);
      return mapper.serialize(topology);
    } catch (BranchOperationException | JsonProcessingException e) {
      throw internalServerError(e.getMessage());
    }
  }
}
