package tools.vitruv.framework.change.description.impl

import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.interaction.UserInteractionBase
import tools.vitruv.framework.change.id.IdResolver

class EmptyChange implements TransactionalChange {
	public static val INSTANCE = new EmptyChange()
	private new(){}

	override containsConcreteChange() {
		false
	}
	
	override getChangedURIs() {
		emptySet
	}

	override getEChanges() {
		emptyList
	}
	
	override resolveAndApply(IdResolver idResolver) {
		// Do nothing		
	}

	override unresolve() {
		// Do nothing
	}
	
	override getAffectedEObjects() {
		emptySet
	}
	
	override getAffectedEObjectIds() {
		emptySet
	}

	override getAffectedAndReferencedEObjects() {
		emptySet
	}

	override getAffectedAndReferencedEObjectIds() {
		emptySet
	}

	override getUserInteractions() {
		emptyList
	}

	override setUserInteractions(Iterable<UserInteractionBase> userInputs) {
		throw new UnsupportedOperationException("Adding interactions to an empty change is not supported")
	}

	override EmptyChange copy() {
		this
	}
	
	override toString() {
		"empty change"
	}
}
