package tools.vitruv.remote.server.endpoint.impl;

import spark.Request;
import spark.Response;
import tools.vitruv.remote.common.util.ContentTypes;
import tools.vitruv.remote.common.util.Headers;
import tools.vitruv.remote.server.ViewCache;
import tools.vitruv.remote.server.endpoint.GetEndpoint;

/**
 * This view returns whether a {@link tools.vitruv.framework.views.View View} is outdated.
 */
public class IsViewOutdatedEndpoint extends GetEndpoint {

    public IsViewOutdatedEndpoint() {
        super("/vsum/view/outdated");
    }

    @Override
    public String handleRequest(Request request, Response response) {
        var view = ViewCache.getView(request.headers(Headers.VIEW_UUID));
        if (view == null) {
            notFound("View with given id not found!");
        }
        response.type(ContentTypes.TEXT_PLAIN);
        return view.isOutdated() ? Boolean.TRUE.toString() : Boolean.FALSE.toString();
    }
}
