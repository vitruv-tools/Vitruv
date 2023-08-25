package tools.vitruv.framework.remote.common.deserializer;

import java.io.IOException;
import java.util.LinkedList;

import org.eclipse.emf.ecore.resource.Resource;
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

	@Override
	public ResourceSet deserialize(JsonParser parser, DeserializationContext context) throws IOException {
		var rootNode = (ArrayNode) parser.getCodec().readTree(parser);
		var resources = new LinkedList<Resource>();
		
		for (var e : rootNode) {
			resources.add(JsonMapper.deserializeResource(e.get(JsonFieldName.CONTENT).toString(), 
					IdTransformation.toGlobal(e.get(JsonFieldName.URI).asText())));
		}
		
		var resourceSet = ResourceUtil.createJsonResourceSet();
		resourceSet.getResources().addAll(resources);
		
		return resourceSet;
	}

}
