package tools.vitruv.framework.versioning.common

import tools.vitruv.framework.change.description.PropagatedChange
import java.util.List
import com.google.gson.JsonArray
import tools.vitruv.framework.versioning.common.impl.EChangeSerializerImpl

interface EChangeSerializer {

	EChangeSerializer instance = EChangeSerializerImpl::init

	def List<PropagatedChange> deserializeAll(String allChangeString)

	def List<PropagatedChange> deserializeAll(JsonArray jsonArray)

	def PropagatedChange deserialize(String changeString)

	def String serialize(PropagatedChange change)

	def String serializeAll(List<PropagatedChange> change)

}
