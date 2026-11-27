package tools.vitruv.framework.remote.server.rest.endpoints.version;

import tools.vitruv.framework.remote.server.exception.ServerHaltingException;
import tools.vitruv.framework.remote.server.http.HttpWrapper;
import tools.vitruv.framework.remote.server.rest.GetEndpoint;

/**
 * GET dispatcher for the parameterized {@code /vsum/version/} catch-all context.
 *
 * <p>Routes based on path segment 1 (the sub-resource after the version ID):
 * <ul>
 *   <li>absent or empty: {@code GET /vsum/version/{versionId}} -- version metadata</li>
 *   <li>{@code model}: {@code GET /vsum/version/{versionId}/model}</li>
 *   <li>{@code view}: {@code GET /vsum/version/{versionId}/view}</li>
 * </ul>
 */
public class VersionParamGetDispatcher implements GetEndpoint {

  private final GetVersionEndpoint getVersion;
  private final VersionModelEndpoint versionModel;
  private final VersionViewEndpoint versionView;

  public VersionParamGetDispatcher(
      GetVersionEndpoint getVersion,
      VersionModelEndpoint versionModel,
      VersionViewEndpoint versionView) {
    this.getVersion = getVersion;
    this.versionModel = versionModel;
    this.versionView = versionView;
  }

  @Override
  public String process(HttpWrapper wrapper) throws ServerHaltingException {
    String sub = wrapper.getPathSegment(1);
    if (sub == null || sub.isBlank()) {
      return getVersion.process(wrapper);
    }
    return switch (sub) {
      case "model" -> versionModel.process(wrapper);
      case "view" -> versionView.process(wrapper);
      default -> throw notFound("Unknown version sub-path: " + sub);
    };
  }
}
