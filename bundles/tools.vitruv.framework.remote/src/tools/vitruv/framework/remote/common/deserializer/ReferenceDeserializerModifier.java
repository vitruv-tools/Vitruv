package tools.vitruv.framework.remote.common.deserializer;

import org.eclipse.emfcloud.jackson.databind.deser.EcoreReferenceDeserializer;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.type.ReferenceType;


public class ReferenceDeserializerModifier extends BeanDeserializerModifier {

	@Override
	public JsonDeserializer<?> modifyReferenceDeserializer(DeserializationConfig config, ReferenceType type,
			BeanDescription beanDesc, JsonDeserializer<?> deserializer) {
		if (deserializer instanceof EcoreReferenceDeserializer referenceDeserializer) {
			return new HierarichalIdDeserializer(referenceDeserializer);
		}
		return deserializer;	
	}
	
	
}
