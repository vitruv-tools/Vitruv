package tools.vitruv.framework.vsum.internal

import tools.vitruv.framework.correspondence.CorrespondenceModel
import org.eclipse.emf.common.util.URI
import tools.vitruv.framework.vsum.VirtualModel
import tools.vitruv.framework.change.id.IdResolver

interface InternalVirtualModel extends VirtualModel {
	def CorrespondenceModel getCorrespondenceModel()
	def ModelInstance getModelInstance(URI modelUri)
	def IdResolver getIdResolver()
	def void addPropagatedChangeListener(PropagatedChangeListener propagatedChangeListener)
	def void removePropagatedChangeListener(PropagatedChangeListener propagatedChangeListener)
	def void dispose()
}