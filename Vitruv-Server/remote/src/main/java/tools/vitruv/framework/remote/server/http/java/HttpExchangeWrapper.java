package tools.vitruv.framework.remote.server.http.java;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import tools.vitruv.framework.remote.common.rest.constants.Header;
import tools.vitruv.framework.remote.server.http.HttpWrapper;

/** This is an implementation of the {@link HttpWrapper} for the Java built-in HTTP server. */
class HttpExchangeWrapper implements HttpWrapper {
  private final HttpExchange exchange;
  private final String contextPath;

  /**
   * Creates a new {@link HttpExchangeWrapper}.
   *
   * @param exchange    the {@link HttpExchange} to wrap.
   * @param contextPath the registered context path (used to compute path segments).
   */
  public HttpExchangeWrapper(HttpExchange exchange, String contextPath) {
    this.exchange = exchange;
    this.contextPath = contextPath;
  }

  @Override
  public void addResponseHeader(String header, String value) {
    exchange.getResponseHeaders().add(header, value);
  }

  @Override
  public void setContentType(String type) {
    exchange.getResponseHeaders().replace(Header.CONTENT_TYPE, List.of(type));
  }

  @Override
  public String getRequestHeader(String header) {
    return exchange.getRequestHeaders().getFirst(header);
  }

  @Override
  public String getPathSegment(int index) {
    String rawPath = exchange.getRequestURI().getRawPath();
    String prefix = contextPath.endsWith("/") ? contextPath : contextPath + "/";
    String relative = rawPath.startsWith(prefix)
        ? rawPath.substring(prefix.length())
        : (rawPath.startsWith(contextPath) ? rawPath.substring(contextPath.length()) : rawPath);
    if (relative.startsWith("/")) {
      relative = relative.substring(1);
    }
    if (relative.isEmpty()) {
      return null;
    }
    String[] parts = relative.split("/");
    if (index < 0 || index >= parts.length) {
      return null;
    }
    return URLDecoder.decode(parts[index], StandardCharsets.UTF_8);
  }

  @Override
  public String getQueryParameter(String name) {
    String query = exchange.getRequestURI().getQuery();
    if (query == null || query.isEmpty()) {
      return null;
    }
    for (String param : query.split("&")) {
      String[] kv = param.split("=", 2);
      if (kv.length == 2 && URLDecoder.decode(kv[0], StandardCharsets.UTF_8).equals(name)) {
        return URLDecoder.decode(kv[1], StandardCharsets.UTF_8);
      }
    }
    return null;
  }

  @Override
  public String getRequestBodyAsString() throws IOException {
    return new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
  }

  @Override
  public void sendResponse(int responseCode) throws IOException {
    exchange.sendResponseHeaders(responseCode, -1);
  }

  @Override
  public void sendResponse(int responseCode, byte[] body) throws IOException {
    exchange.sendResponseHeaders(responseCode, body.length);
    var outputStream = exchange.getResponseBody();
    outputStream.write(body);
    outputStream.flush();
    outputStream.close();
  }
}
