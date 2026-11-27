package tools.vitruv.framework.remote.server.rest.endpoints.version;

import tools.vitruv.framework.remote.server.exception.ServerHaltingException;
import tools.vitruv.framework.remote.server.http.HttpWrapper;
import tools.vitruv.framework.remote.server.rest.PostEndpoint;

/**
 * POST dispatcher for the parameterized {@code /vsum/version/} catch-all context.
 *
 * <p>Routes based on path segments 1 and 2:
 * <ul>
 *   <li>{@code rollback/preview}: {@code POST /vsum/version/{versionId}/rollback/preview}</li>
 *   <li>{@code rollback/confirm}: {@code POST /vsum/version/{versionId}/rollback/confirm}</li>
 *   <li>{@code branch}: {@code POST /vsum/version/{versionId}/branch}</li>
 * </ul>
 */
public class VersionParamPostDispatcher implements PostEndpoint {

  private final RollbackPreviewEndpoint rollbackPreview;
  private final RollbackConfirmEndpoint rollbackConfirm;
  private final CreateVersionBranchEndpoint createBranch;

  public VersionParamPostDispatcher(
      RollbackPreviewEndpoint rollbackPreview,
      RollbackConfirmEndpoint rollbackConfirm,
      CreateVersionBranchEndpoint createBranch) {
    this.rollbackPreview = rollbackPreview;
    this.rollbackConfirm = rollbackConfirm;
    this.createBranch = createBranch;
  }

  @Override
  public String process(HttpWrapper wrapper) throws ServerHaltingException {
    String sub1 = wrapper.getPathSegment(1);
    String sub2 = wrapper.getPathSegment(2);
    if ("rollback".equals(sub1)) {
      if ("preview".equals(sub2)) {
        return rollbackPreview.process(wrapper);
      }
      if ("confirm".equals(sub2)) {
        return rollbackConfirm.process(wrapper);
      }
      throw notFound("Unknown rollback action: " + sub2);
    }
    if ("branch".equals(sub1)) {
      return createBranch.process(wrapper);
    }
    throw notFound("Unknown POST version sub-path: " + sub1);
  }
}
