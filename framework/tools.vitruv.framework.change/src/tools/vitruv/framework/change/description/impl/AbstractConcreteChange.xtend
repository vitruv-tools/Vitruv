package tools.vitruv.framework.change.description.impl

import tools.vitruv.framework.change.description.ConcreteChange
import java.util.List
import tools.vitruv.framework.change.echange.EChange
import java.util.ArrayList
import tools.vitruv.framework.util.datatypes.VURI

abstract class AbstractConcreteChange implements ConcreteChange {
	protected final List<EChange> eChanges;
	final VURI vuri;
	boolean prepared;
	
	new(VURI vuri) {
		this.eChanges = new ArrayList<EChange>();
		this.vuri = vuri;
		this.prepared = false;
	}
	
	override containsConcreteChange() {
		return true;
	}
	
	override validate() {
		return containsConcreteChange() && URI != null;
	}
	
	override getEChanges() {
		return new ArrayList<EChange>(eChanges);
	}
	
	override getURI() {
		return vuri;
	}
		
	override isPrepared() {
		return this.prepared;
	}

	protected def setPrepated() {
		this.prepared = true;
	}	
}