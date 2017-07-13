package tools.vitruv.framework.change.description.impl

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.ResourceSet
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.tuid.TuidManager
import tools.vitruv.framework.util.datatypes.VURI

class ConcreteApplicableChangeImpl extends ConcreteChangeImpl {
    public new(EChange eChange, VURI vuri) {
    	super(eChange, vuri);
    }

	override resolveBeforeAndApplyForward(ResourceSet resourceSet) {
		this.eChange = this.eChange.resolveBefore(resourceSet)
		this.registerOldObjectTuidsForUpdate(this.affectedEObjects)
		this.eChange.applyForward
		this.updateTuids
	}
	
	override applyForward() {
		this.eChange.applyForward
	}

	override applyBackward() {
		this.eChange.applyBackward
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