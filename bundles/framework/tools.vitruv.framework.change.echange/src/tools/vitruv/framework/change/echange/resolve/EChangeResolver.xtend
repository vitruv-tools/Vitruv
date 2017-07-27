package tools.vitruv.framework.change.echange.resolve

import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.util.EcoreUtil
import tools.vitruv.framework.change.echange.AtomicEChange
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.compound.CompoundEChange

/**
 * Utility class for resolving an given EChange. Dispatches AtomicEChange and CompoundEChange.
 * The xcore-model doesn't provide private methods and to provide a clean EChange interface,
 * the resolve method is placed in this utility class.
 */
class EChangeResolver {
	/**
	 * Creates a copy of the change and resolves the unresolved proxy EObjects of the
	 * change to a given set of resources with concrete EObjects::
	 * Before the change can be applied all proxy objects need to be resolved.
	 * @param change					The {@link EChange} which shall be resolved.
	 * @param resourceSet 				The {@code ResourceSet} which contains the concrete EObjects the proxy objects of
	 * 									the unresolved should be resolved to.
	 * @param resolveBefore				{@code true} if the model is in the state before the change,
	 * 									{@code false} if the model is in the state after.
	 * @param revertAfterResolving		If {@code true} all applied changes while resolving the change will be reverted after resolving.
	 * 									Doesn't affect atomic EChanges::
	 * @return 							Returns a resolved copy of the change. If the copy could not be resolved or the resource set
	 * 									is {@code null}, it returns {@code null}. If the change was already resolved an exception is thrown.
	 * @throws IllegalStateException 	The change is already resolved.
	 */
	static def EChange resolveCopy(
		EChange change,
		ResourceSet resourceSet,
		boolean resolveBefore,
		boolean revertAfterResolving
	) {
		val copy = EcoreUtil::copy(change)
		if (resolve(copy, resourceSet, resolveBefore, revertAfterResolving))
			return copy
		else
			return null
	}

	/**
	 * Resolves the unresolved proxy EObjects of a given {@code EChange} to a given set of resources with concrete EObjects::
	 * Before the change can be applied all proxy objects need to be resolved.
	 * @param change					The {@link EChange} which shall be resolved.
	 * @param resourceSet 				The {@code ResourceSet} which contains the concrete EObjects the proxy objects of
	 * 									the unresolved should be resolved to.
	 * @param resolveBefore				{@code true} if the model is in the state before the change,
	 * 									{@code false} if the model is in the state after.
	 * @param revertAfterResolving		If {@code true} all applied changes while resolving the change will be reverted after resolving.
	 * 									Doesn't affect atomic EChanges::
	 * @return 							Returns the resolved change. If the change could not be resolved or the resource set
	 * 									is {@code null}, it returns {@code null}. If the change was already resolved an exception is thrown.
	 * @throws IllegalStateException 	The change is already resolved.
	 */
	static def boolean resolve(
		EChange change,
		ResourceSet resourceSet,
		boolean resolveBefore,
		boolean revertAfterResolving
	) {
		if (change.resolved)
			throw new IllegalArgumentException
		return resolveChange(change, resourceSet, resolveBefore, revertAfterResolving)

	}

	/**
	 * Dispatch method for resolving an {@link EChange}.
	 */
	private def static dispatch boolean resolveChange(EChange change, ResourceSet resourceSet, boolean resolveBefore,
		boolean revertAfterResolving) {
		// If an EChange reaches this point, there is a dispatch method missing for the concrete type.
		throw new UnsupportedOperationException
	}

	/**
	 * Dispatch method for resolving {@link AtomicEChange}s.
	 */
	private def static dispatch boolean resolveChange(
		AtomicEChange change,
		ResourceSet resourceSet,
		boolean resolveBefore,
		boolean revertAfterResolving
	) {
		AtomicEChangeResolver::resolve(change, resourceSet, resolveBefore)
	}

	/**
	 * Dispatch method for resolving an {@link CompoundEChange}s.
	 */
	private def static dispatch boolean resolveChange(
		CompoundEChange change,
		ResourceSet resourceSet,
		boolean resolveBefore,
		boolean revertAfterResolving
	) {
		CompoundEChangeResolver::resolve(change, resourceSet, resolveBefore, revertAfterResolving)
	}
}
