package tools.vitruv.framework.change.description.impl

import org.eclipse.emf.ecore.change.ChangeDescription
import tools.vitruv.framework.change.description.CompositeTransactionalChange
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.tuid.TuidManager
import java.util.HashSet
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.change.uuid.UuidResolver

/**
 * Represents a change in an EMF model. This change has to be instantiated when the model is in the state
 * right before the change described by the recorded {@link ChangeDescription}.
 */
class LegacyEMFModelChangeImpl extends AbstractCompositeChangeImpl<TransactionalChange> implements CompositeTransactionalChange {
	private final ChangeDescription changeDescription;
	private var boolean canBeBackwardsApplied;
	private val VURI savedURI;
	
    public new(ChangeDescription changeDescription, Iterable<EChange> eChanges) {
    	this.changeDescription = changeDescription;
    	// Save an URI of the changes: if only a remove change is given, the URI is null otherwise
    	// but here the change is not applied yet, so the URI can be extracted
    	val changeUris = eChanges.map[URI].filterNull;
    	this.savedURI = if (!changeUris.empty) changeUris.get(0);
        this.canBeBackwardsApplied = false;
		addChanges(eChanges);
    }

	private def void addChanges(Iterable<EChange> eChanges) {
		for (eChange : eChanges) {
			addChange(VitruviusChangeFactory.instance.createConcreteChange(eChange));
		}
		if (changes.empty) {
			addChange(VitruviusChangeFactory.instance.createEmptyChange(null));
		}
	}

    private def ChangeDescription getChangeDescription() {
        return this.changeDescription;
    }

    override String toString() '''
    	«LegacyEMFModelChangeImpl.simpleName»: VURI «this.URI», EChanges:
    		«FOR eChange : EChanges»
    			Inner change: «eChange»
    		«ENDFOR»
    '''
        
	override getURI() {
		val changeUris = changes.map[URI].filterNull
		return if (!changeUris.empty) changeUris.get(0) else return savedURI;
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
    
	override resolveBeforeAndApplyForward(UuidResolver uuidResolver) {
		applyForward();
	}
	
	override void applyBackwardIfLegacy() {
		this.applyBackward();
	}
				
}
