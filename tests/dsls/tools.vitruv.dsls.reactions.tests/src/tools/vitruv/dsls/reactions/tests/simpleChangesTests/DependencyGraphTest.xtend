package tools.vitruv.dsls.reactions.tests.simpleChangesTests

import org.junit.Test

import tools.vitruv.framework.versioning.DependencyGraphCreator
import tools.vitruv.framework.versioning.EdgeType
import tools.vitruv.framework.versioning.extensions.GraphManager

import static org.hamcrest.CoreMatchers.is
import static org.junit.Assert.assertThat
import tools.vitruv.framework.versioning.extensions.EChangeNode
import java.util.Collection

class DependencyGraphTest extends NoConflict {
	static extension GraphManager = GraphManager::newManager

	@Test
	def void testEChangeNode() {
		val changes = branchDiff.baseChanges.map[originalChange].toList
		val echanges = changes.map[EChanges].flatten.toList
		val dependencyGraphCreator = DependencyGraphCreator::createDependencyGraphCreator
		graph = dependencyGraphCreator.createDependencyGraph(changes)
		val Collection<EChangeNode> nodeSet = graph.nodeSet
		echanges.forEach[echange|nodeSet.exists[echange === EChange]]
	}

	@Test
	def testRequireEdges() {
		val changes = branchDiff.baseChanges.map[originalChange].toList
		val echanges = changes.map[EChanges].flatten.toList
		val dependencyGraphCreator = DependencyGraphCreator::createDependencyGraphCreator
		graph = dependencyGraphCreator.createDependencyGraph(changes)
		val requiresEdges = edgesWithType(EdgeType.PROVIDES)
		assertThat(requiresEdges.size, is(7))
		assertThat(checkIfEdgeExists(echanges.get(0), echanges.get(1), EdgeType.PROVIDES), is(true))
		assertThat(checkIfEdgeExists(echanges.get(0), echanges.get(2), EdgeType.PROVIDES), is(true))
		assertThat(checkIfEdgeExists(echanges.get(0), echanges.get(4), EdgeType.PROVIDES), is(true))
		assertThat(checkIfEdgeExists(echanges.get(0), echanges.get(6), EdgeType.PROVIDES), is(true))
		assertThat(checkIfEdgeExists(echanges.get(2), echanges.get(3), EdgeType.PROVIDES), is(true))
		assertThat(checkIfEdgeExists(echanges.get(4), echanges.get(5), EdgeType.PROVIDES), is(true))
		assertThat(checkIfEdgeExists(echanges.get(6), echanges.get(7), EdgeType.PROVIDES), is(true))
		assertThat(leaves.length, is(1))
		assertThat(leaves.toList.get(0), is(echanges.get(0).node))
		assertThat(calculateComponentNumber, is(1))
	}

	@Test
	def testSeveralComponents() {
		val changes = branchDiff.baseChanges.map[originalChange].toList
		val echanges = changes.map[EChanges].flatten.toList
		val dependencyGraphCreator = DependencyGraphCreator::createDependencyGraphCreator
		graph = dependencyGraphCreator.createDependencyGraph(changes.drop(1).toList)
		val requiresEdges = edgesWithType(EdgeType.PROVIDES)
		assertThat(requiresEdges.size, is(3))
		assertThat(checkIfEdgeExists(echanges.get(2), echanges.get(3), EdgeType.PROVIDES), is(true))
		assertThat(checkIfEdgeExists(echanges.get(4), echanges.get(5), EdgeType.PROVIDES), is(true))
		assertThat(checkIfEdgeExists(echanges.get(6), echanges.get(7), EdgeType.PROVIDES), is(true))
		assertThat(leaves.length, is(3))
		assertThat(calculateComponentNumber, is(3))
		val currentSubgraphs = subgraphs
		assertThat(currentSubgraphs.length, is(3))
		currentSubgraphs.forEach [ g |
			assertThat(g.length, is(2))
		]

	}

	@Test
	def testRequireEdgesChangeMatches() {
		val originalEChanges = branchDiff.baseChanges.map[originalChange].map[EChanges].flatten.toList
		val targetEChanges = branchDiff.baseChanges.map[it.targetToCorrespondentChanges.values].flatten.flatten.map [
			EChanges
		].flatten.toList
		assertThat(originalEChanges.length, is(targetEChanges.length))
		val dependencyGraphCreator = DependencyGraphCreator::createDependencyGraphCreator
		graph = dependencyGraphCreator.createDependencyGraphFromChangeMatches(branchDiff.baseChanges)
		val requiresEdges = edgesWithType(EdgeType.PROVIDES)
		assertThat(requiresEdges.size, is(14))
		assertThat(checkIfEdgeExists(originalEChanges.get(0), originalEChanges.get(1), EdgeType.PROVIDES), is(true))
		assertThat(checkIfEdgeExists(originalEChanges.get(0), originalEChanges.get(2), EdgeType.PROVIDES), is(true))
		assertThat(checkIfEdgeExists(originalEChanges.get(0), originalEChanges.get(4), EdgeType.PROVIDES), is(true))
		assertThat(checkIfEdgeExists(originalEChanges.get(0), originalEChanges.get(6), EdgeType.PROVIDES), is(true))
		assertThat(checkIfEdgeExists(originalEChanges.get(2), originalEChanges.get(3), EdgeType.PROVIDES), is(true))
		assertThat(checkIfEdgeExists(originalEChanges.get(4), originalEChanges.get(5), EdgeType.PROVIDES), is(true))
		assertThat(checkIfEdgeExists(originalEChanges.get(6), originalEChanges.get(7), EdgeType.PROVIDES), is(true))

		assertThat(checkIfEdgeExists(targetEChanges.get(0), targetEChanges.get(1), EdgeType.PROVIDES), is(true))
		assertThat(checkIfEdgeExists(targetEChanges.get(0), targetEChanges.get(2), EdgeType.PROVIDES), is(true))
		assertThat(checkIfEdgeExists(targetEChanges.get(0), targetEChanges.get(4), EdgeType.PROVIDES), is(true))
		assertThat(checkIfEdgeExists(targetEChanges.get(0), targetEChanges.get(6), EdgeType.PROVIDES), is(true))
		assertThat(checkIfEdgeExists(targetEChanges.get(2), targetEChanges.get(3), EdgeType.PROVIDES), is(true))
		assertThat(checkIfEdgeExists(targetEChanges.get(4), targetEChanges.get(5), EdgeType.PROVIDES), is(true))
		assertThat(checkIfEdgeExists(targetEChanges.get(6), targetEChanges.get(7), EdgeType.PROVIDES), is(true))

		originalEChanges.forEach [ c, i |
			val otherEdge = targetEChanges.get(i)
			assertThat('''No edge between «c» and «otherEdge»''', checkIfEdgeExists(c, otherEdge, EdgeType::TRIGGERS),
				is(true))
		]
		assertThat(leaves.length, is(2))
		assertThat(leaves.toList.get(0), is(originalEChanges.get(0).node))
		assertThat(calculateComponentNumber, is(2))
	}
}
