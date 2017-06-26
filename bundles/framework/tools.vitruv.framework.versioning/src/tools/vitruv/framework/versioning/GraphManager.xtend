package tools.vitruv.framework.versioning

import org.eclipse.emf.ecore.EObject
import org.graphstream.graph.Graph
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.impl.GraphManagerImpl

interface GraphManager {
	static def GraphManager getNewManager() {
		GraphManagerImpl::init
	}

	def void addNode(EChange e)

	def boolean checkIfEdgeExists(EChange e1, EChange e2)

	def void addAffectedEdge(EChange e1, EChange e2, EObject affectedObject)

	def Graph getGraph()
}
