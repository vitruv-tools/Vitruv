package tools.vitruv.framework.change.description.impl

import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.tuid.TuidManager
import static extension tools.vitruv.framework.change.echange.EChangeResolverAndApplicator.*
import tools.vitruv.framework.change.uuid.UuidResolver

class ConcreteApplicableChangeImpl extends ConcreteChangeImpl {
    public new(EChange eChange) {
    	super(eChange);
    }

	override resolveBeforeAndApplyForward(UuidResolver uuidResolver) {
		this.EChange = this.EChange.resolveBefore(uuidResolver)
		this.registerOldObjectTuidsForUpdate(this.affectedEObjects)
		this.EChange.applyForward
		this.updateTuids
	}
	
	override resolveAfterAndApplyBackward(UuidResolver uuidResolver) {
		this.EChange = this.EChange.resolveAfter(uuidResolver)
		this.registerOldObjectTuidsForUpdate(this.affectedEObjects)
		this.EChange.applyBackward
		this.updateTuids
	}
	
	override applyForward() {
		this.EChange.applyForward
	}

	override applyBackward() {
		this.EChange.applyBackward
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
	
}