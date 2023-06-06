package tools.vitruv.framework.remote.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Set;

import com.sun.net.httpserver.HttpServer;
import tools.vitruv.framework.remote.server.handler.*;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;

/**
 * A vitruv server wraps a REST based API around a {@link tools.vitruv.framework.vsum.VirtualModel VSUM}. Therefore,
 * it takes a {@link VirtualModelInitializer} which is responsible to create an instance
 * of a {@link InternalVirtualModel virtual model}. Once the serve is started, the API can be used by the
 * vitruv client to perform remote actions on the VSUM.
 */
public class VitruvServer {

    public static int STD_PORT = 8080;

    private final HttpServer server;

    /**
     * Creates a new {@link VitruvServer} using the given {@link VirtualModelInitializer}.
     * Sets the port which is used to open the server on to the given one.
     *
     * @param modelInitializer the initializer which creates a {@link InternalVirtualModel}
     * @param port             the port to open to server on
     */
    public VitruvServer(VirtualModelInitializer modelInitializer, int port) throws IOException {
        this.server = HttpServer.create(new InetSocketAddress(port), 0);

        var model = modelInitializer.init();
        var handlers = Set.of(new HealthHandler(), new IsViewClosedHandler(), new IsViewOutdatedHandler(),
                new ViewHandler(), new ViewTypesHandler(), new ViewSelectorHandler());
        handlers.forEach(it -> {
            it.init(model);
            server.createContext(it.getPath(), it);
        });
    }

    /**
     * Creates a new {@link VitruvServer} using the given {@link VirtualModelInitializer}.
     * Sets the port which is used to open the server on to 8080.
     *
     * @param modelInitializer the initializer which creates a {@link InternalVirtualModel}
     */
    public VitruvServer(VirtualModelInitializer modelInitializer) throws IOException {
        this(modelInitializer, STD_PORT);
    }

    /**
     * Starts the vitruv server.
     */
    public void start() {
        server.start();
    }

    /**
     * Stops the vitruv server.
     */
    public void stop() {
        server.stop(5);
    }
}
