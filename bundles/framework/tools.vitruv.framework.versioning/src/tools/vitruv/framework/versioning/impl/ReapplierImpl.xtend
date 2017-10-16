package tools.vitruv.framework.versioning.impl

import java.util.List
import java.util.Map

import org.eclipse.xtext.xbase.lib.Functions.Function0

import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.copy.EChangeCopier
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.versioning.Reapplier
import tools.vitruv.framework.versioning.extensions.URIRemapper
import tools.vitruv.framework.vsum.InternalTestVersioningVirtualModel
import tools.vitruv.framework.vsum.VersioningVirtualModel

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.mapFixed
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference
import tools.vitruv.framework.change.echange.compound.CreateAndInsertNonRoot
import tools.vitruv.framework.change.echange.compound.RemoveAndDeleteNonRoot
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute

class ReapplierImpl implements Reapplier {
	// Static extensions.
	static extension EChangeCopier = EChangeCopier::createEChangeCopier(#{})
	static extension URIRemapper = URIRemapper::instance

	// Overridden methods.
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
			(virtualModel as InternalTestVersioningVirtualModel).getUnresolvedPropagatedChangesSinceLastCommit(vuri)
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
		val consumers = replaceMap.entrySet.mapFixed [
			createEChangeRemapFunction(key, value)
		]
		val echangeMapFunction = [ EChange e |
			consumers.forEach [ consumer |
				consumer.accept(e)
			]
		]
		// PS This immutable copy is important.
		val newEChanges = echangesToReapply.mapFixed[copy(it)]
		newEChanges.forEach(echangeMapFunction)
		// PS Dirty hack
		newEChanges.filter[!(it instanceof ReplaceSingleValuedEReference<?, ?>)].map [
			VitruviusChangeFactory::instance.createEMFModelChangeFromEChanges(#[it])
		].forEach [
			virtualModel.propagateChange(it)
		]
		val newChanges = fetchPropagatedChanges.apply.drop(changesUntilNowAfterReverse).toList
		// PS Dirty hack to remove CreateAndInsert + RemoveAndDelete
		if(newChanges.length === 2) {
			val firstPropagatedChange = newChanges.get(0)
			val secondPropagatedChange = newChanges.get(1)

			val hasCreateEChange = firstPropagatedChange.originalChange.EChanges.exists [
				it instanceof CreateAndInsertNonRoot<?, ?>
			]
			val hasRemoveEChange = secondPropagatedChange.originalChange.EChanges.exists [
				it instanceof RemoveAndDeleteNonRoot<?, ?>
			]
			val hasReplaceEChange =secondPropagatedChange.originalChange.EChanges.exists[it instanceof ReplaceSingleValuedEAttribute<?,?>]
			if(hasCreateEChange && (hasRemoveEChange || hasReplaceEChange)) {
				return #[secondPropagatedChange]
			}
		}
		return newChanges
	}

}
