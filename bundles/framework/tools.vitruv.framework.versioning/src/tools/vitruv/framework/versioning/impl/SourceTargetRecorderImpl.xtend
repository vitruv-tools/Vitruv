package tools.vitruv.framework.versioning.impl

import java.util.List
import java.util.Map
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.versioning.ChangeMatch
import tools.vitruv.framework.versioning.SourceTargetRecorder
import tools.vitruv.framework.vsum.VirtualModel

class SourceTargetRecorderImpl implements SourceTargetRecorder {
	val Map<VURI, List<ChangeMatch>> changesMatches

	new() {
		changesMatches = newHashMap
	}

	override recordOriginalAndCorrespondentChanges(VURI orignal) {
		val matches = newArrayList
		changesMatches.put(orignal, matches)
	}

	override update(VURI vuri, PropagatedChange change, VirtualModel vm) {
		if (changesMatches.containsKey(vuri)) {
			if (change.originalChange.EChanges.exists[resolved])
				throw new IllegalStateException
			if (change.consequentialChanges.EChanges.exists[resolved])
				throw new IllegalStateException
			val match = ChangeMatch::createChangeMatch(change)
			val changeMatchesList = changesMatches.get(vuri)
			changeMatchesList += match
		}
	}

	override getChangeMatches(VURI source) {
		changesMatches.get(source)
	}
}
