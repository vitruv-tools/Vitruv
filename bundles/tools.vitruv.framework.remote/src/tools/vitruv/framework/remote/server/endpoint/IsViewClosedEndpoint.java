package tools.vitruv.framework.remote.server.endpoint;

import tools.vitruv.framework.remote.common.util.ContentTypes;
import tools.vitruv.framework.remote.common.util.HttpExchangeWrapper;
import tools.vitruv.framework.remote.common.util.Headers;
import tools.vitruv.framework.remote.server.ViewCache;

/**
 * This endpoint returns whether a {@link tools.vitruv.framework.views.View View} is closed.
 */
public class IsViewClosedEndpoint implements Endpoint.Get {

    @Override
    public String process(HttpExchangeWrapper wrapper) {
        var view = ViewCache.getView(wrapper.getRequestHeader(Headers.VIEW_UUID));
        if (view == null) {
            return Boolean.TRUE.toString();
        }
        if (view.isClosed()) {
            ViewCache.removeView(wrapper.getRequestHeader(Headers.VIEW_UUID));
        }
        wrapper.setContentType(ContentTypes.TEXT_PLAIN);
        return view.isClosed() ? Boolean.TRUE.toString() : Boolean.FALSE.toString();
    }
}
