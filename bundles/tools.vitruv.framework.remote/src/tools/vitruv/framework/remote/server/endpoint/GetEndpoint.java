package tools.vitruv.remote.server.endpoint;

import spark.Spark;

/**
 * Represents a GET endpoint. Provides a standard implementation for the init() method.
 */
public abstract class GetEndpoint extends Endpoint {

	public GetEndpoint(String path) {
		super(path);
	}
	
	public GetEndpoint(String path, String acceptType) {
		super(path, acceptType);
	}

	@Override
	public void init() {
		Spark.get(path, acceptType, this::handleRequest);
	}
}
