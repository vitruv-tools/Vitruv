package edu.kit.ipd.sdq.vitruvius.framework.contracts.change.recorded.impl

import edu.kit.ipd.sdq.vitruvius.framework.contracts.change.recorded.EMFModelChange
import org.eclipse.emf.ecore.change.ChangeDescription
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI

class EMFModelChangeImpl extends RecordedConcreteChangeImpl implements EMFModelChange {
	private ChangeDescription changeDescription;

    public new(ChangeDescription changeDescription, VURI vuri) {
    	super(vuri);
        this.changeDescription = changeDescription;
    }

    override ChangeDescription getChangeDescription() {
        return this.changeDescription;
    }

    override String toString() {
        return EMFModelChange.getSimpleName() + ": VURI: " + this.URI + " EChange: " + this.changeDescription;
    }
				
}