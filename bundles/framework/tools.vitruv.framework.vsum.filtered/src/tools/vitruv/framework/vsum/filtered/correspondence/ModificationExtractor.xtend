package tools.vitruv.framework.vsum.filtered.correspondence

import java.util.ArrayList
import java.util.List
import java.util.Map
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import tools.vitruv.framework.change.echange.eobject.CreateEObject
import tools.vitruv.framework.change.echange.feature.FeatureEChange
import tools.vitruv.framework.change.echange.feature.list.UpdateSingleListEntryEChange
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference
import tools.vitruv.framework.change.echange.feature.single.ReplaceSingleValuedFeatureEChange
import tools.vitruv.framework.vsum.filtered.IdCrossResolver
import tools.vitruv.framework.vsum.filtered.changemodification.EChangeModification
import tools.vitruv.framework.vsum.filtered.changemodification.IDBasedEChangeModification
import tools.vitruv.framework.vsum.filtered.changemodification.IndexBasedEChangeModification

import static extension tools.vitruv.framework.vsum.filtered.internal.Util.getChangedObject
import static extension tools.vitruv.framework.vsum.filtered.internal.Util.getChangedObjectID
import static extension tools.vitruv.framework.vsum.filtered.internal.Util.setChangedObjectID
import tools.vitruv.framework.change.echange.eobject.DeleteEObject
import java.util.stream.Collectors

@FinalFieldsConstructor
class ModificationExtractor {

	val Correspondences correspondences
	val IdCrossResolver idCrossResolver
	val List<EChangeModification> modifications = new ArrayList()

	def List<EChangeModification> extract() {
		handleCreatedObjects
		modifications.filter[it instanceof IndexBasedEChangeModification].forEach[it.reverse]
		handleDeletedObjects
		return modifications
	}

	def private void handleCreatedObjects() {
		correspondences.createdObjects.stream.forEach [ visitedEObject |
			visitedEObject.updateChangesIndexBased.forEach[computeAndInsertCorrectIndex(visitedEObject, it)]
		]
		for (var i = 0; i < correspondences.createdObjects.size; i++) {
			val eObjectCreationAndModifications = correspondences.createdObjects.get(i)
			// _____________________________________ index based changes need a changed index
//			for (UpdateSingleListEntryEChange<EObject, EReference> change : eObjectCreationAndModifications.
//				updateChangesIndexBased) {
//				// all changes coming in here are local to one change (they have been added and possibly removed in one change)
//				computeAndInsertCorrectIndex(eObjectCreationAndModifications, change)
//			}
			// all index variables have been correctly set so we can use them to derive the ids
			for (UpdateSingleListEntryEChange<EObject, EReference> change : eObjectCreationAndModifications.
				updateChangesIndexBased) {
				if (change instanceof RemoveEReference) {
					val builder = new StringBuilder(change.changedObjectID)
					val indexLength = builder.reverse.indexOf(".")
					builder.delete(0, indexLength).reverse.append(change.index)
					modifications.add(
						new IDBasedEChangeModification(change, change.changedObjectID, builder.toString, [ s |
							change.changedObjectID = s
						]))
				} else if (change instanceof InsertEReference) {
					// insert references refer to the cache so no changes to ids are needed
				}
			}
			// _____________________________________ and reference based changes (they overlap with index based) need an updated id
			// the list/feature is already existing and may contain elements in its unfiltered state
			if (!eObjectCreationAndModifications.updateChangesIndexBased.empty) {
				// the indices of subsequent changes are only invalid if they were changed by an operation before
				val change = eObjectCreationAndModifications.updateChangesIndexBased.get(0)
				if (!idCrossResolver.isNew(change.affectedEObjectID)) {
					for (ReplaceSingleValuedFeatureEChange<EObject, EStructuralFeature, Object> referenceBasedChange : eObjectCreationAndModifications.
						updateChangesReferenceBased) {
						if (referenceBasedChange.affectedEObjectID.contains("@")) {
							val correspondingIndexBasedChange = find(
								eObjectCreationAndModifications.updateChangesIndexBased, referenceBasedChange)
							if (correspondingIndexBasedChange != -1) {
								val builder = new StringBuilder(referenceBasedChange.affectedEObjectID)
								val indexLength = builder.reverse.indexOf(".")
								builder.delete(0, indexLength).reverse.append(correspondingIndexBasedChange)
								if (!referenceBasedChange.affectedEObjectID.equals(builder.toString)) {
									modifications.add(
										new IDBasedEChangeModification(referenceBasedChange,
											referenceBasedChange.affectedEObjectID, builder.toString, [ s |
												referenceBasedChange.affectedEObjectID = s
											]))
								}
							}

						}
					}

				// we update a list so the format of the id is somwhat like ../../@child.1 so delete every number after the . and add the calculated index
				// with the index of the corresponding insertion operation - corresponding means affectedEObject and affectedFeature and the value are identical
				}

			}

		}
	}

	private def void handleDeletedObjects() {
		for (EObjectExistenceChangesAndModifications<DeleteEObject<EObject>, UpdateSingleListEntryEChange<EObject, EReference>, ReplaceSingleValuedFeatureEChange<EObject, EStructuralFeature, Object>> del : correspondences.
			deletedObjects) {
			// object gets deleted
			if (del.existenceChange.size == 1) {
				if (!del.updateChangesIndexBased.empty) {
					// extract the model element from source and get the differences in crossreferences
					checkAccessToCrossReferences(del)
				}
			}
			for (UpdateSingleListEntryEChange<EObject, EReference> indexBasedChange : del.updateChangesIndexBased) {
				// all elements here are existing so we can just change the index to the one in the initial list
				if (idCrossResolver.hasEObject(indexBasedChange.affectedEObjectID) &&
					idCrossResolver.hasEObject(indexBasedChange.changedObjectID)) {
					val list = idCrossResolver.getEObject(indexBasedChange.affectedEObjectID).eGet(
						indexBasedChange.affectedFeature) as List
					val indexBasedEChangeModification = new IndexBasedEChangeModification(indexBasedChange,
						indexBasedChange.index,
						list.indexOf(idCrossResolver.getEObject(indexBasedChange.changedObjectID)))
					// index based changes have to be applied as the correct indices for later id based changes are extracted from the changed indices
					indexBasedEChangeModification.apply
					modifications.add(indexBasedEChangeModification)
				}
			}
		}
	}

	private def int find(List<UpdateSingleListEntryEChange<EObject, EReference>> indexBasedChanges,
		ReplaceSingleValuedFeatureEChange<EObject, EStructuralFeature, Object> referenceBasedChange) {
		val changes = indexBasedChanges.filter[it.changedObject.equals(referenceBasedChange.affectedEObject)]
		if (changes.map[it.index].iterator.hasNext) {
			return changes.map[it.index].iterator.next
		} else
			return -1
	}

	private def void computeAndInsertCorrectIndex(
		EObjectExistenceChangesAndModifications<CreateEObject<EObject>, UpdateSingleListEntryEChange<EObject, EReference>, ReplaceSingleValuedFeatureEChange<EObject, EStructuralFeature, Object>> correspondence,
		UpdateSingleListEntryEChange<EObject, EReference> indexBasedChange) {
		if (idCrossResolver.hasEObject(indexBasedChange.affectedEObjectID)) {
			val changesToThisFeature = correspondences.featureToListChange.get(indexBasedChange.affectedFeature)
			val changesToThisFeatureUntilNow = changesToThisFeature.subList(0,
				changesToThisFeature.indexOf(indexBasedChange))
			// if the affected object is found we have to map indices from filtered to unfiltered based on the feature that is changed
			if (indexBasedChange instanceof InsertEReference) {
				// existing object is inserted in an existing list so the indices recorded cannot be mapped (place the element directly behind the element before
				// from filtered to unfiltered or directly before the next one which can be different positions), so add the new element at the end
				// filtered a,d,f versus unfiltered a,b,c,d,e,f and insert at position 2 g
				// filtered a,d,g,f unfiltered either index a,b,g,c,d,e,f or after a,b,c,d,g,e,f or before a,b,c,d,e,g,f
				val affectedEObject = idCrossResolver.getEObject(indexBasedChange.affectedEObjectID)
				try {
					val baseIndexUnfiltered = (affectedEObject.eGet(indexBasedChange.affectedFeature) as List).size
					val insertionsUntilNow = changesToThisFeatureUntilNow.stream().filter [
						it instanceof InsertEReference
					].count as int
					val deletionsUntilNow = changesToThisFeatureUntilNow.stream().
						filter[it instanceof RemoveEReference].count as int
					val indexBasedEChangeModification = new IndexBasedEChangeModification(indexBasedChange,
						indexBasedChange.index, (baseIndexUnfiltered + insertionsUntilNow - deletionsUntilNow))
					// index based changes have to be applied as the correct indices for later id based changes are extracted from the changed indices
					indexBasedEChangeModification.apply
					modifications.add(indexBasedEChangeModification)
				} catch (IllegalArgumentException exception) {
				} catch (NullPointerException exception) {
				}

			} else if (indexBasedChange instanceof RemoveEReference) {
				// find the insertion corresponding to the removal
				for (UpdateSingleListEntryEChange<EObject, EStructuralFeature> change : changesToThisFeatureUntilNow.
					clone.reverse) {
					if (change instanceof InsertEReference) {
						if (change.newValue.equals(indexBasedChange.oldValue)) {
							val indexBasedEChangeModification = new IndexBasedEChangeModification(indexBasedChange,
								indexBasedChange.index, change.index)
							// index based changes have to be applied as the correct indices for later id based changes are extracted from the changed indices
							indexBasedEChangeModification.apply
							modifications.add(indexBasedEChangeModification)
						}
					}
				}
			}

		}
	}

	protected def void checkAccessToCrossReferences(
		EObjectExistenceChangesAndModifications<DeleteEObject<EObject>, UpdateSingleListEntryEChange<EObject, EReference>, ReplaceSingleValuedFeatureEChange<EObject, EStructuralFeature, Object>> change) {

		if (change.updateChangesIndexBased.empty) {
			return
		}
		val diffcrossref = new ArrayList<EObject>()
		val resolver = idCrossResolver
		val removeReferenceChange = change.updateChangesIndexBased.stream.filter[it instanceof RemoveEReference].
			findFirst
		if (removeReferenceChange.empty) {
			return
		}
		if (!resolver.hasEObject(removeReferenceChange.get.changedObjectID)) {
			return
		}
		val removedEObject = resolver.getEObject(removeReferenceChange.get.changedObjectID)
		val crossReferencesOriginalEObject = removedEObject.eCrossReferences
		diffcrossref.addAll(crossReferencesOriginalEObject)
		diffcrossref.removeAll(change.updateChangesIndexBased.stream.filter[it instanceof RemoveEReference].map [
			resolver.getEObject(it.affectedEObjectID)
		].collect(Collectors.toList))
		diffcrossref.removeAll(change.updateChangesReferenceBased.stream.filter[it.oldValue.equals(removedEObject)].map [
			resolver.getEObject(it.affectedEObjectID)
		].collect(Collectors.toList))
	// remove all references that have been explicitly deleted by the UpdateSingleListEntry
	// TODO deletions have to be considered as the unfiltered version may has references on them which are not available in the filtered
	// version so EcoreUtil.delete wont delete those cross references, but because they have cache ids they cannot be collected by id
	// and instead have to be collected with object equals and processed differently
//		if (!diffcrossref.empty)
//			throw new IllegalStateException("Element " + change.existenceChange.get(0).affectedEObject +
//				" shall be deleted but contains references on elements you have no access to!")
	}
}
