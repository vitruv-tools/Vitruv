package tools.vitruv.remote.server.endpoint.impl;

import spark.Request;
import spark.Response;

import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;
import tools.vitruv.remote.common.util.ContentTypes;
import tools.vitruv.remote.common.util.JsonMapper;
import tools.vitruv.remote.common.util.Nothing;
import tools.vitruv.remote.server.endpoint.PostEndpoint;

/**
 * This endpoint applies given {@link VitruviusChange}s to the VSUM.
 */
public class ChangePropagationEndpoint extends PostEndpoint {
	
	InternalVirtualModel model;

	public ChangePropagationEndpoint(InternalVirtualModel model) {
		super("/vsum/change", ContentTypes.APPLICATION_JSON);
		this.model = model;
	}

	@Override
	public Nothing handleRequest(Request request, Response response) {
		var change = JsonMapper.deserialize(request.body(), VitruviusChange.class);
		model.propagateChange(change);
		return new Nothing();
	}
}
