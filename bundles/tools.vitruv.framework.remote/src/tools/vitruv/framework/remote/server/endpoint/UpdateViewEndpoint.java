package tools.vitruv.framework.remote.server.endpoint;

import com.fasterxml.jackson.core.JsonProcessingException;

import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceCopier;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import tools.vitruv.framework.remote.common.util.*;
import tools.vitruv.framework.remote.common.util.constants.ContentType;
import tools.vitruv.framework.remote.common.util.constants.Header;

/**
 * This endpoint updates a {@link tools.vitruv.framework.views.View View} and returns the
 * updated {@link org.eclipse.emf.ecore.resource.Resource Resources}.
 */
public class UpdateViewEndpoint implements Endpoint.Get {
	
	private final JsonMapper mapper;
	
    public UpdateViewEndpoint(JsonMapper mapper) {
	
		this.mapper = mapper;
	}

	@Override
    public String process(HttpExchangeWrapper wrapper) {
        var view = Cache.getView(wrapper.getRequestHeader(Header.VIEW_UUID));
        if (view == null) {
            throw notFound("View with given id not found!");
        }

        view.update();

        //Get Resources
        var resources = view.getRootObjects().stream().map(EObject::eResource).distinct().toList();
        var set = new ResourceSetImpl();
        ResourceCopier.copyViewResources(resources, set);

        wrapper.setContentType(ContentType.APPLICATION_JSON);

        try {
            return mapper.serialize(set);
        } catch (JsonProcessingException e) {
            throw internalServerError(e.getMessage());
        }
    }
}
