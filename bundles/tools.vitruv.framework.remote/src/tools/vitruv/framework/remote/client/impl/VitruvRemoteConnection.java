package tools.vitruv.framework.remote.client.impl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Objects;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

import tools.vitruv.change.atomic.root.InsertRootEObject;
import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.framework.remote.client.VitruvClient;
import tools.vitruv.framework.remote.client.exception.BadServerResponseException;
import tools.vitruv.framework.remote.common.util.constants.ContentTypes;
import tools.vitruv.framework.remote.common.util.constants.EndpointPaths;
import tools.vitruv.framework.remote.common.util.constants.Headers;
import tools.vitruv.framework.remote.common.util.JsonMapper;
import tools.vitruv.framework.views.View;
import tools.vitruv.framework.views.ViewSelector;

/**
 * A {@link VitruvRemoteConnection} acts as a {@link HttpClient} to forward requests to a vitruvius server.
 * This enables the ability to perform actions on this remote vitruvius instance.
 */
public class VitruvRemoteConnection implements VitruvClient {

    private final int port;
    private final String url;
    private final HttpClient client;

    /**
     * Creates a new {@link VitruvRemoteConnection} using the given URL and port to connect to the vitruvius server.
     *
     * @param url  of the vitruvius server
     * @param port of the vitruvius server
     */
    public VitruvRemoteConnection(String url, int port) {
        this.client = HttpClient.newHttpClient();
        this.url = url;
        this.port = port;
    }

    /**
     * @inheritDoc
     */
    public List<String> queryViewTypes() throws BadServerResponseException {
        var request = HttpRequest.newBuilder()
                .uri(createURIFrom(url, port, EndpointPaths.VIEW_TYPES)).GET().build();
        try {
            var response = sendRequest(request);
            return JsonMapper.deserializeArrayOf(response, String.class);
        } catch (IOException e) {
            throw new BadServerResponseException(e);
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public ViewSelector querySelector(String typeName) throws BadServerResponseException {
        var request = HttpRequest.newBuilder()
                .uri(createURIFrom(url, port, EndpointPaths.VIEW_SELECTOR))
                .header(Headers.VIEW_TYPE, typeName)
                .GET()
                .build();
        try {
            var response = client.send(request, BodyHandlers.ofString());
            if (response.statusCode() != HttpURLConnection.HTTP_OK) {
                throw new BadServerResponseException(response.body());
            }
            var resource = JsonMapper.deserialize(response.body(), Resource.class);
            return new RemoteViewSelector(response.headers().firstValue(Headers.SELECTOR_UUID).get(), resource, this);
        } catch (IOException | InterruptedException e) {
            throw new BadServerResponseException(e);
        }
    }

    /**
     * Queries the vitruvius server to obtain the view using the given view selector.
     *
     * @param selector the {@link tools.vitruv.framework.views.ViewSelector} which should be used to create the view.
     * @return The view generated with the given view selector.
     * @throws BadServerResponseException if the server answered with a bad response or a connection error occurred.
     */
    View getView(RemoteViewSelector selector) throws BadServerResponseException {
        try {
            var request = HttpRequest.newBuilder()
                    .uri(createURIFrom(url, port, EndpointPaths.VIEW))
                    .header(Headers.SELECTOR_UUID, selector.getUUID())
                    .POST(BodyPublishers.ofString(JsonMapper.serialize(selector.getSelectionIds())))
                    .build();
            var response = client.send(request, BodyHandlers.ofString());
            if (response.statusCode() != HttpURLConnection.HTTP_OK) {
                throw new BadServerResponseException(response.body());
            }

            var rSet = JsonMapper.deserialize(response.body(), ResourceSet.class);
            return new RemoteView(response.headers().firstValue(Headers.VIEW_UUID).get(),
                    rSet, selector, this);
        } catch (IOException | InterruptedException e) {
            throw new BadServerResponseException(e);
        }
    }

    /**
     * Queries the vitruvius server to propagate the given changes for the view with the given UUID.
     *
     * @param uuid   of the changed view
     * @param change the changes performed on the affected view
     * @throws BadServerResponseException if the server answered with a bad response or a connection error occurred.
     */
    void propagateChanges(String uuid, VitruviusChange change) throws BadServerResponseException {
        try {
        	change.getEChanges().forEach(it -> {
        		if(it instanceof InsertRootEObject<?>) {
        			((InsertRootEObject<?>) it).setResource(null);
        		}
        	});
            var jsonBody = JsonMapper.serialize(change);
            var request = HttpRequest.newBuilder()
                    .uri(createURIFrom(url, port, EndpointPaths.VIEW))
                    .header(Headers.CONTENT_TYPE, ContentTypes.APPLICATION_JSON)
                    .header(Headers.VIEW_UUID, uuid)
                    .method("PATCH", BodyPublishers.ofString(jsonBody))
                    .build();
            sendRequest(request);
        } catch (IOException e) {
            throw new BadServerResponseException(e);
        }
    }

    /**
     * Queries the vitruvius server to close the view with the given.
     *
     * @param uuid of the view
     * @throws BadServerResponseException if the server answered with a bad response or a connection error occurred.
     */
    void closeView(String uuid) throws BadServerResponseException {
        var request = HttpRequest.newBuilder()
                .uri(createURIFrom(url, port, EndpointPaths.VIEW))
                .header(Headers.VIEW_UUID, uuid)
                .DELETE()
                .build();
        sendRequest(request);
    }

    /**
     * Queries the vitruvius serve to check if the view with the given id is closed.
     *
     * @param uuid of the view
     * @return {@code true} if the view is closed, {@code false} otherwise.
     * @throws BadServerResponseException if the server answered with a bad response or a connection error occurred.
     */
    boolean isViewClosed(String uuid) throws BadServerResponseException {
        var request = HttpRequest.newBuilder()
                .uri(createURIFrom(url, port, EndpointPaths.IS_VIEW_CLOSED))
                .header(Headers.VIEW_UUID, uuid)
                .GET()
                .build();
        return sendRequestAndCheckTFResult(request);
    }

    /**
     * Queries the vitruvius server to check if the view with the given id is outdated.
     *
     * @param uuid of the view
     * @return {@code true} if the view is outdated, {@code false} otherwise.
     */
    boolean isViewOutdated(String uuid) {
        var request = HttpRequest.newBuilder()
                .uri(createURIFrom(url, port, EndpointPaths.IS_VIEW_OUTDATED))
                .header(Headers.VIEW_UUID, uuid)
                .GET()
                .build();
        return sendRequestAndCheckTFResult(request);
    }

    /**
     * Queries the vitruvius server to update the view with the given id.
     *
     * @param uuid of the view
     * @return The updated {@link ResourceSet} of the view.
     * @throws BadServerResponseException if the server answered with a bad response or a connection error occurred.
     */
    ResourceSet updateView(String uuid) throws BadServerResponseException {
        var request = HttpRequest.newBuilder()
                .uri(createURIFrom(url, port, EndpointPaths.VIEW))
                .header(Headers.VIEW_UUID, uuid)
                .GET()
                .build();
        try {
            var response = sendRequest(request);
            return JsonMapper.deserialize(response, ResourceSet.class);
        } catch (IOException e) {
            throw new BadServerResponseException(e);
        }
    }

    private boolean sendRequestAndCheckTFResult(HttpRequest request) {
        var response = sendRequest(request);
        if (!Objects.equals(response, Boolean.TRUE.toString()) && !Objects.equals(response, Boolean.FALSE.toString())) {
            throw new BadServerResponseException("Expected response to be true or false! Actual: " + response);
        }
        return response.equals(Boolean.TRUE.toString());
    }

    private String sendRequest(HttpRequest request) {
        try {
            var response = client.send(request, BodyHandlers.ofString());
            if (response.statusCode() != HttpURLConnection.HTTP_OK) {
                throw new BadServerResponseException(response.body());
            }
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new BadServerResponseException(e);
        }
    }

    private static URI createURIFrom(String url, int port, String path) {
        return URI.create(String.format("http://%s:%d%s", url, port, path));
    }
}
