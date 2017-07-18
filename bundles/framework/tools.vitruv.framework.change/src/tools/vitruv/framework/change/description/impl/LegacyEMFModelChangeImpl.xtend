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
	private final ChangeDescription changeDescription;
	private final VURI vuri;
	private var boolean canBeBackwardsApplied;
	
    public new(ChangeDescription changeDescription, Iterable<EChange> eChanges) {
    	this.changeDescription = changeDescription;
    	val affectedObjects = eChanges.map[affectedEObjects].flatten.filterNull
        this.vuri = if (affectedObjects.empty) null else VURI.getInstance(affectedObjects.get(0).eResource);
        this.canBeBackwardsApplied = false;
		addChanges(eChanges);
    }

	private def void addChanges(Iterable<EChange> eChanges) {
		for (eChange : eChanges) {
			addChange(VitruviusChangeFactory.instance.createConcreteChange(eChange));
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
	
	def applyForwardWithoutTuidUpdate() throws IllegalStateException {
		if (this.canBeBackwardsApplied) {
			throw new IllegalStateException("Change " + this + " cannot be applied forwards as was not backwards applied before.");	
		}
		changeDescription.applyAndReverse();
		this.canBeBackwardsApplied = true;
	}
	
	private def applyChange() {
		registerOldObjectTuidsForUpdate();
		changeDescription.applyAndReverse();
		updateTuids();
	}
	
	private def void registerOldObjectTuidsForUpdate() {
		val tuidManager = TuidManager.instance;
		val objects = new HashSet<EObject>();
        objects.addAll(getChangeDescription().getObjectChanges().keySet().filterNull);
        objects.addAll(getChangeDescription().getObjectChanges().values().flatten.map[referenceValue].filterNull);
		objects.addAll(getChangeDescription().getObjectsToDetach().filterNull);
		objects.addAll(getChangeDescription().getObjectsToAttach().filterNull)
		// Add container objects
		val containerObjects = objects.map[eContainer].filterNull;
		for (EObject object : objects + containerObjects) {
			tuidManager.registerObjectUnderModification(object);
        }
    }

    protected def void updateTuids() {
        TuidManager.instance.updateTuidsOfRegisteredObjects();
        TuidManager.instance.flushRegisteredObjectsUnderModification();
    }
    
	override resolveBeforeAndApplyForward(ResourceSet resourceSet) {
		applyForward();
	}
	
	override void applyBackwardIfLegacy() {
		this.applyBackward();
	}
				
}
