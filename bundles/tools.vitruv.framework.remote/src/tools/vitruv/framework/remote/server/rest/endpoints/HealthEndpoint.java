package tools.vitruv.framework.remote.server.rest.endpoints;

import tools.vitruv.framework.remote.server.rest.GetEndpoint;
import tools.vitruv.framework.remote.server.rest.HttpExchangeWrapper;
import tools.vitruv.framework.remote.common.rest.constants.ContentType;

/**
 * This endpoint can be used to check, if the server is running.
 */
public class HealthEndpoint implements GetEndpoint {
    @Override
    public String process(HttpExchangeWrapper wrapper) {
        wrapper.setContentType(ContentType.TEXT_PLAIN);
        return "Vitruv server up and running!";
    }
}
