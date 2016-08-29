package edu.kit.ipd.sdq.vitruvius.framework.contracts.change.impl

import edu.kit.ipd.sdq.vitruvius.framework.contracts.change.ConcreteChange
import java.util.List
import edu.kit.ipd.sdq.vitruvius.framework.changes.echange.EChange
import java.util.ArrayList
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ChangePreparing

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