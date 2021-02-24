package tools.vitruv.framework.change.description.impl

import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.resolve.EChangeUnresolver
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
		checkState(resolvedChange !== null, "Failed to resolve this change: %s", this.EChange)
		this.EChange = resolvedChange
		this.EChange.applyForward
	}

	override resolveAfterAndApplyBackward(UuidResolver uuidResolver) {
		// TODO HK Make a copy of the complete change instead of replacing it internally
		val resolvedChange = this.EChange.resolveAfter(uuidResolver)
		checkState(resolvedChange !== null, "Failed to resolve this change: %s", this.EChange)
		this.EChange = resolvedChange
		this.EChange.applyBackward
	}

	override unresolveIfApplicable() {
		EChanges.forEach [EChangeUnresolver.unresolve(it)]
	}
	
	override ConcreteApplicableChangeImpl copy() {
		new ConcreteApplicableChangeImpl(clonedEChange)
	} 
}
