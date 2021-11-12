package tools.vitruv.framework.vsum

import java.util.Collection
import java.util.List
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.change.description.VitruviusChange
import org.eclipse.emf.common.util.URI
import java.nio.file.Path
import tools.vitruv.framework.vsum.ChangePropagationListener
import tools.vitruv.framework.vsum.views.ViewType
import org.eclipse.emf.ecore.resource.ResourceSet
import com.google.common.annotations.Beta

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

	def boolean isCompatible(ViewType viewType) {
		viewTypes.contains(viewType) // TODO TS What do we understand as a ViewType being compatible with a VSUM?
	}

	def Collection<ViewType> getViewTypes()

	/**
	 * IMPORTANT: This method is only temporary, and should be replaced a inversion of control based mechanism to supply
	 * viewtypes with the necessary resources for view creation.
	 */
	@Deprecated
	@Beta
	def ResourceSet getResourceSet()
}
