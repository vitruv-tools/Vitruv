package tools.vitruv.framework.remote.server.http.java;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import tools.vitruv.framework.remote.common.rest.constants.ContentType;
import tools.vitruv.framework.remote.server.exception.ServerHaltingException;
import tools.vitruv.framework.remote.server.rest.PathEndointCollector;

import java.nio.charset.StandardCharsets;

import static java.net.HttpURLConnection.*;

import java.io.IOException;

/**
 * Represents an {@link HttpHandler}.
 */
class RequestHandler implements HttpHandler {
    private PathEndointCollector endpoints;

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
        var wrapper = new HttpExchangeWrapper(exchange);
        try {
            var response = switch (method) {
                case "GET" -> endpoints.getEndpoint().process(wrapper);
                case "PUT" -> endpoints.putEndpoint().process(wrapper);
                case "POST" -> endpoints.postEndpoint().process(wrapper);
                case "PATCH" -> endpoints.patchEndpoint().process(wrapper);
                case "DELETE" -> endpoints.deleteEndpoint().process(wrapper);
                default -> throw new ServerHaltingException(HTTP_NOT_FOUND, "Request method not supported!");
            };
            if (response != null) {
                wrapper.sendResponse(HTTP_OK, response.getBytes(StandardCharsets.UTF_8));
            } else {
                wrapper.sendResponse(HTTP_OK);
            }
        } catch (Exception exception) {
            var statusCode = HTTP_INTERNAL_ERROR;
            if (exception instanceof ServerHaltingException haltingException) {
                statusCode = haltingException.getStatusCode();
            }
            wrapper.setContentType(ContentType.TEXT_PLAIN);
            try {
            	wrapper.sendResponse(statusCode, exception.getMessage().getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
            	throw new IllegalStateException("Sending a response (" + statusCode + " " + exception.getMessage() + ") failed.", e);
            }
        }
    }
}
