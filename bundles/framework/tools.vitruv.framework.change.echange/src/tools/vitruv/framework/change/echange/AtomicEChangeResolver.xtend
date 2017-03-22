package tools.vitruv.framework.change.echange

import org.eclipse.emf.common.util.EList
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.InternalEObject
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.util.EcoreUtil
import tools.vitruv.framework.change.echange.eobject.CreateEObject
import tools.vitruv.framework.change.echange.eobject.DeleteEObject
import tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange
import tools.vitruv.framework.change.echange.feature.FeatureEChange
import tools.vitruv.framework.change.echange.feature.reference.AdditiveReferenceEChange
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference
import tools.vitruv.framework.change.echange.feature.reference.SubtractiveReferenceEChange
import tools.vitruv.framework.change.echange.root.InsertRootEObject
import tools.vitruv.framework.change.echange.root.RemoveRootEObject
import tools.vitruv.framework.change.echange.root.RootEChange
import tools.vitruv.framework.change.echange.util.EChangeUtil
import tools.vitruv.framework.change.echange.util.StagingArea

/**
 * Static class for resolving EChanges internally.
 */
class AtomicEChangeResolver {
	/**
	 * 'Resolves' {@link EChange} attributes.
	 * @param change 			The change which should be resolved.
	 * @param resourceSet 		The resources set with the EObject which the 
	 * 							change should be resolved to.
	 * @param resolveBefore		{@code true} if the model is in state before the change,
	 * 							{@code false} if the model is in state after.
	 */
	def package static boolean resolveEChange(EChange change, ResourceSet resourceSet, boolean resolveBefore) {
		if (!change.isResolved) {
			if (resourceSet == null) {
				return false
			}
		}
		return true		
	}
	
	/**
	 * Resolves {@link FeatureEChange} attributes {@code affectedEObject} and {@code affectedFeature}.
	 * @param change 			The change which should be resolved.
	 * @param resourceSet 		The resources set with the EObject which the 
	 * 							change should be resolved to.
	 * @param resolveBefore		{@code true} if the model is in state before the change,
	 * 							{@code false} if the model is in state after.
	 */
	def private static <A extends EObject, F extends EStructuralFeature> boolean resolveFeatureEChange(FeatureEChange<A, F> change, ResourceSet resourceSet, boolean resolveBefore) {
		if (!change.isResolved) {	
			if (change.affectedEObject == null || !resolveEChange(change, resourceSet, true)) {
				return false
			}
				
			change.affectedEObject = EChangeUtil.resolveProxy(change.affectedEObject, resourceSet) as A

			if (change.affectedFeature == null || change.affectedEObject == null 
				|| change.affectedEObject.eIsProxy) {
				return false
			}
		}
		return true		
	}
	
	
	/**
	 * Resolves the new value of the {@link AdditiveReferenceEChange}.
	 * @param 	The change which should be resolved.
	 * @param 	resourceSet The {@code ResourceSet} which contains the concrete EObjects the new value of
	 * 			the unresolved should be resolved to.
	 * @param 	resolveBefore {@code true} if the model is in state before the change.
	 * 			{@code false} if the model is in state after the change.
	 * @param	isList The new value will be part of a list after the change is applied forward.
	 * @param	index The index, if the new value will be part of a list after the change is applied forward.
	 * @return	The resolved EObject. If the value could not be resolved, the original new value.
	 */
	def private static <A extends EObject, T extends EObject> EObject resolveNewValue(AdditiveReferenceEChange<A, T> change, ResourceSet resourceSet, boolean resolveBefore, boolean isList, int index) {
		if (change.newValue == null) {
			return null
		}
		if (!change.containment) {
			// Non containment => New object is already in resource
			return EChangeUtil.resolveProxy(change.newValue, resourceSet)
		}
		if (resolveBefore) {
			// Before => New object is in staging area.
			val stagingArea = StagingArea.getStagingArea(resourceSet)
			return stagingArea.contents.get(0)			
		} else {
			// After => New object is in containment reference.
			if (isList) {
				var list = change.affectedEObject.eGet(change.affectedFeature) as EList<T>
				if (0 <= index && index < list.size) {
					return list.get(index)
				} else {
					return change.newValue // Return unresolved
				}
			} else {
				return change.affectedEObject.eGet(change.affectedFeature) as T
			}
		}
	}
	
	/**
	 * Resolves the old value of the {@link SubtractiveReferenceEChange}.
	 * @param	change The change which should be resolved.
	 * @param 	resourceSet The {@code ResourceSet} which contains the concrete EObjects the old value of
	 * 			the unresolved should be resolved to.
	 * @param 	resolveBefore {@code true} if the model is in state before the change.
	 * 			{@code false} if the model is in state after the change.
	 * @param	isList The old value is part of a list, before the change is applied forward.
	 * @param	index The index, if the old value is part of a list, before the change is applied forward.
	 * @return	The resolved EObject. If the value could not be resolved, the original old value.
	 */
	def private static <A extends EObject, T extends EObject> EObject resolveOldValue(SubtractiveReferenceEChange<A, T> change, ResourceSet resourceSet, boolean resolveBefore, boolean isList, int index) {
		if (change.oldValue == null) {
			return null
		}
		if (!change.containment) {
			// Non containment => Old object is already in resource
			return EChangeUtil.resolveProxy(change.oldValue, resourceSet)
		}				
		if (!resolveBefore) {
			// After => Old object is in staging area.
			val stagingArea = StagingArea.getStagingArea(resourceSet)
			return stagingArea.contents.get(0)			
		} else {
			// Before => Old object is in containment reference.
			if (isList) {
				var list = change.affectedEObject.eGet(change.affectedFeature) as EList<T>
				if (0 <= index && index < list.size) {
					return list.get(index)
				} else {
					return change.oldValue // Return unresolved	
				}
			} else {
				return change.affectedEObject.eGet(change.affectedFeature) as T
			}
		}
	}
	/**
	 * Resolves {@link EObjectExistenceEChange} attribute {@code affectedEObject}.
	 * @param change 			The change which should be resolved.
	 * @param resourceSet 		The resources set with the EObject which the 
	 * 							change should be resolved to.
	 * @param resolveBefore		{@code true} if the model is in state before the change,
	 * 							{@code false} if the model is in state after.
	 */
	def private static <A extends EObject> boolean resolveEObjectExistenceEChange(EObjectExistenceEChange<A> change, ResourceSet resourceSet, boolean newObject) {
		if (!change.isResolved) {
			if (change.affectedEObject == null || !change.resolveEChange(resourceSet, true)) {
				return false
			}
			// Get the staging area where the created object will placed in or deleted from.
			change.stagingArea = StagingArea.getStagingArea(resourceSet)

			// Resolve the affected object
			if (newObject) {
				// Create new one
				change.affectedEObject = EcoreUtil.copy(change.affectedEObject)
				(change.affectedEObject as InternalEObject).eSetProxyURI(null)		
			} else {
				// Object still exists
				change.affectedEObject = change.stagingArea.contents.get(0) as A
			}
			
			if (change.affectedEObject == null || change.affectedEObject.eIsProxy
				|| change.stagingArea == null) {
				return false
			}
		}
		return true		
	}
	
	/**
	 * Resolves {@link RootEChange} attribute {@code resource}.
	 * @param change 			The change which should be resolved.
	 * @param resourceSet 		The resources set with the EObject which the 
	 * 							change should be resolved to.
	 * @param resolveBefore		{@code true} if the model is in state before the change,
	 * 							{@code false} if the model is in state after.
	 */
	def private static boolean resolveRootEChange(RootEChange change, ResourceSet resourceSet, boolean resolveBefore) {
		if (!change.isResolved) {
			if (!resolveEChange(change, resourceSet, resolveBefore)) {
				return false
			}
			// Get resource where the root object will be inserted / removed.
			change.resource = resourceSet.getResource(URI.createURI(change.uri), false)
				
			if (change.resource == null) {
				return false
			}
		}
		return true
	}
	
	/**
	 * Dispatch method for resolving the {@link EChange}.
	 * @param change 			The change which should be resolved.
	 * @param resourceSet 		The resources set with the EObject which the 
	 * 							change should be resolved to.
	 * @param resolveBefore		{@code true} if the model is in state before the change,
	 * 							{@code false} if the model is in state after.
	 */
	def static dispatch boolean resolve(EChange change, ResourceSet resourceSet, boolean resolveBefore) {
		return false
	}	
	
	/**
	 * Dispatch method for resolving the {@link FeatureEChange} EChange.
	 * @param change 			The change which should be resolved.
	 * @param resourceSet 		The resources set with the EObject which the 
	 * 							change should be resolved to.
	 * @param resolveBefore		{@code true} if the model is in state before the change,
	 * 							{@code false} if the model is in state after.
	 */
	def static dispatch boolean resolve(FeatureEChange<EObject, EStructuralFeature> change, ResourceSet resourceSet, boolean resolveBefore) {
		return change.resolveFeatureEChange(resourceSet, resolveBefore)
	}	

	/**
	 * Dispatch method for resolving the {@link InsertEReference} EChange.
	 * @param change 			The change which should be resolved.
	 * @param resourceSet 		The resources set with the EObject which the 
	 * 							change should be resolved to.
	 * @param resolveBefore		{@code true} if the model is in state before the change,
	 * 							{@code false} if the model is in state after.
	 */	
	def static dispatch boolean resolve(InsertEReference<EObject, EObject> change, ResourceSet resourceSet, boolean resolveBefore) {
		if (!change.isResolved) {	
			if (!change.resolveFeatureEChange(resourceSet, resolveBefore)) {
				return false
			}
			change.newValue = change.resolveNewValue(resourceSet, resolveBefore, true, change.index)
			
			if (change.newValue != null && (change.newValue as EObject).eIsProxy) {
				return false
			} 
		} 
		return true		
	}

	/**
	 * Dispatch method for resolving the {@link RemoveEReference} EChange.
	 * @param change 			The change which should be resolved.
	 * @param resourceSet 		The resources set with the EObject which the 
	 * 							change should be resolved to.
	 * @param resolveBefore		{@code true} if the model is in state before the change,
	 * 							{@code false} if the model is in state after.
	 */	
	def static dispatch boolean resolve(RemoveEReference<EObject, EObject> change, ResourceSet resourceSet, boolean resolveBefore) {
		if (!change.isResolved) {
			if (!change.resolveFeatureEChange(resourceSet, resolveBefore)) {
				return false
			}
			change.oldValue = change.resolveOldValue(resourceSet, resolveBefore, true, change.index)
			
			if (change.oldValue != null && (change.oldValue as EObject).eIsProxy) {
				return false
			}
		} 
		return true		
	}

	/**
	 * Dispatch method for resolving the {@link ReplaceSingleValuedEReferenceEReference} EChange.
	 * @param change 			The change which should be resolved.
	 * @param resourceSet 		The resources set with the EObject which the 
	 * 							change should be resolved to.
	 * @param resolveBefore		{@code true} if the model is in state before the change,
	 * 							{@code false} if the model is in state after.
	 */	
	def static dispatch boolean resolve(ReplaceSingleValuedEReference<EObject, EObject> change, ResourceSet resourceSet, boolean resolveBefore) {
		if (!change.isResolved) {	
			if (!change.resolveFeatureEChange(resourceSet, resolveBefore)) {
				return false
			}	
			
			change.newValue = change.resolveNewValue(resourceSet, resolveBefore, false, -1)
			change.oldValue = change.resolveOldValue(resourceSet, resolveBefore, false, -1)			

			if ((change.newValue != null && (change.newValue as EObject).eIsProxy)
				|| (change.oldValue != null && (change.oldValue as EObject).eIsProxy)) {
				return false
			}
		} 
		return true		
	}
	
	/**
	 * Dispatch method for resolving the {@link InsertRootEObject} EChange.
	 * @param change 			The change which should be resolved.
	 * @param resourceSet 		The resources set with the EObject which the 
	 * 							change should be resolved to.
	 * @param resolveBefore		{@code true} if the model is in state before the change,
	 * 							{@code false} if the model is in state after.
	 */		
	def static dispatch boolean resolve(InsertRootEObject<EObject> change, ResourceSet resourceSet, boolean resolveBefore) {
		if (!change.isResolved) {
			if (!change.resolveRootEChange(resourceSet, resolveBefore)) {
				return false
			}
			
			// Resolve the root object
			if (resolveBefore) {
				// Root was created and reference is stored in staging area.
				var stagingArea = StagingArea.getStagingArea(resourceSet)
				if (!stagingArea.contents.empty) {
					change.newValue = stagingArea.contents.get(0)
				} else {
					return false	
				}
			} else {
				// Root object was already inserted
				change.newValue = change.resource.contents.get(change.index)
			}
	
			if (change.newValue == null || change.newValue.eIsProxy) {
				return false
			}
		}
		return true		
	}

	/**
	 * Dispatch method for resolving the {@link RemoveRootEObject} EChange.
	 * @param change 			The change which should be resolved.
	 * @param resourceSet 		The resources set with the EObject which the 
	 * 							change should be resolved to.
	 * @param resolveBefore		{@code true} if the model is in state before the change,
	 * 							{@code false} if the model is in state after.
	 */		
	def static dispatch boolean resolve(RemoveRootEObject<EObject> change, ResourceSet resourceSet, boolean resolveBefore) {
		if (!change.isResolved) {
			if (!change.resolveRootEChange(resourceSet, resolveBefore)) {
				return false
			}
			// Resolve the root object
			if (resolveBefore) {
				// Root object is still in resource (=> change can be applied)
				change.oldValue = EChangeUtil.resolveProxy(change.oldValue, resourceSet)
			} else  {
				// Root object was removed and is in the staging area (=> change can be reverted)
				var stagingArea = StagingArea.getStagingArea(resourceSet)
				if (!stagingArea.contents.empty) {
					change.oldValue = stagingArea.contents.get(0)
				} else {
					return false	
				}
			}
					
			if (change.oldValue == null || change.oldValue.eIsProxy) {
				return false
			}
		}
		return true
	}

	/**
	 * Dispatch method for resolving the {@link CreateEObject} EChange.
	 * @param change 			The change which should be resolved.
	 * @param resourceSet 		The resources set with the EObject which the 
	 * 							change should be resolved to.
	 * @param resolveBefore		{@code true} if the model is in state before the change,
	 * 							{@code false} if the model is in state after.
	 */		
	def static dispatch boolean resolve(CreateEObject<EObject> change, ResourceSet resourceSet, boolean resolveBefore) {
		return change.resolveEObjectExistenceEChange(resourceSet, resolveBefore)
	}	

	/**
	 * Dispatch method for resolving the {@link DeleteEObject} EChange.
	 * @param change 			The change which should be resolved.
	 * @param resourceSet 		The resources set with the EObject which the 
	 * 							change should be resolved to.
	 * @param resolveBefore		{@code true} if the model is in state before the change,
	 * 							{@code false} if the model is in state after.
	 */		
	def static dispatch boolean resolve(DeleteEObject<EObject> change, ResourceSet resourceSet, boolean resolveBefore) {
		return change.resolveEObjectExistenceEChange(resourceSet, !resolveBefore)
	}	


}