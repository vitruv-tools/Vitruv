package tools.vitruv.framework.versioning.tests

import org.junit.Test

import tools.vitruv.framework.versioning.EdgeType
import tools.vitruv.framework.versioning.IsomorphismTesterAlgorithm
import tools.vitruv.framework.versioning.extensions.EChangeNode

import static org.hamcrest.collection.IsCollectionWithSize.hasSize
import static org.hamcrest.collection.IsEmptyCollection.empty

import static org.hamcrest.CoreMatchers.is
import static org.junit.Assert.assertThat

class ConflictNotExistsGraphIsomorphismTest extends AbstractConflictNotExistsTest {
	@Test
	def void testOnlyOriginalEChanges() {
		graph = createDependencyGraph(changes)
		val otherChanges = branchDiff.compareChanges.map[originalChange]
		val otherGraph = createDependencyGraph(otherChanges)
		val IsomorphismTesterAlgorithm tester = IsomorphismTesterAlgorithm::createIsomorphismTester
		tester.init(graph, otherGraph)
		tester.compute
		assertThat(tester.isIsomorphic, is(true))
		val combinedGraph = tester.combinedGraph
		assertThat(combinedGraph.nodeSet, hasSize(graph.nodeSet.size + otherGraph.nodeSet.size))
		assertThat(combinedGraph.edgeSet.size >= graph.edgeSet.size + otherGraph.edgeSet.size, is(true))
		val iso = tester.isomorphism
		val unmatchedOfGraph1 = tester.unmatchedOfGraph1
		val unmatchedOfGraph2 = tester.unmatchedOfGraph2
		assertThat(unmatchedOfGraph1, is(empty))
		assertThat(unmatchedOfGraph2, is(empty))
		graph.<EChangeNode>nodeSet.forEach [ node1 |
			val node2 = iso.get(node1)
			assertThat(otherGraph.<EChangeNode>nodeSet.exists[node2 == it], is(true))
			assertThat(combinedGraph.checkIfEdgeExists(node1.EChange, node2.EChange, EdgeType::ISOMORPHIC), is(true))
			assertThat(node1.enteringEdgeSet.size, is(node2.enteringEdgeSet.size))
			assertThat(node1.leavingEdgeSet.size, is(node2.leavingEdgeSet.size))
		]
	}

	@Test
	def void testWithCorrespondence() {
		graph = createDependencyGraphFromChangeMatches(branchDiff.baseChanges)
		val otherGraph = createDependencyGraphFromChangeMatches(branchDiff.compareChanges)
		val IsomorphismTesterAlgorithm tester = IsomorphismTesterAlgorithm::createIsomorphismTester
		tester.init(graph, otherGraph)
		tester.compute
		assertThat(tester.isIsomorphic, is(true))
		val combinedGraph = tester.combinedGraph
		assertThat(combinedGraph.nodeSet, hasSize(graph.nodeSet.size + otherGraph.nodeSet.size))
		assertThat(combinedGraph.edgeSet.size >= graph.edgeSet.size + otherGraph.edgeSet.size, is(true))
		val iso = tester.isomorphism
		val unmatchedOfGraph1 = tester.unmatchedOfGraph1
		val unmatchedOfGraph2 = tester.unmatchedOfGraph2
		assertThat(unmatchedOfGraph1, is(empty))
		assertThat(unmatchedOfGraph2, is(empty))
		graph.<EChangeNode>nodeSet.forEach [ node1 |
			val node2 = iso.get(node1)
			assertThat(otherGraph.<EChangeNode>nodeSet.exists[node2 == it], is(true))
			assertThat(combinedGraph.checkIfEdgeExists(node1.EChange, node2.EChange, EdgeType::ISOMORPHIC), is(true))
			assertThat(node1.enteringEdgeSet.size, is(node2.enteringEdgeSet.size))
			assertThat(node1.leavingEdgeSet.size, is(node2.leavingEdgeSet.size))
		]
	}

	@Test
	def void testConflictDetector() {
		assertThat(conflicts, is(empty))
		val conflictFreeEChanges = conflictDetector.conflictFreeOriginalEChanges
		assertThat(conflictFreeEChanges, hasSize(10))
	}

}
