package tools.vitruv.framework.remote.server.rest.endpoints;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Collection;
import java.util.List;
import tools.vitruv.framework.remote.server.http.HttpWrapper;
import tools.vitruv.framework.remote.server.rest.GetEndpoint;
import tools.vitruv.framework.remote.common.json.JsonMapper;
import tools.vitruv.framework.remote.common.rest.constants.ContentType;
import tools.vitruv.framework.views.ViewType;
import tools.vitruv.framework.vsum.VirtualModel;

/**
 * This end point returns a list of names of all registered {@link ViewType}s in the VSUM.
 */
public class ViewTypesEndpoint implements GetEndpoint {
    private final VirtualModel model;
    private final JsonMapper mapper;

    public ViewTypesEndpoint(VirtualModel model, JsonMapper mapper) {
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
