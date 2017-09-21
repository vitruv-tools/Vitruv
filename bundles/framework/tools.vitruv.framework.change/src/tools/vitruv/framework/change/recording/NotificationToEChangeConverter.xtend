package tools.vitruv.framework.change.recording

import java.util.ArrayList
import java.util.Collections
import java.util.List
import org.eclipse.emf.common.notify.Notification
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.SubtractiveEChange
import tools.vitruv.framework.change.echange.feature.attribute.AttributeFactory
import tools.vitruv.framework.change.echange.feature.attribute.InsertEAttributeValue
import tools.vitruv.framework.change.echange.feature.attribute.RemoveEAttributeValue
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute
import tools.vitruv.framework.change.echange.feature.attribute.SubtractiveAttributeEChange
import tools.vitruv.framework.change.echange.TypeInferringAtomicEChangeFactory
import tools.vitruv.framework.change.uuid.UuidGeneratorAndResolver
import tools.vitruv.framework.change.echange.EChangeIdManager
import tools.vitruv.framework.change.echange.feature.reference.ReferenceFactory
import tools.vitruv.framework.change.echange.eobject.EobjectFactory
import tools.vitruv.framework.change.echange.feature.reference.SubtractiveReferenceEChange
import static extension tools.vitruv.framework.change.preparation.EMFModelChangeTransformationUtil.*
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.framework.change.echange.eobject.EObjectAddedEChange
import tools.vitruv.framework.change.echange.eobject.EObjectSubtractedEChange

/** 
 * Converts an EMF notification to an {@link EChange}.
 * @author Heiko Klare
 */
final class NotificationToEChangeConverter {
	/** 
	 * Converts given notification to an Echange. May return null if the
	 * notification signifies a no-op.
	 * @param nthe notification to convert
	 * @return the EChange or null
	 */
	def // BEGIN COMPLEX CODE
	Iterable<EChange> convert(NotificationInfo n, List<EChange> previousChanges, EChangeIdManager idManager) {
		if (n.isTouch() || n.isTransient()) {
			return #[]
		}

		switch (n.getEventType()) {
			case Notification.SET: {
				if (n.isAttributeNotification()) {
					return handleSetAttribute(n, idManager)
				} else if (n.isReferenceNotification) {
					return handleSetReference(n, idManager);
				}
			}
			case Notification.UNSET: {
				if (n.isAttributeNotification()) {
					return handleUnsetAttribute(n, previousChanges, idManager)
				} else if (n.isReferenceNotification) {
					return handleUnsetReference(n, previousChanges, idManager);
				}
			}
			case Notification.ADD: {
				if (n.isAttributeNotification()) {
					return handleMultiAttribute(n, idManager)
				} else if (n.isReferenceNotification) {
					return handleMultiReference(n, idManager);
				}
			}
			case Notification.ADD_MANY: {
				if (n.isAttributeNotification()) {
					return handleMultiAttribute(n, idManager)
				} else if (n.isReferenceNotification) {
					return handleMultiReference(n, idManager);
				}
			}
			case Notification.REMOVE: {
				if (n.isAttributeNotification()) {
					return handleMultiAttribute(n, idManager)
				} else if (n.isReferenceNotification) {
					return handleMultiReference(n, idManager);
				}
			}
			case Notification.REMOVE_MANY: {
				if (n.isAttributeNotification()) {
					return handleMultiAttribute(n, idManager)
				} else if (n.isReferenceNotification) {
					return handleMultiReference(n, idManager);
				}
			}
		//
		// case Notification.MOVE:
		// if (n.isAttributeNotification()) {
		// return handleAttributeMove(n);
		// }
		// return handleReferenceMove(n);
		}
		if (n.notifier instanceof Resource) {
			return handleResourceChange(n, idManager)
		}
		return #[]
	}

	// END COMPLEX CODE
	@SuppressWarnings("unchecked") def private Iterable<EChange> handleMultiAttribute(NotificationInfo n,
		EChangeIdManager idManager) {
		var List<EChange> changes = new ArrayList()

		switch (n.getEventType()) {
			case Notification.ADD: {
				var InsertEAttributeValue<EObject, Object> op = AttributeFactory.eINSTANCE.createInsertEAttributeValue()
				op.setAffectedFeature((n.getFeature() as EAttribute))
				op.setNewValue(n.getNewValue())
				op.setAffectedEObject((n.getNotifier() as EObject))
				op.setIndex(n.getPosition())
				changes.add(op)
			}
			case Notification.ADD_MANY: {
				var List<Object> list = (n.getNewValue() as List<Object>)
				for (var int i = 0; i < list.size(); i++) {
					var InsertEAttributeValue<EObject, Object> operation = AttributeFactory.eINSTANCE.
						createInsertEAttributeValue()
					operation.setAffectedFeature((n.getFeature() as EAttribute))
					operation.setNewValue(list.get(i))
					operation.setAffectedEObject((n.getNotifier() as EObject))
					operation.setIndex(n.getInitialIndex() + i)
					changes.add(operation)
				}
			}
			case Notification.REMOVE: {
				var RemoveEAttributeValue<EObject, Object> operation = AttributeFactory.eINSTANCE.
					createRemoveEAttributeValue()
				operation.setOldValue(n.getOldValue())
				operation.setAffectedFeature((n.getFeature() as EAttribute))
				operation.setAffectedEObject((n.getNotifier() as EObject))
				operation.setIndex(n.getPosition())
				changes.add(operation)
			}
			case Notification.REMOVE_MANY: /* FIXME unsupported fall-through */ {
				var List<Object> list2 = (n.getOldValue() as List<Object>)
				if (n.getNewValue() === null) {
					for (var int i = list2.size() - 1; i >= 0; i--) {
						var RemoveEAttributeValue<EObject, Object> ope = AttributeFactory.eINSTANCE.
							createRemoveEAttributeValue()
						ope.setOldValue(list2.get(i))
						ope.setAffectedFeature((n.getFeature() as EAttribute))
						ope.setAffectedEObject((n.getNotifier() as EObject))
						ope.setIndex(n.getInitialIndex() + i)
						changes.add(ope)
					}
				}
			}
		}
		changes.forEach[idManager.setOrGenerateIds(it)];
		return changes
	}

	@SuppressWarnings("unchecked")
	def private Iterable<EChange> handleMultiReference(NotificationInfo n, EChangeIdManager idManager) {
		val changes = <EChange>newArrayList();

		val affectedEObject = n.notifierModelElement;
		val affectedReference = n.reference;
		switch (n.getEventType()) {
			case Notification.ADD: {
				changes += handleInsertReference(affectedEObject, affectedReference, n.newModelElementValue, n.position, idManager);
			}
			case Notification.ADD_MANY: {
				var List<EObject> list = (n.getNewValue() as List<EObject>)
				for (var int i = 0; i < list.size(); i++) {
					changes += handleInsertReference(affectedEObject, affectedReference, list.get(i), n.initialIndex + i, idManager);
				}
			}
			case Notification.REMOVE: {
				changes += handleRemoveReference(affectedEObject, affectedReference, n.oldModelElementValue, n.position, idManager);
			}
			case Notification.REMOVE_MANY: {
				var List<EObject> list = (n.getOldValue() as List<EObject>)
				if (n.getNewValue() === null) {
					for (var int i = list.size() - 1; i >= 0; i--) {
						changes += handleRemoveReference(affectedEObject, affectedReference, list.get(i), n.initialIndex + i, idManager);
					}
				}
			}
		}
		return changes;
	}
	
	private def handleInsertReference(EObject affectedEObject, EReference affectedReference, EObject newValue, int position, EChangeIdManager idManager) {
		val change = TypeInferringAtomicEChangeFactory.instance.createInsertReferenceChange(affectedEObject,
						affectedReference, newValue, position);
		return change.addIdsAndMakeCreateIfNecessary(idManager)
	}
	
	private def handleRemoveReference(EObject affectedEObject, EReference affectedReference, EObject oldValue, int position, EChangeIdManager idManager) {
		val change = TypeInferringAtomicEChangeFactory.instance.
							createRemoveReferenceChange(affectedEObject, affectedReference, oldValue, position);
		return change.addIdsAndMakeDeleteIfNecessary(change.affectedFeature.containment, idManager)
	}

	private def Iterable<EChange> addIdsAndMakeCreateIfNecessary(EObjectAddedEChange<?> change,
		EChangeIdManager idManager) {
		val changes = <EChange>newArrayList();
		val created = idManager.isCreateChange(change)
		if (created) {
			val createChange = TypeInferringAtomicEChangeFactory.instance.createCreateEObjectChange(change.newValue)
			idManager.setOrGenerateIds(createChange);
			changes.add(createChange);
		}
		idManager.setOrGenerateIds(change);
		changes.add(change)
		if (created) {
			changes += recursiveAddition(change.newValue, idManager);
		}
		return changes;
	}

	private def Iterable<EChange> addIdsAndMakeDeleteIfNecessary(EObjectSubtractedEChange<?> change,
		boolean containment, EChangeIdManager idManager) {
		val changes = <EChange>newArrayList();
		changes += change;
		idManager.setOrGenerateIds(change);
		val deleted = change.oldValue !== null && containment;
		if (deleted) {
			val deleteChange = TypeInferringAtomicEChangeFactory.instance.createDeleteEObjectChange(change.oldValue);
			idManager.setOrGenerateIds(deleteChange);
			deleteChange.consequentialRemoveChanges += recursiveRemoval(change.oldValue, idManager);
			changes += deleteChange;
		}
		return changes;
	}

	def handleResourceChange(NotificationInfo info, EChangeIdManager idManager) {
		val changes = <EChange>newArrayList();
		switch (info.getEventType()) {
			case Notification.ADD: {
				val change = TypeInferringAtomicEChangeFactory.instance.createInsertRootChange(
					info.newModelElementValue, info.notifier as Resource, info.position)
				changes += change.addIdsAndMakeCreateIfNecessary(idManager)
			}
			case Notification.ADD_MANY: {
				// Not supported
			}
			case Notification.REMOVE: {
				val change = TypeInferringAtomicEChangeFactory.instance.createRemoveRootChange(
					info.oldModelElementValue, info.notifier as Resource, info.position);
				changes += change.addIdsAndMakeDeleteIfNecessary(true, idManager);
			}
			case Notification.REMOVE_MANY: {
				// Not supported
			}
		}
		return changes;
	}

	// /**
	// * Creates a multi reference operation based on the given information.
	// *
	// * @param collection
	// *            the collection the <code>modelElement</code> is contained in
	// * @param modelElement
	// *            the model element holding the reference
	// * @param reference
	// *            the actual reference
	// * @param referencedElements
	// *            the elements referenced by the reference
	// * @param isAdd
	// *            whether any referenced model elements were added to the <code>collection</code>
	// * @param position
	// *            the index of the model element within the <code>referenceElements</code> affected by
	// *            the generated operation
	// * @return a multi reference operation
	// */
	// public static MultiReferenceOperation createMultiReferenceOperation(IdEObjectCollectionImpl collection,
	// EObject modelElement, EReference reference, List<EObject> referencedElements, boolean isAdd, int position) {
	// final MultiReferenceOperation op = OperationsFactory.eINSTANCE.createMultiReferenceOperation();
	// setCommonValues(collection, op, modelElement);
	// setBidirectionalAndContainmentInfo(op, reference);
	// op.setFeatureName(reference.getName());
	// op.setAdd(isAdd);
	// op.setIndex(position);
	// final List<ModelElementId> referencedModelElements = op.getReferencedModelElements();
	//
	// for (final EObject valueElement : referencedElements) {
	// ModelElementId id = collection.getModelElementId(valueElement);
	// if (id == null) {
	// id = collection.getDeletedModelElementId(valueElement);
	// }
	// if (id != null) {
	// referencedModelElements.add(id);
	// } else if (ModelUtil.getProject(valueElement) == collection) {
	// throw new IllegalStateException(
	// Messages.NotificationToOperationConverter_Element_Has_No_ID + valueElement);
	// }
	// // ignore value elements outside of the current project, they are
	// // not tracked
	// }
	// return op;
	//
	// }
	//
	// private AbstractOperation handleReferenceMove(NotificationInfo n) {
	//
	// final MultiReferenceMoveOperation op = OperationsFactory.eINSTANCE.createMultiReferenceMoveOperation();
	// setCommonValues(project, op, n.getNotifierModelElement());
	// op.setFeatureName(n.getReference().getName());
	// op.setReferencedModelElementId(project.getModelElementId(n.getNewModelElementValue()));
	// op.setNewIndex(n.getPosition());
	// op.setOldIndex((Integer) n.getOldValue());
	//
	// return op;
	// }
	//
	// private AbstractOperation handleAttributeMove(NotificationInfo n) {
	// final MultiAttributeMoveOperation operation = OperationsFactory.eINSTANCE.createMultiAttributeMoveOperation();
	// setCommonValues(project, operation, n.getNotifierModelElement());
	// operation.setFeatureName(n.getAttribute().getName());
	// operation.setNewIndex(n.getPosition());
	// operation.setOldIndex((Integer) n.getOldValue());
	// operation.setReferencedValue(n.getNewValue());
	// return operation;
	// }
	def private Iterable<EChange> handleSetAttribute(NotificationInfo n, EChangeIdManager idManager) {
		var List<EChange> changes = new ArrayList()
		if (n.getAttribute().isMany()) {
		// special handling for diagram layout changes
			if (n.getOldValue() !== null) {
				var RemoveEAttributeValue<EObject, Object> op=AttributeFactory.eINSTANCE.createRemoveEAttributeValue() 
				op.setOldValue(n.getOldValue()) 
				op.setAffectedFeature((n.getFeature() as EAttribute)) 
				op.setAffectedEObject((n.getNotifier() as EObject)) 
				op.setIndex(n.getPosition()) 
				changes.add(op) 
			} 
			if (n.getNewValue() !== null) {
				var InsertEAttributeValue<EObject, Object> op=AttributeFactory.eINSTANCE.createInsertEAttributeValue() 
				op.setAffectedFeature((n.getFeature() as EAttribute)) 
				op.setNewValue(n.getNewValue()) 
				op.setAffectedEObject((n.getNotifier() as EObject)) 
				op.setIndex(n.getPosition()) 
				changes.add(op) 
			}
		} else {
			var ReplaceSingleValuedEAttribute<EObject, Object> op = AttributeFactory.eINSTANCE.
				createReplaceSingleValuedEAttribute()
			op.setOldValue(n.getOldValue())
			op.setNewValue(n.getNewValue())
			op.setAffectedFeature((n.getFeature() as EAttribute))
			op.setAffectedEObject((n.getNotifier() as EObject))
			changes.add(op)
		}
		if (n.wasUnset()) {
			((changes.get(changes.size() - 1) as SubtractiveAttributeEChange<EObject, Object>)).setIsUnset(true)
		}
		changes.forEach[idManager.setOrGenerateIds(it)];
		return changes
	}

	// /**
	// * Creates a single reference operation based on the given information.
	// *
	// * @param collection
	// *            the collection the <code>modelElement</code> is contained in
	// * @param oldReference
	// *            the {@link ModelElementId} of the model element the reference was pointing to
	// * @param newReference
	// *            the {@link ModelElementId} of the model element the reference is now pointing to
	// * @param reference
	// *            the actual reference
	// * @param modelElement
	// *            the model element holding the reference
	// * @return a single reference operation
	// */
	// public static SingleReferenceOperation createSingleReferenceOperation(IdEObjectCollectionImpl collection,
	// ModelElementId oldReference, ModelElementId newReference, EReference reference, EObject modelElement) {
	//
	// final SingleReferenceOperation op = OperationsFactory.eINSTANCE.createSingleReferenceOperation();
	// setCommonValues(collection, op, modelElement);
	// op.setFeatureName(reference.getName());
	// setBidirectionalAndContainmentInfo(op, reference);
	//
	// op.setOldValue(oldReference);
	// op.setNewValue(newReference);
	//
	// return op;
	// }
	//
	private def List<EChange> handleSetReference(NotificationInfo n, EChangeIdManager idManager) {
		val oldValue = n.getOldModelElementValue()
		val newValue = n.getNewModelElementValue()

		if (!n.getReference().isMany()) {
			val change = ReferenceFactory.eINSTANCE.createReplaceSingleValuedEReference;
			change.affectedEObject = n.notifierModelElement
			change.oldValue = oldValue;
			change.newValue = newValue;
			change.affectedFeature = n.reference;

			if (n.wasUnset()) {
				change.isUnset = true;
			}

			val List<EChange> result = newArrayList;
			result += change.addIdsAndMakeCreateIfNecessary(idManager)
			// Remove first element as it is the original change again
			result += change.addIdsAndMakeDeleteIfNecessary(change.affectedFeature.containment, idManager).tail;
			return result;

		} else {
			val changes = newArrayList;
			if (oldValue !== null)
				changes += handleRemoveReference(n.notifierModelElement, n.reference, oldValue, n.position, idManager);
			if (newValue !== null)
				changes += handleInsertReference(n.notifierModelElement, n.reference, newValue, n.position, idManager);
			return changes;
		}

//			final MultiReferenceSetOperation setOperation = OperationsFactory.eINSTANCE
//				.createMultiReferenceSetOperation();
//			setCommonValues(project, setOperation, (EObject) n.getNotifier());
//			setOperation.setFeatureName(n.getReference().getName());
//			setBidirectionalAndContainmentInfo(setOperation, n.getReference());
//	
//			setOperation.setIndex(n.getPosition());
//	
//			if (n.getOldValue() != null) {
//				setOperation.setOldValue(oldModelElementId);
//			}
//	
//			if (n.getNewValue() != null) {
//				setOperation.setNewValue(newModelElementId);
//			}
//	
//			if (n.wasUnset()) {
//				setOperation.setUnset(UnsetType.WAS_UNSET);
//			}
//	
//			return setOperation;
	}

	//
	// // utility methods
	// private static void setCommonValues(IdEObjectCollectionImpl collection, AbstractOperation operation,
	// EObject modelElement) {
	// operation.setClientDate(new Date());
	// ModelElementId id = collection.getModelElementId(modelElement);
	// if (id == null) {
	// id = collection.getDeletedModelElementId(modelElement);
	// }
	// if (id == null) {
	// WorkspaceUtil.handleException(new IllegalStateException(
	// Messages.NotificationToOperationConverter_Element_Has_No_ID
	// + modelElement));
	// }
	// operation.setModelElementId(id);
	// }
	//
	// private static void setBidirectionalAndContainmentInfo(ReferenceOperation referenceOperation, EReference reference) {
	// if (reference.getEOpposite() != null) {
	// referenceOperation.setBidirectional(true);
	// referenceOperation.setOppositeFeatureName(reference.getEOpposite().getName());
	// } else {
	// referenceOperation.setBidirectional(false);
	// }
	// if (reference.isContainer()) {
	// referenceOperation.setContainmentType(ContainmentType.CONTAINER);
	// }
	// if (reference.isContainment()) {
	// referenceOperation.setContainmentType(ContainmentType.CONTAINMENT);
	// }
	// }
	//
	def private Iterable<EChange> handleUnsetAttribute(NotificationInfo n, List<EChange> previousChanges,
		EChangeIdManager idManager) {
		var Iterable<EChange> op = null
		if (!n.getAttribute().isMany()) {
			op = handleSetAttribute(n, idManager)
		} else {
			if (!(previousChanges.last instanceof SubtractiveAttributeEChange<?, ?>)) {
				throw new IllegalStateException
			}
			val lastChange = previousChanges.last as SubtractiveAttributeEChange<?, ?>
			lastChange.isUnset = true
			return Collections.emptyList()
		}
		return op
	}

	private def Iterable<EChange> handleUnsetReference(NotificationInfo n, List<EChange> previousChanges,
		EChangeIdManager idManager) {
		var Iterable<EChange> op = null
		if (!n.getReference().isMany()) {
			op = handleSetReference(n, idManager);
		} else {
			if (!(previousChanges.last instanceof SubtractiveReferenceEChange<?, ?>)) {
				throw new IllegalStateException
			}
			val lastChange = previousChanges.last as SubtractiveReferenceEChange<?, ?>
			lastChange.isUnset = true
			return Collections.emptyList()
		// op = (FeatureOperation) handleMultiReference(n);
		}
		// op.setUnset(UnsetType.IS_UNSET);
		return op;
	}

	def private Iterable<EChange> recursiveAddition(EObject eObject, EChangeIdManager idManager) {
		val result = <EChange>newArrayList
		recursivelyAddChangesForNonDefaultAttributesAndContainments(eObject, result)
		recursivelyAddChangesForNonDefaultReferences(eObject, result)
		for (change : result) {
			idManager.setOrGenerateIds(change);
		}
		return result;
	}

	def private Iterable<EChange> recursiveRemoval(EObject eObject, EChangeIdManager idManager) {
		val result = <EChange>newArrayList
		// if (RECORD_DELETE_RECURSIVELY) {
		recursivelyRemoveChangesForNonDefaultReferences(eObject, result)
		recursivelyRemoveChangesForNonDefaultAttributesAndContainments(eObject, result)
		// }
		for (change : result) {
			idManager.setOrGenerateIds(change);
		}
		return result;
	}

	def private void recursivelyAddChangesForNonDefaultAttributesAndContainments(EObject eObject,
		List<EChange> eChanges) {
		if (eObject.hasNonDefaultValue()) {
			val metaclass = eObject.eClass
			for (feature : metaclass.EAllStructuralFeatures.filter(EAttribute)) {
				if (eObject.hasChangeableUnderivedPersistedNotContainingNonDefaultValue(feature)) {
					val recursiveChanges = createAdditiveChangesForValue(eObject, feature);
					eChanges.addAll(recursiveChanges);
				}
			}

			for (feature : metaclass.EAllStructuralFeatures.filter(EReference).filter[isContainment]) {
				if (eObject.hasChangeableUnderivedPersistedNotContainingNonDefaultValue(feature)) {
					val recursiveChanges = createAdditiveCreateChangesForValue(eObject, feature);
					eChanges.addAll(recursiveChanges);

					for (element : eObject.getReferencedElements(feature)) {
						recursivelyAddChangesForNonDefaultAttributesAndContainments(element, eChanges);
					}
				}
			}
		}
	}

	def private void recursivelyRemoveChangesForNonDefaultAttributesAndContainments(EObject eObject,
		List<EChange> eChanges) {
		if (eObject.hasNonDefaultValue()) {
			val metaclass = eObject.eClass
			for (feature : metaclass.EAllStructuralFeatures.filter(EAttribute)) {
				if (eObject.hasChangeableUnderivedPersistedNotContainingNonDefaultValue(feature)) {
					val recursiveChanges = createSubtractiveChangesForValue(eObject, feature);
					eChanges.addAll(recursiveChanges);
				}
			}

			for (feature : metaclass.EAllStructuralFeatures.filter(EReference).filter[isContainment]) {
				if (eObject.hasChangeableUnderivedPersistedNotContainingNonDefaultValue(feature)) {
					val recursiveChanges = createSubtractiveChangesForValue(eObject, feature);
					eChanges.addAll(recursiveChanges);

					for (element : eObject.getReferencedElements(feature)) {
						recursivelyRemoveChangesForNonDefaultAttributesAndContainments(element, eChanges);
					}
				}
			}
		}
	}

	def static private List<? extends EObject> getReferencedElements(EObject eObject, EReference reference) {
		return if (reference.many) eObject.eGet(reference) as List<? extends EObject> else #[
			eObject.eGet(reference) as EObject];
	}

	def private void recursivelyAddChangesForNonDefaultReferences(EObject eObject, List<EChange> eChanges) {
		if (eObject.hasNonDefaultValue()) {
			val metaclass = eObject.eClass
			for (feature : metaclass.EAllStructuralFeatures.filter(EReference).filter[!containment]) {
				if (eObject.hasChangeableUnderivedPersistedNotContainingNonDefaultValue(feature)) {
					val recursiveChanges = createAdditiveChangesForValue(eObject, feature);
					eChanges.addAll(recursiveChanges);
				}
			}
			for (feature : metaclass.EAllStructuralFeatures.filter(EReference).filter[containment]) {
				if (eObject.hasChangeableUnderivedPersistedNotContainingNonDefaultValue(feature)) {
					for (element : eObject.getReferencedElements(feature)) {
						recursivelyAddChangesForNonDefaultReferences(element, eChanges);
					}
				}
			}
		}
	}

	def private void recursivelyRemoveChangesForNonDefaultReferences(EObject eObject, List<EChange> eChanges) {
		if (eObject.hasNonDefaultValue()) {
			val metaclass = eObject.eClass
			for (feature : metaclass.EAllStructuralFeatures.filter(EReference).filter[!containment]) {
				if (eObject.hasChangeableUnderivedPersistedNotContainingNonDefaultValue(feature)) {
					val recursiveChanges = createSubtractiveChangesForValue(eObject, feature);
					eChanges.addAll(recursiveChanges);
				}
			}
			for (feature : metaclass.EAllStructuralFeatures.filter(EReference).filter[containment]) {
				if (eObject.hasChangeableUnderivedPersistedNotContainingNonDefaultValue(feature)) {
					for (element : eObject.getReferencedElements(feature)) {
						recursivelyRemoveChangesForNonDefaultReferences(element, eChanges);
					}
				}
			}
		}
	}
}
