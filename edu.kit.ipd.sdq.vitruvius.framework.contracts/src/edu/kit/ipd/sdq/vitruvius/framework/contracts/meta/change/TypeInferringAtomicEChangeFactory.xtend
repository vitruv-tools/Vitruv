package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Metamodel
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.EFeatureChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.AttributeFactory
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.InsertEAttributeValue
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.PermuteEAttributeValues
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.RemoveEAttributeValue
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.ReplaceSingleValuedEAttribute
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.InsertEReference
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.PermuteEReferences
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.ReferenceFactory
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.RemoveEReference
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.ReplaceSingleValuedEReference
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.InsertRootEObject
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.RemoveRootEObject
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.RootFactory
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import java.util.List

final class TypeInferringAtomicEChangeFactory {
	val Metamodel metamodel
	
	new(Metamodel metamodel) {
		this.metamodel = metamodel
	}
	
	def <T extends EObject> InsertRootEObject<T> createInsertRootChange(T newValue, boolean isCreate) {
		val c = RootFactory.eINSTANCE.createInsertRootEObject
		c.newValue = newValue
		c.isCreate = isCreate
		return c
	}
	
	def <T extends EObject> RemoveRootEObject<T> createRemoveRootChange(T oldObject, boolean isDelete) {
		val c = RootFactory.eINSTANCE.createRemoveRootEObject
		setSubtractiveEReferenceChangeFeatures(c,oldObject,isDelete)
		return c
	}
	
	private def <T extends EObject> void setSubtractiveEReferenceChangeFeatures(SubtractiveEReferenceChange<T> c, T oldEObject, boolean isDelete) {
//		val oldTUID = getTUID(oldEObject)
		// FIXME MK add oldValue magic for subtractive reference changes
//		c.oldTUID = oldTUID
//		val feature2OldValueMap = new HashMap<EStructuralFeature,Object>();
//		for (feature : oldEObject.eClass.EAllStructuralFeatures) {
//			val oldValue = getOldValueForMap(oldEObject, feature)
//			feature2OldValueMap.put(feature,oldValue)
//		}
		c.isDelete = isDelete
	}
	
//	private def TUID getTUID(EObject o) {
//		return TUID::getInstance(metamodel.calculateTUIDFromEObject(o))
//	}
//	
//	private def dispatch Object getOldValueForMap(EObject o, EAttribute attribute) {
//		return o.eGet(attribute)
//	}
//	
//	private def dispatch Object getOldValueForMap(EObject o, EReference reference) {
//		val referencedEObject = o.eGet(reference) as EObject
//		return getTUID(referencedEObject)
//	}
	
//	def dispatch InsertInEList createInsert(EObject affectedEObject, TUID oldTUIDOfAffectedEObject, EAttribute affectedAttribute, Object newValue, int index) {
//		return createInsertAttributeChange(affectedEObject,oldTUIDOfAffectedEObject,affectedAttribute,newValue,index)
//	}
//	
//	def dispatch InsertInEList createInsert(EObject affectedEObject, TUID oldTUIDOfAffectedEObject, EReference affectedReference, EObject newValue, int index) {
//		return createInsertReferenceChange(affectedEObject,oldTUIDOfAffectedEObject,affectedReference,newValue,index)
//	}
	
	def <A extends EObject, T extends Object> InsertEAttributeValue<A,T> createInsertAttributeChange(A affectedEObject, EAttribute affectedAttribute, T newValue, int index) {
		val c = AttributeFactory.eINSTANCE.createInsertEAttributeValue()
		setFeatureChangeFeatures(c,affectedEObject,affectedAttribute)
		c.newValue = newValue
		c.index = index
		return c
	}
	
	private def <A extends EObject, F extends EStructuralFeature> void setFeatureChangeFeatures(EFeatureChange<A,F> c, A affectedEObject, F affectedFeature) {
		c.affectedEObject = affectedEObject
		c.affectedFeature = affectedFeature
	}
	
//	def dispatch UpdateSingleValuedEFeature createReplaceSingle(EObject affectedEObject, TUID oldTUIDOfAffectedEObject, EAttribute affectedAttribute, Object oldValue, Object newValue) {
//		return createReplaceSingleAttributeChange(affectedEObject, oldTUIDOfAffectedEObject, affectedAttribute, oldValue, newValue)
//	}
//	
//	def dispatch UpdateSingleValuedEFeature createReplaceSingle(EObject affectedEObject, TUID oldTUIDOfAffectedEObject, EReference affectedReference, EObject oldValue, EObject newValue) {
//		return createReplaceSingleReferenceChange(affectedEObject,oldTUIDOfAffectedEObject,affectedReference,oldEO
//	}
	
	def <A extends EObject, T extends Object> ReplaceSingleValuedEAttribute<A,T> createReplaceSingleAttributeChange(A affectedEObject, EAttribute affectedAttribute, T oldValue, T newValue) {
		val c = AttributeFactory.eINSTANCE.createReplaceSingleValuedEAttribute
		setFeatureChangeFeatures(c,affectedEObject,affectedAttribute)
		c.oldValue = oldValue
		c.newValue = newValue
		return c
	}
	
	def <A extends EObject, T extends Object> RemoveEAttributeValue<A,T> createRemoveAttributeChange(A affectedEObject, EAttribute affectedAttribute, T oldValue, int index) {
		val c = AttributeFactory.eINSTANCE.createRemoveEAttributeValue()
		setFeatureChangeFeatures(c,affectedEObject,affectedAttribute)
		c.oldValue = oldValue
		c.index = index
		return c
	}
	
	def <A extends EObject> PermuteEAttributeValues<A> createPermuteAttributesChange(A affectedEObject, EAttribute affectedAttribute, List<Integer> newIndicesForElementsAtOldIndices) {
		val c = AttributeFactory.eINSTANCE.createPermuteEAttributeValues()
		setFeatureChangeFeatures(c,affectedEObject,affectedAttribute)
		c.newIndicesForElementsAtOldIndices.addAll(newIndicesForElementsAtOldIndices)
		return c
	}
	
	def <A extends EObject, T extends EObject> InsertEReference<A,T> createInsertReferenceChange(A affectedEObject, EReference affectedReference, T newValue, int index) {
		val c = ReferenceFactory.eINSTANCE.createInsertEReference()
		setFeatureChangeFeatures(c,affectedEObject,affectedReference)
		c.newValue = newValue
		c.index = index
		return c
	}
	
	def <A extends EObject, T extends EObject> ReplaceSingleValuedEReference<A,T> createReplaceSingleReferenceChange(A affectedEObject, EReference affectedReference, T oldEObject, T newValue, boolean isCreate, boolean isDelete) {
		val c = ReferenceFactory.eINSTANCE.createReplaceSingleValuedEReference
		setFeatureChangeFeatures(c,affectedEObject,affectedReference)
		setSubtractiveEReferenceChangeFeatures(c,oldEObject,isDelete)
		c.newValue = newValue
		c.isCreate = isCreate
		return c
	}
	
	def <A extends EObject, T extends EObject> RemoveEReference<A,T> createRemoveReferenceChange(A affectedEObject, EReference affectedReference, T oldEObject, int index, boolean isDelete) {
		val c = ReferenceFactory.eINSTANCE.createRemoveEReference()
		setFeatureChangeFeatures(c,affectedEObject,affectedReference)
		setSubtractiveEReferenceChangeFeatures(c,oldEObject,isDelete)
		c.index = index
		return c
	}
	
	def <A extends EObject> PermuteEReferences<A> createPermuteReferencesChange(A affectedEObject, EReference affectedReference, List<Integer> newIndicesForElementsAtOldIndices) {
		val c = ReferenceFactory.eINSTANCE.createPermuteEReferences()
		setFeatureChangeFeatures(c,affectedEObject,affectedReference)
		c.newIndicesForElementsAtOldIndices.addAll(newIndicesForElementsAtOldIndices)
		return c
	}
}