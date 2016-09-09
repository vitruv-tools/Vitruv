package tools.vitruv.framework.change.description.impl

import org.eclipse.emf.ecore.change.ChangeDescription
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.change.description.EMFModelChange
import tools.vitruv.framework.change.preparation.ChangeDescription2EChangesTransformation
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.change.description.VitruviusChange

class EMFModelChangeImpl extends GenericCompositeChangeImpl<VitruviusChange> implements EMFModelChange {
	private final ChangeDescription changeDescription;
	private final VURI vuri;
	private boolean prepared;

    public new(ChangeDescription changeDescription, VURI vuri) {
    	this.changeDescription = changeDescription;
        this.vuri = vuri;
        this.prepared = false;
    }

    override ChangeDescription getChangeDescription() {
        return this.changeDescription;
    }

    override String toString() {
        return EMFModelChange.getSimpleName() + ": VURI: " + this.URI + " EChange: " + this.changeDescription;
    }

	override prepare() {
		val eChanges = new ChangeDescription2EChangesTransformation(this.changeDescription).transform()
		for (eChange : eChanges) {
			addChange(VitruviusChangeFactory.instance.createConcreteChange(eChange, vuri));
		}
		if (changes.empty) {
			addChange(VitruviusChangeFactory.instance.createEmptyChange(vuri));
		}
		this.prepared = true;
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
	
	override isPrepared() {
		return prepared;
	}
	
}
