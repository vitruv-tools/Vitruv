package tools.vitruv.framework.remote.server.handler;

import tools.vitruv.framework.remote.common.util.JsonMapper;
import tools.vitruv.framework.remote.common.util.constants.EndpointPath;
import tools.vitruv.framework.remote.server.endpoint.IsViewOutdatedEndpoint;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;

public class IsViewOutdatedHandler extends RequestHandler {
    public IsViewOutdatedHandler() {
        super(EndpointPath.IS_VIEW_OUTDATED);
    }

    @Override
    public void init(InternalVirtualModel model, JsonMapper mapper) {
        this.getEndpoint = new IsViewOutdatedEndpoint();
    }
}
