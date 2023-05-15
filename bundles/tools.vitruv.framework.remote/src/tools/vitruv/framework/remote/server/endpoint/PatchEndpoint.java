package tools.vitruv.framework.remote.server.endpoint;

import spark.Spark;

/**
 * Represents a PATCH endpoint. Provides a standard implementation for the init() method.
 */
public abstract class PatchEndpoint extends Endpoint {

    public PatchEndpoint(String path) {
        super(path);
    }

    public PatchEndpoint(String path, String acceptType) {
        super(path, acceptType);
    }

    @Override
    public void init() {
        Spark.patch(path, acceptType, this::handleRequest);
    }
}
