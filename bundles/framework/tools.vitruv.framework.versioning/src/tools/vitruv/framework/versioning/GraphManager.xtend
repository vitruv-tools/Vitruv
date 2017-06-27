package tools.vitruv.framework.versioning

import org.graphstream.graph.Edge
import org.graphstream.graph.Graph
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.impl.GraphManagerImpl
import org.graphstream.graph.Node

interface GraphManager {
	static def GraphManager getNewManager() {
		GraphManagerImpl::init
	}

	def Graph getGraph()

	def Iterable<Edge> edgesWithType(EdgeType t)

	def Iterable<Node> getLeaves()

	def Node getNode(EChange e)

	def boolean checkIfEdgeExists(EChange e1, EChange e2)

	def boolean checkIfEdgeExists(EChange e1, EChange e2, EdgeType type)

	def void addEdge(EChange fromEchange, EChange toEChange, EdgeType type)

	def void addNode(EChange e)

	def void setGraph(Graph graph)
}
