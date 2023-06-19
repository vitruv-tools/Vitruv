package tools.vitruv.framework.remote.server.handler;

import tools.vitruv.framework.remote.common.util.constants.EndpointPaths;
import tools.vitruv.framework.remote.server.endpoint.IsViewOutdatedEndpoint;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;

public class IsViewOutdatedHandler extends RequestHandler {
    public IsViewOutdatedHandler() {
        super(EndpointPaths.IS_VIEW_OUTDATED);
    }

    @Override
    public void init(InternalVirtualModel model) {
        this.getEndpoint = new IsViewOutdatedEndpoint();
    }
}
