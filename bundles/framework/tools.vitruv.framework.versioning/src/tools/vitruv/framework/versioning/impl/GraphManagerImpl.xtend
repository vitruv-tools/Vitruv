package tools.vitruv.framework.versioning.impl

import org.eclipse.xtend.lib.annotations.Accessors
import org.graphstream.graph.Edge
import org.graphstream.graph.Graph
import org.graphstream.graph.Node
import org.graphstream.graph.implementations.SingleGraph
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.EdgeType
import tools.vitruv.framework.versioning.GraphManager
import tools.vitruv.framework.versioning.EChangeGraphStringUtil

class GraphManagerImpl implements GraphManager {
	static extension EChangeGraphStringUtil = EChangeGraphStringUtil::newManager
	static val uiLabel = "ui.label"
	@Accessors(PUBLIC_GETTER, PUBLIC_SETTER)
	Graph graph

	static def GraphManager init() {
		new GraphManagerImpl
	}

	private static def boolean isLeave(Node node) {
		val x = node.leavingEdgeSet.forall[!isType(EdgeType.REQUIRES)]
		return x
	}

	private static def boolean isType(Edge edge, EdgeType type) {
		edge.id.contains(type.toString)
	}

	private new() {
		graph = new SingleGraph("Test")
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer")
	}

	override addNode(EChange e) {
		val node = graph.addNode(e.nodeId)
		node.addAttribute(uiLabel, e.nodeLabel)
	}

	override getLeaves() {
		graph.nodeSet.filter[leave]
	}

	override checkIfEdgeExists(EChange e1, EChange e2) {
		val node1 = e1.node
		val node2 = e2.node
		val x = node1.hasEdgeBetween(node2)
		return x
	}

	override checkIfEdgeExists(EChange e1, EChange e2, EdgeType type) {
		val edgeExists = checkIfEdgeExists(e1, e2)
		if (!edgeExists)
			return false
		val edge = e1.node.getEdgeBetween(e2.node)
		return edge.isType(type)
	}

	override getNode(EChange e) {
		graph.getNode(e.nodeId)
	}

	override edgesWithType(EdgeType t) {
		graph.edgeSet.filter[id.contains(t.toString)]
	}

	override addEdge(EChange e1, EChange e2, EdgeType type) {
		switch (type) {
			case REQUIRES: {
				addDirectedEdge(e1, e2, type)
			}
			case TRIGGERS: {
				addDirectedEdge(e1, e2, type)
			}
			default: {
				throw new UnsupportedOperationException
			}
		}
	}

	private static def void setType(Edge edge, EdgeType type) {
		edge.addAttribute(uiLabel, type.edgeLabel)
	}

	private def addDirectedEdge(EChange e1, EChange e2, EdgeType type) {
		val edge = addChangeEdge(createEdgeName(e1, e2, type), e1, e2, true)
		edge.type = type
	}

	private def Edge addChangeEdge(String n, EChange e1, EChange e2, boolean directed) {
		graph.addEdge(n, e1.nodeId, e2.nodeId, directed)
	}

}
