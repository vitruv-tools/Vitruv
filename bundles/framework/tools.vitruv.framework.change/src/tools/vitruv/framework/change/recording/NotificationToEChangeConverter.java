package tools.vitruv.framework.change.recording;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;

import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.compound.CompoundFactory;
import tools.vitruv.framework.change.echange.compound.ExplicitUnsetEAttribute;
import tools.vitruv.framework.change.echange.feature.attribute.AttributeFactory;
import tools.vitruv.framework.change.echange.feature.attribute.InsertEAttributeValue;
import tools.vitruv.framework.change.echange.feature.attribute.RemoveEAttributeValue;
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute;
import tools.vitruv.framework.change.echange.feature.attribute.SubtractiveAttributeEChange;

/**
 * Converts an EMF notification to an {@link EChange}.
 *
 * @author Heiko Klare
 */
public final class NotificationToEChangeConverter {
	/**
	 * Converts given notification to an Echange. May return null if the
	 * notification signifies a no-op.
	 *
	 * @param n
	 *            the notification to convert
	 * @return the EChange or null
	 */
	// BEGIN COMPLEX CODE
	public Iterable<EChange> convert(NotificationInfo n) {

		if (n.isTouch() || n.isTransient()) {
			return null;
		}

		switch (n.getEventType()) {

		case Notification.SET:
			if (n.isAttributeNotification()) {
				return handleSetAttribute(n);
			}
//			return handleSetReference(n);

		case Notification.UNSET:
			if (n.isAttributeNotification()) {
				return handleUnsetAttribute(n);
			}
//			return handleUnsetReference(n);

		case Notification.ADD:
			if (n.isAttributeNotification()) {
				return handleMultiAttribute(n);
			}
//			return handleMultiReference(n);
//
		case Notification.ADD_MANY:
			if (n.isAttributeNotification()) {
				return handleMultiAttribute(n);
			}
//			return handleMultiReference(n);
//
		case Notification.REMOVE:
			if (n.isAttributeNotification()) {
				return handleMultiAttribute(n);
			}
//			return handleMultiReference(n);
//
		case Notification.REMOVE_MANY:
			if (n.isAttributeNotification()) {
				return handleMultiAttribute(n);
			}
//			return handleMultiReference(n);
//
//		case Notification.MOVE:
//			if (n.isAttributeNotification()) {
//				return handleAttributeMove(n);
//			}
//			return handleReferenceMove(n);

		default:
			return null;
		}
	}

	// END COMPLEX CODE

	@SuppressWarnings("unchecked")
	private Iterable<EChange> handleMultiAttribute(NotificationInfo n) {
		List<EChange> changes = new ArrayList<>();
		switch (n.getEventType()) {

		

	case Notification.ADD:
		InsertEAttributeValue<EObject, Object> op = AttributeFactory.eINSTANCE.createInsertEAttributeValue();
		op.setAffectedFeature((EAttribute) n.getFeature());
		op.setNewValue(n.getNewValue());
		op.setAffectedEObject((EObject)n.getNotifier());
		op.setIndex(n.getPosition());
		changes.add(op);
		break;
	case Notification.ADD_MANY:
		List<Object> list = (List<Object>) n.getNewValue();
		for (int i = 0; i < list.size(); i++) {
			InsertEAttributeValue<EObject, Object> operation = AttributeFactory.eINSTANCE.createInsertEAttributeValue();
			operation.setAffectedFeature((EAttribute) n.getFeature());
			operation.setNewValue(list.get(i));
			operation.setAffectedEObject((EObject)n.getNotifier());
			operation.setIndex(n.getInitialIndex() + i);
			changes.add(operation);
		}
		break;
	case Notification.REMOVE:
		RemoveEAttributeValue<EObject, Object> operation = AttributeFactory.eINSTANCE.createRemoveEAttributeValue();
		operation.setOldValue(n.getOldValue());
		operation.setAffectedFeature((EAttribute) n.getFeature());
		operation.setAffectedEObject((EObject)n.getNotifier());
		operation.setIndex(n.getPosition());
		changes.add(operation);
		break;
	case Notification.REMOVE_MANY:
		List<Object> list2 = (List<Object>) n.getOldValue();
		if (n.getNewValue() == null) {			
			for (int i = list2.size() -1 ; i >= 0; i--) {
				RemoveEAttributeValue<EObject, Object> ope = AttributeFactory.eINSTANCE.createRemoveEAttributeValue();
				ope.setOldValue(list2.get(i));
				ope.setAffectedFeature((EAttribute) n.getFeature());
				ope.setAffectedEObject((EObject)n.getNotifier());
				ope.setIndex(n.getInitialIndex() + i);
				changes.add(ope);
			}
		} 
		//else {
//			for (final int value : (int[]) n.getNewValue()) {
//				operation.getIndexes().add(value);
//			}
//		}
		//break;
	default:
		break;
	}
	
		
		return changes;
	}

//	@SuppressWarnings("unchecked")
//	private AbstractOperation handleMultiReference(NotificationInfo n) {
//
//		List<EObject> list = new ArrayList<EObject>();
//
//		switch (n.getEventType()) {
//		case Notification.ADD:
//			list.add(n.getNewModelElementValue());
//			break;
//		case Notification.ADD_MANY:
//			list = (List<EObject>) n.getNewValue();
//			break;
//		case Notification.REMOVE:
//			list.add(n.getOldModelElementValue());
//			break;
//		case Notification.REMOVE_MANY:
//			list = (List<EObject>) n.getOldValue();
//			break;
//		default:
//			break;
//		}
//
//		final boolean isAdd = n.isAddEvent() || n.isAddManyEvent();
//		final MultiReferenceOperation multiRefOp = createMultiReferenceOperation(project, n.getNotifierModelElement(),
//			n.getReference(), list, isAdd,
//			n.getPosition());
//
//		if (n.wasUnset()) {
//			multiRefOp.setUnset(UnsetType.WAS_UNSET);
//		}
//
//		return multiRefOp;
//	}

//	/**
//	 * Creates a multi reference operation based on the given information.
//	 *
//	 * @param collection
//	 *            the collection the <code>modelElement</code> is contained in
//	 * @param modelElement
//	 *            the model element holding the reference
//	 * @param reference
//	 *            the actual reference
//	 * @param referencedElements
//	 *            the elements referenced by the reference
//	 * @param isAdd
//	 *            whether any referenced model elements were added to the <code>collection</code>
//	 * @param position
//	 *            the index of the model element within the <code>referenceElements</code> affected by
//	 *            the generated operation
//	 * @return a multi reference operation
//	 */
//	public static MultiReferenceOperation createMultiReferenceOperation(IdEObjectCollectionImpl collection,
//		EObject modelElement, EReference reference, List<EObject> referencedElements, boolean isAdd, int position) {
//		final MultiReferenceOperation op = OperationsFactory.eINSTANCE.createMultiReferenceOperation();
//		setCommonValues(collection, op, modelElement);
//		setBidirectionalAndContainmentInfo(op, reference);
//		op.setFeatureName(reference.getName());
//		op.setAdd(isAdd);
//		op.setIndex(position);
//		final List<ModelElementId> referencedModelElements = op.getReferencedModelElements();
//
//		for (final EObject valueElement : referencedElements) {
//			ModelElementId id = collection.getModelElementId(valueElement);
//			if (id == null) {
//				id = collection.getDeletedModelElementId(valueElement);
//			}
//			if (id != null) {
//				referencedModelElements.add(id);
//			} else if (ModelUtil.getProject(valueElement) == collection) {
//				throw new IllegalStateException(
//					Messages.NotificationToOperationConverter_Element_Has_No_ID + valueElement);
//			}
//			// ignore value elements outside of the current project, they are
//			// not tracked
//		}
//		return op;
//
//	}
//
//	private AbstractOperation handleReferenceMove(NotificationInfo n) {
//
//		final MultiReferenceMoveOperation op = OperationsFactory.eINSTANCE.createMultiReferenceMoveOperation();
//		setCommonValues(project, op, n.getNotifierModelElement());
//		op.setFeatureName(n.getReference().getName());
//		op.setReferencedModelElementId(project.getModelElementId(n.getNewModelElementValue()));
//		op.setNewIndex(n.getPosition());
//		op.setOldIndex((Integer) n.getOldValue());
//
//		return op;
//	}
//
//	private AbstractOperation handleAttributeMove(NotificationInfo n) {
//		final MultiAttributeMoveOperation operation = OperationsFactory.eINSTANCE.createMultiAttributeMoveOperation();
//		setCommonValues(project, operation, n.getNotifierModelElement());
//		operation.setFeatureName(n.getAttribute().getName());
//		operation.setNewIndex(n.getPosition());
//		operation.setOldIndex((Integer) n.getOldValue());
//		operation.setReferencedValue(n.getNewValue());
//		return operation;
//	}

	private Iterable<EChange> handleSetAttribute(NotificationInfo n) {
		List<EChange> changes = new ArrayList<>();
		if (n.getAttribute().isMany()) {
			// special handling for diagram layout changes
			if (n.getOldValue() != null) {
				RemoveEAttributeValue<EObject, Object> op = AttributeFactory.eINSTANCE.createRemoveEAttributeValue();
				op.setOldValue(n.getOldValue());
				op.setAffectedFeature((EAttribute) n.getFeature());
				op.setAffectedEObject((EObject)n.getNotifier());
				op.setIndex(n.getPosition());
				changes.add(op);
			} else if (n.getNewValue() != null) {
				InsertEAttributeValue<EObject, Object> op = AttributeFactory.eINSTANCE.createInsertEAttributeValue();
				op.setAffectedFeature((EAttribute) n.getFeature());
				op.setNewValue(n.getOldValue());
				op.setAffectedEObject((EObject)n.getNotifier());
				op.setIndex(n.getPosition());
				changes.add(op);
			}
		} else {
			ReplaceSingleValuedEAttribute<EObject, Object> op  = AttributeFactory.eINSTANCE.createReplaceSingleValuedEAttribute();
			op.setOldValue(n.getOldValue());
			op.setNewValue(n.getNewValue());
			op.setAffectedFeature((EAttribute) n.getFeature());
			op.setAffectedEObject((EObject)n.getNotifier());
			changes.add(op);
		}
		
		if (n.wasUnset()) {
			ExplicitUnsetEAttribute<EObject, Object> unsetChange = CompoundFactory.eINSTANCE.createExplicitUnsetEAttribute();
			for (EChange change : changes) {
				if (change instanceof SubtractiveAttributeEChange<?, ?>) {
					unsetChange.getSubtractiveChanges().add((SubtractiveAttributeEChange<EObject, Object>) change);		
				} else {
					throw new IllegalStateException();
				}
			}
			return Collections.singletonList(unsetChange);
		}
		
		return changes;
	}

//	/**
//	 * Creates a single reference operation based on the given information.
//	 *
//	 * @param collection
//	 *            the collection the <code>modelElement</code> is contained in
//	 * @param oldReference
//	 *            the {@link ModelElementId} of the model element the reference was pointing to
//	 * @param newReference
//	 *            the {@link ModelElementId} of the model element the reference is now pointing to
//	 * @param reference
//	 *            the actual reference
//	 * @param modelElement
//	 *            the model element holding the reference
//	 * @return a single reference operation
//	 */
//	public static SingleReferenceOperation createSingleReferenceOperation(IdEObjectCollectionImpl collection,
//		ModelElementId oldReference, ModelElementId newReference, EReference reference, EObject modelElement) {
//
//		final SingleReferenceOperation op = OperationsFactory.eINSTANCE.createSingleReferenceOperation();
//		setCommonValues(collection, op, modelElement);
//		op.setFeatureName(reference.getName());
//		setBidirectionalAndContainmentInfo(op, reference);
//
//		op.setOldValue(oldReference);
//		op.setNewValue(newReference);
//
//		return op;
//	}
//
//	private AbstractOperation handleSetReference(NotificationInfo n) {
//
//		ModelElementId oldModelElementId = project.getModelElementId(n.getOldModelElementValue());
//		ModelElementId newModelElementId = project.getModelElementId(n.getNewModelElementValue());
//
//		if (oldModelElementId == null) {
//			oldModelElementId = ((ProjectImpl) project).getDeletedModelElementId(n.getOldModelElementValue());
//		}
//
//		if (newModelElementId == null) {
//			newModelElementId = ((ProjectImpl) project).getDeletedModelElementId(n.getNewModelElementValue());
//		}
//
//		if (!n.getReference().isMany()) {
//			final SingleReferenceOperation singleRefOperation = createSingleReferenceOperation(project,
//				oldModelElementId,
//				newModelElementId, n.getReference(),
//				n.getNotifierModelElement());
//
//			if (n.wasUnset()) {
//				singleRefOperation.setUnset(UnsetType.WAS_UNSET);
//			}
//
//			return singleRefOperation;
//
//		}
//		final MultiReferenceSetOperation setOperation = OperationsFactory.eINSTANCE
//			.createMultiReferenceSetOperation();
//		setCommonValues(project, setOperation, (EObject) n.getNotifier());
//		setOperation.setFeatureName(n.getReference().getName());
//		setBidirectionalAndContainmentInfo(setOperation, n.getReference());
//
//		setOperation.setIndex(n.getPosition());
//
//		if (n.getOldValue() != null) {
//			setOperation.setOldValue(oldModelElementId);
//		}
//
//		if (n.getNewValue() != null) {
//			setOperation.setNewValue(newModelElementId);
//		}
//
//		if (n.wasUnset()) {
//			setOperation.setUnset(UnsetType.WAS_UNSET);
//		}
//
//		return setOperation;
//	}
//
//	// utility methods
//	private static void setCommonValues(IdEObjectCollectionImpl collection, AbstractOperation operation,
//		EObject modelElement) {
//		operation.setClientDate(new Date());
//		ModelElementId id = collection.getModelElementId(modelElement);
//		if (id == null) {
//			id = collection.getDeletedModelElementId(modelElement);
//		}
//		if (id == null) {
//			WorkspaceUtil.handleException(new IllegalStateException(
//				Messages.NotificationToOperationConverter_Element_Has_No_ID
//					+ modelElement));
//		}
//		operation.setModelElementId(id);
//	}
//
//	private static void setBidirectionalAndContainmentInfo(ReferenceOperation referenceOperation, EReference reference) {
//		if (reference.getEOpposite() != null) {
//			referenceOperation.setBidirectional(true);
//			referenceOperation.setOppositeFeatureName(reference.getEOpposite().getName());
//		} else {
//			referenceOperation.setBidirectional(false);
//		}
//		if (reference.isContainer()) {
//			referenceOperation.setContainmentType(ContainmentType.CONTAINER);
//		}
//		if (reference.isContainment()) {
//			referenceOperation.setContainmentType(ContainmentType.CONTAINMENT);
//		}
//	}
//
	private Iterable<EChange> handleUnsetAttribute(NotificationInfo n) {
		Iterable<EChange> op = null;
		if(!n.getAttribute().isMany())  
			handleSetAttribute(n); 
		else {
			ExplicitUnsetEAttribute<EObject, Object> unsetChange = CompoundFactory.eINSTANCE.createExplicitUnsetEAttribute();
			return Collections.singletonList(unsetChange);
		}
		return op;
	}

//	private AbstractOperation handleUnsetReference(NotificationInfo n) {
//		FeatureOperation op;
//		if (!n.getReference().isMany()) {
//			op = (FeatureOperation) handleSetReference(n);
//		} else {
//			op = (FeatureOperation) handleMultiReference(n);
//		}
//		op.setUnset(UnsetType.IS_UNSET);
//		return op;
//	}

}
