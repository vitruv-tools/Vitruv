package tools.vitruv.framework.remote.server.endpoint.impl;

import java.util.UUID;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import spark.Request;
import spark.Response;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;
import tools.vitruv.framework.remote.common.util.ContentTypes;
import tools.vitruv.framework.remote.common.util.Headers;
import tools.vitruv.framework.remote.common.util.JsonMapper;
import tools.vitruv.framework.remote.common.util.ResourceUtils;
import tools.vitruv.framework.remote.server.ViewCache;
import tools.vitruv.framework.remote.server.endpoint.GetEndpoint;

/**
 * This endpoint returns a serialized {@link tools.vitruv.framework.views.View View} for the given
 * {@link tools.vitruv.framework.views.ViewType ViewType}.
 */
public class ViewEndpoint extends GetEndpoint {

    private final InternalVirtualModel model;

    public ViewEndpoint(InternalVirtualModel model) {
        super("/vsum/view");
        this.model = model;
    }

    @Override
    public String handleRequest(Request request, Response response) {
        String viewTypeName = request.headers(Headers.VIEW_TYPE);
        var types = model.getViewTypes();

        //Check if view type exists
        if (types.stream().noneMatch(it -> it.getName().equals(viewTypeName))) {
            notFound("View Type with name " + viewTypeName + " not found!");
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

        response.type(ContentTypes.APPLICATION_JSON);
        response.header(Headers.VIEW_UUID, uuid);
        return JsonMapper.serialize(rSet);
    }
}
