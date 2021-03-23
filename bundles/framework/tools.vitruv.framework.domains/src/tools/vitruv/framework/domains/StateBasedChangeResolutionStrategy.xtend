package tools.vitruv.framework.domains

import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.uuid.UuidResolver

/** 
 * Strategy for resolving state-based changes to individual change sequences.
 * This strategy is used by the domains when there are no change sequences available and changes need to be propagated based on the difference between the old and new state.
 */
interface StateBasedChangeResolutionStrategy {

	/**
	 * Resolves the state-based delta of two resources and returns the correlating change sequences.
	 * @param newState is the new state of the resource, must not be <code>null</code> and must not contain proxies.
	 * @param oldState is the current or old state of the resource, must not be <code>null</code> and must not contain proxies.
	 * @param uuidResolver is the UUID resolver of the virtual model using this propagation strategy, must not be <code>null</code>.
	 * @return a {@link VitruviusChange} that contains the individual change sequence.
	 */
	def VitruviusChange getChangeSequenceBetween(Resource newState, Resource oldState, UuidResolver resolver)
	
	/**
	 * Resolves the state-based delta for creating the given resource and returns the correlating change sequences.
	 * @param newState is the new state of the resource, must not be <code>null</code> and must not contain proxies.
	 * @param uuidResolver is the UUID resolver of the virtual model using this propagation strategy, must not be <code>null</code>.
	 * @return a {@link VitruviusChange} that contains the individual change sequence.
	 */
	def VitruviusChange getChangeSequenceForCreated(Resource newState, UuidResolver resolver)
	
	/**
	 * Resolves the state-based delta for deleting the given resource and returns the correlating change sequences.
	 * @param oldState is the new state of the resource, must not be <code>null</code> and must not contain proxies.
	 * @param uuidResolver is the UUID resolver of the virtual model using this propagation strategy, must not be <code>null</code>.
	 * @return a {@link VitruviusChange} that contains the individual change sequence.
	 */
	def VitruviusChange getChangeSequenceForDeleted(Resource oldState, UuidResolver resolver)
}
