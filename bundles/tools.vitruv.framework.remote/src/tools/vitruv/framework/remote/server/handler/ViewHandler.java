package tools.vitruv.framework.remote.server.handler;

import tools.vitruv.framework.remote.common.util.EndpointPaths;
import tools.vitruv.framework.remote.server.endpoint.ChangePropagationEndpoint;
import tools.vitruv.framework.remote.server.endpoint.CloseViewEndpoint;
import tools.vitruv.framework.remote.server.endpoint.UpdateViewEndpoint;
import tools.vitruv.framework.remote.server.endpoint.ViewEndpoint;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;

public class ViewHandler extends RequestHandler {
    public ViewHandler() {
        super(EndpointPaths.VIEW);
    }

    @Override
    public void init(InternalVirtualModel model) {
        this.getEndpoint = new ViewEndpoint(model);
        this.patchEndpoint = new UpdateViewEndpoint();
        this.deleteEndpoint = new CloseViewEndpoint();
        this.postEndpoint = new ChangePropagationEndpoint();
    }
}
