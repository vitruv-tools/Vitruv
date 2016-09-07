package tools.vitruvius.framework.change.description.impl

import tools.vitruvius.framework.change.description.impl.ConcreteChangeImpl
import tools.vitruvius.framework.change.description.ConcreteChange
import tools.vitruvius.framework.util.datatypes.VURI
import tools.vitruvius.framework.change.preparation.ChangePreparing

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