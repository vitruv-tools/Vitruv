package tools.vitruv.framework.versioning

import tools.vitruv.framework.versioning.impl.EdgeExtensionImpl
import org.graphstream.graph.Edge

interface EdgeExtension {
	static def EdgeExtension newManager() {
		EdgeExtensionImpl::init
	}

	def void setType(Edge edge, EdgeType type)

	def boolean isType(Edge edge, EdgeType type)
}
