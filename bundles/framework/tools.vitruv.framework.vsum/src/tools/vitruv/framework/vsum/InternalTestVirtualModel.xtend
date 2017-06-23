package tools.vitruv.framework.vsum

import tools.vitruv.framework.vsum.InternalVirtualModel
import tools.vitruv.framework.domains.repository.ModelRepository

interface InternalTestVirtualModel extends InternalVirtualModel {
	def ModelRepository getModelRepository()
}
