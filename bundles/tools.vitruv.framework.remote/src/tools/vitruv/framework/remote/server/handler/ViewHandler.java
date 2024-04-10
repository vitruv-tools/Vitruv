package tools.vitruv.framework.remote.server.handler;

import tools.vitruv.framework.remote.common.util.JsonMapper;
import tools.vitruv.framework.remote.common.util.constants.EndpointPath;
import tools.vitruv.framework.remote.server.endpoint.ChangePropagationEndpoint;
import tools.vitruv.framework.remote.server.endpoint.CloseViewEndpoint;
import tools.vitruv.framework.remote.server.endpoint.UpdateViewEndpoint;
import tools.vitruv.framework.remote.server.endpoint.ViewEndpoint;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;

public class ViewHandler extends RequestHandler {
    public ViewHandler() {
        super(EndpointPath.VIEW);
    }

    @Override
    public void init(InternalVirtualModel model, JsonMapper mapper) {
        this.getEndpoint = new UpdateViewEndpoint(mapper);
        this.patchEndpoint = new ChangePropagationEndpoint(mapper);
        this.deleteEndpoint = new CloseViewEndpoint();
        this.postEndpoint = new ViewEndpoint(mapper) ;
    }
}
