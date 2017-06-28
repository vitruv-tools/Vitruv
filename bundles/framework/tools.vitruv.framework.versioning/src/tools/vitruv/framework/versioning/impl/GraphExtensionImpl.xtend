package tools.vitruv.framework.versioning.impl

import org.graphstream.graph.Graph
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.EdgeType
import tools.vitruv.framework.versioning.extensions.EChangeExtension
import tools.vitruv.framework.versioning.extensions.NodeExtension
import tools.vitruv.framework.versioning.extensions.impl.GraphExtension
import tools.vitruv.framework.versioning.extensions.EdgeExtension
import org.graphstream.graph.Edge
import tools.vitruv.framework.versioning.extensions.EChangeNode

class GraphExtensionImpl implements GraphExtension {
	static extension EChangeExtension = EChangeExtension::newManager
	static extension NodeExtension = NodeExtension::newManager
	static extension EdgeExtension = EdgeExtension::newManager

	static def GraphExtension init() {
		new GraphExtensionImpl
	}

	private new() {
	}

	override addNode(Graph graph, EChange e) {
		val EChangeNode node = graph.addNode(e.nodeId)
		node.label = e.nodeLabel
		node.EChange = e
	}

	override getLeaves(Graph graph) {
		graph.nodeSet.filter[leave]
	}

	override getNode(Graph graph, EChange e) {
		graph.getNode(e.nodeId)
	}

	override edgesWithType(Graph graph, EdgeType t) {
		graph.edgeSet.filter[id.contains(t.toString)]
	}

	override checkIfEdgeExists(Graph graph, EChange e1, EChange e2) {
		val node1 = graph.getNode(e1)
		val node2 = graph.getNode(e2)
		val x = node1.hasEdgeBetween(node2)
		return x
	}

	override checkIfEdgeExists(Graph graph, EChange e1, EChange e2, EdgeType type) {
		val edgeExists = graph.checkIfEdgeExists(e1, e2)
		if (!edgeExists)
			return false
		val edge = graph.getNode(e1).getEdgeBetween(graph.getNode(e2))
		return edge.isType(type)
	}

	override addEdge(Graph graph, EChange fromEchange, EChange toEChange, EdgeType type) {
		switch (type) {
			case REQUIRES: {
				graph.addDirectedEdge(fromEchange, toEChange, type)
			}
			case TRIGGERS: {
				graph.addDirectedEdge(fromEchange, toEChange, type)
			}
			default: {
				throw new UnsupportedOperationException
			}
		}
	}

	private def addDirectedEdge(Graph graph, EChange e1, EChange e2, EdgeType type) {
		val edge = graph.addChangeEdge(createEdgeName(e1, e2, type), e1, e2, true)
		edge.type = type
	}

	private def Edge addChangeEdge(Graph graph, String n, EChange e1, EChange e2, boolean directed) {
		graph.addEdge(n, e1.nodeId, e2.nodeId, directed)
	}

}
