package tools.vitruv.framework.change.echange.impl

import java.util.List

import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.resource.Resource

import tools.vitruv.framework.change.echange.compound.CompoundFactory
import tools.vitruv.framework.change.echange.compound.CreateAndInsertNonRoot
import tools.vitruv.framework.change.echange.compound.CreateAndInsertRoot
import tools.vitruv.framework.change.echange.compound.CreateAndReplaceAndDeleteNonRoot
import tools.vitruv.framework.change.echange.compound.CreateAndReplaceNonRoot
import tools.vitruv.framework.change.echange.compound.ExplicitUnsetEAttribute
import tools.vitruv.framework.change.echange.compound.ExplicitUnsetEFeature
import tools.vitruv.framework.change.echange.compound.ExplicitUnsetEReference
import tools.vitruv.framework.change.echange.compound.RemoveAndDeleteNonRoot
import tools.vitruv.framework.change.echange.compound.RemoveAndDeleteRoot
import tools.vitruv.framework.change.echange.compound.ReplaceAndDeleteNonRoot
import tools.vitruv.framework.change.echange.feature.attribute.SubtractiveAttributeEChange
import tools.vitruv.framework.change.echange.TypeInferringAtomicEChangeFactory
import tools.vitruv.framework.change.echange.TypeInferringCompoundEChangeFactory
import tools.vitruv.framework.change.echange.EChange

class TypeInferringCompoundEChangeFactoryImpl implements TypeInferringCompoundEChangeFactory {
	static extension CompoundFactory = CompoundFactory::eINSTANCE
	protected TypeInferringAtomicEChangeFactory atomicFactory

	protected new(TypeInferringAtomicEChangeFactory atomicFactory) {
		this.atomicFactory = atomicFactory
	}

	static def TypeInferringCompoundEChangeFactory init() {
		new TypeInferringCompoundEChangeFactoryImpl(TypeInferringAtomicEChangeFactory::instance)
	}

	override <T extends EObject> CreateAndInsertRoot<T> createCreateAndInsertRootChange(
		T affectedEObject,
		Resource resource,
		int index,
		String objectId
	) {
		val c = createCreateAndInsertRoot
		c.createChange = atomicFactory.createCreateEObjectChange(affectedEObject, resource, objectId)
		c.insertChange = atomicFactory.createInsertRootChange(affectedEObject, resource, index)
		c
	}

	override <T extends EObject> RemoveAndDeleteRoot<T> createRemoveAndDeleteRootChange(
		T affectedEObject,
		Resource resource,
		int index,
		String objectId
	) {
		val c = createRemoveAndDeleteRoot
		c.deleteChange = atomicFactory.createDeleteEObjectChange(affectedEObject, resource, objectId)
		c.removeChange = atomicFactory.createRemoveRootChange(affectedEObject, resource, index)
		c
	}

	override <A extends EObject, T extends EObject> CreateAndInsertNonRoot<A, T> createCreateAndInsertNonRootChange(
		A affectedEObject,
		EReference reference,
		T newValue,
		int index,
		String objectId
	) {
		val c = createCreateAndInsertNonRoot
		c.createChange = atomicFactory.createCreateEObjectChange(newValue, affectedEObject.eResource, objectId)
		c.insertChange = atomicFactory.createInsertReferenceChange(affectedEObject, reference, newValue, index)
		c
	}

	override <A extends EObject, T extends EObject> RemoveAndDeleteNonRoot<A, T> createRemoveAndDeleteNonRootChange(
		A affectedEObject,
		EReference reference,
		T oldValue,
		int index,
		String objectId
	) {
		val c = createRemoveAndDeleteNonRoot
		c.deleteChange = atomicFactory.createDeleteEObjectChange(oldValue, affectedEObject.eResource, objectId)
		c.removeChange = atomicFactory.createRemoveReferenceChange(affectedEObject, reference, oldValue, index)
		c
	}

	override <A extends EObject, T extends EObject> CreateAndReplaceNonRoot<A, T> createCreateAndReplaceNonRootChange(
		A affectedEObject,
		EReference reference,
		T newValue,
		String objectId
	) {
		val c = createCreateAndReplaceNonRoot
		c.createChange = atomicFactory.createCreateEObjectChange(newValue, affectedEObject.eResource, objectId)
		c.insertChange = atomicFactory.createReplaceSingleReferenceChange(affectedEObject, reference, null, newValue)
		c
	}

	override <A extends EObject, T extends EObject> ReplaceAndDeleteNonRoot<A, T> createReplaceAndDeleteNonRootChange(
		A affectedEObject,
		EReference reference,
		T oldValue,
		String objectId
	) {
		val c = createReplaceAndDeleteNonRoot
		c.removeChange = atomicFactory.createReplaceSingleReferenceChange(affectedEObject, reference, oldValue, null)
		c.deleteChange = atomicFactory.createDeleteEObjectChange(oldValue, affectedEObject.eResource,
			objectId)
		c
	}

	override <A extends EObject, T extends EObject> CreateAndReplaceAndDeleteNonRoot<A, T> createCreateAndReplaceAndDeleteNonRootChange(
		A affectedEObject,
		EReference reference,
		T oldValue,
		T newValue,
		String oldObjectId,
		String newObjectId
	) {
		val c = createCreateAndReplaceAndDeleteNonRoot
		c.deleteChange = atomicFactory.createDeleteEObjectChange(oldValue, affectedEObject.eResource, oldObjectId)
		c.createChange = atomicFactory.createCreateEObjectChange(newValue, affectedEObject.eResource, newObjectId)
		c.replaceChange = atomicFactory.createReplaceSingleReferenceChange(affectedEObject, reference, oldValue,
			newValue)
		c
	}

	override <A extends EObject, T extends Object> ExplicitUnsetEAttribute<A, T> createExplicitUnsetEAttributeChange(
		A affectedEObject,
		EAttribute affectedAttribute,
		List<SubtractiveAttributeEChange<A, T>> changes
	) {
		val c = createExplicitUnsetEAttribute
		c.setUnsetChangeFeatures(affectedEObject, affectedAttribute)
		c.setUnsetAttributeChangeSubtractiveChanges(changes)
		c
	}

	override <A extends EObject, T extends EObject> ExplicitUnsetEReference<A> createExplicitUnsetEReferenceChange(
		A affectedEObject,
		EReference affectedReference,
		List<EChange> changes
	) {
		val c = createExplicitUnsetEReference
		c.setUnsetChangeFeatures(affectedEObject, affectedReference)
		c.setUnsetReferenceChangeEChanges(changes)
		c
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
			change.subtractiveChanges.add(c)
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
			change.changes.add(c)
		}
	}
}
