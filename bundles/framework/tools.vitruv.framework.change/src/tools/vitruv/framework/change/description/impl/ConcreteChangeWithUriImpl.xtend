package tools.vitruv.framework.change.description.impl

import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.change.echange.EChange

class ConcreteChangeWithUriImpl extends ConcreteChangeImpl {
	val VURI vuri;
	
	new (VURI vuri, EChange eChange) {
		super(eChange);
		this.vuri = vuri;
	}
	
	override getURI() {
		return vuri;
	}
	
}