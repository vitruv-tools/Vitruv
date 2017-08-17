package tools.vitruv.framework.versioning.impl

import java.util.List
import java.util.Map

import org.eclipse.xtext.xbase.lib.Functions.Function0

import tools.vitruv.framework.change.copy.EChangeCopier
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.versioning.Reapplier
import tools.vitruv.framework.versioning.extensions.URIRemapper
import tools.vitruv.framework.vsum.VersioningVirtualModel

class ReapplierImpl implements Reapplier {
	static extension EChangeCopier = EChangeCopier::createEChangeCopier(#{})
	static extension URIRemapper = URIRemapper::instance

	override reapply(
		List<PropagatedChange> changesToRollBack,
		List<EChange> echangesToReapply,
		VersioningVirtualModel virtualModel
	) {
		reapplyIntern(null, changesToRollBack, echangesToReapply, virtualModel, [
			virtualModel.allUnresolvedPropagatedChangesSinceLastCommit
		], #{})
	}

	override reapply(
		List<PropagatedChange> changesToRollBack,
		List<EChange> echangesToReapply,
		VersioningVirtualModel virtualModel,
		VURI vuri
	) {
		reapply(changesToRollBack, echangesToReapply, virtualModel, vuri, #{})
	}

	override reapply(
		List<PropagatedChange> changesToRollBack,
		List<EChange> echangesToReapply,
		VersioningVirtualModel virtualModel,
		VURI vuri,
		Map<VURI, VURI> replaceMap
	) {
		if(null === vuri)
			throw new IllegalStateException("VURI vuri must not be null!")
		reapplyIntern(vuri, changesToRollBack, echangesToReapply, virtualModel, [
			virtualModel.getUnresolvedPropagatedChangesSinceLastCommit(vuri)
		], replaceMap)
	}

	private static def reapplyIntern(
		VURI vuri,
		List<PropagatedChange> changesToRollBack,
		List<EChange> echangesToReapply,
		VersioningVirtualModel virtualModel,
		Function0<List<PropagatedChange>> fetchPropagatedChanges,
		Map<VURI, VURI> replaceMap
	) {
		var changesUntilNowAfterReverse = 0

		if(!changesToRollBack.empty) {
			changesToRollBack.reverseView.forEach [
				virtualModel.reverseChanges(#[it])
			]
			changesUntilNowAfterReverse = fetchPropagatedChanges.apply.length
		}
		val consumers = replaceMap.entrySet.map[createEChangeRemapFunction(key, value)].toList.immutableCopy
		val echangeMapFunction = [ EChange e |
			consumers.forEach [ consumer |
				consumer.accept(e)
			]
		]

		val newEChanges = echangesToReapply.map[copy(it)].toList.immutableCopy
		newEChanges.forEach[echangeMapFunction.apply(it)]
		newEChanges.map [
			VitruviusChangeFactory::instance.createEMFModelChangeFromEChanges(#[it])
		].forEach [
			virtualModel.propagateChange(it)
		]
		val newChanges = fetchPropagatedChanges.apply.drop(changesUntilNowAfterReverse).toList
		return newChanges
	}

}
