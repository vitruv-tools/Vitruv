package tools.vitruv.framework.change.preparation

import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.root.InsertRootEObject
import tools.vitruv.framework.change.echange.root.RemoveRootEObject
import java.util.List
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.change.ChangeDescription
import org.eclipse.emf.ecore.resource.Resource

import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.EObjectUtil.*
import static tools.vitruv.framework.change.echange.TypeInferringAtomicEChangeFactory.*
import tools.vitruv.framework.change.echange.TypeInferringAtomicEChangeFactory
import java.util.ArrayList
import tools.vitruv.framework.change.echange.AdditiveEChange
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference
import tools.vitruv.framework.change.echange.feature.attribute.UpdateAttributeEChange
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference

/**
 * A utility class providing extension methods for transforming change descriptions to change models.
 * 
 */
package class EMFModelChangeTransformationUtil {
	/** Utility classes should not have a public or default constructor. */
	private new() {
	}
	
	def static dispatch List<AdditiveEChange<?>> createAdditiveChangesForValue(EObject eObject,  EReference reference) {
		return createAdditiveEChangeForReferencedObject(eObject, reference)
	}
	
	def static dispatch List<AdditiveEChange<?>> createAdditiveChangesForValue(EObject eObject,  EAttribute attribute) {
		return #[createAdditiveEChangeForAttributeValue(eObject, attribute)]
	}
	
	def static List<AdditiveEChange<?>> createAdditiveEChangeForReferencedObject(EObject referencingEObject, EReference reference) {
		val result = new ArrayList<AdditiveEChange<?>>(); 
		if (reference.isMany) {
			for (referenceValue : referencingEObject.getReferenceValueList(reference)) {
				result += createInsertReferenceChange(referencingEObject, reference, (referencingEObject.eGet(reference) as EList<?>).indexOf(referenceValue), referenceValue);
			}
		} else {
				result += createReplaceSingleValuedReferenceChange(referencingEObject, reference, null, referencingEObject.getReferenceValueList(reference).get(0));
		}
		return result;
		// FIXME MK ChangeBridge
		//throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	def static AdditiveEChange<?> createAdditiveEChangeForAttributeValue(EObject eObject, EAttribute attribute) {
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
		return container == null //|| container instanceof ChangeDescription
	}
	
	def static EChange createSubtractiveEChangeForEObject(EObject eObject) {
		// FIXME MK ChangeBridge
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
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
	
	def static EList<EObject> getReferenceValueList(EObject eObject, EReference reference) {
		return getValueList(eObject, reference) as EList<EObject>
	}
	
	def static EList<Object> getReferenceValueList(EObject eObject, EAttribute attribute) {
		return getValueList(eObject, attribute) as EList<Object>
	}
	
	def static InsertRootEObject<?> createInsertRootChange(EObject rootToInsert, EObject oldRootContainer, Resource oldRootResource, String resourceURI) {
		val isCreate = isCreate(oldRootContainer, oldRootResource)
		return createInsertRootChange(rootToInsert, isCreate, resourceURI)
	}
	
	def static boolean isCreate(EObject oldContainer, Resource oldResource) {
		return (oldContainer == null || oldContainer instanceof ChangeDescription) && oldResource == null
	}
	
	def static boolean isDelete(EObject newContainer, Resource newResource) {
		return (newContainer == null || newContainer instanceof ChangeDescription) && newResource == null
	}
	
	def static RemoveRootEObject<?> createRemoveRootChange(EObject rootToRemove, EObject newRootContainer, Resource newRootResource, String resourceURI) {
		val isDelete = isDelete(newRootContainer, newRootResource)
		return createRemoveRootChange(rootToRemove, isDelete, resourceURI)
	}
	
	def static InsertEReference<?,?> createInsertReferenceChange(EObject affectedEObject, EReference affectedReference, int index, EObject referenceValue) {
		val isContainment = affectedReference.containment
		val oldContainer = referenceValue.eContainer
		val oldResource = referenceValue.eResource
		val isCreate = isContainment && isCreate(oldContainer,oldResource)
		return createInsertReferenceChange(affectedEObject, affectedReference, index, referenceValue, isCreate)
	}
	
	def static EChange createRemoveReferenceChange(EObject affectedEObject, EReference affectedReference, int index, EObject referenceValue, EObject newContainer, Resource newResource) {
		val isContainment = affectedReference.containment
		val isDelete = isContainment && isDelete(newContainer,newResource)
		return createRemoveReferenceChange(affectedEObject, affectedReference, referenceValue, index, isDelete)
	}
	
	def static ReplaceSingleValuedEReference<?,?> createReplaceSingleValuedReferenceChange(EObject affectedEObject, EReference affectedReference, EObject oldReferenceValue, EObject newReferenceValue) {
		val isContainment = affectedReference.containment
		val isCreate = newReferenceValue != null && isContainment && isCreate(newReferenceValue.eContainer, newReferenceValue.eResource)
		val isDelete = oldReferenceValue != null && isContainment && oldReferenceValue.eResource == null //isDelete(container, resource)
		return createReplaceSingleReferenceChange(affectedEObject, affectedReference, oldReferenceValue, newReferenceValue, isCreate, isDelete)
	}
	
	def static EChange createInsertAttributeChange(EObject affectedEObject, EAttribute affectedAttribute, int index, Object newValue) {
		return TypeInferringAtomicEChangeFactory.createInsertAttributeChange(affectedEObject, affectedAttribute, index, newValue)
	}
	
	def static EChange createRemoveAttributeChange(EObject affectedEObject, EAttribute affectedAttribute, int index, Object oldValue) {
		return TypeInferringAtomicEChangeFactory.createRemoveAttributeChange(affectedEObject, affectedAttribute, index, oldValue)
	}
	
	def static UpdateAttributeEChange<?> createReplaceSingleValuedAttributeChange(EObject affectedEObject, EAttribute affectedAttribute, Object oldValue, Object newValue) {
		return TypeInferringAtomicEChangeFactory.createReplaceSingleAttributeChange(affectedEObject, affectedAttribute, oldValue, newValue)
	}
	
}