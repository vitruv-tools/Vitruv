package tools.vitruv.framework.change.description.impl

import tools.vitruv.framework.change.echange.EChange

class ConcreteChangeImpl extends AbstractConcreteChange {
    new(EChange eChange) {
		super(eChange);
    }

    override String toString() {
        return this.class.getSimpleName() + ", VURI: " + this.URI + "\n	EChange: " + this.EChange;
    }

}