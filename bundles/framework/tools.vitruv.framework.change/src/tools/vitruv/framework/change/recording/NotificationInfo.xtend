package tools.vitruv.framework.change.recording

import java.lang.reflect.Field
import org.eclipse.emf.common.notify.Notification
import org.eclipse.emf.common.notify.impl.NotificationImpl
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtend.lib.annotations.Delegate
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor

/** 
 * NotificationInfo is a type safe wrapper for EMF Notifications. It wraps a {@link Notification}and implements a few 
 * additional getter methods
 */
@FinalFieldsConstructor
class NotificationInfo implements Notification {
	@Delegate
	val Notification notification
	@Accessors
	var String validationMessage

	/** 
	 * @return the structural feature affected
	 */
	def EStructuralFeature getStructuralFeature() {
		val feature = this.feature
		if (feature instanceof EStructuralFeature) feature else null
	}

	def boolean isStructuralFeatureNotification() {
		isAttributeNotification || isReferenceNotification
	}

	def boolean isAttributeNotification() {
		feature instanceof EAttribute
	}

	/** 
	 * @return whether this notification signals a change of a reference value
	 */
	def boolean isReferenceNotification() {
		feature instanceof EReference
	}

	/** 
	 * @return the EAttribute if the notification relates to an attribute, null otherwise
	 */
	def EAttribute getAttribute() {
		val feature = this.feature
		if (feature instanceof EAttribute) feature else null
	}

	/** 
	 * @return the EReference if the notification relates to a reference feature, null otherwise
	 */
	def EReference getReference() {
		val feature = this.feature
		if (feature instanceof EReference) feature else null
	}

	/** 
	 * @return true if the changed feature is marked transient, false otherwise
	 */
	def boolean isTransient() {
		isStructuralFeatureNotification && structuralFeature.isTransient
	}

	/** 
	 * @return true if the event is of type Notification.ADD, false otherwise
	 */
	def boolean isAddEvent() {
		eventType === ADD
	}

	/** 
	 * @return true if the event is of type Notification.REMOVE, false otherwise
	 */
	def boolean isRemoveEvent() {
		eventType === REMOVE
	}

	/** 
	 * @return true if the event is of type Notification.SET, false otherwise
	 */
	def boolean isSetEvent() {
		eventType === SET
	}

	/** 
	 * @return true if the event is of type Notification.ADD_MANY, false otherwise
	 */
	def boolean isAddManyEvent() {
		return getEventType() === Notification.ADD_MANY
	}

	/** 
	 * @return true if the event is of type Notification.REMOVE_MANY, false otherwise
	 */
	def boolean isRemoveManyEvent() {
		eventType === REMOVE_MANY
	}

	/** 
	 * @return true if the event is of type Notification.MOVE, false otherwise
	 */
	def boolean isMoveEvent() {
		eventType === MOVE
	}

	/** 
	 * @return true if this notification is followed by more notifications in a chain, false if this is the last
	 * notification of a chain
	 */
	def boolean hasNext() {
		if (!(notification instanceof NotificationImpl)) {
			return false
		}
		try {
			val Field declaredField = NotificationImpl.getDeclaredField("next")
			// $NON-NLS-1$
			declaredField.setAccessible(true)
			val Object object = declaredField.get(notification)
			val Notification nextNotification = (object as Notification)
			if (nextNotification === null) {
				return false
			}
			val Object feature = nextNotification.getFeature()
			if (feature instanceof EReference) {
				val EReference eReference = (feature as EReference)
				if (eReference.isTransient()) {
					return false
				}
			}
			// // notifications from project are never propagated, thus considered nonexistent
			// // however, they themselves might have followups
			// if (nextNotification.getNotifier() instanceof Project) {
			// final NotificationInfo nextNextInfo = new NotificationInfo(nextNotification);
			// return nextNextInfo.hasNext();
			// }
			return true // BEGIN SUPRESS CATCH EXCEPTION
		} catch (RuntimeException e) {
			return false // END SUPRESS CATCH EXCEPTION
		} catch (IllegalAccessException e) {
			return false
		} catch (NoSuchFieldException e) {
			return false
		}

	}

	/** 
	 * @return @see Notification#getNewValue()
	 */
	def EObject getNewModelElementValue() {
		notification.newValue as EObject
	}

	/** 
	 * @return @see Notification#getOldValue()
	 */
	def EObject getOldModelElementValue() {
		notification.oldValue as EObject
	}

	def int getInitialIndex() {
		if (getPosition() === NotificationInfo.NO_INDEX) 0 else getPosition()
	}

	/** 
	 * @return true if the feature is unsettable and was in the set state before this notification.
	 */
	def boolean wasUnset() {
		isStructuralFeatureNotification && structuralFeature.isUnsettable && notification.wasSet &&
			!notifierModelElement.eIsSet(structuralFeature)
	}

	/** 
	 * @return @see Notification#getNotifier()
	 */
	def EObject getNotifierModelElement() {
		notification.notifier as EObject
	}

	/** 
	 * @return @see Notification#getNotifier()
	 */
	def Resource getNotifierResource() {
		notification.notifier as Resource
	}

	/** 
	 * @return a string useful for debugging only
	 */
	def String getDebugString() {
		val StringBuilder sb = new StringBuilder()
		// handle type
		if (isAddEvent()) {
			sb.append("ADD") // $NON-NLS-1$
		} else if (isSetEvent()) {
			sb.append("SET") // $NON-NLS-1$
		} else if (isAddManyEvent()) {
			sb.append("ADD_MANY") // $NON-NLS-1$
		} else if (isRemoveEvent()) {
			sb.append("REMOVE") // $NON-NLS-1$
		} else if (isRemoveManyEvent()) {
			sb.append("REMOVE_MANY") // $NON-NLS-1$
		} else if (isMoveEvent()) {
			sb.append("MOVE") // $NON-NLS-1$
		} else {
			sb.append(getEventType())
		}
		sb.append(''' val: «getValidationMessage()»''')
		// $NON-NLS-1$
		val EObject n = (notification.getNotifier() as EObject)
		sb.append(''' / on: «extractName(n)»''')
		// $NON-NLS-1$
		sb.append(".")
		// $NON-NLS-1$
		if (isAttributeNotification()) {
			sb.append(getAttribute().getName())
		} else if (isReferenceNotification()) {
			sb.append(getReference().getName())
		}
		sb.append(" / old: ")
		// $NON-NLS-1$
		if (getOldValue() instanceof EObject) {
			sb.append(extractName((getOldValue() as EObject)))
		} else {
			sb.append(getOldValue())
		}
		sb.append(" / new: ")
		// $NON-NLS-1$
		if (getNewValue() instanceof EObject) {
			sb.append(extractName((getNewValue() as EObject)))
		} else {
			sb.append(getNewValue())
		}
		return sb.toString()
	}

	def private String extractName(EObject o) {
		if (o === null) {
			return null
		}
		val EStructuralFeature f = o.eClass().getEStructuralFeature("name")
		// $NON-NLS-1$
		if (f !== null && o.eGet(f) !== null) {
			return ''''«»«(o.eGet(f) as String)»'«»''' // $NON-NLS-1$//$NON-NLS-2$
		}
		return o.eClass().getName()
	}

	/** 
	 * Returns the type of the {@link Notification}.
	 * @return a {@link Notification} type
	 */
	def Class<? extends Notification> getNotificationType() {
		notification.class
	}
}
