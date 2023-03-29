package tools.vitruv.framework.remote.server.endpoint;

import spark.Spark;

/**
 * Represents a POST endpoint. Provides a standard implementation for the init() method.
 */
public abstract class PostEndpoint extends Endpoint{
	
	public PostEndpoint(String path) {
		super(path);
	}
	
	public PostEndpoint(String path, String acceptType) {
		super(path, acceptType);
	}

	@Override
	public void init() {
		Spark.post(path, acceptType, this::handleRequest);
	}
}
