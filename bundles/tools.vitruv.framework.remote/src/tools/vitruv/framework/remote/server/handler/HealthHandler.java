package tools.vitruv.framework.remote.server.handler;

import tools.vitruv.framework.remote.common.util.constants.EndpointPaths;
import tools.vitruv.framework.remote.server.endpoint.HealthEndpoint;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;

public class HealthHandler extends RequestHandler {
    public HealthHandler() {
        super(EndpointPaths.HEALTH);
    }

    @Override
    public void init(InternalVirtualModel model) {
        this.getEndpoint = new HealthEndpoint();
    }
}
