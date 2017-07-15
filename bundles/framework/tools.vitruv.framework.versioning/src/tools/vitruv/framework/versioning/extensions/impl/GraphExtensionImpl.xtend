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
import java.util.Set

class GraphExtensionImpl implements GraphExtension {

	static extension EChangeExtension = EChangeExtension::instance
	static extension EdgeExtension = EdgeExtension::instance
	static extension NodeExtension = NodeExtension::instance
	static val Map<Graph, Integer> graphMap = newHashMap
	static val VM_ARGUMENT_PRINT_PICTURES = "printPictures"

	static def GraphExtension init() {
		new GraphExtensionImpl
	}

	private new() {
	}

	override addNode(Graph graph, EChange e) {
		val EChangeNode node = graph.addNode(e.nodeId)
		node.label = e.nodeLabel
		node.EChange = e
		return node
	}

	override getProvideLeaves(Graph graph) {
		graph.determineLeaves([EChangeNode n|n.provideLeave])
	}

	override getLeaves(Graph graph) {
		graph.determineLeaves([EChangeNode n|n.leave])
	}

	private static def determineLeaves(Graph graph, Function1<EChangeNode, Boolean> nodePredicate) {
		graph.<EChangeNode>nodeSet.filter[nodePredicate.apply(it)]
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
			case REQUIRED: {
				graph.addDirectedEdge(fromEchange, toEChange, type)
				graph.addDirectedEdge(toEChange, fromEchange, type.inverse)
			}
			case TRIGGERS: {
				graph.addDirectedEdge(fromEchange, toEChange, type)
			}
			case ISOMORPHIC: {
				graph.addUndirectedEdge(fromEchange, toEChange, type)
			}
			case CONFLICTS: {
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
			node.triggered = oldNode.triggered
		]
		oldgraph.edgeSet.filter [
			nodePredicate.apply(sourceNode) && nodePredicate.apply(targetNode)
		].filter[edgePredicate.apply(it)].forEach [
			val newSourceNode = newGraph.getNode(sourceNode.id)
			val newTargetNode = newGraph.getNode(targetNode.id)
			newGraph.addEdge(id, newSourceNode, newTargetNode, directed)
		]
		return newGraph
	}

	override getSubgraphs(Graph graph) {
		val requiredGraph = graph.cloneGraph([true], [isType(EdgeType::REQUIRED)])
		val currentLeaves = requiredGraph.provideLeaves
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

	override calculateComponentNumber(Graph graph) {
		val newGraph = graph.cloneGraph([true], [isType(EdgeType::REQUIRED)])
		val cc = new ConnectedComponents
		cc.init(newGraph)
		return cc.connectedComponentsCount
	}

	override getSubgraphContainingEChanges(Graph graph, Set<EChange> nodes) {
		graph.cloneGraph([EChangeNode n|nodes.contains(n.EChange)], [
			isType(EdgeType::REQUIRED) || isType(EdgeType::TRIGGERS)
		])
	}

	override savePicture(Graph graph, String name) {
		val isPrintActive = null !==
			System.getProperty(
				tools.vitruv.framework.versioning.extensions.impl.GraphExtensionImpl.VM_ARGUMENT_PRINT_PICTURES)
		if (isPrintActive) {
			val fsi = new FileSinkImages(OutputType.PNG, Resolutions.HD720)
			fsi.setLayoutPolicy(LayoutPolicy.COMPUTED_FULLY_AT_NEW_IMAGE)
			graph.write(fsi, '''./test_«name»_write.png''')
		}
	}

	override savePicture(Graph graph) {
		graph.savePicture(graph.id)
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

}
