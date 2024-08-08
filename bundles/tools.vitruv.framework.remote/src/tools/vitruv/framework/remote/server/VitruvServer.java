package tools.vitruv.framework.remote.server;

import java.io.IOException;
import tools.vitruv.framework.remote.common.util.JsonMapper;
import tools.vitruv.framework.remote.server.http.java.*;
import tools.vitruv.framework.remote.server.rest.endpoints.EndpointsProvider;
import tools.vitruv.framework.vsum.VirtualModel;

/**
 * A vitruv server wraps a REST based API around a {@link VirtualModel VSUM}. Therefore,
 * it takes a {@link VirtualModelInitializer} which is responsible to create an instance
 * of a {@link VirtualModel virtual model}. Once the serve is started, the API can be used by the
 * vitruv client to perform remote actions on the VSUM.
 */
public class VitruvServer {
    public static int STD_PORT = 8080;

    private final VitruvJavaHttpServer server;

    /**
     * Creates a new {@link VitruvServer} using the given {@link VirtualModelInitializer}.
     * Sets the port which is used to open the server on to the given one.
     *
     * @param modelInitializer The initializer which creates an {@link VirtualModel}.
     * @param port             The port to open to server on.
     */
    public VitruvServer(VirtualModelInitializer modelInitializer, int port) throws IOException {
    	var model = modelInitializer.init();
        var mapper = new JsonMapper(model.getFolder());
        var endpoints = EndpointsProvider.getAllEndpoints(model, mapper);
        
        this.server = new VitruvJavaHttpServer(null, port, endpoints);
    }

    /**
     * Creates a new {@link VitruvServer} using the given {@link VirtualModelInitializer}.
     * Sets the port which is used to open the server on to 8080.
     *
     * @param modelInitializer The initializer which creates an {@link InternalVirtualModel}.
     */
    public VitruvServer(VirtualModelInitializer modelInitializer) throws IOException {
        this(modelInitializer, STD_PORT);
    }

    /**
     * Starts the Vitruvius server.
     */
    public void start() {
        server.start();
    }

    /**
     * Stops the Vitruvius server.
     */
    public void stop() {
        server.stop();
    }
}
