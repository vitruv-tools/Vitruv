package tools.vitruv.framework.versioning

import org.eclipse.emf.ecore.EObject
import org.graphstream.graph.Graph
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.impl.GraphManagerImpl
import org.graphstream.graph.Edge

interface GraphManager {
	static def GraphManager getNewManager() {
		GraphManagerImpl::init
	}

	def void addNode(EChange e)

	def boolean checkIfEdgeExists(EChange e1, EChange e2)

	def boolean checkIfEdgeExists(EChange e1, EChange e2, EdgeType type)

	def void addAffectedEdge(EChange e1, EChange e2, EObject affectedObject)

	def Graph getGraph()

	def void setGraph(Graph graph)

	def Iterable<Edge> edgesWithType(EdgeType t)
}
