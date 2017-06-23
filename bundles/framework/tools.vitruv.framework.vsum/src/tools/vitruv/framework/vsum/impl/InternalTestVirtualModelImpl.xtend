package tools.vitruv.framework.vsum.impl

import tools.vitruv.framework.vsum.VirtualModelImpl
import tools.vitruv.framework.vsum.InternalTestVirtualModel
import java.io.File
import tools.vitruv.framework.vsum.VirtualModelConfiguration

class InternalTestVirtualModelImpl extends VirtualModelImpl implements InternalTestVirtualModel {

	new(File folder, VirtualModelConfiguration modelConfiguration) {
		super(folder, modelConfiguration)
	}

	override getModelRepository() {
		modelRepository
	}

}
