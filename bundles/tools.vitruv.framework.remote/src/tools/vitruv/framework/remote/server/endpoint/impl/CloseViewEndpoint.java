package tools.vitruv.remote.server.endpoint.impl;

import spark.Request;
import spark.Response;
import tools.vitruv.remote.common.util.Headers;
import tools.vitruv.remote.common.util.Nothing;
import tools.vitruv.remote.server.ViewCache;
import tools.vitruv.remote.server.endpoint.PostEndpoint;

/**
 * This endpoint closes a {@link tools.vitruv.framework.views.View View}.
 */
public class CloseViewEndpoint extends PostEndpoint {

    public CloseViewEndpoint() {
        super("/vsum/view/closed");
    }

    @Override
    public Nothing handleRequest(Request request, Response response) {
        var view = ViewCache.removeView(request.headers(Headers.VIEW_UUID));
        if (view == null) {
            notFound("View with given id not found!");
        }
        try {
            view.close();
        } catch (Exception e) {
            internalServerError(e.getMessage());
        }
        return new Nothing();
    }
}
