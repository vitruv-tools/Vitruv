package tools.vitruv.framework.change.description.impl

import org.eclipse.emf.ecore.change.ChangeDescription
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.change.preparation.ChangeDescription2EChangesTransformation
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.change.description.VitruviusChange
import org.eclipse.emf.ecore.change.FeatureChange
import org.eclipse.emf.ecore.EObject
import java.util.ArrayList
import tools.vitruv.framework.tuid.TUID
import tools.vitruv.framework.change.description.CompositeTransactionalChange

/**
 * Represents a change in an EMF model. This change has to be instantiated when the model is in the state
 * right before the change described by the recorded {@link ChangeDescription}.
 */
class EMFModelChangeImpl extends GenericCompositeChangeImpl<VitruviusChange> implements CompositeTransactionalChange {
	private final ChangeDescription changeDescription;
	private final VURI vuri;
	private var boolean canBeBackwardsApplied;
	
    public new(ChangeDescription changeDescription, VURI vuri) {
    	this.changeDescription = changeDescription;
        this.vuri = vuri;
        this.canBeBackwardsApplied = false;
		extractChangeInformation();
    }

	private def void extractChangeInformation() {
        val eChanges = new ChangeDescription2EChangesTransformation(this.changeDescription).transform()
		for (eChange : eChanges) {
			addChange(VitruviusChangeFactory.instance.createConcreteChange(eChange, vuri));
		}
		if (changes.empty) {
			addChange(VitruviusChangeFactory.instance.createEmptyChange(vuri));
		}
	}

    private def ChangeDescription getChangeDescription() {
        return this.changeDescription;
    }

    override String toString() {
        return EMFModelChangeImpl.getSimpleName() + ": VURI: " + this.URI + " EChange: " + this.changeDescription;
    }

	override getURI() {
		return vuri;
	}
	
	override containsConcreteChange() {
		return true;
	}
	
	override validate() {
		return true;
	}
	
	override applyBackward() throws IllegalStateException {
		if (!this.canBeBackwardsApplied) {
			throw new IllegalStateException("Change " + this + " cannot be applied backwards as was not forward applied before.");	
		}
		applyChange();
		this.canBeBackwardsApplied = false;
	}
	
	override applyForward() throws IllegalStateException {
		if (this.canBeBackwardsApplied) {
			throw new IllegalStateException("Change " + this + " cannot be applied forwards as was not backwards applied before.");	
		}
		applyChange();
		this.canBeBackwardsApplied = true;
	}
	
	private def applyChange() {
		registerOldObjectTuidsForUpdate();
		changeDescription.applyAndReverse();
		updateTuids();
	}
	
	private def void registerOldObjectTuidsForUpdate() {
		val objects = new ArrayList<EObject>();
        objects.addAll(getChangeDescription().getObjectChanges().keySet());
		objects.addAll(getChangeDescription().getObjectsToDetach());
        for (EObject object : getChangeDescription().getObjectChanges().keySet()) {
			TUID.registerObjectForUpdate(object);
        	for (FeatureChange featureChange : getChangeDescription().getObjectChanges().get(object)) {
	        	TUID.registerObjectForUpdate(featureChange.getReferenceValue());
			}
        }
        for (EObject object : getChangeDescription().getObjectsToDetach()) {
        	TUID.registerObjectForUpdate(object);
		}
    }

    protected def void updateTuids() {
        // TODO HK There is something wrong with transactions if we have to start a transaction to
        // update the TUID here.
        // Possibilities:
        // 1. There should not be an active transaction when this method is called
        // 2. The TUID mechanism is refactored so that only the TUID object is modified and no other
        // resources
        TUID.updateRegisteredObjectsTuids();
    }
	
}
