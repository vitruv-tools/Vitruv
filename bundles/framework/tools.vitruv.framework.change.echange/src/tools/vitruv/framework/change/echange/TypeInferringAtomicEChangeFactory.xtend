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
import org.eclipse.emf.ecore.resource.Resource

/**
 * Factory singleton class for elements of change models.
 * Infers types (i.e. metaclasses and feature types) from parameters where possible.<br/>
 * 
 * Can be used by any transformation that creates change models.
 */
class TypeInferringAtomicEChangeFactory {
	private static TypeInferringAtomicEChangeFactory instance

	protected new() {
	}

	/**
	 * Get the singleton instance of the factory.
	 * @return The singleton instance.
	 */
	def public static TypeInferringAtomicEChangeFactory getInstance() {
		if (instance === null) {
			instance = new TypeInferringAtomicEChangeFactory()
		}
		return instance
	}

	/** 
	 * Sets the attributes of a RootEChange.
	 * @param change The RootEChange which attributes are to be set.
	 * @param resourceURI The affected resource of the change.
	 * @param index The affected index of the resource.
	 */
	def protected setRootChangeFeatures(RootEChange change, Resource resource, int index) {
		change.uri = resource.URI.toString
		change.resource = resource
		change.index = index
	}

	/**
	 * Sets the attributes of a FeatureEChange.
	 * @param change The FeatureEChange which attributes are to be set.
	 * @param affectedEObject The affected EObject of the change.
	 * @param affectedFeature The affected feature of the change.
	 */
	def protected <A extends EObject, F extends EStructuralFeature> void setFeatureChangeFeatures(
		FeatureEChange<A, F> change, A affectedEObject, F affectedFeature) {
		change.affectedEObject = affectedEObject
		change.affectedFeature = affectedFeature
	}

	/** 
	 * Sets the new value of a EObjectAddedEChange.
	 * @param change The EObjectAddedEChange which new value is to be set.
	 * @param newValue The new value of the change.
	 */
	def protected <T extends EObject> void setNewValue(EObjectAddedEChange<T> change, T newValue) {
		change.newValue = newValue
	}

	/**
	 * Sets the old value of the EObjectSubtractedEChange.
	 * @param change The EObjectSubtractedEChange which old value is to be set.
	 * @param oldValue The old value of the change.
	 */
	def protected <T extends EObject> void setOldValue(EObjectSubtractedEChange<T> change, T oldValue) {
		change.oldValue = oldValue
	}

	/**
	 * Sets the affected EObject of a EObjectExistenceEChange.
	 * @param change The EObjectExistenceEChange which affected EObject is to be set.
	 * @param affectedEObject The affected EObject.
	 * @param resource The resource which contains the staging area, where the object will be placed in / removed from.
	 */
	def protected <A extends EObject> void setEObjectExistenceChange(EObjectExistenceEChange<A> change,
		A affectedEObject, Resource resource, String objectId) {
		change.affectedEObject = affectedEObject;
		change.idAttributeValue = objectId;
	}

	/**
	 * Creates a new {@link InsertRootEObject} EChange.
	 * @param newValue The new root EObject.
	 * @param resource The resource which the new root object is placed in.
	 * @param index The index of the resource which the new root object is placed in.
	 * @return The created InsertRootEObject EChange.
	 */
	def <T extends EObject> InsertRootEObject<T> createInsertRootChange(T newValue, Resource resource, int index) {
		val c = RootFactory.eINSTANCE.createInsertRootEObject
		setNewValue(c, newValue)
		setRootChangeFeatures(c, resource, index)
		return c
	}

	/**
	 * Creates a new {@link RemoveRootEObject} EChange.
	 * @param oldValue The root EObject which is removed.
	 * @param resource The resource which the root object is removed from.
	 * @param index The index of the resource which the root object is removed from.
	 * @return The created RemoveRootEObject EChange.
	 */
	def <T extends EObject> RemoveRootEObject<T> createRemoveRootChange(T oldValue, Resource resource, int index) {
		val c = RootFactory.eINSTANCE.createRemoveRootEObject
		setOldValue(c, oldValue)
		setRootChangeFeatures(c, resource, index)
		return c
	}

	/**
	 * Creates a new {@link InsertEAttribute} EChange.
	 * @param affectedEObject The affected EObject of the change.
	 * @param affectedAttribute The affected EAttribute of the change.
	 * @param newValue The inserted value.
	 * @param index The index at which the new value is inserted in the attribute.
	 * @return The created InsertEAttributeValue EChange.
	 */
	def <A extends EObject, T extends Object> InsertEAttributeValue<A, T> createInsertAttributeChange(A affectedEObject,
		EAttribute affectedAttribute, int index, T newValue) {
		val c = AttributeFactory.eINSTANCE.createInsertEAttributeValue()
		setFeatureChangeFeatures(c, affectedEObject, affectedAttribute)
		c.newValue = newValue
		c.index = index
		return c
	}

	/** 
	 * Creates a new {@link ReplaceSingleValuedEAttribute} EChange.
	 * @param affectedEObject The affected EObject of the change.
	 * @param affectedAttribute The affected EAttribute of the change.
	 * @param oldValue The replaced value.
	 * @param newValue The new value.
	 * @return The created ReplaceSingleValuedEAttribute EChange.
	 */
	def <A extends EObject, T extends Object> ReplaceSingleValuedEAttribute<A, T> createReplaceSingleAttributeChange(
		A affectedEObject, EAttribute affectedAttribute, T oldValue, T newValue) {
		val c = AttributeFactory.eINSTANCE.createReplaceSingleValuedEAttribute
		setFeatureChangeFeatures(c, affectedEObject, affectedAttribute)
		c.oldValue = oldValue
		c.newValue = newValue
		return c
	}

	/**
	 * Creates a new {@link RemoveEAttributeValue} EChange.
	 * @param affectedEObject The affected EObject of the change.
	 * @param affectedAttribute The affected EAttribute of the change.
	 * @param oldValue The removed value.
	 * @param index The index at which the old value is removed from.
	 * @return The created RemoveEAttributeValue EChange.
	 */
	def <A extends EObject, T extends Object> RemoveEAttributeValue<A, T> createRemoveAttributeChange(A affectedEObject,
		EAttribute affectedAttribute, int index, T oldValue) {
		val c = AttributeFactory.eINSTANCE.createRemoveEAttributeValue()
		setFeatureChangeFeatures(c, affectedEObject, affectedAttribute)
		c.oldValue = oldValue
		c.index = index
		return c
	}

	/**
	 * Creates a new {@link InsertEReference} EChange.
	 * @param affectedEObject The affected EObject of the change.
	 * @param affectedReference The affected EReference of the change.
	 * @param newValue The inserted value.
	 * @param index The index at which the new value is inserted in the reference.
	 * @return The created InsertEReference EChange.
	 */
	def <A extends EObject, T extends EObject> InsertEReference<A, T> createInsertReferenceChange(A affectedEObject,
		EReference affectedReference, T newValue, int index) {
		val c = ReferenceFactory.eINSTANCE.createInsertEReference()
		setFeatureChangeFeatures(c, affectedEObject, affectedReference)
		setNewValue(c, newValue)
		c.index = index
		return c
	}

	/**
	 * Creates a new {@link ReplaceSingleValuedEReference} EChange.
	 * @param affectedEObject The affected EObject of the change.
	 * @param affectedReference The affected EReference of the change.
	 * @param oldValue The value which is replaced.
	 * @param newValue The value which replaces the old one.
	 * @return The created ReplaceSingleValuedEReference EChange.
	 */
	def <A extends EObject, T extends EObject> ReplaceSingleValuedEReference<A, T> createReplaceSingleReferenceChange(
		A affectedEObject, EReference affectedReference, T oldValue, T newValue) {
		val c = ReferenceFactory.eINSTANCE.createReplaceSingleValuedEReference
		setFeatureChangeFeatures(c, affectedEObject, affectedReference)
		setOldValue(c, oldValue)
		setNewValue(c, newValue)
		return c
	}

	/**
	 * Creates a new {@link RemoveEReference} EChange.
	 * @param affectedEObject The affected EObject of the change.
	 * @param affectedReference The affected EReference of the change.
	 * @param oldValue The value which is removed.
	 * @param index The index at which the old value is removed from.
	 * @return The created RemoveEReference EChange.
	 */
	def <A extends EObject, T extends EObject> RemoveEReference<A, T> createRemoveReferenceChange(A affectedEObject,
		EReference affectedReference, T oldValue, int index) {
		val c = ReferenceFactory.eINSTANCE.createRemoveEReference()
		setFeatureChangeFeatures(c, affectedEObject, affectedReference)
		setOldValue(c, oldValue)
		c.index = index
		return c
	}

	/**
	 * Creates a new {@link CreateEObject} EChange.
	 * @param affectedEObject The created EObject.
	 * @param resource The resource, in which staging area the EObject is inserted.
	 * @return The created CreateEObject EChange.
	 */
	def <A extends EObject> CreateEObject<A> createCreateEObjectChange(A affectedEObject, Resource resource, String objectId) {
		val c = EobjectFactory.eINSTANCE.createCreateEObject()
		setEObjectExistenceChange(c, affectedEObject, resource, objectId)
		return c
	}

	/**
	 * Creates a new {@link DeleteEObject} EChange.
	 * @param affectedEObject The deleted EObject.
	 * @param resource The resource, from which staging area the EObject is removed.
	 * @return The created DeleteEObject EChange.
	 */
	def <A extends EObject> DeleteEObject<A> createDeleteEObjectChange(A affectedEObject, Resource resource, String objectId) {
		val c = EobjectFactory.eINSTANCE.createDeleteEObject()
		setEObjectExistenceChange(c, affectedEObject, resource, objectId)
		return c
	}

}
