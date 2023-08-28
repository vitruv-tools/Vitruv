package tools.vitruv.framework.remote.common.deserializer;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;

import tools.vitruv.framework.remote.common.util.IdTransformation;
import tools.vitruv.framework.remote.common.util.JsonMapper;
import tools.vitruv.framework.remote.common.util.ResourceUtil;
import tools.vitruv.framework.remote.common.util.constants.JsonFieldName;

public class ResourceSetDeserializer extends JsonDeserializer<ResourceSet> {
	
	private final IdTransformation transformation;
	private final JsonMapper mapper;
	
	public ResourceSetDeserializer(JsonMapper mapper, IdTransformation transformation) {
		this.transformation = transformation;
		this.mapper = mapper;
	}

	@Override
	public ResourceSet deserialize(JsonParser parser, DeserializationContext context) throws IOException {
		var rootNode = (ArrayNode) parser.getCodec().readTree(parser);
		
		var resourceSet = ResourceUtil.createJsonResourceSet();
		for (var e : rootNode) {
			mapper.deserializeResource(e.get(JsonFieldName.CONTENT).toString(), 
					transformation.toGlobal(URI.createURI(e.get(JsonFieldName.URI).asText())).toString(), resourceSet);
		}
	
		return resourceSet;
	}
}
