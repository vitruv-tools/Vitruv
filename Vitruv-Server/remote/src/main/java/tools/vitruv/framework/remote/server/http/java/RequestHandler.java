package tools.vitruv.framework.remote.server.http.java;

import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import static java.net.HttpURLConnection.HTTP_OK;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import tools.vitruv.framework.remote.common.rest.constants.ContentType;
import tools.vitruv.framework.remote.server.exception.ServerHaltingException;
import tools.vitruv.framework.remote.server.rest.PathEndointCollector;

/** Represents an {@link HttpHandler}. */
class RequestHandler implements HttpHandler {
  private PathEndointCollector endpoints;

  /**
   * Creates a new {@link RequestHandler}.
   *
   * @param endpoints The endpoint collector providing the endpoints to handle requests.
   */
  RequestHandler(PathEndointCollector endpoints) {
    this.endpoints = endpoints;
  }

  /**
   * Handles the request when this end point is called.
   *
   * @param exchange An object encapsulating the HTTP request and response.
   */
  @Override
  public void handle(HttpExchange exchange) {
    var method = exchange.getRequestMethod();
    var wrapper = new HttpExchangeWrapper(exchange, endpoints.path());
    try {
      var response =
          switch (method) {
            case "GET" -> endpoints.getEndpoint().process(wrapper);
            case "PUT" -> endpoints.putEndpoint().process(wrapper);
            case "POST" -> endpoints.postEndpoint().process(wrapper);
            case "PATCH" -> endpoints.patchEndpoint().process(wrapper);
            case "DELETE" -> endpoints.deleteEndpoint().process(wrapper);
            default ->
                throw new ServerHaltingException(HTTP_NOT_FOUND, "Request method not supported!");
          };
      if (response != null) {
        wrapper.sendResponse(HTTP_OK, response.getBytes(StandardCharsets.UTF_8));
      } else {
        wrapper.sendResponse(HTTP_OK);
      }
    } catch (IOException | RuntimeException exception) {
      var statusCode = HTTP_INTERNAL_ERROR;
      if (exception instanceof ServerHaltingException haltingException) {
        statusCode = haltingException.getStatusCode();
      }
      String message = exception.getMessage() != null
          ? exception.getMessage()
          : exception.getClass().getName();
      wrapper.setContentType(ContentType.TEXT_PLAIN);
      try {
        wrapper.sendResponse(statusCode, message.getBytes(StandardCharsets.UTF_8));
      } catch (IOException e) {
        throw new IllegalStateException(
            "Sending a response (" + statusCode + " " + message + ") failed.", e);
      }
    }
  }
}
