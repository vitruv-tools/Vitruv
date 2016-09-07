package tools.vitruvius.framework.change.description.impl

import tools.vitruvius.framework.change.description.ConcreteChange
import java.util.List
import tools.vitruvius.framework.change.echange.EChange
import java.util.ArrayList
import tools.vitruvius.framework.util.datatypes.VURI
import tools.vitruvius.framework.change.preparation.ChangePreparing

abstract class ConcreteChangeImpl implements ConcreteChange {
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
	
	override prepare(ChangePreparing preparer) {
		if (prepared) {
			throw new IllegalStateException("The change is already prepared.");
		}
		this.eChanges.addAll(preparer.prepareChange(this));
		this.prepared = true;
	}
	
}