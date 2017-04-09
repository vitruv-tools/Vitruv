package tools.vitruv.framework.change.description.impl

import java.util.ArrayList
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.change.ChangeDescription
import org.eclipse.emf.ecore.change.FeatureChange
import org.eclipse.emf.ecore.resource.ResourceSet
import tools.vitruv.framework.change.description.CompositeTransactionalChange
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.change.preparation.ChangeDescription2EChangesTransformation
import tools.vitruv.framework.tuid.TuidManager
import tools.vitruv.framework.util.datatypes.VURI

/**
 * Represents a change in an EMF model. This change has to be instantiated when the model is in the state
 * right before the change described by the recorded {@link ChangeDescription}.
 */
class EMFModelChangeImpl extends AbstractCompositeChangeImpl<TransactionalChange> implements CompositeTransactionalChange {
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

    override String toString() '''
    	«EMFModelChangeImpl.simpleName»: VURI «this.vuri», EChanges:
    		«FOR eChange : EChanges»
    			Inner change: «eChange»
    		«ENDFOR»
    '''
        
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
		registerOldObjectTuidsForUpdate();
		for (c : changes.reverseView) {
			c.applyBackward
		}
		updateTuids();
		this.canBeBackwardsApplied = false;
	}
	
	override applyForward() throws IllegalStateException {
		if (this.canBeBackwardsApplied) {
			throw new IllegalStateException("Change " + this + " cannot be applied forwards as was not backwards applied before.");	
		}
		registerOldObjectTuidsForUpdate();
		for (c : changes) {
			c.applyForward
		}
		updateTuids();
		this.canBeBackwardsApplied = true;
	}
	
	
	/* 
	private def applyChange() {
		registerOldObjectTuidsForUpdate();
		changeDescription.applyAndReverse();
		updateTuids();
	}*/
	
	private def void registerOldObjectTuidsForUpdate() {
		val tuidManager = TuidManager.instance;
		val objects = new ArrayList<EObject>();
        objects.addAll(getChangeDescription().getObjectChanges().keySet());
		objects.addAll(getChangeDescription().getObjectsToDetach());
        for (EObject object : getChangeDescription().getObjectChanges().keySet()) {
			tuidManager.registerObjectUnderModification(object);
        	for (FeatureChange featureChange : getChangeDescription().getObjectChanges().get(object)) {
	        	tuidManager.registerObjectUnderModification(featureChange.getReferenceValue());
			}
        }
        for (EObject object : getChangeDescription().getObjectsToDetach()) {
        	tuidManager.registerObjectUnderModification(object);
		}
		for (EObject object : getChangeDescription().getObjectsToAttach()) {
        	tuidManager.registerObjectUnderModification(object);
		}
    }

    protected def void updateTuids() {
        TuidManager.instance.updateTuidsOfRegisteredObjects();
        TuidManager.instance.flushRegisteredObjectsUnderModification();
    }
	
	override resolveBeforeAndApplyForward(ResourceSet resourceSet) {
		if (this.canBeBackwardsApplied) {
			throw new IllegalStateException("Change " + this + " cannot be applied forwards as was not backwards applied before.");	
		}
		for (c : changes) {
			c.resolveBeforeAndApplyForward(resourceSet)
		}
		this.canBeBackwardsApplied = true;
	}
	
	override resolveAfterAndApplyBackward(ResourceSet resourceSet) {
		if (!this.canBeBackwardsApplied) {
			throw new IllegalStateException("Change " + this + " cannot be applied backwards as was not forward applied before.");	
		}
		for (c : changes.reverseView) {
			c.resolveAfterAndApplyBackward(resourceSet)
		}
		this.canBeBackwardsApplied = false;
	}
	
}
