package tools.vitruv.framework.remote.common.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emfcloud.jackson.databind.ser.EcoreReferenceSerializer;
import tools.vitruv.change.atomic.hid.HierarchicalId;
import tools.vitruv.framework.remote.common.json.IdTransformation;

/** A serializer for {@link HierarchicalId}. */
public class HierarichalIdSerializer extends JsonSerializer<EObject> {
  private final EcoreReferenceSerializer standardSerializer;
  private final IdTransformation transformation;

  /**
   * Creates a new HierarichalIdSerializer.
   *
   * @param standardDeserializer the standard deserializer
   * @param transformation the id transformation to be used
   */
  public HierarichalIdSerializer(
      EcoreReferenceSerializer standardDeserializer, IdTransformation transformation) {
    this.standardSerializer = standardDeserializer;
    this.transformation = transformation;
  }

  @Override
  public void serialize(EObject value, JsonGenerator gen, SerializerProvider serializers)
      throws IOException {
    if (value instanceof HierarchicalId hid) {
      gen.writeString(transformation.toLocal(URI.createURI(hid.getId())).toString());
    } else {
      standardSerializer.serialize(value, gen, serializers);
    }
  }
}
