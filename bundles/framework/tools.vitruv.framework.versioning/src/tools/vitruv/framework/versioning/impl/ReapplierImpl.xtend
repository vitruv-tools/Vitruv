package tools.vitruv.framework.versioning.impl

import java.util.List
import org.eclipse.xtext.xbase.lib.Functions.Function0
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
		reapplyIntern(changesToRollBack, echangesToReapply, virtualModel, [
			virtualModel.getUnresolvedPropagatedChanges(vuri)
		])
	}

	override reapply(
		List<PropagatedChange> changesToRollBack,
		List<EChange> echangesToReapply,
		VersioningVirtualModel virtualModel
	) {
		reapplyIntern(changesToRollBack, echangesToReapply, virtualModel, [
			virtualModel.allUnresolvedPropagatedChangesSinceLastCommit
		])
	}

	private static def reapplyIntern(
		List<PropagatedChange> changesToRollBack,
		List<EChange> echangesToReapply,
		VersioningVirtualModel virtualModel,
		Function0<List<PropagatedChange>> fetchPropagatedChanges
	) {
		var changesUntilNowAfterReverse = 0

		if (!changesToRollBack.empty) {
			changesToRollBack.reverseView.forEach [
				virtualModel.reverseChanges(#[it])
			]
			changesUntilNowAfterReverse = fetchPropagatedChanges.apply.length
		}

		echangesToReapply.map [
			VitruviusChangeFactory::instance.createEMFModelChangeFromEChanges(#[it])
		].forEach [
			virtualModel.propagateChange(it)
		]
		val newChanges = fetchPropagatedChanges.apply.drop(changesUntilNowAfterReverse).toList
		return newChanges
	}

}
