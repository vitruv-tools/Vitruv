package tools.vitruv.framework.remote.common.serializer;

import java.io.IOException;

import org.eclipse.emf.ecore.resource.Resource;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import tools.vitruv.framework.remote.common.util.IdTransformation;
import tools.vitruv.framework.remote.common.util.ResourceUtils;
import tools.vitruv.framework.remote.common.util.SerializationConstants;

public class ResourceSerializer extends JsonSerializer<Resource> {

    @Override
    public void serialize(Resource resource, JsonGenerator generator, SerializerProvider provider) throws IOException {
        generator.writeStartObject();
        generator.writeStringField(SerializationConstants.URI, IdTransformation.toLocal(resource.getURI().toString()));
        generator.writeStringField(SerializationConstants.CONTENT, ResourceUtils.serialize(resource));
        generator.writeEndObject();
    }
}
