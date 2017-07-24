package tools.vitruv.dsls.reactions.tests.versioning

import java.util.Set
import org.junit.Test
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.IsomorphismTesterAlgorithm
import tools.vitruv.framework.versioning.extensions.EChangeNode
import tools.vitruv.framework.versioning.impl.PrimitiveIsomorphismTesterImpl

import static org.hamcrest.CoreMatchers.hasItem
import static org.hamcrest.CoreMatchers.is
import static org.junit.Assert.assertThat
import tools.vitruv.framework.versioning.Conflict
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import java.util.List
import allElementTypes.NonRoot
import tools.vitruv.dsls.reactions.tests.AbstractTheirAdditionalElementTest

class TheirAdditionalElementTest extends AbstractTheirAdditionalElementTest {
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
		assertThat(unmatchedOfGraph1.size, is(0))
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
		val correspondentEChanges = branchDiff.baseChanges.map[consequentialChanges.EChanges].flatten.toList
		val otherCorrespondentEChanges = branchDiff.compareChanges.map[consequentialChanges.EChanges].flatten.toList
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
		assertThat(unmatchedOfGraph1.size, is(0))
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
		assertThat(conflicts.empty, is(true))
		val conflictFreeEChanges = conflictDetector.conflictFreeOriginalEChanges
		assertThat(conflictFreeEChanges.length, is(10))
	}

	@Test
	def void testModelMerger() {
		assertThat(conflicts.empty, is(true))
		val failingFunction = [ Conflict c |
			assertThat("This method should never been called", true, is(false))
			return #[]
		]
		val ResourceSet source = new ResourceSetImpl
		modelMerger.init(branchDiff, failingFunction)
		modelMerger.compute
		val echanges = modelMerger.resultingOriginalEChanges

		val testOnResourceSet = [ ResourceSet resourceSet, List<EChange> es |
			assertThat(es.length, is(10))
			assertThat(es.exists[resolved], is(false))
			es.forEach [
				resolveBeforeAndApplyForward(resourceSet)
			]

			assertThat(resourceSet.allContents.filter[it instanceof NonRoot].map[it as NonRoot].exists [
				id == additionalID
			], is(true))
			assertThat(resourceSet.allContents.filter[it instanceof NonRoot].size, is(4))
		]

		testOnResourceSet.apply(source, echanges)
		val ResourceSet target = new ResourceSetImpl
		val targetEChanges = modelMerger.resultingTriggeredEChanges

		testOnResourceSet.apply(target, targetEChanges)
	}
}
