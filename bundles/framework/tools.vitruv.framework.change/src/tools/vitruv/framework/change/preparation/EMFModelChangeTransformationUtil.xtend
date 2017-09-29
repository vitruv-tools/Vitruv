package tools.vitruv.framework.change.preparation

import java.util.ArrayList
import java.util.List
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.change.ChangeDescription
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.TypeInferringAtomicEChangeFactory
import tools.vitruv.framework.change.echange.TypeInferringCompoundEChangeFactory
import tools.vitruv.framework.change.echange.feature.attribute.AdditiveAttributeEChange
import tools.vitruv.framework.change.echange.feature.attribute.SubtractiveAttributeEChange

import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.EObjectUtil.*

/**
 * A utility class providing extension methods for transforming change descriptions to change models.
 * 
 */
public class EMFModelChangeTransformationUtil {
	def static List<EChange> createAdditiveCreateChangesForValue(EObject eObject, EReference reference) {
		return createAdditiveEChangeForReferencedObject(eObject, reference, true)
	}
	
	def static dispatch List<EChange> createSubtractiveChangesForValue(EObject eObject, EReference reference) {
		return createSubtractiveEChangeForReferencedObject(eObject, reference, false)
	}
	
	def static dispatch List<SubtractiveAttributeEChange<?,Object>> createSubtractiveChangesForValue(EObject eObject, EAttribute attribute) {
		return createSubtractiveEChangeForAttribute(eObject, attribute)
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
				resultChanges += TypeInferringAtomicEChangeFactory.instance.createInsertAttributeChange(affectedEObject, affectedAttribute, index, newValues.get(index));
			}
			return resultChanges;
		} else {
			val oldValue = affectedAttribute.defaultValue
			val newValue = affectedEObject.getFeatureValue(affectedAttribute)

			return #[TypeInferringAtomicEChangeFactory.instance.createReplaceSingleAttributeChange(affectedEObject, affectedAttribute, oldValue, newValue)]
		}
	}
	
	def static <A extends EObject> List<SubtractiveAttributeEChange<?, Object>> createSubtractiveEChangeForAttribute(A affectedEObject, EAttribute affectedAttribute) {
		if (affectedAttribute.many) {
			val oldValues = affectedEObject.getFeatureValues(affectedAttribute)
			val resultChanges = new ArrayList<SubtractiveAttributeEChange<?, Object>>();
			for (var index = oldValues.size - 1; index >= 0; index--) {
				resultChanges += TypeInferringAtomicEChangeFactory.instance.createRemoveAttributeChange(affectedEObject, affectedAttribute, index, oldValues.get(index));
			}
			return resultChanges;
		} else {
			val oldValue = affectedEObject.getFeatureValue(affectedAttribute)
			val newValue = affectedAttribute.defaultValue
			
			return #[TypeInferringAtomicEChangeFactory.instance.createReplaceSingleAttributeChange(affectedEObject, affectedAttribute, oldValue, newValue)]
		}
	}
	
	def static EList<EObject> getReferenceValueList(EObject eObject, EReference reference) {
		return getValueList(eObject, reference) as EList<EObject>
	}
	
	def static EList<Object> getReferenceValueList(EObject eObject, EAttribute attribute) {
		return getValueList(eObject, attribute) as EList<Object>
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
	
	def static List<EChange> createSubtractiveEChangeForReferencedObject(EObject referencingEObject, EReference reference, boolean forceDelete) {
		val result = new ArrayList<EChange>(); 
		if (reference.isMany) {
			for (referenceValue : referencingEObject.getReferenceValueList(reference).reverseView) {
				result += createRemoveReferenceChange(referencingEObject, reference, (referencingEObject.eGet(reference) as EList<?>).indexOf(referenceValue), referenceValue, null, null, forceDelete);
			}
		} else {

			result += createReplaceSingleValuedReferenceChange(referencingEObject, reference, referencingEObject.getReferenceValueList(reference).get(0), null, forceDelete);
		}
		return result;
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
	
	def static private boolean isChangeableUnderivedPersistedNotContainingFeature(EObject eObject, EStructuralFeature feature) {

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
			return list !== null && !list.isEmpty
		} else {
			// TODO MK is equals (== in Xtend) for feature default value comparison okay or is identity (===) needed?
			return value != feature.defaultValue
		}
	}
	
	def static boolean hasChangeableUnderivedPersistedNotContainingNonDefaultValue(EObject eObject, EStructuralFeature feature) {
		return isChangeableUnderivedPersistedNotContainingFeature(eObject, feature) && valueIsNonDefault(eObject, feature)
	}
	
	def static boolean isDelete(EObject newContainer, Resource newResource) {
		return (newContainer === null || newContainer instanceof ChangeDescription) && newResource === null
	}

	def static List<EChange> createInsertReferenceChange(EObject affectedEObject, EReference affectedReference, int index, EObject referenceValue, boolean forceCreate) {
		val isContainment = affectedReference.containment

		val oldResource = referenceValue.eResource

		val isCreate = forceCreate || (isContainment && oldResource === null)
		if (isCreate) {

			return TypeInferringCompoundEChangeFactory.instance.createCreateAndInsertNonRootChange(affectedEObject, affectedReference, referenceValue, index);
		} else {

			return #[TypeInferringAtomicEChangeFactory.instance.createInsertReferenceChange(affectedEObject, affectedReference, referenceValue, index)];
		}
	}
	
	def static List<EChange> createRemoveReferenceChange(EObject affectedEObject, EReference affectedReference, int index, EObject referenceValue, EObject newContainer, Resource newResource, boolean forceDelete) {
		val isContainment = affectedReference.containment

		val isDelete = forceDelete || (isContainment && isDelete(newContainer, newResource))
		if (isDelete) {

			return TypeInferringCompoundEChangeFactory.instance.createRemoveAndDeleteNonRootChange(affectedEObject, affectedReference, referenceValue, index);
		} else {

			return #[TypeInferringAtomicEChangeFactory.instance.createRemoveReferenceChange(affectedEObject, affectedReference, referenceValue, index)];
		}
	}
	
	def static List<EChange> createReplaceSingleValuedReferenceChange(EObject affectedEObject, EReference affectedReference, EObject oldReferenceValue, EObject newReferenceValue, boolean forceCreate) {
		val isContainment = affectedReference.containment

		if (forceCreate || isContainment) {
			if (oldReferenceValue === null) {
				return TypeInferringCompoundEChangeFactory.instance.createCreateAndReplaceNonRootChange(affectedEObject, affectedReference, newReferenceValue)
			} else if (newReferenceValue === null) {
				return TypeInferringCompoundEChangeFactory.instance.createReplaceAndDeleteNonRootChange(affectedEObject, affectedReference, oldReferenceValue)
			} else {
				return TypeInferringCompoundEChangeFactory.instance.createCreateAndReplaceAndDeleteNonRootChange(affectedEObject, affectedReference, oldReferenceValue, newReferenceValue);				
			}
		} else {

			return #[TypeInferringAtomicEChangeFactory.instance.createReplaceSingleReferenceChange(affectedEObject, affectedReference, oldReferenceValue, newReferenceValue)];
		}
	}
	
}