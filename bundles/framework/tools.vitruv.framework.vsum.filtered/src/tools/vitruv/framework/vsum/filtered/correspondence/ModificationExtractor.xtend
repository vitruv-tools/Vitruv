package tools.vitruv.framework.vsum.filtered.correspondence

import java.util.ArrayList
import java.util.List
import java.util.stream.Collectors
import org.eclipse.emf.ecore.EEnumLiteral
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import tools.vitruv.framework.change.echange.eobject.CreateEObject
import tools.vitruv.framework.change.echange.eobject.DeleteEObject
import tools.vitruv.framework.change.echange.feature.list.UpdateSingleListEntryEChange
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference
import tools.vitruv.framework.change.echange.feature.single.ReplaceSingleValuedFeatureEChange
import tools.vitruv.framework.vsum.filtered.IdCrossResolver
import tools.vitruv.framework.vsum.filtered.changemodification.EChangeModification
import tools.vitruv.framework.vsum.filtered.changemodification.IDBasedEChangeModification
import tools.vitruv.framework.vsum.filtered.changemodification.IndexBasedEChangeModification
import org.eclipse.uml2.uml.PrimitiveType

import static extension tools.vitruv.framework.vsum.filtered.internal.Util.getChangedObject
import static extension tools.vitruv.framework.vsum.filtered.internal.Util.getChangedObjectID
import static extension tools.vitruv.framework.vsum.filtered.internal.Util.setChangedObjectID
import tools.vitruv.framework.change.echange.feature.FeatureEChange
import java.util.Set
import tools.vitruv.framework.vsum.accesscontrolsystem.accesscontrol.OperationAccessRightUtil
import tools.vitruv.framework.vsum.accesscontrolsystem.accesscontrol.ResourceFilter
import tools.vitruv.framework.change.description.VitruviusChange
import org.eclipse.xtend.lib.annotations.Accessors

@Accessors(NONE)
@FinalFieldsConstructor
class ModificationExtractor {

	val VitruviusChange change
	val IdCrossResolver idCrossResolver
	var CorrespondenceExtractor correspondences

	@Accessors(PUBLIC_GETTER)
	val List<EChangeModification> modifications = new ArrayList()

	def List<EChangeModification> extract(IdCrossResolver resolver, ResourceFilter filter) {
		correspondences = new CorrespondenceExtractor(change, idCrossResolver)
		checkIfModifiedObjectsCanBeModified(resolver, filter)
		handleCreatedObjects
		modifications.filter[it.containsChange].forEach[it.apply]
		handleDeletedObjects
		modifications.filter[it.containsChange].forEach[it.reverse]
		return modifications
	}

	private def void checkIfModifiedObjectsCanBeModified(IdCrossResolver resolver, ResourceFilter filter) {
		for (FeatureEChange<EObject, EReference> echange : correspondences.modifiedObjects) {
			if (resolver.hasEObject(echange.affectedEObjectID)) {
				if (!filter.hasAccessRights(Set.of(resolver.getEObject(echange.affectedEObjectID)),
					OperationAccessRightUtil.neededRightsModifying)) {
					throw new IllegalStateException(
						"Tried to modify element " + resolver.getEObject(echange.affectedEObjectID) + " with id " +
							echange.affectedEObjectID + " without access and with change " + echange + " in resource " +
							echange.affectedEObject.eResource)
				}
			}
		}
	}

	private def void handleCreatedObjects() {
		correspondences.createdObjects.stream.forEach [ visitedEObject |
			visitedEObject.updateChangesIndexBased.forEach[computeAndInsertCorrectIndex(visitedEObject, it)]
		]
		for (var i = 0; i < correspondences.createdObjects.size; i++) {
			val eObjectCreationAndModifications = correspondences.createdObjects.get(i)
			// all index variables have been correctly set so we can use them to derive the ids
			for (UpdateSingleListEntryEChange<EObject, EReference> change : eObjectCreationAndModifications.
				updateChangesIndexBased) {
				if (change instanceof RemoveEReference) {
					val builder = new StringBuilder(change.changedObjectID)
					val indexLength = builder.reverse.indexOf(".")
					builder.delete(0, indexLength).reverse.append(change.index)
					modifications.add(
						new IDBasedEChangeModification(change, change.changedObjectID, builder.toString, [ c, s |
							(c as UpdateSingleListEntryEChange<EObject, EReference>).changedObjectID = s
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
				if (!idCrossResolver.notPresentInSource(change.affectedEObjectID)) {
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
											referenceBasedChange.affectedEObjectID, builder.toString, [ c,s |
												(c as ReplaceSingleValuedFeatureEChange<EObject, EStructuralFeature, Object>).affectedEObjectID = s
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
					if (del.existenceChange.size == 1) {
						if (!del.updateChangesIndexBased.empty) {
							// extract the model element from source and get the differences in crossreferences
							checkAccessToCrossReferences(del,
								idCrossResolver.getEObject(indexBasedChange.changedObjectID))
						}
					}

				}
			}
		// object gets deleted
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
		EObjectExistenceChangesAndModifications<DeleteEObject<EObject>, UpdateSingleListEntryEChange<EObject, EReference>, ReplaceSingleValuedFeatureEChange<EObject, EStructuralFeature, Object>> change,
		EObject original) {

		if (change.updateChangesIndexBased.empty) {
			return
		}
		val diffcrossref = new ArrayList<EObject>()
		val crossReferencesOriginalEObject = original.eCrossReferences
		diffcrossref.addAll(crossReferencesOriginalEObject)
		diffcrossref.removeAll(change.updateChangesIndexBased.stream.filter[it instanceof RemoveEReference].map [
			idCrossResolver.getEObject(it.affectedEObjectID)
		].collect(Collectors.toList))
		// does not work because oldValue is part of the VitruvView whereas removedEObject is part of UnfilteredSource and as no IDs are collected and 
		// the VitruvView does not contain methods to get the element from the FilteredSource corresponding to the oldValue we cannot get the correct value here
		// diffcrossref.removeAll(change.updateChangesReferenceBased.stream.filter[it.oldValue.equals(removedEObject)].map [
		// resolver.getEObject(it.affectedEObjectID)
		// ].collect(Collectors.toList))
		// so overapproximate with all removed elements
		diffcrossref.removeAll(change.updateChangesReferenceBased.stream.map [
			idCrossResolver.getEObject(it.affectedEObjectID)
		].collect(Collectors.toList))
		for (EObject referenced : diffcrossref) {
			// Enum Literals are not contained in the model and cannot have accessright definitions
			if (!(referenced instanceof EEnumLiteral) && !(referenced instanceof PrimitiveType)) {
				throw new IllegalStateException(
					"Element " + change.existenceChange.get(0).affectedEObject +
						" shall be deleted but contains references on elements you have no access to!" + diffcrossref)
			}
		}
	}
}
