package tools.vitruv.framework.vsum

import tools.vitruv.framework.vsum.InternalVirtualModel

interface InternalTestVirtualModel extends InternalVirtualModel {
	def ModelRepository getModelRepository()
}
