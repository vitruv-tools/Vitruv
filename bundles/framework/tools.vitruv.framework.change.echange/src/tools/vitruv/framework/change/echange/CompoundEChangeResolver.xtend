package tools.vitruv.framework.change.echange

import org.eclipse.emf.common.util.BasicEList
import org.eclipse.emf.ecore.resource.ResourceSet
import tools.vitruv.framework.change.echange.compound.CompoundEChange
import tools.vitruv.framework.change.echange.compound.ExplicitUnsetEFeature
import tools.vitruv.framework.change.echange.util.EChangeUtil

class CompoundEChangeResolver {
	def private static boolean resolveCompoundChange(CompoundEChange change, ResourceSet resourceSet, boolean resolveBefore, boolean revertAfterResolving) {
		if (!change.isResolved) {
			if (!AtomicEChangeResolver.resolveEChange(change, resourceSet, resolveBefore)
				|| !resolveAtomicChanges(change, resourceSet, resolveBefore, revertAfterResolving)) {
				return false
			}	
		}
		return true				
	}
	
	def private static boolean resolveAtomicChanges(CompoundEChange change, ResourceSet resourceSet, boolean resolveBefore, boolean revertAfterResolving) {
		val appliedChanges = new BasicEList<EChange>
		if (resolveBefore) {
			for (AtomicEChange c : change.atomicChanges) {
				if (!AtomicEChangeResolver.resolve(c, resourceSet, true) || !c.applyForward) {
					// Error resolving or applying forward => revert all applied changes
					for (EChange changed : appliedChanges.reverseView) {
						changed.applyBackward
					}
					return false
				} else {
					appliedChanges.add(c)
				}
			}
		} else {
			for (AtomicEChange c : change.atomicChanges.reverseView) {
				if (!AtomicEChangeResolver.resolve(c, resourceSet, false) || !c.applyBackward) {
					// Error resolving or applying backward => revert all applied changes
					for (EChange changed : appliedChanges.reverseView) {
						changed.applyForward
					}
					return false
				} else {
					appliedChanges.add(c)
				}
			}
		}
		
		// Revert all changes which were made resolving the compound change.
		if (revertAfterResolving) {
			if (resolveBefore) {
				change.applyBackward
			} else {
				change.applyForward
			}
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
	def static dispatch boolean resolveCompoundEChange(CompoundEChange change, ResourceSet resourceSet, boolean resolveBefore, boolean revertAfterResolving) {
		return resolveCompoundChange(change, resourceSet, resolveBefore, revertAfterResolving)
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
	def static dispatch boolean resolveCompoundEChange(ExplicitUnsetEFeature change, ResourceSet resourceSet, boolean resolveBefore, boolean revertAfterResolving) {
		if (!change.isResolved) {
			if (change.affectedEObject == null || change.affectedFeature == null 
				|| !resolveCompoundChange(change, resourceSet, resolveBefore, revertAfterResolving)) {
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
		}
		return true
	}
}