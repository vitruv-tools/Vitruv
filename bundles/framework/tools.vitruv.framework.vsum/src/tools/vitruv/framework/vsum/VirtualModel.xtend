package tools.vitruv.framework.vsum

import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.util.datatypes.ModelInstance
import java.io.File

interface VirtualModel {
	def File getFolder();
	def void propagateChange(VitruviusChange change);
	def ModelInstance getModelInstance(VURI modelVuri);
}
