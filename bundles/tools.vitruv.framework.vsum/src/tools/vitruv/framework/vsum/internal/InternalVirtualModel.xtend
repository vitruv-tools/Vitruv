package tools.vitruv.framework.vsum.internal

import org.eclipse.emf.common.util.URI
import tools.vitruv.framework.views.ChangeableViewSource
import tools.vitruv.framework.vsum.VirtualModel
import tools.vitruv.change.correspondence.Correspondence
import tools.vitruv.change.correspondence.view.EditableCorrespondenceModelView
import java.util.function.Consumer

interface InternalVirtualModel extends VirtualModel, ChangeableViewSource {
	def EditableCorrespondenceModelView<Correspondence> getCorrespondenceModel()

	@Deprecated(since="3.1.0")
	def ModelInstance getModelInstance(URI modelUri)
	
	def void validate(Consumer<ModelRepository> validator)

	def void dispose()
}
