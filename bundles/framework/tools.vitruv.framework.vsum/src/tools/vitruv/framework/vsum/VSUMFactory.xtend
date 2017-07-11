package tools.vitruv.framework.vsum

import java.io.File
import tools.vitruv.framework.vsum.impl.VSUMFactoryImpl
import tools.vitruv.framework.userinteraction.UserInteracting

interface VSUMFactory {
	VSUMFactory instance = VSUMFactoryImpl::init

	def InternalVirtualModel createVirtualModel(File folder, UserInteracting userInteracting,
		VirtualModelConfiguration modelConfiguration)
}
