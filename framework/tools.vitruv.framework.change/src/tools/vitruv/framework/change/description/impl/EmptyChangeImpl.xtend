package tools.vitruv.framework.change.description.impl

import tools.vitruv.framework.change.description.impl.ConcreteChangeImpl
import tools.vitruv.framework.change.description.ConcreteChange
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.change.preparation.ChangePreparing

class EmptyChangeImpl extends ConcreteChangeImpl implements ConcreteChange {
	
	new(VURI vuri) {
		super(vuri);
	}
	
	override isPrepared() {
		return true;
	}
	
	override prepare(ChangePreparing preparer) {
		// Do nothing, empty changes do not have to be prepared
	}
	
}