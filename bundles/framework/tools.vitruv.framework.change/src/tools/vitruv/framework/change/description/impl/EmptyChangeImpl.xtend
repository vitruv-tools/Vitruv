package tools.vitruv.framework.change.description.impl

import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.uuid.UuidResolver

class EmptyChangeImpl implements TransactionalChange {
	private val VURI vuri;
	
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
	
	override applyBackward() throws IllegalStateException {
		// Nothing to be done
	}
	
	override applyForward() throws IllegalStateException {
		// Nothing to be done
	}
	
	override resolveBeforeAndApplyForward(UuidResolver uuidResolver) {
		
	}
	
	override resolveAfterAndApplyBackward(UuidResolver uuidResolver) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	override applyBackwardIfLegacy() {
		// Do nothing
	}
	
	override unresolveIfNonLegacy() {
		// Do nothing
	}
	
	override getAffectedEObjects() {
		return #[]
	}
	
}