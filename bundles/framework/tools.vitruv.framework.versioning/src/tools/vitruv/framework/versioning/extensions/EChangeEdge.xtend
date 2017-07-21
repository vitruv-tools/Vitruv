package tools.vitruv.framework.versioning.extensions

import org.graphstream.graph.Edge
import tools.vitruv.framework.versioning.EdgeType

interface EChangeEdge extends Edge {
	def void setType(EdgeType type)

	def EdgeType getType()

	def boolean isType(EdgeType type)

	def boolean isEChangeEdgeEqual(Edge e2)
}
