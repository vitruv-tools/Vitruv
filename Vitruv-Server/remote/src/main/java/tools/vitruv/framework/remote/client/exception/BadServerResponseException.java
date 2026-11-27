package tools.vitruv.framework.remote.client.exception;

/** Exception thrown when the client receives a bad response from the server. */
public class BadServerResponseException extends RuntimeException {
  private final int statusCode;

  /** Constructs a new BadServerResponseException with various options. */
  public BadServerResponseException() {
    super();
    this.statusCode = -1;
  }

  /**
   * Constructs a new BadServerResponseException with the specified detail message.
   *
   * @param msg the detail message
   */
  public BadServerResponseException(String msg) {
    super(msg);
    this.statusCode = -1;
  }

  /**
   * Constructs a new BadServerResponseException with the specified detail message and status code.
   *
   * @param msg the detail message
   * @param statusCode the HTTP status code of the bad response
   */
  public BadServerResponseException(String msg, int statusCode) {
    super(msg);
    this.statusCode = statusCode;
  }

  /**
   * Constructs a new BadServerResponseException with the specified detail message and cause.
   *
   * @param msg the detail message
   * @param cause the cause of the exception
   */
  public BadServerResponseException(String msg, Throwable cause) {
    super(msg, cause);
    this.statusCode = -1;
  }

  /**
   * Constructs a new BadServerResponseException with the specified cause.
   *
   * @param cause the cause of the exception
   */
  public BadServerResponseException(Throwable cause) {
    super(cause);
    this.statusCode = -1;
  }

  /**
   * Gets the HTTP status code associated with the bad response.
   *
   * @return the HTTP status code
   */
  public int getStatusCode() {
    return statusCode;
  }
}
