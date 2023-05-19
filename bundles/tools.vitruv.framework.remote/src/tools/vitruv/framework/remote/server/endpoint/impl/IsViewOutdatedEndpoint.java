package tools.vitruv.framework.remote.server.endpoint.impl;

import spark.Request;
import spark.Response;
import tools.vitruv.framework.remote.common.util.ContentTypes;
import tools.vitruv.framework.remote.common.util.EndpointPaths;
import tools.vitruv.framework.remote.common.util.Headers;
import tools.vitruv.framework.remote.server.ViewCache;
import tools.vitruv.framework.remote.server.endpoint.GetEndpoint;

/**
 * This view returns whether a {@link tools.vitruv.framework.views.View View} is outdated.
 */
public class IsViewOutdatedEndpoint extends GetEndpoint {

    public IsViewOutdatedEndpoint() {
        super(EndpointPaths.IS_VIEW_OUTDATED);
    }

    @Override
    public String handleRequest(Request request, Response response) {
        var view = ViewCache.getView(request.headers(Headers.VIEW_UUID));
        if (view == null) {
            throw notFound("View with given id not found!");
        }
        response.type(ContentTypes.TEXT_PLAIN);
        return view.isOutdated() ? Boolean.TRUE.toString() : Boolean.FALSE.toString();
    }
}
