package tools.vitruv.framework.remote.common.serializer;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import tools.vitruv.change.composite.description.CompositeChange;
import tools.vitruv.change.composite.description.TransactionalChange;
import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.framework.remote.common.util.ChangeType;
import tools.vitruv.framework.remote.common.util.ResourceUtils;

public class VitruviusChangeSerializer extends JsonSerializer<VitruviusChange> {
	
	@Override
	public void serialize(VitruviusChange v, JsonGenerator g, SerializerProvider p) throws IOException {
		g.writeStartObject();
		g.writeStringField("changeType", ChangeType.getChangeTypeOf(v).toString());
		if (v instanceof TransactionalChange tc) {
			var changesResource = ResourceUtils.createResourceWith(URI.createURI("temp"), tc.getEChanges());
			g.writeFieldName("eChanges");
			g.writeObject(ResourceUtils.serialize(changesResource));
		} else if (v instanceof CompositeChange<?> cc) {
			var changes = cc.getChanges();
			g.writeArrayFieldStart("vChanges");
			for(var change : changes) {
				g.writeObject(change);
			}
			g.writeEndArray();
		}
		else {
			throw new UnsupportedOperationException("Change serialization of type " + v.getClass().getName() + " not implemented!");
		}
		g.writeEndObject();
	}
}
