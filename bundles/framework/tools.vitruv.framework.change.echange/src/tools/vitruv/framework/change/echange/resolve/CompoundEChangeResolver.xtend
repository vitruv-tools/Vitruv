package tools.vitruv.framework.change.echange.resolve

import java.util.List
import org.eclipse.emf.common.util.BasicEList
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import tools.vitruv.framework.change.echange.AtomicEChange
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.compound.CompoundEChange
import tools.vitruv.framework.change.echange.compound.ExplicitUnsetEFeature
import tools.vitruv.framework.change.echange.util.ApplyEChangeSwitch
import tools.vitruv.framework.change.uuid.UuidResolver
import tools.vitruv.framework.change.echange.EChangeResolverAndApplicator

class CompoundEChangeResolver {
	/**
	 * Resolving the {@link CompoundEChange} EChange.
	 * @param change 				The change which should be resolved.
	 * @param uuidResolver 			The {@link UuidResolver} to resolve {@link EObject}s from
	 * @param resolveBefore			{@code true} if the model is in state before the change,
	 * 								{@code false} if the model is in state after.
	 * @param revertAfterResolving	{@code true} if the change should be reverted after resolving
	 * 								the compound change.
	 */	
	def private static boolean resolveCompoundEChange(CompoundEChange change, UuidResolver uuidResolver, boolean resolveBefore, boolean revertAfterResolving) {
		if (!AtomicEChangeResolver.resolveEChange(change, uuidResolver, resolveBefore) 
			|| !resolveAtomicChanges(change, uuidResolver, resolveBefore, revertAfterResolving)) {
			return false
		}
		return true				
	}
	
	/**
	 * Resolves the atomic EChanges of a compound EChange. 
	 * The atomic EChanges will be applied while resolving.
	 * @param change 				The change which should be resolved.
	 * @param uuidResolver 			The {@link UuidResolver} to resolve {@link EObject}s from
	 * @param resolveBefore			{@code true} if the model is in state before the change,
	 * 								{@code false} if the model is in state after.
	 * @param revertAfterResolving	{@code true} if the change should be reverted after resolving
	 * 								the atomic changes.
	 */
	def private static boolean resolveAtomicChanges(CompoundEChange change, UuidResolver uuidResolver, boolean resolveBefore, boolean revertAfterResolving) {
		var List<AtomicEChange> atomicChanges = change.atomicChanges
		if (!resolveBefore) {
			atomicChanges.reverse
		}
		
		val appliedChanges = new BasicEList<EChange>		
		for (AtomicEChange c : atomicChanges) {
			if (!AtomicEChangeResolver.resolve(c, uuidResolver, resolveBefore) 
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
	 * @param uuidResolver 			The {@link UuidResolver} to resolve {@link EObject}s from
	 * @param resolveBefore			{@code true} if the model is in state before the change,
	 * 								{@code false} if the model is in state after.
	 * @param revertAfterResolving	{@code true} if the change should be reverted after resolving
	 * 								the compound change.
	 */		
	def package static dispatch boolean resolve(CompoundEChange change, UuidResolver uuidResolver, boolean resolveBefore, boolean revertAfterResolving) {
		return resolveCompoundEChange(change, uuidResolver, resolveBefore, revertAfterResolving)
	}	
	

	
	/**
	 * Dispatch method for resolving the {@link CompoundEChange} EChange.
	 * @param change 				The change which should be resolved.
	 *@param uuidResolver 			The {@link UuidResolver} to resolve {@link EObject}s from
	 * @param resolveBefore			{@code true} if the model is in state before the change,
	 * 								{@code false} if the model is in state after.
	 * @param revertAfterResolving	{@code true} if the change should be reverted after resolving
	 * 								the compound change.
	 */	
	def package static dispatch boolean resolve(ExplicitUnsetEFeature<EObject, EStructuralFeature> change, UuidResolver uuidResolver, boolean resolveBefore, boolean revertAfterResolving) {
		if (change.affectedEObjectID === null || change.affectedFeature === null) {
			return false;
		} 
					
		change.affectedEObject = uuidResolver.getEObject(change.affectedEObjectID)

		if (!resolveCompoundEChange(change, uuidResolver, resolveBefore, revertAfterResolving)) {
			return false
		}

		if (change.affectedEObject === null || change.affectedEObject.eIsProxy) {
			return false
		}	
		
		// Unset change is special case of compound changes, because the application of the 
		// atomic changes (removing all elements) != application of the compound change (explicit unset command)
		// => Attribute needs to be unset additionally.
		if ((!revertAfterResolving && resolveBefore)
			|| (revertAfterResolving && !resolveBefore)) {
			EChangeResolverAndApplicator.applyForward(change)// Calls single unset command
		}
		return true
	}
}
