package tools.vitruv.framework.versioning.extensions.impl

import org.eclipse.xtend.lib.annotations.Accessors

import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.EdgeType
import tools.vitruv.framework.versioning.extensions.GraphManager
import tools.vitruv.framework.versioning.extensions.GraphStreamConstants
import tools.vitruv.framework.versioning.EChangeGraph

class GraphManagerImpl implements GraphManager {
	@Accessors(PUBLIC_GETTER, PUBLIC_SETTER)
	EChangeGraph graph

	static def GraphManager init() {
		new GraphManagerImpl
	}

	private new() {
		graph = EChangeGraph::createEChangeGraph
		System.setProperty(GraphStreamConstants::renderer, GraphStreamConstants::currentRenderer)
	}

	override checkIfEdgeExists(EChange e1, EChange e2) {
		graph.checkIfEdgeExists(e1, e2)
	}

	override checkIfEdgeExists(EChange e1, EChange e2, EdgeType type) {
		graph.checkIfEdgeExists(e1, e2, type)
	}

	override addEdge(EChange e1, EChange e2, EdgeType type) {
		graph.addEdge(e1, e2, type)
	}

	override calculateComponentNumber() { graph.calculateComponentNumber }

	override addNode(EChange e) { graph.addNode(e) }

	override edgesWithType(EdgeType t) { graph.edgesWithType(t) }

	override getLeaves() { graph.provideLeaves }

	override getNode(EChange e) { graph.getNode(e) }

	override getSubgraphs() { graph.subgraphs }

	override savePicture() { graph.savePicture }

}
