package tools.vitruv.framework.versioning.extensions

import tools.vitruv.framework.versioning.extensions.impl.EChangeSerializerImpl
import tools.vitruv.framework.change.description.PropagatedChange
import java.util.List

interface EChangeSerializer {

	EChangeSerializer instance = EChangeSerializerImpl::init

	def List<PropagatedChange> deserializeAll(String allChangeString)

	def PropagatedChange deserialize(String changeString)

	def String serialize(PropagatedChange change)

	def String serializeAll(List<PropagatedChange> change)

}
