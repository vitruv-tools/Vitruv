package tools.vitruv.framework.change.echange

import tools.vitruv.framework.change.uuid.UuidProviderAndResolver
import tools.vitruv.framework.change.echange.eobject.EObjectAddedEChange
import tools.vitruv.framework.change.echange.eobject.EObjectSubtractedEChange
import tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange
import tools.vitruv.framework.change.echange.feature.FeatureEChange
import tools.vitruv.framework.change.echange.compound.CompoundEChange

/**
 * Provides logic for initializing the IDs within changes and for updating
 * the object references in the {@link UuidProviderAndResolver}. 
 */
class EChangeIdManager {
	val UuidProviderAndResolver uuidProviderAndResolver;
	val boolean strictMode;

	/**
	 * Initializes the manager with a {@link UuidProviderAndResolver}.
	 * 
	 * @param uuidProviderAndResolver -
	 * 		the {@link UuidProviderAndResolver} to use for ID management
	 * @param strictMode -
	 * 		defines if the manager should run in strict mode, which throws {@link IllegalStateExceptions} 
	 * 		if an element that should already have an ID does no have one. Using non-strict mode can be 
	 * 		necessary if model changes are not recorded from beginning of model creation
	 */
	new(UuidProviderAndResolver uuidProviderAndResolver, boolean strictMode) {
		this.uuidProviderAndResolver = uuidProviderAndResolver;
		this.strictMode = strictMode;
	}

	def void setOrGenerateIds(EChange eChange) {
		switch eChange {
			EObjectAddedEChange<?>:
				setOrGenerateNewValueId(eChange)
			EObjectSubtractedEChange<?>:
				setOrGenerateOldValueId(eChange)
			EObjectExistenceEChange<?>:
				setOrGenerateAffectedEObjectId(eChange)
			FeatureEChange<?,?>:
				setOrGenerateAffectedEObjectId(eChange)
			CompoundEChange:
				eChange.atomicChanges.forEach[setOrGenerateIds]
		}
	}

	private def void setOrGenerateNewValueId(EObjectAddedEChange<?> addedEChange) {
		if(addedEChange.newValue === null) {
			return;
		}
		if(!uuidProviderAndResolver.hasUuid(addedEChange.newValue)) {
			if(strictMode) {
				throw new IllegalStateException();
			}
			addedEChange.newValueID = uuidProviderAndResolver.registerEObject(addedEChange.newValue);
		} else {
			addedEChange.newValueID = uuidProviderAndResolver.getUuid(addedEChange.newValue);
		}
	}

	private def void setOrGenerateOldValueId(EObjectSubtractedEChange<?> subtractedEChange) {
		if(subtractedEChange.oldValue === null) {
			return;
		}
		subtractedEChange.oldValueID = uuidProviderAndResolver.getOrRegisterUuid(subtractedEChange.oldValue)
	}

	private def void setOrGenerateAffectedEObjectId(EObjectExistenceEChange<?> existenceChange) {
		if(existenceChange.affectedEObject === null) {
			throw new IllegalStateException();
		}
		existenceChange.affectedEObjectID = uuidProviderAndResolver.getOrRegisterUuid(existenceChange.affectedEObject);
	}

	private def void setOrGenerateAffectedEObjectId(FeatureEChange<?, ?> featureChange) {
		if(featureChange.affectedEObject === null) {
			throw new IllegalStateException();
		}
		if(!uuidProviderAndResolver.hasUuid(featureChange.affectedEObject)) {
			if(strictMode) {
				throw new IllegalStateException();
			}
			featureChange.affectedEObjectID = uuidProviderAndResolver.registerEObject(featureChange.affectedEObject);
		} else {
			featureChange.affectedEObjectID = uuidProviderAndResolver.getUuid(featureChange.affectedEObject);
		}
	}

	def void updateRegisteredObject(EChange eChange) {
		switch eChange {
			EObjectAddedEChange<?>:
				updateNewValueId(eChange)
			EObjectSubtractedEChange<?>:
				updateOldValueId(eChange)
			EObjectExistenceEChange<?>:
				updateAffectedEObjectId(eChange)
			FeatureEChange<?,?>:
				updateAffectedEObjectId(eChange)
			CompoundEChange:
				eChange.atomicChanges.forEach[updateRegisteredObject]
		}
	}

	private def updateNewValueId(EObjectAddedEChange<?> addedEChange) {
		if(addedEChange.newValue === null) {
			return;
		}
		uuidProviderAndResolver.registerEObject(addedEChange.newValueID, addedEChange.newValue);
	}

	private def updateOldValueId(EObjectSubtractedEChange<?> subtractedEChange) {
		if(subtractedEChange.oldValue === null) {
			return;
		}
		uuidProviderAndResolver.registerEObject(subtractedEChange.oldValueID, subtractedEChange.oldValue);
	}

	private def updateAffectedEObjectId(EObjectExistenceEChange<?> existenceChange) {
		if(existenceChange.affectedEObject === null) {
			throw new IllegalStateException();
		}
		uuidProviderAndResolver.registerEObject(existenceChange.affectedEObjectID, existenceChange.affectedEObject);
	}

	private def updateAffectedEObjectId(FeatureEChange<?, ?> featureChange) {
		if(featureChange.affectedEObject === null) {
			throw new IllegalStateException();
		}
		uuidProviderAndResolver.registerEObject(featureChange.affectedEObjectID, featureChange.affectedEObject);
	}
}
