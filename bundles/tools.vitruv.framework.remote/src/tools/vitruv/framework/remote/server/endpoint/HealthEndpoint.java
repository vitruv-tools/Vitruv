package tools.vitruv.framework.remote.server.endpoint;

import tools.vitruv.framework.remote.common.util.constants.ContentType;
import tools.vitruv.framework.remote.common.util.HttpExchangeWrapper;

/**
 * This endpoint can be used to check, if the server is running.
 */
public class HealthEndpoint implements Endpoint.Get {
    @Override
    public String process(HttpExchangeWrapper wrapper) {
        wrapper.setContentType(ContentType.TEXT_PLAIN);
        return "Vitruv server up and running!";
    }
}
