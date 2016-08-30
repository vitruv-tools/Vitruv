package edu.kit.ipd.sdq.vitruvius.framework.change.description.impl

import edu.kit.ipd.sdq.vitruvius.framework.change.description.impl.ConcreteChangeImpl
import edu.kit.ipd.sdq.vitruvius.framework.change.description.ConcreteChange
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.framework.change.preparation.ChangePreparing

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