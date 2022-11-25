package tools.vitruv.framework.views.changederivation

import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import tools.vitruv.change.composite.description.VitruviusChange

/** 
 * Strategy for resolving state-based changes to individual change sequences.
 * This strategy is used by the domains when there are no change sequences available and changes need to be propagated based on the difference between the old and new state.
 */
interface StateBasedChangeResolutionStrategy {

	/**
	 * Resolves the state-based delta of two resources and returns the correlating change sequence.
	 * Any element in the returned {@VitruviusChange} must be contained in the {@link ResourceSet} of the {@code oldState} 
	 * except for newly created elements which are not contained in any resource. The {@code oldState} remains unmodified 
	 * from calling this method. This implies that any element contained in the returned {@link VitruviusChange} is also based
	 * on the old state.
	 *
	 * @param newState is the new state of the resource, must not be <code>null</code> and must not contain proxies.
	 * @param oldState is the current or old state of the resource, must not be <code>null</code> and must not contain proxies.
	 * @return a {@link VitruviusChange} that contains the individual change sequence.
	 */
	def VitruviusChange getChangeSequenceBetween(Resource newState, Resource oldState)

	/**
	 * Resolves the state-based delta for creating the given resource and returns the correlating change sequence.
	 * Any element in the returned {@VitruviusChange} must be contained in the {@link ResourceSet} of the {@code oldState} 
	 * except for newly created elements which are not contained in any resource. As the returned {@VitruviusChange} is not
	 * applied to any {@link ResourceSet}, its contained elements do not reflect the state of {@code newState}.
	 *
	 * @param newState is the new state of the resource, must not be <code>null</code> and must not contain proxies.
	 * @return a {@link VitruviusChange} that contains the individual change sequence.
	 */
	def VitruviusChange getChangeSequenceForCreated(Resource newState)

	/**
	 * Resolves the state-based delta for deleting the given resource and returns the correlating change sequence.
	 * Any element in the returned {@VitruviusChange} must be contained in the {@link ResourceSet} of the {@code oldState}. 
	 * The {@code oldState} remains unmodified from calling this method. This implies that any element contained in the
	 * returned {@link VitruviusChange} is also based on the old state.
	 *
	 * @param oldState is the new state of the resource, must not be <code>null</code> and must not contain proxies.
	 * @return a {@link VitruviusChange} that contains the individual change sequence.
	 */
	def VitruviusChange getChangeSequenceForDeleted(Resource oldState)
}
