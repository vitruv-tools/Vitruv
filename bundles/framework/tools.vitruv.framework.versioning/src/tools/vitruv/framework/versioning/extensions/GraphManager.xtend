package tools.vitruv.framework.versioning.extensions

import java.util.Collection

import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.EChangeGraph
import tools.vitruv.framework.versioning.EdgeType
import tools.vitruv.framework.versioning.extensions.impl.GraphManagerImpl

interface GraphManager {
	static GraphManager instance = GraphManagerImpl::init

	def Collection<EChangeGraph> getSubgraphs()

	def EChangeGraph getGraph()

	def EChangeNode getNode(EChange e)

	def Iterable<EChangeNode> getLeaves()

	def Iterable<EChangeEdge> edgesWithType(EdgeType t)

	def boolean checkIfEdgeExists(EChange e1, EChange e2)

	def boolean checkIfEdgeExists(EChange e1, EChange e2, EdgeType type)

	def int calculateComponentNumber()

	def void addEdge(EChange fromEchange, EChange toEChange, EdgeType type)

	def void addNode(EChange e)

	def void savePicture()

	def void setGraph(EChangeGraph graph)

}
