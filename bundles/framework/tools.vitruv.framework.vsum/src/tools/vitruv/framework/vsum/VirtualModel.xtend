package tools.vitruv.framework.vsum

import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.util.datatypes.ModelInstance
import java.io.File
import java.util.List
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.change.uuid.UuidGeneratorAndResolver

interface VirtualModel {
	def File getFolder();
	def List<PropagatedChange> propagateChange(VitruviusChange change);
	def void reverseChanges(List<PropagatedChange> changes);
	def ModelInstance getModelInstance(VURI modelVuri);
	def UuidGeneratorAndResolver getUuidGeneratorAndResolver();
}