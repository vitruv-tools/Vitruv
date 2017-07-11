package tools.vitruv.framework.vsum.impl

import tools.vitruv.framework.vsum.VirtualModelImpl
import tools.vitruv.framework.vsum.InternalTestVirtualModel
import java.io.File
import tools.vitruv.framework.vsum.VirtualModelConfiguration
import tools.vitruv.framework.userinteraction.UserInteracting

class InternalTestVirtualModelImpl extends VirtualModelImpl implements InternalTestVirtualModel {

	new(File folder, UserInteracting userInteracting, VirtualModelConfiguration modelConfiguration) {
		super(folder, userInteracting, modelConfiguration)
	}

	override getModelRepository() {
		resourceRepository
	}

}
