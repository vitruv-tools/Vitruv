package tools.vitruv.framework.change.description.impl

import java.util.ArrayList
import tools.vitruv.framework.change.description.ConcreteChange
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.util.datatypes.VURI
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.resource.ResourceSet
import java.util.List
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.change.echange.compound.CompoundEChange
import org.eclipse.emf.common.util.BasicEList
import tools.vitruv.framework.change.echange.root.InsertRootEObject
import tools.vitruv.framework.change.echange.root.RemoveRootEObject
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference
import tools.vitruv.framework.change.echange.feature.FeatureEChange
import tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange

abstract class AbstractConcreteChange implements ConcreteChange {
	private static val logger = Logger.getLogger(AbstractConcreteChange);
	
	protected EChange eChange;
	final VURI vuri;
	
	new(VURI vuri) {
		this.vuri = vuri;
	}
	
	override containsConcreteChange() {
		return true;
	}
	
	override validate() {
		return containsConcreteChange() && URI !== null;
	}
	
	override getEChanges() {
		return new ArrayList<EChange>(#[eChange]);
	}
	
	override getURI() {
		return vuri;
	}
		
	override getEChange() {
		return eChange;
	}
	
	override applyBackward() {
		logger.warn("The applyBackward method is not implemented for " + this.class.simpleName + " yet.");
	}
	
	override applyForward() {
		logger.warn("The applyForward method is not implemented for " + this.class.simpleName + " yet.");
	}
	
	override resolveBeforeAndApplyForward(ResourceSet resourceSet) {
		logger.warn("The resolveBeforeAndapplyForward method is not implemented for " + this.class.simpleName + " yet.");
	}
	
	override applyBackwardIfLegacy() {
		// Do nothing
	}
	
	override getAffectedEObjects() {
		return this.eChange.affectedEObjects
	}
	
	private def dispatch Iterable<EObject> getAffectedEObjects(CompoundEChange eChange) {
		var List<EObject> objects = new BasicEList<EObject>
		for (atomicChange : eChange.atomicChanges) {
			objects.addAll(atomicChange.getAffectedEObjects)
		}
		return objects.filterNull
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