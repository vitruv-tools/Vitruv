package edu.kit.ipd.sdq.vitruvius.framework.contracts.change.processable.impl

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange
import java.util.List
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import org.eclipse.emf.ecore.change.ChangeDescription
import edu.kit.ipd.sdq.vitruvius.framework.contracts.change.processable.VitruviusChange

class VitruviusChangeImpl extends ProcessableConcreteChangeImpl implements VitruviusChange {
    private val List<EChange> eChanges;

	private val ChangeDescription originalChangeDescription;
	
    public new(ChangeDescription changeDescription, List<EChange> eChanges, VURI vuri) {
    	super(vuri);
        this.eChanges = eChanges;
        this.originalChangeDescription = changeDescription;
    }

    public override List<EChange> getEChanges() {
        return this.eChanges;
    }
    
    public override ChangeDescription getOriginalChangeDescription() {
        return this.originalChangeDescription;
    }

    public override String toString() {
        return VitruviusChangeImpl.getSimpleName() + ": VURI: " + this.URI + " EChanges: " + this.eChanges;
    }
				
	override containsConcreteChange() {
		return true;
	}
}