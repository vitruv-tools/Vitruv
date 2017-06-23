package tools.vitruv.framework.vsum

import java.io.File
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.util.datatypes.ModelInstance
import tools.vitruv.framework.util.datatypes.VURI

interface VirtualModel {
	def File getFolder()

	def void propagateChange(VitruviusChange change)

	def ModelInstance getModelInstance(VURI modelVuri)
}
