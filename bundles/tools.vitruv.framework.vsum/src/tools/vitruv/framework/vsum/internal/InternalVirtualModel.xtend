package tools.vitruv.framework.vsum.internal

import org.eclipse.emf.common.util.URI
import tools.vitruv.change.correspondence.CorrespondenceModel
import tools.vitruv.framework.views.ChangeableViewSource
import tools.vitruv.framework.vsum.VirtualModel

interface InternalVirtualModel extends VirtualModel, ChangeableViewSource {
	def CorrespondenceModel getCorrespondenceModel()

	def ModelInstance getModelInstance(URI modelUri)

	def void dispose()
}
