package tools.vitruv.framework.remote.server.endpoint;

import tools.vitruv.framework.remote.common.util.constants.ContentType;
import tools.vitruv.framework.remote.common.util.HttpExchangeWrapper;
import tools.vitruv.framework.remote.common.util.constants.Header;
import tools.vitruv.framework.remote.common.util.Cache;

/**
 * This view returns whether a {@link tools.vitruv.framework.views.View View} is outdated.
 */
public class IsViewOutdatedEndpoint implements Endpoint.Get {

    @Override
    public String process(HttpExchangeWrapper wrapper) {
        var view = Cache.getView(wrapper.getRequestHeader(Header.VIEW_UUID));
        if (view == null) {
            throw notFound("View with given id not found!");
        }
        wrapper.setContentType(ContentType.TEXT_PLAIN);
        return view.isOutdated() ? Boolean.TRUE.toString() : Boolean.FALSE.toString();
    }
}
