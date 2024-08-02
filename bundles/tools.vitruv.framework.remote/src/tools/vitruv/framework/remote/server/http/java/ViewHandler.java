package tools.vitruv.framework.remote.server.http.java;

import tools.vitruv.framework.remote.common.rest.constants.EndpointPath;
import tools.vitruv.framework.remote.common.util.JsonMapper;
import tools.vitruv.framework.remote.server.rest.endpoints.ChangePropagationEndpoint;
import tools.vitruv.framework.remote.server.rest.endpoints.CloseViewEndpoint;
import tools.vitruv.framework.remote.server.rest.endpoints.UpdateViewEndpoint;
import tools.vitruv.framework.remote.server.rest.endpoints.ViewEndpoint;
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
