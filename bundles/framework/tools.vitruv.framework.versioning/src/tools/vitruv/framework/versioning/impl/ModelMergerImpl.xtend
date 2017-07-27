package tools.vitruv.framework.versioning.impl

import java.util.List
import java.util.Map
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtext.xbase.lib.Functions.Function1
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.BranchDiff
import tools.vitruv.framework.versioning.Conflict
import tools.vitruv.framework.versioning.ConflictDetector
import tools.vitruv.framework.versioning.ConflictSeverity
import tools.vitruv.framework.versioning.ModelMerger
import tools.vitruv.framework.versioning.extensions.URIRemapper
import java.util.function.Consumer

class ModelMergerImpl implements ModelMerger {
	static extension URIRemapper = URIRemapper::instance
	BranchDiff branchDiff
	Function1<Conflict, List<EChange>> originalCallback
	Function1<Conflict, List<EChange>> triggeredCallback
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

	override init(
		BranchDiff b,
		Function1<Conflict, List<EChange>> ocb,
		Function1<Conflict, List<EChange>> tcb
	) {
		branchDiff = b
		originalCallback = ocb
		triggeredCallback = tcb
		conflictDetector.init(branchDiff)
		createMap(branchDiff.baseChanges.get(0), branchDiff.compareChanges.get(0))
		resultingOriginalEChanges.clear
		resultingTriggeredEChanges.clear
	}

	override compute() {
		conflictDetector.compute
		val getVURI = [Iterable<PropagatedChange> cIt|cIt.get(0).originalChange.URI]
		val getTriggeredVURIs = [ Iterable<PropagatedChange> cIt |
			cIt.get(0).consequentialChanges.URI
		]
		val myOriginalVURI = getVURI.apply(branchDiff.baseChanges)
		val theirOriginalVURI = getVURI.apply(branchDiff.compareChanges)

		val myTriggeredVURI = getTriggeredVURIs.apply(branchDiff.baseChanges)
		val theirTriggeredVURI = getTriggeredVURIs.apply(branchDiff.compareChanges)

		val commonConflictFreeOriginalEChanges = conflictDetector.commonConflictFreeOriginalEChanges
		val commonConflictFreeTriggeredEChanges = conflictDetector.commonConflictFreeTriggeredEChanges

		val myConflictFreeOriginalEChanges = conflictDetector.myConflictFreeOriginalEChanges
		val myConflictFreeTriggeredEChanges = conflictDetector.myConflictFreeTriggeredEChanges

		val theirConflictFreeOriginalEChanges = conflictDetector.theirConflictFreeOriginalEChanges
		val theirConflictFreeTriggeredEChanges = conflictDetector.theirConflictFreeTriggeredEChanges

		val remapTheirVURI = createEChangeRemapFunction(theirOriginalVURI, myOriginalVURI)
		theirConflictFreeOriginalEChanges.forEach[remapTheirVURI.accept(it)]
		val remapTheirTriggeredVURI = createEChangeRemapFunction(theirTriggeredVURI, myTriggeredVURI)
		theirConflictFreeTriggeredEChanges.forEach[remapTheirTriggeredVURI.accept(it)]

		val conflictFreeOriginalEChanges = commonConflictFreeOriginalEChanges + myConflictFreeOriginalEChanges +
			theirConflictFreeOriginalEChanges
		val conflictFreeTriggeredEChanges = commonConflictFreeTriggeredEChanges + myConflictFreeTriggeredEChanges +
			theirConflictFreeTriggeredEChanges
		val conflicts = conflictDetector.conflicts
		val softConflicts = conflicts.filter[solvability === ConflictSeverity::SOFT]
		val hardConflicts = conflicts.filter[solvability === ConflictSeverity::HARD]
		val originalEChangesFromSoftConflicts = softConflicts.map[sourceDefaultSolution].flatten.toList
		val triggeredEChangesFromSoftConflicts = softConflicts.map[triggeredDefaultSolution].flatten.toList

		resultingOriginalEChanges += (conflictFreeOriginalEChanges + originalEChangesFromSoftConflicts)
		resultingTriggeredEChanges += (conflictFreeTriggeredEChanges +
			triggeredEChangesFromSoftConflicts)
		val conflictFunction = [ List<EChange> echanges, Function1<Conflict, List<EChange>> cb, Consumer<EChange> remap, Conflict c |
			val propagatedChanges = cb.apply(c)
			propagatedChanges.forEach[remap.accept(it)]
			echanges += propagatedChanges
		]
		val originalConflictFunction = conflictFunction.curry(resultingOriginalEChanges).curry(originalCallback).curry(
			remapTheirVURI)
		val triggeredConflictFunction = conflictFunction.curry(resultingTriggeredEChanges).curry(triggeredCallback).
			curry(remapTheirTriggeredVURI)
		hardConflicts.forEach [ hardConflict |
			originalConflictFunction.apply(hardConflict)
			triggeredConflictFunction.apply(hardConflict)
		]

	}

	private def createMap(PropagatedChange myChange, PropagatedChange theirChange) {
		val sourceVURI = myChange.originalChange.URI
		val newSourceVURI = theirChange.originalChange.URI

		val Map<String, String> rootToRootMap = newHashMap
		rootToRootMap.put(sourceVURI.EMFUri.toFileString, newSourceVURI.EMFUri.toFileString)
		if (null !== myChange.consequentialChanges.URI) {
			val targetVURI = myChange.consequentialChanges.URI
			val newTargetVURI = theirChange.consequentialChanges.URI
			rootToRootMap.put(targetVURI.EMFUri.toFileString, newTargetVURI.EMFUri.toFileString)
		}
		conflictDetector.addMap(rootToRootMap)
	}

}
