package tools.vitruv.framework.remote.server.http.java;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;
import tools.vitruv.framework.remote.server.rest.PathEndointCollector;

public class VitruvJavaHttpServer {
  private final HttpServer server;

  /**
   * Creates a Vitruvius HTTP server on the given host and port, registering the given endpoints.
   */
  public VitruvJavaHttpServer(String host, int port, List<PathEndointCollector> endpoints)
      throws IOException {
    this.server = HttpServer.create(new InetSocketAddress(host, port), 0);
    endpoints.forEach(endp -> server.createContext(endp.path(), new RequestHandler(endp)));
  }

  /** Starts the Vitruvius server. */
  public void start() {
    server.start();
  }

  /** Stops the Vitruvius server. */
  public void stop() {
    server.stop(0);
  }

  /**
   * Returns the port the server is bound to.
   * When the server was created with port {@code 0}, the actual assigned port is returned.
   *
   * @return the local port number.
   */
  public int getPort() {
    return server.getAddress().getPort();
  }
}
