package tools.vitruv.framework.remote.common.deserializer;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;

import tools.vitruv.framework.remote.common.util.ResourceUtils;
import tools.vitruv.framework.remote.common.util.SerializationConstants;

public class ResourceDeserializer extends JsonDeserializer<Resource> {

	@Override
	public Resource deserialize(JsonParser parser, DeserializationContext context) throws IOException {
		var rootNode = parser.getCodec().readTree(parser);
		
		var uri = ((TextNode)rootNode.get(SerializationConstants.URI)).asText();
		var content = ((TextNode)rootNode.get(SerializationConstants.CONTENT)).asText();
		
		return ResourceUtils.deserialize(URI.createURI(uri), content);
	}

}
