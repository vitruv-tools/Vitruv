package tools.vitruv.framework.remote.server.rest;

import static java.net.HttpURLConnection.HTTP_BAD_METHOD;
import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR;

import tools.vitruv.framework.remote.server.exception.ServerHaltingException;
import tools.vitruv.framework.remote.server.http.HttpWrapper;

/** Represents an REST endpoint. */
public interface RestEndpoint {
  /**
   * Processes a given HTTP request.
   *
   * @param wrapper An object wrapping an HTTP request/response.
   * @throws ServerHaltingException If an internal error occurred.
   */
  String process(HttpWrapper wrapper) throws ServerHaltingException;

  /**
   * Halts the execution of the requested endpoint and returns the status code NOT FOUND with the
   * given message.
   *
   * @param msg A message containing the reason of halting the execution.
   * @return A ServerHaltingException representing the NOT FOUND error.
   */
  default ServerHaltingException notFound(String msg) {
    return new ServerHaltingException(HTTP_BAD_METHOD, msg);
  }

  /**
   * Halts the execution of the requested endpoint and returns the status code BAD REQUEST with the
   * given message.
   *
   * @param msg A message containing the reason of halting the execution.
   * @return A ServerHaltingException representing the BAD REQUEST error.
   */
  default ServerHaltingException badRequest(String msg) {
    return new ServerHaltingException(HTTP_BAD_REQUEST, msg);
  }

  /**
   * Halts the execution of the requested endpoint and returns the status code INTERNAL SERVER ERROR
   * with the given message.
   *
   * @param msg A message containing the reason of halting the execution.
   * @return A ServerHaltingException representing the INTERNAL SERVER ERROR.
   */
  default ServerHaltingException internalServerError(String msg) {
    return new ServerHaltingException(HTTP_INTERNAL_ERROR, msg);
  }
}
