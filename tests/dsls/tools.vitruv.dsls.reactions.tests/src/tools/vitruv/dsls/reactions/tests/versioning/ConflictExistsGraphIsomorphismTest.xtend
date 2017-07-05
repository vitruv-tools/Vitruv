package tools.vitruv.dsls.reactions.tests.versioning

import java.util.Set
import org.junit.Test

import tools.vitruv.dsls.reactions.tests.AbstractConflictExistsTest
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.IsomorphismTesterAlgorithm
import tools.vitruv.framework.versioning.extensions.EChangeNode
import tools.vitruv.framework.versioning.impl.PrimitiveIsomorphismTesterImpl
import static org.hamcrest.CoreMatchers.hasItem
import static org.hamcrest.CoreMatchers.is
import static org.hamcrest.CoreMatchers.not
import static org.hamcrest.CoreMatchers.equalTo
import static org.junit.Assert.assertThat
import tools.vitruv.framework.versioning.MultiChangeConflict
import tools.vitruv.framework.versioning.ConflictSolvability
import tools.vitruv.framework.versioning.ConflictType

class ConflictExistsGraphIsomorphismTest extends AbstractConflictExistsTest {
	@Test
	def void testOnlyOriginalEChanges() {
		graph = createDependencyGraph(changes)
		val otherChanges = branchDiff.compareChanges.map[originalChange]
		val otherEChanges = branchDiff.compareChanges.map[originalChange.EChanges].flatten
		val otherGraph = createDependencyGraph(otherChanges)
		val IsomorphismTesterAlgorithm tester = new PrimitiveIsomorphismTesterImpl
		tester.init(graph, otherGraph)
		tester.compute
		assertThat(tester.isIsomorphic, is(false))
		val combinedGraph = tester.combinedGraph
		assertThat(combinedGraph.nodeSet.size, is(graph.nodeSet.size + otherGraph.nodeSet.size))
		assertThat(combinedGraph.edgeSet.size >= graph.edgeSet.size + otherGraph.edgeSet.size, is(true))
		val unmatchedOfGraph1 = tester.unmatchedOfGraph1
		val unmatchedOfGraph2 = tester.unmatchedOfGraph2
		assertThat(unmatchedOfGraph1.size, is(2))
		assertThat(unmatchedOfGraph2.size, is(2))
		val testEchanges = [ Set<EChangeNode> nodes, Iterable<EChange> eChanges |
			nodes.map[EChange].forEach [
				assertThat(eChanges, hasItem(it))
			]
		]
		testEchanges.apply(unmatchedOfGraph1, echanges)
		testEchanges.apply(unmatchedOfGraph2, otherEChanges)
	}

	@Test
	def void testWithCorrespondence() {
		graph = createDependencyGraphFromChangeMatches(branchDiff.baseChanges)
		val otherEChanges = branchDiff.compareChanges.map[originalChange.EChanges].flatten
		val correspondentEChanges = branchDiff.baseChanges.map[targetToCorrespondentChanges.entrySet].flatten.
			map[value].flatten.map[EChanges].flatten.toList
		val otherCorrespondentEChanges = branchDiff.compareChanges.map[targetToCorrespondentChanges.entrySet].flatten.
			map[value].flatten.map[EChanges].flatten.toList
		val otherGraph = createDependencyGraphFromChangeMatches(branchDiff.compareChanges)
		val IsomorphismTesterAlgorithm tester = new PrimitiveIsomorphismTesterImpl
		tester.init(graph, otherGraph)
		tester.compute
		assertThat(tester.isIsomorphic, is(false))
		val combinedGraph = tester.combinedGraph
		assertThat(combinedGraph.nodeSet.size, is(graph.nodeSet.size + otherGraph.nodeSet.size))
		assertThat(combinedGraph.edgeSet.size >= graph.edgeSet.size + otherGraph.edgeSet.size, is(true))
		val unmatchedOfGraph1 = tester.unmatchedOfGraph1
		val unmatchedOfGraph2 = tester.unmatchedOfGraph2
		assertThat(unmatchedOfGraph1.size, is(4))
		assertThat(unmatchedOfGraph2.size, is(4))
		val testEchanges = [ Set<EChangeNode> nodes, Iterable<EChange> eChanges |
			nodes.map[EChange].forEach [
				assertThat(eChanges, hasItem(it))
			]
		]
		val allBaseEChanges = echanges + correspondentEChanges
		val allCompareEChanges = otherEChanges + otherCorrespondentEChanges
		testEchanges.apply(unmatchedOfGraph1, allBaseEChanges)
		testEchanges.apply(unmatchedOfGraph2, allCompareEChanges)
	}

	@Test
	def void testConflictDetector() {
		assertThat(conflicts.empty, is(false))
		assertThat(conflicts.size, is(1))
		val conflict = conflicts.filter[it instanceof MultiChangeConflict].map[it as MultiChangeConflict].get(0)
		assertThat(conflict, not(equalTo(null)))
		assertThat(conflict.solvability, is(ConflictSolvability::AUTOMATIC))
		assertThat(conflict.type, is(ConflictType::INSERTING_IN_SAME_CONTANER))
		assertThat(conflict.sourceChanges.size, is(4))
		assertThat(conflict.targetChanges.size, is(4))
	}

}
