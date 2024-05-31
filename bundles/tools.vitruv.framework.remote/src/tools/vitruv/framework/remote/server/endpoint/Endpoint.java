package tools.vitruv.framework.remote.server.endpoint;

import com.sun.net.httpserver.HttpExchange;
import tools.vitruv.framework.remote.common.util.HttpExchangeWrapper;
import tools.vitruv.framework.remote.server.exception.ServerHaltingException;

import static java.net.HttpURLConnection.*;

/**
 * Represents an REST endpoint.
 */
public interface Endpoint {
    /**
     * Processes the given HTTP request
     *
     * @param wrapper An object containing utility functions for a specific {@link HttpExchange}
     * @throws ServerHaltingException if an internal error occurred
     */
    String process(HttpExchangeWrapper wrapper) throws ServerHaltingException;

    /**
     * Halts the execution of the requested endpoint and returns the status code NOT FOUND with the given message.
     *
     * @param msg A message containing the reason of halting the execution.
     */
    default ServerHaltingException notFound(String msg) {
        return new ServerHaltingException(HTTP_NOT_FOUND, msg);
    }

    /**
     * Halts the execution of the requested endpoint and returns the status code INTERNAL SERVER ERROR with the given message.
     *
     * @param msg A message containing the reason of halting the execution.
     */
    default ServerHaltingException internalServerError(String msg) {
        return new ServerHaltingException(HTTP_INTERNAL_ERROR, msg);
    }

    interface Get extends Endpoint {
    }

    interface Delete extends Endpoint {
    }

    interface Post extends Endpoint {
    }

    interface Patch extends Endpoint {
    }

    interface Put extends Endpoint {
    }
}
