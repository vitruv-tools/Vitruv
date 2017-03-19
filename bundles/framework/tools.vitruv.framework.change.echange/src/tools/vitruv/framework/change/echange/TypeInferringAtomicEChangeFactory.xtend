package tools.vitruv.framework.change.echange

import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import tools.vitruv.framework.change.echange.eobject.CreateEObject
import tools.vitruv.framework.change.echange.eobject.DeleteEObject
import tools.vitruv.framework.change.echange.eobject.EObjectAddedEChange
import tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange
import tools.vitruv.framework.change.echange.eobject.EObjectSubtractedEChange
import tools.vitruv.framework.change.echange.eobject.EobjectFactory
import tools.vitruv.framework.change.echange.feature.FeatureEChange
import tools.vitruv.framework.change.echange.feature.attribute.AttributeFactory
import tools.vitruv.framework.change.echange.feature.attribute.InsertEAttributeValue
import tools.vitruv.framework.change.echange.feature.attribute.RemoveEAttributeValue
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference
import tools.vitruv.framework.change.echange.feature.reference.ReferenceFactory
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference
import tools.vitruv.framework.change.echange.root.InsertRootEObject
import tools.vitruv.framework.change.echange.root.RemoveRootEObject
import tools.vitruv.framework.change.echange.root.RootEChange
import tools.vitruv.framework.change.echange.root.RootFactory

/**
 * Factory class for elements of change models. 
 * Infers types (i.e. metaclasses and feature types) from parameters where possible.<br/>
 * 
 * Can be used by any transformation that creates change models.
 */
class TypeInferringAtomicEChangeFactory {
	def private setRootChangeFeatures(RootEChange c, String resourceURI, int index) {
		c.uri = resourceURI
		c.index = index
	}
	
	def protected <A extends EObject, F extends EStructuralFeature> void setFeatureChangeFeatures(FeatureEChange<A,F> c, A affectedEObject, F affectedFeature) {
		c.affectedEObject = affectedEObject	
		c.affectedFeature = affectedFeature
	}

	def protected <T extends EObject> void setNewValue(EObjectAddedEChange<T> c, T newReferenceValue) {
		c.newValue = newReferenceValue
	}	
	
	def protected <T extends EObject> void setOldValue(EObjectSubtractedEChange<T> c, T oldReferenceValue) {
		c.oldValue = oldReferenceValue
	}	
	
	def protected <A extends EObject> void setEObjectExistenceChange(EObjectExistenceEChange<A> c, A affectedEObject) {
		c.affectedEObject = affectedEObject;
	}	
	
	def <T extends EObject> InsertRootEObject<T> createInsertRootChange(T newValue, String resourceURI, int index) {
		val c = RootFactory.eINSTANCE.createInsertRootEObject
		setNewValue(c, newValue)
		setRootChangeFeatures(c, resourceURI, index)
		return c
	}
	
	def <T extends EObject> RemoveRootEObject<T> createRemoveRootChange(T oldValue, String resourceURI, int index) {
		val c = RootFactory.eINSTANCE.createRemoveRootEObject
		setOldValue(c, oldValue)
		setRootChangeFeatures(c, resourceURI, index)
		return c
	}

	def <A extends EObject, T extends Object> InsertEAttributeValue<A,T> createInsertAttributeChange(A affectedEObject, EAttribute affectedAttribute, int index, T newValue) {
		val c = AttributeFactory.eINSTANCE.createInsertEAttributeValue()
		setFeatureChangeFeatures(c, affectedEObject, affectedAttribute)
		c.newValue = newValue
		c.index = index
		return c
	}
	
	def <A extends EObject, T extends Object> ReplaceSingleValuedEAttribute<A,T> createReplaceSingleAttributeChange(A affectedEObject, EAttribute affectedAttribute, T oldValue, T newValue) {
		val c = AttributeFactory.eINSTANCE.createReplaceSingleValuedEAttribute
		setFeatureChangeFeatures(c, affectedEObject, affectedAttribute)
		c.oldValue = oldValue
		c.newValue = newValue
		return c
	}
	
	def <A extends EObject, T extends Object> RemoveEAttributeValue<A,T> createRemoveAttributeChange(A affectedEObject, EAttribute affectedAttribute, int index, T oldValue) {
		val c = AttributeFactory.eINSTANCE.createRemoveEAttributeValue()
		setFeatureChangeFeatures(c, affectedEObject, affectedAttribute)
		c.oldValue = oldValue
		c.index = index
		return c
	}
	
	def <A extends EObject, T extends EObject> InsertEReference<A,T> createInsertReferenceChange(A affectedEObject, EReference affectedReference, T newValue, int index) {
		val c = ReferenceFactory.eINSTANCE.createInsertEReference()
		setFeatureChangeFeatures(c, affectedEObject, affectedReference)
		setNewValue(c, newValue)
		c.index = index
		return c
	}
	
	def <A extends EObject, T extends EObject> ReplaceSingleValuedEReference<A,T> createReplaceSingleReferenceChange(A affectedEObject, EReference affectedReference, T oldValue, T newValue) {
		val c = ReferenceFactory.eINSTANCE.createReplaceSingleValuedEReference
		setFeatureChangeFeatures(c, affectedEObject, affectedReference)
		setOldValue(c, oldValue)
		setNewValue(c, newValue)
		return c
	}
	
	def <A extends EObject, T extends EObject> RemoveEReference<A,T> createRemoveReferenceChange(A affectedEObject, EReference affectedReference, T oldValue, int index) {
		val c = ReferenceFactory.eINSTANCE.createRemoveEReference()
		setFeatureChangeFeatures(c, affectedEObject, affectedReference)
		setOldValue(c, oldValue)
		c.index = index
		return c
	}
	
	def <A extends EObject> CreateEObject<A> createCreateEObjectChange(A affectedEObject) {
		val c = EobjectFactory.eINSTANCE.createCreateEObject()
		setEObjectExistenceChange(c, affectedEObject)
		return c
	}
	
	def <A extends EObject> DeleteEObject<A> createDeleteEObjectChange(A affectedEObject) {
		val c = EobjectFactory.eINSTANCE.createDeleteEObject()
		setEObjectExistenceChange(c, affectedEObject)
		return c
	}
	
}