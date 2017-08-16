package tools.vitruv.framework.versioning.impl

import java.util.List
import org.eclipse.xtext.xbase.lib.Functions.Function0
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.versioning.Reapplier
import tools.vitruv.framework.vsum.VersioningVirtualModel
import tools.vitruv.framework.versioning.extensions.URIRemapper
import java.util.function.Consumer
import tools.vitruv.framework.change.copy.EChangeCopier

class ReapplierImpl implements Reapplier {
	static extension EChangeCopier = EChangeCopier::createEChangeCopier(#{})
	static extension URIRemapper = URIRemapper::instance

	override reapply(
		VURI vuri,
		List<PropagatedChange> changesToRollBack,
		List<EChange> echangesToReapply,
		VersioningVirtualModel virtualModel
	) {
		if(null === vuri)
			throw new IllegalStateException("VURI vuri must not be null!")
		reapplyIntern(vuri, changesToRollBack, echangesToReapply, virtualModel, [
			virtualModel.getUnresolvedPropagatedChangesSinceLastCommit(vuri)
		])
	}

	override reapply(
		List<PropagatedChange> changesToRollBack,
		List<EChange> echangesToReapply,
		VersioningVirtualModel virtualModel
	) {
		reapplyIntern(null, changesToRollBack, echangesToReapply, virtualModel, [
			virtualModel.allUnresolvedPropagatedChangesSinceLastCommit
		])
	}

	private static def Consumer<EChange> createRemapFunction(VURI vuri, VersioningVirtualModel virtualModel) {
		if(null !== vuri) {
			val otherVURI = virtualModel.getMappedVURI(vuri)
			if(null !== otherVURI) {
				return createEChangeRemapFunction(vuri, otherVURI)
			}
		}
		return []
	}

	private static def reapplyIntern(
		VURI vuri,
		List<PropagatedChange> changesToRollBack,
		List<EChange> echangesToReapply,
		VersioningVirtualModel virtualModel,
		Function0<List<PropagatedChange>> fetchPropagatedChanges
	) {
		var changesUntilNowAfterReverse = 0
		val echangeMapFunction = createRemapFunction(vuri, virtualModel)
		if(!changesToRollBack.empty) {
			changesToRollBack.reverseView.forEach [
				virtualModel.reverseChanges(#[it])
			]
			changesUntilNowAfterReverse = fetchPropagatedChanges.apply.length
		}
		val newEChanges = echangesToReapply.map[copy(it)].toList.immutableCopy
		newEChanges.forEach[echangeMapFunction.accept(it)]
		newEChanges.map [
			VitruviusChangeFactory::instance.createEMFModelChangeFromEChanges(#[it])
		].forEach [
			virtualModel.propagateChange(it)
		]
		val newChanges = fetchPropagatedChanges.apply.drop(changesUntilNowAfterReverse).toList
		return newChanges
	}

}
