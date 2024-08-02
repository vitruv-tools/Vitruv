package tools.vitruv.framework.remote.server.http.java;

import tools.vitruv.framework.remote.common.rest.constants.EndpointPath;
import tools.vitruv.framework.remote.common.util.JsonMapper;
import tools.vitruv.framework.remote.server.rest.endpoints.IsViewOutdatedEndpoint;
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
