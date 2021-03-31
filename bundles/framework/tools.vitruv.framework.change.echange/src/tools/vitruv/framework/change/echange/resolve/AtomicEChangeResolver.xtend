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
import tools.vitruv.framework.change.echange.feature.reference.UpdateReferenceEChange
import tools.vitruv.framework.change.echange.root.InsertRootEObject
import tools.vitruv.framework.change.echange.root.RemoveRootEObject
import tools.vitruv.framework.change.echange.root.RootEChange
import java.util.List
import tools.vitruv.framework.uuid.UuidResolver
import static com.google.common.base.Preconditions.checkState
import static com.google.common.base.Preconditions.checkArgument

/**
 * Static class for resolving EChanges internally.
 */
package class AtomicEChangeResolver {
	val UuidResolver uuidResolver
	
	new (UuidResolver uuidResolver) {
		checkArgument(uuidResolver !== null, "uuid resolver must not be null")
		this.uuidResolver = uuidResolver
	}
	
	/**
	 * Resolves {@link FeatureEChange} attributes {@code affectedEObject} and {@code affectedFeature}.
	 * @param change 		The change which should be resolved.
	 * @param resolveBefore	{@code true} if the model is in state before the change,
	 * 						{@code false} if the model is in state after.
	 */
	def private <A extends EObject, F extends EStructuralFeature> void resolveFeatureEChange(FeatureEChange<A, F> change, boolean resolveBefore) {
		checkArgument(change.affectedEObjectID !== null, "change %s must have an affected EObject ID", change)
		checkArgument(change.affectedFeature !== null, "change %s must have an affected feature", change)
		if (uuidResolver.hasEObject(change.affectedEObjectID)) {
			change.affectedEObject = uuidResolver.getEObject(change.affectedEObjectID) as A		
		}
		change.affectedEObject.checkNotNullAndNotProxy(change, "affected object")
	}

	/**
	 * Resolves the value of the Reference EChange.
	 * @param change		The Reference EChange which contains the affected EObject and affected feature
	 * @param value			The value which shall be resolved.
	 * @param isInserted	{@code true} if the value is already inserted in the single / multi valued reference.
	 * 						Depends on the concrete change and the state of the model.
	 * @param index 		The index where the value is inserted in / removed from, if the feature is a multi valued reference.
	 * 						Doesn't affect single valued references.
	 * @return				The resolved EObject. If the value could not be resolved, the original value.
	 */
	def private <A extends EObject, T extends EObject> EObject resolveReferenceValue(UpdateReferenceEChange<A> change, T value, String valueId,
		boolean isInserted, int index) {
		if (valueId === null) {
			return null;
		}
		return uuidResolver.getEObject(valueId)
	}

	/**
	 * Resolves {@link EObjectExistenceEChange} attribute {@code affectedEObject}.
	 * @param change 			The change which should be resolved.
	 * @param resolveBefore		{@code true} if the model is in state before the change,
	 * 							{@code false} if the model is in state after.
	 */
	def private <A extends EObject> void resolveEObjectExistenceEChange(EObjectExistenceEChange<A> change, boolean isNewObject) {
		checkArgument(change.affectedEObjectID !== null, "change %s must have an affected EObject ID", change)

		// Resolve the affected object
		if (isNewObject) {
			// Check if UUID resolver may still contain the removed object
			if (uuidResolver.hasEObject(change.affectedEObjectID)) {
				val stillExistingObject = uuidResolver.getEObject(change.affectedEObjectID) as A
				checkState(!stillExistingObject.eIsProxy, "object in UUID resolver is a proxy")
				change.affectedEObject = stillExistingObject
			} else {
				// Create new one
				val newObject = EcoreUtil.create(change.affectedEObjectType) as A
				change.affectedEObject = newObject
				uuidResolver.registerEObject(change.affectedEObjectID, newObject)
			}
		} else {
			// Object still exists
			change.affectedEObject = uuidResolver.getEObject(change.affectedEObjectID) as A
			change.affectedEObject.checkNotNullAndNotProxy(change, "affected object")
		}
		
		if (change.idAttributeValue !== null) {
			EcoreUtil.setID(change.affectedEObject, change.idAttributeValue)
		}
	}

	/**
	 * Resolves {@link RootEChange} attribute {@code resource}.
	 * @param change 			The change which should be resolved.
	 * @param resolveBefore		{@code true} if the model is in state before the change,
	 * 							{@code false} if the model is in state after.
	 */
	def private void resolveRootEChange(RootEChange change, boolean resolveBefore) {
		// Get resource where the root object will be inserted / removed.
		change.resource = uuidResolver.getResource(URI.createURI(change.uri))
	}

	/**
	 * Resolves the value of an {@link RootEChange}.
	 * @param change		The change whose value shall be resolved.
	 * @param value			The value which shall be resolved.
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
				return uuidResolver.getEObject(change.newValueID)	
			} else if (change instanceof RemoveRootEObject<?>) {
				return uuidResolver.getEObject(change.oldValueID)	
			}
		}
		return value		
	}
	
	/**
	 * Dispatch method for resolving the {@link EChange}.
	 * @param change 			The change which should be resolved.
	 * @param resolveBefore		{@code true} if the model is in state before the change,
	 * 							{@code false} if the model is in state after.
	 */
	def package dispatch void resolve(EChange change, boolean resolveBefore) {
		// If an EChange reaches this point, there is a dispatch method missing for the concrete type.
		throw new UnsupportedOperationException("change of type " + change?.eClass + " is not supported")
	}

	/**
	 * Dispatch method for resolving the {@link FeatureEChange} EChange.
	 * @param change 			The change which should be resolved.
	 * @param resolveBefore		{@code true} if the model is in state before the change,
	 * 							{@code false} if the model is in state after.
	 */
	def package dispatch void resolve(FeatureEChange<EObject, EStructuralFeature> change, boolean resolveBefore) {
		change.resolveFeatureEChange(resolveBefore)
	}

	/**
	 * Dispatch method for resolving the {@link InsertEReference} EChange.
	 * @param change 			The change which should be resolved.
	 * @param resolveBefore		{@code true} if the model is in state before the change,
	 * 							{@code false} if the model is in state after.
	 */
	def package dispatch void resolve(InsertEReference<EObject, EObject> change, boolean resolveBefore) {
		change.resolveFeatureEChange(resolveBefore)
		change.newValue = change.resolveReferenceValue(change.newValue, change.newValueID, !resolveBefore, change.index)
		change.newValue.checkNotNullAndNotProxy(change, "new value")
	}

	/**
	 * Dispatch method for resolving the {@link RemoveEReference} EChange.
	 * @param change 			The change which should be resolved.
	 * @param resolveBefore		{@code true} if the model is in state before the change,
	 * 							{@code false} if the model is in state after.
	 */
	def package dispatch void resolve(RemoveEReference<EObject, EObject> change, boolean resolveBefore) {
		change.resolveFeatureEChange(resolveBefore)		
		change.oldValue = change.resolveReferenceValue(change.oldValue, change.oldValueID, resolveBefore, change.index)
		change.oldValue.checkNotNullAndNotProxy(change, "old value")
	}

	/**
	 * Dispatch method for resolving the {@link ReplaceSingleValuedEReferenceEReference} EChange.
	 * @param change 			The change which should be resolved.
	 * @param resolveBefore		{@code true} if the model is in state before the change,
	 * 							{@code false} if the model is in state after.
	 */
	def package dispatch void resolve(ReplaceSingleValuedEReference<EObject, EObject> change, boolean resolveBefore) {
		change.resolveFeatureEChange(resolveBefore)
		change.newValue = change.resolveReferenceValue(change.newValue, change.newValueID, !resolveBefore, -1)
		change.oldValue = change.resolveReferenceValue(change.oldValue, change.oldValueID, resolveBefore, -1)
		change.oldValue.checkEitherNullOrNotProxy(change, "old value")
		change.newValue.checkEitherNullOrNotProxy(change, "new value")
	}

	/**
	 * Dispatch method for resolving the {@link InsertRootEObject} EChange.
	 * @param change 			The change which should be resolved.
	 * @param resolveBefore		{@code true} if the model is in state before the change,
	 * 							{@code false} if the model is in state after.
	 */
	def package dispatch void resolve(InsertRootEObject<EObject> change, boolean resolveBefore) {
		change.resolveRootEChange(resolveBefore)
		change.newValue = change.resolveRootValue(change.newValue, !resolveBefore)
		change.newValue.checkNotNullAndNotProxy(change, "new value")
	}

	/**
	 * Dispatch method for resolving the {@link RemoveRootEObject} EChange.
	 * @param change 			The change which should be resolved.
	 * @param resolveBefore		{@code true} if the model is in state before the change,
	 * 							{@code false} if the model is in state after.
	 */
	def package dispatch void resolve(RemoveRootEObject<EObject> change, boolean resolveBefore) {
		change.resolveRootEChange(resolveBefore)
		change.oldValue = change.resolveRootValue(change.oldValue, resolveBefore)
		change.oldValue.checkNotNullAndNotProxy(change, "old value")
	}
	
	/**
	 * Dispatch method for resolving the {@link CreateEObject} EChange.
	 * @param change 			The change which should be resolved.
	 * @param resolveBefore		{@code true} if the model is in state before the change,
	 * 							{@code false} if the model is in state after.
	 */
	def package dispatch void resolve(CreateEObject<EObject> change, boolean resolveBefore) {
		change.resolveEObjectExistenceEChange(resolveBefore)
	}

	/**
	 * Dispatch method for resolving the {@link DeleteEObject} EChange.
	 * @param change 			The change which should be resolved.
	 * @param resolveBefore		{@code true} if the model is in state before the change,
	 * 							{@code false} if the model is in state after.
	 */
	def package dispatch void resolve(DeleteEObject<EObject> change,
		boolean resolveBefore) {
		val consequentialChanges = change.consequentialRemoveChanges
		if (resolveBefore) {
			consequentialChanges.resolveChangeList(resolveBefore);	
		}
		change.resolveEObjectExistenceEChange(!resolveBefore)
		if (!resolveBefore) {
			consequentialChanges.reverseView.resolveChangeList(resolveBefore);	
		}
	}
	
	/**
	 * Dispatch method for resolving the given list of changes.
	 * @param changeList 		The change list which should be resolved.
	 * @param resolveBefore		{@code true} if the model is in state before the change,
	 * 							{@code false} if the model is in state after.
	 */
	def private void resolveChangeList(List<? extends EChange> changeList, boolean resolveBefore) {
		changeList.forEach[resolve(resolveBefore)]
	}
	
	def private static void checkNotNullAndNotProxy(EObject object, EChange change, String nameOfElementInChange) {
		checkState(object !== null, "%s of change %s was resolved to null", change)
		checkState(!object.eIsProxy, "%s of change %s was resolved to a proxy", object)
	}
	
	def private static void checkEitherNullOrNotProxy(EObject object, EChange change, String nameOfElementInChange) {
		checkState(object === null || !object.eIsProxy, "%s of change %s was resolved to a proxy", object)
	}
}
	