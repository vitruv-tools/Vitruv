package tools.vitruv.framework.versioning.extensions

import java.util.List
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.versioning.ChangeMatch
import tools.vitruv.framework.versioning.extensions.impl.VirtualModelExtensionImpl
import tools.vitruv.framework.vsum.VirtualModel

interface VirtualModelExtension {
	VirtualModelExtension instance = VirtualModelExtensionImpl::init

	def List<ChangeMatch> getChangeMatches(VirtualModel v, VURI vuri)
}
