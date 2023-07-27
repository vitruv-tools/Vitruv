package tools.vitruv.framework.remote.server.endpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.HashBiMap;
import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edapt.internal.common.EcoreUtils;
import tools.vitruv.framework.remote.common.util.*;
import tools.vitruv.framework.remote.common.util.Cache;
import tools.vitruv.framework.remote.common.util.constants.ContentType;
import tools.vitruv.framework.remote.common.util.constants.Header;
import tools.vitruv.framework.remote.common.util.constants.JsonFieldName;
import tools.vitruv.framework.remote.server.exception.ServerHaltingException;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;

import java.util.UUID;

public class ViewSelectorEndpoint implements Endpoint.Get {

    private final InternalVirtualModel model;

    public ViewSelectorEndpoint(InternalVirtualModel model) {
        this.model = model;
    }

    @Override
    public String process(HttpExchangeWrapper wrapper) throws ServerHaltingException {
        var viewTypeName = wrapper.getRequestHeader(Header.VIEW_TYPE);
        var types = model.getViewTypes();
        var viewType = types.stream().filter(it -> it.getName().equals(viewTypeName)).findFirst().orElse(null);

        //Check if view type exists
        if (viewType == null) {
            throw notFound("View Type with name " + viewTypeName + " not found!");
        }

        //Generate selector UUID
        var selectorUuid = UUID.randomUUID().toString();

        var selector = model.createSelector(viewType);
        var originalSelection = selector.getSelectableElements().stream().toList();
        var copiedSelection = EcoreUtil.copyAll(originalSelection).stream().toList();

        //Wrap selection in resource
        var set = ResourceSetUtil.withGlobalFactories(new XMIResourceSetImpl());
        var resource = set.createResource(URI.createURI(JsonFieldName.TEMP));
        resource.getContents().addAll(copiedSelection);

        //Create EObject to UUID mapping
        HashBiMap<String, EObject> mapping = HashBiMap.create();
        for (int i = 0; i < originalSelection.size(); i++) {
            var objectUuid = UUID.randomUUID().toString();
            mapping.put(objectUuid, originalSelection.get(i));
            EcoreUtils.setUUID(copiedSelection.get(i), objectUuid);
        }
        Cache.addSelectorWithMapping(selectorUuid, selector, mapping);

        wrapper.setContentType(ContentType.APPLICATION_JSON);
        wrapper.addResponseHeader(Header.SELECTOR_UUID, selectorUuid);

        try {
            return JsonMapper.serialize(resource);
        } catch (JsonProcessingException e) {
            throw internalServerError(e.getMessage());
        }
    }
}
