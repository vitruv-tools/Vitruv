package tools.vitruv.framework.remote.server.endpoint;

import tools.vitruv.framework.remote.common.util.constants.ContentType;
import tools.vitruv.framework.remote.common.util.HttpExchangeWrapper;
import tools.vitruv.framework.remote.common.util.constants.Header;
import tools.vitruv.framework.remote.common.util.Cache;

/**
 * This endpoint returns whether a {@link tools.vitruv.framework.views.View View} is closed.
 */
public class IsViewClosedEndpoint implements Endpoint.Get {

    @Override
    public String process(HttpExchangeWrapper wrapper) {
        var view = Cache.getView(wrapper.getRequestHeader(Header.VIEW_UUID));
        if (view == null) {
            return Boolean.TRUE.toString();
        }
        if (view.isClosed()) {
            Cache.removeView(wrapper.getRequestHeader(Header.VIEW_UUID));
        }
        wrapper.setContentType(ContentType.TEXT_PLAIN);
        return view.isClosed() ? Boolean.TRUE.toString() : Boolean.FALSE.toString();
    }
}
