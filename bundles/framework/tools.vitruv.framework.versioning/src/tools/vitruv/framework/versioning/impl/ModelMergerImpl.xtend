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
import tools.vitruv.framework.change.echange.EChange
import org.eclipse.xtend.lib.annotations.Accessors

class ModelMergerImpl implements ModelMerger {
	static extension BranchDiffCreator = BranchDiffCreator::instance
	BranchDiff branchDiff
	val ConflictDetector conflictDetector
	@Accessors(PUBLIC_GETTER)
	val List<EChange> resultingSourceEChanges
	@Accessors(PUBLIC_GETTER)
	val List<EChange> resultingTargetEChanges

	new() {
		conflictDetector = ConflictDetector::createConflictDetector
		resultingSourceEChanges = newArrayList
		resultingTargetEChanges = newArrayList
	}

	override init(List<ChangeMatch> myChanges, List<ChangeMatch> theirChanges,
		Function1<Conflict, List<ChangeMatch>> cb) {
		init(createVersionDiff(myChanges, theirChanges), cb)
	}

	override init(BranchDiff b, Function1<Conflict, List<ChangeMatch>> cb) {
		branchDiff = b
		conflictDetector.init(branchDiff)
		createMap(branchDiff.baseChanges.get(0), branchDiff.compareChanges.get(0))
		resultingSourceEChanges.clear
		resultingTargetEChanges.clear
	}

	override compute() {
		conflictDetector.compute
		val conflictFreeEChanges = conflictDetector.conflictFreeEChanges
		val conflicts = conflictDetector.conflicts
		val softConflicts = conflicts.filter[solvability === ConflictSeverity::SOFT]
		val echangesFromSoftConflicts = softConflicts.map[defaultSolution].flatten.toList
		val myTriggeredEChanges = branchDiff.baseChanges.map [
			targetToCorrespondentChanges.entrySet.map[value].flatten.map[EChanges]
		].flatten.flatten.toList
		val theirTriggeredSourceEChanges = branchDiff.compareChanges.map [
			targetToCorrespondentChanges.entrySet.map[value].flatten.map[EChanges]
		].flatten.flatten.toList
		val sourceEChanges = echangesFromSoftConflicts.filter [
			!myTriggeredEChanges.contains(it) && !theirTriggeredSourceEChanges.contains(it)
		]

		resultingSourceEChanges += (conflictFreeEChanges + sourceEChanges)
		if (softConflicts.length !== conflicts.length)
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

}
