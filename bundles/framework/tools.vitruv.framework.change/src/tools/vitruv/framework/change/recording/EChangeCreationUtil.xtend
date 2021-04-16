package tools.vitruv.framework.change.recording

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

import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.EObjectUtil.*
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import edu.kit.ipd.sdq.activextendannotations.Utility

/**
 * A utility class providing extension methods for transforming change descriptions to change models.
 * 
 */
@Utility
package class EChangeCreationUtil {
	def static <A extends EObject> List<AdditiveAttributeEChange<?, Object>> createAdditiveEChangeForAttribute(
		A affectedEObject, EAttribute affectedAttribute) {
		if (affectedAttribute.many) {
			affectedEObject.getFeatureValues(affectedAttribute).mapFixedIndexed [ index, value |
				TypeInferringAtomicEChangeFactory.instance.createInsertAttributeChange(affectedEObject,
					affectedAttribute, index, value)
			]
		} else {
			val oldValue = affectedAttribute.defaultValue
			val newValue = affectedEObject.getFeatureValue(affectedAttribute)

			return List.of(
				TypeInferringAtomicEChangeFactory.instance.createReplaceSingleAttributeChange(affectedEObject,
					affectedAttribute, oldValue, newValue))
		}
	}

	def static EList<EObject> getReferenceValueList(EObject eObject, EReference reference) {
		return getValueList(eObject, reference) as EList<EObject>
	}

	def static List<EChange> createAdditiveEChangeForReferencedObject(EObject referencingEObject, EReference reference,
		(EObject)=>boolean isCreate) {
		return if (reference.isMany) {
			referencingEObject.getReferenceValueList(reference).flatMapFixed [ referenceValue |
				createInsertReferenceChange(referencingEObject, reference,
					(referencingEObject.eGet(reference) as EList<?>).indexOf(referenceValue), referenceValue,
					isCreate.apply(referenceValue))
			]
		} else {
			val referenceValue = referencingEObject.getReferenceValueList(reference).get(0)
			createReplaceSingleValuedReferenceChange(referencingEObject, reference, null,
				referenceValue, isCreate.apply(referenceValue))
		}
	}

	def static private boolean isChangeableUnderivedPersistedNotContainingFeature(EObject eObject,
		EStructuralFeature feature) {
		// Ensure that its not the containing feature by checking if the value equals the container value.
		// Checking if the feature is the eContainingFeature is not correct because the eObject can be contained
		// in a reference that it declares itself (e.g. a package contained in a packagedElements reference can also 
		// have that packagedElements reference if is of the same type)
		return feature.isChangeable() && !feature.isDerived() && !feature.isTransient() &&
			eObject.eContainer != eObject.eGet(feature)
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

	def static boolean hasChangeableUnderivedPersistedNotContainingNonDefaultValue(EObject eObject,
		EStructuralFeature feature) {
		return isChangeableUnderivedPersistedNotContainingFeature(eObject, feature) &&
			valueIsNonDefault(eObject, feature)
	}

	def static boolean isDelete(EObject newContainer, Resource newResource) {
		return (newContainer === null || newContainer instanceof ChangeDescription) && newResource === null
	}

	def static List<EChange> createInsertReferenceChange(EObject affectedEObject, EReference affectedReference,
		int index, EObject referenceValue, boolean forceCreate) {
		val isContainment = affectedReference.containment

		val oldResource = referenceValue.eResource

		val isCreate = forceCreate || (isContainment && oldResource === null)
		if (isCreate) {
			return TypeInferringCompoundEChangeFactory.instance.createCreateAndInsertNonRootChange(affectedEObject,
				affectedReference, referenceValue, index);
		} else {
			return List.of(
				TypeInferringAtomicEChangeFactory.instance.createInsertReferenceChange(affectedEObject,
					affectedReference, referenceValue, index))
		}
	}

	def static List<EChange> createRemoveReferenceChange(EObject affectedEObject, EReference affectedReference,
		int index, EObject referenceValue, EObject newContainer, Resource newResource, boolean forceDelete) {
		val isContainment = affectedReference.containment

		val isDelete = forceDelete || (isContainment && isDelete(newContainer, newResource))
		return if (isDelete) {
			TypeInferringCompoundEChangeFactory.instance.createRemoveAndDeleteNonRootChange(affectedEObject,
				affectedReference, referenceValue, index);
		} else {
			List.of(
				TypeInferringAtomicEChangeFactory.instance.createRemoveReferenceChange(affectedEObject,
					affectedReference, referenceValue, index))
		}
	}

	def static List<EChange> createReplaceSingleValuedReferenceChange(EObject affectedEObject,
		EReference affectedReference, EObject oldReferenceValue, EObject newReferenceValue, boolean forceCreate) {
		val isContainment = affectedReference.containment

		return if (forceCreate || isContainment) {
			if (oldReferenceValue === null) {
				TypeInferringCompoundEChangeFactory.instance.createCreateAndReplaceNonRootChange(affectedEObject,
					affectedReference, newReferenceValue)
			} else if (newReferenceValue === null) {
				TypeInferringCompoundEChangeFactory.instance.createReplaceAndDeleteNonRootChange(affectedEObject,
					affectedReference, oldReferenceValue)
			} else {
				TypeInferringCompoundEChangeFactory.instance.
					createCreateAndReplaceAndDeleteNonRootChange(affectedEObject, affectedReference, oldReferenceValue,
						newReferenceValue);
			}
		} else {
			List.of(
				TypeInferringAtomicEChangeFactory.instance.createReplaceSingleReferenceChange(affectedEObject,
					affectedReference, oldReferenceValue, newReferenceValue))
		}
	}
}
