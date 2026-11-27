package tools.vitruv.framework.remote.server.rest.endpoints;

import tools.vitruv.framework.remote.common.rest.constants.ContentType;
import tools.vitruv.framework.remote.server.http.HttpWrapper;
import tools.vitruv.framework.remote.server.rest.GetEndpoint;

/** This endpoint can be used to check, if the server is running. */
public class HealthEndpoint implements GetEndpoint {
  @Override
  public String process(HttpWrapper wrapper) {
    wrapper.setContentType(ContentType.TEXT_PLAIN);
    return "Vitruv server up and running!";
  }
}
