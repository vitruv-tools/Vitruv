package tools.vitruv.framework.change.description.impl

import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.uuid.UuidResolver
import tools.vitruv.framework.change.interaction.UserInteractionBase
import tools.vitruv.framework.change.description.VitruviusChange

class EmptyChangeImpl extends AbstractVitruviusChangeImpl implements TransactionalChange {
	val VURI vuri;

	new(VURI vuri) {
		this.vuri = vuri;
	}

	override containsConcreteChange() {
		return true;
	}

	override validate() {
		return true;
	}

	override getEChanges() {
		return #[];
	}

	override getURI() {
		return vuri;
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
		return #[]
	}

	override getAffectedEObjectIds() {
		return #[]
	}

	override getUserInteractions() {
		#[]
	}

	override setUserInteractions(Iterable<UserInteractionBase> userInputs) {
		throw new UnsupportedOperationException("Adding interactions to an empty change is not supported")
	}

	override changedEObjectEquals(VitruviusChange change) {
		return change.affectedEObjects.empty
	}
	
}
