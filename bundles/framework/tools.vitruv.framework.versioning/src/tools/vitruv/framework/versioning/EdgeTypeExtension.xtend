package tools.vitruv.framework.versioning

import tools.vitruv.framework.versioning.impl.EdgeTypeExtensionImpl

interface EdgeTypeExtension {
	static def EdgeTypeExtension newManager() {
		EdgeTypeExtensionImpl::init
	}

	def String getEdgeLabel(EdgeType type)
}
