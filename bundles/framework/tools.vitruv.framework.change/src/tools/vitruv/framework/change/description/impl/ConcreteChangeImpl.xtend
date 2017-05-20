package tools.vitruv.framework.change.description.impl

import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.util.datatypes.VURI

class ConcreteChangeImpl extends AbstractConcreteChange {
	public new(EChange eChange, VURI vuri) {
		super(vuri)
		this.eChange = eChange
	}

	public override String toString() {
		'''«class.simpleName»: VURI: «URI» EChange: «eChange»'''
	}

}
