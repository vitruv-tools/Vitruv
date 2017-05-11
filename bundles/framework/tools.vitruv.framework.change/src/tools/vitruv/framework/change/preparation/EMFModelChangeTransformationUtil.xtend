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

import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.EObjectUtil.*
import static tools.vitruv.framework.change.echange.TypeInferringAtomicEChangeFactory.*
import static tools.vitruv.framework.change.echange.TypeInferringCompoundEChangeFactory.*
import tools.vitruv.framework.change.echange.TypeInferringAtomicEChangeFactory
import java.util.ArrayList
import tools.vitruv.framework.change.echange.feature.attribute.SubtractiveAttributeEChange
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute
import tools.vitruv.framework.change.echange.feature.attribute.AdditiveAttributeEChange
import tools.vitruv.framework.change.echange.TypeInferringCompoundEChangeFactory

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
	
	def static dispatch List<AdditiveAttributeEChange<?,Object>> createAdditiveChangesForValue(EObject eObject, EAttribute attribute) {
		return createAdditiveEChangeForAttribute(eObject, attribute)
	}
	
	def static <A extends EObject> List<AdditiveAttributeEChange<?, Object>> createAdditiveEChangeForAttribute(A affectedEObject, EAttribute affectedAttribute) {
		if (affectedAttribute.many) {
			val newValues = affectedEObject.getFeatureValues(affectedAttribute)
			val resultChanges = new ArrayList<AdditiveAttributeEChange<?, Object>>();
			for (var index = 0; index < newValues.size; index++) {
				resultChanges += TypeInferringAtomicEChangeFactory.createInsertAttributeChange(affectedEObject, affectedAttribute, index, newValues.get(index));
			}
			return resultChanges;
		} else {
			val oldValue = affectedAttribute.defaultValue
			val newValue = affectedEObject.getFeatureValue(affectedAttribute)
			return #[createReplaceSingleAttributeChange(affectedEObject, affectedAttribute, oldValue, newValue)]
		}
	}
	
	
	def static List<EChange> createAdditiveEChangeForReferencedObject(EObject referencingEObject, EReference reference, boolean forceCreate) {
		val result = new ArrayList<EChange>(); 
		if (reference.isMany) {
			for (referenceValue : referencingEObject.getReferenceValueList(reference)) {
				result += createInsertReferenceChange(referencingEObject, reference, (referencingEObject.eGet(reference) as EList<?>).indexOf(referenceValue), referenceValue, forceCreate);
			}
		} else {
				result += createReplaceSingleValuedReferenceChange(referencingEObject, reference, null, referencingEObject.getReferenceValueList(reference).get(0), forceCreate);
		}
		return result;
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
		// Ensure that its not the containing feature by checking if the value equals the container value.
		// Checking if the feature is the eContainingFeature is not correct because the eObject can be contained
		// in a reference that it declares itself (e.g. a package contained in a packagedElements reference can also 
		// have that packagedElements reference if is of the same type)
        return feature.isChangeable() && !feature.isDerived() && !feature.isTransient() && eObject.eContainer != eObject.eGet(feature);
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
		val isDelete = oldReferenceValue != null && isContainment;// && isDelete(oldReferenceValue.eContainer, oldReferenceValue.eResource)
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
	
	def static ReplaceSingleValuedEAttribute<EObject, Object> createReplaceSingleValuedAttributeChange(EObject affectedEObject, EAttribute affectedAttribute, Object oldValue, Object newValue) {
		return TypeInferringAtomicEChangeFactory.createReplaceSingleAttributeChange(affectedEObject, affectedAttribute, oldValue, newValue)
	}
	
	def static createExplicitUnsetEAttributeChange(EObject affectedEObject, EAttribute affectedAttribute, List<SubtractiveAttributeEChange<EObject, Object>> changes) {
		return TypeInferringCompoundEChangeFactory.createExplicitUnsetEAttributeChange(affectedEObject, affectedAttribute, changes);
	}
	
	def static EChange createExplicitUnsetEReferenceChange(EObject affectedEObject, EReference affectedReference, List<EChange> changes) {
		return TypeInferringCompoundEChangeFactory.createExplicitUnsetEReferenceChange(affectedEObject, affectedReference, changes);
	}
	
}