package tools.vitruv.framework.change.echange

import tools.vitruv.framework.change.echange.eobject.EObjectAddedEChange
import tools.vitruv.framework.change.echange.eobject.EObjectSubtractedEChange
import tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange
import tools.vitruv.framework.change.echange.feature.FeatureEChange
import tools.vitruv.framework.uuid.UuidGeneratorAndResolver
import org.eclipse.emf.ecore.EObject
import static com.google.common.base.Preconditions.checkArgument
import static com.google.common.base.Preconditions.checkState

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
			EObjectExistenceEChange<?>:
				setOrGenerateAffectedEObjectId(eChange)
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
		val uuid = uuidGeneratorAndResolver.getAndUpdateId(object)
		checkState(uuid !== null, "uuid must not be null")
		return uuid
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

	private def void setOrGenerateAffectedEObjectId(EObjectExistenceEChange<?> existenceChange) {
		val affectedEObject = existenceChange.affectedEObject
		checkArgument(affectedEObject !== null, "Existence change must have an affected EObject: %s", existenceChange)
		existenceChange.affectedEObjectID = affectedEObject.uuid	
	}

	private def void setOrGenerateAffectedEObjectId(FeatureEChange<?, ?> featureChange) {
		val affectedEObject = featureChange.affectedEObject
		checkArgument(affectedEObject !== null, "Feature change must have an affected EObject: %s", featureChange)
		featureChange.affectedEObjectID = affectedEObject.uuid
	}

}
