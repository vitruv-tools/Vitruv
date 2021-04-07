package tools.vitruv.framework.vsum.internal

import tools.vitruv.framework.correspondence.CorrespondenceModel
import org.eclipse.emf.common.util.URI
import tools.vitruv.framework.vsum.VirtualModel
import tools.vitruv.framework.uuid.UuidResolver
import tools.vitruv.framework.vsum.variability.InternalVaveModel

interface InternalVirtualModel extends VirtualModel {
	def CorrespondenceModel getCorrespondenceModel()
	def InternalVaveModel getVaveModel()
	def ModelInstance getModelInstance(URI modelUri)
	def UuidResolver getUuidResolver()
	def void addPropagatedChangeListener(PropagatedChangeListener propagatedChangeListener)
	def void removePropagatedChangeListener(PropagatedChangeListener propagatedChangeListener)
	def void dispose()
}