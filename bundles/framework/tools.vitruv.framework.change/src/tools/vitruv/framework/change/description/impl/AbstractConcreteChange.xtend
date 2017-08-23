package tools.vitruv.framework.change.description.impl

import java.util.ArrayList
import tools.vitruv.framework.change.description.ConcreteChange
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.util.datatypes.VURI
import org.apache.log4j.Logger
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
import org.eclipse.emf.ecore.InternalEObject
import tools.vitruv.framework.change.uuid.UuidResolver

abstract class AbstractConcreteChange implements ConcreteChange {
	private static val logger = Logger.getLogger(AbstractConcreteChange);
	private var EChange eChange;
	
	new(EChange eChange) {
		this.eChange = eChange;
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
		val resolvedResources = affectedNotReferencedEObjects.map[eResource].filterNull;
		if (resolvedResources.size > 0) {
			return VURI.getInstance(resolvedResources.get(0));
		}
		val proxyUris = affectedNotReferencedEObjects.filter(InternalEObject).map[eProxyURI].filterNull.filter[segmentCount > 0]
		if (proxyUris.size > 0) {
			return VURI.getInstance(proxyUris.get(0).trimFragment);
		}
	}
		
	override getEChange() {
		return eChange;
	}
	
	protected def setEChange(EChange eChange) {
		this.eChange = eChange;
	}
	
	override applyBackward() {
		logger.warn("The applyBackward method is not implemented for " + this.class.simpleName + " yet.");
	}
	
	override applyForward() {
		logger.warn("The applyForward method is not implemented for " + this.class.simpleName + " yet.");
	}
	
	override resolveBeforeAndApplyForward(UuidResolver uuidResolver) {
		logger.warn("The resolveBeforeAndapplyForward method is not implemented for " + this.class.simpleName + " yet.");
	}
	
	override applyBackwardIfLegacy() {
		// Do nothing
	}
	
	def getAffectedNotReferencedEObjects() {
		return this.eChange.affectedEObjects.filterNull
	}
	
	override getAffectedEObjects() {
		return affectedNotReferencedEObjects + this.eChange.referencedEObjects.filterNull
	}
	
	private def dispatch Iterable<EObject> getAffectedEObjects(CompoundEChange eChange) {
		var List<EObject> objects = new BasicEList<EObject>
		for (atomicChange : eChange.atomicChanges) {
			objects.addAll(atomicChange.getAffectedEObjects)
		}
		return objects.filterNull
	}
	
	private def dispatch List<EObject> getAffectedEObjects(EChange eChange) {
		return #[]
	}
	
	private def dispatch List<EObject> getAffectedEObjects(InsertRootEObject<EObject> eChange) {
		return #[eChange.newValue]
	}

	private def dispatch List<EObject> getAffectedEObjects(RemoveRootEObject<EObject> eChange) {
		return #[eChange.oldValue]
	}
	
	private def dispatch List<EObject> getAffectedEObjects(InsertEReference<EObject, EObject> eChange) {
		return #[eChange.affectedEObject]
	}
	
	private def dispatch List<EObject> getAffectedEObjects(RemoveEReference<EObject, EObject> eChange) {
		return #[eChange.affectedEObject]
	}
	
	private def dispatch List<EObject> getAffectedEObjects(ReplaceSingleValuedEReference<EObject, EObject> eChange) {
		return #[eChange.affectedEObject]
	}
	
	private def dispatch List<EObject> getAffectedEObjects(FeatureEChange<EObject, ?> eChange) {
		return #[eChange.affectedEObject]
	}
	
	private def dispatch List<EObject> getAffectedEObjects(EObjectExistenceEChange<EObject> eChange) {
		return #[eChange.affectedEObject]
	}
	
	private def dispatch List<EObject> getReferencedEObjects(EChange eChange) {
		return #[]
	}
	
	private def dispatch List<EObject> getReferencedEObjects(InsertEReference<EObject, EObject> eChange) {
		return #[eChange.newValue]
	}
	
	private def dispatch List<EObject> getReferencedEObjects(RemoveEReference<EObject, EObject> eChange) {
		return #[eChange.oldValue]
	}
	
	private def dispatch List<EObject> getReferencedEObjects(ReplaceSingleValuedEReference<EObject, EObject> eChange) {
		return #[eChange.affectedEObject, eChange.oldValue, eChange.newValue]
	}

}