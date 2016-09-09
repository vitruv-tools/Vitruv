package tools.vitruv.framework.change.description.impl

import tools.vitruv.framework.change.description.ConcreteChange
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.change.preparation.ChangeToEChangeConverter

class EmptyChangeImpl extends AbstractConcreteChange implements ConcreteChange {
	
	new(VURI vuri) {
		super(vuri);
	}
	
	override isPrepared() {
		return true;
	}
	
	override prepare(ChangeToEChangeConverter preparer) {
		// Do nothing, empty changes do not have to be prepared
	}
	
}