package tools.vitruv.framework.remote.server.exception;

/**
 * Represents an exception which should be thrown when the processing of a REST-request is halted
 * due to an error. An HTTP-status code must be provided, since this exception is caught by the
 * {@link tools.vitruv.framework.remote.server.VitruvServer Server} in order to create error
 * response messages.
 */
public class ServerHaltingException extends RuntimeException {

  private final int statusCode;

  /**
   * Creates a new {@link ServerHaltingException}.
   *
   * @param statusCode the HTTP status code representing the error
   * @param msg the error message
   */
  public ServerHaltingException(int statusCode, String msg) {
    super(msg);
    this.statusCode = statusCode;
  }

  /**
   * Returns the HTTP status code representing the error.
   *
   * @return the HTTP status code
   */
  public int getStatusCode() {
    return statusCode;
  }
}
