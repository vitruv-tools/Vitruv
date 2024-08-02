package tools.vitruv.framework.remote.server.rest.endpoints;

import tools.vitruv.framework.remote.server.rest.DeleteEndpoint;
import tools.vitruv.framework.remote.server.rest.HttpExchangeWrapper;
import tools.vitruv.framework.remote.common.rest.constants.Header;
import tools.vitruv.framework.remote.common.util.Cache;

/**
 * This endpoint closes a {@link tools.vitruv.framework.views.View View}.
 */
public class CloseViewEndpoint implements DeleteEndpoint {
    @Override
    public String process(HttpExchangeWrapper wrapper) {
        var view = Cache.removeView(wrapper.getRequestHeader(Header.VIEW_UUID));
        if (view == null) {
            throw notFound("View with given id not found!");
        }
        try {
            view.close();
            return null;
        } catch (Exception e) {
            throw internalServerError(e.getMessage());
        }
    }
}
