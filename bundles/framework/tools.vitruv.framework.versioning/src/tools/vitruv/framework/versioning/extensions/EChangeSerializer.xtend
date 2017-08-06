package tools.vitruv.framework.versioning.extensions

import tools.vitruv.framework.versioning.extensions.impl.EChangeSerializerImpl
import tools.vitruv.framework.change.description.PropagatedChange

interface EChangeSerializer {

	EChangeSerializer instance = EChangeSerializerImpl::init

	def String serialize(PropagatedChange change)

	def PropagatedChange deserialize(String changeString)

}
