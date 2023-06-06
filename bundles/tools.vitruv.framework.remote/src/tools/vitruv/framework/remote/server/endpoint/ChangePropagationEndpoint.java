package tools.vitruv.framework.remote.server.endpoint;

import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.framework.remote.common.util.Headers;
import tools.vitruv.framework.remote.common.util.HttpExchangeWrapper;
import tools.vitruv.framework.remote.server.Cache;
import tools.vitruv.framework.views.impl.ModifiableView;
import tools.vitruv.framework.views.impl.ViewCreatingViewType;
import tools.vitruv.framework.remote.common.util.JsonMapper;

import java.io.IOException;

/**
 * This endpoint applies given {@link VitruviusChange}s to the VSUM.
 */
public class ChangePropagationEndpoint implements Endpoint.Patch {

    @Override
    public String process(HttpExchangeWrapper wrapper) {
        var view = Cache.getView(wrapper.getRequestHeader(Headers.VIEW_UUID));
        if (view == null) {
            throw notFound("View with given id not found!");
        }
        try {
            var body = wrapper.getRequestBodyAsString();
            var change = JsonMapper.deserialize(body, VitruviusChange.class);
            var type = (ViewCreatingViewType<?>) view.getViewType();
            type.commitViewChanges((ModifiableView) view, change);
            return null;
        } catch (IOException e) {
            throw internalServerError(e.getMessage());
        }
    }
}
