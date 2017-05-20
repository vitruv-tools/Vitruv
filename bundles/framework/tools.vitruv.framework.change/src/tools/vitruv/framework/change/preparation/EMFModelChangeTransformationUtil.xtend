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
		createAdditiveEChangeForReferencedObject(eObject, reference, true)
	}

	def static dispatch List<EChange> createAdditiveChangesForValue(EObject eObject, EReference reference) {
		createAdditiveEChangeForReferencedObject(eObject, reference, false)
	}

	def static dispatch List<AdditiveAttributeEChange<?, Object>> createAdditiveChangesForValue(EObject eObject,
		EAttribute attribute) {
		createAdditiveEChangeForAttribute(eObject, attribute)
	}

	def static <A extends EObject> List<AdditiveAttributeEChange<?, Object>> createAdditiveEChangeForAttribute(
		A affectedEObject, EAttribute affectedAttribute) {
		if (affectedAttribute.many) {
			val newValues = affectedEObject.getFeatureValues(affectedAttribute)
			val resultChanges = new ArrayList<AdditiveAttributeEChange<?, Object>>()
			for (var index = 0; index < newValues.size; index++) {
				resultChanges +=
					TypeInferringAtomicEChangeFactory::instance.createInsertAttributeChange(affectedEObject,
						affectedAttribute, index, newValues.get(index))
			}
			resultChanges
		} else {
			val oldValue = affectedAttribute.defaultValue
			val newValue = affectedEObject.getFeatureValue(affectedAttribute)
			#[
				TypeInferringAtomicEChangeFactory::instance.createReplaceSingleAttributeChange(affectedEObject,
					affectedAttribute, oldValue, newValue)]
		}
	}

	def static EList<EObject> getReferenceValueList(EObject eObject, EReference reference) {
		getValueList(eObject, reference) as EList<EObject>
	}

	def static EList<Object> getReferenceValueList(EObject eObject, EAttribute attribute) {
		getValueList(eObject, attribute) as EList<Object>
	}

	def static List<EChange> createAdditiveEChangeForReferencedObject(EObject referencingEObject, EReference reference,
		boolean forceCreate) {
		val result = new ArrayList<EChange>
		if (reference.many) {
			referencingEObject.getReferenceValueList(reference).forEach [
				result +=
					createInsertReferenceChange(referencingEObject, reference,
						(referencingEObject.eGet(reference) as EList<?>).indexOf(it), it, false)
			]
		} else {
			result +=
				createReplaceSingleValuedReferenceChange(referencingEObject, reference, null,
					referencingEObject.getReferenceValueList(reference).get(0), false)
		}
		result
	}

	def static boolean isRootAfterChange(EObject eObject, EObject newContainer) {
		isRootContainer(newContainer)
	}

	def static boolean wasRootBeforeChange(EObject eObject) {
		val oldContainer = eObject.eContainer
		isRootContainer(oldContainer)
	}

	def static private boolean isRootContainer(EObject container) {
		container === null // || container instanceof ChangeDescription
	}

	def static boolean hasNonDefaultValue(EObject eObject) {
		var hasNonDefaultValue = false
		for (feature : eObject.eClass.EAllStructuralFeatures) {
			if (isChangeableUnderivedPersistedNotContainingFeature(eObject, feature)) {
				hasNonDefaultValue = hasNonDefaultValue || valueIsNonDefault(eObject, feature)
			}
		}
		hasNonDefaultValue
	}

	def private static boolean isChangeableUnderivedPersistedNotContainingFeature(EObject eObject,
		EStructuralFeature feature) {
		// Ensure that its not the containing feature by checking if the value equals the container value.
		// Checking if the feature is the eContainingFeature is not correct because the eObject can be contained
		// in a reference that it declares itself (e.g. a package contained in a packagedElements reference can also 
		// have that packagedElements reference if is of the same type)
		feature.changeable && !feature.derived && !feature.transient && eObject.eContainer != eObject.eGet(feature)
	}

	def static private boolean valueIsNonDefault(EObject eObject, EStructuralFeature feature) {
		val value = eObject.eGet(feature)
		if (feature.many) {
			val list = value as List<?>
			list !== null && !list.empty
		} else {
			// TODO MK is equals (== in Xtend) for feature default value comparison okay or is identity (===) needed?
			value != feature.defaultValue
		}
	}

	def static boolean hasChangeableUnderivedPersistedNotContainingNonDefaultValue(EObject eObject,
		EStructuralFeature feature) {
		isChangeableUnderivedPersistedNotContainingFeature(eObject, feature) && valueIsNonDefault(eObject, feature)
	}

	def static EChange createInsertRootChange(EObject rootToInsert, EObject oldRootContainer, Resource oldRootResource,
		Resource newResource, int index) {
		if (isCreate(oldRootContainer, oldRootResource))
			TypeInferringCompoundEChangeFactory::instance.createCreateAndInsertRootChange(rootToInsert, newResource,
				index) as EChange
		else
			TypeInferringAtomicEChangeFactory::instance.createInsertRootChange(rootToInsert, newResource, index)
	}

	def static boolean isCreate(EObject oldContainer, Resource oldResource) {
		(oldContainer === null || oldContainer instanceof ChangeDescription) && oldResource === null
	}

	def static boolean isDelete(EObject newContainer, Resource newResource) {
		(newContainer === null || newContainer instanceof ChangeDescription) && newResource === null
	}

	def static EChange createRemoveRootChange(EObject rootToRemove, EObject newRootContainer, Resource newRootResource,
		Resource oldResource, int index) {
		if (isDelete(newRootContainer, newRootResource))
			TypeInferringCompoundEChangeFactory::instance.createRemoveAndDeleteRootChange(rootToRemove, oldResource,
				index) as EChange
		else
			TypeInferringAtomicEChangeFactory::instance.createRemoveRootChange(rootToRemove, oldResource, index)
	}

	def static EChange createInsertReferenceChange(EObject affectedEObject, EReference affectedReference, int index,
		EObject referenceValue, boolean forceCreate) {
		val isContainment = affectedReference.containment

		val oldResource = referenceValue.eResource
		val isCreate = forceCreate || (isContainment && oldResource === null)
		if (isCreate)
			TypeInferringCompoundEChangeFactory::instance.createCreateAndInsertNonRootChange(affectedEObject,
				affectedReference, referenceValue, index) as EChange
		else
			TypeInferringAtomicEChangeFactory::instance.createInsertReferenceChange(affectedEObject, affectedReference,
				referenceValue, index)
	}

	def static EChange createRemoveReferenceChange(EObject affectedEObject, EReference affectedReference, int index,
		EObject referenceValue, EObject newContainer, Resource newResource) {
		val isContainment = affectedReference.containment

		val isDelete = isContainment && isDelete(newContainer, newResource)
		if (isDelete)
			TypeInferringCompoundEChangeFactory::instance.createRemoveAndDeleteNonRootChange(affectedEObject,
				affectedReference, referenceValue, index) as EChange
		else
			TypeInferringAtomicEChangeFactory::instance.createRemoveReferenceChange(affectedEObject, affectedReference,
				referenceValue, index)
	}

	def static EChange createReplaceSingleValuedReferenceChange(EObject affectedEObject, EReference affectedReference,
		EObject oldReferenceValue, EObject newReferenceValue, boolean forceCreate) {
		val isContainment = affectedReference.containment

		if (forceCreate || isContainment) {
			if (oldReferenceValue === null) {
				TypeInferringCompoundEChangeFactory::instance.createCreateAndReplaceNonRootChange(affectedEObject,
					affectedReference, newReferenceValue) as EChange
			} else if (newReferenceValue === null)
				TypeInferringCompoundEChangeFactory::instance.createReplaceAndDeleteNonRootChange(affectedEObject,
					affectedReference, oldReferenceValue) as EChange
			else
				TypeInferringCompoundEChangeFactory::instance.
					createCreateAndReplaceAndDeleteNonRootChange(affectedEObject, affectedReference, oldReferenceValue,
						newReferenceValue) as EChange
		} else {
			TypeInferringAtomicEChangeFactory::instance.createReplaceSingleReferenceChange(affectedEObject,
				affectedReference, oldReferenceValue, newReferenceValue)
		}
	}

	def static EChange createInsertAttributeChange(EObject affectedEObject, EAttribute affectedAttribute, int index,
		Object newValue) {
		TypeInferringAtomicEChangeFactory::instance.createInsertAttributeChange(affectedEObject, affectedAttribute,
			index, newValue)
	}

	def static EChange createRemoveAttributeChange(EObject affectedEObject, EAttribute affectedAttribute, int index,
		Object oldValue) {
		TypeInferringAtomicEChangeFactory::instance.createRemoveAttributeChange(affectedEObject, affectedAttribute,
			index, oldValue)
	}

	def static ReplaceSingleValuedEAttribute<EObject, Object> createReplaceSingleValuedAttributeChange(
		EObject affectedEObject, EAttribute affectedAttribute, Object oldValue, Object newValue) {
		TypeInferringAtomicEChangeFactory::instance.createReplaceSingleAttributeChange(affectedEObject,
			affectedAttribute, oldValue, newValue)
	}

	def static createExplicitUnsetEAttributeChange(EObject affectedEObject, EAttribute affectedAttribute,
		List<SubtractiveAttributeEChange<EObject, Object>> changes) {
		TypeInferringCompoundEChangeFactory::instance.createExplicitUnsetEAttributeChange(affectedEObject,
			affectedAttribute, changes)
	}

	def static EChange createExplicitUnsetEReferenceChange(EObject affectedEObject, EReference affectedReference,
		List<EChange> changes) {
		TypeInferringCompoundEChangeFactory::instance.createExplicitUnsetEReferenceChange(affectedEObject,
			affectedReference, changes) as EChange
	}

}
