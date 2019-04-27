package tools.vitruv.framework.vsum

import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.util.datatypes.ModelInstance
import java.io.File
import java.util.List
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.uuid.UuidGeneratorAndResolver
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.EObject

interface VirtualModel {
	def File getFolder();

	def List<PropagatedChange> propagateChange(VitruviusChange change);

	/**
	 * Propagates delta-based changes. Should be used when there are no change sequences available
	 * and consistency needs to be preserved based on the changes between the previous and new state
	 * of a model.
	 * @param newState is the resource of either the {@link ModelInstance} or the model root element.
	 */
	def List<PropagatedChange> propagateChangedState(Resource newState);

	/**
	 * Propagates delta-based changes. Should be used when there are no change sequences available
	 * and consistency needs to be preserved based on the changes between the previous and new state
	 * of a model.
	 * @param newState is the root {@link EObject} of the model. 
	 */
	def List<PropagatedChange> propagateChangedState(EObject newState);

	def void reverseChanges(List<PropagatedChange> changes);

	def ModelInstance getModelInstance(VURI modelVuri);

	def UuidGeneratorAndResolver getUuidGeneratorAndResolver();
}
