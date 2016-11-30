package tools.vitruv.framework.change.description.impl

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
		super.applyForward();
		// for (EChange eChange : recordedChange.getEChanges()) {
        // if (eChange instanceof JavaFeatureEChange<?, ?>) {
        // if (((JavaFeatureEChange<?, ?>) eChange).getOldAffectedEObject() != null) {
        // JavaFeatureEChange<?, ?> javaFeatureEChange = (JavaFeatureEChange<?, ?>) eChange;
        // TUID tuid = correspondenceModel
        // .calculateTUIDFromEObject(javaFeatureEChange.getOldAffectedEObject());
        // if (tuid != null && javaFeatureEChange.getAffectedEObject() != null) {
        // tuidMap.put(javaFeatureEChange.getAffectedEObject(), tuid);
        // }
        // }
        // }
        // }
	}
	
}