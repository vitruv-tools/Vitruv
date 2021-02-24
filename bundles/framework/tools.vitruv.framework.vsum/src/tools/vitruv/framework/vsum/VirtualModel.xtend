package tools.vitruv.framework.vsum

import java.util.List
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.util.datatypes.ModelInstance
import tools.vitruv.framework.util.datatypes.VURI
import org.eclipse.emf.common.util.URI
import java.nio.file.Path
import tools.vitruv.framework.uuid.UuidResolver

interface VirtualModel {
	def Path getFolder();

	def List<PropagatedChange> propagateChange(VitruviusChange change);

	/**
	 * Propagates delta-based changes as long as the location and the name of the resource was not changed.
	 * Should be used when there are no change sequences available and consistency needs to be preserved based on the changes between the previous and new state
	 * of a model.
	 * @param newState is the resource of the new state.
	 */
	def List<PropagatedChange> propagateChangedState(Resource newState);

	/**
	 * Propagates delta-based changes. Allows to change the location and the name of the resource.
	 * Should be used when there are no change sequences available and consistency needs to be preserved based on the changes between the previous and new state
	 * of a model.
	 * @param newState is the resource of the new state.
	 * @param oldLocation specifies the previous location of the resource to avoid problems with renaming or moving elements.
	 */
	def List<PropagatedChange> propagateChangedState(Resource newState, URI oldLocation);

	def void reverseChanges(List<PropagatedChange> changes);

	def ModelInstance getModelInstance(VURI modelVuri);

	def UuidResolver getUuidResolver();
}
