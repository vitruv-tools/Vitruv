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
		v.getUnresolvedPropagatedChanges(vuri).toList
	}

	override getChangeMatchesFrom(VersioningVirtualModel v, VURI vuri, String otherId) {
		v.getChangeMatches(vuri).dropWhile[id !== otherId].toList
	}

	override getChangeMatchesFromTo(VersioningVirtualModel v, VURI vuri, String from, String to) {
		v.getChangeMatchesFrom(vuri, from).takeWhile[id !== to].toList
	}

}
