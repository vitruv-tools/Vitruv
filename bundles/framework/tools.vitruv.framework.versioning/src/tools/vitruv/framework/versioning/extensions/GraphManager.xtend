package tools.vitruv.framework.versioning.extensions

import org.graphstream.graph.Graph
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.EdgeType
import tools.vitruv.framework.versioning.extensions.impl.GraphManagerImpl
import org.graphstream.graph.Node
import org.graphstream.graph.Edge

interface GraphManager {
	static def GraphManager getNewManager() {
		GraphManagerImpl::init
	}

	def Graph getGraph()

	def Iterable<Edge> edgesWithType(EdgeType t)

	def Node getNode(EChange e)

	def boolean checkIfEdgeExists(EChange e1, EChange e2)

	def boolean checkIfEdgeExists(EChange e1, EChange e2, EdgeType type)

	def int calculateComponentNumber()

	def void addEdge(EChange fromEchange, EChange toEChange, EdgeType type)

	def void addNode(EChange e)

	def void setGraph(Graph graph)

	def Iterable<Node> getLeaves()
}
