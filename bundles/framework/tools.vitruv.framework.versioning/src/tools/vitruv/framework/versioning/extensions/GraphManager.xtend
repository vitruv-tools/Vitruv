package tools.vitruv.framework.versioning.extensions

import org.graphstream.graph.Edge
import org.graphstream.graph.Graph
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.EdgeType
import tools.vitruv.framework.versioning.extensions.impl.GraphManagerImpl
import java.util.Collection

interface GraphManager {
	static GraphManager instance = GraphManagerImpl::init

	def Graph getGraph()

	def Iterable<Edge> edgesWithType(EdgeType t)

	def EChangeNode getNode(EChange e)

	def boolean checkIfEdgeExists(EChange e1, EChange e2)

	def boolean checkIfEdgeExists(EChange e1, EChange e2, EdgeType type)

	def int calculateComponentNumber()

	def void addEdge(EChange fromEchange, EChange toEChange, EdgeType type)

	def void addNode(EChange e)

	def void setGraph(Graph graph)

	def Iterable<EChangeNode> getLeaves()

	def Collection<Graph> getSubgraphs()

	def void savePicture()

}
