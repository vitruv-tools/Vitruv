package tools.vitruv.framework.change.echange

import tools.vitruv.framework.change.echange.eobject.EObjectAddedEChange
import tools.vitruv.framework.change.echange.eobject.EObjectSubtractedEChange
import tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange
import tools.vitruv.framework.change.echange.feature.FeatureEChange
import tools.vitruv.framework.change.echange.eobject.DeleteEObject
import tools.vitruv.framework.change.echange.eobject.CreateEObject
import tools.vitruv.framework.uuid.UuidGeneratorAndResolver
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.change.echange.feature.reference.UpdateReferenceEChange

/**
 * Provides logic for initializing the IDs within changes and for updating
 * the object references in the {@link UuidGeneratorAndResolver}. 
 */
class EChangeIdManager {
	val UuidGeneratorAndResolver uuidGeneratorAndResolver;

	/**
	 * Initializes the manager with a {@link UuidGeneratorAndResolver}.
	 * 
	 * @param uuidGeneratorAndResolver -
	 * 		the {@link UuidGeneratorAndResolver} to use for ID management
	 */
	new(UuidGeneratorAndResolver uuidGeneratorAndResolver) {
		if (uuidGeneratorAndResolver === null) {
			throw new IllegalArgumentException;
		}
		this.uuidGeneratorAndResolver = uuidGeneratorAndResolver;
	}

	def void setOrGenerateIds(EChange eChange) {
		switch eChange {
			EObjectExistenceEChange<?>,
			FeatureEChange<?,?>:
				setOrGenerateAffectedEObjectId(eChange)
		}
		switch eChange {
			EObjectSubtractedEChange<?>:
				setOrGenerateOldValueId(eChange)
		}
		switch eChange {
			EObjectAddedEChange<?>:
				setOrGenerateNewValueId(eChange)
		}
	}

	def boolean isCreateChange(EObjectAddedEChange<?> addedEChange) {
		// We do not check the containment of the reference, because an element may be inserted into a non-containment
		// reference before inserting it into a containment reference so that the create change has to be added
		// for the inserting into the non-containment reference
		var create = addedEChange.newValue !== null && !(uuidGeneratorAndResolver.hasUuid(addedEChange.newValue))
		// Look if the new value has no resource or if it is a reference change, if the resource of the affected
		// object is the same. Otherwise, the create has to be handled by an insertion/reference in that resource, as
		// it can be potentially a reference to a third party model, for which no create shall be instantiated		
		create = create && (addedEChange.newValue.eResource === null || !(addedEChange instanceof UpdateReferenceEChange<?>) || addedEChange.newValue.eResource == (addedEChange as UpdateReferenceEChange<?>).affectedEObject.eResource)
		return create;
	}
	
	private def String getOrGenerateValue(EObject object) {
		if (uuidGeneratorAndResolver.hasUuid(object)) {
			return uuidGeneratorAndResolver.getUuid(object);
		}
		return uuidGeneratorAndResolver.generateUuidWithoutCreate(object);
	}
	
	private def void setOrGenerateNewValueId(EObjectAddedEChange<?> addedEChange) {
		if(addedEChange.newValue === null) {
			return;
		}
		addedEChange.newValueID = getOrGenerateValue(addedEChange.newValue);
	}	

	private def void setOrGenerateOldValueId(EObjectSubtractedEChange<?> subtractedEChange) {
		if(subtractedEChange.oldValue === null) {
			return;
		}
		subtractedEChange.oldValueID = getOrGenerateValue(subtractedEChange.oldValue);
	}

	private def dispatch void setOrGenerateAffectedEObjectId(CreateEObject<?> createChange) {
		if(createChange.affectedEObject === null) {
			throw new IllegalStateException();
		}
		val affectedObject = createChange.affectedEObject
		if (!uuidGeneratorAndResolver.hasUuid(affectedObject)) {
			createChange.affectedEObjectID = uuidGeneratorAndResolver.generateUuid(affectedObject);	
		} else {
			createChange.affectedEObjectID = uuidGeneratorAndResolver.getUuid(affectedObject);
		}
		
	}
	
	private def dispatch void setOrGenerateAffectedEObjectId(DeleteEObject<?> deleteChange) {
		if(deleteChange.affectedEObject === null) {
			throw new IllegalStateException();
		}
		deleteChange.affectedEObjectID = getOrGenerateValue(deleteChange.affectedEObject);
		deleteChange.consequentialRemoveChanges.forEach[setOrGenerateIds]
	}

	private def dispatch void setOrGenerateAffectedEObjectId(FeatureEChange<?, ?> featureChange) {
		if(featureChange.affectedEObject === null) {
			throw new IllegalStateException();
		}
		featureChange.affectedEObjectID = getOrGenerateValue(featureChange.affectedEObject);
	}

}
