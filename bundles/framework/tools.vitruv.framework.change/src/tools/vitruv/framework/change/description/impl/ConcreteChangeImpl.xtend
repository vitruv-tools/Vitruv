package tools.vitruv.framework.change.description.impl

import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.util.datatypes.VURI

class ConcreteChangeImpl extends AbstractConcreteChange {
	new(EChange eChange, VURI vuri) {
		super(vuri)
		this.eChange = eChange
	}

	override toString() {
		class.simpleName + ": VURI: " + URI + " EChange: " + eChange
	}
}
