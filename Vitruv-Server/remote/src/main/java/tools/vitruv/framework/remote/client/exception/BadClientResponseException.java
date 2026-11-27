package tools.vitruv.framework.remote.client.exception;

/** Exception thrown when the client receives a bad response from the server. */
public class BadClientResponseException extends RuntimeException {
  /** Creates a new BadClientResponseException. */
  public BadClientResponseException() {
    super();
  }

  /**
   * Creates a new BadClientResponseException with the given message.
   *
   * @param msg the message of the exception
   */
  public BadClientResponseException(String msg) {
    super(msg);
  }

  /**
   * Creates a new BadClientResponseException with the given message and cause.
   *
   * @param msg the message of the exception
   * @param cause the cause of the exception
   */
  public BadClientResponseException(String msg, Throwable cause) {
    super(msg, cause);
  }

  /**
   * Creates a new BadClientResponseException with the given cause.
   *
   * @param cause the cause of the exception
   */
  public BadClientResponseException(Throwable cause) {
    super(cause);
  }
}
