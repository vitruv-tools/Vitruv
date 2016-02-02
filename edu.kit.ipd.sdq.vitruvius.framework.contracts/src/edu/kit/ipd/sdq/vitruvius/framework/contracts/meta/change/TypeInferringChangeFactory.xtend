package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.InsertRootEObject
import org.eclipse.emf.ecore.EObject
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.RootFactory
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.RemoveRootEObject
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TUID
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Metamodel
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.EAttribute
import java.util.HashMap
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.InsertEAttributeValue
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.AttributeFactory
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.ReplaceSingleValuedEAttribute
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.EFeatureChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.RemoveEAttributeValue
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.PermuteEAttributeValues
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.InsertEReference
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.ReferenceFactory
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.ReplaceSingleValuedEReference
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.RemoveEReference
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.PermuteEReferences

final class TypeInferringChangeFactory {
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
	
	def RemoveRootEObject createRemoveRootChange(EObject oldObject, boolean isDelete) {
		val c = RootFactory.eINSTANCE.createRemoveRootEObject
		setSubtractiveEReferenceChangeFeatures(c,oldObject,isDelete)
		return c
	}
	
	private def void setSubtractiveEReferenceChangeFeatures(SubtractiveEReferenceChange c, EObject oldEObject, boolean isDelete) {
		val oldTUID = getTUID(oldEObject)
		c.oldTUID = oldTUID
		val feature2OldValueMap = new HashMap<EStructuralFeature,Object>();
		for (feature : oldEObject.eClass.EAllStructuralFeatures) {
			val oldValue = getOldValueForMap(oldEObject, feature)
			feature2OldValueMap.put(feature,oldValue)
		}
		c.isDelete = isDelete
	}
	
	private def TUID getTUID(EObject o) {
		return TUID::getInstance(metamodel.calculateTUIDFromEObject(o))
	}
	
	private def dispatch Object getOldValueForMap(EObject o, EAttribute attribute) {
		return o.eGet(attribute)
	}
	
	private def dispatch Object getOldValueForMap(EObject o, EReference reference) {
		val referencedEObject = o.eGet(reference) as EObject
		return getTUID(referencedEObject)
	}
	
	def <A extends EObject, T extends Object> InsertEAttributeValue<A,T> createInsertAttributeChange(A affectedEObject, TUID oldTUIDOfAffectedEObject, EAttribute affectedAttribute, T newValue, int index) {
		val c = AttributeFactory.eINSTANCE.createInsertEAttributeValue()
		setFeatureChangeFeatures(c,affectedEObject,oldTUIDOfAffectedEObject,affectedAttribute)
		c.newValue = newValue
		c.index = index
		return c
	}
	
	private def <A extends EObject, F extends EStructuralFeature> void setFeatureChangeFeatures(EFeatureChange<A,F> c, A affectedEObject, TUID oldTUIDOfAffectedEObject, F affectedFeature) {
		c.affectedEObject = affectedEObject
		c.oldTUIDOfAffectedEObject = oldTUIDOfAffectedEObject
		c.affectedFeature = affectedFeature
	}
	
	def <A extends EObject, T extends Object> ReplaceSingleValuedEAttribute<A,T> createReplaceSingleAttributeChange(A affectedEObject, TUID oldTUIDOfAffectedEObject, EAttribute affectedAttribute, T oldValue, T newValue) {
		val c = AttributeFactory.eINSTANCE.createReplaceSingleValuedEAttribute
		setFeatureChangeFeatures(c,affectedEObject,oldTUIDOfAffectedEObject,affectedAttribute)
		c.oldValue = oldValue
		c.newValue = newValue
		return c
	}
	
	def <A extends EObject, T extends Object> RemoveEAttributeValue<A,T> createRemoveAttributeChange(A affectedEObject, TUID oldTUIDOfAffectedEObject, EAttribute affectedAttribute, T oldValue, int index) {
		val c = AttributeFactory.eINSTANCE.createRemoveEAttributeValue()
		setFeatureChangeFeatures(c,affectedEObject,oldTUIDOfAffectedEObject,affectedAttribute)
		c.oldValue = oldValue
		c.index = index
		return c
	}
	
	def <A extends EObject> PermuteEAttributeValues<A> createPermuteAttributesChange(A affectedEObject, TUID oldTUIDOfAffectedEObject, EAttribute affectedAttribute, int oldIndex, int newIndex) {
		val c = AttributeFactory.eINSTANCE.createPermuteEAttributeValues()
		setFeatureChangeFeatures(c,affectedEObject,oldTUIDOfAffectedEObject,affectedAttribute)
		c.oldIndex = oldIndex
		c.newIndex = newIndex
		return c
	}
	
	def <A extends EObject, T extends EObject> InsertEReference<A,T> createInsertReferenceChange(A affectedEObject, TUID oldTUIDOfAffectedEObject, EReference affectedReference, T newValue, int index) {
		val c = ReferenceFactory.eINSTANCE.createInsertEReference()
		setFeatureChangeFeatures(c,affectedEObject,oldTUIDOfAffectedEObject,affectedReference)
		c.newValue = newValue
		c.index = index
		return c
	}
	
	def <A extends EObject, T extends EObject> ReplaceSingleValuedEReference<A,T> createReplaceSingleReferenceChange(A affectedEObject, TUID oldTUIDOfAffectedEObject, EReference affectedReference, EObject oldEObject, T newValue, boolean isCreate, boolean isDelete) {
		val c = ReferenceFactory.eINSTANCE.createReplaceSingleValuedEReference
		setFeatureChangeFeatures(c,affectedEObject,oldTUIDOfAffectedEObject,affectedReference)
		setSubtractiveEReferenceChangeFeatures(c,oldEObject,isDelete)
		c.newValue = newValue
		c.isCreate = isCreate
		return c
	}
	
	def <A extends EObject> RemoveEReference<A> createRemoveReferenceChange(A affectedEObject, TUID oldTUIDOfAffectedEObject, EReference affectedReference, EObject oldEObject, int index, boolean isDelete) {
		val c = ReferenceFactory.eINSTANCE.createRemoveEReference()
		setFeatureChangeFeatures(c,affectedEObject,oldTUIDOfAffectedEObject,affectedReference)
		setSubtractiveEReferenceChangeFeatures(c,oldEObject,isDelete)
		c.index = index
		return c
	}
	
	def <A extends EObject> PermuteEReferences<A> createPermuteReferencesChange(A affectedEObject, TUID oldTUIDOfAffectedEObject, EReference affectedReference, int oldIndex, int newIndex) {
		val c = ReferenceFactory.eINSTANCE.createPermuteEReferences()
		setFeatureChangeFeatures(c,affectedEObject,oldTUIDOfAffectedEObject,affectedReference)
		c.oldIndex = oldIndex
		c.newIndex = newIndex
		return c
	}
}