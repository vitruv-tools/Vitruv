package tools.vitruv.framework.vsum.filtered.correspondence

import java.util.ArrayList
import java.util.HashMap
import java.util.HashSet
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.change.echange.eobject.CreateEObject
import tools.vitruv.framework.change.echange.eobject.DeleteEObject
import tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange
import tools.vitruv.framework.change.echange.feature.FeatureEChange
import tools.vitruv.framework.change.echange.feature.list.UpdateSingleListEntryEChange
import tools.vitruv.framework.change.echange.feature.single.ReplaceSingleValuedFeatureEChange
import tools.vitruv.framework.vsum.filtered.IdCrossResolver

import static tools.vitruv.framework.vsum.filtered.internal.Util.*

import static extension tools.vitruv.framework.vsum.filtered.internal.Util.getChangedObject

class Correspondences {
	public val modifiedObjects = new HashSet<FeatureEChange<EObject, EReference>>()
	public val deletedObjects = new EObjectExistenceChangesAndModificationsList<DeleteEObject<EObject>, UpdateSingleListEntryEChange<EObject, EReference>, ReplaceSingleValuedFeatureEChange<EObject, EStructuralFeature, Object>>()
	public val createdObjects = new EObjectExistenceChangesAndModificationsList<CreateEObject<EObject>, UpdateSingleListEntryEChange<EObject, EReference>, ReplaceSingleValuedFeatureEChange<EObject, EStructuralFeature, Object>>()
	public val featureToListChange = new HashMap<EStructuralFeature, List<UpdateSingleListEntryEChange<EObject, EStructuralFeature>>>()

	new(VitruviusChange change, IdCrossResolver crossResolver) {
		extractCorrespondeces(change, crossResolver)
		mapFeatureToModifications(change)
	}

	private def void extractCorrespondeces(VitruviusChange change, IdCrossResolver crossResolver) {
		// map all ids that can be found in the filtered version to the filtered idresolver by adding them
		for (echange : change.EChanges) {
			if (echange instanceof EObjectExistenceEChange) {
				addExistenceChange(echange)
			}
			// newly created objects can either be created, deleted or inserted or removed from either a single- or multi-valued reference
			if (echange instanceof FeatureEChange) {
				if (crossResolver.isNew(getAffectedEObjectID(echange))) {
					if (echange instanceof UpdateSingleListEntryEChange) {
						if (createdObjects.isPresent(echange.changedObject as EObject)) {
							createdObjects.add(echange)
						} else {
							deletedObjects.add(echange)
						}
					} else if (echange instanceof ReplaceSingleValuedFeatureEChange) {
						val changed = new ArrayList
						changed.add(echange.affectedEObject)
						if(echange.newValue instanceof EObject) changed.add(echange.newValue as EObject)
						if(echange.oldValue instanceof EObject) changed.add(echange.oldValue as EObject)
						for (EObject changedEObject : changed) {
							if (createdObjects.isPresent(changedEObject)) {
								createdObjects.add(echange)
							} else {
								deletedObjects.add(echange)
							}
						}
					} else {
						throw new IllegalArgumentException("unknown echange " + echange)
					}
				} else {
					// the affected object is already existing so we probably have to change indices and check if the modification is allowed
					modifiedObjects.add(echange)
					if (echange instanceof UpdateSingleListEntryEChange) {
						if (createdObjects.isPresent(echange.changedObject as EObject)) {
							createdObjects.add(echange)
						} else {
							deletedObjects.add(echange)
						}
					} else if (echange instanceof ReplaceSingleValuedFeatureEChange) {
						// the affectedObject is existing and new and old value are directly part of the change and have no ids that could be changed
					} else {
						throw new IllegalArgumentException("unknown echange " + echange)
					}
				}
			}
		}
	}

	protected def void addExistenceChange(EObjectExistenceEChange echange) {
		if (echange instanceof CreateEObject) {
			createdObjects.add(echange)
		} else if (echange instanceof DeleteEObject) {
			deletedObjects.add(echange)
		}
	}

	private def void mapFeatureToModifications(VitruviusChange change) {
		change.EChanges.stream.filter[it instanceof UpdateSingleListEntryEChange].map [
			it as UpdateSingleListEntryEChange<EObject, EStructuralFeature>
		].forEach [
			if (featureToListChange.get(it.affectedFeature) === null) {
				featureToListChange.put(it.affectedFeature, new ArrayList())
			}
			featureToListChange.get(it.affectedFeature).add(it)
		]
	}
}
