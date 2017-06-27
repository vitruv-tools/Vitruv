package tools.vitruv.framework.versioning

import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.impl.EChangeRequireManagerImpl

interface EChangeRequireManager {
	static def EChangeRequireManager getNewManager() {
		EChangeRequireManagerImpl::init
	}

	def boolean checkForRequireEdge(EChange parent, EChange child)
}
