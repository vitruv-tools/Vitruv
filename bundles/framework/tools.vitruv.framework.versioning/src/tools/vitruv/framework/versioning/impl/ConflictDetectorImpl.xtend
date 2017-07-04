package tools.vitruv.framework.versioning.impl

import java.util.Map
import org.apache.log4j.Level
import org.apache.log4j.Logger
import tools.vitruv.framework.versioning.BranchDiff
import tools.vitruv.framework.versioning.ConflictDetector
import tools.vitruv.framework.versioning.DependencyGraphCreator
import tools.vitruv.framework.versioning.IsomorphismTesterAlgorithm
import tools.vitruv.framework.versioning.conflict.ConflictFactory
import tools.vitruv.framework.versioning.extensions.EChangeCompareUtil

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

		val graph1 = createDependencyGraphFromChangeMatches(branchDiff.baseChanges)
		val graph2 = createDependencyGraphFromChangeMatches(branchDiff.compareChanges)
		val tester = IsomorphismTesterAlgorithm::createIsomorphismTester
		tester.init(graph1, graph2)
		tester.compute

		val conflict = ConflictFactory::eINSTANCE.createSimpleChangeConflict
		return conflict
	}

	override addMap(Map<String, String> rootToRootMap) {
		rootToRootMap.entrySet.map[new Pair(key, value)].forEach[addPair]
	}

}
