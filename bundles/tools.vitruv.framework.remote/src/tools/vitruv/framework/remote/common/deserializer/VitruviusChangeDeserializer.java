package tools.vitruv.framework.remote.common.deserializer;

import java.io.IOException;
import java.util.LinkedList;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.TextNode;

import tools.vitruv.change.atomic.EChange;
import tools.vitruv.change.composite.description.TransactionalChange;
import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.change.composite.description.VitruviusChangeFactory;
import tools.vitruv.change.interaction.UserInteractionBase;
import tools.vitruv.framework.remote.common.util.constants.JsonFieldName;
import tools.vitruv.framework.remote.common.util.ChangeType;
import tools.vitruv.framework.remote.common.util.IdTransformation;
import tools.vitruv.framework.remote.common.util.JsonMapper;
import tools.vitruv.framework.remote.common.util.ResourceUtil;

public class VitruviusChangeDeserializer extends JsonDeserializer<VitruviusChange<?>> {

    @Override
    public VitruviusChange<?> deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        var rootNode = parser.getCodec().readTree(parser);
        var type = ChangeType.valueOf(((TextNode)rootNode.get(JsonFieldName.CHANGE_TYPE)).asText());

        VitruviusChange<?> change;
        if (type == ChangeType.TRANSACTIONAL) {
            var resourceNode = rootNode.get(JsonFieldName.E_CHANGES);
            var changesResource = JsonMapper.deserializeResource(resourceNode.toString(), JsonFieldName.TEMP_VALUE, ResourceUtil.createJsonResourceSet());
            var changes = changesResource.getContents().stream().map(e -> (EChange<?>) e).toList();
            IdTransformation.allToGlobal(changes);
            change = VitruviusChangeFactory.getInstance().createTransactionalChange(changes);
            var interactions = JsonMapper.deserializeArrayOf(rootNode.get(JsonFieldName.U_INTERACTIONS).toString(), UserInteractionBase.class);
            ((TransactionalChange<?>) change).setUserInteractions(interactions);
        } else if (type == ChangeType.COMPOSITE) {
            var changesNode = (ArrayNode) rootNode.get(JsonFieldName.V_CHANGES);
            var changes = new LinkedList<VitruviusChange<?>>();
            for (var e : changesNode) {
                changes.add(JsonMapper.deserialize(e.asText(), VitruviusChange.class));
            }
            change = VitruviusChangeFactory.getInstance().createCompositeChange(changes);
        } else {
            throw new UnsupportedOperationException("Change deserialization for type" + type + " not implemented!");
        }
        return change;
    }
}
