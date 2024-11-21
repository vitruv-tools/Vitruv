package tools.vitruv.framework.remote.server.rest;

import tools.vitruv.framework.remote.server.exception.ServerHaltingException;
import tools.vitruv.framework.remote.server.http.HttpWrapper;

import static java.net.HttpURLConnection.*;

/**
 * Represents an REST endpoint.
 */
public interface RestEndpoint {
    /**
     * Processes a given HTTP request.
     *
     * @param wrapper An object wrapping an HTTP request/response.
     * @throws ServerHaltingException If an internal error occurred.
     */
    String process(HttpWrapper wrapper) throws ServerHaltingException;

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
}
