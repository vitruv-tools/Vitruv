package tools.vitruv.framework.change.description.impl

import java.util.ArrayList
import java.util.List
import org.apache.log4j.Logger
import org.eclipse.emf.common.util.BasicEList
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.ResourceSet
import tools.vitruv.framework.change.description.ConcreteChange
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.compound.CompoundEChange
import tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange
import tools.vitruv.framework.change.echange.feature.FeatureEChange
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference
import tools.vitruv.framework.change.echange.root.InsertRootEObject
import tools.vitruv.framework.change.echange.root.RemoveRootEObject
import tools.vitruv.framework.util.datatypes.VURI

abstract class AbstractConcreteChange implements ConcreteChange {
	static extension Logger = Logger::getLogger(AbstractConcreteChange)

	protected EChange eChange
	val VURI vuri

	new(VURI vuri) {
		this.vuri = vuri
	}

	override containsConcreteChange() {
		true
	}

	override validate() {
		containsConcreteChange && URI !== null
	}

	override getEChanges() {
		new ArrayList<EChange>(#[eChange])
	}

	override getURI() {
		vuri
	}

	override getEChange() {
		eChange
	}

	override applyBackward() {
		warn("The applyBackward method is not implemented for " + class.simpleName + " yet.")
	}

	override applyForward() {
		warn("The applyForward method is not implemented for " + class.simpleName + " yet.")
	}

	override resolveBeforeAndApplyForward(ResourceSet resourceSet) {
		warn("The resolveBeforeAndapplyForward method is not implemented for " + class.simpleName + " yet.")
	}

	override applyBackwardIfLegacy() {
// Do nothing
	}

	override getAffectedEObjects() {
		eChange.affectedEObjects
	}

	private def dispatch List<EObject> getAffectedEObjects(CompoundEChange eChange) {
		var List<EObject> objects = new BasicEList<EObject>
		for (atomicChange : eChange.atomicChanges) {
			objects.addAll(atomicChange.getAffectedEObjects)
		}
		return objects.filterNull.toList
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
