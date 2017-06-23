package tools.vitruv.framework.vsum

import java.io.File
import tools.vitruv.framework.vsum.impl.VirtualModelManagerImpl

interface VirtualModelManager {
	VirtualModelManager currentInstance = VirtualModelManagerImpl::init

	static def getInstance() {
		return currentInstance
	}

	def InternalVirtualModel getVirtualModel(File folder)

	def void putVirtualModel(InternalVirtualModel model)
}
