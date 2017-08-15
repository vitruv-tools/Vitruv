package tools.vitruv.dsls.reactions.tests.versioning

import java.util.Collection

import org.junit.Test

import tools.vitruv.dsls.reactions.tests.AbstractConflictNotExistsTest
import tools.vitruv.framework.versioning.EdgeType
import tools.vitruv.framework.versioning.extensions.EChangeNode

import static org.hamcrest.collection.IsCollectionWithSize.hasSize
import static org.hamcrest.collection.IsEmptyCollection.empty
import static org.hamcrest.collection.IsIterableWithSize.iterableWithSize

import static org.hamcrest.CoreMatchers.is
import static org.junit.Assert.assertThat

class ConflictNotExistsDependencyGraphTest extends AbstractConflictNotExistsTest {

	@Test
	def void testEChangeNode() {
		graph = createDependencyGraph(changes)
		val Collection<EChangeNode> nodeSet = graph.nodeSet
		echanges.forEach[echange|nodeSet.exists[echange === EChange]]
	}

	@Test
	def testRequireEdges() {
		graph = createDependencyGraph(changes)
		val requiresEdges = graph.edgesWithType(EdgeType::REQUIRED)
		assertThat(requiresEdges, iterableWithSize(9))
		assertThat(graph.checkIfEdgeExists(echanges.get(0), echanges.get(1), EdgeType::REQUIRED), is(true))
		assertThat(graph.checkIfEdgeExists(echanges.get(0), echanges.get(2), EdgeType::REQUIRED), is(true))
		assertThat(graph.checkIfEdgeExists(echanges.get(2), echanges.get(3), EdgeType::REQUIRED), is(true))
		assertThat(graph.checkIfEdgeExists(echanges.get(2), echanges.get(4), EdgeType::REQUIRED), is(true))
		assertThat(graph.checkIfEdgeExists(echanges.get(2), echanges.get(6), EdgeType::REQUIRED), is(true))
		assertThat(graph.checkIfEdgeExists(echanges.get(2), echanges.get(8), EdgeType::REQUIRED), is(true))
		assertThat(graph.checkIfEdgeExists(echanges.get(4), echanges.get(5), EdgeType::REQUIRED), is(true))
		assertThat(graph.checkIfEdgeExists(echanges.get(6), echanges.get(7), EdgeType::REQUIRED), is(true))
		assertThat(graph.checkIfEdgeExists(echanges.get(8), echanges.get(9), EdgeType::REQUIRED), is(true))
		assertThat(graph.provideLeaves, iterableWithSize(1))
		assertThat(graph.provideLeaves.toList.get(0), is(graph.getNode(echanges.get(0))))
		assertThat(graph.calculateComponentNumber, is(1))
	}

	@Test
	def testSeveralComponents() {
		graph = createDependencyGraph(changes.drop(2).toList)
		val requiresEdges = graph.edgesWithType(EdgeType::REQUIRED)
		assertThat(requiresEdges, iterableWithSize(3))
		assertThat(graph.checkIfEdgeExists(echanges.get(4), echanges.get(5), EdgeType::REQUIRED), is(true))
		assertThat(graph.checkIfEdgeExists(echanges.get(6), echanges.get(7), EdgeType::REQUIRED), is(true))
		assertThat(graph.checkIfEdgeExists(echanges.get(8), echanges.get(9), EdgeType::REQUIRED), is(true))
		assertThat(graph.provideLeaves, iterableWithSize(3))
		assertThat(graph.calculateComponentNumber, is(3))
		val currentSubgraphs = graph.subgraphs
		assertThat(currentSubgraphs, iterableWithSize(3))
		currentSubgraphs.forEach [ g |
			assertThat(g.nodeSet, iterableWithSize(2))
			assertThat(g.edgeSet, iterableWithSize(1))
		]

	}

	@Test
	def testRequireEdgesChangeMatches() {
		val originalEChanges = branchDiff.baseChanges.map[originalChange].map[EChanges].flatten.toList
		val targetEChanges = branchDiff.baseChanges.map[consequentialChanges.EChanges].flatten.toList
		assertThat(originalEChanges.length, is(targetEChanges.length))
		graph = createDependencyGraphFromChangeMatches(branchDiff.baseChanges)
		val requiresEdges = graph.edgesWithType(EdgeType::REQUIRED)
		assertThat(requiresEdges, iterableWithSize(18))
		assertThat(graph.checkIfEdgeExists(originalEChanges.get(0), originalEChanges.get(1), EdgeType::REQUIRED),
			is(true))
		assertThat(graph.checkIfEdgeExists(originalEChanges.get(0), originalEChanges.get(2), EdgeType::REQUIRED),
			is(true))
		assertThat(graph.checkIfEdgeExists(originalEChanges.get(2), originalEChanges.get(3), EdgeType::REQUIRED),
			is(true))
		assertThat(graph.checkIfEdgeExists(originalEChanges.get(2), originalEChanges.get(4), EdgeType::REQUIRED),
			is(true))
		assertThat(graph.checkIfEdgeExists(originalEChanges.get(2), originalEChanges.get(6), EdgeType::REQUIRED),
			is(true))
		assertThat(graph.checkIfEdgeExists(originalEChanges.get(2), originalEChanges.get(8), EdgeType::REQUIRED),
			is(true))
		assertThat(graph.checkIfEdgeExists(originalEChanges.get(4), originalEChanges.get(5), EdgeType::REQUIRED),
			is(true))
		assertThat(graph.checkIfEdgeExists(originalEChanges.get(6), originalEChanges.get(7), EdgeType::REQUIRED),
			is(true))
		assertThat(graph.checkIfEdgeExists(originalEChanges.get(8), originalEChanges.get(9), EdgeType::REQUIRED),
			is(true))

		assertThat(graph.checkIfEdgeExists(targetEChanges.get(0), targetEChanges.get(1), EdgeType::REQUIRED), is(true))
		assertThat(graph.checkIfEdgeExists(targetEChanges.get(0), targetEChanges.get(2), EdgeType::REQUIRED), is(true))
		assertThat(graph.checkIfEdgeExists(targetEChanges.get(2), targetEChanges.get(3), EdgeType::REQUIRED), is(true))
		assertThat(graph.checkIfEdgeExists(targetEChanges.get(2), targetEChanges.get(4), EdgeType::REQUIRED), is(true))
		assertThat(graph.checkIfEdgeExists(targetEChanges.get(2), targetEChanges.get(6), EdgeType::REQUIRED), is(true))
		assertThat(graph.checkIfEdgeExists(targetEChanges.get(2), targetEChanges.get(8), EdgeType::REQUIRED), is(true))
		assertThat(graph.checkIfEdgeExists(targetEChanges.get(4), targetEChanges.get(5), EdgeType::REQUIRED), is(true))
		assertThat(graph.checkIfEdgeExists(targetEChanges.get(6), targetEChanges.get(7), EdgeType::REQUIRED), is(true))
		assertThat(graph.checkIfEdgeExists(targetEChanges.get(8), targetEChanges.get(9), EdgeType::REQUIRED), is(true))

		originalEChanges.forEach [ c, i |
			val otherEdge = targetEChanges.get(i)
			assertThat('''No edge between «c» and «otherEdge»''',
				graph.checkIfEdgeExists(c, otherEdge, EdgeType::TRIGGERS), is(true))
		]
		assertThat(graph.provideLeaves, iterableWithSize(2))
		assertThat(graph.provideLeaves.toList.get(0), is(graph.getNode(originalEChanges.get(0))))
		assertThat(graph.calculateComponentNumber, is(2))
	}

	@Test
	def void testConflictDetector() {
		assertThat(conflicts, is(empty))
		val conflictFreeEChanges = conflictDetector.conflictFreeOriginalEChanges
		assertThat(conflictFreeEChanges, hasSize(10))
	}
}
