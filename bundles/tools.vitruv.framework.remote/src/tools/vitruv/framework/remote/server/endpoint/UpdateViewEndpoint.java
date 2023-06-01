package tools.vitruv.framework.remote.server.endpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import tools.vitruv.framework.remote.common.util.*;
import tools.vitruv.framework.remote.server.ViewCache;

/**
 * This endpoint updates a {@link tools.vitruv.framework.views.View View} and returns the
 * updated {@link org.eclipse.emf.ecore.resource.Resource Resources}.
 */
public class UpdateViewEndpoint implements Endpoint.Patch {

    @Override
    public String process(HttpExchangeWrapper wrapper) {
        var view = ViewCache.getView(wrapper.getRequestHeader(Headers.VIEW_UUID));
        if (view == null) {
            throw notFound("View with given id not found!");
        }

        view.update();

        //Get Resources
        var resources = view.getRootObjects().stream().map(EObject::eResource).distinct().toList();
        var rSet = new ResourceSetImpl();
        rSet.getResources().addAll(resources);

        wrapper.setContentType(ContentTypes.APPLICATION_JSON);

        try {
            return JsonMapper.serialize(rSet);
        } catch (JsonProcessingException e) {
            throw internalServerError(e.getMessage());
        }
    }
}
