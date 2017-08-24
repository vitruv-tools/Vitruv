package tools.vitruv.framework.change.echange

import tools.vitruv.framework.change.echange.eobject.EObjectAddedEChange
import tools.vitruv.framework.change.echange.eobject.EObjectSubtractedEChange
import tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange
import tools.vitruv.framework.change.echange.feature.FeatureEChange
import tools.vitruv.framework.change.echange.compound.CompoundEChange
import tools.vitruv.framework.change.echange.eobject.DeleteEObject
import tools.vitruv.framework.change.echange.eobject.CreateEObject
import tools.vitruv.framework.change.uuid.UuidGeneratorAndResolver
import tools.vitruv.framework.change.uuid.UuidResolver
import org.eclipse.emf.ecore.EObject

/**
 * Provides logic for initializing the IDs within changes and for updating
 * the object references in the {@link UuidProviderAndResolver}. 
 */
class EChangeIdManager {
	val UuidResolver globalUuidResolver;
	val UuidGeneratorAndResolver localUuidGeneratorAndResolver;
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
	new(UuidResolver globalUuidResolver, UuidGeneratorAndResolver localUuidGeneratorAndResolver, boolean strictMode) {
		this.globalUuidResolver = globalUuidResolver;
		this.localUuidGeneratorAndResolver = localUuidGeneratorAndResolver;
		this.strictMode = strictMode;
	}

	def void setOrGenerateIds(EChange eChange) {
		switch eChange {
			EObjectExistenceEChange<?>,
			FeatureEChange<?,?>:
				setOrGenerateAffectedEObjectId(eChange)
			CompoundEChange:
				eChange.atomicChanges.forEach[setOrGenerateIds]
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

	private def String getOrGenerateValue(EObject object, boolean strictMode) {
		if (globalUuidResolver.hasUuid(object)) {
			return globalUuidResolver.getUuid(object);
		} else {
			if(!localUuidGeneratorAndResolver.hasUuid(object)) {
				if(strictMode) {
					throw new IllegalStateException("No UUID registered for existing EObject: " + object);
				}
				return localUuidGeneratorAndResolver.registerEObject(object);
			} else {
				return localUuidGeneratorAndResolver.getUuid(object);
			}
		}
	}

	private def void setOrGenerateNewValueId(EObjectAddedEChange<?> addedEChange) {
		if(addedEChange.newValue === null) {
			return;
		}
		addedEChange.newValueID = getOrGenerateValue(addedEChange.newValue, strictMode);
	}	

	private def void setOrGenerateOldValueId(EObjectSubtractedEChange<?> subtractedEChange) {
		if(subtractedEChange.oldValue === null) {
			return;
		}
		subtractedEChange.oldValueID = getOrGenerateValue(subtractedEChange.oldValue, false);
	}

	private def dispatch void setOrGenerateAffectedEObjectId(CreateEObject<?> createChange) {
		if(createChange.affectedEObject === null) {
			throw new IllegalStateException();
		}
		createChange.affectedEObjectID = getOrGenerateValue(createChange.affectedEObject, false);
	}
	
	private def dispatch void setOrGenerateAffectedEObjectId(DeleteEObject<?> deleteChange) {
		if(deleteChange.affectedEObject === null) {
			throw new IllegalStateException();
		}
		deleteChange.affectedEObjectID = getOrGenerateValue(deleteChange.affectedEObject, false);
		deleteChange.consequentialRemoveChanges.forEach[setOrGenerateIds]
	}

	private def dispatch void setOrGenerateAffectedEObjectId(FeatureEChange<?, ?> featureChange) {
		if(featureChange.affectedEObject === null) {
			throw new IllegalStateException();
		}
		featureChange.affectedEObjectID = getOrGenerateValue(featureChange.affectedEObject, strictMode);
	}

}
