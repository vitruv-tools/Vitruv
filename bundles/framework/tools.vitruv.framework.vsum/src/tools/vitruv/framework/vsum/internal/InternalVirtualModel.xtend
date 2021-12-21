package tools.vitruv.framework.vsum.internal

import tools.vitruv.framework.correspondence.CorrespondenceModel
import org.eclipse.emf.common.util.URI
import tools.vitruv.framework.vsum.VirtualModel
import tools.vitruv.framework.vsum.views.ChangeableViewSource

interface InternalVirtualModel extends VirtualModel, ChangeableViewSource {
	def CorrespondenceModel getCorrespondenceModel()
	def ModelInstance getModelInstance(URI modelUri)
	def void addPropagatedChangeListener(PropagatedChangeListener propagatedChangeListener)
	def void removePropagatedChangeListener(PropagatedChangeListener propagatedChangeListener)
	def void dispose()
}