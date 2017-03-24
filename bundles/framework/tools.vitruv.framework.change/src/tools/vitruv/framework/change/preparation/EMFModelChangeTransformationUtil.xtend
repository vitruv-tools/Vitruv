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
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute
import tools.vitruv.framework.change.echange.feature.attribute.SubtractiveAttributeEChange

import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.EObjectUtil.*

/**
 * A utility class providing extension methods for transforming change descriptions to change models.
 * 
 */
package class EMFModelChangeTransformationUtil {
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

				resultChanges += TypeInferringAtomicEChangeFactory.instance.createInsertAttributeChange(affectedEObject, affectedAttribute, index, newValues.get(index));
			}
			return resultChanges;
		} else {
			val oldValue = affectedAttribute.defaultValue
			val newValue = affectedEObject.getFeatureValue(affectedAttribute)

			return #[TypeInferringAtomicEChangeFactory.instance.createReplaceSingleAttributeChange(affectedEObject, affectedAttribute, oldValue, newValue)]
		}
	}
	
	
	def static List<EChange> createAdditiveEChangeForReferencedObject(EObject referencingEObject, EReference reference, boolean forceCreate) {
		val result = new ArrayList<EChange>(); 
		if (reference.isMany) {
			for (referenceValue : referencingEObject.getReferenceValueList(reference)) {
				result += createInsertReferenceChange(referencingEObject, reference, (referencingEObject.eGet(reference) as EList<?>).indexOf(referenceValue), referenceValue, false);
			}
		} else {
			result += createReplaceSingleValuedReferenceChange(referencingEObject, reference, null, referencingEObject.getReferenceValueList(reference).get(0), false);
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
	

	def static private boolean isRootContainer(EObject container) {
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
	

	def static private boolean isChangeableUnderivedPersistedNotContainingFeature(EObject eObject, EStructuralFeature feature) {
        return feature.isChangeable() && !feature.isDerived() && !feature.isTransient() && feature != eObject.eContainingFeature();
	}
	
	def static private boolean valueIsNonDefault(EObject eObject, EStructuralFeature feature) {
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
	

	def static EChange createInsertRootChange(EObject rootToInsert, EObject oldRootContainer, Resource oldRootResource, Resource newResource, int index) {
		val isCreate = isCreate(oldRootContainer, oldRootResource)
		if (isCreate) {
			return TypeInferringCompoundEChangeFactory.instance.createCreateAndInsertRootChange(rootToInsert, newResource, index);
		} else {
			return TypeInferringAtomicEChangeFactory.instance.createInsertRootChange(rootToInsert, newResource, index)
		}
	}
	
	def static boolean isCreate(EObject oldContainer, Resource oldResource) {
		return (oldContainer == null || oldContainer instanceof ChangeDescription) && oldResource == null
	}
	
	def static boolean isDelete(EObject newContainer, Resource newResource) {
		return (newContainer == null || newContainer instanceof ChangeDescription) && newResource == null
	}
	

	def static EChange createRemoveRootChange(EObject rootToRemove, EObject newRootContainer, Resource newRootResource, Resource oldResource, int index) {
		val isDelete = isDelete(newRootContainer, newRootResource)
		if (isDelete) {
			return TypeInferringCompoundEChangeFactory.instance.createRemoveAndDeleteRootChange(rootToRemove, oldResource, index);
		} else {
			return TypeInferringAtomicEChangeFactory.instance.createRemoveRootChange(rootToRemove, oldResource, index);
		}
	}
	
	def static EChange createInsertReferenceChange(EObject affectedEObject, EReference affectedReference, int index, EObject referenceValue, boolean forceCreate) {
		val isContainment = affectedReference.containment
		val oldContainer = referenceValue.eContainer
		val oldResource = referenceValue.eResource
		val isCreate = forceCreate || (isContainment && isCreate(oldContainer,oldResource))
		if (isCreate) {
			return TypeInferringCompoundEChangeFactory.instance.createCreateAndInsertNonRootChange(affectedEObject, affectedReference, referenceValue, index);
		} else {
			return TypeInferringAtomicEChangeFactory.instance.createInsertReferenceChange(affectedEObject, affectedReference, referenceValue, index);
		}
	}
	
	def static EChange createRemoveReferenceChange(EObject affectedEObject, EReference affectedReference, int index, EObject referenceValue, EObject newContainer, Resource newResource) {
		val isContainment = affectedReference.containment
		val isDelete = isContainment && isDelete(newContainer,newResource)
		if (isDelete) {
			return TypeInferringCompoundEChangeFactory.instance.createRemoveAndDeleteNonRootChange(affectedEObject, affectedReference, referenceValue, index);
		} else {
			return TypeInferringAtomicEChangeFactory.instance.createRemoveReferenceChange(affectedEObject, affectedReference, referenceValue, index);
		}
	}
	
	def static EChange createReplaceSingleValuedReferenceChange(EObject affectedEObject, EReference affectedReference, EObject oldReferenceValue, EObject newReferenceValue, boolean forceCreate) {
		val isContainment = affectedReference.containment
		val isCreate = newReferenceValue != null && isContainment && isCreate(newReferenceValue.eContainer, newReferenceValue.eResource)
		val isDelete = oldReferenceValue != null && isContainment;// && isDelete(oldReferenceValue.eContainer, oldReferenceValue.eResource)
		if (forceCreate || (isCreate && isDelete)) {
			return TypeInferringCompoundEChangeFactory.instance.createCreateAndReplaceAndDeleteNonRootChange(affectedEObject, affectedReference, oldReferenceValue, newReferenceValue);
		} else {
			return TypeInferringAtomicEChangeFactory.instance.createReplaceSingleReferenceChange(affectedEObject, affectedReference, oldReferenceValue, newReferenceValue);
		}
	}
	
	def static EChange createInsertAttributeChange(EObject affectedEObject, EAttribute affectedAttribute, int index, Object newValue) {
		return TypeInferringAtomicEChangeFactory.instance.createInsertAttributeChange(affectedEObject, affectedAttribute, index, newValue)
	}
	

	def static EChange createRemoveAttributeChange(EObject affectedEObject, 
		EAttribute affectedAttribute, int index, Object oldValue) {
		return TypeInferringAtomicEChangeFactory.instance.createRemoveAttributeChange(affectedEObject, affectedAttribute, index, oldValue)
	}
	
	def static ReplaceSingleValuedEAttribute<EObject, Object> createReplaceSingleValuedAttributeChange(EObject affectedEObject, EAttribute affectedAttribute, Object oldValue, Object newValue) {
		return TypeInferringAtomicEChangeFactory.instance.createReplaceSingleAttributeChange(affectedEObject, affectedAttribute, oldValue, newValue)
	}
	

	def static createExplicitUnsetEAttributeChange(EObject affectedEObject, EAttribute affectedAttribute, List<SubtractiveAttributeEChange<EObject, Object>> changes) {
		return TypeInferringCompoundEChangeFactory.instance.createExplicitUnsetEAttributeChange(affectedEObject, affectedAttribute, changes);
	}
	
	def static EChange createExplicitUnsetEReferenceChange(EObject affectedEObject, EReference affectedReference, List<EChange> changes) {
		return TypeInferringCompoundEChangeFactory.instance.createExplicitUnsetEReferenceChange(affectedEObject, affectedReference, changes);
	}
	
}