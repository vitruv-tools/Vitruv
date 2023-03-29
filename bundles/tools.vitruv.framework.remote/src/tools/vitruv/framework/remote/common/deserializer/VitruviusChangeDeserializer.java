package tools.vitruv.framework.remote.common.deserializer;

import java.io.IOException;
import java.util.LinkedList;

import org.eclipse.emf.common.util.URI;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.TextNode;

import tools.vitruv.change.atomic.EChange;
import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.change.composite.description.VitruviusChangeFactory;
import tools.vitruv.framework.remote.common.util.ChangeType;
import tools.vitruv.framework.remote.common.util.IdTransformation;
import tools.vitruv.framework.remote.common.util.ResourceUtils;

public class VitruviusChangeDeserializer extends JsonDeserializer<VitruviusChange> {

	@Override
	public VitruviusChange deserialize(JsonParser p, DeserializationContext c) throws IOException, JacksonException {
		var rootNode = p.getCodec().readTree(p);
		var type = c.readTreeAsValue((TextNode) rootNode.get("changeType"), ChangeType.class);

		VitruviusChange change = null;
		if (type == ChangeType.TRANSACTIONAL) {
			var json = ((TextNode)rootNode.get("eChanges")).asText();
			var changesResource = ResourceUtils.deserialize(URI.createURI("temp"), json);
			var eChanges = changesResource.getContents().stream().map(e -> (EChange) e).toList();
			IdTransformation.allToGlobal(eChanges);
			change = VitruviusChangeFactory.getInstance().createTransactionalChange(eChanges);
		} else if (type == ChangeType.COMPOSITE) {
			var changesNode = (ArrayNode) rootNode.get("vChanges");
			var changes = new LinkedList<VitruviusChange>();
			for (var e : changesNode) {
				changes.add(c.readTreeAsValue(e, VitruviusChange.class));
			}
			change = VitruviusChangeFactory.getInstance().createCompositeChange(changes);
		}
		else {
			throw new UnsupportedOperationException("Change deserialization for type" + type + " not implemented!");
		}
		return change;
	}
}
