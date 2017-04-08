package tools.vitruv.framework.change.description.impl

import org.eclipse.emf.ecore.resource.ResourceSet
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.util.datatypes.VURI

class ConcreteChangeImpl extends AbstractConcreteChange {
    public new(EChange eChange, VURI vuri) {
    	super(vuri);
        this.eChange = eChange;
    }

    public override String toString() {
        return ConcreteChangeImpl.getSimpleName() + ": VURI: " + this.URI + " EChange: " + this.eChange;
    }

	override applyForward() {
		this.eChange.applyForward
	}
	
	override applyBackward() {
		this.eChange.applyBackward
	}
	
	override resolveBeforeAndApplyForward(ResourceSet resourceSet) {
		this.eChange = this.eChange.resolveBeforeAndApplyForward(resourceSet)
	}
	
	override resolveAfterAndApplyBackward(ResourceSet resourceSet) {
		this.eChange = this.eChange.resolveAfterAndApplyBackward(resourceSet)
	}
}