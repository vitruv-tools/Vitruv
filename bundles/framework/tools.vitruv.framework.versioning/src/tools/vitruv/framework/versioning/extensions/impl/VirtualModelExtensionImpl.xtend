package tools.vitruv.framework.versioning.extensions.impl

import tools.vitruv.framework.versioning.extensions.VirtualModelExtension
import tools.vitruv.framework.vsum.VirtualModel
import tools.vitruv.framework.util.datatypes.VURI

class VirtualModelExtensionImpl implements VirtualModelExtension {
	static def VirtualModelExtension init() {
		new VirtualModelExtensionImpl
	}

	private new() {
	}

	override getChangeMatches(VirtualModel v, VURI vuri) {
		// FIXME PS drop(1) is a dirty hack to exclude the CreateAndInsertRoot EChanges
		v.getUnresolvedPropagatedChanges(vuri).drop(1).toList
	}

}
