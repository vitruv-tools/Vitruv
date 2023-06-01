package tools.vitruv.framework.remote.server.handler;

import tools.vitruv.framework.remote.common.util.EndpointPaths;
import tools.vitruv.framework.remote.server.endpoint.IsViewClosedEndpoint;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;

public class IsViewClosedHandler extends RequestHandler {
    public IsViewClosedHandler() {
        super(EndpointPaths.IS_VIEW_CLOSED);
    }

    @Override
    public void init(InternalVirtualModel model) {
        this.getEndpoint = new IsViewClosedEndpoint();
    }
}
