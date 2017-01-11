package tools.vitruv.framework.change.preparation

import tools.vitruv.framework.change.echange.EChange
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
import static tools.vitruv.framework.change.echange.TypeInferringCompoundEChangeFactory.*
import tools.vitruv.framework.change.echange.TypeInferringAtomicEChangeFactory
import java.util.ArrayList
import tools.vitruv.framework.change.echange.AdditiveEChange
import tools.vitruv.framework.change.echange.feature.attribute.UpdateAttributeEChange

/**
 * A utility class providing extension methods for transforming change descriptions to change models.
 * 
 */
package class EMFModelChangeTransformationUtil {
	/** Utility classes should not have a public or default constructor. */
	private new() {
	}
	
	def static List<EChange> createAdditiveCreateChangesForValue(EObject eObject, EReference reference) {
		return createAdditiveEChangeForReferencedObject(eObject, reference, true)
	}
	
	def static dispatch List<EChange> createAdditiveChangesForValue(EObject eObject, EReference reference) {
		return createAdditiveEChangeForReferencedObject(eObject, reference, false)
	}
	
	def static dispatch List<AdditiveEChange<?>> createAdditiveChangesForValue(EObject eObject, EAttribute attribute) {
		// FIXME This is wrong! We only extract the first attribute, not all
		return #[createAdditiveEChangeForAttributeValue(eObject, attribute)]
	}
	
	def static List<EChange> createAdditiveEChangeForReferencedObject(EObject referencingEObject, EReference reference, boolean forceCreate) {
		val result = new ArrayList<EChange>(); 
		if (reference.isMany) {
			for (referenceValue : referencingEObject.getReferenceValueList(reference)) {
				result += createInsertReferenceChange(referencingEObject, reference, (referencingEObject.eGet(reference) as EList<?>).indexOf(referenceValue), referenceValue, true);
			}
		} else {
				result += createReplaceSingleValuedReferenceChange(referencingEObject, reference, null, referencingEObject.getReferenceValueList(reference).get(0), true);
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
	
	def static EChange createInsertRootChange(EObject rootToInsert, EObject oldRootContainer, Resource oldRootResource, String resourceURI) {
		val isCreate = isCreate(oldRootContainer, oldRootResource)
		if (isCreate) {
			return createCreateAndInsertRootChange(rootToInsert, resourceURI);
		} else {
			return createInsertRootChange(rootToInsert, resourceURI)
		}
	}
	
	def static boolean isCreate(EObject oldContainer, Resource oldResource) {
		return (oldContainer == null || oldContainer instanceof ChangeDescription) && oldResource == null
	}
	
	def static boolean isDelete(EObject newContainer, Resource newResource) {
		return (newContainer == null || newContainer instanceof ChangeDescription) && newResource == null
	}
	
	def static EChange createRemoveRootChange(EObject rootToRemove, EObject newRootContainer, Resource newRootResource, String resourceURI) {
		val isDelete = isDelete(newRootContainer, newRootResource)
		if (isDelete) {
			return createRemoveAndDeleteRootChange(rootToRemove, resourceURI);
		} else {
			return createRemoveRootChange(rootToRemove, resourceURI);
		}
	}
	
	def static EChange createInsertReferenceChange(EObject affectedEObject, EReference affectedReference, int index, EObject referenceValue, boolean forceCreate) {
		val isContainment = affectedReference.containment
		val oldContainer = referenceValue.eContainer
		val oldResource = referenceValue.eResource
		val isCreate = forceCreate || (isContainment && isCreate(oldContainer,oldResource))
		if (isCreate) {
			return createCreateAndInsertNonRootChange(affectedEObject, affectedReference, referenceValue, index);
		} else {
			return createInsertReferenceChange(affectedEObject, affectedReference, referenceValue, index);
		}
	}
	
	def static EChange createRemoveReferenceChange(EObject affectedEObject, EReference affectedReference, int index, EObject referenceValue, EObject newContainer, Resource newResource) {
		val isContainment = affectedReference.containment
		val isDelete = isContainment && isDelete(newContainer,newResource)
		if (isDelete) {
			return createRemoveAndDeleteNonRootChange(affectedEObject, affectedReference, referenceValue, index);
		} else {
			return createRemoveReferenceChange(affectedEObject, affectedReference, referenceValue, index);
		}
	}
	
	def static EChange createReplaceSingleValuedReferenceChange(EObject affectedEObject, EReference affectedReference, EObject oldReferenceValue, EObject newReferenceValue, boolean forceCreate) {
		val isContainment = affectedReference.containment
		val isCreate = newReferenceValue != null && isContainment && isCreate(newReferenceValue.eContainer, newReferenceValue.eResource)
		val isDelete = oldReferenceValue != null && isContainment && oldReferenceValue.eResource == null //isDelete(container, resource)
		if (forceCreate || (isCreate && isDelete)) {
			return createCreateAndReplaceAndDeleteNonRootChange(affectedEObject, affectedReference, oldReferenceValue, newReferenceValue);
		} else {
			return createReplaceSingleReferenceChange(affectedEObject, affectedReference, oldReferenceValue, newReferenceValue);
		}
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