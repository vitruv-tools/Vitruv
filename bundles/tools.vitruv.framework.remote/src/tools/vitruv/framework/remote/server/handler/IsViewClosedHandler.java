package tools.vitruv.framework.remote.server.handler;

import tools.vitruv.framework.remote.common.util.JsonMapper;
import tools.vitruv.framework.remote.common.util.constants.EndpointPath;
import tools.vitruv.framework.remote.server.endpoint.IsViewClosedEndpoint;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;

public class IsViewClosedHandler extends RequestHandler {
    public IsViewClosedHandler() {
        super(EndpointPath.IS_VIEW_CLOSED);
    }

    @Override
    public void init(InternalVirtualModel model, JsonMapper mapper) {
        this.getEndpoint = new IsViewClosedEndpoint();
    }
}
