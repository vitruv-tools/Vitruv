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

class ConflictDetectorImpl implements ConflictDetector {
	static extension DependencyGraphCreator = DependencyGraphCreator::instance
	static extension EChangeCompareUtil = EChangeCompareUtil::instance
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
		val tester = IsomorphismTesterAlgorithm::createIsomorphismTester
		tester.init(graph1, graph2)
		tester.compute
		if (tester.isIsomorphic)
			return conflicts
		val myChanges = tester.unmatchedOfGraph1.map[EChange]
		val theirChanges = tester.unmatchedOfGraph2.map[EChange]
		
	}

	override addMap(Map<String, String> rootToRootMap) {
		rootToRootMap.entrySet.map[new Pair(key, value)].forEach[addPair]
	}

}
