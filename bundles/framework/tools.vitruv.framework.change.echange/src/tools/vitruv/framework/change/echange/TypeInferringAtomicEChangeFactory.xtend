package tools.vitruv.framework.change.echange

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
import tools.vitruv.framework.change.echange.root.RootFactory
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature

import tools.vitruv.framework.change.echange.root.RootEChange
import tools.vitruv.framework.change.echange.feature.FeatureEChange
import tools.vitruv.framework.change.echange.eobject.CreateEObject
import tools.vitruv.framework.change.echange.eobject.EobjectFactory
import tools.vitruv.framework.change.echange.eobject.DeleteEObject

/**
 * Factory class for elements of change models. 
 * Infers types (i.e. metaclasses and feature types) from parameters where possible.<br/>
 * 
 * Can be used by any transformation that creates change models.
 */
final class TypeInferringAtomicEChangeFactory {
	def private static setRootChangeFeatures(RootEChange c, String resourceURI) {
		c.uri = resourceURI
	}
	
	private def static <A extends EObject, F extends EStructuralFeature> void setFeatureChangeFeatures(FeatureEChange<A,F> c, A affectedEObject, F affectedFeature) {
		c.affectedEObject = affectedEObject
		c.affectedFeature = affectedFeature
	}

	def static <T extends EObject> InsertRootEObject<T> createInsertRootChange(T newValue, String resourceURI) {
		val c = RootFactory.eINSTANCE.createInsertRootEObject
		c.newValue = newValue
		setRootChangeFeatures(c, resourceURI)
		return c
	}
	
	def static <T extends EObject> RemoveRootEObject<T> createRemoveRootChange(T oldValue, String resourceURI) {
		val c = RootFactory.eINSTANCE.createRemoveRootEObject
		c.oldValue = oldValue
		setRootChangeFeatures(c, resourceURI)
		return c
	}

	def static <A extends EObject, T extends Object> InsertEAttributeValue<A,T> createInsertAttributeChange(A affectedEObject, EAttribute affectedAttribute, int index, T newValue) {
		val c = AttributeFactory.eINSTANCE.createInsertEAttributeValue()
		setFeatureChangeFeatures(c,affectedEObject,affectedAttribute)
		c.newValue = newValue
		c.index = index
		return c
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
	
	def static <A extends EObject, T extends EObject> InsertEReference<A,T> createInsertReferenceChange(A affectedEObject, EReference affectedReference, T newValue, int index) {
		val c = ReferenceFactory.eINSTANCE.createInsertEReference()
		setFeatureChangeFeatures(c,affectedEObject,affectedReference)
		c.newValue = newValue
		c.index = index
		return c
	}
	
	def static <A extends EObject, T extends EObject> ReplaceSingleValuedEReference<A,T> createReplaceSingleReferenceChange(A affectedEObject, EReference affectedReference, T oldValue, T newValue) {
		val c = ReferenceFactory.eINSTANCE.createReplaceSingleValuedEReference
		setFeatureChangeFeatures(c,affectedEObject,affectedReference)
		c.oldValue = oldValue
		c.newValue = newValue
		return c
	}
	
	def static <A extends EObject, T extends EObject> RemoveEReference<A,T> createRemoveReferenceChange(A affectedEObject, EReference affectedReference, T oldValue, int index) {
		val c = ReferenceFactory.eINSTANCE.createRemoveEReference()
		setFeatureChangeFeatures(c,affectedEObject,affectedReference)
		c.oldValue = oldValue
		c.index = index
		return c
	}
	
	def static <A extends EObject> CreateEObject<A> createCreateEObjectChange(A affectedEObject) {
		val c = EobjectFactory.eINSTANCE.createCreateEObject()
		c.affectedEObject = affectedEObject
		return c
	}
	
	def static <A extends EObject> DeleteEObject<A> createDeleteEObjectChange(A affectedEObject) {
		val c = EobjectFactory.eINSTANCE.createDeleteEObject()
		c.affectedEObject = affectedEObject
		return c
	}
	
}