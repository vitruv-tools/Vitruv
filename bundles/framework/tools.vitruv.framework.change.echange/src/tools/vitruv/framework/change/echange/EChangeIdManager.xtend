package tools.vitruv.framework.change.echange

import tools.vitruv.framework.change.echange.eobject.EObjectAddedEChange
import tools.vitruv.framework.change.echange.eobject.EObjectSubtractedEChange
import tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange
import tools.vitruv.framework.change.echange.feature.FeatureEChange
import org.eclipse.emf.ecore.EObject
import static com.google.common.base.Preconditions.checkArgument
import static com.google.common.base.Preconditions.checkState
import tools.vitruv.framework.change.id.IdResolver

/**
 * Provides logic for initializing the IDs within changes. 
 */
class EChangeIdManager {
	val IdResolver idResolver

	/**
	 * Initializes the manager with a {@link IdResolver}.
	 * 
	 * @param idResolver -
	 * 		the {@link IdResolver} to use for ID management
	 */
	new(IdResolver idResolver) {
		checkArgument(idResolver !== null, "id resolver must not be null")
		this.idResolver = idResolver
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

	private def String getId(EObject object) {
		val id = idResolver.getAndUpdateId(object)
		checkState(id !== null, "id must not be null")
		return id
	}

	private def void setOrGenerateNewValueId(EObjectAddedEChange<?> addedEChange) {
		if (addedEChange.newValue === null) {
			return;
		}
		addedEChange.newValueID = addedEChange.newValue.id
	}

	private def void setOrGenerateOldValueId(EObjectSubtractedEChange<?> subtractedEChange) {
		if (subtractedEChange.oldValue === null) {
			return;
		}
		subtractedEChange.oldValueID = subtractedEChange.oldValue.id
	}

	private def void setOrGenerateAffectedEObjectId(EObjectExistenceEChange<?> existenceChange) {
		val affectedEObject = existenceChange.affectedEObject
		checkArgument(affectedEObject !== null, "existence change must have an affected EObject: %s", existenceChange)
		existenceChange.affectedEObjectID = affectedEObject.id	
	}

	private def void setOrGenerateAffectedEObjectId(FeatureEChange<?, ?> featureChange) {
		val affectedEObject = featureChange.affectedEObject
		checkArgument(affectedEObject !== null, "feature change must have an affected EObject: %s", featureChange)
		featureChange.affectedEObjectID = affectedEObject.id
	}

}
