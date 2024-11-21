package tools.vitruv.framework.remote.server.rest.endpoints;

import tools.vitruv.framework.remote.server.http.HttpWrapper;
import tools.vitruv.framework.remote.server.rest.GetEndpoint;
import tools.vitruv.framework.remote.common.rest.constants.ContentType;
import tools.vitruv.framework.remote.common.rest.constants.Header;

/**
 * This view returns whether a {@link tools.vitruv.framework.views.View View} is outdated.
 */
public class IsViewOutdatedEndpoint implements GetEndpoint {
    @Override
    public String process(HttpWrapper wrapper) {
        var view = Cache.getView(wrapper.getRequestHeader(Header.VIEW_UUID));
        if (view == null) {
            throw notFound("View with given id not found!");
        }
        wrapper.setContentType(ContentType.TEXT_PLAIN);
        return view.isOutdated() ? Boolean.TRUE.toString() : Boolean.FALSE.toString();
    }
}
