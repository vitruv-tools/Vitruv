package tools.vitruv.framework.change.description.impl

import org.eclipse.emf.ecore.change.ChangeDescription
import org.eclipse.emf.ecore.resource.ResourceSet
import tools.vitruv.framework.change.description.CompositeTransactionalChange
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.change.preparation.ChangeDescription2EChangesTransformation
import tools.vitruv.framework.util.datatypes.VURI

/**
 * Represents a change in an EMF model. This change has to be instantiated when the model is in the state
 * right before the change described by the recorded {@link ChangeDescription}.
 */
class EMFModelChangeImpl extends AbstractCompositeChangeImpl<TransactionalChange> implements CompositeTransactionalChange {
	private final ChangeDescription changeDescription
	private final VURI vuri
	private var boolean canBeBackwardsApplied
	
    public new(ChangeDescription changeDescription, VURI vuri) {
    	this.changeDescription = changeDescription
        this.vuri = vuri
        canBeBackwardsApplied = false
		extractChangeInformation
    }

	private def void extractChangeInformation() {
        val eChanges = new ChangeDescription2EChangesTransformation(this.changeDescription).transform
        eChanges.forEach[addChange(VitruviusChangeFactory::instance.createConcreteChange(it, vuri))]
		if (changes.empty) {
			addChange(VitruviusChangeFactory::instance.createEmptyChange(vuri))
		}
	}

    override String toString() '''
    	«EMFModelChangeImpl.simpleName»: VURI «this.vuri», EChanges:
    		«FOR eChange : EChanges»
    			Inner change: «eChange»
    		«ENDFOR»
    '''
        
	override getURI() {
		vuri
	}
	
	override containsConcreteChange() {
		true
	}
	
	override validate() {
		true
	}
	
	override applyBackward() throws IllegalStateException {
		if (!canBeBackwardsApplied) {
			throw new IllegalStateException("Change " + this + " cannot be applied backwards as was not forward applied before.");	
		}
		changes.reverseView.forEach[applyBackward]
		canBeBackwardsApplied = false
	}
	
	override applyForward() throws IllegalStateException {
		if (canBeBackwardsApplied) {
			throw new IllegalStateException("Change " + this + " cannot be applied forwards as was not backwards applied before.");	
		}
		changes.forEach[applyForward]
		canBeBackwardsApplied = true
	}
	
	override resolveBeforeAndApplyForward(ResourceSet resourceSet) {
		if (canBeBackwardsApplied) {
			throw new IllegalStateException("Change " + this + " cannot be applied forwards as was not backwards applied before.");	
		}
		changes.forEach[resolveBeforeAndApplyForward(resourceSet)]
		canBeBackwardsApplied = true
	}
}
