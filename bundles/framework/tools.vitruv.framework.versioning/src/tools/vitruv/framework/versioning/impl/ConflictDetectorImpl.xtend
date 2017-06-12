package tools.vitruv.framework.versioning.impl

import java.util.ArrayList
import java.util.List
import org.apache.log4j.Level
import org.apache.log4j.Logger
import tools.vitruv.framework.versioning.BranchDiff
import tools.vitruv.framework.versioning.ChangeMatch
import tools.vitruv.framework.versioning.ConflictDetector

class ConflictDetectorImpl implements ConflictDetector {
	static val logger = Logger::getLogger(ConflictDetectorImpl)

	override detectConlicts(BranchDiff branchDiff) {
		// TODO PS Remove 
		logger.level = Level::DEBUG
		branchDiff.checkLength
		branchDiff.findMatchesInChangeMatches
		return null
	}

	private static def void checkLength(BranchDiff branchDiff) {
		if (branchDiff.baseChanges.length === branchDiff.compareChanges.length) {
			logger.debug('''The change sequences have the same length: «branchDiff.baseChanges.length»''')
		} else {
			logger.debug(branchDiff.baseChanges.length)
			logger.debug(branchDiff.compareChanges.length)
		}

	}

	private static def void findMatchesInChangeMatches(BranchDiff branchDiff) {
		val List<Pair<ChangeMatch, ChangeMatch>> matches = new ArrayList
		branchDiff.baseChanges.forEach [ baseChange |
			branchDiff.compareChanges.filter[originalChange.class == baseChange.originalChange.class].forEach [
				matches += new Pair(baseChange, it)
			]
		]
		logger.debug('''The are potential «matches.length» matches''')
	}

}
