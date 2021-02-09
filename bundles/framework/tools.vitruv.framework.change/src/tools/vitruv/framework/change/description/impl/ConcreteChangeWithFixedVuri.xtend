package tools.vitruv.framework.change.description.impl

import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.util.datatypes.VURI

/**
 * A change that allows to explicitly set the URI of the affected resource. This can be necessary when producing 
 * artificial changes that do not correspond to actual changes to real EMF resources. Views that directly monitor
 * EMF resources should have no need for using this change.
 */
class ConcreteChangeWithFixedVuri extends ConcreteChangeImpl {
	val VURI fixedVuri
	
	new(EChange eChange, VURI fixedVuri) {
		super(eChange)
		this.fixedVuri = fixedVuri
	}
	
	override getChangedVURI() {
		fixedVuri
	}
	
	override ConcreteChangeWithFixedVuri copy() {
		new ConcreteChangeWithFixedVuri(clonedEChange, fixedVuri)
	}
}
