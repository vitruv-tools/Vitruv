package tools.vitruv.framework.remote.server.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import tools.vitruv.framework.remote.common.util.constants.ContentType;
import tools.vitruv.framework.remote.common.util.HttpExchangeWrapper;
import tools.vitruv.framework.remote.common.util.JsonMapper;
import tools.vitruv.framework.remote.server.endpoint.*;
import tools.vitruv.framework.remote.server.exception.ServerHaltingException;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;

import java.nio.charset.StandardCharsets;

import static java.net.HttpURLConnection.*;

/**
 * Represents a {@link HttpHandler}.
 */
public abstract class RequestHandler implements HttpHandler {

    /**
     * The path name of this handler
     */
    private final String path;

    protected Endpoint.Get getEndpoint;
    protected Endpoint.Put putEndpoint;
    protected Endpoint.Post postEndpoint;
    protected Endpoint.Patch patchEndpoint;
    protected Endpoint.Delete deleteEndpoint;


    public RequestHandler(String path) {
        this.path = path;
        this.getEndpoint = new Endpoint.Get() {
            @Override
            public String process(HttpExchangeWrapper wrapper) throws ServerHaltingException {
                throw notFound("Get mapping for this request path not found!");
            }
        };
        this.postEndpoint = new Endpoint.Post() {
            @Override
            public String process(HttpExchangeWrapper wrapper) throws ServerHaltingException {
                throw notFound("Post mapping for this request path not found!");
            }
        };
        this.patchEndpoint = new Endpoint.Patch() {
            @Override
            public String process(HttpExchangeWrapper wrapper) throws ServerHaltingException {
                throw notFound("Patch mapping for this request path not found!");
            }
        };
        this.deleteEndpoint = new Endpoint.Delete() {
            @Override
            public String process(HttpExchangeWrapper wrapper) throws ServerHaltingException {
                throw notFound("Delete mapping for this request path not found!");
            }
        };
        this.putEndpoint = new Endpoint.Put() {
            @Override
            public String process(HttpExchangeWrapper wrapper) throws ServerHaltingException {
                throw notFound("Put mapping for this request path not found!");
            }
        };
    }

    public String getPath() {
        return path;
    }

    /**
     * Initializes the supported endpoints of this request handler.
     */
    public abstract void init(InternalVirtualModel model, JsonMapper mapper);

    /**
     * Handles the request, when this end point is called.
     *
     * @param exchange an object encapsulating the HTTP request
     */
    public void handle(HttpExchange exchange) {
        var method = exchange.getRequestMethod();
        var wrapper = new HttpExchangeWrapper(exchange);
        try {
            var response = switch (method) {
                case "GET" -> getEndpoint.process(wrapper);
                case "PUT" -> putEndpoint.process(wrapper);
                case "POST" -> postEndpoint.process(wrapper);
                case "PATCH" -> patchEndpoint.process(wrapper);
                case "DELETE" -> deleteEndpoint.process(wrapper);
                default -> throw new ServerHaltingException(HTTP_NOT_FOUND, "Request method not supported!");
            };
            if (response != null) {
                wrapper.sendResponseWithBody(HTTP_OK, response.getBytes(StandardCharsets.UTF_8));
            } else {
                wrapper.sendResponse(HTTP_OK);
            }
        } catch (Exception exception) {
            var statusCode = HTTP_INTERNAL_ERROR;
            if (exception instanceof ServerHaltingException haltingException) {
                statusCode = haltingException.getStatusCode();
            }
            wrapper.setContentType(ContentType.TEXT_PLAIN);
            wrapper.sendResponseWithBody(statusCode, exception.getMessage().getBytes(StandardCharsets.UTF_8));
        }
    }
}
