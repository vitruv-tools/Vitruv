package tools.vitruv.framework.versioning.impl

import java.util.Map
import java.util.Set

import org.eclipse.xtend.lib.annotations.Accessors

import tools.vitruv.framework.versioning.EChangeGraph
import tools.vitruv.framework.versioning.EdgeType
import tools.vitruv.framework.versioning.IsomorphismTesterAlgorithm
import tools.vitruv.framework.versioning.NodeType
import tools.vitruv.framework.versioning.extensions.EChangeNode

class PrimitiveIsomorphismTesterImpl implements IsomorphismTesterAlgorithm {
	// Values.
	@Accessors(PUBLIC_GETTER)
	val Set<EChangeNode> unmatchedOfGraph1

	@Accessors(PUBLIC_GETTER)
	val Set<EChangeNode> unmatchedOfGraph2

	// Variables.
	@Accessors(PUBLIC_GETTER)
	EChangeGraph combinedGraph

	@Accessors(PUBLIC_GETTER)
	Map<EChangeNode, EChangeNode> isomorphism

	@Accessors(PUBLIC_GETTER)
	boolean isomorphic

	EChangeGraph graph1
	EChangeGraph graph2

	private new() {
		unmatchedOfGraph1 = newHashSet
		unmatchedOfGraph2 = newHashSet
	}

	static def IsomorphismTesterAlgorithm createPrimitiveIsomorphismTesterImpl() {
		new PrimitiveIsomorphismTesterImpl
	}

	// Overridden methods.
	override areIsomorphic(EChangeGraph g1, EChangeGraph g2) {
		unmatchedOfGraph1.clear
		unmatchedOfGraph2.clear
		processGraphs(g1, g2, unmatchedOfGraph1)
		processGraphs(g2, g1, unmatchedOfGraph2)
		return unmatchedOfGraph1.empty && unmatchedOfGraph2.empty
	}

	override init(EChangeGraph g1, EChangeGraph g2) {
		combinedGraph = null
		graph1 = g1
		graph2 = g2
		isomorphic = false
		isomorphism = null
	}

	override compute() {
		combinedGraph = EChangeGraph::createEChangeGraph
		combinedGraph.add(graph1)
		combinedGraph.add(graph2)
		combinedGraph.savePicture
		isomorphism = newHashMap
		isomorphic = areIsomorphic(graph1, graph2)
		combinedGraph.savePicture
	}

	// Private methods.
	private def void processGraphs(EChangeGraph g1, EChangeGraph g2, Set<EChangeNode> nodes) {
		g1.<EChangeNode>nodeSet.filter [ node1 |
			!g2.<EChangeNode>nodeSet.exists [ node2 |
				if (node1.isEChangeNodeEqual(node2)) {
					return processNodePair(node1, node2)
				}
				return false
			]
		].forEach [
			val node = combinedGraph.getNode(EChange)
			node.type = NodeType::UNPAIRED
			nodes += node
		]
	}

	private def boolean processNodePair(EChangeNode node1, EChangeNode node2) {
		if (combinedGraph.checkIfEdgeExists(node1.EChange, node2.EChange, EdgeType::ISOMORPHIC))
			return true
		if (isomorphism.containsKey(node1))
			throw new IllegalStateException('''«node1» has to mappings: «node2» and «isomorphism.get(node1)»''')
		isomorphism.put(node1, node2)
		combinedGraph.addEdge(node1.EChange, node2.EChange, EdgeType::ISOMORPHIC)
		return true
	}
}
