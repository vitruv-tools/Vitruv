package tools.vitruv.framework.remote.common.deserializer;

import java.io.IOException;
import java.util.LinkedList;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class ResourceSetDeserializer extends JsonDeserializer<ResourceSet> {

	@Override
	public ResourceSet deserialize(JsonParser parser, DeserializationContext context) throws IOException {
		var rootNode = (ArrayNode) parser.getCodec().readTree(parser);
		var resources = new LinkedList<Resource>();
		
		for (var e : rootNode) {
			resources.add(context.readTreeAsValue(e, Resource.class));
		}
		
		var resourceSet = new ResourceSetImpl();
		resourceSet.getResources().addAll(resources);
		
		return resourceSet;
	}

}
