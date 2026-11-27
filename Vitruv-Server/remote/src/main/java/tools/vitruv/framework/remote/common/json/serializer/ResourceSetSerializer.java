package tools.vitruv.framework.remote.common.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import org.eclipse.emf.ecore.resource.ResourceSet;
import tools.vitruv.framework.remote.common.json.IdTransformation;
import tools.vitruv.framework.remote.common.json.JsonFieldName;

/** A serializer for {@link ResourceSet}. */
public class ResourceSetSerializer extends JsonSerializer<ResourceSet> {
  private final IdTransformation transformation;

  /**
   * Creates a new ResourceSetSerializer.
   *
   * @param transformation the id transformation to be used
   */
  public ResourceSetSerializer(IdTransformation transformation) {
    this.transformation = transformation;
  }

  @Override
  public void serialize(
      ResourceSet resourceSet, JsonGenerator generator, SerializerProvider provider)
      throws IOException {
    generator.writeStartArray();
    var resources = resourceSet.getResources();
    for (var r : resources) {
      generator.writeStartObject();
      generator.writeObjectField(JsonFieldName.URI, transformation.toLocal(r.getURI()).toString());
      generator.writeObjectField(JsonFieldName.CONTENT, r);
      generator.writeEndObject();
    }
    generator.writeEndArray();
  }
}
