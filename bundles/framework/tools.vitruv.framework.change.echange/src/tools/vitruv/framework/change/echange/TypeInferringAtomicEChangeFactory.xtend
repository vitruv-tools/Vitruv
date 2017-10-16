package tools.vitruv.framework.change.echange

import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.resource.Resource

import tools.vitruv.framework.change.echange.eobject.CreateEObject
import tools.vitruv.framework.change.echange.eobject.DeleteEObject
import tools.vitruv.framework.change.echange.feature.attribute.InsertEAttributeValue
import tools.vitruv.framework.change.echange.feature.attribute.RemoveEAttributeValue
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference
import tools.vitruv.framework.change.echange.impl.TypeInferringAtomicEChangeFactoryImpl
import tools.vitruv.framework.change.echange.root.InsertRootEObject
import tools.vitruv.framework.change.echange.root.RemoveRootEObject

/**
 * Factory singleton class for elements of change models.
 * Infers types (i.e. metaclasses and feature types) from parameters where possible.<br/>
 * 
 * Can be used by any transformation that creates change models.
 */
interface TypeInferringAtomicEChangeFactory {
	TypeInferringAtomicEChangeFactory instance = TypeInferringAtomicEChangeFactoryImpl::init

	/**
	 * Creates a new {@link InsertRootEObject} EChange.
	 * @param newValue The new root EObject.
	 * @param resource The resource which the new root object is placed in.
	 * @param index The index of the resource which the new root object is placed in.
	 * @return The created InsertRootEObject EChange.
	 */
	def <T extends EObject> InsertRootEObject<T> createInsertRootChange(T newValue, Resource resource, int index)

	/**
	 * Creates a new {@link RemoveRootEObject} EChange.
	 * @param oldValue The root EObject which is removed.
	 * @param resource The resource which the root object is removed from.
	 * @param index The index of the resource which the root object is removed from.
	 * @return The created RemoveRootEObject EChange.
	 */
	def <T extends EObject> RemoveRootEObject<T> createRemoveRootChange(T oldValue, Resource resource, int index)

	/**
	 * Creates a new {@link InsertEAttribute} EChange.
	 * @param affectedEObject The affected EObject of the change.
	 * @param affectedAttribute The affected EAttribute of the change.
	 * @param newValue The inserted value.
	 * @param index The index at which the new value is inserted in the attribute.
	 * @return The created InsertEAttributeValue EChange.
	 */
	def <A extends EObject, T extends Object> InsertEAttributeValue<A, T> createInsertAttributeChange(A affectedEObject,
		EAttribute affectedAttribute, int index, T newValue)

	/** 
	 * Creates a new {@link ReplaceSingleValuedEAttribute} EChange.
	 * @param affectedEObject The affected EObject of the change.
	 * @param affectedAttribute The affected EAttribute of the change.
	 * @param oldValue The replaced value.
	 * @param newValue The new value.
	 * @return The created ReplaceSingleValuedEAttribute EChange.
	 */
	def <A extends EObject, T extends Object> ReplaceSingleValuedEAttribute<A, T> createReplaceSingleAttributeChange(
		A affectedEObject, EAttribute affectedAttribute, T oldValue, T newValue)

	/**
	 * Creates a new {@link RemoveEAttributeValue} EChange.
	 * @param affectedEObject The affected EObject of the change.
	 * @param affectedAttribute The affected EAttribute of the change.
	 * @param oldValue The removed value.
	 * @param index The index at which the old value is removed from.
	 * @return The created RemoveEAttributeValue EChange.
	 */
	def <A extends EObject, T extends Object> RemoveEAttributeValue<A, T> createRemoveAttributeChange(A affectedEObject,
		EAttribute affectedAttribute, int index, T oldValue)

	/**
	 * Creates a new {@link InsertEReference} EChange.
	 * @param affectedEObject The affected EObject of the change.
	 * @param affectedReference The affected EReference of the change.
	 * @param newValue The inserted value.
	 * @param index The index at which the new value is inserted in the reference.
	 * @return The created InsertEReference EChange.
	 */
	def <A extends EObject, T extends EObject> InsertEReference<A, T> createInsertReferenceChange(A affectedEObject,
		EReference affectedReference, T newValue, int index)

	/**
	 * Creates a new {@link ReplaceSingleValuedEReference} EChange.
	 * @param affectedEObject The affected EObject of the change.
	 * @param affectedReference The affected EReference of the change.
	 * @param oldValue The value which is replaced.
	 * @param newValue The value which replaces the old one.
	 * @return The created ReplaceSingleValuedEReference EChange.
	 */
	def <A extends EObject, T extends EObject> ReplaceSingleValuedEReference<A, T> createReplaceSingleReferenceChange(
		A affectedEObject,
		EReference affectedReference,
		T oldValue,
		T newValue
	)

	/**
	 * Creates a new {@link RemoveEReference} EChange.
	 * @param affectedEObject The affected EObject of the change.
	 * @param affectedReference The affected EReference of the change.
	 * @param oldValue The value which is removed.
	 * @param index The index at which the old value is removed from.
	 * @return The created RemoveEReference EChange.
	 */
	def <A extends EObject, T extends EObject> RemoveEReference<A, T> createRemoveReferenceChange(A affectedEObject,
		EReference affectedReference, T oldValue, int index)

	/**
	 * Creates a new {@link CreateEObject} EChange.
	 * @param affectedEObject The created EObject.
	 * @param resource The resource, in which staging area the EObject is inserted.
	 * @return The created CreateEObject EChange.
	 */
	def <A extends EObject> CreateEObject<A> createCreateEObjectChange(
		A affectedEObject,
		Resource resource,
		String objectId
	)

	/**
	 * Creates a new {@link DeleteEObject} EChange.
	 * @param affectedEObject The deleted EObject.
	 * @param resource The resource, from which staging area the EObject is removed.
	 * @return The created DeleteEObject EChange.
	 */
	def <A extends EObject> DeleteEObject<A> createDeleteEObjectChange(
		A affectedEObject,
		Resource resource,
		String objectId
	)
}
