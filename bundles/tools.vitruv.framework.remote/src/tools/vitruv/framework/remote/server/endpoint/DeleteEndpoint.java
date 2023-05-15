package tools.vitruv.framework.remote.server.endpoint;

import spark.Spark;

/**
 * Represents a DELETE endpoint. Provides a standard implementation for the init() method.
 */
public abstract class DeleteEndpoint extends Endpoint {

    public DeleteEndpoint(String path) {
        super(path);
    }

    public DeleteEndpoint(String path, String acceptType) {
        super(path, acceptType);
    }

    @Override
    public void init() {
        Spark.delete(path, acceptType, this::handleRequest);
    }
}
