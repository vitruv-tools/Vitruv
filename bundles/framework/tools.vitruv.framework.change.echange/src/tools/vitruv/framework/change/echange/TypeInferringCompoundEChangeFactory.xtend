package tools.vitruv.framework.change.echange

import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.resource.Resource

class TypeInferringCompoundEChangeFactory {
	protected TypeInferringAtomicEChangeFactory atomicFactory
	static TypeInferringCompoundEChangeFactory instance

	protected new(TypeInferringAtomicEChangeFactory atomicFactory) {
		this.atomicFactory = atomicFactory
	}

	/**
	 * Get the singleton instance of the factory.
	 * @return The singleton instance.
	 */
	def static TypeInferringCompoundEChangeFactory getInstance() {
		if (instance === null) {
			instance = new TypeInferringCompoundEChangeFactory(TypeInferringAtomicEChangeFactory.instance)
		}
		return instance
	}

	/**
	 * Creates a new {@link CreateAndInsertRoot} EChange.
	 * @param affectedEObject The created and inserted root object by the change.
	 * @param resource The resource where the root object will be inserted.
	 * @param index The index at which the root object will be inserted into the resource.
	 * @return The created change.
	 */
	def <T extends EObject> List<EChange> createCreateAndInsertRootChange(T affectedEObject, Resource resource,
		int index) {
		val createChange = atomicFactory.createCreateEObjectChange(affectedEObject);
		val insertChange = atomicFactory.createInsertRootChange(affectedEObject, resource, index);
		return #[createChange, insertChange]
	}

	/**
	 * Creates a new {@link CreateAndRemoveDeleteRoot} EChange.
	 * @param affectedEObject The removed and deleted root object by the change.
	 * @param resource The resource where the root object will be removed from.
	 * @param index The index at which the root object will be removed from the resource.
	 * @return The created change.
	 */
	def <T extends EObject> List<EChange> createRemoveAndDeleteRootChange(T affectedEObject, Resource resource,
		int index) {
		val deleteChange = atomicFactory.createDeleteEObjectChange(affectedEObject);
		val removeChange = atomicFactory.createRemoveRootChange(affectedEObject, resource, index);
		return #[removeChange, deleteChange]
	}

	/**
	 * Creates a new {@link CreateAndInsertNonRoot} EChange.
	 * @param affectedEObject The affected object, in which feature the created non root element will be inserted.
	 * @param reference The reference of the affected object, in which the created non root element will be inserted.
	 * @param newValue The created and inserted non root element.
	 * @param index The index at which the non root element will be inserted into the reference.
	 * @return The created change.
	 */
	def <A extends EObject, T extends EObject> List<EChange> createCreateAndInsertNonRootChange(
		A affectedEObject, EReference reference, T newValue, int index) {
		val createChange = atomicFactory.createCreateEObjectChange(newValue);
		val insertChange = atomicFactory.createInsertReferenceChange(affectedEObject, reference, newValue, index);
		return #[createChange, insertChange]
	}

	/**
	 * Creates a new {@link RemoveAndDeleteNonRoot} EChange.
	 * @param affectedEObject The affected object, from which feature the non root element will be removed.
	 * @param reference The reference of the affected object, from which the non root element will be removed.
	 * @param oldValue The removed and deleted non root element.
	 * @param index The index at which the non root element will be removed from the reference.
	 * @return The created change.
	 */
	def <A extends EObject, T extends EObject> List<EChange> createRemoveAndDeleteNonRootChange(
		A affectedEObject, EReference reference, T oldValue, int index) {
		val deleteChange = atomicFactory.createDeleteEObjectChange(oldValue)
		val removeChange = atomicFactory.createRemoveReferenceChange(affectedEObject, reference, oldValue, index)
		return #[removeChange, deleteChange]
	}

	/**
	 * Creates a new {@link CreateAndReplaceNonRoot} EChange.
	 * @param affectedEObject The affected object, in which feature null will be replaced by the new value.
	 * @param reference The reference of the affected object, in which null will be replaced by the new value.
	 * @param newValue The new value which replaces null.
	 * @return The created change.
	 */
	def <A extends EObject, T extends EObject> List<EChange> createCreateAndReplaceNonRootChange(
		A affectedEObject, EReference reference, T newValue) {
		val createChange = atomicFactory.createCreateEObjectChange(newValue)
		val insertChange = atomicFactory.createReplaceSingleReferenceChange(affectedEObject, reference, null, newValue)
		return #[createChange, insertChange]
	}

	/**
	 * Creates a new {@link ReplaceAndDeleteNonRoot} EChange.
	 * @param affectedEObject The affected object, in which feature the old value will be replaced by null.
	 * @param reference The reference of the affected object, in which the old value will be replaced by null.
	 * @param oldValue The old value which will be replaced by null.
	 * @return The created change.
	 */
	def <A extends EObject, T extends EObject> List<EChange> createReplaceAndDeleteNonRootChange(
		A affectedEObject, EReference reference, T oldValue) {
		val removeChange = atomicFactory.createReplaceSingleReferenceChange(affectedEObject, reference, oldValue, null)
		val deleteChange = atomicFactory.createDeleteEObjectChange(oldValue)
		return #[removeChange, deleteChange]
	}

	/**
	 * Creates a new {@link CreateAndReplaceAndDeleteNonRoot} EChange.
	 * @param affectedEObject The affected object, in which feature the non root element will be replaced.
	 * @param reference The reference of the affected object, in which the non root element will be replaced.
	 * @param oldValue The replaced and deleted non root element.
	 * @param newValue The created and replacing non root element.
	 * @return The created change.
	 */
	def <A extends EObject, T extends EObject> List<EChange> createCreateAndReplaceAndDeleteNonRootChange(
		A affectedEObject, EReference reference, T oldValue, T newValue) {
		val deleteChange = atomicFactory.createDeleteEObjectChange(oldValue);
		val createChange = atomicFactory.createCreateEObjectChange(newValue);
		val replaceChange = atomicFactory.createReplaceSingleReferenceChange(affectedEObject, reference, oldValue,
			newValue);
		return #[createChange, replaceChange, deleteChange]
	}

}
		