package edu.kit.ipd.sdq.vitruvius.framework.changedescription2change

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.InsertRootEObject
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.RemoveRootEObject
import java.util.List
import org.eclipse.emf.common.util.BasicEList
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.change.ChangeDescription
import org.eclipse.emf.ecore.resource.Resource

import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.EObjectUtil.*
import static edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.TypeInferringAtomicEChangeFactory.*

/**
 * A utility class providing extension methods for transforming change descriptions to change models.
 * 
 */
class ChangeDescription2ChangeUtil {
	/** Utility classes should not have a public or default constructor. */
	private new() {
	}
	
	def static dispatch EChange createAdditiveChangeForValue(EObject eObject,  EReference reference) {
		return createAdditiveEChangeForReferencedObject(eObject, reference)
	}
	
	def static dispatch EChange createAdditiveChangeForValue(EObject eObject,  EAttribute attribute) {
		return createAdditiveEChangeForAttributeValue(eObject, attribute)
	}
	
	def static EChange createAdditiveEChangeForReferencedObject(EObject referencingEObject, EReference reference) {
		// FIXME MK ChangeBridge
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	def static EChange createAdditiveEChangeForAttributeValue(EObject eObject, EAttribute attribute) {
		return createAdditiveAttributeChange(eObject, attribute)
	}
	
	def static EChange createSubtractiveEChangeForReferencedObject(EObject parent, EObject child, EReference reference) {
		// FIXME MK ChangeBridge
//		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	def static boolean isRootAfterChange(EObject eObject, EObject newContainer) {
		return isRootContainer(newContainer)
	}
	
	def static boolean wasRootBeforeChange(EObject eObject) {
		val oldContainer = eObject.eContainer()
		return isRootContainer(oldContainer)
	}
	
	def private static boolean isRootContainer(EObject container) {
		return container == null || container instanceof ChangeDescription
	}
	
	def static EChange createSubtractiveEChangeForEObject(EObject eObject) {
		// FIXME MK ChangeBridge
//		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	def static boolean hasNonDefaultValue(EObject eObject) {
		var hasNonDefaultValue = false
		for (feature : eObject.eClass.EAllStructuralFeatures) {
			if (isChangeableUnderivedPersistedNotContainingFeature(eObject, feature)) {
				hasNonDefaultValue = hasNonDefaultValue || valueIsNonDefault(eObject, feature)
			}
		}
		return hasNonDefaultValue
	}
	
	def private static boolean isChangeableUnderivedPersistedNotContainingFeature(EObject eObject, EStructuralFeature feature) {
        return feature.isChangeable() && !feature.isDerived() && !feature.isTransient() && feature != eObject.eContainingFeature();
	}
	
	def private static boolean valueIsNonDefault(EObject eObject, EStructuralFeature feature) {
		val value = eObject.eGet(feature)
		if (feature.many) {
			val list = value as List<?>
			return list != null && !list.isEmpty
		} else {
			// TODO MK is equals (== in Xtend) for feature default value comparison okay or is identity (===) needed?
			return value != feature.defaultValue
		}
	}
	
	def static boolean hasChangeableUnderivedPersistedNotContainingNonDefaultValue(EObject eObject, EStructuralFeature feature) {
		return isChangeableUnderivedPersistedNotContainingFeature(eObject, feature) && valueIsNonDefault(eObject, feature)
	}
	
	def static dispatch EList<EObject> getReferenceValueList(EObject eObject, EStructuralFeature feature) {
		return new BasicEList
	}
	
	def static dispatch EList<EObject> getReferenceValueList(EObject eObject, EReference reference) {
		return getValueList(eObject, reference) as EList<EObject>
	}
	
	def static InsertRootEObject<?> createInsertRootChange(EObject rootToInsert, EObject oldRootContainer, Resource oldRootResource, String resourceURI) {
		val isCreate = isRootContainer(oldRootContainer) && oldRootResource == null
		return createInsertRootChange(rootToInsert, isCreate, resourceURI)
	}
	
	def static RemoveRootEObject<?> createRemoveRootChange(EObject rootToRemove, EObject newRootContainer, Resource newRootResource, String resourceURI) {
		val isDelete = isRootContainer(newRootContainer) && newRootResource == null
		return createRemoveRootChange(rootToRemove, isDelete, resourceURI)
	}
	
}