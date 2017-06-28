package tools.vitruv.framework.versioning.extensions.impl

import java.util.Collection
import org.apache.log4j.Level
import org.apache.log4j.Logger
import org.eclipse.xtext.xbase.lib.Functions.Function1
import org.graphstream.graph.Edge
import org.graphstream.graph.Graph
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.EdgeType
import tools.vitruv.framework.versioning.extensions.EChangeExtension
import tools.vitruv.framework.versioning.extensions.EChangeNode
import tools.vitruv.framework.versioning.extensions.EdgeExtension
import tools.vitruv.framework.versioning.extensions.GraphExtension
import tools.vitruv.framework.versioning.extensions.NodeExtension

class GraphExtensionImpl implements GraphExtension {
	static extension EChangeExtension = EChangeExtension::newManager
	static extension NodeExtension = NodeExtension::newManager
	static extension EdgeExtension = EdgeExtension::newManager
	static extension Logger = Logger::getLogger(GraphExtensionImpl)

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
			case PROVIDES: {
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

	override cloneGraph(Graph oldgraph, Function1<EChangeNode, Boolean> nodePredicate,
		Function1<Edge, Boolean> edgePredicate) {
		val newGraph = createNewEChangeGraph
		oldgraph.<EChangeNode>nodeSet.filter[nodePredicate.apply(it)].forEach [ EChangeNode oldNode |
			val EChangeNode node = newGraph.addNode(oldNode.id)
			node.EChange = oldNode.EChange
		]
		oldgraph.edgeSet.filter[edgePredicate.apply(it)].forEach [
			val newSourceNode = newGraph.getNode(sourceNode.id)
			val newTargetNode = newGraph.getNode(targetNode.id)
			newGraph.addEdge(id, newSourceNode, newTargetNode, directed)
		]
		return newGraph
	}

	override getSubgraphs(Graph graph) {
		val requiredGraph = graph.cloneGraph([true], [isType(EdgeType::PROVIDES)])
		val edgesLength = requiredGraph.edgeSet.size
		val nodeLength = requiredGraph.nodeSet.size
		if (nodeLength !== graph.nodeSet.size)
			throw new IllegalStateException('''Length was «nodeLength» but should be «graph.nodeSet.size»''')
		if (edgesLength !== graph.edgeSet.size)
			throw new IllegalStateException('''Length was «edgesLength» but should be «graph.edgeSet.size»''')
		level = Level::DEBUG
		requiredGraph.edgeSet.forEach [ edge, i |
			debug('''Edge «i»''')
			debug(edge.id)
			debug(edge.sourceNode.id)
			debug(edge.targetNode.id)
			debug(edge.isType(EdgeType::PROVIDES))
			debug(edge.sourceNode.leave)
			debug(edge.targetNode.leave)
		]
		val currentLeaves = requiredGraph.leaves
		if (currentLeaves.length !== graph.leaves.length)
			throw new IllegalStateException('''Length was «currentLeaves.length» but should be «graph.leaves.length»''')
		val Collection<Graph> currentGraphs = newArrayList
		currentLeaves.forEach [ leave |
			val g = createNewEChangeGraph
			leave.breadthFirstIterator.forEach[EChangeNode n|g.addNode(n.EChange)]
			requiredGraph.edgeSet.forEach [ edge |
				val source = g.getNode(edge.sourceNode.id)
				val target = g.getNode(edge.targetNode.id)
				if (null !== source && null !== target)
					g.addEdge(edge.id, source, target)
			]
			currentGraphs += g
		]
		return currentGraphs
	}
}
