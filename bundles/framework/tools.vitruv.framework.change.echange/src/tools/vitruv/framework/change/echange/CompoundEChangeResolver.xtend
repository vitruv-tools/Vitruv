package tools.vitruv.framework.change.echange

import org.eclipse.emf.common.util.BasicEList
import org.eclipse.emf.ecore.resource.ResourceSet
import tools.vitruv.framework.change.echange.compound.CompoundEChange
import tools.vitruv.framework.change.echange.compound.ExplicitUnsetEFeature

class CompoundEChangeResolver {
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
		if (!change.isResolved) {
			
			if (!AtomicEChangeResolver.resolveEChange(change, resourceSet, resolveBefore)) {
				return false
			}
			
			val appliedChanges = new BasicEList<EChange>
			if (resolveBefore) {
				for (EChange c : change.atomicChanges) {
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
				for (EChange c : change.atomicChanges.reverseView) {
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
	def static dispatch boolean resolveCompoundEChange(ExplicitUnsetEFeature<?, ?> change, ResourceSet resourceSet, boolean resolveBefore, boolean revertAfterResolving) {
		// ExplicitUnsetEFeature is special case of compound changes, because
		// applying its atomic changes removes only all values but does not unset the feature.
		// Applying the compound change unsets the feature.
		// => needs to be unset additionally
		if (!change.isResolved) {
			if (!AtomicEChangeResolver.resolveEChange(change, resourceSet, resolveBefore)) {
				return false
			}
			
			if (resolveBefore) {
				for (EChange c : change.atomicChanges) {
					if (!AtomicEChangeResolver.resolve(c, resourceSet, true)) {
						return false
					}	
				}
			} else {
				val changesMade = new BasicEList<EChange>
				for (EChange c : change.atomicChanges.reverseView) {
					if (!AtomicEChangeResolver.resolve(c, resourceSet, true) || !c.applyBackward) {
						// Error resolving or applying backward => revert
						for (EChange changed : changesMade.reverseView) {
							changed.applyForward
						}
						return false						
					} else {
						changesMade.add(c)
					}
				}
			}
			
			// Revert all changes which were made resolving the compound change.
			if ((!revertAfterResolving && resolveBefore)
				|| (revertAfterResolving && !resolveBefore)
			) {
				change.applyForward // Calls single unset command
			}
		}
		return true
	}
}