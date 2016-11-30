package tools.vitruv.framework.vsum

import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.util.datatypes.ModelInstance

interface VirtualModel {
	def String getName();
	def void propagateChange(VitruviusChange change);
	def ModelInstance getModelInstance(VURI modelVuri);
}