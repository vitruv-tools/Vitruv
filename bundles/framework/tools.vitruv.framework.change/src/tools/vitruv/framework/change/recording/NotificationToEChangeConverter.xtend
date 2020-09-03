package tools.vitruv.framework.change.recording

import java.util.ArrayList
import java.util.List
import org.eclipse.emf.common.notify.Notification
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.feature.attribute.AttributeFactory
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute
import tools.vitruv.framework.change.echange.TypeInferringAtomicEChangeFactory
import static extension tools.vitruv.framework.change.preparation.EMFModelChangeTransformationUtil.*
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.framework.change.echange.eobject.EObjectAddedEChange
import tools.vitruv.framework.change.echange.eobject.EObjectSubtractedEChange
import tools.vitruv.framework.change.echange.EChangeIdManager
import org.eclipse.emf.ecore.EStructuralFeature

/** 
 * Converts an EMF notification to an {@link EChange}.
 * @author Heiko Klare
 */
final class NotificationToEChangeConverter {
	EChangeIdManager eChangeIdManager;

	new(EChangeIdManager eChangeeChangeIdManager) {
		this.eChangeIdManager = eChangeeChangeIdManager;
	}

	/** 
	 * Converts the given notification to a list of {@link EChange}s.
	 * @param n the notification to convert
	 * @return the  {@link Iterable} of {@link EChange}s
	 */
	def Iterable<EChange> convert(NotificationInfo notification, List<EChange> previousChanges) {
		if (notification.isTouch() || notification.isTransient()) {
			return #[]
		}

		if (notification.oldValue == notification.newValue) {
			return #[]
		}

		switch (notification.getEventType()) {
			case Notification.SET: {
				if (notification.isAttributeNotification()) {
					return handleSetAttribute(notification)
				} else if (notification.isReferenceNotification) {
					return handleSetReference(notification);
				}
			}
			case Notification.UNSET: {
				if (notification.isAttributeNotification()) {
					return handleUnsetAttribute(notification, previousChanges)
				} else if (notification.isReferenceNotification) {
					return handleUnsetReference(notification, previousChanges);
				}
			}
			case Notification.ADD: {
				if (notification.isAttributeNotification()) {
					return handleMultiAttribute(notification)
				} else if (notification.isReferenceNotification) {
					return handleMultiReference(notification);
				}
			}
			case Notification.ADD_MANY: {
				if (notification.isAttributeNotification()) {
					return handleMultiAttribute(notification)
				} else if (notification.isReferenceNotification) {
					return handleMultiReference(notification);
				}
			}
			case Notification.REMOVE: {
				if (notification.isAttributeNotification()) {
					return handleMultiAttribute(notification)
				} else if (notification.isReferenceNotification) {
					return handleMultiReference(notification);
				}
			}
			case Notification.REMOVE_MANY: {
				if (notification.isAttributeNotification()) {
					return handleMultiAttribute(notification)
				} else if (notification.isReferenceNotification) {
					return handleMultiReference(notification);
				}
			}
		//
		// case Notification.MOVE:
		// if (n.isAttributeNotification()) {
		// return handleAttributeMove(n);
		// }
		// return handleReferenceMove(n);
		}
		if (notification.notifier instanceof Resource) {
			return handleResourceChange(notification)
		}
		return #[]
	}
		
	def createDeleteChange(EObjectSubtractedEChange<?> change) {
		val deleteChange = TypeInferringAtomicEChangeFactory.instance.createDeleteEObjectChange(change.oldValue);
		eChangeIdManager.setOrGenerateIds(deleteChange);
		deleteChange.consequentialRemoveChanges += recursiveRemoval(change.oldValue);
		return deleteChange;
	}	

	def private Iterable<EChange> handleMultiAttribute(NotificationInfo n) {
		var List<EChange> changes = new ArrayList()
		val affectedEObject = n.notifierModelElement;
		val affectedFeature = n.attribute;
		switch (n.getEventType()) {
			case Notification.ADD: {
				changes += handleInsertAttribute(affectedEObject, affectedFeature, n.newValue, n.position);
			}
			case Notification.ADD_MANY: {
				var List<Object> list = (n.getNewValue() as List<Object>)
				for (var int i = 0; i < list.size(); i++) {
					changes += handleInsertAttribute(affectedEObject, affectedFeature, list.get(i), n.initialIndex + i);
				}
			}
			case Notification.REMOVE: {
				changes += handleRemoveAttribute(affectedEObject, affectedFeature, n.oldValue, n.position);
				if (n.wasUnset) {
					changes += TypeInferringAtomicEChangeFactory.instance.createUnsetFeatureChange(affectedEObject, affectedFeature);
				}
			}
			case Notification.REMOVE_MANY: {
				var List<Object> list = (n.getOldValue() as List<Object>)
				// TODO HK Is that check necessary?
				if (n.getNewValue() === null) {
					for (var int i = list.size() - 1; i >= 0; i--) {
						changes +=
							handleRemoveAttribute(affectedEObject, affectedFeature, list.get(i), n.initialIndex + i);
					}
				}
				if (n.wasUnset) {
					changes += TypeInferringAtomicEChangeFactory.instance.createUnsetFeatureChange(affectedEObject, affectedFeature);
				}
			}
		}
		return changes
	}

	def private Iterable<EChange> handleMultiReference(NotificationInfo n) {
		val changes = <EChange>newArrayList();

		val affectedEObject = n.notifierModelElement;
		val affectedReference = n.reference;
		switch (n.getEventType()) {
			case Notification.ADD: {
				changes +=
					handleInsertReference(affectedEObject, affectedReference, n.newModelElementValue, n.position);
			}
			case Notification.ADD_MANY: {
				var List<EObject> list = (n.getNewValue() as List<EObject>)
				for (var int i = 0; i < list.size(); i++) {
					changes +=
						handleInsertReference(affectedEObject, affectedReference, list.get(i), n.initialIndex + i);
				}
			}
			case Notification.REMOVE: {
				changes +=
					handleRemoveReference(affectedEObject, affectedReference, n.oldModelElementValue, n.position);
				if (n.wasUnset) {
					changes += TypeInferringAtomicEChangeFactory.instance.createUnsetFeatureChange(affectedEObject, affectedReference);
				}
			}
			case Notification.REMOVE_MANY: {
				var List<EObject> list = (n.getOldValue() as List<EObject>)
				if (n.getNewValue() === null) {
					for (var int i = list.size() - 1; i >= 0; i--) {
						changes +=
							handleRemoveReference(affectedEObject, affectedReference, list.get(i), n.initialIndex + i);
					}
				}
				if (n.wasUnset) {
					changes += TypeInferringAtomicEChangeFactory.instance.createUnsetFeatureChange(affectedEObject, affectedReference);
				}
			}
		}
		return changes;
	}

	private def handleInsertAttribute(EObject affectedEObject, EAttribute affectedReference, Object newValue,
		int position) {
		val change = TypeInferringAtomicEChangeFactory.instance.createInsertAttributeChange(affectedEObject,
			affectedReference, position, newValue);
		eChangeIdManager.setOrGenerateIds(change);
		return change;
	}

	private def handleRemoveAttribute(EObject affectedEObject, EAttribute affectedReference, Object oldValue,
		int position) {
		val change = TypeInferringAtomicEChangeFactory.instance.createRemoveAttributeChange(affectedEObject,
			affectedReference, position, oldValue);
		eChangeIdManager.setOrGenerateIds(change);
		return change;
	}

	private def Iterable<EChange> handleInsertReference(EObject affectedEObject, EReference affectedReference,
		EObject newValue, int position) {
		val change = TypeInferringAtomicEChangeFactory.instance.createInsertReferenceChange(affectedEObject,
			affectedReference, newValue, position);
		val beforeAndAfterCreateChanges = change.getCreateBeforeAndAfterChangesIfNecessary;
		eChangeIdManager.setOrGenerateIds(change);
		return beforeAndAfterCreateChanges.key + #[change] + beforeAndAfterCreateChanges.value
	}

	private def Iterable<EChange> handleRemoveReference(EObject affectedEObject, EReference affectedReference, EObject oldValue,
		int position) {
		val change = TypeInferringAtomicEChangeFactory.instance.createRemoveReferenceChange(affectedEObject,
			affectedReference, oldValue, position);
		eChangeIdManager.setOrGenerateIds(change);
		return #[change]
	}

	private def Iterable<EChange> handleReplaceReference(EObject affectedEObject, EReference reference, EObject oldValue,
		EObject newValue, boolean unset) {
		val change = TypeInferringAtomicEChangeFactory.instance.
			createReplaceSingleReferenceChange(affectedEObject, reference, oldValue, newValue)
		change.isUnset = unset;

		val List<EChange> result = newArrayList;
		val beforeAndAfterCreateChanges = change.getCreateBeforeAndAfterChangesIfNecessary;
		eChangeIdManager.setOrGenerateIds(change);
		result += beforeAndAfterCreateChanges.key;
		result += change;
		result += beforeAndAfterCreateChanges.value;
		return result;
	}

	private def Pair<Iterable<EChange>, Iterable<EChange>> getCreateBeforeAndAfterChangesIfNecessary(
		EObjectAddedEChange<?> change) {
		val beforeChanges = <EChange>newArrayList();
		val afterChanges = <EChange>newArrayList();
		val created = eChangeIdManager.isCreateChange(change)
		if (created) {
			val createChange = TypeInferringAtomicEChangeFactory.instance.createCreateEObjectChange(change.newValue)
			eChangeIdManager.setOrGenerateIds(createChange);
			beforeChanges.add(createChange);
		}
		if (created) {
			afterChanges += recursiveAddition(change.newValue);
		}
		return new Pair(beforeChanges, afterChanges);
	}

	private def handleResourceChange(NotificationInfo notification) {
		if (notification.getFeatureID(Resource) !== Resource.RESOURCE__CONTENTS) {
			return #[]
		}

		val changes = <EChange>newArrayList();
		val resource = notification.notifier as Resource
		switch (notification.getEventType()) {
			case Notification.ADD: {
				changes += handleInsertRootChange(resource, notification.newModelElementValue, notification.position)
			}
			case Notification.ADD_MANY: {
				var List<EObject> list = (notification.getNewValue() as List<EObject>)
				for (var int i = 0; i < list.size(); i++) {
					changes += handleInsertRootChange(resource, list.get(i), notification.initialIndex + i)
				}
			}
			case Notification.REMOVE: {
				changes += handleRemoveRootChange(resource, notification.oldModelElementValue, notification.position)
			}
			case Notification.REMOVE_MANY: {
				var List<EObject> list = (notification.getOldValue() as List<EObject>)
				for (var int i = list.size() - 1; i >= 0; i--) {
					changes += handleRemoveRootChange(resource, list.get(i), notification.initialIndex + i)
				}
			}
		}
		return changes;
	}

	private def handleInsertRootChange(Resource resource, EObject rootElement, int position) {
		val change = TypeInferringAtomicEChangeFactory.instance.createInsertRootChange(rootElement, resource, position)
		val beforeAndAfterCreateChanges = change.getCreateBeforeAndAfterChangesIfNecessary;
		eChangeIdManager.setOrGenerateIds(change);
		return beforeAndAfterCreateChanges.key + #[change] + beforeAndAfterCreateChanges.value
	}

	private def handleRemoveRootChange(Resource resource, EObject oldRootElement, int position) {
		val change = TypeInferringAtomicEChangeFactory.instance.createRemoveRootChange(oldRootElement, resource,
			position);
		eChangeIdManager.setOrGenerateIds(change);
		return #[change];
	}

	def private Iterable<EChange> handleSetAttribute(NotificationInfo n) {
		val List<EChange> changes = new ArrayList();
		val affectedEObject = n.notifierModelElement;
		val affectedFeature = n.attribute;
		if (n.getAttribute().isMany()) {
			if (n.oldValue !== null) {
				changes += handleRemoveAttribute(affectedEObject, affectedFeature, n.oldValue, n.position);
				if (n.wasUnset) {
					changes += TypeInferringAtomicEChangeFactory.instance.createUnsetFeatureChange(affectedEObject, affectedFeature);
				}
			}
			if (n.newValue !== null) {
				changes += handleInsertAttribute(affectedEObject, affectedFeature, n.newValue, n.position);
			}
		} else {
			var ReplaceSingleValuedEAttribute<EObject, Object> op = AttributeFactory.eINSTANCE.
				createReplaceSingleValuedEAttribute()
			op.setOldValue(n.getOldValue())
			op.setNewValue(n.getNewValue())
			op.setAffectedFeature((n.getFeature() as EAttribute))
			op.setAffectedEObject((n.getNotifier() as EObject))
			if (n.wasUnset) {
				op.isUnset = true;
			}
			eChangeIdManager.setOrGenerateIds(op);
			changes.add(op)
		}
		return changes
	}

	private def Iterable<EChange> handleSetReference(NotificationInfo n) {
		val oldValue = n.getOldModelElementValue()
		val newValue = n.getNewModelElementValue()

		if (!n.getReference().isMany()) {
			return handleReplaceReference(n.notifierModelElement, n.reference, oldValue, newValue, n.wasUnset);
		} else {
			val changes = newArrayList;
			if (oldValue !== null)
				changes += handleRemoveReference(n.notifierModelElement, n.reference, oldValue, n.position);
				if (n.wasUnset) {
					changes += TypeInferringAtomicEChangeFactory.instance.createUnsetFeatureChange(n.notifierModelElement, n.reference);
				}
			if (newValue !== null)
				changes += handleInsertReference(n.notifierModelElement, n.reference, newValue, n.position);
			return changes;
		}
	}

	def private Iterable<EChange> handleUnsetAttribute(NotificationInfo n, List<EChange> previousChanges) {
		var Iterable<EChange> op = null
		if (!n.getAttribute().isMany()) {
			op = handleSetAttribute(n)
		} else {
			val change = TypeInferringAtomicEChangeFactory.instance.createUnsetFeatureChange(n.notifierModelElement, n.feature as EStructuralFeature);
			eChangeIdManager.setOrGenerateIds(change);
			op = #[change];
		}
		return op
	}

	private def Iterable<EChange> handleUnsetReference(NotificationInfo n, List<EChange> previousChanges) {
		var Iterable<EChange> op = null
		if (!n.getReference().isMany()) {
			op = handleSetReference(n);
		} else {
			val change = TypeInferringAtomicEChangeFactory.instance.createUnsetFeatureChange(n.notifierModelElement, n.feature as EStructuralFeature);
			eChangeIdManager.setOrGenerateIds(change);
			op = #[change];
		}
		return op;
	}

	def private Iterable<EChange> recursiveAddition(EObject eObject) {
		val result = <EChange>newArrayList
		recursivelyAddChangesForNonDefaultAttributesAndContainments(eObject, result)
		recursivelyAddChangesForNonDefaultReferences(eObject, result)
		for (change : result) {
			eChangeIdManager.setOrGenerateIds(change);
		}
		return result;
	}

	def private Iterable<EChange> recursiveRemoval(EObject eObject) {
		val result = <EChange>newArrayList
		// if (RECORD_DELETE_RECURSIVELY) {
		recursivelyRemoveChangesForNonDefaultReferences(eObject, result)
		recursivelyRemoveChangesForNonDefaultAttributesAndContainments(eObject, result)
		// }
		for (change : result) {
			eChangeIdManager.setOrGenerateIds(change);
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
		return if (reference.many)
			eObject.eGet(reference) as List<? extends EObject>
		else
			#[eObject.eGet(reference) as EObject];
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
