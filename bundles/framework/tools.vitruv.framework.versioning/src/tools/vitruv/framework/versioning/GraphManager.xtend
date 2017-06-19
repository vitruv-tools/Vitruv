package tools.vitruv.framework.versioning

import org.graphstream.graph.Graph
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.impl.GraphManagerImpl

interface GraphManager {
	static def GraphManager getNewManager() {
		GraphManagerImpl::init
	}

	def void addNode(VitruviusChange e)

	def boolean checkIfEdgeExists(EChange e1, EChange e2)

	def void addAffectedEdge(EChange e1, EChange e2)

	def Graph getGraph()
}
