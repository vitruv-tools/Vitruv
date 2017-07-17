package tools.vitruv.framework.versioning.extensions

import java.util.Collection
import java.util.Set
import java.util.UUID
import org.eclipse.xtext.xbase.lib.Functions.Function1
import org.graphstream.graph.Edge
import org.graphstream.graph.Graph
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.EdgeType
import tools.vitruv.framework.versioning.extensions.EChangeNode
import tools.vitruv.framework.versioning.extensions.impl.EChangeGraphImpl
import tools.vitruv.framework.versioning.extensions.impl.GraphExtensionImpl

interface GraphExtension {
	static GraphExtension instance = GraphExtensionImpl::init

	static def Graph createNewEChangeGraph() {
		val id = UUID.randomUUID.toString
		new EChangeGraphImpl(id)
	}

	def Iterable<Edge> edgesWithType(Graph graph, EdgeType t)

	def Iterable<EChangeNode> getProvideLeaves(Graph graph)

	def Iterable<EChangeNode> getLeaves(Graph graph)

	def EChangeNode getNode(Graph graph, EChange e)

	def boolean checkIfEdgeExists(Graph graph, EChange e1, EChange e2)

	def void addEdge(Graph graph, EChange fromEchange, EChange toEChange, EdgeType type)

	def EChangeNode addNode(Graph graph, EChange e)

	def boolean checkIfEdgeExists(
		Graph graph,
		EChange e1,
		EChange e2,
		EdgeType type
	)

	def Graph cloneGraph(Graph oldgraph, Function1<EChangeNode, Boolean> nodePredicate,
		Function1<EChangeEdge, Boolean> edgePredicate)

	def Collection<Graph> getSubgraphs(Graph graph)

	def void savePicture(Graph graph)

	def void savePicture(Graph graph, String name)

	def void add(Graph graph, Graph graphToAdd)

	def int calculateComponentNumber(Graph graph)

	def Graph getSubgraphContainingEChanges(Graph graph, Set<EChange> nodes)
}
