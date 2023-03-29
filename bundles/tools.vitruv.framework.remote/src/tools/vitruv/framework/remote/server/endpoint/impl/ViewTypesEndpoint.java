package tools.vitruv.framework.remote.server.endpoint.impl;

import java.util.Collection;
import java.util.List;

import spark.Request;
import spark.Response;
import tools.vitruv.framework.views.ViewType;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;
import tools.vitruv.framework.remote.common.util.JsonMapper;
import tools.vitruv.framework.remote.server.endpoint.GetEndpoint;

/**
 * This endpoint returns a list of names of all registered {@link ViewType}s in the VSUM.
 */
public class ViewTypesEndpoint extends GetEndpoint {
	
	private final InternalVirtualModel model;

	public ViewTypesEndpoint(InternalVirtualModel model) {
		super("/vsum/viewtypes");
		this.model = model;
	}

	@Override
	public String handleRequest(Request request, Response response) {
		Collection<ViewType<?>> types = model.getViewTypes();
		List<String> names = types.stream().map(ViewType::getName).toList();
		
		response.type("application/json");
		return JsonMapper.serialize(names);
	}
}
