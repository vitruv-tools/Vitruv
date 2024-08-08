package tools.vitruv.framework.remote.server.http.java;

import com.sun.net.httpserver.HttpExchange;

import tools.vitruv.framework.remote.common.rest.constants.Header;
import tools.vitruv.framework.remote.server.http.HttpWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * This is an implementation of the {@link HttpWrapper} for the Java built-in HTTP server.
 */
class HttpExchangeWrapper implements HttpWrapper {
    private final HttpExchange exchange;

    HttpExchangeWrapper(HttpExchange exchange) {
        this.exchange = exchange;
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
