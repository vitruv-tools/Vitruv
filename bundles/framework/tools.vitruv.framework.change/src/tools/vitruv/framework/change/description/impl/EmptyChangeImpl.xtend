package tools.vitruv.framework.change.description.impl

import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.uuid.UuidResolver
import java.util.Collection
import tools.vitruv.framework.change.interaction.UserInputBase

class EmptyChangeImpl implements TransactionalChange {
	private val VURI vuri;
	private Collection<UserInputBase> userInputs;
	
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
		
	}
	
	override resolveAfterAndApplyBackward(UuidResolver uuidResolver) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
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
	
	override getUserInputs() {
        return userInputs
    }
    
    override setUserInputs(Collection<UserInputBase> userInputs) {
        this.userInputs = userInputs
    }
}