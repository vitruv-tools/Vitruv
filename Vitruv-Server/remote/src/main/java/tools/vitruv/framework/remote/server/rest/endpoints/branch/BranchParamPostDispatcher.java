package tools.vitruv.framework.remote.server.rest.endpoints.branch;

import tools.vitruv.framework.remote.server.exception.ServerHaltingException;
import tools.vitruv.framework.remote.server.http.HttpWrapper;
import tools.vitruv.framework.remote.server.rest.PostEndpoint;

/**
 * POST dispatcher for the parameterized {@code /vsum/branch/} catch-all context.
 *
 * <p>Routes based on path segment 1:
 * <ul>
 *   <li>{@code switch}: {@code POST /vsum/branch/{branchName}/switch}</li>
 * </ul>
 */
public class BranchParamPostDispatcher implements PostEndpoint {

  private final SwitchBranchEndpoint switchBranch;

  public BranchParamPostDispatcher(SwitchBranchEndpoint switchBranch) {
    this.switchBranch = switchBranch;
  }

  @Override
  public String process(HttpWrapper wrapper) throws ServerHaltingException {
    String sub = wrapper.getPathSegment(1);
    if ("switch".equals(sub)) {
      return switchBranch.process(wrapper);
    }
    throw notFound("Unknown POST branch sub-path: " + sub);
  }
}
