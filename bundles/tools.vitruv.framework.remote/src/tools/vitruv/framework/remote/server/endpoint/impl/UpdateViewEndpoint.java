package tools.vitruv.remote.server.endpoint.impl;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import spark.Request;
import spark.Response;
import tools.vitruv.remote.common.util.ContentTypes;
import tools.vitruv.remote.common.util.Headers;
import tools.vitruv.remote.common.util.JsonMapper;
import tools.vitruv.remote.server.ViewCache;
import tools.vitruv.remote.server.endpoint.GetEndpoint;

/**
 * This endpoint updates a {@link tools.vitruv.framework.views.View View} and returns the
 * updated {@link org.eclipse.emf.ecore.resource.Resource Resources}.
 */
public class UpdateViewEndpoint extends GetEndpoint {

    public UpdateViewEndpoint() {
        super("/vsum/view/update");
    }

    @Override
    public Object handleRequest(Request request, Response response) {
        var view = ViewCache.getView(request.headers(Headers.VIEW_UUID));
        if (view == null) {
            notFound("View with given id not found!");
        }

        view.update();

        //Get Resources
        var resources = view.getRootObjects().stream().map(EObject::eResource).distinct().toList();
        var rSet = new ResourceSetImpl();
        rSet.getResources().addAll(resources);

        response.type(ContentTypes.APPLICATION_JSON);
        return JsonMapper.serialize(rSet);
    }
}
