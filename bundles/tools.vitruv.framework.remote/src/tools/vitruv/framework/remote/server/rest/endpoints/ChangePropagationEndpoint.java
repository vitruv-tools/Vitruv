package tools.vitruv.framework.remote.server.rest.endpoints;

import tools.vitruv.change.atomic.root.InsertRootEObject;
import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.framework.remote.server.exception.ServerHaltingException;
import tools.vitruv.framework.remote.server.rest.HttpExchangeWrapper;
import tools.vitruv.framework.remote.server.rest.PatchEndpoint;
import tools.vitruv.framework.remote.common.rest.constants.Header;
import tools.vitruv.framework.remote.common.util.Cache;
import tools.vitruv.framework.views.impl.ModifiableView;
import tools.vitruv.framework.views.impl.ViewCreatingViewType;
import tools.vitruv.framework.remote.common.util.JsonMapper;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;

import static java.net.HttpURLConnection.*;

/**
 * This endpoint applies given {@link VitruviusChange}s to the VSUM.
 */
public class ChangePropagationEndpoint implements PatchEndpoint {
	private final JsonMapper mapper;
	
	
	public ChangePropagationEndpoint(JsonMapper mapper) {

		this.mapper = mapper;
	}


	@SuppressWarnings("unchecked")
	@Override
    public String process(HttpExchangeWrapper wrapper) {
        var view = Cache.getView(wrapper.getRequestHeader(Header.VIEW_UUID));
        if (view == null) {
            throw notFound("View with given id not found!");
        }
        try {
            var body = wrapper.getRequestBodyAsString();
            var change = mapper.deserialize(body, VitruviusChange.class);
            change.getEChanges().forEach(it -> {
            	if (it instanceof InsertRootEObject<?> echange) {
            		echange.setResource(new ResourceImpl(URI.createURI(echange.getUri())));
				}
            });
            var type = (ViewCreatingViewType<? , ?>) view.getViewType();
            type.commitViewChanges((ModifiableView) view, change);
            return null;
        } catch (IOException e) {
            throw internalServerError(e.getMessage());
        } catch (AssertionError e) {
			throw new ServerHaltingException(HTTP_CONFLICT, "User interactions are required to commit these changes!");
		}
    }
}
