package tools.vitruv.remote.server.endpoint.impl;

import spark.Request;
import spark.Response;
import tools.vitruv.remote.server.endpoint.GetEndpoint;

/**
 * This endpoint can be used to check, if the server is running.
 */
public class HealthEndpoint extends GetEndpoint {

	public HealthEndpoint() {
		super("/health");
	}

	@Override
	public Object handleRequest(Request request, Response response) {
		return "Vitruv Server is up and running!";
	}
}
