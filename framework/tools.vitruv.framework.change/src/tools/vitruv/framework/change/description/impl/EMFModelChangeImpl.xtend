package tools.vitruv.framework.change.description.impl

import org.eclipse.emf.ecore.change.ChangeDescription
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.change.description.EMFModelChange

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