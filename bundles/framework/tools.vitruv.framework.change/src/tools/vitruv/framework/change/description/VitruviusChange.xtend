package tools.vitruv.framework.change.description

import java.util.List
import tools.vitruv.framework.change.echange.EChange
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.uuid.UuidResolver
import tools.vitruv.framework.change.interaction.UserInteractionBase
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.framework.util.datatypes.VURI
import java.util.Set

/** 
 * Base interface for all kinds of changes in Vitruvius.
 * 
 * @author Heiko Klare
 */
interface VitruviusChange {
	/** 
	 * Returns whether the change contains any concrete change or consists only of composite ones.
	 */
	def boolean containsConcreteChange()

	/** 
	 * Returns the {@link EChange}s that describe this change. Requires the change to be prepared so
	 * that the original change information is transformed into {@link EChange}s.  
	 */
	def List<EChange> getEChanges()
	
	/**
	 * Resolves the change and applies it forward so that the model is in the state after the change afterwards.
	 * It has to be ensured that the model is in a state the change can be applied to before calling this method.
	 * 
	 * @throws RuntimeException if the change cannot be resolved or is already resolved.
	 */
	def void resolveBeforeAndApplyForward(UuidResolver uuidResolver)

	/**
	 * Resolves the change and applies it backward so that the model is in the state before the change afterwards.
	 * It has to be ensured that the model is in a state the change can be applied to before calling this method.
	 *
	 * @throws RuntimeException if the change cannot be resolved or is already resolved.
	 */
	def void resolveAfterAndApplyBackward(UuidResolver uuidResolver)

	/**
	 * Unresolves the change if it can be applied to a resource. If it is not applicable, it will not be unresolved
	 * (as a new resolution is impossible).
	 */
	def void unresolveIfApplicable()
	
	/**
	 * Returns all {@link EObject}s directly affected by this change. This does not include referenced elements.
	 */
	def Set<EObject> getAffectedEObjects()
	
	/**
	 * Returns the ID all {@link EObject}s directly affected by this change. This does not include referenced elements.
	 */
	def Set<String> getAffectedEObjectIds()

	/**
	 * Returns all {@link EObject}s affected by this change, including both the elements of which an attribute or
	 * reference was changes, as well as the referenced elements.
	 */
	def Set<EObject> getAffectedAndReferencedEObjects()

	/**
	 * Returns the IDs of all {@link EObject}s affected by this change, including both the elements of which an attribute or
	 * reference was changes, as well as the referenced elements.
	 */
	def Set<String> getAffectedAndReferencedEObjectIds()

	/**
	 * Returns the {@link VURI}s of all {@link Resource}s changed by this change, i.e. the resources containing the 
	 * changed {@link EObject}s. The returned {@link Iterable} may be empty if no {@link EObject}s are affected by this
	 * change or if this change was not resolved yet.
	 */
	def Set<VURI> getChangedVURIs()

	/**
	 * Returns all user interactions performed during application of this change and performing consistency preservation.
	 */
	def Iterable<UserInteractionBase> getUserInteractions()

	def VitruviusChange copy()
}
