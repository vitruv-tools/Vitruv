package tools.vitruv.framework.versioning.extensions

import org.graphstream.graph.Edge
import tools.vitruv.framework.versioning.EdgeType
import tools.vitruv.framework.versioning.extensions.impl.EdgeExtensionImpl

interface EdgeExtension {
	static def EdgeExtension newManager() {
		EdgeExtensionImpl::init
	}

	def void setType(Edge edge, EdgeType type)

	def boolean isType(Edge edge, EdgeType type)
}
