package tools.vitruv.framework.remote.common.serializer;

import org.eclipse.emfcloud.jackson.databind.ser.EcoreReferenceSerializer;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;

public class ReferenceSerializerModifier extends BeanSerializerModifier {
	
	@Override
	public JsonSerializer<?> modifySerializer(SerializationConfig config, BeanDescription desc, JsonSerializer<?> serializer) {
		if (serializer instanceof EcoreReferenceSerializer referenceSerializer) {
			return new HierarichalIdSerializer(referenceSerializer);
		}
		return serializer;	
	}
}
