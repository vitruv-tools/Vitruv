package tools.vitruv.framework.versioning.impl

import tools.vitruv.framework.versioning.Reapplier
import java.util.List
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.vsum.VirtualModel
import tools.vitruv.framework.change.description.VitruviusChangeFactory

class ReapplierImpl implements Reapplier {

	override reapply(
		List<PropagatedChange> changesToRollBack,
		List<EChange> echangesToReapply,
		VirtualModel virtualModel
	) {
		val vuri = changesToRollBack.get(0).originalChange.URI
		val changesToRollbackLength = changesToRollBack.length
		val changesUntilNow = virtualModel.getResolvedPropagatedChanges(vuri).length
		changesToRollBack.reverseView.forEach [
			virtualModel.reverseChanges(#[it])
		]

		val changesUntilNowAfterReverse = virtualModel.getUnresolvedPropagatedChanges(vuri).length
		if (changesUntilNow - changesToRollbackLength !== changesUntilNowAfterReverse)
			throw new IllegalStateException('''
				The number of changes recorded after the reverse should be 
				«changesUntilNow» - «changesToRollBack.length» !== «changesUntilNowAfterReverse»
			''')
		echangesToReapply.map [
			VitruviusChangeFactory::instance.createEMFModelChangeFromEChanges(#[it], vuri)
		].forEach [
			virtualModel.propagateChange(it)
		]
		val newChanges = virtualModel.getUnresolvedPropagatedChanges(vuri).drop(changesUntilNowAfterReverse).toList
		return newChanges
	}

}
