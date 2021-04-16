package tools.vitruv.framework.change.description

import java.util.List
import tools.vitruv.framework.change.echange.EChange
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.change.interaction.UserInteractionBase
import org.eclipse.emf.ecore.resource.Resource
import java.util.Set
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.ResourceSet

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
	 * Returns the resolved change
	 * 
	 * @throws IllegalStateException if the change cannot be resolved or is already resolved.
	 */
	def VitruviusChange resolveAndApply(ResourceSet resourceSet)

	/**
	 * Returns an unresolved change, such that all its affected and referenced {@link EObjects} are removed.
	 */
	def VitruviusChange unresolve()
	
	/**
	 * Returns all {@link EObject}s directly affected by this change. This does not include referenced elements.
	 */
	def Set<EObject> getAffectedEObjects()
	
	/**
	 * Returns all {@link EObject}s affected by this change, including both the elements of which an attribute or
	 * reference was changes, as well as the referenced elements.
	 */
	def Set<EObject> getAffectedAndReferencedEObjects()

	/**
	 * Returns the {@link URI}s of all {@link Resource}s changed by this change, i.e. the resources containing the 
	 * changed {@link EObject}s. The returned {@link Iterable} may be empty if no {@link EObject}s are affected by this
	 * change or if this change was not resolved yet.
	 */
	def Set<URI> getChangedURIs()

	/**
	 * Returns all user interactions performed during application of this change and performing consistency preservation.
	 */
	def Iterable<UserInteractionBase> getUserInteractions()

	def VitruviusChange copy()
}
