package tools.vitruv.framework.remote.common;

/**
 * Defines default settings for the connection between a Vitruvius server and client. They are only
 * used if no other settings are provided.
 */
public class DefaultConnectionSettings {
  /** The standard protocol used for the connection. */
  public static final String STD_PROTOCOL = "http";

  /** The standard host used for the connection. */
  public static final String STD_HOST = "localhost";

  /** The standard port used for the connection. */
  public static final int STD_PORT = 8080;

  private DefaultConnectionSettings() {}
}
