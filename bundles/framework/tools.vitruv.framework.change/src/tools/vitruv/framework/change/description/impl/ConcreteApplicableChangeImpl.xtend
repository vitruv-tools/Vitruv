package tools.vitruv.framework.change.description.impl

import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.tuid.TuidManager
import static extension tools.vitruv.framework.change.echange.EChangeResolverAndApplicator.*
import tools.vitruv.framework.change.uuid.UuidResolver
import tools.vitruv.framework.change.echange.resolve.EChangeUnresolver

class ConcreteApplicableChangeImpl extends ConcreteChangeImpl {
	private var boolean canBeBackwardsApplied;
	
    public new(EChange eChange) {
    	super(eChange);
    	this.canBeBackwardsApplied = true;
    }

	override resolveBeforeAndApplyForward(UuidResolver uuidResolver) {
		this.EChange = this.EChange.resolveBefore(uuidResolver)
		this.registerOldObjectTuidsForUpdate(this.affectedEObjects)
		this.canBeBackwardsApplied = false;
		applyForward()
		this.updateTuids
	}
	
	override resolveAfterAndApplyBackward(UuidResolver uuidResolver) {
		this.EChange = this.EChange.resolveAfter(uuidResolver)
		this.registerOldObjectTuidsForUpdate(this.affectedEObjects)
		this.canBeBackwardsApplied = true;
		applyBackward()
		this.updateTuids
	}
	
	override applyForward() {
		if (this.canBeBackwardsApplied) {
			throw new IllegalStateException("Change " + this + " cannot be applied forwards as was not backwards applied before.");	
		}
		this.EChange.applyForward
		this.canBeBackwardsApplied = true;
	}

	override applyBackward() {
		if (!this.canBeBackwardsApplied) {
			throw new IllegalStateException("Change " + this + " cannot be applied backwards as was not forward applied before.");	
		}
		this.EChange.applyBackward
		this.canBeBackwardsApplied = false;
	}
	
	private def void registerOldObjectTuidsForUpdate(Iterable<EObject> objects) {
		val tuidManager = TuidManager.instance
		for (object : objects) {
			tuidManager.registerObjectUnderModification(object)
		}
	}
	
	private def void updateTuids() {
		TuidManager.instance.updateTuidsOfRegisteredObjects
		TuidManager.instance.flushRegisteredObjectsUnderModification
	}
	
	override unresolveIfApplicable() {
		EChanges.forEach[EChangeUnresolver.unresolve(it)]	
	}
	
}