package tools.vitruv.remote.common.deserializer;

import java.io.IOException;
import java.util.LinkedList;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class ResourceSetDeserializer extends JsonDeserializer<ResourceSet> {

	@Override
	public ResourceSet deserialize(JsonParser p, DeserializationContext c) throws IOException, JacksonException {
		var rootNode = (ArrayNode) p.getCodec().readTree(p);
		var resources = new LinkedList<Resource>();
		
		for (var e : rootNode) {
			resources.add(c.readTreeAsValue(e, Resource.class));
		}
		
		var resourceSet = new ResourceSetImpl();
		resourceSet.getResources().addAll(resources);
		
		return resourceSet;
	}

}
