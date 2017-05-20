package tools.vitruv.framework.change.description.impl

import org.eclipse.emf.ecore.change.ChangeDescription
import tools.vitruv.framework.change.description.CompositeTransactionalChange
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.tuid.TuidManager
import java.util.HashSet
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.ResourceSet
import tools.vitruv.framework.change.echange.EChange

/**
 * Represents a change in an EMF model. This change has to be instantiated when the model is in the state
 * right before the change described by the recorded {@link ChangeDescription}.
 */
class LegacyEMFModelChangeImpl extends AbstractCompositeChangeImpl<TransactionalChange> implements CompositeTransactionalChange {
	val ChangeDescription changeDescription
	val VURI vuri
	var boolean canBeBackwardsApplied
	
    public new(ChangeDescription changeDescription, Iterable<EChange> eChanges, VURI vuri) {
    	this.changeDescription = changeDescription
        this.vuri = vuri
        canBeBackwardsApplied = false
		addChanges(eChanges)
    }

	private def void addChanges(Iterable<EChange> eChanges) {
		eChanges.forEach [addChange(VitruviusChangeFactory::instance.createConcreteChange(it, vuri))]
		if (changes.empty) 
			addChange(VitruviusChangeFactory::instance.createEmptyChange(vuri))
	}

    private def ChangeDescription getChangeDescription() {
        changeDescription
    }

    override String toString() '''
    	«EMFModelChangeImpl.simpleName»: VURI vuri», EChanges:
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
		applyChange
		canBeBackwardsApplied = false
	}
	
	override applyForward() throws IllegalStateException {
		if (canBeBackwardsApplied) {
			throw new IllegalStateException("Change " + this + " cannot be applied forwards as was not backwards applied before.");	
		}
		applyChange
		canBeBackwardsApplied = true
	}
	
	private def applyChange() {
		registerOldObjectTuidsForUpdate
		changeDescription.applyAndReverse
		updateTuids
	}
	
	private def void registerOldObjectTuidsForUpdate() {
		val tuidManager = TuidManager::instance
		val objects = new HashSet<EObject>
        objects.addAll(getChangeDescription.objectChanges.keySet.filterNull)
        objects.addAll(getChangeDescription.objectChanges.values.flatten.map[referenceValue].filterNull)
		objects.addAll(getChangeDescription.objectsToDetach.filterNull)
		objects.addAll(getChangeDescription.objectsToAttach.filterNull)
		// Add container objects
		val containerObjects = objects.map[eContainer].filterNull
		for (EObject object : objects + containerObjects) {
			tuidManager.registerObjectUnderModification(object)
        }
    }

    protected def void updateTuids() {
        TuidManager::instance.updateTuidsOfRegisteredObjects
        TuidManager::instance.flushRegisteredObjectsUnderModification
    }
    
	override resolveBeforeAndApplyForward(ResourceSet resourceSet) {
		applyForward
	}
	
	override void applyBackwardIfLegacy() {
		applyBackward
	}
				
}
