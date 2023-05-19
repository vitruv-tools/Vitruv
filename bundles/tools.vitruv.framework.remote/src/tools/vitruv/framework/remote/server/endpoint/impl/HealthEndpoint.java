package tools.vitruv.framework.remote.server.endpoint.impl;

import spark.Request;
import spark.Response;
import tools.vitruv.framework.remote.common.util.EndpointPaths;
import tools.vitruv.framework.remote.server.endpoint.GetEndpoint;

/**
 * This endpoint can be used to check, if the server is running.
 */
public class HealthEndpoint extends GetEndpoint {

    public HealthEndpoint() {
        super(EndpointPaths.HEALTH);
    }

    @Override
    public Object handleRequest(Request request, Response response) {
        return "Vitruv Server is up and running!";
    }
}
