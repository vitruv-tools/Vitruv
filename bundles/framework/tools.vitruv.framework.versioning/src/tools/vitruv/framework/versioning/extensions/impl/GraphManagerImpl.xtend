package tools.vitruv.framework.versioning.extensions.impl

import org.eclipse.xtend.lib.annotations.Accessors
import org.graphstream.graph.Edge
import org.graphstream.graph.Graph
import org.graphstream.graph.implementations.SingleGraph
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.EdgeType
import tools.vitruv.framework.versioning.extensions.EChangeExtension
import tools.vitruv.framework.versioning.extensions.EdgeExtension
import tools.vitruv.framework.versioning.extensions.GraphManager
import tools.vitruv.framework.versioning.extensions.NodeExtension

class GraphManagerImpl implements GraphManager {
	static extension EChangeExtension = EChangeExtension::newManager
	static extension EdgeExtension = EdgeExtension::newManager
	static extension NodeExtension = NodeExtension::newManager

	@Accessors(PUBLIC_GETTER, PUBLIC_SETTER)
	Graph graph

	static def GraphManager init() {
		new GraphManagerImpl
	}

	private new() {
		graph = new SingleGraph("Test")
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer")
	}

	override addNode(EChange e) {
		val node = graph.addNode(e.nodeId)
		node.label = e.nodeLabel
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

	private def addDirectedEdge(EChange e1, EChange e2, EdgeType type) {
		val edge = addChangeEdge(createEdgeName(e1, e2, type), e1, e2, true)
		edge.type = type
	}

	private def Edge addChangeEdge(String n, EChange e1, EChange e2, boolean directed) {
		graph.addEdge(n, e1.nodeId, e2.nodeId, directed)
	}

	override calculateComponentNumber() {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}

}
