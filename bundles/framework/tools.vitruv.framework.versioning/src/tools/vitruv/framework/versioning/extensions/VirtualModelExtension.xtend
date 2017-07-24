package tools.vitruv.framework.versioning.extensions

import java.util.List
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.versioning.extensions.impl.VirtualModelExtensionImpl
import tools.vitruv.framework.vsum.VirtualModel
import tools.vitruv.framework.change.description.PropagatedChange

interface VirtualModelExtension {
	VirtualModelExtension instance = VirtualModelExtensionImpl::init

	def List<PropagatedChange> getChangeMatches(VirtualModel v, VURI vuri)

	def List<PropagatedChange> getChangeMatchesFrom(VirtualModel v, VURI vuri, String id)

	def List<PropagatedChange> getChangeMatchesFromTo(VirtualModel v, VURI vuri, String from, String to)
}
