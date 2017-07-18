package tools.vitruv.framework.change.description.impl

import org.eclipse.emf.ecore.change.ChangeDescription
import org.eclipse.emf.ecore.resource.ResourceSet
import tools.vitruv.framework.change.description.CompositeTransactionalChange
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.change.echange.EChange

/**
 * Represents a change in an EMF model. This change has to be instantiated when the model is in the state
 * right before the change described by the recorded {@link ChangeDescription}.
 */
class EMFModelChangeImpl extends AbstractCompositeChangeImpl<TransactionalChange> implements CompositeTransactionalChange {
	private var boolean canBeBackwardsApplied;
	
    public new(Iterable<EChange> eChanges) {
        this.canBeBackwardsApplied = false;
		addChanges(eChanges);
    }

	private def void addChanges(Iterable<EChange> eChanges) {
		for (eChange : eChanges) {
			addChange(VitruviusChangeFactory.instance.createConcreteApplicableChange(eChange));
		}
		if (changes.empty) {
			addChange(VitruviusChangeFactory.instance.createEmptyChange(URI));
		}
	}

    override String toString() '''
		«EMFModelChangeImpl.simpleName»: VURI «this.URI», EChanges:
			«FOR eChange : EChanges»
				Inner change: «eChange»
			«ENDFOR»
			'''
        
	override getURI() {
		val uris = this.changes.map[URI].filterNull
		if (uris.size > 0) 
			return uris.get(0);
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
