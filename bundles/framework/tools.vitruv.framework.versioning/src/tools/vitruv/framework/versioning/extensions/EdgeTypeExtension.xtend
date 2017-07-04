package tools.vitruv.framework.versioning.extensions

import tools.vitruv.framework.versioning.EdgeType
import tools.vitruv.framework.versioning.extensions.impl.EdgeTypeExtensionImpl

interface EdgeTypeExtension {
	static EdgeTypeExtension instance = EdgeTypeExtensionImpl::init

	def String getEdgeLabel(EdgeType type)
}
