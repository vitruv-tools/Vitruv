package tools.vitruv.framework.remote.server.rest.endpoints;

import com.fasterxml.jackson.core.JsonProcessingException;

import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceCopier;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import tools.vitruv.framework.remote.common.json.JsonMapper;
import tools.vitruv.framework.remote.common.rest.constants.ContentType;
import tools.vitruv.framework.remote.common.rest.constants.Header;
import tools.vitruv.framework.remote.server.http.HttpWrapper;
import tools.vitruv.framework.remote.server.rest.GetEndpoint;

/**
 * This endpoint updates a {@link tools.vitruv.framework.views.View View} and returns the
 * updated {@link org.eclipse.emf.ecore.resource.Resource Resources}.
 */
public class UpdateViewEndpoint implements GetEndpoint {
	private final JsonMapper mapper;
	
    public UpdateViewEndpoint(JsonMapper mapper) {
		this.mapper = mapper;
	}

	@Override
    public String process(HttpWrapper wrapper) {
        var view = Cache.getView(wrapper.getRequestHeader(Header.VIEW_UUID));
        if (view == null) {
            throw notFound("View with given id not found!");
        }

        view.update();

        // Get resources.
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
