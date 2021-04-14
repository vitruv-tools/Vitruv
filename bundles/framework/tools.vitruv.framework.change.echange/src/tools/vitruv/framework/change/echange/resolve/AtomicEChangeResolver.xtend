package tools.vitruv.framework.change.echange.resolve

import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.util.EcoreUtil
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.eobject.CreateEObject
import tools.vitruv.framework.change.echange.eobject.DeleteEObject
import tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange
import tools.vitruv.framework.change.echange.feature.FeatureEChange
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference
import tools.vitruv.framework.change.echange.root.InsertRootEObject
import tools.vitruv.framework.change.echange.root.RemoveRootEObject
import tools.vitruv.framework.change.echange.root.RootEChange
import static com.google.common.base.Preconditions.checkState
import static com.google.common.base.Preconditions.checkArgument
import tools.vitruv.framework.change.id.IdResolver

/**
 * Static class for resolving EChanges internally.
 */
package class AtomicEChangeResolver {
	val IdResolver idResolver
	
	new (IdResolver idResolver) {
		checkArgument(idResolver !== null, "id resolver must not be null")
		this.idResolver = idResolver
	}
	
	/**
	 * Resolves {@link FeatureEChange} attributes {@code affectedEObject} and {@code affectedFeature}.
	 * @param change 		The change which should be resolved.
	 */
	def private <A extends EObject, F extends EStructuralFeature> void resolveFeatureEChange(FeatureEChange<A, F> change) {
		checkArgument(change.affectedEObjectID !== null, "change %s must have an affected EObject ID", change)
		checkArgument(change.affectedFeature !== null, "change %s must have an affected feature", change)
		if (idResolver.hasEObject(change.affectedEObjectID)) {
			change.affectedEObject = idResolver.getEObject(change.affectedEObjectID) as A		
		}
		change.affectedEObject.checkNotNullAndNotProxy(change, "affected object")
	}

	def private EObject resolveObject(String valueId) {
		if (valueId === null) {
			return null
		}
		return idResolver.getEObject(valueId)
	}

	/**
	 * Resolves {@link EObjectExistenceEChange} attribute {@code affectedEObject}.
	 * @param change 			The change which should be resolved.
	 */
	def private <A extends EObject> void resolveEObjectExistenceEChange(EObjectExistenceEChange<A> change, boolean isNewObject) {
		checkArgument(change.affectedEObjectID !== null, "change %s must have an affected EObject ID", change)

		// Resolve the affected object
		if (isNewObject) {
			// Check if ID resolver may still contain the removed object
			if (idResolver.hasEObject(change.affectedEObjectID)) {
				val stillExistingObject = idResolver.getEObject(change.affectedEObjectID) as A
				change.affectedEObject = stillExistingObject
				change.affectedEObject.checkNotNullAndNotProxy(change, "affected object")
			} else {
				// Create new one
				val newObject = EcoreUtil.create(change.affectedEObjectType) as A
				change.affectedEObject = newObject
				val id = idResolver.getAndUpdateId(newObject)
				checkState(id == change.affectedEObjectID, "generated ID %s does not match the original ID %s on element creation", id, change.affectedEObjectID)
			}
		} else {
			// Object still exists
			change.affectedEObject = idResolver.getEObject(change.affectedEObjectID) as A
			change.affectedEObject.checkNotNullAndNotProxy(change, "affected object")
		}
		
		if (change.idAttributeValue !== null) {
			EcoreUtil.setID(change.affectedEObject, change.idAttributeValue)
		}
	}

	/**
	 * Resolves {@link RootEChange} attribute {@code resource}.
	 * @param change 			The change which should be resolved.
	 */
	def private void resolveRootEChange(RootEChange change) {
		// Get resource where the root object will be inserted / removed.
		change.resource = idResolver.getResource(URI.createURI(change.uri))
	}

	/**
	 * Resolves the value of an {@link RootEChange}.
	 * @param change		The change whose value shall be resolved.
	 * @param isInserted	{@code true} if the concrete value is already inserted into the resource.
	 * 						Depends on the kind of the change and the model state.
	 * @returns				The resolved value.
	 */
	def private <T extends EObject> resolveRootValue(RootEChange change, T value, boolean isInserted) {
		// Resolve the root object
		if (isInserted) {
			// Root object is in resource
			if (0 <= change.index && change.index < change.resource.contents.size) {
				return change.resource.contents.get(change.index)				
			}
		} else {
			// Root object is in staging area
			if (change instanceof InsertRootEObject<?>) {
				return idResolver.getEObject(change.newValueID)	
			} else if (change instanceof RemoveRootEObject<?>) {
				return idResolver.getEObject(change.oldValueID)	
			}
		}
		return value		
	}
	
	/**
	 * Dispatch method for resolving the {@link EChange}.
	 * @param change 			The change which should be resolved.
	 */
	def package dispatch void resolve(EChange change) {
		// If an EChange reaches this point, there is a dispatch method missing for the concrete type.
		throw new UnsupportedOperationException("change of type " + change?.eClass + " is not supported")
	}

	/**
	 * Dispatch method for resolving the {@link FeatureEChange} EChange.
	 * @param change 			The change which should be resolved.
	 */
	def package dispatch void resolve(FeatureEChange<EObject, EStructuralFeature> change) {
		change.resolveFeatureEChange()
	}

	/**
	 * Dispatch method for resolving the {@link InsertEReference} EChange.
	 * @param change 			The change which should be resolved.
	 */
	def package dispatch void resolve(InsertEReference<EObject, EObject> change) {
		change.resolveFeatureEChange()
		change.newValue = change.newValueID.resolveObject()
		change.newValue.checkNotNullAndNotProxy(change, "new value")
	}

	/**
	 * Dispatch method for resolving the {@link RemoveEReference} EChange.
	 * @param change 			The change which should be resolved.
	 */
	def package dispatch void resolve(RemoveEReference<EObject, EObject> change) {
		change.resolveFeatureEChange()		
		change.oldValue = change.oldValueID.resolveObject()
		change.oldValue.checkNotNullAndNotProxy(change, "old value")
	}

	/**
	 * Dispatch method for resolving the {@link ReplaceSingleValuedEReferenceEReference} EChange.
	 * @param change 			The change which should be resolved.
	 */
	def package dispatch void resolve(ReplaceSingleValuedEReference<EObject, EObject> change) {
		change.resolveFeatureEChange()
		change.newValue = change.newValueID.resolveObject()
		change.oldValue = change.oldValueID.resolveObject()
		change.oldValue.checkEitherNullOrNotProxy(change, "old value")
		change.newValue.checkEitherNullOrNotProxy(change, "new value")
	}

	/**
	 * Dispatch method for resolving the {@link InsertRootEObject} EChange.
	 * @param change 			The change which should be resolved.
	 */
	def package dispatch void resolve(InsertRootEObject<EObject> change) {
		change.resolveRootEChange()
		change.newValue = change.resolveRootValue(change.newValue, false)
		change.newValue.checkNotNullAndNotProxy(change, "new value")
	}

	/**
	 * Dispatch method for resolving the {@link RemoveRootEObject} EChange.
	 * @param change 			The change which should be resolved.
	 */
	def package dispatch void resolve(RemoveRootEObject<EObject> change) {
		change.resolveRootEChange()
		change.oldValue = change.resolveRootValue(change.oldValue, true)
		change.oldValue.checkNotNullAndNotProxy(change, "old value")
	}
	
	/**
	 * Dispatch method for resolving the {@link CreateEObject} EChange.
	 * @param change 			The change which should be resolved.
	 */
	def package dispatch void resolve(CreateEObject<EObject> change) {
		change.resolveEObjectExistenceEChange(true)
	}

	/**
	 * Dispatch method for resolving the {@link DeleteEObject} EChange.
	 * @param change 			The change which should be resolved.
	 */
	def package dispatch void resolve(DeleteEObject<EObject> change) {
		change.resolveEObjectExistenceEChange(false)
	}
	
	def private static void checkNotNullAndNotProxy(EObject object, EChange change, String nameOfElementInChange) {
		checkState(object !== null, "%s of change %s was resolved to null", nameOfElementInChange, change)
		checkState(!object.eIsProxy, "%s of change %s was resolved to a proxy", nameOfElementInChange, object)
	}
	
	def private static void checkEitherNullOrNotProxy(EObject object, EChange change, String nameOfElementInChange) {
		checkState(object === null || !object.eIsProxy, "%s of change %s was resolved to a proxy", nameOfElementInChange, object)
	}
}
	