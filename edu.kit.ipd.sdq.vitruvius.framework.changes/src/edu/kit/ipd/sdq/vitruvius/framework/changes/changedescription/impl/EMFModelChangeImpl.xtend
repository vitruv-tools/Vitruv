package edu.kit.ipd.sdq.vitruvius.framework.changes.changedescription.impl

import org.eclipse.emf.ecore.change.ChangeDescription
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.framework.changes.changedescription.EMFModelChange

class EMFModelChangeImpl extends ConcreteChangeImpl implements EMFModelChange {
	private final ChangeDescription changeDescription;

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