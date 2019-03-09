package tools.vitruv.framework.change.description.impl

import java.util.ArrayList
import tools.vitruv.framework.change.description.ConcreteChange
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.util.datatypes.VURI
import org.apache.log4j.Logger
import java.util.List
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.change.echange.root.InsertRootEObject
import tools.vitruv.framework.change.echange.root.RemoveRootEObject
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference
import tools.vitruv.framework.change.echange.feature.FeatureEChange
import tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange
import org.eclipse.emf.ecore.InternalEObject
import tools.vitruv.framework.uuid.UuidResolver
import tools.vitruv.framework.change.interaction.UserInteractionBase
import org.eclipse.emf.ecore.util.EcoreUtil

abstract class AbstractConcreteChange implements ConcreteChange {
	private static val logger = Logger.getLogger(AbstractConcreteChange);
	private var EChange eChange;
	private List<UserInteractionBase> userInteractions;
	
	new(EChange eChange) {
		this.eChange = eChange;
		this.userInteractions = new ArrayList<UserInteractionBase>();
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
	
	override resolveBeforeAndApplyForward(UuidResolver uuidResolver) {
		logger.warn("The resolveBeforeAndapplyForward method is not implemented for " + this.class.simpleName + " yet.");
	}
	
	override resolveAfterAndApplyBackward(UuidResolver uuidResolver) {
		logger.warn("The resolveAfterAndApplyBackward method is not implemented for " + this.class.simpleName + " yet.");
	}
	
	override unresolveIfApplicable() {
		// Do nothing	
	}
		
	def getAffectedNotReferencedEObjects() {
		return this.eChange.affectedEObjects.filterNull
	}
	
	override getAffectedEObjectIds() {
		return this.eChange.affectedEObjectIds.filterNull
	}
	
	override getAffectedEObjects() {
		return affectedNotReferencedEObjects + this.eChange.referencedEObjects.filterNull
	}
	
	override getUserInteractions() {
	    return userInteractions
	}
	
	override setUserInteractions(Iterable<UserInteractionBase> userInteractions) {
		if (userInteractions === null) {
			throw new IllegalArgumentException("Interactions must not be null");
		}
		this.userInteractions.clear();
        this.userInteractions += userInteractions
    }
	
	override equals(Object obj) {
		return obj.isEqual // delegate to dynamic dispatch
	}
	
	private def dispatch boolean isEqual(Object object) { false }

	private def dispatch boolean isEqual(ConcreteChange change) {
		return EcoreUtil.equals(eChange, change.EChange)
	}
	
	private def dispatch List<String> getAffectedEObjectIds(EChange eChange) {
		return #[]
	}
	
	private def dispatch List<String> getAffectedEObjectIds(InsertRootEObject<EObject> eChange) {
		return #[eChange.newValueID]
	}

	private def dispatch List<String> getAffectedEObjectIds(RemoveRootEObject<EObject> eChange) {
		return #[eChange.oldValueID]
	}
	
	private def dispatch List<String> getAffectedEObjectIds(InsertEReference<EObject, EObject> eChange) {
		return #[eChange.affectedEObjectID, eChange.newValueID]
	}
	
	private def dispatch List<String> getAffectedEObjectIds(RemoveEReference<EObject, EObject> eChange) {
		return #[eChange.affectedEObjectID, eChange.oldValueID]
	}
	
	private def dispatch List<String> getAffectedEObjectIds(ReplaceSingleValuedEReference<EObject, EObject> eChange) {
		return #[eChange.affectedEObjectID, eChange.newValueID, eChange.oldValueID]
	}
	
	private def dispatch List<String> getAffectedEObjectIds(FeatureEChange<EObject, ?> eChange) {
		return #[eChange.affectedEObjectID]
	}
	
	private def dispatch List<String> getAffectedEObjectIds(EObjectExistenceEChange<EObject> eChange) {
		return #[eChange.affectedEObjectID]
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