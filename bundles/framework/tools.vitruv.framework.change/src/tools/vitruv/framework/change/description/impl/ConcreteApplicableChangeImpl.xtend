package tools.vitruv.framework.change.description.impl

import org.eclipse.emf.ecore.EObject

import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.resolve.EChangeUnresolver
import tools.vitruv.framework.tuid.TuidManager
import tools.vitruv.framework.uuid.UuidResolver

import static extension tools.vitruv.framework.change.echange.resolve.EChangeResolverAndApplicator.*
import static com.google.common.base.Preconditions.checkState

class ConcreteApplicableChangeImpl extends ConcreteChangeImpl {
	new(EChange eChange) {
		super(eChange)
	}

	override resolveBeforeAndApplyForward(UuidResolver uuidResolver) {
		// TODO HK Make a copy of the complete change instead of replacing it internally
		val resolvedChange = this.EChange.resolveBefore(uuidResolver)
		checkState(resolvedChange !== null, "Failed to resolve this change:%s", this.EChange)
		this.EChange = resolvedChange
		this.registerOldObjectTuidsForUpdate(getObjectsWithPotentiallyModifiedTuids)
		this.EChange.applyForward;
		this.updateTuids
	}

	override resolveAfterAndApplyBackward(UuidResolver uuidResolver) {
		// TODO HK Make a copy of the complete change instead of replacing it internally
		val resolvedChange = this.EChange.resolveAfter(uuidResolver)
		checkState(resolvedChange !== null, "Failed to resolve this change:%s", this.EChange)
		this.EChange = resolvedChange
		this.registerOldObjectTuidsForUpdate(getObjectsWithPotentiallyModifiedTuids)
		this.EChange.applyBackward;
		this.updateTuids
	}

	private def getObjectsWithPotentiallyModifiedTuids() {
		// We currently support 3 hierarchy layers upwards update. This is necessary
		// e.g. for Operations whose TUIDs depend on the values of their parameter type references.
		// This number of layers may still be too few, this is just a random number.
		this.affectedEObjects.map[#{it, it.eContainer, it.eContainer?.eContainer, it.eContainer?.eContainer?.eContainer}].flatten.filterNull.toSet
	}

	private def void registerOldObjectTuidsForUpdate(Iterable<EObject> objects) {
		val tuidManager = TuidManager.instance
		for (object : objects) {
			tuidManager.registerObjectUnderModification(object)
		}
	}

	private def void updateTuids() {
		TuidManager.instance.updateTuidsOfRegisteredObjects
		TuidManager.instance.flushRegisteredObjectsUnderModification
	}

	override unresolveIfApplicable() {
		EChanges.forEach [EChangeUnresolver.unresolve(it)]
	}
	
	override ConcreteApplicableChangeImpl copy() {
		new ConcreteApplicableChangeImpl(clonedEChange)
	} 
}
