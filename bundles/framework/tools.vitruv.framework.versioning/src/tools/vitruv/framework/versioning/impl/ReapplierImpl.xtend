package tools.vitruv.framework.versioning.impl

import java.util.List
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.versioning.Reapplier
import tools.vitruv.framework.vsum.VersioningVirtualModel

class ReapplierImpl implements Reapplier {

	override reapply(
		VURI vuri,
		List<PropagatedChange> changesToRollBack,
		List<EChange> echangesToReapply,
		VersioningVirtualModel virtualModel
	) {
		var changesUntilNowAfterReverse = 0

		if (!changesToRollBack.empty) {
			changesToRollBack.reverseView.forEach [
				virtualModel.reverseChanges(#[it])
			]
			changesUntilNowAfterReverse = virtualModel.getUnresolvedPropagatedChanges(vuri).length
		}

		echangesToReapply.map [
			VitruviusChangeFactory::instance.createEMFModelChangeFromEChanges(#[it], vuri)
		].forEach [
			virtualModel.propagateChange(it)
		]
		val newChanges = virtualModel.getUnresolvedPropagatedChanges(vuri).drop(changesUntilNowAfterReverse).toList
		return newChanges
	}

}
