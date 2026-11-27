package tools.vitruv.framework.remote.common.json.serializer;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import org.eclipse.emfcloud.jackson.databind.ser.EcoreReferenceSerializer;
import tools.vitruv.framework.remote.common.json.IdTransformation;

/** A {@link BeanSerializerModifier} that modifies reference serializers to use. */
public class ReferenceSerializerModifier extends BeanSerializerModifier {
  private final transient IdTransformation transformation;

  /**
   * Creates a new ReferenceSerializerModifier.
   *
   * @param transformation the id transformation to be used
   */
  public ReferenceSerializerModifier(IdTransformation transformation) {
    this.transformation = transformation;
  }

  @Override
  public JsonSerializer<?> modifySerializer(
      SerializationConfig config, BeanDescription desc, JsonSerializer<?> serializer) {
    if (serializer instanceof EcoreReferenceSerializer referenceSerializer) {
      return new HierarichalIdSerializer(referenceSerializer, transformation);
    }
    return serializer;
  }
}
