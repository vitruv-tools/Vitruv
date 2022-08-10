package tools.vitruv.framework.vsum.internal

import org.eclipse.emf.common.util.URI
import tools.vitruv.framework.views.ChangeableViewSource
import tools.vitruv.framework.vsum.VirtualModel
import tools.vitruv.change.correspondence.Correspondence
import tools.vitruv.change.correspondence.view.EditableCorrespondenceModelView

interface InternalVirtualModel extends VirtualModel, ChangeableViewSource {
	def EditableCorrespondenceModelView<Correspondence> getCorrespondenceModel()

	def ModelInstance getModelInstance(URI modelUri)

	def void dispose()
}
