package tools.vitruv.framework.versioning.impl

import java.util.ArrayList
import java.util.List
import java.util.Map
import org.apache.log4j.Level
import org.apache.log4j.Logger
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.framework.change.echange.EChange
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
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.change.description.VitruviusChange

class ConflictDetectorImpl implements ConflictDetector {
	static extension DependencyGraphCreator = DependencyGraphCreator::instance
	static extension EChangeCompareUtil = EChangeCompareUtil::instance
	static extension GraphExtension = GraphExtension::instance
	static extension Logger = Logger::getLogger(ConflictDetectorImpl)
	val ConflictDetectionStrategy conflictDetectionStrategy

	@Accessors(PUBLIC_GETTER)
	val List<Conflict> conflicts

	@Accessors(PUBLIC_GETTER)
	val List<EChange> conflictFreeOriginalEChanges
	@Accessors(PUBLIC_GETTER)
	val List<EChange> conflictFreeTriggeredEChanges
	BranchDiff branchDiff
	VURI myVURI
	VURI theirVURI

	new() {
		conflictDetectionStrategy = new ConflictDetectionStrategyImpl
		conflictFreeOriginalEChanges = newArrayList
		conflictFreeTriggeredEChanges = newArrayList
		conflicts = newArrayList
	}

	override init(BranchDiff currentbranchDiff) {
		this.branchDiff = currentbranchDiff
		conflicts.clear
		conflictFreeOriginalEChanges.clear
		conflictFreeTriggeredEChanges.clear
		myVURI = branchDiff.baseChanges.get(0).originalVURI
		theirVURI = branchDiff.compareChanges.get(0).originalVURI
	}

	override compute() {
		// TODO PS Remove 
		level = Level::DEBUG
		val List<Conflict> naiveConflicts = new ArrayList
		val graph1 = createDependencyGraphFromChangeMatches(branchDiff.baseChanges)
		val graph2 = createDependencyGraphFromChangeMatches(branchDiff.compareChanges)
		val myChanges = branchDiff.baseChanges.map[allEChanges].flatten
		val theirChanges = branchDiff.compareChanges.map[allEChanges].flatten
		val tester = IsomorphismTesterAlgorithm::createIsomorphismTester
		tester.init(graph1, graph2)
		tester.compute

		val combinedGraph = tester.combinedGraph
		val myUnpairedChanges = tester.unmatchedOfGraph1.map[EChange].toSet
		val filterFunction = [ Iterable<VitruviusChange> changeIterator |
			changeIterator.map[EChanges].flatten.filter [
				!myUnpairedChanges.contains(it)
			]
		]
		val myConflictFreeOriginalEChanges = filterFunction.apply(branchDiff.baseChanges.map[originalChange])
		conflictFreeOriginalEChanges += myConflictFreeOriginalEChanges
		val myConflictFreeTriggeredEChanges = filterFunction.apply(branchDiff.baseChanges.map [
			targetToCorrespondentChanges.entrySet.map[value].flatten
		].flatten)
		conflictFreeTriggeredEChanges += myConflictFreeTriggeredEChanges
		if (tester.isIsomorphic)
			return
		val theirUnpairedChanges = tester.unmatchedOfGraph2.map[EChange].toSet

		myUnpairedChanges.forEach [ myChange |
			theirChanges.filter[conflictDetectionStrategy.conflicts(myChange, it)].forEach [
				processConflict(myChange, it, naiveConflicts)
				combinedGraph.addEdge(myChange, it, EdgeType::CONFLICTS)
			]
		]
		theirUnpairedChanges.forEach [ theirChange |
			myChanges.filter[!combinedGraph.checkIfEdgeExists(theirChange, it, EdgeType::CONFLICTS)].filter [
				conflictDetectionStrategy.conflicts(theirChange, it)
			].forEach [
				processConflict(it, theirChange, naiveConflicts)
			]
		]
		val myConflictedChanges = naiveConflicts.map[it as SimpleChangeConflict].map[sourceChange].toSet
		val theirConflictedChanges = naiveConflicts.map[it as SimpleChangeConflict].map[targetChange].toSet
		val mySubgraph = graph1.getSubgraphContainingEChanges(myConflictedChanges)
		val theirSubgraph = graph2.getSubgraphContainingEChanges(theirConflictedChanges)
		val leaves1 = mySubgraph.leaves
		val leaves2 = theirSubgraph.leaves
		val List<Conflict> cleanedConflicts = new ArrayList
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
		rootToRootMap.entrySet.map[new Pair(key, value)].forEach[addPair]
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

			currentConflicts +=
				conflict
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

}
