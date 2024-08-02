package tools.vitruv.framework.remote.server.http.java;

import tools.vitruv.framework.remote.common.rest.constants.EndpointPath;
import tools.vitruv.framework.remote.common.util.JsonMapper;
import tools.vitruv.framework.remote.server.rest.endpoints.ViewTypesEndpoint;
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
