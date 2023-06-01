package tools.vitruv.framework.remote.server.endpoint;

import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import tools.vitruv.framework.remote.common.util.*;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;
import tools.vitruv.framework.remote.server.ViewCache;

/**
 * This endpoint returns a serialized {@link tools.vitruv.framework.views.View View} for the given
 * {@link tools.vitruv.framework.views.ViewType ViewType}.
 */
public class ViewEndpoint implements Endpoint.Get {

    private final InternalVirtualModel model;

    public ViewEndpoint(InternalVirtualModel model) {
        this.model = model;
    }

    @Override
    public String process(HttpExchangeWrapper wrapper) {
        String viewTypeName = wrapper.getRequestHeader(Headers.VIEW_TYPE);
        var types = model.getViewTypes();

        //Check if view type exists
        if (types.stream().noneMatch(it -> it.getName().equals(viewTypeName))) {
            throw notFound("View Type with name " + viewTypeName + " not found!");
        }

        //Get selector and select every element
        var selector = model.createSelector(types.stream().filter(e -> e.getName().equals(viewTypeName)).findFirst().get());
        selector.getSelectableElements().forEach(it -> selector.setSelected(it, true));

        //Create and cache view
        String uuid = UUID.randomUUID().toString();
        var view = selector.createView();
        ViewCache.addView(uuid, view);

        //Get Resources
        var resources = view.getRootObjects().stream().map(EObject::eResource).distinct().toList();
        var rSet = new ResourceSetImpl();
        rSet.getResources().addAll(resources.stream().map(r -> ResourceUtils.copyResource(r, rSet)).toList());

        wrapper.setContentType(ContentTypes.APPLICATION_JSON);
        wrapper.addResponseHeader(Headers.VIEW_UUID, uuid);
        try {
            return JsonMapper.serialize(rSet);
        } catch (JsonProcessingException e) {
            throw internalServerError(e.getMessage());
        }
    }
}
