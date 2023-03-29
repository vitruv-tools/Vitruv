package tools.vitruv.remote.common.serializer;

import java.io.IOException;

import org.eclipse.emf.ecore.resource.ResourceSet;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class ResourceSetSerializer extends JsonSerializer<ResourceSet> {

	@Override
	public void serialize(ResourceSet rs, JsonGenerator g, SerializerProvider p) throws IOException {
		g.writeStartArray();
		var resources = rs.getResources();
		for (var r : resources) {
			g.writeObject(r);
		}
		g.writeEndArray();
	}
}
