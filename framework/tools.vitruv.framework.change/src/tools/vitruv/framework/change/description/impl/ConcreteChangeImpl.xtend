package tools.vitruv.framework.change.description.impl

import tools.vitruv.framework.change.echange.EChange
import java.util.List
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.change.description.ConcreteChange

class ConcreteChangeImpl extends AbstractConcreteChange implements ConcreteChange {
    public new(List<EChange> eChanges, VURI vuri) {
    	super(vuri);
        this.eChanges.addAll(eChanges);
    }

    public override String toString() {
        return ConcreteChangeImpl.getSimpleName() + ": VURI: " + this.URI + " EChanges: " + this.eChanges;
    }
				
	override prepare() {
		// Do nothing
	}
	
	override isPrepared() {
		return true;
	}
				
}