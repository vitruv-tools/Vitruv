package tools.vitruv.framework.vsum

import java.io.File
import java.util.List

import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.util.datatypes.ModelInstance
import tools.vitruv.framework.util.datatypes.VURI

interface VirtualModel {
	def File getFolder()

	def List<PropagatedChange> propagateChange(VitruviusChange change)

	def ModelInstance getModelInstance(VURI modelVuri)

	def void reverseChanges(List<PropagatedChange> changes)
}
