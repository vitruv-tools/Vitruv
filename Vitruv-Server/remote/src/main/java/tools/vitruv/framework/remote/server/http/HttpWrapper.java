package tools.vitruv.framework.remote.server.http;

import java.io.IOException;

/** This interface wraps an HTTP request/response from the underlying HTTP server implementation. */
public interface HttpWrapper {
  /**
   * Returns a response header.
   *
   * @param header Name of the header.
   * @return The value of the header.
   */
  String getRequestHeader(String header);

  /**
   * Returns a path segment from the request URI, relative to the registered context prefix.
   * Segment 0 is the first dynamic path segment (e.g., {@code branchName} or {@code versionId}).
   * Returns {@code null} if the index is out of range.
   *
   * @param index zero-based segment index after the context prefix.
   * @return the URL-decoded segment value, or {@code null}.
   */
  String getPathSegment(int index);

  /**
   * Returns a query parameter value from the request URI.
   *
   * @param name the parameter name.
   * @return the URL-decoded value, or {@code null} if the parameter is absent.
   */
  String getQueryParameter(String name);

  /**
   * Returns the request body converted to a String.
   *
   * @return The request body as String.
   * @throws IOException If the body cannot be read or if the conversion fails.
   */
  String getRequestBodyAsString() throws IOException;

  /**
   * Adds a value to the response header.
   *
   * @param header Name of the header.
   * @param value The value of the header to add.
   */
  void addResponseHeader(String header, String value);

  /**
   * Sets the content type of the response.
   *
   * @param type The content type.
   */
  void setContentType(String type);

  /**
   * Sends an HTTP response without a body.
   *
   * @param responseCode The status code of the response.
   * @throws IOException If the response cannot be sent.
   */
  void sendResponse(int responseCode) throws IOException;

  /**
   * Sends an HTTP response with a body.
   *
   * @param responseCode The status code of the response.
   * @param body The body of the response.
   * @throws IOException If the response cannot be sent.
   */
  void sendResponse(int responseCode, byte[] body) throws IOException;
}
