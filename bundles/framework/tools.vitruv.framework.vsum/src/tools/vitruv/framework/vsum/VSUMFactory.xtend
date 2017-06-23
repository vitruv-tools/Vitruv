package tools.vitruv.framework.vsum

import java.io.File
import tools.vitruv.framework.vsum.impl.VSUMFactoryImpl

interface VSUMFactory {
	VSUMFactory instance = VSUMFactoryImpl::init

	def InternalVirtualModel createVirtualModel(File folder, VirtualModelConfiguration modelConfiguration)
}
