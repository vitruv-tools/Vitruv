package tools.vitruv.framework.remote.server.http.java;

import tools.vitruv.framework.remote.common.rest.constants.EndpointPath;
import tools.vitruv.framework.remote.common.util.JsonMapper;
import tools.vitruv.framework.remote.server.rest.endpoints.HealthEndpoint;
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
