package tools.vitruv.framework.versioning.extensions

import org.graphstream.graph.Edge
import tools.vitruv.framework.versioning.EdgeType
import tools.vitruv.framework.versioning.extensions.impl.EdgeExtensionImpl

interface EdgeExtension {
	static EdgeExtension instance = EdgeExtensionImpl::init

	def void setType(Edge edge, EdgeType type)

	def boolean isType(Edge edge, EdgeType type)

	def boolean isEChangeEdgeEqual(Edge e1, Edge e2)
}
