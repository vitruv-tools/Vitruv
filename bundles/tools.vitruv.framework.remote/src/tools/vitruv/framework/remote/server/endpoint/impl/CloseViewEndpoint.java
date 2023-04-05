package tools.vitruv.framework.remote.server.endpoint.impl;

import spark.Request;
import spark.Response;
import tools.vitruv.framework.remote.common.util.Headers;
import tools.vitruv.framework.remote.server.ViewCache;
import tools.vitruv.framework.remote.server.endpoint.PostEndpoint;

/**
 * This endpoint closes a {@link tools.vitruv.framework.views.View View}.
 */
public class CloseViewEndpoint extends PostEndpoint {

    public CloseViewEndpoint() {
        super("/vsum/view/closed");
    }

    @Override
    public Void handleRequest(Request request, Response response) {
        var view = ViewCache.removeView(request.headers(Headers.VIEW_UUID));
        if (view == null) {
            notFound("View with given id not found!");
        }
        try {
            view.close();
        } catch (Exception e) {
            internalServerError(e.getMessage());
        }
        return null;
    }
}
