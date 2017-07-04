package tools.vitruv.framework.versioning.extensions

import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.extensions.impl.EChangeRequireExtensionImpl

interface EChangeRequireExtension {
	static EChangeRequireExtension instance = EChangeRequireExtensionImpl::init

	def boolean checkForRequireEdge(EChange parent, EChange child)
}
