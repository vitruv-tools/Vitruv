package tools.vitruv.framework.remote.common.deserializer;

import java.io.IOException;
import java.util.LinkedList;

import org.eclipse.emf.common.util.URI;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.TextNode;

import tools.vitruv.change.atomic.EChange;
import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.change.composite.description.VitruviusChangeFactory;
import tools.vitruv.framework.remote.common.util.constants.JsonFieldName;
import tools.vitruv.framework.remote.common.util.ChangeType;
import tools.vitruv.framework.remote.common.util.IdTransformation;
import tools.vitruv.framework.remote.common.util.ResourceUtil;

public class VitruviusChangeDeserializer extends JsonDeserializer<VitruviusChange<?>> {

    @Override
    public VitruviusChange<?> deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        var rootNode = parser.getCodec().readTree(parser);
        var type = context.readTreeAsValue((TextNode) rootNode.get(JsonFieldName.CHANGE_TYPE), ChangeType.class);

        VitruviusChange<?> change;
        if (type == ChangeType.TRANSACTIONAL) {
            var jsonString = ((TextNode) rootNode.get(JsonFieldName.E_CHANGES)).asText();
            var changesResource = ResourceUtil.deserialize(URI.createURI(JsonFieldName.TEMP), jsonString);
            var eChanges = changesResource.getContents().stream().map(e -> (EChange<?>) e).toList();
            IdTransformation.allToGlobal(eChanges);
            change = VitruviusChangeFactory.getInstance().createTransactionalChange(eChanges);
        } else if (type == ChangeType.COMPOSITE) {
            var changesNode = (ArrayNode) rootNode.get(JsonFieldName.V_CHANGES);
            var changes = new LinkedList<VitruviusChange<?>>();
            for (var e : changesNode) {
                changes.add(context.readTreeAsValue(e, VitruviusChange.class));
            }
            change = VitruviusChangeFactory.getInstance().createCompositeChange(changes);
        } else {
            throw new UnsupportedOperationException("Change deserialization for type" + type + " not implemented!");
        }
        return change;
    }
}
