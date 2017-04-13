package tools.vitruv.framework.change.echange.resolve

import java.util.List
import org.eclipse.emf.common.util.BasicEList
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.resource.ResourceSet
import tools.vitruv.framework.change.echange.AtomicEChange
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.compound.CompoundEChange
import tools.vitruv.framework.change.echange.compound.ExplicitUnsetEFeature
import tools.vitruv.framework.change.echange.util.ApplyEChangeSwitch
import tools.vitruv.framework.change.echange.util.EChangeUtil

class CompoundEChangeResolver {
	/**
	 * Resolving the {@link CompoundEChange} EChange.
	 * @param change 				The change which should be resolved.
	 * @param resourceSet 			The resources set with the EObject which the 
	 * 								change should be resolved to.
	 * @param resolveBefore			{@code true} if the model is in state before the change,
	 * 								{@code false} if the model is in state after.
	 * @param revertAfterResolving	{@code true} if the change should be reverted after resolving
	 * 								the compound change.
	 */	
	def private static boolean resolveCompoundEChange(CompoundEChange change, ResourceSet resourceSet, boolean resolveBefore, boolean revertAfterResolving) {
		if (!AtomicEChangeResolver.resolveEChange(change, resourceSet, resolveBefore) 
			|| !resolveAtomicChanges(change, resourceSet, resolveBefore, revertAfterResolving)) {
			return false
		}
		return true				
	}
	
	/**
	 * Resolves the atomic EChanges of a compound EChange. 
	 * The atomic EChanges will be applied while resolving.
	 * @param change 				The change which should be resolved.
	 * @param resourceSet 			The resources set with the EObject which the 
	 * 								change should be resolved to.
	 * @param resolveBefore			{@code true} if the model is in state before the change,
	 * 								{@code false} if the model is in state after.
	 * @param revertAfterResolving	{@code true} if the change should be reverted after resolving
	 * 								the atomic changes.
	 */
	def private static boolean resolveAtomicChanges(CompoundEChange change, ResourceSet resourceSet, boolean resolveBefore, boolean revertAfterResolving) {
		var List<AtomicEChange> atomicChanges = change.atomicChanges
		if (!resolveBefore) {
			atomicChanges.reverse
		}
		
		val appliedChanges = new BasicEList<EChange>		
		for (AtomicEChange c : atomicChanges) {
			if (!AtomicEChangeResolver.resolve(c, resourceSet, resolveBefore) 
				|| !ApplyEChangeSwitch.applyEChange(c, resolveBefore)) {
				// Error resolving or applying => revert all applied changes
				for (EChange changed : appliedChanges.reverseView) {
					ApplyEChangeSwitch.applyEChange(changed, !resolveBefore)
				}
				return false
			} else {
				appliedChanges.add(c)
			}
		}
		
		// Revert all changes which were made resolving the compound change.
		if (revertAfterResolving) {
			ApplyEChangeSwitch.applyEChange(change, !resolveBefore)
		}
		return true		
	}
	
	
	/**
	 * Dispatch method for resolving the {@link CompoundEChange} EChange.
	 * @param change 				The change which should be resolved.
	 * @param resourceSet 			The resources set with the EObject which the 
	 * 								change should be resolved to.
	 * @param resolveBefore			{@code true} if the model is in state before the change,
	 * 								{@code false} if the model is in state after.
	 * @param revertAfterResolving	{@code true} if the change should be reverted after resolving
	 * 								the compound change.
	 */		
	def package static dispatch boolean resolve(CompoundEChange change, ResourceSet resourceSet, boolean resolveBefore, boolean revertAfterResolving) {
		return resolveCompoundEChange(change, resourceSet, resolveBefore, revertAfterResolving)
	}	
	

	
	/**
	 * Dispatch method for resolving the {@link CompoundEChange} EChange.
	 * @param change 				The change which should be resolved.
	 * @param resourceSet 			The resources set with the EObject which the 
	 * 								change should be resolved to.
	 * @param resolveBefore			{@code true} if the model is in state before the change,
	 * 								{@code false} if the model is in state after.
	 * @param revertAfterResolving	{@code true} if the change should be reverted after resolving
	 * 								the compound change.
	 */	
	def package static dispatch boolean resolve(ExplicitUnsetEFeature<EObject, EStructuralFeature> change, ResourceSet resourceSet, boolean resolveBefore, boolean revertAfterResolving) {
		if (change.affectedEObject == null || change.affectedFeature == null 
			|| !resolveCompoundEChange(change, resourceSet, resolveBefore, revertAfterResolving)) {
			return false
		}
			
		change.affectedEObject = EChangeUtil.resolveProxy(change.affectedEObject, resourceSet)

		if (change.affectedEObject == null || change.affectedEObject.eIsProxy) {
			return false
		}	
		
		// Unset change is special case of compound changes, because the application of the 
		// atomic changes (removing all elements) != application of the compound change (explicit unset command)
		// => Attribute needs to be unset additionally.
		if ((!revertAfterResolving && resolveBefore)
			|| (revertAfterResolving && !resolveBefore)) {
			change.applyForward // Calls single unset command
		}
		return true
	}
}