package tools.vitruv.framework.change.echange

import tools.vitruv.framework.change.echange.feature.attribute.AttributeFactory
import tools.vitruv.framework.change.echange.feature.attribute.InsertEAttributeValue
import tools.vitruv.framework.change.echange.feature.attribute.PermuteEAttributeValues
import tools.vitruv.framework.change.echange.feature.attribute.RemoveEAttributeValue
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference
import tools.vitruv.framework.change.echange.feature.reference.PermuteEReferences
import tools.vitruv.framework.change.echange.feature.reference.ReferenceFactory
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference
import tools.vitruv.framework.change.echange.root.InsertRootEObject
import tools.vitruv.framework.change.echange.root.RemoveRootEObject
import tools.vitruv.framework.change.echange.root.RootFactory
import java.util.List
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature

import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.EObjectUtil.*
import tools.vitruv.framework.change.echange.root.RootEChange
import tools.vitruv.framework.change.echange.feature.attribute.AdditiveAttributeEChange
import tools.vitruv.framework.change.echange.feature.FeatureEChange

/**
 * Factory class for elements of change models. 
 * Infers types (i.e. metaclasses and feature types) from parameters where possible.<br/>
 * 
 * Can be used by any transformation that creates change models.
 */
final class TypeInferringAtomicEChangeFactory {
	
	def static <T extends EObject> InsertRootEObject<T> createInsertRootChange(T newValue, boolean isCreate, String resourceURI) {
		val c = RootFactory.eINSTANCE.createInsertRootEObject
		c.newValue = newValue
		c.isCreate = isCreate
		setRootChangeFeatures(c, resourceURI)
		return c
	}
	
	def static <T extends EObject> RemoveRootEObject<T> createRemoveRootChange(T oldValue, boolean isDelete, String resourceURI) {
		val c = RootFactory.eINSTANCE.createRemoveRootEObject
		c.oldValue = oldValue
		c.isDelete = isDelete
		setRootChangeFeatures(c, resourceURI)
		return c
	}
	
	def private static setRootChangeFeatures(RootEChange c, String resourceURI) {
		c.uri = resourceURI
	}
	
	def static <T extends EObject> RemoveRootEObject<T> createRemoveRootChange(T oldObject, boolean isDelete) {
		val c = RootFactory.eINSTANCE.createRemoveRootEObject
		tools.vitruv.framework.change.echange.TypeInferringAtomicEChangeFactory.setEObjectSubtractedEChangeFeatures(c,oldObject,isDelete)
		return c
	}
	
	def private  static <T extends EObject> void setEObjectSubtractedEChangeFeatures(EObjectSubtractedEChange<T> c, T oldEObject, boolean isDelete) {
		c.isDelete = isDelete
		c.oldValue = oldEObject
	}
	
	def static <A extends EObject> AdditiveAttributeEChange<?, Object> createAdditiveAttributeChange(A affectedEObject, EAttribute affectedAttribute) {
		if (affectedAttribute.many) {
			val newValue = affectedEObject.getFeatureValues(affectedAttribute)
			val index = 0 // FIXME MK calculate index!
			return createInsertAttributeChange(affectedEObject, affectedAttribute, index, newValue)
		} else {
			val oldValue = affectedAttribute.defaultValue
			val newValue = affectedEObject.getFeatureValue(affectedAttribute)
			return createReplaceSingleAttributeChange(affectedEObject, affectedAttribute, oldValue, newValue)
		}
	}

	def static <A extends EObject, T extends Object> InsertEAttributeValue<A,T> createInsertAttributeChange(A affectedEObject, EAttribute affectedAttribute, int index, T newValue) {
		val c = AttributeFactory.eINSTANCE.createInsertEAttributeValue()
		setFeatureChangeFeatures(c,affectedEObject,affectedAttribute)
		c.newValue = newValue
		c.index = index
		return c
	}
	
	private def static <A extends EObject, F extends EStructuralFeature> void setFeatureChangeFeatures(FeatureEChange<A,F> c, A affectedEObject, F affectedFeature) {
		c.affectedEObject = affectedEObject
		c.affectedFeature = affectedFeature
	}

	def static <A extends EObject, T extends Object> ReplaceSingleValuedEAttribute<A,T> createReplaceSingleAttributeChange(A affectedEObject, EAttribute affectedAttribute, T oldValue, T newValue) {
		val c = AttributeFactory.eINSTANCE.createReplaceSingleValuedEAttribute
		setFeatureChangeFeatures(c,affectedEObject,affectedAttribute)
		c.oldValue = oldValue
		c.newValue = newValue
		return c
	}
	
	def static <A extends EObject, T extends Object> RemoveEAttributeValue<A,T> createRemoveAttributeChange(A affectedEObject, EAttribute affectedAttribute, int index, T oldValue) {
		val c = AttributeFactory.eINSTANCE.createRemoveEAttributeValue()
		setFeatureChangeFeatures(c,affectedEObject,affectedAttribute)
		c.oldValue = oldValue
		c.index = index
		return c
	}
	
	def static <A extends EObject> PermuteEAttributeValues<A> createPermuteAttributesChange(A affectedEObject, EAttribute affectedAttribute, List<Integer> newIndicesForElementsAtOldIndices) {
		val c = AttributeFactory.eINSTANCE.createPermuteEAttributeValues()
		setFeatureChangeFeatures(c,affectedEObject,affectedAttribute)
		c.newIndicesForElementsAtOldIndices.addAll(newIndicesForElementsAtOldIndices)
		return c
	}
	
	def static <A extends EObject, T extends EObject> InsertEReference<A,T> createInsertReferenceChange(A affectedEObject, EReference affectedReference, int index, T newValue, boolean isCreate) {
		val c = ReferenceFactory.eINSTANCE.createInsertEReference()
		setFeatureChangeFeatures(c,affectedEObject,affectedReference)
		c.newValue = newValue
		c.index = index
		c.isCreate = isCreate
		return c
	}
	
	def static <A extends EObject, T extends EObject> ReplaceSingleValuedEReference<A,T> createReplaceSingleReferenceChange(A affectedEObject, EReference affectedReference, T oldEObject, T newValue, boolean isCreate, boolean isDelete) {
		val c = ReferenceFactory.eINSTANCE.createReplaceSingleValuedEReference
		setFeatureChangeFeatures(c,affectedEObject,affectedReference)
		tools.vitruv.framework.change.echange.TypeInferringAtomicEChangeFactory.setEObjectSubtractedEChangeFeatures(c,oldEObject,isDelete)
		c.newValue = newValue
		c.isCreate = isCreate
		return c
	}
	
	def static <A extends EObject, T extends EObject> RemoveEReference<A,T> createRemoveReferenceChange(A affectedEObject, EReference affectedReference, T oldEObject, int index, boolean isDelete) {
		val c = ReferenceFactory.eINSTANCE.createRemoveEReference()
		setFeatureChangeFeatures(c,affectedEObject,affectedReference)
		tools.vitruv.framework.change.echange.TypeInferringAtomicEChangeFactory.setEObjectSubtractedEChangeFeatures(c,oldEObject,isDelete)
		c.oldValue = oldEObject
		c.index = index
		c.isDelete = isDelete
		return c
	}
	
	def static <A extends EObject> PermuteEReferences<A> createPermuteReferencesChange(A affectedEObject, EReference affectedReference, List<Integer> newIndicesForElementsAtOldIndices) {
		val c = ReferenceFactory.eINSTANCE.createPermuteEReferences()
		setFeatureChangeFeatures(c,affectedEObject,affectedReference)
		c.newIndicesForElementsAtOldIndices.addAll(newIndicesForElementsAtOldIndices)
		return c
	}
}