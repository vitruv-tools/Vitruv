package tools.vitruv.framework.vsum.impl

import tools.vitruv.framework.vsum.VSUMFactory
import java.io.File
import tools.vitruv.framework.vsum.VirtualModelConfiguration
import tools.vitruv.framework.userinteraction.UserInteracting

class VSUMFactoryImpl implements VSUMFactory {
	static def VSUMFactory init() {
		new VSUMFactoryImpl
	}

	private new() {
	}

	override createVirtualModel(File folder, UserInteracting userInteracting,
		VirtualModelConfiguration modelConfiguration) {
		new InternalTestVirtualModelImpl(folder, userInteracting, modelConfiguration)
	}

}
