package tools.vitruv.framework.change.description.impl

import org.eclipse.emf.ecore.change.ChangeDescription
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.change.description.EMFModelChange
import tools.vitruv.framework.change.preparation.ChangeDescription2EChangesTransformation
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.change.description.VitruviusChange

/**
 * Represents a change in an EMF model. This change has to be instantiated when the model is in the state
 * right before the change described by the recorded {@link ChangeDescription} .
 */
class EMFModelChangeImpl extends GenericCompositeChangeImpl<VitruviusChange> implements EMFModelChange {
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

    override ChangeDescription getChangeDescription() {
        return this.changeDescription;
    }

    override String toString() {
        return EMFModelChange.getSimpleName() + ": VURI: " + this.URI + " EChange: " + this.changeDescription;
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
		changeDescription.applyAndReverse();
		this.canBeBackwardsApplied = false;
	}
	
	override applyForward() throws IllegalStateException {
		if (this.canBeBackwardsApplied) {
			throw new IllegalStateException("Change " + this + " cannot be applied forwards as was not backwards applied before.");	
		}
		changeDescription.applyAndReverse();
		this.canBeBackwardsApplied = true;
	}
	
}
