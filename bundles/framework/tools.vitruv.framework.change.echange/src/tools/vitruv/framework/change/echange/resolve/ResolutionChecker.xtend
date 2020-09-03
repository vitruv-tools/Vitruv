package tools.vitruv.framework.change.echange.resolve

import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange
import tools.vitruv.framework.change.echange.feature.FeatureEChange
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference
import tools.vitruv.framework.change.echange.root.RootEChange
import tools.vitruv.framework.change.echange.root.InsertRootEObject
import tools.vitruv.framework.change.echange.root.RemoveRootEObject

class ResolutionChecker {
	static def dispatch isResolved(EChange echange) {
		return true;
	}
	
	static def dispatch isResolved(EObjectExistenceEChange<?> existenceChange) {
		return (existenceChange.getAffectedEObject() !== null &&
			!existenceChange.getAffectedEObject().eIsProxy());// && 
			//existenceChange.getStagingArea() !== null);
	}
	
	static def dispatch isResolved(FeatureEChange<?,?> featureChange) {
		return featureChange.isFeatureChangeResolved;
	}
	
	static def isFeatureChangeResolved(FeatureEChange<?,?> featureChange) {
		return (featureChange.getAffectedEObject() !== null &&
			!featureChange.getAffectedEObject().eIsProxy() && 
			featureChange.getAffectedFeature() !== null);
	}
	
	static def dispatch isResolved(InsertEReference<?,?> insertEReferenceChange) {
		return (insertEReferenceChange.newValue === null
			|| ! insertEReferenceChange.newValue.eIsProxy)
			&& insertEReferenceChange.featureChangeResolved;	
	}
	
	static def dispatch isResolved(RemoveEReference<?,?> removeEReferenceChange) {
		return (removeEReferenceChange.oldValue === null
			|| ! removeEReferenceChange.oldValue.eIsProxy)
			&& removeEReferenceChange.featureChangeResolved;	
	}
	
	static def dispatch isResolved(ReplaceSingleValuedEReference<?,?> replaceSingleValuedEReferenceChange) {
		return (replaceSingleValuedEReferenceChange.oldValue === null
			|| ! replaceSingleValuedEReferenceChange.oldValue.eIsProxy)
			&& (replaceSingleValuedEReferenceChange.newValue === null
			|| ! replaceSingleValuedEReferenceChange.newValue.eIsProxy)
			&& replaceSingleValuedEReferenceChange.featureChangeResolved;	
	}
	
	static def dispatch isResolved(RootEChange rootChange) {
		return rootChange.isRootChangeResolved;
	}
	
	static def isRootChangeResolved(RootEChange rootChange) {
		return rootChange.resource !== null;
	}
	
	static def dispatch isResolved(InsertRootEObject<?> insertRootChange) {
		return insertRootChange.newValue !== null &&
			!insertRootChange.newValue.eIsProxy && 
			insertRootChange.isRootChangeResolved;
	}
	
	static def dispatch isResolved(RemoveRootEObject<?> removeRootChange) {
		return removeRootChange.oldValue !== null &&
			!removeRootChange.oldValue.eIsProxy && 
			removeRootChange.isRootChangeResolved;
	}
	
}