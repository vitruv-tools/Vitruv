package tools.vitruv.framework.change.recording;

import java.lang.reflect.Field;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.NotificationImpl;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * NotificationInfo is a type safe wrapper for EMF Notifications. It wraps a {@link Notification}
 * and implements a few additional getter methods
 *
 */
public class NotificationInfo implements Notification {

	private final Notification notification;
	private String validationMessage;

	/**
	 * The constructor needs the notification to wrap.
	 *
	 * @param n the notification to wrap
	 */
	public NotificationInfo(Notification n) {
		notification = n;
	}

	/**
	 * @return the structural feature affected
	 */
	public EStructuralFeature getStructuralFeature() {
		if (getFeature() instanceof EStructuralFeature) {
			return (EStructuralFeature) getFeature();
		}
		return null;
	}

	/**
	 * @return the validationMessage
	 */
	public String getValidationMessage() {
		return validationMessage;
	}

	/**
	 * @param validationMessage the validationMessage to set
	 */
	protected void setValidationMessage(String validationMessage) {
		this.validationMessage = validationMessage;
	}

	/**
	 * @return whether this notification signals a change of an attribute value
	 */
	public boolean isAttributeNotification() {
		return notification.getFeature() instanceof EAttribute;
	}

	/**
	 * @return whether this notification signals a change of a reference value
	 */
	public boolean isReferenceNotification() {
		return notification.getFeature() instanceof EReference;
	}

	/**
	 * @return the EAttribute if the notification relates to an attribute, null otherwise
	 */
	public EAttribute getAttribute() {
		if (isAttributeNotification()) {
			return (EAttribute) notification.getFeature();
		}
		return null;
	}

	/**
	 * @return the EReference if the notification relates to a reference feature, null otherwise
	 */
	public EReference getReference() {
		if (isReferenceNotification()) {
			return (EReference) notification.getFeature();
		}
		return null;
	}

	/**
	 * @return true if the changed feature is marked transient, false otherwise
	 */
	public boolean isTransient() {
		return isReferenceNotification() && getReference().isTransient()
			|| isAttributeNotification() && getAttribute().isTransient();
	}

	/**
	 * @return true if the event is of type Notification.ADD, false otherwise
	 */
	public boolean isAddEvent() {
		return getEventType() == Notification.ADD;
	}

	/**
	 * @return true if the event is of type Notification.REMOVE, false otherwise
	 */

	public boolean isRemoveEvent() {
		return getEventType() == Notification.REMOVE;
	}

	/**
	 * @return true if the event is of type Notification.SET, false otherwise
	 */

	public boolean isSetEvent() {
		return getEventType() == Notification.SET;
	}

	/**
	 * @return true if the event is of type Notification.ADD_MANY, false otherwise
	 */

	public boolean isAddManyEvent() {
		return getEventType() == Notification.ADD_MANY;
	}

	/**
	 * @return true if the event is of type Notification.REMOVE_MANY, false otherwise
	 */

	public boolean isRemoveManyEvent() {
		return getEventType() == Notification.REMOVE_MANY;
	}

	/**
	 * @return true if the event is of type Notification.MOVE, false otherwise
	 */
	public boolean isMoveEvent() {
		return getEventType() == Notification.MOVE;
	}

	/**
	 * @return true if this notification is followed by more notifications in a chain, false if this is the last
	 *         notification of a chain
	 */

	public boolean hasNext() {

		if (!(notification instanceof NotificationImpl)) {
			return false;
		}

		try {
			final Field declaredField = NotificationImpl.class.getDeclaredField("next"); //$NON-NLS-1$
			declaredField.setAccessible(true);
			final Object object = declaredField.get(notification);
			final Notification nextNotification = (Notification) object;

			if (nextNotification == null) {
				return false;
			}

			final Object feature = nextNotification.getFeature();

			if (feature instanceof EReference) {
				final EReference eReference = (EReference) feature;

				if (eReference.isTransient()) {
					return false;
				}
			}

//			// notifications from project are never propagated, thus considered nonexistent
//			// however, they themselves might have followups
//			if (nextNotification.getNotifier() instanceof Project) {
//				final NotificationInfo nextNextInfo = new NotificationInfo(nextNotification);
//				return nextNextInfo.hasNext();
//			}
			return true;

			// BEGIN SUPRESS CATCH EXCEPTION
		} catch (final RuntimeException e) {
			return false;
			// END SUPRESS CATCH EXCEPTION
		} catch (final IllegalAccessException e) {
			return false;
		} catch (final NoSuchFieldException e) {
			return false;
		}
	}

	/**
	 * @return @see org.eclipse.emf.common.notify.Notification#getEventType()
	 */
	public int getEventType() {
		return notification.getEventType();
	}

	/**
	 * @return @see org.eclipse.emf.common.notify.Notification#getFeature()
	 */
	public Object getFeature() {
		return notification.getFeature();
	}

	/**
	 * @param expectedClass @see org.eclipse.emf.common.notify.Notification#getFeatureID(java.lang.Class)
	 * @return @see org.eclipse.emf.common.notify.Notification#getFeatureID(java.lang.Class)
	 */
	public int getFeatureID(Class<?> expectedClass) {
		return notification.getFeatureID(expectedClass);
	}

	/**
	 * @return @see org.eclipse.emf.common.notify.Notification#getNewBooleanValue()
	 */

	public boolean getNewBooleanValue() {
		return notification.getNewBooleanValue();
	}

	/**
	 * @return @see org.eclipse.emf.common.notify.Notification#getNewByteValue()
	 */
	public byte getNewByteValue() {
		return notification.getNewByteValue();
	}

	/**
	 * @return @see org.eclipse.emf.common.notify.Notification#getNewCharValue()
	 */
	public char getNewCharValue() {
		return notification.getNewCharValue();
	}

	/**
	 * @return @see org.eclipse.emf.common.notify.Notification#getNewDoubleValue()
	 */

	public double getNewDoubleValue() {
		return notification.getNewDoubleValue();
	}

	/**
	 * @return @see org.eclipse.emf.common.notify.Notification#getNewFloatValue()
	 */
	public float getNewFloatValue() {
		return notification.getNewFloatValue();
	}

	/**
	 * @return @see org.eclipse.emf.common.notify.Notification#getNewIntValue()
	 */
	public int getNewIntValue() {
		return notification.getNewIntValue();
	}

	/**
	 * @return @see org.eclipse.emf.common.notify.Notification#getNewLongValue()
	 */
	public long getNewLongValue() {
		return notification.getNewLongValue();
	}

	/**
	 * @return @see org.eclipse.emf.common.notify.Notification#getNewShortValue()
	 */
	public short getNewShortValue() {
		return notification.getNewShortValue();
	}

	/**
	 * @return @see org.eclipse.emf.common.notify.Notification#getNewStringValue()
	 */
	public String getNewStringValue() {
		return notification.getNewStringValue();
	}

	/**
	 * @return @see org.eclipse.emf.common.notify.Notification#getNewValue()
	 */
	public Object getNewValue() {
		return notification.getNewValue();
	}

	/**
	 * @return @see org.eclipse.emf.common.notify.Notification#getNewValue()
	 */
	public EObject getNewModelElementValue() {
		return (EObject) notification.getNewValue();
	}

	/**
	 * @return @see org.eclipse.emf.common.notify.Notification#getNotifier()
	 */
	public Object getNotifier() {
		return notification.getNotifier();
	}

	/**
	 * @return @see org.eclipse.emf.common.notify.Notification#getOldBooleanValue()
	 */
	public boolean getOldBooleanValue() {
		return notification.getOldBooleanValue();
	}

	/**
	 * @return @see org.eclipse.emf.common.notify.Notification#getOldByteValue()
	 */
	public byte getOldByteValue() {
		return notification.getOldByteValue();
	}

	/**
	 * @return @see org.eclipse.emf.common.notify.Notification#getOldCharValue()
	 */
	public char getOldCharValue() {
		return notification.getOldCharValue();
	}

	/**
	 * @return @see org.eclipse.emf.common.notify.Notification#getOldDoubleValue()
	 */
	public double getOldDoubleValue() {
		return notification.getOldDoubleValue();
	}

	/**
	 * @return @see org.eclipse.emf.common.notify.Notification#getOldFloatValue()
	 */
	public float getOldFloatValue() {
		return notification.getOldFloatValue();
	}

	/**
	 * @return @see org.eclipse.emf.common.notify.Notification#getOldIntValue()
	 */
	public int getOldIntValue() {
		return notification.getOldIntValue();
	}

	/**
	 * @return @see org.eclipse.emf.common.notify.Notification#getOldLongValue()
	 */
	public long getOldLongValue() {
		return notification.getOldLongValue();
	}

	/**
	 * @return @see org.eclipse.emf.common.notify.Notification#getOldShortValue()
	 */
	public short getOldShortValue() {
		return notification.getOldShortValue();
	}

	/**
	 * @return @see org.eclipse.emf.common.notify.Notification#getOldStringValue()
	 */
	public String getOldStringValue() {
		return notification.getOldStringValue();
	}

	/**
	 * @return @see org.eclipse.emf.common.notify.Notification#getOldValue()
	 */
	public Object getOldValue() {
		return notification.getOldValue();
	}

	/**
	 * @return @see org.eclipse.emf.common.notify.Notification#getOldValue()
	 */
	public EObject getOldModelElementValue() {
		return (EObject) notification.getOldValue();
	}

	/**
	 * @return @see org.eclipse.emf.common.notify.Notification#getPosition()
	 */
	public int getPosition() {
		return notification.getPosition();
	}
	
	public int getInitialIndex() {
		return getPosition() == NotificationInfo.NO_INDEX ? 0 : getPosition();
	}

	/**
	 * @return @see org.eclipse.emf.common.notify.Notification#isReset()
	 */
	public boolean isReset() {
		return notification.isReset();
	}

	/**
	 * @return @see org.eclipse.emf.common.notify.Notification#isTouch()
	 */
	public boolean isTouch() {
		return notification.isTouch();
	}

	/**
	 * @param notification @see
	 *            org.eclipse.emf.common.notify.Notification#merge(org.eclipse.emf.common.notify.Notification)
	 * @return @see org.eclipse.emf.common.notify.Notification#merge(org.eclipse.emf.common.notify.Notification)
	 */
	public boolean merge(Notification notification) {
		return notification.merge(notification);
	}

	/**
	 * @return true if the feature is unsetable and was in the set state before this notification.
	 */
	public boolean wasUnset() {
		return getStructuralFeature().isUnsettable() && notification.wasSet() && !getNotifierModelElement().eIsSet(getStructuralFeature());
	}

	/**
	 * @return @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return notification.toString();
	}

	/**
	 * @return @see org.eclipse.emf.common.notify.Notification#getNotifier()
	 */
	public EObject getNotifierModelElement() {
		return (EObject) notification.getNotifier();
	}

	/**
	 * @return a string useful for debugging only
	 */
	public String getDebugString() {
		final StringBuilder sb = new StringBuilder();
		// handle type

		if (isAddEvent()) {
			sb.append("ADD"); //$NON-NLS-1$
		} else if (isSetEvent()) {
			sb.append("SET"); //$NON-NLS-1$
		} else if (isAddManyEvent()) {
			sb.append("ADD_MANY"); //$NON-NLS-1$
		} else if (isRemoveEvent()) {
			sb.append("REMOVE"); //$NON-NLS-1$
		} else if (isRemoveManyEvent()) {
			sb.append("REMOVE_MANY"); //$NON-NLS-1$
		} else if (isMoveEvent()) {
			sb.append("MOVE"); //$NON-NLS-1$
		} else {
			sb.append(getEventType());
		}

		sb.append(" val: " + getValidationMessage()); //$NON-NLS-1$
		final EObject n = (EObject) notification.getNotifier();

		sb.append(" / on: " + extractName(n)); //$NON-NLS-1$
		sb.append("."); //$NON-NLS-1$
		if (isAttributeNotification()) {
			sb.append(getAttribute().getName());
		} else if (isReferenceNotification()) {
			sb.append(getReference().getName());
		}
		sb.append(" / old: "); //$NON-NLS-1$
		if (getOldValue() instanceof EObject) {
			sb.append(extractName((EObject) getOldValue()));
		} else {
			sb.append(getOldValue());
		}
		sb.append(" / new: "); //$NON-NLS-1$
		if (getNewValue() instanceof EObject) {
			sb.append(extractName((EObject) getNewValue()));
		} else {
			sb.append(getNewValue());
		}

		return sb.toString();

	}

	private String extractName(EObject o) {

		if (o == null) {
			return null;
		}

		final EStructuralFeature f = o.eClass().getEStructuralFeature("name"); //$NON-NLS-1$
		if (f != null && o.eGet(f) != null) {
			return "'" + (String) o.eGet(f) + "'"; //$NON-NLS-1$//$NON-NLS-2$
		}
		return o.eClass().getName();
	}

	/**
	 * Returns the type of the {@link Notification}.
	 *
	 * @return a {@link Notification} type
	 */
	public Class<? extends Notification> getNotificationType() {
		return notification.getClass();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.common.notify.Notification#wasSet()
	 */
	public boolean wasSet() {
		return notification.wasSet();
	}
}
