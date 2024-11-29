package tools.vitruv.framework.remote.server;

import java.io.IOException;

import tools.vitruv.framework.remote.common.DefaultConnectionSettings;
import tools.vitruv.framework.remote.common.json.JsonMapper;
import tools.vitruv.framework.remote.server.http.java.VitruvJavaHttpServer;
import tools.vitruv.framework.remote.server.rest.endpoints.EndpointsProvider;
import tools.vitruv.framework.vsum.VirtualModel;

/**
 * A Vitruvius server wraps a REST-based API around a {@link VirtualModel VSUM}. Therefore,
 * it takes a {@link VirtualModelInitializer} which is responsible to create an instance
 * of a {@link VirtualModel virtual model}. Once the server is started, the API can be used by the
 * Vitruvius client to perform remote actions on the VSUM.
 */
public class VitruvServer {
    private final VitruvJavaHttpServer server;

    /**
     * Creates a new {@link VitruvServer} using the given {@link VirtualModelInitializer}.
     * Sets host name or IP address and port which are used to open the server.
     *
     * @param modelInitializer The initializer which creates an {@link VirtualModel}.
     * @param port             The port to open to server on.
     * @param hostOrIp         The host name or IP address to which the server is bound.
     */
    public VitruvServer(VirtualModelInitializer modelInitializer, int port, String hostOrIp) throws IOException {
    	var model = modelInitializer.init();
        var mapper = new JsonMapper(model.getFolder());
        var endpoints = EndpointsProvider.getAllEndpoints(model, mapper);

        this.server = new VitruvJavaHttpServer(hostOrIp, port, endpoints);
    }
    
    /**
     * Creates a new {@link VitruvServer} using the given {@link VirtualModelInitializer}.
     * Sets the port which is used to open the server on to the given one.
     *
     * @param modelInitializer The initializer which creates an {@link VirtualModel}.
     * @param port             The port to open to server on.
     */
    public VitruvServer(VirtualModelInitializer modelInitializer, int port) throws IOException {
    	this(modelInitializer, port, DefaultConnectionSettings.STD_HOST);
    }

    /**
     * Creates a new {@link VitruvServer} using the given {@link VirtualModelInitializer}.
     * Sets the port which is used to open the server on to 8080.
     *
     * @param modelInitializer The initializer which creates an {@link tools.vitruv.framework.vsum.internal.InternalVirtualModel}.
     */
    public VitruvServer(VirtualModelInitializer modelInitializer) throws IOException {
        this(modelInitializer, DefaultConnectionSettings.STD_PORT);
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
