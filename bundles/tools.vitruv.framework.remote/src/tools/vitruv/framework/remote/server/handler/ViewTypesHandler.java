package tools.vitruv.framework.remote.server.handler;

import tools.vitruv.framework.remote.common.util.JsonMapper;
import tools.vitruv.framework.remote.common.util.constants.EndpointPath;
import tools.vitruv.framework.remote.server.endpoint.ViewTypesEndpoint;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;

public class ViewTypesHandler extends RequestHandler {
    public ViewTypesHandler() {
        super(EndpointPath.VIEW_TYPES);
    }

    @Override
    public void init(InternalVirtualModel model, JsonMapper mapper) {
        this.getEndpoint = new ViewTypesEndpoint(model, mapper);
    }
}
