package tools.vitruv.framework.remote.client;

import tools.vitruv.framework.remote.client.exception.BadServerResponseException;
import tools.vitruv.framework.views.ViewSelector;

import java.util.List;

/**
 * A vitruv client can connect to a vitruv server and query view type names and views from this server.
 */
public interface VitruvClient {

    /**
     * Queries the vitruvius server to gain a list of all available view type names.
     *
     * @return A {@link List} of view type names.
     * @throws BadServerResponseException if the server answered with a bad response or a connection error occurred.
     */
    List<String> queryViewTypes() throws BadServerResponseException;

    /**
     * Queries the vitruvius server to obtain the selector described through the view type with the given name.
     *
     * @param typeName the name of the view type
     * @return A {@link ViewSelector} generated from the given view type.
     * @throws BadServerResponseException if the server answered with a bad response or a connection error occurred.
     */
    ViewSelector querySelector(String typeName) throws BadServerResponseException;
}
