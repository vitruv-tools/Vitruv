package tools.vitruv.framework.versioning.extensions.impl

import org.graphstream.graph.Edge
import org.graphstream.graph.Graph
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.EdgeType
import tools.vitruv.framework.versioning.extensions.EChangeNode
import tools.vitruv.framework.versioning.impl.GraphExtensionImpl

interface GraphExtension {
	static def GraphExtension newManager() {
		GraphExtensionImpl::init
	}

	def Iterable<Edge> edgesWithType(Graph graph, EdgeType t)

	def Iterable<EChangeNode> getLeaves(Graph graph)

	def EChangeNode getNode(Graph graph, EChange e)

	def boolean checkIfEdgeExists(Graph graph, EChange e1, EChange e2)

	def void addEdge(Graph graph, EChange fromEchange, EChange toEChange, EdgeType type)

	def void addNode(Graph graph, EChange e)

	def boolean checkIfEdgeExists(
		Graph graph,
		EChange e1,
		EChange e2,
		EdgeType type
	)
}
