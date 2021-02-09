package tools.vitruv.framework.change.description.impl

import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.uuid.UuidResolver
import tools.vitruv.framework.change.interaction.UserInteractionBase
import tools.vitruv.framework.change.description.VitruviusChange

class EmptyChange implements TransactionalChange {
	public static val INSTANCE = new EmptyChange()
	private new(){}

	override containsConcreteChange() {
		false
	}
	
	override getChangedVURIs() {
		emptyList
	}

	override getEChanges() {
		emptyList
	}
	
	override resolveBeforeAndApplyForward(UuidResolver uuidResolver) {
		// Do nothing		
	}

	override resolveAfterAndApplyBackward(UuidResolver uuidResolver) {
		// Do nothing
	}

	override unresolveIfApplicable() {
		// Do nothing
	}

	override getAffectedEObjects() {
		emptyList
	}

	override getAffectedEObjectIds() {
		emptyList
	}

	override getUserInteractions() {
		emptyList
	}

	override setUserInteractions(Iterable<UserInteractionBase> userInputs) {
		throw new UnsupportedOperationException("Adding interactions to an empty change is not supported")
	}

	override changedEObjectEquals(VitruviusChange change) {
		change.affectedEObjects.empty
	}
	
	override EmptyChange copy() {
		this
	}
	
	override toString() {
		"empty change"
	}
}
