package tools.vitruv.framework.remote.server.rest.endpoints.branch;

import tools.vitruv.framework.remote.server.exception.ServerHaltingException;
import tools.vitruv.framework.remote.server.http.HttpWrapper;
import tools.vitruv.framework.remote.server.rest.DeleteEndpoint;
import tools.vitruv.framework.vsum.branch.BranchManager;
import tools.vitruv.framework.vsum.branch.exception.BranchOperationException;

/**
 * {@code DELETE /vsum/branch/{branchName}}
 *
 * <p>Deletes an existing branch by name. The branch must not be the currently checked-out branch.
 * Deleted branches are not removed from metadata - their state is set to {@code DELETED} so that
 * history and topology remain intact.
 *
 * <p>The branch name is extracted from path segment 0 after {@code /vsum/branch/}.
 *
 * <p>Example request:
 * <pre>
 *   DELETE /vsum/branch/feature%2Fmy-feature
 * </pre>
 *
 * <p>Returns {@code 200 OK} with no body on success.
 */
public class DeleteBranchEndpoint implements DeleteEndpoint {

  private final BranchManager branchManager;

  /**
   * Creates a new {@link DeleteBranchEndpoint}.
   *
   * @param branchManager the branch manager used to delete the branch.
   */
  public DeleteBranchEndpoint(BranchManager branchManager) {
    this.branchManager = branchManager;
  }

  @Override
  public String process(HttpWrapper wrapper) throws ServerHaltingException {
    String name = wrapper.getPathSegment(0);
    if (name == null || name.isBlank()) {
      throw badRequest("Missing branch name in path");
    }
    try {
      branchManager.deleteBranch(name, true);
      return null;
    } catch (BranchOperationException e) {
      throw internalServerError(e.getMessage());
    }
  }
}
