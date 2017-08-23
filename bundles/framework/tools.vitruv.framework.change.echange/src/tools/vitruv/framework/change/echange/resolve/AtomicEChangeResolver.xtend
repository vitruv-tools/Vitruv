package tools.vitruv.framework.change.echange.resolve

import org.eclipse.emf.common.util.EList
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
import tools.vitruv.framework.change.uuid.UuidResolver

/**
 * Static class for resolving EChanges internally.
 */
class AtomicEChangeResolver {
	
	/**
	 * Resolves {@link EChange} attributes.
	 * @param change 		The change which should be resolved.
	 * @param uuidResolver 	The {@link UuidResolver} to resolve {@link EObject}s from
	 * @param resolveBefore	{@code true} if the model is in state before the change,
	 * 						{@code false} if the model is in state after.
	 */
	def package static boolean resolveEChange(EChange change, UuidResolver uuidResolver, boolean resolveBefore) {
		if (uuidResolver === null) {
			return false
		}
		return true
	}

	/**
	 * Resolves {@link FeatureEChange} attributes {@code affectedEObject} and {@code affectedFeature}.
	 * @param change 		The change which should be resolved.
	 * @param uuidResolver 	The {@link UuidResolver} to resolve {@link EObject}s from
	 * @param resolveBefore	{@code true} if the model is in state before the change,
	 * 						{@code false} if the model is in state after.
	 */
	def private static <A extends EObject, F extends EStructuralFeature> boolean resolveFeatureEChange(
		FeatureEChange<A, F> change, UuidResolver uuidResolver, boolean resolveBefore) {
		if (change.affectedEObjectID === null || !resolveEChange(change, uuidResolver, true)) {
			return false
		}

		change.affectedEObject = uuidResolver.getEObject(change.affectedEObjectID) as A

		if (change.affectedFeature === null || change.affectedEObject === null || change.affectedEObject.eIsProxy) {
			return false
		}
		return true
	}

	/**
	 * Resolves the value of the Reference EChange.
	 * @param change		The Reference EChange which contains the affected EObject and affected feature
	 * @param value			The value which shall be resolved.
	 * @param uuidResolver 	The {@link UuidResolver} to resolve {@link EObject}s from
	 * @param isInserted	{@code true} if the value is already inserted in the single / multi valued reference.
	 * 						Depends on the concrete change and the state of the model.
	 * @param index 		The index where the value is inserted in / removed from, if the feature is a multi valued reference.
	 * 						Doesn't affect single valued references.
	 * @return				The resolved EObject. If the value could not be resolved, the original value.
	 */
	def private static <A extends EObject, T extends EObject> EObject resolveReferenceValue(UpdateReferenceEChange<A> change, T value, String valueId,
		UuidResolver uuidResolver, boolean isInserted, int index) {
		if (valueId === null) {
			return null;
		}
		if (!change.affectedFeature.containment) {
			// Non containment => New object is already in resource
			return uuidResolver.getEObject(valueId)
		}
		if (!isInserted) {
			// Before => New object is in staging area.
			return uuidResolver.getEObject(valueId);
		} else {
			// TODO HK Replace with UUID resolution!?
			// After => New object is in containment reference.
			if (change.affectedFeature.many) {
				var list = change.affectedEObject.eGet(change.affectedFeature) as EList<T>
				if (0 <= index && index < list.size) {
					return list.get(index)
				} else {
					return value // Return unresolved
				}
			} else {
				return change.affectedEObject.eGet(change.affectedFeature) as T
			}			
		}
	}

	/**
	 * Resolves {@link EObjectExistenceEChange} attribute {@code affectedEObject}.
	 * @param change 			The change which should be resolved.
	 * @param uuidResolver 		The {@link UuidResolver} to resolve {@link EObject}s from
	 * @param resolveBefore		{@code true} if the model is in state before the change,
	 * 							{@code false} if the model is in state after.
	 */
	def private static <A extends EObject> boolean resolveEObjectExistenceEChange(EObjectExistenceEChange<A> change,
		UuidResolver uuidResolver, boolean newObject) {
		if (change.affectedEObjectID === null || !change.resolveEChange(uuidResolver, true)) {
			return false
		}

		// Resolve the affected object
		if (newObject) {
			// Create new one
			change.affectedEObject = EcoreUtil.create(change.affectedEObjectType) as A;
		} else {
			// Object still exists
			change.affectedEObject = uuidResolver.getEObject(change.affectedEObjectID) as A;
		}
		
		uuidResolver.registerEObject(change.affectedEObjectID, change.affectedEObject);
		
		if (change.affectedEObject === null || change.affectedEObject.eIsProxy) {
			return false
		}
		
		if (change.idAttributeValue !== null) {
			EcoreUtil.setID(change.affectedEObject, change.idAttributeValue);
		}
		
		return true
	}

	/**
	 * Resolves {@link RootEChange} attribute {@code resource}.
	 * @param change 			The change which should be resolved.
	 * @param uuidResolver 		The {@link UuidResolver} to resolve {@link EObject}s from
	 * @param resolveBefore		{@code true} if the model is in state before the change,
	 * 							{@code false} if the model is in state after.
	 */
	def private static boolean resolveRootEChange(RootEChange change, UuidResolver uuidResolver,
		boolean resolveBefore) {
		if (!resolveEChange(change, uuidResolver, resolveBefore)) {
			return false
		}
		// Get resource where the root object will be inserted / removed.
		change.resource = uuidResolver.resourceSet.getResource(URI.createURI(change.uri), false)

		if (change.resource === null) {
			return false
		}
		return true
	}

	/**
	 * Resolves the value of an {@link RootEChange}.
	 * @param change		The change whose value shall be resolved.
	 * @param value			The value which shall be resolved.
	 * @param uuidResolver 	The {@link UuidResolver} to resolve {@link EObject}s from
	 * @param isInserted	{@code true} if the concrete value is already inserted into the resource.
	 * 						Depends on the kind of the change and the model state.
	 * @returns				The resolved value.
	 */
	def private static <T extends EObject> resolveRootValue(RootEChange change, T value, UuidResolver uuidResolver, boolean isInserted) {
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
	 * @param uuidResolver 		The {@link UuidResolver} to resolve {@link EObject}s from
	 * @param resolveBefore		{@code true} if the model is in state before the change,
	 * 							{@code false} if the model is in state after.
	 */
	def package static dispatch boolean resolve(EChange change, UuidResolver uuidResolver, boolean resolveBefore) {
		// If an EChange reaches this point, there is a dispatch method missing for the concrete type.
		throw new UnsupportedOperationException
	}

	/**
	 * Dispatch method for resolving the {@link FeatureEChange} EChange.
	 * @param change 			The change which should be resolved.
	 * @param uuidResolver 		The {@link UuidResolver} to resolve {@link EObject}s from
	 * @param resolveBefore		{@code true} if the model is in state before the change,
	 * 							{@code false} if the model is in state after.
	 */
	def package static dispatch boolean resolve(FeatureEChange<EObject, EStructuralFeature> change,
		UuidResolver uuidResolver, boolean resolveBefore) {
		return change.resolveFeatureEChange(uuidResolver, resolveBefore)
	}

	/**
	 * Dispatch method for resolving the {@link InsertEReference} EChange.
	 * @param change 			The change which should be resolved.
	 * @param uuidResolver 		The {@link UuidResolver} to resolve {@link EObject}s from
	 * @param resolveBefore		{@code true} if the model is in state before the change,
	 * 							{@code false} if the model is in state after.
	 */
	def package static dispatch boolean resolve(InsertEReference<EObject, EObject> change, UuidResolver uuidResolver,
		boolean resolveBefore) {
		if (!change.resolveFeatureEChange(uuidResolver, resolveBefore)) {
			return false
		}
		
		change.newValue = change.resolveReferenceValue(change.newValue, change.newValueID, uuidResolver, !resolveBefore, change.index)

		if (change.newValue !== null && change.newValue.eIsProxy) {
			return false
		}
		return true
	}

	/**
	 * Dispatch method for resolving the {@link RemoveEReference} EChange.
	 * @param change 			The change which should be resolved.
	 * @param uuidResolver 		The {@link UuidResolver} to resolve {@link EObject}s from
	 * @param resolveBefore		{@code true} if the model is in state before the change,
	 * 							{@code false} if the model is in state after.
	 */
	def package static dispatch boolean resolve(RemoveEReference<EObject, EObject> change, UuidResolver uuidResolver,
		boolean resolveBefore) {
		if (!change.resolveFeatureEChange(uuidResolver, resolveBefore)) {
			return false
		}
		
		change.oldValue = change.resolveReferenceValue(change.oldValue, change.oldValueID, uuidResolver, resolveBefore, change.index)

		if (change.oldValue !== null && change.oldValue.eIsProxy) {
			return false
		}
		return true
	}

	/**
	 * Dispatch method for resolving the {@link ReplaceSingleValuedEReferenceEReference} EChange.
	 * @param change 			The change which should be resolved.
	 * @param uuidResolver 		The {@link UuidResolver} to resolve {@link EObject}s from
	 * @param resolveBefore		{@code true} if the model is in state before the change,
	 * 							{@code false} if the model is in state after.
	 */
	def package static dispatch boolean resolve(ReplaceSingleValuedEReference<EObject, EObject> change,
		UuidResolver uuidResolver, boolean resolveBefore) {
		if (!change.resolveFeatureEChange(uuidResolver, resolveBefore)) {
			return false
		}

		change.newValue = change.resolveReferenceValue(change.newValue, change.newValueID, uuidResolver, !resolveBefore, -1)
		change.oldValue = change.resolveReferenceValue(change.oldValue, change.oldValueID, uuidResolver, resolveBefore, -1)

		if ((change.newValue !== null && change.newValue.eIsProxy) ||
			(change.oldValue !== null && change.oldValue.eIsProxy)) {
			return false
		}
		return true
	}

	/**
	 * Dispatch method for resolving the {@link InsertRootEObject} EChange.
	 * @param change 			The change which should be resolved.
	 * @param uuidResolver 		The {@link UuidResolver} to resolve {@link EObject}s from
	 * @param resolveBefore		{@code true} if the model is in state before the change,
	 * 							{@code false} if the model is in state after.
	 */
	def package static dispatch boolean resolve(InsertRootEObject<EObject> change, UuidResolver uuidResolver,
		boolean resolveBefore) {
		if (!change.resolveRootEChange(uuidResolver, resolveBefore)) {
			return false
		}

		change.newValue = change.resolveRootValue(change.newValue, uuidResolver, !resolveBefore)

		if (change.newValue === null || change.newValue.eIsProxy) {
			return false
		}
		return true
	}

	/**
	 * Dispatch method for resolving the {@link RemoveRootEObject} EChange.
	 * @param change 			The change which should be resolved.
	 * @param uuidResolver 		The {@link UuidResolver} to resolve {@link EObject}s from
	 * @param resolveBefore		{@code true} if the model is in state before the change,
	 * 							{@code false} if the model is in state after.
	 */
	def package static dispatch boolean resolve(RemoveRootEObject<EObject> change, UuidResolver uuidResolver,
		boolean resolveBefore) {
		if (!change.resolveRootEChange(uuidResolver, resolveBefore)) {
			return false
		}

		change.oldValue = change.resolveRootValue(change.oldValue, uuidResolver, resolveBefore)
		
		if (change.oldValue === null || change.oldValue.eIsProxy) {
			return false
		}
		return true
	}
	
	/**
	 * Dispatch method for resolving the {@link CreateEObject} EChange.
	 * @param change 			The change which should be resolved.
	 * @param uuidResolver 		The {@link UuidResolver} to resolve {@link EObject}s from
	 * @param resolveBefore		{@code true} if the model is in state before the change,
	 * 							{@code false} if the model is in state after.
	 */
	def package static dispatch boolean resolve(CreateEObject<EObject> change, UuidResolver uuidResolver,
		boolean resolveBefore) {
		return change.resolveEObjectExistenceEChange(uuidResolver, resolveBefore)
	}

	/**
	 * Dispatch method for resolving the {@link DeleteEObject} EChange.
	 * @param change 			The change which should be resolved.
	 * @param uuidResolver 		The {@link UuidResolver} to resolve {@link EObject}s from
	 * @param resolveBefore		{@code true} if the model is in state before the change,
	 * 							{@code false} if the model is in state after.
	 */
	def package static dispatch boolean resolve(DeleteEObject<EObject> change, UuidResolver uuidResolver,
		boolean resolveBefore) {
		var result = true;
		val consequentialChanges = change.consequentialRemoveChanges
		if (resolveBefore) {
			result = consequentialChanges.resolveChangeList(uuidResolver, resolveBefore);	
		}
		result = result && change.resolveEObjectExistenceEChange(uuidResolver, !resolveBefore)
		if (!resolveBefore) {
			result = consequentialChanges.resolveChangeList(uuidResolver, resolveBefore);	
		}
		return result;
	}
	
	/**
	 * Dispatch method for resolving the given list of changes.
	 * @param changeList 		The change list which should be resolved.
	 * @param uuidResolver 		The {@link UuidResolver} to resolve {@link EObject}s from
	 * @param resolveBefore		{@code true} if the model is in state before the change,
	 * 							{@code false} if the model is in state after.
	 */
	def private static boolean resolveChangeList(List<EChange> changeList, UuidResolver uuidResolver, boolean resolveBefore) {
		return changeList.fold(true, [res, localChange | res && EChangeResolver.resolve(localChange, uuidResolver, resolveBefore, false)]);	
	}
}
	