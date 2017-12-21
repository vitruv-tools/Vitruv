package tools.vitruv.framework.change.echange.resolve

import tools.vitruv.framework.uuid.UuidResolver
import tools.vitruv.framework.change.echange.util.ApplyEChangeSwitch
import com.google.common.base.Objects
import org.eclipse.emf.ecore.util.EcoreUtil
import tools.vitruv.framework.change.echange.EChange

/**
 * Utility class for applying and resolving a given EChange.
 */
class EChangeResolverAndApplicator {
	static def EChange resolveBefore(EChange eChange, UuidResolver uuidResolver) {
		return resolveCopy(eChange, uuidResolver, true, true);
	}

	static def EChange resolveAfter(EChange eChange, UuidResolver uuidResolver) {
		return resolveCopy(eChange, uuidResolver, false, true);
	}
	
	static def EChange resolveBeforeAndApplyForward(EChange eChange, UuidResolver uuidResolver) {
		val EChange resolvedChange = resolveBefore(eChange, uuidResolver);
		if (((!Objects.equal(resolvedChange, null)) && resolvedChange.applyForward())) {
			return resolvedChange;
		}
		else {
			return null;
		}
	}

	static def EChange resolveAfterAndApplyBackward(EChange eChange, UuidResolver uuidResolver) {
		val EChange resolvedChange = resolveAfter(eChange, uuidResolver);
		if (((!Objects.equal(resolvedChange, null)) && resolvedChange.applyBackward())) {
			return resolvedChange;
		}
		else {
			return null;
		}
	}

	static def boolean applyForward(EChange eChange) {
		return ApplyEChangeSwitch.applyEChange(eChange, true);
	}

	static def boolean applyBackward(EChange eChange) {
		return ApplyEChangeSwitch.applyEChange(eChange, false);
	}
	
	/**
	 * Creates a copy of the change and resolves the unresolved proxy EObjects of the 
	 * change to a given set of resources with concrete EObjects.
	 * Before the change can be applied all proxy objects need to be resolved.
	 * @param change					The {@link EChange} which shall be resolved.
	 * @param uuidResolver 				The {@link UuidResolver} to resolve {@link EObject}s from
	 * @param resolveBefore				{@code true} if the model is in the state before the change,
	 * 									{@code false} if the model is in the state after.
	 * @param revertAfterResolving		If {@code true} all applied changes while resolving the change will be reverted after resolving.
	 * 									Doesn't affect atomic EChanges.
	 * @return 							Returns a resolved copy of the change. If the copy could not be resolved or the resource set
	 * 									is {@code null}, it returns {@code null}. If the change was already resolved an exception is thrown.
	 * @throws IllegalStateException 	The change is already resolved.
	 */
	def private static EChange resolveCopy(EChange change, UuidResolver uuidResolver, boolean resolveBefore,
		boolean revertAfterResolving) {
		var EChange copy = EcoreUtil.copy(change)
		if (resolve(copy, uuidResolver, resolveBefore, revertAfterResolving)) {
			return copy
		} else {
			return null
		}
	}
	
	/**
	 * Resolves the unresolved proxy EObjects of a given {@code EChange} to a given set of resources with concrete EObjects.
	 * Before the change can be applied all proxy objects need to be resolved.
	 * @param change					The {@link EChange} which shall be resolved.
	 * @param uuidResolver 				The {@link UuidResolver} to resolve {@link EObject}s from
	 * @param resolveBefore				{@code true} if the model is in the state before the change,
	 * 									{@code false} if the model is in the state after.
	 * @param revertAfterResolving		If {@code true} all applied changes while resolving the change will be reverted after resolving.
	 * 									Doesn't affect atomic EChanges.
	 * @return 							Returns the resolved change. If the change could not be resolved or the resource set
	 * 									is {@code null}, it returns {@code null}. If the change was already resolved an exception is thrown.
	 * @throws IllegalStateException 	The change is already resolved.
	 */
	def package static boolean resolve(EChange change, UuidResolver uuidResolver, boolean resolveBefore,
		boolean revertAfterResolving) {
		if (change.isResolved) {
			throw new IllegalArgumentException
		}
		if (!AtomicEChangeResolver.resolve(change, uuidResolver, resolveBefore)) {
			return false
		}
		return true
	}
}