package tools.vitruv.framework.versioning.extensions

import tools.vitruv.framework.versioning.EdgeType
import tools.vitruv.framework.versioning.extensions.impl.EdgeTypeExtensionImpl

interface EdgeTypeExtension {
	static def EdgeTypeExtension newManager() {
		EdgeTypeExtensionImpl::init
	}

	def String getEdgeLabel(EdgeType type)
}
