package tools.vitruv.framework.change.description.impl

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.ResourceSet
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.tuid.TuidManager

class ConcreteApplicableChangeImpl extends ConcreteChangeImpl {
	static extension TuidManager = TuidManager.instance

	public new(EChange eChange) {
		super(eChange);
	}

	override resolveBeforeAndApplyForward(ResourceSet resourceSet) {
		val oldEChange = EChange
		val newEChange = oldEChange.resolveBefore(resourceSet)
		EChange = newEChange
		registerOldObjectTuidsForUpdate(affectedEObjects)
		EChange.applyForward
		updateTuids
	}

	override applyForward() {
		EChange.applyForward
	}

	override applyBackward() {
		EChange.applyBackward
	}

	private def void registerOldObjectTuidsForUpdate(Iterable<EObject> objects) {
		for (object : objects) {
			registerObjectUnderModification(object)
		}
	}

	private def void updateTuids() {
		updateTuidsOfRegisteredObjects
		flushRegisteredObjectsUnderModification
	}

}
