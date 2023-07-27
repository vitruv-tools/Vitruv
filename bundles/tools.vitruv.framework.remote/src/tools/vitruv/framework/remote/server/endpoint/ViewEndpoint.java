package tools.vitruv.framework.remote.server.endpoint;

import java.io.IOException;
import java.util.UUID;

import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil;
import org.eclipse.emf.ecore.EObject;

import tools.vitruv.framework.remote.common.util.*;
import tools.vitruv.framework.remote.common.util.Cache;
import tools.vitruv.framework.remote.common.util.constants.ContentType;
import tools.vitruv.framework.remote.common.util.constants.Header;
import tools.vitruv.framework.views.util.ResourceCopier;

/**
 * This endpoint returns a serialized {@link tools.vitruv.framework.views.View View} for the given
 * {@link tools.vitruv.framework.views.ViewType ViewType}.
 */
public class ViewEndpoint implements Endpoint.Post {

    @Override
    public String process(HttpExchangeWrapper wrapper) {
        var selectorUuid = wrapper.getRequestHeader(Header.SELECTOR_UUID);
        var selector = Cache.getSelector(selectorUuid);

        //Check if view type exists
        if (selector == null) {
            throw notFound("Selector with UUID " + selectorUuid + " not found!");
        }

        try {
            var body = wrapper.getRequestBodyAsString();
            var selection = JsonMapper.deserializeArrayOf(body, String.class);
            selection.forEach(it -> {
                var object = Cache.getEObjectFromMapping(selectorUuid, it);
                if (object != null) {
                    selector.setSelected(object, true);
                }
            });

            //Create and cache view
            var uuid = UUID.randomUUID().toString();
            var view = selector.createView();
            Cache.addView(uuid, view);
            Cache.removeSelectorAndMapping(selectorUuid);

            //Get Resources
            var resources = view.getRootObjects().stream().map(EObject::eResource).distinct().toList();
            var set = ResourceSetUtil.withGlobalFactories(new XMIResourceSetImpl());
            ResourceCopier.copyViewResources(resources, set);

            wrapper.setContentType(ContentType.APPLICATION_JSON);
            wrapper.addResponseHeader(Header.VIEW_UUID, uuid);

            return JsonMapper.serialize(set);
        } catch (IOException e) {
            throw internalServerError(e.getMessage());
        }
    }
}
