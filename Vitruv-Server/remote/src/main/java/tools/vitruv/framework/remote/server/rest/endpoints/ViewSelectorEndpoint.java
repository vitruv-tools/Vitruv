package tools.vitruv.framework.remote.server.rest.endpoints;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.HashBiMap;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emfcloud.jackson.resource.JsonResource;
import tools.vitruv.framework.remote.common.json.JsonFieldName;
import tools.vitruv.framework.remote.common.json.JsonMapper;
import tools.vitruv.framework.remote.common.rest.constants.ContentType;
import tools.vitruv.framework.remote.common.rest.constants.Header;
import tools.vitruv.framework.remote.common.util.ResourceUtil;
import tools.vitruv.framework.remote.server.exception.ServerHaltingException;
import tools.vitruv.framework.remote.server.http.HttpWrapper;
import tools.vitruv.framework.remote.server.rest.GetEndpoint;
import tools.vitruv.framework.views.ViewSelector;
import tools.vitruv.framework.views.ViewType;
import tools.vitruv.framework.vsum.VirtualModel;

/** This endpoint creates a view selector for a given view type. */
public class ViewSelectorEndpoint implements GetEndpoint {
  private final VirtualModel model;
  private final JsonMapper mapper;

  /**
   * Creates a new ViewSelectorEndpoint.
   *
   * @param model The virtual model to create selectors from.
   * @param mapper The JSON mapper to use.
   */
  public ViewSelectorEndpoint(VirtualModel model, JsonMapper mapper) {
    this.model = model;
    this.mapper = mapper;
  }

  @Override
  public String process(HttpWrapper wrapper) throws ServerHaltingException {
    String viewTypeName = wrapper.getRequestHeader(Header.VIEW_TYPE);
    Collection<ViewType<?>> types = model.getViewTypes();
    ViewType<?> viewType =
        types.stream().filter(it -> it.getName().equals(viewTypeName)).findFirst().orElse(null);

    // Check if view type exists.
    if (viewType == null) {
      throw notFound("View Type with name " + viewTypeName + " not found!");
    }

    // Generate selector UUID.
    String selectorUuid = UUID.randomUUID().toString();

    ViewSelector selector = model.createSelector(viewType);
    List<EObject> originalSelection = selector.getSelectableElements().stream().toList();
    List<EObject> copiedSelection = EcoreUtil.copyAll(originalSelection).stream().toList();

    // Wrap selection in resource for serialization.
    JsonResource resource =
        (JsonResource)
            ResourceUtil.createResourceWith(
                URI.createURI(JsonFieldName.TEMP_VALUE), copiedSelection);

    // Create EObject to UUID mapping.
    HashBiMap<String, EObject> mapping = HashBiMap.create();
    for (int i = 0; i < originalSelection.size(); i++) {
      var objectUuid = UUID.randomUUID().toString();
      mapping.put(objectUuid, originalSelection.get(i));
      resource.setID(copiedSelection.get(i), objectUuid);
    }
    Cache.addSelectorWithMapping(selectorUuid, selector, mapping);

    wrapper.setContentType(ContentType.APPLICATION_JSON);
    wrapper.addResponseHeader(Header.SELECTOR_UUID, selectorUuid);

    try {
      return mapper.serialize(resource);
    } catch (JsonProcessingException e) {
      throw internalServerError(e.getMessage());
    }
  }
}
