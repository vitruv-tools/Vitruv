package tools.vitruv.framework.remote.server.rest.endpoints;

import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

import tools.vitruv.framework.remote.server.http.HttpWrapper;
import tools.vitruv.framework.remote.server.rest.GetEndpoint;
import tools.vitruv.framework.remote.common.rest.constants.ContentType;
import tools.vitruv.framework.views.ViewType;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;
import tools.vitruv.framework.remote.common.util.JsonMapper;

/**
 * This endpoint returns a list of names of all registered {@link ViewType}s in the VSUM.
 */
public class ViewTypesEndpoint implements GetEndpoint {
    private final InternalVirtualModel model;
    private final JsonMapper mapper;

    public ViewTypesEndpoint(InternalVirtualModel model, JsonMapper mapper) {
        this.model = model;
        this.mapper = mapper;
    }

    @Override
    public String process(HttpWrapper wrapper) {
        Collection<ViewType<?>> types = model.getViewTypes();
        List<String> names = types.stream().map(ViewType::getName).toList();

        wrapper.setContentType(ContentType.APPLICATION_JSON);
        try {
            return mapper.serialize(names);
        } catch (JsonProcessingException e) {
            throw internalServerError(e.getMessage());
        }
    }
}
