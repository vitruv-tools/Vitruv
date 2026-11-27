package tools.vitruv.framework.remote.common.json.deserializer;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.type.ReferenceType;
import org.eclipse.emfcloud.jackson.databind.deser.EcoreReferenceDeserializer;
import tools.vitruv.framework.remote.common.json.IdTransformation;

/** A {@link BeanDeserializerModifier} that modifies reference deserializers to use. */
public class ReferenceDeserializerModifier extends BeanDeserializerModifier {
  private final transient IdTransformation transformation;

  /**
   * Creates a new ReferenceDeserializerModifier.
   *
   * @param transformation the id transformation to be used
   */
  public ReferenceDeserializerModifier(IdTransformation transformation) {
    this.transformation = transformation;
  }

  @Override
  public JsonDeserializer<?> modifyReferenceDeserializer(
      DeserializationConfig config,
      ReferenceType type,
      BeanDescription beanDesc,
      JsonDeserializer<?> deserializer) {
    if (deserializer instanceof EcoreReferenceDeserializer referenceDeserializer) {
      return new HierarichalIdDeserializer(referenceDeserializer, transformation);
    }
    return deserializer;
  }
}
