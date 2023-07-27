package tools.vitruv.framework.remote.common.util;

import com.sun.net.httpserver.HttpExchange;
import tools.vitruv.framework.remote.common.util.constants.Header;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class HttpExchangeWrapper {

    private final HttpExchange exchange;

    public HttpExchangeWrapper(HttpExchange exchange) {
        this.exchange = exchange;
    }

    public void addResponseHeader(String header, String value) {
        exchange.getResponseHeaders().add(header, value);
    }

    public void setContentType(String type) {
        exchange.getResponseHeaders().replace(Header.CONTENT_TYPE, List.of(type));
    }

    public String getRequestHeader(String header) {
        return exchange.getRequestHeaders().getFirst(header);
    }

    public String getRequestBodyAsString() throws IOException {
        return new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
    }

    public void sendResponse(int responseCode) {
        try {
            exchange.sendResponseHeaders(responseCode, -1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendResponseWithBody(int responseCode, byte[] body) {
        try {
            exchange.sendResponseHeaders(responseCode, body.length);
            var outputStream = exchange.getResponseBody();
            outputStream.write(body);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
