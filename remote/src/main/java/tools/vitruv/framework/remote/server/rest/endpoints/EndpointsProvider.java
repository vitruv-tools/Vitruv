package tools.vitruv.framework.remote.server.rest.endpoints;

import java.util.ArrayList;
import java.util.List;

import tools.vitruv.framework.remote.common.json.JsonMapper;
import tools.vitruv.framework.remote.common.rest.constants.EndpointPath;
import tools.vitruv.framework.remote.server.exception.ServerHaltingException;
import tools.vitruv.framework.remote.server.http.HttpWrapper;
import tools.vitruv.framework.remote.server.rest.DeleteEndpoint;
import tools.vitruv.framework.remote.server.rest.GetEndpoint;
import tools.vitruv.framework.remote.server.rest.PatchEndpoint;
import tools.vitruv.framework.remote.server.rest.PathEndointCollector;
import tools.vitruv.framework.remote.server.rest.PostEndpoint;
import tools.vitruv.framework.remote.server.rest.PutEndpoint;
import tools.vitruv.framework.vsum.VirtualModel;

public class EndpointsProvider {
	public static List<PathEndointCollector> getAllEndpoints(VirtualModel virtualModel, JsonMapper mapper) {
		var defaultEndpoints = getDefaultEndpoints();
		
		List<PathEndointCollector> result = new ArrayList<>();
		result.add(new PathEndointCollector(
			EndpointPath.HEALTH,
			new HealthEndpoint(),
			defaultEndpoints.postEndpoint(),
			defaultEndpoints.putEndpoint(),
			defaultEndpoints.patchEndpoint(),
			defaultEndpoints.deleteEndpoint()
		));
		result.add(new PathEndointCollector(
			EndpointPath.IS_VIEW_CLOSED,
			new IsViewClosedEndpoint(),
			defaultEndpoints.postEndpoint(),
			defaultEndpoints.putEndpoint(),
			defaultEndpoints.patchEndpoint(),
			defaultEndpoints.deleteEndpoint()
		));
		result.add(new PathEndointCollector(
			EndpointPath.IS_VIEW_OUTDATED,
			new IsViewOutdatedEndpoint(),
			defaultEndpoints.postEndpoint(),
			defaultEndpoints.putEndpoint(),
			defaultEndpoints.patchEndpoint(),
			defaultEndpoints.deleteEndpoint()
		));
		result.add(new PathEndointCollector(
			EndpointPath.VIEW,
			new UpdateViewEndpoint(mapper),
			new ViewEndpoint(mapper),
			defaultEndpoints.putEndpoint(),
			new ChangePropagationEndpoint(mapper),
			new CloseViewEndpoint()
		));
		result.add(new PathEndointCollector(
			EndpointPath.VIEW_SELECTOR,
			new ViewSelectorEndpoint(virtualModel, mapper),
			defaultEndpoints.postEndpoint(),
			defaultEndpoints.putEndpoint(),
			defaultEndpoints.patchEndpoint(),
			defaultEndpoints.deleteEndpoint()
		));
		result.add(new PathEndointCollector(
			EndpointPath.VIEW_TYPES,
			new ViewTypesEndpoint(virtualModel, mapper),
			defaultEndpoints.postEndpoint(),
			defaultEndpoints.putEndpoint(),
			defaultEndpoints.patchEndpoint(),
			defaultEndpoints.deleteEndpoint()
		));
		
		return result;
	}
	
	private static PathEndointCollector getDefaultEndpoints() {
		var getEndpoint = new GetEndpoint() {
            @Override
            public String process(HttpWrapper wrapper) throws ServerHaltingException {
                throw notFound("Get mapping for this request path not found!");
            }
        };
        var postEndpoint = new PostEndpoint() {
            @Override
            public String process(HttpWrapper wrapper) throws ServerHaltingException {
                throw notFound("Post mapping for this request path not found!");
            }
        };
        var patchEndpoint = new PatchEndpoint() {
            @Override
            public String process(HttpWrapper wrapper) throws ServerHaltingException {
                throw notFound("Patch mapping for this request path not found!");
            }
        };
        var deleteEndpoint = new DeleteEndpoint() {
            @Override
            public String process(HttpWrapper wrapper) throws ServerHaltingException {
                throw notFound("Delete mapping for this request path not found!");
            }
        };
        var putEndpoint = new PutEndpoint() {
            @Override
            public String process(HttpWrapper wrapper) throws ServerHaltingException {
                throw notFound("Put mapping for this request path not found!");
            }
        };
        return new PathEndointCollector("", getEndpoint, postEndpoint, putEndpoint, patchEndpoint, deleteEndpoint);
	}
	
	private EndpointsProvider() {}
}
