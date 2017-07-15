package tools.vitruv.framework.versioning.impl

import tools.vitruv.framework.versioning.ModelMerger
import java.util.List
import tools.vitruv.framework.versioning.ChangeMatch
import org.eclipse.xtext.xbase.lib.Functions.Function1
import tools.vitruv.framework.versioning.Conflict
import tools.vitruv.framework.versioning.BranchDiffCreator
import tools.vitruv.framework.versioning.ConflictDetector
import tools.vitruv.framework.versioning.ConflictSeverity
import tools.vitruv.framework.versioning.BranchDiff
import java.util.Map

class ModelMergerImpl implements ModelMerger {
	static extension BranchDiffCreator = BranchDiffCreator::instance
	val ConflictDetector conflictDetector

	new() {
		conflictDetector = ConflictDetector::createConflictDetector
	}

	override mergeModels(List<ChangeMatch> myChanges, List<ChangeMatch> theirChanges,
		Function1<Conflict, List<ChangeMatch>> cb) {
		createMap(myChanges.get(0), theirChanges.get(0))
		val branchDiff = createVersionDiff(myChanges, theirChanges)
		conflictDetector.init(branchDiff)
		conflictDetector.compute
		val conflictFreeEChanges = conflictDetector.conflictFreeEChanges
		val conflicts = conflictDetector.conflicts
		val softConflicts = conflicts.filter[solvability === ConflictSeverity::SOFT]
		val echangesFromSoftConflicts = softConflicts.map[defaultSolution].flatten.toList
		val myTriggeredEChanges = myChanges.map[targetToCorrespondentChanges.entrySet.map[value].flatten.map[EChanges]].
			flatten.flatten.toList
		val theirTriggeredSourceEChanges = theirChanges.map [
			targetToCorrespondentChanges.entrySet.map[value].flatten.map[EChanges]
		].flatten.flatten.toList
		val sourceEChanges = echangesFromSoftConflicts.filter [
			!myTriggeredEChanges.contains(it) && !theirTriggeredSourceEChanges.contains(it)
		]
		if (softConflicts.length === conflicts.length)
			return (conflictFreeEChanges + sourceEChanges).toList
		throw new UnsupportedOperationException
	}

	private def createMap(ChangeMatch myChange, ChangeMatch theirChange) {
		val sourceVURI = myChange.originalVURI
		val newSourceVURI = theirChange.originalVURI

		val Map<String, String> rootToRootMap = newHashMap
		rootToRootMap.put(sourceVURI.EMFUri.toFileString, newSourceVURI.EMFUri.toFileString)
		myChange.targetToCorrespondentChanges.entrySet.forEach [ entry, index |
			val targetVURI = entry.key
			// FIXME PS Dirty HACK 
			val newTargetVURI = theirChange.targetToCorrespondentChanges.entrySet.get(index).key
			rootToRootMap.put(targetVURI.EMFUri.toFileString, newTargetVURI.EMFUri.toFileString)
		]
		conflictDetector.addMap(rootToRootMap)
	}

	override mergeModels(BranchDiff branchDiff, Function1<Conflict, List<ChangeMatch>> cb) {
		mergeModels(branchDiff.baseChanges, branchDiff.compareChanges, cb)
	}

}
