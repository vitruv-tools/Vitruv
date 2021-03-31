package tools.vitruv.framework.change.echange

import tools.vitruv.framework.change.echange.eobject.EObjectAddedEChange
import tools.vitruv.framework.change.echange.eobject.EObjectSubtractedEChange
import tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange
import tools.vitruv.framework.change.echange.feature.FeatureEChange
import tools.vitruv.framework.change.echange.eobject.DeleteEObject
import tools.vitruv.framework.change.echange.eobject.CreateEObject
import tools.vitruv.framework.uuid.UuidGeneratorAndResolver
import org.eclipse.emf.ecore.EObject
import static com.google.common.base.Preconditions.checkArgument

/**
 * Provides logic for initializing the IDs within changes. 
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
		checkArgument(uuidGeneratorAndResolver !== null, "uuid generator and resolver must not be null")
		this.uuidGeneratorAndResolver = uuidGeneratorAndResolver
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

	private def String getUuid(EObject object) {
		return uuidGeneratorAndResolver.getUuid(object);
	}

	private def void setOrGenerateNewValueId(EObjectAddedEChange<?> addedEChange) {
		if (addedEChange.newValue === null) {
			return;
		}
		addedEChange.newValueID = addedEChange.newValue.uuid
	}

	private def void setOrGenerateOldValueId(EObjectSubtractedEChange<?> subtractedEChange) {
		if (subtractedEChange.oldValue === null) {
			return;
		}
		subtractedEChange.oldValueID = subtractedEChange.oldValue.uuid
	}

	private def dispatch void setOrGenerateAffectedEObjectId(CreateEObject<?> createChange) {
		val affectedEObject = createChange.affectedEObject
		checkArgument(affectedEObject !== null, "Create change must have an affected EObject: %s", createChange)
		if (!uuidGeneratorAndResolver.hasUuid(affectedEObject)) {
			createChange.affectedEObjectID = uuidGeneratorAndResolver.generateUuid(affectedEObject);
		} else {
			createChange.affectedEObjectID = affectedEObject.uuid;
		}
	}

	private def dispatch void setOrGenerateAffectedEObjectId(DeleteEObject<?> deleteChange) {
		val affectedEObject = deleteChange.affectedEObject
		checkArgument(affectedEObject !== null, "Delete change must have an affected EObject: %s", deleteChange)
		deleteChange.affectedEObjectID = affectedEObject.uuid
		deleteChange.consequentialRemoveChanges.forEach[setOrGenerateIds]
	}

	private def dispatch void setOrGenerateAffectedEObjectId(FeatureEChange<?, ?> featureChange) {
		val affectedEObject = featureChange.affectedEObject
		checkArgument(affectedEObject !== null, "Feature change must have an affected EObject: %s", featureChange)
		featureChange.affectedEObjectID = affectedEObject.uuid
	}

}
