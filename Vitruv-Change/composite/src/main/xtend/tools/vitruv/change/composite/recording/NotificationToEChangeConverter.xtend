package tools.vitruv.change.composite.recording

import java.util.List
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import tools.vitruv.change.atomic.EChange
import tools.vitruv.change.atomic.TypeInferringAtomicEChangeFactory
import tools.vitruv.change.atomic.eobject.EObjectAddedEChange
import tools.vitruv.change.atomic.feature.attribute.AttributeFactory
import tools.vitruv.change.atomic.feature.reference.UpdateReferenceEChange

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import static extension tools.vitruv.change.composite.recording.EChangeCreationUtil.*

/** 
 * Converts an EMF notification to an {@link EChange}.
 * @author Heiko Klare
 */
@FinalFieldsConstructor
package final class NotificationToEChangeConverter {
	extension val TypeInferringAtomicEChangeFactory changeFactory = TypeInferringAtomicEChangeFactory.instance
	
	val (EObject, EObject)=>boolean isCreateChange
	
	def EChange<EObject> createDeleteChange(EObject eObject) {
		return createDeleteEObjectChange(eObject)
	}
	
	private def String convertExceptionMessage(EventType eventType, String notificationType) {
		String.format("Event type {} for {} Notifications unexpected.")
	}
	final String ATTRIBUTE_TYPE = "Attribute";
	final String REFERENCE_TYPE = "Reference";
	final String RESOURCE_CONTENTS_TYPE = "Resource Contents"
	
	/** 
	 * Converts the given notification to a list of {@link EChange}s.
	 * @param n the notification to convert
	 * @return the  {@link Iterable} of {@link EChange}s
	 */
	def Iterable<? extends EChange<EObject>> convert(extension NotificationInfo notification) {
		return switch (notification) {
			case isTouch,
			case isTransient,
			case oldValue == newValue:
				emptyList()
			case notification.isAttributeNotification:
				switch (eventTypeEnum) {
					case SET: handleSetAttribute(notification)
					case UNSET: handleUnsetAttribute(notification)
					case ADD: handleInsertAttribute(notification)
					case ADD_MANY: handleMultiInsertAttribute(notification)
					case REMOVE: handleRemoveAttribute(notification)
					case REMOVE_MANY: handleMultiRemoveAttribute(notification)
					case MOVE: handleMoveAttribute(notification)
					case RESOLVE: throw new IllegalArgumentException(convertExceptionMessage(EventType.RESOLVE, ATTRIBUTE_TYPE))
					case REMOVING_ADAPTER: throw new IllegalArgumentException(convertExceptionMessage(EventType.REMOVING_ADAPTER, ATTRIBUTE_TYPE))
					default: throw new IllegalArgumentException("Unexpected event type " + eventType)
				}
			case notification.isReferenceNotification:
				switch (eventTypeEnum) {
					case SET: handleSetReference(notification)
					case UNSET: handleUnsetReference(notification)
					case ADD: handleInsertReference(notification)
					case ADD_MANY: handleMultiInsertReference(notification)
					case REMOVE: handleRemoveReference(notification)
					case REMOVE_MANY: handleMultiRemoveReference(notification)
					case MOVE: handleMoveReference(notification)
					case RESOLVE: throw new IllegalArgumentException(convertExceptionMessage(EventType.RESOLVE, REFERENCE_TYPE))
					case REMOVING_ADAPTER: throw new IllegalArgumentException(convertExceptionMessage(EventType.REMOVING_ADAPTER, REFERENCE_TYPE))
					default: throw new IllegalArgumentException("Unexpected event type " + eventType)
				}
			case notifier instanceof Resource:
				switch (getFeatureID(Resource)) {
					case Resource.RESOURCE__CONTENTS:
						switch (eventTypeEnum) {
							case ADD: handleInsertRootChange(notification)
							case ADD_MANY: handleMultiInsertRootChange(notification)
							case REMOVE: handleRemoveRootChange(notification)
							case REMOVE_MANY: handleMultiRemoveRootChange(notification)
							case SET: throw new IllegalArgumentException(convertExceptionMessage(EventType.SET, RESOURCE_CONTENTS_TYPE))
							case UNSET: throw new IllegalArgumentException(convertExceptionMessage(EventType.UNSET, RESOURCE_CONTENTS_TYPE))
							case MOVE: throw new IllegalArgumentException(convertExceptionMessage(EventType.MOVE, RESOURCE_CONTENTS_TYPE))
							case RESOLVE: throw new IllegalArgumentException(convertExceptionMessage(EventType.RESOLVE, RESOURCE_CONTENTS_TYPE))
							case REMOVING_ADAPTER: throw new IllegalArgumentException(convertExceptionMessage(EventType.REMOVING_ADAPTER, RESOURCE_CONTENTS_TYPE))
							default: throw new IllegalArgumentException("Unexpected event type " + eventType)
						}
					case Resource.RESOURCE__URI:
						switch (eventTypeEnum) {
							case SET: handleSetUriChange(notification)
							default: throw new IllegalArgumentException("Unexpected event type " + eventType + " for Resource URI Notification.")
						}
					default:
						emptyList()
				}
			default:
				emptyList()
		}
	}
	
	private def Iterable<? extends EChange<EObject>> handleMoveAttribute(extension NotificationInfo notification) {
		#[
			createRemoveAttributeChange(notifierModelElement, attribute, oldValue as Integer, newValue),
			createInsertAttributeChange(notifierModelElement, attribute, position, newValue)
		]
	}
	
	private def Iterable<? extends EChange<EObject>> handleMoveReference(extension NotificationInfo notification) {
		#[
			createRemoveReferenceChange(notifierModelElement, reference, newModelElementValue, oldValue as Integer),
			createInsertReferenceChange(notifierModelElement, reference, newModelElementValue, position)
		]
	}

	def private Iterable<? extends EChange<EObject>> handleSetAttribute(extension NotificationInfo notification) {
		switch (notification) {
			case !attribute.isMany:
				handleReplaceAttribute(notification)
			case oldValue !== null && newValue !== null:
				handleRemoveAttribute(notification) + handleInsertAttribute(notification)
			case newValue !== null:
				handleInsertAttribute(notification)
			case oldValue !== null:
				handleRemoveAttribute(notification)
			default:
				emptyList()
		}
	}

	private def Iterable<? extends EChange<EObject>> handleSetReference(extension NotificationInfo notification) {
		switch (notification) {
			case !reference.isMany:
				handleReplaceReference(notification)
			case oldValue !== null && newValue !== null:
				handleRemoveReference(notification) + handleInsertReference(notification)
			case newValue !== null:
				handleInsertReference(notification)
			case oldValue !== null:
				handleRemoveReference(notification)
			default:
				emptyList()
		}
	}

	def private Iterable<? extends EChange<EObject>> handleUnsetAttribute(extension NotificationInfo notification) {
		return if (!attribute.isMany) {
			handleSetAttribute(notification)
		} else {
			List.of(createUnsetFeatureChange(notifierModelElement, attribute))
		}
	}

	private def Iterable<? extends EChange<EObject>> handleUnsetReference(extension NotificationInfo notification) {
		if (!reference.isMany) {
			handleSetReference(notification)
		} else {
			List.of(createUnsetFeatureChange(notifierModelElement, reference))
		}
	}

	private def Iterable<? extends EChange<EObject>> handleReplaceAttribute(extension NotificationInfo notification) {
		val change = AttributeFactory.eINSTANCE.createReplaceSingleValuedEAttribute()
		change.oldValue = oldValue
		change.newValue = newValue
		change.affectedFeature = attribute
		change.affectedElement = notifierModelElement
		change.isUnset = wasUnset
		return List.of(change)
	}

	private def Iterable<? extends EChange<EObject>> handleReplaceReference(extension NotificationInfo notification) {
		val change = createReplaceSingleReferenceChange(notifierModelElement, reference, oldModelElementValue,
			newModelElementValue)
		change.isUnset = notification.wasUnset
		return change.surroundWithCreateAndFeatureChangesIfNecessary()
	}

	private def handleRemoveAttribute(extension NotificationInfo notification) {
		createRemoveAttributeChange(notifierModelElement, attribute, position, oldValue).
			addUnsetChangeIfNecessary(notification)
	}

	private def handleMultiRemoveAttribute(extension NotificationInfo notification) {
		// TODO HK Is that check necessary?
		if (newValue === null) {
			val oldValues = oldValue as List<?>
			oldValues.reverseView.mapFixedIndexed [ index, value |
				val valueIndex = initialIndex + oldValues.size - 1 - index
				createRemoveAttributeChange(notifierModelElement, attribute, valueIndex, value)
			].addUnsetChangeIfNecessary(notification)
		} else {
			unsetChangeOrEmpty(notification)
		}
	}

	private def Iterable<? extends EChange<EObject>> handleRemoveReference(extension NotificationInfo notification) {
		createRemoveReferenceChange(notifierModelElement, reference, oldModelElementValue, position).
			addUnsetChangeIfNecessary(notification)
	}

	private def Iterable<? extends EChange<EObject>> handleMultiRemoveReference(extension NotificationInfo notification) {
		if (newValue === null) {
			val oldValues = oldValue as List<EObject>
			oldValues.reverseView.mapFixedIndexed [ index, value |
				val valueIndex = initialIndex + oldValues.size - 1 - index
				createRemoveReferenceChange(notifierModelElement, reference, value, valueIndex)
			].addUnsetChangeIfNecessary(notification)
		} else {
			unsetChangeOrEmpty(notification)
		}
	}

	private def handleInsertAttribute(extension NotificationInfo notification) {
		List.of(createInsertAttributeChange(notifierModelElement, attribute, position, newValue))
	}

	private def handleMultiInsertAttribute(extension NotificationInfo notification) {
		(newValue as List<?>).mapFixedIndexed [ index, value |
			createInsertAttributeChange(notifierModelElement, attribute, initialIndex + index, value)
		]
	}

	private def Iterable<? extends EChange<EObject>> handleInsertReference(extension NotificationInfo notification) {
		createInsertReferenceChange(notifierModelElement, reference, newModelElementValue, position).
			surroundWithCreateAndFeatureChangesIfNecessary()
	}

	private def Iterable<? extends EChange<EObject>> handleMultiInsertReference(extension NotificationInfo notification) {
		(newValue as List<EObject>).flatMapFixedIndexed [ index, value |
			createInsertReferenceChange(notifierModelElement, reference, value, initialIndex + index).
				surroundWithCreateAndFeatureChangesIfNecessary()
		]
	}

	private def handleInsertRootChange(extension NotificationInfo notification) {
		createInsertRootChange(newModelElementValue, notifierResource, position).
			surroundWithCreateAndFeatureChangesIfNecessary()
	}

	private def handleMultiInsertRootChange(extension NotificationInfo notification) {
		(notification.newValue as List<EObject>).flatMapFixedIndexed [ index, value |
			createInsertRootChange(value, notifierResource, initialIndex + index).
				surroundWithCreateAndFeatureChangesIfNecessary()
		]
	}

	private def handleRemoveRootChange(extension NotificationInfo notification) {
		List.of(createRemoveRootChange(oldModelElementValue, notifierResource, position))
	}

	private def handleMultiRemoveRootChange(extension NotificationInfo notification) {
		val oldValues = notification.oldValue as List<EObject>
		oldValues.reverseView.mapFixedIndexed [ index, value |
			val valueIndex = initialIndex + oldValues.size - 1 - index
			createRemoveRootChange(value, notifierResource, valueIndex)
		]
	}

	private def Iterable<? extends EChange<EObject>> handleSetUriChange(extension NotificationInfo notification) {
		val oldUri = notification.oldValue as URI
		notifierResource.contents.mapFixedIndexed [ index, value |
			val valueIndex = initialIndex + notifierResource.contents.size - 1 - index
			val oldResource = notifierResource.resourceSet.createResource(oldUri)
			createRemoveRootChange(value, oldResource, oldUri, valueIndex)
		] + notifierResource.contents.flatMapFixedIndexed [ index, value |
			createInsertRootChange(value, notifierResource, initialIndex + index).
				surroundWithCreateAndFeatureChangesIfNecessary()
		]
	}

	def private Iterable<? extends EChange<EObject>> allAdditiveChangesForChangeRelevantFeatures(EObjectAddedEChange<EObject> change, EObject eObject) {
		change.newValue.walkChangeRelevantFeatures(
			[object, attribute|createAdditiveEChangeForAttribute(object, attribute)],
			[object, reference|if (reference.isContainment) createAdditiveEChangeForReferencedObject(object, reference, [referencedObject | isCreateChange.apply(object, referencedObject)])]
		) + change.newValue.walkChangeRelevantFeatures(null) [ object, reference |
			if (!reference.isContainment) createAdditiveEChangeForReferencedObject(object, reference, [false])
		]
	}

	def private static Iterable<? extends EChange<EObject>> walkChangeRelevantFeatures(
		EObject eObject,
		(EObject, EAttribute)=>Iterable<? extends EChange<EObject>> attributeVisitor,
		(EObject, EReference)=>Iterable<? extends EChange<EObject>> referenceVisitor
	) {
		val changeRelevantFeatures = eObject.eClass.EAllStructuralFeatures.filter [
			eObject.hasChangeableUnderivedPersistedNotContainingNonDefaultValue(it)
		]
		val thisLayerAttributeResults = changeRelevantFeatures.filter(EAttribute).flatMapFixed [
			attributeVisitor?.apply(eObject, it) ?: emptyList()
		]
		val thisLayerReferenceResults = changeRelevantFeatures.filter(EReference).flatMapFixed [
			referenceVisitor?.apply(eObject, it) ?: emptyList()
		]
		val nextLayer = changeRelevantFeatures.filter(EReference).filter[isContainment].flatMap [
			eObject.getReferencedElements(it)
		].flatMapFixed [
			it.walkChangeRelevantFeatures(attributeVisitor, referenceVisitor)
		]
		thisLayerAttributeResults + thisLayerReferenceResults + nextLayer
	}

	def static private Iterable<EObject> getReferencedElements(EObject eObject, EReference reference) {
		return if (reference.many)
			eObject.eGet(reference) as Iterable<EObject>
		else
			List.of(eObject.eGet(reference) as EObject)
	}

	def private unsetChangeOrEmpty(NotificationInfo notification) {
		if (notification.wasUnset) {
			List.of(createUnsetFeatureChange(notification.notifierModelElement, notification.structuralFeature))
		} else
			emptyList()
	}

	def private <T extends EChange<EObject>> addUnsetChangeIfNecessary(Iterable<T> changes, NotificationInfo notification) {
		return if (notification.wasUnset)
			changes +
				List.of(createUnsetFeatureChange(notification.notifierModelElement, notification.structuralFeature))
		else
			changes
	}

	def private addUnsetChangeIfNecessary(EChange<EObject> change, NotificationInfo notification) {
		return if (notification.wasUnset)
			List.of(change, createUnsetFeatureChange(notification.notifierModelElement, notification.structuralFeature))
		else
			List.of(change)
	}

	private def Iterable<? extends EChange<EObject>> surroundWithCreateAndFeatureChangesIfNecessary(
		EObjectAddedEChange<EObject> change) {
		val affectedElement = switch change {
			UpdateReferenceEChange<EObject>: change.affectedElement
			default: null
		}
		return if (isCreateChange.apply(affectedElement, change.newValue)) {
			val createChange = createCreateEObjectChange(change.newValue)
			List.of(createChange, change) + allAdditiveChangesForChangeRelevantFeatures(change, change.newValue)
		} else
			List.of(change)
	}

}
