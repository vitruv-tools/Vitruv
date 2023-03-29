package tools.vitruv.framework.remote.common.serializer;

import java.io.IOException;

import org.eclipse.emf.ecore.resource.Resource;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import tools.vitruv.framework.remote.common.util.IdTransformation;
import tools.vitruv.framework.remote.common.util.ResourceUtils;

public class ResourceSerializer extends JsonSerializer<Resource>{

	@Override
	public void serialize(Resource r, JsonGenerator g, SerializerProvider p) throws IOException {
		g.writeStartObject();
		g.writeStringField("uri", IdTransformation.toLocal(r.getURI().toString()));
		g.writeStringField("content", ResourceUtils.serialize(r));
		g.writeEndObject();
	}
}
