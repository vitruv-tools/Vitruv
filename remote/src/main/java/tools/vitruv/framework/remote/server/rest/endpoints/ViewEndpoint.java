package tools.vitruv.framework.remote.server.rest.endpoints;

import java.io.IOException;
import java.util.UUID;

import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceCopier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import tools.vitruv.framework.remote.common.json.JsonMapper;
import tools.vitruv.framework.remote.common.rest.constants.ContentType;
import tools.vitruv.framework.remote.common.rest.constants.Header;
import tools.vitruv.framework.remote.server.http.HttpWrapper;
import tools.vitruv.framework.remote.server.rest.PostEndpoint;

/**
 * This endpoint returns a serialized {@link tools.vitruv.framework.views.View View} for the given
 * {@link tools.vitruv.framework.views.ViewType ViewType}.
 */
public class ViewEndpoint implements PostEndpoint {
	private final JsonMapper mapper;
	
    public ViewEndpoint(JsonMapper mapper) {
		this.mapper = mapper;
	}

	@Override
    public String process(HttpWrapper wrapper) {
        var selectorUuid = wrapper.getRequestHeader(Header.SELECTOR_UUID);
        var selector = Cache.getSelector(selectorUuid);

        // Check if view type exists.
        if (selector == null) {
            throw notFound("Selector with UUID " + selectorUuid + " not found!");
        }

        try {
            var body = wrapper.getRequestBodyAsString();
            var selection = mapper.deserializeArrayOf(body, String.class);
            
            // Select elements using IDs sent from client.
            selection.forEach(it -> {
                var object = Cache.getEObjectFromMapping(selectorUuid, it);
                if (object != null) {
                    selector.setSelected(object, true);
                }
            });

            // Create and cache view.
            var uuid = UUID.randomUUID().toString();
            var view = selector.createView();
            Cache.addView(uuid, view);
            Cache.removeSelectorAndMapping(selectorUuid);

            // Get resources.
            var resources = view.getRootObjects().stream().map(EObject::eResource).distinct().toList();
            var set = new ResourceSetImpl();
            ResourceCopier.copyViewResources(resources, set);

            wrapper.setContentType(ContentType.APPLICATION_JSON);
            wrapper.addResponseHeader(Header.VIEW_UUID, uuid);

            return mapper.serialize(set);
        } catch (IOException e) {
            throw internalServerError(e.getMessage());
        }
    }
}
