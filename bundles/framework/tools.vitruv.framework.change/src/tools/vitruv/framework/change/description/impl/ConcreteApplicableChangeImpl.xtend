package tools.vitruv.framework.change.description.impl

import java.util.List
import org.eclipse.emf.common.util.BasicEList
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.ResourceSet
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.compound.CompoundEChange
import tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange
import tools.vitruv.framework.change.echange.feature.FeatureEChange
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference
import tools.vitruv.framework.change.echange.root.InsertRootEObject
import tools.vitruv.framework.change.echange.root.RemoveRootEObject
import tools.vitruv.framework.tuid.TuidManager
import tools.vitruv.framework.util.datatypes.VURI

class ConcreteApplicableChangeImpl extends ConcreteChangeImpl {
    public new(EChange eChange, VURI vuri) {
    	super(eChange, vuri);
    }

	override resolveBeforeAndApplyForward(ResourceSet resourceSet) {
		this.eChange = this.eChange.resolveBefore(resourceSet)
		this.registerOldObjectTuidsForUpdate(this.eChange.affectedEObjects)
		this.eChange.applyForward
		this.updateTuids
	}
	
	override applyForward() {
		this.eChange.applyForward
	}

	override applyBackward() {
		this.eChange.applyBackward
	}
	
	private def void registerOldObjectTuidsForUpdate(List<EObject> objects) {
		val tuidManager = TuidManager.instance
		for (object : objects) {
			tuidManager.registerObjectUnderModification(object)
		}
	}
	
	private def void updateTuids() {
		TuidManager.instance.updateTuidsOfRegisteredObjects
		TuidManager.instance.flushRegisteredObjectsUnderModification
	}
	
	private def dispatch List<EObject> getAffectedEObjects(CompoundEChange eChange) {
		var List<EObject> objects = new BasicEList<EObject>
		for (atomicChange : eChange.atomicChanges) {
			objects.addAll(atomicChange.getAffectedEObjects)
		}
		return objects
	}
	
	private def dispatch List<EObject> getAffectedEObjects(InsertRootEObject<EObject> eChange) {
		return #[eChange.newValue]
	}

	private def dispatch List<EObject> getAffectedEObjects(RemoveRootEObject<EObject> eChange) {
		return #[eChange.oldValue]
	}
	
	private def dispatch List<EObject> getAffectedEObjects(InsertEReference<EObject, EObject> eChange) {
		return #[eChange.newValue, eChange.affectedEObject]
	}
	
	private def dispatch List<EObject> getAffectedEObjects(RemoveEReference<EObject, EObject> eChange) {
		return #[eChange.oldValue, eChange.affectedEObject]
	}
	
	private def dispatch List<EObject> getAffectedEObjects(ReplaceSingleValuedEReference<EObject, EObject> eChange) {
		return #[eChange.oldValue, eChange.newValue, eChange.affectedEObject]
	}
	
	private def dispatch List<EObject> getAffectedEObjects(FeatureEChange<EObject, ?> eChange) {
		return #[eChange.affectedEObject]
	}
	
	private def dispatch List<EObject> getAffectedEObjects(EObjectExistenceEChange<EObject> eChange) {
		return #[eChange.affectedEObject]
	}
}