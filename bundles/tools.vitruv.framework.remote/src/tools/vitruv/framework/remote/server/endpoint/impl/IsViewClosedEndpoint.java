package tools.vitruv.remote.server.endpoint.impl;

import spark.Request;
import spark.Response;
import tools.vitruv.remote.common.util.ContentTypes;
import tools.vitruv.remote.common.util.Headers;
import tools.vitruv.remote.server.ViewCache;
import tools.vitruv.remote.server.endpoint.GetEndpoint;

/**
 * This endpoint returns whether a {@link tools.vitruv.framework.views.View View} is closed.
 */
public class IsViewClosedEndpoint extends GetEndpoint {

    public IsViewClosedEndpoint() {
        super("/vsum/view/closed");
    }

    @Override
    public String handleRequest(Request request, Response response) {
        var view = ViewCache.getView(request.headers(Headers.VIEW_UUID));
        if (view == null) {
            return Boolean.TRUE.toString();
        }
        if (view.isClosed()) {
            ViewCache.removeView(request.headers(Headers.VIEW_UUID));
        }
        response.type(ContentTypes.TEXT_PLAIN);
        return view.isClosed() ? Boolean.TRUE.toString() : Boolean.FALSE.toString();
    }
}
