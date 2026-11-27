package tools.vitruv.framework.remote.client;

import java.nio.file.Path;
import tools.vitruv.framework.remote.client.impl.VitruvRemoteConnection;
import tools.vitruv.framework.remote.common.DefaultConnectionSettings;

/** A factory for creating {@link VitruvClient} instances to connect to a Vitruvius server. */
public class VitruvClientFactory {

  /**
   * Private constructor to prevent instantiation of this utility class.
   *
   * @throws UnsupportedOperationException if an appempt is made to instantiate this class.
   */
  private VitruvClientFactory() {
    throw new UnsupportedOperationException(
        "VitruvClientFactory is a utility class and cannot be instantiated.");
  }

  /**
   * Creates a new {@link VitruvClient} using the given host name or IP address and the standard
   * port of 8080.
   *
   * @param url The host name or IP address of the Vitruvius server.
   * @param temp A non-existing or empty directory for temporary files.
   * @return A {@link VitruvClient}.
   */
  public static VitruvClient create(String url, Path temp) {
    return create(url, DefaultConnectionSettings.STD_PORT, temp);
  }

  /**
   * Creates a new {@link VitruvClient} using the given host name or IP address and port.
   *
   * @param hostOrIp The host name or IP address of the Vitruvius server.
   * @param port Port of the Vitruvius server.
   * @param temp A non-existing or empty directory for temporary files.
   * @return A {@link VitruvClient}.
   */
  public static VitruvClient create(String hostOrIp, int port, Path temp) {
    return create(DefaultConnectionSettings.STD_PROTOCOL, hostOrIp, port, temp);
  }

  /**
   * Creates a new {@link VitruvClient} using the given protocol, host name or IP address, and port.
   *
   * @param protocol The protocol.
   * @param hostOrIp The host name of IP address of the Vitruvius server.
   * @param port Port of the Vitruvius server.
   * @param temp A non-existing or empty directory for temporary files.
   * @return A {@link VitruvClient}.
   */
  public static VitruvClient create(String protocol, String hostOrIp, int port, Path temp) {
    return new VitruvRemoteConnection(protocol, hostOrIp, port, temp);
  }
}
