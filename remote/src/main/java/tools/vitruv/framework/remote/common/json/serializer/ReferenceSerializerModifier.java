package tools.vitruv.framework.remote.common.json.serializer;

import org.eclipse.emfcloud.jackson.databind.ser.EcoreReferenceSerializer;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;

import tools.vitruv.framework.remote.common.json.IdTransformation;

public class ReferenceSerializerModifier extends BeanSerializerModifier {
	private final IdTransformation transformation;
	
	public ReferenceSerializerModifier(IdTransformation transformation) {
		this.transformation = transformation;
	}

	@Override
	public JsonSerializer<?> modifySerializer(SerializationConfig config, BeanDescription desc, JsonSerializer<?> serializer) {
		if (serializer instanceof EcoreReferenceSerializer referenceSerializer) {
			return new HierarichalIdSerializer(referenceSerializer, transformation);
		}
		return serializer;	
	}
}
