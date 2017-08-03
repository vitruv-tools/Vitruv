package tools.vitruv.framework.util.command

import java.util.Map

import org.eclipse.emf.ecore.EObject
import org.eclipse.xtend.lib.annotations.Accessors

import tools.vitruv.framework.util.datatypes.VURI

/**
 * Represents the result of a change propagation regarding necessary persistence.
 * Essentially, the mapping of {@link EObject}s to {@link VURI}s to persist them in is managed.
 * @author Heiko Klare
 */
class ChangePropagationResult {
	@Accessors(PUBLIC_GETTER)
	val Map<EObject, VURI> elementToPersistenceMap

	new() {
		elementToPersistenceMap = newHashMap
	}

	/**
	 * Revokes the registration for persistence of the specified {@link EObject}.
	 * This is only useful, if an element was registered for persistence using the method{@link ChangePropagationResult#registerForEstablishPersistence(EObject, VURI)}, which
	 * shall be revoked before it was processed by a persistence mechanisms.
	 * @param persistenceRootElement the {@link EObject} of which the persistence registration shall be revoked
	 */
	def void revokeRegistrationForPersistence(EObject persistenceRootElement) {
		elementToPersistenceMap.put(persistenceRootElement, null)
	}

	/**
	 * Registers the specified {@link EObject} for being persisted by the mechanisms that finally
	 * processes this {@link ChangePropagationResult}. Persistence will be performed as root element
	 * in a resource with the specified {@link VURI}.
	 * If the persistence registration shall be revoked before persistence is executed, the method{@link ChangePropagationResult#revokeRegistrationForPersistence(EObject)} has to be called.
	 * @param persistenceRootElement the {@link EObject} to persist as root
	 * @param persistenceVuri the {@link VURI} of the resource to persist element in
	 */
	def void registerForEstablishPersistence(EObject persistenceRootElement, VURI persistenceVuri) {
		elementToPersistenceMap.put(persistenceRootElement, persistenceVuri)
	}

	def void integrateResult(ChangePropagationResult transformationResult) {
		if (transformationResult !== null) {
			transformationResult.elementToPersistenceMap.entrySet.forEach [
				registerForEstablishPersistence(key, value)
			]
		}
	}
}
