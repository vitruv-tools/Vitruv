package tools.vitruv.framework.change.echange

import java.util.List
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.framework.change.echange.compound.CompoundFactory
import tools.vitruv.framework.change.echange.compound.ExplicitUnsetEAttribute
import tools.vitruv.framework.change.echange.compound.ExplicitUnsetEFeature
import tools.vitruv.framework.change.echange.compound.ExplicitUnsetEReference
import tools.vitruv.framework.change.echange.feature.attribute.SubtractiveAttributeEChange

class TypeInferringCompoundEChangeFactory {
	protected TypeInferringAtomicEChangeFactory atomicFactory
	private static TypeInferringCompoundEChangeFactory instance

	protected new(TypeInferringAtomicEChangeFactory atomicFactory) {
		this.atomicFactory = atomicFactory
	}

	/**
	 * Get the singleton instance of the factory.
	 * @return The singleton instance.
	 */
	def public static TypeInferringCompoundEChangeFactory getInstance() {
		if (instance === null) {
			instance = new TypeInferringCompoundEChangeFactory(TypeInferringAtomicEChangeFactory.instance)
		}
		return instance
	}

	/**
	 * Sets the attributes of an ExplicitUnsetEFeature change.
	 * @param change The ExplicitUnsetEFeature which attributes will be set.
	 * @param affectedEObject The affected EObject of the change.
	 * @param affectedFeature The affected feature of the change.
	 */
	def protected <A extends EObject, F extends EStructuralFeature> setUnsetChangeFeatures(
		ExplicitUnsetEFeature<A, F> change, A affectedEObject, F affectedFeature) {
		change.affectedEObject = affectedEObject
		change.affectedFeature = affectedFeature
	}

	/**
	 * Sets the subtractive changes of an ExplicitUnsetEAttribute change.
	 * @param change The ExplicitUnsetEAttribute EChange which subtractive changes will be set.
	 * @param changes The subtractive changes.
	 */
	def protected <A extends EObject, T extends Object> setUnsetAttributeChangeSubtractiveChanges(
		ExplicitUnsetEAttribute<A, T> change, List<SubtractiveAttributeEChange<A, T>> changes) {
		for (c : changes) {
			change.subtractiveChanges.add(c);
		}
	}

	/**
	 * Sets the EChanges of an ExplicitUnsetEReference change.
	 * @param change The ExplicitUnsetEReference EChange which EChanges will be set.
	 * @param changes The EChanges.
	 */
	def protected <A extends EObject> setUnsetReferenceChangeEChanges(ExplicitUnsetEReference<A> change,
		List<EChange> changes) {
		for (c : changes) {
			change.changes.add(c);
		}
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

	/**
	 * Creates a new {@link ExplicitUnsetEAttribute} EChange.
	 * @param affectedEObject The affected object, which attribute will be unset.
	 * @param affectedAttribute The affected attribute of the affected object, which will be unset.
	 * @param changes The subtractive changes, which removes the values of the attribute, before it will be unset.
	 * @return The created change.
	 */
	def <A extends EObject, T extends Object> ExplicitUnsetEAttribute<A, T> createExplicitUnsetEAttributeChange(
		A affectedEObject, EAttribute affectedAttribute, List<SubtractiveAttributeEChange<A, T>> changes) {
		val c = CompoundFactory.eINSTANCE.createExplicitUnsetEAttribute()
		c.setUnsetChangeFeatures(affectedEObject, affectedAttribute)
		c.setUnsetAttributeChangeSubtractiveChanges(changes)
		return c
	}

	/**
	 * Creates a new {@link ExplicitUnsetEReference} EChange.
	 * @param affectedEObject The affected object, which reference will be unset.
	 * @param affectedReference The affected reference of the affected object, which will be unset.
	 * @param changes The subtractive changes, which removes the values of the reference, before it will be unset.
	 * @return The created change.
	 */
	def <A extends EObject, T extends EObject> ExplicitUnsetEReference<A> createExplicitUnsetEReferenceChange(
		A affectedEObject, EReference affectedReference, List<EChange> changes) {
		val c = CompoundFactory.eINSTANCE.createExplicitUnsetEReference();
		c.setUnsetChangeFeatures(affectedEObject, affectedReference)
		c.setUnsetReferenceChangeEChanges(changes)
		return c;
	}
}
		