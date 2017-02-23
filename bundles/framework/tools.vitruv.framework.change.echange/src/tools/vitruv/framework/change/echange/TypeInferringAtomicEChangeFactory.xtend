package tools.vitruv.framework.change.echange

import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.InternalEObject
import org.eclipse.emf.ecore.util.EcoreUtil
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
final class TypeInferringAtomicEChangeFactory {
	def private static setRootChangeFeatures(RootEChange c, String resourceURI, int index) {
		c.uri = URI.createURI(resourceURI)
		c.index = index
	}
	
	private def static <A extends EObject> A createProxy(A resolvedObject) {
		val proxy = EcoreUtil.copy(resolvedObject) as InternalEObject
		proxy.eSetProxyURI(EcoreUtil.getURI(resolvedObject))
		return proxy as A
	}
	
	private def static <A extends EObject, F extends EStructuralFeature> void setFeatureChangeFeatures(FeatureEChange<A,F> c, A affectedEObject, F affectedFeature, boolean unresolve) {
		if (affectedEObject != null && unresolve) {
			c.affectedEObject = createProxy(affectedEObject)
		} else {
			c.affectedEObject = affectedEObject			
		}
		c.affectedFeature = affectedFeature
	}

	private def static <T extends EObject> void setNewValue(EObjectAddedEChange<T> c, T newReferenceValue, boolean unresolve) {
		if (newReferenceValue != null && unresolve) {
			c.newValue = createProxy(newReferenceValue)
		} else {
			c.newValue = newReferenceValue
		}
	}
	
	private def static <T extends EObject> void setOldValue(EObjectSubtractedEChange<T> c, T oldReferenceValue, boolean unresolve) {
		if (oldReferenceValue != null && unresolve) {
			c.oldValue = createProxy(oldReferenceValue)
		} else {
			c.oldValue = oldReferenceValue
		}
	}
	
	private def static <A extends EObject> void setEObjectExistenceChange(EObjectExistenceEChange<A> c, A affectedEObject, boolean unresolve) {
		if (affectedEObject != null && unresolve) {
			c.affectedEObject = createProxy(affectedEObject)
		} else {
			c.affectedEObject = affectedEObject;
		}
	}
	
	def static <T extends EObject> InsertRootEObject<T> createInsertRootChange(T newValue, String resourceURI, int index, boolean unresolve) {
		val c = RootFactory.eINSTANCE.createInsertRootEObject
		setNewValue(c, newValue, unresolve)
		setRootChangeFeatures(c, resourceURI, index)
		return c
	}
	
	def static <T extends EObject> RemoveRootEObject<T> createRemoveRootChange(T oldValue, String resourceURI, int index, boolean unresolve) {
		val c = RootFactory.eINSTANCE.createRemoveRootEObject
		setOldValue(c, oldValue, unresolve)
		setRootChangeFeatures(c, resourceURI, index)
		return c
	}

	def static <A extends EObject, T extends Object> InsertEAttributeValue<A,T> createInsertAttributeChange(A affectedEObject, EAttribute affectedAttribute, int index, T newValue, boolean unresolve) {
		val c = AttributeFactory.eINSTANCE.createInsertEAttributeValue()
		setFeatureChangeFeatures(c, affectedEObject, affectedAttribute, unresolve)
		c.newValue = newValue
		c.index = index
		return c
	}
	
	def static <A extends EObject, T extends Object> ReplaceSingleValuedEAttribute<A,T> createReplaceSingleAttributeChange(A affectedEObject, EAttribute affectedAttribute, T oldValue, T newValue, boolean unresolve) {
		val c = AttributeFactory.eINSTANCE.createReplaceSingleValuedEAttribute
		setFeatureChangeFeatures(c,affectedEObject,affectedAttribute, unresolve)
		c.oldValue = oldValue
		c.newValue = newValue
		return c
	}
	
	def static <A extends EObject, T extends Object> RemoveEAttributeValue<A,T> createRemoveAttributeChange(A affectedEObject, EAttribute affectedAttribute, int index, T oldValue, boolean unresolve) {
		val c = AttributeFactory.eINSTANCE.createRemoveEAttributeValue()
		setFeatureChangeFeatures(c, affectedEObject, affectedAttribute, unresolve)
		c.oldValue = oldValue
		c.index = index
		return c
	}
	
	def static <A extends EObject, T extends EObject> InsertEReference<A,T> createInsertReferenceChange(A affectedEObject, EReference affectedReference, T newValue, int index, boolean unresolve) {
		val c = ReferenceFactory.eINSTANCE.createInsertEReference()
		setFeatureChangeFeatures(c, affectedEObject, affectedReference, unresolve)
		setNewValue(c, newValue, unresolve)
		c.index = index
		return c
	}
	
	def static <A extends EObject, T extends EObject> ReplaceSingleValuedEReference<A,T> createReplaceSingleReferenceChange(A affectedEObject, EReference affectedReference, T oldValue, T newValue, boolean unresolve) {
		val c = ReferenceFactory.eINSTANCE.createReplaceSingleValuedEReference
		setFeatureChangeFeatures(c, affectedEObject, affectedReference, unresolve)
		setOldValue(c, oldValue, unresolve)
		setNewValue(c, newValue, unresolve)
		return c
	}
	
	def static <A extends EObject, T extends EObject> RemoveEReference<A,T> createRemoveReferenceChange(A affectedEObject, EReference affectedReference, T oldValue, int index, boolean unresolve) {
		val c = ReferenceFactory.eINSTANCE.createRemoveEReference()
		setFeatureChangeFeatures(c, affectedEObject, affectedReference, unresolve)
		setOldValue(c, oldValue, unresolve)
		c.index = index
		return c
	}
	
	def static <A extends EObject> CreateEObject<A> createCreateEObjectChange(A affectedEObject, boolean unresolve) {
		val c = EobjectFactory.eINSTANCE.createCreateEObject()
		setEObjectExistenceChange(c, affectedEObject, unresolve)
		return c
	}
	
	def static <A extends EObject> DeleteEObject<A> createDeleteEObjectChange(A affectedEObject, boolean unresolve) {
		val c = EobjectFactory.eINSTANCE.createDeleteEObject()
		setEObjectExistenceChange(c, affectedEObject, unresolve)
		return c
	}
	
}