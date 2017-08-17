package tools.vitruv.framework.versioning

import java.util.Collection
import java.util.Set

import org.eclipse.xtext.xbase.lib.Functions.Function1

import org.graphstream.graph.Graph

import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.extensions.EChangeEdge
import tools.vitruv.framework.versioning.extensions.EChangeNode
import tools.vitruv.framework.versioning.extensions.impl.EChangeGraphImpl

interface EChangeGraph extends Graph {
	static def EChangeGraph createEChangeGraph() {
		return EChangeGraphImpl::createNewEChangeGraph
	}

	def Collection<EChangeGraph> getSubgraphs()

	def EChangeGraph getSubgraphContainingEChanges(Set<EChange> nodes)

	def EChangeNode addNode(EChange e)

	def EChangeNode getNode(EChange e)

	def Iterable<EChangeEdge> edgesWithType(EdgeType t)

	def Iterable<EChangeNode> getLeaves()

	def Iterable<EChangeNode> getProvideLeaves()

	def boolean checkIfEdgeExists(EChange e1, EChange e2)

	def int calculateComponentNumber()

	def void add(EChangeGraph graphToAdd)

	def void addEdge(EChange fromEchange, EChange toEChange, EdgeType type)

	def void savePicture()

	def void savePicture(String name)

	def EChangeGraph cloneGraph(
		Function1<EChangeNode, Boolean> nodePredicate,
		Function1<EChangeEdge, Boolean> edgePredicate
	)

	def boolean checkIfEdgeExists(
		EChange e1,
		EChange e2,
		EdgeType type
	)
}
