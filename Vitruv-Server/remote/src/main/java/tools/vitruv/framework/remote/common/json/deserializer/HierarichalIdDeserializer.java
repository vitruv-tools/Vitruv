package tools.vitruv.framework.remote.common.json.deserializer;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emfcloud.jackson.databind.EMFContext;
import org.eclipse.emfcloud.jackson.databind.deser.EcoreReferenceDeserializer;
import org.eclipse.emfcloud.jackson.databind.deser.ReferenceEntry;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import tools.vitruv.framework.remote.common.json.IdTransformation;

public class HierarichalIdDeserializer extends JsonDeserializer<ReferenceEntry> {
	private final EcoreReferenceDeserializer standardDeserializer;
	private final IdTransformation transformation;

	public HierarichalIdDeserializer(EcoreReferenceDeserializer standardDeserializer, IdTransformation transformation) {
		this.standardDeserializer = standardDeserializer;
		this.transformation = transformation;
	}

	@Override
	public ReferenceEntry deserialize(JsonParser parser, DeserializationContext context) throws IOException {
		if (parser.currentToken() == JsonToken.VALUE_STRING) {
			var node = context.readTree(parser);
			return new HidReferenceEntry(EMFContext.getParent(context), EMFContext.getReference(context),
					transformation.toGlobal(URI.createURI(node.asText())).toString());
		}
		return standardDeserializer.deserialize(parser, context);
	}
}
