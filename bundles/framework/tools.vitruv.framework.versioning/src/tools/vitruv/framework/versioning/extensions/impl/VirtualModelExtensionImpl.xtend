package tools.vitruv.framework.versioning.extensions.impl

import tools.vitruv.framework.versioning.extensions.VirtualModelExtension
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.vsum.VersioningVirtualModel

class VirtualModelExtensionImpl implements VirtualModelExtension {
	static def VirtualModelExtension init() {
		new VirtualModelExtensionImpl
	}

	private new() {
	}

	override getChangeMatches(VersioningVirtualModel v, VURI vuri) {
		// FIXME PS drop(1) is a dirty hack to exclude the CreateAndInsertRoot EChanges
		v.getUnresolvedPropagatedChanges(vuri).drop(1).toList
	}

	override getChangeMatchesFrom(VersioningVirtualModel v, VURI vuri, String otherId) {
		v.getChangeMatches(vuri).dropWhile[id !== otherId].toList
	}

	override getChangeMatchesFromTo(VersioningVirtualModel v, VURI vuri, String from, String to) {
		v.getChangeMatchesFrom(vuri, from).takeWhile[id !== to].toList
	}

}
