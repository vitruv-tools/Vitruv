package tools.vitruv.framework.change.echange.resolve

import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.compound.CompoundEChange
import tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange
import tools.vitruv.framework.change.echange.feature.FeatureEChange
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference
import tools.vitruv.framework.change.echange.root.InsertRootEObject
import tools.vitruv.framework.change.echange.root.RemoveRootEObject
import tools.vitruv.framework.change.echange.root.RootEChange

class ResolutionChecker {
	static def dispatch isResolved(EChange echange) { true }

	static def dispatch isResolved(CompoundEChange compoundChange) {
		return compoundChange.atomicChanges.parallelStream.allMatch[it.resolved]
	}

	static def dispatch isResolved(EObjectExistenceEChange<?> existenceChange) {
		return (existenceChange.affectedEObject !== null && !existenceChange.affectedEObject.eIsProxy &&
			existenceChange.stagingArea !== null)
	}

	static def dispatch isResolved(FeatureEChange<?, ?> featureChange) {
		return featureChange.isFeatureChangeResolved
	}

	static def isFeatureChangeResolved(FeatureEChange<?, ?> featureChange) {
		return (featureChange.affectedEObject !== null && !featureChange.affectedEObject.eIsProxy &&
			featureChange.affectedFeature !== null)
	}

	static def dispatch isResolved(InsertEReference<?, ?> insertEReferenceChange) {
		return (insertEReferenceChange.newValue === null || ! insertEReferenceChange.newValue.eIsProxy) &&
			insertEReferenceChange.featureChangeResolved;
	}

	static def dispatch isResolved(RemoveEReference<?, ?> removeEReferenceChange) {
		return (removeEReferenceChange.oldValue === null || ! removeEReferenceChange.oldValue.eIsProxy) &&
			removeEReferenceChange.featureChangeResolved;
	}

	static def dispatch isResolved(ReplaceSingleValuedEReference<?, ?> replaceSingleValuedEReferenceChange) {
		return (replaceSingleValuedEReferenceChange.oldValue === null ||
			! replaceSingleValuedEReferenceChange.oldValue.eIsProxy) &&
			(replaceSingleValuedEReferenceChange.newValue === null ||
				! replaceSingleValuedEReferenceChange.newValue.eIsProxy) &&
			replaceSingleValuedEReferenceChange.featureChangeResolved;
	}

	static def dispatch isResolved(RootEChange rootChange) {
		return rootChange.isRootChangeResolved
	}

	static def isRootChangeResolved(RootEChange rootChange) {
		return rootChange.resource !== null
	}

	static def dispatch isResolved(InsertRootEObject<?> insertRootChange) {
		return insertRootChange.newValue !== null && !insertRootChange.newValue.eIsProxy &&
			insertRootChange.isRootChangeResolved
	}

	static def dispatch isResolved(RemoveRootEObject<?> removeRootChange) {
		return removeRootChange.oldValue !== null && !removeRootChange.oldValue.eIsProxy &&
			removeRootChange.isRootChangeResolved
	}
}
