package tools.vitruv.framework.domains

import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.framework.change.description.CompositeChange
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.uuid.UuidGeneratorAndResolver
import org.eclipse.emf.ecore.EObject

/** 
 * Strategy for resolving state-based changes to individual change sequences.
 * This strategy is used by the domains when there are no change sequences available and changes need to be propagated based on the difference between the old and new state.
 */
interface StateBasedChangeResolutionStrategy {

	/**
	 * Resolves the state-based delta of two resources and returns the correlating change sequences.
	 * @param newState is the new state of the resource.
	 * @param currentState is the current or old state of the resource.
	 * @param uuidGeneratorAndResolver is the UUID resolver of the virtual model using this propagation strategy.
	 * @return a {@link CompositeChange} that contains the individual change sequences.
	 */
	def CompositeChange<VitruviusChange> getChangeSequences(Resource newState, Resource currentState,
		UuidGeneratorAndResolver uuidGeneratorAndResolver)

	/**
	 * Resolves the state-based delta of two EObjects and returns the correlating change sequences.
	 * @param newState is the new state of the EObject.
	 * @param currentState is the current or old state of the EObject.
	 * @param uuidGeneratorAndResolver is the UUID resolver of the virtual model using this propagation strategy.
	 * @return a {@link CompositeChange} that contains the individual change sequences.
	 */
	def CompositeChange<VitruviusChange> getChangeSequences(EObject newState, EObject currentState, UuidGeneratorAndResolver resolver)
}
