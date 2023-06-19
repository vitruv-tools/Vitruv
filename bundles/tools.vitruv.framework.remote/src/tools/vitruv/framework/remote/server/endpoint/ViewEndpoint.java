package tools.vitruv.framework.remote.server.endpoint;

import java.io.IOException;
import java.util.UUID;

import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import tools.vitruv.framework.remote.common.util.*;
import tools.vitruv.framework.remote.common.util.Cache;
import tools.vitruv.framework.remote.common.util.constants.ContentTypes;
import tools.vitruv.framework.remote.common.util.constants.Headers;

/**
 * This endpoint returns a serialized {@link tools.vitruv.framework.views.View View} for the given
 * {@link tools.vitruv.framework.views.ViewType ViewType}.
 */
public class ViewEndpoint implements Endpoint.Post {

    @Override
    public String process(HttpExchangeWrapper wrapper) {
        var selectorUuid = wrapper.getRequestHeader(Headers.SELECTOR_UUID);
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
            var set = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
            set.getResources().addAll(resources.stream().map(r -> ResourceUtils.copyResource(r, set)).toList());

            wrapper.setContentType(ContentTypes.APPLICATION_JSON);
            wrapper.addResponseHeader(Headers.VIEW_UUID, uuid);

            return JsonMapper.serialize(set);
        } catch (IOException e) {
            throw internalServerError(e.getMessage());
        }
    }
}
