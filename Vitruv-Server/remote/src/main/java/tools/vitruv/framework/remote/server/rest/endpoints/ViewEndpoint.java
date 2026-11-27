package tools.vitruv.framework.remote.server.rest.endpoints;

import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceCopier;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import tools.vitruv.framework.remote.common.json.JsonMapper;
import tools.vitruv.framework.remote.common.rest.constants.ContentType;
import tools.vitruv.framework.remote.common.util.ResourceUtil;
import tools.vitruv.framework.remote.common.rest.constants.Header;
import tools.vitruv.framework.remote.server.http.HttpWrapper;
import tools.vitruv.framework.remote.server.rest.PostEndpoint;
import tools.vitruv.framework.views.View;
import tools.vitruv.framework.views.ViewSelector;

/**
 * This endpoint returns a serialized {@link tools.vitruv.framework.views.View View} for the given
 * {@link tools.vitruv.framework.views.ViewType ViewType}.
 */
public class ViewEndpoint implements PostEndpoint {
  private final JsonMapper mapper;

  /**
   * Creates a new ViewEndpoint.
   *
   * @param mapper The JSON mapper to use.
   */
  public ViewEndpoint(JsonMapper mapper) {
    this.mapper = mapper;
  }

  @Override
  public String process(HttpWrapper wrapper) {
    String selectorUuid = wrapper.getRequestHeader(Header.SELECTOR_UUID);
    ViewSelector selector = Cache.getSelector(selectorUuid);

    // Check if view type exists.
    if (selector == null) {
      throw notFound("Selector with UUID " + selectorUuid + " not found!");
    }

    try {
      String body = wrapper.getRequestBodyAsString();
      List<String> selection = mapper.deserializeArrayOf(body, String.class);

      // Select elements using IDs sent from client.
      selection.forEach(
          it -> {
            EObject object = Cache.getEObjectFromMapping(selectorUuid, it);
            if (object != null) {
              selector.setSelected(object, true);
            }
          });

      // Create and cache view.
      String uuid = UUID.randomUUID().toString();
      View view = selector.createView();
      Cache.addView(uuid, view);
      Cache.removeSelectorAndMapping(selectorUuid);

      // Get resources.
      List<Resource> resources =
          view.getRootObjects().stream().map(EObject::eResource).distinct().toList();
      ResourceSet set = ResourceUtil.createJsonResourceSet();
      ResourceCopier.copyViewResources(resources, set);

      wrapper.setContentType(ContentType.APPLICATION_JSON);
      wrapper.addResponseHeader(Header.VIEW_UUID, uuid);

      return mapper.serialize(set);
    } catch (IOException e) {
      throw internalServerError(e.getMessage());
    }
  }
}
