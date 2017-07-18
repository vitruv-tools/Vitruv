package tools.vitruv.framework.change.description.impl

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.ResourceSet
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.tuid.TuidManager

class ConcreteApplicableChangeImpl extends ConcreteChangeImpl {
    public new(EChange eChange) {
    	super(eChange);
    }

	override resolveBeforeAndApplyForward(ResourceSet resourceSet) {
		this.EChange = this.EChange.resolveBefore(resourceSet)
		this.registerOldObjectTuidsForUpdate(this.affectedEObjects)
		this.EChange.applyForward
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