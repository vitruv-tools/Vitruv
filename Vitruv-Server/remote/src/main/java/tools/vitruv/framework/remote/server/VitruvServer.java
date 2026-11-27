package tools.vitruv.framework.remote.server;

import java.io.IOException;
import java.util.List;
import tools.vitruv.framework.remote.common.DefaultConnectionSettings;
import tools.vitruv.framework.remote.common.json.JsonMapper;
import tools.vitruv.framework.remote.server.http.java.VitruvJavaHttpServer;
import tools.vitruv.framework.remote.server.rest.PathEndointCollector;
import tools.vitruv.framework.remote.server.rest.endpoints.EndpointsProvider;
import tools.vitruv.framework.vsum.VirtualModel;
import tools.vitruv.framework.vsum.branch.BranchManager;
import tools.vitruv.framework.vsum.branch.CommitManager;
import tools.vitruv.framework.vsum.branch.MergeManager;
import tools.vitruv.framework.vsum.branch.handler.PostCheckoutHandler;
import tools.vitruv.framework.vsum.versioning.VersioningService;

/**
 * A Vitruvius server wraps a REST-based API around a {@link VirtualModel VSUM}. Therefore, it takes
 * a {@link VirtualModelInitializer} which is responsible to create an instance of a {@link
 * VirtualModel virtual model}. Once the server is started, the API can be used by the Vitruvius
 * client to perform remote actions on the VSUM.
 */
public class VitruvServer {
  private final VitruvJavaHttpServer server;

  /**
   * Creates a new {@link VitruvServer} using the given {@link VirtualModelInitializer}. Sets host
   * name or IP address and port which are used to open the server.
   *
   * @param modelInitializer The initializer which creates an {@link VirtualModel}.
   * @param port The port to open to server on.
   * @param hostOrIp The host name or IP address to which the server is bound.
   */
  public VitruvServer(VirtualModelInitializer modelInitializer, int port, String hostOrIp)
      throws IOException {
    VirtualModel model = modelInitializer.init();
    JsonMapper mapper = new JsonMapper(model.getFolder());
    List<PathEndointCollector> endpoints = EndpointsProvider.getAllEndpoints(model, mapper);

    this.server = new VitruvJavaHttpServer(hostOrIp, port, endpoints);
  }

  /**
   * Creates a new {@link VitruvServer} using the given {@link VirtualModelInitializer}. Sets the
   * port which is used to open the server on to the given one.
   *
   * @param modelInitializer The initializer which creates an {@link VirtualModel}.
   * @param port The port to open to server on.
   */
  public VitruvServer(VirtualModelInitializer modelInitializer, int port) throws IOException {
    this(modelInitializer, port, DefaultConnectionSettings.STD_HOST);
  }

  /**
   * Creates a new {@link VitruvServer} using the given {@link VirtualModelInitializer}. Sets the
   * port which is used to open the server on to 8080.
   *
   * @param modelInitializer The initializer which creates an {@link
   *     tools.vitruv.framework.vsum.internal.InternalVirtualModel}.
   */
  public VitruvServer(VirtualModelInitializer modelInitializer) throws IOException {
    this(modelInitializer, DefaultConnectionSettings.STD_PORT);
  }

  /**
   * Creates a new {@link VitruvServer} with optional branching, commit, merge, and versioning
   * support. Pass {@code null} for any manager whose endpoint group should be omitted.
   *
   * @param modelInitializer the initializer which creates a {@link VirtualModel}.
   * @param port the port to open the server on.
   * @param hostOrIp the host name or IP address to bind to.
   * @param branchManager the branch manager, or {@code null} to omit branch endpoints.
   * @param commitManager the commit manager, or {@code null} to omit commit endpoints.
   * @param mergeManager the merge manager, or {@code null} to omit the merge endpoint.
   * @param versioningService the versioning service, or {@code null} to omit version endpoints.
   */
  public VitruvServer(VirtualModelInitializer modelInitializer, int port, String hostOrIp,
      BranchManager branchManager, CommitManager commitManager,
      MergeManager mergeManager, VersioningService versioningService) throws IOException {
    VirtualModel model = modelInitializer.init();
    JsonMapper mapper = new JsonMapper(model.getFolder());
    if (branchManager != null) {
      branchManager.setPostCheckoutHandler(new PostCheckoutHandler(model));
    }
    List<PathEndointCollector> endpoints = EndpointsProvider.builder(model, mapper)
        .branchManager(branchManager)
        .commitManager(commitManager)
        .mergeManager(mergeManager)
        .versioningService(versioningService)
        .build();
    this.server = new VitruvJavaHttpServer(hostOrIp, port, endpoints);
  }

  /**
   * Creates a new {@link VitruvServer} with optional manager support on the default host
   * and port 8080.
   *
   * @param modelInitializer the initializer which creates a {@link VirtualModel}.
   * @param branchManager the branch manager, or {@code null} to omit branch endpoints.
   * @param commitManager the commit manager, or {@code null} to omit commit endpoints.
   * @param mergeManager the merge manager, or {@code null} to omit the merge endpoint.
   * @param versioningService the versioning service, or {@code null} to omit version endpoints.
   */
  public VitruvServer(VirtualModelInitializer modelInitializer,
      BranchManager branchManager, CommitManager commitManager,
      MergeManager mergeManager, VersioningService versioningService) throws IOException {
    this(modelInitializer, DefaultConnectionSettings.STD_PORT, DefaultConnectionSettings.STD_HOST,
        branchManager, commitManager, mergeManager, versioningService);
  }

  /** Starts the Vitruvius server. */
  public void start() {
    server.start();
  }

  /** Stops the Vitruvius server. */
  public void stop() {
    server.stop();
  }

  /**
   * Returns the port the server is bound to.
   * Useful when the server was started on port {@code 0} for dynamic port allocation.
   *
   * @return the local port number.
   */
  public int getPort() {
    return server.getPort();
  }
}
