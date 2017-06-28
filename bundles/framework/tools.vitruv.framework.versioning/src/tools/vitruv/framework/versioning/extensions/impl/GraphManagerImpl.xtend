package tools.vitruv.framework.versioning.extensions.impl

import org.eclipse.xtend.lib.annotations.Accessors
import org.graphstream.algorithm.ConnectedComponents
import org.graphstream.graph.Graph
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.EdgeType
import tools.vitruv.framework.versioning.extensions.EdgeExtension
import tools.vitruv.framework.versioning.extensions.GraphExtension
import tools.vitruv.framework.versioning.extensions.GraphManager

class GraphManagerImpl implements GraphManager {
	static extension EdgeExtension = EdgeExtension::newManager
	static extension GraphExtension = GraphExtension::newManager
	@Accessors(PUBLIC_GETTER, PUBLIC_SETTER)
	Graph graph

	static def GraphManager init() {
		new GraphManagerImpl
	}

	private new() {
		graph = GraphExtension::createNewEChangeGraph
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer")
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

	override calculateComponentNumber() {
		val newGraph = graph.cloneGraph([true], [isType(EdgeType::PROVIDES)])
		val cc = new ConnectedComponents
		cc.init(newGraph)
		return cc.connectedComponentsCount
	}

	override addNode(EChange e) { graph.addNode(e) }

	override edgesWithType(EdgeType t) { graph.edgesWithType(t) }

	override getLeaves() { graph.leaves }

	override getNode(EChange e) { graph.getNode(e) }

	override getSubgraphs() { graph.subgraphs }

}
