package tools.vitruv.framework.versioning.impl

import java.util.Map
import org.eclipse.xtend.lib.annotations.Accessors
import org.graphstream.graph.Graph
import tools.vitruv.framework.versioning.IsomorphismTesterAlgorithm
import tools.vitruv.framework.versioning.extensions.EChangeNode
import tools.vitruv.framework.versioning.extensions.GraphExtension
import tools.vitruv.framework.versioning.EdgeType

class PrimitiveIsomorphismTesterImpl implements IsomorphismTesterAlgorithm {
	extension GraphExtension = GraphExtension::newManager
	Graph graph1
	Graph graph2
	@Accessors(PUBLIC_GETTER)
	Graph combinedGraph
	@Accessors(PUBLIC_GETTER)
	Map<EChangeNode, EChangeNode> isomorphism
	@Accessors(PUBLIC_GETTER)
	boolean isomorphic

	override areIsomorphic(Graph g1, Graph g2) {
		g1.<EChangeNode>nodeSet.forall [ node1 |
			g2.<EChangeNode>nodeSet.exists [ node2 |
				if (node1.isEChangeNodeEqual(node2)) {
					processNodePair(node1, node2)
					return true
				}
				return false

			]
		]
	}

	override findIsomorphism(Graph g1, Graph g2) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}

	override init(Graph g1, Graph g2) {
		combinedGraph = null
		graph1 = g1
		graph2 = g2
		isomorphic = false
		isomorphism = null
	}

	override compute() {
		combinedGraph = GraphExtension::createNewEChangeGraph
		combinedGraph.add(graph1)
		combinedGraph.add(graph2)
		combinedGraph.savePicture
		isomorphism = newHashMap
		isomorphic = areIsomorphic(graph1, graph2)
		combinedGraph.savePicture
	}

	private def void processNodePair(EChangeNode node1, EChangeNode node2) {
		if (!isomorphism.containsKey(node1))
			isomorphism.put(node1, node2)
		else
			throw new IllegalStateException('''«node1» has to mappings: «node2» and «isomorphism.get(node1)»''')

		this.combinedGraph.addEdge(node1.EChange, node2.EChange, EdgeType::ISOMORPHIC)
	}
}
