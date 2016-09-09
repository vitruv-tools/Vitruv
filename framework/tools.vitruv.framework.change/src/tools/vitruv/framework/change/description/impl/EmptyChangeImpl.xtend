package tools.vitruv.framework.change.description.impl

import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.change.description.TransactionalChange

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
	
	override isPrepared() {
		return true;
	}
	
	override prepare() {
		// Do nothing
	}
	
	override getEChanges() {
		return #[];
	}
	
	override getURI() {
		return vuri;
	}
		
}