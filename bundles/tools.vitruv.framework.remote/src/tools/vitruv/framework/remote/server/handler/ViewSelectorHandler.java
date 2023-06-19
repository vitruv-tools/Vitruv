package tools.vitruv.framework.remote.server.handler;

import tools.vitruv.framework.remote.common.util.constants.EndpointPaths;
import tools.vitruv.framework.remote.server.endpoint.ViewSelectorEndpoint;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;

public class ViewSelectorHandler extends RequestHandler{
    public ViewSelectorHandler() {
        super(EndpointPaths.VIEW_SELECTOR);
    }
    @Override
    public void init(InternalVirtualModel model) {
        this.getEndpoint = new ViewSelectorEndpoint(model);
    }
}
