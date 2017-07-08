package tools.vitruv.framework.change.description.impl

import org.eclipse.emf.ecore.change.ChangeDescription
import org.eclipse.emf.ecore.resource.ResourceSet
import tools.vitruv.framework.change.description.CompositeTransactionalChange
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.change.echange.EChange

/**
 * Represents a change in an EMF model. This change has to be instantiated when the model is in the state
 * right before the change described by the recorded {@link ChangeDescription}.
 */
class EMFModelChangeImpl extends AbstractCompositeChangeImpl<TransactionalChange> implements CompositeTransactionalChange {
	private final VURI vuri;
	private var boolean canBeBackwardsApplied;
	
    public new(Iterable<EChange> eChanges, VURI vuri) {
        this.vuri = vuri;
        this.canBeBackwardsApplied = false;
		addChanges(eChanges);
    }

	private def void addChanges(Iterable<EChange> eChanges) {
		for (eChange : eChanges) {
			addChange(VitruviusChangeFactory.instance.createConcreteApplicableChange(eChange, vuri));
		}
		if (changes.empty) {
			addChange(VitruviusChangeFactory.instance.createEmptyChange(vuri));
		}
	}

    override String toString() '''
    	«EMFModelChangeImpl.simpleName»: VURI «this.vuri», EChanges:
    		«FOR eChange : EChanges»
    			Inner change: «eChange»«ENDFOR»'''
        
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
		for (c : changes.reverseView) {
			c.applyBackward
		}
		this.canBeBackwardsApplied = false;
	}
	
	override applyForward() throws IllegalStateException {
		if (this.canBeBackwardsApplied) {
			throw new IllegalStateException("Change " + this + " cannot be applied forwards as was not backwards applied before.");	
		}
		for (c : changes) {
			c.applyForward
		}
		this.canBeBackwardsApplied = true;
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
	
	override applyBackwardIfLegacy() {
		// Do nothing
	}
	
}
