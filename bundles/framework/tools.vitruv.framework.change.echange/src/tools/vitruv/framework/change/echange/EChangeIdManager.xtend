package tools.vitruv.framework.change.echange

import tools.vitruv.framework.change.echange.eobject.EObjectAddedEChange
import tools.vitruv.framework.change.echange.eobject.EObjectSubtractedEChange
import tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange
import tools.vitruv.framework.change.echange.feature.FeatureEChange
import tools.vitruv.framework.change.echange.eobject.DeleteEObject
import tools.vitruv.framework.change.echange.eobject.CreateEObject
import tools.vitruv.framework.change.uuid.UuidGeneratorAndResolver
import tools.vitruv.framework.change.uuid.UuidResolver
import org.eclipse.emf.ecore.EObject
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.util.EcoreUtil
import tools.vitruv.framework.change.echange.feature.reference.UpdateReferenceEChange

/**
 * Provides logic for initializing the IDs within changes and for updating
 * the object references in the {@link UuidProviderAndResolver}. 
 */
class EChangeIdManager {
	val static Logger logger = Logger.getLogger(EChangeIdManager)
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

	public def boolean isCreateChange(EObjectAddedEChange<?> addedEChange) {
		// TODO Currently, we only look for the UUID locally. If we look globally, resolving the URI can result
		// in an old element with the same URI being matched. Nevertheless, this currently requires the local
		// UUID repository to be always complete
		var create = addedEChange.newValue !== null && !(localUuidGeneratorAndResolver.hasUuid(addedEChange.newValue))
		// Look if the new value has no resource or if it is a reference change, if the resource of the affected
		// object is the same. Otherwise, the create has to be handled by an insertion/reference in that resource, as
		// it can be potentially a reference to a third party model, for which no create shall be instantiated		
		create = create && (addedEChange.newValue.eResource === null || !(addedEChange instanceof UpdateReferenceEChange<?>) || addedEChange.newValue.eResource == (addedEChange as UpdateReferenceEChange<?>).affectedEObject.eResource)
		return create;
	}
	
	private def String getOrGenerateValue(EObject object) {
		// First check local mapping because an element may have the same URI than a previous one
		// so it would be resolved to another object globally, giving a false UUID
		if (localUuidGeneratorAndResolver.hasUuid(object)) {
			return localUuidGeneratorAndResolver.getUuid(object);
		}
		if (globalUuidResolver.hasUuid(object)) {
			return globalUuidResolver.getUuid(object);
		} else {
			if(!localUuidGeneratorAndResolver.hasUuid(object)) {
				val result = localUuidGeneratorAndResolver.registerEObject(object);
				registerGlobally(result, object);
				return result;
			} else {
				return localUuidGeneratorAndResolver.getUuid(object);
			}
		}
	}
	
	private def void registerGlobally(String uuid, EObject object) {
		// Register UUID globally for third party elements that are statically accessible and are never created
		var EObject globallyResolvedObject = null
		try {
			globallyResolvedObject = globalUuidResolver.resourceSet.getEObject(EcoreUtil.getURI(object), true)
		} catch (Exception e) {
			// If object cannot be resolved, it is null and will be correctly handled in the following
		}
		if (globallyResolvedObject !== null) {
			globalUuidResolver.registerEObject(uuid, globallyResolvedObject);	
		} else if (strictMode) {
			throw new IllegalStateException("Object has no UUID and is not globally accessible: " + object);
		} else {
			logger.warn("Object is not statically accessible but also has no globally mapped UUID: " + object);
		}
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
		createChange.affectedEObjectID = localUuidGeneratorAndResolver.getOrRegisterUuid(affectedObject);
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
