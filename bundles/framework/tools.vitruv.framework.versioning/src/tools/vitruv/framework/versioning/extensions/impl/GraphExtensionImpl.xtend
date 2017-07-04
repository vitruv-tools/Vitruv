package tools.vitruv.framework.versioning.extensions.impl

import java.util.Collection
import org.eclipse.xtext.xbase.lib.Functions.Function1
import org.graphstream.algorithm.ConnectedComponents
import org.graphstream.graph.Edge
import org.graphstream.graph.Graph
import org.graphstream.stream.file.FileSinkImages
import org.graphstream.stream.file.FileSinkImages.LayoutPolicy
import org.graphstream.stream.file.FileSinkImages.OutputType
import org.graphstream.stream.file.FileSinkImages.Resolutions
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.EdgeType
import tools.vitruv.framework.versioning.extensions.EChangeExtension
import tools.vitruv.framework.versioning.extensions.EChangeNode
import tools.vitruv.framework.versioning.extensions.EdgeExtension
import tools.vitruv.framework.versioning.extensions.GraphExtension
import tools.vitruv.framework.versioning.extensions.NodeExtension
import java.util.Map
import tools.vitruv.framework.versioning.extensions.GraphStreamConstants

class GraphExtensionImpl implements GraphExtension {
	static extension EChangeExtension = EChangeExtension::newManager
	static extension EdgeExtension = EdgeExtension::newManager
	static extension NodeExtension = NodeExtension::newManager
	static val Map<Graph, Integer> graphMap = newHashMap

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
			case ISOMORPHIC: {
				graph.addUndirectedEdge(fromEchange, toEChange, type)
			}
			default: {
				throw new UnsupportedOperationException
			}
		}
	}

	private def addDirectedEdge(Graph graph, EChange e1, EChange e2, EdgeType type) {
		graph.addEdge(e1, e2, type, true)
	}

	private def addUndirectedEdge(Graph graph, EChange e1, EChange e2, EdgeType type) {
		graph.addEdge(e1, e2, type, false)
	}

	private def addEdge(Graph graph, EChange e1, EChange e2, EdgeType type, boolean directed) {
		val edge = graph.addChangeEdge(createEdgeName(e1, e2, type), e1, e2, directed)
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
		val currentLeaves = requiredGraph.leaves
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

	override savePicture(Graph graph) {
		val fsi = new FileSinkImages(OutputType.PNG, Resolutions.HD720)
		fsi.setLayoutPolicy(LayoutPolicy.COMPUTED_FULLY_AT_NEW_IMAGE)
		graph.write(fsi, '''./test_«graph.id»_write.png''')
	}

	override add(Graph graph, Graph graphToAdd) {
		val x = if (graphMap.containsKey(graph)) {
				graphMap.remove(graph)
			} else
				0

		graphToAdd.<EChangeNode>nodeSet.forEach [
			val newNode = graph.<EChangeNode>addNode(id)
			attributeKeySet.forEach [ attKey |
				val attribute = <String>getAttribute(attKey)
				newNode.addAttribute(attKey, attribute)
			]
			newNode.EChange = EChange
			newNode.setAttribute(GraphStreamConstants::uiClass, '''graph«x»''')
		]
		graphMap.put(graph, x + 1)
		graphToAdd.edgeSet.forEach [
			val newSourceNode = graph.getNode(sourceNode.id)
			val newTargetNode = graph.getNode(targetNode.id)
			val edge = graph.addEdge(id, newSourceNode, newTargetNode, directed)
			attributeKeySet.forEach [ attKey |
				val attribute = <String>getAttribute(attKey)
				edge.addAttribute(attKey, attribute)
			]
		]
	}

	override calculateComponentNumber(Graph graph) {
		val newGraph = graph.cloneGraph([true], [isType(EdgeType::PROVIDES)])
		val cc = new ConnectedComponents
		cc.init(newGraph)
		return cc.connectedComponentsCount
	}

}
