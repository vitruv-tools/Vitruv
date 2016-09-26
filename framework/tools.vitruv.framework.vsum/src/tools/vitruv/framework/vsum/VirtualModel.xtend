package tools.vitruv.framework.vsum

import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.metamodel.ModelInstance
import tools.vitruv.framework.util.datatypes.VURI

interface VirtualModel {
	def void propagateChange(VitruviusChange change);
	def ModelInstance getModelInstance(VURI modelVuri);
}