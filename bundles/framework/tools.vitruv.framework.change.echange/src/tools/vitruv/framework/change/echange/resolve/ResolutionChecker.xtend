package tools.vitruv.framework.change.echange.resolve

import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.AtomicEChange
import tools.vitruv.framework.change.echange.compound.CompoundEChange
import tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange
import tools.vitruv.framework.change.echange.feature.FeatureEChange
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference
import tools.vitruv.framework.change.echange.root.RootEChange
import tools.vitruv.framework.change.echange.root.InsertRootEObject
import tools.vitruv.framework.change.echange.root.RemoveRootEObject

class ResolutionChecker {
	public static def dispatch isResolved(EChange echange) {
		return true;
	}
	
	public static def dispatch isResolved(CompoundEChange compoundChange) {
		val atomicChanges = compoundChange.getAtomicChanges();
		for (AtomicEChange change : atomicChanges) {
			if (!change.isResolved()) {
				return false;
			}
		}
		return true;
	}
	
	public static def dispatch isResolved(EObjectExistenceEChange<?> existenceChange) {
		return (existenceChange.getAffectedEObject() !== null &&
			!existenceChange.getAffectedEObject().eIsProxy() && 
			existenceChange.getStagingArea() !== null);
	}
	
	public static def dispatch isResolved(FeatureEChange<?,?> featureChange) {
		return featureChange.isFeatureChangeResolved;
	}
	
	public static def isFeatureChangeResolved(FeatureEChange<?,?> featureChange) {
		return (featureChange.getAffectedEObject() !== null &&
			!featureChange.getAffectedEObject().eIsProxy() && 
			featureChange.getAffectedFeature() !== null);
	}
	
	public static def dispatch isResolved(InsertEReference<?,?> insertEReferenceChange) {
		return (insertEReferenceChange.newValue === null
			|| ! insertEReferenceChange.newValue.eIsProxy)
			&& insertEReferenceChange.featureChangeResolved;	
	}
	
	public static def dispatch isResolved(RemoveEReference<?,?> removeEReferenceChange) {
		return (removeEReferenceChange.oldValue === null
			|| ! removeEReferenceChange.oldValue.eIsProxy)
			&& removeEReferenceChange.featureChangeResolved;	
	}
	
	public static def dispatch isResolved(ReplaceSingleValuedEReference<?,?> replaceSingleValuedEReferenceChange) {
		return (replaceSingleValuedEReferenceChange.oldValue === null
			|| ! replaceSingleValuedEReferenceChange.oldValue.eIsProxy)
			&& (replaceSingleValuedEReferenceChange.newValue === null
			|| ! replaceSingleValuedEReferenceChange.newValue.eIsProxy)
			&& replaceSingleValuedEReferenceChange.featureChangeResolved;	
	}
	
	public static def dispatch isResolved(RootEChange rootChange) {
		return rootChange.isRootChangeResolved;
	}
	
	public static def isRootChangeResolved(RootEChange rootChange) {
		return rootChange.resource !== null;
	}
	
	public static def dispatch isResolved(InsertRootEObject<?> insertRootChange) {
		return insertRootChange.newValue !== null &&
			!insertRootChange.newValue.eIsProxy && 
			insertRootChange.isRootChangeResolved;
	}
	
	public static def dispatch isResolved(RemoveRootEObject<?> removeRootChange) {
		return removeRootChange.oldValue !== null &&
			!removeRootChange.oldValue.eIsProxy && 
			removeRootChange.isRootChangeResolved;
	}
	
}