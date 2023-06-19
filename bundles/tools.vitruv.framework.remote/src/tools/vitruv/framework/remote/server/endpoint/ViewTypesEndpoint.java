package tools.vitruv.framework.remote.server.endpoint;

import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import tools.vitruv.framework.remote.common.util.constants.ContentTypes;
import tools.vitruv.framework.remote.common.util.HttpExchangeWrapper;
import tools.vitruv.framework.views.ViewType;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;
import tools.vitruv.framework.remote.common.util.JsonMapper;

/**
 * This endpoint returns a list of names of all registered {@link ViewType}s in the VSUM.
 */
public class ViewTypesEndpoint implements Endpoint.Get {

    private final InternalVirtualModel model;

    public ViewTypesEndpoint(InternalVirtualModel model) {
        this.model = model;
    }

    @Override
    public String process(HttpExchangeWrapper wrapper) {
        Collection<ViewType<?>> types = model.getViewTypes();
        List<String> names = types.stream().map(ViewType::getName).toList();

        wrapper.setContentType(ContentTypes.APPLICATION_JSON);
        try {
            return JsonMapper.serialize(names);
        } catch (JsonProcessingException e) {
            throw internalServerError(e.getMessage());
        }
    }
}
