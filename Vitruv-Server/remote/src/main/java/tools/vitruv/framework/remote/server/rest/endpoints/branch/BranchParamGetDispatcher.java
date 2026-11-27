package tools.vitruv.framework.remote.server.rest.endpoints.branch;

import tools.vitruv.framework.remote.server.exception.ServerHaltingException;
import tools.vitruv.framework.remote.server.http.HttpWrapper;
import tools.vitruv.framework.remote.server.rest.GetEndpoint;

/**
 * GET dispatcher for the parameterized {@code /vsum/branch/} catch-all context.
 *
 * <p>Routes based on path segment 1 (the sub-resource after the branch name):
 * <ul>
 *   <li>absent or empty: {@code GET /vsum/branch/{branchName}}: single branch metadata</li>
 *   <li>{@code state}: {@code GET /vsum/branch/{branchName}/state}</li>
 *   <li>{@code history}: {@code GET /vsum/branch/{branchName}/history}</li>
 *   <li>{@code conflicts}: {@code GET /vsum/branch/{branchName}/conflicts}</li>
 * </ul>
 */
public class BranchParamGetDispatcher implements GetEndpoint {

  private final GetEndpoint singleBranch;
  private final GetEndpoint state;
  private final GetEndpoint history;
  private final GetEndpoint conflicts;

  public BranchParamGetDispatcher(
      GetEndpoint singleBranch,
      GetEndpoint state,
      GetEndpoint history,
      GetEndpoint conflicts) {
    this.singleBranch = singleBranch;
    this.state = state;
    this.history = history;
    this.conflicts = conflicts;
  }

  @Override
  public String process(HttpWrapper wrapper) throws ServerHaltingException {
    String sub = wrapper.getPathSegment(1);
    if (sub == null || sub.isBlank()) {
      return singleBranch.process(wrapper);
    }
    return switch (sub) {
      case "state" -> state.process(wrapper);
      case "history" -> history.process(wrapper);
      case "conflicts" -> conflicts.process(wrapper);
      default -> throw notFound("Unknown branch sub-path: " + sub);
    };
  }
}
