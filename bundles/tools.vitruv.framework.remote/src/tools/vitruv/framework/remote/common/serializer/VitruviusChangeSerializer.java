package tools.vitruv.framework.remote.common.serializer;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import tools.vitruv.change.composite.description.CompositeChange;
import tools.vitruv.change.composite.description.TransactionalChange;
import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.framework.remote.common.util.SerializationConstants;
import tools.vitruv.framework.remote.common.util.ChangeType;
import tools.vitruv.framework.remote.common.util.ResourceUtils;

public class VitruviusChangeSerializer extends JsonSerializer<VitruviusChange> {

    @Override
    public void serialize(VitruviusChange vitruviusChange, JsonGenerator generator, SerializerProvider provider) throws IOException {
        generator.writeStartObject();
        generator.writeStringField(SerializationConstants.CHANGE_TYPE, ChangeType.getChangeTypeOf(vitruviusChange).toString());
        if (vitruviusChange instanceof TransactionalChange tc) {
            var changesResource = ResourceUtils.createResourceWith(URI.createURI(SerializationConstants.TEMP), tc.getEChanges());
            generator.writeFieldName(SerializationConstants.E_CHANGES);
            generator.writeObject(ResourceUtils.serialize(changesResource));
        } else if (vitruviusChange instanceof CompositeChange<?> cc) {
            var changes = cc.getChanges();
            generator.writeArrayFieldStart(SerializationConstants.V_CHANGES);
            for (var change : changes) {
                generator.writeObject(change);
            }
            generator.writeEndArray();
        } else {
            throw new UnsupportedOperationException("Change serialization of type " + vitruviusChange.getClass().getName() + " not implemented!");
        }
        generator.writeEndObject();
    }
}