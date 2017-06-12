package tools.vitruv.framework.versioning

import java.util.List
import org.apache.log4j.Logger
import org.apache.log4j.Level
import java.util.ArrayList

class ConflictDetector {
	static val logger = Logger::getLogger(ConflictDetector)

	val ChangePair changePair

	new(List<ChangeMatch> sourceChanges, List<ChangeMatch> targetChanges) {
		changePair = new ChangePair(sourceChanges, targetChanges)
	}

	def void detectConlicts() {
		// TODO PS Remove 
		logger.level = Level::DEBUG
		checkLength
		findMatchesInChangeMatches
	}

	private def void checkLength() {
		if (changePair.baseChanges.length === changePair.compareChanges.length) {
			logger.debug('''The change sequences have the same length: «changePair.baseChanges.length»''')
		} else {
			logger.debug(changePair.baseChanges.length)
			logger.debug(changePair.compareChanges.length)
		}
		
	}

	private def void findMatchesInChangeMatches() {
		val List<Pair<ChangeMatch, ChangeMatch>> matches = new ArrayList
		changePair.baseChanges.forEach [ baseChange |
			changePair.compareChanges.filter[originalChange.class == baseChange.originalChange.class].forEach [
				matches += new Pair(baseChange, it)
			]
		]
		logger.debug('''The are potential «matches.length» matches''')
	}
}
