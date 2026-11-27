package tools.vitruv.framework.remote.server.rest.endpoints;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceCopier;
import java.util.List;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import tools.vitruv.framework.remote.common.json.JsonMapper;
import tools.vitruv.framework.remote.common.rest.constants.ContentType;
import tools.vitruv.framework.remote.common.util.ResourceUtil;
import tools.vitruv.framework.remote.common.rest.constants.Header;
import tools.vitruv.framework.remote.server.http.HttpWrapper;
import tools.vitruv.framework.remote.server.rest.GetEndpoint;
import tools.vitruv.framework.views.View;

/**
 * This endpoint updates a {@link tools.vitruv.framework.views.View View} and returns the updated
 * {@link org.eclipse.emf.ecore.resource.Resource Resources}.
 */
public class UpdateViewEndpoint implements GetEndpoint {
  private final JsonMapper mapper;

  /**
   * Creates a new UpdateViewEndpoint.
   *
   * @param mapper The JSON mapper to use.
   */
  public UpdateViewEndpoint(JsonMapper mapper) {
    this.mapper = mapper;
  }

  @Override
  public String process(HttpWrapper wrapper) {
    View view = Cache.getView(wrapper.getRequestHeader(Header.VIEW_UUID));
    if (view == null) {
      throw notFound("View with given id not found!");
    }

    view.update();

    // Get resources.
    List<Resource> resources =
        view.getRootObjects().stream().map(EObject::eResource).distinct().toList();
    ResourceSet set = ResourceUtil.createJsonResourceSet();
    ResourceCopier.copyViewResources(resources, set);

    wrapper.setContentType(ContentType.APPLICATION_JSON);

    try {
      return mapper.serialize(set);
    } catch (JsonProcessingException e) {
      throw internalServerError(e.getMessage());
    }
  }
}
