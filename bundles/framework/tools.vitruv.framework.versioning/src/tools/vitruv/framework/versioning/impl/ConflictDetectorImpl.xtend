package tools.vitruv.framework.versioning.impl

import java.util.ArrayList
import java.util.List
import java.util.Map
import org.apache.log4j.Level
import org.apache.log4j.Logger
import org.graphstream.graph.Graph
import org.graphstream.graph.implementations.SingleGraph
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.BranchDiff
import tools.vitruv.framework.versioning.ConflictDetector
import tools.vitruv.framework.versioning.DistanceCalculator
import tools.vitruv.framework.versioning.commit.ChangeMatch
import tools.vitruv.framework.versioning.conflict.ConflictFactory
import tools.vitruv.framework.versioning.extensions.EChangeCompareUtil

class ConflictDetectorImpl implements ConflictDetector {
	static extension Logger = Logger::getLogger(ConflictDetectorImpl)
	static extension EChangeCompareUtil = EChangeCompareUtil::newManager
	BranchDiff diff
	List<EChange> baseEchanges
	List<EChange> compareEchanges

	new() {
		this(#{})
	}

	new(Map<String, String> rootToRootMap) {
		rootToRootMap.entrySet.map[new Pair(key, value)].forEach[addPair]
	}

	override detectConlicts(BranchDiff branchDiff) {
		// TODO PS Remove 
		level = Level::DEBUG
		this.diff = branchDiff
		setup
		checkLength
		findMatchesInChangeMatches
		val comparison = [EChange a, EChange b|isEChangeEqual(a, b)]
		val distance = DistanceCalculator::instance.levenshteinDistance(baseEchanges, compareEchanges, comparison)
		val Graph dependencyGraph = new SingleGraph("Conflict")
		cleanup
		val conflict = ConflictFactory::eINSTANCE.createSimpleChangeConflict

		conflict.EChangeDependencyGraph = dependencyGraph
		conflict.originalChangesLevenshteinDistance = distance
		return conflict
	}

	private def void checkLength() {
		if (diff.baseChanges.length === diff.compareChanges.length) {
			debug('''The change sequences have the same length: «diff.baseChanges.length»''')
		} else {
			debug(diff.baseChanges.length)
			debug(diff.compareChanges.length)
		}
	}

	private def void findMatchesInChangeMatches() {
		val List<Pair<ChangeMatch, ChangeMatch>> matches = new ArrayList
		diff.baseChanges.forEach[processBaseChange(originalChange)]
		debug('''The are potential «matches.length» matches''')
	}

	private def processBaseChange(TransactionalChange originalChange) {
		if (originalChange.EChanges.empty)
			debug("No Echange contained")
		else {
			originalChange.EChanges.forEach [ baseEChange |
				val correspondentChange = compareEchanges.findFirst[baseEChange == it]
				if (null !== correspondentChange) {
				}
			]
		}
	}

	private def void setup() {
		debug('''Start setup''')
		baseEchanges = diff.baseChanges.map[originalChange.EChanges].flatten.toList
		compareEchanges = diff.compareChanges.map[originalChange.EChanges].flatten.toList

		debug('''Base has «baseEchanges.length» EChanges''')
		debug('''Compare has «compareEchanges.length» EChanges''')
		debug('''End setup''')
	}

	private def void cleanup() {
		debug('''Start cleanup''')
		baseEchanges = null
		compareEchanges = null
		diff = null
		debug('''End cleanup''')
	}
}
