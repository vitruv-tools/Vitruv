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
import tools.vitruv.framework.remote.common.util.constants.SerializationConstants;
import tools.vitruv.framework.remote.common.util.ChangeType;
import tools.vitruv.framework.remote.common.util.IdTransformation;
import tools.vitruv.framework.remote.common.util.ResourceUtils;

public class VitruviusChangeDeserializer extends JsonDeserializer<VitruviusChange> {

    @Override
    public VitruviusChange deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        var rootNode = parser.getCodec().readTree(parser);
        var type = context.readTreeAsValue((TextNode) rootNode.get(SerializationConstants.CHANGE_TYPE), ChangeType.class);

        VitruviusChange change;
        if (type == ChangeType.TRANSACTIONAL) {
            var jsonString = ((TextNode) rootNode.get(SerializationConstants.E_CHANGES)).asText();
            var changesResource = ResourceUtils.deserialize(URI.createURI(SerializationConstants.TEMP), jsonString);
            var eChanges = changesResource.getContents().stream().map(e -> (EChange) e).toList();
            IdTransformation.allToGlobal(eChanges);
            change = VitruviusChangeFactory.getInstance().createTransactionalChange(eChanges);
        } else if (type == ChangeType.COMPOSITE) {
            var changesNode = (ArrayNode) rootNode.get(SerializationConstants.V_CHANGES);
            var changes = new LinkedList<VitruviusChange>();
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
