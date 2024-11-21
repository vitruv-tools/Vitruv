package tools.vitruv.framework.remote.server.http.java;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;
import tools.vitruv.framework.remote.server.rest.PathEndointCollector;

public class VitruvJavaHttpServer {
    private final HttpServer server;

    public VitruvJavaHttpServer(String host, int port, List<PathEndointCollector> endpoints) throws IOException {
        this.server = HttpServer.create(new InetSocketAddress(host, port), 0);
        endpoints.forEach(endp -> {
        	server.createContext(endp.path(), new RequestHandler(endp));
        });
    }
    
    public void start() {
        server.start();
    }

    public void stop() {
        server.stop(0);
    }
}
