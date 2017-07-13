package tools.vitruv.framework.change.description.impl

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.ResourceSet

import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.tuid.TuidManager
import tools.vitruv.framework.util.datatypes.VURI
import java.util.List

class ConcreteApplicableChangeImpl extends ConcreteChangeImpl {
	new(EChange eChange, VURI vuri) {
		super(eChange, vuri)
	}

	override resolveBeforeAndApplyForward(ResourceSet resourceSet) {
		this.eChange = this.eChange.resolveBefore(resourceSet)
		this.registerOldObjectTuidsForUpdate(this.affectedEObjects)
		this.eChange.applyForward
		this.updateTuids
	}

	override applyForward() {
		eChange.applyForward
	}

	override applyBackward() {
		eChange.applyBackward
	}

	private def void registerOldObjectTuidsForUpdate(List<EObject> objects) {
		objects.forEach[TuidManager::instance.registerObjectUnderModification(it)]
	}

	private def void updateTuids() {
		TuidManager::instance.updateTuidsOfRegisteredObjects
		TuidManager::instance.flushRegisteredObjectsUnderModification
	}
	
}
