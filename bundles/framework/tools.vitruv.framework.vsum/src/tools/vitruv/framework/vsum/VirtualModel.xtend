package tools.vitruv.framework.vsum

import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.util.datatypes.ModelInstance
import java.io.File
import java.util.List
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.uuid.UuidGeneratorAndResolver
import org.eclipse.emf.ecore.resource.Resource

interface VirtualModel {
	def File getFolder();
	def List<PropagatedChange> propagateChange(VitruviusChange change);
	/**
	 * propagates changes from the delta between the current state and a specific new state.
	 * @param newState is the resource of the new states {@link ModelInstance}.
	 */
	def List<PropagatedChange> propagateChangedState(Resource newState);
	def void reverseChanges(List<PropagatedChange> changes);
	def ModelInstance getModelInstance(VURI modelVuri);
	def UuidGeneratorAndResolver getUuidGeneratorAndResolver();
}