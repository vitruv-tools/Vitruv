package tools.vitruv.framework.versioning.extensions

import tools.vitruv.framework.versioning.extensions.impl.EChangeSerializerImpl
import tools.vitruv.framework.change.description.PropagatedChange
import java.util.List
import com.google.gson.JsonArray

interface EChangeSerializer {

	EChangeSerializer instance = EChangeSerializerImpl::init

	def List<PropagatedChange> deserializeAll(String allChangeString)

	def List<PropagatedChange> deserializeAll(JsonArray jsonArray)

	def PropagatedChange deserialize(String changeString)

	def String serialize(PropagatedChange change)

	def String serializeAll(List<PropagatedChange> change)

}
