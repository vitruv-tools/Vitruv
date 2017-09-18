/**
 */
package tools.vitruv.framework.change.echange.feature.attribute.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import tools.vitruv.framework.change.echange.feature.attribute.AttributePackage;
import tools.vitruv.framework.change.echange.feature.attribute.SubtractiveAttributeEChange;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Subtractive Attribute EChange</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.feature.attribute.impl.SubtractiveAttributeEChangeImpl#getOldValue <em>Old Value</em>}</li>
 *   <li>{@link tools.vitruv.framework.change.echange.feature.attribute.impl.SubtractiveAttributeEChangeImpl#isIsUnset <em>Is Unset</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class SubtractiveAttributeEChangeImpl<A extends EObject, T extends Object> extends UpdateAttributeEChangeImpl<A> implements SubtractiveAttributeEChange<A, T> {
	/**
	 * The cached value of the '{@link #getOldValue() <em>Old Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOldValue()
	 * @generated
	 * @ordered
	 */
	protected T oldValue;

	/**
	 * The default value of the '{@link #isIsUnset() <em>Is Unset</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsUnset()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_UNSET_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIsUnset() <em>Is Unset</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsUnset()
	 * @generated
	 * @ordered
	 */
	protected boolean isUnset = IS_UNSET_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SubtractiveAttributeEChangeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AttributePackage.Literals.SUBTRACTIVE_ATTRIBUTE_ECHANGE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public T getOldValue() {
		return oldValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOldValue(T newOldValue) {
		T oldOldValue = oldValue;
		oldValue = newOldValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AttributePackage.SUBTRACTIVE_ATTRIBUTE_ECHANGE__OLD_VALUE, oldOldValue, oldValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isIsUnset() {
		return isUnset;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIsUnset(boolean newIsUnset) {
		boolean oldIsUnset = isUnset;
		isUnset = newIsUnset;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AttributePackage.SUBTRACTIVE_ATTRIBUTE_ECHANGE__IS_UNSET, oldIsUnset, isUnset));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case AttributePackage.SUBTRACTIVE_ATTRIBUTE_ECHANGE__OLD_VALUE:
				return getOldValue();
			case AttributePackage.SUBTRACTIVE_ATTRIBUTE_ECHANGE__IS_UNSET:
				return isIsUnset();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case AttributePackage.SUBTRACTIVE_ATTRIBUTE_ECHANGE__OLD_VALUE:
				setOldValue((T)newValue);
				return;
			case AttributePackage.SUBTRACTIVE_ATTRIBUTE_ECHANGE__IS_UNSET:
				setIsUnset((Boolean)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case AttributePackage.SUBTRACTIVE_ATTRIBUTE_ECHANGE__OLD_VALUE:
				setOldValue((T)null);
				return;
			case AttributePackage.SUBTRACTIVE_ATTRIBUTE_ECHANGE__IS_UNSET:
				setIsUnset(IS_UNSET_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case AttributePackage.SUBTRACTIVE_ATTRIBUTE_ECHANGE__OLD_VALUE:
				return oldValue != null;
			case AttributePackage.SUBTRACTIVE_ATTRIBUTE_ECHANGE__IS_UNSET:
				return isUnset != IS_UNSET_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (oldValue: ");
		result.append(oldValue);
		result.append(", isUnset: ");
		result.append(isUnset);
		result.append(')');
		return result.toString();
	}

} //SubtractiveAttributeEChangeImpl
