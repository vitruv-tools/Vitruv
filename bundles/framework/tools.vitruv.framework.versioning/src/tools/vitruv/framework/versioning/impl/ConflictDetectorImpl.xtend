package tools.vitruv.framework.versioning.impl

import java.util.Collection
import java.util.List
import java.util.Map
import java.util.Set

import org.apache.log4j.Level
import org.apache.log4j.Logger

import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtext.xbase.lib.Functions.Function1

import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.versioning.BranchDiff
import tools.vitruv.framework.versioning.Conflict
import tools.vitruv.framework.versioning.ConflictDetectionStrategy
import tools.vitruv.framework.versioning.ConflictDetector
import tools.vitruv.framework.versioning.DependencyGraphCreator
import tools.vitruv.framework.versioning.EdgeType
import tools.vitruv.framework.versioning.IsomorphismTesterAlgorithm
import tools.vitruv.framework.versioning.SimpleChangeConflict
import tools.vitruv.framework.versioning.extensions.EChangeCompareUtil
import tools.vitruv.framework.versioning.extensions.EChangeNode
import tools.vitruv.framework.versioning.extensions.GraphExtension

class ConflictDetectorImpl implements ConflictDetector {
	// Extensions 
	static extension DependencyGraphCreator = DependencyGraphCreator::instance
	static extension EChangeCompareUtil = EChangeCompareUtil::instance
	static extension GraphExtension = GraphExtension::instance
	static extension Logger = Logger::getLogger(ConflictDetectorImpl)

	// Functions  
	static val getEChanges = [ Function1<Iterable<PropagatedChange>, Iterable<VitruviusChange>> toEChange, Iterable<PropagatedChange> changeIt |
		toEChange.apply(changeIt).map[EChanges].flatten
	]
	static val originalEChangesFunction = getEChanges.curry([Iterable<PropagatedChange> x|x.map[originalChange]])
	static val triggeredEChangesFunction = getEChanges.curry([ Iterable<PropagatedChange> x |
		x.map[consequentialChanges]
	])
	
	static val toEChangeSet = [Set<EChangeNode> nodes|nodes.map[EChange].toSet]
	
	static val filterFunction = [ Collection<EChange> unpairedChanges, Iterable<EChange> echangeIterator |
		echangeIterator.filter[!unpairedChanges.contains(it)]
	]	
	
	static val functionEChangesWithoutConflict = [Set<EChangeNode> nodeSet|nodeSet.filter[!conflicting].map[EChange].toSet]
	static val isNotConflicting = [Set<EChange> nonConflictingEChangeSet, EChange e|nonConflictingEChangeSet.contains(e)]
	static val filterEChangeListAgainFunction = [ List<EChange> listToFilter, List<EChange> listToCompareAgainst, List<EChange> listToAddIfTrue, List<EChange> listToAddIfFalse |
			listToFilter.forEach [ myEChange |
				val correspondentEChange = listToCompareAgainst.findFirst[myEChange.isEChangeEqual(it)]
				if (null !== correspondentEChange)
					listToAddIfTrue += myEChange
				else
					listToAddIfFalse += myEChange
			]
		]
	static val isNotInCommon = [ Collection<EChange> common, EChange e|
			!common.exists [ o |e.isEChangeEqual(o)]
		]
	private static def getOriginalEChanges(Iterable<PropagatedChange> propagatedChanges) {
		originalEChangesFunction.apply(propagatedChanges)
	}

	private static def getTriggeredEChanges(Iterable<PropagatedChange> propagatedChanges) {
		triggeredEChangesFunction.apply(propagatedChanges)
	}

	private static def getEChangeSet(Set<EChangeNode> nodes) {
		toEChangeSet.apply(nodes)
	}
	private static def getEChangesWithoutConflict(Set<EChangeNode> nodes) {
		functionEChangesWithoutConflict.apply(nodes)
	}

	val ConflictDetectionStrategy conflictDetectionStrategy

	@Accessors(PUBLIC_GETTER)
	val List<Conflict> conflicts

	@Accessors(PUBLIC_GETTER)
	val List<EChange> theirConflictFreeOriginalEChanges
	@Accessors(PUBLIC_GETTER)
	val List<EChange> theirConflictFreeTriggeredEChanges
	@Accessors(PUBLIC_GETTER)
	val List<EChange> myConflictFreeOriginalEChanges
	@Accessors(PUBLIC_GETTER)
	val List<EChange> myConflictFreeTriggeredEChanges
	@Accessors(PUBLIC_GETTER)
	val List<EChange> commonConflictFreeOriginalEChanges
	@Accessors(PUBLIC_GETTER)
	val List<EChange> commonConflictFreeTriggeredEChanges

	BranchDiff branchDiff
	VURI myVURI
	VURI theirVURI

	val List<List<?>> lists

	new() {
		conflictDetectionStrategy = new ConflictDetectionStrategyImpl
		conflicts = newArrayList
		commonConflictFreeOriginalEChanges = newArrayList
		commonConflictFreeTriggeredEChanges = newArrayList
		myConflictFreeOriginalEChanges = newArrayList
		myConflictFreeTriggeredEChanges = newArrayList
		theirConflictFreeOriginalEChanges = newArrayList
		theirConflictFreeTriggeredEChanges = newArrayList
		lists = #[
			commonConflictFreeOriginalEChanges,
			commonConflictFreeTriggeredEChanges,
			myConflictFreeOriginalEChanges,
			myConflictFreeTriggeredEChanges,
			theirConflictFreeOriginalEChanges,
			theirConflictFreeTriggeredEChanges
		]
	}

	override init(BranchDiff currentbranchDiff) {
		this.branchDiff = currentbranchDiff
		conflicts.clear
		lists.forEach[clear]
		myVURI = branchDiff.baseChanges.get(0).originalChange.URI
		theirVURI = branchDiff.compareChanges.get(0).originalChange.URI
	}

	override compute() {
		// TODO PS Remove 
		level = Level::DEBUG
		val List<Conflict> naiveConflicts = newArrayList
		// PS Create dependency graphs.
		val graph1 = createDependencyGraphFromChangeMatches(branchDiff.baseChanges)
		val graph2 = createDependencyGraphFromChangeMatches(branchDiff.compareChanges)

		// PS Determine my EChanges.
		val myOriginalEChanges = branchDiff.baseChanges.originalEChanges
		val myTriggeredEChanges = branchDiff.baseChanges.triggeredEChanges
		val myChanges = myOriginalEChanges + myTriggeredEChanges

		// PS Determine their EChanges.
		val theirOriginalEChanges = branchDiff.compareChanges.originalEChanges
		val theirTriggeredEChanges = branchDiff.compareChanges.triggeredEChanges
		val theirChanges = theirOriginalEChanges + theirTriggeredEChanges

		val tester = IsomorphismTesterAlgorithm::createIsomorphismTester
		tester.init(graph1, graph2)
		tester.compute

		val combinedGraph = tester.combinedGraph

		val myUnpairedChanges = tester.unmatchedOfGraph1.EChangeSet
		val theirUnpairedChanges = tester.unmatchedOfGraph2.EChangeSet

		// PS Create two filter predicates. The predicate returns true, if 
		// the given EChange is not in the unpaired set.
		val myFilterFunction = filterFunction.curry(myUnpairedChanges)
		val theirFilterFunction = filterFunction.curry(theirUnpairedChanges)

		val myOriginalEChangesWithIsomorphicChange = myFilterFunction.apply(myOriginalEChanges)
		val myTriggeredEChangesWithIsomorphiChange = myFilterFunction.apply(myTriggeredEChanges)

		val theirOriginalEChangesWithIsomorphicChange = theirFilterFunction.apply(theirOriginalEChanges)
		val theirTriggeredEChangesWithIsomorphiChange = theirFilterFunction.apply(theirTriggeredEChanges)

		if (myOriginalEChangesWithIsomorphicChange.length !== theirOriginalEChangesWithIsomorphicChange.length)
			throw new IllegalStateException
		if (myTriggeredEChangesWithIsomorphiChange.length !== theirTriggeredEChangesWithIsomorphiChange.length)
			throw new IllegalStateException

		// PS Add the EChanges with partner on the other side to the set of EChanges without conflict.
		commonConflictFreeOriginalEChanges += myOriginalEChangesWithIsomorphicChange
		commonConflictFreeTriggeredEChanges += myTriggeredEChangesWithIsomorphiChange

		if (tester.isIsomorphic)
			return;

		// PS Traverse through my unpaired changes to find conflicts in their changes. 
		myUnpairedChanges.forEach [ myChange |
			theirChanges.filter[conflictDetectionStrategy.conflicts(myChange, it)].forEach [
				processConflict(myChange, it, naiveConflicts)
				combinedGraph.addEdge(myChange, it, EdgeType::CONFLICTS)
			]
		]
		// PS Traverse through their unpaired changes to find conflicts in my changes.
		theirUnpairedChanges.forEach [ theirChange |
			// PS Exclude already found conflicts
			myChanges.filter[!combinedGraph.checkIfEdgeExists(theirChange, it, EdgeType::CONFLICTS)].filter [
				conflictDetectionStrategy.conflicts(theirChange, it)
			].forEach [
				processConflict(it, theirChange, naiveConflicts)
				combinedGraph.addEdge(it, theirChange, EdgeType::CONFLICTS)
			]
		]
		
		val myEChangesWithoutConflict = tester.unmatchedOfGraph1.EChangesWithoutConflict
		val theirEChangesWithoutConflict = tester.unmatchedOfGraph2.EChangesWithoutConflict

		// PS Create two filter predicates. The predicate returns true, if 
		// the given EChange is not conflicting.		
		val myIsNotConflicting = isNotConflicting.curry(myEChangesWithoutConflict)
		val theirIsNotConflicting = isNotConflicting.curry(theirEChangesWithoutConflict)

		val myOriginalEChangesWithoutConflict = myOriginalEChanges.filter(myIsNotConflicting).toList
		val myTriggeredEChangesWithouConflict = myTriggeredEChanges.filter(myIsNotConflicting).toList
		val theirOriginalEChangesWithoutConflict = theirOriginalEChanges.filter(theirIsNotConflicting).toList
		val theirTriggeredEChangesWithouConflict = theirTriggeredEChanges.filter(theirIsNotConflicting).toList

		val myOriginalEChangesToAdd = newArrayList
		val myTriggeredEChangesToAdd = newArrayList
		
		filterEChangeListAgainFunction.apply(myOriginalEChangesWithoutConflict, theirOriginalEChangesWithoutConflict,
			commonConflictFreeOriginalEChanges, myOriginalEChangesToAdd)

		filterEChangeListAgainFunction.apply(myTriggeredEChangesWithouConflict, theirTriggeredEChangesWithouConflict,
			commonConflictFreeTriggeredEChanges, myTriggeredEChangesToAdd)

		// PS Add my changes to the conflict free EChanges.
		myConflictFreeOriginalEChanges += myOriginalEChangesToAdd
		myConflictFreeTriggeredEChanges += myTriggeredEChangesToAdd
		
		// PS Create two filter predicates. The predicate returns true, if 
		// the given EChange is not in the common list.
		val original = isNotInCommon.curry(commonConflictFreeOriginalEChanges)
		val triggered = isNotInCommon.curry(commonConflictFreeTriggeredEChanges)
		
		val theirOriginalEChangesWithoutConflictFiltered = theirOriginalEChangesWithoutConflict.filter(original)
		val theirTriggeredEChangesWithouConflictFiltered = theirTriggeredEChangesWithouConflict.filter(triggered)

		// PS Add their changes which are not already in the conflict free EChanges set.
		theirConflictFreeOriginalEChanges += theirOriginalEChangesWithoutConflictFiltered
		theirConflictFreeTriggeredEChanges += theirTriggeredEChangesWithouConflictFiltered

		// PS Get the conflicted changes.
		val myConflictedChanges = naiveConflicts.filter(SimpleChangeConflict).map[sourceChange].toSet
		val theirConflictedChanges = naiveConflicts.filter(SimpleChangeConflict).map[targetChange].toSet
		
		// PS Get the subgraphs consisting of the conflicted changes.
		val mySubgraph = graph1.getSubgraphContainingEChanges(myConflictedChanges)
		val theirSubgraph = graph2.getSubgraphContainingEChanges(theirConflictedChanges)
		
		val leaves1 = mySubgraph.leaves
		val leaves2 = theirSubgraph.leaves
		val List<Conflict> cleanedConflicts = newArrayList
		combinedGraph.savePicture("combined_graph")
		// Find correspondences in the two subgraphs. 
		leaves1.forEach [ myLeave |
			val myEchange = myLeave.EChange
			val theirLeave = leaves2.findFirst [
				val theirEChange = EChange
				return combinedGraph.checkIfEdgeExists(myEchange, theirEChange, EdgeType::CONFLICTS)
			]
			if (null !== theirLeave) {
				processLeave(myLeave, theirLeave, cleanedConflicts)
			} else {
				// TODO PS recursively remove Nodes from the graph
				throw new IllegalStateException
			}
		]
		conflicts += cleanedConflicts
	}

	override addMap(Map<String, String> rootToRootMap) {
		rootToRootMap.entrySet.map[key -> value].forEach[addPair]
	}

	private def processConflict(EChange e1, EChange e2, List<Conflict> currentConflicts) {
		val type = conflictDetectionStrategy.getConflictType(e1, e2)
		val solvability = conflictDetectionStrategy.getConflictSolvability(e1, e2, type)
		val conflict = new SimpleChangeConflictImpl(type, solvability, myVURI, theirVURI, #[], #[], e1, e2)
		currentConflicts += conflict
	}

	private def processLeave(EChangeNode leave1, EChangeNode leave2, List<Conflict> currentConflicts) {
		val e1 = leave1.EChange
		val e2 = leave2.EChange
		val type = conflictDetectionStrategy.getConflictType(e1, e2)
		val solvability = conflictDetectionStrategy.getConflictSolvability(e1, e2, type)
		if (leave1.breadthFirstIterator.size == 1 && leave2.breadthFirstIterator.size == 1) {
			val conflict = new SimpleChangeConflictImpl(type, solvability, myVURI, theirVURI, #[], #[], e1, e2)

			currentConflicts += conflict
		} else {
			val myEchanges = newArrayList
			val myTriggeredEChanges = newArrayList
			val theirEChanges = newArrayList
			val theirTriggeredEChanges = newArrayList
			val myTriggeredVuris = newArrayList
			val theirTriggeredVuris = newArrayList
			val fillLists = [ EChangeNode leave, List<EChange> originalList, List<EChange> triggeredList, List<VURI> vuris |
				leave.<EChangeNode>breadthFirstIterator.forEach [ node |
					if (node.triggered) {
						triggeredList += node.EChange
						vuris += node.vuri
					} else
						originalList += node.EChange
				]
			]
			fillLists.apply(leave1, myEchanges, myTriggeredEChanges, myTriggeredVuris)
			fillLists.apply(leave2, theirEChanges, theirTriggeredEChanges, theirTriggeredVuris)
			val conflict = new MultiChangeConflictImpl(type, solvability, e1, e2, myEchanges, theirEChanges, myVURI,
				theirVURI, myTriggeredEChanges, theirTriggeredEChanges, myTriggeredVuris, theirTriggeredVuris)
			currentConflicts += conflict
		}
	}

	override getConflictFreeOriginalEChanges() {
		return (commonConflictFreeOriginalEChanges + myConflictFreeOriginalEChanges +
			theirConflictFreeOriginalEChanges).toList
	}

}
