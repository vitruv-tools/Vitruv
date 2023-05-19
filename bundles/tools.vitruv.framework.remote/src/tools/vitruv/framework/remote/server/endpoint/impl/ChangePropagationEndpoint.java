package tools.vitruv.framework.remote.server.endpoint.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import spark.Request;
import spark.Response;

import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.framework.remote.common.util.EndpointPaths;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;
import tools.vitruv.framework.remote.common.util.ContentTypes;
import tools.vitruv.framework.remote.common.util.JsonMapper;
import tools.vitruv.framework.remote.server.endpoint.PostEndpoint;

/**
 * This endpoint applies given {@link VitruviusChange}s to the VSUM.
 */
public class ChangePropagationEndpoint extends PostEndpoint {

    InternalVirtualModel model;

    public ChangePropagationEndpoint(InternalVirtualModel model) {
        super(EndpointPaths.VIEW, ContentTypes.APPLICATION_JSON);
        this.model = model;
    }

    @Override
    public Void handleRequest(Request request, Response response) {
        try {
            var change = JsonMapper.deserialize(request.body(), VitruviusChange.class);
            model.propagateChange(change);
        } catch (JsonProcessingException e) {
            throw internalServerError(e.getMessage());
        }

        return null;
    }
}
