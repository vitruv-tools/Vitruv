package tools.vitruv.framework.change.description.impl

import tools.vitruv.framework.change.echange.EChange
import java.util.List
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.change.description.GeneralChange
import tools.vitruv.framework.change.preparation.ChangePreparing

class GeneralChangeImpl extends ConcreteChangeImpl implements GeneralChange {
    public new(List<EChange> eChanges, VURI vuri) {
    	super(vuri);
        this.eChanges.addAll(eChanges);
    }

    public override String toString() {
        return GeneralChangeImpl.getSimpleName() + ": VURI: " + this.URI + " EChanges: " + this.eChanges;
    }
				
	override prepare(ChangePreparing preparer) {
		// Do nothing
	}
	
	override isPrepared() {
		return true;
	}
				
}