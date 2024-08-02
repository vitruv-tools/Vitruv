package tools.vitruv.framework.remote.server.http.java;

import tools.vitruv.framework.remote.common.rest.constants.EndpointPath;
import tools.vitruv.framework.remote.common.util.JsonMapper;
import tools.vitruv.framework.remote.server.rest.endpoints.ViewSelectorEndpoint;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;

public class ViewSelectorHandler extends RequestHandler{
    public ViewSelectorHandler() {
        super(EndpointPath.VIEW_SELECTOR);
    }
    @Override
    public void init(InternalVirtualModel model, JsonMapper mapper) {
        this.getEndpoint = new ViewSelectorEndpoint(model, mapper);
    }
}
