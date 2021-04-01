package tools.vitruv.framework.vsum

import java.util.List
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.change.description.VitruviusChange
import org.eclipse.emf.common.util.URI
import java.nio.file.Path
import tools.vitruv.framework.vsum.ChangePropagationListener
import tools.vitruv.framework.uuid.UuidGeneratorAndResolver
import org.eclipse.emf.ecore.resource.ResourceSet

interface VirtualModel {
	def Path getFolder()

	def void addChangePropagationListener(ChangePropagationListener propagationListener)
	def void removeChangePropagationListener(ChangePropagationListener propagationListener)
	
	def List<PropagatedChange> propagateChange(VitruviusChange change)

	/**
	 * Propagates delta-based changes as long as the location and the name of the resource was not changed.
	 * Should be used when there are no change sequences available and consistency needs to be preserved based on the changes between the previous and new state
	 * of a model.
	 * @param newState is the {@link Resource} of the new state
	 */
	def List<PropagatedChange> propagateChangedState(Resource newState)

	/**
	 * Propagates delta-based changes. Allows to change the location and the name of the resource.
	 * Should be used when there are no change sequences available and consistency needs to be preserved based on the changes between the previous and new state
	 * of a model.
	 * @param newState is the {@link Resource} of the new state
	 * @param oldLocation specifies the previous location of the resource to avoid problems with renaming or moving elements
	 */
	def List<PropagatedChange> propagateChangedState(Resource newState, URI oldLocation)

	/**
	 * Creates a {@link UuidGeneratorAndResolver} for the given {@link ResourceSet} based on the UUID resolver used in this {@link VirtualModel} as a parent
	 * resolver.
	 * It can, in particular, be used to create a {@link UuidGeneratorAndResolver} for view resource sets, as long as no comprehensive mechanism for
	 * deriving views from a {@link VirtualModel} is provided.
	 * 
	 * @param resourceSet is the {@link ResourceSet} to create the {@link UuidGeneratorAndResolver} for
	 */
	def UuidGeneratorAndResolver createChildUuidGeneratorAndResolver(ResourceSet resourceSet)
}
