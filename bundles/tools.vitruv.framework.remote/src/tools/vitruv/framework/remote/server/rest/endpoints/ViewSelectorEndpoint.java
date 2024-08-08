package tools.vitruv.framework.remote.server.rest.endpoints;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.HashBiMap;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emfcloud.jackson.resource.JsonResource;

import tools.vitruv.framework.remote.common.json.JsonFieldName;
import tools.vitruv.framework.remote.common.json.JsonMapper;
import tools.vitruv.framework.remote.common.rest.constants.ContentType;
import tools.vitruv.framework.remote.common.rest.constants.Header;
import tools.vitruv.framework.remote.common.util.*;
import tools.vitruv.framework.remote.server.exception.ServerHaltingException;
import tools.vitruv.framework.remote.server.http.HttpWrapper;
import tools.vitruv.framework.remote.server.rest.GetEndpoint;
import tools.vitruv.framework.vsum.VirtualModel;

import java.util.UUID;

public class ViewSelectorEndpoint implements GetEndpoint {
    private final VirtualModel model;
    private final JsonMapper mapper;

    public ViewSelectorEndpoint(VirtualModel model, JsonMapper mapper) {
        this.model = model;
        this.mapper = mapper;
    }

    @Override
    public String process(HttpWrapper wrapper) throws ServerHaltingException {
        var viewTypeName = wrapper.getRequestHeader(Header.VIEW_TYPE);
        var types = model.getViewTypes();
        var viewType = types.stream().filter(it -> it.getName().equals(viewTypeName)).findFirst().orElse(null);

        // Check if view type exists.
        if (viewType == null) {
            throw notFound("View Type with name " + viewTypeName + " not found!");
        }

        // Generate selector UUID.
        var selectorUuid = UUID.randomUUID().toString();

        var selector = model.createSelector(viewType);
        var originalSelection = selector.getSelectableElements().stream().toList();
        var copiedSelection = EcoreUtil.copyAll(originalSelection).stream().toList();

        // Wrap selection in resource for serialization.
        var resource = (JsonResource) ResourceUtil.createResourceWith(URI.createURI(JsonFieldName.TEMP_VALUE), copiedSelection);

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
