package tools.vitruv.framework.change.description

import java.util.List
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.util.datatypes.URIHaving
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.uuid.UuidResolver
import tools.vitruv.framework.change.interaction.UserInteractionBase
import org.eclipse.emf.ecore.resource.Resource

/** 
 * Base interface for all kinds of changes in Vitruvius.
 * 
 * @author Heiko Klare
 */
interface VitruviusChange extends URIHaving {
	/** 
	 * Returns whether the change contains any concrete change or consists only of composite ones.
	 */
	def boolean containsConcreteChange();

	/** 
	 * Validates the change by checking if at least one concrete change is contained and
	 * the URIs of all contained changes are same. 
	 */
	def boolean validate();

	/** 
	 * Returns the {@link EChange}s that describe this change. Requires the change to be prepared so
	 * that the original change information is transformed into {@link EChange}s.  
	 */
	def List<EChange> getEChanges();

	/**
	 * Resolves the change and applies it forward so that the model is in the state after the change afterwards.
	 * It has to be ensured that the model is in a state the change can be applied to before calling this method.
	 * If the change cannot be resolved or is already resolved, an Exception is thrown.
	 */
	def void resolveBeforeAndApplyForward(UuidResolver uuidResolver);

	/**
	 * Resolves the change and applies it backward so that the model is in the state before the change afterwards.
	 * It has to be ensured that the model is in a state the change can be applied to before calling this method.
	 * If the change cannot be resolved or is already resolved, an Exception is thrown.
	 */
	def void resolveAfterAndApplyBackward(UuidResolver uuidResolver);

	/**
	 * Unresolves the change if it can be applied to a resource. If it is not applicable, it will not be unresolved
	 * (as a new resolution is impossible).
	 */
	def void unresolveIfApplicable();

	/**
	 * Returns all {@link EObject}s affected by this change, including both the elements of which an attribute or
	 * reference was changes, as well as the referenced elements.
	 */
	def Iterable<EObject> getAffectedEObjects();

	/**
	 * Returns the IDs of all {@link EObject}s affected by this change, including both the elements of which an attribute or
	 * reference was changes, as well as the referenced elements.
	 */
	def Iterable<String> getAffectedEObjectIds();

	/**
	 * Returns the unique {@link Resource} changed by this change, i.e., the resource containing the changed {@link EObject}s.
	 * If no {@link EObject}s are affected or if the change is not resolved, <code>null</code> is returned.
	 * The {@link URI} of the returned {@link Resource} is the same as of the change itself,
	 * i.e., <code>this.changedResource.getURI().equals(this.getURI())</code>
	 * @throws IllegalStateExecption if the change is not valid (see {@link #validate() validate()})
	 */
	def Resource getChangedResource();

	/**
	 * Returns all user interactions performed during application of this change and performing consistency preservation.
	 */
	def Iterable<UserInteractionBase> getUserInteractions()

	/**
	 * Checks whether the affected EObjects of this change are equal to the EObjects of the other change.
	 */
	def boolean changedEObjectEquals(VitruviusChange change)
}
