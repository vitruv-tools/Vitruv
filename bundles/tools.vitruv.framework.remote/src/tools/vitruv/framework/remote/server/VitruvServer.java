package tools.vitruv.remote.server;

import java.util.HashSet;

import spark.Spark;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;
import tools.vitruv.remote.common.util.ContentTypes;
import tools.vitruv.remote.server.endpoint.Endpoint;
import tools.vitruv.remote.server.endpoint.impl.*;
import tools.vitruv.remote.server.exception.ServerHaltingException;

/**
 * A vitruv server wraps a REST based API around a {@link tools.vitruv.framework.vsum.VirtualModel VSUM}. Therefore,
 * it takes a {@link VirtualModelInitializer} which is responsible to create an instance
 * of a {@link InternalVirtualModel virtual model}. Once the serve is started, the API can be used by the
 * vitruv client to perform remote actions on the VSUM.
 */
public class VitruvServer {

    private int port = 8080;

    private final HashSet<Endpoint> endpoints;

    /**
     * Creates a new {@link VitruvServer} using the given {@link VirtualModelInitializer}.
     * Sets the port which is used to open the server on to the given one.
     *
     * @param modelInitializer the initializer which creates a {@link InternalVirtualModel}
     * @param port             the port to open to server on
     */
    public VitruvServer(VirtualModelInitializer modelInitializer, int port) {
        this(modelInitializer);
        this.port = port;
    }

    /**
     * Creates a new {@link VitruvServer} using the given {@link VirtualModelInitializer}.
     * Sets the port which is used to open the server on to 8080.
     *
     * @param modelInitializer the initializer which creates a {@link InternalVirtualModel}
     */
    public VitruvServer(VirtualModelInitializer modelInitializer) {
        InternalVirtualModel model = modelInitializer.init();

        endpoints = new HashSet<>();
        endpoints.add(new HealthEndpoint());
        endpoints.add(new ViewTypesEndpoint(model));
        endpoints.add(new ViewEndpoint(model));
        endpoints.add(new ChangePropagationEndpoint(model));
        endpoints.add(new CloseViewEndpoint());
        endpoints.add(new IsViewClosedEndpoint());
        endpoints.add(new IsViewOutdatedEndpoint());
        endpoints.add(new UpdateViewEndpoint());
    }

    /**
     * Starts the vitruv server.
     */
    public void start() {
        Spark.port(port);
        Spark.exception(Exception.class, (ex, req, res) -> {
            int statusCode = 500;
            if (ex instanceof ServerHaltingException s) {
                statusCode = s.getStatusCode();
            }
            res.status(statusCode);
            res.type(ContentTypes.TEXT_PLAIN);
            res.body(ex.getMessage());
        });
        endpoints.forEach(Endpoint::init);
    }

    /**
     * Stops the vitruv server.
     */
    public void stop() {
        Spark.stop();
    }

    /**
     * Blocks until the server is initialized.
     */
    public void awaitInit() {
        Spark.awaitInitialization();
    }
}
