package tools.vitruv.framework.remote.server.handler;

import tools.vitruv.framework.remote.common.util.JsonMapper;
import tools.vitruv.framework.remote.common.util.constants.EndpointPath;
import tools.vitruv.framework.remote.server.endpoint.HealthEndpoint;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;

public class HealthHandler extends RequestHandler {
    public HealthHandler() {
        super(EndpointPath.HEALTH);
    }

    @Override
    public void init(InternalVirtualModel model, JsonMapper mapper) {
        this.getEndpoint = new HealthEndpoint();
    }
}
