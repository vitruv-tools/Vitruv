package tools.vitruv.framework.versioning.extensions

import java.util.List
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.versioning.extensions.impl.VirtualModelExtensionImpl
import tools.vitruv.framework.vsum.VersioningVirtualModel

interface VirtualModelExtension {
	VirtualModelExtension instance = VirtualModelExtensionImpl::init

	def List<PropagatedChange> getChangeMatches(VersioningVirtualModel v, VURI vuri)

	def List<PropagatedChange> getChangeMatchesFrom(VersioningVirtualModel v, VURI vuri, String id)

	def List<PropagatedChange> getChangeMatchesFromTo(VersioningVirtualModel v, VURI vuri, String from, String to)
}
