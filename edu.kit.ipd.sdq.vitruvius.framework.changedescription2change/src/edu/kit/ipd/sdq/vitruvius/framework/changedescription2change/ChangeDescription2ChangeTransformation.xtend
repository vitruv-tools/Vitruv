package edu.kit.ipd.sdq.vitruvius.framework.changedescription2change

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange
import java.util.List
import org.eclipse.emf.common.util.BasicEList
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.change.ChangeDescription
import org.eclipse.emf.ecore.EReference

class ChangeDescription2ChangeTransformation {
	
	def static List<EChange> transform(ChangeDescription changeDescription) {
		val eChanges = new BasicEList<EChange>
		val objectsToAttach = changeDescription.getObjectsToAttach
		objectsToAttach.forEach[recursivelyAddChangesForNonDefaultValues(it, eChanges)]
		// TODO MK inspire from old transformation
		val objectsToDetach = changeDescription.getObjectsToDetach
		val objectAndFeatureChangePairs = changeDescription.getObjectChanges().entrySet
		
		return null
	}
	
	def static void recursivelyAddChangesForNonDefaultValues(EObject eObject, List<EChange> eChanges) {
		if (hasNonDefaultValue(eObject)) {
			for (reference : eObject.eClass.EAllReferences) {
				if (hasChangeableUnderivedPersistedNotContainingNonDefaultValue(eObject, reference)) {
					eChanges.add(ChangeBridge.createChangeForReferencedObject(eObject, reference))
					getReferenceValueList(eObject, reference).forEach[recursivelyAddChangesForNonDefaultValues(it, eChanges)]
				}
			}
			// TODO MK attributes
			
		}
	}
	
	def static boolean hasNonDefaultValue(EObject eObject) {
		var hasNonDefaultValue = false
		for (feature : eObject.eClass.EAllStructuralFeatures) {
			if (isChangeableUnderivedPersistedNotContainingFeature(eObject, feature)) {
				hasNonDefaultValue = hasNonDefaultValue || hasNonDefaultValue(eObject, feature)
			}
		}
		return hasNonDefaultValue
	}
	
	def static boolean isChangeableUnderivedPersistedNotContainingFeature(EObject eObject, EStructuralFeature feature) {
        return feature.isChangeable() && !feature.isDerived() && !feature.isTransient() && feature != eObject.eContainingFeature();
	}
	
	def static boolean hasNonDefaultValue(EObject eObject, EStructuralFeature feature) {
		val value = eObject.eGet(feature)
		if (feature.many) {
			val eList = value as EList<?>
			return eList != null && !eList.isEmpty
		} else {
			// TODO MK use equals for feature default value comparison?
			return value != feature.defaultValue
		}
	}
	
	def static boolean hasChangeableUnderivedPersistedNotContainingNonDefaultValue(EObject eObject, EStructuralFeature feature) {
		return isChangeableUnderivedPersistedNotContainingFeature(eObject, feature) && hasNonDefaultValue(eObject, feature)
	}
	
	def static EList<EObject> getReferenceValueList(EObject eObject, EReference reference) {
		return getValueList(eObject, reference) as EList<EObject>
	}
	
	def static EList<?> getValueList(EObject eObject, EStructuralFeature feature) {
		val value = eObject.eGet(feature)
		if (feature.many && value != null) {
			return value as EList<?>
		} else {
			return new BasicEList(#[value])
		}
	}
}