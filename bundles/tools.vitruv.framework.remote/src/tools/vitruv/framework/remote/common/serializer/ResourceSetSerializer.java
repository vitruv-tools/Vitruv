package tools.vitruv.framework.remote.common.serializer;

import java.io.IOException;

import org.eclipse.emf.ecore.resource.ResourceSet;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class ResourceSetSerializer extends JsonSerializer<ResourceSet> {

    @Override
    public void serialize(ResourceSet resourceSet, JsonGenerator generator, SerializerProvider provider) throws IOException {
        generator.writeStartArray();
        var resources = resourceSet.getResources();
        for (var r : resources) {
            generator.writeObject(r);
        }
        generator.writeEndArray();
    }
}