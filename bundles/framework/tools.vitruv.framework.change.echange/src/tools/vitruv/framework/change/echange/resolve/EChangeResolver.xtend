package tools.vitruv.framework.change.echange.resolve

import org.eclipse.emf.ecore.util.EcoreUtil
import tools.vitruv.framework.change.echange.AtomicEChange
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.uuid.UuidResolver

/**
 * Utility class for resolving an given EChange. Dispatches AtomicEChange and CompoundEChange.
 * The xcore-model doesn't provide private methods and to provide a clean EChange interface,
 * the resolve method is placed in this utility class.
 */
class EChangeResolver {
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
	def public static EChange resolveCopy(EChange change, UuidResolver uuidResolver, boolean resolveBefore,
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
	def public static boolean resolve(EChange change, UuidResolver uuidResolver, boolean resolveBefore,
		boolean revertAfterResolving) {
		if (change.isResolved) {
			throw new IllegalArgumentException
		}
		if (!resolveChange(change, uuidResolver, resolveBefore, revertAfterResolving)) {
			return false
		}
		return true
	}

	/**
	 * Dispatch method for resolving an {@link EChange}.
	 */
	def private static dispatch boolean resolveChange(EChange change, UuidResolver uuidResolver, boolean resolveBefore,
		boolean revertAfterResolving) {
		// If an EChange reaches this point, there is a dispatch method missing for the concrete type.
		throw new UnsupportedOperationException
	}

	/**
	 * Dispatch method for resolving {@link AtomicEChange}s.
	 */
	def private static dispatch boolean resolveChange(AtomicEChange change, UuidResolver uuidResolver,
		boolean resolveBefore, boolean revertAfterResolving) {
		AtomicEChangeResolver.resolve(change, uuidResolver, resolveBefore)
	}

}
