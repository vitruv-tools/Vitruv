package tools.vitruv.framework.remote.common.serializer;

import java.io.IOException;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emfcloud.jackson.databind.ser.EcoreReferenceSerializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import tools.vitruv.change.atomic.hid.HierarchicalId;

public class HierarichalIdSerializer extends JsonSerializer<EObject>{
	
	private final EcoreReferenceSerializer standardSerializer;
	
	public HierarichalIdSerializer(EcoreReferenceSerializer standardDeserializer) {
		this.standardSerializer = standardDeserializer;
	}

	@Override
	public void serialize(EObject value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		if (value instanceof HierarchicalId hid) {
			gen.writeString(hid.getId());
		} else {
			standardSerializer.serialize(value, gen, serializers);
		}
	}
}
