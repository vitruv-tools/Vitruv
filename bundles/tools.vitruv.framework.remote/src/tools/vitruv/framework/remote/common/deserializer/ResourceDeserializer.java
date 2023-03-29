package tools.vitruv.remote.common.deserializer;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;

import tools.vitruv.remote.common.util.ResourceUtils;

public class ResourceDeserializer extends JsonDeserializer<Resource> {

	@Override
	public Resource deserialize(JsonParser p, DeserializationContext c) throws IOException, JacksonException {
		var rootNode = p.getCodec().readTree(p);
		
		var uri = ((TextNode)rootNode.get("uri")).asText();
		var content = ((TextNode)rootNode.get("content")).asText();
		
		return ResourceUtils.deserialize(URI.createURI(uri), content);
	}

}
