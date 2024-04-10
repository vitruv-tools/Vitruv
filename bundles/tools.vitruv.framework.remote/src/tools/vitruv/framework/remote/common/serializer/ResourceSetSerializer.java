package tools.vitruv.framework.remote.common.serializer;

import java.io.IOException;

import org.eclipse.emf.ecore.resource.ResourceSet;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import tools.vitruv.framework.remote.common.util.IdTransformation;
import tools.vitruv.framework.remote.common.util.constants.JsonFieldName;

public class ResourceSetSerializer extends JsonSerializer<ResourceSet> {
	
	private final IdTransformation transformation;
	
	public ResourceSetSerializer(IdTransformation transformation) {
		this.transformation = transformation;
	}

    @Override
    public void serialize(ResourceSet resourceSet, JsonGenerator generator, SerializerProvider provider) throws IOException {
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
