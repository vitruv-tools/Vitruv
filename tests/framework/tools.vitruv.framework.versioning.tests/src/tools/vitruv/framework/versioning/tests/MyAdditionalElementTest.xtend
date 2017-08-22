package tools.vitruv.framework.versioning.tests

import java.util.List
import java.util.Set

import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl

import org.junit.Test

import allElementTypes.NonRoot

import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.Conflict
import tools.vitruv.framework.versioning.IsomorphismTesterAlgorithm
import tools.vitruv.framework.versioning.extensions.EChangeNode

import static org.hamcrest.CoreMatchers.hasItem
import static org.hamcrest.CoreMatchers.is

import static org.hamcrest.collection.IsCollectionWithSize.hasSize
import static org.hamcrest.collection.IsEmptyCollection.empty

import static org.junit.Assert.assertThat

class MyAdditionalElementTest extends AbstractMyAdditionalElementTest {
	@Test
	def void testOnlyOriginalEChanges() {
		graph = createDependencyGraph(changes)
		val otherChanges = branchDiff.compareChanges.map[originalChange]
		val otherEChanges = branchDiff.compareChanges.map[originalChange.EChanges].flatten
		val otherGraph = createDependencyGraph(otherChanges)
		val IsomorphismTesterAlgorithm tester = IsomorphismTesterAlgorithm::createIsomorphismTester
		tester.init(graph, otherGraph)
		tester.compute
		assertThat(tester.isIsomorphic, is(false))
		val combinedGraph = tester.combinedGraph
		assertThat(combinedGraph.nodeSet, hasSize(graph.nodeSet.size + otherGraph.nodeSet.size))
		assertThat(combinedGraph.edgeSet.size >= graph.edgeSet.size + otherGraph.edgeSet.size, is(true))
		val unmatchedOfGraph1 = tester.unmatchedOfGraph1
		val unmatchedOfGraph2 = tester.unmatchedOfGraph2
		assertThat(unmatchedOfGraph1, hasSize(2))
		assertThat(unmatchedOfGraph2, hasSize(0))
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
		val IsomorphismTesterAlgorithm tester = IsomorphismTesterAlgorithm::createIsomorphismTester
		tester.init(graph, otherGraph)
		tester.compute
		assertThat(tester.isIsomorphic, is(false))
		val combinedGraph = tester.combinedGraph
		assertThat(combinedGraph.nodeSet.size, is(graph.nodeSet.size + otherGraph.nodeSet.size))
		assertThat(combinedGraph.edgeSet.size >= graph.edgeSet.size + otherGraph.edgeSet.size, is(true))
		val unmatchedOfGraph1 = tester.unmatchedOfGraph1
		val unmatchedOfGraph2 = tester.unmatchedOfGraph2
		assertThat(unmatchedOfGraph1, hasSize(4))
		assertThat(unmatchedOfGraph2, hasSize(0))
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
		assertThat(conflicts, is(empty))
		val conflictFreeEChanges = conflictDetector.conflictFreeOriginalEChanges
		assertThat(conflictFreeEChanges, hasSize(12))
	}

	@Test
	def void testModelMerger() {
		assertThat(conflicts, is(empty))
		val failingFunction = [ Conflict c |
			assertThat("This method should never been called", true, is(false))
			return #[]
		]
		val ResourceSet source = new ResourceSetImpl
		modelMerger.init(branchDiff, failingFunction, failingFunction)
		modelMerger.compute
		val echanges = modelMerger.resultingOriginalEChanges

		val testOnResourceSet = [ ResourceSet resourceSet, List<EChange> es |
			assertThat(es, hasSize(12))
			assertThat(es.exists[resolved], is(false))
			es.forEach [
				resolveBeforeAndApplyForward(resourceSet)
			]

			assertThat(resourceSet.allContents.filter[it instanceof NonRoot]::map[it as NonRoot]::exists [
				id == myAdditionialID
			], is(true))
			assertThat(resourceSet.allContents.filter[it instanceof NonRoot].toList, hasSize(4))
		]

		testOnResourceSet.apply(source, echanges)
		val ResourceSet target = new ResourceSetImpl
		val targetEChanges = modelMerger.resultingTriggeredEChanges

		testOnResourceSet.apply(target, targetEChanges)
	}
}
