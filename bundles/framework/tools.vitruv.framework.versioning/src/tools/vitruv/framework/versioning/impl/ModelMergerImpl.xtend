package tools.vitruv.framework.versioning.impl

import java.util.List
import java.util.Map
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtext.xbase.lib.Functions.Function1
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.BranchDiff
import tools.vitruv.framework.versioning.BranchDiffCreator
import tools.vitruv.framework.versioning.ChangeMatch
import tools.vitruv.framework.versioning.Conflict
import tools.vitruv.framework.versioning.ConflictDetector
import tools.vitruv.framework.versioning.ConflictSeverity
import tools.vitruv.framework.versioning.ModelMerger
import tools.vitruv.framework.versioning.extensions.URIRemapper

class ModelMergerImpl implements ModelMerger {
	static extension URIRemapper = URIRemapper::instance
	static extension BranchDiffCreator = BranchDiffCreator::instance
	BranchDiff branchDiff
	val ConflictDetector conflictDetector

	@Accessors(PUBLIC_GETTER)
	val List<EChange> resultingOriginalEChanges
	@Accessors(PUBLIC_GETTER)
	val List<EChange> resultingTriggeredEChanges

	new() {
		conflictDetector = ConflictDetector::createConflictDetector
		resultingOriginalEChanges = newArrayList
		resultingTriggeredEChanges = newArrayList
	}

	override init(List<ChangeMatch> myChanges, List<ChangeMatch> theirChanges,
		Function1<Conflict, List<ChangeMatch>> cb) {
		init(createVersionDiff(myChanges, theirChanges), cb)
	}

	override init(BranchDiff b, Function1<Conflict, List<ChangeMatch>> cb) {
		branchDiff = b
		conflictDetector.init(branchDiff)
		createMap(branchDiff.baseChanges.get(0), branchDiff.compareChanges.get(0))
		resultingOriginalEChanges.clear
		resultingTriggeredEChanges.clear
	}

	override compute() {
		conflictDetector.compute
		val getVURI = [Iterable<ChangeMatch> cIt|cIt.get(0).originalVURI]
		val getTriggeredVURIs = [Iterable<ChangeMatch> cIt|cIt.get(0).targetToCorrespondentChanges.entrySet.map[key]]
		val myOriginalVURI = getVURI.apply(branchDiff.baseChanges)
		val theirOriginalVURI = getVURI.apply(branchDiff.compareChanges)

		val myTriggeredVURIs = getTriggeredVURIs.apply(branchDiff.baseChanges)
		val theirTriggeredVURIs = getTriggeredVURIs.apply(branchDiff.compareChanges)

		val commonConflictFreeOriginalEChanges = conflictDetector.commonConflictFreeOriginalEChanges
		val commonConflictFreeTriggeredEChanges = conflictDetector.commonConflictFreeTriggeredEChanges

		val myConflictFreeOriginalEChanges = conflictDetector.myConflictFreeOriginalEChanges
		val myConflictFreeTriggeredEChanges = conflictDetector.myConflictFreeTriggeredEChanges

		val theirConflictFreeOriginalEChanges = conflictDetector.theirConflictFreeOriginalEChanges
		val theirConflictFreeTriggeredEChanges = conflictDetector.theirConflictFreeTriggeredEChanges

		val remapTheirVURI = createEChangeRemapFunction(theirOriginalVURI, myOriginalVURI)
		theirConflictFreeOriginalEChanges.forEach[remapTheirVURI.accept(it)]
		myTriggeredVURIs.forEach [ myTriggeredVURI, i |
			val theirTriggeredVURI = theirTriggeredVURIs.get(i)
			val remapTheirTriggeredVURI = createEChangeRemapFunction(theirTriggeredVURI, myTriggeredVURI)
			theirConflictFreeTriggeredEChanges.forEach[remapTheirTriggeredVURI.accept(it)]
		]
		val conflictFreeOriginalEChanges = commonConflictFreeOriginalEChanges + myConflictFreeOriginalEChanges +
			theirConflictFreeOriginalEChanges
		val conflictFreeTriggeredEChanges = commonConflictFreeTriggeredEChanges + myConflictFreeTriggeredEChanges +
			theirConflictFreeTriggeredEChanges
		val conflicts = conflictDetector.conflicts
		val softConflicts = conflicts.filter[solvability === ConflictSeverity::SOFT]
		val originalEChangesFromSoftConflicts = softConflicts.map[sourceDefaultSolution].flatten.toList
		val triggeredEChangesFromSoftConflicts = softConflicts.map[triggeredDefaultSolution].flatten.toList

		resultingOriginalEChanges += (conflictFreeOriginalEChanges + originalEChangesFromSoftConflicts)
		resultingTriggeredEChanges += (conflictFreeTriggeredEChanges + triggeredEChangesFromSoftConflicts)

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
