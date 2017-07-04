package tools.vitruv.framework.versioning.impl

import java.util.Map
import org.apache.log4j.Level
import org.apache.log4j.Logger
import tools.vitruv.framework.versioning.BranchDiff
import tools.vitruv.framework.versioning.ConflictDetector
import tools.vitruv.framework.versioning.DependencyGraphCreator
import tools.vitruv.framework.versioning.IsomorphismTesterAlgorithm
import tools.vitruv.framework.versioning.extensions.EChangeCompareUtil
import java.util.List
import tools.vitruv.framework.versioning.Conflict
import java.util.ArrayList
//import tools.vitruv.framework.versioning.extensions.GraphExtension
import tools.vitruv.framework.change.echange.EChange

class ConflictDetectorImpl implements ConflictDetector {
	static extension DependencyGraphCreator = DependencyGraphCreator::instance
	static extension EChangeCompareUtil = EChangeCompareUtil::instance
//	static extension GraphExtension = GraphExtension::instance
	static extension Logger = Logger::getLogger(ConflictDetectorImpl)

	static def ConflictDetector init() {
		new ConflictDetectorImpl
	}

	private new() {
	}

	override detectConlicts(BranchDiff branchDiff) {
		// TODO PS Remove 
		level = Level::DEBUG
		val List<Conflict> conflicts = new ArrayList
		val graph1 = createDependencyGraphFromChangeMatches(branchDiff.baseChanges)
		val graph2 = createDependencyGraphFromChangeMatches(branchDiff.compareChanges)
		val myChanges = branchDiff.baseChanges.map[allEChanges].flatten
		val theirChanges = branchDiff.compareChanges.map[allEChanges].flatten
		val tester = IsomorphismTesterAlgorithm::createIsomorphismTester
		tester.init(graph1, graph2)
		tester.compute
		if (tester.isIsomorphic)
			return conflicts

//		val combinedGraph = tester.combinedGraph
		val myUnpairedChanges = tester.unmatchedOfGraph1.map[EChange].toSet
		val theirUnpairedChanges = tester.unmatchedOfGraph2.map[EChange].toSet
//		val subGraph1 = combinedGraph.getSubgraphContainingEChanges(myUnpairedChanges)
//		val subGraph2 = combinedGraph.getSubgraphContainingEChanges(theirUnpairedChanges)
//		val leaves1 = subGraph1.leaves
//		val leaves2 = subGraph2.leaves
		myUnpairedChanges.forEach [ myChange |
			theirChanges.filter[isConflictingEachOther(myChange, it)].forEach [
				processConflict(myChange, it, conflicts)
			]
		]
		theirUnpairedChanges.forEach [ theirChange |
			myChanges.filter[isConflictingEachOther(theirChange, it)].forEach [
				processConflict(theirChange, it, conflicts)
			]
		]
		return conflicts

	}

	override addMap(Map<String, String> rootToRootMap) {
		rootToRootMap.entrySet.map[new Pair(key, value)].forEach[addPair]
	}

	private def processConflict(EChange e1, EChange e2, List<Conflict> conflicts) {
	}
}
