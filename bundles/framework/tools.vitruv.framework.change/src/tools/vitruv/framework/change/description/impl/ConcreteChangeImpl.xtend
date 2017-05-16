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

class ConcreteChangeImpl extends AbstractConcreteChange {
	public new(EChange eChange, VURI vuri) {
		super(vuri)
		this.eChange = eChange
	}

	public override String toString() {
		'''«ConcreteChangeImpl.simpleName»: VURI: «this.URI» EChange: «this.eChange»'''
	}

	override resolveBeforeAndApplyForward(ResourceSet resourceSet) {
		eChange = this.eChange.resolveBefore(resourceSet)
		registerOldObjectTuidsForUpdate(eChange.affectedEObjects)
		eChange?.applyForward
		updateTuids
	}

	private def void registerOldObjectTuidsForUpdate(List<EObject> objects) {
		val tuidManager = TuidManager::instance
		objects?.forEach[tuidManager.registerObjectUnderModification(it)]
	}

	private def void updateTuids() {
		TuidManager::instance.updateTuidsOfRegisteredObjects
		TuidManager::instance.flushRegisteredObjectsUnderModification
	}

	private def dispatch List<EObject> getAffectedEObjects(Void x) {
	}

	private def dispatch List<EObject> getAffectedEObjects(CompoundEChange eChange) {
		val List<EObject> objects = new BasicEList<EObject>
		eChange?.atomicChanges?.forEach[objects.addAll(it.getAffectedEObjects)]
		objects
	}

	private def dispatch List<EObject> getAffectedEObjects(InsertRootEObject<EObject> eChange) {
		#[eChange.newValue]
	}

	private def dispatch List<EObject> getAffectedEObjects(RemoveRootEObject<EObject> eChange) {
		#[eChange.oldValue]
	}

	private def dispatch List<EObject> getAffectedEObjects(InsertEReference<EObject, EObject> eChange) {
		#[eChange.newValue, eChange.affectedEObject]
	}

	private def dispatch List<EObject> getAffectedEObjects(RemoveEReference<EObject, EObject> eChange) {
		#[eChange.oldValue, eChange.affectedEObject]
	}

	private def dispatch List<EObject> getAffectedEObjects(ReplaceSingleValuedEReference<EObject, EObject> eChange) {
		#[eChange.oldValue, eChange.newValue, eChange.affectedEObject]
	}

	private def dispatch List<EObject> getAffectedEObjects(FeatureEChange<EObject, ?> eChange) {
		#[eChange.affectedEObject]
	}

	private def dispatch List<EObject> getAffectedEObjects(EObjectExistenceEChange<EObject> eChange) {
		#[eChange.affectedEObject]
	}
}
