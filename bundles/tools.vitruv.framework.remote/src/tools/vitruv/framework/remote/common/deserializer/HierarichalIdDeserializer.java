package tools.vitruv.framework.remote.common.deserializer;

import java.io.IOException;

import org.eclipse.emfcloud.jackson.databind.EMFContext;
import org.eclipse.emfcloud.jackson.databind.deser.EcoreReferenceDeserializer;
import org.eclipse.emfcloud.jackson.databind.deser.ReferenceEntry;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import tools.vitruv.framework.remote.common.util.HidReferenceEntry;

public class HierarichalIdDeserializer extends JsonDeserializer<ReferenceEntry> {
	
	private final EcoreReferenceDeserializer standardDeserializer;
	
	public HierarichalIdDeserializer(EcoreReferenceDeserializer standardDeserializer) {
		this.standardDeserializer = standardDeserializer;
	}

	@Override
	public ReferenceEntry deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
		if (parser.currentToken() == JsonToken.VALUE_STRING) {
			var node = context.readTree(parser);
			return new HidReferenceEntry(EMFContext.getParent(context), EMFContext.getReference(context), node.asText());

		}
		return standardDeserializer.deserialize(parser, context);
	}
}
