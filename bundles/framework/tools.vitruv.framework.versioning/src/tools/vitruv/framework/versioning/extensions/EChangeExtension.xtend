package tools.vitruv.framework.versioning.extensions

import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.EdgeType
import tools.vitruv.framework.versioning.extensions.impl.EChangeExtensionImpl

interface EChangeExtension {
	static def EChangeExtension newManager() {
		EChangeExtensionImpl::init
	}

	def String createEdgeName(EChange e1, EChange e2, EdgeType type)

	def String getNodeId(EChange e)

	def String getNodeLabel(EChange e)

}
