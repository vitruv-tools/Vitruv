package tools.vitruv.framework.change.echange

import java.util.List

import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.resource.Resource

import tools.vitruv.framework.change.echange.compound.CreateAndInsertNonRoot
import tools.vitruv.framework.change.echange.compound.CreateAndInsertRoot
import tools.vitruv.framework.change.echange.compound.CreateAndReplaceAndDeleteNonRoot
import tools.vitruv.framework.change.echange.compound.CreateAndReplaceNonRoot
import tools.vitruv.framework.change.echange.compound.ExplicitUnsetEAttribute
import tools.vitruv.framework.change.echange.compound.ExplicitUnsetEReference
import tools.vitruv.framework.change.echange.compound.RemoveAndDeleteNonRoot
import tools.vitruv.framework.change.echange.compound.RemoveAndDeleteRoot
import tools.vitruv.framework.change.echange.compound.ReplaceAndDeleteNonRoot
import tools.vitruv.framework.change.echange.feature.attribute.SubtractiveAttributeEChange
import tools.vitruv.framework.change.echange.impl.TypeInferringCompoundEChangeFactoryImpl

interface TypeInferringCompoundEChangeFactory {
	TypeInferringCompoundEChangeFactory instance = TypeInferringCompoundEChangeFactoryImpl::init

	/**
	 * Creates a new {@link CreateAndInsertRoot} EChange.
	 * @param affectedEObject The created and inserted root object by the change.
	 * @param resource The resource where the root object will be inserted.
	 * @param index The index at which the root object will be inserted into the resource.
	 * @return The created change.
	 */
	def <T extends EObject> CreateAndInsertRoot<T> createCreateAndInsertRootChange(T affectedEObject, Resource resource,
		int index)

	/**
	 * Creates a new {@link CreateAndRemoveDeleteRoot} EChange.
	 * @param affectedEObject The removed and deleted root object by the change.
	 * @param resource The resource where the root object will be removed from.
	 * @param index The index at which the root object will be removed from the resource.
	 * @return The created change.
	 */
	def <T extends EObject> RemoveAndDeleteRoot<T> createRemoveAndDeleteRootChange(T affectedEObject, Resource resource,
		int index)

	/**
	 * Creates a new {@link CreateAndInsertNonRoot} EChange.
	 * @param affectedEObject The affected object, in which feature the created non root element will be inserted.
	 * @param reference The reference of the affected object, in which the created non root element will be inserted.
	 * @param newValue The created and inserted non root element.
	 * @param index The index at which the non root element will be inserted into the reference.
	 * @return The created change.
	 */
	def <A extends EObject, T extends EObject> CreateAndInsertNonRoot<A, T> createCreateAndInsertNonRootChange(
		A affectedEObject, EReference reference, T newValue, int index)

	/**
	 * Creates a new {@link RemoveAndDeleteNonRoot} EChange.
	 * @param affectedEObject The affected object, from which feature the non root element will be removed.
	 * @param reference The reference of the affected object, from which the non root element will be removed.
	 * @param oldValue The removed and deleted non root element.
	 * @param index The index at which the non root element will be removed from the reference.
	 * @return The created change.
	 */
	def <A extends EObject, T extends EObject> RemoveAndDeleteNonRoot<A, T> createRemoveAndDeleteNonRootChange(
		A affectedEObject, EReference reference, T oldValue, int index)

	/**
	 * Creates a new {@link CreateAndReplaceNonRoot} EChange.
	 * @param affectedEObject The affected object, in which feature null will be replaced by the new value.
	 * @param reference The reference of the affected object, in which null will be replaced by the new value.
	 * @param newValue The new value which replaces null.
	 * @return The created change.
	 */
	def <A extends EObject, T extends EObject> CreateAndReplaceNonRoot<A, T> createCreateAndReplaceNonRootChange(
		A affectedEObject, EReference reference, T newValue)

	/**
	 * Creates a new {@link ReplaceAndDeleteNonRoot} EChange.
	 * @param affectedEObject The affected object, in which feature the old value will be replaced by null.
	 * @param reference The reference of the affected object, in which the old value will be replaced by null.
	 * @param oldValue The old value which will be replaced by null.
	 * @return The created change.
	 */
	def <A extends EObject, T extends EObject> ReplaceAndDeleteNonRoot<A, T> createReplaceAndDeleteNonRootChange(
		A affectedEObject, EReference reference,
		T oldValue)

		/**
		 * Creates a new {@link CreateAndReplaceAndDeleteNonRoot} EChange.
		 * @param affectedEObject The affected object, in which feature the non root element will be replaced.
		 * @param reference The reference of the affected object, in which the non root element will be replaced.
		 * @param oldValue The replaced and deleted non root element.
		 * @param newValue The created and replacing non root element.
		 * @return The created change.
		 */
		def <A extends EObject, T extends EObject> CreateAndReplaceAndDeleteNonRoot<A, T> createCreateAndReplaceAndDeleteNonRootChange(
			A affectedEObject, EReference reference, T oldValue, T newValue)

		/**
		 * Creates a new {@link ExplicitUnsetEAttribute} EChange.
		 * @param affectedEObject The affected object, which attribute will be unset.
		 * @param affectedAttribute The affected attribute of the affected object, which will be unset.
		 * @param changes The subtractive changes, which removes the values of the attribute, before it will be unset.
		 * @return The created change.
		 */
		def <A extends EObject, T extends Object> ExplicitUnsetEAttribute<A, T> createExplicitUnsetEAttributeChange(
			A affectedEObject, EAttribute affectedAttribute, List<SubtractiveAttributeEChange<A, T>> changes)

		/**
		 * Creates a new {@link ExplicitUnsetEReference} EChange.
		 * @param affectedEObject The affected object, which reference will be unset.
		 * @param affectedReference The affected reference of the affected object, which will be unset.
		 * @param changes The subtractive changes, which removes the values of the reference, before it will be unset.
		 * @return The created change.
		 */
		def <A extends EObject, T extends EObject> ExplicitUnsetEReference<A> createExplicitUnsetEReferenceChange(
			A affectedEObject, EReference affectedReference, List<EChange> changes)
	}
	