package tools.vitruv.framework.remote.server.http.java;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import tools.vitruv.framework.remote.common.rest.constants.ContentType;
import tools.vitruv.framework.remote.common.util.JsonMapper;
import tools.vitruv.framework.remote.server.exception.ServerHaltingException;
import tools.vitruv.framework.remote.server.rest.DeleteEndpoint;
import tools.vitruv.framework.remote.server.rest.GetEndpoint;
import tools.vitruv.framework.remote.server.rest.HttpExchangeWrapper;
import tools.vitruv.framework.remote.server.rest.PatchEndpoint;
import tools.vitruv.framework.remote.server.rest.PostEndpoint;
import tools.vitruv.framework.remote.server.rest.PutEndpoint;
import tools.vitruv.framework.remote.server.rest.RestEndpoint;
import tools.vitruv.framework.remote.server.rest.endpoints.*;
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

    protected GetEndpoint getEndpoint;
    protected PutEndpoint putEndpoint;
    protected PostEndpoint postEndpoint;
    protected PatchEndpoint patchEndpoint;
    protected DeleteEndpoint deleteEndpoint;


    public RequestHandler(String path) {
        this.path = path;
        this.getEndpoint = new GetEndpoint() {
            @Override
            public String process(HttpExchangeWrapper wrapper) throws ServerHaltingException {
                throw notFound("Get mapping for this request path not found!");
            }
        };
        this.postEndpoint = new PostEndpoint() {
            @Override
            public String process(HttpExchangeWrapper wrapper) throws ServerHaltingException {
                throw notFound("Post mapping for this request path not found!");
            }
        };
        this.patchEndpoint = new PatchEndpoint() {
            @Override
            public String process(HttpExchangeWrapper wrapper) throws ServerHaltingException {
                throw notFound("Patch mapping for this request path not found!");
            }
        };
        this.deleteEndpoint = new DeleteEndpoint() {
            @Override
            public String process(HttpExchangeWrapper wrapper) throws ServerHaltingException {
                throw notFound("Delete mapping for this request path not found!");
            }
        };
        this.putEndpoint = new PutEndpoint() {
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
